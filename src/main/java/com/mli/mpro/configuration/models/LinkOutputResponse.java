package com.mli.mpro.configuration.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LinkOutputResponse {
    private ErrorResponse errorResponse;
    @JsonProperty("response")
    private SellerResponse response;

    public LinkOutputResponse(ErrorResponse errorResponse, SellerResponse response) {
        this.errorResponse = errorResponse;
        this.response = response;
    }

    public SellerResponse getResponse() {
        return response;
    }

    public void setResponse(SellerResponse response) {
        this.response = response;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }
}
