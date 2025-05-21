package com.mli.mpro.configuration.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.config.BeanUtil;
import com.mli.mpro.config.ConfigurationData;
import com.mli.mpro.config.ExternalServiceConfig;
import com.mli.mpro.configuration.models.*;
import com.mli.mpro.configuration.repository.ConfigurationRepository;
import com.mli.mpro.configuration.repository.FeatureFlagrepository;
import com.mli.mpro.configuration.repository.MultiSelectDataRepository;
import com.mli.mpro.configuration.repository.SupervisorDetailsRepository;
import com.mli.mpro.configuration.service.ConfigurationService;
import com.mli.mpro.location.common.soa.model.Header;
import com.mli.mpro.location.newApplication.model.ResponseMsgInfo;
import com.mli.mpro.productRestriction.repository.ProposalRepository;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.EncryptionDecryptionOnboardingUtil;
import com.mli.mpro.utils.Utility;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.mli.mpro.productRestriction.util.AppConstants.*;

/*This class has all the implementation to get the configuration from DB*/
@Service
public class ConfigurationServiceImpl implements ConfigurationService {

	private final SupervisorDetailsRepository supervisorDetailsRepository;
	private ConfigurationRepository repository;
    private MultiSelectDataRepository multiSelectDataRepository;
	private ProposalRepository proposalRepository;
    private static final Logger logger= LoggerFactory.getLogger(ConfigurationServiceImpl.class);

    @Autowired
    public ConfigurationServiceImpl(ConfigurationRepository repository, MultiSelectDataRepository multiSelectDataRepository,SupervisorDetailsRepository supervisorDetailsRepository,ProposalRepository proposalRepository){
        this.repository = repository;
        this.multiSelectDataRepository = multiSelectDataRepository;
        this.supervisorDetailsRepository = supervisorDetailsRepository;
		this.proposalRepository = proposalRepository;
    }
    
    @Autowired
	private FeatureFlagrepository featureFlagrepository;

	@Autowired
	MongoOperations mongoOperation;
    /* To get the configuration using key */
    @Override
    public List<Configuration> getConfigurationByKey(String key) {
	return repository.findByKeyIgnoreCase(key);
    }

    /* To get all the configurations */
    @Override
    public List<Configuration> getAllConfiguration() {
	return repository.findAll();
    }

    /* To get the configuration using type */
    @Override
    public List<Configuration> getConfigurationByType(String type) {
	return repository.findByTypeIgnoreCase(type);
    }

    @Override
    public MultiSelectData getMultiSelectDataByKey(String key) {
        return multiSelectDataRepository.findByKeyIgnoreCase(key);
    }

    @Override
    public Boolean isYblTelesalesBranchCode(String branchCode) {
    	try {
        List<Configuration> yblTelesalesConfiguration = repository.findByTypeIgnoreCase("yblTelesales");
        if (!yblTelesalesConfiguration.isEmpty() && yblTelesalesConfiguration.get(0) != null) {
            List<String> branchCodes = yblTelesalesConfiguration.get(0).getBranchCodes();
            if (!branchCodes.isEmpty()) {
                return branchCodes.contains(branchCode);
            } else {
                logger.info("No branch Codes found for type: yblTelesales");
                return false;
            }
        } else {
            logger.info("No Configuration found for type: yblTelesales for branch code:{}", branchCode);
            return false;
        }
	} catch (Exception ex) {
		logger.error("Exception while yblTelesalesConfiguration {} ", Utility.getExceptionAsString(ex));
	}
		return false;
    }

	@Override
	public Configuration getVideoConfigurationByType(String type) {

		Configuration videoConfig = null;
		try {
			List<Configuration> videoPosvConfig = ConfigurationData.configurationDetails.stream()
                    .filter(configuration -> type.equalsIgnoreCase(configuration.getType())).collect(Collectors.toList());
			if (!CollectionUtils.isEmpty(videoPosvConfig)) {
				videoConfig = videoPosvConfig.get(0);
			}
		} catch (Exception ex) {
			logger.error("Exception while getVideoConfigurationByType {} ", Utility.getExceptionAsString(ex));
		}
		return videoConfig;
	}

