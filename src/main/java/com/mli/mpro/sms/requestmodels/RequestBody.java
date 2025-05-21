package com.mli.mpro.sms.requestmodels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "appAccId", "appAccPass", "appId", "msgTo", "msgText" })
public class RequestBody {

	@JsonProperty("appAccId")
	private String appAccId;
	@JsonProperty("appAccPass")
	private String appAccPass;
	@JsonProperty("appId")
	private String appId;
	@Sensitive(MaskType.MOBILE)
	@JsonProperty("msgTo")
	private String msgTo;
	@JsonProperty("msgText")
	private String msgText;
	@JsonProperty("encryptionKey")
	private String encryptionKey;

	public String getEncryptionKey() {
		return encryptionKey;
	}

	public void setEncryptionKey(String encryptionKey) {
		this.encryptionKey = encryptionKey;
	}

	@JsonProperty("appAccId")
	public String getAppAccId() {
		return appAccId;
	}

	@JsonProperty("appAccId")
	public void setAppAccId(String appAccId) {
		this.appAccId = appAccId;
	}

	@JsonProperty("appAccPass")
	public String getAppAccPass() {
		return appAccPass;
	}

	@JsonProperty("appAccPass")
	public void setAppAccPass(String appAccPass) {
		this.appAccPass = appAccPass;
	}

	@JsonProperty("appId")
	public String getAppId() {
		return appId;
	}

	@JsonProperty("appId")
	public void setAppId(String appId) {
		this.appId = appId;
	}

	@JsonProperty("msgTo")
	public String getMsgTo() {
		return msgTo;
	}

	@JsonProperty("msgTo")
	public void setMsgTo(String msgTo) {
		this.msgTo = msgTo;
	}

	@JsonProperty("msgText")
	public String getMsgText() {
		return msgText;
	}

	@JsonProperty("msgText")
	public void setMsgText(String msgText) {
		this.msgText = msgText;
	}

	@Override
	public String toString() {
		if (Utility.isCalledFromLogs(Thread.currentThread())) {
			return Utility.toString(this);
		}
		return "RequestBody [appAccId=" + appAccId + ", appAccPass=" + appAccPass + ", appId=" + appId + ", msgTo="
				+ msgTo + ", msgText=" + msgText + ", encryptionKey=" + encryptionKey + "]";
	}

}