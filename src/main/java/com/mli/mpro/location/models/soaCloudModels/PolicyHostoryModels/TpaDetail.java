package com.mli.mpro.location.models.soaCloudModels.PolicyHostoryModels;

import java.util.List;

public class TpaDetail {
    public String tpaName;
    public List<StatusUpdate> statusUpdate;

    public String getTpaName() {
        return tpaName;
    }

    public void setTpaName(String tpaName) {
        this.tpaName = tpaName;
    }

    public List<StatusUpdate> getStatusUpdate() {
        return statusUpdate;
    }

    public void setStatusUpdate(List<StatusUpdate> statusUpdate) {
        this.statusUpdate = statusUpdate;
    }

    @Override
    public String toString() {
        return "TpaDetail{" +
                "tpaName='" + tpaName + '\'' +
                ", statusUpdate=" + statusUpdate +
                '}';
    }
}
