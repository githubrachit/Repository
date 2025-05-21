package com.mli.mpro.location.altfinInquiry.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

import java.util.ArrayList;

public class Cs1 {
    @JsonProperty("aiResult")
    private ArrayList<AiResult> aiResult;
    @JsonProperty("fgResult")
    private FgResult fgResult;
    @JsonProperty("serviceCallStatus")
    private ServiceCallStatus serviceCallStatus;

    public ArrayList<AiResult> getAiResult() {
        return aiResult;
    }

    public void setAiResult(ArrayList<AiResult> aiResult) {
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
        return "Cs1{" +
                "aiResult=" + aiResult +
                ", fgResult=" + fgResult +
                ", serviceCallStatus=" + serviceCallStatus +
                '}';
    }
}
