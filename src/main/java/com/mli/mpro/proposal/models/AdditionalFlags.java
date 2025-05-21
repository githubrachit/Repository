
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.configuration.models.AfypDetails;
import com.mli.mpro.configuration.models.ReplacementSaleDetails;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "onWhichScreen", " le3ServiceResponse", "emailSentResponse", "ifscMicrFailResponse", "dedupeFlag", "existingApiResponse", "screen1",
	"screen2", "screen2", "screen4", "screen5", "screen6" })
public class AdditionalFlags {

    @JsonProperty("currentActiveScreen")
    public int currentActiveScreen;
    @JsonProperty("isIllustrationGenerated")
    public boolean isIllustrationGenerated;
    @JsonProperty("isEmailSent")
    public boolean isEmailSent;
    @JsonProperty("isifscMicrValidated")
    public boolean isifscMicrValidated;
    @JsonProperty("isDedupeValidated")
    public String isDedupeValidated;
    @JsonProperty("screen1")
    public String screen1;
    @JsonProperty("screen2")
    public String screen2;
    @JsonProperty("screen3")
    public String screen3;
    @JsonProperty("screen4")
    public String screen4;
    @JsonProperty("screen5")
    public String screen5;
    @JsonProperty("screen6")
    public String screen6;
    @JsonProperty("isPayorDiffFromPropser")
    private boolean isPayorDiffFromPropser;
    @JsonProperty("isIllustrationGeneratedOnScreen2")
    private boolean isIllustrationGeneratedOnScreen2;
    @JsonProperty("isPaymentDone")
    private boolean isPaymentDone;
    @JsonProperty("preIssuanceVerificationNumber")
    private String preIssuanceVerificationNumber;
    @JsonProperty("requestSource")
    private String requestSource;
    @JsonProperty("agentKnowsProposerSince")
    private String agentKnowsProposerSince;
    @JsonProperty("agentKnowsProposerUnitType")
    private String agentKnowsProposerUnitType;
    @JsonProperty("emailBIStatus")
    private String emailBIStatus;
    @JsonProperty("eBCCDocStatus")
    private String eBCCDocStatus;
    @JsonProperty("journeyFieldsModificationStatus")
    private JourneyFieldsModificationStatus journeyFieldsModificationStatus;
    @JsonProperty("eBCCDocGenerationStatus")
    private String eBCCDocGenerationStatus;
    @JsonProperty("isAgentSelf")
    private String isAgentSelf;
    //FUL2-103459 Additional Flag isMaxEmp to check if user is/was MLI employee
    @JsonProperty("isMaxEmp")
    private String isMaxEmp;
    @Sensitive(MaskType.POLICY_NUM)
    @JsonProperty("proposerPreviousPolicyNumber")
    private String proposerPreviousPolicyNumber;
    @JsonProperty("stpCounterStatus")
    private String stpCounterStatus;
    @JsonProperty("ekycProposerConsent")
    private String ekycProposerConsent;
    @JsonProperty("ekycInsuredConsent")
    private String ekycInsuredConsent;
    @JsonProperty("ekycPayorConsent")
    private String ekycPayorConsent;
    @JsonProperty("insuredDOBRequired")
    private String insuredDOBRequired;
    @JsonProperty("isPersonalInfoEdited")
    private boolean isPersonalInfoEdited;
    @JsonProperty("isPermAddressEdited")
    private boolean isPermAddressEdited;
    @JsonProperty("isCommAddressEdited")
    private boolean isCommAddressEdited;
    @JsonProperty("issuerConfirmCertificateStatus")
    private String issuerConfirmCertificateStatus;
    @JsonProperty("isteleMERStatus")
    private String isteleMERStatus;
    //FUL2-6218 FIP UW - Added new gridName VideoMer
    @JsonProperty("isvideoMERStatus")
    private String isvideoMERStatus;
    @JsonProperty("isteleMERFlag")
    private boolean isteleMERFlag;
    @JsonProperty("isvideoMERFlag")
    private boolean isvideoMERFlag;
    //FUL2-10115_Digital_Debit_Mandate_Registration-Axis_Channel
    @JsonProperty("axisDirectDebitPOSVData")
    private AxisDirectDebitPOSVData axisDirectDebitPOSVData;
    @JsonProperty("psmApiStatus")
    private String psmApiStatus;
    @JsonProperty("replacementSale")
    private String replacementSale;
    /*FUL2-17826 removed the temporary gst Waiver changes*/

    @JsonProperty("isNewYblReplacementSale")
    private String isNewYblReplacementSale;

    @JsonProperty("yblReplacementPolicyStatus")
    private String yblReplacementPolicyStatus;
    @JsonProperty("ebcc")
    private boolean ebcc;
    //FUL2-11834 to identify that whether this is case of YblTeleSales
    @JsonProperty("isYblTelesalesCase")
    private boolean isYblTelesalesCase;
    //FUL2-11412
    @JsonProperty("isEducationPresent")
    private Boolean isEducationPresent;

    //FUL2-16866 Fields added for GLIP Product
    @JsonProperty("isSecondAnnuitantPEP")
    private Boolean isSecondAnnuitantPEP;
    @JsonProperty("annuitantReflexive")
    private String annuitantReflexive;
    @JsonProperty("posEmailStatus")
    private boolean posEmailStatus;
    @JsonProperty("isSellerDeclarationApplicable")
    private String isSellerDeclarationApplicable;

    // FUL2-13674 photo supress
    @JsonProperty("isPhotoSupress")
    private boolean isPhotoSupress;
    @JsonProperty("photoType")
    private String photoType;
    /* FUL2-25833 Video POSV - dolphin push story */
  	@JsonProperty("reasonForVideoPosv")
	private String reasonForVideoPosv;
    /* FUL2-25833 Video POSV - dolphin push story */
    //FUL2-35335 PEP wip cases handling
    private String isPEPDisabled;
    @JsonProperty("isProspectiveYBLCustomer")
    private Boolean isProspectiveYBLCustomer;

    @JsonProperty("isRenewelPaymentDone")
    private boolean isRenewelPaymentDone;
    @JsonProperty("isInitiativeTypeBrmsApiCallRequired")
    private String isInitiativeTypeBrmsApiCallRequired;

    //FUL2-37012 WIP CASE handling for IRP Document
    @JsonProperty("isNewIrpGenerate")
    private  Boolean isNewIrpGenerate;

    private String isTeleSaleCase;

    //FUL2-61094 Whatsapp Consent for POSV msg
    @JsonProperty("whatsappConsent")
    private String whatsappConsent;
    @JsonProperty("paymentGateway")
    private String paymentGateway;

    @JsonProperty("optinStatus")
    private String optinStatus;

    @JsonProperty("isOnboardedProduct")
    private String isOnboardedProduct;

    private String gstWaiverRequired;
	@JsonProperty("isForm60acceptable")
	private Boolean isForm60acceptable;
	@JsonProperty("isCRIFvalidated")
	private Boolean isCRIFvalidated;
	@JsonProperty("isPanModifiedForThanos")
	private Boolean isPanModifiedForThanos;
    @JsonProperty("showHealthQuesOnPosv")
    private String showHealthQuesOnPosv;

	@JsonProperty("manualDirectDebitDocGenerated")
	private boolean manualDirectDebitDocGenerated;
	@JsonProperty("directDebitNewCrIdentifier")
	private Boolean directDebitNewCrIdentifier;

	//*start* FUL2-74120 PTF payment changes
    @JsonProperty("isPtfPayment")
    private String isPtfPayment;
    @Sensitive(MaskType.POLICY_NUM)
    @JsonProperty("ptfPolicyNumber")
    private String ptfPolicyNumber;

    @JsonProperty("typeOfPtfProduct")
    private String typeOfPtfProduct;

    @JsonProperty("ptfPlanName")
    private String ptfPlanName;
    //*end* FUL2-74120 PTF payment changes


    private String journeyType;
    private String diyJourneyType;

    // FUL2-46513
    @JsonProperty("showCovidQuesOnPosv")
    private String showCovidQuesOnPosv;
    
    @JsonProperty("emailSMSConsent")
    private Boolean emailSMSConsent;

    @JsonProperty("isSeniorCitizen")
    private String isSeniorCitizen;

    @JsonProperty("physicalJourneyPosvStatus")
    private String physicalJourneyPosvStatus;
    
