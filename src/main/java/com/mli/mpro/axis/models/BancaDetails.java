

package com.mli.mpro.axis.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "customerId", "bancaId", "creationTime", "updatedTime", "addOnBancaId", "addOnPolicyNumber", "creationDate", "lastSyncDate", "customerID",
	"bankAccountOpeningDate", "isCountryCodeIndian", "ekyc", "ukyc", "customerSegment", "customerClassification", "relationshipAdvisorSegment", "leadId",
	"addonType", "addOnFlag", "Type", "maxLifeRegisteredState", "emailTimeStamp", "isNameModified", "isDOBModified", "productSolutionMatrix",
	"illustrationDetails" })
public class BancaDetails {

    @JsonProperty("customerId")
    private String customerId;
    @JsonProperty("bancaId")
    private String bancaId;
    @JsonProperty("creationTime")
    private String creationTime;
    @JsonProperty("updatedTime")
    private String updatedTime;
    @JsonProperty("addOnBancaId")
    private String addOnBancaId;
    @Sensitive(MaskType.POLICY_NUM)
    @JsonProperty("addOnPolicyNumber")
    private String addOnPolicyNumber;
    @JsonProperty("creationDate")
    private String creationDate;
    @JsonProperty("lastSyncDate")
    private String lastSyncDate;
    @JsonProperty("bankAccountOpeningDate")
    private String bankAccountOpeningDate;
    @JsonProperty("isCountryCodeIndian")
    private String isCountryCodeIndian;
    @JsonProperty("ekyc")
    private String ekyc;
    @JsonProperty("ukyc")
    private String ukyc;
    @JsonProperty("customerSegment")
    private String customerSegment;
    @JsonProperty("customerClassification")
    private String customerClassification;
    @JsonProperty("relationshipAdvisorSegment")
    private String relationshipAdvisorSegment;
    @JsonProperty("leadId")
    private String leadId;
    @JsonProperty("addonType")
    private String addonType;
    @JsonProperty("addOnFlag")
    private Boolean addOnFlag;
    @JsonProperty("Type")
    private String type;
    @JsonProperty("maxLifeRegisteredState")
    private String maxLifeRegisteredState;
    @JsonProperty("emailTimeStamp")
    private String emailTimeStamp;
    @JsonProperty("isNameModified")
    private Boolean isNameModified;
    @JsonProperty("isDOBModified")
    private Boolean isDOBModified;
    @JsonProperty("productSolutionMatrix")
    private ProductSolutionMatrix productSolutionMatrix;
    @JsonProperty("illustrationDetails")
    private IllustrationDetails illustrationDetails;
    @JsonProperty("isNRI")
    private String isNRI;
    private AxisDocumentResponse axisOutputResponse;
    @JsonProperty("spDetails")
    private BancaSpDetails bancaSpDetails;
    @JsonProperty("lastKYC")
    private String lastKYC;
    @JsonProperty("customerTypeFlag")
    private String customerTypeFlag;
    @JsonProperty("customerType")
    private String customerType;
    @JsonProperty("riskCategory")
    private String riskCategory;
    @JsonProperty("isPANAvailable")
    private boolean isPANAvailable;
    @JsonProperty("isMICRAvailable")
    private boolean isMICRAvailable;
    @JsonProperty("referenceId")
    private String referenceId;
    @JsonProperty("isCommAddressModified")
    private String isCommAddressModified;
    @JsonProperty("ruleOutput")
    private Object ruleOutput;
    @JsonProperty("primaryId")
    private long primaryId;
    @JsonProperty("addOnDetails")
    private AddOnDetails addOnDetails;

    
    @JsonProperty("freeText1")
    private String freeText1;
    @JsonProperty("freeText2")
    private String freeText2;
    @JsonProperty("freeText3")
    private String freeText3;
    @JsonProperty("freeText4")
    private String freeText4;
    @JsonProperty("freeText5")
    private String freeText5;
    @JsonProperty("freeText6")
    private String freeText6;
    @JsonProperty("freeText7")
    private String freeText7;
    @JsonProperty("freeText8")
    private String freeText8;
    @JsonProperty("freeText9")
    private String freeText9;
    @JsonProperty("freeText10")
    private String freeText10;
    @JsonProperty("freeText11")
    private String freeText11;
    @JsonProperty("freeText12")
    private String freeText12;
    @JsonProperty("freeText13")
    private String freeText13;
    @JsonProperty("freeText14")
    private String freeText14;
    @JsonProperty("freeText15")
    private String freeText15;
    @JsonProperty("freeText16")
    private String freeText16;
    @JsonProperty("freeText17")
    private String freeText17;
    @JsonProperty("freeText18")
    private String freeText18;
    @JsonProperty("freeText19")
    private String freeText19;
    @JsonProperty("freeText20")
    private String freeText20;
    @JsonProperty("sourceOfSale")
    private String sourceOfSale;

