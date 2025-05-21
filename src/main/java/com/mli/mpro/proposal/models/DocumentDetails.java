package com.mli.mpro.proposal.models;

import java.util.List;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document
public class DocumentDetails {

    @JsonProperty("tileId")
    private String tileId;
    @JsonProperty("requirementId")
    private String requirementId;
    @JsonProperty("requirementName")
    private String requirementName;
    @JsonProperty("omnidocsName")
    private String omnidocsName;
    @JsonProperty("brmsId")
    private String brmsId;
    @JsonProperty("documentName")
    private String documentName;
    @JsonProperty("documentType")
    private String documentType;
    @Sensitive(MaskType.POLICY_NUM)
    @JsonProperty("policyNumber")
    private String policyNumber;
    @JsonProperty("channelName")
    private String channelName;
    @JsonProperty("transactionId")
    private long transactionId;
    @JsonProperty("actualDocument")
    private String actualDocument;
    @JsonProperty("documentRepositoryStatus")
    private String documentRepositoryStatus;
    @JsonProperty("tppStatus")
    private String tppStatus;
    @JsonProperty("mproDocumentId")
    private String mproDocumentId;
    @JsonProperty("omnidocsINI")
    private String omnidocsINI;
    @JsonProperty("s3Filepath")
    private String s3Filepath;
    @JsonProperty("partyType")
    private String partyType;
    @JsonProperty("physicalDocumentType")
    private String physicalDocumentType;
    @JsonProperty("type")
    private String type;
    @JsonProperty("mProDocumentStatus")
    private boolean mProDocumentStatus;
    @JsonProperty("documentRequestInfo")
    private List<DocumentRequestInfo> documentRequestInfo;
    @JsonProperty("numberOfPages")
    private int numberOfPages;
    @JsonProperty("fetchType")
    private String fetchType;
    @JsonProperty("agentId")
    private String agentId;
    @JsonProperty("isRequiredForMproUi")
    private boolean isRequiredForMproUi;
    @JsonProperty("discrepencyTag")
    private String discrepencyTag;
    @JsonProperty("secondaryTransactionId")
    private long secondaryTransactionId;
    private boolean thanosDolphinIntegrationEnabled;

    private String isDocVerified;

    private String sourceChannel;

    public DocumentDetails() {
	super();
    }



    public DocumentDetails(String tileId, String requirementId, String requirementName, String omnidocsName, String brmsId, String documentName,
	    String documentType, String policyNumber, String channelName, long transactionId, String actualDocument, String documentRepositoryStatus,
	    String tppStatus, String mproDocumentId, String omnidocsINI, String s3Filepath, String physicalDocumentType, String type,
	    boolean mProDocumentStatus, List<DocumentRequestInfo> documentRequestInfo, int numberOfPages, String fetchType, String agentId,
	    boolean isRequiredForMproUi, String discrepencyTag, long secondaryTransactionId, String sourceChannel) {
	super();
	this.tileId = tileId;
	this.requirementId = requirementId;
	this.requirementName = requirementName;
	this.omnidocsName = omnidocsName;
	this.brmsId = brmsId;
	this.documentName = documentName;
	this.documentType = documentType;
	this.policyNumber = policyNumber;
	this.channelName = channelName;
	this.transactionId = transactionId;
	this.actualDocument = actualDocument;
	this.documentRepositoryStatus = documentRepositoryStatus;
	this.tppStatus = tppStatus;
	this.mproDocumentId = mproDocumentId;
	this.omnidocsINI = omnidocsINI;
	this.s3Filepath = s3Filepath;
	this.physicalDocumentType = physicalDocumentType;
	this.type = type;
	this.mProDocumentStatus = mProDocumentStatus;
	this.documentRequestInfo = documentRequestInfo;
	this.numberOfPages = numberOfPages;
	this.fetchType = fetchType;
	this.agentId = agentId;
	this.isRequiredForMproUi = isRequiredForMproUi;
	this.discrepencyTag = discrepencyTag;
	this.secondaryTransactionId = secondaryTransactionId;
    this.sourceChannel=sourceChannel;
    }



