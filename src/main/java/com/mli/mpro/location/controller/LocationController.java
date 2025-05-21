package com.mli.mpro.location.controller;

import com.amazonaws.services.fms.model.App;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mli.mpro.configuration.models.ApiResponse;
import com.mli.mpro.configuration.models.LinkOutputResponse;
import com.mli.mpro.configuration.models.SellerSaveRequest;
import com.mli.mpro.configuration.models.SupervisorDetails;
import com.mli.mpro.configuration.service.ConfigurationService;
import com.mli.mpro.emailPolicyPack.EmailPolicyPackResponsePayload;
import com.mli.mpro.emailPolicyPack.SoaResponseData;
import com.mli.mpro.location.altfinInquiry.input.AltfinPayload;
import com.mli.mpro.location.altfinInquiry.output.AltfinSoaInputResponse;
import com.mli.mpro.location.models.soaCloudModels.PolicyHostoryModels.PolicyHistoryRequest;
import com.mli.mpro.location.models.soaCloudModels.PolicyHostoryModels.PolicyHistoryResponse;
import com.mli.mpro.location.models.soaCloudModels.agentCommissionSummary.AgentCommissionSummaryRequest;
import com.mli.mpro.location.models.soaCloudModels.agentCommissionSummary.AgentCommissionSummaryResponse;
import com.mli.mpro.location.models.unifiedPayment.models.Data;
import com.mli.mpro.location.models.unifiedPayment.models.UIPaymentRequestResponse;
import com.mli.mpro.location.models.unifiedPayment.models.UnifiedUIRequest;
import com.mli.mpro.location.models.unifiedPayment.models.UnifiedWebHookRequest;
import com.mli.mpro.location.newApplication.model.ResponseMsgInfo;
import com.mli.mpro.location.otp.models.OtpInputRequest;
import com.mli.mpro.location.otp.models.OtpOutputResponse;
import com.mli.mpro.location.otp.service.OTPService;
import com.mli.mpro.otpservice.OtpServiceRequest;
import com.mli.mpro.otpservice.OtpServiceResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.agent.models.SoaApiRequest;
import com.mli.mpro.agent.models.AgentResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mli.mpro.agent.models.MsgInfo;
import com.mli.mpro.agent360.V3.models.Agent360V3Request;
import com.mli.mpro.agentSelf.AgentSelfResponsePayload;
import com.mli.mpro.agentSelfIdentifiedSkip.AgentSelfIdentifiedSkipResponse;
import com.mli.mpro.agentSelfIdentifiedSkip.AgentSelfResponse;
import com.mli.mpro.agentSelfIdentifiedSkip.ResponsePayload;
import com.mli.mpro.bajajCapital.models.BajajSellerDataResponsePayload;
import com.mli.mpro.common.models.genericModels.GenericApiRequest;
import com.mli.mpro.common.models.genericModels.GenericApiResponse;
import com.mli.mpro.common.models.ResumeJourneyRequest;
import com.mli.mpro.common.models.ResumeJourneyResponse;
import com.mli.mpro.ekyc.model.EkycPayload;
import com.mli.mpro.ekyc.model.EkycResponsePayload;
import com.mli.mpro.ekyc.service.EkycService;
import com.mli.mpro.location.UtmConf.Payload.UtmConfiguratorInputRequest;
import com.mli.mpro.location.UtmConf.Payload.UtmConfiguratorResponse;
import com.mli.mpro.location.UtmConf.Service.UtmConfiguratorService;
import com.mli.mpro.location.YblPasa.Payload.YblPasaResponse;
import com.mli.mpro.location.YblPasa.Payload.YblResponsePayload;
import com.mli.mpro.location.YblPasa.Service.YblPasaService;
import com.mli.mpro.location.YblPasa.Service.YblPasaServiceImpl;
import com.mli.mpro.location.amlulip.training.model.SoaAmlRequest;
import com.mli.mpro.location.amlulip.training.model.SoaResponsePayload;
import com.mli.mpro.location.models.mfaOauthAgent360.MFAAgent360InputRequest;
import com.mli.mpro.location.models.mfaOauthAgent360.OauthBasedAgent360Response;
import com.mli.mpro.common.exception.ErrorMessageConfig;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.*;
import com.mli.mpro.common.models.ErrorResponse;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.docsApp.models.DocsappResponse;
import com.mli.mpro.ekyc.model.EkycPayload;
import com.mli.mpro.ekyc.model.EkycResponsePayload;
import com.mli.mpro.ekyc.service.EkycService;
import com.mli.mpro.location.login.model.LoginRequest;
import com.mli.mpro.location.login.service.LoginService;
import com.mli.mpro.location.YblPasa.Payload.YblPasaResponse;
import com.mli.mpro.location.YblPasa.Payload.YblResponsePayload;
import com.mli.mpro.location.YblPasa.Service.YblPasaService;
import com.mli.mpro.location.models.*;
import com.mli.mpro.location.models.mfaOauthAgent360.MFAAgent360InputRequest;
import com.mli.mpro.location.models.mfaOauthAgent360.OauthBasedAgent360Response;
import com.mli.mpro.location.models.soaCloudModels.*;
import com.mli.mpro.location.models.soaCloudModels.policySplittingModels.PolicySplittingRequest;
import com.mli.mpro.location.models.soaCloudModels.policySplittingModels.PolicySplittingResponsePayload;
import com.mli.mpro.location.models.soaCloudModels.spCodeValidationModels.SellerInfoPayload;
import com.mli.mpro.location.models.soaCloudModels.spCodeValidationModels.SellerResponsePayload;
import com.mli.mpro.location.models.zeroqc.ekyc.UIRequestPayload;
import com.mli.mpro.location.models.zeroqc.ekyc.UIResponsePayload;
import com.mli.mpro.location.services.*;
import com.mli.mpro.location.services.impl.LocationServiceImpl;
import com.mli.mpro.location.YblPasa.Service.YblPasaServiceImpl;
import com.mli.mpro.onboarding.partner.model.CpdRequest;
import com.mli.mpro.onboarding.partner.model.CpdResponse;
import com.mli.mpro.otpservice.YBLSendOTPService;
import com.mli.mpro.pasa.models.PasaValidationResponsePayload;
import com.mli.mpro.posseller.models.MessageRequest;
import com.mli.mpro.pratham.models.MessageInputRequest;
import com.mli.mpro.pratham.models.MessageInputResponse;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.PensionPlans;
import com.mli.mpro.routingposv.models.InputMessageRequest;
import com.mli.mpro.samlTraceId.TraceIdRequest;
import com.mli.mpro.samlTraceId.TraceIdResponse;
import com.mli.mpro.utils.Utility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.mli.mpro.location.UtmConf.models.BrmsInputRequest;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.mli.mpro.productRestriction.util.AppConstants.*;
import static com.mli.mpro.utils.MDCHelper.setLogVariable;
import com.mli.mpro.onboarding.pathflex.model.BrmsBrokerAPIRequest;
import com.mli.mpro.onboarding.pathflex.model.BrmsBrokerAPIResponse;

@RestController
@RequestMapping(path = "/locationservices")
public class LocationController {

	private LocationService locationService;
    //F21-411 Pasa Validation Api
    private PasaValidationService pasaValidationService;
    private List<Object> message;
    private static final Logger logger= LoggerFactory.getLogger(LocationServiceImpl.class);
    private DocsAppService docsAppService;
    private PincodeMasterService pincodeMasterService;
    private PrathamService prathamService;
    private PosSellerCommunicationService posSellerCommunicationService;
    private RoutePosvService routePosvService;
	private AgentSelfService agentSelfService;
	//FUL2-116428 NPS via PRAN
	private NpsService npsService;
	//S2-26 saral dashboard
	@Autowired
	private EmailPolicyService emailPolicyService;

	@Autowired
	private YblPasaService yblPasaService;
	@Autowired
	private SoaCloudService soaCloudService;

	@Autowired
	private IIBService iibService;

    @Autowired
    OauthBasedAgent360Service oauthBasedAgent360Service;
	@Autowired
	YblReplacementSaleService yblReplacementSaleService;
	@Autowired
	BajajSellerDataService bajajSellerDataService;
	@Autowired
	EkycService ekycService;
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	ConfigurationService configurationService;

	@Value("${urlDetails.utmCodeValidationURL}")
	private String utmCodeValidationURL;

	@Value("${proposal.bypass.header.encrypt.value}")
	String bypassHeaderEncryptValue;

    @Autowired
    private UtmConfiguratorService utmConfiguratorService;
	@Autowired
	UnifiedPaymentService unifiedPaymentService;

	@Autowired
    private LoginService loginService;

	@Autowired
	private Map<String, String> headerMap;

	@Autowired
	private BrmsBrokerService brmsBrokerService;
	private OTPService otpService;
	//ivc phase 1
	@Autowired
	private PosvRetriggerService posvRetriggerService;
	//FUL2-224962 financial frictionless
	@Autowired
	private AltfinInquiryService altfinInquiryService;


	// Added Constant for String Literals to resolve Sonar Critical Issue.
	private static final String CITY_LIST = "cityList";
	private static final String STATE_LIST = "stateList";
	private static final String COUNTRY_LIST = "countryList";
	private static final String COMPANY_LIST = "companiesList";

    @Autowired
    public LocationController(LocationService locationService, ErrorMessageConfig errorMessageConfig,PasaValidationService pasaValidationService,DocsAppService docsAppService,PincodeMasterService pincodeMasterService,
    		PrathamService prathamService,PosSellerCommunicationService posSellerCommunicationService,RoutePosvService routePosvService,AgentSelfService agentSelfService,NpsService npsService,BrmsBrokerService brmsBrokerService,
	OTPService otpService) {
	this.locationService = locationService;
	this.pasaValidationService=pasaValidationService;
	this.docsAppService=docsAppService;
	this.pincodeMasterService = pincodeMasterService;
	this.prathamService = prathamService;
	this.posSellerCommunicationService=posSellerCommunicationService;
	this.routePosvService = routePosvService;
	this.agentSelfService = agentSelfService;
	this.npsService = npsService;
	this.brmsBrokerService = brmsBrokerService;
	this.otpService = otpService;
    }

