package com.mli.mpro.configuration.models;

public class ReplacementSaleDetails {
    private String replacementSalesStatus;
    private boolean isReplacementSales;

    public String getReplacementSalesStatus() {
        return replacementSalesStatus;
    }

    public void setReplacementSalesStatus(String replacementSalesStatus) {
        this.replacementSalesStatus = replacementSalesStatus;
    }

    public boolean isReplacementSales() {
        return isReplacementSales;
    }

    public void setReplacementSales(boolean replacementSales) {
        isReplacementSales = replacementSales;
    }

    @Override
    public String toString() {
        return "ReplacementSaleDetails{" +
                "replacementSalesStatus='" + replacementSalesStatus + '\'' +
                ", isReplacementSales=" + isReplacementSales +
                '}';
    }
}
