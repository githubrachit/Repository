package com.mli.mpro.common.models;

public class BranchDetailsRequestPayload {

    private String channelCode;
    private String enquiryType;

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }
   
    public String getEnquiryType() {
		return enquiryType;
	}

	public void setEnquiryType(String enquiryType) {
		this.enquiryType = enquiryType;
	}

	@Override
	public String toString() {
		return "BranchDetailsRequestPayload [channelCode=" + channelCode + ", enquiryType=" + enquiryType + "]";
	}
}
