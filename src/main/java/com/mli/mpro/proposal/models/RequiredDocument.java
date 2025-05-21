package com.mli.mpro.proposal.models;

import com.mli.mpro.utils.Utility;

public class RequiredDocument {

    private String tileId;
    private String requirementId;
    private String omnidocsIni;
    private String documentId;
    private String documentName;
    private String documentType;
    private boolean mproDocumentStatus;
    private boolean policyProcessingStatus = false;
    private String documentRepositoryFilePath;
    private String type;

    /**
     * @return the type
     */
    public String getType() {
	return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
	this.type = type;
    }

    /**
     * @return the tileId
     */
    public String getTileId() {
	return tileId;
    }

    /**
     * @param tileId the tileId to set
     */
    public void setTileId(String tileId) {
	this.tileId = tileId;
    }

    /**
     * @return the requirmementId
     */
    public String getRequirementId() {
	return requirementId;
    }

    /**
     */
    public void setRequirementId(String requirementId) {
	this.requirementId = requirementId;
    }

    /**
     * @return the omnidocsIni
     */
    public String getOmnidocsIni() {
	return omnidocsIni;
    }

    /**
     * @param omnidocsIni the omnidocsIni to set
     */
    public void setOmnidocsIni(String omnidocsIni) {
	this.omnidocsIni = omnidocsIni;
    }

    /**
     * @return the documentId
     */
    public String getDocumentId() {
	return documentId;
    }

    /**
     * @param documentId the documentId to set
     */
    public void setDocumentId(String documentId) {
	this.documentId = documentId;
    }

    /**
     * @return the documentName
     */
    public String getDocumentName() {
	return documentName;
    }

    /**
     * @param documentName the documentName to set
     */
    public void setDocumentName(String documentName) {
	this.documentName = documentName;
    }

    /**
     * @return the documentType
     */
    public String getDocumentType() {
	return documentType;
    }

    /**
     * @param documentType the documentType to set
     */
    public void setDocumentType(String documentType) {
	this.documentType = documentType;
    }

    /**
     * @return the mproDocumentStatus
     */
    public boolean isMproDocumentStatus() {
	return mproDocumentStatus;
    }

    /**
     * @param mproDocumentStatus the mproDocumentStatus to set
     */
    public void setMproDocumentStatus(boolean mproDocumentStatus) {
	this.mproDocumentStatus = mproDocumentStatus;
    }

    /**
     * @return the policyProcessingStatus
     */
    public boolean isPolicyProcessingStatus() {
	return policyProcessingStatus;
    }

    /**
     * @param policyProcessingStatus the policyProcessingStatus to set
     */
    public void setPolicyProcessingStatus(boolean policyProcessingStatus) {
	this.policyProcessingStatus = policyProcessingStatus;
    }

    /**
     * @return the documentRepositoryFilePath
     */
    public String getDocumentRepositoryFilePath() {
	return documentRepositoryFilePath;
    }

    /**
     * @param documentRepositoryFilePath the documentRepositoryFilePath to set
     */
    public void setDocumentRepositoryFilePath(String documentRepositoryFilePath) {
	this.documentRepositoryFilePath = documentRepositoryFilePath;
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
	return "RequiredDocument [tileId=" + tileId + ", requirmementId=" + requirementId + ", omnidocsIni=" + omnidocsIni + ", documentId=" + documentId
		+ ", documentName=" + documentName + ", documentType=" + documentType + ", mproDocumentStatus=" + mproDocumentStatus
		+ ", policyProcessingStatus=" + policyProcessingStatus + ", documentRepositoryFilePath=" + documentRepositoryFilePath + ", type=" + type + "]";
    }

}
