package com.mli.mpro.pasa.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class PasaValidationRequestPayload {

	@JsonProperty("clientId")
	private String clientId;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "PasaValidationRequestPayload{" +
				"clientId='" + clientId + '\'' +
				'}';
	}

}
