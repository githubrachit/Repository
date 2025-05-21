package com.mli.mpro.onboarding.partner.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.onboarding.model.MsgInfo;

public abstract class PartnerPayload {

	@JsonProperty("msgInfo")
	private MsgInfo msginfo;

	
	public MsgInfo getMsginfo() {
		return msginfo;
	}

	public void setMsginfo(MsgInfo msginfo) {
		this.msginfo = msginfo;
	}
}
