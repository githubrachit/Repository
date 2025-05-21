package com.mli.mpro.onboarding.illustration.pdf.model;



import com.mli.mpro.onboarding.documents.model.RequiredDocuments;

import java.util.List;

public class ResponsePayload {

    private String payloadBody;


    public ResponsePayload(String payloadBody) {
        this.payloadBody = payloadBody;
    }

    public ResponsePayload() {

    }

    public String getPayloadBody() {
        return payloadBody;
    }

    public void setPayloadBody(String payloadBody) {
        this.payloadBody = payloadBody;
    }

    @Override
    public String toString() {
        return "ResponsePayload{" +
                "payloadBody='" + payloadBody + '\'' +
                '}';
    }
}
