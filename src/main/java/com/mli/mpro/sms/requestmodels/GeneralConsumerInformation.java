package com.mli.mpro.sms.requestmodels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "messageVersion", "consumerId", "correlationId" })
public class GeneralConsumerInformation {

	@JsonProperty("messageVersion")
	private String messageVersion;
	@JsonProperty("consumerId")
	private String consumerId;
	@JsonProperty("correlationId")
	private String correlationId;

	@JsonProperty("messageVersion")
	public String getMessageVersion() {
		return messageVersion;
	}

	@JsonProperty("messageVersion")
	public void setMessageVersion(String messageVersion) {
		this.messageVersion = messageVersion;
	}

	@JsonProperty("consumerId")
	public String getConsumerId() {
		return consumerId;
	}

	@JsonProperty("consumerId")
	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}

	@JsonProperty("correlationId")
	public String getCorrelationId() {
		return correlationId;
	}

	@JsonProperty("correlationId")
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "GeneralConsumerInformation [messageVersion=" + messageVersion + ", consumerId=" + consumerId
				+ ", correlationId=" + correlationId + "]";
	}

}
