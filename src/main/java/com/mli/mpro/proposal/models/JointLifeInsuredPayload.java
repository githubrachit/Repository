package com.mli.mpro.proposal.models;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JointLifeInsuredPayload {

    @JsonProperty("iibResponse")
    public IIBPayload getIibResponse() {
        return iibResponse;
    }

    private IIBPayload iibResponse;

    private String statusCode;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    public String getStatusCode() {
        return statusCode;
    }

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return new StringJoiner(", ", IIBResponseDetails.class.getSimpleName() + "[", "]")
                .add("iibResponse='" + iibResponse + "'")
                .add("statusCode='" + statusCode + "'")
                .add("additionalProperties=" + additionalProperties)
                .toString();
    }
}