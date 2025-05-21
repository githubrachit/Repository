package com.mli.mpro.productRestriction.models;

import com.mli.mpro.utils.Utility;

import java.util.List;

public class RequestPayload {

    private String type;
    private List<String> data;
    private String status;
    private boolean shouldUpdateRestrictionData;
    private boolean shouldUpdatedProductRestrictionData;
    private ProductRestrictionPayload productRestrictionPayload;
    private RestrictionData restrictionData;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isShouldUpdateRestrictionData() {
        return shouldUpdateRestrictionData;
    }

    public void setShouldUpdateRestrictionData(boolean shouldUpdateRestrictionData) {
        this.shouldUpdateRestrictionData = shouldUpdateRestrictionData;
    }

    public boolean isShouldUpdatedProductRestrictionData() {
        return shouldUpdatedProductRestrictionData;
    }

    public void setShouldUpdatedProductRestrictionData(boolean shouldUpdatedProductRestrictionData) {
        this.shouldUpdatedProductRestrictionData = shouldUpdatedProductRestrictionData;
    }

    public ProductRestrictionPayload getProductRestrictionPayload() {
        return productRestrictionPayload;
    }

    public void setProductRestrictionPayload(ProductRestrictionPayload productRestrictionPayload) {
        this.productRestrictionPayload = productRestrictionPayload;
    }

    public RestrictionData getRestrictionData() {
        return restrictionData;
    }

    public void setRestrictionData(RestrictionData restrictionData) {
        this.restrictionData = restrictionData;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "RequestPayload{" +
                "type='" + type + '\'' +
                ", data=" + data +
                ", status='" + status + '\'' +
                ", shouldUpdateRestrictionData=" + shouldUpdateRestrictionData +
                ", shouldUpdatedProductRestrictionData=" + shouldUpdatedProductRestrictionData +
                ", productRestrictionPayload=" + productRestrictionPayload +
                ", restrictionData=" + restrictionData +
                '}';
    }
}
