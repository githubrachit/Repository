package com.mli.mpro.sms.requestmodels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "generalConsumerInformation" })
public class RequestHeader {

	@JsonProperty("generalConsumerInformation")
	private GeneralConsumerInformation generalConsumerInformation;

	@JsonProperty("generalConsumerInformation")
	public GeneralConsumerInformation getGeneralConsumerInformation() {
		return generalConsumerInformation;
	}

	@JsonProperty("generalConsumerInformation")
	public void setGeneralConsumerInformation(GeneralConsumerInformation generalConsumerInformation) {
		this.generalConsumerInformation = generalConsumerInformation;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "RequestHeader [generalConsumerInformation=" + generalConsumerInformation + "]";
	}

}