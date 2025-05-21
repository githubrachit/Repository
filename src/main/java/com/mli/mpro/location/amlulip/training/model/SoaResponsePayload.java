package com.mli.mpro.location.amlulip.training.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SoaResponsePayload {
	
	private List<RegistrationDetails> registrationDetails;
	private List<RADetails> raDetails;
	private String type;
	private String id;
	private String key1;
	private String key2;
	private String key3;
	
	public List<RegistrationDetails> getRegistrationDetails() {
		return registrationDetails;
	}
	public void setRegistrationDetails(List<RegistrationDetails> registrationDetails) {
		this.registrationDetails = registrationDetails;
	}
	public List<RADetails> getRaDetails() {
		return raDetails;
	}
	public void setRaDetails(List<RADetails> raDetails) {
		this.raDetails = raDetails;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKey1() {
		return key1;
	}
	public void setKey1(String key1) {
		this.key1 = key1;
	}
	public String getKey2() {
		return key2;
	}
	public void setKey2(String key2) {
		this.key2 = key2;
	}
	public String getKey3() {
		return key3;
	}
	public void setKey3(String key3) {
		this.key3 = key3;
	}
	@Override
	public String toString() {
		return "SoaResponsePayload [registrationDetails=" + registrationDetails + ", raDetails=" + raDetails + ", type="
				+ type + ", id=" + id + ", key1=" + key1 + ", key2=" + key2 + ", key3=" + key3 + "]";
	}

	public SoaResponsePayload(List<RegistrationDetails> registrationDetails, List<RADetails> raDetails, String type, String id, String key1, String key2, String key3) {
		this.registrationDetails = registrationDetails;
		this.raDetails = raDetails;
		this.type = type;
		this.id = id;
		this.key1 = key1;
		this.key2 = key2;
		this.key3 = key3;
	}

	public SoaResponsePayload() {
	}
}
