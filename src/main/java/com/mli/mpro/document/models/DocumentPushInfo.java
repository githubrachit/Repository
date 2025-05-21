package com.mli.mpro.document.models;

import com.mli.mpro.utils.Utility;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "timePeriodInfo")
public class DocumentPushInfo {

    @Id
    String id;

    private int numberOfDays;

    private String reason;

    private int numberOfRecords;

    public DocumentPushInfo() {
    }

    public DocumentPushInfo(String id, int numberOfDays, String reason, int numberOfRecords) {
	super();
	this.id = id;
	this.numberOfDays = numberOfDays;
	this.reason = reason;
	this.numberOfRecords = numberOfRecords;
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public int getNumberOfDays() {
	return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
	this.numberOfDays = numberOfDays;
    }

    public String getReason() {
	return reason;
    }

    public void setReason(String reason) {
	this.reason = reason;
    }

    public int getNumberOfRecords() {
	return numberOfRecords;
    }

    public void setNumberOfRecords(int numberOfRecords) {
	this.numberOfRecords = numberOfRecords;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "DocumentPushInfo [id=" + id + ", numberOfDays=" + numberOfDays + ", reason=" + reason + ", numberOfRecords=" + numberOfRecords + "]";
    }

}
