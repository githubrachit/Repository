package com.mli.mpro.proposal.models;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class IIBResponseDetails {

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("iibResponse")
    public IIBPayload getIibResponse() {
        return iibResponse;
    }

    private String iibRequestTimestamp;
    private String iibResponseTimestamp;

    private IIBPayload iibResponse;

    private JointLifeInsuredPayload jointLifeInsuredPayload;

    private String iibServiceStatus;

    private String statusCode;

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public String getIibRequestTimestamp() {
        return iibRequestTimestamp;
    }

    public String getIibResponseTimestamp() {
        return iibResponseTimestamp;
    }

    public String getIibServiceStatus() {
        return iibServiceStatus;
    }

    public JointLifeInsuredPayload getJointLifeInsuredPayload() {
        return jointLifeInsuredPayload;
    }

    public String getStatusCode() {
        return statusCode;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return new StringJoiner(", ", IIBResponseDetails.class.getSimpleName() + "[", "]")
                .add("iibResponse='" + iibResponse + "'")
                .add("jointLifeInsuredPayload='" + jointLifeInsuredPayload + "'")
                .add("statusCode='" + statusCode + "'")
                .add("iibRequestTimestamp='" + iibRequestTimestamp + "'")
                .add("iibResponseTimestamp='" + iibResponseTimestamp + "'")
                .add("iibServiceStatus='" + iibServiceStatus + "'")
                .add("additionalProperties='" + additionalProperties + "'")
                .toString();
    }
}