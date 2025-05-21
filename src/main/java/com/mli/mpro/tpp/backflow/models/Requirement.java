package com.mli.mpro.tpp.backflow.models;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.tpp.backflow.models.Comment;
import com.mli.mpro.utils.Utility;

/**
 * @author ravishankar
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "tppTileId", "tppRequirementID", "tppRequirementOrderDate", "tppRequirementUserId", "tppRequirementStatus", "mAppRequirementId",
	"mAppRequirementOrderDate", "requirementStatus", "comments" })
public class Requirement {

    @JsonProperty("tppTileId")
    private String tppTileId;
    @JsonProperty("tppRequirementID")
    private String tppRequirementID;
    @JsonProperty("tppRequirementOrderDate")
    private String tppRequirementOrderDate;
    @JsonProperty("tppRequirementUserId")
    private String tppRequirementUserId;
    @JsonProperty("tppRequirementStatus")
    private String tppRequirementStatus;
    @JsonProperty("mAppRequirementId")
    private String mAppRequirementId;
    @JsonProperty("mAppRequirementOrderDate")
    private String mAppRequirementOrderDate;
    @JsonProperty("requirementStatus")
    private String requirementStatus;
    @JsonProperty("comments")
    private List<Comment> comments = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Requirement() {
    }

    /**
     * 
     * @param tppRequirementID
     * @param tppRequirementOrderDate
     * @param mAppRequirementOrderDate
     * @param tppTileId
     * @param requirementStatus
     * @param tppRequirementStatus
     * @param mAppRequirementId
     * @param comments
     * @param tppRequirementUserId
     */
    public Requirement(String tppTileId, String tppRequirementID, String tppRequirementOrderDate, String tppRequirementUserId, String tppRequirementStatus,
	    String mAppRequirementId, String mAppRequirementOrderDate, String requirementStatus, List<Comment> comments) {
	super();
	this.tppTileId = tppTileId;
	this.tppRequirementID = tppRequirementID;
	this.tppRequirementOrderDate = tppRequirementOrderDate;
	this.tppRequirementUserId = tppRequirementUserId;
	this.tppRequirementStatus = tppRequirementStatus;
	this.mAppRequirementId = mAppRequirementId;
	this.mAppRequirementOrderDate = mAppRequirementOrderDate;
	this.requirementStatus = requirementStatus;
	this.comments = comments;
    }

    @JsonProperty("tppTileId")
    public String getTppTileId() {
	return tppTileId;
    }

    @JsonProperty("tppTileId")
    public void setTppTileId(String tppTileId) {
	this.tppTileId = tppTileId;
    }

    @JsonProperty("tppRequirementID")
    public String getTppRequirementID() {
	return tppRequirementID;
    }

    @JsonProperty("tppRequirementID")
    public void setTppRequirementID(String tppRequirementID) {
	this.tppRequirementID = tppRequirementID;
    }

    @JsonProperty("tppRequirementOrderDate")
    public String getTppRequirementOrderDate() {
	return tppRequirementOrderDate;
    }

    @JsonProperty("tppRequirementOrderDate")
    public void setTppRequirementOrderDate(String tppRequirementOrderDate) {
	this.tppRequirementOrderDate = tppRequirementOrderDate;
    }

    @JsonProperty("tppRequirementUserId")
    public String getTppRequirementUserId() {
	return tppRequirementUserId;
    }

    @JsonProperty("tppRequirementUserId")
    public void setTppRequirementUserId(String tppRequirementUserId) {
	this.tppRequirementUserId = tppRequirementUserId;
    }

    @JsonProperty("tppRequirementStatus")
    public String getTppRequirementStatus() {
	return tppRequirementStatus;
    }

    @JsonProperty("tppRequirementStatus")
    public void setTppRequirementStatus(String tppRequirementStatus) {
	this.tppRequirementStatus = tppRequirementStatus;
    }

    @JsonProperty("mAppRequirementId")
    public String getMAppRequirementId() {
	return mAppRequirementId;
    }

    @JsonProperty("mAppRequirementId")
    public void setMAppRequirementId(String mAppRequirementId) {
	this.mAppRequirementId = mAppRequirementId;
    }

    @JsonProperty("mAppRequirementOrderDate")
    public String getMAppRequirementOrderDate() {
	return mAppRequirementOrderDate;
    }

    @JsonProperty("mAppRequirementOrderDate")
    public void setMAppRequirementOrderDate(String mAppRequirementOrderDate) {
	this.mAppRequirementOrderDate = mAppRequirementOrderDate;
    }

    @JsonProperty("requirementStatus")
    public String getRequirementStatus() {
	return requirementStatus;
    }

    @JsonProperty("requirementStatus")
    public void setRequirementStatus(String requirementStatus) {
	this.requirementStatus = requirementStatus;
    }

    @JsonProperty("comments")
    public List<Comment> getComments() {
	return comments;
    }

    @JsonProperty("comments")
    public void setComments(List<Comment> comments) {
	this.comments = comments;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
           return Utility.toString(this);
        }
	return "Requirement [tppTileId=" + tppTileId + ", tppRequirementID=" + tppRequirementID + ", tppRequirementOrderDate=" + tppRequirementOrderDate
		+ ", tppRequirementUserId=" + tppRequirementUserId + ", tppRequirementStatus=" + tppRequirementStatus + ", mAppRequirementId="
		+ mAppRequirementId + ", mAppRequirementOrderDate=" + mAppRequirementOrderDate + ", requirementStatus=" + requirementStatus + ", comments="
		+ comments + "]";
    }
}