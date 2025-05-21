package com.mli.mpro.location.models.zeroqc.ekyc;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UIRequestPayload {
    private String channel;
    private String channelSource;
    private String goCode;
    private String journeyTypeDiy;
    private String physicalJourneyEnabled;

    public String getChannel() {
        return channel;
    }
    public void setChannel(String channel) {
        this.channel = channel;
    }
    public String getChannelSource() {
        return channelSource;
    }
    public void setChannelSource(String channelSource) {
        this.channelSource = channelSource;
    }
    public String getGoCode() {
        return goCode;
    }
    public void setGoCode(String goCode) {
        this.goCode = goCode;
    }
    public String getJourneyTypeDiy() {
        return journeyTypeDiy;
    }
    public void setJourneyTypeDiy(String journeyTypeDiy) {
        this.journeyTypeDiy = journeyTypeDiy;
    }
    public String getPhysicalJourneyEnabled() { return physicalJourneyEnabled; }
    public void setPhysicalJourneyEnabled(String physicalJourneyEnabled) { this.physicalJourneyEnabled = physicalJourneyEnabled; }

    @Override
    public String toString() {
        return "UIRequestPayload{" +
                "channel='" + channel + '\'' +
                ", channelSource='" + channelSource + '\'' +
                ", goCode='" + goCode + '\'' +
                ", journeyTypeDiy='" + journeyTypeDiy + '\'' +
                ", physicalJourneyEnabled='" + physicalJourneyEnabled + '\'' +
                '}';
    }
}