	@Override
	public OutputResponse getFeatureFlagData() {
		ResponseMsgInfo msgInfo;
		List<FeatureFlag> featureFlags = new ArrayList<>();
		try {
			featureFlags = featureFlagrepository.findAll();
			if (featureFlags.isEmpty()) {
				msgInfo = new ResponseMsgInfo(200, "Data not found!", "");
			} else {
				msgInfo = new ResponseMsgInfo(200, AppConstants.SUCCESS, "Data found successfully");
			}
		} catch (Exception e) {
			logger.error("exception occurred while retrieving the feature flag data {}", Utility.getExceptionAsString(e));
			msgInfo = new ResponseMsgInfo(500, AppConstants.INTERNAL_SERVER_ERROR, "Service not responding please try again later !!");
		}
		Response response = new Response();
		response.setHeader(new Header());
		response.setMsginfo(msgInfo);
		response.setPayload(featureFlags);
		Result result = new Result(response);
		return  new OutputResponse(result);
	}
	@Override
	public FeatureFlag saveFeatureFlagData(FeatureFlagRequest featureFlagRequest) {
		FeatureFlag featureFlag = null;
		try {
			if (validateFeatureFlagData(featureFlagRequest)) {
				featureFlag = featureFlagrepository.findByFeatureName(featureFlagRequest.getFeatureName());
				if (featureFlag != null) {
					featureFlag.setEnabled(featureFlagRequest.getEnabled());
					featureFlag.setJiraId(featureFlagRequest.getJiraId());
				} else {
					featureFlag = new FeatureFlag();
					featureFlag.setFeatureName(featureFlagRequest.getFeatureName());
					featureFlag.setEnabled(featureFlagRequest.getEnabled());
					featureFlag.setJiraId(featureFlagRequest.getJiraId());
				}
				featureFlag = featureFlagrepository.save(featureFlag);
			}
		} catch (Exception ex) {
			logger.error("Exception while saving the data {} ", Utility.getExceptionAsString(ex));
		}
		return featureFlag;
	}
	
	private boolean validateFeatureFlagData(FeatureFlagRequest featureFlagRequest) {
		boolean valid;
		if (!ObjectUtils.isEmpty(featureFlagRequest) && !StringUtils.isBlank(featureFlagRequest.getFeatureName())
				&& !StringUtils.isBlank(featureFlagRequest.getJiraId())
				&& !ObjectUtils.isEmpty(featureFlagRequest.getEnabled())) {
			valid = true;

		} else {
			valid = false;
		}
		return valid;
	}

		@Override
		public SupervisorDetails getSupervisorDetails(String supervisorId) {
			SupervisorDetails supervisorDetails = new SupervisorDetails();
			try {
				if (org.springframework.util.StringUtils.isEmpty(supervisorId)) {
					logger.info("supervisorId cannot be null or empty for supervisor request");
				} else {
					supervisorDetails = supervisorDetailsRepository.findBySpCodeIgnoreCase(supervisorId);
					if (supervisorDetails != null) {
						logger.info("Supervisor Details {}", supervisorDetails);
						return supervisorDetails;
					}
				}

			} catch (Exception e) {
				logger.error("Error occurred while getting Supervisor Details from DB {}", Utility.getExceptionAsString(e));
			}

			return supervisorDetails;
		}