    @JsonProperty("insurerCustomerID")
    private String insurerCustomerID;
    /**
     * No args constructor for use in serialization
     * 
     */
    public BancaDetails() {
    }

    /**
     * 
     * @param addOnFlag
     * @param leadId
     * @param type
     * @param isDOBModified
     * @param illustrationDetails
     * @param customerId
     * @param addOnPolicyNumber
     * @param ekyc
     * @param creationTime
     * @param productSolutionMatrix
     * @param ukyc
     * @param updatedTime
     * @param isCountryCodeIndian
     * @param addOnBancaId
     * @param relationshipAdvisorSegment
     * @param bancaId
     * @param emailTimeStamp
     * @param addonType
     * @param customerSegment
     * @param creationDate
     * @param lastSyncDate
     * @param maxLifeRegisteredState
     * @param customerID
     * @param bankAccountOpeningDate
     * @param customerClassification
     * @param isNameModified
     */

   
    

   
    public BancaDetails(String customerId, String bancaId, String creationTime, String updatedTime, String addOnBancaId, String addOnPolicyNumber,
	    String creationDate, String lastSyncDate, String bankAccountOpeningDate, String isCountryCodeIndian, String ekyc, String ukyc,
	    String customerSegment, String customerClassification, String relationshipAdvisorSegment, String leadId, String addonType, Boolean addOnFlag,
	    String type, String maxLifeRegisteredState, String emailTimeStamp, Boolean isNameModified, Boolean isDOBModified,
	    ProductSolutionMatrix productSolutionMatrix, IllustrationDetails illustrationDetails, String isNRI, AxisDocumentResponse axisOutputResponse,
	    BancaSpDetails bancaSpDetails, String lastKYC, String customerTypeFlag, String customerType, String riskCategory, boolean isPANAvailable,
	    boolean isMICRAvailable, String referenceId, String isCommAddressModified, Object ruleOutput, long primaryId, AddOnDetails addOnDetails,
	    String freeText1, String freeText2, String freeText3, String freeText4, String freeText5, String freeText6, String freeText7, String freeText8,
	    String freeText9, String freeText10, String freeText11, String freeText12, String freeText13, String freeText14, String freeText15,
	    String freeText16, String freeText17, String freeText18, String freeText19, String freeText20, String sourceOfSale) {
	super();
	this.customerId = customerId;
	this.bancaId = bancaId;
	this.creationTime = creationTime;
	this.updatedTime = updatedTime;
	this.addOnBancaId = addOnBancaId;
	this.addOnPolicyNumber = addOnPolicyNumber;
	this.creationDate = creationDate;
	this.lastSyncDate = lastSyncDate;
	this.bankAccountOpeningDate = bankAccountOpeningDate;
	this.isCountryCodeIndian = isCountryCodeIndian;
	this.ekyc = ekyc;
	this.ukyc = ukyc;
	this.customerSegment = customerSegment;
	this.customerClassification = customerClassification;
	this.relationshipAdvisorSegment = relationshipAdvisorSegment;
	this.leadId = leadId;
	this.addonType = addonType;
	this.addOnFlag = addOnFlag;
	this.type = type;
	this.maxLifeRegisteredState = maxLifeRegisteredState;
	this.emailTimeStamp = emailTimeStamp;
	this.isNameModified = isNameModified;
	this.isDOBModified = isDOBModified;
	this.productSolutionMatrix = productSolutionMatrix;
	this.illustrationDetails = illustrationDetails;
	this.isNRI = isNRI;
	this.axisOutputResponse = axisOutputResponse;
	this.bancaSpDetails = bancaSpDetails;
	this.lastKYC = lastKYC;
	this.customerTypeFlag = customerTypeFlag;
	this.customerType = customerType;
	this.riskCategory = riskCategory;
	this.isPANAvailable = isPANAvailable;
	this.isMICRAvailable = isMICRAvailable;
	this.referenceId = referenceId;
	this.isCommAddressModified = isCommAddressModified;
	this.ruleOutput = ruleOutput;
	this.primaryId = primaryId;
	this.addOnDetails = addOnDetails;
	this.freeText1 = freeText1;
	this.freeText2 = freeText2;
	this.freeText3 = freeText3;
	this.freeText4 = freeText4;
	this.freeText5 = freeText5;
	this.freeText6 = freeText6;
	this.freeText7 = freeText7;
	this.freeText8 = freeText8;
	this.freeText9 = freeText9;
	this.freeText10 = freeText10;
	this.freeText11 = freeText11;
	this.freeText12 = freeText12;
	this.freeText13 = freeText13;
	this.freeText14 = freeText14;
	this.freeText15 = freeText15;
	this.freeText16 = freeText16;
	this.freeText17 = freeText17;
	this.freeText18 = freeText18;
	this.freeText19 = freeText19;
	this.freeText20 = freeText20;
	this.sourceOfSale = sourceOfSale;
    }

    
   

