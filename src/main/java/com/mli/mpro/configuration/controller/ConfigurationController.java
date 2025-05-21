package com.mli.mpro.configuration.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.mli.mpro.configuration.UIConfigurationData;
import com.mli.mpro.configuration.models.*;
import com.mli.mpro.configuration.repository.LEProductListRepository;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.location.models.IIBResponsePayload;
import com.mli.mpro.productRestriction.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mli.mpro.auditservice.models.ErrorResponse;
import com.mli.mpro.common.exception.ErrorMessageConfig;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.InputRequest;
import com.mli.mpro.common.models.OutputResponse;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.common.models.ResponseData;
import com.mli.mpro.common.models.UIOutputResponse;
import com.mli.mpro.configuration.repository.FeatureFlagrepository;
import com.mli.mpro.configuration.service.ConfigurationService;
import com.mli.mpro.configuration.service.UIConfigurationService;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.utils.Utility;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

import static com.mli.mpro.productRestriction.util.AppConstants.FIELD_VALIDATION_ERROR_DURING_BAD_REQUEST;


/*This class has all the API to get data from DB through service*/
@RestController
@RequestMapping("/configurationservices")
public class ConfigurationController {
	 @Autowired
		private UIConfigurationService uIConfigurationService;
	 
	 @Autowired
		private FeatureFlagrepository featureFlagrepository;

	@Autowired
	LEProductListRepository leProductListRepository;

    private ConfigurationService configurationService;
    private ErrorMessageConfig errorMessageConfig;
	private static final Logger logger= LoggerFactory.getLogger(ConfigurationController.class);
    @Autowired
    public ConfigurationController(ConfigurationService configurationService, ErrorMessageConfig errorMessageConfig) {
	this.configurationService = configurationService;
	this.errorMessageConfig = errorMessageConfig;
    }

    private List<String> errorMessages;
    private Response response;
    private ConfigurationResponsePayload payload;
    private ResponseData responseData;

    /* To get the configuration using key */
    @RequestMapping(value = "/getByKey/{key}", method = RequestMethod.GET)
    public OutputResponse configurationByKey(@PathVariable String key) throws UserHandledException {
	if (key != null && !StringUtils.isEmpty(key)) {
	    List<Configuration> configurations = configurationService.getConfigurationByKey(key);
	    payload = new ConfigurationResponsePayload(configurations);
	    responseData = new ResponseData(payload);
	    response = new Response(responseData);
	    return new OutputResponse(response);
	} else {
	    response = new Response();
	    errorMessages = new ArrayList<String>();
	    errorMessages.add(errorMessageConfig.getErrorMessages().get("noValidKey"));
	    throw new UserHandledException(response, errorMessages, HttpStatus.BAD_REQUEST);
	}
    }

    /* To get the configuration using type */
    @RequestMapping(value = "/getByType/{type}", method = RequestMethod.GET)
    public OutputResponse configurationByType(@PathVariable String type) throws UserHandledException {
	if (type != null && !StringUtils.isEmpty(type)) {
	    List<Configuration> configurations = configurationService.getConfigurationByType(type);
	    payload = new ConfigurationResponsePayload(configurations);
	    responseData = new ResponseData(payload);
	    response = new Response(responseData);
	    return new OutputResponse(response);
	} else {
	    response = new Response();
	    errorMessages = new ArrayList<String>();
	    errorMessages.add(errorMessageConfig.getErrorMessages().get("noValidValue"));
	    throw new UserHandledException(response, errorMessages, HttpStatus.BAD_REQUEST);
	}
    }

    /* To get all the configurations */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public OutputResponse configurations() {
	List<Configuration> configurations = configurationService.getAllConfiguration();
	payload = new ConfigurationResponsePayload(configurations);
	responseData = new ResponseData(payload);
	response = new Response(responseData);
	return new OutputResponse(response);
    }

	@RequestMapping(value = "/getDataByKey/{key}", method = RequestMethod.GET)
	public OutputResponse multiSelectDataByKey(@PathVariable String key) throws UserHandledException {
		if (key != null && !StringUtils.isEmpty(key)) {
			MultiSelectData multiSelectData = configurationService.getMultiSelectDataByKey(key);
			payload = new ConfigurationResponsePayload();
			payload.setMultiSelectData(multiSelectData);
			responseData = new ResponseData(payload);
			response = new Response(responseData);
			return new OutputResponse(response);
		} else {
			response = new Response();
			errorMessages = new ArrayList<String>();
			errorMessages.add(errorMessageConfig.getErrorMessages().get("noValidData"));
			throw new UserHandledException(response, errorMessages, HttpStatus.BAD_REQUEST);
		}
	}

