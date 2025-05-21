package com.mli.mpro.onboarding.documents.model;



import java.util.List;

public class ResponsePayload {

    private List<RequiredDocuments> requiredDocuments;

    public ResponsePayload(List<RequiredDocuments> requiredDocuments) {
        this.requiredDocuments = requiredDocuments;
    }

    public ResponsePayload() {
        
    }

    public List<RequiredDocuments> getRequiredDocuments() {
        return requiredDocuments;
    }

    public void setRequiredDocuments(List<RequiredDocuments> requiredDocuments) {
        this.requiredDocuments = requiredDocuments;
    }

    @Override
    public String toString() {
        return "ResponsePayload [requiredDocuments=" + requiredDocuments + "]";
    }
  
}
