package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "addRiderUIN", "termPlusRiderUIN", "wopPlusRiderUIN", "ciDisabilityRiderUIN", "ciDisabilitySecureRiderUIN"})
public class RiderUIN {
    @JsonProperty("addRiderUIN")
    private String addRiderUIN;
    @JsonProperty("termPlusRiderUIN")
    private String termPlusRiderUIN;
    @JsonProperty("wopPlusRiderUIN")
    private String wopPlusRiderUIN;
    @JsonProperty("ciDisabilityRiderUIN")
    private String ciDisabilityRiderUIN;
    @JsonProperty("ciDisabilitySecureRiderUIN")
    private String ciDisabilitySecureRiderUIN;
    @JsonProperty("suprRiderUIN")
    private String suprRiderUIN;

    public String getAddRiderUIN() {
        return addRiderUIN;
    }

    public void setAddRiderUIN(String addRiderUIN) {
        this.addRiderUIN = addRiderUIN;
    }

    public String getTermPlusRiderUIN() {
        return termPlusRiderUIN;
    }

    public void setTermPlusRiderUIN(String termPlusRiderUIN) {
        this.termPlusRiderUIN = termPlusRiderUIN;
    }

    public String getWopPlusRiderUIN() {
        return wopPlusRiderUIN;
    }

    public void setWopPlusRiderUIN(String wopPlusRiderUIN) {
        this.wopPlusRiderUIN = wopPlusRiderUIN;
    }

    public String getCiDisabilityRiderUIN() {
        return ciDisabilityRiderUIN;
    }

    public void setCiDisabilityRiderUIN(String ciDisabilityRiderUIN) {
        this.ciDisabilityRiderUIN = ciDisabilityRiderUIN;
    }

    public String getCiDisabilitySecureRiderUIN() {
        return ciDisabilitySecureRiderUIN;
    }

    public void setCiDisabilitySecureRiderUIN(String ciDisabilitySecureRiderUIN) {
        this.ciDisabilitySecureRiderUIN = ciDisabilitySecureRiderUIN;
    }

    public String getSuprRiderUIN() {
        return suprRiderUIN;
    }

    public void setSuprRiderUIN(String suprRiderUIN) {
        this.suprRiderUIN = suprRiderUIN;
    }

    @Override
    public String toString() {
        return "RiderUIN{" +
                "addRiderUIN='" + addRiderUIN + '\'' +
                ", termPlusRiderUIN='" + termPlusRiderUIN + '\'' +
                ", wopPlusRiderUIN='" + wopPlusRiderUIN + '\'' +
                ", ciDisabilityRiderUIN='" + ciDisabilityRiderUIN + '\'' +
                ", ciDisabilitySecureRiderUIN='" + ciDisabilitySecureRiderUIN + '\'' +
                '}';
    }
}