    public BancaDetails(BancaDetails bancaDetails) {
	// TODO Auto-generated constructor stub
    }

    @JsonProperty("customerId")
    public String getCustomerId() {
	return customerId;
    }

    @JsonProperty("customerId")
    public void setCustomerId(String customerId) {
	this.customerId = customerId;
    }

    @JsonProperty("bancaId")
    public String getBancaId() {
	return bancaId;
    }

    @JsonProperty("bancaId")
    public void setBancaId(String bancaId) {
	this.bancaId = bancaId;
    }

    @JsonProperty("creationTime")
    public String getCreationTime() {
	return creationTime;
    }

    @JsonProperty("creationTime")
    public void setCreationTime(String creationTime) {
	this.creationTime = creationTime;
    }

    @JsonProperty("updatedTime")
    public String getUpdatedTime() {
	return updatedTime;
    }

    @JsonProperty("updatedTime")
    public void setUpdatedTime(String updatedTime) {
	this.updatedTime = updatedTime;
    }

    @JsonProperty("addOnBancaId")
    public String getAddOnBancaId() {
	return addOnBancaId;
    }

    @JsonProperty("addOnBancaId")
    public void setAddOnBancaId(String addOnBancaId) {
	this.addOnBancaId = addOnBancaId;
    }

    @JsonProperty("addOnPolicyNumber")
    public String getAddOnPolicyNumber() {
	return addOnPolicyNumber;
    }

    @JsonProperty("addOnPolicyNumber")
    public void setAddOnPolicyNumber(String addOnPolicyNumber) {
	this.addOnPolicyNumber = addOnPolicyNumber;
    }

    @JsonProperty("creationDate")
    public String getCreationDate() {
	return creationDate;
    }

    @JsonProperty("creationDate")
    public void setCreationDate(String creationDate) {
	this.creationDate = creationDate;
    }

    @JsonProperty("lastSyncDate")
    public String getLastSyncDate() {
	return lastSyncDate;
    }

    @JsonProperty("lastSyncDate")
    public void setLastSyncDate(String lastSyncDate) {
	this.lastSyncDate = lastSyncDate;
    }

    @JsonProperty("bankAccountOpeningDate")
    public String getBankAccountOpeningDate() {
	return bankAccountOpeningDate;
    }

    @JsonProperty("bankAccountOpeningDate")
    public void setBankAccountOpeningDate(String bankAccountOpeningDate) {
	this.bankAccountOpeningDate = bankAccountOpeningDate;
    }

    @JsonProperty("isCountryCodeIndian")
    public String getIsCountryCodeIndian() {
	return isCountryCodeIndian;
    }

    @JsonProperty("isCountryCodeIndian")
    public void setIsCountryCodeIndian(String isCountryCodeIndian) {
	this.isCountryCodeIndian = isCountryCodeIndian;
    }

