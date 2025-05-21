package com.mli.mpro.location.amlulip.training.model;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class RADetails {

	private String raId;
	@Sensitive(MaskType.NAME)
	private String raName;
	private String raStatus;
	private String roleCode;
	private String roleName;
	@Sensitive(MaskType.MOBILE)
	private String raPhoneNumber;
	@Sensitive(MaskType.EMAIL)
	private String raEmail;

	public String getRaId() {
		return raId;
	}

	public void setRaId(String raId) {
		this.raId = raId;
	}

	public String getRaName() {
		return raName;
	}

	public void setRaName(String raName) {
		this.raName = raName;
	}

	public String getRaStatus() {
		return raStatus;
	}

	public void setRaStatus(String raStatus) {
		this.raStatus = raStatus;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRaPhoneNumber() {
		return raPhoneNumber;
	}

	public void setRaPhoneNumber(String raPhoneNumber) {
		this.raPhoneNumber = raPhoneNumber;
	}

	public String getRaEmail() {
		return raEmail;
	}

	public void setRaEmail(String raEmail) {
		this.raEmail = raEmail;
	}

	@Override
	public String toString() {
		if (Utility.isCalledFromLogs(Thread.currentThread())) {
			return Utility.toString(this);
		}
		return "RADetails [raId=" + raId + ", raName=" + raName + ", raStatus=" + raStatus + ", roleCode=" + roleCode
				+ ", roleName=" + roleName + ", raPhoneNumber=" + raPhoneNumber + ", raEmail=" + raEmail + "]";
	}
}
