package com.mli.mpro.bajajCapital.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bajajIPCSellerDetails")
public class BajajIPCSellerDetails {
    @JsonProperty("sellerId")
    private String sellerId;

    @JsonProperty("branchName")
    private String branchName;

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    @Override
    public String toString() {
        return "BajajRetailSellerDetails{" +
                "sellerId='" + sellerId + '\'' +
                ", branchName='" + branchName + '\'' +
                '}';
    }

}
