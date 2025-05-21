package com.mli.mpro.document.mapper.term;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.document.models.ProposalRiderDetails;
import com.mli.mpro.document.utils.TraditionalFormUtil;
import com.mli.mpro.location.services.impl.DocsAppServiceImpl;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.*;
import com.mli.mpro.utils.Utility;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mli.mpro.productRestriction.util.AppConstants.*;
import static com.mli.mpro.utils.Utility.convertToBigDecimal;
import static com.mli.mpro.utils.Utility.zeroIfNullOrEmpty;
import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class TermCoverageDetailsMapper {

    private static final Logger logger = LoggerFactory.getLogger(TermCoverageDetailsMapper.class);
    private boolean isNeoOrAggregator = false;
    /* start FUL2-19674 - transgender handling changes */
    private static final String TRANSGENDER = "Transgender";
    private static final List<String> SWAG_NONPOS = Arrays.asList(TSGMB, TSGLB);
    /* start FUL2-19674 - transgender handling changes */
    @Autowired
    private DocsAppServiceImpl docsAppServiceImpl;
    Map<String, String> sourceOfFunds = new HashMap<String, String>() {

	private static final long serialVersionUID = 1L;
	{
	    put("salaried", "Salary");
	    put("agriculture", "Agriculture");
	    put("professional", "Professional");
	    put("self employed", "Business");
	    put("unanswered", AppConstants.OTHER_INCOME);
	    put("self employed from home", "Business");
	    put("housewife", AppConstants.OTHER_INCOME);
	    put("retired", AppConstants.OTHER_INCOME);
	    put("others", AppConstants.OTHER_INCOME);
		put("student", AppConstants.OTHER_INCOME);
	}

    };

    static List<String> riderListName = new ArrayList<>(
    		Arrays.asList(
    				AppConstants.TNCPAB,
					AppConstants.TNCIB,
					AppConstants.VN04,
					AppConstants.TCCIB,
					AppConstants.VN05,
					AppConstants.TCCPAB,
					AppConstants.TCPADB,
					AppConstants.TCLCIB,
					AppConstants.TCICIB,
					AppConstants.VN06,
					AppConstants.TNPADB,
					AppConstants.TCLNIB,
					AppConstants.TCINIB,
					AppConstants.VN07,
					AppConstants.RC19T1,
					AppConstants.PP02,
					AppConstants.RPPT2,
					AppConstants.VP02,
					AppConstants.VPWOP,
					AppConstants.TCIGR,
					AppConstants.TCIGPR,
					AppConstants.TCIPR,
					AppConstants.TCIPPR,
					AppConstants.TCIPDR,
					AppConstants.TCIGL,
					AppConstants.TCIGPL,
					AppConstants.TCIPL,
					AppConstants.TCIPPL,
					AppConstants.TCIPDL,
					AppConstants.VDWOP,
					AppConstants.VEWOP,
					AppConstants.TCADB,
					AppConstants.TCILB,
					AppConstants.TSPJS,
					AppConstants.TSPJR,
					AppConstants.TSPJL,
					AppConstants.TSPJ6,
					AppConstants.TSRJS,
					AppConstants.TSRJR,
					AppConstants.TSRJL,
					AppConstants.TSRJ6

			)
	);

    public Context setDataOfCoverageDetails(ProposalDetails proposalDetails) throws UserHandledException {

	Context context = new Context();
	Map<String, Object> dataVariables = new HashMap<>();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
	SimpleDateFormat output = new SimpleDateFormat(AppConstants.DD_MM_YYYY_HYPHEN);
	output.setTimeZone(TimeZone.getTimeZone(AppConstants.APP_TIMEZONE));

	logger.info("Mapping coverage details of proposal form document for transactionId {}", proposalDetails.getTransactionId());
	try {
		String formType = proposalDetails.getApplicationDetails().getFormType();
		String schemeType = proposalDetails.getApplicationDetails().getSchemeType();
	    ProductDetails productDetails = proposalDetails.getProductDetails().get(0);
		//NEORW-173: this will check that incoming request is from NEO or Aggregator
	    isNeoOrAggregator = AppConstants.CHANNEL_NEO.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())
				|| AppConstants.CHANNEL_AGGREGATOR.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel());

	    //NEORW-80: handle NULL pointer exception for bank and bankDetails objects
	    BankDetails bankdetails = null;
		BasicDetails proposerDetails = null;
		BasicDetails payorDetails = null;

		if ((isNeoOrAggregator) && checkingBankDetailsNullValue(proposalDetails)) {
			bankdetails = proposalDetails.getPartyInformation().get(0).getBankDetails();
		} else {
			if (Objects.nonNull(proposalDetails.getBank()) &&
					Objects.nonNull(proposalDetails.getBank().getBankDetails())) {
				bankdetails = proposalDetails.getBank().getBankDetails().get(0);
			}
		}
		if (Objects.nonNull(proposalDetails.getPartyInformation()) &&
			!proposalDetails.getPartyInformation().isEmpty()) {
			proposerDetails = proposalDetails.getPartyInformation().get(0).getBasicDetails();
			payorDetails = proposalDetails.getPartyInformation().size() > 2 ?
					proposalDetails.getPartyInformation().get(2).getBasicDetails() : null;
		}
		String productId = productDetails.getProductInfo().getProductId();
	    String planName = "";
	    //FUL2-61169 Smart Secure Easy Solution
		if (Utility.isSSESProduct(productId, Utility.isSSESProductorNot(proposalDetails),
				Utility.getSSESProductSolveOption(proposalDetails))) {
			planName = "MAX LIFE SMART SECURE PLUS PLAN";
		} else {
			planName = productDetails.getProductInfo().getProductName();
		}
	    
	    String premiumBackOption = productDetails.getProductInfo().getPremiumBackOption();
	    String coverageTerm = productDetails.getProductInfo().getProductIllustrationResponse().getCoverageTerm();
	    String premiumPaymentTerm = productDetails.getProductInfo().getProductIllustrationResponse().getPremiumPaymentTerm();
		String sumAssured = isNeoOrAggregator ? convertToBigDecimal(productDetails.getProductInfo().getProductIllustrationResponse().getSumAssured())
				: String.format("%.0f",roundOffValue(productDetails.getProductInfo().getProductIllustrationResponse().getSumAssured()));
		String modalPremium = isNeoOrAggregator ? convertToBigDecimal(productDetails.getProductInfo().getProductIllustrationResponse().getModalPremium()): String.format("%.0f", roundOffValue(productDetails.getProductInfo().getProductIllustrationResponse().getModalPremium()));
		if (proposalDetails.getChannelDetails().getChannel()
				.equalsIgnoreCase(AppConstants.CHANNEL_AGGREGATOR)) {
			modalPremium = productDetails.getProductInfo().getProductIllustrationResponse().getBaseModalPremium();
		}
		String modalPremiumWithoutGST = isNeoOrAggregator ? String.valueOf(productDetails.getProductInfo().getProductIllustrationResponse().getRequiredModalPremium())
				: String.format("%.2f", roundOffValue(productDetails.getProductInfo().getProductIllustrationResponse().getRequiredModalPremium()));
		String gstCess = "";
		String planGST = "";
		Boolean isSwpWli = AppConstants.WHOLE_LIFE_INCOME.equalsIgnoreCase(proposalDetails.getProductDetails().get(0).getProductInfo().getVariant());
		
		//NEORW-80: set total GST, base plan GST, death benefit and life event stage benefit in case of NEO or Aggregator
	    if (isNeoOrAggregator) {
	    	final String pcbRiders= "03";
	    	gstCess = String.valueOf(roundOffValue(productDetails.getProductInfo().getProductIllustrationResponse().getServiceTaxInclCessBaseModalPrem()));
			planGST = productDetails.getProductInfo().getProductIllustrationResponse().getServiceTax();
			String policyTerm = Utility.isNotNullOrEmpty(productDetails.getProductInfo().getPolicyTerm()) ? productDetails.getProductInfo().getPolicyTerm() : " ";
			dataVariables.put("deathBenefit",
					StringUtils.isEmpty(productDetails.getProductInfo().getDeathBenefit()) ? "" : productDetails.getProductInfo().getDeathBenefit());
			dataVariables.put("lifeStageEvent", StringUtils.isEmpty(productDetails.getProductInfo().getLifeStageEventBenefit())
					? AppConstants.NO : Utility.convertToYesOrNo(productDetails.getProductInfo().getLifeStageEventBenefit()));
			dataVariables.put("policyTerm", policyTerm);
			dataVariables.put("PcbRiders", pcbRiders);
			setIncomeRelatedValue(productDetails,dataVariables);
	    } else {
			gstCess = String.format("%.0f",roundOffValue(productDetails.getProductInfo().getProductIllustrationResponse().getServiceTax()));
		}
		if(AppConstants.YES.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getGstWaiverRequired())){
			gstCess="0";
		}
		/*FUL2-17826 removed the temporary gst Waiver changes*/
	    String totalPremium = isNeoOrAggregator ? productDetails.getProductInfo().getProductIllustrationResponse().getInitialPremiumPaid()
				: String.format("%.0f",roundOffValue(productDetails.getProductInfo().getProductIllustrationResponse().getInitialPremiumPaid()));
	    // FUL2-118606/FUL2-119208 inclusion of SWAG_PAR product
	    String pcb = Utility.evaluateConditionalOperation(AppConstants.STEP.equals(productId),Optional.ofNullable(StringUtils.capitalize(productDetails.getProductInfo().getPcb())).orElse(AppConstants.BLANK), "NA");
	    String bonusOption = Utility.evaluateConditionalOperation(!StringUtils.isEmpty(productDetails.getProductInfo().getDividendOption())
				, productDetails.getProductInfo().getDividendOption(), "NA");
	    //NEORW-80: handle NULL pointer exception here for bankdetails object
	    String micr = Objects.nonNull(bankdetails) ? bankdetails.getMicr() : "";
	    String ifsc = Objects.nonNull(bankdetails) ? bankdetails.getIfsc() : "";
	    String accountNumber = Objects.nonNull(bankdetails) ? bankdetails.getBankAccountNumber() : "";
	    String accHolder = Objects.nonNull(bankdetails) ? bankdetails.getAccountHolderName(): "";
	    String typeOfBank = Objects.nonNull(bankdetails) ? bankdetails.getTypeOfAccount(): "";
		String bankBranch = "";
		if (isNeoOrAggregator) {
			bankBranch = Objects.nonNull(bankdetails) && Objects.nonNull(bankdetails.getBankBranch()) ? (bankdetails.getBankBranch()) : "";

		} else {
			bankBranch = Objects.nonNull(bankdetails) && Objects.nonNull(bankdetails.getBankName()) ? bankdetails.getBankName().concat(" ").concat(bankdetails.getBankBranch()) : "";
		}
	    String bankingsince = "NA";
		if (proposalDetails != null && proposalDetails.getBank() != null && !proposalDetails.getBank().getBankDetails().isEmpty() && proposalDetails.getBank().getBankDetails().get(0) != null && proposalDetails.getBank().getBankDetails().get(0).getBankAccOpeningDate() != null) {
			bankingsince = String.valueOf(Utility.dateFormatter(proposalDetails.getBank().getBankDetails().get(0).getBankAccOpeningDate()));

		}
	    String pan = "";
	    //NEORW-80: handle NULL pointer exception for Pan details object
	    if(AppConstants.FORM3.equalsIgnoreCase(formType) && !Utility.schemeBCase(schemeType)) {
			pan = proposalDetails.getPartyInformation().stream()
					.filter(Objects::nonNull)
					.filter(partyInfo -> AppConstants.COMPANY.equalsIgnoreCase(partyInfo.getPartyType()))
					.findFirst().map(PartyInformation::getPersonalIdentification).map(PersonalIdentification::getPanDetails)
					.map(PanDetails::getPanNumber).orElse("");
			
		}else if (Objects.nonNull(proposalDetails.getPartyInformation()) &&
			!proposalDetails.getPartyInformation().isEmpty() &&
			Objects.nonNull(proposalDetails.getPartyInformation().get(0).getPersonalIdentification()) &&
			Objects.nonNull(proposalDetails.getPartyInformation().get(0).getPersonalIdentification().getPanDetails())) {
			pan = proposalDetails.getPartyInformation().get(0).getPersonalIdentification().getPanDetails().getPanNumber();
		}
	    String panRequired = "YES";
	    String form60 = "NO";
	    String form49a="NO";
	    String payorName = "";
	    String payorGender = "";
	    String payorDOB = "";
	    String relationShipWithProposer = "";
	    String payorAddress = "";
	    String payorAnnualIncome = "";
	    String payorPan = "";
	    String payorBank = "";
	    String payorBankBranch = "";
	    String payorAccountNumber = "";
	    String funds = "";
	    String occupation = StringUtils.isEmpty(proposerDetails.getOccupation()) ? "others" : proposerDetails.getOccupation();
		if (AppConstants.FORM3.equalsIgnoreCase(formType) && !Utility.schemeBCase(schemeType)) {
			funds = proposalDetails.getPartyInformation().stream().filter(Objects::nonNull)
					.filter(partyInfo -> AppConstants.COMPANY.equalsIgnoreCase(partyInfo.getPartyType())).findFirst()
					.map(PartyInformation::getBasicDetails).map(BasicDetails::getSourceOfFunds).orElse("");
		}else {
			 funds = isNeoOrAggregator ? setSourceOfFundsForNeo(proposalDetails, dataVariables) : sourceOfFunds.get(occupation.toLowerCase());
		}

	    String effectiveDateOfCoverage = "";

	    // Getting insured pan details
	   //FUL2-7517 Smart Wealth Plan in mPRO - PF Changes
		PersonalIdentification insuredDetails = null;
		String insuredPan = "";
		if (Objects.nonNull(proposalDetails.getPartyInformation())
				&& proposalDetails.getPartyInformation().size() > 1
				&& Objects.nonNull(proposalDetails.getPartyInformation().get(1).getPersonalIdentification())) {
			insuredDetails = proposalDetails.getPartyInformation().get(1).getPersonalIdentification();
			insuredPan = insuredDetails.getPanDetails().getPanNumber();
		}
	    String insuredForm60 = "NO";
	    // FUL2-118606/FUL2-119208 inclusion of SWAG_PAR product
	    boolean isSWPSJBSWAGSWAG_PAR;
		boolean isSmartWealthPlan = false;

		String incomePayoutFrequency = productDetails.getProductInfo().getIncomePayoutFrequency();
	    String variant = productDetails.getProductInfo().getVariant();
	    boolean isIncomePayout = Boolean.FALSE;
	    double incomePayout = 0.0;
	    double illusIncomePayoutFrequency = 0.0;
		String swpIncomePayout = "";
	    try {
		effectiveDateOfCoverage =productDetails.getProductInfo().getEffectiveDateOfCoverage() != null
			? output.format(sdf.parse(productDetails.getProductInfo().getEffectiveDateOfCoverage()))
			: StringUtils.EMPTY;

	    } catch (Exception ex) {
		logger.error("Error converting Effective Date of coverage for stp:",ex);
	    }
		String modeOfPayment = productDetails.getProductInfo().getModeOfPayment();
		/*
		 * F21-265 Standing Instruction on Credit Card- If user has opted
		 * the SI then credit card should be displayed in renewal Option
		 */
		Receipt receiptDetails = proposalDetails.getPaymentDetails().getReceipt().get(0);
		String isSIOpted = receiptDetails.getIsSIOpted();
		String renewalPayment = StringUtils.EMPTY;
		if (isNeoOrAggregator) {
			renewalPayment = Utility.setRenewalPaymentDataForNeo(proposalDetails, receiptDetails);
		} else {
			renewalPayment = setRenewalPayment(proposalDetails, modeOfPayment, isSIOpted, renewalPayment);
		}

		String basePlanGst = Utility.evaluateConditionalOperation(
				StringUtils.isEmpty(productDetails.getProductInfo().getProductIllustrationResponse().getBasePlanGST()),
				"", productDetails.getProductInfo().getProductIllustrationResponse().getBasePlanGST());
		if (basePlanGst.equals("0")) {
			dataVariables.put("GST", "");
		} else {
			dataVariables.put("GST", basePlanGst);
		}
		dataVariables.put("smartTerm",
				AppConstants.STEP.equalsIgnoreCase(productId)
						|| AppConstants.SSP_PRODUCT_ID.equalsIgnoreCase(productId));
		dataVariables.put("pBoPpt", Utility.or(Utility.isSSPJointLife(productDetails)));
		dataVariables.put("deathBenefit",
				Utility.evaluateConditionalOperation(
						(productDetails.getProductInfo().getDeathBenefit() == null
								|| StringUtils.isEmpty(productDetails.getProductInfo().getDeathBenefit())),
						AppConstants.NA, productDetails.getProductInfo().getDeathBenefit()));
		dataVariables.put("lifeStageEvent",
				Utility.evaluateConditionalOperation(
						productDetails.getProductInfo().getDeathBenefit() == null
								|| StringUtils.isEmpty(productDetails.getProductInfo().getLifeStageEventBenefit()),
						AppConstants.NA, productDetails.getProductInfo().getLifeStageEventBenefit()));

	    //NEORW-80: this function will fetch payor details on the basis of party type in case of NEO or Aggregator
		String payorDiffFormProposer = "";
	    if (isNeoOrAggregator &&
			!proposalDetails.getPartyInformation().isEmpty()) {
			PartyInformation partyInformation = proposalDetails.getPartyInformation()
					.stream()
					.filter(pd -> pd.getPartyType().equals("Payor"))
					.findFirst().orElse(null);
			//NEORW: conditions to check payor details exists or not
			if (Objects.nonNull(partyInformation)
					&& Objects.nonNull(partyInformation.getBasicDetails())
					&& Objects.nonNull(partyInformation.getPartyDetails())
					&& !StringUtils.isEmpty(partyInformation.getPartyDetails().getRelationshipWithProposer())) {
				payorDetails = partyInformation.getBasicDetails();
			}
				payorDiffFormProposer =
						Objects.nonNull(payorDetails) && Utility.isNotNullOrEmpty(payorDetails.getFirstName())
								? AppConstants.YES : AppConstants.NO;
			if (Objects.nonNull(partyInformation) && Objects.nonNull(partyInformation.getPersonalIdentification())
				&& Objects.nonNull(partyInformation.getPersonalIdentification().getPanDetails()) && Objects.nonNull(payorDetails)) {
				payorPan = partyInformation.getPersonalIdentification().getPanDetails().getPanNumber();
			}
		} else {
			payorDiffFormProposer = Objects.nonNull(proposalDetails.getAdditionalFlags()) ?
					(proposalDetails.getAdditionalFlags().isPayorDiffFromPropser() ? "YES" : "NO") : "NO";
		}
	    if (payorDiffFormProposer.equalsIgnoreCase("YES") && Objects.nonNull(payorDetails)) {
		payorName = Stream.of(payorDetails.getFirstName(), payorDetails.getMiddleName(), payorDetails.getLastName())
				.filter(s -> !StringUtils.isEmpty(s)).collect(Collectors.joining(" "));

		payorGender = getGender(payorDetails.getGender());
		if (Utility.schemeBCase(formType, schemeType) || (AppConstants.SELF.equalsIgnoreCase(formType)
				&& (AppConstants.CEIP.equalsIgnoreCase(productDetails.getObjectiveOfInsurance())
						|| AppConstants.EMPLOYER_EMPLOYEE.equalsIgnoreCase(productDetails.getObjectiveOfInsurance()))))
			payorGender = AppConstants.NA;
		
		/* start FUL2-19674 - transgender handling changes */
		if(AppConstants.OTHER.equalsIgnoreCase(payorGender)){
			payorGender = getGenderTrad(proposalDetails, payorGender);
		}
		/* end FUL2-19674 - Transgender handling changes */

		payorDOB = Utility.dateFormatter(payorDetails.getDob());
		payorAnnualIncome = payorDetails.getAnnualIncome();
		if (isNeoOrAggregator && Objects.nonNull(payorDetails.getAddress()) && !payorDetails.getAddress().isEmpty()) {
			payorAddress = payorDetails.getAddress().get(0).getAddressDetails().getHouseNo();
			payorDOB = Utility.dateFormatter(payorDOB, "dd-MM-yyyy", "dd/MM/yyyy");
		}
		else if (!isNeoOrAggregator && Objects.nonNull(payorDetails.getAddress()) &&
			!payorDetails.getAddress().isEmpty() && Objects.nonNull(payorDetails.getAddress().get(0).getAddressDetails())) {
			payorAddress = String.join(" ", payorDetails.getAddress().get(0).getAddressDetails().getArea(),
					payorDetails.getAddress().get(0).getAddressDetails().getState());
		}
		if (Objects.nonNull(proposalDetails.getPartyInformation()) && proposalDetails.getPartyInformation().size() > 2 ) {
			if (Objects.nonNull(proposalDetails.getPartyInformation().get(2).getPersonalIdentification()) &&
				Objects.nonNull(proposalDetails.getPartyInformation().get(2).getPersonalIdentification().getPanDetails())) {
				payorPan = Utility.evaluateConditionalOperation(StringUtils.isEmpty(proposalDetails.getPartyInformation().get(2).getPersonalIdentification().getPanDetails().getPanNumber()) , "NO"
						, proposalDetails.getPartyInformation().get(2).getPersonalIdentification().getPanDetails().getPanNumber());
			}
			if (Objects.nonNull(proposalDetails.getPartyInformation().get(2).getBankDetails())) {
				payorBank = Utility.evaluateConditionalOperation( StringUtils.isEmpty(proposalDetails.getPartyInformation().get(2).getBankDetails().getBankName()) , StringUtils.EMPTY
						, proposalDetails.getPartyInformation().get(2).getBankDetails().getBankName());
				payorBankBranch =Utility.evaluateConditionalOperation( StringUtils.isEmpty(proposalDetails.getPartyInformation().get(2).getBankDetails().getBankBranch()) , StringUtils.EMPTY
						, proposalDetails.getPartyInformation().get(2).getBankDetails().getBankBranch());
				payorAccountNumber = proposalDetails.getPartyInformation().get(2).getBankDetails().getBankAccountNumber();
			}
		}
		relationShipWithProposer = payorDetails.getRelationshipWithProposer();
	    }

		String panApplied = StringUtils.EMPTY;
		String panAck = StringUtils.EMPTY;
		String appDate = StringUtils.EMPTY;
		String isChargeableIncome = StringUtils.EMPTY;
		String isIncomeExceedLimit = StringUtils.EMPTY;
		String isTaxableIncome = StringUtils.EMPTY;
		String isItOtherIncome = StringUtils.EMPTY;
		String isNRI = StringUtils.EMPTY;
		String isApplicableIncome = StringUtils.EMPTY;
		boolean ispanNotRequired = false;
		String panNotRequired = "NO";


		if (StringUtils.isEmpty(pan)) {
			pan = "NO";
			ispanNotRequired = true;
			form60 = "YES";
			form49a = "YES";
			Form60Details form60Details = proposalDetails.getForm60Details();
			//NEORW-80: handle NULL pointer exception for form60Details
			if (Objects.nonNull(form60Details)) {
				panApplied = Utility.evaluateConditionalOperation(form60Details.getDetailsOfDontHavePan().equalsIgnoreCase("YES"), "YES", "NO");
				panAck = form60Details.getPanAcknowledgementNo();
				appDate = Utility.dateFormatter(form60Details.getPanApplicationDate());
				isChargeableIncome = Utility.evaluateConditionalOperation(form60Details.isChargeableIncome(), "YES", "NO");
				isIncomeExceedLimit = Utility.evaluateConditionalOperation(form60Details.isIncomeExceedLimit(), "YES", "NO");
				isTaxableIncome = Utility.evaluateConditionalOperation(form60Details.isTaxableIncome(), "YES", "NO");
				isItOtherIncome = Utility.evaluateConditionalOperation(form60Details.isItOtherIncome(), "YES", "NO");
				isNRI = Utility.evaluateConditionalOperation(form60Details.isNRI(), "YES", "NO");
				isApplicableIncome = Utility.evaluateConditionalOperation(form60Details.isApplicableIncome(), "YES", "NO");
			}
			panNotRequired = Utility.evaluateConditionalOperation(ispanNotRequired , "YES" , "NO");
		}

	    //NEORW-80: In case of NEO or Aggregator form60Details Object is not available
		String aadhar = "";
		String insuredAadhar = "";
		if (isNeoOrAggregator && Objects.nonNull(proposalDetails.getPartyInformation().get(0).getPersonalIdentification().getAadhaarDetails())) {
			aadhar = !StringUtils
					.isEmpty(proposalDetails.getPartyInformation().get(0).getPersonalIdentification().getAadhaarDetails().getAadhaarNumber())
					? AppConstants.YES : AppConstants.BLANK;
		} else {
			aadhar = !StringUtils
					.isEmpty(proposalDetails.getPartyInformation().get(0).getPersonalIdentification().getAadhaarDetails().getAadhaarNumber())
					? proposalDetails.getPartyInformation().get(0).getPersonalIdentification().getAadhaarDetails().getAadhaarNumber()
					: ("AADHAR".equalsIgnoreCase(proposalDetails.getForm60Details().getIdentityProofName())
					? proposalDetails.getForm60Details().getIdentityProofNumber() : "NO");
			insuredAadhar = !StringUtils
					.isEmpty(insuredDetails.getAadhaarDetails().getAadhaarNumber())
					? insuredDetails.getAadhaarDetails().getAadhaarNumber()
					: (AppConstants.AADHAAR.equalsIgnoreCase(proposalDetails.getForm60Details().getIdentityProofName())
					? proposalDetails.getForm60Details().getIdentityProofNumber() : "NO");
			//FUL2-5925 Aadhaar Masking in mPRO
			aadhar = Utility.maskAadhaarNumber(aadhar);
			insuredAadhar = Utility.maskAadhaarNumber(insuredAadhar);

		}
		//NEORW-352: In case of NEO or Aggregator
		String agentSelf = "";
		//FUL2-103459 Additional Flag isMaxEmp to check if user is/was MLI employee
		String maxEmp = "";
		if (isNeoOrAggregator){
			// NR-2160 Hardcoding agent question as NO for now.
			agentSelf = AppConstants.NO;
		} else {
			agentSelf = Utility.evaluateConditionalOperation((!StringUtils.isEmpty(proposalDetails.getAdditionalFlags().isAgentSelf())
					&& proposalDetails.getAdditionalFlags().isAgentSelf().equalsIgnoreCase("TRUE")) , "YES" , "NO");
			maxEmp = Utility.evaluateConditionalOperation((!StringUtils.isEmpty(proposalDetails.getAdditionalFlags().getIsMaxEmp())
					&& proposalDetails.getAdditionalFlags().getIsMaxEmp().equalsIgnoreCase("YES")) , "YES" , "NO");
		}

		//FUL2-7517 Smart Wealth Plan in mPRO - PF Changes
	    insuredForm60 = StringUtils.isEmpty(insuredPan) ? "YES" : "NO" ;

		//FUL2-7517 Smart Wealth Plan in mPRO - PF Changes
		//incomePayout = (Tag: illustrationOutputV2.annualIncomeAmt) * Income Benefit Frequency (12 if monthly, 4 if quarterly, 2 if semi-annual and 1 if Annual)
		String annualIncomeAmt = productDetails.getProductInfo().getProductIllustrationResponse().getAnnualIncomeAmt();
		//NR-706 added fields for SWP proposal form
		if (isNeoOrAggregator && AppConstants.SWP.equalsIgnoreCase(productDetails.getProductType())
				&& !StringUtils.isEmpty(productDetails.getProductInfo().getBenefitMonthlyIncome())
				&& !StringUtils.isEmpty(productDetails.getProductInfo().getBenefitReturnFrequency())) {

			incomePayoutFrequency = productDetails.getProductInfo().getBenefitReturnFrequency();
			swpIncomePayout = convertToBigDecimal(Double.valueOf(productDetails.getProductInfo().getBenefitMonthlyIncome()));
		}

		if (isNeoOrAggregator && AppConstants.YBL.equalsIgnoreCase(proposalDetails.getBankJourney())) {
			setCoverageTillAgeField(proposalDetails, dataVariables, coverageTerm);
		}
		if(AppConstants.LONGTERM_INCOME.equalsIgnoreCase(variant) && AppConstants.THANOS_CHANNEL.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())){
			premiumBackOption = AppConstants.YES;
		}
		// FUL2-113681_FUL2-120213 Joint Life option changes
		if ((isNeoOrAggregator && Utility.isSSPJLProduct(proposalDetails)) || (proposalDetails.getProductDetails() != null && !proposalDetails.getProductDetails().isEmpty()
				&& Utility.isSSPJointLife(productDetails))) {
			if(!isNeoOrAggregator){
				insuredForm60 = StringUtils.isEmpty(insuredPan) ? "NA" : "NO";
			}
			dataVariables.put("isJointLife", true);
		} else {
			insuredForm60 = "NA";
			insuredPan = "NA";
			dataVariables.put("isJointLife", false);
		}
		//FUL2-75012 added fields for PF Changes
        String incomeDefermentPeriod = productDetails.getProductInfo().getDefermentPeriod();
        String incomePeriod = productDetails.getProductInfo().getIncomePeriod();
        String incomeStartYear = productDetails.getProductInfo().getIncomeStartYear();
        String desiredDateOfIncomePayout = productDetails.getProductInfo().getDesiredDateOfIncomePayout();
        String desiredDate = productDetails.getProductInfo().getDesiredDate();
        dataVariables.put("incomeDefermentPeriod", StringUtils.isEmpty(incomeDefermentPeriod) ? "NA" : incomeDefermentPeriod);
        dataVariables.put("incomePeriod", StringUtils.isEmpty(incomePeriod) ? "NA" : incomePeriod);
        dataVariables.put("incomeStartYear", StringUtils.isEmpty(incomeStartYear) ? "NA" : incomeStartYear);
        dataVariables.put("desiredDateOfIncomePayout", StringUtils.isEmpty(desiredDateOfIncomePayout) ? "NA" : desiredDateOfIncomePayout);
        dataVariables.put("desiredDate", StringUtils.isEmpty(desiredDate) ? "No" : desiredDate); 
		
		boolean isSwpPosSellerNonLumSumVariant = isSwpPosSellerNonLumSumVariant(proposalDetails);
		dataVariables.put("formType", formType.toUpperCase());
	    dataVariables.put("basePlan", planName);
	    dataVariables.put("premiumBackOption",Utility.evaluateConditionalOperation( StringUtils.isEmpty(premiumBackOption) , "NA" , premiumBackOption));
	    dataVariables.put("coverageTerm", coverageTerm);
	    dataVariables.put("premimumPayingTerm", premiumPaymentTerm);
	   // FUL2 -144663
		dataVariables.put("sumAssured", Utility.evaluateConditionalOperation( StringUtils.isEmpty(sumAssured) , "NA" , sumAssured));
	    dataVariables.put("modalPremium", modalPremium);
	    dataVariables.put("sourceOfFund", funds);
	    dataVariables.put("modalPremiumGST", modalPremiumWithoutGST);
	    dataVariables.put("GSTCess", gstCess);
	    dataVariables.put("planGST", planGST);
	    dataVariables.put("totalPremium", totalPremium);
	    dataVariables.put("bonusOption", bonusOption);
	    dataVariables.put("pcb", pcb);
	    dataVariables.put("micrCode", micr);
	    dataVariables.put("accountNumber", accountNumber);
	    dataVariables.put("IFSCCode", ifsc);
	    dataVariables.put("accountHolderName", accHolder);
	    dataVariables.put("typeOfAcc", typeOfBank);
	    dataVariables.put("bankNameBranch", bankBranch);
	    dataVariables.put("bankingSince", bankingsince);
	    dataVariables.put("pan", pan);
		  dataVariables.put("insuredPan", insuredPan);
		dataVariables.put("isForm60", form60);
		dataVariables.put("isForm49a", form49a);
		dataVariables.put("appliedForPan", panApplied);
	    dataVariables.put("ackNo", panAck);
	    dataVariables.put("appDate", appDate);
	    dataVariables.put("panNotRequired", panNotRequired);
	    dataVariables.put("ispanNotRequired", ispanNotRequired);
	    dataVariables.put("isaadhar", aadhar);
	    dataVariables.put("modeOfPayment", modeOfPayment);
	    dataVariables.put("renewalPremium", renewalPayment);
	    dataVariables.put("isPayorDiffFromProposer", Objects.nonNull(proposalDetails.getAdditionalFlags()) ? proposalDetails.getAdditionalFlags().isPayorDiffFromPropser() : "");
	    dataVariables.put("payorDiffFromProposer", payorDiffFormProposer);
	    dataVariables.put("payorName", payorName);
	    dataVariables.put("relationShipwithProposer", relationShipWithProposer);
	    dataVariables.put("payoraddress", payorAddress);
	    dataVariables.put("payorGender", payorGender);
	    dataVariables.put("payorAnnualIncome", payorAnnualIncome);
	    dataVariables.put("payorPan", payorPan);
	    dataVariables.put("payorDOB", payorDOB);
	    dataVariables.put("payorAccountNumber", payorAccountNumber);
	    dataVariables.put("payorBankBranch", payorBank.concat(" ").concat(payorBankBranch));
	    dataVariables.put("isAgentSelf", agentSelf);
		//FUL2-103459 Additional Flag isMaxEmp to check if user is/was MLI employee
		dataVariables.put("isMaxEmp", maxEmp);
	    dataVariables.put("income", isChargeableIncome);
	    dataVariables.put("businessTurnover", isIncomeExceedLimit);
	    dataVariables.put("taxableincome", isTaxableIncome);
	    dataVariables.put("agriculture", isItOtherIncome);
	    dataVariables.put("nonResident", isNRI);
	    dataVariables.put("tribal", isApplicableIncome);
	    dataVariables.put("effectivePolicy", effectiveDateOfCoverage);
	    dataVariables.put("insuredForm60", insuredForm60);
	    dataVariables.put("isaadharinsured", insuredAadhar);
		dataVariables.put("SWPplan", isSmartWealthPlan);
		dataVariables.put("incomePayoutFrequency",StringUtils.isEmpty(incomePayoutFrequency) ? "NA" : incomePayoutFrequency);
	    dataVariables.put("variant", StringUtils.isEmpty(variant) ? "NA" : variant);
	    dataVariables.put("incomePayout", incomePayout);
		dataVariables.put("swpIncomePayout", swpIncomePayout);
	    dataVariables.put("isIncomePayout", isIncomePayout);
		dataVariables.put("isSwpLumpsum", Utility.checkIsSWPLumpsum(proposalDetails));
		dataVariables.put("isSwpWli", isSwpWli);
		dataVariables.put("isSwpPosSellerNonLumSumVariant", isSwpPosSellerNonLumSumVariant);		
	    dataVariables = setPaymentDetails(proposalDetails, dataVariables,isNeoOrAggregator);
	  //FUL2-46299_52545
	    if(Utility.isCapitalGuaranteeSolutionProduct(proposalDetails)) {
	    	Utility.convertNumberToWords((int) Double.parseDouble(totalPremium));
	    	String amountInWords = Utility.convertNumberToWords((int) Double.parseDouble(totalPremium));
	    	dataVariables.put("amount", totalPremium);
	    	dataVariables.put("amountWords", amountInWords);
		}
	    dataVariables = setDataForRiders(productDetails, dataVariables,Utility.isSSPJLProduct(proposalDetails));
		dataVariables = setDataForTermPlan(productDetails,dataVariables);
	    context.setVariables(dataVariables);
	} catch (Exception ex) {
		logger.error("Data addition failed for Proposal Form Document:",ex);
	    List<String> errorMessages = new ArrayList<String>();
	    errorMessages.add("Data addition failed");
	    throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	logger.info("Mapping coverage details of proposal form document is completed successfully for transactionId {}", proposalDetails.getTransactionId());
	return context;
    }

	private boolean checkingBankDetailsNullValue(ProposalDetails proposalDetails) {
		return Objects.nonNull(proposalDetails.getPartyInformation())
				&& !proposalDetails.getPartyInformation().isEmpty()
				&& Objects.nonNull(proposalDetails.getPartyInformation().get(0))
				&& Objects.nonNull(proposalDetails.getPartyInformation().get(0).getBankDetails())
		        && Objects.nonNull(proposalDetails.getPartyInformation().get(0).getBankDetails().getBankAccountNumber());
	}

	private void setIncomeRelatedValue(ProductDetails productDetails, Map<String, Object> dataVariables) {
		String incomeDefermentPeriod = "";
		String incomePeriod = "";
		String incomeStartYear = "";
		String availDate = "";
		String availProvideDate = "";
        if(Objects.nonNull(productDetails) && Objects.nonNull(productDetails.getProductInfo())) {
			incomeDefermentPeriod = org.springframework.util.StringUtils.isEmpty(productDetails.getProductInfo().getDefermentPeriod()) ? "" : productDetails.getProductInfo().getDefermentPeriod();
			incomePeriod = org.springframework.util.StringUtils.isEmpty(productDetails.getProductInfo().getIncomePeriod()) ? "" : productDetails.getProductInfo().getIncomePeriod();
			incomeStartYear = org.springframework.util.StringUtils.isEmpty(productDetails.getProductInfo().getIncomeStartYear()) ? "" : productDetails.getProductInfo().getIncomeStartYear();
			availProvideDate = org.springframework.util.StringUtils.isEmpty(productDetails.getProductInfo().getDesiredDate()) ? "" : productDetails.getProductInfo().getDesiredDate();
			availDate = org.springframework.util.StringUtils.isEmpty(productDetails.getProductInfo().getDesiredDateOfIncomePayout()) ? "" : productDetails.getProductInfo().getDesiredDateOfIncomePayout();
		}
		dataVariables.put("incomeDefermentPeriod", incomeDefermentPeriod);
		dataVariables.put("incomePeriod", incomePeriod);
		dataVariables.put("incomeStartYear", incomeStartYear);
		dataVariables.put("AvailDate", availDate);
		dataVariables.put("AvailProvideDate", availProvideDate);
	}


	private String setSourceOfFundsForNeo(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {
    	String occupation = "";
    	String sourceOfFund = "";
		if (Objects.nonNull(proposalDetails.getEmploymentDetails())
				&& Objects.nonNull(proposalDetails.getEmploymentDetails().getPartiesInformation())
				&& !proposalDetails.getEmploymentDetails().getPartiesInformation().isEmpty()) {
			if (Utility.isApplicationIsForm2(proposalDetails)) {
				BasicDetails basicDetails = proposalDetails.getEmploymentDetails().getPartiesInformation()
						.stream()
						.filter(partyInformation -> "Proposer".equalsIgnoreCase(partyInformation.getPartyType()))
						.map(PartyInformation::getBasicDetails)
						.findFirst()
						.orElse(null);
				if (Objects.nonNull(basicDetails) && !StringUtils.isEmpty(basicDetails.getOccupation())) {
					occupation = basicDetails.getOccupation();
					sourceOfFund = sourceOfFunds.get(basicDetails.getOccupation().toLowerCase());
				}
			} else {
				BasicDetails basicDetails = proposalDetails.getEmploymentDetails().getPartiesInformation()
						.stream()
						.filter(partyInformation -> "LifeInsured".equalsIgnoreCase(partyInformation.getPartyType()))
						.map(PartyInformation::getBasicDetails)
						.findFirst()
						.orElse(null);
				if (Objects.nonNull(basicDetails) && !StringUtils.isEmpty(basicDetails.getOccupation())) {
					occupation = basicDetails.getOccupation();
					sourceOfFund = sourceOfFunds.get(basicDetails.getOccupation().toLowerCase());
				}
			}
		}
		dataVariables.put("occupation", occupation);
    	return sourceOfFund;
	}

    private void setCoverageTillAgeField(ProposalDetails proposalDetails, Map<String, Object> dataVariables, String coverageTerm) {
        String coverageTillAge = "";
        try {
            Date dateOfBirth = proposalDetails.getPartyInformation().get(0).getBasicDetails().getDob();
            LocalDate birthDate = new LocalDate(dateOfBirth);
            LocalDate currentDate = new LocalDate();
            Years age = Years.yearsBetween(birthDate, currentDate);
            coverageTillAge = String.valueOf(Integer.parseInt(coverageTerm) + age.getYears());
        } catch (Exception e) {
            e.printStackTrace();
        }
        dataVariables.put("coverageTillAge", coverageTillAge);
    }

    protected Map<String, Object> setPaymentDetails(ProposalDetails proposalDetails, Map<String, Object> dataVariables,boolean isNeoOrAggregator) throws Exception {
    //FUL2-11549 Payment acknowledgement for all channels
    // Fetching receipt data for Secondary policy using getProposal api from proposal service - To fetch date for the salesstory secondary policy.
    proposalDetails = docsAppServiceImpl.setSecondPolicyReceipt(proposalDetails);
    Receipt receipt = proposalDetails.getPaymentDetails().getReceipt().get(0);
	String paymentType = receipt.getPremiumMode();
	boolean isChannelAggregator = AppConstants.CHANNEL_AGGREGATOR.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel());
	double amount = 0;
		String paymentDate = StringUtils.EMPTY;
		String paymentMode = StringUtils.EMPTY;
		String transactionNumber = StringUtils.EMPTY;
		String bankName = StringUtils.EMPTY;
	long chequeNumber = 0;
	Boolean enachStatus = Objects.nonNull(proposalDetails.getBank()) && proposalDetails.getBank().getEnachDetails() != null
			&& Utility.andTwoExpressions( !isEmpty(proposalDetails.getBank().getEnachDetails().getEnachStatus())
			, "SUCCESS".equalsIgnoreCase(proposalDetails.getBank().getEnachDetails().getEnachStatus()));


		logger.info("Mapping payment details of proposal form document for transactionId {}", proposalDetails.getTransactionId());
	if (!isNeoOrAggregator) {
		enachStatus = proposalDetails.getBank().getEnachDetails() == null ? Boolean.FALSE : isEmpty(proposalDetails.getBank().getEnachDetails().getEnachStatus()) ? Boolean.FALSE : proposalDetails.getBank().getEnachDetails().getEnachStatus().equalsIgnoreCase("SUCCESS") ? Boolean.TRUE : Boolean.FALSE;
	}
	try {
		if (AppConstants.PAY_LATER.equalsIgnoreCase(paymentType) && !isEmpty(receipt.getIsSIOpted()) && receipt.getIsSIOpted().equalsIgnoreCase("True")) {
			amount = Utility.setPremiumAmount(proposalDetails);
			paymentDate = receipt.getTransactionDateTimeStamp();
			paymentMode = "PAY LATER";
			transactionNumber = receipt.getTransactionReferenceNumber();
			bankName = AppConstants.NA;
			dataVariables.put(AppConstants.TRANSACTION_NUMBER, transactionNumber);

		}
		else if (AppConstants.PAY_LATER.equalsIgnoreCase(paymentType) && enachStatus) {
			amount = Utility.setPremiumAmount(proposalDetails);
			paymentDate = proposalDetails.getBank().getEnachDetails().getFirstCollectionDate().toString();
			paymentMode = "PAY LATER";
			transactionNumber = proposalDetails.getBank().getEnachDetails().getIngenicoTransactionId();
			bankName = proposalDetails.getBank().getEnachDetails().getSponsorBank();
			dataVariables.put(AppConstants.TRANSACTION_NUMBER, transactionNumber);

		}
	    else if (AppConstants.ONLINE.equalsIgnoreCase(paymentType)) {
		amount = Utility.setPremiumAmount(proposalDetails);
		//NEORW-80: In case of NEO or Aggregator paymentDate and transactionNumber mapping comes in paymentDate and receiptId fields respectively
		if (isNeoOrAggregator) {
			amount = Double.valueOf(proposalDetails.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getInitialPremiumPaid());
			paymentDate = Utility.dateFormatter(receipt.getPaymentDate(), (isChannelAggregator
					? AppConstants.DD_MM_YYYY_HYPHEN : AppConstants.DD_MM_YYYY_HH_MM_SS_HYPHEN), AppConstants.DD_MM_YYYY_HH_MM_SS_AA_SLASH);
			transactionNumber = receipt.getReceiptId();
			dataVariables.put("desiredEffectiveDateOfPolicy", Utility.dateFormatter(receipt.getPaymentDate(),
					isChannelAggregator ? AppConstants.DD_MM_YYYY_HYPHEN : AppConstants.DD_MM_YYYY_HH_MM_SS_HYPHEN, AppConstants.DD_MM_YYYY_SLASH));
		} else {
				if (AppConstants.THANOS_CHANNEL.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())) {
				paymentDate = Utility
						.dateFormatter(receipt.getTransactionDateTimeStamp(), AppConstants.YYYY_MM_DD_HH_MM_SS_SSS_Z,
								AppConstants.DATE_FORMAT);
				} else if(Utility.compareDateFormats(receipt.getTransactionDateTimeStamp(), AppConstants.DD_MM_YYYY_HH_MM_SS_HYPHEN)) {
					paymentDate = Utility
							.dateFormatter(receipt.getTransactionDateTimeStamp(), AppConstants.DD_MM_YYYY_HH_MM_SS_HYPHEN,
									AppConstants.DATE_FORMAT);
				} else if(AppConstants.J3_JOURNEY.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getIsOnboardedProduct())){
					paymentDate = Utility.dateFormatter(receipt.getTransactionDateTimeStamp(), AppConstants.DD_MM_YYYY_HH_MM_SS_HYPHEN,
							AppConstants.DATE_FORMAT);
				} else {
				paymentDate = Utility
						.dateFormatter(receipt.getTransactionDateTimeStamp(), AppConstants.YYYY_MM_DD_HH_MM_SS_SSS_Z_CHAR,
								AppConstants.DATE_FORMAT);
				}
			transactionNumber = receipt.getTransactionReferenceNumber();
		}
		paymentMode = isNeoOrAggregator ? receipt.getModeOfPayment() : "ONLINE";
		bankName = "NA";
		dataVariables.put(AppConstants.TRANSACTION_NUMBER, transactionNumber);

	    } else if (AppConstants.CHEQUE.equalsIgnoreCase(paymentType)) {
		amount = Utility.setPremiumAmount(proposalDetails);
		paymentDate = Utility.dateFormatter(receipt.getPaymentChequeDetails().getChequeDate());
		paymentMode = AppConstants.CHEQUE;
		chequeNumber = receipt.getPaymentChequeDetails().getChequeNumber();
		bankName = receipt.getPaymentChequeDetails().getChequeBankName();
		dataVariables.put(AppConstants.TRANSACTION_NUMBER, chequeNumber);

	    } else if (AppConstants.DEMAND_DRAFT.equalsIgnoreCase(paymentType)) {
		amount = Utility.setPremiumAmount(proposalDetails);
		paymentDate = Utility.dateFormatter(receipt.getDemandDraftDetails().getDemandDraftDate());
		paymentMode = "DEMAND DRAFT";
		transactionNumber = receipt.getDemandDraftDetails().getDemandDraftNumber();
		bankName = receipt.getDemandDraftDetails().getDemandDraftBankName();
		dataVariables.put(AppConstants.TRANSACTION_NUMBER, transactionNumber);

	    } else if (AppConstants.DIRECTDEBIT.equalsIgnoreCase(paymentType) || AppConstants.DIRECTDEBITWITHRENEWALS.equalsIgnoreCase(paymentType)) {

		if (Utility.andTwoExpressions(proposalDetails.getChannelDetails().getChannel().equalsIgnoreCase("BY")
				, ( receipt.getYblPaymentDetails() != null
				&& receipt.getYblPaymentDetails().getDirectDebitDetails() != null))) {
		    transactionNumber = receipt.getYblPaymentDetails().getDirectDebitDetails().getExternalRefNumber();
		    paymentDate = Utility.evaluateConditionalOperation(
		    		!StringUtils.isEmpty(receipt.getYblPaymentDetails().getDirectDebitDetails().getDirectDebitOtpValidated())
			    	, receipt.getYblPaymentDetails().getDirectDebitDetails().getDirectDebitOtpValidated()
					, Utility.dateFormatter(new Date()));
		} else if (Utility.andTwoExpressions(Utility.isTMBPartner(proposalDetails.getAdditionalFlags().getSourceChannel())
				, ( receipt.getPartnerPaymentDetails() != null
						&& receipt.getPartnerPaymentDetails().getDirectDebitDetails() != null))) {
			transactionNumber = receipt.getPartnerPaymentDetails().getDirectDebitDetails().getExternalRefNumber();
			paymentDate = Utility.evaluateConditionalOperation(
					!StringUtils.isEmpty(receipt.getPartnerPaymentDetails().getDirectDebitDetails().getDirectDebitOtpValidated())
					, receipt.getPartnerPaymentDetails().getDirectDebitDetails().getDirectDebitOtpValidated()
					, Utility.dateFormatter(new Date()));
		} else if (receipt.getDirectPaymentDetails() != null) {
		    transactionNumber = receipt.getDirectPaymentDetails().getvoucherNumber();
		    paymentDate =Utility.evaluateConditionalOperation(
		    		receipt.getDirectPaymentDetails().getVoucherUpdatedDate() != null
			   		, Utility.dateFormatter(receipt.getDirectPaymentDetails().getVoucherUpdatedDate())
					, Utility.dateFormatter(new Date()));
		} else if(Objects.nonNull(proposalDetails.getAdditionalFlags()) && Objects.nonNull(proposalDetails.getAdditionalFlags().getRequestSource())
		  && (proposalDetails.getAdditionalFlags().getRequestSource().equalsIgnoreCase("Thanos 1") ||
				proposalDetails.getAdditionalFlags().getRequestSource().equalsIgnoreCase("Thanos 2")) || REQUEST_SOURCE_TELESALES.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getRequestSource())){
		  	transactionNumber = receipt.getPaymentReferenceCode();
				paymentDate = Utility
						.dateFormatter(receipt.getTransactionDateTimeStamp(), AppConstants.YYYY_MM_DD_HH_MM_SS_SSS_Z,
								AppConstants.DATE_FORMAT);
		}
		amount = Utility.setPremiumAmount(proposalDetails);

			if(proposalDetails.getAdditionalFlags().getRequestSource().equalsIgnoreCase(AppConstants.AXIS_TELESALES_REQUEST)
					&& AppConstants.DIRECTDEBIT.equalsIgnoreCase(paymentType)) {
				paymentMode = "DIRECT DEBIT (IVR)";
			} else {
				paymentMode = "DIRECT DEBIT";
			}

			bankName = StringUtils.EMPTY;
		dataVariables.put(AppConstants.TRANSACTION_NUMBER, transactionNumber);

	    }
        else if (AppConstants.YES.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getIsPtfPayment())
                && proposalDetails.getProductDetails() != null && !proposalDetails.getProductDetails().isEmpty()
                && proposalDetails.getProductDetails().get(0) != null
                && proposalDetails.getProductDetails().get(0).getProductInfo() != null
                && AppConstants.GLIP_ID
                        .equals(proposalDetails.getProductDetails().get(0).getProductInfo().getProductId())) {
            amount = Utility.setPremiumAmount(proposalDetails);
            transactionNumber = proposalDetails.getAdditionalFlags().getPtfPolicyNumber();
            bankName = AppConstants.BLANK;
            paymentMode = AppConstants.PTF_PAYMENT;
			paymentDate = Utility.dateFormatter(proposalDetails.getPosvDetails().getPosvStatus().getSubmittedOTPDate());
            dataVariables.put(AppConstants.TRANSACTION_NUMBER, transactionNumber);
        }
	    String amountInWords = Utility.convertNumberToWords((int) amount);
	    dataVariables.put("amountWords", amountInWords);
		if(STPP_PRODUCT_ID.equalsIgnoreCase(Utility.getProductId(proposalDetails))){
			String formattedValue =(amount > 0) ? String.format("%.2f", amount) : ZERO;
			dataVariables.put("amount",formattedValue);
		}
		else {
			dataVariables.put("amount", amount);
		}
	    dataVariables.put("paymentMode", paymentMode);
	    dataVariables.put("paymentDate", paymentDate);
	    dataVariables.put("paymentBank", bankName);
	} catch (Exception ex) {
		logger.error("Mapping payment details of proposal form document is not found:",ex);
	    throw new Exception();
	}
	return dataVariables;

    }

    private double roundOffValue(double value) {

	return Math.round(value * 100.00) / 100.00;
    }

    private double roundOffValue(String value) {

	double convertedValue = 0;
	if (!StringUtils.isEmpty(value)) {
	    convertedValue = Math.round(Double.valueOf(value) * 100.00) / 100.00;
	}
	return convertedValue;
    }

    private Map<String, Object> setDataForRiders(ProductDetails productDetails, Map<String, Object> dataVariables, boolean isSSPJL) throws Exception {

	logger.info("Mapping rider details of proposal form document");

	List<RiderDetails> riderDetails = productDetails.getProductInfo().getRiderDetails();
	List<ProposalRiderDetails> proposalRiderDetails = new ArrayList<>();
	ProductIllustrationResponse illustrationResponse = productDetails.getProductInfo().getProductIllustrationResponse();
	String premiumBackOption = productDetails.getProductInfo().getPremiumBackOption();
        // if riderDetails list is not empty
        if (!riderDetails.isEmpty()) {
            try {
				for (int riderCount = 0; riderCount < riderDetails.size(); riderCount++) {

					checkRiderDataRequired(riderDetails, proposalRiderDetails, illustrationResponse, premiumBackOption, riderCount,isSSPJL);

					if (!proposalRiderDetails.isEmpty()) {
						dataVariables.put("riderExist", true);
					}
				}
				setRiderPremiumPayingTerm(productDetails, proposalRiderDetails);
			} catch (Exception ex) {
			logger.error("Riding Details not found:",ex);
			throw new Exception();
		}
	}
    setJointLifeDetails(productDetails, proposalRiderDetails, dataVariables);
		if (proposalRiderDetails.isEmpty()) {
			ProposalRiderDetails proposalRiderDetails1 = new ProposalRiderDetails("NA","NA" ,"NA","NA","NA",true,"NA","NA");
			proposalRiderDetails.add(proposalRiderDetails1);
		}
	dataVariables.put("riderDetails", proposalRiderDetails);
	return dataVariables;
    }
    // FUL2-113681_FUL2-120213 Joint Life option changes
	private void setJointLifeDetails(ProductDetails productDetails, List<ProposalRiderDetails> proposalRiderDetails,
			Map<String, Object> dataVariables) {
		if (Utility.isSSPJointLife(productDetails)) {
			ProposalRiderDetails details = new ProposalRiderDetails();
			details.setRiderName(AppConstants.JOINT_LIFE_BENEFIT);
			details.setRiderSumAssured(String.format("%.0f",
					roundOffValue(productDetails.getProductInfo().getProductIllustrationResponse().getJlSumAssured())));
			details.setCoverageTerm(productDetails.getProductInfo().getProductIllustrationResponse().getJlPolicyTerm());
			details.setModalPremium(String.format("%.0f", roundOffValue(
					productDetails.getProductInfo().getProductIllustrationResponse().getJlPremiumWithoutGST())));
			details.setRiderGST(String.format("%.0f",
					roundOffValue(productDetails.getProductInfo().getProductIllustrationResponse().getJlServiceTax()))); // gst = premiumWithGST - premiumWithoutGST
			details.setPremiumBackOption(productDetails.getProductInfo().getPremiumBackOption());
			details.setPremiumPayingTerm(productDetails.getProductInfo().getPremiumPaymentTerm());
			dataVariables.put("riderExist", true);
			proposalRiderDetails.add(details);
		}
	}

	private void setRiderPremiumPayingTerm(ProductDetails productDetails, List<ProposalRiderDetails> proposalRiderDetails) {
		proposalRiderDetails.forEach(proposalRiderDetails1 -> {
			if (proposalRiderDetails1.getPremiumPayingTerm().equalsIgnoreCase("NA") && (!proposalRiderDetails1.getRiderName().equalsIgnoreCase("Critical Illness and Disability Rider") || !proposalRiderDetails1.getRiderName().equalsIgnoreCase("COVID 19 One Year Term Rider"))) {
				proposalRiderDetails1.setPremiumPayingTerm(productDetails.getProductInfo().getPremiumPaymentTerm());
			}
		});
	}

	private void checkRiderDataRequired(List<RiderDetails> riderDetails, List<ProposalRiderDetails> proposalRiderDetails,
			ProductIllustrationResponse illustrationResponse, String premiumBackOption, int riderCount, boolean isSSPJL) {
		ProposalRiderDetails details;
		if (riderDetails.get(riderCount).isRiderRequired()) {
		    details = new ProposalRiderDetails();
		    details.setRiderRequired(true);
			String riderName = riderDetails.get(riderCount).getRiderInfo();
			details.setPremiumBackOption(StringUtils.isEmpty(premiumBackOption) ? "NA" : premiumBackOption);
		    details.setPremiumPayingTerm("NA");
			ProposalRiderDetails propRiderDetails = setRiderSpecificData(riderDetails, illustrationResponse, riderCount, details, riderName);
			propRiderDetails.setPremiumBackOption(StringUtils.isEmpty(propRiderDetails.getPremiumBackOption()) ? "NA" : propRiderDetails.getPremiumBackOption());
			propRiderDetails.setPremiumPayingTerm(StringUtils.isEmpty(propRiderDetails.getPremiumPayingTerm()) ? "NA" : propRiderDetails.getPremiumPayingTerm());
			proposalRiderDetails.add(propRiderDetails);
		}
	}


    private ProposalRiderDetails setProposalRiderDetailsWOP(ProductIllustrationResponse illustrationResponse) {
        ProposalRiderDetails details = new ProposalRiderDetails();
        double riderSumAssuredWOP = 0;
        details.setRiderName(AppConstants.AXIS_WOP);
        details.setCoverageTerm(
                !StringUtils.isEmpty(illustrationResponse.getWopPlusRiderTerm()) ? illustrationResponse.getWopPlusRiderTerm() : "0");
        details.setModalPremium(String.format("%.0f", roundOffValue(illustrationResponse.getFirstYearWOPPlusRiderPremiumSummary())));
        riderSumAssuredWOP = setRiderSumAssuredWOP(illustrationResponse, riderSumAssuredWOP);
		details.setRiderSumAssured(String.format("%.0f", roundOffValue(riderSumAssuredWOP)));
		if (illustrationResponse.getWopPlusRiderGST().equals("0") || StringUtils.isEmpty(illustrationResponse.getWopPlusRiderGST())) {
			details.setRiderGST("");
		} else {
			details.setRiderGST(illustrationResponse.getWopPlusRiderGST());
		}
        return details;
    }

    private ProposalRiderDetails setProposalRiderDetailsTERM(ProductIllustrationResponse illustrationResponse){
        ProposalRiderDetails details = new ProposalRiderDetails();
        details.setRiderName(AppConstants.AXIS_TERM);
        details.setCoverageTerm(
                !StringUtils.isEmpty(illustrationResponse.getTermPlusRiderTerm()) ? illustrationResponse.getTermPlusRiderTerm() : "0");
        details.setModalPremium(String.format("%.0f", roundOffValue(illustrationResponse.getFirstYearTermPlusRiderPremiumSummary())));
        details.setRiderSumAssured(String.format("%.0f", roundOffValue(illustrationResponse.getTermPlusRiderSumAssured())));
        details.setRiderGST(!StringUtils.isEmpty(illustrationResponse.getTermPlusRiderGST()) ? illustrationResponse.getTermPlusRiderGST() : "");
        return details;
    }

    private ProposalRiderDetails setProposalRiderDetailsACD(ProductIllustrationResponse illustrationResponse){
        ProposalRiderDetails details = new ProposalRiderDetails();
        details.setRiderName(AppConstants.AXIS_ACD);
        details.setCoverageTerm(!StringUtils.isEmpty(illustrationResponse.getAddRiderTerm()) ? illustrationResponse.getAddRiderTerm() : "0");
        details.setModalPremium(String.format("%.0f", roundOffValue(illustrationResponse.getFirstYearADDRiderPremiumSummary())));
        details.setRiderSumAssured(String.format("%.0f", roundOffValue(illustrationResponse.getAddRiderSumAssured())));
        details.setRiderGST(!StringUtils.isEmpty(illustrationResponse.getAddRiderGST()) ? illustrationResponse.getAddRiderGST() : "");
        return details;
    }
    private ProposalRiderDetails setProposalRiderDetailsAcdCover(ProductIllustrationResponse illustrationResponse){
        ProposalRiderDetails details = new ProposalRiderDetails();
        details.setRiderName(AppConstants.AXIS_ACD_COVER);
        details.setCoverageTerm(!StringUtils.isEmpty(illustrationResponse.getAccidentCoverRiderTerm())
                ? illustrationResponse.getAccidentCoverRiderTerm() : "0");
        details.setModalPremium(String.format("%.0f", roundOffValue(illustrationResponse.getFirstYearAccidentCoverRiderPremiumSummary())));
        details.setRiderSumAssured(String.format("%.0f", roundOffValue(illustrationResponse.getAccidentCoverRiderSumAssured())));
        details.setRiderGST(!StringUtils.isEmpty(illustrationResponse.getAccidentalCoverRiderGST())
                ? illustrationResponse.getAccidentalCoverRiderGST() : "");
        return details;
    }
    private ProposalRiderDetails setProposalRiderDetailsPARTNER(ProductIllustrationResponse illustrationResponse){
        ProposalRiderDetails details = new ProposalRiderDetails();
        details.setRiderName(AppConstants.AXIS_PARTNER);
        details.setCoverageTerm(
                !StringUtils.isEmpty(illustrationResponse.getPartnerCareRiderTerm()) ? illustrationResponse.getPartnerCareRiderTerm() : "0");
        details.setModalPremium(String.format("%.0f", roundOffValue(illustrationResponse.getFirstYearPartnerCareRiderPremiumSummary())));
        details.setRiderSumAssured(String.format("%.0f", roundOffValue(illustrationResponse.getPartnerCareRiderSumAssured())));
        details.setRiderGST("");
        return details;
    }
    private ProposalRiderDetails setProposalRiderDetailsCI(ProductIllustrationResponse illustrationResponse){
        ProposalRiderDetails details = new ProposalRiderDetails();
        details.setRiderName(AppConstants.AXIS_CI);
        details.setCoverageTerm(!StringUtils.isEmpty(illustrationResponse.getAcceleratedCriticalIllnessRiderTerm())
                ? illustrationResponse.getAcceleratedCriticalIllnessRiderTerm() : "0");
        details.setModalPremium(String.format("%.0f", roundOffValue(illustrationResponse.getFirstYearAcceleratedCriticalIllnessRiderPremiumSummary())));
        details.setRiderSumAssured(String.format("%.0f", roundOffValue(illustrationResponse.getAcceleratedCriticalIllnessRiderSumAssured())));
        details.setRiderGST(!StringUtils.isEmpty(illustrationResponse.getAccidentalCriticalIllnessRiderGST())
                ? illustrationResponse.getAccidentalCriticalIllnessRiderGST() : "");
        return details;
    }
    private ProposalRiderDetails setProposalRiderDetailsCAB(ProductIllustrationResponse illustrationResponse){
        ProposalRiderDetails details = new ProposalRiderDetails();
        details.setRiderName(AppConstants.AXIS_CAB);
        details.setCoverageTerm(!StringUtils.isEmpty(illustrationResponse.getCABRiderTerm())
                ? illustrationResponse.getCABRiderTerm() : "0");
        details.setModalPremium(String.format("%.0f", roundOffValue(illustrationResponse.getCABRiderPremium())));
        details.setRiderSumAssured(String.format("%.0f", roundOffValue(illustrationResponse.getCABRiderSumAssured())));
        details.setRiderGST(!StringUtils.isEmpty(illustrationResponse.getCABRiderGST())
                ? illustrationResponse.getCABRiderGST() : "");
		details.setRiderGST(!StringUtils.isEmpty(illustrationResponse.getCABRiderGST())
				? String.format("%.0f", roundOffValue(illustrationResponse.getCABRiderGST())) : "");
        return details;
    }
    private ProposalRiderDetails setProposalRiderDetailsCOVID(ProductIllustrationResponse illustrationResponse){
        ProposalRiderDetails details = new ProposalRiderDetails();
        details.setRiderName(AppConstants.COVID);
        details.setCoverageTerm(AppConstants.ONE);
        details.setModalPremium(String.format("%.0f", roundOffValue(illustrationResponse.getCovideRiderPremium())));
        details.setRiderSumAssured(String.format("%.0f", roundOffValue(illustrationResponse.getCovideRiderSumAssured())));
        //FUL2-11755 COVID Rider GST: Setting value of COVID GST
		if(("0".equals(illustrationResponse.getCovideRiderGST())) || StringUtils.isEmpty(illustrationResponse.getCovideRiderGST())){
			details.setRiderGST("");
		}else {
			details.setRiderGST(illustrationResponse.getCovideRiderGST());
		}
        details.setPremiumPayingTerm(AppConstants.ONE);
        return details;
    }
    private ProposalRiderDetails setProposalRiderDetailsCiRider(ProductIllustrationResponse illustrationResponse, List<RiderDetails> riderDetails,int riderCount, String riderName){
    	String riderNameCIDR = riderName;
    	ProposalRiderDetails details = new ProposalRiderDetails();
        riderName = getCIRiderVariantName(riderDetails, riderCount, riderName);
        details = TraditionalFormUtil.setCIRiderDetails(illustrationResponse, details, riderName);
        // The PPT for FUL2-224743 can be 20 or less for non-ULIP products and will be mapped from the UI/LEResponse tag at a later stage.
        try {
            if (AppConstants.CI_RIDER.equalsIgnoreCase(riderNameCIDR) &&  Integer.parseInt(details.getPremiumPayingTerm()) > 20) {
                details.setPremiumPayingTerm("20");
            }
        } catch(Exception e) {
            logger.error("Could not map PPT for CIDR");
        }
        return details;
    }

	//FUL2-9523 set CI Rider details - Refactored below method to reduce cognitive complexity
	private ProposalRiderDetails setRiderSpecificData(List<RiderDetails> riderDetails,
													  ProductIllustrationResponse illustrationResponse, int riderCount, ProposalRiderDetails details, String riderName) {
		switch (riderName) {
			case AppConstants.WOP:
			case AppConstants.AXIS_WOP:
				details = setProposalRiderDetailsWOP(illustrationResponse);
				break;
			case AppConstants.TERM:
			case AppConstants.AXIS_TERM:
				details = setProposalRiderDetailsTERM(illustrationResponse);
				break;
			case AppConstants.ACD:
			case AppConstants.AXIS_ACD:
				details = setProposalRiderDetailsACD(illustrationResponse);
				break;
			case AppConstants.ACD_COVER:
			case AppConstants.AXIS_ACD_COVER:
				details = setProposalRiderDetailsAcdCover(illustrationResponse);
				break;
			case AppConstants.PARTNER:
			case AppConstants.AXIS_PARTNER:
				details = setProposalRiderDetailsPARTNER(illustrationResponse);
				break;
			case AppConstants.CI:
			case AppConstants.AXIS_CI:
				details = setProposalRiderDetailsCI(illustrationResponse);
				break;
			case AppConstants.CAB:
			case AppConstants.AXIS_CAB:
				details = setProposalRiderDetailsCAB(illustrationResponse);
				break;
			case AppConstants.COVID:
				details = setProposalRiderDetailsCOVID(illustrationResponse);
				break;
			//FUL2-9523 set CI Rider details
			case AppConstants.CI_RIDER:
				//FUL2-30525 CIDR- Rider Name Change for ULIP products
				case AppConstants.CI_RIDER_ULIP:
				case AppConstants.AXIS_CI_RIDER_ULIP:
				details = setProposalRiderDetailsCiRider(illustrationResponse, riderDetails, riderCount, riderName);
				break;
			case AppConstants.MATERNITY_COVER:
			case AppConstants.AXIS_MATERNITY_COVER:
				details = setMaternityCoverRiderDetails(illustrationResponse);
                break;
			default:
				logger.info("No Rider details found for rider name {}", riderName);
		}
		return setRiderDetailsNeoAggregator(riderDetails, riderCount, details, riderName, illustrationResponse);
	}

	/**
	 * @param riderDetails
	 * @param riderCount
	 * @param details
	 * @param riderName
	 */
	private ProposalRiderDetails setRiderDetailsNeoAggregator(List<RiderDetails> riderDetails, int riderCount,
			ProposalRiderDetails details, String riderName,
			ProductIllustrationResponse illustrationResponse) {
		if (riderListName.contains(riderName) && isNeoOrAggregator) {
			details.setRiderName(riderDetails.get(riderCount).getTypeOfValue());
			details.setCoverageTerm(!StringUtils.isEmpty(riderDetails.get(riderCount).getRiderTerm()) ? riderDetails.get(riderCount).getRiderTerm() : "0");
			details.setModalPremium(String.valueOf(riderDetails.get(riderCount).getRiderModalPremium()));
			details.setRiderSumAssured(new BigDecimal(riderDetails.get(riderCount).getRiderSumAssured(), MathContext.DECIMAL64).setScale(2, RoundingMode.CEILING).toString());
			details.setRiderGST(!StringUtils.isEmpty(riderDetails.get(riderCount).getRiderServiceTax())
					? String.valueOf(riderDetails.get(riderCount).getRiderServiceTax()) : "0");
			setPremiumPayingTerm(riderDetails, riderCount, details, riderName, illustrationResponse);
		}
		return details;
	}

	private void setPremiumPayingTerm(List<RiderDetails> riderDetails, int riderCount,
			ProposalRiderDetails details, String riderName,
			ProductIllustrationResponse illustrationResponse) {
		if(Utility.isCC60Rider(riderName)){
			details.setPremiumPayingTerm(riderDetails.get(riderCount).getCiRiderPpt());
		}else if(riderName.equalsIgnoreCase(TCADB)){
			details.setPremiumPayingTerm(illustrationResponse.getPremiumPaymentTerm());
		}else if(details.getRiderName().equalsIgnoreCase(SSPJL_RIDERS_NAME) || details.getRiderName().equalsIgnoreCase(AXIS_SSPJL_RIDERS_NAME)){
			details.setPremiumPayingTerm(illustrationResponse.getJlPremiumPaymentTerm());
		}else {
			details.setPremiumPayingTerm(AppConstants.RIDER_PPT);
		}
	}

	/**
	 * @param riderDetails
	 * @param riderCount
	 * @param riderName
	 */
	//FUL2-9523 CI Rider - concat riderName with variant as per requirement .
	private String getCIRiderVariantName(List<RiderDetails> riderDetails, int riderCount, String riderName) {
		String riderVariant = riderDetails.get(riderCount).getRiderVariant();
		//FUL2-30525 CIDR- Rider Name Change for ULIP products
		if(AppConstants.CI_RIDERS.contains(riderName)) {
			riderName = riderName +" - "+ (!StringUtils.isEmpty(riderVariant) ? riderVariant : AppConstants.BLANK);
		}
		return riderName;
	}
	//FUL2-13533
	private double setRiderSumAssuredWOP(ProductIllustrationResponse illustrationResponse, double riderSumAssuredWOP) {
		try {
			riderSumAssuredWOP = Double.valueOf(zeroIfNullOrEmpty(illustrationResponse.getFirstYearADDRiderPremiumSummary()))
					+ Double.valueOf(zeroIfNullOrEmpty(illustrationResponse.getFirstYearTermPlusRiderPremiumSummary()))
					+ illustrationResponse.getModalPremium()
					+ Double.valueOf(zeroIfNullOrEmpty(illustrationResponse.getFirstYearAccidentCoverRiderPremiumSummary()))
					+ Double.valueOf(zeroIfNullOrEmpty(illustrationResponse.getFirstYearAcceleratedCriticalIllnessRiderPremiumSummary()))
					+ Double.valueOf(zeroIfNullOrEmpty(illustrationResponse.getCABRiderPremium()))
					+ Double.valueOf(zeroIfNullOrEmpty(illustrationResponse.getFirstYearSmartHealthPlusPremiumSummary()));
		} catch (Exception ex) {
			logger.error("Error calculating riderSumAssuredWOP because {}",ex.getMessage());
		}
		return riderSumAssuredWOP;
	}

    private String getGender(String gender) {
	String formattedGender = "Female";
	if ("F".equalsIgnoreCase(gender)) {
	    formattedGender = "Female";
	} else if ("M".equalsIgnoreCase(gender)) {
		formattedGender = "Male";
	} else if ("Others".equalsIgnoreCase(gender)) {
	    formattedGender = "Other";
	}

	return formattedGender;
    }

    /* start FUL2-19674 - transgender handling changes */
	private String getGenderTrad(ProposalDetails proposalDetails, String formattedGender) {
		if (!isNeoOrAggregator && AppConstants.TRADITIONAL.equalsIgnoreCase(proposalDetails.getProductDetails().get(0).getProductType())) {
			formattedGender = TRANSGENDER;
		}
		return formattedGender;
	}
	/* start FUL2-19674 - transgender handling changes */

    /**
	 * @param proposalDetails
	 * @param modeOfPayment
	 * @param isSIOpted
	 * @param renewalPayment
	 * @return
	 */
    //FUL2-10115_Digital_Debit_Mandate_Registration-Axis_ChannelN
    //Added else if condition to set direct debit as renewal payment
	private String setRenewalPayment(ProposalDetails proposalDetails,String modeOfPayment, String isSIOpted, String renewalPayment) {
		logger.info("Setting Renewal Payment type for transactionID : {}",proposalDetails.getTransactionId());
		if ("SINGLE".equalsIgnoreCase(modeOfPayment)) {
			renewalPayment = "Not Applicable";
		} else if(Utility.andTwoExpressions(AppConstants.CHANNEL_AXIS.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel()),
				(Objects.nonNull(proposalDetails.getBank()) && !StringUtils.isEmpty(proposalDetails.getBank().getPaymentRenewedBy()))
				&& AppConstants.DIRECTDEBIT.equalsIgnoreCase(proposalDetails.getBank().getPaymentRenewedBy()))) {
			renewalPayment = AppConstants.DIRECT_DEBIT;
		} else if (Utility.andTwoExpressions(!StringUtils.isEmpty(isSIOpted) , "TRUE".equalsIgnoreCase(isSIOpted))) {
			renewalPayment = AppConstants.CREDIT_CARD_RENEWAL;
		} else if (Objects.nonNull(proposalDetails.getBank()) && !StringUtils.isEmpty(proposalDetails.getBank().getPaymentRenewedBy())) {
			renewalPayment = proposalDetails.getBank().getPaymentRenewedBy();
		}
		if(isThanosRenewalPremium(proposalDetails)){
			renewalPayment = AppConstants.DIRECT_DEBIT;
		}

		//FUL2-18174 if AXISR and IVR Case, renewalPayment should be CHEQUE by default
		if(AppConstants.CHANNEL_AXIS.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())
				&& AppConstants.AXIS_TELESALES_REQUEST.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getRequestSource())
				&& AppConstants.DIRECTDEBIT.equalsIgnoreCase(proposalDetails.getPaymentDetails().getReceipt().get(0).getPremiumMode()))
		{
			renewalPayment = AppConstants.CHEQUE;
		}

		logger.info("Setting Renewal Payment type for transactionID : {} & renewalPayment : {}",proposalDetails.getTransactionId(),renewalPayment);
		return renewalPayment;
	}

	private boolean isThanosRenewalPremium(ProposalDetails proposalDetails){
		boolean channel = proposalDetails.getChannelDetails().getChannel().equalsIgnoreCase(AppConstants.THANOS_CHANNEL);
		boolean isSiOpted = proposalDetails.getPaymentDetails().getReceipt().get(0).getIsSIOpted().equalsIgnoreCase("TRUE");
		boolean isSiCheck = proposalDetails.getPaymentDetails().getReceipt().get(0).isSICheck();
		boolean premiumMode = proposalDetails.getPaymentDetails().getReceipt().get(0).getPremiumMode().equalsIgnoreCase(AppConstants.DIRECTDEBIT);
    return channel && isSiOpted && isSiCheck && premiumMode;
	}
	
	private boolean isSwpPosSellerNonLumSumVariant(ProposalDetails proposalDetails) {
		try {
			if(Objects.nonNull(proposalDetails.getSourcingDetails())
					&& proposalDetails.getSourcingDetails().isPosSeller()
					&& Objects.nonNull(proposalDetails.getProductDetails())
					&& AppConstants.SWP_PRODUCTCODE.equalsIgnoreCase(
							proposalDetails.getProductDetails().get(0).getProductInfo().getProductId())
					&& org.springframework.util.StringUtils
							.hasText(proposalDetails.getProductDetails().get(0).getProductInfo().getVariant())
					&& !AppConstants.LUMP_SUM.equalsIgnoreCase(
							proposalDetails.getProductDetails().get(0).getProductInfo().getVariant())) {
				return true;
			}
		} catch (Exception ex) {
			logger.error("Exception occurred at checking Pos seller swp non lumsum variant for transactionId {} is {} ",
					proposalDetails.getTransactionId(), Utility.getExceptionAsString(ex));
		}
		return false;
	}
	private Map<String, Object> setDataForTermPlan(ProductDetails productDetails,Map<String, Object> dataVariables) {
		ProductInfo productInfo = (productDetails != null) ? productDetails.getProductInfo() : null;
		String incomeCover = (productInfo != null && org.springframework.util.StringUtils.hasText(productInfo.getIncomeCover()))
				? productInfo.getIncomeCover()
				: "NA";
		String sumAssuredBooster = (productInfo != null && org.springframework.util.StringUtils.hasText(productInfo.getSumAssuredBooster()))
				? productInfo.getSumAssuredBooster()
				: "NA";
		dataVariables.put("incomeCover", incomeCover);
		dataVariables.put("sumAssuredBooster", sumAssuredBooster);
		return dataVariables;
	}
	private ProposalRiderDetails setMaternityCoverRiderDetails(ProductIllustrationResponse illustrationResponse){
		ProposalRiderDetails details = new ProposalRiderDetails();
		details.setRiderName(AXIS_MATERNITY_COVER);
		details.setCoverageTerm(
				!StringUtils.isEmpty(illustrationResponse.getMaternityRiderTerm()) ? illustrationResponse.getMaternityRiderTerm() : "0");
		details.setModalPremium(String.format("%.0f", roundOffValue(illustrationResponse.getMaternityRiderPremiumSummary())));
		details.setRiderSumAssured(String.format("%.0f", roundOffValue(illustrationResponse.getMaternityRiderSumAssured())));
		details.setRiderGST(!StringUtils.isEmpty(illustrationResponse.getMaternityRiderGST()) ? illustrationResponse.getMaternityRiderGST() : "");
		details.setPremiumPayingTerm(!StringUtils.isEmpty(illustrationResponse.getMaternityRiderPremiumPayingTerm()) ? illustrationResponse.getMaternityRiderPremiumPayingTerm() : "");
		return details;
	}
}
