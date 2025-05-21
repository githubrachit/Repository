package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.*;
import com.mli.mpro.utils.Utility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.mli.mpro.productRestriction.util.AppConstants.*;
import static com.mli.mpro.utils.Utility.*;

@Service
public class CISDocumentMapper {

    @Autowired
    private BaseMapper baseMapper;
    private static final Logger logger = LoggerFactory.getLogger(CISDocumentMapper.class);

    public Context setCISData (ProposalDetails proposalDetails) throws UserHandledException{
        logger.info("Stared Mapping for CIS Document for transactionId {}",proposalDetails.getTransactionId());
        Context context = new Context();
        Map<String, Object> dataForDocument = new HashMap<>();
        String productName="";
        String variant="";
        String UIN = "";
        String policyNumber = "";
        String typeOfInsurance ="";
        String instalmentPremium="";
        String premiumPaymentMode = "";
        String sumAssuredOnDeath = "";
        String sumAssuredOnMaturity = "";
        String policyTerm = "";
        String premiumPaymentTerm = "";
        boolean riderOpted = false;
        boolean criticalIllnessRiderOpted = false;
        boolean termBoosterOpted = false;
        boolean accidentalDeathBenefitOpted = false;
        boolean accidentalDisabilityOpted = false;
        boolean payorBenefitOpted = false;
        boolean partnerCareRiderOpted= false;
        boolean wopPlusRiderOpted= false;
        boolean criticalIllnessAndDisabilityRiderOpted= false;
        boolean accidentalCoverRiderOpted= false;
        boolean addRiderOpted = false;
        boolean termPlusRiderOpted = false;
        String numberOfDays = "";
        String tatDays = "";
        String place ="";
        String date = "";
        String turnAroundTime ="";
        String annuityOption= "";
        String incomeBenefit= "";
        String incomeBenefitPeriod= "";
        String incomeBenefitPayoutFrequency= "";
        String incomeBenefitDefermentPeriod= "";
        String customerDiscount ="";
        String incomeCover="";
        String sumAssuredOnDeathAtInception="";
        String monthlyIncomeChosenAtInception="";

        List<RiderDetails> riderDetails = proposalDetails.getProductDetails().get(0).getProductInfo().getRiderDetails();
        ProductIllustrationResponse productIllustrationDetails = proposalDetails.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse();
        ProductInfo productInfo = proposalDetails.getProductDetails().get(0).getProductInfo();
        String productId = productInfo.getProductId();
        String formType = proposalDetails.getApplicationDetails().getFormType();
        String schemeType = proposalDetails.getApplicationDetails().getSchemeType();
        PartyInformation partyInformation = (AppConstants.FORM3.equalsIgnoreCase(formType) && !Utility.schemeBCase(schemeType))
                ? proposalDetails.getPartyInformation().stream()
                .filter(Objects::nonNull)
                .filter(partyInfo -> AppConstants.COMPANY.equalsIgnoreCase(partyInfo.getPartyType()))
                .findFirst()
                .orElse(null)
                : proposalDetails.getPartyInformation().get(0);
        //Mapping Keys
       date = getDeclarationDate(proposalDetails);
       UIN = productIllustrationDetails!=null? productIllustrationDetails.getUin(): UIN;
       policyNumber = (proposalDetails != null && proposalDetails.getApplicationDetails() != null) ? proposalDetails.getApplicationDetails().getPolicyNumber():policyNumber;
       typeOfInsurance = AppConstants.TYPE_OF_INSURANCE;
       sumAssuredOnMaturity = Optional.ofNullable(productIllustrationDetails).map(ProductIllustrationResponse :: getSumAssured).map((val) -> String.format("%.2f",val)).orElse(AppConstants.BLANK);
       sumAssuredOnMaturity = getSumAssuredOnMaturity(productInfo,sumAssuredOnMaturity);
       instalmentPremium = (productIllustrationDetails != null && String.valueOf(productIllustrationDetails.getModalPremium())!= null)
                ? String.format("%.2f", productIllustrationDetails.getModalPremium())
                : instalmentPremium;
       premiumPaymentMode = (productInfo!=null && productInfo.getModeOfPayment()!=null) ?productInfo.getModeOfPayment():premiumPaymentMode;
       sumAssuredOnDeath = (productIllustrationDetails != null && String.valueOf(productIllustrationDetails.getSumAssured())!= null)
                ? String.format("%.2f",productIllustrationDetails.getSumAssured())
                : sumAssuredOnDeath;
       policyTerm = (productInfo!=null && productInfo.getPolicyTerm()!=null)? productInfo.getPolicyTerm():policyTerm;
       premiumPaymentTerm = (productInfo!=null&& productInfo.getPremiumPaymentTerm()!=null)? productInfo.getPremiumPaymentTerm():premiumPaymentTerm;
       productName = getPlanName(productInfo);
       variant = Optional.ofNullable(productInfo).map(ProductInfo :: getVariant).orElse(AppConstants.BLANK);
       numberOfDays= Utility.determineNumberOfDays(premiumPaymentMode);
       tatDays=AppConstants.TAT_DAYS;
       turnAroundTime = AppConstants.TRUN_AROUND_TIME;
       place = getPlace(partyInformation);
       customerDiscount = productInfo.getIsWellnessProgram()!=null ? StringUtils.capitalize(productInfo.getIsWellnessProgram().toLowerCase())
               :AppConstants.BLANK;

       boolean supRiderOpted = false;
        //Mapping RiderDetails
        if (riderDetails != null && riderDetails.stream().anyMatch(rider -> rider.isRiderRequired())) {
            riderOpted = true;
            criticalIllnessRiderOpted = riderDetails.stream().anyMatch(rider -> rider.getRiderInfo().equalsIgnoreCase(AppConstants.CI_RIDER_ULIP) || rider.getRiderInfo().equalsIgnoreCase(AppConstants.AXIS_CI_RIDER_ULIP));
            payorBenefitOpted = riderDetails.stream().anyMatch(rider -> rider.getRiderInfo().equalsIgnoreCase(AppConstants.RIDERVAR_PB));
            termBoosterOpted = riderDetails.stream().anyMatch(rider -> rider.getRiderInfo().equalsIgnoreCase(AppConstants.RIDERVAR_TBATI));
            accidentalDeathBenefitOpted = riderDetails.stream().anyMatch(rider -> rider.getRiderInfo().equalsIgnoreCase(AppConstants.RIDERVAR_ADB));
            accidentalDisabilityOpted = riderDetails.stream().anyMatch(rider -> rider.getRiderInfo().equalsIgnoreCase(AppConstants.RIDERVAR_ATPD));
            partnerCareRiderOpted= riderDetails.stream().anyMatch(rider -> rider.getRiderInfo().equalsIgnoreCase(AppConstants.PARTNER) || rider.getRiderInfo().equalsIgnoreCase(AppConstants.AXIS_PARTNER));
            // Check if any rider other than critical illness is selected
            supRiderOpted = payorBenefitOpted || termBoosterOpted || accidentalDeathBenefitOpted || accidentalDisabilityOpted;
            wopPlusRiderOpted = riderDetails.stream().anyMatch(rider -> rider.getRiderInfo().equalsIgnoreCase(AppConstants.WOP) || rider.getRiderInfo().equalsIgnoreCase(AppConstants.AXIS_WOP));
            criticalIllnessAndDisabilityRiderOpted = riderDetails.stream().anyMatch(rider -> rider.getRiderInfo().equalsIgnoreCase(AppConstants.CI_RIDER));
            addRiderOpted = riderDetails.stream().anyMatch(rider -> rider.getRiderInfo().equalsIgnoreCase(AppConstants.ACD) || rider.getRiderInfo().equalsIgnoreCase(AppConstants.AXIS_ACD));
            termPlusRiderOpted = riderDetails.stream().anyMatch(rider -> rider.getRiderInfo().equalsIgnoreCase(AppConstants.TERM) ||  rider.getRiderInfo().equalsIgnoreCase(AppConstants.AXIS_TERM));
            //accidentalCoverRiderOpted = riderDetails.stream().anyMatch(rider -> rider.getRiderInfo().equalsIgnoreCase(AppConstants.ACD_COVER));
        }
        if(productInfo!=null && productInfo.getProductId()!=null && !productInfo.getProductId().isEmpty() && AppConstants.SPP_ID.equalsIgnoreCase(productInfo.getProductId())){
           policyTerm = setPolicyTermForSPP(productInfo);
            sumAssuredOnDeath = (productIllustrationDetails != null && String.valueOf(productIllustrationDetails.getGuaranteedDeathBenefit())!= null)
                    ? String.format("%.2f",Double.valueOf(productIllustrationDetails.getGuaranteedDeathBenefit()))
                    : AppConstants.BLANK;
        }
        if(productInfo!=null && productInfo.getProductId()!=null && !productInfo.getProductId().isEmpty() && AppConstants.SJB.equalsIgnoreCase(productInfo.getProductId())){
            instalmentPremium = productInfo.getPremiumType()!=null ? productInfo.getPremiumType():AppConstants.BLANK;
        }
        annuityOption = productInfo != null && productInfo.getAnnuityOption() != null && !productInfo.getAnnuityOption().isEmpty()? productInfo.getAnnuityOption():AppConstants.BLANK;

        if(AppConstants.CIS_SUM_ASSURED_DEATH_PRODUCT.contains(Optional.ofNullable(productInfo).map(ProductInfo :: getProductId).orElse(""))){
            sumAssuredOnDeath = Optional.ofNullable(productIllustrationDetails).map(ProductIllustrationResponse :: getGuaranteedDeathBenefit).orElse(AppConstants.BLANK);
        }
		if (AppConstants.SWIP.equals(productId) || AppConstants.SAP_PRODUCT_ID.equals(productId)) {
			if (productIllustrationDetails != null && productIllustrationDetails.getBiSumAssuredOnDeath() != null) {
				double biSumAssuredOnDeath = Double.parseDouble(productIllustrationDetails.getBiSumAssuredOnDeath());
				sumAssuredOnDeath = String.format("%.2f", biSumAssuredOnDeath);
			} else {
				sumAssuredOnDeath = AppConstants.BLANK;
			}
		}
        instalmentPremium = getInstalmentPremium(productInfo,instalmentPremium);

        String wopRiderUin= getUIN(productIllustrationDetails,AppConstants.AXIS_WOP);
        String cidrRiderUin= getUIN(productIllustrationDetails,AppConstants.CI_RIDER);
        String addRiderUin= getUIN(productIllustrationDetails,AppConstants.AXIS_ACD);
        String termplusRiderUin= getUIN(productIllustrationDetails,AppConstants.AXIS_TERM);
        String ciDisabilitySecureRiderUIN= getUIN(productIllustrationDetails,AppConstants.CIDS_RIDER_NAME);
        String suprRiderUin= getUIN(productIllustrationDetails,AppConstants.AXIS_SUPR_RIDER_NAME);
        incomeBenefit = productIllustrationDetails!=null ? Utility.ifEmptyThenNA(productIllustrationDetails.getAnnualIncomeAmt()): NA;
        incomeBenefitPeriod= Utility.ifEmptyThenNA(productInfo.getIncomePeriod());
        incomeBenefitPayoutFrequency= Utility.ifEmptyThenNA(productInfo.getIncomePayoutFrequency());
        incomeBenefitDefermentPeriod= Utility.ifEmptyThenNA(productInfo.getDefermentPeriod());
        incomeCover = productInfo!=null && org.springframework.util.StringUtils.hasText(productInfo.getIncomeCover()) ? productInfo.getIncomeCover() : NA;
        sumAssuredOnDeathAtInception = (productIllustrationDetails != null && org.springframework.util.StringUtils.hasText(productIllustrationDetails.getBiSumAssuredOnDeath()) && Double.parseDouble(productIllustrationDetails.getBiSumAssuredOnDeath())>0)
                ? String.format("%.2f", Double.parseDouble(productIllustrationDetails.getBiSumAssuredOnDeath()))
                : NA;
        monthlyIncomeChosenAtInception = (productIllustrationDetails != null && org.springframework.util.StringUtils.hasText(productIllustrationDetails.getMonthlyIncomeChosenAtInception()) && Double.parseDouble(productIllustrationDetails.getMonthlyIncomeChosenAtInception())>0)
                ? String.format("%.2f", Double.parseDouble(productIllustrationDetails.getMonthlyIncomeChosenAtInception()))
                : NA;

        setDataForSurvivalBenefit(dataForDocument,productInfo);


        dataForDocument.put("productName",productName);
        dataForDocument.put("variant",variant);
        dataForDocument.put("UIN",UIN);
        dataForDocument.put("policyNumber",policyNumber);
        dataForDocument.put("typeOfInsurance",typeOfInsurance);
        dataForDocument.put("instalmentPremium",instalmentPremium);
        dataForDocument.put("premiumPaymentMode",premiumPaymentMode);
        dataForDocument.put("sumAssuredOnDeath",sumAssuredOnDeath);
        dataForDocument.put("sumAssuredOnMaturity",sumAssuredOnMaturity);
        dataForDocument.put("policyTerm",policyTerm);
        dataForDocument.put("premiumPaymentTerm",premiumPaymentTerm);
        dataForDocument.put("riderOpted",riderOpted);
        dataForDocument.put("supRiderOpted",supRiderOpted);
        dataForDocument.put("criticalIllnessRiderOpted",criticalIllnessRiderOpted);
        dataForDocument.put("termBoosterOpted",termBoosterOpted);
        dataForDocument.put("accidentalDeathBenefitOpted",accidentalDeathBenefitOpted);
        dataForDocument.put("accidentalDisabilityOpted",accidentalDisabilityOpted);
        dataForDocument.put("payorBenefitOpted",payorBenefitOpted);
        dataForDocument.put("partnerCareRiderOpted",partnerCareRiderOpted);
        dataForDocument.put("wopPlusRiderOpted",wopPlusRiderOpted);
        dataForDocument.put("criticalIllnessAndDisabilityRiderOpted",criticalIllnessAndDisabilityRiderOpted);
        dataForDocument.put("accidentalCoverRiderOpted",accidentalCoverRiderOpted);
        dataForDocument.put("addRiderOpted",addRiderOpted);
        dataForDocument.put("termPlusRiderOpted",termPlusRiderOpted);
        dataForDocument.put("numberOfDays",numberOfDays);
        dataForDocument.put("tatDays",tatDays);
        dataForDocument.put("turnAroundTime",turnAroundTime);
        dataForDocument.put("place",place);
        dataForDocument.put("date",date);
        dataForDocument.put("annuityOption",annuityOption);
        dataForDocument.put("wopRiderUin",wopRiderUin);
        dataForDocument.put("cidrRiderUin",cidrRiderUin);
        dataForDocument.put("addRiderUin",addRiderUin);
        dataForDocument.put("termplusRiderUin",termplusRiderUin);
        dataForDocument.put("ciDisabilitySecureRiderUIN",ciDisabilitySecureRiderUIN);
        dataForDocument.put("suprRiderUin",suprRiderUin);
        dataForDocument.put("incomeBenefit",incomeBenefit);
        dataForDocument.put("incomeBenefitPeriod",incomeBenefitPeriod);
        dataForDocument.put("incomeBenefitPayoutFrequency",incomeBenefitPayoutFrequency);
        dataForDocument.put("incomeBenefitDefermentPeriod",incomeBenefitDefermentPeriod);
        dataForDocument.put("customerDiscount",customerDiscount);
        dataForDocument.put("incomeCover",incomeCover);
        dataForDocument.put("sumAssuredOnDeathAtInception",sumAssuredOnDeathAtInception);
        dataForDocument.put("monthlyIncomeChosenAtInception",monthlyIncomeChosenAtInception);

        if(ANNUITY_PRODUCTS.contains(productId)) {
            mapDataForAnnuity(dataForDocument, proposalDetails);
        }

        context.setVariables(dataForDocument);
        return context;

    }

