package com.mli.mpro.location.common.soa.model;

import com.mli.mpro.onboarding.partner.model.ErrorResponse;

import java.util.Set;

public class MsgInfo {

	private String msg;
	private String msgCode;
	private String msgDescription;
	private Set<ErrorResponse> errors;

	public MsgInfo() {
	}

	public MsgInfo(String msg, String msgCode, String msgDescription) {
		this.msg = msg;
		this.msgCode = msgCode;
		this.msgDescription = msgDescription;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	public String getMsgDescription() {
		return msgDescription;
	}

	public void setMsgDescription(String msgDescription) {
		this.msgDescription = msgDescription;
	}

	public Set<ErrorResponse> getErrors() {
		return errors;
	}

	public void setErrors(Set<ErrorResponse> errors) {
		this.errors = errors;
	}

	@Override
	public String toString() {
		return "MsgInfo{" + "msg='" + msg + '\'' + ", msgCode='" + msgCode + '\'' + "," +
				" msgDescription='" + msgDescription +
				" errors='" + errors +
				'\'' + '}';
	}
}