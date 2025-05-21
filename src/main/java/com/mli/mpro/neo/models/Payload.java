package com.mli.mpro.neo.models;

import com.mli.mpro.utils.Utility;

import java.util.StringJoiner;

/**
 * The type Payload.
 */
public class Payload {

    private String equoteNumber;
    private String leadId;
    private String docType;
    private String fileFormat;
    private String atchmnt;
    private String date;
    private String validationType;

    /**
     * Gets equote number.
     *
     * @return the equote number
     */
    public String getEquoteNumber() {
        return equoteNumber;
    }

    /**
     * Sets equote number.
     *
     * @param equoteNumber the equote number
     */
    public void setEquoteNumber(String equoteNumber) {
        this.equoteNumber = equoteNumber;
    }

    /**
     * With equote number payload.
     *
     * @param equoteNumber the equote number
     * @return the payload
     */
    public Payload withEquoteNumber(String equoteNumber) {
        this.equoteNumber = equoteNumber;
        return this;
    }

    /**
     * Gets lead id.
     *
     * @return the lead id
     */
    public String getLeadId() {
        return leadId;
    }

    /**
     * Sets lead id.
     *
     * @param leadId the lead id
     */
    public void setLeadId(String leadId) {
        this.leadId = leadId;
    }

    /**
     * With lead id payload.
     *
     * @param leadId the lead id
     * @return the payload
     */
    public Payload withLeadId(String leadId) {
        this.leadId = leadId;
        return this;
    }

    /**
     * Gets doc type.
     *
     * @return the doc type
     */
    public String getDocType() {
        return docType;
    }

    /**
     * Sets doc type.
     *
     * @param docType the doc type
     */
    public void setDocType(String docType) {
        this.docType = docType;
    }

    /**
     * With doc type payload.
     *
     * @param docType the doc type
     * @return the payload
     */
    public Payload withDocType(String docType) {
        this.docType = docType;
        return this;
    }

    /**
     * Gets file format.
     *
     * @return the file format
     */
    public String getFileFormat() {
        return fileFormat;
    }

    /**
     * Sets file format.
     *
     * @param fileFormat the file format
     */
    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    /**
     * With file format payload.
     *
     * @param fileFormat the file format
     * @return the payload
     */
    public Payload withFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
        return this;
    }

    /**
     * Gets atchmnt.
     *
     * @return the atchmnt
     */
    public String getAtchmnt() {
        return atchmnt;
    }

    /**
     * Sets atchmnt.
     *
     * @param atchmnt the atchmnt
     */
    public void setAtchmnt(String atchmnt) {
        this.atchmnt = atchmnt;
    }

    /**
     * With atchmnt payload.
     *
     * @param atchmnt the atchmnt
     * @return the payload
     */
    public Payload withAtchmnt(String atchmnt) {
        this.atchmnt = atchmnt;
        return this;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * With date payload.
     *
     * @param date the date
     * @return the payload
     */
    public Payload withDate(String date) {
        this.date = date;
        return this;
    }

    /**
     * Gets validation type.
     *
     * @return the validation type
     */
    public String getValidationType() {
        return validationType;
    }

    /**
     * Sets validation type.
     *
     * @param validationType the validation type
     */
    public void setValidationType(String validationType) {
        this.validationType = validationType;
    }

    /**
     * With validation type payload.
     *
     * @param validationType the validation type
     * @return the payload
     */
    public Payload withValidationType(String validationType) {
        this.validationType = validationType;
        return this;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
           return Utility.toString(this);
        }
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
                .add("atchmnt = " + atchmnt)
                .add("date = " + date)
                .add("docType = " + docType)
                .add("equoteNumber = " + equoteNumber)
                .add("fileFormat = " + fileFormat)
                .add("validationType = " + validationType)
                .toString();
    }
}
