package com.mli.mpro.location.models.soaCloudModels.PolicyHostoryModels;

import java.util.ArrayList;
import java.util.List;

public class AddInfoDetails {

    private String isAddInfo;
    private List<RequirementReasons> pendingRequirements;

    public String getIsAddInfo() {
        return isAddInfo;
    }

    public void setIsAddInfo(String isAddInfo) {
        this.isAddInfo = isAddInfo;
    }

    public List<RequirementReasons> getPendingRequirements() {
        return pendingRequirements;
    }

    public void setPendingRequirements(List<RequirementReasons> pendingRequirements) {
        this.pendingRequirements = pendingRequirements;
    }

    @Override
    public String toString() {
        return "AddInfoDetails{" +
                "isAddInfo='" + isAddInfo + '\'' +
                ", pendingRequirements=" + pendingRequirements +
                '}';
    }
}
