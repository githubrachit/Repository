package com.mli.mpro.location.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "utmConfigurator")
public class UtmConfiguratorRepository {
    @JsonProperty("journeyType")
    private String journeyType;

    @JsonProperty("channel")
    private String channel;

    @JsonProperty("goCode")
    private String goCode;

    @JsonProperty("agentCode")
    private String agentCode;

    @JsonProperty("agentSegmentation")
    private String agentSegmentation;

    @JsonProperty("agentDesignation")
    private String agentDesignation;

    @JsonProperty("utmCode")
    private String utmCode;

    public String getJourneyType() {
        return journeyType;
    }

    public void setJourneyType(String journeyType) {
        this.journeyType = journeyType;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getGoCode() {
        return goCode;
    }

    public void setGoCode(String goCode) {
        this.goCode = goCode;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getAgentSegmentation() {
        return agentSegmentation;
    }

    public void setAgentSegmentation(String agentSegmentation) {
        this.agentSegmentation = agentSegmentation;
    }

    public String getAgentDesignation() {
        return agentDesignation;
    }

    public void setAgentDesignation(String agentDesignation) {
        this.agentDesignation = agentDesignation;
    }

    public String getUtmCode() {
        return utmCode;
    }

    public void setUtmCode(String utmCode) {
        this.utmCode = utmCode;
    }

    @Override
    public String toString() {
        return "UtmConfigurator{" +
                "journeyType='" + journeyType + '\'' +
                ", channel='" + channel + '\'' +
                ", goCode='" + goCode + '\'' +
                ", agentCode='" + agentCode + '\'' +
                ", agentSegmentation='" + agentSegmentation + '\'' +
                ", agentDesignation='" + agentDesignation + '\'' +
                ", utmCode='" + utmCode + '\'' +
                '}';
    }
}
