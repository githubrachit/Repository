package com.mli.mpro.location.models.soaCloudModels.PolicyHostoryModels;

import java.util.ArrayList;

public class CancellationDetails {

    public String isCancelled;
    public ArrayList<Object> cancellationReason;

    public String getIsCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(String isCancelled) {
        this.isCancelled = isCancelled;
    }

    public ArrayList<Object> getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(ArrayList<Object> cancellationReason) {
        this.cancellationReason = cancellationReason;
    }
}
