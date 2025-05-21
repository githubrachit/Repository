package com.mli.mpro.document.service.impl;
import static com.mli.mpro.productRestriction.util.AppConstants.ANNUITY_PRODUCTS;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.configuration.repository.ConfigurationRepository;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.document.models.SellerConsentDetails;
import com.mli.mpro.location.repository.SellerConsentDetailsRepository;
import com.mli.mpro.productRestriction.util.AppConstants;
import static com.mli.mpro.utils.Utility.nullSafe;
import static com.mli.mpro.utils.Utility.*;

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

import com.mli.mpro.proposal.models.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.context.Context;

import com.mli.mpro.utils.Utility;


@Service
public class DeclarationMapper {
	@Autowired
	private BaseMapper baseMapper;

	@Autowired
	private SellerConsentDetailsRepository sellerConsentDetailsRepository;

	@Value("${pf.vernacular.declaration.visibility.date}")
	private String deploymentDate;

	@Autowired
	private ConfigurationRepository configurationRepository;

    private static final Logger logger = LoggerFactory.getLogger(DeclarationMapper.class);
    Map<String, String> proposalFormVersion = new HashMap<String, String>() {

	private static final long serialVersionUID = 1L;
	{
	    put("A", AppConstants.PDF_VERSION);
	    put("F", AppConstants.PDF_VERSION);
	    put("T", AppConstants.PDF_VERSION);
	    put("K", AppConstants.PDF_VERSION);
	    put("B", AppConstants.PDF_VERSION);
	    put("B2", AppConstants.PDF_VERSION);
	    put("BY", AppConstants.PDF_VERSION);
	    put("X", AppConstants.PDF_VERSION);
	    put("P", AppConstants.PDF_VERSION);
	    put("LX", AppConstants.PDF_VERSION);
	}

    };
    
    // FUL2-7112 Peerless & ASL Updates
	Map<String, String> channelName = new HashMap<String, String>() {

		private static final long serialVersionUID = 2L;
		{
			put("LX", AppConstants.ASL);
			put("P", AppConstants.PEERLESS);
		}

	};

