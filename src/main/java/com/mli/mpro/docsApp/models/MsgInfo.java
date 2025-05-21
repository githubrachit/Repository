package com.mli.mpro.docsApp.models;

import com.mli.mpro.utils.Utility;

public class MsgInfo {
	private String msg;

	private String msgDescription;

	private String msgCode;

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
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "ClassPojo [msg = " + msg + ", msgDescription = " + msgDescription + ", msgCode = " + msgCode + "]";
	}
}