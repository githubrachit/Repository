package com.mli.mpro.proposal.models;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class IIBPayload {
    @JsonProperty("iibDataMatch")
    private List<IibDataMatch> iibDataMatch;

    public List<IibDataMatch> getIibDataMatch() {
        return iibDataMatch;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return new StringJoiner(", ", IIBPayload.class.getSimpleName() + "[", "]")
                .add("iibDataMatch=" + (iibDataMatch != null ? Arrays.toString(iibDataMatch.toArray()) : "null"))
                .toString();
    }
}