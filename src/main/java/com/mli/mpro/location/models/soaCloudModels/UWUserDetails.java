package com.mli.mpro.location.models.soaCloudModels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UWUserDetails {
    @JsonProperty("uWUser")
    private String uWUser;

    public String getuWUser() {
        return uWUser;
    }

    public void setuWUser(String uWUser) {
        this.uWUser = uWUser;
    }

    @Override
    public String toString() {
        return "UWUserDetails{" +
                "uWUser='" + uWUser + '\'' +
                '}';
    }
}
