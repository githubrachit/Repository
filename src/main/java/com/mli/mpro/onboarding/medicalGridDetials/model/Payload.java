package com.mli.mpro.onboarding.medicalGridDetials.model;

public class Payload {
    private String policyNumber="";

    public Payload() {

    }

    public Payload(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    @Override
    public String toString() {
        return "Payload{" +
                "policyNumber='" + policyNumber + '\'' +
                '}';
    }
}
