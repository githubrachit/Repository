package com.mli.mpro.document.mapper;

import static org.apache.logging.log4j.util.Strings.isEmpty;

import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.proposal.models.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import com.mli.mpro.proposal.models.irpPsmForNeo.Fund;
import com.mli.mpro.proposal.models.irpPsmForNeo.IrpTags;
import com.mli.mpro.proposal.models.irpPsmForNeo.Returns;
import com.mli.mpro.proposal.models.irpPsmForNeo.Suitability;
import com.mli.mpro.utils.UtilityService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.sellerSignature.service.SellerSignatureServiceImpl;
import com.mli.mpro.utils.Utility;

@Service
public class PSMDocumentMapper {

    private static final Logger logger = LoggerFactory.getLogger(PSMDocumentMapper.class);

    @Autowired
	SellerSignatureServiceImpl sellerSignatureService;
	@Autowired
	private UtilityService utilityService;

	private final String IS_ULIP = "isUlip";

    public Context setDataForDocument(ProposalDetails proposalDetails) throws UserHandledException {

	Context context = new Context();
	try {
	    Map<String, Object> dataForDocument = new HashMap<>();
	    String formType = proposalDetails.getApplicationDetails().getFormType();
	    String schemeType = proposalDetails.getApplicationDetails().getSchemeType();
		logger.info("Data Mapping is initiated for transactionId {} formType {} ", proposalDetails.getTransactionId(),formType);
		Optional<PartyInformation> opPartyInformation;
		opPartyInformation = getPartyInformation(proposalDetails, formType, schemeType);
		if(!opPartyInformation.isPresent()){
         return context;
			}
	    PartyInformation partyInformation = opPartyInformation.get();
	    BasicDetails proposerBasicDetails = partyInformation.getBasicDetails();
	    SimpleDateFormat formatter = new SimpleDateFormat(AppConstants.DD_MM_YYYY_SPACE);
	    formatter.setTimeZone(TimeZone.getTimeZone(AppConstants.APP_TIMEZONE));
	    String otpVerifiedDate = "";
		String sellerSignature = sellerSignatureService.getSellerSignature(proposalDetails);
		otpVerifiedDate = getOtpVerifiedDate(proposalDetails, formatter, otpVerifiedDate);
		String proposerName = getCustomerName(proposerBasicDetails);
		int proposerCurrentAge = getAge(proposerBasicDetails.getDob());
	    String age = "";
	    if(proposerCurrentAge != 0){
		age = String.valueOf(proposerCurrentAge);
	    }
		String occupation = getOccupation(proposalDetails, proposerBasicDetails);
		String riskAppetite = null;
		if (Objects.nonNull(proposalDetails.getPsmDetails()) && !AppConstants.YES.equalsIgnoreCase(proposalDetails.getApplicationDetails().getIsParallelJourney())) {
			riskAppetite = proposalDetails.getPsmDetails().getRiskAppetite();
		}
		ProductDetails productDetails = null;
	    String lifeStage = null;
	    String insuranceNeed = null;
	    String productSelected = null;
	    String productType = null;
	    String premiumPaymentTerm = null;
	    String policyTerm = null;
	    String premiumPaymentMode = null;
	    String totalSumAssured = "NA";
		productDetails = getProductDetails(proposalDetails, productDetails);
		if (productDetails != null) {
			lifeStage = productDetails.getLifeStage();
			insuranceNeed = Utility.setDefaultValuePosSeller(proposalDetails);
			productSelected = productDetails.getProductInfo().getProductName();
			if (proposalDetails.getSalesStoriesProductDetails() != null && !isEmpty(proposalDetails.getSalesStoriesProductDetails().getIsSalesProduct()) && proposalDetails.getSalesStoriesProductDetails().getIsSalesProduct().equalsIgnoreCase("YES")) {
				productType = "Combo";
				productSelected = getProductSelected(proposalDetails, productSelected);
			} else {
				productType =getProductType(productDetails);
			}
			productSelected = productSelected.replaceAll(AppConstants.SFPS_PRODUCT_NAME, AppConstants.SFPS_PRODUCT_NAME_WITH_BASE_PRODUCT_AND_RIDER);
			productSelected = productSelected.replaceAll(AppConstants.AXIS_SFPS_PRODUCT_NAME, AppConstants.SFPS_PRODUCT_NAME_WITH_BASE_PRODUCT_AND_RIDER);
			productSelected = productSelected.replaceAll(AppConstants.SSES_PRODUCT_NAME, AppConstants.SSES_PRODUCT_NAME_WITH_BASE_PRODUCT_AND_RIDER);
			productSelected = productSelected.replaceAll(AppConstants.AXIS_SSES_PRODUCT_NAME, AppConstants.SSES_PRODUCT_NAME_WITH_BASE_PRODUCT_AND_RIDER);
			premiumPaymentTerm =
					getTerm(Utility.nullSafe(productDetails.getProductInfo().getProductIllustrationResponse().getPremiumPaymentTerm()), Utility.nullSafe(productDetails.getProductInfo().getPremiumPaymentTerm()));
			policyTerm = getTerm(Utility.nullSafe(productDetails.getProductInfo().getPolicyTerm()),
					Utility.nullSafe(productDetails.getProductInfo().getProductIllustrationResponse().getCoverageTerm()));
			premiumPaymentMode = productDetails.getProductInfo().getModeOfPayment();
			String productId = productDetails.getProductInfo().getProductId();
			if((AppConstants.SGPP_ID.equals(productId)||AppConstants.SWAGPP.equalsIgnoreCase(productId)) 
					&& AppConstants.SINGLE_PAY.equalsIgnoreCase(productDetails.getProductInfo().getPremiumType())) {
				premiumPaymentMode = AppConstants.SINGLE;
			}
		}
	    String existingCoverHeld = proposalDetails.getPsmDetails() != null ? proposalDetails.getPsmDetails().getIsExistingLICover():"";
		existingCoverHeld = getExistingCoverHeld(proposalDetails, existingCoverHeld);
		String productRecommended = StringUtils.EMPTY;
		productRecommended = getProductRecommended(proposalDetails, productRecommended);
	    
		// FUL2-45823 - NPS selected product should be shown in PSM document.
		if (AppConstants.ANNUITY_PRODUCTS.contains(productDetails.getProductInfo().getProductId())
				&& AppConstants.YES.equalsIgnoreCase(proposerBasicDetails.getIsNPSCustomer())) {
			productRecommended = productSelected;
		}

	    
		String policyNumber = "";
		policyNumber = getPolicyNumber(proposalDetails, policyNumber);
		// FUL2-46310
		policyNumber = Utility.getSecondaryPolicyNumber(proposalDetails, policyNumber);
		policyNumber = Utility.getPrimaryPolicyNumber(proposalDetails, policyNumber);
		String grossAnnualIncome =proposerBasicDetails.getAnnualIncome();
	    String bypass = "NO";
	    String dateType = "PSM Date";
	    if (proposalDetails.getPsmDetails() != null) {
		totalSumAssured = getTerm(proposalDetails.getPsmDetails().getExistingLICover(), "");
		bypass = ("YES".equalsIgnoreCase(proposalDetails.getPsmDetails().getBypass())) ? "YES" : "NO";
		logger.info("bypass value in location service is {} ",bypass);
	    }

    Boolean isThanos = AppConstants.THANOS_CHANNEL
				.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel());
		String psmDate = null;
		psmDate = getPsmDate(proposalDetails, formatter, psmDate);
	if(AppConstants.SWAG_ELITE_PRODUCT_ID.equalsIgnoreCase(productDetails.getProductInfo().getProductId()))	{
		policyTerm= AppConstants.WHOLE_LIFE;
	}
    if ("YES".equalsIgnoreCase(bypass)) {
      dateType = "PSM/Bypass Date";
      dataForDocument.put("Age", "");
      dataForDocument.put("Occupation", "");
      dataForDocument.put("LifeStage", "");
      dataForDocument.put("GrossAnnualIncome", "");
      dataForDocument.put("PurposeOfInsurance", "");
      dataForDocument.put("ExistingLifeCover", "");
      dataForDocument.put("TotalSumAssured", "");
      dataForDocument.put("RecommendedProducts", "");
      dataForDocument.put("SelectedProduct", "");
      dataForDocument.put("NatureOfProduct", "");
      dataForDocument.put("PremiumPaymentTerm", "");
      dataForDocument.put("PolicyTerm", "");
      dataForDocument.put("ModeOfPayment", "");
      dataForDocument.put("OTPConfirmationDate", "");
    }else {
      dataForDocument.put("Age", age);
      dataForDocument.put("Occupation", occupation);
      dataForDocument.put("RiskProfile", riskAppetite);
      dataForDocument.put("LifeStage", lifeStage);
      dataForDocument.put("GrossAnnualIncome", grossAnnualIncome);
      dataForDocument.put("PurposeOfInsurance", insuranceNeed);
      dataForDocument.put("ExistingLifeCover", existingCoverHeld);
      dataForDocument.put("TotalSumAssured", totalSumAssured);
      dataForDocument.put("RecommendedProducts", productRecommended);
      dataForDocument.put("SelectedProduct", productSelected);
      dataForDocument.put("NatureOfProduct", productType);
      dataForDocument.put("PremiumPaymentTerm", premiumPaymentTerm);
      dataForDocument.put("PolicyTerm", policyTerm);
      dataForDocument.put("ModeOfPayment", premiumPaymentMode);
      dataForDocument.put("OTPConfirmationDate", otpVerifiedDate);
    }
    dataForDocument.put("NeedForAnalysis", proposerName);
    dataForDocument.put("isThanos", isThanos);
    dataForDocument.put("CustomerName", proposerName);
	    dataForDocument.put("ProposalNo", policyNumber);
	    dataForDocument.put("bypass", bypass);
	    dataForDocument.put("DateType", dateType);
	    dataForDocument.put("Date", psmDate);
	    dataForDocument.put(AppConstants.IS_NOT_YBL_PROPOSAL, !Utility.isYBLProposal(proposalDetails));
	    dataForDocument.put("sellerSignature",isEmpty(sellerSignature)? StringUtils.EMPTY:sellerSignature);
	    context.setVariables(dataForDocument);
	} catch (Exception ex) {
	    logger.error("Data addition failed for PSM Document:", ex);
	    List<String> errorMessages = new ArrayList<>();
	    errorMessages.add("Data addition failed");
	    throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	return context;
    }

