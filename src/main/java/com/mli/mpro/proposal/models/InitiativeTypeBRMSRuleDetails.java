package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class InitiativeTypeBRMSRuleDetails {

	@JsonProperty("serviceCall")
    private String serviceCall;
    @JsonProperty("initiativeType")
    private String initiativeType;
    
    @JsonProperty("serviceCall")
	public String getServiceCall() {
		return serviceCall;
	}
    @JsonProperty("serviceCall")
	public void setServiceCall(String serviceCall) {
		this.serviceCall = serviceCall;
	}
    @JsonProperty("initiativeType")
	public String getInitiativeType() {
		return initiativeType;
	}
    @JsonProperty("initiativeType")
	public void setInitiativeType(String initiativeType) {
		this.initiativeType = initiativeType;
	}
	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "InitiativeTypeBRMSRuleDetails [serviceCall=" + serviceCall + ", initiativeType=" + initiativeType + "]";
	}
}
