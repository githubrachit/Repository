package com.mli.mpro.location.models.soaCloudModels;
import com.fasterxml.jackson.annotation.JsonProperty;
public class IdentifiableInfo {
        @JsonProperty("idProof")
        private String idProof;

        @JsonProperty("idenPrfNum")
        private String idenPrfNum;

        @JsonProperty("panNum")
        private String panNum;

        @JsonProperty("panUpdateDt")
        private String panUpdateDt;

        @JsonProperty("adharCard")
        private String adharCard;

        @JsonProperty("idProofType")
        private String idProofType;

        @JsonProperty("idProofVal")
        private String idProofVal;

        @JsonProperty("idProofUpdateDt")
        private String idProofUpdateDt;

        @JsonProperty("validTill")
        private String validTill;

        @JsonProperty("panSubmittedInd")
        private String panSubmittedInd;

        @JsonProperty("panValidInd")
        private String panValidInd;

        @JsonProperty("aadhaarNum")
        private String aadhaarNum;

        @JsonProperty("aadhaarAuthInd")
        private String aadhaarAuthInd;

        @JsonProperty("panAadhaarInd")
        private String panAadhaarInd;

        @JsonProperty("panAadhaarLinked")
        private String panAadhaarLinked;

    public String getIdProof() {
        return idProof;
    }

    public void setIdProof(String idProof) {
        this.idProof = idProof;
    }

    public String getIdenPrfNum() {
        return idenPrfNum;
    }

    public void setIdenPrfNum(String idenPrfNum) {
        this.idenPrfNum = idenPrfNum;
    }

    public String getPanNum() {
        return panNum;
    }

    public void setPanNum(String panNum) {
        this.panNum = panNum;
    }

    public String getPanUpdateDt() {
        return panUpdateDt;
    }

    public void setPanUpdateDt(String panUpdateDt) {
        this.panUpdateDt = panUpdateDt;
    }

    public String getAdharCard() {
        return adharCard;
    }

    public void setAdharCard(String adharCard) {
        this.adharCard = adharCard;
    }

    public String getIdProofType() {
        return idProofType;
    }

    public void setIdProofType(String idProofType) {
        this.idProofType = idProofType;
    }

    public String getIdProofVal() {
        return idProofVal;
    }

    public void setIdProofVal(String idProofVal) {
        this.idProofVal = idProofVal;
    }

    public String getIdProofUpdateDt() {
        return idProofUpdateDt;
    }

    public void setIdProofUpdateDt(String idProofUpdateDt) {
        this.idProofUpdateDt = idProofUpdateDt;
    }

    public String getValidTill() {
        return validTill;
    }

    public void setValidTill(String validTill) {
        this.validTill = validTill;
    }

    public String getPanSubmittedInd() {
        return panSubmittedInd;
    }

    public void setPanSubmittedInd(String panSubmittedInd) {
        this.panSubmittedInd = panSubmittedInd;
    }

    public String getPanValidInd() {
        return panValidInd;
    }

    public void setPanValidInd(String panValidInd) {
        this.panValidInd = panValidInd;
    }

    public String getAadhaarNum() {
        return aadhaarNum;
    }

    public void setAadhaarNum(String aadhaarNum) {
        this.aadhaarNum = aadhaarNum;
    }

    public String getAadhaarAuthInd() {
        return aadhaarAuthInd;
    }

    public void setAadhaarAuthInd(String aadhaarAuthInd) {
        this.aadhaarAuthInd = aadhaarAuthInd;
    }

    public String getPanAadhaarInd() {
        return panAadhaarInd;
    }

    public void setPanAadhaarInd(String panAadhaarInd) {
        this.panAadhaarInd = panAadhaarInd;
    }

    public String getPanAadhaarLinked() {
        return panAadhaarLinked;
    }

    public void setPanAadhaarLinked(String panAadhaarLinked) {
        this.panAadhaarLinked = panAadhaarLinked;
    }

    @Override
    public String toString() {
        return "IdentifiableInfo{" +
                "idProof='" + idProof + '\'' +
                ", idenPrfNum='" + idenPrfNum + '\'' +
                ", panNum='" + panNum + '\'' +
                ", panUpdateDt='" + panUpdateDt + '\'' +
                ", adharCard='" + adharCard + '\'' +
                ", idProofType='" + idProofType + '\'' +
                ", idProofVal='" + idProofVal + '\'' +
                ", idProofUpdateDt='" + idProofUpdateDt + '\'' +
                ", validTill='" + validTill + '\'' +
                ", panSubmittedInd='" + panSubmittedInd + '\'' +
                ", panValidInd='" + panValidInd + '\'' +
                ", aadhaarNum='" + aadhaarNum + '\'' +
                ", aadhaarAuthInd='" + aadhaarAuthInd + '\'' +
                ", panAadhaarInd='" + panAadhaarInd + '\'' +
                ", panAadhaarLinked='" + panAadhaarLinked + '\'' +
                '}';
    }
}
