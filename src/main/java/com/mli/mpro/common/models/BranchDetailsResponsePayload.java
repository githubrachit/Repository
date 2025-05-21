package com.mli.mpro.common.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BranchDetailsResponsePayload {

    @JsonProperty("addressDetails")
    List<BranchDetails> addressDetails;

    public List<BranchDetails> getAddressDetails() {
        return addressDetails;
    }

    public void setAddressDetails(List<BranchDetails> addressDetails) {
        this.addressDetails = addressDetails;
    }

    @Override
    public String toString() {
        return "BranchDetailsResponsePayload{" +
                "addressDetails=" + addressDetails +
                '}';
    }
}
