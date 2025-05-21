package com.mli.mpro.location.models.soaCloudModels.agentCommissionSummary;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;
import java.util.List;

public class RequestPayload {

    @JsonProperty("agentId")
    private List<String> agentId;

    @JsonProperty("month")
    private String month;

    @JsonProperty("year")
    private String year;

    public List<String> getAgentId() {
        return agentId;
    }

    public void setAgentId(List<String> agentId) {
        this.agentId = agentId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Payload{" +
                "agentId=" + agentId +
                ", month='" + month + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}
