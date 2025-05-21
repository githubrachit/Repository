package com.mli.mpro.otpservice;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OtpServiceRequest {
    @JsonProperty("request")
    private Request request;

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "OtpServiceRequest{" +
                "request=" + request +
                '}';
    }
}
