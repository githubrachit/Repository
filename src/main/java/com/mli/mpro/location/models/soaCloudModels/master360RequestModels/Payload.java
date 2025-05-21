package com.mli.mpro.location.models.soaCloudModels.master360RequestModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;

public class Payload {

	@Sensitive(MaskType.POLICY_NUM)
	@JsonProperty("policyNo")
	private String policyNo;
	@JsonProperty("effectiveDate")
	private String effectiveDate;

	/**
	 * @return the policyNo
	 */
	public String getPolicyNo() {
		return policyNo;
	}

	/**
	 * @param policyNo the policyNo to set
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	@Override
	public String toString() {

           if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }

		return "Payload [policyNo=" + policyNo + ", effectiveDate=" + effectiveDate + "]";
	}
	
	
	
}