    @JsonProperty("isCatChannel")
    private String isCatChannel;

    @JsonProperty("cancelledChequeNeft")
    private boolean cancelledChequeNeft;

    @JsonProperty("pennyDropNameMatch")
    private String pennyDropNameMatch;

    @JsonProperty("pennyDropVerification")
    private String pennyDropVerification;

    @JsonProperty("isBankDetailsReEdited")
    private Boolean isBankDetailsReEdited;

    @JsonProperty("isPhysicalAxisCase")
    private String isPhysicalAxisCase;

    @JsonProperty("agentFraudCheckDetails")
    public AgentFraudCheckDetails agentFraudCheckDetails;

    @JsonProperty("ekycAadhaarDetails")
    private EkycAadhaarDetails ekycAadhaarDetails;

    @JsonProperty("pennyDropConsent")
    private String pennyDropConsent;
   //FUL2-116428 NPS via PRAN
    @JsonProperty("isNpsJourney")
    public String isNpsJourney;
    @JsonProperty("isNpsJourneyInitiated")
    public String isNpsJourneyInitiated;
    @JsonProperty("isPennyDropApplicable")
    private Boolean isPennyDropApplicable;
    @JsonProperty("defenceChannel")
    private String defenceChannel;
    //FUL2-146253 Capital SmalL Finance Bank
    @JsonProperty("isCSFBChannel")
    private String isCSFBChannel;

    @JsonProperty("customerSignDateForCDF")
    private Date customerSignDateForCDF;

    @JsonProperty("sourceChannel")
    private String sourceChannel;

    @JsonProperty("journeyIdentifer")
    private String journeyIdentifer;
    //FUL2-144984
    @JsonProperty("isSolution")
    private String isSolution;

    @JsonProperty("clientAciRiderSa")
    private String clientAciRiderSa;

    @JsonProperty("policyAciRiderSa")
    private String policyAciRiderSa;

    @JsonProperty("deathBenefit")
    private String deathBenefit;

    @JsonProperty("schemeType")
    private String schemeType;

    @JsonProperty("lst2YrCliCiRiderSa")
    private String lst2YrCliCiRiderSa;

    @JsonProperty("physicalJourneyEnabled")
    private String physicalJourneyEnabled ;

    @JsonProperty("premiumPaymentTerm")
    private String premiumPaymentTerm;

    @JsonProperty("policyTerm")
    private String policyTerm;

    @JsonProperty("pcb")
    private String pcb;

    @JsonProperty("isJointLife")
    private String isJointLife;

    @JsonProperty("partnerCareRiderSumAssured")
    private String partnerCareRiderSumAssured;

    @JsonProperty("clientPartnerCareRiderSumAssured")
    private String clientPartnerCareRiderSumAssured;

    @JsonProperty("termPlusRiderSumAssured")
    private String termPlusRiderSumAssured;

    @JsonProperty("clientTermPlusRiderSumAssured")
    private String clientTermPlusRiderSumAssured;

    @JsonProperty("clientSFPSSumAssured")
    private String clientSFPSSumAssured;

    @JsonProperty("clientWopRiderSa")
    private String clientWopRiderSa;

    @JsonProperty("isCombo")
    private String isCombo;
    @JsonProperty("underwritingCount")
    private Integer underwritingCount;

    @JsonProperty("sellerConsentStatus")
    private String sellerConsentStatus;
    
    @JsonProperty("partnerSourceCode")
    private String partnerSourceCode;
    @JsonProperty("isCISApplicable")
    private String isCISApplicable;
    @JsonProperty("mandateAmountPercentage")
    private String mandateAmountPercentage;

    @JsonProperty("isInsurerVerified")
    private Boolean isInsurerVerified;

    @JsonProperty("iibAnsPresent")
    private String iibAnsPresent;
    @JsonProperty("formCFeatureEnabled")
    private String formCFeatureEnabled;

    private Boolean isCurrentInsurerVerified;

    @JsonProperty("isInsurerMajor")
    private String isInsurerMajor;

    @JsonProperty("insuredPosvType")
    private String insuredPosvType;

    @JsonProperty("afypDetails")
    private AfypDetails afypDetails;
    @JsonProperty("replacementSaleDetails")
    private ReplacementSaleDetails replacementSaleDetails;

    @JsonProperty("documentGenerated")
    private String documentGenerated;
    @JsonProperty("spApproverName")
    private String spApproverName;
    @JsonProperty("uniqueId")
    private String uniqueId;

    @JsonProperty("spDigitalConsentSkip")
    private String spDigitalConsentSkip;
    @JsonProperty("spDigitalConsentSkipReason")
    private String spDigitalConsentSkipReason;
    @JsonProperty("unifiedRenewalStatus")
    private String unifiedRenewalStatus;
    @JsonProperty("unifiedPaymentStatus")
    private String unifiedPaymentStatus;
    @JsonProperty("isFalconJourney")
    private String isFalconJourney;
    @JsonProperty("isFalconProduct")
    private String isFalconProduct;
    @JsonProperty("paymentFirstConsent")
    private Boolean paymentFirstConsent;

    public Boolean isPaymentFirstConsent() {
        return paymentFirstConsent;
    }

    public void setPaymentFirstConsent(Boolean paymentFirstConsent) {
        this.paymentFirstConsent = paymentFirstConsent;
    }

    public String getIsFalconJourney() {
        return isFalconJourney;
    }

    public void setIsFalconJourney(String isFalconJourney) {
        this.isFalconJourney = isFalconJourney;
    }

    public String getIsFalconProduct() {
        return isFalconProduct;
    }

    public void setIsFalconProduct(String isFalconProduct) {
        this.isFalconProduct = isFalconProduct;
    }
    public String getSpDigitalConsentSkip() {
        return spDigitalConsentSkip;
    }

    public void setSpDigitalConsentSkip(String spDigitalConsentSkip) {
        this.spDigitalConsentSkip = spDigitalConsentSkip;
    }

    public String getSpDigitalConsentSkipReason() {
        return spDigitalConsentSkipReason;
    }

    public void setSpDigitalConsentSkipReason(String spDigitalConsentSkipReason) {
        this.spDigitalConsentSkipReason = spDigitalConsentSkipReason;
    }
    public String getSellerConsentStatus() {
        return sellerConsentStatus;
    }

    public void setSellerConsentStatus(String sellerConsentStatus) {
        this.sellerConsentStatus = sellerConsentStatus;
    }

    public AdditionalFlags() {
    }

