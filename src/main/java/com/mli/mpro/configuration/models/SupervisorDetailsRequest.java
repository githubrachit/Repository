package com.mli.mpro.configuration.models;

public class SupervisorDetailsRequest {

    private String spCode;
    private String spName;

    public String getSpCode() {
        return spCode;
    }

    public void setSpCode(String spCode) {
        this.spCode = spCode;
    }

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }

    @Override
    public String toString() {
        return "SupervisorDetailsRequest{" +
                "spCode='" + spCode + '\'' +
                ", spName='" + spName + '\'' +
                '}';
    }
}
