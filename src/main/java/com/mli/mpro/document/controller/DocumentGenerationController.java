package com.mli.mpro.document.controller;

import com.mli.mpro.accountaggregator.FinancialInfoResultResponse;
import com.mli.mpro.accountaggregator.documentservice.AccountAggregatorDocumentGenerationService;
import com.amazonaws.services.simplesystemsmanagement.model.Parameter;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.DocumentResponsePayload;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.document.service.DocumentGenerationRetryService;
import com.mli.mpro.document.service.impl.DocumentGenerator;
import com.mli.mpro.document.service.impl.DocumentImplementationDecider;
import com.mli.mpro.neo.models.attachment.GetAttachmentApiRequest;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.neo.models.attachment.GetAttachmentApiResponse;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mli.mpro.proposal.models.InputRequest;
import com.mli.mpro.proposal.models.OutputResponse;
import com.mli.mpro.proposal.models.RequestPayload;
import com.mli.mpro.proposal.models.ResponseData;
import com.mli.mpro.proposal.models.ResponsePayload;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import static com.mli.mpro.utils.MDCHelper.setLogVariable;

@RestController
@RequestMapping(path = "/locationservices/documentgenerationService")
public class DocumentGenerationController {

    private static final Logger logger = LoggerFactory.getLogger(DocumentGenerationController.class);

    @Autowired
    private DocumentGenerator documentGenerator;

    @Autowired
    private DocumentGenerationRetryService documentGenerationRetryService;

    @Autowired
    private DocumentImplementationDecider implementationDecider;

	@Autowired
	private AccountAggregatorDocumentGenerationService documentGenerationService;

	@PostMapping(path = "/initiateGeneration")
    public ResponseEntity<OutputResponse> generateDocument(@RequestBody InputRequest inputRequest) throws UserHandledException {
	com.mli.mpro.proposal.models.OutputResponse outputResponse = new com.mli.mpro.proposal.models.OutputResponse();
	com.mli.mpro.proposal.models.Response response = new com.mli.mpro.proposal.models.Response();
	ResponseData responseData = new ResponseData();
	ResponsePayload responsePayload = new ResponsePayload();
	List<Object> message;
	RequestPayload requestPayload = new RequestPayload();
	try {
	    requestPayload = inputRequest.getRequest().getRequestData().getRequestPayload();
		setLogVariable(inputRequest);
	    logger.info("Input Request Received for document generation for transactionId {} is {}", requestPayload.getProposalDetails().getTransactionId(),
		    inputRequest);
		logger.info("Input Request is received for document generation for transactionId {}.", requestPayload.getProposalDetails().getTransactionId());
	    message = documentGenerator.initiateDocumentGeneration(requestPayload);
	    responsePayload.setMessage(message);
	    responseData.setResponsePayload(responsePayload);
	    response.setResponseData(responseData);
	    outputResponse.setResponse(response);
	} catch (UserHandledException exception) {
	    throw new UserHandledException(new Response(), exception.getErrorMessages(), exception.getHttpstatus());
	} catch (Exception ex) {
		logger.error("Input Request for failed document generation for transactionId {} : {}", requestPayload.getProposalDetails().getTransactionId(),
				inputRequest);
	   	logger.error("Input Request failed for document generation:",ex);
	}
	return new ResponseEntity<>(outputResponse, HttpStatus.OK);
    }

	@PostMapping(path = "/retry/documentGeneration")
    public OutputResponse getProposalFormDocFailedData(@RequestBody com.mli.mpro.document.models.InputRequest inputRequest) throws UserHandledException {
	List<Object> retryTransactions = null;

	try {
		setLogVariable(inputRequest);
		logger.info("Received request to initaite document generation retry");
	    retryTransactions = documentGenerationRetryService.executeRetryForDocumentFailure(inputRequest);
	} catch (UserHandledException ex) {
		logger.error("Document generation retry failed:",ex);
	    throw new UserHandledException(ex.getResponse(), ex.getErrorMessages(), ex.getHttpstatus());
	} catch (Exception ex) {
		logger.error("Document generation retry failed:",ex);
	}
	OutputResponse outputResponse = new OutputResponse();
	com.mli.mpro.proposal.models.Response response = new com.mli.mpro.proposal.models.Response();
	ResponseData responseData = new ResponseData();
	ResponsePayload responsePayload = new ResponsePayload();
	responsePayload.setMessage(retryTransactions);
	responseData.setResponsePayload(responsePayload);
	response.setResponseData(responseData);
	outputResponse.setResponse(response);
	logger.debug("Transactions Picked for retrying {}", outputResponse);
	logger.info("Transactions Picked for retrying successfully.");
	return outputResponse;
    }

