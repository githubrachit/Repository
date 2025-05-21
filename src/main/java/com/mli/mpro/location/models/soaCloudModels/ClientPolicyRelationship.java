package com.mli.mpro.location.models.soaCloudModels;
import com.fasterxml.jackson.annotation.JsonProperty;
public class ClientPolicyRelationship {
        @JsonProperty("polId")
        private String polId;
        @JsonProperty("cliId")
        private String cliId;
        @JsonProperty("policyStatus")
        private String policyStatus;
        @JsonProperty("policyStatusDesc")
        private String policyStatusDesc;

        @JsonProperty("relationship")
        private String relationship;

        @JsonProperty("relationshipDesc")
        private String relationshipDesc;

        @JsonProperty("shareOfPercent")
        private String shareOfPercent;

        @JsonProperty("polCliRelTypCd")
        private String polCliRelTypCd;

        @JsonProperty("policyBasePlanIdCd")
        private String policyBasePlanIdCd;

        @JsonProperty("policyBasePlanIdDesc")
        private String policyBasePlanIdDesc;

        @JsonProperty("policyBillingTypeCd")
        private String policyBillingTypeCd;

        @JsonProperty("policyBillingTypeDesc")
        private String policyBillingTypeDesc;

        @JsonProperty("policyRelationshipTypeCd")
        private String policyRelationshipTypeCd;

        @JsonProperty("policyRelationshipTypeDesc")
        private String policyRelationshipTypeDesc;

        @JsonProperty("billingFreqCd")
        private String billingFreqCd;

        @JsonProperty("billingFreqDesc")
        private String billingFreqDesc;

        @JsonProperty("lastPremPmtDt")
        private String lastPremPmtDt;

        @JsonProperty("polDueDate")
        private String polDueDate;

        @JsonProperty("maturityDate")
        private String maturityDate;

        @JsonProperty("planDescription")
        private String planDescription;

        @JsonProperty("comboPolId")
        private String comboPolId;

        @JsonProperty("comboPkgInd")
        private String comboPkgInd;

    public String getPolId() {
        return polId;
    }

    public void setPolId(String polId) {
        this.polId = polId;
    }

    public String getCliId() {
        return cliId;
    }

    public void setCliId(String cliId) {
        this.cliId = cliId;
    }

    public String getPolicyStatus() {
        return policyStatus;
    }

    public void setPolicyStatus(String policyStatus) {
        this.policyStatus = policyStatus;
    }

    public String getPolicyStatusDesc() {
        return policyStatusDesc;
    }

