package com.mli.mpro.location.models.zeroqc.ekyc;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BRMSEkycResponseInput {
    private String channelsource;
    private String channel;
    private String goodec;
    private String journeytypediy;
    private String physicaljourneyenabled;

    // setters and getters
    public String getChannelsource() {
        return channelsource;
    }

    public void setChannelsource(String channelsource) {
        this.channelsource = channelsource;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getGoodec() {
        return goodec;
    }

    public void setGoodec(String goodec) {
        this.goodec = goodec;
    }

    public String getJourneytypediy() {
        return journeytypediy;
    }

    public void setJourneytypediy(String journeytypediy) {
        this.journeytypediy = journeytypediy;
    }

    public String getPhysicaljourneyenabled() { return physicaljourneyenabled; }

    public void setPhysicaljourneyenabled(String physicaljourneyenabled) { this.physicaljourneyenabled = physicaljourneyenabled; }

    @Override
    public String toString() {
        return "BRMSEkycResponseInput{" +
                "channelsource='" + channelsource + '\'' +
                ", channel='" + channel + '\'' +
                ", goodec='" + goodec + '\'' +
                ", journeytypediy='" + journeytypediy + '\'' +
                ", physicaljourneyenabled='" + physicaljourneyenabled + '\'' +
                '}';
    }
}
