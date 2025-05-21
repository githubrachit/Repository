package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.*;
import com.mli.mpro.utils.Utility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class NeoCISDocumentMapper {

    public static final String NA = "NA";

    @Autowired
    private BaseMapper baseMapper;
    private static final Logger logger = LoggerFactory.getLogger(NeoCISDocumentMapper.class);

    @Value("${cis.fwap.product.name}")
    private String productNameFWAP;
    @Value("${cis.hsa.product.name}")
    private String productNameHSA;
    @Value("${cis.osp.product.name}")
    private String productNameOSP;
    @Value("${cis.sfrd.product.name}")
    private String sfrdProductName;
    @Value("${cis.sjb.product.name}")
    private String sjbProductName;
    @Value("${cis.swag.product.name}")
    private String swagProductName;
    @Value("${cis.step.product.name}")
    private String stepProductName;
    @Value("${cis.ssp.product.name}")
    private String sspProductName;
    @Value("${cis.swp.product.name}")
    private String swpProductName;
    @Value("${cis.stpp.product.name}")
    private String stppProductName;

    @Value("${cis.fwap.uin}")
    private String fwapUIN;
    @Value("${fwap.cis.new.uin}")
    private String fwapNewUIN;
    @Value("${cis.fwp.uin}")
    private String hsaUIN;
    @Value("${cis.osp.uin}")
    private String ospUIN;
    @Value("${cis.sfrd.uin}")
    private String sfrdUIN;
    @Value("${cis.sjb.uin}")
    private String sjbUIN;
    @Value("${cis.swag.uin}")
    private String swagUIN;
    @Value("${cis.swag.old.uin}")
    private String swagOldUIN;
    @Value("${cis.step.uin}")
    private String stepUIN;
    @Value("${cis.step.old.uin}")
    private String stepOldUIN;
    @Value("${cis.ssp.uin}")
    private String sspUIN;
    @Value("${cis.swp.uin}")
    private String swpUIN;
    @Value("${cis.swp.old.uin}")
    private String swpOldUIN;
    @Value("${cis.stpp.uin}")
    private String stppUIN;
    @Value("${cis.stpp.old.uin")
    private String stppOldUIN;

    @Value("${fwap.cis.payment.date}")
    private String fwapPaymentDate;

    private static final Map<String, String> frequencyOfPaymentMap;
    List<String> instalmentPremiumProducts = List.of("SFRD","SJB","SWP","SWPV1","SWPV2","SWPV3");
    List<String> stppIncomeCoverProductCode = List.of("TSTIPS","TSTIPR","TSTIPL","TSTIP6", "TSHIPS", "TSHIPR", "TSHIPL", "TSHIP6");
    List<String> checkForSSPAndSTEPAndSTPP = List.of("ste", "SSP", "stpp");
    static {
        frequencyOfPaymentMap = new HashMap<>();
        frequencyOfPaymentMap.put("01", "Annual");
        frequencyOfPaymentMap.put("02", "Semi-Annual");
        frequencyOfPaymentMap.put("03", "Monthly");
        frequencyOfPaymentMap.put("04", "Quarterly");
        frequencyOfPaymentMap.put("05", "Single");
    }


    public Context setNeoCISData (ProposalDetails proposalDetails, String htmlForm) throws UserHandledException {
        logger.info("Stared Mapping for Neo CIS Document for transactionId {}",proposalDetails.getTransactionId());
        Context context = new Context();
        Map<String, Object> dataForDocument = new HashMap<>();
        String productName="";
        String uin = "";
        String policyNumber = "";
        String typeOfInsurance ="";
        String instalmentPremium="";
        String premiumPaymentMode = "";
        String sumAssuredOnDeath = "";
        String sumAssuredOnMaturity = "";
        String policyTerm = "";
        String premiumPaymentTerm = "";
        String gracePeriod = "";
        String tatDays = "";
        String place ="";
        String date = "";
        String incomeBenefit = "";
        String incomeBenefitPeriod = "";
        String incomeBenefitPayPeriod = "";
        String defermentPeriod = "";
        String variant = "";
        String benefitReturnFrequency = "";
        String incomeBenefitPayable = "";
        String incomeCover = "";
        String survivalBenefit = "";
        String maturityBenefit = "";
        String monthlyIncomeInception = "";
        String sumAssured = "";

        String product = proposalDetails.getProductDetails().get(0).getProductType();
        ProductInfo productInfo = proposalDetails.getProductDetails().get(0).getProductInfo();
        variant = productInfo.getVariant();
        switch (htmlForm){
            case AppConstants.FWAP_CIS:
                productName = productNameFWAP;
                uin = settingUIN(proposalDetails, htmlForm);
                break;
            case AppConstants.FWP_CIS:
                productName = productNameHSA;
                uin = hsaUIN;
                break;
            case AppConstants.OSP_CIS:
                productName = productNameOSP;
                uin = ospUIN;
                break;
            case AppConstants.SFRD_CIS:
                productName = sfrdProductName;
                uin = sfrdUIN;
                break;
            case AppConstants.SJB_CIS:
                productName = sjbProductName;
                uin = sjbUIN;
                break;
            case AppConstants.SWAG_CIS:
                productName = swagProductName;
                uin = settingUIN(proposalDetails, htmlForm);
                break;
            case AppConstants.STEP_CIS:
                productName= stepProductName;
                uin = settingUIN(proposalDetails, htmlForm);
                break;
            case AppConstants.SSP_CIS:
                productName= sspProductName;
                uin = sspUIN;
                break;
            case AppConstants.SWP_CIS:
                productName = swpProductName;
                uin = settingUIN(proposalDetails, htmlForm);
                break;
            case AppConstants.SWPV1_CIS:
                productName = swpProductName;
                uin = settingUIN(proposalDetails, htmlForm);
                break;
            case AppConstants.SWPV2_CIS:
                productName = swpProductName;
                uin = settingUIN(proposalDetails, htmlForm);
                break;
            case AppConstants.SWPV3_CIS:
                productName = swpProductName;
                uin = settingUIN(proposalDetails, htmlForm);
                break;
            case AppConstants.NEO_STPP_PRODUCT_TYPE:
                productName = stppProductName;
                uin = settingUIN(proposalDetails, htmlForm);
                break;
            default:
                logger.info("CIS Document is generated only for valid products ");
        }
        policyNumber = proposalDetails.getApplicationDetails() != null ? proposalDetails.getApplicationDetails().getPolicyNumber():policyNumber;
        typeOfInsurance = AppConstants.TYPE_OF_INSURANCE;
        if(proposalDetails.getQuotePayload() != null && proposalDetails.getQuotePayload().getApplication() != null &&
                proposalDetails.getQuotePayload().getApplication().getQuote() != null &&
                proposalDetails.getQuotePayload().getApplication().getQuote().getProductsDetails() != null &&
                !proposalDetails.getQuotePayload().getApplication().getQuote().getProductsDetails().isEmpty() &&
                proposalDetails.getQuotePayload().getApplication().getQuote().getProductsDetails().get(0).getProductsList() != null &&
                !proposalDetails.getQuotePayload().getApplication().getQuote().getProductsDetails().get(0).getProductsList().isEmpty() &&
                proposalDetails.getQuotePayload().getApplication().getQuote().getProductsDetails().get(0).getProductsList().get(0).getQuoteDetails() != null){
            QuoteDetails quoteDetails = proposalDetails.getQuotePayload().getApplication().getQuote().getProductsDetails().get(0).getProductsList().get(0).getQuoteDetails();
            if (checkForSSPAndSTEPAndSTPP.contains(htmlForm) && StringUtils.isNotEmpty(quoteDetails.getCurrentPlanPremium())
                    && StringUtils.isNotEmpty(quoteDetails.getPlanServiceTax())) {
                instalmentPremium = String.format("%.2f", Double.parseDouble(quoteDetails.getCurrentPlanPremium()) + Double.parseDouble(quoteDetails.getPlanServiceTax()));
            } else if (AppConstants.CHANNEL_NEO.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())
                    && "01".equalsIgnoreCase(proposalDetails.getApplicationDetails().getIsComboSale())
                    && AppConstants.SWAG_CIS.equalsIgnoreCase(htmlForm)) {
                instalmentPremium = StringUtils.isNotEmpty(quoteDetails.getTotalPremium()) ? quoteDetails.getTotalPremium() : instalmentPremium;
            } else {
                instalmentPremium = quoteDetails.getTotalPremiumExcGST() != null ? quoteDetails.getTotalPremiumExcGST() : instalmentPremium;
            }
            sumAssuredOnDeath = quoteDetails.getSumAssured() != null ? quoteDetails.getSumAssured() : sumAssuredOnDeath;
            sumAssuredOnMaturity = quoteDetails.getCorpusValue() != null ? quoteDetails.getCorpusValue() : sumAssuredOnMaturity;
            policyTerm =  quoteDetails.getPolicyTerm() != null ? quoteDetails.getPolicyTerm() : policyTerm;
            premiumPaymentTerm =  quoteDetails.getPolicyPaymentTerm() != null ? quoteDetails.getPolicyPaymentTerm() : premiumPaymentTerm;
            incomeBenefitPayable = quoteDetails.getBenefitMonthlyIncome();
            incomeCover = StringUtils.isNotEmpty(quoteDetails.getIncomeCover()) ? quoteDetails.getIncomeCover() : incomeCover;
            monthlyIncomeInception = StringUtils.isNotEmpty(quoteDetails.getMonthlyIncomeInception()) ? quoteDetails.getMonthlyIncomeInception() : monthlyIncomeInception;
            if(AppConstants.SWAG_CIS.equalsIgnoreCase(htmlForm) && (AppConstants.LONG_TERM_WEALTH.equalsIgnoreCase(variant.replace("\u00A0", " ")) || AppConstants.EARLY_WEALTH.equalsIgnoreCase(variant.replace("\u00A0", " ")) || AppConstants.LIFE_LONG_WEALTH.equalsIgnoreCase(variant.replace("\u00A0", " ")))){
                sumAssuredOnDeath = quoteDetails.getTerminalBenefit();
                sumAssuredOnMaturity = quoteDetails.getSumAssured();
                incomeBenefit = quoteDetails.getBenefitMonthlyIncome();
                incomeBenefitPeriod = quoteDetails.getIncomePeriod();
                incomeBenefitPayPeriod = frequencyOfPaymentMap.get(quoteDetails.getBenefitReturnFrequency());
                defermentPeriod = quoteDetails.getDefermentPeriod();
            } else if (AppConstants.SWAG_CIS.equalsIgnoreCase(htmlForm) && AppConstants.WEALTH_FOR_MILESTONE.equalsIgnoreCase(variant)){
                sumAssuredOnMaturity = StringUtils.isNotEmpty(quoteDetails.getCorpusValue()) ? quoteDetails.getCorpusValue() : sumAssuredOnMaturity;
                sumAssuredOnDeath = StringUtils.isNotEmpty(quoteDetails.getTerminalBenefit()) ? quoteDetails.getTerminalBenefit() : sumAssuredOnDeath;
            } else if (Utility.isSwpCisWithValidVariant(htmlForm, variant)){
                sumAssuredOnDeath = StringUtils.isNotEmpty(quoteDetails.getGuaranteedDeathBenefit()) ? quoteDetails.getGuaranteedDeathBenefit() : sumAssuredOnDeath;
            } else if(AppConstants.NEO_STPP_PRODUCT_TYPE.equalsIgnoreCase(htmlForm)){
                sumAssuredOnMaturity = StringUtils.isNotEmpty(quoteDetails.getGuaranteedDeathBenefit()) ? quoteDetails.getGuaranteedDeathBenefit() : quoteDetails.getSumAssured();
                survivalBenefit = StringUtils.isNotEmpty(quoteDetails.getSurvivalBenefit()) ? quoteDetails.getSurvivalBenefit() : NA;
                maturityBenefit = StringUtils.isNotEmpty(quoteDetails.getMaturityBenefit()) ? quoteDetails.getMaturityBenefit() : NA;
                incomeCover = StringUtils.isNotEmpty(quoteDetails.getIncomeCover()) ? quoteDetails.getIncomeCover() : NA;
                monthlyIncomeInception = StringUtils.isNotEmpty(quoteDetails.getMonthlyIncomeInception()) ? quoteDetails.getMonthlyIncomeInception() : NA;
                sumAssured = StringUtils.isNotEmpty(quoteDetails.getSumAssured()) ? quoteDetails.getSumAssured() : NA;
            }
            if(AppConstants.SWAG_CIS.equalsIgnoreCase(htmlForm) && AppConstants.EARLY_WEALTH.equalsIgnoreCase(variant.replace("\u00A0", " "))){
                sumAssuredOnMaturity = StringUtils.isNotEmpty(quoteDetails.getCorpusValue()) ? quoteDetails.getCorpusValue() : sumAssuredOnMaturity;
            }
            if("TSWPTL".equalsIgnoreCase(productInfo.getProductId()) || "TSWPTS".equalsIgnoreCase(productInfo.getProductId())) {
                sumAssuredOnMaturity = NA;
            }
            if ("TSWPPL".equalsIgnoreCase(productInfo.getProductId())) {
                incomeBenefitPayable = NA;
            }
        }

        if(proposalDetails.getQuotePayload() != null && proposalDetails.getQuotePayload().getApplication() != null &&
                proposalDetails.getQuotePayload().getApplication().getQuote() != null &&
                proposalDetails.getQuotePayload().getApplication().getQuote().getQuoteInformation() != null){
            premiumPaymentMode = proposalDetails.getQuotePayload().getApplication().getQuote().getQuoteInformation().getFrequencyOfPayment() != null ?
                    frequencyOfPaymentMap.get(proposalDetails.getQuotePayload().getApplication().getQuote().getQuoteInformation().getFrequencyOfPayment()): premiumPaymentMode;
            gracePeriod = determineNumberOfDays(premiumPaymentMode);
            if(instalmentPremiumProducts.contains(htmlForm)
                    || (AppConstants.SWAG_CIS.equalsIgnoreCase(htmlForm) && !"01".equalsIgnoreCase(proposalDetails.getApplicationDetails().getIsComboSale()))){
                instalmentPremium = proposalDetails.getQuotePayload().getApplication().getQuote().getQuoteInformation().getCollectiveservicewithST();
            }
        }
        tatDays=AppConstants.TAT_DAYS;
        date = getDeclarationDate(proposalDetails);
        String formType = proposalDetails.getApplicationDetails() != null ? proposalDetails.getApplicationDetails().getFormType(): StringUtils.EMPTY;
        String schemeType = proposalDetails.getApplicationDetails() != null ? proposalDetails.getApplicationDetails().getSchemeType(): StringUtils.EMPTY;
        PartyInformation partyInformation = (AppConstants.FORM3.equalsIgnoreCase(formType) && !Utility.schemeBCase(schemeType))
                ? proposalDetails.getPartyInformation().stream()
                .filter(Objects::nonNull)
                .filter(partyInfo -> AppConstants.COMPANY.equalsIgnoreCase(partyInfo.getPartyType()))
                .findFirst()
                .orElse(null)
                : proposalDetails.getPartyInformation().get(0);
        place = getPlace(partyInformation);
        if (Objects.nonNull(productInfo) && Objects.nonNull(productInfo.getBenefitReturnFrequency())) {
            if ("TSWPPL".equalsIgnoreCase(productInfo.getProductId())) {
                benefitReturnFrequency = NA;
            } else {
                benefitReturnFrequency = productInfo.getBenefitReturnFrequency();
            }
        }


        dataForDocument.put("productName",productName);
        dataForDocument.put("UIN",uin);
        dataForDocument.put("policyNumber",policyNumber);
        dataForDocument.put("typeOfInsurance",typeOfInsurance);
        dataForDocument.put("instalmentPremium",instalmentPremium);
        dataForDocument.put("premiumPaymentMode",premiumPaymentMode);
        dataForDocument.put("sumAssuredOnDeath",sumAssuredOnDeath);
        dataForDocument.put("sumAssuredOnMaturity",sumAssuredOnMaturity);
        dataForDocument.put("policyTerm",policyTerm);
        dataForDocument.put("premiumPaymentTerm",premiumPaymentTerm);
        dataForDocument.put("gracePeriod",gracePeriod);
        dataForDocument.put("tatDays",tatDays);
        dataForDocument.put("place",place);
        dataForDocument.put("date",date);
        dataForDocument.put("product",product);
        dataForDocument.put("incomeBenefit", incomeBenefit);
        dataForDocument.put("incomeBenefitPayPeriod", incomeBenefitPayPeriod);
        dataForDocument.put("incomeBenefitPeriod", incomeBenefitPeriod);
        dataForDocument.put("defermentPeriod", defermentPeriod);
        dataForDocument.put("variant", variant);
        dataForDocument.put("benefitReturnFrequency",benefitReturnFrequency);
        dataForDocument.put("incomeBenefitPayable",incomeBenefitPayable);
        dataForDocument.put("incomeCover", incomeCover);
        dataForDocument.put("survivalBenefit", survivalBenefit);
        dataForDocument.put("maturityBenefit", maturityBenefit);
        dataForDocument.put("monthlyIncomeInception", monthlyIncomeInception);
        dataForDocument.put("sumAssured", sumAssured);

        context.setVariables(dataForDocument);
        return context;
    }

    private String settingUIN(ProposalDetails proposalDetails, String htmlForm) {
        String uin;
        switch (htmlForm) {
            case AppConstants.SWAG_CIS:
                if (AppConstants.NEO_YES.equalsIgnoreCase(proposalDetails.getIsWipCaseForRateChange())) {
                    uin = swagOldUIN;
                } else {
                    uin = swagUIN;
                }
                break;
            case AppConstants.NEO_STPP_PRODUCT_TYPE:
                if (AppConstants.NEO_YES.equalsIgnoreCase(proposalDetails.getIsWipCaseForRateChange())){
                    uin = stppOldUIN;
                }else{
                    uin = stppUIN;
                }
                break;
            case AppConstants.STEP_CIS:
                if (AppConstants.NEO_YES.equalsIgnoreCase(proposalDetails.getIsWipCaseForRateChange())){
                    uin = stepOldUIN;
                }else{
                    uin = stepUIN;
                }
                break;
            case AppConstants.SWP_CIS:
            case AppConstants.SWPV1_CIS:
            case AppConstants.SWPV2_CIS:
            case AppConstants.SWPV3_CIS:
                if (AppConstants.NEO_YES.equalsIgnoreCase(proposalDetails.getIsWipCaseForRateChange())){
                    uin = swpOldUIN;
                }else{
                    uin = swpUIN;
                }
                break;
            case AppConstants.FWAP_CIS:
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                LocalDateTime comparisonDate = LocalDateTime.parse(fwapPaymentDate, formatter);
                String paymentDate = Optional.ofNullable(proposalDetails)
                        .map(ProposalDetails::getPaymentDetails)
                        .map(PaymentDetails::getReceipt)
                        .filter(list -> !list.isEmpty())
                        .map(list -> list.get(0))
                        .map(Receipt::getPaymentDate)
                        .orElse(null);
                LocalDateTime paymentDateTime = LocalDateTime.parse(paymentDate, formatter);
                if (paymentDateTime.isAfter(comparisonDate)){
                    uin = fwapNewUIN;
                }else {
                    uin = fwapUIN;
                }
                break;
            default:
                uin = "Invalid htmlForm value: "+ htmlForm;
        }
        return uin;
    }

    private String determineNumberOfDays(String premiumPaymentMode) {
        if(AppConstants.MONTHLY.equalsIgnoreCase(premiumPaymentMode)){
            return AppConstants.GRACE_PERIOD_MONTHLY;
        } else if (AppConstants.ANNUAL.equalsIgnoreCase(premiumPaymentMode) || AppConstants.QUARTERLY.equalsIgnoreCase(premiumPaymentMode) || AppConstants.SEMI_ANNUAL.equalsIgnoreCase(premiumPaymentMode)) {
            return AppConstants.GRACE_PERIOD;
        } else {
            return "Not Applicable in Single Premium Mode";
        }
    }

    private String getPlace(PartyInformation partyInformation) {
        if (partyInformation != null) {
            BasicDetails basicDetails = partyInformation.getBasicDetails();
            if (basicDetails != null) {
                List<Address> addresses = basicDetails.getAddress();
                if (addresses != null && !addresses.isEmpty()) {
                    AddressDetails addressDetails = addresses.get(0).getAddressDetails();
                    if (addressDetails != null) {
                        return addressDetails.getCity();
                    }
                }
            }
        }
        return AppConstants.BLANK;
    }

    private String getDeclarationDate (ProposalDetails proposalDetails){
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        format.setTimeZone(TimeZone.getTimeZone(AppConstants.APP_TIMEZONE));
        String generatedOtp = (proposalDetails != null &&
                proposalDetails.getApplicationDetails() != null  &&
                proposalDetails.getApplicationDetails().getOtpDateTimeStamp() !=null)
                ? proposalDetails.getApplicationDetails().getOtpDateTimeStamp()
                : null;
        return generatedOtp != null ? generatedOtp.substring(0, 10): StringUtils.EMPTY;
    }
}
