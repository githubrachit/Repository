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
@JsonPropertyOrder({ "request" })
public class InputRequest {

    @JsonProperty("request")
    private Request request;

    /**
     * No args constructor for use in serialization
     * 
     */
    public InputRequest() {
    }

    /**
     * 
     * @param request
     */
    public InputRequest(Request request) {
	super();
	this.request = request;
    }

    @JsonProperty("request")
    public Request getRequest() {
	return request;
    }

    @JsonProperty("request")
    public void setRequest(Request request) {
	this.request = request;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "InputRequest [request=" + request + "]";
    }
}