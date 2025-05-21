package com.mli.mpro.proposal.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

/**
 * This class is used to store the response being received from Run illustration
 * response of exising MLI service (LE). This response basically be used in
 * PolicyProcessing service of MLI (TPP).
 *
 *
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductIllustrationResponse {

    @JsonProperty("coverageTerm")
    private String coverageTerm;

    @JsonProperty("coverMultiple")
    private String coverMultiple;

    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("sumAssured")
    private double sumAssured;

    @JsonProperty("modeOfPayment")
    private String modeOfPayment;

    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("modalPremium")
    private double modalPremium;

    @JsonProperty("premiumPaymentTerm")
    private String premiumPaymentTerm;

    @JsonProperty("term")
    private String term;

    @JsonProperty("requiredModalPremium")
    private String requiredModalPremium;

    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("totalRequiredModalPremium")
    private String totalRequiredModalPremium;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("serviceTax")
    private String serviceTax;

    @JsonProperty("afyp")
    private String afyp;

    @JsonProperty("bonusOption")
    private String bonusOption;

    @JsonProperty("effectiveDate")
    private Date effectiveDate;

    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("riderSumAssured")
    private double riderSumAssured;

    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("riderModalPremium")
    private double riderModalPremium;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("riderServiceTax")
    private String riderServiceTax;

    @JsonProperty("riderInsuredDetails")
    private String riderInsuredDetails;

    @JsonProperty("atp")
    private double atp;

    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("initialPremiumPaid")
    private String initialPremiumPaid;

    @JsonProperty("guaranteedDeathBenefit")
    private String guaranteedDeathBenefit;

    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("annualPremium")
    private String annualPremium;

    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("baseModalPremium")
    private String baseModalPremium;

    @JsonProperty("serviceTaxInclCessBaseModalPrem")
    private String serviceTaxInclCessBaseModalPrem;

    @JsonProperty("firstYearADDRiderPremiumSummary")
    private String firstYearADDRiderPremiumSummary;

    @JsonProperty("firstYearTermPlusRiderPremiumSummary")
    private String firstYearTermPlusRiderPremiumSummary;

    @JsonProperty("firstYearWOPPlusRiderPremiumSummary")
    private String firstYearWOPPlusRiderPremiumSummary;

    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("addRiderSumAssured")
    private String addRiderSumAssured;

    @JsonProperty("addRiderTerm")
    private String addRiderTerm;

    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("termPlusRiderSumAssured")
    private String termPlusRiderSumAssured;

    @JsonProperty("termPlusRiderTerm")
    private String termPlusRiderTerm;

    @JsonProperty("wopPlusRiderTerm")
    private String wopPlusRiderTerm;

    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("wopPlusRiderSumAssured")
    private String wopPlusRiderSumAssured;

    @JsonProperty("firstYearPartnerCareRiderPremiumSummary")
    private String firstYearPartnerCareRiderPremiumSummary;

    @JsonProperty("partnerCareRiderTerm")
    private String partnerCareRiderTerm;

    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("partnerCareRiderSumAssured")
    private String partnerCareRiderSumAssured;

    @JsonProperty("firstYearAccidentCoverRiderPremiumSummary")
    private String firstYearAccidentCoverRiderPremiumSummary;

    @JsonProperty("accidentCoverRiderTerm")
    private String accidentCoverRiderTerm;

    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("accidentCoverRiderSumAssured")
    private String accidentCoverRiderSumAssured;

    @JsonProperty("firstYearAcceleratedCriticalIllnessRiderPremiumSummary")
    private String firstYearAcceleratedCriticalIllnessRiderPremiumSummary;

    @JsonProperty("acceleratedCriticalIllnessRiderTerm")
    private String acceleratedCriticalIllnessRiderTerm;

    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("acceleratedCriticalIllnessRiderSumAssured")
    private String acceleratedCriticalIllnessRiderSumAssured;

    @JsonProperty("employeeDiscount")
    private String employeeDiscount;

    @JsonProperty("agentDiscount")
    private String agentDiscount;

    @JsonProperty("webDiscount")
    private String webDiscount;

    @JsonProperty("customerDiscount")
    private String customerDiscount;

    @JsonProperty("deathBenefit")
    private String deathBenefit;

    @JsonProperty("incomePeriod")
    private String incomePeriod;

    @JsonProperty("premiumBackOption")
    private String premiumBackOption;

    @JsonProperty("lifeStageEventBenefit")
    private String lifeStageEventBenefit;

    @JsonProperty("guaranteedSumAssured")
    private String guaranteedSumAssured;

    @JsonProperty("basePlanGST")
    private String basePlanGST;

    @JsonProperty("accidentalCoverRiderGST")
    private String accidentalCoverRiderGST;

    @JsonProperty("accidentalCriticalIllnessRiderGST")
    private String accidentalCriticalIllnessRiderGST;

    @JsonProperty("wopPlusRiderGST")
    private String wopPlusRiderGST;

    @JsonProperty("coverageString")
    private String coverageString;

    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("CABRiderPremium")
    private String CABRiderPremium;

    @JsonProperty("CABRiderTerm")
    private String CABRiderTerm;

    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("CABRiderSumAssured")
    private String CABRiderSumAssured;

    @JsonProperty("CABRiderGST")
    private String CABRiderGST;

    @JsonProperty("initialPremiumforSales")
    private String initialPremiumforSales;

    private Date bIGeneratedDate;
    
    //FUL2-7517 SWP in mPRO - PF Changes -IncomePayout for Long & Short term income
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("annualIncomeAmt")
    private String annualIncomeAmt;
    // FUL2-6977 Smart Wealth Plan in mPRO - LE and UI Changes
 	@JsonProperty("incomePayoutFrequency")
 	private String incomePayoutFrequency;
 	// FUL2-7788 Covid rider
    @Sensitive(MaskType.AMOUNT)
 	@JsonProperty("covideRiderPremium")
	private String covideRiderPremium;

    @Sensitive(MaskType.AMOUNT)
	@JsonProperty("covideRiderSumAssured")
	private String covideRiderSumAssured;

	@JsonProperty("covideRiderTerm")
	private String covideRiderTerm;
    //FUL2-11755 COVID Rider GST: This field is added as this is required to be printed on PF
    @JsonProperty("covideRiderGST")
    private String covideRiderGST;

	//FUL2-9523_Critical_Illness_Plus_Rider
    @JsonProperty("firstYearSmartHealthPlusPremiumSummary")
    private String firstYearSmartHealthPlusPremiumSummary;
    @JsonProperty("smartHealthPlusRiderTerm")
    private String smartHealthPlusRiderTerm;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("smartHealthPlusRiderSumAssured")
    private String smartHealthPlusRiderSumAssured;
    @JsonProperty("smartHealthPlusRiderGST")
    private String smartHealthPlusRiderGST;
    @JsonProperty("termPlusRiderGST")
    private String termPlusRiderGST;
    @JsonProperty("addRiderGST")
    private String addRiderGST;
    @JsonProperty("smartHealthPlusRiderVariant")
    private String smartHealthPlusRiderVariant;
    @JsonProperty("smartHealthPlusRiderPPT")
    private String smartHealthPlusRiderPPT;

    //FUL2-16865 Fields added for GLIP Product
    @JsonProperty("annuityPurchasedFrom")
    private String annuityPurchasedFrom;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("guaranteedIncomeAmt")
    private String guaranteedIncomeAmt;
    @JsonProperty("dateOfQuotation")
    private Date dateOfQuotation;
    @JsonProperty("annuityStartDate")
    private Date annuityStartDate;
    @JsonProperty("returnOfPremium")
    private String returnOfPremium;
    
  //FUL2-46299
    @JsonProperty("allocatedPremium")
    private Double allocatedPremium;
    @JsonProperty("totalBenefit")
    private Double totalBenefit;
    @JsonProperty("cumulativeAnnualPremium")
    private Double cumulativeAnnualPremium;
    @JsonProperty("guaranteedMaturityValue")
    private Double guaranteedMaturityValue;
    @JsonProperty("nonGuaranteedMaturityValue_4")
    private Integer nonGuaranteedMaturityValue_4;
    @JsonProperty("nonGuaranteedMaturityValue_8")
    private Integer nonGuaranteedMaturityValue_8;
    @JsonProperty("maturityBenifit4pers")
    private Integer maturityBenifit4pers;
    @JsonProperty("maturityBenifit8pers")
    private Integer maturityBenifit8pers;

  //FUL2-113645
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("jLSumAssured")
    private double jlSumAssured;

    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("jLPremiumWithoutGST")
    private double jlPremiumWithoutGST;

    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("jLPremiumWithGST")
    private String jlPremiumWithGST;

    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("jLPremiumTotalRenewalWithGST")
    private String jlPremiumTotalRenewalWithGST;

    @JsonProperty("jLPolicyTerm")
    private String jlPolicyTerm;

    @JsonProperty("jLPremiumPaymentTerm")
    private String jlPremiumPaymentTerm;

    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("jLServiceTax")
    private String jlServiceTax;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("swipWopPlusRiderSumAssured")
    private String swipWopPlusRiderSumAssured;
    //TBATI
    @JsonProperty("SUPRiderTBATITerm")
    private String SUPRiderTBATITerm;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("SUPRiderTBATISumAssured")
    private String SUPRiderTBATISumAssured;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("SUPRiderTBATIRiderGST")
    private String SUPRiderTBATIRiderGST;
    @JsonProperty("SUPRiderTBATIVariant")
    private String SUPRiderTBATIVariant;
    @JsonProperty("SUPRiderTBATIPremiumPayingTerm")
    private String SUPRiderTBATIPremiumPayingTerm;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("firstYearSUPRiderTBATIPremiumSummary")
    private String firstYearSUPRiderTBATIPremiumSummary;
    //ADB
    @JsonProperty("SUPRiderADBTerm")
    private String SUPRiderADBTerm;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("SUPRiderADBSumAssured")
    private String SUPRiderADBSumAssured;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("SUPRiderADBRiderGST")
    private String SUPRiderADBRiderGST;
    @JsonProperty("SUPRiderADBVariant")
    private String SUPRiderADBVariant;
    @JsonProperty("SUPRiderADBPremiumPayingTerm")
    private String SUPRiderADBPremiumPayingTerm;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("firstYearSUPRiderADBPremiumSummary")
    private String firstYearSUPRiderADBPremiumSummary;
    //ATPD
    @JsonProperty("SUPRiderATPDTerm")
    private String SUPRiderATPDTerm;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("SUPRiderATPDSumAssured")
    private String SUPRiderATPDSumAssured;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("SUPRiderATPDRiderGST")
    private String SUPRiderATPDRiderGST;
    @JsonProperty("SUPRiderATPDVariant")
    private String SUPRiderATPDVariant;
    @JsonProperty("SUPRiderATPDPremiumPayingTerm")
    private String SUPRiderATPDPremiumPayingTerm;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("firstYearSUPRiderATPDPremiumSummary")
    private String firstYearSUPRiderATPDPremiumSummary;
    //Payor Benefit
    @JsonProperty("SUPRiderPayorTerm")
    private String SUPRiderPayorTerm;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("SUPRiderPayorSumAssured")
    private String SUPRiderPayorSumAssured;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("SUPRiderPayorRiderGST")
    private String SUPRiderPayorRiderGST;
    @JsonProperty("SUPRiderPayorVariant")
    private String SUPRiderPayorVariant;
    @JsonProperty("SUPRiderPayorPremiumPayingTerm")
    @Sensitive(MaskType.AMOUNT)
    private String SUPRiderPayorPremiumPayingTerm;
    @JsonProperty("firstYearSUPRiderPayorPremiumSummary")
    private String firstYearSUPRiderPayorPremiumSummary;
    
    // FUL2-194198 For SWAG ELITE Plan
    @JsonProperty("termPlusRiderPPT")
    private String termPlusRiderPPT;
	@JsonProperty("wopPlusRiderPPT")
    private String wopPlusRiderPPT;
    @JsonProperty("returnOfPremiumBenefit")
    private String returnOfPremiumBenefit;
    @JsonProperty("milestoneBenefit")
    private String milestoneBenefit;
    @JsonProperty("deferralAccrualOption")
    private String deferralAccrualOption;
    @JsonProperty("totalSurvivalBenefit")
    private String totalSurvivalBenefit;
    @JsonProperty("uin")
    private String uin;
    @JsonProperty("biSumAssuredOnDeath")
    private String biSumAssuredOnDeath;
    @JsonProperty("biSumAssuredOnMaturity")
    private double biSumAssuredOnMaturity;
    @JsonProperty("riderUIN")
    private RiderUIN riderUIN;
    @JsonProperty("maturityBenefit")
    private String maturityBenefit;
    @JsonProperty("maternityRiderTerm")
    private String maternityRiderTerm;
    @JsonProperty("maternityRiderSumAssured")
    private String maternityRiderSumAssured;
    @JsonProperty("maternityRiderGST")
    private String maternityRiderGST;
    @JsonProperty("maternityRiderPremiumSummary")
    private String maternityRiderPremiumSummary;
    @JsonProperty("maternityRiderPremiumPayingTerm")
    private String maternityRiderPremiumPayingTerm;
    @JsonProperty("monthlyIncomeChosenAtInception")
    private String monthlyIncomeChosenAtInception;

    public ProductIllustrationResponse() {
    }

    public ProductIllustrationResponse(String coverageTerm, String coverMultiple, double sumAssured,
                                       String modeOfPayment, double modalPremium, String premiumPaymentTerm, String term,
                                       String requiredModalPremium, String totalRequiredModalPremium, String serviceTax, String afyp,
                                       String bonusOption, Date effectiveDate, double riderSumAssured, double riderModalPremium,
                                       String riderServiceTax, String riderInsuredDetails, double atp, String initialPremiumPaid,
                                       String guaranteedDeathBenefit, String annualPremium, String baseModalPremium,
                                       String serviceTaxInclCessBaseModalPrem, String firstYearADDRiderPremiumSummary,
                                       String firstYearTermPlusRiderPremiumSummary, String firstYearWOPPlusRiderPremiumSummary,
                                       String addRiderSumAssured, String addRiderTerm, String termPlusRiderSumAssured, String termPlusRiderTerm,
                                       String wopPlusRiderTerm, String wopPlusRiderSumAssured, String firstYearPartnerCareRiderPremiumSummary,
                                       String partnerCareRiderTerm, String partnerCareRiderSumAssured,
                                       String firstYearAccidentCoverRiderPremiumSummary, String accidentCoverRiderTerm,
                                       String accidentCoverRiderSumAssured, String firstYearAcceleratedCriticalIllnessRiderPremiumSummary,
                                       String acceleratedCriticalIllnessRiderTerm, String acceleratedCriticalIllnessRiderSumAssured,
                                       String employeeDiscount, String agentDiscount, String webDiscount, String customerDiscount,
                                       String deathBenefit, String incomePeriod, String premiumBackOption, String lifeStageEventBenefit,
                                       String guaranteedSumAssured, String basePlanGST, String accidentalCoverRiderGST,
                                       String accidentalCriticalIllnessRiderGST, String wopPlusRiderGST, String coverageString,
                                       String CABRiderPremium, String CABRiderTerm, String CABRiderSumAssured, String CABRiderGST,
                                       String initialPremiumforSales, Date bIGeneratedDate, String annualIncomeAmt, String incomePayoutFrequency,
                                       String covideRiderPremium, String covideRiderSumAssured, String covideRiderTerm, String covideRiderGST,
                                       String firstYearSmartHealthPlusPremiumSummary, String smartHealthPlusRiderTerm,
                                       String smartHealthPlusRiderSumAssured, String smartHealthPlusRiderGST, String termPlusRiderGST,
                                       String addRiderGST, String smartHealthPlusRiderVariant, String smartHealthPlusRiderPPT,
                                       String annuityPurchasedFrom, String guaranteedIncomeAmt, Date dateOfQuotation, Date annuityStartDate,
                                       String returnOfPremium, Double allocatedPremium, Double totalBenefit, Double cumulativeAnnualPremium,
                                       Double guaranteedMaturityValue, Integer nonGuaranteedMaturityValue_4, Integer nonGuaranteedMaturityValue_8,
                                       Integer maturityBenifit4pers, Integer maturityBenifit8pers, double jlSumAssured, double jlPremiumWithoutGST,
                                       String jlPremiumWithGST, String jlPremiumTotalRenewalWithGST, String jlPolicyTerm,
                                       String jlPremiumPaymentTerm, String jlServiceTax, String sUPRiderTBATITerm, String sUPRiderTBATISumAssured,
                                       String sUPRiderTBATIRiderGST, String sUPRiderTBATIVariant, String sUPRiderTBATIPremiumPayingTerm,
                                       String firstYearSUPRiderTBATIPremiumSummary, String sUPRiderADBTerm, String sUPRiderADBSumAssured,
                                       String sUPRiderADBRiderGST, String sUPRiderADBVariant, String sUPRiderADBPremiumPayingTerm,
                                       String firstYearSUPRiderADBPremiumSummary, String sUPRiderATPDTerm, String sUPRiderATPDSumAssured,
                                       String sUPRiderATPDRiderGST, String sUPRiderATPDVariant, String sUPRiderATPDPremiumPayingTerm,
                                       String firstYearSUPRiderATPDPremiumSummary, String sUPRiderPayorTerm, String sUPRiderPayorSumAssured,
                                       String sUPRiderPayorRiderGST, String sUPRiderPayorVariant, String sUPRiderPayorPremiumPayingTerm,
                                       String firstYearSUPRiderPayorPremiumSummary, String uin, String biSumAssuredOnDeath, Double biSumAssuredOnMaturity, RiderUIN riderUIN,
                                       String maturityBenefit, String maternityRiderTerm, String maternityRiderSumAssured,
                                       String maternityRiderGST, String maternityRiderPremiumSummary, String maternityRiderPremiumPayingTerm,String monthlyIncomeChosenAtInception) {
        super();
        this.coverageTerm = coverageTerm;
        this.coverMultiple = coverMultiple;
        this.sumAssured = sumAssured;
        this.modeOfPayment = modeOfPayment;
        this.modalPremium = modalPremium;
        this.premiumPaymentTerm = premiumPaymentTerm;
        this.term = term;
        this.requiredModalPremium = requiredModalPremium;
        this.totalRequiredModalPremium = totalRequiredModalPremium;
        this.serviceTax = serviceTax;
        this.afyp = afyp;
        this.bonusOption = bonusOption;
        this.effectiveDate = effectiveDate;
        this.riderSumAssured = riderSumAssured;
        this.riderModalPremium = riderModalPremium;
        this.riderServiceTax = riderServiceTax;
        this.riderInsuredDetails = riderInsuredDetails;
        this.atp = atp;
        this.initialPremiumPaid = initialPremiumPaid;
        this.guaranteedDeathBenefit = guaranteedDeathBenefit;
        this.annualPremium = annualPremium;
        this.baseModalPremium = baseModalPremium;
        this.serviceTaxInclCessBaseModalPrem = serviceTaxInclCessBaseModalPrem;
        this.firstYearADDRiderPremiumSummary = firstYearADDRiderPremiumSummary;
        this.firstYearTermPlusRiderPremiumSummary = firstYearTermPlusRiderPremiumSummary;
        this.firstYearWOPPlusRiderPremiumSummary = firstYearWOPPlusRiderPremiumSummary;
        this.addRiderSumAssured = addRiderSumAssured;
        this.addRiderTerm = addRiderTerm;
        this.termPlusRiderSumAssured = termPlusRiderSumAssured;
        this.termPlusRiderTerm = termPlusRiderTerm;
        this.wopPlusRiderTerm = wopPlusRiderTerm;
        this.wopPlusRiderSumAssured = wopPlusRiderSumAssured;
        this.firstYearPartnerCareRiderPremiumSummary = firstYearPartnerCareRiderPremiumSummary;
        this.partnerCareRiderTerm = partnerCareRiderTerm;
        this.partnerCareRiderSumAssured = partnerCareRiderSumAssured;
        this.firstYearAccidentCoverRiderPremiumSummary = firstYearAccidentCoverRiderPremiumSummary;
        this.accidentCoverRiderTerm = accidentCoverRiderTerm;
        this.accidentCoverRiderSumAssured = accidentCoverRiderSumAssured;
        this.firstYearAcceleratedCriticalIllnessRiderPremiumSummary = firstYearAcceleratedCriticalIllnessRiderPremiumSummary;
        this.acceleratedCriticalIllnessRiderTerm = acceleratedCriticalIllnessRiderTerm;
        this.acceleratedCriticalIllnessRiderSumAssured = acceleratedCriticalIllnessRiderSumAssured;
        this.employeeDiscount = employeeDiscount;
        this.agentDiscount = agentDiscount;
        this.webDiscount = webDiscount;
        this.customerDiscount = customerDiscount;
        this.deathBenefit = deathBenefit;
        this.incomePeriod = incomePeriod;
        this.premiumBackOption = premiumBackOption;
        this.lifeStageEventBenefit = lifeStageEventBenefit;
        this.guaranteedSumAssured = guaranteedSumAssured;
        this.basePlanGST = basePlanGST;
        this.accidentalCoverRiderGST = accidentalCoverRiderGST;
        this.accidentalCriticalIllnessRiderGST = accidentalCriticalIllnessRiderGST;
        this.wopPlusRiderGST = wopPlusRiderGST;
        this.coverageString = coverageString;
        this.CABRiderPremium = CABRiderPremium;
        this.CABRiderTerm = CABRiderTerm;
        this.CABRiderSumAssured = CABRiderSumAssured;
        this.CABRiderGST = CABRiderGST;
        this.initialPremiumforSales = initialPremiumforSales;
        this.bIGeneratedDate = bIGeneratedDate;
        this.annualIncomeAmt = annualIncomeAmt;
        this.incomePayoutFrequency = incomePayoutFrequency;
        this.covideRiderPremium = covideRiderPremium;
        this.covideRiderSumAssured = covideRiderSumAssured;
        this.covideRiderTerm = covideRiderTerm;
        this.covideRiderGST = covideRiderGST;
        this.firstYearSmartHealthPlusPremiumSummary = firstYearSmartHealthPlusPremiumSummary;
        this.smartHealthPlusRiderTerm = smartHealthPlusRiderTerm;
        this.smartHealthPlusRiderSumAssured = smartHealthPlusRiderSumAssured;
        this.smartHealthPlusRiderGST = smartHealthPlusRiderGST;
        this.termPlusRiderGST = termPlusRiderGST;
        this.addRiderGST = addRiderGST;
        this.smartHealthPlusRiderVariant = smartHealthPlusRiderVariant;
        this.smartHealthPlusRiderPPT = smartHealthPlusRiderPPT;
        this.annuityPurchasedFrom = annuityPurchasedFrom;
        this.guaranteedIncomeAmt = guaranteedIncomeAmt;
        this.dateOfQuotation = dateOfQuotation;
        this.annuityStartDate = annuityStartDate;
        this.returnOfPremium = returnOfPremium;
        this.allocatedPremium = allocatedPremium;
        this.totalBenefit = totalBenefit;
        this.cumulativeAnnualPremium = cumulativeAnnualPremium;
        this.guaranteedMaturityValue = guaranteedMaturityValue;
        this.nonGuaranteedMaturityValue_4 = nonGuaranteedMaturityValue_4;
        this.nonGuaranteedMaturityValue_8 = nonGuaranteedMaturityValue_8;
        this.maturityBenifit4pers = maturityBenifit4pers;
        this.maturityBenifit8pers = maturityBenifit8pers;
        this.jlSumAssured = jlSumAssured;
        this.jlPremiumWithoutGST = jlPremiumWithoutGST;
        this.jlPremiumWithGST = jlPremiumWithGST;
        this.jlPremiumTotalRenewalWithGST = jlPremiumTotalRenewalWithGST;
        this.jlPolicyTerm = jlPolicyTerm;
        this.jlPremiumPaymentTerm = jlPremiumPaymentTerm;
        this.jlServiceTax = jlServiceTax;
        SUPRiderTBATITerm = sUPRiderTBATITerm;
        SUPRiderTBATISumAssured = sUPRiderTBATISumAssured;
        SUPRiderTBATIRiderGST = sUPRiderTBATIRiderGST;
        SUPRiderTBATIVariant = sUPRiderTBATIVariant;
        SUPRiderTBATIPremiumPayingTerm = sUPRiderTBATIPremiumPayingTerm;
        this.firstYearSUPRiderTBATIPremiumSummary = firstYearSUPRiderTBATIPremiumSummary;
        SUPRiderADBTerm = sUPRiderADBTerm;
        SUPRiderADBSumAssured = sUPRiderADBSumAssured;
        SUPRiderADBRiderGST = sUPRiderADBRiderGST;
        SUPRiderADBVariant = sUPRiderADBVariant;
        SUPRiderADBPremiumPayingTerm = sUPRiderADBPremiumPayingTerm;
        this.firstYearSUPRiderADBPremiumSummary = firstYearSUPRiderADBPremiumSummary;
        SUPRiderATPDTerm = sUPRiderATPDTerm;
        SUPRiderATPDSumAssured = sUPRiderATPDSumAssured;
        SUPRiderATPDRiderGST = sUPRiderATPDRiderGST;
        SUPRiderATPDVariant = sUPRiderATPDVariant;
        SUPRiderATPDPremiumPayingTerm = sUPRiderATPDPremiumPayingTerm;
        this.firstYearSUPRiderATPDPremiumSummary = firstYearSUPRiderATPDPremiumSummary;
        SUPRiderPayorTerm = sUPRiderPayorTerm;
        SUPRiderPayorSumAssured = sUPRiderPayorSumAssured;
        SUPRiderPayorRiderGST = sUPRiderPayorRiderGST;
        SUPRiderPayorVariant = sUPRiderPayorVariant;
        SUPRiderPayorPremiumPayingTerm = sUPRiderPayorPremiumPayingTerm;
        this.firstYearSUPRiderPayorPremiumSummary = firstYearSUPRiderPayorPremiumSummary;
        this.uin = uin;
        this.biSumAssuredOnDeath = biSumAssuredOnDeath;
        this.biSumAssuredOnMaturity = biSumAssuredOnMaturity;
        this.riderUIN = riderUIN;
        this.maturityBenefit = maturityBenefit;
        this.maternityRiderTerm = maternityRiderTerm;
        this.maternityRiderSumAssured = maternityRiderSumAssured;
        this.maternityRiderGST = maternityRiderGST;
        this.maternityRiderPremiumSummary = maternityRiderPremiumSummary;
        this.maternityRiderPremiumPayingTerm = maternityRiderPremiumPayingTerm;
        this.monthlyIncomeChosenAtInception = monthlyIncomeChosenAtInception;
    }

	public String getSwipWopPlusRiderSumAssured() {
        return swipWopPlusRiderSumAssured;
    }

    public void setSwipWopPlusRiderSumAssured(String swipWopPlusRiderSumAssured) {
        this.swipWopPlusRiderSumAssured = swipWopPlusRiderSumAssured;
    }

    public String getMaturityBenefit() {
        return maturityBenefit;
    }
    public void setMaturityBenefit(String maturityBenefit) {
        this.maturityBenefit = maturityBenefit;
    }
    public String getCoverageString() {
	return coverageString;
    }

    public void setCoverageString(String coverageString) {
	this.coverageString = coverageString;
    }

    public String getCoverageTerm() {
	return coverageTerm;
    }

    public void setCoverageTerm(String coverageTerm) {
	this.coverageTerm = coverageTerm;
    }

    public String getCoverMultiple() {
	return coverMultiple;
    }

    public void setCoverMultiple(String coverMultiple) {
	this.coverMultiple = coverMultiple;
    }

    public double getSumAssured() {
	return sumAssured;
    }

    public void setSumAssured(double sumAssured) {
	this.sumAssured = sumAssured;
    }

    public String getModeOfPayment() {
	return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
	this.modeOfPayment = modeOfPayment;
    }

    public double getModalPremium() {
	return modalPremium;
    }

    public void setModalPremium(double modalPremium) {
	this.modalPremium = modalPremium;
    }

    public String getPremiumPaymentTerm() {
	return premiumPaymentTerm;
    }

    public void setPremiumPaymentTerm(String premiumPaymentTerm) {
	this.premiumPaymentTerm = premiumPaymentTerm;
    }

    public String getTerm() {
	return term;
    }

    public void setTerm(String term) {
	this.term = term;
    }

    public String getRequiredModalPremium() {
	return requiredModalPremium;
    }

    public void setRequiredModalPremium(String requiredModalPremium) {
	this.requiredModalPremium = requiredModalPremium;
    }

    public String getTotalRequiredModalPremium() {
	return totalRequiredModalPremium;
    }

    public void setTotalRequiredModalPremium(String totalRequiredModalPremium) {
	this.totalRequiredModalPremium = totalRequiredModalPremium;
    }

    public String getServiceTax() {
	return serviceTax;
    }

    public void setServiceTax(String serviceTax) {
	this.serviceTax = serviceTax;
    }

    public String getAfyp() {
	return afyp;
    }

    public void setAfyp(String afyp) {
	this.afyp = afyp;
    }

    public String getBonusOption() {
	return bonusOption;
    }

    public void setBonusOption(String bonusOption) {
	this.bonusOption = bonusOption;
    }

    public Date getEffectiveDate() {
	return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
	this.effectiveDate = effectiveDate;

    }

    public double getRiderSumAssured() {
	return riderSumAssured;
    }

    public void setRiderSumAssured(double riderSumAssured) {
	this.riderSumAssured = riderSumAssured;
    }

    public double getRiderModalPremium() {
	return riderModalPremium;
    }

    public void setRiderModalPremium(double riderModalPremium) {
	this.riderModalPremium = riderModalPremium;
    }

    public String getRiderServiceTax() {
	return riderServiceTax;
    }

    public void setRiderServiceTax(String riderServiceTax) {
	this.riderServiceTax = riderServiceTax;
    }

    public String getRiderInsuredDetails() {
	return riderInsuredDetails;
    }

    public void setRiderInsuredDetails(String riderInsuredDetails) {
	this.riderInsuredDetails = riderInsuredDetails;
    }

    public double getAtp() {
	return atp;
    }

    public void setAtp(double atp) {
	this.atp = atp;
    }

    public String getInitialPremiumPaid() {
	return initialPremiumPaid;
    }

    public void setInitialPremiumPaid(String initialPremiumPaid) {
	this.initialPremiumPaid = initialPremiumPaid;
    }

    public String getGuaranteedDeathBenefit() {
	return guaranteedDeathBenefit;
    }

    public void setGuaranteedDeathBenefit(String guaranteedDeathBenefit) {
	this.guaranteedDeathBenefit = guaranteedDeathBenefit;
    }

    public String getAnnualPremium() {
	return annualPremium;
    }

    public void setAnnualPremium(String annualPremium) {
	this.annualPremium = annualPremium;
    }

    public String getBaseModalPremium() {
	return baseModalPremium;
    }

    public void setBaseModalPremium(String baseModalPremium) {
	this.baseModalPremium = baseModalPremium;
    }

    public String getServiceTaxInclCessBaseModalPrem() {
	return serviceTaxInclCessBaseModalPrem;
    }

    public void setServiceTaxInclCessBaseModalPrem(String serviceTaxInclCessBaseModalPrem) {
	this.serviceTaxInclCessBaseModalPrem = serviceTaxInclCessBaseModalPrem;
    }

    public String getFirstYearADDRiderPremiumSummary() {
	return firstYearADDRiderPremiumSummary;
    }

    public void setFirstYearADDRiderPremiumSummary(String firstYearADDRiderPremiumSummary) {
	this.firstYearADDRiderPremiumSummary = firstYearADDRiderPremiumSummary;
    }

    public String getFirstYearTermPlusRiderPremiumSummary() {
	return firstYearTermPlusRiderPremiumSummary;
    }

    public void setFirstYearTermPlusRiderPremiumSummary(String firstYearTermPlusRiderPremiumSummary) {
	this.firstYearTermPlusRiderPremiumSummary = firstYearTermPlusRiderPremiumSummary;
    }

    public String getFirstYearWOPPlusRiderPremiumSummary() {
	return firstYearWOPPlusRiderPremiumSummary;
    }

    public void setFirstYearWOPPlusRiderPremiumSummary(String firstYearWOPPlusRiderPremiumSummary) {
	this.firstYearWOPPlusRiderPremiumSummary = firstYearWOPPlusRiderPremiumSummary;
    }

    public String getAddRiderSumAssured() {
	return addRiderSumAssured;
    }

    public void setAddRiderSumAssured(String addRiderSumAssured) {
	this.addRiderSumAssured = addRiderSumAssured;
    }

    public String getAddRiderTerm() {
	return addRiderTerm;
    }

    public void setAddRiderTerm(String addRiderTerm) {
	this.addRiderTerm = addRiderTerm;
    }

    public String getTermPlusRiderSumAssured() {
	return termPlusRiderSumAssured;
    }

    public void setTermPlusRiderSumAssured(String termPlusRiderSumAssured) {
	this.termPlusRiderSumAssured = termPlusRiderSumAssured;
    }

    public String getTermPlusRiderTerm() {
	return termPlusRiderTerm;
    }

    public void setTermPlusRiderTerm(String termPlusRiderTerm) {
	this.termPlusRiderTerm = termPlusRiderTerm;
    }

    public String getWopPlusRiderTerm() {
	return wopPlusRiderTerm;
    }

    public void setWopPlusRiderTerm(String wopPlusRiderTerm) {
	this.wopPlusRiderTerm = wopPlusRiderTerm;
    }

    public String getWopPlusRiderSumAssured() {
	return wopPlusRiderSumAssured;
    }

    public void setWopPlusRiderSumAssured(String wopPlusRiderSumAssured) {
	this.wopPlusRiderSumAssured = wopPlusRiderSumAssured;
    }

    public String getFirstYearPartnerCareRiderPremiumSummary() {
	return firstYearPartnerCareRiderPremiumSummary;
    }

    public void setFirstYearPartnerCareRiderPremiumSummary(String firstYearPartnerCareRiderPremiumSummary) {
	this.firstYearPartnerCareRiderPremiumSummary = firstYearPartnerCareRiderPremiumSummary;
    }

    public String getPartnerCareRiderTerm() {
	return partnerCareRiderTerm;
    }

    public void setPartnerCareRiderTerm(String partnerCareRiderTerm) {
	this.partnerCareRiderTerm = partnerCareRiderTerm;
    }

    public String getPartnerCareRiderSumAssured() {
	return partnerCareRiderSumAssured;
    }

    public void setPartnerCareRiderSumAssured(String partnerCareRiderSumAssured) {
	this.partnerCareRiderSumAssured = partnerCareRiderSumAssured;
    }

    public String getFirstYearAccidentCoverRiderPremiumSummary() {
	return firstYearAccidentCoverRiderPremiumSummary;
    }

    public void setFirstYearAccidentCoverRiderPremiumSummary(String firstYearAccidentCoverRiderPremiumSummary) {
	this.firstYearAccidentCoverRiderPremiumSummary = firstYearAccidentCoverRiderPremiumSummary;
    }

    public String getAccidentCoverRiderTerm() {
	return accidentCoverRiderTerm;
    }

    public void setAccidentCoverRiderTerm(String accidentCoverRiderTerm) {
	this.accidentCoverRiderTerm = accidentCoverRiderTerm;
    }

    public String getAccidentCoverRiderSumAssured() {
	return accidentCoverRiderSumAssured;
    }

    public void setAccidentCoverRiderSumAssured(String accidentCoverRiderSumAssured) {
	this.accidentCoverRiderSumAssured = accidentCoverRiderSumAssured;
    }

    public String getFirstYearAcceleratedCriticalIllnessRiderPremiumSummary() {
	return firstYearAcceleratedCriticalIllnessRiderPremiumSummary;
    }

    public void setFirstYearAcceleratedCriticalIllnessRiderPremiumSummary(String firstYearAcceleratedCriticalIllnessRiderPremiumSummary) {
	this.firstYearAcceleratedCriticalIllnessRiderPremiumSummary = firstYearAcceleratedCriticalIllnessRiderPremiumSummary;
    }

    public String getAcceleratedCriticalIllnessRiderTerm() {
	return acceleratedCriticalIllnessRiderTerm;
    }

    public void setAcceleratedCriticalIllnessRiderTerm(String acceleratedCriticalIllnessRiderTerm) {
	this.acceleratedCriticalIllnessRiderTerm = acceleratedCriticalIllnessRiderTerm;
    }

    public String getAcceleratedCriticalIllnessRiderSumAssured() {
	return acceleratedCriticalIllnessRiderSumAssured;
    }

    public void setAcceleratedCriticalIllnessRiderSumAssured(String acceleratedCriticalIllnessRiderSumAssured) {
	this.acceleratedCriticalIllnessRiderSumAssured = acceleratedCriticalIllnessRiderSumAssured;
    }

    public String getEmployeeDiscount() {
	return employeeDiscount;
    }

    public void setEmployeeDiscount(String employeeDiscount) {
	this.employeeDiscount = employeeDiscount;
    }

    public String getAgentDiscount() {
	return agentDiscount;
    }

    public void setAgentDiscount(String agentDiscount) {
	this.agentDiscount = agentDiscount;
    }

    public String getWebDiscount() {
	return webDiscount;
    }

    public void setWebDiscount(String webDiscount) {
	this.webDiscount = webDiscount;
    }

    public String getCustomerDiscount() {
	return customerDiscount;
    }

    public void setCustomerDiscount(String customerDiscount) {
	this.customerDiscount = customerDiscount;
    }

    public String getDeathBenefit() {
	return deathBenefit;
    }

    public void setDeathBenefit(String deathBenefit) {
	this.deathBenefit = deathBenefit;
    }

    public String getIncomePeriod() {
	return incomePeriod;
    }

    public void setIncomePeriod(String incomePeriod) {
	this.incomePeriod = incomePeriod;
    }

    public String getPremiumBackOption() {
	return premiumBackOption;
    }

    public void setPremiumBackOption(String premiumBackOption) {
	this.premiumBackOption = premiumBackOption;
    }

    public String getLifeStageEventBenefit() {
	return lifeStageEventBenefit;
    }

    public void setLifeStageEventBenefit(String lifeStageEventBenefit) {
	this.lifeStageEventBenefit = lifeStageEventBenefit;
    }

    public String getGuaranteedSumAssured() {
	return guaranteedSumAssured;
    }

    public void setGuaranteedSumAssured(String guaranteedSumAssured) {
	this.guaranteedSumAssured = guaranteedSumAssured;
    }

    public String getBasePlanGST() {
	return basePlanGST;
    }

    public void setBasePlanGST(String basePlanGST) {
	this.basePlanGST = basePlanGST;
    }

    public String getAccidentalCoverRiderGST() {
	return accidentalCoverRiderGST;
    }

    public void setAccidentalCoverRiderGST(String accidentalCoverRiderGST) {
	this.accidentalCoverRiderGST = accidentalCoverRiderGST;
    }

    public String getAccidentalCriticalIllnessRiderGST() {
	return accidentalCriticalIllnessRiderGST;
    }

    public void setAccidentalCriticalIllnessRiderGST(String accidentalCriticalIllnessRiderGST) {
	this.accidentalCriticalIllnessRiderGST = accidentalCriticalIllnessRiderGST;
    }

    public String getWopPlusRiderGST() {
	return wopPlusRiderGST;
    }

    public void setWopPlusRiderGST(String wopPlusRiderGST) {
	this.wopPlusRiderGST = wopPlusRiderGST;
    }

    public String getCABRiderPremium() {
	return CABRiderPremium;
    }

    public void setCABRiderPremium(String cABRiderPremium) {
	CABRiderPremium = cABRiderPremium;
    }

    public String getCABRiderTerm() {
	return CABRiderTerm;
    }

    public void setCABRiderTerm(String cABRiderTerm) {
	CABRiderTerm = cABRiderTerm;
    }

    public String getCABRiderSumAssured() {
	return CABRiderSumAssured;
    }

    public void setCABRiderSumAssured(String cABRiderSumAssured) {
	CABRiderSumAssured = cABRiderSumAssured;
    }

    public String getCABRiderGST() {
	return CABRiderGST;
    }

    public void setCABRiderGST(String cABRiderGST) {
	CABRiderGST = cABRiderGST;
    }

    public String getInitialPremiumforSales() {
	return initialPremiumforSales;
    }

    public void setInitialPremiumforSales(String initialPremiumforSales) {
	this.initialPremiumforSales = initialPremiumforSales;
    }

    public Date getbIGeneratedDate() {
        return bIGeneratedDate;
    }

    public void setbIGeneratedDate(Date bIGeneratedDate) {
        this.bIGeneratedDate = bIGeneratedDate;
    }

    public String getAnnualIncomeAmt() {
		return annualIncomeAmt;
	}

	public void setAnnualIncomeAmt(String annualIncomeAmt) {
		this.annualIncomeAmt = annualIncomeAmt;
	}
	
	public String getIncomePayoutFrequency() {
		return incomePayoutFrequency;
	}

	public void setIncomePayoutFrequency(String incomePayoutFrequency) {
		this.incomePayoutFrequency = incomePayoutFrequency;
	}

	public String getCovideRiderPremium() {
		return covideRiderPremium;
	}

	public void setCovideRiderPremium(String covideRiderPremium) {
		this.covideRiderPremium = covideRiderPremium;
	}

	public String getCovideRiderSumAssured() {
		return covideRiderSumAssured;
	}

	public void setCovideRiderSumAssured(String covideRiderSumAssured) {
		this.covideRiderSumAssured = covideRiderSumAssured;
	}

	public String getCovideRiderTerm() {
		return covideRiderTerm;
	}

	public void setCovideRiderTerm(String covideRiderTerm) {
		this.covideRiderTerm = covideRiderTerm;
	}

    public String getCovideRiderGST() {
        return covideRiderGST;
    }

    public void setCovideRiderGST(String covideRiderGST) {
        this.covideRiderGST = covideRiderGST;
    }

	public String getFirstYearSmartHealthPlusPremiumSummary() {
		return firstYearSmartHealthPlusPremiumSummary;
	}

	public void setFirstYearSmartHealthPlusPremiumSummary(String firstYearSmartHealthPlusPremiumSummary) {
		this.firstYearSmartHealthPlusPremiumSummary = firstYearSmartHealthPlusPremiumSummary;
	}

	public String getSmartHealthPlusRiderTerm() {
		return smartHealthPlusRiderTerm;
	}

	public void setSmartHealthPlusRiderTerm(String smartHealthPlusRiderTerm) {
		this.smartHealthPlusRiderTerm = smartHealthPlusRiderTerm;
	}

	public String getSmartHealthPlusRiderSumAssured() {
		return smartHealthPlusRiderSumAssured;
	}

	public void setSmartHealthPlusRiderSumAssured(String smartHealthPlusRiderSumAssured) {
		this.smartHealthPlusRiderSumAssured = smartHealthPlusRiderSumAssured;
	}

	public String getSmartHealthPlusRiderGST() {
		return smartHealthPlusRiderGST;
	}

	public void setSmartHealthPlusRiderGST(String smartHealthPlusRiderGST) {
		this.smartHealthPlusRiderGST = smartHealthPlusRiderGST;
	}

	public String getSmartHealthPlusRiderVariant() {
		return smartHealthPlusRiderVariant;
	}

	public void setSmartHealthPlusRiderVariant(String smartHealthPlusRiderVariant) {
		this.smartHealthPlusRiderVariant = smartHealthPlusRiderVariant;
	}

    public String getSmartHealthPlusRiderPPT() {
        return smartHealthPlusRiderPPT;
    }

    public void setSmartHealthPlusRiderPPT(String smartHealthPlusRiderPPT) {
        this.smartHealthPlusRiderPPT = smartHealthPlusRiderPPT;
    }

    public String getTermPlusRiderGST() {
        return termPlusRiderGST;
    }

    public void setTermPlusRiderGST(String termPlusRiderGST) {
        this.termPlusRiderGST = termPlusRiderGST;
    }

    public String getAddRiderGST() {
        return addRiderGST;
    }

    public void setAddRiderGST(String addRiderGST) {
        this.addRiderGST = addRiderGST;
    }

    public String getAnnuityPurchasedFrom() {
        return annuityPurchasedFrom;
    }

    public void setAnnuityPurchasedFrom(String annuityPurchasedFrom) {
        this.annuityPurchasedFrom = annuityPurchasedFrom;
    }

    public String getGuaranteedIncomeAmt() {
        return guaranteedIncomeAmt;
    }

    public void setGuaranteedIncomeAmt(String guaranteedIncomeAmt) {
        this.guaranteedIncomeAmt = guaranteedIncomeAmt;
    }

    public Date getDateOfQuotation() {
        return dateOfQuotation;
    }

    public void setDateOfQuotation(Date dateOfQuotation) {
        this.dateOfQuotation = dateOfQuotation;
    }

    public Date getAnnuityStartDate() {
        return annuityStartDate;
    }

    public void setAnnuityStartDate(Date annuityStartDate) {
        this.annuityStartDate = annuityStartDate;
    }

    public String getReturnOfPremium() {
        return returnOfPremium;
    }

    public void setReturnOfPremium(String returnOfPremium) {
        this.returnOfPremium = returnOfPremium;
    }
    public Double getallocatedPremium() {
		return allocatedPremium;
	}

	public void setallocatedPremium(Double allocatedPremium) {
		this.allocatedPremium = allocatedPremium;
	}

	public Double gettotalBenefit() {
		return totalBenefit;
	}

	public void settotalBenefit(Double totalBenefit) {
		this.totalBenefit = totalBenefit;
	}

	public Double getCumulativeAnnualPremium() {
		return cumulativeAnnualPremium;
	}

	public void setCumulativeAnnualPremium(Double cumulativeAnnualPremium) {
		this.cumulativeAnnualPremium = cumulativeAnnualPremium;
	}

	public Double getGuaranteedMaturityValue() {
		return guaranteedMaturityValue;
	}

	public void setGuaranteedMaturityValue(Double guaranteedMaturityValue) {
		this.guaranteedMaturityValue = guaranteedMaturityValue;
	}

	public Integer getNonGuaranteedMaturityValue_4() {
		return nonGuaranteedMaturityValue_4;
	}

	public void setNonGuaranteedMaturityValue_4(Integer nonGuaranteedMaturityValue_4) {
		this.nonGuaranteedMaturityValue_4 = nonGuaranteedMaturityValue_4;
	}

	public Integer getNonGuaranteedMaturityValue_8() {
		return nonGuaranteedMaturityValue_8;
	}

	public void setNonGuaranteedMaturityValue_8(Integer nonGuaranteedMaturityValue_8) {
		this.nonGuaranteedMaturityValue_8 = nonGuaranteedMaturityValue_8;
	}

	public Integer getmaturityBenifit4pers() {
		return maturityBenifit4pers;
	}

	public void setmaturityBenifit4pers(Integer maturityBenifit4pers) {
		this.maturityBenifit4pers = maturityBenifit4pers;
	}

	public Integer getmaturityBenifit8pers() {
		return maturityBenifit8pers;
	}

	public void setmaturityBenifit8pers(Integer maturityBenifit8pers) {
		this.maturityBenifit8pers = maturityBenifit8pers;
	}

    public double getJlSumAssured() {
		return jlSumAssured;
	}

	public void setJlSumAssured(double jlSumAssured) {
		this.jlSumAssured = jlSumAssured;
	}

	public double getJlPremiumWithoutGST() {
		return jlPremiumWithoutGST;
	}

	public void setJlPremiumWithoutGST(double jlPremiumWithoutGST) {
		this.jlPremiumWithoutGST = jlPremiumWithoutGST;
	}

	public String getJlPremiumWithGST() {
		return jlPremiumWithGST;
	}

	public void setJlPremiumWithGST(String jlPremiumWithGST) {
		this.jlPremiumWithGST = jlPremiumWithGST;
	}

	public String getJlPremiumTotalRenewalWithGST() {
		return jlPremiumTotalRenewalWithGST;
	}

	public void setJlPremiumTotalRenewalWithGST(String jlPremiumTotalRenewalWithGST) {
		this.jlPremiumTotalRenewalWithGST = jlPremiumTotalRenewalWithGST;
	}

	public String getJlPolicyTerm() {
		return jlPolicyTerm;
	}

	public void setJlPolicyTerm(String jlPolicyTerm) {
		this.jlPolicyTerm = jlPolicyTerm;
	}

	public String getJlPremiumPaymentTerm() {
		return jlPremiumPaymentTerm;
	}

	public void setJlPremiumPaymentTerm(String jlPremiumPaymentTerm) {
		this.jlPremiumPaymentTerm = jlPremiumPaymentTerm;
	}

	public String getJlServiceTax() {
		return jlServiceTax;
	}

	public void setJlServiceTax(String jlServiceTax) {
		this.jlServiceTax = jlServiceTax;
	}

	public String getSUPRiderTBATITerm() {
		return SUPRiderTBATITerm;
	}

	public void setSUPRiderTBATITerm(String sUPRiderTBATITerm) {
		SUPRiderTBATITerm = sUPRiderTBATITerm;
	}

	public String getSUPRiderTBATISumAssured() {
		return SUPRiderTBATISumAssured;
	}

	public void setSUPRiderTBATISumAssured(String sUPRiderTBATISumAssured) {
		SUPRiderTBATISumAssured = sUPRiderTBATISumAssured;
	}

	public String getSUPRiderTBATIRiderGST() {
		return SUPRiderTBATIRiderGST;
	}

	public void setSUPRiderTBATIRiderGST(String sUPRiderTBATIRiderGST) {
		SUPRiderTBATIRiderGST = sUPRiderTBATIRiderGST;
	}

	public String getSUPRiderTBATIVariant() {
		return SUPRiderTBATIVariant;
	}

	public void setSUPRiderTBATIVariant(String sUPRiderTBATIVariant) {
		SUPRiderTBATIVariant = sUPRiderTBATIVariant;
	}

	public String getSUPRiderTBATIPremiumPayingTerm() {
		return SUPRiderTBATIPremiumPayingTerm;
	}

	public void setSUPRiderTBATIPremiumPayingTerm(String sUPRiderTBATIPremiumPayingTerm) {
		SUPRiderTBATIPremiumPayingTerm = sUPRiderTBATIPremiumPayingTerm;
	}

	public String getFirstYearSUPRiderTBATIPremiumSummary() {
		return firstYearSUPRiderTBATIPremiumSummary;
	}

	public void setFirstYearSUPRiderTBATIPremiumSummary(String firstYearSUPRiderTBATIPremiumSummary) {
		this.firstYearSUPRiderTBATIPremiumSummary = firstYearSUPRiderTBATIPremiumSummary;
	}

	public String getSUPRiderADBTerm() {
		return SUPRiderADBTerm;
	}

	public void setSUPRiderADBTerm(String sUPRiderADBTerm) {
		SUPRiderADBTerm = sUPRiderADBTerm;
	}

	public String getSUPRiderADBSumAssured() {
		return SUPRiderADBSumAssured;
	}

	public void setSUPRiderADBSumAssured(String sUPRiderADBSumAssured) {
		SUPRiderADBSumAssured = sUPRiderADBSumAssured;
	}

	public String getSUPRiderADBRiderGST() {
		return SUPRiderADBRiderGST;
	}

	public void setSUPRiderADBRiderGST(String sUPRiderADBRiderGST) {
		SUPRiderADBRiderGST = sUPRiderADBRiderGST;
	}

	public String getSUPRiderADBVariant() {
		return SUPRiderADBVariant;
	}

	public void setSUPRiderADBVariant(String sUPRiderADBVariant) {
		SUPRiderADBVariant = sUPRiderADBVariant;
	}

	public String getSUPRiderADBPremiumPayingTerm() {
		return SUPRiderADBPremiumPayingTerm;
	}

	public void setSUPRiderADBPremiumPayingTerm(String sUPRiderADBPremiumPayingTerm) {
		SUPRiderADBPremiumPayingTerm = sUPRiderADBPremiumPayingTerm;
	}

	public String getFirstYearSUPRiderADBPremiumSummary() {
		return firstYearSUPRiderADBPremiumSummary;
	}

	public void setFirstYearSUPRiderADBPremiumSummary(String firstYearSUPRiderADBPremiumSummary) {
		this.firstYearSUPRiderADBPremiumSummary = firstYearSUPRiderADBPremiumSummary;
	}

	public String getSUPRiderATPDTerm() {
		return SUPRiderATPDTerm;
	}

	public void setSUPRiderATPDTerm(String sUPRiderATPDTerm) {
		SUPRiderATPDTerm = sUPRiderATPDTerm;
	}

	public String getSUPRiderATPDSumAssured() {
		return SUPRiderATPDSumAssured;
	}

	public void setSUPRiderATPDSumAssured(String sUPRiderATPDSumAssured) {
		SUPRiderATPDSumAssured = sUPRiderATPDSumAssured;
	}

	public String getSUPRiderATPDRiderGST() {
		return SUPRiderATPDRiderGST;
	}

	public void setSUPRiderATPDRiderGST(String sUPRiderATPDRiderGST) {
		SUPRiderATPDRiderGST = sUPRiderATPDRiderGST;
	}

	public String getSUPRiderATPDVariant() {
		return SUPRiderATPDVariant;
	}

	public void setSUPRiderATPDVariant(String sUPRiderATPDVariant) {
		SUPRiderATPDVariant = sUPRiderATPDVariant;
	}

	public String getSUPRiderATPDPremiumPayingTerm() {
		return SUPRiderATPDPremiumPayingTerm;
	}

	public void setSUPRiderATPDPremiumPayingTerm(String sUPRiderATPDPremiumPayingTerm) {
		SUPRiderATPDPremiumPayingTerm = sUPRiderATPDPremiumPayingTerm;
	}

	public String getFirstYearSUPRiderATPDPremiumSummary() {
		return firstYearSUPRiderATPDPremiumSummary;
	}

	public void setFirstYearSUPRiderATPDPremiumSummary(String firstYearSUPRiderATPDPremiumSummary) {
		this.firstYearSUPRiderATPDPremiumSummary = firstYearSUPRiderATPDPremiumSummary;
	}

	public String getSUPRiderPayorTerm() {
		return SUPRiderPayorTerm;
	}

	public void setSUPRiderPayorTerm(String sUPRiderPayorTerm) {
		SUPRiderPayorTerm = sUPRiderPayorTerm;
	}

	public String getSUPRiderPayorSumAssured() {
		return SUPRiderPayorSumAssured;
	}

	public void setSUPRiderPayorSumAssured(String sUPRiderPayorSumAssured) {
		SUPRiderPayorSumAssured = sUPRiderPayorSumAssured;
	}

	public String getSUPRiderPayorRiderGST() {
		return SUPRiderPayorRiderGST;
	}

	public void setSUPRiderPayorRiderGST(String sUPRiderPayorRiderGST) {
		SUPRiderPayorRiderGST = sUPRiderPayorRiderGST;
	}

	public String getSUPRiderPayorVariant() {
		return SUPRiderPayorVariant;
	}

	public void setSUPRiderPayorVariant(String sUPRiderPayorVariant) {
		SUPRiderPayorVariant = sUPRiderPayorVariant;
	}

	public String getSUPRiderPayorPremiumPayingTerm() {
		return SUPRiderPayorPremiumPayingTerm;
	}

	public void setSUPRiderPayorPremiumPayingTerm(String sUPRiderPayorPremiumPayingTerm) {
		SUPRiderPayorPremiumPayingTerm = sUPRiderPayorPremiumPayingTerm;
	}

	public String getFirstYearSUPRiderPayorPremiumSummary() {
		return firstYearSUPRiderPayorPremiumSummary;
	}

	public void setFirstYearSUPRiderPayorPremiumSummary(String firstYearSUPRiderPayorPremiumSummary) {
		this.firstYearSUPRiderPayorPremiumSummary = firstYearSUPRiderPayorPremiumSummary;
	}
	
    public String getTermPlusRiderPPT() {
		return termPlusRiderPPT;
	}

	public void setTermPlusRiderPPT(String termPlusRiderPPT) {
		this.termPlusRiderPPT = termPlusRiderPPT;
	}

	public String getWopPlusRiderPPT() {
		return wopPlusRiderPPT;
	}

	public void setWopPlusRiderPPT(String wopPlusRiderPPT) {
		this.wopPlusRiderPPT = wopPlusRiderPPT;
	}

	public String getReturnOfPremiumBenefit() {
		return returnOfPremiumBenefit;
	}

	public void setReturnOfPremiumBenefit(String returnOfPremiumBenefit) {
		this.returnOfPremiumBenefit = returnOfPremiumBenefit;
	}

	public String getMilestoneBenefit() {
		return milestoneBenefit;
	}

	public void setMilestoneBenefit(String milestoneBenefit) {
		this.milestoneBenefit = milestoneBenefit;
	}

	public String getDeferralAccrualOption() {
		return deferralAccrualOption;
	}

	public void setDeferralAccrualOption(String deferralAccrualOption) {
		this.deferralAccrualOption = deferralAccrualOption;
	}

	public String getTotalSurvivalBenefit() {
		return totalSurvivalBenefit;
	}

	public void setTotalSurvivalBenefit(String totalSurvivalBenefit) {
		this.totalSurvivalBenefit = totalSurvivalBenefit;
	}

    public String getUin() {
        return uin;
    }

    public void setUin(String uin) {
        this.uin = uin;
    }

    public String getBiSumAssuredOnDeath() {
        return biSumAssuredOnDeath;
    }

    public void setBiSumAssuredOnDeath(String biSumAssuredOnDeath) {
        this.biSumAssuredOnDeath = biSumAssuredOnDeath;
    }

    public double getBiSumAssuredOnMaturity() {
        return biSumAssuredOnMaturity;
    }

    public void setBiSumAssuredOnMaturity(double biSumAssuredOnMaturity) {
        this.biSumAssuredOnMaturity = biSumAssuredOnMaturity;
    }

    public RiderUIN getRiderUIN() {
        return riderUIN;
    }

    public void setRiderUIN(RiderUIN riderUIN) {
        this.riderUIN = riderUIN;
    }

    public String getMaternityRiderTerm() {
        return maternityRiderTerm;
    }

    public void setMaternityRiderTerm(String maternityRiderTerm) {
        this.maternityRiderTerm = maternityRiderTerm;
    }

    public String getMaternityRiderSumAssured() {
        return maternityRiderSumAssured;
    }

    public void setMaternityRiderSumAssured(String maternityRiderSumAssured) {
        this.maternityRiderSumAssured = maternityRiderSumAssured;
    }

    public String getMaternityRiderGST() {
        return maternityRiderGST;
    }

    public void setMaternityRiderGST(String maternityRiderGST) {
        this.maternityRiderGST = maternityRiderGST;
    }

    public String getMaternityRiderPremiumSummary() {
        return maternityRiderPremiumSummary;
    }

    public void setMaternityRiderPremiumSummary(String maternityRiderPremiumSummary) {
        this.maternityRiderPremiumSummary = maternityRiderPremiumSummary;
    }

    public String getMaternityRiderPremiumPayingTerm() {
        return maternityRiderPremiumPayingTerm;
    }

    public void setMaternityRiderPremiumPayingTerm(String maternityRiderPremiumPayingTerm) {
        this.maternityRiderPremiumPayingTerm = maternityRiderPremiumPayingTerm;
    }

    public String getMonthlyIncomeChosenAtInception() {
        return monthlyIncomeChosenAtInception;
    }

    public void setMonthlyIncomeChosenAtInception(String monthlyIncomeChosenAtInception) {
        this.monthlyIncomeChosenAtInception = monthlyIncomeChosenAtInception;
    }

    @Override
	public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
		return "ProductIllustrationResponse [coverageTerm=" + coverageTerm + ", coverMultiple=" + coverMultiple
				+ ", sumAssured=" + sumAssured + ", modeOfPayment=" + modeOfPayment + ", modalPremium=" + modalPremium
				+ ", premiumPaymentTerm=" + premiumPaymentTerm + ", term=" + term + ", requiredModalPremium="
				+ requiredModalPremium + ", totalRequiredModalPremium=" + totalRequiredModalPremium + ", serviceTax="
				+ serviceTax + ", afyp=" + afyp + ", bonusOption=" + bonusOption + ", effectiveDate=" + effectiveDate
				+ ", riderSumAssured=" + riderSumAssured + ", riderModalPremium=" + riderModalPremium
				+ ", riderServiceTax=" + riderServiceTax + ", riderInsuredDetails=" + riderInsuredDetails + ", atp="
				+ atp + ", initialPremiumPaid=" + initialPremiumPaid + ", guaranteedDeathBenefit="
				+ guaranteedDeathBenefit + ", annualPremium=" + annualPremium + ", baseModalPremium=" + baseModalPremium
				+ ", serviceTaxInclCessBaseModalPrem=" + serviceTaxInclCessBaseModalPrem
				+ ", firstYearADDRiderPremiumSummary=" + firstYearADDRiderPremiumSummary
				+ ", firstYearTermPlusRiderPremiumSummary=" + firstYearTermPlusRiderPremiumSummary
				+ ", firstYearWOPPlusRiderPremiumSummary=" + firstYearWOPPlusRiderPremiumSummary
				+ ", addRiderSumAssured=" + addRiderSumAssured + ", addRiderTerm=" + addRiderTerm
				+ ", termPlusRiderSumAssured=" + termPlusRiderSumAssured + ", termPlusRiderTerm=" + termPlusRiderTerm
				+ ", wopPlusRiderTerm=" + wopPlusRiderTerm + ", wopPlusRiderSumAssured=" + wopPlusRiderSumAssured
				+ ", firstYearPartnerCareRiderPremiumSummary=" + firstYearPartnerCareRiderPremiumSummary
				+ ", partnerCareRiderTerm=" + partnerCareRiderTerm + ", partnerCareRiderSumAssured="
				+ partnerCareRiderSumAssured + ", firstYearAccidentCoverRiderPremiumSummary="
				+ firstYearAccidentCoverRiderPremiumSummary + ", accidentCoverRiderTerm=" + accidentCoverRiderTerm
				+ ", accidentCoverRiderSumAssured=" + accidentCoverRiderSumAssured
				+ ", firstYearAcceleratedCriticalIllnessRiderPremiumSummary="
				+ firstYearAcceleratedCriticalIllnessRiderPremiumSummary + ", acceleratedCriticalIllnessRiderTerm="
				+ acceleratedCriticalIllnessRiderTerm + ", acceleratedCriticalIllnessRiderSumAssured="
				+ acceleratedCriticalIllnessRiderSumAssured + ", employeeDiscount=" + employeeDiscount
				+ ", agentDiscount=" + agentDiscount + ", webDiscount=" + webDiscount + ", customerDiscount="
				+ customerDiscount + ", deathBenefit=" + deathBenefit + ", incomePeriod=" + incomePeriod
				+ ", premiumBackOption=" + premiumBackOption + ", lifeStageEventBenefit=" + lifeStageEventBenefit
				+ ", guaranteedSumAssured=" + guaranteedSumAssured + ", basePlanGST=" + basePlanGST
				+ ", accidentalCoverRiderGST=" + accidentalCoverRiderGST + ", accidentalCriticalIllnessRiderGST="
				+ accidentalCriticalIllnessRiderGST + ", wopPlusRiderGST=" + wopPlusRiderGST + ", coverageString="
				+ coverageString + ", CABRiderPremium=" + CABRiderPremium + ", CABRiderTerm=" + CABRiderTerm
				+ ", CABRiderSumAssured=" + CABRiderSumAssured + ", CABRiderGST=" + CABRiderGST
				+ ", initialPremiumforSales=" + initialPremiumforSales + ", bIGeneratedDate=" + bIGeneratedDate
				+ ", annualIncomeAmt=" + annualIncomeAmt + ", incomePayoutFrequency=" + incomePayoutFrequency
				+ ", covideRiderPremium=" + covideRiderPremium + ", covideRiderSumAssured=" + covideRiderSumAssured
				+ ", covideRiderTerm=" + covideRiderTerm + ", covideRiderGST=" + covideRiderGST
				+ ", firstYearSmartHealthPlusPremiumSummary=" + firstYearSmartHealthPlusPremiumSummary
				+ ", smartHealthPlusRiderTerm=" + smartHealthPlusRiderTerm + ", smartHealthPlusRiderSumAssured="
				+ smartHealthPlusRiderSumAssured + ", smartHealthPlusRiderGST=" + smartHealthPlusRiderGST
				+ ", termPlusRiderGST=" + termPlusRiderGST + ", addRiderGST=" + addRiderGST
				+ ", smartHealthPlusRiderVariant=" + smartHealthPlusRiderVariant + ", smartHealthPlusRiderPPT="
				+ smartHealthPlusRiderPPT + ", annuityPurchasedFrom=" + annuityPurchasedFrom + ", guaranteedIncomeAmt="
				+ guaranteedIncomeAmt + ", dateOfQuotation=" + dateOfQuotation + ", annuityStartDate="
				+ annuityStartDate + ", returnOfPremium=" + returnOfPremium + ", allocatedPremium=" + allocatedPremium
				+ ", totalBenefit=" + totalBenefit + ", cumulativeAnnualPremium=" + cumulativeAnnualPremium
				+ ", guaranteedMaturityValue=" + guaranteedMaturityValue + ", nonGuaranteedMaturityValue_4="
				+ nonGuaranteedMaturityValue_4 + ", nonGuaranteedMaturityValue_8=" + nonGuaranteedMaturityValue_8
				+ ", maturityBenifit4pers=" + maturityBenifit4pers + ", maturityBenifit8pers=" + maturityBenifit8pers
				+ ", jlSumAssured=" + jlSumAssured + ", jlPremiumWithoutGST=" + jlPremiumWithoutGST
				+ ", jlPremiumWithGST=" + jlPremiumWithGST + ", jlPremiumTotalRenewalWithGST="
				+ jlPremiumTotalRenewalWithGST + ", jlPolicyTerm=" + jlPolicyTerm + ", jlPremiumPaymentTerm="
				+ jlPremiumPaymentTerm + ", jlServiceTax=" + jlServiceTax + ", swipWopPlusRiderSumAssured="
				+ swipWopPlusRiderSumAssured + ", SUPRiderTBATITerm=" + SUPRiderTBATITerm + ", SUPRiderTBATISumAssured="
				+ SUPRiderTBATISumAssured + ", SUPRiderTBATIRiderGST=" + SUPRiderTBATIRiderGST
				+ ", SUPRiderTBATIVariant=" + SUPRiderTBATIVariant + ", SUPRiderTBATIPremiumPayingTerm="
				+ SUPRiderTBATIPremiumPayingTerm + ", firstYearSUPRiderTBATIPremiumSummary="
				+ firstYearSUPRiderTBATIPremiumSummary + ", SUPRiderADBTerm=" + SUPRiderADBTerm
				+ ", SUPRiderADBSumAssured=" + SUPRiderADBSumAssured + ", SUPRiderADBRiderGST=" + SUPRiderADBRiderGST
				+ ", SUPRiderADBVariant=" + SUPRiderADBVariant + ", SUPRiderADBPremiumPayingTerm="
				+ SUPRiderADBPremiumPayingTerm + ", firstYearSUPRiderADBPremiumSummary="
				+ firstYearSUPRiderADBPremiumSummary + ", SUPRiderATPDTerm=" + SUPRiderATPDTerm
				+ ", SUPRiderATPDSumAssured=" + SUPRiderATPDSumAssured + ", SUPRiderATPDRiderGST="
				+ SUPRiderATPDRiderGST + ", SUPRiderATPDVariant=" + SUPRiderATPDVariant
				+ ", SUPRiderATPDPremiumPayingTerm=" + SUPRiderATPDPremiumPayingTerm
				+ ", firstYearSUPRiderATPDPremiumSummary=" + firstYearSUPRiderATPDPremiumSummary
				+ ", SUPRiderPayorTerm=" + SUPRiderPayorTerm 
				+ ", SUPRiderPayorSumAssured=" + SUPRiderPayorSumAssured
				+ ", SUPRiderPayorRiderGST=" + SUPRiderPayorRiderGST 
				+ ", SUPRiderPayorVariant=" + SUPRiderPayorVariant 
				+ ", SUPRiderPayorPremiumPayingTerm=" + SUPRiderPayorPremiumPayingTerm
				+ ", firstYearSUPRiderPayorPremiumSummary=" + firstYearSUPRiderPayorPremiumSummary
				+ ", termPlusRiderPPT=" + termPlusRiderPPT
				+ ", wopPlusRiderPPT=" + wopPlusRiderPPT
				+ ", returnOfPremiumBenefit=" + returnOfPremiumBenefit
				+ ", milestoneBenefit=" + milestoneBenefit
				+ ", deferralAccrualOption=" + deferralAccrualOption
				+ ", totalSurvivalBenefit=" + totalSurvivalBenefit
                + ", uin=" + uin
                + ", biSumAssuredOnDeath="+ biSumAssuredOnDeath
                + ",biSumAssuredOnMaturity=" +biSumAssuredOnMaturity
                + ",riderUIN=" +riderUIN
                + ",maturityBenefit=" +maturityBenefit
                + ",maternityRiderTerm=" +maternityRiderTerm
                + ",maternityRiderSumAssured=" +maternityRiderSumAssured
                + ",maternityRiderGST=" +maternityRiderGST
                + ",maternityRiderPremiumSummary=" +maternityRiderPremiumSummary
                + ",maternityRiderPremiumPayingTerm=" +maternityRiderPremiumPayingTerm
                + ",monthlyIncomeChosenAtInception=" +monthlyIncomeChosenAtInception
				+ "]";
    }

}