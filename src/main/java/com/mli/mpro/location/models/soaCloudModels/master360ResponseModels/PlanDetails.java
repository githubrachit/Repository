package com.mli.mpro.location.models.soaCloudModels.master360ResponseModels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "planInsuredTypCd"
})
public class PlanDetails
{

    @JsonProperty("planInsuredTypCd")
    private String planInsuredTypCd;

    @JsonProperty("planInsuredTypCd")
    public String getPlanInsuredTypCd() {
        return planInsuredTypCd;
    }

    @JsonProperty("planInsuredTypCd")
    public void setPlanInsuredTypCd(String planInsuredTypCd) {
        this.planInsuredTypCd = planInsuredTypCd;
    }

    public PlanDetails withPlanInsuredTypCd(String planInsuredTypCd) {
        this.planInsuredTypCd = planInsuredTypCd;
        return this;
    }

	@Override
	public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
		return "PlanDetails [planInsuredTypCd=" + planInsuredTypCd + "]";
	}


}