    public Context setDataOfDeclaration(ProposalDetails proposalDetails) throws UserHandledException {


	Map<String, Object> dataVariables = new HashMap<>();
	Context decalarationContext = new Context();
	logger.info("Mapping declaration details of proposal form document for transactionId {}", proposalDetails.getTransactionId());
	try {
		//NEORW: handle null pointer exception for PosvStatus object
		Date otpConfirmation = null;
		Date otpSubmission = null;
		
		boolean isPosSeller = proposalDetails.getSourcingDetails() != null && proposalDetails.getSourcingDetails().isPosSeller();
		String formType = proposalDetails.getApplicationDetails().getFormType();
		String schemeType = proposalDetails.getApplicationDetails().getSchemeType();
		//FUL2-170700 Brms Broker Integration
		BrmsFieldConfigurationDetails brmsFieldConfigurationDetails = Optional.ofNullable(proposalDetails)
				.map(ProposalDetails::getBrmsFieldConfigurationDetails)
				.orElse(null);
		String isBrmsApplicable = (brmsFieldConfigurationDetails != null && brmsFieldConfigurationDetails.getIsBRMSApiApplicable() != null)
				? brmsFieldConfigurationDetails.getIsBRMSApiApplicable()
				: AppConstants.BLANK;
		String digitalJourney = (brmsFieldConfigurationDetails != null && brmsFieldConfigurationDetails.getDigitalJourney() != null)
				? brmsFieldConfigurationDetails.getDigitalJourney()
				: AppConstants.BLANK;


		if(AppConstants.FORM3.equalsIgnoreCase(formType) && !Utility.schemeBCase(schemeType)) {
			//FUL2-146253 Capital Small Finance Bank
			if((AppConstants.YES.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getIsCSFBChannel())
			&& Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLE_CSFBTAGS)))
			          //FUL2-170700 Brms Broker Integration
				|| (AppConstants.Y.equalsIgnoreCase(isBrmsApplicable) && AppConstants.CDF_JOURNEY.equalsIgnoreCase(digitalJourney)
					&& Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLE_BRMS_BROKER)))){
				otpConfirmation =(Objects.nonNull(proposalDetails.getAdditionalFlags())
		                           && Objects.nonNull(proposalDetails.getAdditionalFlags().getCustomerSignDateForCDF()))
		                             ? proposalDetails.getAdditionalFlags().getCustomerSignDateForCDF()
		                             : null;

			}else {
				otpConfirmation = Objects.nonNull(proposalDetails.getPosvDetails()) && Objects.nonNull(proposalDetails.getPosvDetails().getPosvStatus())
						? proposalDetails.getPosvDetails().getPosvStatus().getAuthorizedSignatoryOTPSubmittedDate() : null;
			}
				otpSubmission = otpConfirmation;

		}else {
			//FUL2-146253 Capital Small Finance Bank
		otpConfirmation = selectDateToDisplay(proposalDetails);
		//FUL2-33746 printing posv submission date		
		otpSubmission = Objects.nonNull(proposalDetails.getPosvDetails()) && Objects.nonNull(proposalDetails.getPosvDetails().getPosvStatus())
				                     //FUL2-146253 Capital Small Finance Bank
						? isCSFB(proposalDetails) ?otpConfirmation: proposalDetails.getPosvDetails().getPosvStatus().getSubmittedOTPDate()
				         : null;
		}
	    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	    SimpleDateFormat posvDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		format.setTimeZone(TimeZone.getTimeZone(AppConstants.APP_TIMEZONE));
		posvDateFormat.setTimeZone(TimeZone.getTimeZone(AppConstants.APP_TIMEZONE));
		String otpConfirmationDate = otpConfirmation != null ? format.format(otpConfirmation) : StringUtils.EMPTY;
		String otpSubmissionDate = otpConfirmation != null ? posvDateFormat.format(otpSubmission) : StringUtils.EMPTY;
		Date sellerDeclarationSubmissionDate = Utility.otpDatePF(proposalDetails, sellerConsentDetailsRepository);
		String otpConfirmationSellerDeclarationDate = "";
		logger.info("seller declaration date : {}",sellerDeclarationSubmissionDate);
		otpConfirmationSellerDeclarationDate = Utility.getOTPforPF(proposalDetails, format, otpConfirmationDate, sellerDeclarationSubmissionDate);
		String place = StringUtils.EMPTY;
		if (AppConstants.FORM3.equalsIgnoreCase(proposalDetails.getApplicationDetails().getFormType())
				&& !Utility.schemeBCase(proposalDetails.getApplicationDetails().getSchemeType())) {
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
		Boolean isSwpWli = AppConstants.WHOLE_LIFE_INCOME.equalsIgnoreCase(proposalDetails.getProductDetails().get(0).getProductInfo().getVariant());
		String agentName ;
		String versionDetails ;
		String channel = proposalDetails.getChannelDetails().getChannel();
		String requestSource = proposalDetails.getAdditionalFlags().getRequestSource();
		String pdfVersion;
		String agentKnowsUnitType;
		String agentKnowsProposer;
		String agentKnowsProposerSince;
		String specifiedPersonCode = StringUtils.EMPTY;
		String cipPfVersion=StringUtils.EMPTY;
		String isAgencyOrAxisOrYblChannel = "NO";
		String bancaPartner = StringUtils.EMPTY;
		String productId= proposalDetails.getProductDetails().get(0).getProductInfo().getProductId();
		boolean isNeoOrAggregator = channel.equalsIgnoreCase(AppConstants.CHANNEL_NEO)
				|| channel.equalsIgnoreCase(AppConstants.CHANNEL_AGGREGATOR);
		boolean notNeoNotAggregatorIsTradProduct = !isNeoOrAggregator && AppConstants.TRADITIONAL.equalsIgnoreCase(proposalDetails.getProductDetails().get(0).getProductType());
		String pdfVersionNumber = notNeoNotAggregatorIsTradProduct ? AppConstants.PF_VERSION_TRAD : AppConstants.PF_VERSION;
		pdfVersion = notNeoNotAggregatorIsTradProduct ? AppConstants.PDF_VERSION_TRAD : AppConstants.PDF_VERSION;
		if (productId.equalsIgnoreCase("108")) {
			pdfVersion = "0720";
		}
		//FUL2-123815
		if (AppConstants.CHANNEL_AGENCY.equalsIgnoreCase(channel) || AppConstants.CHANNEL_CAT.equalsIgnoreCase(channel)) {
			specifiedPersonCode = proposalDetails.getSourcingDetails().getSpecifiedPersonCode();
			isAgencyOrAxisOrYblChannel = "YES";
		}
		agentKnowsUnitType = Objects.nonNull(proposalDetails.getAdditionalFlags()) ?
				proposalDetails.getAdditionalFlags().getAgentKnowsProposerUnitType() : AppConstants.BLANK;
		agentKnowsProposerSince = Objects.nonNull(proposalDetails.getAdditionalFlags()) ?
				proposalDetails.getAdditionalFlags().getAgentKnowsProposerSince() : AppConstants.BLANK;
		agentKnowsProposer = !StringUtils.isEmpty(agentKnowsProposerSince) ?
				agentKnowsUnitType.concat(AppConstants.WHITE_SPACE).concat(agentKnowsProposerSince.toUpperCase()) : AppConstants.BLANK;

		if (AppConstants.CHANNEL_AXIS.equalsIgnoreCase(channel)) {
			if(Utility.isDIYJourney(proposalDetails)){
				agentName=Utility.diyStaticID(proposalDetails.getAdditionalFlags().getSourceChannel(),1);
			}else{
				agentName = proposalDetails.getSourcingDetails().getSpecifiedPersonDetails().getSpName();
			}
			specifiedPersonCode =
					proposalDetails.getSourcingDetails().getSpecifiedPersonDetails().getSpCertificateNumber();
			versionDetails = StringUtils.join(AppConstants.PF_VERSION_START, "AXIS/", pdfVersion,
					pdfVersionNumber);
			isAgencyOrAxisOrYblChannel = "YES";
		} else if (AppConstants.CHANNEL_PEERLESS.equalsIgnoreCase(channel) || AppConstants.CHANNEL_ASL.equalsIgnoreCase(channel)) {
			agentName = Objects.nonNull(proposalDetails.getSourcingDetails()) ?
					proposalDetails.getSourcingDetails().getAgentName() : AppConstants.BLANK;
			versionDetails = StringUtils.join(AppConstants.PF_VERSION_START, channelName.get(channel), "/",
					pdfVersion
					, pdfVersionNumber);
		} else {
			agentName = Objects.nonNull(proposalDetails.getSourcingDetails()) ?
					proposalDetails.getSourcingDetails().getAgentName() : AppConstants.BLANK;

			if(AppConstants.REQUEST_SOURCE_THANOS2.equalsIgnoreCase(requestSource) || AppConstants.REQUEST_SOURCE_TELESALES.equalsIgnoreCase(requestSource)){

				SellerConsentDetails sellerConsentDetails = sellerConsentDetailsRepository.findByPolicyNumber(proposalDetails.getApplicationDetails().getPolicyNumber());
				if(sellerConsentDetails != null){
					agentName=sellerConsentDetails.getSellerName();
					agentKnowsUnitType = sellerConsentDetails.getPeriodUnit();
					agentKnowsProposerSince = String.valueOf(sellerConsentDetails.getPeriod());
					agentKnowsProposer = !StringUtils.isEmpty(agentKnowsProposerSince) ?
							agentKnowsProposerSince.concat(AppConstants.WHITE_SPACE).concat( !StringUtils.isEmpty(agentKnowsUnitType) ? agentKnowsUnitType.toUpperCase() : AppConstants.BLANK) : AppConstants.BLANK;
					otpConfirmationSellerDeclarationDate=format.format(sellerConsentDetails.getLastModifiedDate());
					specifiedPersonCode = sellerConsentDetails.getSpCode();
				}
				else{
					agentName=AppConstants.BLANK;
					agentKnowsProposer=AppConstants.BLANK;
				}
				}
			//FUL2-75012 removing channel name from (PF_VERSION_START+channel+"/"+pdfVersion+pdfVersionNumber)
			versionDetails = StringUtils.join(AppConstants.PF_VERSION_START, pdfVersion, pdfVersionNumber);
		}

		//FUL2-121187 POS Proposal Form Observations
		if(ANNUITY_PRODUCTS.contains(proposalDetails.getProductDetails().get(0).getProductInfo().getProductId())) {
			if(isPosSeller) {
				versionDetails = AppConstants.GLIP_POS_PF_VERSION;
			} else {
				versionDetails = AppConstants.GLIP_PF_VERSION;
			}
		}
		if (AppConstants.CHANNEL_YBL.equalsIgnoreCase(channel)) {
		specifiedPersonCode = proposalDetails.getSourcingDetails().getSpecifiedPersonCode();
		isAgencyOrAxisOrYblChannel = "YES";
	    }
		// FUL2-140172 map SpCode and POSP code in PF form for IMF Channel
		if(AppConstants.CHANNEL_IMF.equalsIgnoreCase(channel)){
			specifiedPersonCode = proposalDetails.getSourcingDetails().getSpecifiedPersonCode();
		}
	    long agentMobileNumber = Objects.nonNull(proposalDetails.getSourcingDetails()) ?
				proposalDetails.getSourcingDetails().getAgentMobileNumber() : 0;
	    String replacementSale = StringUtils.EMPTY;
		/*FUL2-12464 Updating Replacement sale*/
		if (Objects.nonNull(proposalDetails.getAdditionalFlags())) {
			replacementSale = proposalDetails.getAdditionalFlags().getReplacementSale();
		}
	    List<CommissionShare> commission = null;
	    List<CommissionShare> commissiondetails = new ArrayList<>();
		if (proposalDetails.getCsgDetails() != null && null!=proposalDetails.getCsgDetails().getCommissionShare()) {
		commission = proposalDetails.getCsgDetails().getCommissionShare();
		commissiondetails=commission.stream().filter(c->!StringUtils.isEmpty(c.getAgentId())).collect(Collectors.toList());

	    } else {
		CommissionShare commissionDetails = new CommissionShare();
			// FUL2-97686 set agentId as spCode for axis channel only
			commissionDetails.setAgentId(Utility.setAgentIDOrSpCode(proposalDetails));
		commissionDetails.setAgentName(agentName);
			// set signature as seller declaration date and time for all channel except neo
			commissionDetails.setSignature(!AppConstants.CHANNEL_NEO.equalsIgnoreCase(channel)?otpConfirmationSellerDeclarationDate:Utility.setAgentIDOrSpCode(proposalDetails));
		commissionDetails.setPercentage(100);
		commissiondetails.add(commissionDetails);
	    }

	    if(productId.equalsIgnoreCase("86") && !AppConstants.CHANNEL_PEERLESS.equalsIgnoreCase(channel) && !AppConstants.CHANNEL_ASL.equalsIgnoreCase(channel)){
			cipPfVersion =
					channel.equalsIgnoreCase(AppConstants.CHANNEL_AXIS)
							?AppConstants.CIP_AXIS_PFVERSION
							:"Cancer Insurance/mPro/".concat(channel).concat("/0619/Ver1.0");
		}else if(productId.equalsIgnoreCase("86") && (AppConstants.CHANNEL_PEERLESS.equalsIgnoreCase(channel) || AppConstants.CHANNEL_ASL.equalsIgnoreCase(channel))) {
			cipPfVersion =
					channel.equalsIgnoreCase(AppConstants.CHANNEL_AXIS)
							?AppConstants.CIP_AXIS_PFVERSION
							:"Cancer Insurance/mPro/".concat(channelName.get(channel)).concat("/0619/Ver1.0");
		}
	    
	    if (StringUtils.equalsIgnoreCase(channel, AppConstants.CHANNEL_AXIS)) {
		bancaPartner = "AXIS BANK";
	    } else if (StringUtils.equalsIgnoreCase(channel, AppConstants.CHANNEL_YBL)) {
		bancaPartner = "YES BANK LIMITED";
	    }
	    
	    dataVariables.put("agentName", agentName);
	    dataVariables.put("otpConfirmation", otpConfirmation != null ? posvDateFormat.format(otpConfirmation) : StringUtils.EMPTY);
	    dataVariables.put("posvOtpSubmission", otpSubmissionDate);
	    dataVariables.put("otpConfirmationSellerDeclarationDate", otpConfirmationSellerDeclarationDate == null ? StringUtils.EMPTY : otpConfirmationSellerDeclarationDate);
	    dataVariables.put("place", nullSafe(place));
	    /*FUL2-12464 Updating Replacement sale : Updating logic as per policy splitting api response*/
		if ("Y".equalsIgnoreCase(replacementSale)) {
			dataVariables.put("replacementSale", AppConstants.YES);
		} else {
			dataVariables.put("replacementSale", AppConstants.NO);
		}
		//FUL2-97686 - map signature as seller declaration date and time for all channel other than neo
		if(!AppConstants.CHANNEL_NEO.equalsIgnoreCase(channel)){
			dataVariables.put("signature", Objects.nonNull(proposalDetails) ? otpConfirmationSellerDeclarationDate : AppConstants.BLANK);
		}else{
			dataVariables.put("signature",Objects.nonNull(proposalDetails.getSourcingDetails()) ? proposalDetails.getSourcingDetails().getAgentId() : AppConstants.BLANK);
		}
		dataVariables.put("licence", specifiedPersonCode);
	    dataVariables.put("agentMobile", agentMobileNumber);
	    dataVariables.put("agentCount", commissiondetails);
	    dataVariables.put("pdfVersion", versionDetails);
	    //NEORW: get date from payment details object
	    if ((AppConstants.CHANNEL_NEO.equalsIgnoreCase(channel)
				|| AppConstants.CHANNEL_AGGREGATOR.equalsIgnoreCase(channel))
				&& Objects.nonNull(proposalDetails.getApplicationDetails())
				&& Objects.nonNull(proposalDetails.getPaymentDetails())
				&& Objects.nonNull(proposalDetails.getPaymentDetails().getReceipt())
				&& !proposalDetails.getPaymentDetails().getReceipt().isEmpty()) {
			dataVariables.put("date", Utility.getDateOnTheBasisOfRateChange(proposalDetails, true));
			dataVariables.put("agentAdvisorName", Utility.setAgentAdvisorName(proposalDetails));
		} else {
			dataVariables.put("date", baseMapper.getPFGeneratedDate());
		}
	    dataVariables.put("THANOS2",(AppConstants.REQUEST_SOURCE_THANOS2.equalsIgnoreCase(requestSource) || AppConstants.REQUEST_SOURCE_TELESALES.equalsIgnoreCase(requestSource)));
	    dataVariables.put("time", baseMapper.getPfGeneratedTime());
	    dataVariables.put("AXIS", AppConstants.CHANNEL_AXIS.equalsIgnoreCase(channel));
		  dataVariables.put("isTelesalesRequest", AppConstants.AXIS_TELESALES_REQUEST.equalsIgnoreCase(requestSource));
	    dataVariables.put("YBL", AppConstants.CHANNEL_YBL.equalsIgnoreCase(channel));
		dataVariables.put("isProspectiveYBLCustomer",
				Objects.nonNull(proposalDetails.getAdditionalFlags().getIsProspectiveYBLCustomer())
						? proposalDetails.getAdditionalFlags().getIsProspectiveYBLCustomer()
						: false);
	    dataVariables.put("agentKnowsUnit", agentKnowsProposer);
	    dataVariables.put("cipPfVersion", cipPfVersion);
		// set agentCode as spCode for axis channel and agentId for non-axis channel
		dataVariables.put("agentCode", Objects.nonNull(proposalDetails.getSourcingDetails()) ? Utility.setAgentIDOrSpCode(proposalDetails): AppConstants.BLANK);
		dataVariables.put("bancaPartner", bancaPartner);
	    dataVariables.put("isSwpWli", isSwpWli);
	    dataVariables.put("spCode", specifiedPersonCode);
	    dataVariables.put("isAgencyOrAxisOrYblChannel", isAgencyOrAxisOrYblChannel);
		dataVariables.put("isNotYBLProposal", !Utility.isYBLProposal(proposalDetails));
	    dataVariables.put("isSSP", AppConstants.SSP_PRODUCT_ID.equalsIgnoreCase(productId));
		//FUL2-69831-Prioritise the Pan number before AADHAR
		String aadhaarOrPAN = Objects.nonNull(proposalDetails.getSourcingDetails()) ? proposalDetails.getSourcingDetails().getAgentPAN(): AppConstants.BLANK;
		if (StringUtils.isEmpty(aadhaarOrPAN)) {
			aadhaarOrPAN = Objects.nonNull(proposalDetails.getSourcingDetails()) ? proposalDetails.getSourcingDetails().getAgentAadhaar()  : AppConstants.BLANK;
		}
		dataVariables.put("aadhaarOrPAN", aadhaarOrPAN);
		dataVariables.put(AppConstants.IS_NOT_YBL_PROPOSAL, !Utility.isYBLProposal(proposalDetails));
		dataVariables.put("formType", proposalDetails.getApplicationDetails().getFormType().toUpperCase());
		//FUL2-32221 PEP changes
		if (AppConstants.CIP_PRODUCT_ID.equals(productId)) {
			Utility.addPepDetails(proposalDetails,dataVariables);
		}
		//FUL2-46150 Vernacular Declaration Addition
		boolean visibleVernacular = false;
		if(!isNeoOrAggregator && StringUtils.isNotEmpty(deploymentDate)){
			visibleVernacular = Utility.newPFDeclaration(proposalDetails, deploymentDate);
		}
		dataVariables.put("visibleVernacular", visibleVernacular);

		//FUL2-211284_IRDA_PF_Changes
		if(!isNeoOrAggregator) {
			dataVariables = paymentFirstConsentTagApplicability(proposalDetails, dataVariables);
			Utility.setDataForVernacularAndDisabilityDeclaration(proposalDetails, dataVariables);
		}
		if (isNeoOrAggregator && Objects.nonNull(proposalDetails.getNomineeDetails())) {
			List<PartyDetails> partyDetailsExtracted = proposalDetails.getNomineeDetails().getPartyDetails();
			if (!CollectionUtils.isEmpty(partyDetailsExtracted)) {
				List<NomineeAppointeePfDetails> pfDetails = new ArrayList<>();
				initializePfDetails(pfDetails);
				int i = 0;
				for (PartyDetails partyDetails : partyDetailsExtracted) {
					if (Objects.nonNull(partyDetails)) {
						updateBankDetails(partyDetails, pfDetails, i);
						updateNomineeAddressDetails(partyDetails, pfDetails, i);
						updateAppointeeAddressDetails(partyDetails, pfDetails, i);
						updateMobileAndEmailDetails(partyDetails, pfDetails, i);
						i++;
					}
				}
				dataVariables.put("nomineeDetails", pfDetails);
			}
		}
		decalarationContext.setVariables(dataVariables);
	} catch (Exception ex) {
		logger.error("Data addition failed for Proposal Form Document:",ex);
	    List<String> errorMessages = new ArrayList<String>();
	    errorMessages.add("Data addition failed");
	    throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	logger.info("Mapping declaration details of proposal form document is completed successfully for transactionId {}", proposalDetails.getTransactionId());
	return decalarationContext;
    }

	private  Map<String,Object> paymentFirstConsentTagApplicability(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {
		Boolean enablePaymentFirstConsent = FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.PAYMENT_FIRST_CONSENT);
		logger.info(" enablePaymentFirstConsent flag for transaction id {} and flag value is {}: ", proposalDetails.getTransactionId(), enablePaymentFirstConsent);
		Boolean isPaymentFirstNonWipCase = compareWipCaseDate(proposalDetails.getTransactionId(), proposalDetails.getApplicationDetails().getCreatedTime(), AppConstants.PAYMENT_FIRST_WIP_CASE);
		if (Boolean.TRUE.equals(enablePaymentFirstConsent)
				&& isPaymentFirstNonWipCase) {
			logger.info(" Inside payment first consent for transaction id : {}  ", proposalDetails.getTransactionId());
			dataVariables.put("isPaymentFirstConsent", proposalDetails.getAdditionalFlags().isPaymentFirstConsent());
			dataVariables.put(AppConstants.IS_PAYMENT_FIRST_NON_WIP_CASE, true);
		} else {
			dataVariables.put("isPaymentFirstConsent", false);
			dataVariables.put(AppConstants.IS_PAYMENT_FIRST_NON_WIP_CASE,false);
		}
		return dataVariables;
	}

	private Date selectDateToDisplay(ProposalDetails proposalDetails){
		    //FUL2-170700 Brms Broker Integration
		BrmsFieldConfigurationDetails brmsFieldConfigurationDetails = Optional.ofNullable(proposalDetails)
				.map(ProposalDetails::getBrmsFieldConfigurationDetails)
				.orElse(null);
		String isBrmsApplicable = (brmsFieldConfigurationDetails != null && brmsFieldConfigurationDetails.getIsBRMSApiApplicable() != null)
				? brmsFieldConfigurationDetails.getIsBRMSApiApplicable()
				: AppConstants.BLANK;
		String digitalJourney = (brmsFieldConfigurationDetails != null && brmsFieldConfigurationDetails.getDigitalJourney() != null)
				? brmsFieldConfigurationDetails.getDigitalJourney()
				: AppConstants.BLANK;

		if((AppConstants.YES.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getIsCSFBChannel())
		&& Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLE_CSFBTAGS)))
			//FUL2-170700 Brms Broker Integration
			|| (AppConstants.Y.equalsIgnoreCase(isBrmsApplicable) && AppConstants.CDF_JOURNEY.equalsIgnoreCase(digitalJourney)
			&& Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLE_BRMS_BROKER)))){

		return Objects.nonNull(proposalDetails.getAdditionalFlags())
				&& Objects.nonNull(proposalDetails.getAdditionalFlags().getCustomerSignDateForCDF())
				? proposalDetails.getAdditionalFlags().getCustomerSignDateForCDF() : null;
	}
	return 	Objects.nonNull(proposalDetails.getPosvDetails()) && Objects.nonNull(proposalDetails.getPosvDetails().getPosvStatus())
				? proposalDetails.getPosvDetails().getPosvStatus().getGeneratedOTPDate() : null;

	}
	private boolean isCSFB(ProposalDetails proposalDetails){
		//FUL2-170700 Brms Broker Integration
		BrmsFieldConfigurationDetails brmsFieldConfigurationDetails = Optional.ofNullable(proposalDetails)
				.map(ProposalDetails::getBrmsFieldConfigurationDetails)
				.orElse(null);
		String isBrmsApplicable = (brmsFieldConfigurationDetails != null && brmsFieldConfigurationDetails.getIsBRMSApiApplicable() != null)
				? brmsFieldConfigurationDetails.getIsBRMSApiApplicable()
				: AppConstants.BLANK;
		String digitalJourney = (brmsFieldConfigurationDetails != null && brmsFieldConfigurationDetails.getDigitalJourney() != null)
				? brmsFieldConfigurationDetails.getDigitalJourney()
				: AppConstants.BLANK;

		return (Objects.nonNull(proposalDetails.getAdditionalFlags())
				&& Objects.nonNull(proposalDetails.getAdditionalFlags().getIsCSFBChannel())
				&& Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLE_CSFBTAGS))
				&& AppConstants.YES.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getIsCSFBChannel())
				//FUL2-170700 Brms Broker Integration
				|| (AppConstants.Y.equalsIgnoreCase(isBrmsApplicable) && AppConstants.CDF_JOURNEY.equalsIgnoreCase(digitalJourney)
				&& Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLE_BRMS_BROKER))));
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

}