    public DocumentDetails(String channelName, long transactionId, String mproDocumentId, String documentName, List<DocumentRequestInfo> documentRequestInfo) {

	this.channelName = channelName;
	this.transactionId = transactionId;
	this.mproDocumentId = mproDocumentId;
	this.documentName = documentName;
	this.documentRequestInfo = documentRequestInfo;
    }

    public DocumentDetails(String channelName, String documentType, long transactionId, String mproDocumentId, String documentName,
	    List<DocumentRequestInfo> documentRequestInfo, String physicalDocumentType, String tileId, String requirementId, String omnidocsINI, String type,
	    boolean isRequiredForMproUi) {
	this.channelName = channelName;
	this.documentType = documentType;
	this.transactionId = transactionId;
	this.mproDocumentId = mproDocumentId;
	this.documentName = documentName;
	this.documentRequestInfo = documentRequestInfo;
	this.physicalDocumentType = physicalDocumentType;
	this.tileId = tileId;
	this.requirementId = requirementId;
	this.omnidocsINI = omnidocsINI;
	this.type = type;
	this.isRequiredForMproUi = isRequiredForMproUi;
    }

    public String getTileId() {
	return tileId;
    }

    public void setTileId(String tileId) {
	this.tileId = tileId;
    }

    public String getRequirementId() {
	return requirementId;
    }

    public void setRequirementId(String requirementId) {
	this.requirementId = requirementId;
    }

    public String getRequirementName() {
	return requirementName;
    }

    public void setRequirementName(String requirementName) {
	this.requirementName = requirementName;
    }

    public String getOmnidocsName() {
	return omnidocsName;
    }

    public void setOmnidocsName(String omnidocsName) {
	this.omnidocsName = omnidocsName;
    }

    public String getBrmsId() {
	return brmsId;
    }

    public void setBrmsId(String brmsId) {
	this.brmsId = brmsId;
    }

    public String getDocumentName() {
	return documentName;
    }

    public void setDocumentName(String documentName) {
	this.documentName = documentName;
    }

    public String getDocumentType() {
	return documentType;
    }

    public void setDocumentType(String documentType) {
	this.documentType = documentType;
    }

    public String getPolicyNumber() {
	return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
	this.policyNumber = policyNumber;
    }

    public String getChannelName() {
	return channelName;
    }

    public void setChannelName(String channelName) {
	this.channelName = channelName;
    }

    public long getTransactionId() {
	return transactionId;
    }

    public void setTransactionId(long transactionId) {
	this.transactionId = transactionId;
    }

    public String getActualDocument() {
	return actualDocument;
    }

    public void setActualDocument(String actualDocument) {
	this.actualDocument = actualDocument;
    }

    public String getDocumentRepositoryStatus() {
	return documentRepositoryStatus;
    }

    public void setDocumentRepositoryStatus(String documentRepositoryStatus) {
	this.documentRepositoryStatus = documentRepositoryStatus;
    }

    public String getTppStatus() {
	return tppStatus;
    }

    public void setTppStatus(String tppStatus) {
	this.tppStatus = tppStatus;
    }

    /**
     * @return the mproDocumentId
     */
    public String getMproDocumentId() {
	return mproDocumentId;
    }

    /**
     * @param mproDocumentId the mproDocumentId to set
     */
    public void setMproDocumentId(String mproDocumentId) {
	this.mproDocumentId = mproDocumentId;
    }

    public String getOmnidocsINI() {
	return omnidocsINI;
    }

    public void setOmnidocsINI(String omnidocsINI) {
	this.omnidocsINI = omnidocsINI;
    }

    public String getS3Filepath() {
	return s3Filepath;
    }

