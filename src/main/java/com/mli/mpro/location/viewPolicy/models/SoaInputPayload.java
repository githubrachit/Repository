package com.mli.mpro.location.viewPolicy.models;

import java.util.List;

import com.mli.mpro.utils.Utility;

public class SoaInputPayload {
	
	private List<String> policyNumber;
	private String systemType;
	private String infoType;
	
	public List<String> getPolicyNumber() {
		return policyNumber;
	}
	public void setPolicyNumber(List<String> policyNumber) {
		this.policyNumber = policyNumber;
	}
	public String getSystemType() {
		return systemType;
	}
	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}
	public String getInfoType() {
		return infoType;
	}
	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}
	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "SoaInputPayload [policyNumber=" + policyNumber + ", systemType=" + systemType + ", infoType=" + infoType
				+ "]";
	}
}