	@Override
	public LinkOutputResponse validateApprovalLink(String jsonInput) {
		Details details = new Details();
		SellerResponse response =new SellerResponse();
		ErrorResponse errorResponse=new ErrorResponse();
		LinkOutputResponse outputResponse = new LinkOutputResponse(errorResponse, response);
		outputResponse.setResponse(response);
		Set<String> validStatuses = Set.of(APPROVED, REJECTED);
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree(jsonInput);
			JsonNode payloadNode = rootNode.path("request").path("data").path("payload");
			if (payloadNode.isMissingNode() || payloadNode.asText().trim().isEmpty()) {
				throw new IllegalArgumentException("Payload is empty, null, or missing.");
			}
			String payload = payloadNode.asText();
			logger.info("Extracted payload: {}", payload);
			String decodedPayload = decryptRequest(payload);
			logger.info("Decoded Payload: {}", decodedPayload);
			Map<String, Object> payloadMap = objectMapper.readValue(decodedPayload, Map.class);

			String transactionIdstr = (String) payloadMap.get("transactionId");
			Long transactionId = Long.parseLong(transactionIdstr);
			String expiryDateStr = (String) payloadMap.get("expiryDate");
			String approverName = (String) payloadMap.get("approverName");
			String uniqueId=(String) payloadMap.get("uniqueId");
			String currentUniqueId= null;
			logger.info("decoded transaction id is {} and expiry date is {} ", transactionId, expiryDateStr);

			if (transactionIdstr == null || expiryDateStr == null || approverName == null || uniqueId==null) {
				throw new IllegalArgumentException("Missing required parameters in the payload.");
			}
			Date expiryDate = null;
			try {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.ENGLISH);
				expiryDate = formatter.parse(expiryDateStr);
			} catch (ParseException e) {
				throw new IllegalArgumentException("Invalid date format for expiry date: " + expiryDateStr, e);
			}
			Date currentTime = new Date();
			if (currentTime.after(expiryDate)) {
				logger.error("Generated link has expired. Expiry date: {}", expiryDateStr);
				errorResponse.setMessage("Link has expired");
				errorResponse.setErrorCode("404");
				outputResponse.setErrorResponse(errorResponse);
				return outputResponse;

			}
			else {
				logger.info("The expiry date {} is valid and after the current time {}", expiryDate, currentTime);
			}
			ProposalDetails proposalDetails = proposalRepository.findByTransactionId(transactionId);
			if (proposalDetails == null) {
				logger.info("Proposal Details object is null");
				throw new IllegalArgumentException("Proposal details not found for transaction ID: " + transactionId);
			}
			String spCode=proposalDetails.getSourcingDetails().getSpecifiedPersonCode();
			String currentApproverName = proposalDetails.getAdditionalFlags().getSpApproverName();
			if(proposalDetails.getAdditionalFlags().getUniqueId()!=null) {
				currentUniqueId = proposalDetails.getAdditionalFlags().getUniqueId();
			}
			else {
				currentUniqueId = uniqueId;
			}
			String spDigitalConsent = null;
			if(proposalDetails.getAdditionalFlags()!=null && proposalDetails.getAdditionalFlags().getSpDigitalConsentSkip()!=null) {
				spDigitalConsent = proposalDetails.getAdditionalFlags().getSpDigitalConsentSkip();
			}
			if(spDigitalConsent!=null && spDigitalConsent.equalsIgnoreCase("Yes")) {
				logger.error("Generated link has expired for spDigitalConsentskip {}", spDigitalConsent);
				errorResponse.setMessage("Link has expired");
				errorResponse.setErrorCode("404");
				outputResponse.setErrorResponse(errorResponse);
				return outputResponse;
			}

			if((approverName!=null && currentApproverName!=null && !approverName.equalsIgnoreCase(currentApproverName))) {
				logger.error("Generated link has expired for approverName {}", approverName);
				errorResponse.setMessage("Link has expired");
				errorResponse.setErrorCode("404");
				outputResponse.setErrorResponse(errorResponse);
				return outputResponse;
			}
			if(!uniqueId.equalsIgnoreCase(currentUniqueId)) {
				logger.error("Generated link has expired for approverName {}", approverName);
				errorResponse.setMessage("Link has expired");
				errorResponse.setErrorCode("404");
				outputResponse.setErrorResponse(errorResponse);
				return outputResponse;
			}

			logger.info("spcode of Proposal Details is {}", spCode);
			SupervisorDetails supervisorDetails = supervisorDetailsRepository.findBySpCodeIgnoreCase(spCode);

			if (supervisorDetails == null) {
				throw new IllegalArgumentException("supervisorDetails not found for transaction ID: " + transactionId);
			}
			logger.info("Supervisor Details is  {}", supervisorDetails);

			AfypDetails afypDetails = proposalDetails != null && proposalDetails.getAdditionalFlags() != null
					? proposalDetails.getAdditionalFlags().getAfypDetails()
					: null;
			logger.info("afyp details is:",afypDetails);

