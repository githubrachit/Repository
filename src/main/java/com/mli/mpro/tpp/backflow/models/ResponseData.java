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
@JsonPropertyOrder({ "responsePayload" })
public class ResponseData {

    @JsonProperty("responsePayload")
    private ResponsePayload responsePayload;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ResponseData() {
    }

    /**
     * 
     * @param responsePayload
     */
    public ResponseData(ResponsePayload responsePayload) {
	super();
	this.responsePayload = responsePayload;
    }

    @JsonProperty("responsePayload")
    public ResponsePayload getResponsePayload() {
	return responsePayload;
    }

    @JsonProperty("responsePayload")
    public void setResponsePayload(ResponsePayload responsePayload) {
	this.responsePayload = responsePayload;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "ResponseData [responsePayload=" + responsePayload + "]";
    }

}