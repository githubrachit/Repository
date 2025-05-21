package com.mli.mpro.location.soa.controller;

import com.mli.mpro.agent.models.MsgInfo;
import com.mli.mpro.location.amlulip.training.model.SoaUlipRequest;
import com.mli.mpro.location.endUser.models.EndUserRequest;
import com.mli.mpro.location.newApplication.model.*;
import com.mli.mpro.location.productRecommendation.models.InputRequest;
import com.mli.mpro.location.soa.service.*;
import com.mli.mpro.location.training.model.Data;
import com.mli.mpro.onboarding.partner.model.ErrorResponse;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.Utility;
import com.mli.mpro.location.clientAllPolicyDetails.model.PolicyDetailsRequest;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.location.ifsc.model.IfscMicrRequest;
import com.mli.mpro.location.labslist.models.LabsListRequest;
import com.mli.mpro.location.soa.exception.SoaCustomException;
import com.mli.mpro.location.soa.service.AmlUlipService;
import com.mli.mpro.location.soa.service.IfscMicrService;
import com.mli.mpro.location.soa.service.LabsListService;
import com.mli.mpro.location.soa.service.NewApplicationService;
import com.mli.mpro.location.soa.service.PanDobVerificationService;
import com.mli.mpro.location.soa.service.TrainingService;
import com.mli.mpro.location.training.model.TrainingRequest;
import com.mli.mpro.location.viewPolicy.models.OutPutResponse;
import com.mli.mpro.location.viewPolicy.models.ViewPolicyInputRequest;
import com.mli.mpro.location.panDOBVerification.model.PanDobRequest;

import static com.mli.mpro.productRestriction.util.AppConstants.FIELD_VALIDATION_ERROR_DURING_BAD_REQUEST;

@RestController
@RequestMapping(path = "/locationservices/soa")
public class SOAController {

	public static final String SERVICE_NOT_RESPONDING = "Service not responding please try again later !!";
	Logger logger = LoggerFactory.getLogger(SOAController.class);
	@Autowired
	private TrainingService trainingService;

	@Autowired
	private NewApplicationService newApplicationService;

	@Autowired
	private PanDobVerificationService panDobVerificationService;

	@Autowired
	private PolicyValidateService policyValidateService;

	@Autowired
	private ProductRecommendationService productRecommendationService;
	
	@Autowired
	private LabsListService labsListService;
	
	@Autowired
	private AmlUlipService amlUlipService;
	
	@Autowired
	private IfscMicrService ifscMicrService;

	@Autowired
	private EndUserService endUserService;
	
	@Autowired
	private ClientPolicyDetailsService clientPolicyDetailsService;
	
	@Autowired
	private ViewPolicyStatusService viewPolicyStatusService;
	
	@Autowired
    private Map<String, String> headerMap;


	@PostMapping("/training")
	public ResponseEntity<Object> getTrainingData(@RequestBody TrainingRequest trainingRequest)
			throws SoaCustomException {
		return trainingService.executeTrainingService(trainingRequest);
	}

	@PostMapping("/isNewApplicationEnabled")
	public ResponseEntity<com.mli.mpro.location.newApplication.model.OutputResponse> getNewApplicationEnabled(
			@RequestBody NewApplicationRequest newApplicationRequest) {
		return newApplicationService.executeNewApplicationService(newApplicationRequest);
	}

	@PostMapping("/panDobVerification")
	public ResponseEntity<Object> getPanDobVerification(
			@RequestBody PanDobRequest panDobRequest) throws UserHandledException {
		return panDobVerificationService.executePanDobVerification(panDobRequest);
	}

	@PostMapping("/getPolicyNumberStatus")
	public com.mli.mpro.proposal.models.OutputResponse getPolicyNumberStatus(@RequestBody com.mli.mpro.proposal.models.InputRequest inputRequest) throws UserHandledException {
		return policyValidateService.validatePolicyNumber(inputRequest);
	}
	
	@PostMapping("/getlabslist")
	public ResponseEntity<Object> getLabsList(@RequestBody LabsListRequest labsListRequest) throws SoaCustomException {
		Utility utility= new Utility();
		String jsonString=Utility.getJsonRequest(labsListRequest);
		Set<com.mli.mpro.onboarding.partner.model.ErrorResponse> errors = utility.validateJson(jsonString, AppConstants.GET_LABLIST);
		com.mli.mpro.common.models.OutputResponse labListoutputResponse = new com.mli.mpro.common.models.OutputResponse();
		com.mli.mpro.common.models.Response labListResponse = new com.mli.mpro.common.models.Response();
		if (!CollectionUtils.isEmpty(errors)) {
			MsgInfo msgInfo = new MsgInfo();
			msgInfo.setErrors(errors);
			msgInfo.setMsgCode(String.valueOf(HttpStatus.BAD_REQUEST));
			labListResponse.setMsgInfo(msgInfo);
			labListoutputResponse.setResponse(labListResponse);
			return new ResponseEntity<>(labListoutputResponse, HttpStatus.BAD_REQUEST);
		}
		return labsListService.executeLabsListService(labsListRequest);
	}
	@PostMapping("/amlUlipStatus")
	public ResponseEntity<Object> getAmlUlipData(@RequestBody TrainingRequest trainingRequest) throws SoaCustomException {
		return amlUlipService.executeAmlUlipService(trainingRequest);
	}

