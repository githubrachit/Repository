package com.mli.mpro.pratham.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "msgCode", "msg", "msgDescription" })
public class MsgInfo {

	@JsonProperty("msgCode")
	private String msgCode;
	@JsonProperty("msg")
	private String msg;
	@JsonProperty("msgDescription")
	private String msgDescription;

	@JsonProperty("msgCode")
	public String getMsgCode() {
		return msgCode;
	}

	@JsonProperty("msgCode")
	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	@JsonProperty("msg")
	public String getMsg() {
		return msg;
	}

	@JsonProperty("msg")
	public void setMsg(String msg) {
		this.msg = msg;
	}

	@JsonProperty("msgDescription")
	public String getMsgDescription() {
		return msgDescription;
	}

	@JsonProperty("msgDescription")
	public void setMsgDescription(String msgDescription) {
		this.msgDescription = msgDescription;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "MsgInfo [msgCode=" + msgCode + ", msg=" + msg + ", msgDescription=" + msgDescription + "]";
	}

}