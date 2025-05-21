package com.mli.mpro.onboarding.partner.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DedupeSOAResponsePayload extends SOARequestPayload{
    @JsonProperty("matchProfiles")
    private List<MatchProfiles> matchProfiles;

    @JsonProperty("dedupeFlag")
    private String dedupeFlag;

    public List<MatchProfiles> getMatchProfiles() {
        return matchProfiles;
    }

    public void setMatchProfiles(List<MatchProfiles> matchProfiles) {
        this.matchProfiles = matchProfiles;
    }

    public String getDedupeFlag() {
        return dedupeFlag;
    }

    public void setDedupeFlag(String dedupeFlag) {
        this.dedupeFlag = dedupeFlag;
    }

    @Override
    public String toString() {
        return "DedupeSOAResponse{" +
                "matchProfiles=" + matchProfiles +
                ", dedupeFlag='" + dedupeFlag + '\'' +
                '}';
    }
}
