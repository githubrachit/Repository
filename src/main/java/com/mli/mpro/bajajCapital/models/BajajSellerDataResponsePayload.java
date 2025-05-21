package com.mli.mpro.bajajCapital.models;

import java.util.List;

public class BajajSellerDataResponsePayload {

    private String isAgentApplicable;

    private List<String> locations;

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public String getIsAgentApplicable() {
        return isAgentApplicable;
    }

    public void setIsAgentApplicable(String isAgentApplicable) {
        this.isAgentApplicable = isAgentApplicable;
    }

    @Override
    public String toString() {
        return "BajajSellerDataResponsePayload{" +
                "isAgentApplicable='" + isAgentApplicable + '\'' +
                '}';
    }
}