	@PostMapping(path = "/generateDocument")
    public OutputResponse generatePDFDocument(@RequestBody InputRequest inputRequest) {
	DocumentResponsePayload documentResponse = null;
	List<Object> documentPayload = new ArrayList<>();
		setLogVariable(inputRequest);
	logger.debug("The request received to generate the document {}", inputRequest);
	logger.info("The request is received to generate the document.");
	try {
		String version = inputRequest.getRequest().getRequestData().getRequestPayload().getVersion();
		logger.info("Version received for document generation is {}", version);
		boolean featureFlagForNewVersion = FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.DOCUMENT_GENERATION_BY_VERSION);
		logger.info("Feature flag for new version is {}", featureFlagForNewVersion);
		if(org.apache.commons.lang3.StringUtils.isEmpty(version) || AppConstants.ZERO.equalsIgnoreCase(version)){
			documentResponse = getDocumentResponsePayloadWithoutVersion(inputRequest);
		} else if (AppConstants.ONE.equalsIgnoreCase(version) && featureFlagForNewVersion) {
			String errorMsg = implementationDecider.documentGenerateValidation(inputRequest);
			if(StringUtils.hasLength(errorMsg)){
				documentResponse = new DocumentResponsePayload();
				documentResponse.setDocumentGenerationStatus(errorMsg);
			} else {
				documentResponse = implementationDecider.documentGenerateForNewVersion(inputRequest.getRequest().getRequestData().getRequestPayload().getProposalDetails());
			}
		} else if(AppConstants.ONE.equalsIgnoreCase(version) && !featureFlagForNewVersion){
			documentResponse = getDocumentResponsePayloadWithoutVersion(inputRequest);
		} else {
			logger.error("Invalid version received for document generation");
			documentResponse = new DocumentResponsePayload();
			documentResponse.setDocumentGenerationStatus(AppConstants.VERSION_INVALID);
		}
	} catch (Exception ex) {
		logger.error("Failed to generate document for the request received : {}", inputRequest);
		logger.error("Failed to generate document:",ex);
	    documentResponse = new DocumentResponsePayload();
	    documentResponse.setDocumentGenerationStatus(AppConstants.FAILED);

	}
	documentPayload.add(documentResponse);
	OutputResponse outputResponse = new OutputResponse();
	com.mli.mpro.proposal.models.Response response = new com.mli.mpro.proposal.models.Response();
	ResponseData responseData = new ResponseData();
	ResponsePayload responsePayload = new ResponsePayload();
	responsePayload.setMessage(documentPayload);
	responseData.setResponsePayload(responsePayload);
	response.setResponseData(responseData);
	response.setMetadata(inputRequest.getRequest().getMetadata());
	outputResponse.setResponse(response);
	return outputResponse;
    }

	private DocumentResponsePayload getDocumentResponsePayloadWithoutVersion(InputRequest inputRequest) {
		logger.info("Initiating document generation for old version.");
		DocumentResponsePayload documentResponse;
		documentResponse = implementationDecider.decideDocumentToGenerate(inputRequest.getRequest().getRequestData().getRequestPayload().getProposalDetails(),
				inputRequest.getRequest().getRequestData().getRequestPayload().getDocumentType());
		return documentResponse;
	}

	@PostMapping(path = "initiateGeneration/payment")
	public ResponseEntity<GetAttachmentApiResponse> generatePaymentSuccessAttachment(@RequestBody GetAttachmentApiRequest apiRequest) {
		setLogVariable(apiRequest);
		return ResponseEntity.ok(new GetAttachmentApiResponse(documentGenerator.getPaymentAttachment(apiRequest)));
	}

	@PostMapping(path = "proposalForm/base64String")
	public ResponseEntity<OutputResponse> getProposalFormDocumentBase64(@RequestBody InputRequest inputRequest) throws UserHandledException {
		com.mli.mpro.proposal.models.OutputResponse outputResponse = new com.mli.mpro.proposal.models.OutputResponse();
		com.mli.mpro.proposal.models.Response response = new com.mli.mpro.proposal.models.Response();
		ResponseData responseData = new ResponseData();
		ResponsePayload responsePayload = new ResponsePayload();
		List<Object> message;
		RequestPayload requestPayload = new RequestPayload();
		try {
			requestPayload = inputRequest.getRequest().getRequestData().getRequestPayload();
			setLogVariable(inputRequest);
			logger.debug("Input Request Received for document generation for transactionId {} is {}", requestPayload.getProposalDetails().getTransactionId(),
					inputRequest);
			logger.info("Input Request is received for document generation for transactionId {}.", requestPayload.getProposalDetails().getTransactionId());
			String documentBase64 = documentGenerator.getDocumentBase64(requestPayload.getProposalDetails(), requestPayload.getRetryCategory());
			message = Arrays.asList("SUCCESS", documentBase64);
			responsePayload.setMessage(message);
			responseData.setResponsePayload(responsePayload);
			response.setResponseData(responseData);
			outputResponse.setResponse(response);
		} catch (UserHandledException exception) {
			throw new UserHandledException(new Response(), exception.getErrorMessages(), exception.getHttpstatus());
		} catch (Exception ex) {
			logger.error("Input Request for failed document generation for transactionId {} : {}", requestPayload.getProposalDetails().getTransactionId(),
					inputRequest);
			logger.error("Input Request failed for document generation:",ex);
		}
		return new ResponseEntity<>(outputResponse, HttpStatus.OK);
	}

	@PostMapping(path="/getDoc")
	public String generatingAccountAggregatorDoc(@RequestBody FinancialInfoResultResponse financialInfoResultResponse) {
		try {
			logger.info("financalInfo doc generation request received for RunId::{} and flowId is ::{} ",
					financialInfoResultResponse.getRunId(), financialInfoResultResponse.getFlowId());
			return documentGenerationService.generateDocument(financialInfoResultResponse);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
