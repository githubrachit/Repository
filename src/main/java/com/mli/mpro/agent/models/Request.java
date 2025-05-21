package com.mli.mpro.agent.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class Request<T> {

    @JsonProperty("header")
    private RequestHeader header;
    @JsonProperty("payload")
    private T payload;

    public Request(T payload) {
		this.payload = payload;
	}public Request() {
    }

	public Request(RequestHeader header, T payload) {
		super();
		this.header = header;
		this.payload = payload;
	}

	public RequestHeader getHeader() {
		return header;
	}

	public void setHeader(RequestHeader header) {
		this.header = header;
	}

	public T getPayload() {
		return payload;
	}

	public void setPayload(T payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "Request [header=" + header + ", payload=" + payload + "]";
	}
}