    public AdditionalFlags(int currentActiveScreen, boolean isIllustrationGenerated, boolean isEmailSent,
                           boolean isifscMicrValidated, String isDedupeValidated, String screen1, String screen2,
                           String screen3, String screen4, String screen5, String screen6,
                           boolean isPayorDiffFromPropser, boolean isIllustrationGeneratedOnScreen2,
                           boolean isPaymentDone, String preIssuanceVerificationNumber, String requestSource,
                           String agentKnowsProposerSince, String agentKnowsProposerUnitType, String emailBIStatus,
                           String eBCCDocStatus, JourneyFieldsModificationStatus journeyFieldsModificationStatus,
                           String eBCCDocGenerationStatus, String isAgentSelf, String isMaxEmp, String proposerPreviousPolicyNumber,
                           String stpCounterStatus, boolean isPersonalInfoEdited, boolean isPermAddressEdited,
                           boolean isCommAddressEdited, String issuerConfirmCertificateStatus, String isteleMERStatus
            , String isvideoMERStatus, boolean isteleMERFlag, boolean isvideoMERFlag,
                           AxisDirectDebitPOSVData axisDirectDebitPOSVData, String psmApiStatus,
                           String replacementSale, boolean isYblTelesalesCase,
                           Boolean isEducationPresent, Boolean isSecondAnnuitantPEP, String annuitantReflexive, boolean ebcc,
			boolean isPhotoSupress, String photoType, Boolean isCRIFvalidated, Boolean isForm60acceptable,
			Boolean isPanModifiedForThanos, boolean manualDirectDebitDocGenerated, Boolean directDebitNewCrIdentifier,AgentFraudCheckDetails agentFraudCheckDetails
    ,String isNpsJourney,String isNpsJourneyInitiated,String isCSFBChannel,Date customerSignDateForCDF,String isNewYblReplacementSale, String yblReplacementPolicyStatus, String sourceChannel,Integer underwritingCount,String partnerSourceCode, String isCISApplicable) {
        super();
        this.underwritingCount = underwritingCount;
        this.currentActiveScreen = currentActiveScreen;
        this.isIllustrationGenerated = isIllustrationGenerated;
        this.isEmailSent = isEmailSent;
        this.isifscMicrValidated = isifscMicrValidated;
        this.isDedupeValidated = isDedupeValidated;
        this.screen1 = screen1;
        this.screen2 = screen2;
        this.screen3 = screen3;
        this.screen4 = screen4;
        this.screen5 = screen5;
        this.screen6 = screen6;
        this.isPayorDiffFromPropser = isPayorDiffFromPropser;
        this.isIllustrationGeneratedOnScreen2 = isIllustrationGeneratedOnScreen2;
        this.isPaymentDone = isPaymentDone;
        this.preIssuanceVerificationNumber = preIssuanceVerificationNumber;
        this.requestSource = requestSource;
        this.agentKnowsProposerSince = agentKnowsProposerSince;
        this.agentKnowsProposerUnitType = agentKnowsProposerUnitType;
        this.emailBIStatus = emailBIStatus;
        this.eBCCDocStatus = eBCCDocStatus;
        this.journeyFieldsModificationStatus = journeyFieldsModificationStatus;
        this.eBCCDocGenerationStatus = eBCCDocGenerationStatus;
        this.isAgentSelf = isAgentSelf;
        //FUL2-103459 Additional Flag isMaxEmp to check if user is/was MLI employee
        this.isMaxEmp = isMaxEmp;
        this.proposerPreviousPolicyNumber = proposerPreviousPolicyNumber;
        this.stpCounterStatus = stpCounterStatus;
        this.isPersonalInfoEdited = isPersonalInfoEdited;
        this.isPermAddressEdited = isPermAddressEdited;
        this.isCommAddressEdited = isCommAddressEdited;
        this.issuerConfirmCertificateStatus = issuerConfirmCertificateStatus;
        this.isteleMERStatus = isteleMERStatus;
        this.isvideoMERStatus = isvideoMERStatus;
        this.isteleMERFlag = isteleMERFlag;
        this.isvideoMERFlag = isvideoMERFlag;
        this.axisDirectDebitPOSVData = axisDirectDebitPOSVData;
        this.psmApiStatus = psmApiStatus;
        this.replacementSale = replacementSale;
        this.isNewYblReplacementSale =isNewYblReplacementSale;
        this.yblReplacementPolicyStatus = yblReplacementPolicyStatus;
        this.isYblTelesalesCase = isYblTelesalesCase;
        this.isEducationPresent = isEducationPresent;
        this.isSecondAnnuitantPEP = isSecondAnnuitantPEP;
        this.annuitantReflexive = annuitantReflexive;
	      this.ebcc = ebcc;
        this.isPhotoSupress = isPhotoSupress;
        this.photoType = photoType;
		this.isForm60acceptable = isForm60acceptable;
		this.isCRIFvalidated = isForm60acceptable;
		this.isPanModifiedForThanos = isPanModifiedForThanos;
		this.manualDirectDebitDocGenerated = manualDirectDebitDocGenerated;
		this.directDebitNewCrIdentifier = directDebitNewCrIdentifier;
        this.agentFraudCheckDetails = agentFraudCheckDetails;
        this.isNpsJourney=isNpsJourney;
        this.isNpsJourneyInitiated = isNpsJourneyInitiated;
       this.isCSFBChannel = isCSFBChannel;
       this.customerSignDateForCDF = customerSignDateForCDF;
        this.sourceChannel=sourceChannel;
        this.partnerSourceCode=partnerSourceCode;
        this.isCISApplicable = isCISApplicable;
    }

    public AdditionalFlags(AdditionalFlags additionalFlags) {
	if(additionalFlags!=null)
	{
	this.currentActiveScreen = additionalFlags.currentActiveScreen;
	this.isIllustrationGenerated = additionalFlags.isIllustrationGenerated;
	this.isEmailSent = additionalFlags.isEmailSent;
	this.isifscMicrValidated = additionalFlags.isifscMicrValidated;
	this.isDedupeValidated = additionalFlags.isDedupeValidated;
	this.screen1 = additionalFlags.screen1;
	this.screen2 = additionalFlags.screen2;
	this.screen3 = additionalFlags.screen3;
	this.screen4 = additionalFlags.screen4;
	this.screen5 = additionalFlags.screen5;
	this.screen6 = additionalFlags.screen6;
	this.isPayorDiffFromPropser = additionalFlags.isPayorDiffFromPropser;
	this.isIllustrationGeneratedOnScreen2 = additionalFlags.isIllustrationGeneratedOnScreen2;
	this.isPaymentDone = additionalFlags.isPaymentDone;
	this.preIssuanceVerificationNumber = additionalFlags.preIssuanceVerificationNumber;
	this.requestSource = additionalFlags.requestSource;
	this.agentKnowsProposerSince = additionalFlags.agentKnowsProposerSince;
	this.agentKnowsProposerUnitType = additionalFlags.agentKnowsProposerUnitType;
	this.emailBIStatus = additionalFlags.emailBIStatus;
	this.eBCCDocStatus = additionalFlags.eBCCDocStatus;
	this.journeyFieldsModificationStatus = additionalFlags.journeyFieldsModificationStatus;
	this.eBCCDocGenerationStatus = additionalFlags.eBCCDocGenerationStatus;
	this.isAgentSelf = additionalFlags.isAgentSelf;
    //FUL2-103459 Additional Flag isMaxEmp to check if user is/was MLI employee
    this.isMaxEmp = additionalFlags.isMaxEmp;
	this.proposerPreviousPolicyNumber = additionalFlags.proposerPreviousPolicyNumber;
	this.stpCounterStatus = additionalFlags.stpCounterStatus;
	this.isPersonalInfoEdited = additionalFlags.isPersonalInfoEdited;
	this.isPermAddressEdited = additionalFlags.isPermAddressEdited;
	this.isCommAddressEdited = additionalFlags.isCommAddressEdited;
	this.issuerConfirmCertificateStatus = additionalFlags.issuerConfirmCertificateStatus;
	this.isteleMERStatus=additionalFlags.isteleMERStatus;
	this.isvideoMERStatus=additionalFlags.isvideoMERStatus;
	this.isteleMERFlag=additionalFlags.isteleMERFlag;
	this.isvideoMERFlag=additionalFlags.isvideoMERFlag;
	this.axisDirectDebitPOSVData = additionalFlags.axisDirectDebitPOSVData;
	this.psmApiStatus = additionalFlags.psmApiStatus;
	//FUL2-11834 to identify that whether this is case of YblTeleSales
    this.isYblTelesalesCase=additionalFlags.isYblTelesalesCase;
    this.isEducationPresent=additionalFlags.isEducationPresent;
    this.isSecondAnnuitantPEP = additionalFlags.isSecondAnnuitantPEP;
    this.annuitantReflexive = additionalFlags.annuitantReflexive;
	  this.ebcc=additionalFlags.ebcc;
        this.isPEPDisabled = additionalFlags.isPEPDisabled;
		this.isProspectiveYBLCustomer = additionalFlags.isProspectiveYBLCustomer;
		this.whatsappConsent = additionalFlags.whatsappConsent;
		this.optinStatus = additionalFlags.optinStatus;
        this.gstWaiverRequired= additionalFlags.gstWaiverRequired;
		this.isForm60acceptable = additionalFlags.isForm60acceptable;
		this.isCRIFvalidated = additionalFlags.isCRIFvalidated;
		this.isPanModifiedForThanos = additionalFlags.isPanModifiedForThanos;
		this.manualDirectDebitDocGenerated = additionalFlags.manualDirectDebitDocGenerated;
		this.directDebitNewCrIdentifier = additionalFlags.directDebitNewCrIdentifier;
        this.isPtfPayment = additionalFlags.isPtfPayment;
		this.ptfPlanName = additionalFlags.ptfPlanName;
		this.ptfPolicyNumber = additionalFlags.ptfPolicyNumber;
		this.typeOfPtfProduct = additionalFlags.typeOfPtfProduct;
		this.emailSMSConsent = additionalFlags.emailSMSConsent;
		this.isSeniorCitizen = additionalFlags.isSeniorCitizen;
		this.physicalJourneyPosvStatus = additionalFlags.physicalJourneyPosvStatus;
        this.cancelledChequeNeft = additionalFlags.cancelledChequeNeft;
        this.pennyDropNameMatch = additionalFlags.pennyDropNameMatch;
        this.pennyDropVerification = additionalFlags.pennyDropVerification;
        this.isBankDetailsReEdited = additionalFlags.isBankDetailsReEdited;
		this.isPhysicalAxisCase = isPhysicalAxisCase;
        this.agentFraudCheckDetails = agentFraudCheckDetails;
        this.pennyDropConsent = additionalFlags.pennyDropConsent;
        this.isNpsJourney= additionalFlags.isNpsJourney;
        this.isNpsJourneyInitiated = additionalFlags.isNpsJourneyInitiated;
        this.isCSFBChannel = additionalFlags.isCSFBChannel;
        this.customerSignDateForCDF = additionalFlags.customerSignDateForCDF;

		this.isCatChannel = additionalFlags.isCatChannel;
		this.defenceChannel = additionalFlags.defenceChannel;
		this.partnerSourceCode = additionalFlags.partnerSourceCode;
        this.isCISApplicable = additionalFlags.isCISApplicable;
    }
    }


