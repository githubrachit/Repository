
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "requestPayload" })
public class RequestData {

    @JsonProperty("requestPayload")
    private RequestPayload requestPayload;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RequestData() {
    }

    /**
     * 
     * @param requestPayload
     */
    public RequestData(RequestPayload requestPayload) {
	super();
	this.requestPayload = requestPayload;
    }

    @JsonProperty("requestPayload")
    public RequestPayload getRequestPayload() {
	return requestPayload;
    }

    @JsonProperty("requestPayload")
    public void setRequestPayload(RequestPayload requestPayload) {
	this.requestPayload = requestPayload;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "RequestData [requestPayload=" + requestPayload + "]";
    }

}
