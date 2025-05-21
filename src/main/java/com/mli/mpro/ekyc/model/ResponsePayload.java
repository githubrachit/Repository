package com.mli.mpro.ekyc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class ResponsePayload {

    @Sensitive(MaskType.AADHAAR_NUM)
    @JsonProperty("aadhaarNumber")
    private String aadhaarNumber;
    @JsonProperty("requestType")
    private String requestType;
    @JsonProperty("policyNo")
    private String policyNo;
    @JsonProperty("aadhaarDetail")
    private AadhaarDetail aadhaarDetail;

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

    public AadhaarDetail getAadhaarDetail() {
        return aadhaarDetail;
    }

    public void setAadhaarDetail(AadhaarDetail aadhaarDetail) {
        this.aadhaarDetail = aadhaarDetail;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "ResponsePayload{" +
                "aadhaarNumber='" + aadhaarNumber + '\'' +
                ", requestType='" + requestType + '\'' +
                ", policyNo='" + policyNo + '\'' +
                ", aadhaarDetail=" + aadhaarDetail +
                '}';
    }
}

