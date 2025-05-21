
package com.mli.mpro.location.models.soaCloudModels.master360ResponseModels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.AddressDetails;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "policyNo",
    "planName",
    "planType",
    "policyIssueDate",
    "policyStatusCd",
    "policyStatusDesc",
    "term",
    "policyMaturityDateBase",
    "baseSumAssured",
    "modalPremiumBase",
    "premiumModeCd",
    "premiumModeDesc",
    "premiumDueDate",
    "policyPtToDtNum",
    "bounceDates",
    "premiumDueAmount",
    "policyHolderName",
    "totalFundValue",
    "hybridPolicyFlag",
    "assignedPolicyFlag",
    "lifeInsuredDetails",
    "landlineNo",
    "paymentFrequency",
    "modalPremiumexclusiveGST",
    "modalPremiuminclusiveGST",
    "premiumPaymentTerm",
    "lastPremiumPayableDate",
    "nominees",
    "apointeeName",
    "methodOfPayment",
    "ecsDrawDate",
    "surrenderValue",
    "loanableCashValue",
    "lastPaymentDate",
    "polInsPurpCd",
    "polInsPurp",
    "form2Policy",
    "polRejectionReason",
    "employerEmployeePol",
    "keyManPolicy",
    "tdsExempt",
    "policyAssignedMaxLife",
    "lastPaymentAmount",
    "currentBonusOption",
    "grossAnnualPremium",
    "polCumUnclrdAmt",
    "polPremSuspAmt",
    "polMiscSuspAmt",
    "polOsDisbAmt",
    "polGrossPremAmt",
    "policyInsuranceTypeCd",
    "PolSuprsIssInd",
    "polAppReceiveDt",
    "bonusDetails",
    "lastBonusDate",
    "lastBonusAmount",
    "riderDetails",
    "neftAccountNum",
    "ifscCode",
    "micrCode",
    "accountType",
    "accountHolder",
    "bankName",
    "branchName",
    "ownerClientAccount",
    "refundDate",
    "refundAmount",
    "refundMethodCd",
    "refundMethodDesc",
    "refundDetails",
    "paymentHistory",
    "addressDetails",
    "prefAddrCode",
    "mobileNo",
    "dob",
    "emailId",
    "ownerEarnedIncome",
    "agentName",
    "agentCode",
    "agentEmailId",
    "agentMobileNo",
    "servicingAgentId",
    "servicingAgentBranchId",
    "policyRelationships",
    "coverageDetails",
    "eiaDetails",
    "relationshipUnderPolicy",
    "requirementUnderPolicy",
    "planDetails"
})
public class Payload
{
    @Sensitive(MaskType.POLICY_NUM)
    @JsonProperty("policyNo")
    private String policyNo;
    @JsonProperty("planName")
    private String planName;
    @JsonProperty("planType")
    private String planType;
    @JsonProperty("policyIssueDate")
    private String policyIssueDate;
    @JsonProperty("policyStatusCd")
    private String policyStatusCd;
    @JsonProperty("policyStatusDesc")
    private String policyStatusDesc;
    @JsonProperty("term")
    private String term;
    @JsonProperty("policyMaturityDateBase")
    private String policyMaturityDateBase;
    @JsonProperty("baseSumAssured")
    private String baseSumAssured;
    @JsonProperty("modalPremiumBase")
    private String modalPremiumBase;
    @JsonProperty("premiumModeCd")
    private String premiumModeCd;
    @JsonProperty("premiumModeDesc")
    private String premiumModeDesc;
    @JsonProperty("premiumDueDate")
    private String premiumDueDate;
    @JsonProperty("policyPtToDtNum")
    private String policyPtToDtNum;
    @JsonProperty("bounceDates")
    @Valid
    private List<BounceDate> bounceDates = null;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("premiumDueAmount")
    private String premiumDueAmount;
    @Sensitive(MaskType.NAME)
    @JsonProperty("policyHolderName")
    private String policyHolderName;
    @JsonProperty("totalFundValue")
    private String totalFundValue;
    @JsonProperty("hybridPolicyFlag")
    private String hybridPolicyFlag;
    @JsonProperty("assignedPolicyFlag")
    private String assignedPolicyFlag;
    @JsonProperty("lifeInsuredDetails")
    @Valid
    private List<LifeInsuredDetail> lifeInsuredDetails = null;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("landlineNo")
    private String landlineNo;
    @JsonProperty("paymentFrequency")
    private String paymentFrequency;
    @JsonProperty("modalPremiumexclusiveGST")
    private String modalPremiumexclusiveGST;
    @JsonProperty("modalPremiuminclusiveGST")
    private String modalPremiuminclusiveGST;
    @JsonProperty("premiumPaymentTerm")
    private String premiumPaymentTerm;
    @JsonProperty("lastPremiumPayableDate")
    private String lastPremiumPayableDate;
    @JsonProperty("nominees")
    @Valid
    private List<Nominee> nominees = null;
    @Sensitive(MaskType.NAME)
    @JsonProperty("apointeeName")
    private String apointeeName;
    @JsonProperty("methodOfPayment")
    private String methodOfPayment;
    @JsonProperty("ecsDrawDate")
    private String ecsDrawDate;
    @JsonProperty("surrenderValue")
    private String surrenderValue;
    @JsonProperty("loanableCashValue")
    private String loanableCashValue;
    @JsonProperty("lastPaymentDate")
    private String lastPaymentDate;
    @JsonProperty("polInsPurpCd")
    private String polInsPurpCd;
    @JsonProperty("polInsPurp")
    private String polInsPurp;
    @JsonProperty("form2Policy")
    private String form2Policy;
    @JsonProperty("polRejectionReason")
    private String polRejectionReason;
    @JsonProperty("employerEmployeePol")
    private String employerEmployeePol;
    @JsonProperty("keyManPolicy")
    private String keyManPolicy;
    @JsonProperty("tdsExempt")
    private String tdsExempt;
    @JsonProperty("policyAssignedMaxLife")
    private String policyAssignedMaxLife;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("lastPaymentAmount")
    private String lastPaymentAmount;
    @JsonProperty("currentBonusOption")
    private String currentBonusOption;
    @JsonProperty("grossAnnualPremium")
    private String grossAnnualPremium;
    @JsonProperty("polCumUnclrdAmt")
    private String polCumUnclrdAmt;
    @JsonProperty("polPremSuspAmt")
    private String polPremSuspAmt;
    @JsonProperty("polMiscSuspAmt")
    private String polMiscSuspAmt;
    @JsonProperty("polOsDisbAmt")
    private String polOsDisbAmt;
    @JsonProperty("polGrossPremAmt")
    private String polGrossPremAmt;
    @JsonProperty("policyInsuranceTypeCd")
    private String policyInsuranceTypeCd;
    @JsonProperty("PolSuprsIssInd")
    private String polSuprsIssInd;
    @JsonProperty("polAppReceiveDt")
    private String polAppReceiveDt;
    @JsonProperty("bonusDetails")
    @Valid
    private List<BonusDetail> bonusDetails = null;
    @JsonProperty("lastBonusDate")
    private String lastBonusDate;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("lastBonusAmount")
    private String lastBonusAmount;
    @JsonProperty("riderDetails")
    @Valid
    private List<Object> riderDetails = null;
    @Sensitive(MaskType.BANK_ACC_NUM)
    @JsonProperty("neftAccountNum")
    private String neftAccountNum;
    @Sensitive(MaskType.BANK_IFSC)
    @JsonProperty("ifscCode")
    private String ifscCode;
    @Sensitive(MaskType.BANK_MICR)
    @JsonProperty("micrCode")
    private String micrCode;
    @Sensitive(MaskType.BANK_MICR)
    @JsonProperty("accountType")
    private String accountType;
    @Sensitive(MaskType.BANK_ACC_HOLDER_NAME)
    @JsonProperty("accountHolder")
    private String accountHolder;
    @Sensitive(MaskType.BANK_NAME)
    @JsonProperty("bankName")
    private String bankName;
    @Sensitive(MaskType.BANK_BRANCH_NAME)
    @JsonProperty("branchName")
    private String branchName;
    @Sensitive(MaskType.BANK_ACC_NUM)
    @JsonProperty("ownerClientAccount")
    private String ownerClientAccount;
    @JsonProperty("refundDate")
    private String refundDate;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("refundAmount")
    private String refundAmount;
    @JsonProperty("refundMethodCd")
    private String refundMethodCd;
    @JsonProperty("refundMethodDesc")
    private String refundMethodDesc;
    @JsonProperty("refundDetails")
    @Valid
    private List<RefundDetail> refundDetails = null;
    @JsonProperty("paymentHistory")
    @Valid
    private List<PaymentHistory> paymentHistory = null;
    @JsonProperty("addressDetails")
    @Valid
    private List<AddressDetails> addressDetails = null;
    @JsonProperty("prefAddrCode")
    private String prefAddrCode;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("mobileNo")
    private String mobileNo;
    @Sensitive(MaskType.DOB)
    @JsonProperty("dob")
    private String dob;
    @Sensitive(MaskType.EMAIL)
    @JsonProperty("emailId")
    private String emailId;
    @JsonProperty("ownerEarnedIncome")
    private String ownerEarnedIncome;
    @Sensitive(MaskType.NAME)
    @JsonProperty("agentName")
    private String agentName;
    @JsonProperty("agentCode")
    private String agentCode;
    @Sensitive(MaskType.EMAIL)
    @JsonProperty("agentEmailId")
    private String agentEmailId;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("agentMobileNo")
    private String agentMobileNo;
    @JsonProperty("servicingAgentId")
    private String servicingAgentId;
    @JsonProperty("servicingAgentBranchId")
    private String servicingAgentBranchId;
    @JsonProperty("policyRelationships")
    @Valid
    private List<PolicyRelationship> policyRelationships = null;
    @JsonProperty("coverageDetails")
    @Valid
    private List<CoverageDetail> coverageDetails = null;
    @JsonProperty("eiaDetails")
    @Valid
    private EiaDetails eiaDetails;
    @JsonProperty("relationshipUnderPolicy")
    @Valid
    private List<RelationshipUnderPolicy> relationshipUnderPolicy = null;
    @JsonProperty("requirementUnderPolicy")
    @Valid
    private List<RequirementUnderPolicy> requirementUnderPolicy = null;
    @JsonProperty("planDetails")
    @Valid
    private PlanDetails planDetails;

