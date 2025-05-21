package com.mli.mpro.agent.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Payload{
    @JsonProperty("agtBsDtlType") 
    public AgtBsDtlType getAgtBsDtlType() { 
		 return this.agtBsDtlType; } 
    public void setAgtBsDtlType(AgtBsDtlType agtBsDtlType) { 
		 this.agtBsDtlType = agtBsDtlType; } 
    AgtBsDtlType agtBsDtlType;
    @JsonProperty("agtCommDtlType") 
    public ArrayList<AgtCommDtlType> getAgtCommDtlType() {
		 return this.agtCommDtlType; } 
    public void setAgtCommDtlType(ArrayList<AgtCommDtlType> agtCommDtlType) { 
		 this.agtCommDtlType = agtCommDtlType; } 
    ArrayList<AgtCommDtlType> agtCommDtlType;
    @JsonProperty("agtrefDtlType") 
    public ArrayList<AgtrefDtlType> getAgtrefDtlType() { 
		 return this.agtrefDtlType; } 
    public void setAgtrefDtlType(ArrayList<AgtrefDtlType> agtrefDtlType) { 
		 this.agtrefDtlType = agtrefDtlType; } 
    ArrayList<AgtrefDtlType> agtrefDtlType;
    @JsonProperty("agtMoveDtlType") 
    public ArrayList<AgtMoveDtlType> getAgtMoveDtlType() { 
		 return this.agtMoveDtlType; } 
    public void setAgtMoveDtlType(ArrayList<AgtMoveDtlType> agtMoveDtlType) { 
		 this.agtMoveDtlType = agtMoveDtlType; } 
    ArrayList<AgtMoveDtlType> agtMoveDtlType;
    @JsonProperty("agtContrtDtlType") 
    public AgtContrtDtlType getAgtContrtDtlType() { 
		 return this.agtContrtDtlType; } 
    public void setAgtContrtDtlType(AgtContrtDtlType agtContrtDtlType) { 
		 this.agtContrtDtlType = agtContrtDtlType; } 
    AgtContrtDtlType agtContrtDtlType;
    @JsonProperty("agtPolDtlType") 
    public ArrayList<AgtPolDtlType> getAgtPolDtlType() { 
		 return this.agtPolDtlType; } 
    public void setAgtPolDtlType(ArrayList<AgtPolDtlType> agtPolDtlType) { 
		 this.agtPolDtlType = agtPolDtlType; } 
    ArrayList<AgtPolDtlType> agtPolDtlType;
    @JsonProperty("agtContDtlType") 
    public AgtContDtlType getAgtContDtlType() { 
		 return this.agtContDtlType; } 
    public void setAgtContDtlType(AgtContDtlType agtContDtlType) { 
		 this.agtContDtlType = agtContDtlType; } 
    AgtContDtlType agtContDtlType;
    @JsonProperty("agtAddDtlType") 
    public ArrayList<AgtAddDtlType> getAgtAddDtlType() { 
		 return this.agtAddDtlType; } 
    public void setAgtAddDtlType(ArrayList<AgtAddDtlType> agtAddDtlType) { 
		 this.agtAddDtlType = agtAddDtlType; } 
    ArrayList<AgtAddDtlType> agtAddDtlType;
    @JsonProperty("agtFinDtlType") 
    public ArrayList<AgtFinDtlType> getAgtFinDtlType() { 
		 return this.agtFinDtlType; } 
    public void setAgtFinDtlType(ArrayList<AgtFinDtlType> agtFinDtlType) { 
		 this.agtFinDtlType = agtFinDtlType; } 
    ArrayList<AgtFinDtlType> agtFinDtlType;
    @JsonProperty("subordinateDetails") 
    public ArrayList<Object> getSubordinateDetails() { 
		 return this.subordinateDetails; } 
    public void setSubordinateDetails(ArrayList<Object> subordinateDetails) { 
		 this.subordinateDetails = subordinateDetails; } 
    ArrayList<Object> subordinateDetails;

    ArrayList<Object> agtWRPolDtlType;

    public ArrayList<Object> getAgtWRPolDtlType() {
        return agtWRPolDtlType;
    }

    public Payload setAgtWRPolDtlType(ArrayList<Object> agtWRPolDtlType) {
        this.agtWRPolDtlType = agtWRPolDtlType;
        return this;
    }

    ArrayList<Object> agtSRPolDtlType;

    public ArrayList<Object> getAgtSRPolDtlType() {
        return agtSRPolDtlType;
    }

    public Payload setAgtSRPolDtlType(ArrayList<Object> agtSRPolDtlType) {
        this.agtSRPolDtlType = agtSRPolDtlType;
        return this;
    }
    ArrayList<Object> sPDtlType;

    public ArrayList<Object> getsPDtlType() {
        return sPDtlType;
    }

    public Payload setsPDtlType(ArrayList<Object> sPDtlType) {
        this.sPDtlType = sPDtlType;
        return this;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
           return Utility.toString(this);
        }
        return "Payload{" +
                "agtBsDtlType=" + agtBsDtlType +
                ", agtCommDtlType=" + agtCommDtlType +
                ", agtrefDtlType=" + agtrefDtlType +
                ", agtMoveDtlType=" + agtMoveDtlType +
                ", agtContrtDtlType=" + agtContrtDtlType +
                ", agtPolDtlType=" + agtPolDtlType +
                ", agtContDtlType=" + agtContDtlType +
                ", agtAddDtlType=" + agtAddDtlType +
                ", agtFinDtlType=" + agtFinDtlType +
                ", subordinateDetails=" + subordinateDetails +
                ", agtWRPolDtlType=" + agtWRPolDtlType +
                ", agtSRPolDtlType=" + agtSRPolDtlType +
                ", sPDtlType=" + sPDtlType +
                '}';
    }
}
