package com.mli.mpro.productRestriction.models.proposalFormRequestModels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;

import static com.mli.mpro.productRestriction.util.AppConstants.LOG_TYPE_LIST;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "header", "payload" })
public class Request {

    @JsonProperty("header")
    private Header header;
    @JsonProperty("payload")
    private Payload payload;

    public Request() {
	super();
    }

    public Request(Header header, Payload payload) {
	super();
	this.header = header;
	this.payload = payload;
    }

    @JsonProperty("header")
    public Header getHeader() {
	return header;
    }

    @JsonProperty("header")
    public void setHeader(Header header) {
	this.header = header;
    }

    @JsonProperty("payload")
    public Payload getPayload() {
	return payload;
    }

    @JsonProperty("payload")
    public void setPayload(Payload payload) {
	this.payload = payload;
    }

    @Override
    public String toString() {
           if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "Request [header=" + header + ", payload=" + payload + ", additionalProperties=" + "]";
    }

}