    @JsonProperty("policyNo")
    public String getPolicyNo() {
        return policyNo;
    }

    @JsonProperty("policyNo")
    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public Payload withPolicyNo(String policyNo) {
        this.policyNo = policyNo;
        return this;
    }

    @JsonProperty("planName")
    public String getPlanName() {
        return planName;
    }

    @JsonProperty("planName")
    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Payload withPlanName(String planName) {
        this.planName = planName;
        return this;
    }

    @JsonProperty("planType")
    public String getPlanType() {
        return planType;
    }

    @JsonProperty("planType")
    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public Payload withPlanType(String planType) {
        this.planType = planType;
        return this;
    }

    @JsonProperty("policyIssueDate")
    public String getPolicyIssueDate() {
        return policyIssueDate;
    }

    @JsonProperty("policyIssueDate")
    public void setPolicyIssueDate(String policyIssueDate) {
        this.policyIssueDate = policyIssueDate;
    }

    public Payload withPolicyIssueDate(String policyIssueDate) {
        this.policyIssueDate = policyIssueDate;
        return this;
    }

    @JsonProperty("policyStatusCd")
    public String getPolicyStatusCd() {
        return policyStatusCd;
    }

    @JsonProperty("policyStatusCd")
    public void setPolicyStatusCd(String policyStatusCd) {
        this.policyStatusCd = policyStatusCd;
    }

    public Payload withPolicyStatusCd(String policyStatusCd) {
        this.policyStatusCd = policyStatusCd;
        return this;
    }

    @JsonProperty("policyStatusDesc")
    public String getPolicyStatusDesc() {
        return policyStatusDesc;
    }

    @JsonProperty("policyStatusDesc")
    public void setPolicyStatusDesc(String policyStatusDesc) {
        this.policyStatusDesc = policyStatusDesc;
    }

    public Payload withPolicyStatusDesc(String policyStatusDesc) {
        this.policyStatusDesc = policyStatusDesc;
        return this;
    }

    @JsonProperty("term")
    public String getTerm() {
        return term;
    }

    @JsonProperty("term")
    public void setTerm(String term) {
        this.term = term;
    }

    public Payload withTerm(String term) {
        this.term = term;
        return this;
    }

    @JsonProperty("policyMaturityDateBase")
    public String getPolicyMaturityDateBase() {
        return policyMaturityDateBase;
    }