	//FUL2-11834 check Branch code for Ybl telesales agents
	@PostMapping("/checkIfYblTelesalebranchCode")
	public OutputResponse isYblTelesalebranchCode(@RequestBody InputRequest inputRequest) throws UserHandledException {
		try {
		if (Utility.isValidateYblTelesalebranchCodeRequest(inputRequest)) {
			String branchCode = inputRequest.getRequest().getRequestData().getRequestPayload().getBranchCd();
			Boolean ifYblTelesalebranchCode = configurationService.isYblTelesalesBranchCode(branchCode);
			payload = new ConfigurationResponsePayload();
			payload.setTelesalesCase(ifYblTelesalebranchCode);
			responseData = new ResponseData(payload);
			response = new Response(responseData);
			return new OutputResponse(response);
		} else {
			response = new Response();
			errorMessages = new ArrayList<>();
			errorMessages.add(errorMessageConfig.getErrorMessages().get("noValidData"));
			logger.error("Error occurred for Invalid Branch Code");
			throw new UserHandledException(response, errorMessages, HttpStatus.BAD_REQUEST);
		}
	} catch (Exception ex) {
		logger.error("Exception occurred while calling checkIfYblTelesalebranchCode {}", Utility.getExceptionAsString(ex));
		throw new UserHandledException(Arrays.asList(AppConstants.INTERNAL_SERVER_ERROR),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
	}
	
	/* FUL2-48097_Sustainability-equity-fund
	 * To get the UI configuration using key */
	@RequestMapping(value = "/getuiconfigByKey/{type}/{key}", method = RequestMethod.GET)
	public UIOutputResponse uiConfigurationByTypeKey(@PathVariable String type, @PathVariable String key)
			throws UserHandledException {
		try {
			logger.info("Input request received for product configuration api with type {} and key {}", type, key);
			UIOutputResponse uIOutputResponse = new UIOutputResponse();
			HashMap output = new HashMap();
			if (key != null && !StringUtils.isEmpty(key)) {
				List<UIConfiguration> configurations = uIConfigurationService.getConfigurationByTypeAndKey(type, key);
				if (configurations != null && configurations.size() > 0) {
					Date dateToCheck = new Date();
					for (UIConfiguration configuration : configurations) {
						//FUL2-156787_Nifty Fund Cooling Period Handling for UI
						if (Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled("niftyFund")) && AppConstants.NIFTY_INDEX_FUND_KEY.equalsIgnoreCase(key)) {
							return Utility.isNftySmallCapIndexFundPopUpEnableOrNot(configuration, uIOutputResponse);
						} else if (Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled("smartUltraProtectRider") && AppConstants.SMART_ULTRA_PROTECT_RIDER.contains(key))) {
							return Utility.isRiderEnabled(configuration, uIOutputResponse, key);
						} else if (Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled("wopRiderSWAGELITE")
								&& AppConstants.WOP_PLUS_RIDER.contains(key))) {
							return Utility.isRiderEnabled(configuration, uIOutputResponse, key);
						}
						else {
							if (dateToCheck.compareTo(configuration.getFromDate()) >= 0
									&& dateToCheck.compareTo(configuration.getToDate()) <= 0) {
								logger.info("uiConfigurationByTypeKey for type {} and key {} and value {}", type, key, "Y");
								output.put(key, "Yes");
								uIOutputResponse.setResponse(output);
								return uIOutputResponse;
							}
						}
					}
				} else {
					output.put(key, "No");
					uIOutputResponse.setResponse(output);
					return uIOutputResponse;
				}
			} else {
				ErrorResponse errorResponse = new ErrorResponse();
				response = new Response();
				errorMessages = new ArrayList<>();
				errorMessages.add("Key required");
				errorResponse.setErrorMessages(errorMessages);
				logger.error("Error occurred: Key required");
				uIOutputResponse.setErrorResponse(errorResponse);
				return uIOutputResponse;
			}
			output.put(key, "No");
			uIOutputResponse.setResponse(output);
			return uIOutputResponse;
		} catch (Exception ex) {
			logger.error("exception occurred while fetching configurationDetails {}", Utility.getExceptionAsString(ex));
			errorMessages = new ArrayList<>();
			errorMessages.add(errorMessageConfig.getErrorMessages().get("internalServererror"));
			throw new UserHandledException(errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	// FUL2-83715 To get all the data from feature-flag db
	@GetMapping(value = "/getFeatureFlags")
	public List<FeatureFlag> getFeatureFlags() {
		List<FeatureFlag> featureFlags = new ArrayList<>();
		try {
			featureFlags = featureFlagrepository.findAll();
		} catch (Exception e) {
			logger.error("exception occoured while retrieving the data {}", Utility.getExceptionAsString(e));
		}
		return featureFlags;
	}

	@GetMapping(value = "/getLEProductList")
	public LEProductResponse getLEProductList() {
		LEProductResponse leProductResponse = new LEProductResponse();
		List<LEProductList> leProductLists = new ArrayList<>();
		try {
			leProductLists = leProductListRepository.findAll();
		} catch (Exception ex) {
			logger.error("exception occurred while retrieving the data from getLEProductList {}", Utility.getExceptionAsString(ex));
		}
		leProductResponse.setResult(leProductLists);
		return leProductResponse;
	}

	// find the supervisor details by sp code
	@PostMapping(value="/getSupervisorDetails")
	public SupervisorDetails getSupervisorDetails(@RequestBody SupervisorDetailsRequest supervisorDetailsRequest) {
		SupervisorDetails supervisorDetails = null;
		try {
			supervisorDetails = configurationService.getSupervisorDetails(supervisorDetailsRequest.getSpCode());
		} catch (Exception e) {
			logger.error("exception occoured while retrieving the sp details {}", Utility.getExceptionAsString(e));
		}
		return supervisorDetails;
	}

	// FUL2-83715 To save data in feature-flag db
	// If data already exists ,will update the data
	@PostMapping(value = "/saveFeatureFlag")
	public ResponseEntity<Object> saveFeatureFlag(@RequestBody FeatureFlagRequest featureFlagRequest)
			throws UserHandledException {
		FeatureFlag saveData = null;
		try {
				saveData = configurationService.saveFeatureFlagData(featureFlagRequest);
				if(!ObjectUtils.isEmpty(saveData)) {
				return new ResponseEntity<>(saveData, HttpStatus.OK);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter the valid data.All the feilds are mandatory");
			}
		} catch (Exception e) {
			logger.error("exception occoured while saving/updating the data {}", Utility.getExceptionAsString(e));
			errorMessages = new ArrayList<>();
			errorMessages.add(errorMessageConfig.getErrorMessages().get("badRequest"));
			throw new UserHandledException(errorMessages, HttpStatus.BAD_REQUEST);
		}
		
		 
	}

	@RequestMapping(value = "/getuiconfigByKey/{type}/{productId}/{key}", method = RequestMethod.GET)
	public UIOutputResponse uiConfigurationByTypeKeyAndProductId(@PathVariable String type, @PathVariable String productId, @PathVariable String key)
			throws UserHandledException {
		try {
			logger.info("Input request received for product configuration api with type {}, key {} and productId {}", type, key, productId);
			UIOutputResponse uIOutputResponse = new UIOutputResponse();
			HashMap output = new HashMap();
			if (key != null && !StringUtils.isEmpty(key)) {
				List<UIConfiguration> configurations = uIConfigurationService.getConfigurationByTypeAndKey(type, key);
				if (configurations != null && configurations.size() > 0) {

					for (UIConfiguration configuration : configurations) {
						//FUL2-208757 IRDA Changes
						if(AppConstants.IRDA_CIS_KEY.contains(key)) {
							return Utility.checkDeclarationApplability(key, configuration, productId, uIOutputResponse);
						}else{
							output.put(key, AppConstants.CAMEL_NO);
							uIOutputResponse.setResponse(output);
							return uIOutputResponse;
						}
					}
				} else {
					output.put(key, AppConstants.CAMEL_NO);
					uIOutputResponse.setResponse(output);
					return uIOutputResponse;
				}
			} else {
				ErrorResponse errorResponse = new ErrorResponse();
				response = new Response();
				errorMessages = new ArrayList<>();
				errorMessages.add("Key required");
				errorResponse.setErrorMessages(errorMessages);
				logger.error("Error occurred: Key required");
				uIOutputResponse.setErrorResponse(errorResponse);
				return uIOutputResponse;
			}
			output.put(key, AppConstants.CAMEL_NO);
			uIOutputResponse.setResponse(output);
			return uIOutputResponse;
		} catch (Exception ex) {
			logger.error("exception occurred while fetching configurationDetails {}", Utility.getExceptionAsString(ex));
			errorMessages = new ArrayList<>();
			errorMessages.add(errorMessageConfig.getErrorMessages().get("internalServererror"));
			throw new UserHandledException(errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/getShorterJourneyConfigurations", method=RequestMethod.POST)
	public UIOutputResponse getShorterJourneyConfigurations(@Valid @RequestBody AgentInfo agentInfo, BindingResult result) {

		if (result.hasErrors()) {
			List<String> errorMessages = result.getAllErrors()
					.stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage)
					.collect(Collectors.toList());

			String errorMessage = String.join(", ", errorMessages);
			logger.error(FIELD_VALIDATION_ERROR_DURING_BAD_REQUEST, agentInfo, errorMessage);

			return new UIOutputResponse(new ErrorResponse(new Date(), 400, errorMessages));
		}

		UIOutputResponse output = new UIOutputResponse();
		output.setResponse(uIConfigurationService.getShorterJourneyConfiguration(agentInfo));
		return output;
	}
	
}
