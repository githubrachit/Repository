package com.mli.mpro.location.models.soaCloudModels.master360ResponseModels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.Utility;

import java.io.Serializable;
import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "msgCode",
    "msg",
    "msgDescription"
})
public class MsgInfo implements Serializable
{

    @JsonProperty("msgCode")
    private String msgCode;
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("msgDescription")
    private String msgDescription;
    private final static long serialVersionUID = 3423122049618756735L;

    @JsonProperty("msgCode")
    public String getMsgCode() {
        return msgCode;
    }

    @JsonProperty("msgCode")
    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public MsgInfo withMsgCode(String msgCode) {
        this.msgCode = msgCode;
        return this;
    }

    @JsonProperty("msg")
    public String getMsg() {
        return msg;
    }

    @JsonProperty("msg")
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public MsgInfo withMsg(String msg) {
        this.msg = msg;
        return this;
    }

    @JsonProperty("msgDescription")
    public String getMsgDescription() {
        return msgDescription;
    }

    @JsonProperty("msgDescription")
    public void setMsgDescription(String msgDescription) {
        this.msgDescription = msgDescription;
    }

    public MsgInfo withMsgDescription(String msgDescription) {
        this.msgDescription = msgDescription;
        return this;
    }

	@Override
	public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
		return "MsgInfo [msgCode=" + msgCode + ", msg=" + msg + ", msgDescription=" + msgDescription + "]";
	}

   

}