    @JsonProperty("policyMaturityDateBase")
    public void setPolicyMaturityDateBase(String policyMaturityDateBase) {
        this.policyMaturityDateBase = policyMaturityDateBase;
    }

    public Payload withPolicyMaturityDateBase(String policyMaturityDateBase) {
        this.policyMaturityDateBase = policyMaturityDateBase;
        return this;
    }

    @JsonProperty("baseSumAssured")
    public String getBaseSumAssured() {
        return baseSumAssured;
    }

    @JsonProperty("baseSumAssured")
    public void setBaseSumAssured(String baseSumAssured) {
        this.baseSumAssured = baseSumAssured;
    }

    public Payload withBaseSumAssured(String baseSumAssured) {
        this.baseSumAssured = baseSumAssured;
        return this;
    }

    @JsonProperty("modalPremiumBase")
    public String getModalPremiumBase() {
        return modalPremiumBase;
    }

    @JsonProperty("modalPremiumBase")
    public void setModalPremiumBase(String modalPremiumBase) {
        this.modalPremiumBase = modalPremiumBase;
    }

    public Payload withModalPremiumBase(String modalPremiumBase) {
        this.modalPremiumBase = modalPremiumBase;
        return this;
    }

    @JsonProperty("premiumModeCd")
    public String getPremiumModeCd() {
        return premiumModeCd;
    }

    @JsonProperty("premiumModeCd")
    public void setPremiumModeCd(String premiumModeCd) {
        this.premiumModeCd = premiumModeCd;
    }

    public Payload withPremiumModeCd(String premiumModeCd) {
        this.premiumModeCd = premiumModeCd;
        return this;
    }

    @JsonProperty("premiumModeDesc")
    public String getPremiumModeDesc() {
        return premiumModeDesc;
    }

    @JsonProperty("premiumModeDesc")
    public void setPremiumModeDesc(String premiumModeDesc) {
        this.premiumModeDesc = premiumModeDesc;
    }

    public Payload withPremiumModeDesc(String premiumModeDesc) {
        this.premiumModeDesc = premiumModeDesc;
        return this;
    }

    @JsonProperty("premiumDueDate")
    public String getPremiumDueDate() {
        return premiumDueDate;
    }

    @JsonProperty("premiumDueDate")
    public void setPremiumDueDate(String premiumDueDate) {
        this.premiumDueDate = premiumDueDate;
    }

    public Payload withPremiumDueDate(String premiumDueDate) {
        this.premiumDueDate = premiumDueDate;
        return this;
    }

    @JsonProperty("policyPtToDtNum")
    public String getPolicyPtToDtNum() {
        return policyPtToDtNum;
    }

    @JsonProperty("policyPtToDtNum")
    public void setPolicyPtToDtNum(String policyPtToDtNum) {
        this.policyPtToDtNum = policyPtToDtNum;
    }

    public Payload withPolicyPtToDtNum(String policyPtToDtNum) {
        this.policyPtToDtNum = policyPtToDtNum;
        return this;
    }

    @JsonProperty("bounceDates")
    public List<BounceDate> getBounceDates() {
        return bounceDates;
    }

    @JsonProperty("bounceDates")
    public void setBounceDates(List<BounceDate> bounceDates) {
        this.bounceDates = bounceDates;
    }

    public Payload withBounceDates(List<BounceDate> bounceDates) {
        this.bounceDates = bounceDates;
        return this;
    }

    @JsonProperty("premiumDueAmount")
    public String getPremiumDueAmount() {
        return premiumDueAmount;
    }

    @JsonProperty("premiumDueAmount")
    public void setPremiumDueAmount(String premiumDueAmount) {
        this.premiumDueAmount = premiumDueAmount;
    }

    public Payload withPremiumDueAmount(String premiumDueAmount) {
        this.premiumDueAmount = premiumDueAmount;
        return this;
    }

    @JsonProperty("policyHolderName")
    public String getPolicyHolderName() {
        return policyHolderName;
    }

    @JsonProperty("policyHolderName")
    public void setPolicyHolderName(String policyHolderName) {
        this.policyHolderName = policyHolderName;
    }

    public Payload withPolicyHolderName(String policyHolderName) {
        this.policyHolderName = policyHolderName;
        return this;
    }

    @JsonProperty("totalFundValue")
    public String getTotalFundValue() {
        return totalFundValue;
    }

    @JsonProperty("totalFundValue")
    public void setTotalFundValue(String totalFundValue) {
        this.totalFundValue = totalFundValue;
    }

    public Payload withTotalFundValue(String totalFundValue) {
        this.totalFundValue = totalFundValue;
        return this;
    }

    @JsonProperty("hybridPolicyFlag")
    public String getHybridPolicyFlag() {
        return hybridPolicyFlag;
    }

    @JsonProperty("hybridPolicyFlag")
    public void setHybridPolicyFlag(String hybridPolicyFlag) {
        this.hybridPolicyFlag = hybridPolicyFlag;
    }

    public Payload withHybridPolicyFlag(String hybridPolicyFlag) {
        this.hybridPolicyFlag = hybridPolicyFlag;
        return this;
    }

    @JsonProperty("assignedPolicyFlag")
    public String getAssignedPolicyFlag() {
        return assignedPolicyFlag;
    }

    @JsonProperty("assignedPolicyFlag")
    public void setAssignedPolicyFlag(String assignedPolicyFlag) {
        this.assignedPolicyFlag = assignedPolicyFlag;
    }

    public Payload withAssignedPolicyFlag(String assignedPolicyFlag) {
        this.assignedPolicyFlag = assignedPolicyFlag;
        return this;
    }

    @JsonProperty("lifeInsuredDetails")
    public List<LifeInsuredDetail> getLifeInsuredDetails() {
        return lifeInsuredDetails;
    }

    @JsonProperty("lifeInsuredDetails")
    public void setLifeInsuredDetails(List<LifeInsuredDetail> lifeInsuredDetails) {
        this.lifeInsuredDetails = lifeInsuredDetails;
    }

    public Payload withLifeInsuredDetails(List<LifeInsuredDetail> lifeInsuredDetails) {
        this.lifeInsuredDetails = lifeInsuredDetails;
        return this;
    }

    @JsonProperty("landlineNo")
    public String getLandlineNo() {
        return landlineNo;
    }

    @JsonProperty("landlineNo")
    public void setLandlineNo(String landlineNo) {
        this.landlineNo = landlineNo;
    }

