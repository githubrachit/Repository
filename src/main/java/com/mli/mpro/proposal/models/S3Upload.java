package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class S3Upload {

    @JsonProperty("channel")
    private String channel;
    @Sensitive(MaskType.POLICY_NUM)
    @JsonProperty("proposalNumber")
    private String proposalNumber;
    @JsonProperty("documnetType")
    private String documnetType;
    @JsonProperty("fileName")
    private String fileName;
    @JsonProperty("actualDocument")
    private String actualDocument;

    public String getChannel() {
	return channel;
    }

    public void setChannel(String channel) {
	this.channel = channel;
    }

    public String getProposalNumber() {
	return proposalNumber;
    }

    public void setProposalNumber(String proposalNumber) {
	this.proposalNumber = proposalNumber;
    }

    public String getDocumnetType() {
	return documnetType;
    }

    public void setDocumnetType(String documnetType) {
	this.documnetType = documnetType;
    }

    public String getFileName() {
	return fileName;
    }

    public void setFileName(String fileName) {
	this.fileName = fileName;
    }

    public String getActualDocument() {
	return actualDocument;
    }

    public void setActualDocument(String actualDocument) {
	this.actualDocument = actualDocument;
    }

    public S3Upload() {

    }

    public S3Upload(String channel, String proposalNumber, String documnetType, String fileName, String actualDocument) {
	super();
	this.channel = channel;
	this.proposalNumber = proposalNumber;
	this.documnetType = documnetType;
	this.fileName = fileName;
	this.actualDocument = actualDocument;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "S3Upload{" +
                "channel='" + channel + '\'' +
                ", proposalNumber='" + proposalNumber + '\'' +
                ", documnetType='" + documnetType + '\'' +
                ", fileName='" + fileName + '\'' +
                ", actualDocument='" + actualDocument + '\'' +
                '}';
    }
}