    private String getSumAssuredOnMaturity(ProductInfo productInfo, String sumAssuredOnMaturity) {
        try {
            // Check if the product ID is SSP_PRODUCT_ID and productIllustrationResponse is not null
            if ((AppConstants.SSP_PRODUCT_ID.equalsIgnoreCase(productInfo.getProductId()) || AppConstants.SWP_PRODUCT_ID.equalsIgnoreCase(productInfo.getProductId())|| AppConstants.SEWA_PRODUCT_ID.equalsIgnoreCase(productInfo.getProductId())||STPP_PRODUCT_ID.equalsIgnoreCase(productInfo.getProductId()))
                    && productInfo.getProductIllustrationResponse() != null) {
                // Check if returnOfPremium is "yes"
                if (AppConstants.YES_LOWERCASE.equalsIgnoreCase(productInfo.getPremiumBackOption()) || AppConstants.SWP_PRODUCT_ID.equalsIgnoreCase(productInfo.getProductId())|| SEWA_PRODUCT_ID.equalsIgnoreCase(productInfo.getProductId())||STPP_PRODUCT_ID.equalsIgnoreCase(productInfo.getProductId())) {
                    // Bind the maturityBenefit value if returnOfPremium is "yes"
                    String maturityBenefit = zeroIfNullOrEmpty(productInfo.getProductIllustrationResponse().getMaturityBenefit());
                    sumAssuredOnMaturity = (Double.parseDouble(maturityBenefit) > 0)
                            ? String.format("%.2f", Double.valueOf(maturityBenefit))
                            : NA;
                } else {
                    // Set the value to "Not Applicable" if returnOfPremium is not "yes"
                    sumAssuredOnMaturity = AppConstants.NOT_APPLICABLE;
                }
            }
        } catch (Exception ex) {
            logger.error("Exception occured while mapping the sumAssuredOnMaturity for ProductID {}", productInfo.getProductId(), ex);
        }
        return sumAssuredOnMaturity;
    }

