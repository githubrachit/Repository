package com.mli.mpro.proposal.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class PhysicalJourneyDetails {
    @JsonProperty("customerSignDate")
    private String customerSignDate;
    @JsonProperty("customerSignLocation")
    private String customerSignLocation;
    @JsonProperty("sellerSignDate")
    private String sellerSignDate;
    @JsonProperty("sellerSignLocation")
    private String sellerSignLocation;
    @JsonProperty("applicationReceivedDate")
    private String applicationReceivedDate;
    @JsonProperty("illiterateAndVernacularDeclaration")
    private String illiterateAndVernacularDeclaration;
    @JsonProperty("sellerId")
    private String sellerId;
    @JsonProperty("biGeneratedDatePhysical")
    private Date biGeneratedDatePhysical;
    

    /**
     * @param customerSignDate
     * @param customerSignLocation
     * @param sellerSignDate
     * @param sellerSignLocation
     * @param applicationReceivedDate
     * @param illiterateAndVernacularDeclaration
     */
    public PhysicalJourneyDetails(String customerSignDate, String customerSignLocation, String sellerSignDate,
            String sellerSignLocation, String applicationReceivedDate, String illiterateAndVernacularDeclaration
            , String sellerId, Date biGeneratedDatePhysical) {
        this.customerSignDate = customerSignDate;
        this.customerSignLocation = customerSignLocation;
        this.sellerSignDate = sellerSignDate;
        this.sellerSignLocation = sellerSignLocation;
        this.applicationReceivedDate = applicationReceivedDate;
        this.illiterateAndVernacularDeclaration = illiterateAndVernacularDeclaration;
        this.sellerId = sellerId;
        this.biGeneratedDatePhysical = biGeneratedDatePhysical;
    }
    
    public PhysicalJourneyDetails() {
        // Auto-generated constructor stub
    }

    public String getCustomerSignDate() {
        return customerSignDate;
    }

    public void setCustomerSignDate(String customerSignDate) {
        this.customerSignDate = customerSignDate;
    }

    public String getCustomerSignLocation() {
        return customerSignLocation;
    }

    public void setCustomerSignLocation(String customerSignLocation) {
        this.customerSignLocation = customerSignLocation;
    }

    public String getSellerSignDate() {
        return sellerSignDate;
    }

    public void setSellerSignDate(String sellerSignDate) {
        this.sellerSignDate = sellerSignDate;
    }

    public String getSellerSignLocation() {
        return sellerSignLocation;
    }

    public void setSellerSignLocation(String sellerSignLocation) {
        this.sellerSignLocation = sellerSignLocation;
    }

    public String getApplicationReceivedDate() {
        return applicationReceivedDate;
    }

    public void setApplicationReceivedDate(String applicationReceivedDate) {
        this.applicationReceivedDate = applicationReceivedDate;
    }

    public String getIlliterateAndVernacularDeclaration() {
        return illiterateAndVernacularDeclaration;
    }

    public void setIlliterateAndVernacularDeclaration(String illiterateAndVernacularDeclaration) {
        this.illiterateAndVernacularDeclaration = illiterateAndVernacularDeclaration;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public Date getBiGeneratedDatePhysical() {
        return biGeneratedDatePhysical;
    }

    public void setBiGeneratedDatePhysical(Date biGeneratedDatePhysical) {
        this.biGeneratedDatePhysical = biGeneratedDatePhysical;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "PhysicalJourneyDetails [customerSignDate=" + customerSignDate + ", customerSignLocation="
                + customerSignLocation + ", sellerSignDate=" + sellerSignDate + ", sellerSignLocation="
                + sellerSignLocation + ", applicationReceivedDate=" + applicationReceivedDate
                + ", illiterateAndVernacularDeclaration=" + illiterateAndVernacularDeclaration 
                + ", sellerId=" + sellerId + ", biGeneratedDatePhysical=" + biGeneratedDatePhysical +
                "]";
    }

}
