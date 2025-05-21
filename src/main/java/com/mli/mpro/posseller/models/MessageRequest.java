package com.mli.mpro.posseller.models;

import com.mli.mpro.utils.Utility;

public class MessageRequest {

	private long transactionId;

	private InputRequest inputRequest;

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
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
		return "SendMessageRequest [transactionId=" + transactionId + ", inputRequest=" + inputRequest + "]";
	}

}
