
package com.mli.mpro.onboarding.superApp.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.onboarding.util.ApplicationUtils;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;

import static com.mli.mpro.productRestriction.util.AppConstants.LOG_TYPE_LIST;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "metadata", "requestData" })
public class Request {

    @JsonProperty("metadata")
    private Metadata metadata;
    @JsonProperty("requestData")
    private RequestData requestData;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Request() {
    }

    /**
     * 
     * @param requestData
     * @param metadata
     */
    public Request(Metadata metadata, RequestData requestData) {
	super();
	this.metadata = metadata;
	this.requestData = requestData;
    }

    @JsonProperty("metadata")
    public Metadata getMetadata() {
	return metadata;
    }

    @JsonProperty("metadata")
    public void setMetadata(Metadata metadata) {
	this.metadata = metadata;
    }

    @JsonProperty("requestData")
    public RequestData getRequestData() {
	return requestData;
    }

    @JsonProperty("requestData")
    public void setRequestData(RequestData requestData) {
	this.requestData = requestData;
    }

    @Override
    public String toString() {
           if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Request{" +
                "metadata=" + metadata +
                ", requestData=" + requestData +
                '}';
    }
}
