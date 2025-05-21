package com.mli.mpro.location.services.impl;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.GetQueueUrlResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mli.mpro.productRestriction.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.amazonaws.services.kinesis.model.PutRecordResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.common.exception.ErrorMessageConfig;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.location.services.ProposalStreamPushService;
import com.mli.mpro.proposal.models.InputRequest;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.proposal.models.Request;
import com.mli.mpro.proposal.models.RequestData;
import com.mli.mpro.proposal.models.RequestPayload;
import com.mli.mpro.utils.Utility;
import org.springframework.util.StringUtils;

import static java.util.Objects.isNull;

@Service
public class ProposalStreamPushServiceImpl implements ProposalStreamPushService {

	@Value("${urlDetails.proposalStream}")
	private String proposalStream ;
	@Value("${partitionkey.enable.flag}")
	private String partitionKeyEnable ;
	@Autowired
	AmazonSQS sqs;
	@Value("${urlDetails.proposalQueue}")
	private String proposalQueue;
	
	private ErrorMessageConfig errorMessageConfig;
	private static final Logger log = LoggerFactory.getLogger(ProposalStreamPushServiceImpl.class);
	private static final ObjectMapper JSON = new ObjectMapper();
	private static final String REGION_NAME = "ap-south-1";
	private List<String> errorMessages = new ArrayList<>();

	private ClientConfiguration getClientConfiguration() {
		log.info("initializing kinesis client configuration");
		ClientConfiguration config = new ClientConfiguration();
		config.setConnectionTimeout(ClientConfiguration.DEFAULT_CONNECTION_TIMEOUT);
		config.setMaxErrorRetry(3);
		config.setRetryPolicy(ClientConfiguration.DEFAULT_RETRY_POLICY);
		config.setUseReaper(ClientConfiguration.DEFAULT_USE_REAPER);
		return config;
	}

	@Override
	public boolean produceToProposalStream(InputRequest inputRequest) throws UserHandledException {
		try {

			InputRequest requestForStreamPush = setDataForStreamPush(inputRequest);
			String policyNumber = "1";
			if(AppConstants.YES.equalsIgnoreCase(partitionKeyEnable)) {
				policyNumber = getPolicyNumberFromRequest(inputRequest);
				log.info("produceToProposalStream called with policyNumber {} inputRequest {}", policyNumber, requestForStreamPush);
			}else{
				log.info("produceToProposalStream called with inputRequest {}", requestForStreamPush);
			}
			AmazonKinesisClientBuilder clientBuilder = AmazonKinesisClientBuilder.standard();
			clientBuilder.setRegion(REGION_NAME);
			clientBuilder.setClientConfiguration(getClientConfiguration());

			AmazonKinesis kinesisClient = clientBuilder.build();
			log.info("kinesisClient: {}", kinesisClient);
			String sequenceNumberOfPreviousRecord = "100000000000000000000001";

			byte[] bytes = JSON.writeValueAsBytes(requestForStreamPush);

			PutRecordRequest putRecordRequest = new PutRecordRequest();
			putRecordRequest.setStreamName(proposalStream);
			putRecordRequest.setData(ByteBuffer.wrap(bytes));
			if(AppConstants.YES.equalsIgnoreCase(partitionKeyEnable)) {
				putRecordRequest.setPartitionKey(String.format("partitionKey-%s", policyNumber));
			}else{
				putRecordRequest.setPartitionKey(String.format("partitionKey-%d", 1));
			}
			putRecordRequest.setSequenceNumberForOrdering(sequenceNumberOfPreviousRecord);
			PutRecordResult putRecordResult = kinesisClient.putRecord(putRecordRequest);
			sequenceNumberOfPreviousRecord = putRecordResult.getSequenceNumber();
			log.info("sequenceNumberOfPreviousRecord:{} ", sequenceNumberOfPreviousRecord);
			if (sequenceNumberOfPreviousRecord == null) {
				return false;
			}
		} catch (Exception e) {
			log.error("Exception occured in produceToProposalStream from location {}", Utility.getExceptionAsString(e));
			if (errorMessages.size() == 0) {
				errorMessages.add(errorMessageConfig.getErrorMessages().get("proposalStreamPushError"));
			}
			throw new UserHandledException(errorMessages, HttpStatus.SERVICE_UNAVAILABLE);
		}
		return true;
	}

