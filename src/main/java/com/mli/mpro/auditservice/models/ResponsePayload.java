package com.mli.mpro.auditservice.models;

import com.mli.mpro.utils.Utility;

import java.util.List;
import java.util.Map;

public class ResponsePayload {

    private List<Object> message;
    private Map<String, Object> auditData;

    public ResponsePayload() {
	super();
    }

    public List<Object> getMessage() {
	return message;
    }

    public void setMessage(List<Object> message) {
	this.message = message;
    }

    public Map<String, Object> getAuditData() {
	return auditData;
    }

    public void setAuditData(Map<String, Object> auditData) {
	this.auditData = auditData;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "ResponsePayload [message=" + message + ", auditData=" + auditData + "]";
    }

}
