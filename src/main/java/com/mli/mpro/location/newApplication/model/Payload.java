package com.mli.mpro.location.newApplication.model;

public class Payload {

    private String aml;
    private String ulip;
    private String newApplicationButton;

    public Payload() {
    }

    public Payload(String aml, String ulip, String newApplicationButton) {
        this.aml = aml;
        this.ulip = ulip;
        this.newApplicationButton = newApplicationButton;
    }

    public String getAml() {
        return aml;
    }

    public void setAml(String aml) {
        this.aml = aml;
    }

    public String getUlip() {
        return ulip;
    }

    public void setUlip(String ulip) {
        this.ulip = ulip;
    }

    public String getNewApplicationButton() {
        return newApplicationButton;
    }

    public void setNewApplicationButton(String newApplicationButton) {
        this.newApplicationButton = newApplicationButton;
    }

    @Override
    public String toString() {
        return "Payload{" +
                "aml='" + aml + '\'' +
                ", ulip='" + ulip + '\'' +
                ", newApplicationButton='" + newApplicationButton + '\'' +
                '}';
    }
}
