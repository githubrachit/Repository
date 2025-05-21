package com.mli.mpro.location.amlulip.training.model;

import com.mli.mpro.location.common.soa.model.Header;

public class UlipPayload {
	
	private Header header;
	private UlipPayloadRequest payload;
	public Header getHeader() {
		return header;
	}
	public void setHeader(Header header) {
		this.header = header;
	}
	public UlipPayloadRequest getPayload() {
		return payload;
	}
	public void setPayload(UlipPayloadRequest payload) {
		this.payload = payload;
	}
	@Override
	public String toString() {
		return "UlipPayload [header=" + header + ", payload=" + payload + "]";
	}
}
