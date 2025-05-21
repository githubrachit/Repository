package com.mli.mpro.agent.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class RequestHeader {

    @JsonProperty("soaCorrelationId")
    private String correlationId;
    @JsonProperty("soaAppId")
    private String applicationId;

    public RequestHeader() {
    }

    public RequestHeader(String correlationId, String applicationId) {
	super();
	this.correlationId = correlationId;
	this.applicationId = applicationId;
    }

    public String getCorrelationId() {
	return correlationId;
    }

    public void setCorrelationId(String correlationId) {
	this.correlationId = correlationId;
    }

    public String getApplicationId() {
	return applicationId;
    }

    public void setApplicationId(String applicationId) {
	this.applicationId = applicationId;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
           return Utility.toString(this);
        }
	return "AgentSelfRequestHeader [correlationId=" + correlationId + ", applicationId=" + applicationId + "]";
    }

}
