
package com.mli.mpro.productRestriction.models.proposalFormRequestModels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;

import static com.mli.mpro.productRestriction.util.AppConstants.LOG_TYPE_LIST;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "soaCorrelationId", "soaAppId" })
public class Header {

    @JsonProperty("soaCorrelationId")
    private String soaCorrelationId;
    @JsonProperty("soaAppId")
    private String soaAppId;

    public Header() {
	super();
    }

    public Header(String soaCorrelationId, String soaAppId) {
	super();
	this.soaCorrelationId = soaCorrelationId;
	this.soaAppId = soaAppId;
    }

    @JsonProperty("soaCorrelationId")
    public String getSoaCorrelationId() {
	return soaCorrelationId;
    }

    @JsonProperty("soaCorrelationId")
    public void setSoaCorrelationId(String soaCorrelationId) {
	this.soaCorrelationId = soaCorrelationId;
    }

    @JsonProperty("soaAppId")
    public String getSoaAppId() {
	return soaAppId;
    }

    @JsonProperty("soaAppId")
    public void setSoaAppId(String soaAppId) {
	this.soaAppId = soaAppId;
    }

    @Override
    public String toString() {
           if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "Header [soaCorrelationId=" + soaCorrelationId + ", soaAppId=" + soaAppId + "]";
    }

}