package com.mli.mpro.location.login.exception;

public class UserDataException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private String msgCode;
	private String msg;
	private String msgDescription;

	public String getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsgDescription() {
		return msgDescription;
	}

	public void setMsgDescription(String msgDescription) {
		this.msgDescription = msgDescription;
	}

	public UserDataException(String msgCode, String msg, String msgDescription) {
		super();
		this.msgCode = msgCode;
		this.msg = msg;
		this.msgDescription = msgDescription;
	}

	@Override
	public String toString() {
		return "UserDataException [msgCode=" + msgCode + ", msg=" + msg + ", msgDescription=" + msgDescription + "]";
	}

}
