package com.mli.mpro.document.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

import com.mli.mpro.configuration.repository.ConfigurationRepository;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.context.Context;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.document.models.SellerConsentDetails;
import com.mli.mpro.location.repository.SellerConsentDetailsRepository;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.Address;
import com.mli.mpro.proposal.models.AddressDetails;
import com.mli.mpro.proposal.models.BasicDetails;
import com.mli.mpro.proposal.models.CommissionShare;
import com.mli.mpro.proposal.models.PartyInformation;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;
@Service
public class HSADeclarationMapper {
	@Autowired
	private BaseMapper baseMapper;
	@Autowired
	private SellerConsentDetailsRepository sellerConsentDetailsRepository;
	@Value("${pf.vernacular.declaration.visibility.date}")
	private String deploymentDate;

	@Autowired
	private ConfigurationRepository configurationRepository;

	private static final Logger logger = LoggerFactory.getLogger(HSADeclarationMapper.class);
	private static final Map<String, String> proposalFormVersion = new HashMap<>();

    static {
    	proposalFormVersion.put("A", AppConstants.PDF_VERSION_HSA);
    	proposalFormVersion.put("F", AppConstants.PDF_VERSION_HSA);
    	proposalFormVersion.put("T", AppConstants.PDF_VERSION_HSA);
    	proposalFormVersion.put("K", AppConstants.PDF_VERSION_HSA);
    	proposalFormVersion.put("B", AppConstants.PDF_VERSION_HSA);
    	proposalFormVersion.put("B2", AppConstants.PDF_VERSION_HSA);
    	proposalFormVersion.put("BY", AppConstants.PDF_VERSION_HSA);
    	proposalFormVersion.put("X", AppConstants.PDF_VERSION_HSA);
    	proposalFormVersion.put("P", AppConstants.PDF_VERSION_HSA);
    	proposalFormVersion.put("LX", AppConstants.PDF_VERSION_HSA);
    }

    Map<String, String> channelName = new HashMap<String, String>() {

		private static final long serialVersionUID = 2L;
		{
			put("LX", AppConstants.ASL);
			put("P", AppConstants.PEERLESS);
		}

	};

