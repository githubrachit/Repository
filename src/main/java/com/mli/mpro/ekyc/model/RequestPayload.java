package com.mli.mpro.ekyc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class RequestPayload {
    @Sensitive(MaskType.AADHAAR_NUM)
    @JsonProperty("aadhaarNumber")
    private String aadhaarNumber;
    @JsonProperty("requestType")
    private String requestType;
    @JsonProperty("policyNo")
    private String policyNo;
    @JsonProperty("otp")
    private String otp;
    @JsonProperty("type")
    private String type;

    public RequestPayload(String aadhaarNumber, String requestType, String policyNo, String otp, String type) {
        this.aadhaarNumber = aadhaarNumber;
        this.requestType = requestType;
        this.policyNo = policyNo;
        this.otp = otp;
        this.type = type;
    }

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

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "RequestPayload{" +
                "aadhaarNumber='" + aadhaarNumber + '\'' +
                ", requestType='" + requestType + '\'' +
                ", policyNo='" + policyNo + '\'' +
                ", otp='" + otp + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