    public Payload withLandlineNo(String landlineNo) {
        this.landlineNo = landlineNo;
        return this;
    }

    @JsonProperty("paymentFrequency")
    public String getPaymentFrequency() {
        return paymentFrequency;
    }

    @JsonProperty("paymentFrequency")
    public void setPaymentFrequency(String paymentFrequency) {
        this.paymentFrequency = paymentFrequency;
    }

    public Payload withPaymentFrequency(String paymentFrequency) {
        this.paymentFrequency = paymentFrequency;
        return this;
    }

    @JsonProperty("modalPremiumexclusiveGST")
    public String getModalPremiumexclusiveGST() {
        return modalPremiumexclusiveGST;
    }

    @JsonProperty("modalPremiumexclusiveGST")
    public void setModalPremiumexclusiveGST(String modalPremiumexclusiveGST) {
        this.modalPremiumexclusiveGST = modalPremiumexclusiveGST;
    }

    public Payload withModalPremiumexclusiveGST(String modalPremiumexclusiveGST) {
        this.modalPremiumexclusiveGST = modalPremiumexclusiveGST;
        return this;
    }

    @JsonProperty("modalPremiuminclusiveGST")
    public String getModalPremiuminclusiveGST() {
        return modalPremiuminclusiveGST;
    }

    @JsonProperty("modalPremiuminclusiveGST")
    public void setModalPremiuminclusiveGST(String modalPremiuminclusiveGST) {
        this.modalPremiuminclusiveGST = modalPremiuminclusiveGST;
    }

    public Payload withModalPremiuminclusiveGST(String modalPremiuminclusiveGST) {
        this.modalPremiuminclusiveGST = modalPremiuminclusiveGST;
        return this;
    }

    @JsonProperty("premiumPaymentTerm")
    public String getPremiumPaymentTerm() {
        return premiumPaymentTerm;
    }

    @JsonProperty("premiumPaymentTerm")
    public void setPremiumPaymentTerm(String premiumPaymentTerm) {
        this.premiumPaymentTerm = premiumPaymentTerm;
    }

    public Payload withPremiumPaymentTerm(String premiumPaymentTerm) {
        this.premiumPaymentTerm = premiumPaymentTerm;
        return this;
    }

    @JsonProperty("lastPremiumPayableDate")
    public String getLastPremiumPayableDate() {
        return lastPremiumPayableDate;
    }

    @JsonProperty("lastPremiumPayableDate")
    public void setLastPremiumPayableDate(String lastPremiumPayableDate) {
        this.lastPremiumPayableDate = lastPremiumPayableDate;
    }

    public Payload withLastPremiumPayableDate(String lastPremiumPayableDate) {
        this.lastPremiumPayableDate = lastPremiumPayableDate;
        return this;
    }

    @JsonProperty("nominees")
    public List<Nominee> getNominees() {
        return nominees;
    }

    @JsonProperty("nominees")
    public void setNominees(List<Nominee> nominees) {
        this.nominees = nominees;
    }

    public Payload withNominees(List<Nominee> nominees) {
        this.nominees = nominees;
        return this;
    }

    @JsonProperty("apointeeName")
    public String getApointeeName() {
        return apointeeName;
    }

    @JsonProperty("apointeeName")
    public void setApointeeName(String apointeeName) {
        this.apointeeName = apointeeName;
    }

    public Payload withApointeeName(String apointeeName) {
        this.apointeeName = apointeeName;
        return this;
    }

    @JsonProperty("methodOfPayment")
    public String getMethodOfPayment() {
        return methodOfPayment;
    }

    @JsonProperty("methodOfPayment")
    public void setMethodOfPayment(String methodOfPayment) {
        this.methodOfPayment = methodOfPayment;
    }

    public Payload withMethodOfPayment(String methodOfPayment) {
        this.methodOfPayment = methodOfPayment;
        return this;
    }

    @JsonProperty("ecsDrawDate")
    public String getEcsDrawDate() {
        return ecsDrawDate;
    }

    @JsonProperty("ecsDrawDate")
    public void setEcsDrawDate(String ecsDrawDate) {
        this.ecsDrawDate = ecsDrawDate;
    }

    public Payload withEcsDrawDate(String ecsDrawDate) {
        this.ecsDrawDate = ecsDrawDate;
        return this;
    }

    @JsonProperty("surrenderValue")
    public String getSurrenderValue() {
        return surrenderValue;
    }

    @JsonProperty("surrenderValue")
    public void setSurrenderValue(String surrenderValue) {
        this.surrenderValue = surrenderValue;
    }

    public Payload withSurrenderValue(String surrenderValue) {
        this.surrenderValue = surrenderValue;
        return this;
    }

    @JsonProperty("loanableCashValue")
    public String getLoanableCashValue() {
        return loanableCashValue;
    }

    @JsonProperty("loanableCashValue")
    public void setLoanableCashValue(String loanableCashValue) {
        this.loanableCashValue = loanableCashValue;
    }

    public Payload withLoanableCashValue(String loanableCashValue) {
        this.loanableCashValue = loanableCashValue;
        return this;
    }

    @JsonProperty("lastPaymentDate")
    public String getLastPaymentDate() {
        return lastPaymentDate;
    }

    @JsonProperty("lastPaymentDate")
    public void setLastPaymentDate(String lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    }

    public Payload withLastPaymentDate(String lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
        return this;
    }

    @JsonProperty("polInsPurpCd")
    public String getPolInsPurpCd() {
        return polInsPurpCd;
    }

    @JsonProperty("polInsPurpCd")
    public void setPolInsPurpCd(String polInsPurpCd) {
        this.polInsPurpCd = polInsPurpCd;
    }

    public Payload withPolInsPurpCd(String polInsPurpCd) {
        this.polInsPurpCd = polInsPurpCd;
        return this;
    }

    @JsonProperty("polInsPurp")
    public String getPolInsPurp() {
        return polInsPurp;
    }

    @JsonProperty("polInsPurp")
    public void setPolInsPurp(String polInsPurp) {
        this.polInsPurp = polInsPurp;
    }

    public Payload withPolInsPurp(String polInsPurp) {
        this.polInsPurp = polInsPurp;
        return this;
    }

    @JsonProperty("form2Policy")
    public String getForm2Policy() {
        return form2Policy;
    }

    @JsonProperty("form2Policy")
    public void setForm2Policy(String form2Policy) {
        this.form2Policy = form2Policy;
    }

