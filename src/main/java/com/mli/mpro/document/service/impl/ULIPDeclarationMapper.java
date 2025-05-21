package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.configuration.repository.ConfigurationRepository;
import com.mli.mpro.configuration.service.UIConfigurationService;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.document.utils.ProposalFormChannelVersions;
import com.mli.mpro.location.repository.SellerConsentDetailsRepository;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.*;
import com.mli.mpro.utils.Utility;
import com.mli.mpro.utils.UtilityService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.context.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mli.mpro.utils.Utility.*;
import static com.mli.mpro.utils.Utility.updateAppointeeAddressDetails;

/**Mapper class for Declaration Details of ULIP Proposal Form
 * @author akshom4375
 */
@Service
public class ULIPDeclarationMapper {

	@Autowired
	private BaseMapper baseMapper;

	@Autowired
	UIConfigurationService uiConfigurationService;

	@Autowired
	UtilityService utilityService;

	@Autowired
	private SellerConsentDetailsRepository sellerConsentDetailsRepository;

	@Autowired
	private ConfigurationRepository configurationRepository;

	@Value("${pf.vernacular.declaration.visibility.date}")
	private String deploymentDate;

	private static final Logger logger = LoggerFactory.getLogger(ULIPDeclarationMapper.class);
    private boolean isNeoOrAggregator = false;

