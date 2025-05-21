package com.mli.mpro.pratham.models;

import com.mli.mpro.utils.Utility;

public class MessageInputRequest {
	
	private long transactionId;
	private int retryCount;
	private InputRequest inputRequest;
	
	public long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
	public int getRetryCount() {
		return retryCount;
	}
	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}
	public InputRequest getInputRequest() {
		return inputRequest;
	}
	public void setInputRequest(InputRequest inputRequest) {
		this.inputRequest = inputRequest;
	}
	
	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "MessageInputRequest [transactionId=" + transactionId + ", retryCount=" + retryCount + ", inputRequest="
				+ inputRequest + "]";
	}

}
