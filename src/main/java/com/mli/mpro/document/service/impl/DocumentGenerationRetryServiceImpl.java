package com.mli.mpro.document.service.impl;


import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.document.models.DocumentPushInfo;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationRetryService;
import com.mli.mpro.document.service.TimePeriodRepository;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.*;
import com.mli.mpro.utils.Utility;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class DocumentGenerationRetryServiceImpl implements DocumentGenerationRetryService {

	public static final String RESPONSE_RECEIVED_FROM_PROPOSAL_SERVICE = "The response received from proposal service {}";
	@Autowired
	private MongoTemplate mongoTemplate;

	@Value("${urlDetails.proposalService}")
	private String url;

	@Value("${urlDetails.neoproposalService}")
	private String neoUrl;

	@Value("${urlDetails.neoRetryDocumentService}")
	private String neoRetryDocumentUrl;

	@Value("${bypass.header.encrypt.value}")
	private String api_client_secret;

	private static String env = System.getenv("env");

	@Autowired
	private DocumentGenerator documentGenerator;

	@Autowired
	private TimePeriodRepository timePeriodRepository;

	private static final Logger logger = LoggerFactory.getLogger(DocumentGenerationRetryService.class);

	@Override
	public List<Object> executeRetryForDocumentFailure(com.mli.mpro.document.models.InputRequest inputRequest) throws UserHandledException {
		List<Object> transactionsForRetry = new ArrayList<>();
		String retryCategory = "";
		try {
			retryCategory = inputRequest.getRequest().getRequestData().getRequestPayload().getRetryCategory();
		}
		catch(Exception ex) {
			logger.error("Error retrieving retryCategory:", ex);
		}
		ResponseEntity<OutputResponse> outputResponse=null;
		InputRequest inputRequest1=null;
		logger.info("Initiating document retry");
		try {
			List<DocumentStatusDetails> documentFailedData = getFailedDocumentData();
			int documentFailedDataSize = documentFailedData.size();
			ProposalDetails proposalDetails = null;

			try {
				for (int i = 0; i < documentFailedDataSize; i++) {
					outputResponse=null;
					inputRequest1=null;
					proposalDetails = getProposalDetails(documentFailedData.get(i));
					if (proposalDetails != null) {
						logger.info("Retrying document for transaction {} {}", documentFailedData.get(i).getTransactionId(),
								documentFailedData.get(i).getDocumentName());
						inputRequest1 = setDataForProposalService(documentFailedData.get(i));
						RequestPayload requestPayload = setDataForDocumentRetry(proposalDetails);
						requestPayload.setRetryCategory(documentFailedData.get(i).getDocumentName());
						inputRequest1.getRequest().getRequestData().setRequestPayload(requestPayload);
						if(Objects.isNull(documentFailedData.get(i).getChannel()))
							documentGenerator.initiateDocumentGeneration(requestPayload);
						else
						{
							retryCategory=documentFailedData.get(i).getDocumentName();
							if(retryCategory.equalsIgnoreCase(AppConstants.CIBIL_DOCUMENT)||(retryCategory.equalsIgnoreCase(AppConstants.CRIF_DOCUMENT)
									||(retryCategory.equalsIgnoreCase(AppConstants.BSE500))))
								documentGenerator.initiateDocumentGeneration(requestPayload);
							else
							{
								outputResponse = new RestTemplate().postForEntity(neoRetryDocumentUrl, inputRequest1, OutputResponse.class);
								HttpStatus statusCode = outputResponse.getStatusCode();
								logger.info("Status Code {}", statusCode);
								logger.debug(RESPONSE_RECEIVED_FROM_PROPOSAL_SERVICE, outputResponse.getBody());
								logger.info("The response is received from proposal service.");
								logger.debug("Error Response {}", outputResponse.getBody().getResponse().getErrorResponse());
								if (statusCode.equals(HttpStatus.OK) && outputResponse.getBody().getResponse().getResponseData() != null) {
									logger.info("Document Retry successfully invoked");
								}
							}
						}
						transactionsForRetry.add("TransactionId=" + documentFailedData.get(i).getTransactionId() + " policyNumber="
								+ documentFailedData.get(i).getPolicyNumber());
					}
				}
			} catch (Exception ex) {
				logger.error("The request send to proposal service while Document retry failed : {}", inputRequest1);
				logger.error("The response received from proposal service while Document retry failed : {}", outputResponse!=null?outputResponse.getBody():"");
				logger.error("Document retry failed:",ex);
			}
		} catch (UserHandledException ex) {
			logger.error("The request send to proposal service while Initiate document retry failed : {}", inputRequest1);
			logger.error("The response received from proposal service while Initiate document retry failed : {}", outputResponse!=null?outputResponse.getBody():"");
			logger.error("Initiate document retry failed:",ex);
			throw new UserHandledException(ex.getResponse(), ex.getErrorMessages(), ex.getHttpstatus());
		} catch (Exception ex) {
			logger.error("The request send to proposal service while failed to initiate document retry : {}", inputRequest1);
			logger.error("The response received from proposal service while failed to initiate document retry : {}", outputResponse!=null?outputResponse.getBody():"");
			logger.error("Failed to initiate document retry:",ex);
		}
		return transactionsForRetry;
	}

	private List<DocumentStatusDetails> getFailedDocumentData() throws UserHandledException {

		List<DocumentStatusDetails> documentFailedData = new ArrayList<>();
		Query query = new Query();
		DocumentPushInfo documentPushInfo = timePeriodRepository.findByReason("DOCUMENT_RETRY");
		int numberOfDays = documentPushInfo.getNumberOfDays();
		Date startDate = new DateTime().minusDays(numberOfDays).toDate();
		Date endDate = new DateTime().minusMinutes(10).toDate();
		int dataLimit = documentPushInfo.getNumberOfRecords();

		logger.info("Getting document Failed data from DB from start date to end date {} {}", startDate, endDate);
		try {
			query.addCriteria((Criteria.where("retryCount").lt(3)).andOperator(Criteria.where("documentGeneratedDate").gt(startDate).lt(endDate)).orOperator(
					Criteria.where(AppConstants.DOCUMENT_STATUS).is(AppConstants.DOCUMENT_GENERATION_FAILED),
					Criteria.where(AppConstants.DOCUMENT_STATUS).is(AppConstants.DATA_MISSING_FAILURE),
					Criteria.where(AppConstants.DOCUMENT_STATUS).is(AppConstants.DOCUMENT_UPLOAD_FAILED)));
			query.limit(dataLimit);
			documentFailedData = mongoTemplate.find(query, DocumentStatusDetails.class);
			logger.info("Size of document Failed data from DB from start date to end date {} {} is {}", startDate, endDate,documentFailedData.size());
		} catch (Exception ex) {
			logger.error("Failed to fetch the document failed data for retry:",ex);
			List<String> errorMessages = new ArrayList<String>();
			errorMessages.add("Failed to intiate Retry");
			throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return documentFailedData;
	}

	private ProposalDetails getProposalDetails(DocumentStatusDetails documentFailedData) {
		InputRequest inputRequest = setDataForProposalService(documentFailedData);
		ProposalDetails proposalDetails = null;
		String proposalURL=url;
		if(Objects.nonNull(documentFailedData.getChannel())&&documentFailedData.getChannel().equalsIgnoreCase(AppConstants.CHANNEL_NEO))
			proposalURL=neoUrl;
		HttpHeaders httpHeaders = Utility.setAPISecretInHeaders(api_client_secret);
		HttpEntity<InputRequest> request = new HttpEntity<>(inputRequest, httpHeaders);
		ResponseEntity<OutputResponse> outputResponse=null;
		try {

			RestTemplate restTemplate=new RestTemplate();
			logger.info("PROPOSAL URL: {} for transactionId {}",proposalURL,documentFailedData.getTransactionId());
			String json = Utility.printJsonRequest(request);
			logger.debug("The request sent to proposal service for transactionId {} {}", documentFailedData.getTransactionId(), json);
			long requestedTime = System.currentTimeMillis();
			outputResponse = restTemplate.postForEntity(proposalURL, request, OutputResponse.class);
			long processedTime = (System.currentTimeMillis() - requestedTime) / 1000;
			logger.info("Proposal Service took {} seconds to process the request for transactionId {}", documentFailedData.getTransactionId(), processedTime);
			HttpStatus statusCode = outputResponse.getStatusCode();
			logger.info("Status Code {}", statusCode);
			logger.debug(RESPONSE_RECEIVED_FROM_PROPOSAL_SERVICE, outputResponse.getBody());
			logger.info("The response is received from proposal service.");
			logger.debug("Error Response {}", outputResponse.getBody().getResponse().getErrorResponse());
			if (statusCode.equals(HttpStatus.OK)&& outputResponse.getBody().getResponse().getResponseData() != null)
			{
				proposalDetails = outputResponse.getBody().getResponse().getResponseData().getResponsePayload().getProposalDetails();
			}
		} catch (Exception ex) {
            logger.error("The request sent to failed proposal service for transactionId {} : {}", documentFailedData.getTransactionId(), Utility.printJsonRequest(request));
			logger.error(RESPONSE_RECEIVED_FROM_PROPOSAL_SERVICE, outputResponse!=null?outputResponse.getBody():"");
			logger.error("Proposal Service Failed to give response:",ex);
		}
		return proposalDetails;
	}

	private InputRequest setDataForProposalService(DocumentStatusDetails documentFailedData) {
		String agentId = documentFailedData.getAgentId();
		RequestPayload requestPayload = new RequestPayload();
		Metadata metadata = new Metadata(env, UUID.randomUUID().toString());
		ProposalDetails proposalDetails = new ProposalDetails();
		ApplicationDetails applicationDetails = proposalDetails.getApplicationDetails();
		if (applicationDetails == null) {
			applicationDetails = new ApplicationDetails();
		}
		SourcingDetails sourcingDetails = proposalDetails.getSourcingDetails();
		if (proposalDetails.getSourcingDetails() == null) {
			sourcingDetails = new SourcingDetails();
		}
		sourcingDetails.setAgentId(agentId);
		applicationDetails.setPolicyNumber(documentFailedData.getPolicyNumber());
		proposalDetails.setTransactionId(documentFailedData.getTransactionId());
		proposalDetails.setApplicationDetails(applicationDetails);
		proposalDetails.setSourcingDetails(sourcingDetails);
		requestPayload.setProposalDetails(proposalDetails);
		if(Objects.nonNull(documentFailedData.getChannel())&&documentFailedData.getChannel().equalsIgnoreCase(AppConstants.CHANNEL_NEO))
		{
			requestPayload.setChannel(documentFailedData.getChannel());
			requestPayload.setRetryCategory(documentFailedData.getDocumentName());
		}
		RequestData requestData = new RequestData(requestPayload);
		Request request = new Request(metadata, requestData);
		InputRequest inputRequest = new InputRequest(request);
		return inputRequest;
	}

	private RequestPayload setDataForDocumentRetry(ProposalDetails proposalDetails) {
		RequestPayload requestPayload = new RequestPayload();
		requestPayload.setProposalDetails(proposalDetails);
		return requestPayload;
	}
}