package com.mli.mpro.location.services.impl;


import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.time.Instant;
import java.time.LocalDate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.proposal.models.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.configuration.models.Configuration;
import com.mli.mpro.configuration.service.ConfigurationService;
import com.mli.mpro.location.services.PersistencyModelService;
import com.mli.mpro.location.services.ProposalStreamPushService;
import com.mli.mpro.location.services.RoutePosvService;
import com.mli.mpro.posvbrms.models.PosvBrmsPayload;
import com.mli.mpro.posvbrms.models.PosvBrmsRequest;
import com.mli.mpro.posvbrms.models.PosvBrmsResponse;
import com.mli.mpro.productRestriction.repository.ProposalRepository;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.ApplicationDetails;
import com.mli.mpro.proposal.models.IndustryDetails;
import com.mli.mpro.proposal.models.InputRequest;
import com.mli.mpro.proposal.models.PartyInformation;
import com.mli.mpro.proposal.models.PosvViaBrmsDetails;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.proposal.models.UnderwritingServiceDetails;
import com.mli.mpro.proposal.models.ProductDetails;
import com.mli.mpro.proposal.models.Request;
import com.mli.mpro.proposal.models.RequestData;
import com.mli.mpro.proposal.models.RequestPayload;
import com.mli.mpro.proposal.models.SourcingDetails;
import com.mli.mpro.proposal.models.Address;
import com.mli.mpro.proposal.models.AddressDetails;
import com.mli.mpro.routingposv.models.InputMessageRequest;
import com.mli.mpro.routingposv.models.RoutingInfoData;
import com.mli.mpro.utils.Utility;
import com.mli.mpro.location.models.RuralUrbanDetails;
import com.mli.mpro.location.models.ruralurbanresponsemodels.OutputResponse;
import com.mli.mpro.location.ruralurbanrequestmodels.Header;
import com.mli.mpro.location.ruralurbanrequestmodels.Payload;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

import static com.mli.mpro.productRestriction.util.AppConstants.*;


@Service
public class RoutePosvServiceImpl implements RoutePosvService {

	private static final Logger log = LoggerFactory.getLogger(RoutePosvServiceImpl.class);
	
	@Value("${posvRouting.enable}")
	private boolean enableRoutingPosv;

	@Value("${urlDetails.proposalService}")
	private String getProposalData;

	@Value("${bypass.header.encrypt.value}")
	private String api_client_secret;
	
	@Value("${urlDetails.piPosvUrl}")
	private String piPosvUrl;
	
	@Value("${urlDetails.piPosvXapigwApiId}")
	private String piPosvXapigwApiId;
	
	@Value("${urlDetails.piPosvxapikey}")
	private String piPosvXApiKey; 
	
	@Value("${urlDetails.piCorrelationId}")
	private String piCorrelationId;
	
	@Value("${urlDetails.piPosvAppId}")
	private String piPosvAppId;

	private ProposalRepository proposalRepository;
	private ProposalStreamPushService proposalStreamPushService;
	private ConfigurationService configurationService;
	private PersistencyModelService persistencyModelService;

	@Autowired
	public RoutePosvServiceImpl(ProposalRepository proposalRepository,ProposalStreamPushService proposalStreamPushService,
			ConfigurationService configurationService,PersistencyModelService persistencyModelService) {
		this.proposalRepository = proposalRepository;
		this.proposalStreamPushService = proposalStreamPushService;
		this.configurationService = configurationService;
		this.persistencyModelService = persistencyModelService;
	}

