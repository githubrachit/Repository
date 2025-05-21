package com.mli.mpro.posseller.email.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class RequestData {
	@Sensitive(MaskType.EMAIL)
	@JsonProperty("mailIdTo")
    private String mailIdTo;

    @JsonProperty("mailSubject")
    private String mailSubject;

	@Sensitive(MaskType.NAME)
    @JsonProperty("fromName")
    private String fromName;

    @JsonProperty("attachments")
    private List<Attachments> attachments;

    @JsonProperty("isConsolidate")
    private boolean isConsolidate;

    @JsonProperty("isFileAttached")
    private boolean isFileAttached;
	@Sensitive(MaskType.EMAIL)
    @JsonProperty("fromEmail")
    private String fromEmail;

	@Sensitive(MaskType.MASK_ALL)
    @JsonProperty("mailBody")
    private String mailBody;

	public String getMailIdTo() {
		return mailIdTo;
	}

	public void setMailIdTo(String mailIdTo) {
		this.mailIdTo = mailIdTo;
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public List<Attachments> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachments> attachments) {
		this.attachments = attachments;
	}

	public boolean isConsolidate() {
		return isConsolidate;
	}

	public void setConsolidate(boolean isConsolidate) {
		this.isConsolidate = isConsolidate;
	}

	public boolean isFileAttached() {
		return isFileAttached;
	}

	public void setFileAttached(boolean isFileAttached) {
		this.isFileAttached = isFileAttached;
	}

	public String getFromEmail() {
		return fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public String getMailBody() {
		return mailBody;
	}

	public void setMailBody(String mailBody) {
		this.mailBody = mailBody;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "RequestData [mailIdTo=" + mailIdTo + ", mailSubject=" + mailSubject + ", fromName=" + fromName
				+ ", attachments=" + attachments + ", isConsolidate=" + isConsolidate + ", isFileAttached="
				+ isFileAttached + ", fromEmail=" + fromEmail + ", mailBody=" + mailBody + "]";
	}

}
