package com.mli.mpro.onboarding.model;

import java.util.List;

public class Payload {

    private List<String> quoteId;
    private List<String> policyNumber;
    private String systemType;
    private String infoType;

    public Payload() {
       
    }

    public Payload(List<String> policyNumber, String systemType, String infoType) {
        this.quoteId = policyNumber;
        this.systemType = systemType;
        this.infoType = infoType;
    }

    public List<String> getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(List<String> quoteId) {
        this.quoteId = quoteId;
    }

    public String getSystemType() {
        return systemType;
    }
    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }
    public String getInfoType() {
        return infoType;
    }
    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }
    public List<String> getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(List<String> policyNumber) {
        this.policyNumber = policyNumber;
    }

    @Override
    public String toString() {
        return "Payload [quoteId=" + quoteId + ", systemType=" + systemType + ", infoType=" + infoType + "]";
    }
    
}