    public Payload withForm2Policy(String form2Policy) {
        this.form2Policy = form2Policy;
        return this;
    }

    @JsonProperty("polRejectionReason")
    public String getPolRejectionReason() {
        return polRejectionReason;
    }

    @JsonProperty("polRejectionReason")
    public void setPolRejectionReason(String polRejectionReason) {
        this.polRejectionReason = polRejectionReason;
    }

    public Payload withPolRejectionReason(String polRejectionReason) {
        this.polRejectionReason = polRejectionReason;
        return this;
    }

    @JsonProperty("employerEmployeePol")
    public String getEmployerEmployeePol() {
        return employerEmployeePol;
    }

    @JsonProperty("employerEmployeePol")
    public void setEmployerEmployeePol(String employerEmployeePol) {
        this.employerEmployeePol = employerEmployeePol;
    }

    public Payload withEmployerEmployeePol(String employerEmployeePol) {
        this.employerEmployeePol = employerEmployeePol;
        return this;
    }

    @JsonProperty("keyManPolicy")
    public String getKeyManPolicy() {
        return keyManPolicy;
    }

    @JsonProperty("keyManPolicy")
    public void setKeyManPolicy(String keyManPolicy) {
        this.keyManPolicy = keyManPolicy;
    }

    public Payload withKeyManPolicy(String keyManPolicy) {
        this.keyManPolicy = keyManPolicy;
        return this;
    }

    @JsonProperty("tdsExempt")
    public String getTdsExempt() {
        return tdsExempt;
    }

    @JsonProperty("tdsExempt")
    public void setTdsExempt(String tdsExempt) {
        this.tdsExempt = tdsExempt;
    }

    public Payload withTdsExempt(String tdsExempt) {
        this.tdsExempt = tdsExempt;
        return this;
    }

    @JsonProperty("policyAssignedMaxLife")
    public String getPolicyAssignedMaxLife() {
        return policyAssignedMaxLife;
    }

    @JsonProperty("policyAssignedMaxLife")
    public void setPolicyAssignedMaxLife(String policyAssignedMaxLife) {
        this.policyAssignedMaxLife = policyAssignedMaxLife;
    }

    public Payload withPolicyAssignedMaxLife(String policyAssignedMaxLife) {
        this.policyAssignedMaxLife = policyAssignedMaxLife;
        return this;
    }

    @JsonProperty("lastPaymentAmount")
    public String getLastPaymentAmount() {
        return lastPaymentAmount;
    }

    @JsonProperty("lastPaymentAmount")
    public void setLastPaymentAmount(String lastPaymentAmount) {
        this.lastPaymentAmount = lastPaymentAmount;
    }

    public Payload withLastPaymentAmount(String lastPaymentAmount) {
        this.lastPaymentAmount = lastPaymentAmount;
        return this;
    }

    @JsonProperty("currentBonusOption")
    public String getCurrentBonusOption() {
        return currentBonusOption;
    }

    @JsonProperty("currentBonusOption")
    public void setCurrentBonusOption(String currentBonusOption) {
        this.currentBonusOption = currentBonusOption;
    }

    public Payload withCurrentBonusOption(String currentBonusOption) {
        this.currentBonusOption = currentBonusOption;
        return this;
    }

    @JsonProperty("grossAnnualPremium")
    public String getGrossAnnualPremium() {
        return grossAnnualPremium;
    }

    @JsonProperty("grossAnnualPremium")
    public void setGrossAnnualPremium(String grossAnnualPremium) {
        this.grossAnnualPremium = grossAnnualPremium;
    }

    public Payload withGrossAnnualPremium(String grossAnnualPremium) {
        this.grossAnnualPremium = grossAnnualPremium;
        return this;
    }

    @JsonProperty("polCumUnclrdAmt")
    public String getPolCumUnclrdAmt() {
        return polCumUnclrdAmt;
    }

    @JsonProperty("polCumUnclrdAmt")
    public void setPolCumUnclrdAmt(String polCumUnclrdAmt) {
        this.polCumUnclrdAmt = polCumUnclrdAmt;
    }

    public Payload withPolCumUnclrdAmt(String polCumUnclrdAmt) {
        this.polCumUnclrdAmt = polCumUnclrdAmt;
        return this;
    }

    @JsonProperty("polPremSuspAmt")
    public String getPolPremSuspAmt() {
        return polPremSuspAmt;
    }

    @JsonProperty("polPremSuspAmt")
    public void setPolPremSuspAmt(String polPremSuspAmt) {
        this.polPremSuspAmt = polPremSuspAmt;
    }

    public Payload withPolPremSuspAmt(String polPremSuspAmt) {
        this.polPremSuspAmt = polPremSuspAmt;
        return this;
    }

    @JsonProperty("polMiscSuspAmt")
    public String getPolMiscSuspAmt() {
        return polMiscSuspAmt;
    }

    @JsonProperty("polMiscSuspAmt")
    public void setPolMiscSuspAmt(String polMiscSuspAmt) {
        this.polMiscSuspAmt = polMiscSuspAmt;
    }

    public Payload withPolMiscSuspAmt(String polMiscSuspAmt) {
        this.polMiscSuspAmt = polMiscSuspAmt;
        return this;
    }

    @JsonProperty("polOsDisbAmt")
    public String getPolOsDisbAmt() {
        return polOsDisbAmt;
    }

    @JsonProperty("polOsDisbAmt")
    public void setPolOsDisbAmt(String polOsDisbAmt) {
        this.polOsDisbAmt = polOsDisbAmt;
    }

    public Payload withPolOsDisbAmt(String polOsDisbAmt) {
        this.polOsDisbAmt = polOsDisbAmt;
        return this;
    }

    @JsonProperty("polGrossPremAmt")
    public String getPolGrossPremAmt() {
        return polGrossPremAmt;
    }

    @JsonProperty("polGrossPremAmt")
    public void setPolGrossPremAmt(String polGrossPremAmt) {
        this.polGrossPremAmt = polGrossPremAmt;
    }

    public Payload withPolGrossPremAmt(String polGrossPremAmt) {
        this.polGrossPremAmt = polGrossPremAmt;
        return this;
    }

    @JsonProperty("policyInsuranceTypeCd")
    public String getPolicyInsuranceTypeCd() {
        return policyInsuranceTypeCd;
    }

    @JsonProperty("policyInsuranceTypeCd")
    public void setPolicyInsuranceTypeCd(String policyInsuranceTypeCd) {
        this.policyInsuranceTypeCd = policyInsuranceTypeCd;
    }