    public void setS3Filepath(String s3Filepath) {
	this.s3Filepath = s3Filepath;
    }

    public String getPhysicalDocumentType() {
	return physicalDocumentType;
    }

    public void setPhysicalDocumentType(String physicalDocumentType) {
	this.physicalDocumentType = physicalDocumentType;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public boolean ismProDocumentStatus() {
	return mProDocumentStatus;
    }

    public void setmProDocumentStatus(boolean mProDocumentStatus) {
	this.mProDocumentStatus = mProDocumentStatus;
    }

    public List<DocumentRequestInfo> getDocumentRequestInfo() {
	return documentRequestInfo;
    }

    public void setDocumentRequestInfo(List<DocumentRequestInfo> documentRequestInfo) {
	this.documentRequestInfo = documentRequestInfo;
    }

    public int getNumberOfPages() {
	return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
	this.numberOfPages = numberOfPages;
    }

    public String getFetchType() {
	return fetchType;
    }

    public void setFetchType(String fetchType) {
	this.fetchType = fetchType;
    }

    public String getAgentId() {
	return agentId;
    }

    public void setAgentId(String agentId) {
	this.agentId = agentId;
    }

    public boolean isRequiredForMproUi() {
	return isRequiredForMproUi;
    }

    public void setRequiredForMproUi(boolean isRequiredForMproUi) {
	this.isRequiredForMproUi = isRequiredForMproUi;
    }

    public String getDiscrepencyTag() {
	return discrepencyTag;
    }

    public void setDiscrepencyTag(String discrepencyTag) {
	this.discrepencyTag = discrepencyTag;
    }
    

    public long getSecondaryTransactionId() {
        return secondaryTransactionId;
    }

    public void setSecondaryTransactionId(long secondaryTransactionId) {
        this.secondaryTransactionId = secondaryTransactionId;
    }

  public boolean isThanosDolphinIntegrationEnabled() {
    return thanosDolphinIntegrationEnabled;
  }

  public void setThanosDolphinIntegrationEnabled(boolean thanosDolphinIntegrationEnabled) {
    this.thanosDolphinIntegrationEnabled = thanosDolphinIntegrationEnabled;
  }

    public String getIsDocVerified() {
        return isDocVerified;
    }

    public void setIsDocVerified(String isDocVerified) {
        this.isDocVerified = isDocVerified;
    }

    public String getPartyType() { return partyType; }

    public void setPartyType(String partyType) { this.partyType = partyType; }

    public String getSourceChannel() {
        return sourceChannel;
    }

    public void setSourceChannel(String sourceChannel) {
        this.sourceChannel = sourceChannel;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "DocumentDetails [tileId=" + tileId + ", requirementId=" + requirementId + ", requirementName=" + requirementName + ", omnidocsName="
		+ omnidocsName + ", brmsId=" + brmsId + ", documentName=" + documentName + ", documentType=" + documentType + ", policyNumber=" + policyNumber
		+ ", channelName=" + channelName + ", transactionId=" + transactionId + ", actualDocument=" + actualDocument + ", documentRepositoryStatus="
		+ documentRepositoryStatus + ", tppStatus=" + tppStatus + ", mproDocumentId=" + mproDocumentId + ", omnidocsINI=" + omnidocsINI
		+ ", s3Filepath=" + s3Filepath + ", physicalDocumentType=" + physicalDocumentType + ", type=" + type + ", mProDocumentStatus="
		+ mProDocumentStatus + ", documentRequestInfo=" + documentRequestInfo + ", numberOfPages=" + numberOfPages + ", fetchType=" + fetchType
		+ ", agentId=" + agentId + ", isRequiredForMproUi=" + isRequiredForMproUi + ", discrepencyTag=" + discrepencyTag + ", partyType=" + partyType + ", secondaryTransactionId="
		+ secondaryTransactionId + ", sourceChannel=" + sourceChannel+"]";
    }
}
