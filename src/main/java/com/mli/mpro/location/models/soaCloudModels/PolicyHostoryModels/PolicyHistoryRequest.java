package com.mli.mpro.location.models.soaCloudModels.PolicyHostoryModels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PolicyHistoryRequest {

    @JsonProperty("request")
    private Request request;

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
