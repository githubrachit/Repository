package com.mli.mpro.location.models.soaCloudModels.master360ResponseModels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.Utility;

import java.io.Serializable;
import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "chequeBounceDt"
})
public class BounceDate implements Serializable
{

    @JsonProperty("chequeBounceDt")
    private String chequeBounceDt;
    private final static long serialVersionUID = 3585401247936152986L;

    @JsonProperty("chequeBounceDt")
    public String getChequeBounceDt() {
        return chequeBounceDt;
    }

    @JsonProperty("chequeBounceDt")
    public void setChequeBounceDt(String chequeBounceDt) {
        this.chequeBounceDt = chequeBounceDt;
    }

    public BounceDate withChequeBounceDt(String chequeBounceDt) {
        this.chequeBounceDt = chequeBounceDt;
        return this;
    }

	@Override
	public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
		return "BounceDate [chequeBounceDt=" + chequeBounceDt + "]";
	}

  
}