	private Optional<PartyInformation> getPartyInformation(ProposalDetails proposalDetails, String formType, String schemeType) {
		Optional<PartyInformation> opPartyInformation;
		if(Utility.checkForm3WithSchemeA(formType, schemeType)){
				opPartyInformation = proposalDetails.getPartyInformation().stream().filter(partyInfo -> AppConstants.INSURED.equalsIgnoreCase(partyInfo.getPartyType()))
						.findFirst();
			}else{
				opPartyInformation = proposalDetails.getPartyInformation().stream().filter(partyInfo -> AppConstants.PROPOSER.equalsIgnoreCase(partyInfo.getPartyType()))
						.findFirst();
			}
		return opPartyInformation;
	}

	private String getCustomerName(BasicDetails proposerBasicDetails) {
		String proposerName = null;
		String middleName = proposerBasicDetails.getMiddleName();
		if (!isEmpty(middleName)) {
		proposerName = proposerBasicDetails.getFirstName() + " " + middleName + " " + proposerBasicDetails.getLastName();
		} else {
		proposerName = proposerBasicDetails.getFirstName() + " " + proposerBasicDetails.getLastName();
		}
		return proposerName;
	}

	private String getProductType(ProductDetails productDetails) {
    	String productType;
		//FUL2-40700 Changes for All Annuity Products
    	if (AppConstants.ANNUITY_PRODUCTS.contains(productDetails.getProductInfo().getProductId())) {
			productType = "Annuity";
    	} else {
			productType = productDetails.getProductType();
    	}
		return productType;
	}

