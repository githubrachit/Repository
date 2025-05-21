package com.mli.mpro.document.models;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class ProposalRiderDetails {

    private String riderName;
    private String coverageTerm;
    @Sensitive(MaskType.AMOUNT)
    private String modalPremium;
    @Sensitive(MaskType.AMOUNT)
    private String riderSumAssured;
    private String riderGST;
    private boolean isRiderRequired;
    private String premiumBackOption;
    private String premiumPayingTerm;

    public String getRiderName() {
	return riderName;
    }

    public void setRiderName(String riderName) {
	this.riderName = riderName;
    }

    public String getCoverageTerm() {
	return coverageTerm;
    }

    public void setCoverageTerm(String coverageTerm) {
	this.coverageTerm = coverageTerm;
    }

    public String getModalPremium() {
	return modalPremium;
    }

    public void setModalPremium(String modalPremium) {
	this.modalPremium = modalPremium;
    }

    public String getRiderSumAssured() {
	return riderSumAssured;
    }

    public void setRiderSumAssured(String riderSumAssured) {
	this.riderSumAssured = riderSumAssured;
    }

    public String getRiderGST() {
	return riderGST;
    }

    public void setRiderGST(String riderGST) {
	this.riderGST = riderGST;
    }

    public boolean isRiderRequired() {
        return isRiderRequired;
    }

    public void setRiderRequired(boolean isRiderRequired) {
        this.isRiderRequired = isRiderRequired;
    }
    
    public String getPremiumBackOption() {
		return premiumBackOption;
	}

	public void setPremiumBackOption(String premiumBackOption) {
		this.premiumBackOption = premiumBackOption;
	}

	public String getPremiumPayingTerm() {
		return premiumPayingTerm;
	}

	public void setPremiumPayingTerm(String premiumPayingTerm) {
		this.premiumPayingTerm = premiumPayingTerm;
	}

	@Override
	public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
		return "ProposalRiderDetails [riderName=" + riderName + ", coverageTerm=" + coverageTerm + ", modalPremium="
				+ modalPremium + ", riderSumAssured=" + riderSumAssured + ", riderGST=" + riderGST
				+ ", isRiderRequired=" + isRiderRequired + ", premiumBackOption=" + premiumBackOption
				+ ", premiumPayingTerm=" + premiumPayingTerm + "]";
	}

    public ProposalRiderDetails() {
    }

    public ProposalRiderDetails(String riderName, String coverageTerm, String modalPremium, String riderSumAssured, String riderGST, boolean isRiderRequired, String premiumBackOption, String premiumPayingTerm) {
        this.riderName = riderName;
        this.coverageTerm = coverageTerm;
        this.modalPremium = modalPremium;
        this.riderSumAssured = riderSumAssured;
        this.riderGST = riderGST;
        this.isRiderRequired = isRiderRequired;
        this.premiumBackOption = premiumBackOption;
        this.premiumPayingTerm = premiumPayingTerm;
    }
}
