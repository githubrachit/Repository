package com.mli.mpro.location.login.model;

import java.util.List;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;

public class Information {

	@Sensitive(MaskType.NAME)
	private String displayName;
	@Sensitive(MaskType.EMAIL)
	private String emailId;
	@Sensitive(MaskType.FIRST_NAME)
	private String firstName;
	@Sensitive(MaskType.LAST_NAME)
	private String lastName;
	private String appRole;
	private List<String> memberOf;
	private String agentCd;
	private String designation;
	@Sensitive(MaskType.BRANCH_CODE)
	private String branchCd;

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAppRole() {
		return appRole;
	}

	public void setAppRole(String appRole) {
		this.appRole = appRole;
	}

	public List<String> getMemberOf() {
		return memberOf;
	}

	public void setMemberOf(List<String> memberOf) {
		this.memberOf = memberOf;
	}

	public String getAgentCd() {
		return agentCd;
	}

	public void setAgentCd(String agentCd) {
		this.agentCd = agentCd;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getBranchCd() {
		return branchCd;
	}

	public void setBranchCd(String branchCd) {
		this.branchCd = branchCd;
	}

	@Override
	public String toString() {
		return "Information [displayName=" + displayName + ", emailId=" + emailId + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", appRole=" + appRole + ", memberOf=" + memberOf + ", agentCd=" + agentCd
				+ ", designation=" + designation + ", branchCd=" + branchCd + "]";
	}
}
