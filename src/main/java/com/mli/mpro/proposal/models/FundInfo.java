package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class FundInfo {

    @JsonProperty("fundName")
    private String fundName;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("fundValue")
    private int fundValue;

    public FundInfo() {
    }

    public FundInfo(String fundName, int fundValue) {
	super();
	this.fundName = fundName;
	this.fundValue = fundValue;
    }

    public String getFundName() {
	return fundName;
    }

    public void setFundName(String fundName) {
	this.fundName = fundName;
    }

    public int getFundValue() {
	return fundValue;
    }

    public void setFundValue(int fundValue) {
	this.fundValue = fundValue;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "FundInfo [fundName=" + fundName + ", fundValue=" + fundValue + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
		+ super.toString() + "]";
    }

}
