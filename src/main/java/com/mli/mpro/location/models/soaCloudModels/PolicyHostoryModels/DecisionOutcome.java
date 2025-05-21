package com.mli.mpro.location.models.soaCloudModels.PolicyHostoryModels;

public class DecisionOutcome {

    public PostponeDetails postponeDetails;
    public DeclineDetails declineDetails;
    public CancellationDetails cancellationDetails;
    public DiscrepancyDetails discrepancyDetails;
    private AddInfoDetails addInfoDetails;
    public CoDetails coDetails;
    public PremiumPayingDetails premiumPayingDetails;
    public DispatchDetails dispatchDetails;
    public Notes notes;

    public PostponeDetails getPostponeDetails() {
        return postponeDetails;
    }

    public void setPostponeDetails(PostponeDetails postponeDetails) {
        this.postponeDetails = postponeDetails;
    }

    public DeclineDetails getDeclineDetails() {
        return declineDetails;
    }

    public void setDeclineDetails(DeclineDetails declineDetails) {
        this.declineDetails = declineDetails;
    }

    public CancellationDetails getCancellationDetails() {
        return cancellationDetails;
    }

    public void setCancellationDetails(CancellationDetails cancellationDetails) {
        this.cancellationDetails = cancellationDetails;
    }

    public DiscrepancyDetails getDiscrepancyDetails() {
        return discrepancyDetails;
    }

    public void setDiscrepancyDetails(DiscrepancyDetails discrepancyDetails) {
        this.discrepancyDetails = discrepancyDetails;
    }

    public AddInfoDetails getAddInfoDetails() {
        return addInfoDetails;
    }

    public void setAddInfoDetails(AddInfoDetails addInfoDetails) {
        this.addInfoDetails = addInfoDetails;
    }

    public CoDetails getCoDetails() {
        return coDetails;
    }

    public void setCoDetails(CoDetails coDetails) {
        this.coDetails = coDetails;
    }

    public PremiumPayingDetails getPremiumPayingDetails() {
        return premiumPayingDetails;
    }

    public void setPremiumPayingDetails(PremiumPayingDetails premiumPayingDetails) {
        this.premiumPayingDetails = premiumPayingDetails;
    }

    public DispatchDetails getDispatchDetails() {
        return dispatchDetails;
    }

    public void setDispatchDetails(DispatchDetails dispatchDetails) {
        this.dispatchDetails = dispatchDetails;
    }

    public Notes getNotes() {
        return notes;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "DecisionOutcome{" +
                "postponeDetails=" + postponeDetails +
                ", declineDetails=" + declineDetails +
                ", cancellationDetails=" + cancellationDetails +
                ", discrepancyDetails=" + discrepancyDetails +
                ", addInfoDetails=" + addInfoDetails +
                ", coDetails=" + coDetails +
                ", premiumPayingDetails=" + premiumPayingDetails +
                ", dispatchDetails=" + dispatchDetails +
                ", notes=" + notes +
                '}';
    }
}
