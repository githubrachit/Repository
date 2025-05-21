package com.mli.mpro.location.training.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestData {
	
	private String designation;
	
	private String channel;
	
	private String goCode;

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	

	public String getGoCode() {
		return goCode;
	}

	public void setGoCode(String goCode) {
		this.goCode = goCode;
	}

	@Override
	public String toString() {
		return "RequestData [designation=" + designation + ", channel=" + channel + ", goCode=" + goCode + "]";
	}	
}
