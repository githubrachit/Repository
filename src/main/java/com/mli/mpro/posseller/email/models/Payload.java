package com.mli.mpro.posseller.email.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class Payload {
	
	@JsonProperty("status")
    private String status;
    @JsonProperty("statusMsg")
    private String statusMsg;
    
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusMsg() {
		return statusMsg;
	}
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	
	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "Payload [status=" + status + ", statusMsg=" + statusMsg + "]";
	}
    
    

}
