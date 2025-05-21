package com.mli.mpro.tpp.backflow.models;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

/**
 * @author ravishankar
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "timestamp", "statusCode", "statusMessages" })
public class ResponseStatus {

    @JsonProperty("timestamp")
    private Date timestamp;
    @JsonProperty("statusCode")
    private int statusCode;
    @JsonProperty("statusMessages")
    private List<String> statusMessages = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ResponseStatus() {
    }

    /**
     * 
     * @param statusCode
     * @param timestamp
     * @param statusMessages
     */
    public ResponseStatus(Date timestamp, int statusCode, List<String> statusMessages) {
	super();
	this.timestamp = timestamp;
	this.statusCode = statusCode;
	this.statusMessages = statusMessages;
    }

    @JsonProperty("timestamp")
    public Date getTimestamp() {
	return timestamp;
    }

    @JsonProperty("timestamp")
    public void setTimestamp(Date timestamp) {
	this.timestamp = timestamp;
    }

    @JsonProperty("statusCode")
    public int getStatusCode() {
	return statusCode;
    }

    @JsonProperty("statusCode")
    public void setStatusCode(int statusCode) {
	this.statusCode = statusCode;
    }

    @JsonProperty("statusMessages")
    public List<String> getStatusMessages() {
	return statusMessages;
    }

    @JsonProperty("statusMessages")
    public void setStatusMessages(List<String> statusMessages) {
	this.statusMessages = statusMessages;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "ResponseStatus [timestamp=" + timestamp + ", statusCode=" + statusCode + ", statusMessages=" + statusMessages + "]";
    }

}