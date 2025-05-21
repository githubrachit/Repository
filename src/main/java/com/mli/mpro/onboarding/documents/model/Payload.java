package com.mli.mpro.onboarding.documents.model;

import java.util.Date;
import java.util.List;

public class Payload {
    private String policyNumber="";
    private String quoteId="";

    private Date preferredDate;

    private String visitType;

    private String labId;



    public Payload() {

    }
    public Payload(String policyNumber, String quoteId) {
        this.policyNumber = policyNumber;
        this.quoteId = quoteId;
    }

    public Payload(String policyNumber, String quoteId, Date preferredDate, String visitType, String labId) {
        this.policyNumber = policyNumber;
        this.quoteId = quoteId;
        this.preferredDate = preferredDate;
        this.visitType = visitType;
        this.labId = labId;

    }

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public Date getPreferredDate() {
        return preferredDate;
    }

    public void setPreferredDate(Date preferredDate) {
        this.preferredDate = preferredDate;
    }

    public String getVisitType() {
        return visitType;
    }

    public void setVisitType(String visitType) {
        this.visitType = visitType;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }


}
