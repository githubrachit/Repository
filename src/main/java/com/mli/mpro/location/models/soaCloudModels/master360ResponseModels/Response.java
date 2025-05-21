package com.mli.mpro.location.models.soaCloudModels.master360ResponseModels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.Utility;

import javax.validation.Valid;
import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "header",
    "msgInfo",
    "payload"
})
public class Response
{

    @JsonProperty("header")
    @Valid
    private Header header;
    @JsonProperty("msgInfo")
    @Valid
    private MsgInfo msgInfo;
    @JsonProperty("payload")
    @Valid
    private Payload payload;

    @JsonProperty("header")
    public Header getHeader() {
        return header;
    }

    @JsonProperty("header")
    public void setHeader(Header header) {
        this.header = header;
    }

    public Response withHeader(Header header) {
        this.header = header;
        return this;
    }

    @JsonProperty("msgInfo")
    public MsgInfo getMsgInfo() {
        return msgInfo;
    }

    @JsonProperty("msgInfo")
    public void setMsgInfo(MsgInfo msgInfo) {
        this.msgInfo = msgInfo;
    }

    public Response withMsgInfo(MsgInfo msgInfo) {
        this.msgInfo = msgInfo;
        return this;
    }

    @JsonProperty("payload")
    public Payload getPayload() {
        return payload;
    }

    @JsonProperty("payload")
    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public Response withPayload(Payload payload) {
        this.payload = payload;
        return this;
    }

	@Override
	public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
		return "Response [header=" + header + ", msgInfo=" + msgInfo + ", payload=" + payload + "]";
	}

  

}