    private String getInstalmentPremium(ProductInfo productInfo, String instalmentPremium) {

        try {
            double basePlanPremium = 0;
            double basePlanGst = 0;
            double maternityRiderPremium = 0;
            double maternityRiderGST = 0;
            if (productInfo != null && AppConstants.CIS_PRODUCT_TOTAL_REQUIRED_MODAL_PREMIUM.contains(productInfo.getProductId())) {
                basePlanPremium = productInfo.getProductIllustrationResponse() != null ? productInfo.getProductIllustrationResponse().getModalPremium() : Double.parseDouble(instalmentPremium);
                basePlanGst = productInfo.getProductIllustrationResponse() != null ? Double.parseDouble(productInfo.getProductIllustrationResponse().getBasePlanGST()) : 0.0;
                maternityRiderPremium = productInfo.getProductIllustrationResponse()!=null && org.springframework.util.StringUtils.hasText(productInfo.getProductIllustrationResponse().getMaternityRiderPremiumSummary()) ? Double.parseDouble(productInfo.getProductIllustrationResponse().getMaternityRiderPremiumSummary()) : 0.0;
                maternityRiderGST = productInfo.getProductIllustrationResponse()!=null && org.springframework.util.StringUtils.hasText(productInfo.getProductIllustrationResponse().getMaternityRiderGST()) ? Double.parseDouble(productInfo.getProductIllustrationResponse().getMaternityRiderGST()) : 0.0;
                instalmentPremium = String.format("%.2f", roundOffValue(basePlanPremium + basePlanGst + maternityRiderPremium + maternityRiderGST));
            }
        } catch (Exception ex) {
            logger.error("Exception occured while mapping the installementPremium for ProductID {}", productInfo.getProductId());
        }
        return instalmentPremium;
    }
    private void setDataForSurvivalBenefit(Map<String, Object> dataForDocument, ProductInfo productInfo) {
        String survivalBenefitPeriod = Utility.nullSafe(productInfo.getSurvivalBenefitPeriod());
        String survivalBenefit =  org.springframework.util.StringUtils.hasText(productInfo.getProductIllustrationResponse().getTotalSurvivalBenefit()) && Double.parseDouble(productInfo.getProductIllustrationResponse().getTotalSurvivalBenefit())>0 ? String.format("%.2f", Double.valueOf(zeroIfNullOrEmpty(productInfo.getProductIllustrationResponse().getTotalSurvivalBenefit()))) : NA;
        dataForDocument.put("survivalBenefitPeriod",survivalBenefitPeriod);
        dataForDocument.put("survivalBenefit",survivalBenefit);
    }
    private String getUIN(ProductIllustrationResponse productIllustrationResponse, String riderName) {
        String uin = AppConstants.BLANK;
        RiderUIN riderUIN = productIllustrationResponse.getRiderUIN();
        if (productIllustrationResponse == null || riderName == null || riderName.isEmpty() || riderUIN == null) {
            return uin;
        }
        switch (riderName) {
            case AppConstants.AXIS_WOP:
                String wopRiderUIN = riderUIN.getWopPlusRiderUIN();
                return wopRiderUIN != null ? wopRiderUIN : "";
            case AppConstants.CI_RIDER:
                String ciDisabilityRiderUIN = riderUIN.getCiDisabilityRiderUIN();
                return ciDisabilityRiderUIN != null ? ciDisabilityRiderUIN : "";
            case AppConstants.AXIS_ACD:
                String addRiderUIN = riderUIN.getAddRiderUIN();
                return addRiderUIN != null ? addRiderUIN : "";
            case AppConstants.AXIS_TERM:
                String termPlusRiderUIN = riderUIN.getTermPlusRiderUIN() != null ? riderUIN.getTermPlusRiderUIN() : "";
                return termPlusRiderUIN;
            case AppConstants.CIDS_RIDER_NAME:
                String cidsRiderUIN = riderUIN.getCiDisabilitySecureRiderUIN();
                return cidsRiderUIN!=null? cidsRiderUIN:"";
            case AppConstants.AXIS_SUPR_RIDER_NAME:
                String suprRiderUIN = riderUIN.getSuprRiderUIN();
                return suprRiderUIN!=null? suprRiderUIN:"";
            default:
                return AppConstants.BLANK;
        }
    }

