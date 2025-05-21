package com.mli.mpro.pasa;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class Payload {
    @Sensitive(MaskType.PAN_NUM)
    @JsonProperty("panNumber")
    private String panNumber;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("phoneNumber")
    private String phoneNumber;
    @Sensitive(MaskType.EMAIL)
    @JsonProperty("email")
    private String email;

    @JsonProperty("dbMatchType")
    private String dbMatchType;

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDbMatchType() {
        return dbMatchType;
    }

    public void setDbMatchType(String dbMatchType) {
        this.dbMatchType = dbMatchType;
    }

    public Payload(String panNumber, String phoneNumber, String email, String dbMatchType) {
        this.panNumber = panNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.dbMatchType = dbMatchType;
    }

    public Payload() {
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Payload{" +
                "panNumber='" + panNumber + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", dbMatchType='" + dbMatchType + '\'' +
                '}';
    }
}
