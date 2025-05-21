package com.mli.mpro.tpp.backflow.models;

import com.mli.mpro.utils.Utility;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "documentType", "tileId", "documentName", "tppRequirementId", "requirementId", "requirementName", "omnidocsINI", "mproDocumentId" })
@Document(collection =  "policyProcessingDocMapping")
public class PolicyProcessingDocMapping {

    @JsonProperty("documentType")
    private String documentType;
    @JsonProperty("tileId")
    private String tileId;
    @JsonProperty("documentName")
    private String documentName;
    @JsonProperty("tppRequirementId")
    private String tppRequirementId;
    @JsonProperty("requirementId")
    private String requirementId;
    @JsonProperty("requirementName")
    private String requirementName;
    @JsonProperty("omnidocsINI")
    private String omnidocsINI;
    @JsonProperty("mproDocumentId")
    private String mproDocumentId;

    @JsonProperty("documentType")
    public String getDocumentType() {
	return documentType;
    }

    @JsonProperty("documentType")
    public void setDocumentType(String documentType) {
	this.documentType = documentType;
    }

    @JsonProperty("tileId")
    public String getTileId() {
	return tileId;
    }

    @JsonProperty("tileId")
    public void setTileId(String tileId) {
	this.tileId = tileId;
    }

    @JsonProperty("documentName")
    public String getDocumentName() {
	return documentName;
    }

    @JsonProperty("documentName")
    public void setDocumentName(String documentName) {
	this.documentName = documentName;
    }

    @JsonProperty("tppRequirementId")
    public String getTppRequirementId() {
	return tppRequirementId;
    }

    @JsonProperty("tppRequirementId")
    public void setTppRequirementId(String tppRequirementId) {
	this.tppRequirementId = tppRequirementId;
    }

    @JsonProperty("requirementId")
    public String getRequirementId() {
	return requirementId;
    }

    @JsonProperty("requirementId")
    public void setRequirementId(String requirementId) {
	this.requirementId = requirementId;
    }

    @JsonProperty("requirementName")
    public String getRequirementName() {
	return requirementName;
    }

    @JsonProperty("requirementName")
    public void setRequirementName(String requirementName) {
	this.requirementName = requirementName;
    }

    @JsonProperty("omnidocsINI")
    public String getOmnidocsINI() {
	return omnidocsINI;
    }

    @JsonProperty("omnidocsINI")
    public void setOmnidocsINI(String omnidocsINI) {
	this.omnidocsINI = omnidocsINI;
    }

    @JsonProperty("mproDocumentId")
    public String getMproDocumentId() {
	return mproDocumentId;
    }

    @JsonProperty("mproDocumentId")
    public void setMproDocumentId(String mproDocumentId) {
	this.mproDocumentId = mproDocumentId;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "PolicyProcessingDocMapping [documentType=" + documentType + ", tileId=" + tileId + ", documentName=" + documentName + ", tppRequirementId="
		+ tppRequirementId + ", requirementId=" + requirementId + ", requirementName=" + requirementName + ", omnidocsINI=" + omnidocsINI
		+ ", mproDocumentId=" + mproDocumentId + "]";
    }

}