    private void mapDataForAnnuity(Map<String, Object> contextMap, ProposalDetails proposalDetails) {
        if(!ANNUITY_PRODUCTS.contains(proposalDetails.getProductDetails().get(0).getProductInfo().getProductId())) return;
        ProductInfo productInfo = proposalDetails.getProductDetails().get(0).getProductInfo();
        ProductIllustrationResponse productIllustrationResponse = productInfo.getProductIllustrationResponse()!=null ? productInfo.getProductIllustrationResponse() : new ProductIllustrationResponse();
        String instalmentPremium = String.format("%.2f",(Double.valueOf(zeroIfNullOrEmpty(productIllustrationResponse.getTotalRequiredModalPremium()))));
        String biSumAssuredOnDeath = getBiSumAssuredOnDeath(productIllustrationResponse);
        String annuityType = emptyIfNull(productInfo.getAnnuityType());
        String annuityOption = emptyIfNull(productInfo.getAnnuityOption());//Mapped above
        String premiumType = emptyIfNull(productInfo.getPremiumType());
        String returnOfPremium = emptyIfNull(productInfo.getReturnOfPremium());
        String increasingAnnuityFrequency = emptyIfNull(productInfo.getIncreasingAnnuityFrequency());
        String increasingAnnuityPercentage = emptyIfNull(productInfo.getIncreasingAnnuityPercentage());
        String deferredPeriod = emptyIfNull(productInfo.getDeferredPeriod());
        String returnOfPremiumPercentage = emptyIfNull(productInfo.getReturnOfPremiumPercentage());
        String proportionOfAnnuityLastSurvivor = emptyIfNull(productInfo.getProportionOfAnnuityLastSurvivor());
        String earlyROPPercentage = emptyIfNull(productInfo.getEarlyROPPercentage());
        String milestoneAge = emptyIfNull(productInfo.getMilestoneAge());
        String guaranteeAnnuityPeriod = emptyIfNull(productInfo.getGuaranteeAnnuityPeriod());
        String defermentPeriodMonth = zeroIfNullOrEmpty(productInfo.getDefermentPeriodMonth());
        int defermentPeriodInMonth =  Integer.parseInt(defermentPeriodMonth);
        defermentPeriodMonth = defermentPeriodMonth.concat(" ").concat(Integer.valueOf(defermentPeriodMonth) < 2 ? "month" : "months");
        String defermentPeriod = zeroIfNullOrEmpty(productInfo.getDefermentPeriod());
        defermentPeriod = defermentPeriod.concat(" ").concat(Integer.valueOf(defermentPeriod) < 2 ? "year" : "years");
        contextMap.put("defermentPeriodInMonth", defermentPeriodInMonth);
        contextMap.put("instalmentPremium", instalmentPremium);
        contextMap.put("sumAssuredOnDeath", biSumAssuredOnDeath);
        contextMap.put("annuityType", annuityType);
        contextMap.put("annuityOption", annuityOption);
        contextMap.put("premiumType",premiumType);
        contextMap.put("returnOfPremium",returnOfPremium);
        contextMap.put("returnOfPremiumPercentage",returnOfPremiumPercentage);
        contextMap.put("increasingAnnuityFrequency",increasingAnnuityFrequency);
        contextMap.put("increasingAnnuityPercentage",increasingAnnuityPercentage);
        contextMap.put("proportionOfAnnuityLastSurvivor", proportionOfAnnuityLastSurvivor);
        contextMap.put("earlyROPPercentage",earlyROPPercentage);
        contextMap.put("milestoneAge",milestoneAge);
        contextMap.put("guaranteeAnnuityPeriod",guaranteeAnnuityPeriod);
        contextMap.put("deferredPeriod",deferredPeriod);
        contextMap.put("defermentPeriodMonth",defermentPeriodMonth);
        contextMap.put("defermentPeriod",defermentPeriod);
        // Add Annuity Types
        contextMap.put("IMMEDIATE_ANNUITY", IMMEDIATE_ANNUITY_OPTION);
        contextMap.put("DEFERRED_ANNUITY", DEFERRED_ANNUITY_OPTION);
        contextMap.put("INCREASING_IA",INCREASING_IA);
        contextMap.put("IA_EARLY_ROP",IA_EARLY_ROP);
        contextMap.put("IA_LAST_SURVIVOR",IA_LAST_SURVIVOR);
        contextMap.put("IA_LIFE_THEREAFTER",IA_LIFE_THEREAFTER);
        contextMap.put("FOR_LIFE",FOR_LIFE);
        contextMap.put("TILL_DEFERMENT_PERIOD",TILL_DEFERMENT_PERIOD);

        if(GLIP_PRODUCT_ID.equalsIgnoreCase(productInfo.getProductId()) || SGPP_ID.equalsIgnoreCase(productInfo.getProductId())) {
            String deathBenefitForAnnuity = productInfo.getDeathBenefitForAnnuity();

            contextMap.put("deathBenefitForAnnuity",deathBenefitForAnnuity);
        }
    }
    public String getBiSumAssuredOnDeath(ProductIllustrationResponse productIllustrationResponse) {
        String biSumAssuredStr = String.format("%.2f",(Double.valueOf(zeroIfNullOrEmpty(productIllustrationResponse.getBiSumAssuredOnDeath()))));
        int biSumAssuredInt = (int) Double.parseDouble(biSumAssuredStr);
        if (biSumAssuredInt <= 0) {
            return AppConstants.NOT_APPLICABLE;
        }
        return biSumAssuredStr;
    }
    private String determineNumberOfDays(String premiumPaymentMode) {
        if(AppConstants.MONTHLY.equalsIgnoreCase(premiumPaymentMode)){
            return AppConstants.GRACE_PERIOD_MONTHLY;
        } else if (!AppConstants.BLANK.equalsIgnoreCase(premiumPaymentMode) && !AppConstants.SINGLE.equalsIgnoreCase(premiumPaymentMode)) {
            return AppConstants.GRACE_PERIOD;
        } else {
            return AppConstants.NA;
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
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone(AppConstants.APP_TIMEZONE));
        Date generatedOtp = (proposalDetails != null &&
                proposalDetails.getPosvDetails() != null &&
                proposalDetails.getPosvDetails().getPosvStatus() != null &&
                proposalDetails.getPosvDetails().getPosvStatus().getGeneratedOTPDate()!=null)
                ? proposalDetails.getPosvDetails().getPosvStatus().getGeneratedOTPDate()
                : null;

        String finalDisabilityDeclarationDate = generatedOtp != null ? format.format(generatedOtp) : StringUtils.EMPTY;
       return finalDisabilityDeclarationDate;
    }
    private String getPlanName (ProductInfo productInfo){
        String productName = AppConstants.BLANK;
        if (productInfo != null) {
            if (AppConstants.FPS_PRODUCT_ID.equalsIgnoreCase(productInfo.getProductId())) {
                productName = AppConstants.FWP_PRODUCTNAME;
            } else if (productInfo.getProductName() != null) {
                productName = productInfo.getProductName();
            }
        }
        return productName;
    }
    private String setPolicyTermForSPP(ProductInfo productInfo) {
        return (productInfo != null && productInfo.getAnnuityOption() != null && !productInfo.getAnnuityOption().isEmpty())
                ?
                (AppConstants.SINGLE_LIFE.equalsIgnoreCase(productInfo.getAnnuityOption())
                ? AppConstants.SINGLE_LIFE_PT
                : AppConstants.JOINT_LIFE.equalsIgnoreCase(productInfo.getAnnuityOption())
                ? AppConstants.JOINT_LIFE_PT
                : AppConstants.BLANK)
                : AppConstants.BLANK;
    }
}
