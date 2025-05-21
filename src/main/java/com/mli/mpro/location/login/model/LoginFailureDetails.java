package com.mli.mpro.location.login.model;

import java.io.Serializable;
import java.util.Date;

public class LoginFailureDetails implements Serializable {
    private static final long serialVersionUID = 1L;
    private int invalidLoginFailCount;
    private int remainingCoolingPeriod;
    private Date lastLoginFailureTime;
    private String lastLoginStatus;

    public int getInvalidLoginFailCount() {
        return invalidLoginFailCount;
    }

    public void setInvalidLoginFailCount(int invalidLoginFailCount) {
        this.invalidLoginFailCount = invalidLoginFailCount;
    }

    public Date getLastLoginFailureTime() {
        return lastLoginFailureTime;
    }

    public void setLastLoginFailureTime(Date lastLoginFailureTime) {
        this.lastLoginFailureTime = lastLoginFailureTime;
    }

    public int getRemainingCoolingPeriod() {
        return remainingCoolingPeriod;
    }

    public void setRemainingCoolingPeriod(int remainingCoolingPeriod) {
        this.remainingCoolingPeriod = remainingCoolingPeriod;
    }

    public String getLastLoginStatus() {
        return lastLoginStatus;
    }

    public void setLastLoginStatus(String lastLoginStatus) {
        this.lastLoginStatus = lastLoginStatus;
    }
}