    public AfypDetails getAfypDetails() {
        return afypDetails;
    }

    public void setAfypDetails(AfypDetails afypDetails) {
        this.afypDetails = afypDetails;
    }

    public ReplacementSaleDetails getReplacementSaleDetails() {
        return replacementSaleDetails;
    }

    public void setReplacementSaleDetails(ReplacementSaleDetails replacementSaleDetails) {
        this.replacementSaleDetails = replacementSaleDetails;
    }

    public String getIsNpsJourneyInitiated() {
        return isNpsJourneyInitiated;
    }

    public void setIsNpsJourneyInitiated(String isNpsJourneyInitiated) {
        this.isNpsJourneyInitiated = isNpsJourneyInitiated;
    }

    public String getIsCSFBChannel() {
        return isCSFBChannel;
    }

    public void setIsCSFBChannel(String isCSFBChannel) {
        this.isCSFBChannel = isCSFBChannel;
    }

    public String getMandateAmountPercentage() {
        return mandateAmountPercentage;
    }

    public void setMandateAmountPercentage(String mandateAmountPercentage) {
        this.mandateAmountPercentage = mandateAmountPercentage;
    }

    public Date getCustomerSignDateForCDF() {
        return customerSignDateForCDF;
    }

    public void setCustomerSignDateForCDF(Date customerSignDateForCDF) {
        this.customerSignDateForCDF = customerSignDateForCDF;
    }

    public String getShowHealthQuesOnPosv() {
        return showHealthQuesOnPosv;
    }
    public String getIsOnboardedProduct() {
        return isOnboardedProduct;
    }

    public void setShowHealthQuesOnPosv(String showHealthQuesOnPosv) {
        this.showHealthQuesOnPosv = showHealthQuesOnPosv;
    }
    public void setIsOnboardedProduct(String isOnboardedProduct) {
        this.isOnboardedProduct = isOnboardedProduct;
    }

    public int getCurrentActiveScreen() {
	return currentActiveScreen;
    }

    public void setCurrentActiveScreen(int currentActiveScreen) {
	this.currentActiveScreen = currentActiveScreen;
    }

    public boolean isIllustrationGenerated() {
	return isIllustrationGenerated;
    }

    public void setIllustrationGenerated(boolean isIllustrationGenerated) {
	this.isIllustrationGenerated = isIllustrationGenerated;
    }

    public boolean isEmailSent() {
	return isEmailSent;
    }

    public void setEmailSent(boolean isEmailSent) {
	this.isEmailSent = isEmailSent;
    }
    public Integer getUnderwritingCount() {
        return underwritingCount;
    }

    public void setUnderwritingCount(Integer underwritingCount) {
        this.underwritingCount = underwritingCount;
    }
    public boolean isIsifscMicrValidated() {
	return isifscMicrValidated;
    }

    public void setIsifscMicrValidated(boolean isifscMicrValidated) {
	this.isifscMicrValidated = isifscMicrValidated;
    }

    public String getIsDedupeValidated() {
	return isDedupeValidated;
    }

    public void setIsDedupeValidated(String isDedupeValidated) {
	this.isDedupeValidated = isDedupeValidated;
    }

    public String getScreen1() {
	return screen1;
    }

    public void setScreen1(String screen1) {
	this.screen1 = screen1;
    }

    public String getScreen2() {
	return screen2;
    }

    public void setScreen2(String screen2) {
	this.screen2 = screen2;
    }

    public String getScreen3() {
	return screen3;
    }

    public void setScreen3(String screen3) {
	this.screen3 = screen3;
    }

    public String getScreen4() {
	return screen4;
    }

    public void setScreen4(String screen4) {
	this.screen4 = screen4;
    }

    public String getScreen5() {
	return screen5;
    }

    public void setScreen5(String screen5) {
	this.screen5 = screen5;
    }

    public String getScreen6() {
	return screen6;
    }

    public void setScreen6(String screen6) {
	this.screen6 = screen6;
    }

    public boolean isPayorDiffFromPropser() {
	return isPayorDiffFromPropser;
    }

    public void setPayorDiffFromPropser(boolean isPayorDiffFromPropser) {
	this.isPayorDiffFromPropser = isPayorDiffFromPropser;
    }

    public boolean isIllustrationGeneratedOnScreen2() {
	return isIllustrationGeneratedOnScreen2;
    }

    public void setIllustrationGeneratedOnScreen2(boolean isIllustrationGeneratedOnScreen2) {
	this.isIllustrationGeneratedOnScreen2 = isIllustrationGeneratedOnScreen2;
    }

    public boolean isPaymentDone() {
	return isPaymentDone;
    }

    public void setPaymentDone(boolean isPaymentDone) {
	this.isPaymentDone = isPaymentDone;
    }

    public String getPreIssuanceVerificationNumber() {
	return preIssuanceVerificationNumber;
    }

    public void setPreIssuanceVerificationNumber(String preIssuanceVerificationNumber) {
	this.preIssuanceVerificationNumber = preIssuanceVerificationNumber;
    }

    public String getRequestSource() {
	return requestSource;
    }

    public void setRequestSource(String requestSource) {
	this.requestSource = requestSource;
    }

    public String getAgentKnowsProposerSince() {
	return agentKnowsProposerSince;
    }

    public void setAgentKnowsProposerSince(String agentKnowsProposerSince) {
	this.agentKnowsProposerSince = agentKnowsProposerSince;
    }

    public String getAgentKnowsProposerUnitType() {
	return agentKnowsProposerUnitType;
    }

    public void setAgentKnowsProposerUnitType(String agentKnowsProposerUnitType) {
	this.agentKnowsProposerUnitType = agentKnowsProposerUnitType;
    }

    public String getEmailBIStatus() {
	return emailBIStatus;
    }

    public void setEmailBIStatus(String emailBIStatus) {
	this.emailBIStatus = emailBIStatus;
    }

    public String geteBCCDocStatus() {
	return eBCCDocStatus;
    }

    public void seteBCCDocStatus(String eBCCDocStatus) {
	this.eBCCDocStatus = eBCCDocStatus;
    }

    public JourneyFieldsModificationStatus getJourneyFieldsModificationStatus() {
	return journeyFieldsModificationStatus;
    }

    public void setJourneyFieldsModificationStatus(JourneyFieldsModificationStatus journeyFieldsModificationStatus) {
	this.journeyFieldsModificationStatus = journeyFieldsModificationStatus;
    }

    public String geteBCCDocGenerationStatus() {
	return eBCCDocGenerationStatus;
    }

    public void seteBCCDocGenerationStatus(String eBCCDocGenerationStatus) {
	this.eBCCDocGenerationStatus = eBCCDocGenerationStatus;
    }

    public String isAgentSelf() {
	return isAgentSelf;
    }

    public void setAgentSelf(String isAgentSelf) {
	this.isAgentSelf = isAgentSelf;
    }

    //FUL2-103459 Additional Flag isMaxEmp to check if user is/was MLI employee

    //FUL2-116960 wip case handling
    @JsonProperty("isSspSwissReCase")
    private String isSspSwissReCase;

