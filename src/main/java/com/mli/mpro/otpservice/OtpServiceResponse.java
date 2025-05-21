package com.mli.mpro.otpservice;

import com.fasterxml.jackson.annotation.JsonProperty;


public class OtpServiceResponse {
    @JsonProperty("response")
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "OtpServiceResponse{" +
                "response=" + response +
                '}';
    }
}
