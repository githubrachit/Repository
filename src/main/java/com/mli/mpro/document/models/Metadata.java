package com.mli.mpro.document.models;

import com.mli.mpro.utils.Utility;

public class Metadata {

    private String env;

    private String requestId;

    public Metadata() {
	super();
    }

    public String getEnv() {
	return env;
    }

    public void setEnv(String env) {
	this.env = env;
    }

    public String getRequestId() {
	return requestId;
    }

    public void setRequestId(String requestId) {
	this.requestId = requestId;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "Metadata [env=" + env + ", requestId=" + requestId + "]";
    }

}
