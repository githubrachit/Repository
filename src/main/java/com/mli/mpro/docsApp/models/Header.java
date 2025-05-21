package com.mli.mpro.docsApp.models;

import com.mli.mpro.utils.Utility;

public class Header {
	private String soaCorrelationId;

	private String soaPassword;

	private String soaUserId;

	private String soaMsgVersion;

	private String soaAppId;

	public String getSoaCorrelationId() {
		return soaCorrelationId;
	}

	public void setSoaCorrelationId(String soaCorrelationId) {
		this.soaCorrelationId = soaCorrelationId;
	}

	public String getSoaPassword() {
		return soaPassword;
	}

	public void setSoaPassword(String soaPassword) {
		this.soaPassword = soaPassword;
	}

	public String getSoaUserId() {
		return soaUserId;
	}

	public void setSoaUserId(String soaUserId) {
		this.soaUserId = soaUserId;
	}

	public String getSoaMsgVersion() {
		return soaMsgVersion;
	}

	public void setSoaMsgVersion(String soaMsgVersion) {
		this.soaMsgVersion = soaMsgVersion;
	}

	public String getSoaAppId() {
		return soaAppId;
	}

	public void setSoaAppId(String soaAppId) {
		this.soaAppId = soaAppId;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "ClassPojo [soaCorrelationId = " + soaCorrelationId + ", soaPassword = " + soaPassword + ", soaUserId = "
				+ soaUserId + ", soaMsgVersion = " + soaMsgVersion + ", soaAppId = " + soaAppId + "]";
	}
}