	@PostMapping("/ulip-data-lake")
	public ResponseEntity<Object> getULIPDataLake(@RequestBody SoaUlipRequest ulipRequest) throws SoaCustomException {
		return amlUlipService.executeULIPDataLakeService(ulipRequest);
	}

	@PostMapping("/ifsc-micr")
	public ResponseEntity<Object> getIfscMicrData(@RequestBody IfscMicrRequest ifscMicrRequest) throws SoaCustomException {
		return ifscMicrService.executeIfscMicrDataService(ifscMicrRequest);
	}

	@PostMapping("/getProductRecommendations")
	public ResponseEntity<Object> getProductRecommendationList(@RequestBody InputRequest inputRequest){
		try {
			Utility utility= new Utility();
			String jsonString=Utility.getJsonRequest(inputRequest);
			Set<ErrorResponse> errors = utility.validateJson(jsonString, AppConstants.GET_PRODUCT_RECOMMENDATIONS);
			com.mli.mpro.location.productRecommendation.models.OutputResponse productOutputResponse = new com.mli.mpro.location.productRecommendation.models.OutputResponse();
			com.mli.mpro.location.productRecommendation.models.ResponsePayload productResponse = new com.mli.mpro.location.productRecommendation.models.ResponsePayload();
			com.mli.mpro.location.productRecommendation.models.Result result = new com.mli.mpro.location.productRecommendation.models.Result();
			if (!CollectionUtils.isEmpty(errors)) {
				logger.error(FIELD_VALIDATION_ERROR_DURING_BAD_REQUEST, inputRequest, errors);
				ResponseMsgInfo msgInfo = new ResponseMsgInfo();
				msgInfo.setErrors(errors);
				msgInfo.setMsgCode(400);
				productResponse.setMsginfo(msgInfo);
				result.setResponse(productResponse);
				productOutputResponse.setResult(result);
				return new ResponseEntity<>(productOutputResponse, HttpStatus.BAD_REQUEST);
			}
			return productRecommendationService.getRecommendedProductsList(inputRequest);
		}catch (SoaCustomException soaCustomException){
			return  Utility.buildErrorResponse(soaCustomException.getMsg(),soaCustomException.getMsgDescription(), Integer.parseInt(soaCustomException.getMsgCode()),HttpStatus.OK);
		}catch (Exception ex){
			logger.error("Exception in getProductRecommendations API {}", Utility.getExceptionAsString(ex));
			return Utility.buildErrorResponse(AppConstants.STATUS_FAILURE, SERVICE_NOT_RESPONDING,500,HttpStatus.OK);
		}
	}
	@PostMapping("/enduserapi")
	public ResponseEntity<Object> endUserApi(@RequestBody EndUserRequest endUserRequest){
		try {
			return endUserService.endUserAPI(endUserRequest);
		}catch (Exception ex){
			logger.error("Exception in endUser API {}", Utility.getExceptionAsString(ex));
			return Utility.buildErrorResponse(AppConstants.STATUS_FAILURE, SERVICE_NOT_RESPONDING,500,HttpStatus.OK);
		}
	}
	
	@PostMapping("/getClientAllPolicyDetails")
	public ResponseEntity<Object> getClientAllPolicyDetails(@RequestBody PolicyDetailsRequest policyDetailsRequest) throws UserHandledException {
		String agentId="";
		if (!StringUtils.isBlank(headerMap.get("agentid"))) {
			agentId=headerMap.get("agentid");
		}
		Utility utility= new Utility();
		String jsonString=Utility.getJsonRequest(policyDetailsRequest);
		Set<ErrorResponse> errors = utility.validateJson(jsonString, AppConstants.GET_CLIENT_POLICY_DETAILS);
		com.mli.mpro.location.productRecommendation.models.OutputResponse clientPolicyResponse = new com.mli.mpro.location.productRecommendation.models.OutputResponse();
		com.mli.mpro.location.productRecommendation.models.ResponsePayload clientResponse = new com.mli.mpro.location.productRecommendation.models.ResponsePayload();
		com.mli.mpro.location.productRecommendation.models.Result result = new com.mli.mpro.location.productRecommendation.models.Result();
		if (!CollectionUtils.isEmpty(errors)) {
			ResponseMsgInfo msgInfo = new ResponseMsgInfo();
			msgInfo.setErrors(errors);
			msgInfo.setMsgCode(400);
			clientResponse.setMsginfo(msgInfo);
			result.setResponse(clientResponse);
			clientPolicyResponse.setResult(result);
			return new ResponseEntity<>(clientPolicyResponse, HttpStatus.BAD_REQUEST);
		}
		return clientPolicyDetailsService.executeClientPolicyDetailsService(policyDetailsRequest,agentId);
	}
	
	@PostMapping("/viewPolicy")
	public OutPutResponse getOneViewPolicy(@RequestBody ViewPolicyInputRequest viewPolicyInputRequest) throws UserHandledException {
		return viewPolicyStatusService.executeViewPolicyStatusService(viewPolicyInputRequest);
	}

	@PostMapping("/callUpdateChannel")
	public String callUpdateChannel(@RequestBody Data data) {
		return trainingService.updateChannel(data.getAgentxChannel(), data.getBranchCd(), data.getRole());
	}

}
