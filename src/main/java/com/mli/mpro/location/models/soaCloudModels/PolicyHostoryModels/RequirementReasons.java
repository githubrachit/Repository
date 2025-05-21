package com.mli.mpro.location.models.soaCloudModels.PolicyHostoryModels;

public class RequirementReasons {

    public String requirement;
    public String requirementId;
    public String partiesAssociated;
    private String reason;
    private String subReason;

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getRequirementId() {
        return requirementId;
    }

    public void setRequirementId(String requirementId) {
        this.requirementId = requirementId;
    }

    public String getPartiesAssociated() {
        return partiesAssociated;
    }

    public void setPartiesAssociated(String partiesAssociated) {
        this.partiesAssociated = partiesAssociated;
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

    @Override
    public String toString() {
        return "RequirementReasons{" +
                "requirement='" + requirement + '\'' +
                ", requirementId='" + requirementId + '\'' +
                ", partiesAssociated='" + partiesAssociated + '\'' +
                ", reason='" + reason + '\'' +
                ", subReason='" + subReason + '\'' +
                '}';
    }
}
