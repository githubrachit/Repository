package com.mli.mpro.onboarding.model;

import java.util.List;

public class ResponsePayload {

    private List<CurrentStatus> currentStatus;

    public ResponsePayload(List<CurrentStatus> currentStatus) {
        this.currentStatus = currentStatus;
    }

    public ResponsePayload() {
        
    }

    public List<CurrentStatus> getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(List<CurrentStatus> currentStatus) {
        this.currentStatus = currentStatus;
    }

    @Override
    public String toString() {
        return "ResponsePayload [currentStatus=" + currentStatus + "]";
    }
  
}
