package com.mli.mpro.configuration.models;

public class AfypDetails {
    private String afypStatus;
    private boolean isAFYP;

    public String getAfypStatus() {
        return afypStatus;
    }

    public void setAfypStatus(String afypStatus) {
        this.afypStatus = afypStatus;
    }

    public boolean isAFYP() {
        return isAFYP;
    }

    public void setAFYP(boolean AFYP) {
        isAFYP = AFYP;
    }

    @Override
    public String toString() {
        return "AfypDetails{" +
                "afypStatus='" + afypStatus + '\'' +
                ", isAFYP=" + isAFYP +
                '}';
    }
}
