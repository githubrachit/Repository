package com.mli.mpro.location.models.soaCloudModels.PolicyHostoryModels;

import java.util.ArrayList;

public class DeclineDetails {

    public String isDecline;
    public ArrayList<Object> declineReason;

    public String getIsDecline() {
        return isDecline;
    }

    public void setIsDecline(String isDecline) {
        this.isDecline = isDecline;
    }

    public ArrayList<Object> getDeclineReason() {
        return declineReason;
    }

    public void setDeclineReason(ArrayList<Object> declineReason) {
        this.declineReason = declineReason;
    }
}
