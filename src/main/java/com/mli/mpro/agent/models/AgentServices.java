package com.mli.mpro.agent.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class AgentServices {
    @JsonProperty("serviceName")
    private String serviceName;

    public AgentServices() {
	super();
    }

    public AgentServices(String serviceName) {
	super();
	this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
           return Utility.toString(this);
        }
        return "Service [serviceName=" + serviceName + "]";
    }
}
