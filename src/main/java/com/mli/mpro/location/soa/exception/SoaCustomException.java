package com.mli.mpro.location.soa.exception;

public class SoaCustomException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String msg;
	private String msgDescription;
	private String msgCode;

	public SoaCustomException(String msg, String msgDescription, String msgCode) {
		super();
		this.msg = msg;
		this.msgDescription = msgDescription;
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

	public String getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	@Override
	public String toString() {
		return "SoaCustomException{" + "msg='" + msg + '\'' + ", msgDescription='" + msgDescription + '\''
				+ ", msgCode='" + msgCode + '\'' + '}';
	}
}
