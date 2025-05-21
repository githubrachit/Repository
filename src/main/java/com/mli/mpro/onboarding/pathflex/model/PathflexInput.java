package com.mli.mpro.onboarding.pathflex.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PathflexInput {

    @JsonProperty("branchcode")
    private String branchCode;
    @JsonProperty("utmcode")
    private String utmCode;
    @JsonProperty("brmsbrocatype")
    private String brmsBrocaType;

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getUtmCode() {
        return utmCode;
    }

    public void setUtmCode(String utmCode) {
        this.utmCode = utmCode;
    }

    public String getBrmsBrocaType() {
        return brmsBrocaType;
    }

    public void setBrmsBrocaType(String brmsBrocaType) {
        this.brmsBrocaType = brmsBrocaType;
    }

    @Override
    public String toString() {
        return "PathflexInput{" +
                "branchCode='" + branchCode + '\'' +
                ", utmCode='" + utmCode + '\'' +
                ", brmsBrocaType='" + brmsBrocaType + '\'' +
                '}';
    }
}
