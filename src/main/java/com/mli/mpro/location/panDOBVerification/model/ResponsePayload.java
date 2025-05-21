package com.mli.mpro.location.panDOBVerification.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponsePayload {
	
    private String dobStatus;
    private String nameStatus;
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String panAadhaarLinkStatus;
    private String panStatus;
    private String responseDate;

    public ResponsePayload() {
    }

    public ResponsePayload(String dobStatus, String nameStatus, String panAadhaarLinkStatus, String panStatus, String responseDate) {
        this.dobStatus = dobStatus;
        this.nameStatus = nameStatus;
        this.panAadhaarLinkStatus = panAadhaarLinkStatus;
        this.panStatus = panStatus;
        this.responseDate = responseDate;
    }

    public String getDobStatus() {
        return dobStatus;
    }

    public void setDobStatus(String dobStatus) {
        this.dobStatus = dobStatus;
    }

    public String getNameStatus() {
        return nameStatus;
    }

    public void setNameStatus(String nameStatus) {
        this.nameStatus = nameStatus;
    }

    public String getPanAadhaarLinkStatus() {
        return panAadhaarLinkStatus;
    }

    public void setPanAadhaarLinkStatus(String panAadhaarLinkStatus) {
        this.panAadhaarLinkStatus = panAadhaarLinkStatus;
    }

    public String getPanStatus() {
        return panStatus;
    }

    public void setPanStatus(String panStatus) {
        this.panStatus = panStatus;
    }

    public String getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(String responseDate) {
        this.responseDate = responseDate;
    }

    @Override
    public String toString() {
        return "ResponsePayload{" +
                "dobStatus='" + dobStatus + '\'' +
                ", nameStatus='" + nameStatus + '\'' +
                ", panAadhaarLinkStatus='" + panAadhaarLinkStatus + '\'' +
                ", panStatus='" + panStatus + '\'' +
                ", responseDate='" + responseDate + '\'' +
                '}';
    }
}
