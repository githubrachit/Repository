package com.mli.mpro.productRestriction.models.proposalFormRequestModels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;

import static com.mli.mpro.productRestriction.util.AppConstants.LOG_TYPE_LIST;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "request" })
public class OutputRequest {

    @JsonProperty("request")
    private Request request;

    public OutputRequest() {
	super();
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
        return "OutputRequest [request=" + request + "]";
    }

}