    public Context mapDataForDeclarationDetails(ProposalDetails proposalDetails) throws UserHandledException {
    	long transactionId = proposalDetails.getTransactionId();
		logger.info("START mapDataForDeclarationDetails for transactionId {}", transactionId);
		Map<String, Object> dataVariables = new HashMap<>();

    	try {
    		//
    		setVernacularDeclarationData(proposalDetails, dataVariables);
    		setProposerDeclarationData(proposalDetails, dataVariables);
    		setReplacementSaleData(proposalDetails, dataVariables);
    		setAgentCommisionData(proposalDetails, dataVariables);
    		//FUL2-232653_SEWA_PF_Changes
    		String channel = proposalDetails.getChannelDetails().getChannel();
    		boolean isNeoOrAggregator = channel.equalsIgnoreCase(AppConstants.CHANNEL_NEO)
    				|| channel.equalsIgnoreCase(AppConstants.CHANNEL_AGGREGATOR);
    		if(!isNeoOrAggregator) {
    			Utility.setDataForVernacularAndDisabilityDeclaration(proposalDetails, dataVariables);
				dataVariables = paymentFirstConsentTagApplicability(proposalDetails, dataVariables);
			}
    		dataVariables.put("isNotYBLProposal", !Utility.isYBLProposal(proposalDetails));
    	} catch (Exception ex) {
			logger.error(
					"mapDataForDeclarationDetails failed for transactionId {} with exception {}",
					transactionId, ex);
			List<String> errorMessages = new ArrayList<>();
			errorMessages.add("%M Data Mapping Failed");
			throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.info("mapDataForDeclarationDetails completed successfully for transactionId {}",
				transactionId);
		Context declarationDetailsContext = new Context();
		declarationDetailsContext.setVariables(dataVariables);
		logger.info("END mapDataForDeclarationDetails");
		return declarationDetailsContext;
    }

	private Map<String,Object> paymentFirstConsentTagApplicability(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {
		Boolean enablePaymentFirstConsent = FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.PAYMENT_FIRST_CONSENT);
		logger.info(" enablePaymentFirstConsent flag for transaction id {} and flag value is {}: ", proposalDetails.getTransactionId(), enablePaymentFirstConsent);
		Boolean isPaymentFirstNonWipCase = compareWipCaseDate(proposalDetails.getTransactionId(), proposalDetails.getApplicationDetails().getCreatedTime(), AppConstants.PAYMENT_FIRST_WIP_CASE);
		if (Boolean.TRUE.equals(enablePaymentFirstConsent)
				&& isPaymentFirstNonWipCase) {
			logger.info(" Inside payment first consent for transaction id : {}  ", proposalDetails.getTransactionId());
			dataVariables.put("isPaymentFirstConsent", proposalDetails.getAdditionalFlags().isPaymentFirstConsent());
			dataVariables.put(AppConstants.IS_PAYMENT_FIRST_NON_WIP_CASE,true);
		} else {
			dataVariables.put("isPaymentFirstConsent", false);
			dataVariables.put(AppConstants.IS_PAYMENT_FIRST_NON_WIP_CASE,false);
		}
		return dataVariables;
	}

	private  boolean compareWipCaseDate(long transactionId,Date createdTime,String key) {
		try {
			logger.info("Comparing WIP Case Date for transactionId: {}",transactionId);
			String dateStr = configurationRepository.findByKey(key).getValue();
			logger.info("WIP Case Date from Configuration: {}", dateStr);
			String pattern = AppConstants.UTC_DATE_FORMAT;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			simpleDateFormat.setTimeZone(java.util.TimeZone.getTimeZone(AppConstants.UTC));
			Date wipCaseDate = simpleDateFormat.parse(dateStr);
			Date policyDate = createdTime;
			return policyDate.after(wipCaseDate);
		} catch (Exception e) {
			logger.error("Error occurred while comparing WIP Case Date for transactionId: {}",transactionId,e);
			return false;
		}
	}

	private void setVernacularDeclarationData(ProposalDetails proposalDetails, Map<String, Object> dataVariables) throws UserHandledException {
    	//
    	boolean visibleVernacular = false;
		if(StringUtils.isNotEmpty(deploymentDate)){
			visibleVernacular = Utility.newPFDeclaration(proposalDetails, deploymentDate);
		}
		dataVariables.put("visibleVernacular", visibleVernacular);
    }

    /**
     * Maps the variables for section DECLARATION BY PROPOSER
     * @param proposalDetails
     * @param dataVariables
     * @throws UserHandledException
     */
    private void setProposerDeclarationData(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {
    	//
    	String formType = proposalDetails.getApplicationDetails().getFormType();
		String schemeType = proposalDetails.getApplicationDetails().getSchemeType();
		String channel = proposalDetails.getChannelDetails().getChannel();
    	Date otpConfirmation = null;
    	String place = StringUtils.EMPTY;
		if (Utility.andTwoExpressions(AppConstants.FORM3.equalsIgnoreCase(formType), !Utility.schemeBCase(schemeType))) {
			otpConfirmation = Optional.ofNullable(proposalDetails.getPosvDetails().getPosvStatus().getAuthorizedSignatoryOTPSubmittedDate()).orElse(null);
		} else {
			otpConfirmation = Optional.ofNullable(proposalDetails.getPosvDetails().getPosvStatus().getGeneratedOTPDate()).orElse(null);
		}
		if (AppConstants.FORM3.equalsIgnoreCase(proposalDetails.getApplicationDetails().getFormType()) && !Utility.schemeBCase(schemeType)) {
			place = Optional.ofNullable(proposalDetails.getPartyInformation())
			        .orElse(Collections.emptyList())
			        .stream()
			        .filter(eachParty -> AppConstants.COMPANY.equalsIgnoreCase(eachParty.getPartyType()))
			        .findFirst()
			        .map(PartyInformation::getBasicDetails)
			        .map(BasicDetails::getAddress)
			        .flatMap(addressList -> addressList.stream().findFirst())
			        .map(Address::getAddressDetails)
			        .map(AddressDetails::getCity)
			        .orElse("");

		}else if(!CollectionUtils.isEmpty(proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress())){
			place = proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getCity();
		}
      	SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
      	format.setTimeZone(TimeZone.getTimeZone(AppConstants.APP_TIMEZONE));
      	String otpConfirmationDate = Optional.ofNullable(otpConfirmation).map(format::format).orElse(null);
      	Date sellerDeclarationSubmissionDate = Utility.otpDatePF(proposalDetails, sellerConsentDetailsRepository);
      	String otpConfirmationSellerDeclarationDate = Utility.getOTPforPF(proposalDetails, format, otpConfirmationDate, sellerDeclarationSubmissionDate);
      	String requestSource = proposalDetails.getAdditionalFlags().getRequestSource();
      	SellerConsentDetails sellerConsentDetails = sellerConsentDetailsRepository.findByPolicyNumber(proposalDetails.getApplicationDetails().getPolicyNumber());
      if ((sellerConsentDetails != null) && (AppConstants.REQUEST_SOURCE_THANOS2.equalsIgnoreCase(requestSource) || AppConstants.REQUEST_SOURCE_TELESALES.equalsIgnoreCase(requestSource))) {
				otpConfirmationSellerDeclarationDate = format.format(sellerConsentDetails.getLastModifiedDate());
      	}


      	dataVariables.put("otpConfirmation", otpConfirmationDate);
      	dataVariables.put("place", Utility.nullSafe(place));
      	dataVariables.put("otpConfirmationSellerDeclarationDate", otpConfirmationSellerDeclarationDate == null ? StringUtils.EMPTY : otpConfirmationSellerDeclarationDate);
      	dataVariables.put("signature", Utility.setAgentIDOrSpCode(proposalDetails));
      	dataVariables.put("AXIS", AppConstants.CHANNEL_AXIS.equalsIgnoreCase(channel));
      	dataVariables.put("YBL", AppConstants.CHANNEL_YBL.equalsIgnoreCase(channel));
      	dataVariables.put("isProspectiveYBLCustomer",
				Objects.nonNull(proposalDetails.getAdditionalFlags().getIsProspectiveYBLCustomer())
						? proposalDetails.getAdditionalFlags().getIsProspectiveYBLCustomer()
								: false);
      	dataVariables.put("THANOS2", (AppConstants.REQUEST_SOURCE_THANOS2.equalsIgnoreCase(requestSource) || AppConstants.REQUEST_SOURCE_TELESALES.equalsIgnoreCase(requestSource)));
    }

    private void setReplacementSaleData(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {
    	//
    	String formType = proposalDetails.getApplicationDetails().getFormType();
		String schemeType = proposalDetails.getApplicationDetails().getSchemeType();
		String channel = proposalDetails.getChannelDetails().getChannel();
    	String requestSource = proposalDetails.getAdditionalFlags().getRequestSource();
    	String agentName = "";
    	String replacementSale = StringUtils.EMPTY;
    	String isAgencyOrAxisOrYblChannel = "NO";
    	String specifiedPersonCode = StringUtils.EMPTY;
    	long agentMobileNumber = Optional.ofNullable(proposalDetails.getSourcingDetails().getAgentMobileNumber()).orElse(0L);
    	Date otpSubmission = null;
    	String versionDetails = "";
    	String pdfVersionNumber = AppConstants.PF_VERSION_HSA;
		String pdfVersion = AppConstants.PDF_VERSION_HSA;
		String bancaPartner = StringUtils.EMPTY;

    	if (Utility.orTwoExpressions(AppConstants.CHANNEL_AGENCY.equalsIgnoreCase(channel), AppConstants.CHANNEL_CAT.equalsIgnoreCase(channel))) {    ////FUL2-123815
			specifiedPersonCode = proposalDetails.getSourcingDetails().getSpecifiedPersonCode();
			isAgencyOrAxisOrYblChannel = "YES";
		}
    	if (AppConstants.CHANNEL_AXIS.equalsIgnoreCase(channel)) {
			agentName = proposalDetails.getSourcingDetails().getSpecifiedPersonDetails().getSpName();
			specifiedPersonCode = proposalDetails.getSourcingDetails().getSpecifiedPersonDetails()
					.getSpCertificateNumber();
			versionDetails = StringUtils.join(AppConstants.PF_VERSION_START_HSA, "AXIS/", pdfVersion,
					pdfVersionNumber);
			isAgencyOrAxisOrYblChannel = "YES";
			bancaPartner = "AXIS BANK";
		} else if (Utility.orTwoExpressions(AppConstants.CHANNEL_PEERLESS.equalsIgnoreCase(channel), AppConstants.CHANNEL_ASL.equalsIgnoreCase(channel))) {
			agentName = Optional.ofNullable(proposalDetails.getSourcingDetails().getAgentName()).orElse(AppConstants.BLANK);
			versionDetails = StringUtils.join(AppConstants.PF_VERSION_START_HSA, channelName.get(channel), "/", pdfVersion, pdfVersionNumber);
		} else {
			agentName = Optional.ofNullable(proposalDetails.getSourcingDetails().getAgentName()).orElse(AppConstants.BLANK);
			if (Utility.orTwoExpressions(AppConstants.REQUEST_SOURCE_THANOS2.equalsIgnoreCase(requestSource), AppConstants.REQUEST_SOURCE_TELESALES.equalsIgnoreCase(requestSource))) {
				SellerConsentDetails sellerConsentDetails = sellerConsentDetailsRepository.findByPolicyNumber(proposalDetails.getApplicationDetails().getPolicyNumber());
				if (sellerConsentDetails != null) {
					agentName = sellerConsentDetails.getSellerName();
					specifiedPersonCode = sellerConsentDetails.getSpCode();
				} else {
					agentName = AppConstants.BLANK;
				}
			}
			versionDetails = StringUtils.join(AppConstants.PF_VERSION_START_HSA, pdfVersion, pdfVersionNumber);
		}
    	if (AppConstants.CHANNEL_YBL.equalsIgnoreCase(channel)) {
    		specifiedPersonCode = proposalDetails.getSourcingDetails().getSpecifiedPersonCode();
    		isAgencyOrAxisOrYblChannel = "YES";
    		bancaPartner = "YES BANK LIMITED";
    	}
    	specifiedPersonCode = Utility.evaluateConditionalOperation(AppConstants.CHANNEL_IMF.equalsIgnoreCase(channel), proposalDetails.getSourcingDetails().getSpecifiedPersonCode(), specifiedPersonCode);
    	replacementSale = Optional.ofNullable(proposalDetails.getAdditionalFlags().getReplacementSale()).orElse(replacementSale);
		if (Utility.andTwoExpressions(AppConstants.FORM3.equalsIgnoreCase(formType), !Utility.schemeBCase(schemeType))) {
			otpSubmission = Optional.ofNullable(proposalDetails.getPosvDetails().getPosvStatus().getAuthorizedSignatoryOTPSubmittedDate()).orElse(null);
		} else {

			otpSubmission = Optional.ofNullable(proposalDetails.getPosvDetails().getPosvStatus().getSubmittedOTPDate()).orElse(null);
		}
		SimpleDateFormat posvDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		posvDateFormat.setTimeZone(TimeZone.getTimeZone(AppConstants.APP_TIMEZONE));
		String otpSubmissionDate = Optional.ofNullable(otpSubmission).map(posvDateFormat::format).orElse(null);

    	dataVariables.put("isTelesalesRequest", AppConstants.AXIS_TELESALES_REQUEST.equalsIgnoreCase(requestSource));
    	dataVariables.put("agentName", agentName);
		dataVariables.put("replacementSale", Utility.evaluateConditionalOperation(("Y".equalsIgnoreCase(replacementSale)), AppConstants.YES, AppConstants.NO));
		dataVariables.put("isAgencyOrAxisOrYblChannel", isAgencyOrAxisOrYblChannel);
		dataVariables.put("spCode", specifiedPersonCode);
		dataVariables.put("posvOtpSubmission", otpSubmissionDate);
		dataVariables.put("agentMobile", agentMobileNumber);
		dataVariables.put("pdfVersion", versionDetails);
		dataVariables.put("bancaPartner", bancaPartner);
    }

    /**
     * Maps the variables used for agent advisor commission share in DECLARATION BY PRINCIPAL OFFICER/AGENT ADVISOR/SPECIFIED PERSON
     * @param proposalDetails
     * @param dataVariables
     * @throws UserHandledException
     */
    private void setAgentCommisionData(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {
    	//
    	String channel = proposalDetails.getChannelDetails().getChannel();
    	String agentName = (String) dataVariables.get("agentName");
    	String otpConfirmationSellerDeclarationDate = (String) dataVariables.get("otpConfirmationSellerDeclarationDate");
    	List<CommissionShare> commission = null;
	    List<CommissionShare> commissiondetails = new ArrayList<>();
		if (proposalDetails.getCsgDetails() != null && null != proposalDetails.getCsgDetails().getCommissionShare()) {
			commission = proposalDetails.getCsgDetails().getCommissionShare();
			commissiondetails = commission.stream().filter(c -> !StringUtils.isEmpty(c.getAgentId()))
					.collect(Collectors.toList());

		} else {
			CommissionShare commissionDetails = new CommissionShare();
			// FUL2-97686 set agentId as spCode for axis channel only
			commissionDetails.setAgentId(Utility.setAgentIDOrSpCode(proposalDetails));
			commissionDetails.setAgentName(agentName);
			// set signature as seller declaration date and time for all channel except neo
			commissionDetails.setSignature(!AppConstants.CHANNEL_NEO.equalsIgnoreCase(channel) ? otpConfirmationSellerDeclarationDate : Utility.setAgentIDOrSpCode(proposalDetails));
			commissionDetails.setPercentage(100);
			commissiondetails.add(commissionDetails);
		}
		dataVariables.put("agentCount", commissiondetails);
    }

}
