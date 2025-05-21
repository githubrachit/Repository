package com.mli.mpro.location.UtmConf.Payload;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UtmConfiguratorInputRequest {
    @Pattern(regexp = "^(?!\\s)[a-zA-Z0-9 ]+$", message = "Invalid journeyType")
    @JsonProperty("journeyType")
    private String journeyType;
    @Pattern(regexp = "^[a-zA-Z0-9]{1,10}$", message = "Invalid channel")
    @JsonProperty("channel")
    private String channel;
    @Pattern(regexp = "^(?!\\s)[a-zA-Z0-9 ]+$", message = "Invalid goCode")
    @JsonProperty("goCode")
    private String goCode;
    @Pattern(regexp = "^[a-zA-Z0-9]{6}$", message = "Invalid agentCode")
    @JsonProperty("agentCode")
    private String agentCode;
    @Pattern(regexp = "^(?![.,\\-])[a-zA-Z.,\\- ]{0,50}$", message = "Invalid agentSegmentation")
    @JsonProperty("agentSegmentation")
    private String agentSegmentation;
    @Pattern(regexp = "^(?![.,\\-])[a-zA-Z.,\\- ]{0,100}$", message = "Invalid agentDesignation")
    @JsonProperty("agentDesignation")
    private String agentDesignation;

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

    @Override
    public String toString() {
        return "UtmConfiguratorInputRequest{" +
                "journeyType='" + journeyType + '\'' +
                ", channel='" + channel + '\'' +
                ", goCode='" + goCode + '\'' +
                ", agentCode='" + agentCode + '\'' +
                ", agentSegmentation='" + agentSegmentation + '\'' +
                ", agentDesignation='" + agentDesignation + '\'' +
                '}';
    }
}
