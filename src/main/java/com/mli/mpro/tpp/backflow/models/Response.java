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
@JsonPropertyOrder({ "metadata", "responseStatus", "responseData" })
public class Response {

    @JsonProperty("metadata")
    private Metadata metadata;
    @JsonProperty("responseStatus")
    private ResponseStatus responseStatus;
    @JsonProperty("responseData")
    private ResponseData responseData;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Response() {
    }

    /**
     * 
     * @param responseData
     * @param responseStatus
     * @param metadata
     */
    public Response(Metadata metadata, ResponseStatus responseStatus, ResponseData responseData) {
	super();
	this.metadata = metadata;
	this.responseStatus = responseStatus;
	this.responseData = responseData;
    }

    @JsonProperty("metadata")
    public Metadata getMetadata() {
	return metadata;
    }

    @JsonProperty("metadata")
    public void setMetadata(Metadata metadata) {
	this.metadata = metadata;
    }

    @JsonProperty("responseStatus")
    public ResponseStatus getResponseStatus() {
	return responseStatus;
    }

    @JsonProperty("responseStatus")
    public void setResponseStatus(ResponseStatus responseStatus) {
	this.responseStatus = responseStatus;
    }

    @JsonProperty("responseData")
    public ResponseData getResponseData() {
	return responseData;
    }

    @JsonProperty("responseData")
    public void setResponseData(ResponseData responseData) {
	this.responseData = responseData;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "Response [metadata=" + metadata + ", responseStatus=" + responseStatus + ", responseData=" + responseData + "]";
    }

}