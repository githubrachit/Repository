package com.mli.mpro.emailsms.notification;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SOASmsEmailConstantBean {
    @Value("${urlDetails.email}")
    private String email;
    @Value("${urlDetails.sms}")
    private String sms;
    @Value("${urlDetails.sms.new}")
    private String newDataLakeSMSAPI;
    @Value("${soa.x-apigw-api-id}")
    private String xApigwApiId;
    @Value("${soa.x-api-key}")
    private String xApiKey;
    @Value("${urlDetails.encryptionKey}")
    private String encryptionKey;

    @Value("${urlDetails.accpassKey}")
    private String appPassKey;

    public String getEmail() {
        return email;
    }

    public String getSms() {
        return sms;
    }

    public String getNewDataLakeSMSAPI() {
        return newDataLakeSMSAPI;
    }

    public String getxApigwApiId() {
        return xApigwApiId;
    }

    public String getxApiKey() {
        return xApiKey;
    }

    public String getEncryptionKey() {
        return encryptionKey;
    }

    public String getAppPassKey() {
        return appPassKey;
    }
}
