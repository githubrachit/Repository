package com.mli.mpro.location.altfinInquiry.auth.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class AuthPayload {
    @Sensitive(MaskType.NAME)
    @JsonProperty("username")
    private String username;
    @Sensitive(MaskType.MASK_ALL)
    @JsonProperty("password")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "AuthPayload{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
