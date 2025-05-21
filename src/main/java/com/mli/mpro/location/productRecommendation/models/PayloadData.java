package com.mli.mpro.location.productRecommendation.models;

import com.mli.mpro.utils.Utility;

public class PayloadData {
    private String planName;

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "PayloadData{" +
                "planName='" + planName + '\'' +
                '}';
    }
}
