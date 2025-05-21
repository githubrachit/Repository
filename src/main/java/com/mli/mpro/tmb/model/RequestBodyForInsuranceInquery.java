package com.mli.mpro.tmb.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestBodyForInsuranceInquery {
    @JsonProperty("cust_id")
    private String customerId;
    private String transactionId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
