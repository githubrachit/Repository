package com.mli.mpro.common.models;

public class NewReplacementSalePayload {
    private String isReplacementSale;
    private String yblReplacementPolicyStatus;

    public String getIsNewYblReplacementSale() {
        return isReplacementSale;
    }

    public void setReplacementSale(String isReplacementSale) {
        this.isReplacementSale = isReplacementSale;
    }

    public String getYblReplacementPolicyStatus() {
        return yblReplacementPolicyStatus;
    }

    public void setYblReplacementPolicyStatus(String yblReplacementPolicyStatus) {
        this.yblReplacementPolicyStatus = yblReplacementPolicyStatus;
    }

    @Override
    public String toString() {
        return "ReplacementSalePayload{" +
                "isReplacementSale='" + isReplacementSale + '\'' +
                ", yblReplacementPolicyStatus='" + yblReplacementPolicyStatus + '\'' +
                '}';
    }
}
