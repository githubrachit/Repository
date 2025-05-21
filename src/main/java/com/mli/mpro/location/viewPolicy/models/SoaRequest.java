package com.mli.mpro.location.viewPolicy.models;

import com.mli.mpro.onboarding.model.Header;
import com.mli.mpro.utils.Utility;

public class SoaRequest {
	
	private Header header;
	
	private SoaInputPayload payload;

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public SoaInputPayload getPayload() {
		return payload;
	}

	public void setPayload(SoaInputPayload payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "SoaRequest [header=" + header + ", payload=" + payload + "]";
	}
	
}
