package com.mli.mpro.agentSelfIdentifiedSkip;

import java.lang.reflect.Array;
import java.util.List;

public class Payload {
    private List<String> policyNumbers;
    private List<String> transactionId;

    public List<String> getPolicyNumbers() {
        return policyNumbers;
    }

    public void setPolicyNumbers(List<String> policyNumbers) {
        this.policyNumbers = policyNumbers;
    }

    public List<String> getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(List<String> transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return "Payload{" +
                "policyNumber=" + policyNumbers +
                ", transactionId=" + transactionId +
                '}';
    }
}
