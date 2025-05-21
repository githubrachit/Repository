package com.mli.mpro.location.training.model;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class Data {
	
	private String agentCode;
	private String specifiedPersonCode;
	private String agentxChannel;
	@Sensitive(MaskType.BRANCH_CODE)
	private String branchCd;
	private String role;
	
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	public String getSpecifiedPersonCode() {
		return specifiedPersonCode;
	}
	public void setSpecifiedPersonCode(String specifiedPersonCode) {
		this.specifiedPersonCode = specifiedPersonCode;
	}
	public String getAgentxChannel() {
		return agentxChannel;
	}
	public void setAgentxChannel(String agentxChannel) {
		this.agentxChannel = agentxChannel;
	}
	public String getBranchCd() {
		return branchCd;
	}
	public void setBranchCd(String branchCd) {
		this.branchCd = branchCd;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "Data [agentCode=" + agentCode + ", specifiedPersonCode=" + specifiedPersonCode + ", agentxChannel="
				+ agentxChannel + ", branchCd=" + branchCd + ", role=" + role + "]";
	}
}
