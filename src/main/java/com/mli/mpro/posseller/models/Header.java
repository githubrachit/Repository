package com.mli.mpro.posseller.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "soaAppId", "soaCorrelationId" })
public class Header {

	@JsonProperty("soaAppId")
	private String soaAppId;
	@JsonProperty("soaCorrelationId")
	private String soaCorrelationId;

	@JsonProperty("soaAppId")
	public String getSoaAppId() {
		return soaAppId;
	}

	@JsonProperty("soaAppId")
	public void setSoaAppId(String soaAppId) {
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

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "Header [soaAppId=" + soaAppId + ", soaCorrelationId=" + soaCorrelationId + "]";
	}
}
