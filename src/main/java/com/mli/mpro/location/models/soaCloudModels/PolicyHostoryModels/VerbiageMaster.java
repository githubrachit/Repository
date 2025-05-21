package com.mli.mpro.location.models.soaCloudModels.PolicyHostoryModels;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "master_verbiage_data")
public class VerbiageMaster {

    private String requirementId;
    private String reason;
    private String subReason;

    public String getRequirementId() {
        return requirementId;
    }

    public void setRequirementId(String requirementId) {
        this.requirementId = requirementId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSubReason() {
        return subReason;
    }

    public void setSubReason(String subReason) {
        this.subReason = subReason;
    }
}
