package com.mli.mpro.sellerSignature.sellerResponse;

import com.mli.mpro.utils.Utility;

import java.util.List;

public class Payload {

    private List<RegistrationDetails> registrationDetails;
    private String id;
    private String key1;
    private String key2;
    private String Key3;
    private String type;

    public List<RegistrationDetails> getRegistrationDetails() {
        return registrationDetails;
    }

    public void setRegistrationDetails(List<RegistrationDetails> registrationDetails) {
        this.registrationDetails = registrationDetails;
    }

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
        return Key3;
    }

    public void setKey3(String key3) {
        Key3 = key3;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Payload{" +
                "registrationDetails=" + registrationDetails +
                ", id='" + id + '\'' +
                ", key1='" + key1 + '\'' +
                ", key2='" + key2 + '\'' +
                ", Key3='" + Key3 + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
