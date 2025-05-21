package com.mli.mpro.location.training.model;

public class Request {
	
	private Data data;

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Request [data=" + data + "]";
	}
}
