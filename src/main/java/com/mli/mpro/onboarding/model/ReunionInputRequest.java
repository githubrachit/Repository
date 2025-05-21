package com.mli.mpro.onboarding.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.onboarding.partner.model.DedupeAPIPayload;


public class ReunionInputRequest {
    @JsonProperty("dedupeAPIPayload")
    public DedupeAPIPayload dedupeAPIPayload;

    @JsonProperty("request")
    public com.mli.mpro.productRestriction.models.Request  request;

    public DedupeAPIPayload getDedupeAPIPayload() {
        return dedupeAPIPayload;
    }

    public void setDedupeAPIPayload(DedupeAPIPayload dedupeAPIPayload) {
        this.dedupeAPIPayload = dedupeAPIPayload;
    }

    public com.mli.mpro.productRestriction.models.Request getRequest() {
        return request;
    }

    public void setRequest(com.mli.mpro.productRestriction.models.Request request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "ReunionInputRequest{" +
                "dedupeAPIPayload=" + dedupeAPIPayload +
                ", request=" + request +
                '}';
    }
}
