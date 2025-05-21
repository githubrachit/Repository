package com.mli.mpro.location.soa.exception;

public class InvalidRequestException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String msg;
	private final String msgDescription;
	
	
	public InvalidRequestException(String msg, String msgDescription) {
		super();
		this.msg = msg;
		this.msgDescription = msgDescription;
	}
	public String getMsg() {
		return msg;
	}
	public String getMsgDescription() {
		return msgDescription;
	}
	@Override
	public String toString() {
		return "InvalidRequestException [msg=" + msg + ", msgDescription=" + msgDescription + "]";
	}
	
	

}
