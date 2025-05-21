
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "enrollmentNo" })
public class Enrollment {

    @JsonProperty("enrollmentNo")
    private String enrollmentNo;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Enrollment() {
    }

    /**
     * 
     * @param enrollmentNo
     */
    public Enrollment(String enrollmentNo) {
	super();
	this.enrollmentNo = enrollmentNo;
    }

    @JsonProperty("enrollmentNo")
    public String getEnrollmentNo() {
	return enrollmentNo;
    }

    @JsonProperty("enrollmentNo")
    public void setEnrollmentNo(String enrollmentNo) {
	this.enrollmentNo = enrollmentNo;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
           return Utility.toString(this);
        }
	return "Enrollment [enrollmentNo=" + enrollmentNo + "]";
    }

}
