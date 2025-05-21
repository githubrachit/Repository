package com.mli.mpro.onboarding.partner.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DedupeSOAResponse extends SOAResponse {
    @JsonProperty("payload")
    private DedupeSOAResponsePayload payload;

    public DedupeSOAResponsePayload getPayload() {
        return payload;
    }

    public void setPayload(DedupeSOAResponsePayload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "DedupeSOAResponse{" +
                "payload=" + payload +
                '}';
    }
}
