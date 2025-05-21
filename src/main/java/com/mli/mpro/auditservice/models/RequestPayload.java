package com.mli.mpro.auditservice.models;

import com.mli.mpro.utils.Utility;

public class RequestPayload {
    private AuditingDetails serviceTransactionDetails;

    public RequestPayload(AuditingDetails serviceTransactionDetails) {
	super();
	this.serviceTransactionDetails = serviceTransactionDetails;
    }

    public RequestPayload() {

    }

    public AuditingDetails getServiceTransactionDetails() {
	return serviceTransactionDetails;
    }

    public void setServiceTransactionDetails(AuditingDetails serviceTransactionDetails) {
	this.serviceTransactionDetails = serviceTransactionDetails;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "RequestPayload{" +
                "serviceTransactionDetails=" + serviceTransactionDetails +
                '}';
    }
}
