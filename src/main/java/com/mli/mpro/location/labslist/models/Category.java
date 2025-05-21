package com.mli.mpro.location.labslist.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class Category {
	
    private String categoryName;
    @JsonProperty("RequestType")
    private String requestType;
    private String vendor;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Category{" +
                "categoryName='" + categoryName + '\'' +
                ", requestType='" + requestType + '\'' +
                ", vendor='" + vendor + '\'' +
                '}';
    }
}