	private String getOccupation(ProposalDetails proposalDetails, BasicDetails proposerBasicDetails) {
		String occupation = proposerBasicDetails.getOccupation();
		if("NEO".equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())){
		occupation = proposerBasicDetails.getOccupation();
		}else{
		occupation = (occupation.equalsIgnoreCase("SALARIED") || occupation.equalsIgnoreCase("SELF EMPLOYED"))? occupation : "Others";
		}
		return occupation;
	}

	private String getTerm(String term1, String term2) {
		return !isEmpty(term1) ? term1 : term2;
	}

	private ProductDetails getProductDetails(ProposalDetails proposalDetails, ProductDetails productDetails) {
		if (!proposalDetails.getProductDetails().isEmpty()) {
		productDetails = proposalDetails.getProductDetails().get(0);
		}
		return productDetails;
	}

	private String getProductRecommended(ProposalDetails proposalDetails, String productRecommended) {
		/* FUL2-39332 -  we are showing only POS products for Agency and YBL POS in the productRecommended section */
		//FUL2-135547_Setup_of_DCB_Bank_in_Mpro
		if(!AppConstants.CHANNEL_NEO.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel()) && ((AppConstants.CHANNEL_AGENCY.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())
				|| (AppConstants.CHANNEL_CAT.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel()))        //FUL2-123815
				|| AppConstants.CHANNEL_YBL.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())
				|| Utility.checkBrokerTmbChannel(proposalDetails.getChannelDetails().getChannel(), proposalDetails.getSourcingDetails().getGoCABrokerCode())|| utilityService.replicaOfUjjivanChannel(proposalDetails.getChannelDetails().getChannel(), proposalDetails.getSourcingDetails().getGoCABrokerCode()))
				&& proposalDetails.getSourcingDetails().isPosSeller())) {
			proposalDetails.getPsmDetails().getRecommendedProducts().retainAll(AppConstants.POS_PRODCUTS_LIST);
		}
		if(proposalDetails.getPsmDetails()!=null && proposalDetails.getPsmDetails().getRecommendedProducts()!=null) {
			productRecommended = String.join(",\t", proposalDetails.getPsmDetails().getRecommendedProducts());
		}
		productRecommended = productRecommended.replaceAll(AppConstants.SFPS_PRODUCT_NAME, AppConstants.SFPS_PRODUCT_NAME_WITH_BASE_PRODUCT_AND_RIDER);
		productRecommended = productRecommended.replaceAll(AppConstants.AXIS_SFPS_PRODUCT_NAME, AppConstants.SFPS_PRODUCT_NAME_WITH_BASE_PRODUCT_AND_RIDER);
		productRecommended = productRecommended.replaceAll(AppConstants.SSES_PRODUCT_NAME, AppConstants.SSES_PRODUCT_NAME_WITH_BASE_PRODUCT_AND_RIDER);
		productRecommended = productRecommended.replaceAll(AppConstants.AXIS_SSES_PRODUCT_NAME, AppConstants.SSES_PRODUCT_NAME_WITH_BASE_PRODUCT_AND_RIDER);
		return productRecommended;
	}

	private String getPsmDate(ProposalDetails proposalDetails, SimpleDateFormat formatter, String psmDate) {
		if(proposalDetails.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse()!=null && proposalDetails.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getbIGeneratedDate()!=null) {
			 psmDate = formatter.format(proposalDetails.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getbIGeneratedDate());
		}
		if ("NEO".equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())
			|| AppConstants.THANOS_CHANNEL
			.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())
			&& proposalDetails.getPosvDetails().getPosvStatus().getGeneratedOTPDate() != null) {
			psmDate = formatter
				.format(proposalDetails.getPosvDetails().getPosvStatus().getGeneratedOTPDate());
		}
		return psmDate;
	}

	private String getPolicyNumber(ProposalDetails proposalDetails, String policyNumber) {
		return proposalDetails.getApplicationDetails().getPolicyNumber();
	}

	private String getExistingCoverHeld(ProposalDetails proposalDetails, String existingCoverHeld) {
		if (StringUtils.isEmpty(existingCoverHeld)) {
		existingCoverHeld = "";
		} else {
		existingCoverHeld = "YES".equalsIgnoreCase(proposalDetails.getPsmDetails().getIsExistingLICover())
			? "Yes" : "No";
		}
		return existingCoverHeld;
	}

	private String getProductSelected(ProposalDetails proposalDetails, String productSelected) {
		if(proposalDetails.getSalesStoriesProductDetails().getProductDetails()!=null && !proposalDetails.getSalesStoriesProductDetails().getProductDetails().isEmpty()) {
			productSelected = proposalDetails.getSalesStoriesProductDetails().getProductDetails().get(0).getProductInfo().getProductName();
		}
		return productSelected;
	}
	//FUL2-13923 added paymentDate as otp date for PSM document.(YBL telesales)
	private String getOtpVerifiedDate(ProposalDetails proposalDetails, SimpleDateFormat formatter, String otpVerifiedDate) {
		try {
			String isBrmsApplicable = "";
			String digitalJourney = "";
			if(!AppConstants.PROPOSAL_E2E_TRANSFORMATION.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getSourceChannel())) {
				//FUL2-170700 Brms Broker Integration
				BrmsFieldConfigurationDetails brmsFieldConfigurationDetails = Optional.ofNullable(proposalDetails)
						.map(ProposalDetails::getBrmsFieldConfigurationDetails)
						.orElse(null);
				 isBrmsApplicable = Objects.nonNull(brmsFieldConfigurationDetails.getIsBRMSApiApplicable()) ? brmsFieldConfigurationDetails.getIsBRMSApiApplicable() : AppConstants.BLANK;
				 digitalJourney = Objects.nonNull(brmsFieldConfigurationDetails.getDigitalJourney()) ? brmsFieldConfigurationDetails.getDigitalJourney() : AppConstants.BLANK;
			}
			Receipt receipt = proposalDetails.getPaymentDetails().getReceipt().get(0);
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US);
			DateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
			logger.info("paymentDate in location service is {} transactionID {}",receipt.getPaymentDate(),proposalDetails.getTransactionId());
		if((proposalDetails.getAdditionalFlags().isYblTelesalesCase() && ObjectUtils.isNotEmpty(receipt))
		|| (AppConstants.AXIS_TELESALES_REQUEST.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getRequestSource()) && ObjectUtils.isNotEmpty(receipt) && !receipt.getPremiumMode().equalsIgnoreCase(AppConstants.DIRECTDEBIT))) {
				Date date = sdf.parse(receipt.getPaymentDate());
				otpVerifiedDate = (new SimpleDateFormat(AppConstants.DD_MM_YYYY_SPACE)).format(date);
		}else if(AppConstants.AXIS_TELESALES_REQUEST.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getRequestSource()) && ObjectUtils.isNotEmpty(receipt) && receipt.getPremiumMode().equalsIgnoreCase(AppConstants.DIRECTDEBIT)) {
			Date date = inputFormat.parse(receipt.getPaymentDate());
			otpVerifiedDate = (new SimpleDateFormat(AppConstants.DD_MM_YYYY_SPACE)).format(date);
		}else if (proposalDetails.getPosvDetails() != null && proposalDetails.getPosvDetails().getPosvStatus() != null
			) {
			// FUL2-146253 Capital Small Finance Bank
			if(proposalDetails.getPosvDetails().getPosvStatus().getSubmittedOTPDate() != null) {
				otpVerifiedDate = formatter.format(proposalDetails.getPosvDetails().getPosvStatus().getSubmittedOTPDate());
			}
			if((Objects.nonNull(proposalDetails.getAdditionalFlags()) && Objects.nonNull(proposalDetails.getAdditionalFlags().getIsCSFBChannel())
				&& Objects.nonNull(proposalDetails.getAdditionalFlags().getCustomerSignDateForCDF())
			    && Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLE_CSFBTAGS))
			    && AppConstants.YES.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getIsCSFBChannel()))
					//FUL2-170700 Brms Broker Integration
			  || (AppConstants.Y.equalsIgnoreCase(isBrmsApplicable) && AppConstants.CDF_JOURNEY.equalsIgnoreCase(digitalJourney)
					&& Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLE_BRMS_BROKER)))){
				otpVerifiedDate = formatter.format(proposalDetails.getAdditionalFlags().getCustomerSignDateForCDF());
			}
		}
		}catch(Exception ex) {
			logger.error("Exception raised setting OtpVerifiedDate", ex);
		}
		return otpVerifiedDate;
	}

	public int getAge(Date dob) {
	int age = 0;
	if (dob != null) {
	    Calendar calender = Calendar.getInstance();
	    calender.setTimeZone(TimeZone.getTimeZone(AppConstants.APP_TIMEZONE));
	    calender.setTime(dob);
	    age = LocalDate.now().getYear() - calender.get(Calendar.YEAR);
	    if (!(LocalDate.now().getMonth().getValue() > calender.get(Calendar.MONTH) + 1
		    || (LocalDate.now().getMonth().getValue() == calender.get(Calendar.MONTH) + 1
			    && LocalDate.now().getDayOfMonth() >= calender.get(Calendar.DAY_OF_MONTH)))) {
		age--;
	    }
	}
	return age;
    }

	public Context setDataPsmAndIrpDoc(ProposalDetails proposalDetails) throws UserHandledException {
		Context context = new Context();
		String ulipOrNot = proposalDetails.getProductDetails().get(0).getIsUlipProduct();

		Map<String, Object> dataForDocument = new HashMap<>();
		setUlipDefaultValues(dataForDocument,AppConstants.N);
		setDataForNonUlipDocument(dataForDocument, proposalDetails);
		
		if(AppConstants.Y.equalsIgnoreCase(ulipOrNot)){
			setUlipDefaultValues(dataForDocument,AppConstants.Y);
			setDataForUlipDocuments(dataForDocument, proposalDetails);
		}
		context.setVariables(dataForDocument);
		return context;
	}
	
	private void setUlipDefaultValues(Map<String, Object> dataForDocument, String stage){
		switch (stage){
			case AppConstants.Y:
				dataForDocument.put(IS_ULIP, true);
				break;
			case AppConstants.N:
				dataForDocument.put(IS_ULIP, false);
				break;
			default:
				dataForDocument.put(IS_ULIP, false);
				break;
		}
	}

	private void setDataForUlipDocuments(Map<String, Object> dataForDocument, ProposalDetails proposalDetails){
		dataForDocument.put("funds",proposalDetails.getNeoIrp().getFund());
	}


	private void setDataForNonUlipDocument(Map<String, Object> dataForDocument, ProposalDetails proposalDetails){
		String formType = proposalDetails.getApplicationDetails().getFormType();
		String schemeType = proposalDetails.getApplicationDetails().getSchemeType();

		PartyInformation partyInformation = getPartyInformation(proposalDetails,formType,schemeType).get();
		BasicDetails proposerBasicDetails = partyInformation.getBasicDetails();
		String customerName = getCustomerName(proposerBasicDetails);
		dataForDocument.put("customerName", customerName);
		String policyFromNeo = addNextLineInPolicy(proposalDetails.getApplicationDetails().getPolicyNumber());
		dataForDocument.put("policyNumber", policyFromNeo);

		SimpleDateFormat formatter = new SimpleDateFormat(AppConstants.DD_MM_YYYY_SPACE);
		formatter.setTimeZone(TimeZone.getTimeZone(AppConstants.APP_TIMEZONE));
		String date = getPsmDate(proposalDetails, formatter, null);
		dataForDocument.put("date", date);

		Suitability suitability = proposalDetails.getSuitability();

		dataForDocument.put("age", suitability.getAge());
		dataForDocument.put("buyingFor", suitability.getBuyingFor());
		dataForDocument.put("annualIncome", suitability.getAnnualIncome());
		dataForDocument.put("goalHorizon", suitability.getGoalHorizon());
		dataForDocument.put("paymentPreference", suitability.getPaymentPreference());
		dataForDocument.put("riskAppetite", suitability.getRiskAppetite());

		dataForDocument.put("otpDate",formatter.format(proposalDetails.getPosvDetails().getPosvStatus().getSubmittedOTPDate()));

		setFinancialAndFamilyGoals(proposalDetails,dataForDocument);

	}

	private String addNextLineInPolicy(String policy){
		policy = policy.replaceAll(",",","+"\n");
		policy = policy.replaceAll("Max", "\n" + "Max");
		return policy;
	}

	private void setFinancialAndFamilyGoals(ProposalDetails proposalDetails, Map<String, Object> dataForDocument){

		final String SAVING = "saving";
		final String CHILD = "child";
		final String RETIREMENT = "retirement";

		dataForDocument.put(SAVING, false);
		dataForDocument.put(CHILD, false);
		dataForDocument.put(RETIREMENT, false);

		switch (proposalDetails.getSuitability().getFinancialAndFamilyGoals()){
			case AppConstants.ONE:
				dataForDocument.put(SAVING, true);
				break;
			case AppConstants.TWO:
				dataForDocument.put(CHILD, true);
				break;
			case AppConstants.THREE:
				dataForDocument.put(RETIREMENT, true);
				break;
			default:
				break;
		}
	}
}