	@Override
	public String executeRoutingProcess(InputMessageRequest inputRequest)  {
		Configuration videoConfig;
		String posvType = AppConstants.STANDARD_POSV;
		String status = "FAILED";
		String reasonForVideoPosv = "";
		String riskyTagPERS = "";
		String riskyTagUrmu = "";
		String productId = "";
		boolean isVideoPosvpApplicable = false;
		ProposalDetails proposalDetails = null;
		RoutingInfoData infoData = new RoutingInfoData();
		try {
			long transactionId = inputRequest.getTransactionId();
			proposalDetails = getProposalData(inputRequest);
			if(proposalDetails != null){
				riskyTagPERS = getPersistancyRiskyTag(proposalDetails);
				riskyTagUrmu = getURMUScoreRiskyTag(proposalDetails);
				productId = proposalDetails.getProductDetails().get(0).getProductInfo().getProductId();
			}
			infoData.setProductId(productId);
			infoData.setRiskyTagPERS(riskyTagPERS);
			infoData.setRiskyTagURMU(riskyTagUrmu);
			
			if(checkForWOPRiderandSWPJL(proposalDetails)) {
			videoConfig = configurationService.getVideoConfigurationByType("VideoConfig");
			Boolean featureFlag=FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLEVIDEOPOSV);
			isVideoPosvpApplicable = checkEligibility(proposalDetails,videoConfig,featureFlag);
			if (enableRoutingPosv && isVideoPosvpApplicable) {
					posvType = getPosvType(infoData, transactionId);
					// FUL2-52686 - Sr. Citizen Video POSV Code Revert
					if (AppConstants.CHANNEL_AXIS.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())){
						posvType = enableVideoPosvForAxis(proposalDetails, posvType,videoConfig,featureFlag);
					}
					/* in the POSV type we have two values segregated by _ value i.e VideoPOSV_URMU model */
					reasonForVideoPosv =  posvType.split("_").length == 2 ?  posvType.split("_")[1] : "";
					posvType = posvType.split("_")[0];
			} 
			}			
		} catch (Exception ex) {
			log.error("Exception while decideToVideoPosv and message is {} ", Utility.getExceptionAsString(ex));
			status = AppConstants.FAILED;

		}
		status = getStatus(posvType);
		callsaveProposalForPosv(posvType, proposalDetails, reasonForVideoPosv);
		return status;
	}

		private boolean checkEligibility(ProposalDetails proposalDetails,Configuration videoConfig,Boolean featureFlag) {
		try {
			if (proposalDetails != null && videoConfig != null) {
				String channel = proposalDetails.getChannelDetails().getChannel();
				String goCode = proposalDetails.getSourcingDetails().getGoCABrokerCode();
				String productId = proposalDetails.getProductDetails().get(0).getProductInfo().getProductId();
				String formType = proposalDetails.getApplicationDetails().getFormType();
				List<String> channels = videoConfig.getChannel();
				List<String> goCodes = videoConfig.getGoCode();
				List<String> products = videoConfig.getProducts();

				Date proposerDateOfBirth1 = proposalDetails.getPartyInformation().get(0).getBasicDetails().getDob();
				LocalDate proposerDateOfBirth = proposerDateOfBirth1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				int age = calculateAge(proposerDateOfBirth);
				
			
			// form c case and isForm_C_Scheme_A_or_B case
			if (!StringUtils.isEmpty(formType) && AppConstants.FORM_TYPE.equalsIgnoreCase(formType)
						|| isFormCSchemeAorBCase(proposalDetails, formType)) {
					return false;
				}

				// channel axis and age >=60 (senior citizen) 
				// FUL2-52686 - as per the story we have done the code revert as we are triggering video POSV
				if(AppConstants.GLIP_ID.equals(productId)) {
					return false;
			}else if ( Boolean.TRUE.equals(featureFlag) && (AppConstants.CHANNEL_AXIS.equalsIgnoreCase(channel) || THANOS_CHANNEL.equalsIgnoreCase(channel))) {
						return true;
				}
			else if ((AppConstants.CHANNEL_AXIS.equalsIgnoreCase(channel) || THANOS_CHANNEL.equalsIgnoreCase(channel)) && age >= 60) {
                        return true;
					}
				if ((channels.contains(channel) || goCodes.contains(goCode)) && 
						((products.contains(AppConstants.ALL)) || products.contains(productId))) {
					return true;
				} else {
					log.info("Not eligible for channel and product {} {}", channel,productId);
				}
			}
		} catch (Exception ex) {
			log.error("Exception while checkEligibility for videoPOSV and message is {} ", Utility.getExceptionAsString(ex));
		}
		return false;
	}

	private String getPosvType(RoutingInfoData inputInfo, long transactionId) {
		String posvType = AppConstants.STANDARD_POSV;
		String urmuRiskValue = inputInfo.getRiskyTagURMU();
		String persistencyRiskValue = inputInfo.getRiskyTagPERS();
		String productId =inputInfo.getProductId();
		/* FUL2-25833 Dolphin changes */
		String reasonForVideoPosv = "";
		boolean isVideoPosvDueToUrmuModel = false;
		boolean isVideoPosvDueToPersistence = false;
		/* FUL2-25833 Dolphin changes */
		log.info("Input request to check for VideoPosv for transacionId {} {}", transactionId,inputInfo);
		try {
			/*FUL2-69853 Logic changes for POSV type trigger for MEDIUM_RISK and ALARMING_RISK to standard POSV from Video POSV*/
			if (AppConstants.TermProductSet.contains(productId) && ( AppConstants.HIGH_RISK.equalsIgnoreCase(urmuRiskValue))) {
				posvType = AppConstants.VIDEO_POSV;
				reasonForVideoPosv = AppConstants.URMU_MODEL;
				isVideoPosvDueToUrmuModel = true;
			} else if (!AppConstants.CIP_PRODUCT_ID.equalsIgnoreCase(productId) && AppConstants.HIGH_RISK.equalsIgnoreCase(urmuRiskValue)) {
				isVideoPosvDueToUrmuModel = true;
				// this will help in find the reason for video posv
				reasonForVideoPosv = AppConstants.URMU_MODEL;
				posvType = AppConstants.VIDEO_POSV;
			} else {
				posvType = AppConstants.STANDARD_POSV;
			}

			if ((AppConstants.PERS_ALARM_RISK.equalsIgnoreCase(persistencyRiskValue) || AppConstants.VERY_HIGH_RISK.equalsIgnoreCase(persistencyRiskValue)
					||	AppConstants.PERS_HIGH_RISK.equalsIgnoreCase(persistencyRiskValue))) {
				reasonForVideoPosv = AppConstants.PERSISTENCY_MODEL;
				isVideoPosvDueToPersistence = true;
				posvType = AppConstants.VIDEO_POSV;
			}

			if (isVideoPosvDueToUrmuModel && isVideoPosvDueToPersistence) {
				reasonForVideoPosv = AppConstants.BOTH_MODEL;
			}

		} catch (Exception ex) {
			log.error("Exception while conditions check for VideoPosv and message is {} ",
					Utility.getExceptionAsString(ex));
		}

		return posvType + "_" + reasonForVideoPosv;
	}

	String getStatus(String posvType) {
		String status = AppConstants.FAILED;
		if (!StringUtils.isEmpty(posvType)) {
			status = AppConstants.SUCCESS;
		}
		log.info("final status for posvType is {}", status);
		return status;
	}



	private boolean checkForWOPRiderandSWPJL(ProposalDetails proposalDetails) {
		boolean status = true;
		String formType = proposalDetails.getApplicationDetails().getFormType();
		boolean isWOPRider = isWOPRiderCase(proposalDetails.getProductDetails().get(0).getProductInfo()
				.getRiderDetails());
		try {
		if(Utility.isProductSWPJL(proposalDetails) || (AppConstants.DEPENDENT.equalsIgnoreCase(formType) && isWOPRider)) {
			status = false;
		}
		}catch (Exception ex) {
			log.error("Exception while checkForWOPRiderandSWPJL {} ", Utility.getExceptionAsString(ex));
		}
		return status;
	}
	
	private  boolean isWOPRiderCase(List<com.mli.mpro.proposal.models.RiderDetails> riderDetailsList){
		try {
			for (com.mli.mpro.proposal.models.RiderDetails riderDetails : riderDetailsList) {
				if ((AppConstants.WOP.equalsIgnoreCase(riderDetails.getRiderInfo()) || AppConstants.AXIS_WOP.equalsIgnoreCase(riderDetails.getRiderInfo())) && riderDetails.isRiderRequired()) {
					return true;
				}
			}
		}catch (Exception e){
			log.error("error occurred while checking wop rider case ");
		}
		return false;
	}
	
	private void callsaveProposalForPosv(String posvType, ProposalDetails proposalDetails, String reasonForVideoPosv) {
		InputRequest inputRequest = new InputRequest();
		Request request = new Request();
		RequestData requestData = new RequestData();
		RequestPayload requestPayload = new RequestPayload();
		try {
		/* FUL2-25833 dolphin push changes */
		if(Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLE_POSV_VIA_BRMS))) {
			//FUL2-190847 Talic Green
		   PosvViaBrmsDetails posvViaBrmsDetails = new PosvViaBrmsDetails(AppConstants.BLANK,AppConstants.BLANK,AppConstants.N,AppConstants.FAIL);
		   proposalDetails.setPosvViaBrmsDetails(posvViaBrmsDetails);
		}
		if(Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLE_POSV_VIA_BRMS))) {
			//FUL2-190847 Talic Green
		    PosvViaBrmsDetails posvViaBrmsDetails = new PosvViaBrmsDetails(AppConstants.BLANK,AppConstants.BLANK,AppConstants.N,AppConstants.NA);
	        proposalDetails.setPosvViaBrmsDetails(posvViaBrmsDetails);
	    }
		proposalDetails.getAdditionalFlags().setReasonForVideoPosv(reasonForVideoPosv);
		/* FUL2-25833 dolphin push changes */
		proposalDetails.getApplicationDetails().setPosvType(posvType);
		proposalDetails.getApplicationDetails().setBackendHandlerType(AppConstants.HANDLER_TYPE);
		requestPayload.setProposalDetails(proposalDetails);
		requestData.setRequestPayload(requestPayload);
		request.setRequestData(requestData);
		inputRequest.setRequest(request);
		boolean status = false;
		if (FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.KINESIS_FOR_ALL) && FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.KINESIS_FOR_PROPOSAL)) {
				status = proposalStreamPushService.produceToProposalStream(inputRequest);
		}else{
				status = proposalStreamPushService.produceToProposalQueue(inputRequest);
		}
		log.info("called proposal stream from location MS is status {}",status);
		}catch (Exception ex) {
			log.error("Exception while callsaveProposalForPosv {} ", Utility.getExceptionAsString(ex));
		}
		
	}


	 public ProposalDetails getProposalData(InputMessageRequest inputMessageRequest) throws UserHandledException {
		InputRequest inputRequest = new InputRequest();
		String agentId = inputMessageRequest.getAgentId();
		long transactionId = inputMessageRequest.getTransactionId();

		Request request = new Request();
		RequestData requestData = new RequestData();
		RequestPayload requestPayload = new RequestPayload();

		ProposalDetails proposalDetails = new ProposalDetails();
        ApplicationDetails applicationDetails = new ApplicationDetails();
        SourcingDetails sourcingDetails = new SourcingDetails();

        sourcingDetails.setAgentId(agentId);
        applicationDetails.setPolicyNumber(inputMessageRequest.getPolicyNo());
        proposalDetails.setApplicationDetails(applicationDetails);
        proposalDetails.setSourcingDetails(sourcingDetails);
		proposalDetails.setTransactionId(transactionId);

		requestPayload.setProposalDetails(proposalDetails);
		requestData.setRequestPayload(requestPayload);
		request.setRequestData(requestData);
		inputRequest.setRequest(request);
		log.info("For  transactionId {} calling get proposal data for execute routing", transactionId);
		log.info("Loading getProposal api from configuration file {}", getProposalData);
		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders httpHeaders = Utility.setAPISecretInHeaders(api_client_secret);
			HttpEntity<InputRequest> httpEntityRequest = new HttpEntity<>(inputRequest, httpHeaders);
			com.mli.mpro.common.models.OutputResponse response = restTemplate.postForObject(getProposalData,
					httpEntityRequest, com.mli.mpro.common.models.OutputResponse.class);
			proposalDetails = response.getResponse().getResponseData().getResponsePayload().getProposalDetails();
		} catch (Exception ex) {
			List<String> errorMsg = new ArrayList<>();
			errorMsg.add(ex.getMessage());
			log.error("Exception while calling getProposal Data Api and message is: {}", Utility.getExceptionAsString(ex));
			throw new UserHandledException(errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return proposalDetails;
	}

		public static int calculateAge(LocalDate proposerDateOfBirth ) {

			LocalDate curDate = LocalDate.now();
			// calculates the amount of time between two dates and returns the years
			if ((proposerDateOfBirth  != null) && (curDate != null)) {

				return Period.between(proposerDateOfBirth , curDate).getYears();
			} else {
				return 0;
			}
		}

		private String getPersistancyRiskyTag(ProposalDetails proposalDetails) {
			String riskyTagPERS = "";
			if (!StringUtils.isEmpty(proposalDetails.getUnderwritingServiceDetails().getPersistencyModelDetails())) {
				riskyTagPERS = proposalDetails.getUnderwritingServiceDetails().getPersistencyModelDetails()
						.getRiskyTagPers();
			}
			return riskyTagPERS;
		}

		private String getURMUScoreRiskyTag(ProposalDetails proposalDetails) {
			String riskyTagUrmu = "";
			if (!StringUtils.isEmpty(proposalDetails.getUnderwritingServiceDetails().getRiskScoreDetails())) {
				riskyTagUrmu = proposalDetails.getUnderwritingServiceDetails().getRiskScoreDetails().getRiskyTagURMU();
			}
			return riskyTagUrmu;
		}


		// FUL2-45078 checking condition for isForm_C_Scheme_A_or_B case
		private boolean isFormCSchemeAorBCase(ProposalDetails proposalDetails, String formType) {

			if (AppConstants.OBJECTIVE_OF_INSURANCE
					.contains(proposalDetails.getProductDetails().get(0).getObjectiveOfInsurance())
					&& AppConstants.FORM_TYPE_SELF.equalsIgnoreCase(formType)) {

				return true;
			}
			return false;
		}


	
	// FUL2-52686 - Sr. Citizen Video POSV Code Revert
	// FUL2-98649 - VideoPosv Rule Updation for Axis channel
	public String enableVideoPosvForAxis(ProposalDetails proposalDetails, String posvType, Configuration videoConfig,Boolean featureFlag) {
		try {
			if (proposalDetails != null ) {
				long transactionId = proposalDetails.getTransactionId();
				String channel = proposalDetails.getChannelDetails().getChannel();
				Date proposerDob = proposalDetails.getPartyInformation().get(0).getBasicDetails().getDob();
				LocalDate convertedProposerDob = proposerDob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				int proposerAge = calculateAge(convertedProposerDob);
				log.info("checking enable VideoPosv ForAxis for transacionId {} channel {}, proposer age {}",
						transactionId, channel, proposerAge);
					if (proposerAge >= 60){
					posvType = AppConstants.VIDEO_POSV;
				}  else if (Boolean.TRUE.equals(featureFlag) && Objects.nonNull(proposalDetails.getProductDetails()) && productIdCheck(proposalDetails.getProductDetails())) {
					posvType = AppConstants.STANDARD_POSV;
				}  else if (Boolean.TRUE.equals(featureFlag) && Objects.nonNull(proposalDetails.getProductDetails()) && Objects.nonNull(proposalDetails.getProductDetails().get(0).getProductInfo())
							&& AppConstants.productType_List.stream().anyMatch(a -> a.equalsIgnoreCase(proposalDetails.getProductDetails().get(0).getProductType()))
							&& !CollectionUtils.isEmpty(videoConfig.getSolId()) && !CollectionUtils.isEmpty(videoConfig.getSchemeCode())) {
						posvType = checkRulesForVPosv(proposalDetails,videoConfig);
						log.info("VPOSV rules checked in Axis for transactionId {} posvType is {}",transactionId,posvType);
				}
		}
		}
		catch (Exception ex) {
			log.error("Exception while enable VideoPosv For Axis and message is {} ",
					Utility.getExceptionAsString(ex));
		}
		return posvType;

		}

	private String checkRulesForVPosv(ProposalDetails proposalDetails, Configuration videoConfig) {
		String posvType = AppConstants.STANDARD_POSV;
		 if(checkSchemeCode(proposalDetails.getBancaDetails(),videoConfig.getSchemeCode())
				&& (checkSolId(proposalDetails.getSourcingDetails(),videoConfig.getSolId()) ||
				checkCustomerVintage(proposalDetails.getBancaDetails()) || checkPolicySource(proposalDetails.getUnderwritingServiceDetails()))){
			posvType = AppConstants.VIDEO_POSV;
		}
		return posvType;
	}

	private boolean checkPolicySource(UnderwritingServiceDetails underwritingServiceDetails) {
		 return Objects.nonNull(underwritingServiceDetails)
				 && Objects.nonNull(underwritingServiceDetails.getRuralUrbanDetails())
				 && AppConstants.RURAL.equalsIgnoreCase(underwritingServiceDetails.getRuralUrbanDetails().getTagging());
	}
	private boolean checkCustomerVintage(com.mli.mpro.axis.models.BancaDetails bancaDetails) {
		 if(Objects.nonNull(bancaDetails) && !StringUtils.isEmpty(bancaDetails.getBankAccountOpeningDate())){
			 String openingTime = bancaDetails.getBankAccountOpeningDate();
			 LocalDate date1 = Utility.stringToDateFormatter(openingTime,AppConstants.YYYY_MM_DD_HH_MM_SSS_SSSS);
			 long diffInYear = Period.between(date1,LocalDate.now()).getYears();
			 if (diffInYear < 1){
				 return true;
			 }
		 }
		 return false;
	}

	private boolean checkSolId(SourcingDetails sourcingDetails, List<String> solId) {
		 return Objects.nonNull(sourcingDetails) && Objects.nonNull(sourcingDetails.getSpecifiedPersonDetails())
				 && !StringUtils.isEmpty(sourcingDetails.getSpecifiedPersonDetails().getSpGoCABrokerCode())
				 && solId.stream().anyMatch(a -> a.equalsIgnoreCase(sourcingDetails.getSpecifiedPersonDetails().getSpGoCABrokerCode()));
	}

	private boolean checkSchemeCode(com.mli.mpro.axis.models.BancaDetails bancaDetails, List<String> schemeCode) {
		 return Objects.nonNull(bancaDetails) && !StringUtils.isEmpty(bancaDetails.getCustomerClassification())
				 && schemeCode.stream().anyMatch(a -> a.equalsIgnoreCase(bancaDetails.getCustomerClassification()));
	}

	private boolean productIdCheck(List<ProductDetails> productDetails) {
		 return !CollectionUtils.isEmpty(productDetails) && Objects.nonNull(productDetails.get(0).getProductInfo())
				 && AppConstants.productList_VPosv.stream().anyMatch(a -> a.equalsIgnoreCase(productDetails.get(0).getProductInfo().getProductId()));
	 }

    @Override
    public String callPiPosvApi(InputMessageRequest inputRequest) throws UserHandledException {
        String status = null;
        ProposalDetails proposalDetails = null;
        PosvBrmsRequest posvBrmsRequest = new PosvBrmsRequest();
        com.mli.mpro.posvbrms.models.Request request = new com.mli.mpro.posvbrms.models.Request();
        PosvBrmsResponse posvBrmsResponse = new PosvBrmsResponse();
        
        try {
            proposalDetails = getProposalData(inputRequest);
            request.setHeader(getHeaderForPiPosv());
            request.setPayload(getPayloadForPiPosv(proposalDetails));
            posvBrmsRequest.setRequest(request);
            RestTemplate restTemplate = new RestTemplate();
            posvBrmsResponse = restTemplate.postForObject(piPosvUrl, getHttpEntityForPiPosv(posvBrmsRequest), PosvBrmsResponse.class);
            log.info("POSV BRMS API was called for transactionId {} with request {} and response {}",proposalDetails.getTransactionId(),Utility.getJsonRequest(posvBrmsRequest), Utility.getJsonRequest(posvBrmsResponse));
            String posvType = "";
            
            if(Objects.nonNull(posvBrmsResponse) 
                    && Objects.nonNull(posvBrmsResponse.getResponse().getPayload())
                    && AppConstants.SUCCESS_RESPONSE.equals(posvBrmsResponse.getResponse().getMsgInfo().getMsgCode())){
                String output1 = posvBrmsResponse.getResponse().getPayload().getOutput1();
                String output2 = posvBrmsResponse.getResponse().getPayload().getOutput2();
                           //FUL2-190847 Talic Green
				String output3 = StringUtils.hasLength(posvBrmsResponse.getResponse().getPayload().getOutput3())
						          ? posvBrmsResponse.getResponse().getPayload().getOutput3() : AppConstants.N;
				settingInsuredPosvType(proposalDetails, posvBrmsResponse);
                if(Objects.nonNull(output1)
                        && AppConstants.SPOSV.equalsIgnoreCase(output1)){
                    posvType = AppConstants.STANDARD_POSV;
                    status = AppConstants.SUCCESS;
                    setSuccessTagsForPiPosv(proposalDetails, output1, output2, output3);
                    callsaveProposalForPiPosv(posvType, proposalDetails, AppConstants.BLANK);
                }
                else if(Objects.nonNull(output1) && AppConstants.HPOSV.equalsIgnoreCase(output1)) {
                    posvType = AppConstants.VIDEO_POSV;
                    status = AppConstants.SUCCESS;
                    setSuccessTagsForPiPosv(proposalDetails, output1, output2, output3);
                    callsaveProposalForPiPosv(posvType, proposalDetails, AppConstants.BLANK);
                } else if(Objects.nonNull(output1) && AppConstants.IVC.equalsIgnoreCase(output1)) {
					posvType = AppConstants.IVC;
					status = AppConstants.SUCCESS;
					setSuccessTagsForPiPosv(proposalDetails, output1, output2, output3);
					callsaveProposalForPiPosv(posvType, proposalDetails, AppConstants.BLANK);
				} else if (Objects.nonNull(output1) && AppConstants.OTPONLY_POSVTYPE.equalsIgnoreCase(output1)) {
					posvType = AppConstants.OTPONLY_POSVTYPE;
					status = AppConstants.SUCCESS;
					setSuccessTagsForPiPosv(proposalDetails, output1, output2, output3);
					callsaveProposalForPiPosv(posvType, proposalDetails, AppConstants.BLANK);
				}
                else {
                    log.info("Output1 is not SPOSV or HPOSV for transactionId {}, {} going with current flow", proposalDetails.getTransactionId(), output1);
                    status = executeRoutingProcess(inputRequest);
                }
            }          
        }catch (Exception ex) {
            log.error("Exception while calling posv via brms API and message is {} Continuing with current flow", Utility.getExceptionAsString(ex)); 
            throw new UserHandledException();
        }   
        return status; 
    }

	private void settingInsuredPosvType(ProposalDetails proposalDetails, PosvBrmsResponse posvBrmsResponse) {
		if (StringUtils.hasText(posvBrmsResponse.getResponse().getPayload().getOutput4())) {
			String insuredPosvType = posvBrmsResponse.getResponse().getPayload().getOutput4();
			proposalDetails.getAdditionalFlags().setInsuredPosvType(insuredPosvType);
		}
	}

    private void setSuccessTagsForPiPosv(ProposalDetails proposalDetails, String output1, String output2 , String output3) {
        //FUL2-190847 Talic Green
		PosvViaBrmsDetails posvViaBRMSDetails = new PosvViaBrmsDetails(output1, output2, output3, AppConstants.PASS);
        proposalDetails.setPosvViaBrmsDetails(posvViaBRMSDetails);
    }

    private Object getHttpEntityForPiPosv(PosvBrmsRequest posvBrmsRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("x-apigw-api-id", piPosvXapigwApiId);
        httpHeaders.add("x-api-key", piPosvXApiKey);
        httpHeaders.add("correlationid", piCorrelationId);
        httpHeaders.add("appid",piPosvAppId);
        return new HttpEntity<>(posvBrmsRequest, httpHeaders);
    }

    private PosvBrmsPayload getPayloadForPiPosv(ProposalDetails proposalDetails) {
        PosvBrmsPayload posvBrmsPayload = new PosvBrmsPayload();
        posvBrmsPayload.setMortalityAiResponse(getRiskyTagUrmuForPiPosv(proposalDetails));
        posvBrmsPayload.setPersistencyAiResponse(getRiskyTagPersForPiPosv(proposalDetails));
        posvBrmsPayload.setProposerIncome(getAnnualIncomeForProposer(proposalDetails));
        posvBrmsPayload.setProposerEducation(getProposerEducation(proposalDetails));
        posvBrmsPayload.setProposerOccupation(getProposerOccupation(proposalDetails));
        posvBrmsPayload.setCxAiResponse(AppConstants.ZERO); // Default value to be set as per interface sheet
        posvBrmsPayload.setPhysicalJourney(getPhysicalJourneyFlag(proposalDetails));
        posvBrmsPayload.setFormType(getFormType(proposalDetails));
        posvBrmsPayload.setChannel(proposalDetails.getChannelDetails().getChannel().toUpperCase());
        posvBrmsPayload.setIndustry(getIndustryType(proposalDetails));
        posvBrmsPayload.setProposerAge(getProposerAgeForPiPosv(proposalDetails));
        posvBrmsPayload.setUrmuResponse(getUrmuResponse(proposalDetails));
        posvBrmsPayload.setCustomerClassificationCode(getCustometClassificationCodeforPiPosv(proposalDetails));
        posvBrmsPayload.setBankingSince(getDaysForBankingSince(proposalDetails));
        posvBrmsPayload.setBranchCode(getGoCaBrokerCode(proposalDetails));
        posvBrmsPayload.setSolId(getSolId(proposalDetails));
        posvBrmsPayload.setSellerPersistency(getSellerPersitency(proposalDetails));
        //As discussed with PO - setting plancode in productType
        posvBrmsPayload.setProductType(getPlanCode(proposalDetails));
        posvBrmsPayload.setAfyp(getAfypForPiPosv(proposalDetails));
        posvBrmsPayload.setPpt(setPptForPiPosv(proposalDetails));
        posvBrmsPayload.setProposerNationality(getProposerNationality(proposalDetails));
        posvBrmsPayload.setMortalityAiScore(setUrmuScore(proposalDetails));
        posvBrmsPayload.setPersistencyAiScore(getPersistencyScoreForPiPosv(proposalDetails));
        posvBrmsPayload.setNisSourced(getNisSourcedValue(proposalDetails));
        posvBrmsPayload.setChannelCat(getChannelCat(proposalDetails));
        posvBrmsPayload.setSourcingSystem(getSourcingSystemValue(proposalDetails));
        posvBrmsPayload.setSellerCode(getSellerCode(proposalDetails));
        posvBrmsPayload.setSourcingType(getSourcingTypeForPiPosv(proposalDetails));
        posvBrmsPayload.setChannelDefence(getChannelDefeance(proposalDetails));
        posvBrmsPayload.setNpsIndicator(getNpsIndicator(proposalDetails));
        posvBrmsPayload.setTelesales(getTelesalesForPiPosv(proposalDetails));
        setDefaultValuesForOtherTags(posvBrmsPayload);
        posvBrmsPayload.setTag1(getTag1(proposalDetails));
        posvBrmsPayload.setTag2(getTag2(proposalDetails));
        posvBrmsPayload.setTag3(getTag3(proposalDetails));
        posvBrmsPayload.setTag4(getTag4(proposalDetails));
		addSeniorCitizenField(posvBrmsPayload, proposalDetails);
		if(StringUtils.hasLength(proposalDetails.getChannelDetails().getChannel()) && proposalDetails.getChannelDetails().getChannel().equalsIgnoreCase(CHANNEL_AGENCY)) {
			posvBrmsPayload.setTag5(getTag5(proposalDetails));
			posvBrmsPayload.setTag6(getTag6(proposalDetails));
		}
        return posvBrmsPayload;
    }

	private void addSeniorCitizenField(PosvBrmsPayload posvBrmsPayload, ProposalDetails proposalDetails) {
		try {
			if (StringUtils.hasText(proposalDetails.getAdditionalFlags().getIsInsurerMajor())
					&& AppConstants.YES.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getIsInsurerMajor())) {
				int age = 0;
				Date insuredDob = null;
				insuredDob = proposalDetails.getPartyInformation().get(1).getBasicDetails().getDob();

				ZoneId defaultZoneId = ZoneId.of(AppConstants.APP_TIMEZONE);
				Instant insuredDobInstant = insuredDob.toInstant();
				LocalDate insuredDobInstantLocalDate = insuredDobInstant.atZone(defaultZoneId).toLocalDate();
				age = Utility.calculateAgeForPiPosv(insuredDobInstantLocalDate, LocalDate.now());
				String insuredAge = Integer.toString(age);
				posvBrmsPayload.setInsuredAge(insuredAge);
			} else {
				posvBrmsPayload.setInsuredAge(AppConstants.ZERO);
			}


		} catch (Exception e) {
			log.error("Error while setting senior citizen field in posv payload. Error is {}", Utility.getExceptionAsString(e));
		}
	}

    private String getTelesalesForPiPosv(ProposalDetails proposalDetails) {
       if((AppConstants.AXIS_TELESALES_REQUEST.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getRequestSource())
               && AppConstants.CHANNEL_AXIS.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())
               || proposalDetails.getAdditionalFlags().isYblTelesalesCase())) {
           return AppConstants.Y;
       }
       else return AppConstants.N;      
    }

    private String getNpsIndicator(ProposalDetails proposalDetails) {
        if(Objects.nonNull(proposalDetails.getPartyInformation())
                && Objects.nonNull(proposalDetails.getPartyInformation().get(0).getBasicDetails())
                && Objects.nonNull(proposalDetails.getPartyInformation().get(0).getBasicDetails().getIsNPSCustomer())
                && AppConstants.YES.equalsIgnoreCase(proposalDetails.getPartyInformation().get(0).getBasicDetails().getIsNPSCustomer())) {
            return AppConstants.Y;
        }
        else if(Objects.nonNull(proposalDetails.getPartyInformation())
                && Objects.nonNull(proposalDetails.getPartyInformation().get(0).getBasicDetails())
                && Objects.nonNull(proposalDetails.getPartyInformation().get(0).getBasicDetails().getIsNPSCustomer())
                && AppConstants.NO.equalsIgnoreCase(proposalDetails.getPartyInformation().get(0).getBasicDetails().getIsNPSCustomer())) {
            return AppConstants.N;
        }
        return AppConstants.NA;
    }

    private String getChannelDefeance(ProposalDetails proposalDetails) {
        if(Objects.nonNull(proposalDetails.getAdditionalFlags().getDefenceChannel())) {
            if(AppConstants.YES.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getDefenceChannel())) {
                return AppConstants.Y;
            }
            else if(AppConstants.NO.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getDefenceChannel())) {
                return AppConstants.N;
            }
        }
        return AppConstants.NA;
    }

    private String getSourcingTypeForPiPosv(ProposalDetails proposalDetails) {
        if (Objects.nonNull(proposalDetails.getUnderwritingServiceDetails())
                && Objects.nonNull(proposalDetails.getUnderwritingServiceDetails().getRuralUrbanDetails())
                && Objects
                        .nonNull(proposalDetails.getUnderwritingServiceDetails().getRuralUrbanDetails().getTagging())) {
            return proposalDetails.getUnderwritingServiceDetails().getRuralUrbanDetails().getTagging().toUpperCase();
        }
        return AppConstants.NA;
    }

    private String getProposerOccupation(ProposalDetails proposalDetails) {
        try {
            String formType = proposalDetails.getApplicationDetails().getFormType();
            String schemeType = proposalDetails.getApplicationDetails().getSchemeType();
            if (AppConstants.FORM3.equalsIgnoreCase(formType) && AppConstants.SCHEME_A.equalsIgnoreCase(schemeType)) {
                if (Objects.nonNull(proposalDetails.getPartyInformation())
                        && Objects.nonNull(proposalDetails.getPartyInformation().get(1))
                        && Objects.nonNull(proposalDetails.getPartyInformation().get(1).getBasicDetails())
                        && Objects
                                .nonNull(proposalDetails.getPartyInformation().get(1).getBasicDetails()
                                        .getOccupation())) {
                    return proposalDetails.getPartyInformation().get(1).getBasicDetails().getOccupation().toUpperCase();
                }
            } else {
                if (Objects.nonNull(proposalDetails.getPartyInformation())
                        && Objects.nonNull(proposalDetails.getPartyInformation().get(0))
                        && Objects.nonNull(proposalDetails.getPartyInformation().get(0).getBasicDetails())
                        && Objects
                                .nonNull(proposalDetails.getPartyInformation().get(0).getBasicDetails()
                                        .getOccupation())) {
                    return proposalDetails.getPartyInformation().get(0).getBasicDetails().getOccupation().toUpperCase();
                }
            } 
        } catch (Exception e) {
            log.error("Error occured while setting proposer education for transactionId {}",
                    proposalDetails.getTransactionId());
        }
        return AppConstants.NA;
    }

    private String getProposerEducation(ProposalDetails proposalDetails) {
        try {
            String formType = proposalDetails.getApplicationDetails().getFormType();
            String schemeType = proposalDetails.getApplicationDetails().getSchemeType();
            if (AppConstants.FORM3.equalsIgnoreCase(formType) && AppConstants.SCHEME_A.equalsIgnoreCase(schemeType)) {
                if (Objects.nonNull(proposalDetails.getPartyInformation())
                        && Objects.nonNull(proposalDetails.getPartyInformation().get(1).getBasicDetails())
                        && Objects.nonNull(
                                proposalDetails.getPartyInformation().get(1).getBasicDetails().getEducation())) {
                    return proposalDetails.getPartyInformation().get(1).getBasicDetails().getEducation().toUpperCase();
                }
            } else {
                if (Objects.nonNull(proposalDetails.getPartyInformation())
                        && Objects.nonNull(proposalDetails.getPartyInformation().get(0).getBasicDetails())
                        && Objects.nonNull(
                                proposalDetails.getPartyInformation().get(0).getBasicDetails().getEducation())) {
                    return proposalDetails.getPartyInformation().get(0).getBasicDetails().getEducation().toUpperCase();
                }
            } 
        } catch (Exception e) {
            log.error("Error occured while setting proposer education for transactionId {}",
                    proposalDetails.getTransactionId());
        }
        return AppConstants.NA;
    }

    private String getAnnualIncomeForProposer(ProposalDetails proposalDetails) {
        try {
            String formType = proposalDetails.getApplicationDetails().getFormType();
            String schemeType = proposalDetails.getApplicationDetails().getSchemeType();
            // FUL2-155323 Taking insured income for form3 schemeA case
            if (AppConstants.FORM3.equalsIgnoreCase(formType) && AppConstants.SCHEME_A.equalsIgnoreCase(schemeType)) {
                if (Objects.nonNull(proposalDetails.getPartyInformation())
                        && Objects.nonNull(proposalDetails.getPartyInformation().get(1).getBasicDetails())
                        && Objects.nonNull(
                                proposalDetails.getPartyInformation().get(1).getBasicDetails().getAnnualIncome())) {
                    return proposalDetails.getPartyInformation().get(1).getBasicDetails().getAnnualIncome();
                }
            } else {
                if (Objects.nonNull(proposalDetails.getPartyInformation())
                        && Objects.nonNull(proposalDetails.getPartyInformation().get(0).getBasicDetails())
                        && Objects.nonNull(
                                proposalDetails.getPartyInformation().get(0).getBasicDetails().getAnnualIncome())) {
                    return proposalDetails.getPartyInformation().get(0).getBasicDetails().getAnnualIncome();
                }
            }
        } catch (Exception e) {
            log.error("Error occured while setting annual income for transactionId {}",
                    proposalDetails.getTransactionId());
        }
        return AppConstants.ZERO;
    }
    
    private void setDefaultValuesForOtherTags(PosvBrmsPayload posvBrmsPayload) {
        posvBrmsPayload.setTag1(AppConstants.NA);
        posvBrmsPayload.setTag2(AppConstants.NA);
        posvBrmsPayload.setTag3(AppConstants.NA);
        posvBrmsPayload.setTag4(AppConstants.NA);
        posvBrmsPayload.setTag5(AppConstants.NA);
        posvBrmsPayload.setTag6(AppConstants.NA);
        posvBrmsPayload.setTag7(AppConstants.NA);
        posvBrmsPayload.setTag8(AppConstants.NA);
        posvBrmsPayload.setTag9(AppConstants.NA);
        posvBrmsPayload.setTag10(AppConstants.NA);
    }

    private String getSellerCode(ProposalDetails proposalDetails) {
        boolean isAxisCase = Objects.nonNull(proposalDetails.getChannelDetails()) && AppConstants.CHANNEL_AXIS.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel());
        if(isAxisCase && Objects.nonNull(proposalDetails.getSourcingDetails().getSpecifiedPersonDetails())
                && Objects.nonNull(proposalDetails.getSourcingDetails().getSpecifiedPersonDetails().getSpCode())) {
            return proposalDetails.getSourcingDetails().getSpecifiedPersonDetails().getSpCode().toUpperCase();
        }
        if(Objects.nonNull(proposalDetails.getSourcingDetails().getAgentCode())) {
            return proposalDetails.getSourcingDetails().getAgentCode().toUpperCase();
        }
        return AppConstants.NA;
    }

    private String getSourcingSystemValue(ProposalDetails proposalDetails) {
        boolean isAxisCase = Objects.nonNull(proposalDetails.getChannelDetails()) && AppConstants.CHANNEL_AXIS.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel());
        
        try {
            if (isAxisCase && Objects.nonNull(proposalDetails.getAdditionalFlags())
                    && Objects.nonNull(proposalDetails.getAdditionalFlags().getJourneyType())
                    && AppConstants.J3_JOURNEY.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getJourneyType())) {
                       return AppConstants.J3;
            }
            if(isAxisCase && Objects.nonNull(proposalDetails.getAdditionalFlags())
                    && Objects.nonNull(proposalDetails.getAdditionalFlags().getRequestSource())
                    && AppConstants.THANOS_1.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getRequestSource())){
                return AppConstants.J1;
            }
            if(isAxisCase && Objects.nonNull(proposalDetails.getAdditionalFlags())
                    && Objects.nonNull(proposalDetails.getAdditionalFlags().getRequestSource())
                    && AppConstants.THANOS_2.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getRequestSource())){
                return AppConstants.J2;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AppConstants.NA;
    }

    private String getChannelCat(ProposalDetails proposalDetails) {
        if(Objects.nonNull(proposalDetails.getSourcingDetails())
                && Objects.nonNull(proposalDetails.getSourcingDetails().getGoCABrokerCode())) {
            if(proposalDetails.getSourcingDetails().getGoCABrokerCode().startsWith(AppConstants.E)) {
                return AppConstants.Y;
            }
            else return AppConstants.N;
        }
        return AppConstants.NA;
    }

    private String getDaysForBankingSince(ProposalDetails proposalDetails) {
        
        if (Objects.nonNull(proposalDetails.getBank().getBankDetails())) {
            Date accountOpeningDate = proposalDetails.getBank().getBankDetails().get(0).getBankAccOpeningDate();
            if (Objects.nonNull(accountOpeningDate)) {
                Date currentDate = new Date();
                long differenceInMillis = Math.abs(currentDate.getTime() - accountOpeningDate.getTime());
                long daysBetween = TimeUnit.DAYS.convert(differenceInMillis, TimeUnit.MILLISECONDS);
                return String.valueOf(daysBetween);
            }
            return AppConstants.ZERO;
        }
        return AppConstants.ZERO;
    }

    private String getPlanCode(ProposalDetails proposalDetails) {
        if (proposalDetails == null
                || proposalDetails.getProductDetails() == null
                || proposalDetails.getProductDetails().isEmpty()) {
            return AppConstants.NA;
        }
        String planCode = "NA";
        ProductDetails productDetails = proposalDetails.getProductDetails().get(0);
        String productType = productDetails.getProductType();
        if (productType.equalsIgnoreCase(AppConstants.ULIP)) {
            planCode = productDetails.getProductInfo().getPlanCodeMFSA();
            planCode = planCode.trim();
        } else {
            planCode = productDetails.getProductInfo().getPlanCode();
            planCode = planCode.trim();
        }
        return planCode;
    }

    private String getNisSourcedValue(ProposalDetails proposalDetails) {
        boolean isYBLProposal = AppConstants.CHANNEL_YBL.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel()) 
                || proposalDetails.getAdditionalFlags().isYblTelesalesCase() 
                || proposalDetails.getYblDetails()!=null;
        if(isYBLProposal && Objects.nonNull(proposalDetails.getYblDetails())
                && Objects.nonNull(proposalDetails.getYblDetails().getNisTraceId())) {
            return AppConstants.Y;
        }
        else if(isYBLProposal && Objects.nonNull(proposalDetails.getYblDetails())
                && Objects.isNull(proposalDetails.getYblDetails().getNisTraceId())) {
            return AppConstants.N;
        }
        return AppConstants.NA;
    }

    private String getRiskyTagPersForPiPosv(ProposalDetails proposalDetails) {
        if(Objects.nonNull(proposalDetails.getUnderwritingServiceDetails().getPersistencyModelDetails())
                && Objects.nonNull(proposalDetails.getUnderwritingServiceDetails().getPersistencyModelDetails().getRiskyTagPers())) {
			return proposalDetails.getUnderwritingServiceDetails().getPersistencyModelDetails().getRiskyTagPers().toUpperCase();
        }
        return AppConstants.NA;
    }

    private String getRiskyTagUrmuForPiPosv(ProposalDetails proposalDetails) {
        String riskyTagUrmu = AppConstants.NA;
        if (Objects.nonNull(proposalDetails.getUnderwritingServiceDetails().getRiskScoreDetails())
                && Objects.nonNull(
                        proposalDetails.getUnderwritingServiceDetails().getRiskScoreDetails().getRiskyTagURMU())) {

			return proposalDetails.getUnderwritingServiceDetails().getRiskScoreDetails().getRiskyTagURMU().toUpperCase();
                    
        }
        return riskyTagUrmu;
    }
    

    private String getPersistencyScoreForPiPosv(ProposalDetails proposalDetails) {
		String persistencyScore = AppConstants.URMU_API_FAILURE_CODE;
		if(Objects.nonNull(proposalDetails.getUnderwritingServiceDetails().getPersistencyModelDetails())
				&& Objects.nonNull(proposalDetails.getUnderwritingServiceDetails().getPersistencyModelDetails().getNormalizedScorePers())
				&& !proposalDetails.getUnderwritingServiceDetails().getPersistencyModelDetails().getNormalizedScorePers().trim().isEmpty()) {
			float normalisedScorePersFloat = Float.parseFloat(proposalDetails.getUnderwritingServiceDetails().getPersistencyModelDetails().getNormalizedScorePers());
			int normalisedScorePersInt = Math.round(normalisedScorePersFloat);
			return String.valueOf(normalisedScorePersInt);
		}
		return persistencyScore;
	}

    private String setUrmuScore(ProposalDetails proposalDetails) {
        String urmuScore = AppConstants.URMU_API_FAILURE_CODE;
        if (Objects.nonNull(proposalDetails.getUnderwritingServiceDetails().getRiskScoreDetails())
                && Objects.nonNull(proposalDetails.getUnderwritingServiceDetails().getRiskScoreDetails()
                        .getNormalisedScoreURMU())
                && !proposalDetails.getUnderwritingServiceDetails().getRiskScoreDetails()
                .getNormalisedScoreURMU().trim().isEmpty()){
            float normalisedScoreUrmuFloat =  Float.parseFloat(proposalDetails.getUnderwritingServiceDetails().getRiskScoreDetails().getNormalisedScoreURMU());
            int normalisedScoreUrmuInt = Math.round(normalisedScoreUrmuFloat);
            return String.valueOf(normalisedScoreUrmuInt);        
        }
        return urmuScore;
    }

    private String getProposerNationality(ProposalDetails proposalDetails) {
        String nationality = AppConstants.NA;
        try {
            String formType = proposalDetails.getApplicationDetails().getFormType();
            String schemeType = proposalDetails.getApplicationDetails().getSchemeType();
            if (AppConstants.FORM3.equalsIgnoreCase(formType) && AppConstants.SCHEME_A.equalsIgnoreCase(schemeType)) {
                if (Objects.nonNull(proposalDetails.getPartyInformation().get(1).getBasicDetails())
                        && Objects.nonNull(
                                proposalDetails.getPartyInformation().get(1).getBasicDetails().getNationalityDetails())
                        && Objects.nonNull(proposalDetails.getPartyInformation().get(0).getBasicDetails()
                                .getNationalityDetails().getNationality())) {
                    return proposalDetails.getPartyInformation().get(1).getBasicDetails().getNationalityDetails()
                            .getNationality().toUpperCase();
                }
            } else {
                if (Objects.nonNull(proposalDetails.getPartyInformation().get(0).getBasicDetails())
                        && Objects.nonNull(
                                proposalDetails.getPartyInformation().get(0).getBasicDetails().getNationalityDetails())
                        && Objects.nonNull(proposalDetails.getPartyInformation().get(0).getBasicDetails()
                                .getNationalityDetails().getNationality())) {
                    return proposalDetails.getPartyInformation().get(0).getBasicDetails().getNationalityDetails()
                            .getNationality().toUpperCase();
                }
            }
        } catch (Exception e) {
            log.error("Error occured while setting annual income for transactionId {}",
                    proposalDetails.getTransactionId());
        }
        return nationality;
    }

    private String setPptForPiPosv(ProposalDetails proposalDetails) {
        String ppt = AppConstants.ZERO;
        if(Objects.nonNull(proposalDetails.getProductDetails())
                && Objects.nonNull(proposalDetails.getProductDetails().get(0).getProductInfo())
                && Objects.nonNull(proposalDetails.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse())
                && Objects.nonNull(proposalDetails.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getPremiumPaymentTerm())){
                    return proposalDetails.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getPremiumPaymentTerm();
                }
        return ppt;
    }

    private String getAfypForPiPosv(ProposalDetails proposalDetails) {
        String afyp = AppConstants.ZERO;
        if(Objects.nonNull(proposalDetails.getProductDetails())
                && Objects.nonNull(proposalDetails.getProductDetails().get(0).getProductInfo())
                && Objects.nonNull(proposalDetails.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse())
                && Objects.nonNull(proposalDetails.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getAfyp())
                && Objects.nonNull(proposalDetails.getProductDetails().get(0).getProductType())) {
            String productType = proposalDetails.getProductDetails().get(0).getProductType();
            if(AppConstants.ULIP.equalsIgnoreCase(productType) 
                    && Objects.nonNull(proposalDetails.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getAtp())) {
                return String.valueOf(proposalDetails.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getAtp());
            }
            return proposalDetails.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getAfyp();
        }
        return afyp; 
    }

    private String getSellerPersitency(ProposalDetails proposalDetails) {
        String sellerPersistency = AppConstants.ZERO;
        SourcingDetails sourcingDetails = proposalDetails.getSourcingDetails();
        if(Objects.nonNull(sourcingDetails) && Objects.nonNull(sourcingDetails.getPersistency())){
            return sourcingDetails.getPersistency();
        }
        return sellerPersistency;
    }

    private String getSolId(ProposalDetails proposalDetails) {
        boolean isAxisCase = Objects.nonNull(proposalDetails.getChannelDetails()) && AppConstants.CHANNEL_AXIS.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel());
        String branchCode = proposalDetails.getChannelDetails().getBranchCode();
        if(isAxisCase && Objects.nonNull(branchCode)) {
            return branchCode.replaceAll("[a-zA-Z]", "");        
        }
        return AppConstants.NA;
    }

    private String getGoCaBrokerCode(ProposalDetails proposalDetails) {
        String goCaBrokerCode = AppConstants.NA;
        if(Objects.nonNull(proposalDetails.getSourcingDetails().getGoCABrokerCode())) {
            return proposalDetails.getSourcingDetails().getGoCABrokerCode().toUpperCase();
        }
        return goCaBrokerCode;
    }

    private String getCustometClassificationCodeforPiPosv(ProposalDetails proposalDetails) {
        String customerClassification = AppConstants.NA;
        try {
            boolean isYBLProposal = AppConstants.CHANNEL_YBL.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel()) 
                    || proposalDetails.getAdditionalFlags().isYblTelesalesCase() 
                    || proposalDetails.getYblDetails()!=null;
            boolean isAxisCase = Objects.nonNull(proposalDetails.getChannelDetails()) && AppConstants.CHANNEL_AXIS.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel());
            if(isYBLProposal && Objects.nonNull(proposalDetails.getYblDetails())
                    && Objects.nonNull(proposalDetails.getYblDetails().getCustomerClassification())) {
                return proposalDetails.getYblDetails().getCustomerClassification().toUpperCase();
            }
            else if(isAxisCase && Objects.nonNull(proposalDetails.getBancaDetails())
                    && Objects.nonNull(proposalDetails.getBancaDetails().getCustomerClassification())) {
                return proposalDetails.getBancaDetails().getCustomerClassification().toUpperCase();
            }
        } catch (Exception e) {
            log.error("Error occured while setting axis or ybl customerClassification value for transactionId {} setting default value NA", proposalDetails.getTransactionId());
            Utility.getExceptionAsString(e);
        }
        return customerClassification;
    }

    private String getUrmuResponse(ProposalDetails proposalDetails) {
        String urmuResponse = "NA";
        if(Objects.nonNull(proposalDetails.getUnderwritingServiceDetails().getUrmuRuleStatus())
                && Objects.nonNull(proposalDetails.getUnderwritingServiceDetails().getUrmuRuleStatus().getResult())) {
            urmuResponse = proposalDetails.getUnderwritingServiceDetails().getUrmuRuleStatus().getResult().toUpperCase();
        }
        return urmuResponse;
    }

    private String getProposerAgeForPiPosv(ProposalDetails proposalDetails) {
        String proposerAge = AppConstants.ZERO;
        try {
            String formType = proposalDetails.getApplicationDetails().getFormType();
            String schemeType = proposalDetails.getApplicationDetails().getSchemeType();
            int age = 0;
            Date proposerDob = null;
            // FUL2-155323 Taking insured dob for form3 schemeA case
            if (AppConstants.FORM3.equalsIgnoreCase(formType) && AppConstants.SCHEME_A.equalsIgnoreCase(schemeType)) {
                proposerDob = proposalDetails.getPartyInformation().get(1).getBasicDetails().getDob();
            } else {
                proposerDob = proposalDetails.getPartyInformation().get(0).getBasicDetails().getDob();
            }
            ZoneId defaultZoneId = ZoneId.of(AppConstants.APP_TIMEZONE);
            Instant proposerDobInstant = proposerDob.toInstant();
            LocalDate proposerDobInstantLocalDate = proposerDobInstant.atZone(defaultZoneId).toLocalDate();
            age = Utility.calculateAgeForPiPosv(proposerDobInstantLocalDate, LocalDate.now());
            proposerAge = Integer.toString(age);

        } catch (Exception e) {
            log.error("Exception occured for getProposerAgeForPiPosv with {}", proposalDetails.getTransactionId());
            Utility.getExceptionAsString(e);
        }
        return proposerAge;
    }

    private String getIndustryType(ProposalDetails proposalDetails) {       
        String industryType = AppConstants.NA;
        try {
            String formType = proposalDetails.getApplicationDetails().getFormType();
            String schemeType = proposalDetails.getApplicationDetails().getSchemeType();
            if (AppConstants.FORM3.equalsIgnoreCase(formType) && AppConstants.SCHEME_A.equalsIgnoreCase(schemeType)) {
                PartyInformation partyInfoFromDb = proposalDetails.getEmploymentDetails().getPartiesInformation()
                        .get(1);
                if (Objects.nonNull(partyInfoFromDb)
                        && Objects.nonNull(partyInfoFromDb.getPartyDetails())
                        && Objects.nonNull(partyInfoFromDb.getPartyDetails().getIndustryDetails())
                        && Objects.nonNull(partyInfoFromDb.getPartyDetails().getIndustryDetails().getIndustryType())) {
                    return partyInfoFromDb.getPartyDetails().getIndustryDetails().getIndustryType().toUpperCase();
                }
            } else {
                PartyInformation partyInfoFromDb = proposalDetails.getEmploymentDetails().getPartiesInformation()
                        .get(0);
                if (Objects.nonNull(partyInfoFromDb)
                        && Objects.nonNull(partyInfoFromDb.getPartyDetails())
                        && Objects.nonNull(partyInfoFromDb.getPartyDetails().getIndustryDetails())
                        && Objects.nonNull(partyInfoFromDb.getPartyDetails().getIndustryDetails().getIndustryType())) {
                    return partyInfoFromDb.getPartyDetails().getIndustryDetails().getIndustryType().toUpperCase();
                }
            } 
        } catch (Exception e) {
            log.error("Error occured while setting industryType for transactionId {}", proposalDetails.getTransactionId());
        }
        return industryType;
    }

    private String getFormType(ProposalDetails proposalDetails) {
        String formType = "0";
        String formTypeInDb = proposalDetails.getApplicationDetails().getFormType();
        if(AppConstants.SELF.equalsIgnoreCase(formTypeInDb)) {
            return AppConstants.ONE;
        }
        else if(AppConstants.DEPENDENT.equalsIgnoreCase(formTypeInDb)) {
            return AppConstants.TWO;
        }
        else if(AppConstants.FORM3.equalsIgnoreCase(formTypeInDb)) {
            return AppConstants.THREE;
        }
        return formType;
    }

    private String getPhysicalJourneyFlag(ProposalDetails proposalDetails) {
        if(AppConstants.YES.equalsIgnoreCase(proposalDetails.getApplicationDetails().getPhysicalJourneyEnabled())) {
            return AppConstants.Y;
        }
        return AppConstants.N;
    }

    private com.mli.mpro.posvbrms.models.Header getHeaderForPiPosv() {
        com.mli.mpro.posvbrms.models.Header header = new com.mli.mpro.posvbrms.models.Header();
        header.setSoaAppId("Fulfillment");
        header.setSoaCorrelationId(UUID.randomUUID().toString());
        return header;
    }
    
    private void callsaveProposalForPiPosv(String posvType, ProposalDetails proposalDetails, String reasonForVideoPosv) {
        InputRequest inputRequest = new InputRequest();
        Request request = new Request();
        RequestData requestData = new RequestData();
        RequestPayload requestPayload = new RequestPayload();
        try {
        /* FUL2-25833 dolphin push changes */
        proposalDetails.getAdditionalFlags().setReasonForVideoPosv(reasonForVideoPosv);
        /* FUL2-25833 dolphin push changes */
        proposalDetails.getApplicationDetails().setPosvType(posvType);
        proposalDetails.getApplicationDetails().setBackendHandlerType(AppConstants.HANDLER_TYPE);
        requestPayload.setProposalDetails(proposalDetails);
        requestData.setRequestPayload(requestPayload);
        request.setRequestData(requestData);
        inputRequest.setRequest(request);
		boolean status = false;
		if (FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.KINESIS_FOR_ALL) && FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.KINESIS_FOR_PROPOSAL)) {
			status = proposalStreamPushService.produceToProposalStream(inputRequest);
		} else{
			status = proposalStreamPushService.produceToProposalQueue(inputRequest);
		}
        log.info("called proposal stream from location MS is status {}",status);
        }catch (Exception ex) {
            log.error("Exception while callsaveProposalForPosv {} ", Utility.getExceptionAsString(ex));
        }
        
    }


    public String getTag1(ProposalDetails proposalDetails) {
		try {

			if (Objects.nonNull(proposalDetails.getSourcingDetails())
					&& Objects.nonNull(proposalDetails.getSourcingDetails().getAgentJoiningDate())) {

				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);


				Date currentDate = formatter.parse(formatter.format(new Date()));
				Date agentJoiningDate = formatter.parse(formatter.format(proposalDetails.getSourcingDetails().getAgentJoiningDate()));
				long timeDiff = Math.abs(agentJoiningDate.getTime() - currentDate.getTime());
				Long daysDiff = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
				return daysDiff.toString();

			} else {
				return AppConstants.ZERO;
			}
		} catch (Exception e) {
			log.error("Exception occur while setting the tag1 value and message is {}", Utility.getExceptionAsString(e));
			return AppConstants.ZERO;
		}
	}

    public String getTag2(ProposalDetails proposalDetails) {
		String creditScore = AppConstants.ZERO;
		try {
			UnderwritingServiceDetails underwritingServiceDetails = proposalDetails.getUnderwritingServiceDetails();
			BureauResponse bureauResponse1 = new BureauResponse();

			String panDobValidationService = proposalDetails.getPartyInformation().get(0)
					.getPersonalIdentification().getPanDetails().getPanValidationService();

			if (!StringUtils.isEmpty(underwritingServiceDetails.getCibilDetails())
					&& !StringUtils.isEmpty(underwritingServiceDetails.getCibilDetails().getBureauResponse())
					&& (!StringUtils.isEmpty(panDobValidationService)) && panDobValidationService
					.equalsIgnoreCase(AppConstants.CIBIL)) {

				bureauResponse1 = underwritingServiceDetails.getCibilDetails().getBureauResponse().get(0);

				if (!StringUtils.isEmpty(bureauResponse1) && !StringUtils
						.isEmpty(bureauResponse1.getScore()) && (!StringUtils
						.isEmpty(panDobValidationService))) {
					creditScore = setCibilScore(bureauResponse1.getScore());
				}

			} else if (!StringUtils.isEmpty(panDobValidationService)
					&& panDobValidationService.equalsIgnoreCase(AppConstants.CRIF)
					&& (!StringUtils.isEmpty(proposalDetails.getPartyInformation().get(0).getPersonalIdentification()
					.getPanDetails().getCreditScore()))) {

				creditScore = proposalDetails.getPartyInformation().get(0).getPersonalIdentification()
						.getPanDetails().getCreditScore();
			}
		}
		catch (Exception e)
		{
			log.error("Exception occur while setting the tag2 value and message is {}", Utility.getExceptionAsString(e));
		}

        return creditScore;
    }


    public String getTag3(ProposalDetails proposalDetails) {
			try {
				if(Objects.nonNull(proposalDetails.getApplicationDetails().getFormType())
					&& Objects.nonNull(proposalDetails.getApplicationDetails().getSchemeType())
					&& FORM3.equalsIgnoreCase(proposalDetails.getApplicationDetails().getFormType())
					&& SCHEME_A.equalsIgnoreCase(proposalDetails.getApplicationDetails().getSchemeType())){

					return AppConstants.NA;
				}

				else if (Objects.nonNull(proposalDetails.getPartyInformation().get(0))
					&& Objects.nonNull(proposalDetails.getPartyInformation().get(0).getBasicDetails())
					&& Objects.nonNull(proposalDetails.getPartyInformation().get(0).getBasicDetails().getMarriageDetails())
					&& Objects.nonNull(proposalDetails.getPartyInformation().get(0).getBasicDetails().getMarriageDetails().getMaritalStatus())) {

					return proposalDetails.getPartyInformation().get(0).getBasicDetails().getMarriageDetails().getMaritalStatus();
				}

				else {
				return AppConstants.NA;
			}
		}
			catch (Exception e) {
				log.error("Exception occur while setting the tag3 value and message is {}", Utility.getExceptionAsString(e));
				return AppConstants.NA;
			}
	}

    public String getTag4(ProposalDetails proposalDetails) {
			try {
				if (Objects.nonNull(proposalDetails.getUnderwritingServiceDetails())
						&& Objects.nonNull(proposalDetails.getUnderwritingServiceDetails().getClientPolicyProposerDetails())
						&& Objects.nonNull(proposalDetails.getUnderwritingServiceDetails().getClientPolicyProposerDetails().getPolicyDetails())) {

					Integer policyCount = proposalDetails.getUnderwritingServiceDetails().getClientPolicyProposerDetails().getPolicyDetails().size() + 1;
					return policyCount.toString();
				} else {
					return AppConstants.ONE;
				}
			}
			catch (Exception e) {
				log.error("Exception occur while setting the tag4 value and message is {}", Utility.getExceptionAsString(e));
				return AppConstants.ONE;
			}

    }
	public String getTag5(ProposalDetails proposalDetails) {
		try {
			if (Objects.nonNull(proposalDetails.getSourcingDetails())
					&& Objects.nonNull(proposalDetails.getSourcingDetails().getDesignation())) {
				return proposalDetails.getSourcingDetails().getDesignation();
			}
		}
		catch (Exception e) {
			log.error("Exception occur while setting the tag5 value and message is {}", Utility.getExceptionAsString(e));
		}
		return "";
	}
	public String getTag6(ProposalDetails proposalDetails) {
			try {
				if (Objects.nonNull(proposalDetails.getSourcingDetails())
						&& Objects.nonNull(proposalDetails.getSourcingDetails().getCategoryDesc())) {
					return proposalDetails.getSourcingDetails().getCategoryDesc();
				}
			}
			catch (Exception e) {
				log.error("Exception occur while setting the tag6 value and message is {}", Utility.getExceptionAsString(e));
			}
			return "";
		}

    private String setCibilScore(String cibilScore) {
        String formattedScore = "0";
        if (!StringUtils.isEmpty(cibilScore)) {
            if (cibilScore.contains("-")) {
                cibilScore = cibilScore.replace("-", "");
            }
            formattedScore = cibilScore.replaceFirst("^0+(?!$)", "");
        }
        return formattedScore;
    }


}
