package com.mli.mpro.location.services;

import java.util.ArrayList;
import java.util.List;

import com.mli.mpro.auditservice.AuditService;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.location.YblPasa.Payload.YblPasaResponse;
import com.mli.mpro.pasa.models.PasaRequirementDetails;
import com.mli.mpro.productRestriction.repository.ProposalRepository;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.proposal.models.Request;
import com.mli.mpro.proposal.models.RequestData;
import com.mli.mpro.proposal.models.RequestPayload;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.InputRequest;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.pasa.models.PasaValidationDetails;
import com.mli.mpro.pasa.models.PasaValidationRequestPayload;
import com.mli.mpro.pasa.models.PasaValidationResponsePayload;
import com.mongodb.MongoException;

@Service
public class PasaValidationService {

	private static final Logger logger= LoggerFactory.getLogger(PasaValidationService.class);
	
	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	ProposalRepository proposalRepository;
	@Autowired
	MongoOperations mongoOperations;

	@Autowired
	ProposalStreamPushService proposalStreamPushService;
	@Autowired
	AuditService auditService;
	
	//F21-411 Pasa Validation Api
	//FUL2-84428 PASA 2.0
	public PasaValidationResponsePayload validatePasaDetails(InputRequest inputRequest) throws UserHandledException
	{
		String isPasaEligible= AppConstants.NO;
		PasaValidationResponsePayload pasaValidationResponse=new PasaValidationResponsePayload();
	    PasaValidationDetails pasaValidationDetails=null;
		PasaRequirementDetails pasaRequirementDetails = new PasaRequirementDetails();
		try {
			if (Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLEPASATAGS))) {
				String clientID = inputRequest.getRequest().getRequestData().getPasaValidationRequestInfo().getClientId();
				Query query = new Query();
				query.addCriteria(Criteria.where("clientId").is(clientID));
				List<PasaRequirementDetails> pasaRequirementDetailsList = mongoTemplate.find(query, PasaRequirementDetails.class);
				if (!pasaRequirementDetailsList.isEmpty()) {
					pasaRequirementDetails = pasaRequirementDetailsList.get(0);
					isPasaEligible = (null != pasaRequirementDetails) ? AppConstants.YES : AppConstants.NO;
					logger.info("getPASAValidationInfo pasaRequirementDetailsList for clientID {} is {} and isEligible for Pasa {} ", clientID, pasaRequirementDetailsList, isPasaEligible);
					pasaValidationResponse.setPasaRequirementDetails(pasaRequirementDetails);
				} else {
					isPasaEligible = AppConstants.NO;
					pasaRequirementDetails.setClientId(AppConstants.BLANK);
					logger.info("getPASAValidationInfo pasaRequirementDetailsList is Empty for {} is {} and isEligible for Pasa {} ", clientID,
							pasaRequirementDetailsList, isPasaEligible);
				}
			} else {
				isPasaEligible = AppConstants.NO;
				pasaRequirementDetails.setClientId(AppConstants.BLANK);
				logger.info("getPASAValidationInfo Feature Flag failed {} {}", pasaValidationDetails, isPasaEligible);
			}
			pasaValidationResponse.setIsPasaEligible(isPasaEligible);
			//callSaveProposalForPasa(inputRequest.getRequest().getRequestData().getAgentSelfRequestPayload().getTransactionId(), pasaValidationResponse);
			return pasaValidationResponse;
		}
	    catch (MongoException e) {
			List<String> errorMessages = new ArrayList<>();
			logger.error("Error occurred while getting data from data base for request {} with exception: {}",inputRequest,Utility.getExceptionAsString(e));
			errorMessages.add(e.getMessage());
			    throw new UserHandledException(new Response(), errorMessages, HttpStatus.SERVICE_UNAVAILABLE);
		}
	    catch (Exception e) {
			List<String> errorMessages = new ArrayList<>();
			logger.error("Error occurred while validating pasa details for request {} with exception: {}",inputRequest,Utility.getExceptionAsString(e));
			errorMessages.add(e.getMessage());
			    throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@Async
	private void callSaveProposalForPasa(String transactionId, PasaValidationResponsePayload pasaValidationResponsePayload) {
		logger.info("Calling saveProposal for transactionId {}",transactionId);
		com.mli.mpro.proposal.models.InputRequest inputRequest = new com.mli.mpro.proposal.models.InputRequest();
		try {
			/* FUL2-25833 dolphin push changes */
			Query query = new Query();
			Update update = new Update();
			query.addCriteria(Criteria.where(AppConstants.TRANSACTIONID).is(Long.valueOf(transactionId)));
			update.set("pasaDetails.isPasaEligible",pasaValidationResponsePayload.getIsPasaEligible().toUpperCase());
			mongoOperations.findAndModify(query, update, ProposalDetails.class);
			logger.info("called proposal stream from location MS for Pasa for transactionId{}",transactionId);
		}catch (Exception ex) {
			logger.error("Exception while callsaveProposalForPasa {} ", Utility.getExceptionAsString(ex));
		}
	}


}