    //FUL2-208757 IRDA Regulation Changes
    @JsonProperty("vernacularDeclaration")
    private VernacularDeclaration vernacularDeclaration;

    @JsonProperty("disabilityDeclaration")
    private DisabilityDeclaration disabilityDeclaration;

    public String getIsSspSwissReCase() {
        return isSspSwissReCase;
    }

    public void setIsSspSwissReCase(String isSspSwissReCase) {
        this.isSspSwissReCase = isSspSwissReCase;
    }

    public String getIsMaxEmp() {
        return isMaxEmp;
    }

    public void setIsMaxEmp(String isMaxEmp) {
        this.isMaxEmp = isMaxEmp;
    }

    public String getProposerPreviousPolicyNumber() {
	return proposerPreviousPolicyNumber;
    }

    public void setProposerPreviousPolicyNumber(String proposerPreviousPolicyNumber) {
	this.proposerPreviousPolicyNumber = proposerPreviousPolicyNumber;
    }

    public String getIsAgentSelf() {
        return isAgentSelf;
    }

    public void setIsAgentSelf(String isAgentSelf) {
        this.isAgentSelf = isAgentSelf;
    }

    public String getStpCounterStatus() {
        return stpCounterStatus;
    }

    public void setStpCounterStatus(String stpCounterStatus) {
        this.stpCounterStatus = stpCounterStatus;
    }
    

    public boolean isPersonalInfoEdited() {
        return isPersonalInfoEdited;
    }



    public void setPersonalInfoEdited(boolean isPersonalInfoEdited) {
        this.isPersonalInfoEdited = isPersonalInfoEdited;
    }



    public boolean isPermAddressEdited() {
        return isPermAddressEdited;
    }



    public void setPermAddressEdited(boolean isPermAddressEdited) {
        this.isPermAddressEdited = isPermAddressEdited;
    }

    public boolean isCommAddressEdited() {
        return isCommAddressEdited;
    }

    public void setCommAddressEdited(boolean isCommAddressEdited) {
        this.isCommAddressEdited = isCommAddressEdited;
    }

    public String getIssuerConfirmCertificateStatus() {
		return issuerConfirmCertificateStatus;
	}



	public void setIssuerConfirmCertificateStatus(String issuerConfirmCertificateStatus) {
		this.issuerConfirmCertificateStatus = issuerConfirmCertificateStatus;
	}

	public String getIsteleMERStatus() {
		return isteleMERStatus;
	}

	public void setIsteleMERStatus(String isteleMERStatus) {
		this.isteleMERStatus = isteleMERStatus;
	}

	public String getIsvideoMERStatus() {
		return isvideoMERStatus;
	}

	public void setIsvideoMERStatus(String isvideoMERStatus) {
		this.isvideoMERStatus = isvideoMERStatus;
	}

	public boolean isIsteleMERFlag() {
		return isteleMERFlag;
	}

	public void setIsteleMERFlag(boolean isteleMERFlag) {
		this.isteleMERFlag = isteleMERFlag;
	}

	public boolean isIsvideoMERFlag() {
		return isvideoMERFlag;
	}

	public void setVideoMERFlag(boolean isvideoMERFlag) {
		this.isvideoMERFlag = isvideoMERFlag;
	}

	public AxisDirectDebitPOSVData getAxisDirectDebitPOSVData() {
		return axisDirectDebitPOSVData;
	}

	public void setAxisDirectDebitPOSVData(AxisDirectDebitPOSVData axisDirectDebitPOSVData) {
		this.axisDirectDebitPOSVData = axisDirectDebitPOSVData;
	}
    //FUL2-11834 to identify that whether this is case of YblTeleSales
    public boolean isYblTelesalesCase() {
        return isYblTelesalesCase;
    }

    public void setYblTelesalesCase(boolean yblTelesalesCase) {
        isYblTelesalesCase = yblTelesalesCase;
    }

  public boolean getEbcc() {
    return ebcc;
  }

  public void setEbcc(boolean ebcc) {
    this.ebcc = ebcc;
  }

    public String getPsmApiStatus() {
        return psmApiStatus;
    }

    public void setPsmApiStatus(String psmApiStatus) {
        this.psmApiStatus = psmApiStatus;
    }

    public String getReplacementSale() {
        return replacementSale;
    }

    public void setReplacementSale(String replacementSale) {
        this.replacementSale = replacementSale;
    }

    public String getIsYblNewReplacementSale() {
        return isNewYblReplacementSale;
    }

    public void setIsNewYblReplacementSale(String isNewYblReplacementSale) {
        this.isNewYblReplacementSale = isNewYblReplacementSale;
    }

    public String getYblReplacementPolicyStatus() {
        return yblReplacementPolicyStatus;
    }

    public void setYblReplacementPolicyStatus(String yblReplacementPolicyStatus) {
        this.yblReplacementPolicyStatus = yblReplacementPolicyStatus;
    }

    public void setIsvideoMERFlag(boolean isvideoMERFlag) {
        this.isvideoMERFlag = isvideoMERFlag;
    }

    public Boolean getSecondAnnuitantPEP() {
        return isSecondAnnuitantPEP;
    }

    public void setSecondAnnuitantPEP(Boolean secondAnnuitantPEP) {
        isSecondAnnuitantPEP = secondAnnuitantPEP;
    }

    public String getAnnuitantReflexive() {
        return annuitantReflexive;
    }

    public void setAnnuitantReflexive(String annuitantReflexive) {
        this.annuitantReflexive = annuitantReflexive;
    }

    public Boolean getEducationPresent() {
        return isEducationPresent;
    }

    public void setEducationPresent(Boolean educationPresent) {
        isEducationPresent = educationPresent;
    }

    public boolean isPosEmailStatus() {
        return posEmailStatus;
    }

    public void setPosEmailStatus(boolean posEmailStatus) {
        this.posEmailStatus = posEmailStatus;
    }

    public String getIsSellerDeclarationApplicable() {
        return isSellerDeclarationApplicable;
    }

    public void setIsSellerDeclarationApplicable(String isSellerDeclarationApplicable) {
        this.isSellerDeclarationApplicable = isSellerDeclarationApplicable;
    }

    public String getGstWaiverRequired() {
        return gstWaiverRequired;
    }

    public void setGstWaiverRequired(String gstWaiverRequired) {
        this.gstWaiverRequired = gstWaiverRequired;
    }

    public boolean isPhotoSupress() {
		return isPhotoSupress;
	}

	public void setPhotoSupress(boolean isPhotoSupress) {
		this.isPhotoSupress = isPhotoSupress;
	}

	public String getPhotoType() {
		return photoType;
	}

	public void setPhotoType(String photoType) {
		this.photoType = photoType;
	}
	/* FUL2-25833 Video POSV - dolphin push story */
	public String getReasonForVideoPosv() {
		return reasonForVideoPosv;
	}

	public void setReasonForVideoPosv(String reasonForVideoPosv) {
		this.reasonForVideoPosv = reasonForVideoPosv;
	}

    public String getIsPEPDisabled() {return isPEPDisabled;}

    public void setIsPEPDisabled(String isPEPDisabled) {this.isPEPDisabled = isPEPDisabled;}

	public Boolean getIsProspectiveYBLCustomer() {
		return isProspectiveYBLCustomer;
	}

	public void setIsProspectiveYBLCustomer(Boolean isProspectiveYBLCustomer) {
		this.isProspectiveYBLCustomer = isProspectiveYBLCustomer;
	}

    public Boolean getNewIrpGenerate() {
        return isNewIrpGenerate;
    }

    public void setNewIrpGenerate(Boolean newIrpGenerate) {
        isNewIrpGenerate = newIrpGenerate;
    }

	public String getIsInitiativeTypeBrmsApiCallRequired() {
		return isInitiativeTypeBrmsApiCallRequired;
	}

	public void setIsInitiativeTypeBrmsApiCallRequired(String isInitiativeTypeBrmsApiCallRequired) {
		this.isInitiativeTypeBrmsApiCallRequired = isInitiativeTypeBrmsApiCallRequired;
	}


    public String getIsTeleSaleCase() {
        return isTeleSaleCase;
    }

    public void setIsTeleSaleCase(String isTeleSaleCase) {
        this.isTeleSaleCase = isTeleSaleCase;
    }