    @JsonProperty("ekyc")
    public String getEkyc() {
	return ekyc;
    }

    @JsonProperty("ekyc")
    public void setEkyc(String ekyc) {
	this.ekyc = ekyc;
    }

    @JsonProperty("ukyc")
    public String getUkyc() {
	return ukyc;
    }

    @JsonProperty("ukyc")
    public void setUkyc(String ukyc) {
	this.ukyc = ukyc;
    }

    @JsonProperty("customerSegment")
    public String getCustomerSegment() {
	return customerSegment;
    }

    @JsonProperty("customerSegment")
    public void setCustomerSegment(String customerSegment) {
	this.customerSegment = customerSegment;
    }

    @JsonProperty("customerClassification")
    public String getCustomerClassification() {
	return customerClassification;
    }

    @JsonProperty("customerClassification")
    public void setCustomerClassification(String customerClassification) {
	this.customerClassification = customerClassification;
    }

    @JsonProperty("relationshipAdvisorSegment")
    public String getRelationshipAdvisorSegment() {
	return relationshipAdvisorSegment;
    }

    @JsonProperty("relationshipAdvisorSegment")
    public void setRelationshipAdvisorSegment(String relationshipAdvisorSegment) {
	this.relationshipAdvisorSegment = relationshipAdvisorSegment;
    }

    @JsonProperty("leadId")
    public String getLeadId() {
	return leadId;
    }

    @JsonProperty("leadId")
    public void setLeadId(String leadId) {
	this.leadId = leadId;
    }

    @JsonProperty("addonType")
    public String getAddonType() {
	return addonType;
    }

    @JsonProperty("addonType")
    public void setAddonType(String addonType) {
	this.addonType = addonType;
    }

    @JsonProperty("addOnFlag")
    public Boolean getAddOnFlag() {
	return addOnFlag;
    }

    @JsonProperty("addOnFlag")
    public void setAddOnFlag(Boolean addOnFlag) {
	this.addOnFlag = addOnFlag;
    }

    @JsonProperty("Type")
    public String getType() {
	return type;
    }

    @JsonProperty("Type")
    public void setType(String type) {
	this.type = type;
    }

    @JsonProperty("maxLifeRegisteredState")
    public String getMaxLifeRegisteredState() {
	return maxLifeRegisteredState;
    }

    @JsonProperty("maxLifeRegisteredState")
    public void setMaxLifeRegisteredState(String maxLifeRegisteredState) {
	this.maxLifeRegisteredState = maxLifeRegisteredState;
    }

    @JsonProperty("emailTimeStamp")
    public String getEmailTimeStamp() {
	return emailTimeStamp;
    }

    @JsonProperty("emailTimeStamp")
    public void setEmailTimeStamp(String emailTimeStamp) {
	this.emailTimeStamp = emailTimeStamp;
    }

    @JsonProperty("isNameModified")
    public Boolean getIsNameModified() {
	return isNameModified;
    }

    @JsonProperty("isNameModified")
    public void setIsNameModified(Boolean isNameModified) {
	this.isNameModified = isNameModified;
    }

    @JsonProperty("isDOBModified")
    public Boolean getIsDOBModified() {
	return isDOBModified;
    }

    @JsonProperty("isDOBModified")
    public void setIsDOBModified(Boolean isDOBModified) {
	this.isDOBModified = isDOBModified;
    }

    @JsonProperty("productSolutionMatrix")
    public ProductSolutionMatrix getProductSolutionMatrix() {
	return productSolutionMatrix;
    }

    @JsonProperty("productSolutionMatrix")
    public void setProductSolutionMatrix(ProductSolutionMatrix productSolutionMatrix) {
	this.productSolutionMatrix = productSolutionMatrix;
    }

    @JsonProperty("illustrationDetails")
    public IllustrationDetails getIllustrationDetails() {
	return illustrationDetails;
    }

    @JsonProperty("illustrationDetails")
    public void setIllustrationDetails(IllustrationDetails illustrationDetails) {
	this.illustrationDetails = illustrationDetails;
    }

    public String getIsNRI() {
	return isNRI;
    }

    public void setIsNRI(String isNRI) {
	this.isNRI = isNRI;
    }

    public AxisDocumentResponse getAxisOutputResponse() {
	return axisOutputResponse;
    }

