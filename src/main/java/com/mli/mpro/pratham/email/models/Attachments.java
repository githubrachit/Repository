package com.mli.mpro.pratham.email.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class Attachments {

	@JsonProperty("fileName")
	private String fileName;

	@JsonProperty("byteArrayBase64")
	private String byteArrayBase64;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getByteArrayBase64() {
		return byteArrayBase64;
	}

	public void setByteArrayBase64(String byteArrayBase64) {
		this.byteArrayBase64 = byteArrayBase64;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "Attachments [fileName=" + fileName + ", byteArrayBase64=" + byteArrayBase64 + "]";
	}

}
