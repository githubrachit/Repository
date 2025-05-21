package com.mli.mpro.location.models.soaCloudModels.master360ResponseModels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "policyOwnerId",
    "policyAssigneeId",
    "polprimInsrd"
})
public class PolicyRelationship
{

    @JsonProperty("policyOwnerId")
    private String policyOwnerId;
    @JsonProperty("policyAssigneeId")
    private String policyAssigneeId;
    @JsonProperty("polprimInsrd")
    private String polprimInsrd;

    @JsonProperty("policyOwnerId")
    public String getPolicyOwnerId() {
        return policyOwnerId;
    }

    @JsonProperty("policyOwnerId")
    public void setPolicyOwnerId(String policyOwnerId) {
        this.policyOwnerId = policyOwnerId;
    }

    public PolicyRelationship withPolicyOwnerId(String policyOwnerId) {
        this.policyOwnerId = policyOwnerId;
        return this;
    }

    @JsonProperty("policyAssigneeId")
    public String getPolicyAssigneeId() {
        return policyAssigneeId;
    }

    @JsonProperty("policyAssigneeId")
    public void setPolicyAssigneeId(String policyAssigneeId) {
        this.policyAssigneeId = policyAssigneeId;
    }

    public PolicyRelationship withPolicyAssigneeId(String policyAssigneeId) {
        this.policyAssigneeId = policyAssigneeId;
        return this;
    }

    @JsonProperty("polprimInsrd")
    public String getPolprimInsrd() {
        return polprimInsrd;
    }

    @JsonProperty("polprimInsrd")
    public void setPolprimInsrd(String polprimInsrd) {
        this.polprimInsrd = polprimInsrd;
    }

    public PolicyRelationship withPolprimInsrd(String polprimInsrd) {
        this.polprimInsrd = polprimInsrd;
        return this;
    }

	@Override
	public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
		return "PolicyRelationship [policyOwnerId=" + policyOwnerId + ", policyAssigneeId=" + policyAssigneeId
				+ ", polprimInsrd=" + polprimInsrd + "]";
	}

   
}
