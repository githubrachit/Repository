package com.mli.mpro.onboarding.partner.model;

public class ReplacementResponseData {
    private ReplacementSaleReponsePayload replacementSaleResponse;

    public ReplacementSaleReponsePayload getReplacementSaleResponse() {
        return replacementSaleResponse;
    }

    public void setReplacementSaleResponse(ReplacementSaleReponsePayload replacementSaleResponse) {
        this.replacementSaleResponse = replacementSaleResponse;
    }

    @Override
    public String toString() {
        return "ReplacementResponseData{" +
                "replacementSaleResponse=" + replacementSaleResponse +
                '}';
    }
}
