package com.mli.mpro.location.models.soaCloudModels.PolicyHostoryModels;

import java.util.ArrayList;

public class PostponeDetails {

    public String isPolicyPostponed;
    public String postponedReconDt;
    public ArrayList<Object> postponedReason;

    public String getIsPolicyPostponed() {
        return isPolicyPostponed;
    }

    public void setIsPolicyPostponed(String isPolicyPostponed) {
        this.isPolicyPostponed = isPolicyPostponed;
    }

    public String getPostponedReconDt() {
        return postponedReconDt;
    }

    public void setPostponedReconDt(String postponedReconDt) {
        this.postponedReconDt = postponedReconDt;
    }

    public ArrayList<Object> getPostponedReason() {
        return postponedReason;
    }

    public void setPostponedReason(ArrayList<Object> postponedReason) {
        this.postponedReason = postponedReason;
    }
}
