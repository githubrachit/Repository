package com.mli.mpro.productRestriction.models;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "limitationData")
public class LimitationData {

    @Id
    private String id;
    private String channel;
    private String productId;
    private String education;
    private String occupation;
    @Sensitive(MaskType.ADDRESS)
    private String communicationCity;
    /* FUL2-9472 WLS Product Restriction */
    private String limitationType;
    private Map<String,String> fullyRestrictedLocations;
    private Map<String,String> partialRestrictedLocations;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getCommunicationCity() {
        return communicationCity;
    }

    public void setCommunicationCity(String communicationCity) {
        this.communicationCity = communicationCity;
    }

    public String getLimitationType() {
        return limitationType;
    }

    public void setLimitationType(String limitationType) {
        this.limitationType = limitationType;
    }

    public Map<String, String> getFullyRestrictedLocations() {
        return fullyRestrictedLocations;
    }

    public void setFullyRestrictedLocations(Map<String, String> fullyRestrictedLocations) {
        this.fullyRestrictedLocations = fullyRestrictedLocations;
    }

    public Map<String, String> getPartialRestrictedLocations() {
        return partialRestrictedLocations;
    }

    public void setPartialRestrictedLocations(Map<String, String> partialRestrictedLocations) {
        this.partialRestrictedLocations = partialRestrictedLocations;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "LimitationData{" +
                "id='" + id + '\'' +
                ", channel='" + channel + '\'' +
                ", productId='" + productId + '\'' +
                ", education='" + education + '\'' +
                ", occupation='" + occupation + '\'' +
                ", communicationCity='" + communicationCity + '\'' +
                ", limitationType='" + limitationType + '\'' +
                ", fullyRestrictedLocations=" + fullyRestrictedLocations +
                ", partialRestrictedLocations=" + partialRestrictedLocations +
                '}';
    }
}
