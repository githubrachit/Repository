package com.mli.mpro.agent.models;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class AgtContrtDtlType{
    @JsonProperty("licenceNum") 
    public String getLicenceNum() { 
		 return this.licenceNum; } 
    public void setLicenceNum(String licenceNum) { 
		 this.licenceNum = licenceNum; } 
    String licenceNum;
    @JsonProperty("licenceDt") 
    public String getLicenceDt() { 
		 return this.licenceDt; } 
    public void setLicenceDt(String licenceDt) { 
		 this.licenceDt = licenceDt; } 
    String licenceDt;
    @JsonProperty("licenceExpiryDt") 
    public String getLicenceExpiryDt() { 
		 return this.licenceExpiryDt; } 
    public void setLicenceExpiryDt(String licenceExpiryDt) { 
		 this.licenceExpiryDt = licenceExpiryDt; } 
    String licenceExpiryDt;
    @JsonProperty("dtOfJoining") 
    public String getDtOfJoining() { 
		 return this.dtOfJoining; } 
    public void setDtOfJoining(String dtOfJoining) { 
		 this.dtOfJoining = dtOfJoining; } 
    String dtOfJoining;
    @JsonProperty("terminationDt") 
    public String getTerminationDt() { 
		 return this.terminationDt; } 
    public void setTerminationDt(String terminationDt) { 
		 this.terminationDt = terminationDt; } 
    String terminationDt;
    @JsonProperty("ulipStatus") 
    public String getUlipStatus() { 
		 return this.ulipStatus; } 
    public void setUlipStatus(String ulipStatus) { 
		 this.ulipStatus = ulipStatus; } 
    String ulipStatus;
    @JsonProperty("amlStatus") 
    public String getAmlStatus() { 
		 return this.amlStatus; } 
    public void setAmlStatus(String amlStatus) { 
		 this.amlStatus = amlStatus; } 
    String amlStatus;
    @JsonProperty("trainingCompletionDtAMLPrev") 
    public String getTrainingCompletionDtAMLPrev() { 
		 return this.trainingCompletionDtAMLPrev; } 
    public void setTrainingCompletionDtAMLPrev(String trainingCompletionDtAMLPrev) { 
		 this.trainingCompletionDtAMLPrev = trainingCompletionDtAMLPrev; } 
    String trainingCompletionDtAMLPrev;
    @JsonProperty("trainingCompletionDtAMLCurr") 
    public String getTrainingCompletionDtAMLCurr() { 
		 return this.trainingCompletionDtAMLCurr; } 
    public void setTrainingCompletionDtAMLCurr(String trainingCompletionDtAMLCurr) { 
		 this.trainingCompletionDtAMLCurr = trainingCompletionDtAMLCurr; } 
    String trainingCompletionDtAMLCurr;
    @JsonProperty("trainingCompletionDtULPrev") 
    public String getTrainingCompletionDtULPrev() { 
		 return this.trainingCompletionDtULPrev; } 
    public void setTrainingCompletionDtULPrev(String trainingCompletionDtULPrev) { 
		 this.trainingCompletionDtULPrev = trainingCompletionDtULPrev; } 
    String trainingCompletionDtULPrev;
    @JsonProperty("trainingCompletionDtULCurr") 
    public String getTrainingCompletionDtULCurr() { 
		 return this.trainingCompletionDtULCurr; } 
    public void setTrainingCompletionDtULCurr(String trainingCompletionDtULCurr) { 
		 this.trainingCompletionDtULCurr = trainingCompletionDtULCurr; } 
    String trainingCompletionDtULCurr;
    @JsonProperty("nextTrainingStartDtAML") 
    public String getNextTrainingStartDtAML() { 
		 return this.nextTrainingStartDtAML; } 
    public void setNextTrainingStartDtAML(String nextTrainingStartDtAML) { 
		 this.nextTrainingStartDtAML = nextTrainingStartDtAML; } 
    String nextTrainingStartDtAML;
    @JsonProperty("nextTrainingStartDtUL") 
    public String getNextTrainingStartDtUL() { 
		 return this.nextTrainingStartDtUL; } 
    public void setNextTrainingStartDtUL(String nextTrainingStartDtUL) { 
		 this.nextTrainingStartDtUL = nextTrainingStartDtUL; } 
    String nextTrainingStartDtUL;

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "AgtContrtDtlType{" +
                "licenceNum='" + licenceNum + '\'' +
                ", licenceDt='" + licenceDt + '\'' +
                ", licenceExpiryDt='" + licenceExpiryDt + '\'' +
                ", dtOfJoining='" + dtOfJoining + '\'' +
                ", terminationDt='" + terminationDt + '\'' +
                ", ulipStatus='" + ulipStatus + '\'' +
                ", amlStatus='" + amlStatus + '\'' +
                ", trainingCompletionDtAMLPrev='" + trainingCompletionDtAMLPrev + '\'' +
                ", trainingCompletionDtAMLCurr='" + trainingCompletionDtAMLCurr + '\'' +
                ", trainingCompletionDtULPrev='" + trainingCompletionDtULPrev + '\'' +
                ", trainingCompletionDtULCurr='" + trainingCompletionDtULCurr + '\'' +
                ", nextTrainingStartDtAML='" + nextTrainingStartDtAML + '\'' +
                ", nextTrainingStartDtUL='" + nextTrainingStartDtUL + '\'' +
                '}';
    }
}
