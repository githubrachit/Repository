package com.mli.mpro.location.models.soaCloudModels.master360ResponseModels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "soaCorrelationId",
    "soaAppId"
})
public class Header
{

    @JsonProperty("soaCorrelationId")
    private String soaCorrelationId;
    @JsonProperty("soaAppId")
    private String soaAppId;

    @JsonProperty("soaCorrelationId")
    public String getSoaCorrelationId() {
        return soaCorrelationId;
    }

    @JsonProperty("soaCorrelationId")
    public void setSoaCorrelationId(String soaCorrelationId) {
        this.soaCorrelationId = soaCorrelationId;
    }

    public Header withSoaCorrelationId(String soaCorrelationId) {
        this.soaCorrelationId = soaCorrelationId;
        return this;
    }

    @JsonProperty("soaAppId")
    public String getSoaAppId() {
        return soaAppId;
    }

    @JsonProperty("soaAppId")
    public void setSoaAppId(String soaAppId) {
        this.soaAppId = soaAppId;
    }

    public Header withSoaAppId(String soaAppId) {
        this.soaAppId = soaAppId;
        return this;
    }

	@Override
	public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
		return "Header [soaCorrelationId=" + soaCorrelationId + ", soaAppId=" + soaAppId + "]";
	}

 

}