    public String getWhatsappConsent() {
		return whatsappConsent;
	}

	public void setWhatsappConsent(String whatsappConsent) {
		this.whatsappConsent = whatsappConsent;
	}

	public String getOptinStatus() {
		return optinStatus;
	}

	public void setOptinStatus(String optinStatus) {
		this.optinStatus = optinStatus;
	}

	public Boolean getIsForm60acceptable() {
		return isForm60acceptable;
	}

	public void setIsForm60acceptable(Boolean isForm60acceptable) {
		this.isForm60acceptable = isForm60acceptable;
	}

	public Boolean getIsCRIFvalidated() {
		return isCRIFvalidated;
	}

	public void setIsCRIFvalidated(Boolean isCRIFvalidated) {
		this.isCRIFvalidated = isCRIFvalidated;
	}

	public Boolean getIsPanModifiedForThanos() {
		return isPanModifiedForThanos;
	}

	public void setIsPanModifiedForThanos(Boolean isPanModifiedForThanos) {
		this.isPanModifiedForThanos = isPanModifiedForThanos;
	}

	public boolean isManualDirectDebitDocGenerated() {
		return manualDirectDebitDocGenerated;
	}

	public void setManualDirectDebitDocGenerated(boolean manualDirectDebitDocGenerated) {
		this.manualDirectDebitDocGenerated = manualDirectDebitDocGenerated;
	}

	public Boolean getDirectDebitNewCrIdentifier() {
		return directDebitNewCrIdentifier;
	}

	public void setDirectDebitNewCrIdentifier(Boolean directDebitNewCrIdentifier) {
		this.directDebitNewCrIdentifier = directDebitNewCrIdentifier;
	}

	public String getIsPtfPayment() {
		return isPtfPayment;
	}

	public void setIsPtfPayment(String isPtfPayment) {
		this.isPtfPayment = isPtfPayment;
	}

	public String getPtfPolicyNumber() {
		return ptfPolicyNumber;
	}

	public void setPtfPolicyNumber(String ptfPolicyNumber) {
		this.ptfPolicyNumber = ptfPolicyNumber;
	}

	public String getTypeOfPtfProduct() {
		return typeOfPtfProduct;
	}

	public void setTypeOfPtfProduct(String typeOfPtfProduct) {
		this.typeOfPtfProduct = typeOfPtfProduct;
	}

	public String getPtfPlanName() {
		return ptfPlanName;
	}

	public void setPtfPlanName(String ptfPlanName) {
		this.ptfPlanName = ptfPlanName;
	}

	public String getShowCovidQuesOnPosv() {
		return showCovidQuesOnPosv;
	}



	public void setShowCovidQuesOnPosv(String showCovidQuesOnPosv) {
		this.showCovidQuesOnPosv = showCovidQuesOnPosv;
	}

    public String getJourneyType() {
        return journeyType;
    }

    public void setJourneyType(String journeyType) {
        this.journeyType = journeyType;
    }

    public String getDiyJourneyType() {
        return diyJourneyType;
    }

    public void setDiyJourneyType(String diyJourneyType) {
        this.diyJourneyType = diyJourneyType;
    }

    public Boolean getEmailSMSConsent() {
        return emailSMSConsent;
    }

    public void setEmailSMSConsent(Boolean emailSMSConsent) {
        this.emailSMSConsent = emailSMSConsent;
    }

    public String getIsSeniorCitizen() {
        return isSeniorCitizen;
    }

    public void setIsSeniorCitizen(String isSeniorCitizen) {
        this.isSeniorCitizen = isSeniorCitizen;
    }

    public String getPhysicalJourneyPosvStatus() {
        return physicalJourneyPosvStatus;
    }

    public void setPhysicalJourneyPosvStatus(String physicalJourneyPosvStatus) {
        this.physicalJourneyPosvStatus = physicalJourneyPosvStatus;
    }
    
    public String getIsCatChannel() {
        return isCatChannel;
    }

    public void setIsCatChannel(String isCatChannel) {
        this.isCatChannel = isCatChannel;
    }
    
    public String getDefenceChannel() {
        return defenceChannel;
    }

    public void setDefenceChannel(String defenceChannel) {
        this.defenceChannel = defenceChannel;
    }

    public boolean isCancelledChequeNeft() {
        return cancelledChequeNeft;
    }

    public void setCancelledChequeNeft(boolean cancelledChequeNeft) {
        this.cancelledChequeNeft = cancelledChequeNeft;
    }

    public String getPennyDropNameMatch() {
        return pennyDropNameMatch;
    }

    public void setPennyDropNameMatch(String pennyDropNameMatch) {
        this.pennyDropNameMatch = pennyDropNameMatch;
    }

    public String getPennyDropVerification() {
        return pennyDropVerification;
    }

    public void setPennyDropVerification(String pennyDropVerification) {
        this.pennyDropVerification = pennyDropVerification;
    }

    public Boolean getBankDetailsReEdited() {
        return isBankDetailsReEdited;
    }

    public void setBankDetailsReEdited(Boolean bankDetailsReEdited) {
        isBankDetailsReEdited = bankDetailsReEdited;
    }
    public String getIsPhysicalAxisCase() {
        return isPhysicalAxisCase;
    }

    public void setIsPhysicalAxisCase(String isPhysicalAxisCase) {
        this.isPhysicalAxisCase = isPhysicalAxisCase;
    }

    public AgentFraudCheckDetails getAgentFraudCheckDetails() {
        return agentFraudCheckDetails;
    }

    public void setAgentFraudCheckDetails(AgentFraudCheckDetails agentFraudCheckDetails) {
        this.agentFraudCheckDetails = agentFraudCheckDetails;
    }

    public String getPennyDropConsent() {
		return pennyDropConsent;
	}

	public void setPennyDropConsent(String pennyDropConsent) {
		this.pennyDropConsent = pennyDropConsent;
	}

    public String getIsNpsJourney() {
        return isNpsJourney;
    }

    public void setIsNpsJourney(String isNpsJourney) {
        this.isNpsJourney = isNpsJourney;
    }

    public Boolean getIsPennyDropApplicable() {
		return isPennyDropApplicable;
	}

	public void setIsPennyDropApplicable(Boolean isPennyDropApplicable) {
		this.isPennyDropApplicable = isPennyDropApplicable;
	}

    public String getSourceChannel() {
        return sourceChannel;
    }

    public void setSourceChannel(String sourceChannel) {
        this.sourceChannel = sourceChannel;
    }

    public String getJourneyIdentifer() {
        return journeyIdentifer;
    }

    public void setJourneyIdentifer(String journeyIdentifer) {
        this.journeyIdentifer = journeyIdentifer;
    }

    public EkycAadhaarDetails getEkycAadhaarDetails() {
        return ekycAadhaarDetails;
    }

    public void setEkycAadhaarDetails(EkycAadhaarDetails ekycAadhaarDetails) {
        this.ekycAadhaarDetails = ekycAadhaarDetails;
    }

    public String getIsSolution() {
        return isSolution;
    }

    public void setIsSolution(String isSolution) {
        this.isSolution = isSolution;
    }

    public String getClientAciRiderSa() {
        return clientAciRiderSa;
    }

    public void setClientAciRiderSa(String clientAciRiderSa) {
        this.clientAciRiderSa = clientAciRiderSa;
    }

    public String getPolicyAciRiderSa() {
        return policyAciRiderSa;
    }

    public void setPolicyAciRiderSa(String policyAciRiderSa) {
        this.policyAciRiderSa = policyAciRiderSa;
    }

    public String getDeathBenefit() {
        return deathBenefit;
    }

    public void setDeathBenefit(String deathBenefit) {
        this.deathBenefit = deathBenefit;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }

    public String getLst2YrCliCiRiderSa() {
        return lst2YrCliCiRiderSa;
    }

    public void setLst2YrCliCiRiderSa(String lst2YrCliCiRiderSa) {
        this.lst2YrCliCiRiderSa = lst2YrCliCiRiderSa;
    }

    public String getPhysicalJourneyEnabled() {
        return physicalJourneyEnabled;
    }

    public void setPhysicalJourneyEnabled(String physicalJourneyEnabled) {
        this.physicalJourneyEnabled = physicalJourneyEnabled;
    }

    public String getPremiumPaymentTerm() {
        return premiumPaymentTerm;
    }

