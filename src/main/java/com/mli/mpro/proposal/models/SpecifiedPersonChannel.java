package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class SpecifiedPersonChannel {

	@Sensitive(MaskType.BRANCH_CODE)
	@JsonProperty("spBranchCode")
	private String spBranchCode;

	@Sensitive(MaskType.BANK_BRANCH_NAME)
	@JsonProperty("spBranchName")
	private String spBranchName;

	@JsonProperty("spChannel")
	private String spChannel;

	@JsonProperty("spChannelId")
	private String spChannelId;

	public SpecifiedPersonChannel() {
	}

	public SpecifiedPersonChannel(String spBranchCode, String spBranchName, String spChannel, String spChannelId) {
		super();
		this.spBranchCode = spBranchCode;
		this.spBranchName = spBranchName;
		this.spChannel = spChannel;
		this.spChannelId = spChannelId;
	}

	public String getSpBranchCode() {
		return spBranchCode;
	}

	public void setSpBranchCode(String spBranchCode) {
		this.spBranchCode = spBranchCode;
	}

	public String getSpBranchName() {
		return spBranchName;
	}

	public void setSpBranchName(String spBranchName) {
		this.spBranchName = spBranchName;
	}

	public String getSpChannel() {
		return spChannel;
	}

	public void setSpChannel(String spChannel) {
		this.spChannel = spChannel;
	}

	public String getSpChannelId() {
		return spChannelId;
	}

	public void setSpChannelId(String spChannelId) {
		this.spChannelId = spChannelId;
	}

	@Override
	public String toString() {
		if (Utility.isCalledFromLogs(Thread.currentThread())) {
			return Utility.toString(this);
		}
		return "SpecifiedPersonChannel [spBranchCode=" + spBranchCode + ", spBranchName=" + spBranchName
				+ ", spChannel=" + spChannel + ", spChannelId=" + spChannelId + "]";
	}

}