    /**
     * Setting data in Context for Declaration section of ULIP Proposal Form
     *
     * @param proposalDetails
     * @return
     */
    public Context setDataOfDeclaration(ProposalDetails proposalDetails) throws UserHandledException {
	logger.info("START setDataOfDeclaration {}", "%m");
	Map<String, Object> dataVariables = new HashMap<>();

	isNeoOrAggregator = proposalDetails.getChannelDetails().getChannel().equalsIgnoreCase(AppConstants.CHANNEL_NEO)
			|| proposalDetails.getChannelDetails().getChannel().equalsIgnoreCase(AppConstants.CHANNEL_AGGREGATOR);
	logger.info("is Neo or Aggregator : {}",isNeoOrAggregator);

	String place = StringUtils.EMPTY;
	String agentName = StringUtils.EMPTY;
	String signature = StringUtils.EMPTY;
	String bancaPartner = StringUtils.EMPTY;
	String specifiedPersonCode = StringUtils.EMPTY;
	String agentCode = StringUtils.EMPTY;
	String agentMobileNumber = StringUtils.EMPTY;
	String declarationVersionDate = StringUtils.EMPTY;
	String otpConfirmationDate =StringUtils.EMPTY;
	//FUL2-144677
	String otpConfirmationDateOnly = StringUtils.EMPTY;
	String otpConfirmationSellerDeclarationDate = StringUtils.EMPTY;
	/*FUL2-12464 Updating Replacement sale*/
	String replacementSale = StringUtils.EMPTY;
	String productId = proposalDetails.getProductDetails().get(0).getProductInfo().getProductId();
	// channel ver date
	String channel = proposalDetails.getChannelDetails().getChannel();
	String requestSource = proposalDetails.getAdditionalFlags().getRequestSource();
	channel = StringUtils.upperCase(channel);
	switch (channel) {
	case "A":
	    declarationVersionDate = ProposalFormChannelVersions.A.getChannelVersion();
	    break;
	case "F":
	    declarationVersionDate = ProposalFormChannelVersions.F.getChannelVersion();
	    break;
	case "K":
	    declarationVersionDate = ProposalFormChannelVersions.K.getChannelVersion();
	    break;
	case "T":
	    declarationVersionDate = ProposalFormChannelVersions.T.getChannelVersion();
	    break;
	case "BY":
	    declarationVersionDate = ProposalFormChannelVersions.BY.getChannelVersion();
	    break;
	case "X":
	    declarationVersionDate = ProposalFormChannelVersions.X.getChannelVersion();
	    break;
	case "B":
	    declarationVersionDate = ProposalFormChannelVersions.B.getChannelVersion();
	    break;
	case "B2":
	    declarationVersionDate = ProposalFormChannelVersions.B2.getChannelVersion();
	    break;
	case "P":
	    declarationVersionDate = ProposalFormChannelVersions.P.getChannelVersion();
	    break;
	case "LX":
	    declarationVersionDate = ProposalFormChannelVersions.LX.getChannelVersion();
	    break;
	case AppConstants.CHANNEL_NEO:
		declarationVersionDate = ProposalFormChannelVersions.NEO.getChannelVersion(); // added channel version for NEO
		break;
	case AppConstants.CHANNEL_AGGREGATOR:
		declarationVersionDate = ProposalFormChannelVersions.AGGREGATOR.getChannelVersion(); // added channel version for Aggregators
		break;
	default:
        declarationVersionDate= com.mli.mpro.utils.Utility.evaluateConditionalOperation(productId.equals("108"),"0519/",declarationVersionDate);

    }

	try {
		String formType = proposalDetails.getApplicationDetails().getFormType();
		String schemeType = proposalDetails.getApplicationDetails().getSchemeType();
		//NEORW: get date from payment details object
		if (isNeoOrAggregator
				&& Objects.nonNull(proposalDetails.getPaymentDetails())
				&& Objects.nonNull(proposalDetails.getPaymentDetails().getReceipt())
				&& !proposalDetails.getPaymentDetails().getReceipt().isEmpty()) {
			logger.info("Fetching date from payment details object");
			String paymentDate = Utility.nullSafe(proposalDetails.getPaymentDetails().getReceipt().get(0).getPaymentDate());
			otpConfirmationDate = Utility.dateFormatter(paymentDate, AppConstants.CHANNEL_AGGREGATOR.equalsIgnoreCase(channel)
					? AppConstants.DD_MM_YYYY_HYPHEN : AppConstants.DD_MM_YYYY_HH_MM_SS_HYPHEN, AppConstants.DD_MM_YYYY_SLASH);
		} else {
			//FUL2-146253 Capital Small Finance Bank
			Date otpConfirmation = setOtpConfirmation(proposalDetails);

			logger.info("OTP Confirmation Date :: {}",otpConfirmation);
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			format.setTimeZone(TimeZone.getTimeZone(AppConstants.APP_TIMEZONE));
			if(AppConstants.FORM3.equalsIgnoreCase(formType) && !Utility.schemeBCase(schemeType)) {
				//FUL2-146253 Capital Small Finance Bank
				//FUL2-170700 Brms Broker Integration
				if(!((AppConstants.YES.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getIsCSFBChannel())
					&& Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLE_CSFBTAGS)))
						||Utility.brmsBrokerEligibility(proposalDetails))) {

					otpConfirmation = Objects.nonNull(proposalDetails.getPosvDetails()) && Objects.nonNull(proposalDetails.getPosvDetails().getPosvStatus())
							? proposalDetails.getPosvDetails().getPosvStatus().getAuthorizedSignatoryOTPSubmittedDate() : null;
				}
				otpConfirmationDate = otpConfirmation != null ? format.format(otpConfirmation) : StringUtils.EMPTY;
			} else {
				otpConfirmationDate = otpConfirmation != null ? format.format(otpConfirmation) : StringUtils.EMPTY;
				Date sellerDeclarationSubmissionDate = Utility.otpDatePF(proposalDetails, sellerConsentDetailsRepository);
				logger.info("seller declaration date : {}",sellerDeclarationSubmissionDate);
				otpConfirmationSellerDeclarationDate = Utility.getOTPforPF(proposalDetails, format, otpConfirmationDate, sellerDeclarationSubmissionDate);
				logger.info("OTP confirmation date after parsing:: {}", otpConfirmationDate);
				logger.info("otp Confirmation Seller DeclarationDate {}",otpConfirmationSellerDeclarationDate);
			}

		}
	} catch (Exception ex) {
	    // Do Nothing: Date not present or is invalid
	    logger.error("GeneratedOTPDate value is invalid:",ex);
	}
	if(!isNeoOrAggregator){
		dataVariables = paymentFirstConsentTagApplicability(proposalDetails, dataVariables);
	}

	if (!isNeoOrAggregator && null != proposalDetails.getSourcingDetails()) {
        specifiedPersonCode= com.mli.mpro.utils.Utility.evaluateConditionalOperation(
                AppConstants.CHANNEL_YBL.equalsIgnoreCase(channel),
                proposalDetails.getSourcingDetails().getSpecifiedPersonDetails().getSpCode(),
                proposalDetails.getSourcingDetails().getSpecifiedPersonDetails().getSpCertificateNumber());

        Long agentMobileNumberLong = proposalDetails.getSourcingDetails().getAgentMobileNumber();
        agentMobileNumber = com.mli.mpro.utils.Utility.evaluateConditionalOperation(
                null != agentMobileNumberLong,String.valueOf(agentMobileNumberLong),StringUtils.EMPTY);

        agentCode = com.mli.mpro.utils.Utility.evaluateConditionalOperation(StringUtils.isNotBlank(proposalDetails.getSourcingDetails().getAgentCode()), proposalDetails.getSourcingDetails().getAgentCode()
                , proposalDetails.getSourcingDetails().getAgentId());

		// setting signature as seller declaration date and time for all channel except neo
		signature = !AppConstants.CHANNEL_NEO.equalsIgnoreCase(channel)
				?otpConfirmationSellerDeclarationDate
				:proposalDetails.getSourcingDetails().getAgentId();

        agentName = AppConstants.CHANNEL_AXIS.equalsIgnoreCase(channel)
                ?proposalDetails.getSourcingDetails().getSpecifiedPersonDetails().getSpName()
                :proposalDetails.getSourcingDetails().getAgentName();
    }

	/*FUL2-12464 Updating Replacement sale*/
	if (Objects.nonNull(proposalDetails.getAdditionalFlags())) {
		replacementSale = proposalDetails.getAdditionalFlags().getReplacementSale();
	}


    List<CommissionShare> commission = null;
	List<CommissionShare> commissiondetails = new ArrayList<>();
	if (null != proposalDetails.getCsgDetails() && Objects.nonNull(proposalDetails.getCsgDetails().getCommissionShare())) {
	    commission = proposalDetails.getCsgDetails().getCommissionShare();
	    for (int i = 0; i < commission.size(); i++) {
		if (!StringUtils.isEmpty(commission.get(i).getAgentId())) {
		    commissiondetails.add(commission.get(i));
		}
	    }

	} else {
	    CommissionShare commissionDetails = new CommissionShare();
		// set agentId as spcode for axis channel
		commissionDetails.setAgentId(Utility.setAgentIDOrSpCode(proposalDetails));
	    commissionDetails.setAgentName(agentName);
	    commissionDetails.setPercentage(100);
	    commissiondetails.add(commissionDetails);
	}

	String communicationCity = AppConstants.WHITE_SPACE;
	try {
	    communicationCity = proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getCity();
	} catch (NullPointerException npe) {
	    /** Do Nothing */
	    logger.error("Unable to find communicaion City:",npe);
	}

	if (StringUtils.equalsIgnoreCase(channel, AppConstants.CHANNEL_AXIS)) {
	    bancaPartner = "AXIS BANK";
	} else if (StringUtils.equalsIgnoreCase(channel, AppConstants.CHANNEL_YBL)) {
	    bancaPartner = "YES BANK LIMITED";
	}

	String agentKnowsProposerUnitType = proposalDetails.getAdditionalFlags().getAgentKnowsProposerUnitType();
	String agentKnowsProposerSince = proposalDetails.getAdditionalFlags().getAgentKnowsProposerSince();

	// Setting in dataMap
	logger.info("Setting ULIP Declaration data details in context map");
	dataVariables.put("agentCount", commissiondetails);
	dataVariables.put("agentKnowsUnit", String.join(AppConstants.WHITE_SPACE, agentKnowsProposerUnitType,
		StringUtils.isNotBlank(agentKnowsProposerSince) ? agentKnowsProposerSince : AppConstants.WHITE_SPACE));

	// Setting channel name
	if(AppConstants.CHANNEL_PEERLESS.equalsIgnoreCase(channel)) {
		channel = AppConstants.PEERLESS;
	}else if(AppConstants.CHANNEL_ASL.equalsIgnoreCase(channel)) {
		channel = AppConstants.ASL;
	}

	dataVariables.put("agentMobileNumber", agentMobileNumber);
	dataVariables.put("agentName", agentName);
	dataVariables.put("AXIS", AppConstants.CHANNEL_AXIS.equalsIgnoreCase(channel) );
	dataVariables.put("isTelesalesRequest", AppConstants.AXIS_TELESALES_REQUEST.equalsIgnoreCase(requestSource) );
	dataVariables.put("communicationCity", communicationCity);
	dataVariables.put("channel", channel);
	dataVariables.put("declarationAgentCode", agentCode);
	dataVariables.put("declarationVersionDate", declarationVersionDate);
	dataVariables.put("otpConfirmationDate", otpConfirmationDate);
	//FUL2-144677 start
	SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	try {
		if(StringUtils.isNotEmpty(otpConfirmationDate)) {
			Date date = format.parse(otpConfirmationDate);
			format.applyPattern(AppConstants.DD_MM_YYYY_SLASH);
			otpConfirmationDateOnly = format.format(date);
		} else {
			logger.error("otpConfirmationDate is empty.");
		}
	} catch (ParseException e) {
		logger.error("Error in Date Formating {}, otpConfirmationDate is {}",otpConfirmationDateOnly, otpConfirmationDate);
		otpConfirmationDateOnly = otpConfirmationDate;
	}


	dataVariables.put("otpConfirmationDateOnly", otpConfirmationDateOnly);
	//FUL2-144677 End
	dataVariables.put("otpConfirmationDate", otpConfirmationDate);
	dataVariables.put("otpConfirmationSellerDeclarationDate", otpConfirmationSellerDeclarationDate == null ? StringUtils.EMPTY : otpConfirmationSellerDeclarationDate);
		/*FUL2-12464 Updating Replacement sale : Updating logic as per policy splitting api response*/
	if ("Y".equalsIgnoreCase(replacementSale)) {
		dataVariables.put("replacementSale", AppConstants.YES);
	} else {
		dataVariables.put("replacementSale", AppConstants.NO);
	}
	dataVariables.put("specifiedPersonCode", StringUtils.isNotBlank(specifiedPersonCode) ? specifiedPersonCode : AppConstants.WHITE_SPACE);
	dataVariables.put("YBL", AppConstants.CHANNEL_YBL.equalsIgnoreCase(channel) );
	dataVariables.put("isProspectiveYBLCustomer",
			Objects.nonNull(proposalDetails.getAdditionalFlags().getIsProspectiveYBLCustomer())
					? proposalDetails.getAdditionalFlags().getIsProspectiveYBLCustomer()
					: false);
	dataVariables.put("isNotYBLProposal", !Utility.isYBLProposal(proposalDetails));
	dataVariables.put("bancaPartner", bancaPartner);
		//FUL2-239436_ULIP_PF_Changes
		/*if (Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.NIFTYFUND_FEATURE_KEY))) {
			Utility.niftyFundEnableOrNot(uiConfigurationService, null, null, dataVariables, "");
		} else if (Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.NIFTYFUND_FEATURE_KEY))) {
			dataVariables.put("isFeatureFlagNIFTYFund", false);
		}*/
		if(!isNeoOrAggregator && AppConstants.STAR_PRODUCT_ID.equalsIgnoreCase(productId)){
			utilityService.setFeatureFlagNIFTYFundForSTART(dataVariables, String.valueOf(proposalDetails.getTransactionId()));
		}
	String ver = Boolean.TRUE.equals(dataVariables.get("isFeatureFlagNIFTYFund")) ? AppConstants.NEW_ULIP_PF_VERSION : AppConstants.ULIP_PF_VERSION;
	dataVariables.put("newPdfVersion",ver);
	if (isNeoOrAggregator
			&& Objects.nonNull(proposalDetails.getApplicationDetails())
			&& Objects.nonNull(proposalDetails.getPaymentDetails())
			&& Objects.nonNull(proposalDetails.getPaymentDetails().getReceipt())
			&& !proposalDetails.getPaymentDetails().getReceipt().isEmpty()) {

		dataVariables.put("date", Utility.getDateOnTheBasisOfRateChange(proposalDetails, true));
	} else {
        dataVariables.put("date", baseMapper.getPFGeneratedDate());
    }
	dataVariables.put("time", baseMapper.getPfGeneratedTime());

    //NEORW: Added Proposer name and Place in Declaration section for NEO or Aggregator
	String proposerName = "";
	if (AppConstants.CHANNEL_NEO.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel()) ||
			AppConstants.CHANNEL_AGGREGATOR.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())) {
		proposerName = getProposerName(proposalDetails);
		place = proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getCity();
		dataVariables.put("agentAdvisorName", Utility.setAgentAdvisorName(proposalDetails));
	}
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

	}

	dataVariables.put("proposerName", proposerName);
	dataVariables.put("place", place);

	dataVariables.put("signature", signature);
	dataVariables.put(AppConstants.IS_NOT_YBL_PROPOSAL, !Utility.isYBLProposal(proposalDetails));

	//FUL2-46150 Vernacular Declaration Addition
		boolean visibleVernacular = false;
		if(!isNeoOrAggregator && StringUtils.isNotEmpty(deploymentDate)){
			visibleVernacular = Utility.newPFDeclaration(proposalDetails, deploymentDate);
		}
		dataVariables.put("visibleVernacular", visibleVernacular);
		//FUL2-211284_IRDA_PF_Changes
		if(!isNeoOrAggregator) {
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
	Context decalarationContext = new Context();
	decalarationContext.setVariables(dataVariables);

	logger.info("END setDataOfDeclaration {}", "%m");
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

	private String getProposerName(ProposalDetails proposalDetails) {
    	if (Objects.nonNull(proposalDetails)
			&& Objects.nonNull(proposalDetails.getPartyInformation())
			&& !proposalDetails.getPartyInformation().isEmpty()
			&& Objects.nonNull(proposalDetails.getPartyInformation().get(0).getBasicDetails())) {

    		return Stream.of(
					Utility.getTitle(proposalDetails.getPartyInformation().get(0).getBasicDetails().getGender()),
					proposalDetails.getPartyInformation().get(0).getBasicDetails().getFirstName(),
					proposalDetails.getPartyInformation().get(0).getBasicDetails().getMiddleName(),
					proposalDetails.getPartyInformation().get(0).getBasicDetails().getLastName())
					.filter(s -> !org.springframework.util.StringUtils.isEmpty(s)).collect(Collectors.joining(" "));
		}
    	return AppConstants.BLANK;
	}
	private Date setOtpConfirmation(ProposalDetails proposalDetails){

		Date otpConfirmation= proposalDetails.getPosvDetails().getPosvStatus().getGeneratedOTPDate();
		//FUL2-146253 Capital Small Finance Bank
		if(!((AppConstants.YES.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getIsCSFBChannel())
				&& Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLE_CSFBTAGS)))
				//FUL2-170700 BrmsBroker Integration
				||Utility.brmsBrokerEligibility(proposalDetails))){
			otpConfirmation =Objects.nonNull(proposalDetails.getAdditionalFlags())
					          && Objects.nonNull(proposalDetails.getAdditionalFlags().getCustomerSignDateForCDF())
					             ? proposalDetails.getAdditionalFlags().getCustomerSignDateForCDF()
					             : null;
		}
		return otpConfirmation;
	}
}