    public void setPremiumPaymentTerm(String premiumPaymentTerm) {
        this.premiumPaymentTerm = premiumPaymentTerm;
    }

    public String getPolicyTerm() {
        return policyTerm;
    }

    public void setPolicyTerm(String policyTerm) {
        this.policyTerm = policyTerm;
    }

    public String getPcb() {
        return pcb;
    }

    public void setPcb(String pcb) {
        this.pcb = pcb;
    }

    public String getIsJointLife() {
        return isJointLife;
    }

    public void setIsJointLife(String isJointLife) {
        this.isJointLife = isJointLife;
    }

    public String getPartnerCareRiderSumAssured() {
        return partnerCareRiderSumAssured;
    }

    public void setPartnerCareRiderSumAssured(String partnerCareRiderSumAssured) {
        this.partnerCareRiderSumAssured = partnerCareRiderSumAssured;
    }

    public String getClientPartnerCareRiderSumAssured() {
        return clientPartnerCareRiderSumAssured;
    }

    public void setClientPartnerCareRiderSumAssured(String clientPartnerCareRiderSumAssured) {
        this.clientPartnerCareRiderSumAssured = clientPartnerCareRiderSumAssured;
    }

    public String getTermPlusRiderSumAssured() {
        return termPlusRiderSumAssured;
    }

    public void setTermPlusRiderSumAssured(String termPlusRiderSumAssured) {
        this.termPlusRiderSumAssured = termPlusRiderSumAssured;
    }

    public String getClientTermPlusRiderSumAssured() {
        return clientTermPlusRiderSumAssured;
    }

    public void setClientTermPlusRiderSumAssured(String clientTermPlusRiderSumAssured) {
        this.clientTermPlusRiderSumAssured = clientTermPlusRiderSumAssured;
    }

    public String getClientSFPSSumAssured() {
        return clientSFPSSumAssured;
    }

    public void setClientSFPSSumAssured(String clientSFPSSumAssured) {
        this.clientSFPSSumAssured = clientSFPSSumAssured;
    }

    public String getClientWopRiderSa() {
        return clientWopRiderSa;
    }

    public void setClientWopRiderSa(String clientWopRiderSa) {
        this.clientWopRiderSa = clientWopRiderSa;
    }

    public String getIsCombo() {
        return isCombo;
    }

    public void setIsCombo(String isCombo) {
        this.isCombo = isCombo;
    }
    
    public String getPartnerSourceCode() {
        return partnerSourceCode;
    }

    public void setPartnerSourceCode(String partnerSourceCode) {
        this.partnerSourceCode = partnerSourceCode;
    }

    public String getEkycProposerConsent() {
        return ekycProposerConsent;
    }

    public void setEkycProposerConsent(String ekycProposerConsent) {
        this.ekycProposerConsent = ekycProposerConsent;
    }

    public String getEkycInsuredConsent() {
        return ekycInsuredConsent;
    }

    public void setEkycInsuredConsent(String ekycInsuredConsent) {
        this.ekycInsuredConsent = ekycInsuredConsent;
    }

    public String getEkycPayorConsent() {
        return ekycPayorConsent;
    }

    public void setEkycPayorConsent(String ekycPayorConsent) {
        this.ekycPayorConsent = ekycPayorConsent;
    }

    public String getInsuredDOBRequired() { return insuredDOBRequired; }

    public void setInsuredDOBRequired(String insuredDOBRequired) { this.insuredDOBRequired = insuredDOBRequired; }

    public String getIsCISApplicable() {
        return isCISApplicable;
    }

    public void setIsCISApplicable(String isCISApplicable) {
        this.isCISApplicable = isCISApplicable;
    }
    public VernacularDeclaration getVernacularDeclaration() {
        return vernacularDeclaration;
    }

    public void setVernacularDeclaration(VernacularDeclaration vernacularDeclaration) {
        this.vernacularDeclaration = vernacularDeclaration;
    }

    public DisabilityDeclaration getDisabilityDeclaration() {
        return disabilityDeclaration;
    }

    public void setDisabilityDeclaration(DisabilityDeclaration disabilityDeclaration) {
        this.disabilityDeclaration = disabilityDeclaration;
    }

    public Boolean getInsurerVerified() {
        return isInsurerVerified;
    }

    public void setInsurerVerified(Boolean insurerVerified) {
        isInsurerVerified = insurerVerified;
    }

    public String getIibAnsPresent() {
        return iibAnsPresent;
    }

    public Boolean getCurrentInsurerVerified() {
        return isCurrentInsurerVerified;
    }

    public void setCurrentInsurerVerified(Boolean currentInsurerVerified) {
        isCurrentInsurerVerified = currentInsurerVerified;
    }

    public String getFormCFeatureEnabled() {
        return formCFeatureEnabled;
    }

    public void setFormCFeatureEnabled(String formCFeatureEnabled) {
        this.formCFeatureEnabled = formCFeatureEnabled;
    }

    public String getIsInsurerMajor() {
        return isInsurerMajor;
    }

    public void setIsInsurerMajor(String isInsurerMajor) {
        this.isInsurerMajor = isInsurerMajor;
    }

    public String getInsuredPosvType() {
        return insuredPosvType;
    }

    public void setInsuredPosvType(String insuredPosvType) {
        this.insuredPosvType = insuredPosvType;
    }

    public String getSpApproverName() {
        return spApproverName;
    }

