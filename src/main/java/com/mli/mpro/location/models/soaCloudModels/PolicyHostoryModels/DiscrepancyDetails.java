package com.mli.mpro.location.models.soaCloudModels.PolicyHostoryModels;

import java.util.ArrayList;
import java.util.List;

public class DiscrepancyDetails {

    public String isQcDiscrepancyMarked;
    public String isUwDiscrepancyMarked;
    public String isQcDiscrepancy;
    public String isUwDiscrepancy;
    public List<RequirementReasons> requirementReasons;

    public String getIsQcDiscrepancyMarked() {
        return isQcDiscrepancyMarked;
    }

    public void setIsQcDiscrepancyMarked(String isQcDiscrepancyMarked) {
        this.isQcDiscrepancyMarked = isQcDiscrepancyMarked;
    }

    public String getIsUwDiscrepancyMarked() {
        return isUwDiscrepancyMarked;
    }

    public void setIsUwDiscrepancyMarked(String isUwDiscrepancyMarked) {
        this.isUwDiscrepancyMarked = isUwDiscrepancyMarked;
    }

    public String getIsQcDiscrepancy() {
        return isQcDiscrepancy;
    }

    public void setIsQcDiscrepancy(String isQcDiscrepancy) {
        this.isQcDiscrepancy = isQcDiscrepancy;
    }

    public String getIsUwDiscrepancy() {
        return isUwDiscrepancy;
    }

    public void setIsUwDiscrepancy(String isUwDiscrepancy) {
        this.isUwDiscrepancy = isUwDiscrepancy;
    }

    public List<RequirementReasons> getDiscrepancyReasons() {
        return requirementReasons;
    }

    public void setDiscrepancyReasons(List<RequirementReasons> requirementReasons) {
        this.requirementReasons = requirementReasons;
    }

    @Override
    public String toString() {
        return "DiscrepancyDetails{" +
                "isQcDiscrepancyMarked='" + isQcDiscrepancyMarked + '\'' +
                ", isUwDiscrepancyMarked='" + isUwDiscrepancyMarked + '\'' +
                ", isQcDiscrepancy='" + isQcDiscrepancy + '\'' +
                ", isUwDiscrepancy='" + isUwDiscrepancy + '\'' +
                ", requirementReasons=" + requirementReasons +
                '}';
    }
}
