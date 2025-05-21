package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PosvViaBrmsDetails {

    @JsonProperty("posvBrmsOutput")
    private String posvBrmsOutput;
    
    @JsonProperty("posvException")
    private String posvException;
    
    @JsonProperty("serviceStatus")
    private String serviceStatus;

    //FUL2-190847 Talic Green
    @JsonProperty("greenChannel")
    private String greenChannel;
    
    /**
     * @param posvBrmsOutput
     * @param posvException
     * @param serviceStatus
     */
    public PosvViaBrmsDetails(String posvBrmsOutput, String posvException, String serviceStatus) {
        this.posvBrmsOutput = posvBrmsOutput;
        this.posvException = posvException;
        this.serviceStatus = serviceStatus;
    }
     //FUL2-190847 TALIC green
    public PosvViaBrmsDetails(String posvBrmsOutput, String posvException, String greenChannel, String serviceStatus) {
        this.posvBrmsOutput = posvBrmsOutput;
        this.posvException = posvException;
        this.greenChannel = greenChannel;
        this.serviceStatus = serviceStatus;
    }
    /**
     * 
     */
    public PosvViaBrmsDetails() {

    }



    public String getPosvBrmsOutput() {
        return posvBrmsOutput;
    }

    public void setPosvBrmsOutput(String posvBrmsOutput) {
        this.posvBrmsOutput = posvBrmsOutput;
    }

    public String getPosvException() {
        return posvException;
    }

    public void setPosvException(String posvException) {
        this.posvException = posvException;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }
    public String getGreenChannel() {
        return greenChannel;
    }

    public void setGreenChannel(String greenChannel) {
        this.greenChannel = greenChannel;
    }


    @Override
    public String toString() {
        return "PosvViaBrmsDetails [posvBrmsOutput=" + posvBrmsOutput + ", posvException=" + posvException
                + ", serviceStatus=" + serviceStatus + ", greenChannel=" + greenChannel + "]";
    }
    
    
}
