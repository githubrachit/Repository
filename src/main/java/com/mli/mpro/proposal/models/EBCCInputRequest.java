package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class EBCCInputRequest {
    @JsonProperty("transactionIdList")
    private List<Long> transactionIdList;

    public List<Long> getTransactionIdList() {
        return transactionIdList;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return new StringJoiner(", ", EBCCInputRequest.class.getSimpleName() + "[", "]")
                .add("transactionIdList=" + (transactionIdList != null ? Arrays.toString(transactionIdList.toArray()) : "null"))
                .toString();
    }
}