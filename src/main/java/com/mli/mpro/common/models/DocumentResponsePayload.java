package com.mli.mpro.common.models;

import com.mli.mpro.utils.Utility;

public class DocumentResponsePayload {

    private String documentInBase64;
    private String documentGenerationStatus;

    public String getDocumentInBase64() {
	return documentInBase64;
    }

    public void setDocumentInBase64(String documentInBase64) {
	this.documentInBase64 = documentInBase64;
    }

    public String getDocumentGenerationStatus() {
	return documentGenerationStatus;
    }

    public void setDocumentGenerationStatus(String documentGenerationStatus) {
	this.documentGenerationStatus = documentGenerationStatus;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "DocumentResponsePayload [documentInBase64=" + documentInBase64 + ", documentGenerationStatus=" + documentGenerationStatus + "]";
    }

}