    public void setPolicyStatusDesc(String policyStatusDesc) {
        this.policyStatusDesc = policyStatusDesc;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getRelationshipDesc() {
        return relationshipDesc;
    }

    public void setRelationshipDesc(String relationshipDesc) {
        this.relationshipDesc = relationshipDesc;
    }

    public String getShareOfPercent() {
        return shareOfPercent;
    }

    public void setShareOfPercent(String shareOfPercent) {
        this.shareOfPercent = shareOfPercent;
    }

    public String getPolCliRelTypCd() {
        return polCliRelTypCd;
    }

    public void setPolCliRelTypCd(String polCliRelTypCd) {
        this.polCliRelTypCd = polCliRelTypCd;
    }

    public String getPolicyBasePlanIdCd() {
        return policyBasePlanIdCd;
    }

    public void setPolicyBasePlanIdCd(String policyBasePlanIdCd) {
        this.policyBasePlanIdCd = policyBasePlanIdCd;
    }

    public String getPolicyBasePlanIdDesc() {
        return policyBasePlanIdDesc;
    }

    public void setPolicyBasePlanIdDesc(String policyBasePlanIdDesc) {
        this.policyBasePlanIdDesc = policyBasePlanIdDesc;
    }

    public String getPolicyBillingTypeCd() {
        return policyBillingTypeCd;
    }

    public void setPolicyBillingTypeCd(String policyBillingTypeCd) {
        this.policyBillingTypeCd = policyBillingTypeCd;
    }

    public String getPolicyBillingTypeDesc() {
        return policyBillingTypeDesc;
    }

    public void setPolicyBillingTypeDesc(String policyBillingTypeDesc) {
        this.policyBillingTypeDesc = policyBillingTypeDesc;
    }

    public String getPolicyRelationshipTypeCd() {
        return policyRelationshipTypeCd;
    }

    public void setPolicyRelationshipTypeCd(String policyRelationshipTypeCd) {
        this.policyRelationshipTypeCd = policyRelationshipTypeCd;
    }

    public String getPolicyRelationshipTypeDesc() {
        return policyRelationshipTypeDesc;
    }

    public void setPolicyRelationshipTypeDesc(String policyRelationshipTypeDesc) {
        this.policyRelationshipTypeDesc = policyRelationshipTypeDesc;
    }

    public String getBillingFreqCd() {
        return billingFreqCd;
    }

    public void setBillingFreqCd(String billingFreqCd) {
        this.billingFreqCd = billingFreqCd;
    }

    public String getBillingFreqDesc() {
        return billingFreqDesc;
    }

    public void setBillingFreqDesc(String billingFreqDesc) {
        this.billingFreqDesc = billingFreqDesc;
    }

    public String getLastPremPmtDt() {
        return lastPremPmtDt;
    }

    public void setLastPremPmtDt(String lastPremPmtDt) {
        this.lastPremPmtDt = lastPremPmtDt;
    }

    public String getPolDueDate() {
        return polDueDate;
    }

    public void setPolDueDate(String polDueDate) {
        this.polDueDate = polDueDate;
    }

    public String getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(String maturityDate) {
        this.maturityDate = maturityDate;
    }

    public String getPlanDescription() {
        return planDescription;
    }

    public void setPlanDescription(String planDescription) {
        this.planDescription = planDescription;
    }

    public String getComboPolId() {
        return comboPolId;
    }

    public void setComboPolId(String comboPolId) {
        this.comboPolId = comboPolId;
    }

    public String getComboPkgInd() {
        return comboPkgInd;
    }

    public void setComboPkgInd(String comboPkgInd) {
        this.comboPkgInd = comboPkgInd;
    }

    @Override
    public String toString() {
        return "ClientPolicyRelationship{" +
                "polId='" + polId + '\'' +
                ", cliId='" + cliId + '\'' +
                ", policyStatus='" + policyStatus + '\'' +
                ", policyStatusDesc='" + policyStatusDesc + '\'' +
                ", relationship='" + relationship + '\'' +
                ", relationshipDesc='" + relationshipDesc + '\'' +
                ", shareOfPercent='" + shareOfPercent + '\'' +
                ", polCliRelTypCd='" + polCliRelTypCd + '\'' +
                ", policyBasePlanIdCd='" + policyBasePlanIdCd + '\'' +
                ", policyBasePlanIdDesc='" + policyBasePlanIdDesc + '\'' +
                ", policyBillingTypeCd='" + policyBillingTypeCd + '\'' +
                ", policyBillingTypeDesc='" + policyBillingTypeDesc + '\'' +
                ", policyRelationshipTypeCd='" + policyRelationshipTypeCd + '\'' +
                ", policyRelationshipTypeDesc='" + policyRelationshipTypeDesc + '\'' +
                ", billingFreqCd='" + billingFreqCd + '\'' +
                ", billingFreqDesc='" + billingFreqDesc + '\'' +
                ", lastPremPmtDt='" + lastPremPmtDt + '\'' +
                ", polDueDate='" + polDueDate + '\'' +
                ", maturityDate='" + maturityDate + '\'' +
                ", planDescription='" + planDescription + '\'' +
                ", comboPolId='" + comboPolId + '\'' +
                ", comboPkgInd='" + comboPkgInd + '\'' +
                '}';
    }
}
