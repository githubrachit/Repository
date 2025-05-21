package com.mli.mpro.tpp.backflow.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

/**
 * @author ravishankar
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "message", "commentDate" })
public class Comment {

    @JsonProperty("message")
    private String message;
    @JsonProperty("commentDate")
    private String commentDate;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Comment() {
    }

    /**
     * 
     * @param message
     * @param commentDate
     */
    public Comment(String message, String commentDate) {
	super();
	this.message = message;
	this.commentDate = commentDate;
    }

    @JsonProperty("message")
    public String getMessage() {
	return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
	this.message = message;
    }

    @JsonProperty("commentDate")
    public String getCommentDate() {
	return commentDate;
    }

    @JsonProperty("commentDate")
    public void setCommentDate(String commentDate) {
	this.commentDate = commentDate;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "Comment [message=" + message + ", commentDate=" + commentDate + "]";
    }
}