    public void setSpApproverName(String spApproverName) {
        this.spApproverName = spApproverName;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getDocumentGenerated() {
        return documentGenerated;
    }

    public void setDocumentGenerated(String documentGenerated) {
        this.documentGenerated = documentGenerated;
    }

    public boolean isRenewelPaymentDone() { return isRenewelPaymentDone; }

    public void setRenewelPaymentDone(boolean renewelPaymentDone) { isRenewelPaymentDone = renewelPaymentDone; }

    public String getUnifiedRenewalStatus() { return unifiedRenewalStatus; }

    public void setUnifiedRenewalStatus(String unifiedRenewalStatus) { this.unifiedRenewalStatus = unifiedRenewalStatus; }

    public String getUnifiedPaymentStatus() { return unifiedPaymentStatus; }

    public void setUnifiedPaymentStatus(String unifiedPaymentStatus) { this.unifiedPaymentStatus = unifiedPaymentStatus; }

    public String getPaymentGateway() { return paymentGateway; }

    public void setPaymentGateway(String paymentGateway) { this.paymentGateway = paymentGateway; }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "AdditionalFlags{" +
                "currentActiveScreen=" + currentActiveScreen +
                ", isIllustrationGenerated=" + isIllustrationGenerated +
                ", isEmailSent=" + isEmailSent +
                ", isifscMicrValidated=" + isifscMicrValidated +
                ", isDedupeValidated='" + isDedupeValidated + '\'' +
                ", screen1='" + screen1 + '\'' +
                ", screen2='" + screen2 + '\'' +
                ", screen3='" + screen3 + '\'' +
                ", screen4='" + screen4 + '\'' +
                ", screen5='" + screen5 + '\'' +
                ", screen6='" + screen6 + '\'' +
                ", isPayorDiffFromPropser=" + isPayorDiffFromPropser +
                ", isIllustrationGeneratedOnScreen2=" + isIllustrationGeneratedOnScreen2 +
                ", isPaymentDone=" + isPaymentDone +
                ", preIssuanceVerificationNumber='" + preIssuanceVerificationNumber + '\'' +
                ", requestSource='" + requestSource + '\'' +
                ", agentKnowsProposerSince='" + agentKnowsProposerSince + '\'' +
                ", agentKnowsProposerUnitType='" + agentKnowsProposerUnitType + '\'' +
                ", emailBIStatus='" + emailBIStatus + '\'' +
                ", eBCCDocStatus='" + eBCCDocStatus + '\'' +
                ", journeyFieldsModificationStatus=" + journeyFieldsModificationStatus +
                ", eBCCDocGenerationStatus='" + eBCCDocGenerationStatus + '\'' +
                ", isAgentSelf='" + isAgentSelf + '\'' +
                ", isMaxEmp='" + isMaxEmp + '\'' +
                ", proposerPreviousPolicyNumber='" + proposerPreviousPolicyNumber + '\'' +
                ", stpCounterStatus='" + stpCounterStatus + '\'' +
                ", ekycProposerConsent='" + ekycProposerConsent + '\'' +
                ", ekycInsuredConsent='" + ekycInsuredConsent + '\'' +
                ", ekycPayorConsent='" + ekycPayorConsent + '\'' +
                ", insuredDOBRequired='" + insuredDOBRequired + '\'' +
                ", isPersonalInfoEdited=" + isPersonalInfoEdited +
                ", isPermAddressEdited=" + isPermAddressEdited +
                ", isCommAddressEdited=" + isCommAddressEdited +
                ", issuerConfirmCertificateStatus='" + issuerConfirmCertificateStatus + '\'' +
                ", isteleMERStatus='" + isteleMERStatus + '\'' +
                ", isvideoMERStatus='" + isvideoMERStatus + '\'' +
                ", isteleMERFlag=" + isteleMERFlag +
                ", isvideoMERFlag=" + isvideoMERFlag +
                ", axisDirectDebitPOSVData=" + axisDirectDebitPOSVData +
                ", psmApiStatus='" + psmApiStatus + '\'' +
                ", replacementSale='" + replacementSale + '\'' +
                ", isNewYblReplacementSale='" + isNewYblReplacementSale + '\'' +
                ", yblReplacementPolicyStatus='" + yblReplacementPolicyStatus + '\'' +
                ", ebcc=" + ebcc +
                ", isYblTelesalesCase=" + isYblTelesalesCase +
                ", isEducationPresent=" + isEducationPresent +
                ", isSecondAnnuitantPEP=" + isSecondAnnuitantPEP +
                ", annuitantReflexive='" + annuitantReflexive + '\'' +
                ", posEmailStatus=" + posEmailStatus +
                ", isSellerDeclarationApplicable='" + isSellerDeclarationApplicable + '\'' +
                ", isPhotoSupress=" + isPhotoSupress +
                ", photoType='" + photoType + '\'' +
                ", reasonForVideoPosv='" + reasonForVideoPosv + '\'' +
                ", isPEPDisabled='" + isPEPDisabled + '\'' +
                ", isProspectiveYBLCustomer=" + isProspectiveYBLCustomer +
                ", isRenewelPaymentDone=" + isRenewelPaymentDone +
                ", isInitiativeTypeBrmsApiCallRequired='" + isInitiativeTypeBrmsApiCallRequired + '\'' +
                ", isNewIrpGenerate=" + isNewIrpGenerate +
                ", isTeleSaleCase='" + isTeleSaleCase + '\'' +
                ", whatsappConsent='" + whatsappConsent + '\'' +
                ", optinStatus='" + optinStatus + '\'' +
                ", isOnboardedProduct='" + isOnboardedProduct + '\'' +
                ", gstWaiverRequired='" + gstWaiverRequired + '\'' +
                ", isForm60acceptable=" + isForm60acceptable +
                ", isCRIFvalidated=" + isCRIFvalidated +
                ", isPanModifiedForThanos=" + isPanModifiedForThanos +
                ", showHealthQuesOnPosv='" + showHealthQuesOnPosv + '\'' +
                ", manualDirectDebitDocGenerated=" + manualDirectDebitDocGenerated +
                ", directDebitNewCrIdentifier=" + directDebitNewCrIdentifier +
                ", isPtfPayment='" + isPtfPayment + '\'' +
                ", ptfPolicyNumber='" + ptfPolicyNumber + '\'' +
                ", typeOfPtfProduct='" + typeOfPtfProduct + '\'' +
                ", ptfPlanName='" + ptfPlanName + '\'' +
                ", journeyType='" + journeyType + '\'' +
                ", diyJourneyType='" + diyJourneyType + '\'' +
                ", showCovidQuesOnPosv='" + showCovidQuesOnPosv + '\'' +
                ", emailSMSConsent=" + emailSMSConsent +
                ", isSeniorCitizen='" + isSeniorCitizen + '\'' +
                ", physicalJourneyPosvStatus='" + physicalJourneyPosvStatus + '\'' +
                ", isCatChannel='" + isCatChannel + '\'' +
                ", cancelledChequeNeft=" + cancelledChequeNeft +
                ", pennyDropNameMatch='" + pennyDropNameMatch + '\'' +
                ", pennyDropVerification='" + pennyDropVerification + '\'' +
                ", isBankDetailsReEdited=" + isBankDetailsReEdited +
                ", isPhysicalAxisCase='" + isPhysicalAxisCase + '\'' +
                ", agentFraudCheckDetails=" + agentFraudCheckDetails +
                ", ekycAadhaarDetails=" + ekycAadhaarDetails +
                ", pennyDropConsent='" + pennyDropConsent + '\'' +
                ", isNpsJourney='" + isNpsJourney + '\'' +
                ", isNpsJourneyInitiated='" + isNpsJourneyInitiated + '\'' +
                ", isPennyDropApplicable=" + isPennyDropApplicable +
                ", defenceChannel='" + defenceChannel + '\'' +
                ", isCSFBChannel='" + isCSFBChannel + '\'' +
                ", customerSignDateForCDF=" + customerSignDateForCDF +
                ", sourceChannel='" + sourceChannel + '\'' +
                ", journeyIdentifer='" + journeyIdentifer + '\'' +
                ", isSolution='" + isSolution + '\'' +
                ", clientAciRiderSa='" + clientAciRiderSa + '\'' +
                ", policyAciRiderSa='" + policyAciRiderSa + '\'' +
                ", deathBenefit='" + deathBenefit + '\'' +
                ", schemeType='" + schemeType + '\'' +
                ", lst2YrCliCiRiderSa='" + lst2YrCliCiRiderSa + '\'' +
                ", physicalJourneyEnabled='" + physicalJourneyEnabled + '\'' +
                ", premiumPaymentTerm='" + premiumPaymentTerm + '\'' +
                ", policyTerm='" + policyTerm + '\'' +
                ", pcb='" + pcb + '\'' +
                ", isJointLife='" + isJointLife + '\'' +
                ", partnerCareRiderSumAssured='" + partnerCareRiderSumAssured + '\'' +
                ", clientPartnerCareRiderSumAssured='" + clientPartnerCareRiderSumAssured + '\'' +
                ", termPlusRiderSumAssured='" + termPlusRiderSumAssured + '\'' +
                ", clientTermPlusRiderSumAssured='" + clientTermPlusRiderSumAssured + '\'' +
                ", clientSFPSSumAssured='" + clientSFPSSumAssured + '\'' +
                ", clientWopRiderSa='" + clientWopRiderSa + '\'' +
                ", isCombo='" + isCombo + '\'' +
                ", underwritingCount=" + underwritingCount +
                ", sellerConsentStatus='" + sellerConsentStatus + '\'' +
                ", partnerSourceCode='" + partnerSourceCode + '\'' +
                ", isCISApplicable='" + isCISApplicable + '\'' +
                ", mandateAmountPercentage='" + mandateAmountPercentage + '\'' +
                ", isInsurerVerified=" + isInsurerVerified +
                ", iibAnsPresent='" + iibAnsPresent + '\'' +
                ", formCFeatureEnabled='" + formCFeatureEnabled + '\'' +
                ", isCurrentInsurerVerified=" + isCurrentInsurerVerified +
                ", isInsurerMajor='" + isInsurerMajor + '\'' +
                ", insuredPosvType='" + insuredPosvType + '\'' +
                ", afypDetails=" + afypDetails +
                ", replacementSaleDetails=" + replacementSaleDetails +
                ", documentGenerated='" + documentGenerated + '\'' +
                ", spApproverName='" + spApproverName + '\'' +
                ", uniqueId='" + uniqueId + '\'' +
                ", spDigitalConsentSkip='" + spDigitalConsentSkip + '\'' +
                ", spDigitalConsentSkipReason='" + spDigitalConsentSkipReason + '\'' +
                ", unifiedRenewalStatus='" + unifiedRenewalStatus + '\'' +
                ", unifiedPaymentStatus='" + unifiedPaymentStatus + '\'' +
                ", isSspSwissReCase='" + isSspSwissReCase + '\'' +
                ", vernacularDeclaration=" + vernacularDeclaration +
                ", disabilityDeclaration=" + disabilityDeclaration +
                ", paymentGateway=" + paymentGateway +
                ", paymentFirstConsent=" + paymentFirstConsent +
                '}';
    }
}