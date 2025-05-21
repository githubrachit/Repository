package com.mli.mpro.common.models;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

public class IIBRequestPayload {
    @Digits(integer = 10, fraction = 0, message = "Transaction ID must be up to 10 digits")
    @Min(value = 9, message = "Transaction ID must be at most 10")
    private Long transactionId;
    @Pattern(regexp = "^(?i)(proposer|insured)$", message = "Invalid party type. Allowed values: Proposer, proposer, Insured, insured.")
    private String partyType;
    @Pattern(regexp = "^[a-zA-Z0-9]{6}$", message = "AgentId must be exactly 6 alphanumeric characters")
    private String agentId;

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getPartyType() {
        return partyType;
    }

    public void setPartyType(String partyType) {
        this.partyType = partyType;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    @Override
    public String toString() {
        return "IIBRequestPayload{" +
                "transactionId=" + transactionId +
                ", partyType='" + partyType + '\'' +
                ", agentId='" + agentId + '\'' +
                '}';
    }
}