    public void setAxisOutputResponse(AxisDocumentResponse axisOutputResponse) {
	this.axisOutputResponse = axisOutputResponse;
    }

    public BancaSpDetails getBancaSpDetails() {
	return bancaSpDetails;
    }

    public void setBancaSpDetails(BancaSpDetails bancaSpDetails) {
	this.bancaSpDetails = bancaSpDetails;
    }

    public String getLastKYC() {
	return lastKYC;
    }

    public void setLastKYC(String lastKYC) {
	this.lastKYC = lastKYC;
    }

    public String getCustomerTypeFlag() {
	return customerTypeFlag;
    }

    public void setCustomerTypeFlag(String customerTypeFlag) {
	this.customerTypeFlag = customerTypeFlag;
    }

    public String getCustomerType() {
	return customerType;
    }

    public void setCustomerType(String customerType) {
	this.customerType = customerType;
    }

    public String getRiskCategory() {
	return riskCategory;
    }

    public void setRiskCategory(String riskCategory) {
	this.riskCategory = riskCategory;
    }

    public boolean isPANAvailable() {
	return isPANAvailable;
    }

    public void setPANAvailable(boolean isPANAvailable) {
	this.isPANAvailable = isPANAvailable;
    }

    public boolean isMICRAvailable() {
	return isMICRAvailable;
    }

    public void setMICRAvailable(boolean isMICRAvailable) {
	this.isMICRAvailable = isMICRAvailable;
    }

    public String getReferenceId() {
	return referenceId;
    }

    public void setReferenceId(String referenceId) {
	this.referenceId = referenceId;
    }

    public String getIsCommAddressModified() {
	return isCommAddressModified;
    }

    public void setIsCommAddressModified(String isCommAddressModified) {
	this.isCommAddressModified = isCommAddressModified;
    }

    public Object getRuleOutput() {
	return ruleOutput;
    }

    public void setRuleOutput(Object ruleOutput) {
	this.ruleOutput = ruleOutput;
    }
    

    public long getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(long primaryId) {
        this.primaryId = primaryId;
    }
    

    public AddOnDetails getAddOnDetails() {
        return addOnDetails;
    }

    public void setAddOnDetails(AddOnDetails addOnDetails) {
        this.addOnDetails = addOnDetails;
    }
    

    public String getFreeText1() {
        return freeText1;
    }

    public void setFreeText1(String freeText1) {
        this.freeText1 = freeText1;
    }

    public String getFreeText2() {
        return freeText2;
    }

    public void setFreeText2(String freeText2) {
        this.freeText2 = freeText2;
    }

    public String getFreeText3() {
        return freeText3;
    }

    public void setFreeText3(String freeText3) {
        this.freeText3 = freeText3;
    }

    public String getFreeText4() {
        return freeText4;
    }

    public void setFreeText4(String freeText4) {
        this.freeText4 = freeText4;
    }

    public String getFreeText5() {
        return freeText5;
    }

    public void setFreeText5(String freeText5) {
        this.freeText5 = freeText5;
    }

    public String getFreeText6() {
        return freeText6;
    }

    public void setFreeText6(String freeText6) {
        this.freeText6 = freeText6;
    }

    public String getFreeText7() {
        return freeText7;
    }

    public void setFreeText7(String freeText7) {
        this.freeText7 = freeText7;
    }

    public String getFreeText8() {
        return freeText8;
    }

    public void setFreeText8(String freeText8) {
        this.freeText8 = freeText8;
    }

    public String getFreeText9() {
        return freeText9;
    }

    public void setFreeText9(String freeText9) {
        this.freeText9 = freeText9;
    }

    public String getFreeText10() {
        return freeText10;
    }

    public void setFreeText10(String freeText10) {
        this.freeText10 = freeText10;
    }

    public String getFreeText11() {
        return freeText11;
    }

    public void setFreeText11(String freeText11) {
        this.freeText11 = freeText11;
    }

    public String getFreeText12() {
        return freeText12;
    }

    public void setFreeText12(String freeText12) {
        this.freeText12 = freeText12;
    }

    public String getFreeText13() {
        return freeText13;
    }

    public void setFreeText13(String freeText13) {
        this.freeText13 = freeText13;
    }

