package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExternalInternalAnomaly {

    @JsonProperty("isExternalInternalAnomaly")
    private String isExternalInternalAnomaly;
    @JsonProperty("specifyInternalDetails")
    private String specifyInternalDetails;

    public String getIsExternalInternalAnomaly() {
        return isExternalInternalAnomaly;
    }

    public void setIsExternalInternalAnomaly(String isExternalInternalAnomaly) {
        this.isExternalInternalAnomaly = isExternalInternalAnomaly;
    }

    public String getSpecifyInternalDetails() {
        return specifyInternalDetails;
    }

    public void setSpecifyInternalDetails(String specifyInternalDetails) {
        this.specifyInternalDetails = specifyInternalDetails;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "ExternalInternalAnomaly{" +
                "isExternalInternalAnomaly='" + isExternalInternalAnomaly + '\'' +
                ", specifyInternalDetails='" + specifyInternalDetails + '\'' +
                '}';
    }
}
