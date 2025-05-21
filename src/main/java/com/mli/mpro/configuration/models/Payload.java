package com.mli.mpro.configuration.models;

public class Payload {
    private String payload;
    private boolean isDocumentGenerated;
    private ReplacementSaleDetails replacementSaleDetails;
    private AfypDetails highAFYPDetails;

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public boolean isDocumentGenerated() {
        return isDocumentGenerated;
    }

    public void setDocumentGenerated(boolean documentGenerated) {
        isDocumentGenerated = documentGenerated;
    }

    public ReplacementSaleDetails getReplacementSaleDetails() {
        return replacementSaleDetails;
    }

    public void setReplacementSaleDetails(ReplacementSaleDetails replacementSaleDetails) {
        this.replacementSaleDetails = replacementSaleDetails;
    }

    public AfypDetails getHighAFYPDetails() {
        return highAFYPDetails;
    }

    public void setHighAFYPDetails(AfypDetails highAFYPDetails) {
        this.highAFYPDetails = highAFYPDetails;
    }

    @Override
    public String toString() {
        return "Payload{" +
                "payload='" + payload + '\'' +
                ", isDocumentGenerated=" + isDocumentGenerated +
                ", replacementSaleDetails=" + replacementSaleDetails +
                ", highAFYPDetails=" + highAFYPDetails +
                '}';
    }
}
