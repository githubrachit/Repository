package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.location.altfinInquiry.output.FgResult;
import com.mli.mpro.location.altfinInquiry.output.ServiceCallStatus;
import com.mli.mpro.utils.Utility;


import java.util.Arrays;
import java.util.List;
public class Cs3 {

    @JsonProperty("aiResult")
    private List<AiResultCs3> aiResult;
    @JsonProperty("fgResult")
    private FgResult fgResult;
    @JsonProperty("serviceCallStatus")
    private ServiceCallStatus serviceCallStatus;

    public List<AiResultCs3> getAiResult() {
        return aiResult;
    }

    public void setAiResult(List<AiResultCs3> aiResult) {
        this.aiResult = aiResult;
    }

    public FgResult getFgResult() {
        return fgResult;
    }

    public void setFgResult(FgResult fgResult) {
        this.fgResult = fgResult;
    }

    public ServiceCallStatus getServiceCallStatus() {
        return serviceCallStatus;
    }

    public void setServiceCallStatus(ServiceCallStatus serviceCallStatus) {
        this.serviceCallStatus = serviceCallStatus;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "Cs3{" +
                "aiResult=" + aiResult +
                ", fgResult=" + fgResult +
                ", serviceCallStatus=" + serviceCallStatus +
                '}';
    }
}