    public Payload withPolicyInsuranceTypeCd(String policyInsuranceTypeCd) {
        this.policyInsuranceTypeCd = policyInsuranceTypeCd;
        return this;
    }

    @JsonProperty("PolSuprsIssInd")
    public String getPolSuprsIssInd() {
        return polSuprsIssInd;
    }

    @JsonProperty("PolSuprsIssInd")
    public void setPolSuprsIssInd(String polSuprsIssInd) {
        this.polSuprsIssInd = polSuprsIssInd;
    }

    public Payload withPolSuprsIssInd(String polSuprsIssInd) {
        this.polSuprsIssInd = polSuprsIssInd;
        return this;
    }

    @JsonProperty("polAppReceiveDt")
    public String getPolAppReceiveDt() {
        return polAppReceiveDt;
    }

    @JsonProperty("polAppReceiveDt")
    public void setPolAppReceiveDt(String polAppReceiveDt) {
        this.polAppReceiveDt = polAppReceiveDt;
    }

    public Payload withPolAppReceiveDt(String polAppReceiveDt) {
        this.polAppReceiveDt = polAppReceiveDt;
        return this;
    }

    @JsonProperty("bonusDetails")
    public List<BonusDetail> getBonusDetails() {
        return bonusDetails;
    }

    @JsonProperty("bonusDetails")
    public void setBonusDetails(List<BonusDetail> bonusDetails) {
        this.bonusDetails = bonusDetails;
    }

    public Payload withBonusDetails(List<BonusDetail> bonusDetails) {
        this.bonusDetails = bonusDetails;
        return this;
    }

    @JsonProperty("lastBonusDate")
    public String getLastBonusDate() {
        return lastBonusDate;
    }

    @JsonProperty("lastBonusDate")
    public void setLastBonusDate(String lastBonusDate) {
        this.lastBonusDate = lastBonusDate;
    }

    public Payload withLastBonusDate(String lastBonusDate) {
        this.lastBonusDate = lastBonusDate;
        return this;
    }

    @JsonProperty("lastBonusAmount")
    public String getLastBonusAmount() {
        return lastBonusAmount;
    }

    @JsonProperty("lastBonusAmount")
    public void setLastBonusAmount(String lastBonusAmount) {
        this.lastBonusAmount = lastBonusAmount;
    }

    public Payload withLastBonusAmount(String lastBonusAmount) {
        this.lastBonusAmount = lastBonusAmount;
        return this;
    }

    @JsonProperty("riderDetails")
    public List<Object> getRiderDetails() {
        return riderDetails;
    }

    @JsonProperty("riderDetails")
    public void setRiderDetails(List<Object> riderDetails) {
        this.riderDetails = riderDetails;
    }

    public Payload withRiderDetails(List<Object> riderDetails) {
        this.riderDetails = riderDetails;
        return this;
    }

    @JsonProperty("neftAccountNum")
    public String getNeftAccountNum() {
        return neftAccountNum;
    }

    @JsonProperty("neftAccountNum")
    public void setNeftAccountNum(String neftAccountNum) {
        this.neftAccountNum = neftAccountNum;
    }

    public Payload withNeftAccountNum(String neftAccountNum) {
        this.neftAccountNum = neftAccountNum;
        return this;
    }

    @JsonProperty("ifscCode")
    public String getIfscCode() {
        return ifscCode;
    }

