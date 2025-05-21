package com.mli.mpro.neo.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

import java.util.List;
import java.util.StringJoiner;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JourneyStatus {

    @JsonProperty("status")
    private Status status_; // Field name changed from status to status_ to avoid conflict with ybl payment status field
    private String isMedNonMed;
    private List<String> sequence = null;
    private String apiErrorMsg;

    public Status getStatus_() {
        return status_;
    }

    public JourneyStatus setStatus_(Status status_) {
        this.status_ = status_;
        return this;
    }

    public String getIsMedNonMed() {
        return isMedNonMed;
    }

    public JourneyStatus setIsMedNonMed(String isMedNonMed) {
        this.isMedNonMed = isMedNonMed;
        return this;
    }

    public List<String> getSequence() {
        return sequence;
    }

    public JourneyStatus setSequence(List<String> sequence) {
        this.sequence = sequence;
        return this;
    }

    public String getApiErrorMsg() {
        return apiErrorMsg;
    }

    public JourneyStatus setApiErrorMsg(String apiErrorMsg) {
        this.apiErrorMsg = apiErrorMsg;
        return this;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
           return Utility.toString(this);
        }
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
                .add("apiErrorMsg = " + apiErrorMsg)
                .add("isMedNonMed = " + isMedNonMed)
                .add("sequence = " + sequence)
                .add("status = " + status_)
                .toString();
    }
}
