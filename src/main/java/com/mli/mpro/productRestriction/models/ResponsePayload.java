package com.mli.mpro.productRestriction.models;

import com.mli.mpro.utils.Utility;

import java.util.List;

public class ResponsePayload {

    private boolean isEligible;
    private List<String> messages;
    private List<String> errorFields;
    /* FUL2-9472 WLS Product Restriction */
    private String shouldEnableDoc;

    private String pasaQuestionsValidation;

    public String getPasaQuestionsValidation() {
        return pasaQuestionsValidation;
    }

    public void setPasaQuestionsValidation(String pasaQuestionsValidation) {
        this.pasaQuestionsValidation = pasaQuestionsValidation;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public boolean isEligible() {
        return isEligible;
    }

    public void setEligible(boolean eligible) {
        isEligible = eligible;
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
        return "ResponsePayload{" +
                "isEligible=" + isEligible +
                ", messages=" + messages +
                ", errorFields=" + errorFields +
                ", pasaQuestionsValidation=" + pasaQuestionsValidation +
                ", shouldEnableDoc='" + shouldEnableDoc + '\'' +
                '}';
    }
}
