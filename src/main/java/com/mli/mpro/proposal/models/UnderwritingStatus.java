package com.mli.mpro.proposal.models;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.List;
import java.util.stream.Collectors;

public class UnderwritingStatus {

    private String medicalGridStatus;
    private String financialGridStatus;
    @Sensitive(MaskType.MASK_ALL)
    private List<DocumentDetails> requiredDocuments;

    public UnderwritingStatus()
    {}
    public UnderwritingStatus(UnderwritingStatus underwritingStatus) {
	if (underwritingStatus != null) {

	    List<DocumentDetails> requiredDocumentsList = underwritingStatus.requiredDocuments;
	    requiredDocumentsList = (requiredDocumentsList != null && requiredDocumentsList.size() != 0)
		    ? requiredDocumentsList.stream().collect(Collectors.toList())
		    : requiredDocumentsList;
	    this.requiredDocuments = requiredDocumentsList;

	}

    }

    /**
     * @return the financialGridStatus
     */
    public String getFinancialGridStatus() {
	return financialGridStatus;
    }

    /**
     * @param financialGridStatus the financialGridStatus to set
     */
    public void setFinancialGridStatus(String financialGridStatus) {
	this.financialGridStatus = financialGridStatus;
    }

    /**
     * @return the medicalGridStatus
     */
    public String getMedicalGridStatus() {
	return medicalGridStatus;
    }

    /**
     * @param medicalGridStatus the medicalGridStatus to set
     */
    public void setMedicalGridStatus(String medicalGridStatus) {
	this.medicalGridStatus = medicalGridStatus;
    }

    /**
     * @return the requiredDocuments
     */
    public List<DocumentDetails> getRequiredDocuments() {
	return requiredDocuments;
    }

    /**
     * @param requiredDocuments the requiredDocuments to set
     */
    public void setRequiredDocuments(List<DocumentDetails> requiredDocuments) {
	this.requiredDocuments = requiredDocuments;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "UnderwritingStatus [medicalGridStatus=" + medicalGridStatus + ", financialGridStatus=" + financialGridStatus + ", requiredDocuments="
		+ requiredDocuments + "]";
    }

}
