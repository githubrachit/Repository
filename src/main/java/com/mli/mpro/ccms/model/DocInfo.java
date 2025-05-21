package com.mli.mpro.ccms.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;

import static com.mli.mpro.productRestriction.util.AppConstants.LOG_TYPE_LIST;

public class DocInfo{
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public String docId;
    public String productType;
    public String docVersion;
    public String date;

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getDocVersion() {
        return docVersion;
    }

    public void setDocVersion(String docVersion) {
        this.docVersion = docVersion;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
           if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "DocInfo{" +
                "docId='" + docId + '\'' +
                ", productType='" + productType + '\'' +
                ", docVersion='" + docVersion + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}