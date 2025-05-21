package com.mli.mpro.onboarding.partner.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatchProfiles {

    @JsonProperty("matchStats")
    private MatchStats matchStats;

    @JsonProperty("profile")
    private Profile profile;

    public MatchStats getMatchStats() {
        return matchStats;
    }

    public void setMatchStats(MatchStats matchStats) {
        this.matchStats = matchStats;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "MatchProfiles{" +
                "matchStats=" + matchStats +
                ", profile=" + profile +
                '}';
    }
}
