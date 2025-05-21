package com.mli.mpro.location.models.soaCloudModels;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class AccountDetail {
        @JsonProperty("type")
        private String type;

        @JsonProperty("bankName")
        private String bankName;

        @JsonProperty("bankLocation")
        private String bankLocation;

        @JsonProperty("ifscCode")
        private String ifscCode;

        @JsonProperty("accntNum")
        private String accntNum;

        @JsonProperty("accntTypeCd")
        private String accntTypeCd;

        @JsonProperty("accntTypeDesc")
        private String accntTypeDesc;

        @JsonProperty("accntHolderName")
        private String accntHolderName;

        @JsonProperty("mandateRejectionDt")
        private String mandateRejectionDt;

        @JsonProperty("chqFromAccntMICREncd")
        private String chqFromAccntMICREncd;

        @JsonProperty("neftQcPassCd")
        private String neftQcPassCd;

        @JsonProperty("neftQcPassDesc")
        private String neftQcPassDesc;

        @JsonProperty("reasonForDiscrepantCd")
        private String reasonForDiscrepantCd;

        @JsonProperty("reasonForDiscrepantDesc")
        private String reasonForDiscrepantDesc;

        @JsonProperty("micrCode")
        private String micrCode;

        @JsonProperty("currentStateCd")
        private String currentStateCd;

        @JsonProperty("currentStateDesc")
        private String currentStateDesc;

        @JsonProperty("ecsMandateRegStatus")
        private String ecsMandateRegStatus;

        @JsonProperty("ingOwnClientAcc")
        private String ingOwnClientAcc;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankLocation() {
        return bankLocation;
    }

    public void setBankLocation(String bankLocation) {
        this.bankLocation = bankLocation;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getAccntNum() {
        return accntNum;
    }

    public void setAccntNum(String accntNum) {
        this.accntNum = accntNum;
    }

    public String getAccntTypeCd() {
        return accntTypeCd;
    }

    public void setAccntTypeCd(String accntTypeCd) {
        this.accntTypeCd = accntTypeCd;
    }

    public String getAccntTypeDesc() {
        return accntTypeDesc;
    }

    public void setAccntTypeDesc(String accntTypeDesc) {
        this.accntTypeDesc = accntTypeDesc;
    }

    public String getAccntHolderName() {
        return accntHolderName;
    }

    public void setAccntHolderName(String accntHolderName) {
        this.accntHolderName = accntHolderName;
    }

    public String getMandateRejectionDt() {
        return mandateRejectionDt;
    }

    public void setMandateRejectionDt(String mandateRejectionDt) {
        this.mandateRejectionDt = mandateRejectionDt;
    }

    public String getChqFromAccntMICREncd() {
        return chqFromAccntMICREncd;
    }

    public void setChqFromAccntMICREncd(String chqFromAccntMICREncd) {
        this.chqFromAccntMICREncd = chqFromAccntMICREncd;
    }

    public String getNeftQcPassCd() {
        return neftQcPassCd;
    }

    public void setNeftQcPassCd(String neftQcPassCd) {
        this.neftQcPassCd = neftQcPassCd;
    }

    public String getNeftQcPassDesc() {
        return neftQcPassDesc;
    }

    public void setNeftQcPassDesc(String neftQcPassDesc) {
        this.neftQcPassDesc = neftQcPassDesc;
    }

    public String getReasonForDiscrepantCd() {
        return reasonForDiscrepantCd;
    }

    public void setReasonForDiscrepantCd(String reasonForDiscrepantCd) {
        this.reasonForDiscrepantCd = reasonForDiscrepantCd;
    }

    public String getReasonForDiscrepantDesc() {
        return reasonForDiscrepantDesc;
    }

    public void setReasonForDiscrepantDesc(String reasonForDiscrepantDesc) {
        this.reasonForDiscrepantDesc = reasonForDiscrepantDesc;
    }

    public String getMicrCode() {
        return micrCode;
    }

    public void setMicrCode(String micrCode) {
        this.micrCode = micrCode;
    }

    public String getCurrentStateCd() {
        return currentStateCd;
    }

    public void setCurrentStateCd(String currentStateCd) {
        this.currentStateCd = currentStateCd;
    }

    public String getCurrentStateDesc() {
        return currentStateDesc;
    }

    public void setCurrentStateDesc(String currentStateDesc) {
        this.currentStateDesc = currentStateDesc;
    }

    public String getEcsMandateRegStatus() {
        return ecsMandateRegStatus;
    }

    public void setEcsMandateRegStatus(String ecsMandateRegStatus) {
        this.ecsMandateRegStatus = ecsMandateRegStatus;
    }

    public String getIngOwnClientAcc() {
        return ingOwnClientAcc;
    }

    public void setIngOwnClientAcc(String ingOwnClientAcc) {
        this.ingOwnClientAcc = ingOwnClientAcc;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "AccountDetail{" +
                "type='" + type + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankLocation='" + bankLocation + '\'' +
                ", ifscCode='" + ifscCode + '\'' +
                ", accntNum='" + accntNum + '\'' +
                ", accntTypeCd='" + accntTypeCd + '\'' +
                ", accntTypeDesc='" + accntTypeDesc + '\'' +
                ", accntHolderName='" + accntHolderName + '\'' +
                ", mandateRejectionDt='" + mandateRejectionDt + '\'' +
                ", chqFromAccntMICREncd='" + chqFromAccntMICREncd + '\'' +
                ", neftQcPassCd='" + neftQcPassCd + '\'' +
                ", neftQcPassDesc='" + neftQcPassDesc + '\'' +
                ", reasonForDiscrepantCd='" + reasonForDiscrepantCd + '\'' +
                ", reasonForDiscrepantDesc='" + reasonForDiscrepantDesc + '\'' +
                ", micrCode='" + micrCode + '\'' +
                ", currentStateCd='" + currentStateCd + '\'' +
                ", currentStateDesc='" + currentStateDesc + '\'' +
                ", ecsMandateRegStatus='" + ecsMandateRegStatus + '\'' +
                ", ingOwnClientAcc='" + ingOwnClientAcc + '\'' +
                '}';
    }
}