    public String getFreeText14() {
        return freeText14;
    }

    public void setFreeText14(String freeText14) {
        this.freeText14 = freeText14;
    }

    public String getFreeText15() {
        return freeText15;
    }

    public void setFreeText15(String freeText15) {
        this.freeText15 = freeText15;
    }

    public String getFreeText16() {
        return freeText16;
    }

    public void setFreeText16(String freeText16) {
        this.freeText16 = freeText16;
    }

    public String getFreeText17() {
        return freeText17;
    }

    public void setFreeText17(String freeText17) {
        this.freeText17 = freeText17;
    }

    public String getFreeText18() {
        return freeText18;
    }

    public void setFreeText18(String freeText18) {
        this.freeText18 = freeText18;
    }

    public String getFreeText19() {
        return freeText19;
    }

    public void setFreeText19(String freeText19) {
        this.freeText19 = freeText19;
    }

    public String getFreeText20() {
        return freeText20;
    }

    public void setFreeText20(String freeText20) {
        this.freeText20 = freeText20;
    }

    public String getSourceOfSale() {
        return sourceOfSale;
    }

    public void setSourceOfSale(String sourceOfSale) {
        this.sourceOfSale = sourceOfSale;
    }

    public String getInsurerCustomerID() {
        return insurerCustomerID;
    }

    public void setInsurerCustomerID(String insurerCustomerID) {
        this.insurerCustomerID = insurerCustomerID;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "BancaDetails [customerId=" + customerId + ", bancaId=" + bancaId + ", creationTime=" + creationTime + ", updatedTime=" + updatedTime
		+ ", addOnBancaId=" + addOnBancaId + ", addOnPolicyNumber=" + addOnPolicyNumber + ", creationDate=" + creationDate + ", lastSyncDate="
		+ lastSyncDate + ", bankAccountOpeningDate=" + bankAccountOpeningDate + ", isCountryCodeIndian=" + isCountryCodeIndian + ", ekyc=" + ekyc
		+ ", ukyc=" + ukyc + ", customerSegment=" + customerSegment + ", customerClassification=" + customerClassification
		+ ", relationshipAdvisorSegment=" + relationshipAdvisorSegment + ", leadId=" + leadId + ", addonType=" + addonType + ", addOnFlag=" + addOnFlag
		+ ", type=" + type + ", maxLifeRegisteredState=" + maxLifeRegisteredState + ", emailTimeStamp=" + emailTimeStamp + ", isNameModified="
		+ isNameModified + ", isDOBModified=" + isDOBModified + ", productSolutionMatrix=" + productSolutionMatrix + ", illustrationDetails="
		+ illustrationDetails + ", isNRI=" + isNRI + ", axisOutputResponse=" + axisOutputResponse + ", bancaSpDetails=" + bancaSpDetails + ", lastKYC="
		+ lastKYC + ", customerTypeFlag=" + customerTypeFlag + ", customerType=" + customerType + ", riskCategory=" + riskCategory + ", isPANAvailable="
		+ isPANAvailable + ", isMICRAvailable=" + isMICRAvailable + ", referenceId=" + referenceId + ", isCommAddressModified=" + isCommAddressModified
		+ ", ruleOutput=" + ruleOutput + ", primaryId=" + primaryId + ", addOnDetails=" + addOnDetails + ", freeText1=" + freeText1 + ", freeText2="
		+ freeText2 + ", freeText3=" + freeText3 + ", freeText4=" + freeText4 + ", freeText5=" + freeText5 + ", freeText6=" + freeText6 + ", freeText7="
		+ freeText7 + ", freeText8=" + freeText8 + ", freeText9=" + freeText9 + ", freeText10=" + freeText10 + ", freeText11=" + freeText11
		+ ", freeText12=" + freeText12 + ", freeText13=" + freeText13 + ", freeText14=" + freeText14 + ", freeText15=" + freeText15 + ", freeText16="
		+ freeText16 + ", freeText17=" + freeText17 + ", freeText18=" + freeText18 + ", freeText19=" + freeText19 + ", freeText20=" + freeText20 + ", insurerTmbCustomerID=" + insurerCustomerID
            + ", sourceOfSale=" + sourceOfSale + "]";
    }

   
    

    

}

