package com.mli.mpro.tmb.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InsuranceLinkRequest {

    @JsonProperty("cust_id")
    private String customerId;
    @JsonProperty("link")
    private String link;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
