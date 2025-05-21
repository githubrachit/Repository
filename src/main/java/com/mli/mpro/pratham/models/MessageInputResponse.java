package com.mli.mpro.pratham.models;

import com.mli.mpro.utils.Utility;

public class MessageInputResponse {
	
	private long transactionId;
	private String status;
	
	public long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "MessageInputResponse [transactionId=" + transactionId + ", status=" + status + "]";
	}

}
