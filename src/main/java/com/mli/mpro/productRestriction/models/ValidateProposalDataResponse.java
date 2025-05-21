package com.mli.mpro.productRestriction.models;

import com.mli.mpro.utils.Utility;

import java.util.List;

public class ValidateProposalDataResponse {
    private List<String> restrictionMessages;
    private List<String> failedMessages;
    private List<String> errorFields;
    /* FUL2-9472 WLS Product Restriction */
    private String shouldEnableDoc;

    public List<String> getRestrictionMessages() {
        return restrictionMessages;
    }

    public void setRestrictionMessages(List<String> restrictionMessages) {
        this.restrictionMessages = restrictionMessages;
    }

    public List<String> getFailedMessages() {
        return failedMessages;
    }

    public void setFailedMessages(List<String> failedMessages) {
        this.failedMessages = failedMessages;
    }
    
    public List<String> getErrorFields() {
		return errorFields;
	}

	public void setErrorFields(List<String> errorFields) {
		this.errorFields = errorFields;
	}

    public String getShouldEnableDoc() {
        return shouldEnableDoc;
    }

    public void setShouldEnableDoc(String shouldEnableDoc) {
        this.shouldEnableDoc = shouldEnableDoc;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
           return Utility.toString(this);
        }
        return "ValidateProposalDataResponse{" +
                "restrictionMessages=" + restrictionMessages +
                ", failedMessages=" + failedMessages +
                ", errorFields=" + errorFields +
                ", shouldEnableDoc='" + shouldEnableDoc + '\'' +
                '}';
    }
}
