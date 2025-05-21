package com.mli.mpro.location.models.soaCloudModels.agentCommissionSummary;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

import java.util.List;

public class ResponsePayload {

    @JsonProperty("servicingAgentId")
    private String servicingAgentId;
    @JsonProperty("servicingAgentName")
    private String servicingAgentName;
    @JsonProperty("starLevel")
    private String starLevel;
    @JsonProperty("vintageAge")
    private String vintageAge;
    @JsonProperty("premAndCommissionDtls")
    private List<PremAndCommissionDtls> premAndCommissionDtls;

    public String getServicingAgentId() {
        return servicingAgentId;
    }

    public void setServicingAgentId(String servicingAgentId) {
        this.servicingAgentId = servicingAgentId;
    }

    public String getServicingAgentName() {
        return servicingAgentName;
    }

    public void setServicingAgentName(String servicingAgentName) {
        this.servicingAgentName = servicingAgentName;
    }

    public String getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(String starLevel) {
        this.starLevel = starLevel;
    }

    public String getVintageAge() {
        return vintageAge;
    }

    public void setVintageAge(String vintageAge) {
        this.vintageAge = vintageAge;
    }

    public List<PremAndCommissionDtls> getPremAndCommissionDtls() {
        return premAndCommissionDtls;
    }

    public void setPremAndCommissionDtls(List<PremAndCommissionDtls> premAndCommissionDtls) {
        this.premAndCommissionDtls = premAndCommissionDtls;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "ResponsePayload{" +
                "servicingAgentId='" + servicingAgentId + '\'' +
                ", servicingAgentName='" + servicingAgentName + '\'' +
                ", starLevel='" + starLevel + '\'' +
                ", vintageAge='" + vintageAge + '\'' +
                ", premAndCommissionDtls=" + premAndCommissionDtls +
                '}';
    }
}
