package com.mli.mpro.configuration.models;

import java.util.List;

public class ShorterJourneyChannelDetails {

    private String channelCode;
    private String enabledAllGoCode;
    private List<String> goCodes;

    public String getChannelCode() {
        return channelCode;
    }
    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }
    public String getEnabledAllGoCode() {
        return enabledAllGoCode;
    }
    public void setEnabledAllGoCode(String enabledAllGoCode) {
        this.enabledAllGoCode = enabledAllGoCode;
    }
    public List<String> getGoCodes() {
        return goCodes;
    }
    public void setGoCodes(List<String> goCodes) {
        this.goCodes = goCodes;
    }

    @Override
    public String toString() {
        return "ChannelDetails [channelCode=" + channelCode + ", enabledAllGoCode=" + enabledAllGoCode
                + ", goCodes=" + goCodes + "]";
    }
}
