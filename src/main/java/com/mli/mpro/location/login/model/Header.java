package com.mli.mpro.location.login.model;

public class Header {

	private String soaCorrelationId;
	private String soaMsgVersion;
	private String soaAppId;
	private String apitoken;
	private Integer expiryTime;

	public String getSoaCorrelationId() {
		return soaCorrelationId;
	}

	public void setSoaCorrelationId(String soaCorrelationId) {
		this.soaCorrelationId = soaCorrelationId;
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

	public String getApitoken() {
		return apitoken;
	}

	public void setApitoken(String apitoken) {
		this.apitoken = apitoken;
	}

	public Integer getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(Integer expiryTime) {
		this.expiryTime = expiryTime;
	}

	@Override
	public String toString() {
		return "Header [soaCorrelationId=" + soaCorrelationId + ", soaMsgVersion=" + soaMsgVersion + ", soaAppId="
				+ soaAppId + ", apitoken=" + apitoken + ", expiryTime=" + expiryTime + "]";
	}

}
