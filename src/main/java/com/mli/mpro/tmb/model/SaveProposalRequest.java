package com.mli.mpro.tmb.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SaveProposalRequest {

    private String customerId;
    @JsonProperty("transactionId")
    private Long transactionId;
    private String correlationId;
    private String eventType;
    private String agentId;
    private CustomerDetailsResponse customerDetailsResponse;
    private AccountDetailsResponse accountDetailsResponse;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public CustomerDetailsResponse getCustomerDetailsResponse() {
        return customerDetailsResponse;
    }

    public void setCustomerDetailsResponse(CustomerDetailsResponse customerDetailsResponse) {
        this.customerDetailsResponse = customerDetailsResponse;
    }

    public AccountDetailsResponse getAccountDetailsResponse() {
        return accountDetailsResponse;
    }

    public void setAccountDetailsResponse(AccountDetailsResponse accountDetailsResponse) {
        this.accountDetailsResponse = accountDetailsResponse;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }


    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    @Override
    public String toString() {
        return "SaveProposalRequest{" +
                "customerId='" + customerId + '\'' +
                ", transactionId=" + transactionId +
                ", correlationId='" + correlationId + '\'' +
                ", eventType='" + eventType + '\'' +
                ", agentId='" + agentId + '\'' +
                ", customerDetailsResponse=" + customerDetailsResponse +
                ", accountDetailsResponse=" + accountDetailsResponse +
                '}';
    }
}
