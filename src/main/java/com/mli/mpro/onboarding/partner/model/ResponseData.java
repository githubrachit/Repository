package com.mli.mpro.onboarding.partner.model;


public class ResponseData {

    private DedupeResponsePayload dedupeResponse;
    private CpdResponsePayload cpdResponse;

    public DedupeResponsePayload getDedupeResponse() {
        return dedupeResponse;
    }

    public void setDedupeResponse(DedupeResponsePayload dedupeResponse) {
        this.dedupeResponse = dedupeResponse;
    }

    public CpdResponsePayload getCpdResponse() {
        return cpdResponse;
    }

    public void setCpdResponse(CpdResponsePayload cpdResponse) {
        this.cpdResponse = cpdResponse;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "dedupeResponse=" + dedupeResponse +
                ", cpdResponse=" + cpdResponse +
                '}';
    }
}
