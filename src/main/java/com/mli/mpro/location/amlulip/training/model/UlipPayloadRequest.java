package com.mli.mpro.location.amlulip.training.model;

public class UlipPayloadRequest {
	
	private String id;
    private String key1;
    private String key2;
    private String key3;
    private String type;
    private String transTrackingID;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKey1() {
		return key1;
	}
	public void setKey1(String key1) {
		this.key1 = key1;
	}
	public String getKey2() {
		return key2;
	}
	public void setKey2(String key2) {
		this.key2 = key2;
	}
	public String getKey3() {
		return key3;
	}
	public void setKey3(String key3) {
		this.key3 = key3;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTransTrackingID() {
		return transTrackingID;
	}
	public void setTransTrackingID(String transTrackingID) {
		this.transTrackingID = transTrackingID;
	}
	@Override
	public String toString() {
		return "UlipPayloadRequest [id=" + id + ", key1=" + key1 + ", key2=" + key2 + ", key3=" + key3 + ", type="
				+ type + ", transTrackingID=" + transTrackingID + "]";
	}
}
