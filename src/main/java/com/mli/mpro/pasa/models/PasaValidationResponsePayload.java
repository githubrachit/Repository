package com.mli.mpro.pasa.models;

import com.mli.mpro.utils.Utility;

public class PasaValidationResponsePayload {
	
    private String isPasaEligible;
	private PasaValidationDetails pasaValidationDetails;

	private PasaRequirementDetails pasaRequirementDetails;

	public PasaRequirementDetails getPasaRequirementDetails() {
		return pasaRequirementDetails;
	}

	public void setPasaRequirementDetails(PasaRequirementDetails pasaRequirementDetails) {
		this.pasaRequirementDetails = pasaRequirementDetails;
	}
	public String getIsPasaEligible() {
		return isPasaEligible;
	}


	public void setIsPasaEligible(String isPasaEligible) {
		this.isPasaEligible = isPasaEligible;
	}


	public PasaValidationDetails getPasaValidationDetails() {
		return pasaValidationDetails;
	}


	public void setPasaValidationDetails(PasaValidationDetails pasaValidationDetails) {
		this.pasaValidationDetails = pasaValidationDetails;
	}


	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "PasaValidationResponsePayload [isPasaEligible=" + isPasaEligible + ", pasaValidationDetails="
				+ pasaValidationDetails + ", pasaValidationDetails=" + pasaRequirementDetails + "]";
	}
	

}
