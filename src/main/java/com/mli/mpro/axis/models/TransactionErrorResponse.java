package com.mli.mpro.axis.models;

import com.mli.mpro.utils.Utility;

public class TransactionErrorResponse {
    private String message;
    
    private String status;

    public TransactionErrorResponse() {
	super();
    }

    public TransactionErrorResponse(String message, String status) {
	super();
	this.message = message;
	this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
	return "TransactionErrorResponse [message=" + message + ", status=" + status + "]";
    }
}