    @JsonProperty("ifscCode")
    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public Payload withIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
        return this;
    }

    @JsonProperty("micrCode")
    public String getMicrCode() {
        return micrCode;
    }

    @JsonProperty("micrCode")
    public void setMicrCode(String micrCode) {
        this.micrCode = micrCode;
    }

    public Payload withMicrCode(String micrCode) {
        this.micrCode = micrCode;
        return this;
    }

    @JsonProperty("accountType")
    public String getAccountType() {
        return accountType;
    }

    @JsonProperty("accountType")
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Payload withAccountType(String accountType) {
        this.accountType = accountType;
        return this;
    }

    @JsonProperty("accountHolder")
    public String getAccountHolder() {
        return accountHolder;
    }

    @JsonProperty("accountHolder")
    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public Payload withAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
        return this;
    }

    @JsonProperty("bankName")
    public String getBankName() {
        return bankName;
    }

    @JsonProperty("bankName")
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Payload withBankName(String bankName) {
        this.bankName = bankName;
        return this;
    }

    @JsonProperty("branchName")
    public String getBranchName() {
        return branchName;
    }

    @JsonProperty("branchName")
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Payload withBranchName(String branchName) {
        this.branchName = branchName;
        return this;
    }

    @JsonProperty("ownerClientAccount")
    public String getOwnerClientAccount() {
        return ownerClientAccount;
    }

    @JsonProperty("ownerClientAccount")
    public void setOwnerClientAccount(String ownerClientAccount) {
        this.ownerClientAccount = ownerClientAccount;
    }

    public Payload withOwnerClientAccount(String ownerClientAccount) {
        this.ownerClientAccount = ownerClientAccount;
        return this;
    }

    @JsonProperty("refundDate")
    public String getRefundDate() {
        return refundDate;
    }

    @JsonProperty("refundDate")
    public void setRefundDate(String refundDate) {
        this.refundDate = refundDate;
    }

    public Payload withRefundDate(String refundDate) {
        this.refundDate = refundDate;
        return this;
    }

    @JsonProperty("refundAmount")
    public String getRefundAmount() {
        return refundAmount;
    }

    @JsonProperty("refundAmount")
    public void setRefundAmount(String refundAmount) {
        this.refundAmount = refundAmount;
    }

    public Payload withRefundAmount(String refundAmount) {
        this.refundAmount = refundAmount;
        return this;
    }

    @JsonProperty("refundMethodCd")
    public String getRefundMethodCd() {
        return refundMethodCd;
    }

    @JsonProperty("refundMethodCd")
    public void setRefundMethodCd(String refundMethodCd) {
        this.refundMethodCd = refundMethodCd;
    }

    public Payload withRefundMethodCd(String refundMethodCd) {
        this.refundMethodCd = refundMethodCd;
        return this;
    }

    @JsonProperty("refundMethodDesc")
    public String getRefundMethodDesc() {
        return refundMethodDesc;
    }

    @JsonProperty("refundMethodDesc")
    public void setRefundMethodDesc(String refundMethodDesc) {
        this.refundMethodDesc = refundMethodDesc;
    }

    public Payload withRefundMethodDesc(String refundMethodDesc) {
        this.refundMethodDesc = refundMethodDesc;
        return this;
    }

    @JsonProperty("refundDetails")
    public List<RefundDetail> getRefundDetails() {
        return refundDetails;
    }

    @JsonProperty("refundDetails")
    public void setRefundDetails(List<RefundDetail> refundDetails) {
        this.refundDetails = refundDetails;
    }

    public Payload withRefundDetails(List<RefundDetail> refundDetails) {
        this.refundDetails = refundDetails;
        return this;
    }

    @JsonProperty("paymentHistory")
    public List<PaymentHistory> getPaymentHistory() {
        return paymentHistory;
    }

    @JsonProperty("paymentHistory")
    public void setPaymentHistory(List<PaymentHistory> paymentHistory) {
        this.paymentHistory = paymentHistory;
    }

    public Payload withPaymentHistory(List<PaymentHistory> paymentHistory) {
        this.paymentHistory = paymentHistory;
        return this;
    }

    @JsonProperty("addressDetails")
    public List<AddressDetails> getAddressDetails() {
        return addressDetails;
    }

    @JsonProperty("addressDetails")
    public void setAddressDetails(List<AddressDetails> addressDetails) {
        this.addressDetails = addressDetails;
    }

    public Payload withAddressDetails(List<AddressDetails> addressDetails) {
        this.addressDetails = addressDetails;
        return this;
    }

    @JsonProperty("prefAddrCode")
    public String getPrefAddrCode() {
        return prefAddrCode;
    }

    @JsonProperty("prefAddrCode")
    public void setPrefAddrCode(String prefAddrCode) {
        this.prefAddrCode = prefAddrCode;
    }

    public Payload withPrefAddrCode(String prefAddrCode) {
        this.prefAddrCode = prefAddrCode;
        return this;
    }

    @JsonProperty("mobileNo")
    public String getMobileNo() {
        return mobileNo;
    }

    @JsonProperty("mobileNo")
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Payload withMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
        return this;
    }

    @JsonProperty("dob")
    public String getDob() {
        return dob;
    }

    @JsonProperty("dob")
    public void setDob(String dob) {
        this.dob = dob;
    }

    public Payload withDob(String dob) {
        this.dob = dob;
        return this;
    }

    @JsonProperty("emailId")
    public String getEmailId() {
        return emailId;
    }

    @JsonProperty("emailId")
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Payload withEmailId(String emailId) {
        this.emailId = emailId;
        return this;
    }

    @JsonProperty("ownerEarnedIncome")
    public String getOwnerEarnedIncome() {
        return ownerEarnedIncome;
    }

    @JsonProperty("ownerEarnedIncome")
    public void setOwnerEarnedIncome(String ownerEarnedIncome) {
        this.ownerEarnedIncome = ownerEarnedIncome;
    }

    public Payload withOwnerEarnedIncome(String ownerEarnedIncome) {
        this.ownerEarnedIncome = ownerEarnedIncome;
        return this;
    }

    @JsonProperty("agentName")
    public String getAgentName() {
        return agentName;
    }

    @JsonProperty("agentName")
    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public Payload withAgentName(String agentName) {
        this.agentName = agentName;
        return this;
    }

    @JsonProperty("agentCode")
    public String getAgentCode() {
        return agentCode;
    }

    @JsonProperty("agentCode")
    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public Payload withAgentCode(String agentCode) {
        this.agentCode = agentCode;
        return this;
    }

    @JsonProperty("agentEmailId")
    public String getAgentEmailId() {
        return agentEmailId;
    }

    @JsonProperty("agentEmailId")
    public void setAgentEmailId(String agentEmailId) {
        this.agentEmailId = agentEmailId;
    }

    public Payload withAgentEmailId(String agentEmailId) {
        this.agentEmailId = agentEmailId;
        return this;
    }

    @JsonProperty("agentMobileNo")
    public String getAgentMobileNo() {
        return agentMobileNo;
    }

    @JsonProperty("agentMobileNo")
    public void setAgentMobileNo(String agentMobileNo) {
        this.agentMobileNo = agentMobileNo;
    }

    public Payload withAgentMobileNo(String agentMobileNo) {
        this.agentMobileNo = agentMobileNo;
        return this;
    }

    @JsonProperty("servicingAgentId")
    public String getServicingAgentId() {
        return servicingAgentId;
    }

    @JsonProperty("servicingAgentId")
    public void setServicingAgentId(String servicingAgentId) {
        this.servicingAgentId = servicingAgentId;
    }

    public Payload withServicingAgentId(String servicingAgentId) {
        this.servicingAgentId = servicingAgentId;
        return this;
    }

    @JsonProperty("servicingAgentBranchId")
    public String getServicingAgentBranchId() {
        return servicingAgentBranchId;
    }

    @JsonProperty("servicingAgentBranchId")
    public void setServicingAgentBranchId(String servicingAgentBranchId) {
        this.servicingAgentBranchId = servicingAgentBranchId;
    }

    public Payload withServicingAgentBranchId(String servicingAgentBranchId) {
        this.servicingAgentBranchId = servicingAgentBranchId;
        return this;
    }

    @JsonProperty("policyRelationships")
    public List<PolicyRelationship> getPolicyRelationships() {
        return policyRelationships;
    }

    @JsonProperty("policyRelationships")
    public void setPolicyRelationships(List<PolicyRelationship> policyRelationships) {
        this.policyRelationships = policyRelationships;
    }

    public Payload withPolicyRelationships(List<PolicyRelationship> policyRelationships) {
        this.policyRelationships = policyRelationships;
        return this;
    }

    @JsonProperty("coverageDetails")
    public List<CoverageDetail> getCoverageDetails() {
        return coverageDetails;
    }

    @JsonProperty("coverageDetails")
    public void setCoverageDetails(List<CoverageDetail> coverageDetails) {
        this.coverageDetails = coverageDetails;
    }

    public Payload withCoverageDetails(List<CoverageDetail> coverageDetails) {
        this.coverageDetails = coverageDetails;
        return this;
    }

    @JsonProperty("eiaDetails")
    public EiaDetails getEiaDetails() {
        return eiaDetails;
    }

    @JsonProperty("eiaDetails")
    public void setEiaDetails(EiaDetails eiaDetails) {
        this.eiaDetails = eiaDetails;
    }

    public Payload withEiaDetails(EiaDetails eiaDetails) {
        this.eiaDetails = eiaDetails;
        return this;
    }

    @JsonProperty("relationshipUnderPolicy")
    public List<RelationshipUnderPolicy> getRelationshipUnderPolicy() {
        return relationshipUnderPolicy;
    }

    @JsonProperty("relationshipUnderPolicy")
    public void setRelationshipUnderPolicy(List<RelationshipUnderPolicy> relationshipUnderPolicy) {
        this.relationshipUnderPolicy = relationshipUnderPolicy;
    }

    public Payload withRelationshipUnderPolicy(List<RelationshipUnderPolicy> relationshipUnderPolicy) {
        this.relationshipUnderPolicy = relationshipUnderPolicy;
        return this;
    }

    @JsonProperty("requirementUnderPolicy")
    public List<RequirementUnderPolicy> getRequirementUnderPolicy() {
        return requirementUnderPolicy;
    }

    @JsonProperty("requirementUnderPolicy")
    public void setRequirementUnderPolicy(List<RequirementUnderPolicy> requirementUnderPolicy) {
        this.requirementUnderPolicy = requirementUnderPolicy;
    }

    public Payload withRequirementUnderPolicy(List<RequirementUnderPolicy> requirementUnderPolicy) {
        this.requirementUnderPolicy = requirementUnderPolicy;
        return this;
    }

    @JsonProperty("planDetails")
    public PlanDetails getPlanDetails() {
        return planDetails;
    }

    @JsonProperty("planDetails")
    public void setPlanDetails(PlanDetails planDetails) {
        this.planDetails = planDetails;
    }

    public Payload withPlanDetails(PlanDetails planDetails) {
        this.planDetails = planDetails;
        return this;
    }

	@Override
	public String toString() {

        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }

		return "Payload [policyNo=" + policyNo + ", planName=" + planName + ", planType=" + planType
				+ ", policyIssueDate=" + policyIssueDate + ", policyStatusCd=" + policyStatusCd + ", policyStatusDesc="
				+ policyStatusDesc + ", term=" + term + ", policyMaturityDateBase=" + policyMaturityDateBase
				+ ", baseSumAssured=" + baseSumAssured + ", modalPremiumBase=" + modalPremiumBase + ", premiumModeCd="
				+ premiumModeCd + ", premiumModeDesc=" + premiumModeDesc + ", premiumDueDate=" + premiumDueDate
				+ ", policyPtToDtNum=" + policyPtToDtNum + ", bounceDates=" + bounceDates + ", premiumDueAmount="
				+ premiumDueAmount + ", policyHolderName=" + policyHolderName + ", totalFundValue=" + totalFundValue
				+ ", hybridPolicyFlag=" + hybridPolicyFlag + ", assignedPolicyFlag=" + assignedPolicyFlag
				+ ", lifeInsuredDetails=" + lifeInsuredDetails + ", landlineNo=" + landlineNo + ", paymentFrequency="
				+ paymentFrequency + ", modalPremiumexclusiveGST=" + modalPremiumexclusiveGST
				+ ", modalPremiuminclusiveGST=" + modalPremiuminclusiveGST + ", premiumPaymentTerm="
				+ premiumPaymentTerm + ", lastPremiumPayableDate=" + lastPremiumPayableDate + ", nominees=" + nominees
				+ ", apointeeName=" + apointeeName + ", methodOfPayment=" + methodOfPayment + ", ecsDrawDate="
				+ ecsDrawDate + ", surrenderValue=" + surrenderValue + ", loanableCashValue=" + loanableCashValue
				+ ", lastPaymentDate=" + lastPaymentDate + ", polInsPurpCd=" + polInsPurpCd + ", polInsPurp="
				+ polInsPurp + ", form2Policy=" + form2Policy + ", polRejectionReason=" + polRejectionReason
				+ ", employerEmployeePol=" + employerEmployeePol + ", keyManPolicy=" + keyManPolicy + ", tdsExempt="
				+ tdsExempt + ", policyAssignedMaxLife=" + policyAssignedMaxLife + ", lastPaymentAmount="
				+ lastPaymentAmount + ", currentBonusOption=" + currentBonusOption + ", grossAnnualPremium="
				+ grossAnnualPremium + ", polCumUnclrdAmt=" + polCumUnclrdAmt + ", polPremSuspAmt=" + polPremSuspAmt
				+ ", polMiscSuspAmt=" + polMiscSuspAmt + ", polOsDisbAmt=" + polOsDisbAmt + ", polGrossPremAmt="
				+ polGrossPremAmt + ", policyInsuranceTypeCd=" + policyInsuranceTypeCd + ", polSuprsIssInd="
				+ polSuprsIssInd + ", polAppReceiveDt=" + polAppReceiveDt + ", bonusDetails=" + bonusDetails
				+ ", lastBonusDate=" + lastBonusDate + ", lastBonusAmount=" + lastBonusAmount + ", riderDetails="
				+ riderDetails + ", neftAccountNum=" + neftAccountNum + ", ifscCode=" + ifscCode + ", micrCode="
				+ micrCode + ", accountType=" + accountType + ", accountHolder=" + accountHolder + ", bankName="
				+ bankName + ", branchName=" + branchName + ", ownerClientAccount=" + ownerClientAccount
				+ ", refundDate=" + refundDate + ", refundAmount=" + refundAmount + ", refundMethodCd=" + refundMethodCd
				+ ", refundMethodDesc=" + refundMethodDesc + ", refundDetails=" + refundDetails + ", paymentHistory="
				+ paymentHistory + ", addressDetails=" + addressDetails + ", prefAddrCode=" + prefAddrCode
				+ ", mobileNo=" + mobileNo + ", dob=" + dob + ", emailId=" + emailId + ", ownerEarnedIncome="
				+ ownerEarnedIncome + ", agentName=" + agentName + ", agentCode=" + agentCode + ", agentEmailId="
				+ agentEmailId + ", agentMobileNo=" + agentMobileNo + ", servicingAgentId=" + servicingAgentId
				+ ", servicingAgentBranchId=" + servicingAgentBranchId + ", policyRelationships=" + policyRelationships
				+ ", coverageDetails=" + coverageDetails + ", eiaDetails=" + eiaDetails + ", relationshipUnderPolicy="
				+ relationshipUnderPolicy + ", requirementUnderPolicy=" + requirementUnderPolicy + ", planDetails=]";
	}

  

}
