package com.mli.mpro.onboarding.partner.model;

public class Header {  
        
    private String soaCorrelationId;
    private String soaAppId;

    public Header() {
		// default constructor
    }

	public String getSoaCorrelationId() {
		return soaCorrelationId;
	}

	public void setSoaCorrelationId(String soaCorrelationId) {
		this.soaCorrelationId = soaCorrelationId;
	}

	public String getSoaAppId() {
		return soaAppId;
	}

	public void setSoaAppId(String soaAppId) {
		this.soaAppId = soaAppId;
	}
}
