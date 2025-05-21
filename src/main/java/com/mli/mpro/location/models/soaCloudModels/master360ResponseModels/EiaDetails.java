package com.mli.mpro.location.models.soaCloudModels.master360ResponseModels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "policyEiaInd"
})
public class EiaDetails 
{

    @JsonProperty("policyEiaInd")
    private String policyEiaInd;
    
    @JsonProperty("policyEiaInd")
    public String getPolicyEiaInd() {
        return policyEiaInd;
    }

    @JsonProperty("policyEiaInd")
    public void setPolicyEiaInd(String policyEiaInd) {
        this.policyEiaInd = policyEiaInd;
    }

    public EiaDetails withPolicyEiaInd(String policyEiaInd) {
        this.policyEiaInd = policyEiaInd;
        return this;
    }

	@Override
	public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "EiaDetails [policyEiaInd=" + policyEiaInd + "]";
	}

   

}
