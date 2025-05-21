package com.mli.mpro.proposal.models;

import com.mli.mpro.utils.Utility;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "riderCode")
public class RiderCode {
    private String productCode;
    private String riderPlanCode;
    private String riderPlanDesc;
    // FUL2-9523 Critical Illness Rider
    private String variant;
    private String premiumType;

    public RiderCode() {
    }

    public RiderCode(String productCode, String riderPlanCode, String riderPlanDesc) {
	super();
	this.productCode = productCode;
	this.riderPlanCode = riderPlanCode;
	this.riderPlanDesc = riderPlanDesc;
    }

    public String getProductCode() {
	return productCode;
    }

    public void setProductCode(String productCode) {
	this.productCode = productCode;
    }

    public String getRiderPlanCode() {
	return riderPlanCode;
    }

    public void setRiderPlanCode(String riderPlanCode) {
	this.riderPlanCode = riderPlanCode;
    }

    public String getRiderPlanDesc() {
	return riderPlanDesc;
    }

    public void setRiderPlanDesc(String riderPlanDesc) {
	this.riderPlanDesc = riderPlanDesc;
    }

    public String getVariant() {
		return variant;
	}

	public void setVariant(String variant) {
		this.variant = variant;
	}

	public String getPremiumType() {
		return premiumType;
	}

	public void setPremiumType(String premiumType) {
		this.premiumType = premiumType;
	}

	@Override
	public String toString() {
		if (Utility.isCalledFromLogs(Thread.currentThread())) {
			return Utility.toString(this);
		}
		return "RiderCode [productCode=" + productCode + ", riderPlanCode=" + riderPlanCode + ", riderPlanDesc="
				+ riderPlanDesc + ", variant=" + variant + ", premiumType=" + premiumType + "]";
	}

}