	@Autowired
	LocationServiceImpl locationServiceImpl;
    @PostMapping(path = "/getcountries")
    public OutputResponse getCountriesByContinent(@RequestBody InputRequest inputRequest) throws UserHandledException {
    	try {
			Utility utility=new Utility();
			String jsonString = Utility.getJsonRequest(inputRequest);
			logger.info("Input request received for getcountries API {}", jsonString);
			Set<com.mli.mpro.onboarding.partner.model.ErrorResponse> errors= utility.validateJson(jsonString, GET_COUNTRIES);
			if(!CollectionUtils.isEmpty(errors)){
				OutputResponse outputResponse = new OutputResponse();
				Response response=new Response();
				MsgInfo msgInfo=new MsgInfo();
				msgInfo.setMsgCode(BAD_REQUEST_CODE);
				msgInfo.setErrors(errors);
				response.setMsgInfo(msgInfo);
				outputResponse.setResponse(response);
				return outputResponse;
			}
		setLogVariable(inputRequest, false);
		String continent = inputRequest.getRequest().getRequestData().getRequestPayload().getContinent();
	List<String> countryList = locationService.getAllCountriesByContinent(continent);

        return setResponse(inputRequest, countryList, COUNTRY_LIST);
    	} catch (Exception ex) {
			logger.error("Exception occurred while calling getcountries {}",Utility.getExceptionAsString(ex));
			throw new UserHandledException(Arrays.asList(AppConstants.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }


    @PostMapping(path = "/getstates")
    public OutputResponse getStatesByCountry(@RequestBody InputRequest inputRequest) throws UserHandledException {
    	try {
		setLogVariable(inputRequest, false);
			Utility utility = new Utility();
			String jsonString=Utility.getJsonRequest(inputRequest);
			logger.info("Input request received for getstates API {}", jsonString);
			Set<com.mli.mpro.onboarding.partner.model.ErrorResponse> errors = utility.validateJson(jsonString, GET_STATES);
			com.mli.mpro.common.models.OutputResponse statesOutputResponse = new com.mli.mpro.common.models.OutputResponse();
			Response statesResponse = new Response();
			if (!CollectionUtils.isEmpty(errors)) {
				logger.error(FIELD_VALIDATION_ERROR_DURING_BAD_REQUEST, inputRequest, errors);
				MsgInfo msgInfo = new MsgInfo();
				msgInfo.setErrors(errors);
				msgInfo.setMsgCode(String.valueOf(HttpStatus.BAD_REQUEST));
				statesResponse.setMsgInfo(msgInfo);
				statesOutputResponse.setResponse(statesResponse);
				return statesOutputResponse;
			}
        String country = inputRequest.getRequest().getRequestData().getRequestPayload().getCountry();
        List<String> stateList = locationService.getAllStatesByCountry(country);

        return setResponse(inputRequest, stateList, STATE_LIST);
    	} catch (Exception ex) {
			logger.error("Exception occurred while calling getstates {}",Utility.getExceptionAsString(ex));
			throw new UserHandledException(Arrays.asList(AppConstants.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

	@PostMapping(path = "/agentcheck")
	public OutputResponse validateAgent(@RequestBody InputRequest inputRequest, BindingResult result) throws UserHandledException{
		Response response = new Response();
		OutputResponse outputResponse = new OutputResponse();
		try {
		setLogVariable(inputRequest,false);
			Utility utility= new Utility();
			String jsonString=Utility.getJsonRequest(inputRequest);
			Set<com.mli.mpro.onboarding.partner.model.ErrorResponse> errors = utility.validateJson(jsonString, AGENT_CHECK);

			if(inputRequest.getRequest() != null && inputRequest.getRequest().getRequestData() != null && inputRequest.getRequest().getRequestData().getRequestPayload() != null)
				utility.validateTransactionId(inputRequest.getRequest().getRequestData().getRequestPayload().getTransactionId(),errors, "request.requestData.agentSelfRequestPayload.transactionId");

			com.mli.mpro.common.models.OutputResponse brmsCallOutputResponse = new com.mli.mpro.common.models.OutputResponse();
			Response brmsResponse = new Response();
			if (!CollectionUtils.isEmpty(errors)) {
				logger.error(FIELD_VALIDATION_ERROR_DURING_BAD_REQUEST, inputRequest, errors);
				MsgInfo msgInfo = new MsgInfo();
				msgInfo.setErrors(errors);
				msgInfo.setMsgCode(String.valueOf(HttpStatus.BAD_REQUEST));
				brmsResponse.setMsgInfo(msgInfo);
				brmsCallOutputResponse.setResponse(brmsResponse);
				return brmsCallOutputResponse;
			}

			if(result.hasErrors())
		{
			ErrorResponse errorResponse=new ErrorResponse();
			List<String> errorMessages=new ArrayList<>();
			String errorMessage=result.getAllErrors().stream().map(res -> ((FieldError)res).getDefaultMessage()).collect(Collectors.joining(" & "));
			errorMessages.add(errorMessage);
			logger.info(FIELD_VALIDATION_ERROR,errorMessage);
			errorResponse.setErrorMessages(errorMessages);
			errorResponse.setErrorCode(422);
			response.setErrorResponse(errorResponse);
			outputResponse.setResponse(response);
			return outputResponse;
		}
		AgentSelfResponsePayload agentSelfResponsePayload = null;
			agentSelfResponsePayload = agentSelfService.validateAgentSelfCheck(inputRequest);
		response.setMetadata(inputRequest.getRequest().getMetadata());
		ResponseData responseData = new ResponseData();
		responseData.setAgentSelfResponsePayload(agentSelfResponsePayload);
		response.setResponseData(responseData);
		outputResponse.setResponse(response);
		return outputResponse;
		} catch (Exception ex) {
			List<String> errorMessages = new ArrayList<>();
			logger.error("Input request received for failed Agent check validation : {}", inputRequest);
			logger.error(EXCEPTION_OCCURRED_WHILE_PROCESSING_REQUEST, Utility.getExceptionAsString(ex));
			errorMessages.add(ex.getMessage());
			throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

    @PostMapping(path = "/getcities")
    public OutputResponse getCitiesByState(@RequestBody InputRequest inputRequest) throws UserHandledException {
    	try {
			String jsonString = Utility.getJsonRequest(inputRequest);
			logger.info("Input request received for getcities API {}", jsonString);
		setLogVariable(inputRequest, false);
        String state = inputRequest.getRequest().getRequestData().getRequestPayload().getState();
        if (StringUtils.isEmpty(state)) {
            List<String> uniqueCitiesList = locationService.getAllUniqueCities();
            return setResponse(inputRequest,uniqueCitiesList, CITY_LIST);
        }
        List<String> cityList = locationService.getAllCitiesByStates(state);
        return setResponse(inputRequest, cityList, CITY_LIST);
    	} catch (Exception ex) {
			logger.error("Exception occurred while calling getCitiesByState {}",Utility.getExceptionAsString(ex));
			throw new UserHandledException(Arrays.asList(AppConstants.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

	@PostMapping(path = "/getphonecode")
	public OutputResponse getPhoneCodeList(@RequestBody InputRequest inputRequest) throws UserHandledException {
		setLogVariable(inputRequest, false);
		try {
			Utility utility=new Utility();
			String jsonString = Utility.getJsonRequest(inputRequest);
			logger.info("Request received for getPhoneCode list {}", jsonString);
			Set<com.mli.mpro.onboarding.partner.model.ErrorResponse> errors= utility.validateJson(jsonString, GET_COUNTRIES);
			if(!CollectionUtils.isEmpty(errors)){
				logger.error(FIELD_VALIDATION_ERROR_DURING_BAD_REQUEST, inputRequest, errors);
				OutputResponse outputResponse = new OutputResponse();
				Response response=new Response();
				MsgInfo msgInfo=new MsgInfo();
				msgInfo.setMsgCode(BAD_REQUEST_CODE);
				msgInfo.setErrors(errors);
				response.setMsgInfo(msgInfo);
				outputResponse.setResponse(response);
				return outputResponse;
			}
			String continent = inputRequest.getRequest().getRequestData().getRequestPayload().getContinent();
			List<Object> phoneCodeList = locationService.getPhoneCodeForCountry(continent);

			OutputResponse outputResponse = new OutputResponse();
			Response response = new Response();
			ResponseData responseData = new ResponseData();
			LocationResponsePayload locationResponsePayload = new LocationResponsePayload();

			response.setMetadata(inputRequest.getRequest().getMetadata());
			locationResponsePayload.setPhCodeList(phoneCodeList);
			responseData.setLocationResponsePayload(locationResponsePayload);
			response.setResponseData(responseData);
			outputResponse.setResponse(response);
			return outputResponse;
		} catch (Exception ex) {
			logger.error("Input request received failed getphonecode : {}", inputRequest);
			logger.error(EXCEPTION_OCCURRED_WHILE_PROCESSING_REQUEST, Utility.getExceptionAsString(ex));
			throw new UserHandledException(Arrays.asList(AppConstants.INTERNAL_SERVER_ERROR, ex.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

    @PostMapping(path = "/getCompanyList")
    public OutputResponse getCompanyList(@RequestBody InputRequest inputRequest) throws UserHandledException {
		setLogVariable(inputRequest, false);
	List<String> companiesList = new ArrayList<>();
	try {
		Utility utility= new Utility();
		String jsonString=Utility.getJsonRequest(inputRequest);
		logger.info("Input request to fetch the company list {}", jsonString);
	    String type = inputRequest.getRequest().getRequestData().getRequestPayload().getType();
		Set<com.mli.mpro.onboarding.partner.model.ErrorResponse> errors = utility.validateJson(jsonString, GET_COMPANY_LIST);
		com.mli.mpro.common.models.OutputResponse companyOutputResponse = new com.mli.mpro.common.models.OutputResponse();
		Response companyResponse = new Response();
		if (!CollectionUtils.isEmpty(errors)) {
			logger.error(FIELD_VALIDATION_ERROR_DURING_BAD_REQUEST, inputRequest, errors);
			MsgInfo msgInfo = new MsgInfo();
			msgInfo.setErrors(errors);
			msgInfo.setMsgCode(String.valueOf(HttpStatus.BAD_REQUEST));
			companyResponse.setMsgInfo(msgInfo);
			companyOutputResponse.setResponse(companyResponse);
			return companyOutputResponse;
		}
		companiesList = locationService.getCompanyNames(type);
	} catch (Exception exception) {
	    logger.error("Error occurred for request object: {}",Utility.getExceptionAsString(exception));
	    throw new UserHandledException(Arrays.asList(AppConstants.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
	}
        return setResponse(inputRequest, companiesList, COMPANY_LIST);
    }

    // Create New Request for JIRA Id - 4943 validation of OTP State and City
	@PostMapping(path = "/getotpstates")
    public OutputResponse getOtpStatesByCountry(@RequestBody InputRequest inputRequest) throws UserHandledException {
		setLogVariable(inputRequest, false);
        String country = inputRequest.getRequest().getRequestData().getRequestPayload().getCountry();
        List<String> stateList = locationService.getAllOtpStatesByCountry(country);
        return setResponse(inputRequest, stateList, STATE_LIST);
    }

    @PostMapping(path = "/getotpcities")
    public OutputResponse getOtpCitiesByState(@RequestBody InputRequest inputRequest) {
		setLogVariable(inputRequest, false);
        String state = inputRequest.getRequest().getRequestData().getRequestPayload().getState();
        List<String> cityList = locationService.getAllOtpCitiesByStates(state);
        return setResponse(inputRequest, cityList, CITY_LIST);
    } 


    // F21-262 Api To Get Branch Code
	@PostMapping(path = "/getBranchCode")
	public OutputResponse getYblBranchCode() throws UserHandledException {
		try {
		Pageable pageable = PageRequest.of(0, AppConstants.PAGELIMIT);
		List<BranchCodeService> branchCodeList = locationService.getBranchCode(pageable);
		logger.info("yblBranchCode List output for the request is successfully generated");
		OutputResponse outputResponse = new OutputResponse();
		Response response = new Response();
		ResponseData responseData = new ResponseData();
		Payload payload = new Payload();
		payload.setSourcingBranchCodes(branchCodeList);
		responseData.setPayload(payload);
		response.setResponseData(responseData);
		outputResponse.setResponse(response);
		return outputResponse;
		} catch (Exception ex) {
			logger.error("Exception occurred while calling getYblBranchCode {}",Utility.getExceptionAsString(ex));
			throw new UserHandledException(Arrays.asList(AppConstants.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "/calldocsAppService")
	public OutputResponse calldocsAppService(@RequestBody InputRequest inputRequest) throws UserHandledException
	{
		try {
			logger.info("Input request received for calldocsAppService API {}", inputRequest);
		setLogVariable(inputRequest, true);
		DocsappResponse docsappResponse = new DocsappResponse();
		OutputResponse outputResponse = new OutputResponse();
		Response response = new Response();
		ResponseData responseData = new ResponseData();
		docsappResponse = docsAppService.callDocsAppService(inputRequest);
		responseData.setDocsappResponse(docsappResponse);
		response.setResponseData(responseData);
		outputResponse.setResponse(response);
		return outputResponse;
		} catch (Exception ex) {
			logger.error("Exception occurred while calling calldocsAppService {}",Utility.getExceptionAsString(ex));
			throw new UserHandledException(Arrays.asList(AppConstants.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//F21-411 Pasa Validation Api
	@PostMapping(path = "/getPasaValidationInfo")
	public OutputResponse getpasaValidationInfo(@Valid @RequestBody InputRequest inputRequest, BindingResult result) throws UserHandledException {
		Response response = new Response();
		OutputResponse outputResponse = new OutputResponse();
		setLogVariable(inputRequest,false);
		logger.info("Input request received for pasa validation.");
		logger.debug("Input request received for pasa validation {}",inputRequest);
		if(result.hasErrors())
		{
			ErrorResponse errorResponse=new ErrorResponse();
			List<String> errorMessages=new ArrayList<>();
			String errorMessage=result.getAllErrors().stream().map(res -> ((FieldError)res).getDefaultMessage()).collect(Collectors.joining(" & "));
			errorMessages.add(errorMessage);
			logger.info(FIELD_VALIDATION_ERROR,errorMessage);
			errorResponse.setErrorMessages(errorMessages);
			errorResponse.setErrorCode(400);
			response.setErrorResponse(errorResponse);
			outputResponse.setResponse(response);
			return outputResponse;
		}
		PasaValidationResponsePayload pasaValidationResponsePayload=null;
		try {
			pasaValidationResponsePayload = pasaValidationService.validatePasaDetails(inputRequest);
		} catch (UserHandledException e) {
			List<String> errorMessages = new ArrayList<>();
			logger.error("Input request received for failed pasa validation : {}",inputRequest);
			logger.error(EXCEPTION_OCCURED_WHILE_PROCESSING_REQUEST,Utility.getExceptionAsString(e));
			errorMessages.add(e.getMessage());
			throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.setMetadata(inputRequest.getRequest().getMetadata());
		ResponseData responseData = new ResponseData();
		responseData.setPasaValidationResponse(pasaValidationResponsePayload);
		response.setResponseData(responseData);
		outputResponse.setResponse(response);
		return outputResponse;
	}

    @GetMapping(path = "/health")
    public Object healthCheck() {
	String healthMessage = "Server is running";
	message = new ArrayList<>();
	message.add(healthMessage);
	return message;
    }

  	/**
  	 * Utility method for setting data in response
  	 * @param inputRequest
  	 * @param dataList
  	 * @param listType
  	 * @return
  	 */
	private OutputResponse setResponse(InputRequest inputRequest, List<String> dataList, String listType) {
		OutputResponse outputResponse = new OutputResponse();
		Response response = new Response();
		ResponseData responseData = new ResponseData();
		LocationResponsePayload locationResponsePayload = new LocationResponsePayload();

		response.setMetadata(inputRequest.getRequest().getMetadata());
		if (StringUtils.equals(listType, COUNTRY_LIST)) {
			locationResponsePayload.setCountries(dataList);
		} else if (StringUtils.equals(listType, CITY_LIST)) {
			locationResponsePayload.setCities(dataList);
		} else if (StringUtils.equals(listType, COMPANY_LIST)) {
			locationResponsePayload.setCompanies(dataList);
		} else if (StringUtils.equals(listType, STATE_LIST)) {
			locationResponsePayload.setStates(dataList);
		}

		responseData.setLocationResponsePayload(locationResponsePayload);
		response.setResponseData(responseData);
		outputResponse.setResponse(response);
		return outputResponse;
	}

	/**
	 * This API fetch data from SOA API and get Saved in DB
	 * @param inputRequest
	 * @param result
	 * @return
	 * @throws UserHandledException
	 */
	@PostMapping(path = "/getNpsCustData")
	public OutputResponse fetchNpsCustData(@RequestBody InputRequest inputRequest, BindingResult result) throws UserHandledException, InterruptedException {
		Response response = new Response();
		OutputResponse outputResponse = new OutputResponse();
		setLogVariable(inputRequest,false);
		if(result.hasErrors())
		{
			ErrorResponse errorResponse=new ErrorResponse();
			List<String> errorMessages=new ArrayList<>();
			String errorMessage=result.getAllErrors().stream().map(res -> ((FieldError)res).getDefaultMessage()).collect(Collectors.joining(" & "));
			errorMessages.add(errorMessage);
			logger.info(FIELD_VALIDATION_ERROR,errorMessage);
			errorResponse.setErrorMessages(errorMessages);
			errorResponse.setErrorCode(422);
			response.setErrorResponse(errorResponse);
			outputResponse.setResponse(response);
			return outputResponse;
		}
		NpsResponsePayload npsResponsePayload = null;
		try{
			npsResponsePayload = npsService.fetchNpsCustData(inputRequest);
			Thread.sleep(800);
		}
		catch(InterruptedException ie){
			Thread.currentThread().interrupt();
			logger.error("Exception Occured in thread: {}",Utility.getExceptionAsString(ie));
		}
		catch(Exception e){
			List<String> errorMessages = new ArrayList<>();
			logger.error(EXCEPTION_OCCURED_WHILE_PROCESSING_REQUEST,Utility.getExceptionAsString(e));
			errorMessages.add(e.getMessage());
			throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.setMetadata(inputRequest.getRequest().getMetadata());
		ResponseData responseData = new ResponseData();
		responseData.setNpsResponsePayload(npsResponsePayload);
		if(npsResponsePayload.getPranDuplicateFlag()){
			MsgInfo msgInfo= new MsgInfo();
			msgInfo.setMsgCode("410");
			msgInfo.setMsg("PRAN_DUPLICATE_FOUND");
			msgInfo.setMsgDescription(AppConstants.PRAN_DESC);
			response.setMsgInfo(msgInfo);
		}
		response.setResponseData(responseData);
		outputResponse.setResponse(response);
		String responseReceivedTime=setTimeStamp();
		logger.info("Response for NPS received at {} ",responseReceivedTime);
		return outputResponse;
	}
	  //FUL2-13438 Method to get states and cities based on pinCode
    @PostMapping(path = "/getstateandcityByPincode")
	public OutputResponse getStatesAndCitysByPincode(@RequestBody InputRequest inputRequest) throws UserHandledException {
    	try {
			logger.info("Input request received for getstateandcityByPincode API {}", inputRequest);
		setLogVariable(inputRequest, false);
		OutputResponse outputResponse = new OutputResponse();
		Response response = new Response();
		ResponseData responseData = new ResponseData();
		LocationResponsePayload locationResponsePayload = new LocationResponsePayload();
		String pincode = inputRequest.getRequest().getRequestData().getRequestPayload().getPincode();
		HashMap<String, String> stateCityByPincode = null;
		Utility utility= new Utility();
			String jsonString=Utility.getJsonRequest(inputRequest);
			Set<com.mli.mpro.onboarding.partner.model.ErrorResponse> errors = utility.validateJson(jsonString, GET_STATE_CITY);
			com.mli.mpro.common.models.OutputResponse pincodeOutputResponse = new com.mli.mpro.common.models.OutputResponse();
			Response pincodeResponse = new Response();
			if (!CollectionUtils.isEmpty(errors)) {
				logger.error(FIELD_VALIDATION_ERROR_DURING_BAD_REQUEST, inputRequest, errors);
				MsgInfo msgInfo = new MsgInfo();
				msgInfo.setErrors(errors);
				msgInfo.setMsgCode(String.valueOf(HttpStatus.BAD_REQUEST));
				pincodeResponse.setMsgInfo(msgInfo);
				pincodeOutputResponse.setResponse(pincodeResponse);
				return pincodeOutputResponse;
			}
		if (!StringUtils.isEmpty(pincode)) {
			stateCityByPincode = pincodeMasterService.getStatesAndCitiesByPincode(pincode);
		}

		response.setMetadata(inputRequest.getRequest().getMetadata());
		locationResponsePayload.setStateCityByPincode(stateCityByPincode);
		responseData.setLocationResponsePayload(locationResponsePayload);
		response.setResponseData(responseData);
		outputResponse.setResponse(response);
		return outputResponse;
    	} catch (Exception ex) {
			logger.error("Exception occurred while calling getStatesAndCitysByPincode {}",Utility.getExceptionAsString(ex));
			throw new UserHandledException(Arrays.asList(AppConstants.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
    
    @PostMapping(value = "/pushToPratham")
    public MessageInputResponse pushdataToPratham(@RequestBody MessageInputRequest inputRequest) throws UserHandledException {
    	MessageInputResponse msgResponse = new MessageInputResponse();
    	String status = null;
       long transactionId = inputRequest.getTransactionId();
        logger.info("request received for pratham from queue for transactionId {}",transactionId);
        try {
			 status = prathamService.executePrathamService(inputRequest);
        } catch (Exception ex) {
        	logger.error("Exception stack trace for push proposal to pratham {}", Utility.getExceptionAsString(ex));
        }
        msgResponse.setTransactionId(transactionId);
        msgResponse.setStatus(status);
       
        return msgResponse;

    }
    
    @PostMapping(value = "/pushPosdataToSqs")
	public void pushPosdataToSqs() {
		logger.info("request for pushing data to sqs received!");
		try {
			posSellerCommunicationService.findByUnderwritingStatusAndStageAndPosvJourneyStatus();
		} catch (Exception ex) {
			logger.error("Exception stack trace for push proposal to pos {}", Utility.getExceptionAsString(ex));
		}
	}
	
	@PostMapping("/sendEmailAnsSmsToPosSellers")
	public void sendEmailAnsSmsToPosSellers(@RequestBody List<MessageRequest> messageRequestsList) {

		try {
			messageRequestsList.forEach(messageRequest -> {
				logger.info("request received for Posseller to send email and sms from queue for transactionId {}",
						messageRequest.getTransactionId());
				posSellerCommunicationService.sendEmail(messageRequest);
				posSellerCommunicationService.sendSms(messageRequest);
			});

		} catch (Exception ex) {
			logger.error("Exception stack trace for sending email and sms to possellers is {}",
					Utility.getExceptionAsString(ex));
		}

	}
	
	@PostMapping(value = "/SendEmailAfterPosvToPos")
	public void SendEmailAfterPosvTriggerToPos() {
		logger.info("schduler trigger for send email to POS");
		try {
			posSellerCommunicationService.getPosvTriggerDetailsForEmail();
		} catch (Exception ex) {
			logger.error("Exception stack trace getPosv to pos {}", Utility.getExceptionAsString(ex));
		}
	}

	@PostMapping(path = "/transformLocation")
	public LocationTransformationOutputResponse transformLocation(@RequestBody LocationTransformationInputRequest inputRequest) {
		return setLocationTransformedResponse(inputRequest);
	}

	/**
	 * Utility method for setting data in response
	 * @param inputRequest
	 * @return
	 */
	private LocationTransformationOutputResponse setLocationTransformedResponse(LocationTransformationInputRequest inputRequest) {
		LocationTransformationOutputResponse outputResponse = new LocationTransformationOutputResponse();
		LocationTranformationResponse response = new LocationTranformationResponse();
		LocationTransformationResponseData responseData = new LocationTransformationResponseData();
		LocationTransformationResponsePayload responsePayload = new LocationTransformationResponsePayload();
		response.setMetadata(inputRequest.getRequest().getMetadata());
		locationService.transformLocation(inputRequest.getRequest().getRequestData().getRequestPayload().getAddressDetails());
		responsePayload.setAddressDetails(inputRequest.getRequest().getRequestData().getRequestPayload().getAddressDetails());
		responseData.setResponsePayload(responsePayload);
		response.setResponseData(responseData);
		outputResponse.setResponse(response);
		return outputResponse;
	}
	//FUL2-18647
		@PostMapping(path = "/getYBLAccountType")
	    public com.mli.mpro.yblAccount.models.OutputResponse getGstAccount(@RequestBody com.mli.mpro.yblAccount.models.InputRequest inputRequest) throws UserHandledException {
			com.mli.mpro.yblAccount.models.OutputResponse outputResposne = new com.mli.mpro.yblAccount.models.OutputResponse();
			try {
			String type = inputRequest.getRequest().getRequestdata().getPayload().getType();
	        outputResposne = locationService.getGstYblAccount(type);
			} catch (Exception ex) {
				logger.error("Exception stack trace getGst YBL Account {}", Utility.getExceptionAsString(ex));
			}
			outputResposne.setMetadata(inputRequest.getRequest().getMetadata());
	        return outputResposne;
	    }

		
	@PostMapping(value = "/routeToPosv")
	 public MessageInputResponse routeToPosv(@RequestBody InputMessageRequest inputRequest) throws UserHandledException {
    	MessageInputResponse msgResponse = new MessageInputResponse();
    	String status = null;
       long transactionId = inputRequest.getTransactionId();
        logger.info("request received for routing to posv from queue for transactionId {}",transactionId);
        try {
             
            if(Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLE_POSV_VIA_BRMS))){
                status = routePosvService.callPiPosvApi(inputRequest);
            }
            else {
			 status = routePosvService.executeRoutingProcess(inputRequest);
            }   
			 logger.info("status from routing service for transactionId {}",transactionId);
        } catch (Exception ex) {
        	logger.error("Exception stack trace to call save proposal for posv {}", Utility.getExceptionAsString(ex));
        }
        try {
            if(Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLE_POSV_VIA_BRMS)
                    && Objects.isNull(status))) {
                logger.info("Posv via BRMS has not given response for transactionID {}, status {}", transactionId, status);
                status = routePosvService.executeRoutingProcess(inputRequest);
            }
        } catch (Exception ex) {
            logger.error("Exception stack trace to call save proposal for posv {}", Utility.getExceptionAsString(ex));
        }
        msgResponse.setTransactionId(transactionId);
        msgResponse.setStatus(status);

        return msgResponse;
    }
	@PostMapping(value = "/mfa-oauth-agent360")
	public OauthBasedAgent360Response agent360Records(@RequestBody MFAAgent360InputRequest agentRequest,
			@RequestHeader("token-required") String tokenRequired)
			throws JsonProcessingException, GeneralSecurityException {
		logger.info("Api request for Oauth token agent 360 triggered.");
		Boolean hasOuathEnabled = FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLEOAUTHBROKERAGENT360_V3);
		try {
			if (hasOuathEnabled) {
				return oauthBasedAgent360Service.oauthBasedAgent360CallV3(agentRequest.getRequest().getPayload(),
						oauthBasedAgent360Service.getNewOauthToken(), tokenRequired);
			} else {
				return oauthBasedAgent360Service.oauthBasedAgent360Call(agentRequest.getRequest().getPayload(),
						oauthBasedAgent360Service.getOauthToken(), tokenRequired);
			}
		} catch (UserHandledException ex) {
			logger.error(EXCEPTION_DURING_OAUTH_TOKEN_AGENT_API, ex);
			return oauthBasedAgent360Service.setOauthBasedAgentResponse(null, ex);
		}
	}

	@PostMapping(value = "/agent360/v3")
	public AgentResponse agent360Records(@RequestBody Agent360V3Request agent360V3Request)
			throws JsonProcessingException, GeneralSecurityException {
		logger.info("Api request for Oauth token agent 360 triggered.");
		try {
			return oauthBasedAgent360Service.agent360V3(agent360V3Request.getRequest().getPayload(),
					oauthBasedAgent360Service.getNewOauthToken());
		} catch (UserHandledException ex) {
			logger.error(EXCEPTION_DURING_OAUTH_TOKEN_AGENT_API, ex);
			return null;
		}
	}


	/**
	 * @return PensionPlans
	 * @implNote FUL2-74120 Get all pension plans for restricting PTF payment for GLIP
	 */
	@PostMapping(path = "/getPensionPlans")
	public OutputResponse getPensionPlans() {
		logger.info("Api request getPensionPlans triggered.");
		
		List<PensionPlans> pensionPlanList = locationService.getPensionPlans();
		
		OutputResponse outputResponse = new OutputResponse();
		Response response = new Response();
		ResponseData responseData = new ResponseData();
		PensionPlansPayload payload = new PensionPlansPayload();
		payload.setPensionPlansList(pensionPlanList);
		
		responseData.setPensionPlansPayload(payload);
		response.setResponseData(responseData);
		outputResponse.setResponse(response);

        return outputResponse;
	}
	
	//ful2-75750
		@PostMapping(path = "/thanos-direct-debit-bulk-generate")
		public String generateDirectDebitBulk() {
			logger.info("started thanos-direct-debit-bulk-generate");
			locationService.generateDirectDebitBulk();
			return "SUCCESS";
			
		}

	@PostMapping(value = "/callIIB")
	public ResponseEntity<IIBResponsePayload> callIIB(@Valid @RequestBody InputRequest inputRequest, BindingResult result) throws UserHandledException {

		if (result.hasErrors()) {
			List<String> errorMessages = result.getAllErrors()
					.stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage)
					.collect(Collectors.toList());

			String errorMessage = String.join(", ", errorMessages);
			logger.error(FIELD_VALIDATION_ERROR_DURING_BAD_REQUEST, inputRequest, errorMessages);

			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new IIBResponsePayload(errorMessage));
		}


		return ResponseEntity.ok(iibService.executeIIBSerive(inputRequest));
	}

		// FUL2-82109
		@PostMapping("/ulip-funds")
		public Object ulipFunds(@RequestBody InputRequest inputRequest) throws UserHandledException {

			try {
				String productId = inputRequest.getRequest().getRequestData().getRequestPayload().getProposalDetails()
						.getProductDetails().get(0).getProductInfo().getProductId();
				long transactionId = inputRequest.getRequest().getRequestData().getRequestPayload().getProposalDetails()
						.getTransactionId();
			logger.info("request recieved to fetch the ulipFunds for transactionId {} inputRequest {}", transactionId,
					inputRequest);
				if (productId != null) {
					return locationService.getUlipFunds(productId);
				}else{
					throw new Exception("Product Id is missing in request");
				}

			} catch (Exception e) {
				logger.error("Exception during Getting UlipFunds {}", Utility.getExceptionAsString(e));
				throw new UserHandledException(Arrays.asList(AppConstants.INTERNAL_SERVER_ERROR,e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

	//FUL2-200037
	@PostMapping("/getDisableProductList")
	public Object getDisableProductList(@RequestBody com.mli.mpro.location.models.disableProductListModels.Request request) {
		com.mli.mpro.location.models.disableProductListModels.Response response = new com.mli.mpro.location.models.disableProductListModels.Response();
		List<String> productNames = new ArrayList<>();
		try{
			logger.info("Request for getDisableProductList: {}", request);
			productNames = locationService.getDisableProducts(request.getChannel(), request.getGoCode(), request.isPosSeller(), request.isCATAxis(), request.isPhysicalJourney());
		}catch (Exception e) {
			logger.error("Exception occured during disable product list fetch {}",Utility.getExceptionAsString(e));
		}
		response.setProductNames(productNames);
		logger.info("Response for getDisableProductList: {}", response);
		return response;
	}

		// FUL2-103604
		@PostMapping("/existing-policy-status")
		public Object existingPolicyStatus(@RequestBody InputRequest inputRequest) throws UserHandledException {

			String productId = inputRequest.getRequest().getRequestData().getRequestPayload().getProposalDetails()
					.getProductDetails().get(0).getProductInfo().getProductId();
			long transactionId = inputRequest.getRequest().getRequestData().getRequestPayload().getProposalDetails()
					.getTransactionId();
			try {
				logger.info("request recieved to fetch the existinPolicyStatus for transactionId {} inputRequest {}",
						transactionId, inputRequest);
				if (productId != null) {
					return locationService.getExistingPolicy(productId);
				}
			} catch (Exception e) {
				logger.error("Exception during Getting existinPolicyStatus for transactionId {} {}", transactionId, e);
				logger.error(EXCEPTION_OCCURRED_WHILE_PROCESSING_REQUEST, Utility.getExceptionAsString(e));
				throw new UserHandledException(Arrays.asList(AppConstants.INTERNAL_SERVER_ERROR,e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ExistingPolicyStatus();
		}
	@PutMapping(path = "/updateAgentEmpFraudflagSkip")
	public AgentSelfResponse updateAgentCheck(@RequestBody com.mli.mpro.agentSelfIdentifiedSkip.RequestPayload request)throws Exception {
		AgentSelfResponse response = new AgentSelfResponse();
		String transactionId = "";
		String policyNumber = "";
		try {
			logger.info("Request received to get agent Self check with transactionID {} and policy Number {}", transactionId, policyNumber);
			response = agentSelfService.updateAgentEmpFraudflagSkip(request);

				MsgInfo msgInfo = new MsgInfo();
			msgInfo.setMsg(response != null ? "Success" : "No Content");
			msgInfo.setMsgCode(response != null ? "200" : "204");
			msgInfo.setMsgDescription(response != null ? "Response Generated Successfully" : "Null response from API");
			if(Objects.nonNull(response)) {
				response.setMsgInfo(msgInfo);
			}
			logger.info("response set for updateAgentEmpFraudflagSkip transactionId {} is {}", transactionId, response);
		} catch (Exception e) {
			logger.info("error in updateAgentEmpFraudflagSkip for transactionId {} and exception {}", transactionId, Utility.getExceptionAsString(e));
			MsgInfo msgInfo = new MsgInfo();
			msgInfo.setMsg("No Content");
			msgInfo.setMsgCode("204");
			msgInfo.setMsgDescription("Exception in API");
			if(Objects.nonNull(response)) {
			response.setMsgInfo(msgInfo);
			}
		}

		return response;
	}
	//FUL2-133670
	@PostMapping(path = "/getAgentEmployeeFraudCases")
	public ResponsePayload getAgentEmployeeFraudCases(@RequestBody com.mli.mpro.agentSelfIdentifiedSkip.RequestPayload request, BindingResult result) throws Exception {
		ResponsePayload response = new ResponsePayload();
		AgentSelfIdentifiedSkipResponse headerResponse = new AgentSelfIdentifiedSkipResponse();
		try {
			if (validateInputRequest(request)) {
				response = agentSelfService.getAgentSelfIdentifiedSkip(request);

				if (response != null && response.getResponse().getMsgInfo()==null) {
					MsgInfo msgInfo = new MsgInfo();
					msgInfo.setMsg("Success");
					msgInfo.setMsgCode("200");
					msgInfo.setMsgDescription("Response Generated Successfully");
					response.getResponse().setMsgInfo(msgInfo);
				} else if(Objects.nonNull(response) && response.getResponse().getMsgInfo()==null) {
					MsgInfo msgInfo = new MsgInfo();
					msgInfo.setMsg(FAILED_CAMELCASE);
					msgInfo.setMsgCode("204");
					msgInfo.setMsgDescription("Null Values returned from service");
					response.getResponse().setMsgInfo(msgInfo);
				}
			} else {
				MsgInfo msgInfo = new MsgInfo();
				msgInfo.setMsg(FAILED_CAMELCASE);
				msgInfo.setMsgCode("400");
				msgInfo.setMsgDescription("Incorrect input request");
				response.getResponse().setMsgInfo(msgInfo);

			}
		} catch (Exception e) {
			logger.info("Exception in getAgentEmployeeFraudCases: {}", Utility.getExceptionAsString(e));
			getFailedHeaders(response,headerResponse);
		}
		return response;
	}

	private boolean validateInputRequest(com.mli.mpro.agentSelfIdentifiedSkip.RequestPayload request) {
		return null != request.getRequest() &&
				null != request.getRequest().getRequestData() &&
				null != request.getRequest().getRequestData().getRequestPayload()
				&& null != request.getRequest().getRequestData().getRequestPayload().getPolicyDetails() &&
				(!request.getRequest().getRequestData().getRequestPayload().getPolicyDetails().policyNumbers.isEmpty() ||
						!request.getRequest().getRequestData().getRequestPayload().getPolicyDetails().transactionId.isEmpty());
	}

	private static void getFailedHeaders(ResponsePayload response, AgentSelfIdentifiedSkipResponse headerResponse) {
		MsgInfo msgInfo = new MsgInfo();
		msgInfo.setMsg(FAILED_CAMELCASE);
		msgInfo.setMsgCode("204");
		msgInfo.setMsgDescription("Null Values returned from service");
		AgentSelfIdentifiedSkipResponse msgInfoResponse = new AgentSelfIdentifiedSkipResponse();
		msgInfoResponse.setMsgInfo(msgInfo);
		if (Objects.nonNull(response)){
			response.setResponse(msgInfoResponse);
		}
	}
	private String setTimeStamp() {
		String formattedDateTime = "";
		try {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date today = Calendar.getInstance().getTime();
			formatter.setTimeZone(TimeZone.getTimeZone("IST"));
			formattedDateTime = formatter.format(today);
			logger.info("timeStamp for nps is {}", formattedDateTime);
		} catch (Exception e) {
			logger.info("Exception in setTimeStamp: {}", Utility.getExceptionAsString(e));
		}
		return formattedDateTime;
	}

	/*
	Ful2-133676 - Ybl Replacement Api calls at proceed of Screen 2 and retrieve ReplacementSale Flag of Ybl Policies based on Pan Number
	 */
	@PostMapping(value = "/yblReplacementSale")
	public void getReplacementSaleFlag(@RequestBody InputRequest inputRequest) {
		try {
			Utility utility= new Utility();
			String jsonString=Utility.getJsonRequest(inputRequest);
			Set<com.mli.mpro.onboarding.partner.model.ErrorResponse> errors = utility.validateJson(jsonString, YBL_REPLACEMENT);
			com.mli.mpro.common.models.OutputResponse pincodeOutputResponse = new com.mli.mpro.common.models.OutputResponse();
			Response pincodeResponse = new Response();
			if (!CollectionUtils.isEmpty(errors)) {
				MsgInfo msgInfo = new MsgInfo();
				msgInfo.setErrors(errors);
				msgInfo.setMsgCode(String.valueOf(HttpStatus.BAD_REQUEST));
				pincodeResponse.setMsgInfo(msgInfo);
				pincodeOutputResponse.setResponse(pincodeResponse);
				return;
			}
			// Check if feature flag is enabled or not
			if (Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLEYBLREPLACEMENTAPI))) {
				if (null != inputRequest.getRequest() && null != inputRequest.getRequest().getRequestData() &&
						null != inputRequest.getRequest().getRequestData().getYblReplacementSaleRequestPayload()) {
					YblReplacementSaleRequestPayload yblReplacementSaleRequestPayload = inputRequest.getRequest().getRequestData().getYblReplacementSaleRequestPayload();
					yblReplacementSaleService.callYblReplacementSale(yblReplacementSaleRequestPayload);
				} else {
					logger.info("Request is null for Ybl replacement Sale Api");
				}
			} else {
				logger.info("Ybl replacement sale feature flag is disabled ");
			}
		} catch (Exception ex) {
			logger.error("Getting exception for Ybl Replacement Sale {}",ex.getMessage());
		}
	}

	/**
	 * Epic Id : FUL2-156448, Story Id : FUL2-159516
	 * Retrieve encrypted Agent360 data for a given agent ID.
	 * @param agentrequest Request with agentID and services name object.
	 * @return The AgentResponse containing the encrypted data.
	 */
	@PostMapping(value = "/agent360-cloud-service")
	public AgentResponse agent360Data(
			@RequestBody SoaApiRequest<com.mli.mpro.agent.models.RequestPayload> agentrequest) {
		try {
			Utility utility= new Utility();
			String jsonString=Utility.getJsonRequest(agentrequest);
			logger.info("Api request agent 360 data with requestId {}", agentrequest.getRequest().getHeader().getCorrelationId());
			Set<com.mli.mpro.onboarding.partner.model.ErrorResponse> errors = utility.validateJson(jsonString, AGENT360_CLOUD);
			AgentResponse agent360OutputResponse = new AgentResponse();
			com.mli.mpro.agent.models.Response agent360Response = new com.mli.mpro.agent.models.Response();
			if (!CollectionUtils.isEmpty(errors)) {
				logger.error(FIELD_VALIDATION_ERROR_DURING_BAD_REQUEST, agentrequest, errors);
				MsgInfo msgInfo = new MsgInfo();
				msgInfo.setErrors(errors);
				msgInfo.setMsgCode(String.valueOf(HttpStatus.BAD_REQUEST));
				agent360Response.setMsgInfo(msgInfo);
				agent360OutputResponse.setResponse(agent360Response);
				return agent360OutputResponse;
			}
			return soaCloudService.fetchAgent360EncryptedData(agentrequest);
		} catch (UserHandledException ex) {
			logger.error(EXCEPTION_DURING_OAUTH_TOKEN_AGENT_API, ex);
			throw new RuntimeException("Error fetching Agent360 data", ex);
		}
	}


	@PostMapping("/validateBajajSellerData")
	public OutputResponse fetchSellerData(@RequestBody InputRequest inputRequest) throws UserHandledException{
		Response response = new Response();
		ResponseData responseData = new ResponseData();
		OutputResponse outputResponse = new OutputResponse();
		BajajSellerDataResponsePayload bajajSellerDataResponsePayload = null;
		try{
			logger.info("Inside validateBajajSellerData");
			Utility utility=new Utility();
			String jsonString = Utility.getJsonRequest(inputRequest);
			Set<com.mli.mpro.onboarding.partner.model.ErrorResponse> errors= utility.validateJson(jsonString, CHECK_BAJAJ_SELLER_APPLICABILITY);
			if(!CollectionUtils.isEmpty(errors)){
				logger.error(FIELD_VALIDATION_ERROR_DURING_BAD_REQUEST, inputRequest, errors);
				MsgInfo msgInfo=new MsgInfo();
				msgInfo.setMsgCode(BAD_REQUEST_CODE);
				msgInfo.setErrors(errors);
				response.setMsgInfo(msgInfo);
				outputResponse.setResponse(response);
				return outputResponse;
			}
			bajajSellerDataResponsePayload = bajajSellerDataService.checkAgentApplicability(inputRequest);
		}catch(UserHandledException e){
			List<String> errorMessages = new ArrayList<>();
			logger.error("Input request received for failed seller validation : {}",inputRequest);
			logger.error("Exception Occured while processing fetchSellerData: {}",Utility.getExceptionAsString(e));
			errorMessages.add(e.getMessage());
			throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.setMetadata(inputRequest.getRequest().getMetadata());
		responseData.setBajajSellerDataResponsePayload(bajajSellerDataResponsePayload);
		response.setResponseData(responseData);
		outputResponse.setResponse(response);
		return outputResponse;
	}

    @PostMapping(value = "/client360-cloud-service")
    public SoaCloudResponse<SoaClient360ResponsePayload> client360ApiRequest(@RequestBody SoaApiRequest<com.mli.mpro.agent.models.RequestPayload> request){
        SoaCloudResponse<SoaClient360ResponsePayload> soaCloudResponse = null;
        try {
            logger.info("Api request for client 360 Soa API with requestId {}", request.getRequest().getHeader().getCorrelationId());
            soaCloudResponse = soaCloudService.fetchSoaClient360ApiResponse(request);
        }catch (UserHandledException uex){
            logger.error("Exception occurred during client 360 Api {} ",Utility.getExceptionAsString(uex));
        }
        return soaCloudResponse;
    }

	@PostMapping(value = "/spCodeValidation-cloud-service")
	public SoaCloudResponse<SellerResponsePayload> spCodeValidationSoaApi(@RequestBody SoaApiRequest<SellerInfoPayload> request){
		SoaCloudResponse<SellerResponsePayload> soaCloudResponse = null;
		try {
			logger.info("Api request for SP Code validation Soa API with requestId {}", request.getRequest().getHeader().getCorrelationId());
			Utility utility=new Utility();
			String jsonString = Utility.getJsonRequest(request);
			Set<com.mli.mpro.onboarding.partner.model.ErrorResponse> errors= utility.validateJson(jsonString, SPCODE_VALIDATION_CLOUD_SERVICE);
			if(!CollectionUtils.isEmpty(errors)){
				 soaCloudResponse = new SoaCloudResponse<SellerResponsePayload>();
				SoaResponse response=new SoaResponse();
				MsgInfo msgInfo=new MsgInfo();
				msgInfo.setMsgCode(BAD_REQUEST_CODE);
				msgInfo.setErrors(errors);
				response.setMsgInfo(msgInfo);
				soaCloudResponse.setResponse(response);
				return soaCloudResponse;
			}
			soaCloudResponse = soaCloudService.fetchSOASpCodeValidationApiResponse(request);
		}catch (UserHandledException uex){
			logger.error("Exception occurred during spCodeValidation Api {}", Utility.getExceptionAsString(uex));
			return soaCloudService.handelErrorResponse(uex, request);
		}
		return soaCloudResponse;
	}



	@PostMapping("/login")
	public ResponseEntity<String> getLoginData(
			@RequestBody LoginRequest loginRequest) {
		return loginService.executeLoginService(loginRequest);
	}

	@GetMapping("/logout")
	public ResponseEntity<String> getLogOut(@RequestHeader("apitoken") String apiToken) {
		return loginService.executeLogoutService(apiToken);
	}

	@GetMapping("/getRedisKeys")
	public Set<String> getRedisKeys(@RequestParam String prefix) {
		return loginService.getKeysFromRedis(prefix);
	}

	@PostMapping("/sendOrValidateOtp")
	public OutputResponse sendOrValidateOtp(@RequestBody InputRequest inputRequest) throws UserHandledException {
			OutputResponse outputResponse=new OutputResponse();
			Response response= new Response();
			EkycResponsePayload ekycResponse=null;
			ResponseData responseData= new ResponseData();
			Utility utility=new Utility();
		String jsonString = Utility.getJsonRequest(inputRequest);
		logger.info("Request received for sendOrValidateOtp {}", jsonString);
		Set<com.mli.mpro.onboarding.partner.model.ErrorResponse> errors= utility.validateJson(jsonString, SEND_OR_VALIDATE_OTP);
		if(!CollectionUtils.isEmpty(errors)){
			logger.error(FIELD_VALIDATION_ERROR_DURING_BAD_REQUEST, inputRequest, errors);
			MsgInfo msgInfo=new MsgInfo();
			response.setMsgInfo(msgInfo);
			responseData.setEkycResponse(new EkycResponsePayload(AppConstants.STATUS_FAILURE,AppConstants.EKYC_DETAILS_MISSING,null,null,null,null,null));
			response.setResponseData(responseData);
			outputResponse.setResponse(response);
			outputResponse.getResponse().getMsgInfo().setMsgCode(BAD_REQUEST_CODE);
			outputResponse.getResponse().getMsgInfo().setErrors(errors);
			return outputResponse;
		}
		try{
			if (null != inputRequest.getRequest() && null != inputRequest.getRequest().getRequestData() &&
					null != inputRequest.getRequest().getRequestData().getEkycPayload()){
				EkycPayload ekycPayload=inputRequest.getRequest().getRequestData().getEkycPayload();
				ekycResponse = ekycService.sendingOrValidatingOtp(ekycPayload);
			}
		}catch (Exception e){
			List<String> errorMessages = new ArrayList<>();
			logger.error(EXCEPTION_OCCURED_WHILE_PROCESSING_REQUEST,e);
			errorMessages.add(e.getMessage());
			throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		responseData.setEkycResponse(ekycResponse);
		response.setResponseData(responseData);
		outputResponse.setResponse(response);
		logger.info("Response received for sendOrValidateOtp {}", outputResponse);
		return outputResponse;
	}

	/**
	 * EpicId: FUL2-168622
	 * Retreive Axis Branch Details
	 * @param inputRequest with channelName and soaAppId and soaCorrelationId
	 * @return BranchDetailsResponse
	 */
	@PostMapping("/getAxisBranchDetails")
	public BranchDetailsResponse getAxisBranchDetails(@RequestBody InputRequest inputRequest) {
		BranchDetailsResponse response = new BranchDetailsResponse();
		String transactionId = null;
		MsgInfo msgInfo = new MsgInfo();

		try {
			if(Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(ENABLE_BRANCHCODE_EDITABLE))){
				if (null != inputRequest && null != inputRequest.getRequest() && null != inputRequest.getRequest().getRequestData()
						&& null != inputRequest.getRequest().getRequestData().getAxisBranchDetailsPayload() && null != inputRequest.getRequest().getRequestData().getAxisBranchDetailsPayload().getTransactionId()
						&& null != inputRequest.getRequest().getRequestData().getAxisBranchDetailsPayload().getChannelCode()) {
					transactionId = inputRequest.getRequest().getRequestData().getAxisBranchDetailsPayload().getTransactionId();
					String channelCode = inputRequest.getRequest().getRequestData().getAxisBranchDetailsPayload().getChannelCode();
					response = locationService.getAxisBranchDetails(transactionId, channelCode);
				}
			}else{
				logger.info("SP RA Mapped Removed for Physical Journey feature flag is disabled ");
			}
		} catch (Exception ex) {
			logger.error("Getting exception {} while calling Axis Branch Details API for transactionId {}", Utility.getExceptionAsString(ex), transactionId);
			msgInfo.setMsg(ex.getMessage());
			msgInfo.setMsgCode(AppConstants.INTERNAL_SERVER_ERROR_CODE);
			return locationServiceImpl.buildDefaultResponse(msgInfo);
		}
		logger.info("getAxisBranchDetails request and response of transactionId {} is {} and {} ", Objects.nonNull(inputRequest) ? inputRequest.getRequest().getRequestData().getAxisBranchDetailsPayload().getTransactionId() : null, Utility.printJsonRequest(inputRequest),response);
		return response;
	}

	@PostMapping("/getyblpasadata")
	public OutputResponse getyblpasadata(@RequestBody InputRequest inputRequest){
		Response response = new Response();
		ResponseData responseData = new ResponseData();
		OutputResponse outputResponse = new OutputResponse();
		YblPasaResponse yblPasaResponse = new YblPasaResponse();
		String transactionId = inputRequest.getRequest().getRequestData().getPayload().getTransactionId();
		try{
			Utility utility = new Utility();
			String jsonString=Utility.getJsonRequest(inputRequest);
			logger.info("YBL Pasa API input request received {} for transactionId {}",jsonString,transactionId);
			Set<com.mli.mpro.onboarding.partner.model.ErrorResponse> errors = utility.validateJson(jsonString, YBL_PASADATA);

			if(inputRequest.getRequest() != null && inputRequest.getRequest().getRequestData() != null && inputRequest.getRequest().getRequestData().getRequestPayload() != null)
				utility.validateTransactionId(inputRequest.getRequest().getRequestData().getRequestPayload().getTransactionId(),errors, "request.requestData.payload.transactionId");

			com.mli.mpro.common.models.OutputResponse yblPasaOutputResponse = new com.mli.mpro.common.models.OutputResponse();
			Response yblResponse = new Response();
			if (!CollectionUtils.isEmpty(errors)) {
				logger.error(FIELD_VALIDATION_ERROR_DURING_BAD_REQUEST, inputRequest, errors);
				MsgInfo msgInfo = new MsgInfo();
				msgInfo.setErrors(errors);
				msgInfo.setMsgCode(String.valueOf(HttpStatus.BAD_REQUEST));
				yblResponse.setMsgInfo(msgInfo);
				yblPasaOutputResponse.setResponse(yblResponse);
				return yblPasaOutputResponse;
			}
			yblPasaResponse = yblPasaService.fetchYblPasaData(inputRequest);
		}catch(Exception ex){
			ex.printStackTrace();
			com.mli.mpro.nps.model.MsgInfo msgInfo = new com.mli.mpro.nps.model.MsgInfo("504","Exception while executing API Call in Backend","Internal Server error");
			YblResponsePayload yblResponsePayload = new YblResponsePayload();
			new YblPasaServiceImpl().setDefaultValuesForYBLCustomer(yblPasaResponse);
			yblPasaResponse.setMsgInfo(msgInfo);
		}
		responseData.setYblPasaResponse(yblPasaResponse);
		response.setResponseData(responseData);
		outputResponse.setResponse(response);
		logger.info("Response returned from YBL Pasa API for transactionId {} is {}",transactionId,Utility.printJsonRequest(outputResponse));
		return outputResponse;
	}

	@RequestMapping(value = "/callBrmsBroker", method = RequestMethod.POST)
	public OutputResponse brmsBrokerCall(@RequestBody InputRequest inputRequest,BindingResult result) throws JsonProcessingException, UserHandledException, URISyntaxException {
		String uniqueId= UUID.randomUUID().toString();
		ArrayList<String> errorMessages =new ArrayList<>();
		OutputResponse outputResponse = new OutputResponse();
		Response response = new Response();
		ResponseData responseData = new ResponseData();
		logger.info("unique Id for the brmsBroker call is {}",uniqueId);
		setLogVariable(inputRequest,false);
		Utility utility= new Utility();
		String jsonString=Utility.getJsonRequest(inputRequest);
		Set<com.mli.mpro.onboarding.partner.model.ErrorResponse> errors = utility.validateJson(jsonString, CALL_BRMS_BROKER);
		com.mli.mpro.common.models.OutputResponse brmsCallOutputResponse = new com.mli.mpro.common.models.OutputResponse();
		Response brmsResponse = new Response();
		if (!CollectionUtils.isEmpty(errors)) {
			logger.error(FIELD_VALIDATION_ERROR_DURING_BAD_REQUEST, inputRequest, errors);
			MsgInfo msgInfo = new MsgInfo();
			msgInfo.setErrors(errors);
			msgInfo.setMsgCode(String.valueOf(HttpStatus.BAD_REQUEST));
			brmsResponse.setMsgInfo(msgInfo);
			brmsCallOutputResponse.setResponse(brmsResponse);
			return brmsCallOutputResponse;
		}
		if(result.hasErrors())
		{
			ErrorResponse errorResponse=new ErrorResponse();
			String errorMessage=result.getAllErrors().stream().map(res -> ((FieldError)res).getDefaultMessage()).collect(Collectors.joining(" & "));
			errorMessages.add(errorMessage);
			logger.info(FIELD_VALIDATION_ERROR,errorMessage);
			errorResponse.setErrorMessages(errorMessages);
			errorResponse.setErrorCode(422);
			response.setErrorResponse(errorResponse);
			outputResponse.setResponse(response);
			return outputResponse;
		}
		try {
			outputResponse=brmsBrokerService.fetchBrmsResponse(inputRequest, uniqueId, errorMessages);
		}catch(Exception e){
			logger.error("Exception occurred in brmsBrokerCall for uniqueId {} ,{}", uniqueId, Utility.getExceptionAsString(e));
			errorMessages.add("Exception in setDataForBrms " + e.getMessage());
			throw new UserHandledException(new com.mli.mpro.common.models.Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return outputResponse;
	}

    /*
     * FUL2-182527 - FUL2-186213 Tele DIY | Continue Journey URL & Auth
     * Soa otp api for generate and validate otp.
	 * if validation success add jwt token.
	 * if generate success otp send to customer
	* */

    @PostMapping(value = "/onboarding-otpService")
    public OtpServiceResponse otpServiceResponse(@RequestBody OtpServiceRequest request)throws UserHandledException{

            if(Utility.isAnyObjectNull(request, request.getRequest(), request.getRequest().getPayload())){
				throw new UserHandledException(Arrays.asList("Not a valid request please try with valid request"), HttpStatus.BAD_REQUEST);
			}
			logger.info("OTP request from node service  with transactionID {} is {}", request.getRequest().getPayload().getTransactionId(), Utility.printJsonRequest(request));
            return locationService.fetchJ2OTPServiceResponse(request);
	}

	/**
	 * Master360 Soa Api Implementation for DataLake server
	 * Policy360 (V1) now merge into master360 | request contains policy number and effective date
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/master360-cloud-service")
	public SoaCloudResponse<com.mli.mpro.location.models.soaCloudModels.master360ResponseModels.Response> master360SoaApi(@RequestBody SoaApiRequest<com.mli.mpro.location.models.soaCloudModels.master360RequestModels.Request> request){
		SoaCloudResponse<com.mli.mpro.location.models.soaCloudModels.master360ResponseModels.Response> soaCloudResponse = null;
		try {
			com.mli.mpro.location.models.soaCloudModels.master360RequestModels.Payload payload = objectMapper.convertValue(request.getRequest().getPayload(), com.mli.mpro.location.models.soaCloudModels.master360RequestModels.Payload.class);
			logger.info("Api request for Master360-cloud-service with policyNumber {}", payload.getPolicyNo());
			soaCloudResponse = soaCloudService.fetchMaster360Response(request);
		}catch (UserHandledException uex){
			logger.error("Exception occurred during spCodeValidation Api ",uex);
			return soaCloudService.handelErrorResponse(uex, request);
		}
		return soaCloudResponse;
	}

	@PostMapping(value = "/clientPolicyDetail-cloud-service")
	public SoaCloudResponse<CpdResponse> clientPolicyDetailsDLApi(@RequestBody SoaApiRequest<CpdRequest> request){
		SoaCloudResponse<CpdResponse> soaCloudResponse = null;
		try {
			logger.info("Api for Client Policy Detail Soa API called");
			soaCloudResponse = soaCloudService.fetchClientPolicyDetailResponse(request);
		}catch (UserHandledException uex){
			logger.error("Exception occurred during Client Policy Detail Soa API ",uex);
			return soaCloudService.handelErrorResponse(uex, request);
		}
		return soaCloudResponse;
	}

	@PostMapping(value = "/policy-splitting-data-lake")
	public SoaCloudResponse<PolicySplittingResponsePayload> policySplittingDataLake(@RequestBody SoaApiRequest<PolicySplittingRequest> request){
		SoaCloudResponse<PolicySplittingResponsePayload> soaCloudResponse;
		try {
			logger.info("Api request for Policy Splitting Data Lake Soa API with policyNumber {}", ((Map<String,Object>)(request.getRequest().getPayload())).get("currentPolicyNumber"));
			soaCloudResponse = soaCloudService.callPolicySplittingDataLakeAPI(request);
		}catch (UserHandledException uex){
			logger.error("Exception occurred during Policy Splitting Data Lake Api ",uex);
			return soaCloudService.handelErrorResponse(uex, request);
		}
		return soaCloudResponse;
	}

	@PostMapping(value = "/agent-commission-summary-data-lake")
	public SoaCloudResponse<AgentCommissionSummaryResponse> agentCommissionSummaryDataLake(@RequestBody SoaApiRequest<AgentCommissionSummaryRequest> request){
		SoaCloudResponse<AgentCommissionSummaryResponse> soaCloudResponse;
		try {
			logger.info("Api request for Agent Commission Summary Soa API with agentIds {}", ((Map<String,Object>)(request.getRequest().getPayload())).get("agentId"));
			soaCloudResponse = soaCloudService.callAgentCommissionSummaryDataLakeAPI(request);
		}catch (UserHandledException uex){
			logger.error("Exception occurred during Agent Commission Summary Data Lake Api ",uex);
			return soaCloudService.handelErrorResponse(uex, request);
		}
		return soaCloudResponse;
	}

	@PostMapping(value = "/policy-history")
	public SoaCloudResponse<PolicyHistoryResponse> policyHistory(@RequestBody SoaApiRequest<PolicyHistoryRequest> request){
		SoaCloudResponse<PolicyHistoryResponse> soaCloudResponse;
		try {
			logger.info("Calling Policy History API");
			soaCloudResponse = soaCloudService.callPolicyHistoryApi(request);
		}catch (UserHandledException uex){
			logger.error("Exception occurred during executing Policy History Api ",uex);
			return soaCloudService.handelErrorResponse(uex, request);
		}
		return soaCloudResponse;
	}
	@PostMapping(value = "/amlTraining-cloud-service")
	public SoaCloudResponse<SoaResponsePayload> amlTrainingDLApi(@RequestBody SoaAmlRequest request){
		try {
			if(Utility.isAnyObjectNull(request,request.getRequest(),request.getRequest().getRequestData())){
				logger.error("Invalid Request {}", request);
				return getErrorResponse(request,new UserHandledException(Arrays.asList("Invalid request !!"), HttpStatus.BAD_REQUEST));
			}
			logger.info("Api request for AML training for DL api with requestId {}", request.getRequest().getHeader().getSoaCorrelationId());
			return soaCloudService.fetchSOAAMLTrainingDLApi(request);
		}catch (UserHandledException uex){
			return getErrorResponse(request, uex);
		}
	}
	@PostMapping(value = "/sendOTP", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> sendOTP(@RequestBody OtpInputRequest inputRequest) {
		//Request Validation
		Set<com.mli.mpro.onboarding.partner.model.ErrorResponse> otpRequestErrors = validateOtpRequest(inputRequest);
		if (!CollectionUtils.isEmpty(otpRequestErrors)) {
			return errorResponseEntity(otpRequestErrors,inputRequest);
		}
		Set<com.mli.mpro.onboarding.partner.model.ErrorResponse>  flowTypeRequestErrors = validateFlowTypeRequest(inputRequest);
		if (!CollectionUtils.isEmpty(flowTypeRequestErrors)) {
			return errorResponseEntity(flowTypeRequestErrors,inputRequest);
		}
		logger.info("SendOTP Request received for transactionId {}", inputRequest.getRequest().getRequestData().getPayload().getTransactionId());
		return otpService.sendOTP(inputRequest);
	}
	private Set<com.mli.mpro.onboarding.partner.model.ErrorResponse> validateOtpRequest(OtpInputRequest inputRequest) {
		Utility utility= new Utility();
		String jsonString=Utility.getJsonRequest(inputRequest);
		Set<com.mli.mpro.onboarding.partner.model.ErrorResponse> otpRequestValidationErrors = utility.validateJson(jsonString, OTP_VALIDATION);
		return  otpRequestValidationErrors;
	}
	private Set<com.mli.mpro.onboarding.partner.model.ErrorResponse> validateFlowTypeRequest(OtpInputRequest inputRequest) {
		Utility utility= new Utility();
		String flowType= inputRequest.getRequest().getRequestData().getPayload().getFlowType();
		switch (flowType) {
			case DISABILITY_DECLARATION_FLOWTYPE:
				String flowTypeJsonString = Utility.getJsonRequest(inputRequest.getRequest().getRequestData().getPayload().getDisabilityDeclaration());
				return utility.validateJson(flowTypeJsonString, DISABILITY_DECLARATION_FLOWTYPE);
			default:
				return new HashSet<>();
		}
	}
	private ResponseEntity<Object> errorResponseEntity(Set<com.mli.mpro.onboarding.partner.model.ErrorResponse> otpRequestErrors, OtpInputRequest inputRequest) {
		String errorMsgs = otpRequestErrors.stream()
				.map(com.mli.mpro.onboarding.partner.model.ErrorResponse::getMessage)
				.collect(Collectors.joining(", "));
		logger.error("Json validation errors for transactionId {} are {}", inputRequest.getRequest().getRequestData().getPayload().getTransactionId(), errorMsgs);
		return new ResponseEntity<>(new OtpOutputResponse.Builder()
				.errorResponse(new com.mli.mpro.location.otp.models.ErrorResponse.Builder()
						.errorMessage(errorMsgs)
						.errorCode(HttpStatus.BAD_REQUEST.value())
						.build())
				.build(), HttpStatus.BAD_REQUEST);
	}

	@PostMapping(value = "/validateOTP", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> validateOTP(@RequestBody OtpInputRequest inputRequest) {

		return otpService.verifyOTP(inputRequest);
	}
	private SoaCloudResponse getErrorResponse(SoaAmlRequest request, UserHandledException uex) {
		logger.error("Exception occurred during AML training DL Api ", uex);
		com.mli.mpro.agent.models.RequestHeader requestHeader = new com.mli.mpro.agent.models.RequestHeader();
		requestHeader.setApplicationId(request.getRequest().getHeader().getSoaAppId());
		requestHeader.setCorrelationId(request.getRequest().getHeader().getSoaCorrelationId());
		com.mli.mpro.agent.models.Request<SoaAmlRequest> requestAml = new com.mli.mpro.agent.models.Request<>();
		requestAml.setHeader(requestHeader);
		SoaApiRequest<SoaAmlRequest> soaApiRequest = new SoaApiRequest<>();
		soaApiRequest.setRequest(requestAml);
		return soaCloudService.handelErrorResponse(uex, soaApiRequest);
	}

	@PostMapping("/fetch-sarthi-data")
	private ResponseEntity<SarthiResponsePayload> fetchSarthiData(@Valid @RequestBody InputRequest inputRequest, BindingResult result){

		if (result.hasErrors()) {
			List<String> errorMessages = result.getAllErrors()
					.stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage)
					.collect(Collectors.toList());

			String errorMessage = String.join(", ", errorMessages);
			logger.error(FIELD_VALIDATION_ERROR_DURING_BAD_REQUEST, inputRequest, errorMessages);

			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new SarthiResponsePayload(errorMessage));
		}

		return ResponseEntity.ok(locationService.fetchSarthiData(inputRequest));
	}

	@PostMapping("/unified-payment")
	public ResponseEntity<UIPaymentRequestResponse> unifiedPayment(@Valid @RequestBody UIPaymentRequestResponse request, BindingResult result) throws UserHandledException{
		logger.info("Entering unifiedPayment API with request: {}", request);

		if (result.hasErrors()) {
			List<String> errorMessages = result.getAllErrors()
					.stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage)
					.collect(Collectors.toList());

			String errorMessage = String.join(", ", errorMessages);
			logger.error("Validation Errors at unified-payment: {}", errorMessage);

			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new UIPaymentRequestResponse(errorMessage));

		}

		UIPaymentRequestResponse response = unifiedPaymentService.unifiedPayment(request);
		logger.info("Exiting unifiedPayment API with response: {}", response);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/unified-payment-status-update")
	public Object unifiedPaymentStatusUpdate(@RequestBody UnifiedWebHookRequest request) throws UserHandledException{
		logger.info("Entering unifiedPaymentStatusUpdate API with request: {}", request);
		Object response = unifiedPaymentService.unifiedPaymentStatusUpdate(request);
		logger.info("Exiting unifiedPaymentStatusUpdate API with response: {}", response);
		return response;
	}

	@PostMapping("/check-unified-payment-status")
	public UIPaymentRequestResponse checkUnifiedPaymentStatus(@RequestBody UIPaymentRequestResponse request) throws UserHandledException{
		logger.info("Entering checkUnifiedPaymentStatus API with request: {}", request);
		UIPaymentRequestResponse response = unifiedPaymentService.checkUnifiedPaymentStatus(request);
		logger.info("Exiting checkUnifiedPaymentStatus API with response: {}", response);
		return response;

	}

	@PostMapping(value = "/tpa-history")
	public SoaCloudResponse<PolicyHistoryResponse> getTpaHistory(@RequestBody SoaApiRequest<PolicyHistoryRequest> request){
		SoaCloudResponse<PolicyHistoryResponse> soaCloudResponse;
		try {
			logger.info("Calling TPA History API");
			soaCloudResponse = soaCloudService.getTpaHistory(request);
		}catch (UserHandledException uex){
			logger.error("Exception occurred during executing TPA History Api {}",Utility.getExceptionAsString(uex));
			return soaCloudService.handelErrorResponse(uex, request);
		}
		return soaCloudResponse;
	}
	@RequestMapping(value="/getEmailPolicyPack",method = RequestMethod.POST )
	public OutputResponse getEmailPolicy(@RequestBody InputRequest inputRequest,BindingResult result ) throws UserHandledException {
		ArrayList<String> errorMessages =new ArrayList<>();
		OutputResponse outputResponse = new OutputResponse();
		Response response = new Response();
		ResponseData responseData = new ResponseData();
		EmailPolicyPackResponsePayload emailPolicyPackResponsePayload = new EmailPolicyPackResponsePayload();
		ArrayList<String> message = new ArrayList<>();
		try {
			if (result.hasErrors()) {
				ErrorResponse errorResponse = new ErrorResponse();
				String errorMessage = result.getAllErrors().stream().map(res -> ((FieldError) res).getDefaultMessage()).collect(Collectors.joining(" & "));
				errorMessages.add(errorMessage);
				logger.info(FIELD_VALIDATION_ERROR, errorMessage);
				errorResponse.setErrorMessages(errorMessages);
				errorResponse.setErrorCode(422);
				response.setErrorResponse(errorResponse);
				outputResponse.setResponse(response);
				return outputResponse;
			}
			SoaResponseData soaResponseData = emailPolicyService.fetchPolicyStatus(inputRequest);
			if (Objects.isNull(soaResponseData)) {
				message.add("Null response after service execution.");
				throw new UserHandledException(message, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			emailPolicyPackResponsePayload.setStatusCode(soaResponseData.getStatusCode());
			emailPolicyPackResponsePayload.setStatusMessage(soaResponseData.getStatusMessage());
			emailPolicyPackResponsePayload.setByteArr(soaResponseData.getByteArr());
			responseData.setEmailPolicyPackResponsePayload(emailPolicyPackResponsePayload);
			response.setResponseData(responseData);
			outputResponse.setResponse(response);
		}catch (Exception e){
			logger.error("Exception occurred while implementing the service: {}", Utility.getExceptionAsString(e));
			emailPolicyPackResponsePayload.setStatusCode(INTERNAL_SERVER_ERROR_CODE);
			emailPolicyPackResponsePayload.setStatusMessage(FAILED_CAMELCASE);
			responseData.setEmailPolicyPackResponsePayload(emailPolicyPackResponsePayload);
			response.setResponseData(responseData);
			outputResponse.setResponse(response);
		}
		return outputResponse;
	}

	@PostMapping("/getUtmCode")
	public ResponseEntity<UtmConfiguratorResponse> getUtmCode(@Valid @RequestBody BrmsInputRequest utmInputRequest, BindingResult result) {

		if (result.hasErrors()) {
			List<String> errorMessages = result.getAllErrors()
					.stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage)
					.collect(Collectors.toList());

			String errorMessage = String.join(", ", errorMessages);
			logger.error(FIELD_VALIDATION_ERROR_DURING_BAD_REQUEST, utmInputRequest, errorMessage);

			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new UtmConfiguratorResponse(errorMessage));
		}
		UtmConfiguratorResponse utmConfiguratorResponse = utmConfiguratorService.getUtmCode(utmInputRequest);
		return new ResponseEntity<>(utmConfiguratorResponse, HttpStatus.OK);
	}

	@PostMapping("/brmsBrocaAPI")
	public ResponseEntity<BrmsBrokerAPIResponse> brmsBrocaAPI(@Valid @RequestBody BrmsBrokerAPIRequest brmsBrokerAPIRequest, BindingResult result){
		BrmsBrokerAPIResponse brmsBrokerAPIResponse = new BrmsBrokerAPIResponse();
		try {

			if (result.hasErrors()) {
				List<String> errorMessages = result.getAllErrors()
						.stream()
						.map(DefaultMessageSourceResolvable::getDefaultMessage)
						.collect(Collectors.toList());

				String errorMessage = String.join(", ", errorMessages);
				logger.error(FIELD_VALIDATION_ERROR_DURING_BAD_REQUEST, brmsBrokerAPIRequest, errorMessage);

				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new BrmsBrokerAPIResponse("400", errorMessage));
			}

			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("api_client_secret", bypassHeaderEncryptValue);
			HttpEntity<BrmsBrokerAPIRequest> requestEntity = new HttpEntity<>(brmsBrokerAPIRequest, headers);
			brmsBrokerAPIResponse = restTemplate.postForObject(utmCodeValidationURL, requestEntity, BrmsBrokerAPIResponse.class);
			logger.info("Request and response for brmsBrocaApi is {} and {}", requestEntity, brmsBrokerAPIResponse);
		} catch (Exception ex) {
			logger.error("exception occurred while calling brmsBrokerAPI {}", Utility.getExceptionAsString(ex));
		}
		return ResponseEntity.ok(brmsBrokerAPIResponse);
	}
	/**
	 * FUL2-223106 DIY journey-Resume journey enhancement
	 * validate api for resume journey.
	 * policyNumber and DOB validation as per the request.
	 * return success if policyNumber and DOB is valid.
	 * */
	@PostMapping(value = "/resumeJourneyValidation")
	public GenericApiResponse<ResumeJourneyResponse> validateResumeJourney(@RequestBody GenericApiRequest<ResumeJourneyRequest> inputRequest) {
		return locationService.validateResumeJourneyData(inputRequest);
	}
	@RequestMapping(value ="/retriggerPosv",method = RequestMethod.POST)
	public com.mli.mpro.location.ivc.response.OutputResponse fetchRetriggerUrl(@RequestBody com.mli.mpro.location.ivc.request.InputRequest inputRequest){
		ArrayList<String> errorMessages = new ArrayList<>();
		com.mli.mpro.location.ivc.response.Response response = new com.mli.mpro.location.ivc.response.Response();
		com.mli.mpro.location.ivc.response.OutputResponse outputResponse =new com.mli.mpro.location.ivc.response.OutputResponse();
		try{
			outputResponse = posvRetriggerService.callPosvRetriggerApi(inputRequest, errorMessages);
			logger.info("Output response received for api call is {} for inputRequest {}",outputResponse,inputRequest);
			if(!SUCCESS.equalsIgnoreCase(outputResponse.getResponse().getStatus())){
				throw new UserHandledException(errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}catch(Exception e){
			logger.info("Exception occurred while retriggerPosv {}", Utility.getExceptionAsString(e));
			response.setStatus(FAILED);
			outputResponse.setResponse(response);
		}
		return outputResponse;
	}
	@Autowired
	YBLSendOTPService yblSendOTPService;

	@PostMapping(value = "/sendOTPToYBL")
	public Object generateOTP(@RequestBody Map<String,String> request){
		return yblSendOTPService.sendOTPToYBLCustomer(request);
	}

	//API trigger from UI after clicking on new application button OR for hopping journey call on first page rendering.
	@PostMapping(value = "/getEkycType")
	public GenericApiResponse<UIResponsePayload> getBrmsEkycDetails(@RequestBody GenericApiRequest<UIRequestPayload> inputRequest) throws UserHandledException {
		logger.info("Request received for getBrmsEkycDetails API {}", inputRequest);

		Utility utility= new Utility();
		String jsonString=Utility.getJsonRequest(inputRequest);
		Set<com.mli.mpro.onboarding.partner.model.ErrorResponse> errors= utility.validateJson(jsonString, AppConstants.EKYC_TYPE);
		if(!CollectionUtils.isEmpty(errors)){
			logger.error(FIELD_VALIDATION_ERROR_DURING_BAD_REQUEST, inputRequest, errors);
			UIResponsePayload uiResponsePayload = new UIResponsePayload();
			uiResponsePayload.setStatus(errors.toString());
			return new GenericApiResponse<>(uiResponsePayload);
		}

		return locationService.getEkycBrmsDetails(inputRequest);
	}
	@RequestMapping(value ="/altfinEnquiry",method = RequestMethod.POST)
	public ResponseEntity<AltfinSoaInputResponse> fetchAltfinEnquiry(@Valid @RequestBody AltfinPayload inputRequest, BindingResult result) throws UserHandledException {

		if (result.hasErrors()) {
			List<String> errorMessages = result.getAllErrors()
					.stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage)
					.collect(Collectors.toList());

			String errorMessage = String.join(", ", errorMessages);
			logger.error(FIELD_VALIDATION_ERROR_DURING_BAD_REQUEST, inputRequest, errorMessages);
			com.mli.mpro.docsApp.models.MsgInfo msgInfo = new com.mli.mpro.docsApp.models.MsgInfo();
			msgInfo.setMsg("Validation Error");
			msgInfo.setMsgDescription(errorMessage);
			msgInfo.setMsgCode("400");

			AltfinSoaInputResponse altfinSoaInputResponse = new AltfinSoaInputResponse();
			altfinSoaInputResponse.setMsgInfo(msgInfo);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(altfinSoaInputResponse);
		}

		com.mli.mpro.docsApp.models.MsgInfo msgInfo = new com.mli.mpro.docsApp.models.MsgInfo();
		AltfinSoaInputResponse outputResponse = new AltfinSoaInputResponse();
		com.mli.mpro.proposal.models.Response response = new com.mli.mpro.proposal.models.Response();
		ArrayList errorMessages = new ArrayList();
		AltfinSoaInputResponse altfinSoaInputResponse = new AltfinSoaInputResponse();
		String policyNumber = AppConstants.BLANK;
		try {
			policyNumber = Optional.ofNullable(inputRequest).map(AltfinPayload::getPolicyNumber).orElse(AppConstants.BLANK);

			if(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLE_ALTFIN_CS1)&& org.springframework.util.StringUtils.hasText(policyNumber)){
				altfinSoaInputResponse = altfinInquiryService.executeAltFinInquiryApi(policyNumber);
			}
			Optional.ofNullable(altfinSoaInputResponse).map(AltfinSoaInputResponse::getMsgInfo).map(com.mli.mpro.docsApp.models.MsgInfo::getMsgCode).filter(msgCode-> SUCCESS_RESPONSE.equalsIgnoreCase(msgCode)).orElseThrow();
			outputResponse.setPayload(altfinSoaInputResponse.getPayload());
			outputResponse.setMsgInfo(altfinSoaInputResponse.getMsgInfo());
		}catch(Exception e){
			logger.info("Exception occurred while calling altfin for policyNumber {} and exception {}",policyNumber,Utility.getExceptionAsString(e));
			throw new UserHandledException(errorMessages,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(outputResponse);
	}
	@PostMapping(value="/validateApprovalLink")
	public ResponseEntity<LinkOutputResponse> validateApprovalLink(@RequestBody String inputRequest) throws Exception {
		LinkOutputResponse linkOutputResponse = null;
		try {
			logger.info("Received input payload: {}", inputRequest);
			linkOutputResponse = configurationService.validateApprovalLink(inputRequest);
			logger.info("Approval link validated successfully: {}", linkOutputResponse);
			return ResponseEntity.ok(linkOutputResponse);
		}catch (RuntimeException e){
			throw new Exception(e.getMessage());
		}
		catch (Exception e) {
			logger.error("Error while validating approval link: {}", e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@PostMapping(value="/saveSellerSupervisorStatus")
	public ResponseEntity<ApiResponse> saveSellerSupervisorStatus(@RequestBody SellerSaveRequest request) {
		SupervisorDetails supervisorDetails = null;
		ApiResponse apiResponse = null;
		com.mli.mpro.configuration.models.ErrorResponse errorResponse=null;
		try {
			String response = configurationService.saveSellerSupervisorStatus(request);
			if ("SUCCESS".equals(response)) {
				apiResponse = new ApiResponse(200, "data saved successfully", "Data saved successfully");
			}else if ("EXPIRY".equals(response)) {
				errorResponse = new com.mli.mpro.configuration.models.ErrorResponse();
				errorResponse.setErrorCode("404");
				errorResponse.setMessage("Link has expired");
				apiResponse = new ApiResponse(404, "Link has expired", "Link has expired");
				apiResponse.setErrorResponse(errorResponse);
			} else {
				apiResponse = new ApiResponse(208, "Data already saved", response);
			}

		} catch (Exception e) {
			logger.info("Exception occurred while calling saveSellerSupervisorStatus is {}",Utility.getExceptionAsString(e));
			apiResponse = new ApiResponse(500, "Status save failed", "Failed to save data: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
		}
		return ResponseEntity.ok(apiResponse);
	}
	@PostMapping("/getTransactionIdByTraceId")
	public TraceIdResponse getUtmCode(@RequestBody TraceIdRequest traceIdRequest) {
		TraceIdResponse traceIdResponse = locationService.getTransactionIdByTraceId(traceIdRequest);
		return traceIdResponse;
	}
}



