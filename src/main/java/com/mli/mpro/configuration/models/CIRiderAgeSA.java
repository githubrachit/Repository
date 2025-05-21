package com.mli.mpro.configuration.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class CIRiderAgeSA {

    @JsonProperty("minAge")
    private int minAge;
    @JsonProperty("maxAge")
    private int maxAge;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("maxSumAssured")
    private double maxSumAssured;

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public double getMaxSumAssured() {
        return maxSumAssured;
    }

    public void setMaxSumAssured(double maxSumAssured) {
        this.maxSumAssured = maxSumAssured;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
           return Utility.toString(this);
        }
        return "CIRiderAgeSA{" +
                "minAge=" + minAge +
                ", maxAge=" + maxAge +
                ", maxSumAssured=" + maxSumAssured +
                '}';
    }
}
