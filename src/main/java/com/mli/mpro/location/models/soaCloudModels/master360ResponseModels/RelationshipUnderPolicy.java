package com.mli.mpro.location.models.soaCloudModels.master360ResponseModels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "clientInsCd"
})
public class RelationshipUnderPolicy
{

    @JsonProperty("clientInsCd")
    private String clientInsCd;

    @JsonProperty("clientInsCd")
    public String getClientInsCd() {
        return clientInsCd;
    }

    @JsonProperty("clientInsCd")
    public void setClientInsCd(String clientInsCd) {
        this.clientInsCd = clientInsCd;
    }

    public RelationshipUnderPolicy withClientInsCd(String clientInsCd) {
        this.clientInsCd = clientInsCd;
        return this;
    }

	@Override
	public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "RelationshipUnderPolicy [clientInsCd=" + clientInsCd + "]";
	}

   
}
