package com.mli.mpro.underwriting.clientPolicyDetailsResponseModels;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Payload {

    @JsonProperty("currentplanType")
    private String currentPlanType;
    
    private List<PolicyDetails> policyDetails;

    @JsonProperty("currentplanType")
    public String getCurrentPlanType() {
        return currentPlanType;
    }
    
    @JsonProperty("currentplanType")
    public void setCurrentPlanType(String currentPlanType) {
        this.currentPlanType = currentPlanType;
    }

    public List<PolicyDetails> getPolicyDetails() {
        return policyDetails;
    }

    public void setPolicyDetails(List<PolicyDetails> policyDetails) {
        this.policyDetails = policyDetails;
    }

	@Override
	public String toString() {
		  if (Utility.isCalledFromLogs(Thread.currentThread())) {
	            return Utility.toString(this);
	        }
		return "Payload [currentPlanType=" + currentPlanType + ", policyDetails=" + policyDetails + "]";
	}

}
