package com.mli.mpro.tpp.backflow.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

/**
 * @author ravishankar
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "env", "requestId" })
public class Metadata {

    @JsonProperty("env")
    private String env;
    @JsonProperty("requestId")
    private String requestId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Metadata() {
    }

    /**
     * 
     * @param requestId
     * @param env
     */
    public Metadata(String env, String requestId) {
	super();
	this.env = env;
	this.requestId = requestId;
    }

    @JsonProperty("env")
    public String getEnv() {
	return env;
    }

    @JsonProperty("env")
    public void setEnv(String env) {
	this.env = env;
    }

    @JsonProperty("requestId")
    public String getRequestId() {
	return requestId;
    }

    @JsonProperty("requestId")
    public void setRequestId(String requestId) {
	this.requestId = requestId;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "Metadata [env=" + env + ", requestId=" + requestId + "]";
    }

}