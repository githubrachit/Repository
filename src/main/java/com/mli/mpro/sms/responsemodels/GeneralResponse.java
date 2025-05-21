package com.mli.mpro.sms.responsemodels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "correlationId", "consumerId", "appAccId", "status", "code", "description" })
public class GeneralResponse {

	@JsonProperty("correlationId")
	private String correlationId;
	@JsonProperty("consumerId")
	private String consumerId;
	@JsonProperty("appAccId")
	private String appAccId;
	@JsonProperty("status")
	private String status;
	@JsonProperty("code")
	private String code;
	@JsonProperty("description")
	private String description;

	@JsonProperty("correlationId")
	public String getCorrelationId() {
		return correlationId;
	}

	@JsonProperty("correlationId")
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	@JsonProperty("consumerId")
	public String getConsumerId() {
		return consumerId;
	}

	@JsonProperty("consumerId")
	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}

	@JsonProperty("appAccId")
	public String getAppAccId() {
		return appAccId;
	}

	@JsonProperty("appAccId")
	public void setAppAccId(String appAccId) {
		this.appAccId = appAccId;
	}

	@JsonProperty("status")
	public String getStatus() {
		return status;
	}

	@JsonProperty("status")
	public void setStatus(String status) {
		this.status = status;
	}

	@JsonProperty("code")
	public String getCode() {
		return code;
	}

	@JsonProperty("code")
	public void setCode(String code) {
		this.code = code;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "GeneralResponse [correlationId=" + correlationId + ", consumerId=" + consumerId + ", appAccId="
				+ appAccId + ", status=" + status + ", code=" + code + ", description=" + description + "]";
	}

}
