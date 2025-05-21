package com.mli.mpro.location.models;

import com.mli.mpro.utils.Utility;

public class RuralUrbanDetails {

    private String tagging;

    private String matchScore;

    private String matchedWith;

    private String partialMatch;

    private String kickOutMsg;

    public RuralUrbanDetails() {

    }

    public String getTagging() {
        return tagging;
    }

    public void setTagging(String tagging) {
        this.tagging = tagging;
    }

    public String getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(String matchScore) {
        this.matchScore = matchScore;
    }

    public String getMatchedWith() {
        return matchedWith;
    }

    public void setMatchedWith(String matchedWith) {
        this.matchedWith = matchedWith;
    }

    public String getPartialMatch() {
        return partialMatch;
    }

    public void setPartialMatch(String partialMatch) {
        this.partialMatch = partialMatch;
    }

    public String getKickOutMsg() {
        return kickOutMsg;
    }

    public void setKickOutMsg(String kickOutMsg) {
        this.kickOutMsg = kickOutMsg;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "RuralUrbanDetails{" +
                "tagging='" + tagging + '\'' +
                ", matchScore='" + matchScore + '\'' +
                ", matchedWith='" + matchedWith + '\'' +
                ", partialMatch='" + partialMatch + '\'' +
                ", kickOutMsg='" + kickOutMsg + '\'' +
                '}';
    }
}
