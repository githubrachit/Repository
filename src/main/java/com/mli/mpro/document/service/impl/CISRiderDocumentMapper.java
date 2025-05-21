package com.mli.mpro.document.service.impl;


import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.*;
import com.mli.mpro.utils.Utility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CISRiderDocumentMapper {

    private static final Logger logger = LoggerFactory.getLogger(CISRiderDocumentMapper.class);
    private static final Map<String, String> RIDER_NAME_MAP = new HashMap<>();

    static {
        RIDER_NAME_MAP.put(AppConstants.ADD_RIDER, AppConstants.ADD_RIDER_NAME);
        RIDER_NAME_MAP.put(AppConstants.TERM_RIDER, AppConstants.TERM_RIDER_NAME);
        RIDER_NAME_MAP.put(AppConstants.WOP_RIDER,AppConstants.WOP_RIDER_NAME);
        RIDER_NAME_MAP.put(AppConstants.SUPR_RIDER,AppConstants.AXIS_SUPR_RIDER_NAME);
        RIDER_NAME_MAP.put(AppConstants.CIDS_RIDER,AppConstants.CIDS_RIDER_NAME);
        RIDER_NAME_MAP.put(AppConstants.CID_RIDER,AppConstants.CID_RIDER_NAME);
    }

    public Context setRiderCISData(ProposalDetails proposalDetails, String rider) throws UserHandledException {
        logger.info("Stared Mapping for CIS Document for transactionId {}", proposalDetails.getTransactionId());
        Context context = new Context();
        Map<String, Object> dataForDocument = new HashMap<>();
        String riderName = "";
        String riderUIN = "";
        String policyNumber = "";
        String instalmentPremium = "";
        String premiumPaymentMode = "";
        double sumAssuredOnDeath = 0.0;
        String sumAssuredOnMaturity = "";
        String policyTerm = "";
        String premiumPaymentTerm = "";
        String tatDays = "";
        String place = "";
        String date = "";
        String turnAroundTime = "";
        String numberOfDays = "";

        ProductIllustrationResponse productIllustrationDetails = proposalDetails.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse();
        ProductInfo productInfo = proposalDetails.getProductDetails().get(0).getProductInfo();
        String formType = proposalDetails.getApplicationDetails().getFormType();
        String schemeType = proposalDetails.getApplicationDetails().getSchemeType();
        PartyInformation partyInformation = (AppConstants.FORM3.equalsIgnoreCase(formType) && !Utility.schemeBCase(schemeType))
                ? proposalDetails.getPartyInformation().stream()
                .filter(Objects::nonNull)
                .filter(partyInfo -> AppConstants.COMPANY.equalsIgnoreCase(partyInfo.getPartyType()))
                .findFirst()
                .orElse(null)
                : proposalDetails.getPartyInformation().get(0);
        List<RiderDetails> riderDetails = proposalDetails.getProductDetails().get(0).getProductInfo().getRiderDetails();
        //Mapping Keys
        premiumPaymentMode = (productInfo!=null && productInfo.getModeOfPayment()!=null) ?productInfo.getModeOfPayment():premiumPaymentMode;
        numberOfDays= Utility.determineNumberOfDays(premiumPaymentMode);
        date = getDeclarationDate(proposalDetails);
        place = getPlace(partyInformation);
        riderName = getPlanName(rider);
        riderUIN = Utility.getUIN(productIllustrationDetails, riderName);
        policyNumber = (proposalDetails != null && proposalDetails.getApplicationDetails() != null) ? proposalDetails.getApplicationDetails().getPolicyNumber() : policyNumber;
        instalmentPremium = getInstalmentPremium(productIllustrationDetails,rider);
        premiumPaymentMode = (productInfo != null && productInfo.getModeOfPayment() != null) ? productInfo.getModeOfPayment() : premiumPaymentMode;
        premiumPaymentTerm = (productInfo != null && productInfo.getPremiumPaymentTerm() != null) ? productInfo.getPremiumPaymentTerm() : premiumPaymentTerm;
        tatDays = AppConstants.TAT_DAYS;
        turnAroundTime = AppConstants.TRUN_AROUND_TIME;
        getSUPRRiderVariants(riderDetails,dataForDocument,productIllustrationDetails);

        dataForDocument.put("riderName", riderName);
        dataForDocument.put("UIN", riderUIN);
        dataForDocument.put("policyNumber", policyNumber);
        dataForDocument.put("instalmentPremium", instalmentPremium);
        dataForDocument.put("premiumPaymentMode", premiumPaymentMode);
        dataForDocument.put("sumAssuredOnDeath", String.format("%.2f",sumAssuredOnDeath));
        dataForDocument.put("sumAssuredOnMaturity", sumAssuredOnMaturity);
        dataForDocument.put("policyTerm", policyTerm);
        dataForDocument.put("premiumPaymentTerm", premiumPaymentTerm);
        dataForDocument.put("tatDays", tatDays);
        dataForDocument.put("turnAroundTime", turnAroundTime);
        dataForDocument.put("date", date);
        dataForDocument.put("place", place);
        dataForDocument.put("numberOfDays",numberOfDays);

        riderName = AppConstants.CIDS_RIDER.equalsIgnoreCase(rider) ? AppConstants.CIDS_RIDER_DB_NAME : riderName;
        riderName = AppConstants.CID_RIDER.equalsIgnoreCase(rider)? AppConstants.CI_RIDER: riderName;
        getRiderTermAndSumAssured(riderDetails, riderName, dataForDocument);

        context.setVariables(dataForDocument);
        return context;

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

    private String getDeclarationDate(ProposalDetails proposalDetails) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone(AppConstants.APP_TIMEZONE));
        Date generatedOtp = (proposalDetails != null &&
                proposalDetails.getPosvDetails() != null &&
                proposalDetails.getPosvDetails().getPosvStatus() != null &&
                proposalDetails.getPosvDetails().getPosvStatus().getGeneratedOTPDate() != null)
                ? proposalDetails.getPosvDetails().getPosvStatus().getGeneratedOTPDate()
                : null;

        return generatedOtp != null ? format.format(generatedOtp) : StringUtils.EMPTY;
    }

    private String getPlanName(String rider) {
        return RIDER_NAME_MAP.getOrDefault(rider, "");
    }

    private void getRiderTermAndSumAssured(List<RiderDetails> riderDetails, String riderName, Map<String, Object> dataForDocument) {
        String riderTerm = AppConstants.BLANK;
        double sumAssuredOnDeath = 0.0;
        for (RiderDetails rider : riderDetails) {
            String riderSelected = rider.getRiderInfo();
            if (riderSelected != null && !riderSelected.isEmpty()
                    && (riderSelected.equalsIgnoreCase(riderName))) {
                riderTerm = rider.getRiderTerm();
                sumAssuredOnDeath = rider.getAmount();
            }
        }
        dataForDocument.put("policyTerm", riderTerm);
        dataForDocument.put("sumAssuredOnDeath", String.format("%.2f",sumAssuredOnDeath));
    }
    private void getSUPRRiderVariants(List<RiderDetails> riderDetails, Map<String, Object> dataForDocument, ProductIllustrationResponse productIllustrationResponse) {
        boolean termBoosterOpted = false;
        boolean accidentalDeathBenefitOpted = false;
        boolean accidentalDisabilityOpted = false;
        boolean payorBenefitOpted = false;

        String termBoosterSumAssured = AppConstants.BLANK;
        String payorBenefitSumAssured = AppConstants.BLANK;
        String accidentalDeathBenefitSumAssured = AppConstants.BLANK;
        String accidentalDisabilitySumAssured = AppConstants.BLANK;

        // Iterate through the riderDetails list and determine which riders are opted for
        for (RiderDetails rider : riderDetails) {
            String riderInfo = rider.getRiderInfo();
            // Set flags and sum assured based on the rider info
            if (AppConstants.RIDERVAR_TBATI.equalsIgnoreCase(riderInfo)) {
                termBoosterOpted = true;
                termBoosterSumAssured = formatStringToTwoDecimalPlaces(productIllustrationResponse != null && productIllustrationResponse.getSUPRiderTBATISumAssured() != null
                        ? productIllustrationResponse.getSUPRiderTBATISumAssured()
                        : AppConstants.BLANK);
            } else if (AppConstants.RIDERVAR_PB.equalsIgnoreCase(riderInfo)) {
                payorBenefitOpted = true;
                payorBenefitSumAssured = formatStringToTwoDecimalPlaces(productIllustrationResponse!=null && productIllustrationResponse.getSUPRiderPayorSumAssured()!=null
                        ? productIllustrationResponse.getSUPRiderPayorSumAssured()
                        : AppConstants.BLANK);
            } else if (AppConstants.RIDERVAR_ADB.equalsIgnoreCase(riderInfo)) {
                accidentalDeathBenefitOpted = true;
                accidentalDeathBenefitSumAssured =formatStringToTwoDecimalPlaces(productIllustrationResponse!=null && productIllustrationResponse.getSUPRiderADBSumAssured()!=null
                        ? productIllustrationResponse.getSUPRiderADBSumAssured()
                        : AppConstants.BLANK);
            } else if (AppConstants.RIDERVAR_ATPD.equalsIgnoreCase(riderInfo)) {
                accidentalDisabilityOpted = true;
                accidentalDisabilitySumAssured = formatStringToTwoDecimalPlaces(productIllustrationResponse!=null && productIllustrationResponse.getSUPRiderATPDSumAssured()!=null
                        ? productIllustrationResponse.getSUPRiderATPDSumAssured()
                        : AppConstants.BLANK);
            }
        }
        dataForDocument.put("termBoosterOpted", termBoosterOpted);
        dataForDocument.put("accidentalDeathBenefitOpted", accidentalDeathBenefitOpted);
        dataForDocument.put("accidentalDisabilityOpted", accidentalDisabilityOpted);
        dataForDocument.put("payorBenefitOpted", payorBenefitOpted);
        dataForDocument.put("termBoosterSumAssured", termBoosterSumAssured);
        dataForDocument.put("accidentalDeathBenefitSumAssured", accidentalDeathBenefitSumAssured);
        dataForDocument.put("accidentalDisabilitySumAssured", accidentalDisabilitySumAssured);
        dataForDocument.put("payorBenefitSumAssured", payorBenefitSumAssured);
    }
    private String formatStringToTwoDecimalPlaces(String value) {
        if (value != null && !value.isEmpty()) {
            Double doubleValue = Double.parseDouble(value);
            return String.format("%.2f", doubleValue);
        }
        return AppConstants.BLANK;
    }
    private String getInstalmentPremium(ProductIllustrationResponse productIllustrationDetails,String rider) {
        String installmentPremium = AppConstants.BLANK;
        if (productIllustrationDetails != null && AppConstants.TERM_RIDER.equalsIgnoreCase(rider)
                && org.springframework.util.StringUtils.hasText(productIllustrationDetails.getFirstYearTermPlusRiderPremiumSummary()))
        {
            double modalPremium = Double.parseDouble(productIllustrationDetails.getFirstYearTermPlusRiderPremiumSummary());
            double termPlusRiderGST = Double.parseDouble(productIllustrationDetails.getTermPlusRiderGST());
            double totalPremium = modalPremium + termPlusRiderGST;
            installmentPremium = String.format("%.2f", totalPremium);
            return installmentPremium;
        }
        return installmentPremium;
    }
}