			String afyp_status = (afypDetails != null) ? afypDetails.getAfypStatus() : null;

			ReplacementSaleDetails replacementSaleDetails = proposalDetails != null && proposalDetails.getAdditionalFlags() != null
					? proposalDetails.getAdditionalFlags().getReplacementSaleDetails()
					: null;

			String replacement_sales_status = (replacementSaleDetails != null)
					? replacementSaleDetails.getReplacementSalesStatus()
					: null;

			if(afypDetails!=null && afyp_status!=null && validStatuses.contains(afyp_status) && replacementSaleDetails != null && replacement_sales_status != null && validStatuses.contains(replacement_sales_status)) {
				errorResponse.setMessage("Already responded for Both Afyp & Replacement Sales status "+afyp_status);
				errorResponse.setErrorCode("208");
				outputResponse.setErrorResponse(errorResponse);
				replacementSaleDetails.setReplacementSalesStatus(replacement_sales_status);
				afypDetails.setAfypStatus(afyp_status);
			}
			else if(afypDetails!=null && afyp_status!=null && validStatuses.contains(afyp_status)) {
				if (replacementSaleDetails!=null) {
					replacementSaleDetails.setReplacementSalesStatus("");
					replacementSaleDetails.setReplacementSales(false);
					errorResponse.setMessage("Already responded for AFYP status " + afyp_status);
					errorResponse.setErrorCode("208");
					outputResponse.setErrorResponse(errorResponse);
					afypDetails.setAfypStatus(afyp_status);
				}else {
					errorResponse.setMessage("Already responded for AFYP status " + afyp_status);
					errorResponse.setErrorCode("208");
					outputResponse.setErrorResponse(errorResponse);
					afypDetails.setAfypStatus(afyp_status);
					replacementSaleDetails = new ReplacementSaleDetails();
				}
			}
			else if (replacementSaleDetails != null && replacement_sales_status != null && validStatuses.contains(replacement_sales_status)) {
				if(afypDetails!=null) {
					afypDetails.setAfypStatus("");
					afypDetails.setAFYP(false);
					errorResponse.setMessage("Already responded for Replacement Sales status " + replacement_sales_status);
					errorResponse.setErrorCode("208");
					outputResponse.setErrorResponse(errorResponse);
					replacementSaleDetails.setReplacementSalesStatus(replacement_sales_status);
				}else {
					errorResponse.setMessage("Already responded for Replacement Sales status " + replacement_sales_status);
					errorResponse.setErrorCode("208");
					outputResponse.setErrorResponse(errorResponse);
					replacementSaleDetails.setReplacementSalesStatus(replacement_sales_status);
					afypDetails=new AfypDetails();
				}
			}

			details.setAfypDetails(afypDetails);
			details.setReplacementSaleDetails(replacementSaleDetails);
			details.setPolicyNumber(
					proposalDetails.getApplicationDetails().getPolicyNumber() != null
							? proposalDetails.getApplicationDetails().getPolicyNumber()
							: ""
			);

			details.setPlan(
					proposalDetails.getProductDetails().get(0).getProductInfo().getProductName() != null
							? proposalDetails.getProductDetails().get(0).getProductInfo().getProductName()
							: ""
			);
			details.setUin(proposalDetails.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse()!=null &&
					proposalDetails.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getUin() != null
					? proposalDetails.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getUin()
					: ""
			);

			details.setSpName(currentApproverName!=null ? currentApproverName : approverName);

			details.setDesignation(
					proposalDetails.getSourcingDetails().getDesignation() != null
							? proposalDetails.getSourcingDetails().getDesignation()
							: ""
			);
			details.setEmployeeCode(supervisorDetails != null ? supervisorDetails.getSpCode() : "");
			details.setDueDate("");
			details.setTotalRequireModelPremium(
					proposalDetails.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse()!= null &&
							proposalDetails.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getTotalRequiredModalPremium()!=null
							? proposalDetails.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getTotalRequiredModalPremium()
							: ""
			);

