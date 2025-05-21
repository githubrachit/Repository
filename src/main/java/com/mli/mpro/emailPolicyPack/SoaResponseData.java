package com.mli.mpro.emailPolicyPack;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class SoaResponseData {
    @JsonProperty("statusCode")
    private String statusCode;
    @JsonProperty("statusMessage")
    private String statusMessage;
    @JsonProperty("byteArr")
    private String byteArr;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getByteArr() {
        return byteArr;
    }

    public void setByteArr(String byteArr) {
        this.byteArr = byteArr;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "SoaResponseData{" +
                "statusCode='" + statusCode + '\'' +
                ", statusMessage='" + statusMessage + '\'' +
                ", byteArr='" + byteArr + '\'' +
                '}';
    }
}

