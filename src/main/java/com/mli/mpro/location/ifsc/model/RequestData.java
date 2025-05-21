package com.mli.mpro.location.ifsc.model;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class RequestData {
    @Sensitive(MaskType.BANK_IFSC)
	private String ifsc;
	@Sensitive(MaskType.BANK_MICR)
	private String micr;
	private Long transactionId;

	private String channel;

	public String getIfsc() {
		return ifsc;
	}
	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}
	public String getMicr() {
		return micr;
	}
	public void setMicr(String micr) {
		this.micr = micr;
	}

	public Long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "RequestData [ifsc=" + ifsc + ", micr=" + micr + ", channel=" + channel + "]";
	}
}