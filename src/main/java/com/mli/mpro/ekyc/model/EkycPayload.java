package com.mli.mpro.ekyc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class EkycPayload {

    @Sensitive(MaskType.AADHAAR_NUM)
    @JsonProperty("aadhaarNumber")
    private String aadhaarNumber;
    @JsonProperty("requestType")
    private String requestType;
    @JsonProperty("policyNumber")
    private String policyNumber;
    @JsonProperty("otp")
    private String otp;
    @JsonProperty("channel")
    private String channel;
    @JsonProperty("partyType")
    private String partyType;


    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    public void setAadhaarNumber(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getPolicyNumber() { return policyNumber; }

    public void setPolicyNumber(String policyNumber) { this.policyNumber = policyNumber; }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getChannel() { return channel; }

    public void setChannel(String channel) { this.channel = channel; }

    public String getPartyType() {
        return partyType;
    }

    public void setPartyType(String partyType) {
        this.partyType = partyType;
    }

    @Override
    public String toString() {
        return "EkycPayload{" +
                "aadhaarNumber='" + aadhaarNumber + '\'' +
                ", requestType='" + requestType + '\'' +
                ", policyNumber='" + policyNumber + '\'' +
                ", otp='" + otp + '\'' +
                ", channel='" + channel + '\'' +
                ", partyType='" + partyType + '\'' +
                '}';
    }
}
