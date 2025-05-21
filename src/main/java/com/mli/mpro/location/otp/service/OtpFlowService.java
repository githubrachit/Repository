package com.mli.mpro.location.otp.service;

import com.mli.mpro.location.otp.models.Payload;

public interface OtpFlowService {
    String execute(Payload payload, String otp, String proposerName);
    void setSmsPayload(com.mli.mpro.sms.models.RequestPayload smsPayload, Object requestObject, String otp,String proposerName);
}
