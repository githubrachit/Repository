package com.mli.mpro.document.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.configuration.service.UIConfigurationService;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.document.models.Key;
import com.mli.mpro.document.models.NFOOptions;
import com.mli.mpro.document.models.ProposalRiderDetails;
import com.mli.mpro.document.utils.TraditionalFormUtil;
import com.mli.mpro.location.services.impl.DocsAppServiceImpl;
import com.mli.mpro.utils.Utility;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.*;
import com.mli.mpro.utils.UtilityService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mli.mpro.productRestriction.util.AppConstants.*;
import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * Mapper Class for Coverage details section of ULIP Proposal Form Document
 *
 * @author akshom4375
 */
@Service
public class ULIPChildAndCoverageMapper {

    private static final Logger logger = LoggerFactory.getLogger(ULIPChildAndCoverageMapper.class);
	private boolean isNeoOrAggregator = false;

	@Autowired
	private DocsAppServiceImpl docsAppServiceImpl;

    @Autowired
    private UtilityService utilityService;

	@Autowired UIConfigurationService uiConfigurationService;

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
            put(AppConstants.OTHERS, AppConstants.OTHER_INCOME);
            put("student", AppConstants.OTHER_INCOME);
        }
    };
    Map<String, String> sourceOfFundsFWP = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;

        {
            put("salaried", "Salary");
            put("agriculture", AppConstants.SOURCE_OF_FUND_FWP_OTHERS);
            put("professional", "Professional");
            put("self employed", "Self-employed");
            put("unanswered", AppConstants.SOURCE_OF_FUND_FWP_OTHERS);
            put("self employed from home", "Self Employed from Home");
            put("housewife", "Housewife");
            put("retired", "Retired");
            put(AppConstants.OTHERS, AppConstants.SOURCE_OF_FUND_FWP_OTHERS);
        }
    };

    /**
     * Setting Data for Coverage Section in ULIP Proposal Form document
     *
     * @param proposalDetails
     * @return
     */
    public Context setDataOfCoverage(ProposalDetails proposalDetails) throws UserHandledException {
        logger.info("ULIP Coverage Data processing...");
        //NEORW-173: this will check that incoming request is from NEO or Aggregator
        isNeoOrAggregator = proposalDetails.getChannelDetails().getChannel().equalsIgnoreCase(AppConstants.CHANNEL_NEO)
                || proposalDetails.getChannelDetails().getChannel().equalsIgnoreCase(AppConstants.CHANNEL_AGGREGATOR);

        Context context = new Context();
        Map<String, Object> dataVariables = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat output = new SimpleDateFormat(AppConstants.DD_MM_YYYY_HYPHEN);
        output.setTimeZone(TimeZone.getTimeZone(AppConstants.APP_TIMEZONE));


        String childName = StringUtils.EMPTY;
        String childDob = StringUtils.EMPTY;

        String planName = StringUtils.EMPTY;

        String vestingAge = StringUtils.EMPTY;
        String premiumBackOption = StringUtils.EMPTY;
        String coverageTerm = StringUtils.EMPTY;
        String coverageMultiple = StringUtils.EMPTY;
        String policyTerm = StringUtils.EMPTY;
        String premiumPaymentTerm = StringUtils.EMPTY;
        String sumAssured = StringUtils.EMPTY;
        String atp = StringUtils.EMPTY;
        String modalPremium = StringUtils.EMPTY;
        String modalPremiumWithoutGST = StringUtils.EMPTY;
        String gstCess = StringUtils.EMPTY;
        String totalPremium = StringUtils.EMPTY;
        String bonusOption = StringUtils.EMPTY;
        String planGst = StringUtils.EMPTY;
        String effectiveDateOfCoverage = StringUtils.EMPTY;
        String variant = NA;

        try {
            ProductDetails productDetail = proposalDetails.getProductDetails().get(0);
            try {
                logger.info("effective date of coverage:: {}", productDetail.getProductInfo().getEffectiveDateOfCoverage());
                effectiveDateOfCoverage = !StringUtils.isEmpty(productDetail.getProductInfo().getEffectiveDateOfCoverage())
                        ? output.format(sdf.parse(productDetail.getProductInfo().getEffectiveDateOfCoverage()))
                        : StringUtils.EMPTY;

            } catch (Exception ex) {
			logger.error("Error converting Effective Date of coverage for stp:",ex);
            }

            List<PartyDetails> nomineePartyDetailsList = null;
            if (isNeoOrAggregator) {
                nomineePartyDetailsList = Optional.ofNullable(proposalDetails.getNomineeDetails())
                    .map(NomineeDetails::getPartyDetails).orElse(null);
            } else {
                nomineePartyDetailsList = proposalDetails.getNomineeDetails().getPartyDetails();
            }

            List<ProductDetails> productDetailsList = proposalDetails.getProductDetails();
            //Child Details
            if (!CollectionUtils.isEmpty(nomineePartyDetailsList)) {
                childName = nomineePartyDetailsList.get(0).getNomineeChildName();
                if (null != nomineePartyDetailsList.get(0).getNomineeChildDob()) {
                    childDob = Utility.dateFormatter(nomineePartyDetailsList.get(0).getNomineeChildDob());
                }
            }

            if (!CollectionUtils.isEmpty(productDetailsList)) {
                ProductDetails productDetails = productDetailsList.get(0);

                if (AppConstants.FPS_PRODUCT_ID.equalsIgnoreCase(productDetails.getProductInfo().getProductId())) {
                    planName = AppConstants.FWP_PRODUCTNAME;
                } else {
                    planName = isNeoOrAggregator ?
                        getPlanName(productDetails.getProductInfo(), Utility.isChannelAggregator(proposalDetails)) : productDetails.getProductInfo().getProductName();
                }
                vestingAge = productDetails.getProductInfo().getVestingAge();
                if(STAR_PRODUCT_ID.equals(productDetails.getProductInfo().getProductId())) {
                    variant = Utility.ifEmptyThenNA(productDetails.getProductInfo().getVariant());
                    dataVariables.put("variant", variant.contains("3D") ? "3D Life Secure (3X FOP)" : "Life Secure");
                }
                premiumBackOption = productDetails.getProductInfo().getPremiumBackOption();
                coverageTerm = productDetails.getProductInfo().getProductIllustrationResponse().getCoverageTerm();
                coverageMultiple = productDetails.getProductInfo().getProductIllustrationResponse().getCoverMultiple();
                policyTerm = productDetails.getProductInfo().getPolicyTerm();
                premiumPaymentTerm = productDetails.getProductInfo().getProductIllustrationResponse().getPremiumPaymentTerm();
                sumAssured = isNeoOrAggregator ? new BigDecimal(productDetails.getProductInfo().getProductIllustrationResponse().getAnnualPremium(), MathContext.DECIMAL64).toString()
                        : String.format("%.0f", roundOffValue(productDetails.getProductInfo().getProductIllustrationResponse().getSumAssured()));
                modalPremium = isNeoOrAggregator ? String.valueOf(productDetails.getProductInfo().getProductIllustrationResponse().getBaseModalPremium())
                        : String.format("%.0f", roundOffValue(productDetails.getProductInfo().getProductIllustrationResponse().getModalPremium()));
                sumAssured = isNeoOrAggregator ? String.valueOf(productDetails.getProductInfo().getProductIllustrationResponse().getAnnualPremium())
                        : String.format("%.0f", roundOffValue(productDetails.getProductInfo().getProductIllustrationResponse().getSumAssured()));
                atp = isNeoOrAggregator ? String.valueOf(productDetails.getProductInfo().getProductIllustrationResponse().getAtp()) : String.format("%.0f", roundOffValue(productDetails.getProductInfo().getProductIllustrationResponse().getAtp()));
                modalPremium = isNeoOrAggregator ? String.valueOf(productDetails.getProductInfo().getProductIllustrationResponse().getBaseModalPremium())
                        : String.format("%.0f", roundOffValue(productDetails.getProductInfo().getProductIllustrationResponse().getModalPremium()));
                modalPremiumWithoutGST = isNeoOrAggregator ? String.valueOf(productDetails.getProductInfo().getProductIllustrationResponse().getRequiredModalPremium())
                        : String.format("%.0f", roundOffValue(productDetails.getProductInfo().getProductIllustrationResponse().getRequiredModalPremium()));
                planGst = String.valueOf(roundOffValue(productDetails.getProductInfo().getProductIllustrationResponse().getServiceTax()));
                // In case of NEO gstCess is mapped in serviceTaxInclCessBaseModalPrem field
                gstCess = isNeoOrAggregator ? productDetails.getProductInfo().getProductIllustrationResponse().getServiceTaxInclCessBaseModalPrem()
                        : String.format("%.0f", roundOffValue(productDetails.getProductInfo().getProductIllustrationResponse().getServiceTax()));
                    dataVariables.put("basePlanGST", productDetails.getProductInfo().getProductIllustrationResponse().getBasePlanGST());
                    dataVariables.put("totalPremiumWithGST", String.format("%.0f", roundOffValue(productDetails.getProductInfo()
                            .getProductIllustrationResponse().getTotalRequiredModalPremium())));

                /*FUL2-17826 removed the temporary gst Waiver changes*/
                totalPremium = isNeoOrAggregator ? productDetails.getProductInfo().getProductIllustrationResponse().getInitialPremiumPaid()
                        : String.format("%.0f", roundOffValue(productDetails.getProductInfo().getProductIllustrationResponse().getInitialPremiumPaid()));

                bonusOption =Utility.evaluateConditionalOperation(!StringUtils.isEmpty(productDetails.getProductInfo().getDividendOption()), productDetails.getProductInfo().getDividendOption(), AppConstants.NA);

                dataVariables.put(AppConstants.MODE_OF_PAYMENT, productDetails.getProductInfo().getModeOfPayment());
                dataVariables.put(AppConstants.PRODUCT_ID, productDetails.getProductInfo().getProductId());

                //Setting Riders data
                dataVariables = setDataForRiders(productDetails, dataVariables);
            }
            //Setting Fund details
            dataVariables = setDataOfCoverageForFundDetails(productDetailsList, dataVariables);
            //Setting Bank details
            dataVariables = setDataOfCoverageForBankDetails(proposalDetails, dataVariables);
            dataVariables=setDataOfCoverageForBankDetailsForSpecificNeo(proposalDetails, dataVariables);
            // Setting Payors Details
            dataVariables = setDataOfCoverageForPayorsDetails(proposalDetails, dataVariables);
            //setting data for smart withdrawal plan
            dataVariables=setDataOfCoverageForSmartWithdrawalPlan(productDetail,dataVariables);

            // Setting data in dataMap
            dataVariables.put("basePlan", StringUtils.upperCase(planName));
            dataVariables.put("childName", Utility.evaluateConditionalOperation(StringUtils.isNotBlank(childName), childName, AppConstants.NA));
            dataVariables.put("childDob", Utility.evaluateConditionalOperation(StringUtils.isNotBlank(childDob), childDob, AppConstants.NA));
            dataVariables.put("vestingAge", vestingAge);
            dataVariables.put("premiumBackOption", premiumBackOption);
            dataVariables.put("coverageTerm", coverageTerm);
            dataVariables.put("coverageMultiple", coverageMultiple);
            dataVariables.put("premimumPayingTerm", premiumPaymentTerm);
            dataVariables.put("policyTerm", policyTerm);
            dataVariables.put("nfoOption", getNfoOption(planName));
            dataVariables.put("sumAssured", sumAssured);
            dataVariables.put("atp", atp);
            dataVariables.put("modalPremium", modalPremium);
            dataVariables.put("planGST", planGst);
            dataVariables.put("modalPremiumGST", modalPremiumWithoutGST);
            dataVariables.put("GSTCess", gstCess);
            dataVariables.put("totalPremium", totalPremium);
            dataVariables.put("bonusOption", bonusOption);
            dataVariables.put("effectivePolicy", effectiveDateOfCoverage);

            // Adding feature flag for Midcap Momentum Fund
            if(AppConstants.STAR_PRODUCT_ID.equalsIgnoreCase(productDetail.getProductInfo().getProductId()) && !isNeoOrAggregator){
                utilityService.setFeatureFlagNIFTYFundForSTART(dataVariables,String.valueOf(proposalDetails.getTransactionId()));
            } else if (Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.NIFTYFUND_FEATURE_KEY))) {
				Utility.niftyFundEnableOrNot(uiConfigurationService, null, null, dataVariables, "");
			} else if (Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.NIFTYFUND_FEATURE_KEY))) {
				dataVariables.put("isFeatureFlagNIFTYFund", false);
			}
            // Channel specific check for Nifty Fund
            if(OSP.equals(productDetail.getProductInfo().getProductId())
                    && !(proposalDetails.getChannelDetails() != null && CHANNEL_AXIS.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())
                        && proposalDetails.getAdditionalFlags() != null && AppConstants.J3.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getJourneyType()))) {
                dataVariables.put("isFeatureFlagNIFTYFund", false);
            }
            dataVariables = setPaymentDetails(proposalDetails, dataVariables);
          //FUL2-46229
            if(Utility.isCapitalGuaranteeSolutionProduct(proposalDetails)) {
            	setSCGSPaymentData(proposalDetails, dataVariables);
            }
            if (isNeoOrAggregator) {
                dataVariables.put("declarationVersionDate", Utility.getUlipDeclarationVersionDate());
            }
        } catch (Exception ex) {
            logger.error("Coverage Data addition failed for ULIP Proposal Form Document: ", ex);
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Data addition failed");
            throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        context.setVariables(dataVariables);
        logger.info(END, "%m");
        return context;
    }

    private Map<String, Object> setDataOfCoverageForBankDetailsForSpecificNeo(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {
        if ((isNeoOrAggregator) && checkingBankDetailsNullValue(proposalDetails)) {
            String bankBranch = "";
            BankDetails bankdetails = proposalDetails.getPartyInformation().get(0).getBankDetails();
            String  bankingSince = "NA";
            if (Utility.orTwoExpressions(StringUtils.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel(), "X"),
                    StringUtils.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel(), "BY"))) {
                bankingSince = Utility.dateFormatter(bankdetails.getBankAccOpeningDate());
            }
            bankBranch = Objects.nonNull(bankdetails.getBankBranch()) ? (bankdetails.getBankBranch()) : "";
            dataVariables.put("micrCode", bankdetails.getMicr());
            dataVariables.put("IFSCCode", bankdetails.getIfsc());
            dataVariables.put("accountNumber", bankdetails.getBankAccountNumber());
            dataVariables.put("accountHolderName", bankdetails.getAccountHolderName());
            dataVariables.put("typeOfAcc", bankdetails.getTypeOfAccount());
            dataVariables.put("bankNameBranch", bankBranch);
            dataVariables.put("bankingSince", bankingSince);
        }
        return dataVariables;
    }

    private boolean checkingBankDetailsNullValue(ProposalDetails proposalDetails) {
        return Objects.nonNull(proposalDetails.getPartyInformation()) && !proposalDetails.getPartyInformation().isEmpty()
                && Objects.nonNull(proposalDetails.getPartyInformation().get(0))
                && Objects.nonNull(proposalDetails.getPartyInformation().get(0).getBankDetails())
                && Objects.nonNull(proposalDetails.getPartyInformation().get(0).getBankDetails().getBankAccountNumber());
    }


    private void setSCGSPaymentData(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {
    	int amount = 0;
    	try {
    	amount = (int) roundOffValue(proposalDetails.getProductDetails().get(0).getProductInfo()
                .getProductIllustrationResponse().getTotalRequiredModalPremium());
    	}catch(Exception ex) {
    		logger.error("exception occured while getting amount for transactionId {} ", proposalDetails.getTransactionId());
    	}
    	 String amountInWords = Utility.convertNumberToWords(amount);
         dataVariables.put("amountInWords", amountInWords);
         dataVariables.put("amount", amount);
	}
    
    private Map<String, Object> setDataOfCoverageForSmartWithdrawalPlan(ProductDetails productDetail, Map<String, Object> dataVariables) {

        ProductInfo productInfo = productDetail.getProductInfo();
        String smartWithdrawalPayoutMode = "";
        String smartWithdrawalPlan = "";
        if (isNeoOrAggregator) {
            smartWithdrawalPayoutMode = settingValueOfSmartWithdrawlPayoutMode(productInfo.getSmartWithdrawalPayoutMode());
            smartWithdrawalPlan = Utility.convertToYesOrNo(productInfo.getSmartWithdrawalPlan());
        } else {
            smartWithdrawalPayoutMode = productInfo.getSmartWithdrawalPayoutMode();
            smartWithdrawalPlan = productInfo.getSmartWithdrawalPlan();
        }

        dataVariables.put("smartWithdrawalPlan",smartWithdrawalPlan);
        dataVariables.put("smartWithdrawalPayoutPercentage",productInfo.getSmartWithdrawalPayoutPercentage());
        dataVariables.put("smartWithdrawalPayoutMode", smartWithdrawalPayoutMode);
        dataVariables.put("smartWithdrawalPayoutStartYear",productInfo.getSmartWithdrawalPayoutStartYear());
        return dataVariables;
    }

    private String settingValueOfSmartWithdrawlPayoutMode(String smartWithdrawalPayoutMode) {
        if ("01".equalsIgnoreCase(smartWithdrawalPayoutMode)) {
            smartWithdrawalPayoutMode = ANNUAL;
        } else if ("02".equalsIgnoreCase(smartWithdrawalPayoutMode)) {
            smartWithdrawalPayoutMode = SEMI_ANNUAL;
        } else if ("03".equalsIgnoreCase(smartWithdrawalPayoutMode)) {
            smartWithdrawalPayoutMode = MONTHLY;
        } else if ("04".equalsIgnoreCase(smartWithdrawalPayoutMode)) {
            smartWithdrawalPayoutMode = QUARTERLY;
        } else if ("05".equalsIgnoreCase(smartWithdrawalPayoutMode)) {
            smartWithdrawalPayoutMode = SINGLE;
        }
        return smartWithdrawalPayoutMode;
    }


    private Map<String, Object> setDataOfCoverageForBankDetails(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {

        String productId = StringUtils.isEmpty((String) dataVariables.get(AppConstants.PRODUCT_ID)) ? StringUtils.EMPTY : (String) dataVariables.get(AppConstants.PRODUCT_ID);
        String modeOfPayment = StringUtils.isEmpty((String) dataVariables.get(AppConstants.MODE_OF_PAYMENT)) ? StringUtils.EMPTY : (String) dataVariables.get(AppConstants.MODE_OF_PAYMENT);
        String bankingSince = "NA";
        String micr = StringUtils.EMPTY;
        String ifsc = StringUtils.EMPTY;
        String accountNumber = StringUtils.EMPTY;
        String accHolder = StringUtils.EMPTY;
        String typeOfBank = StringUtils.EMPTY;
        String bankBranch = StringUtils.EMPTY;
        String occupation = StringUtils.EMPTY;

        String formType = proposalDetails.getApplicationDetails().getFormType();
        String schemeType = proposalDetails.getApplicationDetails().getSchemeType();

        List<BankDetails> bankDetailsList = Optional.ofNullable(proposalDetails).map(ProposalDetails::getBank).map(Bank::getBankDetails).orElse(Collections.EMPTY_LIST);
        if (!CollectionUtils.isEmpty(bankDetailsList)) {
            BankDetails bankdetails = bankDetailsList.get(0);
            micr = bankdetails.getMicr();
            ifsc = bankdetails.getIfsc();
            accountNumber = bankdetails.getBankAccountNumber();
            accHolder = bankdetails.getAccountHolderName();
            typeOfBank = bankdetails.getTypeOfAccount();
            bankBranch = bankdetails.getBankName().concat(" ").concat(bankdetails.getBankBranch());

            if (Utility.orTwoExpressions(StringUtils.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel(), "X"),
                    StringUtils.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel(), "BY"))) {
                bankingSince = Utility.dateFormatter(bankdetails.getBankAccOpeningDate());
            }
        }

        if (null != proposalDetails.getPartyInformation()) {
            BasicDetails proposerDetails = proposalDetails.getPartyInformation().get(0).getBasicDetails();

            if (isNeoOrAggregator) {
                occupation = proposalDetails.getEmploymentDetails().getPartiesInformation().get(0).getBasicDetails().getOccupation();
            } else {
                occupation = Utility.evaluateConditionalOperation(StringUtils.isEmpty(proposerDetails.getOccupation()), AppConstants.OTHERS, proposerDetails.getOccupation());
            }
        }
        // Setting specific funds & modeOfPayment values for FWP PF
        String funds;
        if (productId.equalsIgnoreCase(AppConstants.FWP_PRODUCTCODE)) {
            funds = sourceOfFundsFWP.get(occupation.toLowerCase());
            if (modeOfPayment.equalsIgnoreCase("SINGLE")) {
                modeOfPayment = "Single Pay";
            } else if (modeOfPayment.equalsIgnoreCase("Semi-Annual")) {
                modeOfPayment = "Half Yearly";
            }
        } else{
            funds = settingFundsForForm3Cases(proposalDetails, formType, schemeType, occupation);
        }

        String renewalPayment = getRenewalPayment(proposalDetails, modeOfPayment);
        if (productId.equalsIgnoreCase(AppConstants.SMTP_PRODUCT_ID)) {
            dataVariables.put("GST", StringUtils.EMPTY);
            dataVariables.put("smartTerm", true);
            dataVariables.put("deathBenefit", AppConstants.BLANK);
            dataVariables.put("lifeStageEvent", AppConstants.BLANK);
        }

        dataVariables.put("micrCode", micr);
        dataVariables.put("IFSCCode", ifsc);
        dataVariables.put("accountNumber", accountNumber);
        dataVariables.put("accountHolderName", accHolder);
        dataVariables.put("typeOfAcc", typeOfBank);
        dataVariables.put("bankNameBranch", bankBranch);
        dataVariables.put("bankingSince", bankingSince);
        dataVariables.put(AppConstants.MODE_OF_PAYMENT, modeOfPayment);
        dataVariables.put("renewalPremium", renewalPayment);
        dataVariables.put("sourceOfFunds", Utility.evaluateConditionalOperation(StringUtils.isNotBlank(funds), funds, AppConstants.NA));
        dataVariables.put("occupation", occupation);

        return dataVariables;
    }

    private String settingFundsForForm3Cases(ProposalDetails proposalDetails, String formType, String schemeType, String occupation){

        if (AppConstants.FORM3.equalsIgnoreCase(formType) && !Utility.schemeBCase(schemeType)) {
            return proposalDetails.getPartyInformation().stream().filter(Objects::nonNull)
                    .filter(partyInfo -> AppConstants.COMPANY.equalsIgnoreCase(partyInfo.getPartyType())).findFirst()
                    .map(PartyInformation::getBasicDetails).map(BasicDetails::getSourceOfFunds).orElse("");
        }else {
            return sourceOfFunds.get(occupation.toLowerCase());
        }

    }

    //FUL2-10115_Digital_Debit_Mandate_Registration-Axis_Channel
    //Added else if condition to set direct debit as renewal payment
    private String getRenewalPayment(ProposalDetails proposalDetails, String modeOfPayment) {
        /*
         * F21-265 Standing Instruction on Credit Card- If user has opted
         * the SI then credit card should be displayed in renewal Option
         */
        String renewalPayment = StringUtils.EMPTY;
        Receipt receiptDetails = proposalDetails.getPaymentDetails().getReceipt().get(0);
        String isSIOpted = receiptDetails.getIsSIOpted();
        String itemCode = receiptDetails.getItemCode();
        logger.info("Setting Renewal Payment type for transactionID : {}",proposalDetails.getTransactionId());

        if ("SINGLE".equalsIgnoreCase(modeOfPayment) || "Single Pay".equalsIgnoreCase(modeOfPayment)) {
            renewalPayment = "Not Applicable";
        } else if(Utility.andTwoExpressions(AppConstants.CHANNEL_AXIS.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel()),
				(Objects.nonNull(proposalDetails.getBank()) && !StringUtils.isEmpty(proposalDetails.getBank().getPaymentRenewedBy()))
				&& AppConstants.DIRECTDEBIT.equalsIgnoreCase(proposalDetails.getBank().getPaymentRenewedBy()))) {
			renewalPayment = AppConstants.DIRECT_DEBIT;
		} else if (!StringUtils.isEmpty(isSIOpted) && "TRUE".equalsIgnoreCase(isSIOpted) && !StringUtils.isEmpty(itemCode)) {
            renewalPayment = AppConstants.CREDIT_CARD_RENEWAL;
        } else if (Objects.nonNull(proposalDetails.getBank()) && !StringUtils.isEmpty(proposalDetails.getBank().getPaymentRenewedBy())) {
            renewalPayment = proposalDetails.getBank().getPaymentRenewedBy();
        }

        //FUL2-18174 if AXISR and IVR Case, renewalPayment should be CHEQUE by default
        if(AppConstants.CHANNEL_AXIS.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())
                && AppConstants.AXIS_TELESALES_REQUEST.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getRequestSource())
                && AppConstants.DIRECTDEBIT.equalsIgnoreCase(proposalDetails.getPaymentDetails().getReceipt().get(0).getPremiumMode()))
        {
            renewalPayment = AppConstants.CHEQUE;
        }
        if (isNeoOrAggregator && renewalPayment.equalsIgnoreCase(StringUtils.EMPTY)) {
            renewalPayment = Utility.setRenewalPaymentDataForNeo(proposalDetails, receiptDetails);
        }
        logger.info("Setting Renewal Payment type for transactionID : {} & renewalPayment : {}",proposalDetails.getTransactionId(),renewalPayment);
        return renewalPayment;
    }

    private Map<String, Object> setDataOfCoverageForPayorsDetails(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {

        String payorName = StringUtils.EMPTY;
        String payorGender = StringUtils.EMPTY;
        String payorDOB = StringUtils.EMPTY;
        String relationShipWithProposer = StringUtils.EMPTY;
        String payorAddress = StringUtils.EMPTY;
        String payorAnnualIncome = StringUtils.EMPTY;
        String payorDiffFormProposer = "";
        String payorPan = StringUtils.EMPTY;
        BasicDetails payorDetails = null;
        String payorBank = StringUtils.EMPTY;
        String payorBankBranch = StringUtils.EMPTY;
        String payorAccountNumber = StringUtils.EMPTY;

        String payorBankingSince = StringUtils.EMPTY;;
        List<PartyInformation> partyInfoList = proposalDetails.getPartyInformation();
        if (isNeoOrAggregator && !proposalDetails.getPartyInformation().isEmpty()) {
            PartyInformation partyInformation = proposalDetails.getPartyInformation()
                    .stream()
                    .filter(pd -> pd.getPartyType().equals("Payor"))
                    .findFirst().orElse(null);
            //NEORW: conditions to check payor details exists or not
            if (Objects.nonNull(partyInformation)
                    && Objects.nonNull(partyInformation.getBasicDetails())
                    && Objects.nonNull(partyInformation.getPartyDetails())
                    && !org.springframework.util.StringUtils.isEmpty(partyInformation.getPartyDetails().getRelationshipWithProposer())) {
                payorDetails = partyInformation.getBasicDetails();
            }
            payorDiffFormProposer = Objects.nonNull(payorDetails) ? AppConstants.YES : AppConstants.NO;

            if (AppConstants.YES.equalsIgnoreCase(payorDiffFormProposer) && Objects.nonNull(payorDetails)) {
                payorName = Stream.of(payorDetails.getFirstName(), payorDetails.getMiddleName(), payorDetails.getLastName())
                        .filter(s -> !org.springframework.util.StringUtils.isEmpty(s)).collect(Collectors.joining(" "));
                payorGender = Utility.getGender(payorDetails.getGender());
                payorDOB = Utility.dateFormatter(Utility.dateFormatter(payorDetails.getDob()), "dd-MM-yyyy", "dd/MM/yyyy");
                relationShipWithProposer = payorDetails.getRelationshipWithProposer();
                payorAnnualIncome = payorDetails.getAnnualIncome();
                payorAddress = payorDetails.getAddress().get(0).getAddressDetails().getHouseNo();

                if (Objects.nonNull(partyInformation) && Objects.nonNull(partyInformation.getPersonalIdentification())
                        && Objects.nonNull(partyInformation.getPersonalIdentification().getPanDetails()) && Objects.nonNull(payorDetails)) {
                    payorPan = partyInformation.getPersonalIdentification().getPanDetails().getPanNumber();
                }

                if (Objects.nonNull(partyInformation) && Objects.nonNull(partyInformation.getBankDetails())) {
                    payorBank = org.springframework.util.StringUtils.isEmpty(partyInformation.getBankDetails().getBankName()) ? ""
                            : partyInformation.getBankDetails().getBankName();
                    payorBankBranch = org.springframework.util.StringUtils.isEmpty(partyInformation.getBankDetails().getBankBranch()) ? ""
                            : partyInformation.getBankDetails().getBankBranch();
                    payorAccountNumber = partyInformation.getBankDetails().getBankAccountNumber();
                    payorBankingSince = Utility.dateFormatter(partyInformation.getBankDetails().getBankAccOpeningDate());
                }
            }
        } else {
            //Setting Payment Details
            payorDiffFormProposer = Utility.evaluateConditionalOperation(proposalDetails.getAdditionalFlags().isPayorDiffFromPropser(), AppConstants.YES, AppConstants.NO);

            if (StringUtils.equalsIgnoreCase(payorDiffFormProposer, AppConstants.YES) && !CollectionUtils.isEmpty(partyInfoList)
                    && partyInfoList.size() >= 3) {
                payorDetails = partyInfoList.get(2).getBasicDetails();

                payorName = Stream.of(payorDetails.getFirstName(), payorDetails.getMiddleName(), payorDetails.getLastName())
                        .filter(s -> !StringUtils.isEmpty(s)).collect(Collectors.joining(AppConstants.WHITE_SPACE));
                payorGender = Utility.getGender(payorDetails.getGender());
                payorDOB = Utility.dateFormatter(payorDetails.getDob());
                payorAnnualIncome = payorDetails.getAnnualIncome();
                payorAddress = String.join(" ", payorDetails.getAddress().get(0).getAddressDetails().getArea(),
                        payorDetails.getAddress().get(0).getAddressDetails().getState());
                payorPan = StringUtils.isEmpty(partyInfoList.get(2).getPersonalIdentification().getPanDetails().getPanNumber()) ? AppConstants.NO
                        : partyInfoList.get(2).getPersonalIdentification().getPanDetails().getPanNumber();
                relationShipWithProposer = payorDetails.getRelationshipWithProposer();
                payorBank = StringUtils.isEmpty(partyInfoList.get(2).getBankDetails().getBankName()) ? AppConstants.BLANK
                        : partyInfoList.get(2).getBankDetails().getBankName();
                payorBankBranch = StringUtils.isEmpty(partyInfoList.get(2).getBankDetails().getBankBranch()) ? AppConstants.BLANK
                        : partyInfoList.get(2).getBankDetails().getBankBranch();
                payorAccountNumber = partyInfoList.get(2).getBankDetails().getBankAccountNumber();
            }
        }
        //Setting PAN details
        dataVariables = setDataOfCoverageForPANDetails(proposalDetails, dataVariables);

        String aadhar = !StringUtils
                .isEmpty(proposalDetails.getPartyInformation().get(0).getPersonalIdentification().getAadhaarDetails().getAadhaarNumber())
                ? proposalDetails.getPartyInformation().get(0).getPersonalIdentification().getAadhaarDetails().getAadhaarNumber()
                : (Objects.nonNull(proposalDetails.getForm60Details())
                ? (proposalDetails.getForm60Details().getIdentityProofName().equalsIgnoreCase(AppConstants.AADHAAR)
                ? proposalDetails.getForm60Details().getIdentityProofNumber() : AppConstants.NO)
                : AppConstants.NO);
        //FUL2-5925 Aadhaar Masking in mPRO
        aadhar = Utility.maskAadhaarNumber(aadhar);

        // Setting data in dataMap
        dataVariables.put("payorName", payorName);
        dataVariables.put("relationShipWithProposer", relationShipWithProposer);
        dataVariables.put("payoraddress", payorAddress);
        dataVariables.put("payorGender", payorGender);
        dataVariables.put("payorBankingSince", payorBankingSince);
        dataVariables.put("payorAnnualIncome", payorAnnualIncome);
        dataVariables.put("payorPan", payorPan);
        dataVariables.put("payorDOB", payorDOB);
        dataVariables.put("payorAccountNumber", payorAccountNumber);
        dataVariables.put("payorBankBranch", payorBank.concat(" ").concat(payorBankBranch));
        dataVariables.put("isaadhar", aadhar);
        dataVariables.put("isPayorDiffFromProposer", proposalDetails.getAdditionalFlags().isPayorDiffFromPropser());
        dataVariables.put("payorDiffFromProposer", payorDiffFormProposer);
        return dataVariables;
    }


    private Map<String, Object> setDataOfCoverageForPANDetails(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {
        String panApplied = StringUtils.EMPTY;
        String panAcknowledgementNo = StringUtils.EMPTY;
        String panApplicationDate = StringUtils.EMPTY;
        String isChargeableIncome = StringUtils.EMPTY;
        String isIncomeExceedLimit = StringUtils.EMPTY;
        String isTaxableIncome = StringUtils.EMPTY;
        String isItOtherIncome = StringUtils.EMPTY;
        String isNRI = StringUtils.EMPTY;
        String isApplicableIncome = StringUtils.EMPTY;
        boolean isPanNotRequired = false;
        String panNotRequired = AppConstants.NA;
        String pan = StringUtils.EMPTY;
        String form60 = "NO";
        // PAN No
        try {

            String formType = proposalDetails.getApplicationDetails().getFormType();
            String schemeType = proposalDetails.getApplicationDetails().getSchemeType();
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
        } catch (NullPointerException npe) {
            logger.error("PAN Details not found : ", npe);
        }
        if (StringUtils.isEmpty(pan)) {
            pan = AppConstants.NO;
            isPanNotRequired = true;
            form60 = AppConstants.YES;
            Form60Details form60Details = proposalDetails.getForm60Details();
            if (Objects.nonNull(form60Details)) {
                panApplied = Utility.evaluateConditionalOperation(form60Details.getDetailsOfDontHavePan().equalsIgnoreCase(AppConstants.YES), AppConstants.YES, AppConstants.NO);
                panAcknowledgementNo = form60Details.getPanAcknowledgementNo();
                panApplicationDate = Utility.dateFormatter(form60Details.getPanApplicationDate());
                isChargeableIncome = Utility.evaluateConditionalOperation(form60Details.isChargeableIncome(), AppConstants.YES, AppConstants.NO);
                isIncomeExceedLimit = Utility.evaluateConditionalOperation(form60Details.isIncomeExceedLimit(), AppConstants.YES, AppConstants.NO);
                isTaxableIncome = Utility.evaluateConditionalOperation(form60Details.isTaxableIncome(), AppConstants.YES, AppConstants.NO);
                isItOtherIncome = Utility.evaluateConditionalOperation(form60Details.isItOtherIncome(), AppConstants.YES, AppConstants.NO);
                isNRI = Utility.evaluateConditionalOperation(form60Details.isNRI(), AppConstants.YES, AppConstants.NO);
                isApplicableIncome = Utility.evaluateConditionalOperation(form60Details.isApplicableIncome(), AppConstants.YES, AppConstants.NO);
            }
            panNotRequired = Utility.evaluateConditionalOperation(isPanNotRequired, AppConstants.YES, AppConstants.NO);
        }
        String isagentSelf = proposalDetails.getAdditionalFlags().isAgentSelf();
        //FUL2-103459 Additional Flag isMaxEmp to check if user is/was MLI employee to get a question response
        String maxEmp = proposalDetails.getAdditionalFlags().getIsMaxEmp();
        String agentSelf = AppConstants.NO;
        if (Utility.andTwoExpressions(!StringUtils.isEmpty(isagentSelf),"TRUE".equalsIgnoreCase( isagentSelf)) ||
                Utility.andTwoExpressions(!StringUtils.isEmpty(maxEmp),"YES".equalsIgnoreCase( maxEmp)) ) {
            agentSelf = AppConstants.YES;
        }
        // Setting data in dataMap
        dataVariables.put("pan", pan);
        dataVariables.put("ispanNotRequired", isPanNotRequired);
        dataVariables.put("isForm60", form60);
        dataVariables.put("appliedForPan", panApplied);
        dataVariables.put("panAcknowledgementNo", panAcknowledgementNo);
        dataVariables.put("panApplicationDate", panApplicationDate);
        dataVariables.put("isChargeableIncome", isChargeableIncome);
        dataVariables.put("isIncomeExceedLimit", isIncomeExceedLimit);
        dataVariables.put("isTaxableIncome", isTaxableIncome);
        dataVariables.put("isItOtherIncome", isItOtherIncome);
        dataVariables.put("isNRI", isNRI);
        dataVariables.put("isApplicableIncome", isApplicableIncome);
        dataVariables.put("panNotRequired", panNotRequired);
        dataVariables.put("isAgentSelf", agentSelf);
        //FUL2-103459 Additional Flag isMaxEmp to check if user is/was MLI employee to get a question response
        dataVariables.put("isMaxEmp", maxEmp);
        return dataVariables;
    }


    private Map<String, Object> setDataOfCoverageForFundDetails(List<ProductDetails> productDetailsList, Map<String, Object> dataVariables) {
        String stp = StringUtils.EMPTY;
        String dfa = StringUtils.EMPTY;
        String lbps = StringUtils.EMPTY;
        String tbps = StringUtils.EMPTY;
        String cuf = StringUtils.EMPTY;

        int totalFund;
        FundSelection fundSelection = new FundSelection();
        if (!CollectionUtils.isEmpty(productDetailsList) && null != productDetailsList.get(0).getProductInfo().getFundSelection()) {
            // Fund details
            fundSelection = productDetailsList.get(0).getProductInfo().getFundSelection();

            stp = Utility.evaluateConditionalOperation(fundSelection.isSTPAllocated(), AppConstants.YES, AppConstants.NO);
            dfa = Utility.evaluateConditionalOperation(fundSelection.isDFAAllocated(), AppConstants.YES, AppConstants.NO);
            lbps = Utility.evaluateConditionalOperation(fundSelection.isLifecyclePortfolioStrategy(), AppConstants.YES, AppConstants.NO);
            tbps = Utility.evaluateConditionalOperation(fundSelection.isTriggerBasedPortfolioStrategy(), AppConstants.YES, AppConstants.NO);
            cuf = Utility.evaluateConditionalOperation(fundSelection.isFundAllocated(), AppConstants.YES, AppConstants.NO);

        }
        //setting fund value by fund name
        dataVariables = getFundValueByName(productDetailsList, dataVariables);

        //setting fund value by index
        dataVariables = getFundValueByIndex(productDetailsList, dataVariables);

        if(Objects.nonNull(fundSelection) && Objects.nonNull(fundSelection.getFundInfo())){
            totalFund = fundSelection.getFundInfo().stream().reduce(0, (fund1, fund2) -> fund1 + fund2.getFundValue(), Integer::sum);
        }
        else{
            totalFund=0;
        }
        dataVariables.put("totalFund", totalFund);

        // Setting data in dataMap
        dataVariables.put("dfa", dfa);
        dataVariables.put("stp", stp);
        dataVariables.put("lbps", lbps);
        dataVariables.put("tbps", tbps);
        dataVariables.put("cuf", cuf);

        return dataVariables;
    }


    private Map<String, Object> getFundValueByName(List<ProductDetails> productDetailsList, Map<String, Object> dataVariables) {
        Map<String, Integer> fundValueMap = new HashMap<>();
        List<FundInfo> fundInfoList = productDetailsList.get(0).getProductInfo().getFundSelection().getFundInfo();

        fundInfoList.stream().forEach(fundInfo -> fundValueMap.put(fundInfo.getFundName(), fundInfo.getFundValue()));

        dataVariables.put(AppConstants.HIGH_GROWTH_FUND, fundValueMap.getOrDefault(AppConstants.HIGH_GROWTH_FUND, 0));
        dataVariables.put(AppConstants.GROWTH_SUPER_FUND, fundValueMap.getOrDefault(AppConstants.GROWTH_SUPER_FUND, 0));
        dataVariables.put(AppConstants.GROWTH_FUND, fundValueMap.getOrDefault(AppConstants.GROWTH_FUND, 0));
        dataVariables.put(AppConstants.BALANCED_FUND, fundValueMap.getOrDefault(AppConstants.BALANCED_FUND, 0));
        dataVariables.put(AppConstants.CONSERVATIVE_FUND, fundValueMap.getOrDefault(AppConstants.CONSERVATIVE_FUND, 0));
        dataVariables.put(AppConstants.SECURE_FUND, fundValueMap.getOrDefault(AppConstants.SECURE_FUND, 0));
        dataVariables.put(AppConstants.DIVERSIFIED_EQUITY_FUND, fundValueMap.getOrDefault(AppConstants.DIVERSIFIED_EQUITY_FUND, 0));
        dataVariables.put(AppConstants.DYNAMIC_BOND_FUND, fundValueMap.getOrDefault(AppConstants.DYNAMIC_BOND_FUND, 0));
        dataVariables.put(AppConstants.MONEY_MARKET_FUND_II, fundValueMap.getOrDefault(AppConstants.MONEY_MARKET_FUND_II, 0));
        dataVariables.put(AppConstants.SUSTAINABLE_EQUITY_FUND, fundValueMap.getOrDefault(AppConstants.SUSTAINABLE_EQUITY_FUND, 0));
        dataVariables.put(AppConstants.PURE_GROWTH_FUND,fundValueMap.getOrDefault(AppConstants.PURE_GROWTH_FUND,0));
        dataVariables.put(AppConstants.SECURE_PLUS_FUND,fundValueMap.getOrDefault(AppConstants.SECURE_PLUS_FUND,0));
        dataVariables.put(AppConstants.NIFTY_SMALLCAP_QUALITY_INDEX_FUND,fundValueMap.getOrDefault(AppConstants.NIFTY_SMALLCAP_QUALITY_INDEX_FUND,0));
        dataVariables.put(AppConstants.MIDCAP_MOMENTUM_FUND,fundValueMap.getOrDefault(AppConstants.MIDCAP_MOMENTUM_FUND,0));
        dataVariables.put(AppConstants.NIFTY_ALPHA_FUND_KEY,fundValueMap.getOrDefault(AppConstants.NIFTY_ALPHA_FUND_KEY,0));
        dataVariables.put(NIFTY_MOMENTUM_FUND,fundValueMap.getOrDefault(NIFTY_MOMENTUM_FUND,0));
        dataVariables.put(NIFTY_MOMENTUM_QUALITY_FUND,fundValueMap.getOrDefault(NIFTY_MOMENTUM_QUALITY_FUND,0));
        dataVariables.put(NIFTY_SUSTAINABLE_FUND,fundValueMap.getOrDefault(NIFTY_SUSTAINABLE_FUND,0));
        dataVariables.put(NIFTY_SUSTAINABLE_WEALTH_FUND, fundValueMap.getOrDefault(NIFTY_SUSTAINABLE_WEALTH_FUND, 0));
        dataVariables.put(SMART_INNOVATION_FUND, fundValueMap.getOrDefault(SMART_INNOVATION_FUND, 0));
        return dataVariables;
    }

	private Map<String, Object> getFundValueByIndex(List<ProductDetails> productDetailsList,
			Map<String, Object> dataVariables) {
		Map<String, Integer> fundValueMap = new HashMap<>();
		List<FundInfo> fundInfoList = productDetailsList.get(0).getProductInfo().getFundSelection().getFundInfo();
		for (FundInfo fundInfo : fundInfoList) {
			if (StringUtils.equalsIgnoreCase(fundInfo.getFundName(), "03")) {
				dataVariables.put(AppConstants.HIGH_GROWTH_FUND, fundInfo.getFundValue());
			} else if (StringUtils.equalsIgnoreCase(fundInfo.getFundName(), "01")) {
				dataVariables.put(AppConstants.GROWTH_SUPER_FUND, fundInfo.getFundValue());
			} else if (StringUtils.equalsIgnoreCase(fundInfo.getFundName(), "02")) {
				dataVariables.put(AppConstants.GROWTH_FUND, fundInfo.getFundValue());
			} else if (StringUtils.equalsIgnoreCase(fundInfo.getFundName(), "04")) {
				dataVariables.put(AppConstants.BALANCED_FUND, fundInfo.getFundValue());
			} else if (StringUtils.equalsIgnoreCase(fundInfo.getFundName(), "05")) {
				dataVariables.put(AppConstants.SECURE_FUND, fundInfo.getFundValue());
			} else if (StringUtils.equalsIgnoreCase(fundInfo.getFundName(), "07")) {
				dataVariables.put(AppConstants.DYNAMIC_BOND_FUND, fundInfo.getFundValue());
			} else if (StringUtils.equalsIgnoreCase(fundInfo.getFundName(), "06")) {
				dataVariables.put(AppConstants.DIVERSIFIED_EQUITY_FUND, fundInfo.getFundValue());
			} else if (StringUtils.equalsIgnoreCase(fundInfo.getFundName(), "08")) {
				dataVariables.put(AppConstants.MONEY_MARKET_FUND_II, fundInfo.getFundValue());
			} else if (StringUtils.equalsIgnoreCase(fundInfo.getFundName(), "09")) {
				dataVariables.put(AppConstants.CONSERVATIVE_FUND, fundInfo.getFundValue());
			} else if (StringUtils.equalsIgnoreCase(fundInfo.getFundName(), "10")) {
				dataVariables.put(AppConstants.SUSTAINABLE_EQUITY_FUND, fundInfo.getFundValue());
			} else if (StringUtils.equalsIgnoreCase(fundInfo.getFundName(), "11")) {
				dataVariables.put(AppConstants.PURE_GROWTH_FUND, fundInfo.getFundValue());
			} else if (StringUtils.equalsIgnoreCase(fundInfo.getFundName(), "12")) {
				dataVariables.put(AppConstants.SECURE_PLUS_FUND, fundInfo.getFundValue());
			} else if (StringUtils.equalsIgnoreCase(fundInfo.getFundName(), "13")) {
				dataVariables.put(AppConstants.NIFTY_SMALLCAP_QUALITY_INDEX_FUND, fundInfo.getFundValue());
			} else if (StringUtils.equalsIgnoreCase(fundInfo.getFundName(), "14")) {
				dataVariables.put(AppConstants.MIDCAP_MOMENTUM_FUND, fundInfo.getFundValue());
			} else if (StringUtils.equalsIgnoreCase(fundInfo.getFundName(), "15")) {
				dataVariables.put(AppConstants.NIFTY_ALPHA_FUND_KEY, fundInfo.getFundValue());
			} else if (StringUtils.equalsIgnoreCase(fundInfo.getFundName(), "16")) {
                dataVariables.put(NIFTY_MOMENTUM_FUND, fundInfo.getFundValue());
            } else if (StringUtils.equalsIgnoreCase(fundInfo.getFundName(), "17")) {
                dataVariables.put(NIFTY_MOMENTUM_QUALITY_FUND, fundInfo.getFundValue());
            } else if (StringUtils.equalsIgnoreCase(fundInfo.getFundName(), "18")) {
                if (isNeoOrAggregator) {
                    dataVariables.put(NIFTY_SUSTAINABLE_WEALTH_FUND, fundInfo.getFundValue());
                } else {
                    dataVariables.put(NIFTY_SUSTAINABLE_FUND, fundInfo.getFundValue());
                }
            } else if (StringUtils.equalsIgnoreCase(fundInfo.getFundName(), "19")) {
                dataVariables.put(SMART_INNOVATION_FUND, fundInfo.getFundValue());
            }
		}

		return dataVariables;
	}

    /**
     * Set Payment Details in Context
     *
     * @param proposalDetails
     * @param dataVariables
     * @return
     */
    private Map<String, Object> setPaymentDetails(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {
        logger.info("START setPaymentDetails {}", "%m");
        //FUL2-11549 Payment acknowledgement for all channels
      	//Fetching receipt data for Secondary policy using getProposal api from proposal service - To fetch date for the salesstory secondary policy.
        proposalDetails = docsAppServiceImpl.setSecondPolicyReceipt(proposalDetails);
        Receipt receipt = proposalDetails.getPaymentDetails().getReceipt().get(0);
        String paymentType = receipt.getPremiumMode();
        double amount = 0;
        String paymentDate = StringUtils.EMPTY;
        String paymentMode = StringUtils.EMPTY;
        String transactionNumber = StringUtils.EMPTY;
        String bankName = StringUtils.EMPTY;
        long chequeNumber = 0;
        Boolean enachStatus = Boolean.FALSE;

        if (!isNeoOrAggregator && proposalDetails.getBank().getEnachDetails() != null && !isEmpty(proposalDetails.getBank().getEnachDetails().getEnachStatus())
                && proposalDetails.getBank().getEnachDetails().getEnachStatus().equalsIgnoreCase("SUCCESS")) {
            enachStatus = Boolean.TRUE;
        }

        if (receipt.getAmount() == null) {
            receipt.setAmount("0");
        }
        boolean isChannelAggregator = AppConstants.CHANNEL_AGGREGATOR.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel());
        if (AppConstants.PAY_LATER.equalsIgnoreCase(paymentType) && !isEmpty(receipt.getIsSIOpted()) && receipt.getIsSIOpted().equalsIgnoreCase("True")) {
            amount = Utility.setPremiumAmount(proposalDetails);
            paymentDate = receipt.getTransactionDateTimeStamp();
            paymentMode = "PAY LATER";
            transactionNumber = receipt.getTransactionReferenceNumber();
            bankName = AppConstants.NA;
            dataVariables.put(AppConstants.TRANSACTION_NUMBER, transactionNumber);

        } else if (AppConstants.PAY_LATER.equalsIgnoreCase(paymentType) && enachStatus) {
            amount = Utility.setPremiumAmount(proposalDetails);
            paymentDate = proposalDetails.getBank().getEnachDetails().getFirstCollectionDate().toString();
            paymentMode = "PAY LATER";
            transactionNumber = proposalDetails.getBank().getEnachDetails().getIngenicoTransactionId();
            bankName = proposalDetails.getBank().getEnachDetails().getSponsorBank();
            dataVariables.put(AppConstants.TRANSACTION_NUMBER, transactionNumber);

        } else if (AppConstants.ONLINE.equalsIgnoreCase(paymentType)) {
        	amount = Utility.setPremiumAmount(proposalDetails);
            if (isNeoOrAggregator) {
                amount = Double.valueOf(proposalDetails.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getInitialPremiumPaid());
                if(!StringUtils.isEmpty(receipt.getPaymentDate())) {
                    paymentDate = Utility.dateFormatter(receipt.getPaymentDate(), isChannelAggregator
                            ? AppConstants.DD_MM_YYYY_HYPHEN : AppConstants.DD_MM_YYYY_HH_MM_SS_HYPHEN, AppConstants.DD_MM_YYYY_HH_MM_SS_AA_SLASH);
                }
                transactionNumber = receipt.getReceiptId();
                dataVariables.put("desiredEffectiveDateOfPolicy", Utility.dateFormatter(receipt.getPaymentDate(), "dd-MM-yyyy HH:mm:ss", "dd/MM/yyyy"));
                paymentMode = receipt.getModeOfPayment();
            } else {
                paymentDate = receipt.getTransactionDateTimeStamp();
                transactionNumber = receipt.getTransactionReferenceNumber();
                paymentMode = "ONLINE";
            }
            bankName = AppConstants.NA;
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
            if (proposalDetails.getChannelDetails().getChannel().equalsIgnoreCase("BY") && receipt.getYblPaymentDetails() != null
                    && receipt.getYblPaymentDetails().getDirectDebitDetails() != null) {
                transactionNumber = receipt.getYblPaymentDetails().getDirectDebitDetails().getExternalRefNumber();
                paymentDate = Utility.evaluateConditionalOperation(
                        !StringUtils.isEmpty(receipt.getYblPaymentDetails().getDirectDebitDetails().getDirectDebitOtpValidated())
                        , receipt.getYblPaymentDetails().getDirectDebitDetails().getDirectDebitOtpValidated()
                        , Utility.dateFormatter(new Date()));
            }else if (Utility.andTwoExpressions(Utility.isTMBPartner(proposalDetails.getAdditionalFlags().getSourceChannel())
                    , ( receipt.getPartnerPaymentDetails() != null
                            && receipt.getPartnerPaymentDetails().getDirectDebitDetails() != null))) {
                transactionNumber = receipt.getPartnerPaymentDetails().getDirectDebitDetails().getExternalRefNumber();
                paymentDate = Utility.evaluateConditionalOperation(
                        !StringUtils.isEmpty(receipt.getPartnerPaymentDetails().getDirectDebitDetails().getDirectDebitOtpValidated())
                        , receipt.getPartnerPaymentDetails().getDirectDebitDetails().getDirectDebitOtpValidated()
                        , Utility.dateFormatter(new Date()));
            } else if (receipt.getDirectPaymentDetails() != null) {
                transactionNumber = receipt.getDirectPaymentDetails().getvoucherNumber();
                paymentDate = Utility.evaluateConditionalOperation(
                        receipt.getDirectPaymentDetails().getVoucherUpdatedDate() != null
                        , Utility.dateFormatter(receipt.getDirectPaymentDetails().getVoucherUpdatedDate())
                        , Utility.dateFormatter(new Date()));
            }
            amount = Utility.setPremiumAmount(proposalDetails);

            if(proposalDetails.getAdditionalFlags().getRequestSource().equalsIgnoreCase(AppConstants.AXIS_TELESALES_REQUEST)
                    && AppConstants.DIRECTDEBIT.equalsIgnoreCase(paymentType)) {
                paymentMode = "DIRECT DEBIT (IVR)";

            } else {
                paymentMode = "DIRECT DEBIT";
            }

            bankName = AppConstants.BLANK;
            dataVariables.put(AppConstants.TRANSACTION_NUMBER, transactionNumber);
        }
        // Setting data in dataMap
        String amountInWords = Utility.convertNumberToWords((int) amount);
        // Fix for FUL2-7287
        int amt = (int) amount;
        dataVariables.put("amountInWords", amountInWords);
        dataVariables.put("amount", amt);
        dataVariables.put("paymentMode", paymentMode);
        dataVariables.put("paymentDate", paymentDate);
        dataVariables.put("paymentBank", bankName);
        logger.info(END, "%m");
        return dataVariables;
    }

    /**
     * @param value
     * @return
     */
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

    /**
     * Setting Riders data in Context for iterative retrievals
     *
     * @param productDetails
     * @param dataVariables
     * @return
     */
    private Map<String, Object> setDataForRiders(ProductDetails productDetails, Map<String, Object> dataVariables) {

        logger.info("START {}", "%m");
        List<RiderDetails> riderDetails = productDetails.getProductInfo().getRiderDetails();
        List<ProposalRiderDetails> proposalRiderDetailsList = new ArrayList<>();
        ProductIllustrationResponse illustrationResponse = productDetails.getProductInfo().getProductIllustrationResponse();
        ProposalRiderDetails details;
        for (int i = 0; i < riderDetails.size(); i++) {

            if (riderDetails.get(i).isRiderRequired()) {
                details = new ProposalRiderDetails();
                details.setRiderRequired(true);
                String riderName = riderDetails.get(i).getRiderInfo();

                if (AppConstants.WOP.equalsIgnoreCase(riderName) || AppConstants.AXIS_WOP.equalsIgnoreCase(riderName)) {
                    details.setRiderName(AppConstants.AXIS_WOP);
                    details.setCoverageTerm(
                            Utility.evaluateConditionalOperation(
                                    !StringUtils.isEmpty(illustrationResponse.getWopPlusRiderTerm()), illustrationResponse.getWopPlusRiderTerm(), "0"));
                    details.setModalPremium(String.format("%.0f", roundOffValue(illustrationResponse.getFirstYearWOPPlusRiderPremiumSummary())));
                    details.setRiderSumAssured(String.format("%.0f", roundOffValue(illustrationResponse.getWopPlusRiderSumAssured())));
                    details.setRiderGST(
                            Utility.evaluateConditionalOperation(
                                    !StringUtils.isEmpty(illustrationResponse.getWopPlusRiderGST()), illustrationResponse.getWopPlusRiderGST(), "0"));


                } else if (AppConstants.TERM.equalsIgnoreCase(riderName) || AppConstants.AXIS_TERM.equalsIgnoreCase(riderName)) {
                    details.setRiderName(AppConstants.AXIS_TERM);
                    details.setCoverageTerm(
                            Utility.evaluateConditionalOperation(
                                    !StringUtils.isEmpty(illustrationResponse.getTermPlusRiderTerm()), illustrationResponse.getTermPlusRiderTerm(), "0"));
                    details.setModalPremium(String.format("%.0f", roundOffValue(illustrationResponse.getFirstYearTermPlusRiderPremiumSummary())));
                    details.setRiderSumAssured(String.format("%.0f", roundOffValue(illustrationResponse.getTermPlusRiderSumAssured())));
                    details.setRiderGST("0");


                } else if (AppConstants.ACD.equalsIgnoreCase(riderName) || AppConstants.AXIS_ACD.equalsIgnoreCase(riderName)) {

                    details.setRiderName(AppConstants.AXIS_ACD);
                    details.setCoverageTerm(
                            Utility.evaluateConditionalOperation(
                                    !StringUtils.isEmpty(illustrationResponse.getAddRiderTerm()), illustrationResponse.getAddRiderTerm(), "0"));
                    details.setModalPremium(String.format("%.0f", roundOffValue(illustrationResponse.getFirstYearADDRiderPremiumSummary())));
                    details.setRiderSumAssured(String.format("%.0f", roundOffValue(illustrationResponse.getAddRiderSumAssured())));
                    details.setRiderGST("0");
                } else if (AppConstants.PARTNER.equalsIgnoreCase(riderName) || AppConstants.AXIS_PARTNER.equalsIgnoreCase(riderName)) {
                    details.setRiderName(AppConstants.AXIS_PARTNER);
                    details.setCoverageTerm(
                            Utility.evaluateConditionalOperation(
                                    !StringUtils.isEmpty(illustrationResponse.getPartnerCareRiderTerm()), illustrationResponse.getPartnerCareRiderTerm(), "0"));
                    details.setModalPremium(String.format("%.0f", roundOffValue(illustrationResponse.getFirstYearPartnerCareRiderPremiumSummary())));
                    details.setRiderSumAssured(String.format("%.0f", roundOffValue(illustrationResponse.getPartnerCareRiderSumAssured())));
                    details.setRiderGST("0");

                } else if (AppConstants.CI.equalsIgnoreCase(riderName) || AppConstants.AXIS_CI.equalsIgnoreCase(riderName)) {
                    details.setRiderName(AppConstants.AXIS_CI);
                    details.setCoverageTerm(
                            Utility.evaluateConditionalOperation(
                                    StringUtils.isNotEmpty(illustrationResponse.getAcceleratedCriticalIllnessRiderTerm())
                                    , illustrationResponse.getAcceleratedCriticalIllnessRiderTerm()
                                    , "0"));
                    details.setModalPremium(
                            String.format("%.0f", roundOffValue(illustrationResponse.getFirstYearAcceleratedCriticalIllnessRiderPremiumSummary())));
                    details.setRiderSumAssured(String.format("%.0f", roundOffValue(illustrationResponse.getAcceleratedCriticalIllnessRiderSumAssured())));
                    details.setRiderGST(
                            Utility.evaluateConditionalOperation(
                                    !StringUtils.isEmpty(illustrationResponse.getAccidentalCriticalIllnessRiderGST())
                                    , illustrationResponse.getAccidentalCriticalIllnessRiderGST()
                                    , "0"));
                } else if (AppConstants.ACD_COVER.equalsIgnoreCase(riderName) || AppConstants.AXIS_ACD_COVER.equalsIgnoreCase(riderName)) {
                    details.setRiderName(AppConstants.AXIS_ACD_COVER);
                    details.setCoverageTerm(Utility.evaluateConditionalOperation(
                            StringUtils.isNotEmpty(illustrationResponse.getAccidentCoverRiderTerm()), illustrationResponse.getAccidentCoverRiderTerm(), "0"));
                    details.setModalPremium(String.format("%.0f", roundOffValue(illustrationResponse.getFirstYearAccidentCoverRiderPremiumSummary())));
                    details.setRiderSumAssured(String.format("%.0f", roundOffValue(illustrationResponse.getAccidentCoverRiderSumAssured())));
                    details.setRiderGST(Utility.evaluateConditionalOperation(
                            StringUtils.isNotEmpty(illustrationResponse.getAccidentalCoverRiderGST()), illustrationResponse.getAccidentalCoverRiderGST()
                            , "0"));
                } else if (AppConstants.CAB.equalsIgnoreCase(riderName) || AppConstants.AXIS_CAB.equalsIgnoreCase(riderName)) {
                    details.setRiderName(AppConstants.AXIS_CAB);
                    details.setCoverageTerm(
                            Utility.evaluateConditionalOperation(
                                    StringUtils.isNotEmpty(illustrationResponse.getCABRiderTerm()), illustrationResponse.getCABRiderTerm(), "0"));
                    details.setModalPremium(String.format("%.0f", roundOffValue(illustrationResponse.getCABRiderPremium())));
                    details.setRiderSumAssured(String.format("%.0f", roundOffValue(illustrationResponse.getCABRiderSumAssured())));
                    details.setRiderGST(Utility.evaluateConditionalOperation(
                            StringUtils.isNotEmpty(illustrationResponse.getCABRiderGST()), illustrationResponse.getCABRiderGST(), "0"));
                } //FUL2-30525 CIDR- Rider Name Change for ULIP products
                else if (AppConstants.CI_RIDER_ULIP.equalsIgnoreCase(riderName) || AppConstants.AXIS_CI_RIDER_ULIP.equalsIgnoreCase(riderName)) {
                    riderName = riderName +" - "+ (!StringUtils.isEmpty(riderDetails.get(i).getRiderVariant()) ? riderDetails.get(i).getRiderVariant() : AppConstants.BLANK);
                    details.setRiderName(riderName);
                    TraditionalFormUtil.setCIRiderDetails(illustrationResponse,details,riderName);
                    // FUL2-228988 PPT can be 10 or less, to be mapped from UI/LEResponse Tag later
                    try {
                        if (Integer.parseInt(details.getPremiumPayingTerm()) > 10) {
                            details.setPremiumPayingTerm("10");
                        }
                    } catch(Exception e) {
                        logger.error("Could not map PPT for CIDSR");
                    }
                } else if(AppConstants.RIDERVARS_SUP.contains(riderName)) {
                	setSUPRiderDetails(riderDetails.get(i),illustrationResponse,riderName,details);
                }
                proposalRiderDetailsList.add(details);
            }

            dataVariables.put("riderExist", !CollectionUtils.isEmpty(proposalRiderDetailsList));

        }
        dataVariables.put("riderDetails", proposalRiderDetailsList);
        logger.info(END, "%m");
        return dataVariables;
    }

    private void setSUPRiderDetails(RiderDetails riderDetails, ProductIllustrationResponse illustrationResponse, String riderName, ProposalRiderDetails details) {
    	details.setRiderName(AppConstants.RIDER_SUP); // Variant name is sent as riderInfo for SUPS Rider
    	details.setPremiumBackOption(riderName + ((!riderName.equalsIgnoreCase(AppConstants.RIDERVAR_PB) && riderDetails.getReturnOfPremium().equalsIgnoreCase(AppConstants.YES)) ?  AppConstants.WITHROP : AppConstants.WITHOUTROP));
		switch (riderName) {
			case AppConstants.RIDERVAR_TBATI:
				details.setPremiumBackOption(riderName
						+ (riderDetails.getReturnOfPremium().equalsIgnoreCase(AppConstants.YES) ? AppConstants.WITHROP
								: AppConstants.WITHOUTROP));
				details.setCoverageTerm(illustrationResponse.getSUPRiderTBATITerm());
				details.setPremiumPayingTerm(illustrationResponse.getSUPRiderTBATIPremiumPayingTerm());
				details.setRiderSumAssured(illustrationResponse.getSUPRiderTBATISumAssured());
				details.setModalPremium(illustrationResponse.getFirstYearSUPRiderTBATIPremiumSummary());
				details.setRiderGST(String.valueOf(roundOffValue(illustrationResponse.getSUPRiderTBATIRiderGST())));
				break;
			case AppConstants.RIDERVAR_ADB:
				details.setPremiumBackOption(riderName
						+ (riderDetails.getReturnOfPremium().equalsIgnoreCase(AppConstants.YES) ? AppConstants.WITHROP
								: AppConstants.WITHOUTROP));
				details.setCoverageTerm(illustrationResponse.getSUPRiderADBTerm());
				details.setPremiumPayingTerm(illustrationResponse.getSUPRiderADBPremiumPayingTerm());
				details.setRiderSumAssured(illustrationResponse.getSUPRiderADBSumAssured());
				details.setModalPremium(illustrationResponse.getFirstYearSUPRiderADBPremiumSummary());
				details.setRiderGST(String.valueOf(roundOffValue(illustrationResponse.getSUPRiderADBRiderGST())));
				break;
			case AppConstants.RIDERVAR_ATPD:
				details.setPremiumBackOption(riderName
						+ (riderDetails.getReturnOfPremium().equalsIgnoreCase(AppConstants.YES) ? AppConstants.WITHROP
								: AppConstants.WITHOUTROP));
				details.setCoverageTerm(illustrationResponse.getSUPRiderATPDTerm());
				details.setPremiumPayingTerm(illustrationResponse.getSUPRiderATPDPremiumPayingTerm());
				details.setRiderSumAssured(illustrationResponse.getSUPRiderATPDSumAssured());
				details.setModalPremium(illustrationResponse.getFirstYearSUPRiderATPDPremiumSummary());
				details.setRiderGST(String.valueOf(roundOffValue(illustrationResponse.getSUPRiderATPDRiderGST())));
				break;
			case AppConstants.RIDERVAR_PB:
				details.setPremiumBackOption(riderName);
				details.setCoverageTerm(illustrationResponse.getSUPRiderPayorTerm());
				details.setPremiumPayingTerm(illustrationResponse.getSUPRiderPayorPremiumPayingTerm());
				details.setRiderSumAssured(illustrationResponse.getSUPRiderPayorSumAssured());
				details.setModalPremium(illustrationResponse.getFirstYearSUPRiderPayorPremiumSummary());
				details.setRiderGST(String.valueOf(roundOffValue(illustrationResponse.getSUPRiderPayorRiderGST())));
				break;
			default:
				break;
		}
	}

	/**
     * NFO Options Data for the base plan
     *
     * @param basePlan
     * @return
     */
    private String getNfoOption(String basePlan) {
	String option = AppConstants.NA;
	logger.info("START {}", "%m");
	try {
	    ObjectMapper mapper = new ObjectMapper();
	    InputStream is = NFOOptions.class.getResourceAsStream("/templates/ulip/nfoOptions.json");
	    NFOOptions nFOOptionsList = mapper.readValue(is, NFOOptions.class);
	    List<Key> nfoDataKeyValue = nFOOptionsList.getKeys();
	    nfoDataKeyValue = nfoDataKeyValue.stream().filter(kv -> kv.getLabel().equalsIgnoreCase(basePlan)).collect(Collectors.toList());
	    if (!nfoDataKeyValue.isEmpty() && nfoDataKeyValue.size() > 0) {
		option = nfoDataKeyValue.get(0).getValue();
	    }
	} catch (IOException ex) {
	    logger.error("IOException occurred while reading the NFO Data from file: ", ex);
	}
	logger.info(END, "%m");
	return option;
    }

	private String getPlanName(ProductInfo productInfo, boolean isAggregator) {
        switch(productInfo.getPlanCode()){
            case "UCOSWF":
                 return "Axis Max Life Online Savings Plan Wealth Solution_CO";
            case "UCOSCF":
                 return "Axis Max Life Online Savings Plan Child Solution_CO";
            case "UNOSWF":
                return "Axis Max Life Online Savings Plan Wealth Solution_NC";
            case "UNOSCF":
                return "Axis Max Life Online Savings Plan Child Solution_NC";
            case "UFINFL":
                return isAggregator ? "Axis Max Life Smart Flexi Protect Solution - Online (Limited Pay)" : productInfo.getProductName();
            case "UFINPL":
                return isAggregator ? "Axis Max Life Smart Flexi Protect Solution Premium - Online (Limited Pay)" : productInfo.getProductName();
            case  "UFINRL":
                return isAggregator ? "Axis Max Life Smart Flexi Protect Solution Risk - Online (Limited Pay)" : productInfo.getProductName();
            case"UFINFR":
                return isAggregator ? "Axis Max Life Smart Flexi Protect Solution - Online (Regular Pay)" : productInfo.getProductName();
            case "UFINPR":
                return isAggregator ? "Axis Max Life Smart Flexi Protect Solution Premium - Online (Regular Pay)" : productInfo.getProductName();
            case "UFINRR":
                return isAggregator ?  "Axis Max Life Smart Flexi Protect Solution Risk - Online (Regular Pay)" : productInfo.getProductName();
            default:
                return productInfo.getProductName();
        }
	}

}
