package com.mli.mpro.location.models.mfaOauthAgent360;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MFAPayload {

    private String agentId;
    private String requestSource="";

    public void setRequestSource(String requestSource) {
        this.requestSource = requestSource;
    }

    public String getRequestSource() {
        return requestSource;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "MFAPayload{" +
                "agentId='" + agentId + '\'' +
                '}';
    }
}
