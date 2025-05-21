package com.mli.mpro.location.models.soaCloudModels.policySplittingModels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Request {
    @JsonProperty("payload")
    private SplittingorReplacementRequestPayload payload;

    public SplittingorReplacementRequestPayload getPayload() {
        return payload;
    }

    public void setPayload(SplittingorReplacementRequestPayload payload) {
        this.payload = payload;
    }
}