			String firstName = proposalDetails.getPartyInformation().get(0).getBasicDetails().getFirstName() != null
					? proposalDetails.getPartyInformation().get(0).getBasicDetails().getFirstName()
					: "";
			String lastName = proposalDetails.getPartyInformation().get(0).getBasicDetails().getLastName() != null
					? proposalDetails.getPartyInformation().get(0).getBasicDetails().getLastName()
					: "";
			String insurerFirstName = proposalDetails.getPartyInformation().get(1).getBasicDetails().getFirstName() != null
					? proposalDetails.getPartyInformation().get(1).getBasicDetails().getFirstName()
					: "";
			String insurerLastName = proposalDetails.getPartyInformation().get(1).getBasicDetails().getLastName() != null
					? proposalDetails.getPartyInformation().get(1).getBasicDetails().getLastName()
					: "";
			details.setProposerName(firstName + " " + lastName);
			details.setInsurerName(insurerFirstName + " " + insurerLastName);
			logger.info("Set data in details for all details: {}", details);

			response.setDetails(details);
			outputResponse.setResponse(response);

			return outputResponse;
		} catch (ExpiredLinkException e) {
			throw new RuntimeException("Link has expired:");
		} catch (Exception e) {
			logger.error("Error validating drop-off link: {}", Utility.getExceptionAsString(e));
			throw new ExpiredLinkException("Missing required parameters in the payload.");
		}
	}

	@Override
	public String saveSellerSupervisorStatus(SellerSaveRequest request) {
		try {
			Query query = new Query();
			Update update = new Update();
			logger.info("SellerSupervisor details: save request {}", request);
			String payload = request.getRequest().getData().getPayload().getPayload();
			Long transactionId = getTransactionId(payload);
            String uniqueId=getUniqueId(payload);

			ProposalDetails mergedProposalDetails = proposalRepository.findByTransactionId(transactionId);
            String currentUniqueId=mergedProposalDetails.getAdditionalFlags().getUniqueId();
			if(!currentUniqueId.equalsIgnoreCase(uniqueId)){
				return "EXPIRY";
			}

			if (org.apache.commons.lang3.ObjectUtils.isEmpty(mergedProposalDetails) || org.apache.commons.lang3.ObjectUtils.isEmpty(mergedProposalDetails.getAdditionalFlags())) {
				return "Proposal not found for transaction ID: " + transactionId;
			}
			query.addCriteria(Criteria.where(AppConstants.TRANSACTION_ID).is(transactionId));
			AfypDetails afypDetails = mergedProposalDetails.getAdditionalFlags().getAfypDetails();
			logger.info("afyp details is:{}",afypDetails);
			String afypStatus = null;
			boolean isAFYP = false;
			if(!org.apache.commons.lang3.ObjectUtils.isEmpty(afypDetails)) {
				afypStatus = afypDetails.getAfypStatus();
				isAFYP = afypDetails.isAFYP();

			}

			ReplacementSaleDetails replacementSaleDetails = mergedProposalDetails.getAdditionalFlags().getReplacementSaleDetails();
			String replacementSalesStatus = null;
			boolean isReplacementSales = false;
			if(!org.apache.commons.lang3.ObjectUtils.isEmpty(replacementSaleDetails)){
				replacementSalesStatus = replacementSaleDetails.getReplacementSalesStatus();
				isReplacementSales = replacementSaleDetails.isReplacementSales();
			}

			boolean isAfypUpdateRestricted = org.springframework.util.StringUtils.hasText(afypStatus)
					&& (afypStatus.equalsIgnoreCase("approved") || afypStatus.equalsIgnoreCase("rejected"))
					&& isAFYP;

			boolean isReplacementSaleUpdateRestricted = org.springframework.util.StringUtils.hasText(replacementSalesStatus)
					&& (replacementSalesStatus.equalsIgnoreCase("approved") || replacementSalesStatus.equalsIgnoreCase("rejected"))
					&& isReplacementSales;

			if (isAfypUpdateRestricted || isReplacementSaleUpdateRestricted) {
				logger.info("Update restricted for AFYP or Replacement Sale. AFYP: {}, Replacement Sale: {}",
						afypStatus, replacementSalesStatus);
				return "You cannot update seller supervisor status";
			}
			if(!org.apache.commons.lang3.ObjectUtils.isEmpty(afypDetails)){
				String requestAfypStatus = request.getRequest().getData().getPayload().getHighAFYPDetails().getAfypStatus();
				boolean requsetAfyp = isAFYP;
				logger.info("SellerSupervisor: request afypStatus {} with afyp {}",requestAfypStatus,requsetAfyp);
				afypDetails.setAfypStatus(requestAfypStatus);
				afypDetails.setAFYP(requsetAfyp);
				mergedProposalDetails.getAdditionalFlags().setAfypDetails(afypDetails);
				mergedProposalDetails.getAdditionalFlags().setDocumentGenerated("Yes");
			}
			if(!org.apache.commons.lang3.ObjectUtils.isEmpty(replacementSaleDetails)){
				String requsetReplacementSaleStatus = request.getRequest().getData().getPayload().getReplacementSaleDetails().getReplacementSalesStatus();
				boolean requestIsReplacementSale = isReplacementSales;
				replacementSaleDetails.setReplacementSalesStatus(requsetReplacementSaleStatus);
				logger.info("SellerSupervisor: request ReplacementSaleStatus {} with replacementSale {}",requsetReplacementSaleStatus,requestIsReplacementSale);
				replacementSaleDetails.setReplacementSales(isReplacementSales);
				logger.info("Updated Replacement Sale details: {}", replacementSaleDetails);
				mergedProposalDetails.getAdditionalFlags().setReplacementSaleDetails(replacementSaleDetails);
				mergedProposalDetails.getAdditionalFlags().setDocumentGenerated("Yes");
			}
			update.set("additionalFlags", mergedProposalDetails.getAdditionalFlags());
			mongoOperation.updateFirst(query, update, ProposalDetails.class);
			return "SUCCESS";
		} catch (Exception e) {
			logger.error("Error occurred while saving seller supervisor status: {}", Utility.getExceptionAsString(e));
			return "An error occurred while processing the request. Please try again later.";
		}
	}

	public static String decryptRequest(String payload) {
		String decryptedRequest = null;
		try {
			String key = BeanUtil.getBean(ExternalServiceConfig.class).getUrlDetails().get(ENC_KEY);
			byte[] decryptedBytes = EncryptionDecryptionOnboardingUtil.decrypt(
					Base64.getDecoder().decode(payload), Base64.getDecoder().decode(key));
			decryptedRequest = new String(decryptedBytes, StandardCharsets.UTF_8);
		} catch (Exception ex) {
			logger.error("Exception while decrypting: {}", Utility.getExceptionAsString(ex));
		}
		return decryptedRequest;
	}

	public Long getTransactionId(String payload) {
		logger.info("Validating drop-off link with payload: {}", payload);
		try {
			String decodedPayload = decryptRequest(payload);
			logger.info("Decoded Payload: {}", decodedPayload);
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree(decodedPayload);
			JsonNode transactionIdNode = rootNode.get("transactionId");
			if (transactionIdNode == null || transactionIdNode.isNull()) {
				throw new IllegalArgumentException("Transaction ID is missing in the payload.");
			}
			String transactionIdStr = transactionIdNode.asText();
			return Long.parseLong(transactionIdStr);
		} catch (Exception e) {
			logger.error("Error processing payload: {}", e.getMessage(), e);
			throw new RuntimeException("Failed to extract transaction ID from payload.", e);
		}
	}
	public String getUniqueId(String payload) {
		logger.info("Validating drop-off link with payload: {}", payload);
		try {
			String decodedPayload = decryptRequest(payload);
			logger.info("Decoded Payload: {}", decodedPayload);

			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree(decodedPayload);

			JsonNode uniqueIdNode = rootNode.get("uniqueId");

			if (uniqueIdNode == null || uniqueIdNode.isNull()) {
				throw new IllegalArgumentException("Unique ID is missing in the payload.");
			}
			return uniqueIdNode.asText();
		} catch (Exception e) {
			logger.error("Error processing payload: {}", e.getMessage(), e);
			throw new RuntimeException("Failed to extract unique ID from payload.", e);
		}
	}


}

