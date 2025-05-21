package com.mli.mpro.configuration.models;

import com.mli.mpro.utils.Utility;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "leProductList")
public class LEProductList {
    private String productId;
    private String productName;
    private Boolean enableLeFromNode;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Boolean getEnableLeFromNode() {
        return enableLeFromNode;
    }

    public void setEnableLeFromNode(Boolean enableLeFromNode) {
        this.enableLeFromNode = enableLeFromNode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "LEProductList{" +
                "productId='" + productId + '\'' +
                "productName='" + productName + '\'' +
                ", enableFromJava=" + enableLeFromNode +
                '}';
    }
}
