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
@JsonPropertyOrder({ "response" })
public class OutputResponse {

    @JsonProperty("response")
    private Response response;

    /**
     * No args constructor for use in serialization
     * 
     */
    public OutputResponse() {
    }

    /**
     * 
     * @param response
     */
    public OutputResponse(Response response) {
	super();
	this.response = response;
    }

    @JsonProperty("response")
    public Response getResponse() {
	return response;
    }

    @JsonProperty("response")
    public void setResponse(Response response) {
	this.response = response;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "OutputResponse [response=" + response + "]";
    }

}