	@Override
	public boolean produceToProposalQueue(InputRequest inputRequest) throws UserHandledException {
		log.info("Pushing sqs message to queue :{}, inputRequest : {}",
				proposalQueue, inputRequest);
		String message = null;
		String uuid = UUID.randomUUID().toString();
		log.info("Details to save in SQSTriggerKeys {}", uuid);
		emptyIllustrationData(inputRequest.getRequest().getRequestData().getRequestPayload());
		inputRequest.getRequest().getRequestData().getRequestPayload().setUuid(uuid);
		try {
			message = new ObjectMapper().writeValueAsString(inputRequest);
			log.info("Pushing sqs final message to queue :{}, inputRequest : {}",
					proposalQueue, message);
		} catch (JsonProcessingException e) {
			log.error("There is some problem in parsing the json for queue message body: {}", Utility.getExceptionAsString(e));
			return false;
		}
		try {
			SendMessageRequest msgrequest = new SendMessageRequest();
			msgrequest.setMessageBody(message);
			GetQueueUrlResult queueUrl = sqs.getQueueUrl(proposalQueue);
			if (isNull(queueUrl)) {
				log.info("Error in finding sqs queue url : {}", queueUrl);
				return false;
			}
			msgrequest.setQueueUrl(queueUrl.getQueueUrl());
			sqs.sendMessage(msgrequest);
			log.info("Pushed sqs message to queue for transactionId {} ", inputRequest.getRequest().getRequestData().getRequestPayload().getProposalDetails().getTransactionId());
		} catch (Exception e) {
			log.error("There is some problem in pushing object with transactionId {} and Exception message {}", inputRequest.getRequest().getRequestData().getRequestPayload().getProposalDetails().getTransactionId(), Utility.getExceptionAsString(e));
			log.info("Pushing into Stream");
			produceToProposalStream(inputRequest);
		}
		return true;
	}

	private void emptyIllustrationData(RequestPayload requestPayload) {
		try {
			if (Objects.nonNull(requestPayload.getProposalDetails())
					&& Objects.nonNull(requestPayload.getProposalDetails().getBancaDetails())
					&& Objects.nonNull(requestPayload.getProposalDetails().getBancaDetails().getIllustrationDetails())
					&& Objects.nonNull(requestPayload.getProposalDetails().getBancaDetails().getIllustrationDetails().getIllustrationOutput())) {
				requestPayload.getProposalDetails().getBancaDetails().getIllustrationDetails().setIllustrationOutput(null);
				requestPayload.getProposalDetails().getBancaDetails().setRuleOutput(null);
			}
		}catch (Exception exp){
			log.error("Exception while setting illustration data as null {}", Utility.getExceptionAsString(exp));
		}
	}

	private String getPolicyNumberFromRequest(InputRequest inputRequest) {
		try {
			RequestPayload requestPayload = inputRequest.getRequest().getRequestData().getRequestPayload();
			if(Objects.nonNull(requestPayload.getProposalDetails())){
				return requestPayload.getProposalDetails().getApplicationDetails().getPolicyNumber();
			}
			return StringUtils.isEmpty(requestPayload.getPolicyNumber())? "1": requestPayload.getPolicyNumber();
		}catch (Exception e){
			log.error("Error occurred while fetching policyNumber before stream call {}", Utility.getExceptionAsString(e));
		}
		return "1";
	}

	private InputRequest setDataForStreamPush(InputRequest inputRequest) {
		try {
			ProposalDetails proposalDetails = inputRequest.getRequest().getRequestData().getRequestPayload()
					.getProposalDetails();
			log.info("For All channel sending partial payload sent to stream");
			ProposalDetails requiredProposalDetails = new ProposalDetails();
			InputRequest requestForStream = new InputRequest();
			Request request = new Request();
			RequestData requestData = new RequestData();
			if(!AppConstants.STAGE_ONE.equalsIgnoreCase(proposalDetails.getApplicationDetails().getStage())) {
				requiredProposalDetails.setTransactionId(proposalDetails.getTransactionId());
				requiredProposalDetails.setApplicationDetails(proposalDetails.getApplicationDetails());
				requiredProposalDetails.setChannelDetails(proposalDetails.getChannelDetails());
				requiredProposalDetails.setSourcingDetails(proposalDetails.getSourcingDetails());
				requiredProposalDetails.setAdditionalFlags(proposalDetails.getAdditionalFlags());
				requiredProposalDetails.setUnderwritingServiceDetails(proposalDetails.getUnderwritingServiceDetails());
				requiredProposalDetails.setPosvViaBrmsDetails(proposalDetails.getPosvViaBrmsDetails());
			}
			//FUL2-116428 NPS via Pran
			else{
				requiredProposalDetails.setTransactionId(proposalDetails.getTransactionId());
				requiredProposalDetails.setApplicationDetails(proposalDetails.getApplicationDetails());
				requiredProposalDetails.setBank(proposalDetails.getBank());
				requiredProposalDetails.setCkycDetails(proposalDetails.getCkycDetails());
				requiredProposalDetails.setEmploymentDetails(proposalDetails.getEmploymentDetails());
				requiredProposalDetails.setLifeStyleDetails(proposalDetails.getLifeStyleDetails());
				requiredProposalDetails.setNomineeDetails(proposalDetails.getNomineeDetails());
				requiredProposalDetails.setPartyInformation(proposalDetails.getPartyInformation());
				requiredProposalDetails.setAdditionalFlags(proposalDetails.getAdditionalFlags());
			}
			RequestPayload requestPayload = new RequestPayload(requiredProposalDetails);
			requestData.setRequestPayload(requestPayload);
			request.setRequestData(requestData);
			requestForStream.setRequest(request);
			return requestForStream;
		} catch (Exception ex) {
			log.error("Exception occured in produceToProposalStream from location {}", Utility.getExceptionAsString(ex));
			return inputRequest;
		}
	}

}
