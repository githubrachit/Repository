package com.mli.mpro.location.models.soaCloudModels.agentCommissionSummary;

import com.mli.mpro.utils.Utility;

public class AgentCommissionSummaryRequest {

    private Request request;

    public AgentCommissionSummaryRequest() {
    }

    public AgentCommissionSummaryRequest(Request request) {
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "AgentCommissionSummaryRequest [" + "request=" + request + "]";
    }
}
