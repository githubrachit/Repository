package com.mli.mpro.onboarding.pathflex.model;

import javax.validation.constraints.Pattern;

public class BrmsBrokerAPIRequest {

    @Pattern(regexp = "^[0-9]{1,15}$", message = "Invalid UTM code")
    private String utmCode;

    public String getUtmCode() {
        return utmCode;
    }

    public void setUtmCode(String utmCode) {
        this.utmCode = utmCode;
    }

    @Override
    public String toString() {
        return "brmsBrokerAPIRequest{" +
                "utmCode='" + utmCode + '\'' +
                '}';
    }
}
