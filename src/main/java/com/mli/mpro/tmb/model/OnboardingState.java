package com.mli.mpro.tmb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Document(collection = "onboarding-state")
public class OnboardingState {

    @Id
    @JsonProperty("id")
    private String id;
    private long transactionId;
    private String agentId;
    private String customerId;
    private Date linkCreatedTime;
    private Date objectCreatedTime;
    private Map<String,OnboardingEvent> Events;


    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }


    public Date getLinkCreatedTime() {
        return linkCreatedTime;
    }

    public void setLinkCreatedTime(Date linkCreatedTime) {
        this.linkCreatedTime = linkCreatedTime;
    }

    public Date getObjectCreatedTime() {
        return objectCreatedTime;
    }

    public void setObjectCreatedTime(Date objectCreatedTime) {
        this.objectCreatedTime = objectCreatedTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public Map<String, OnboardingEvent> getEvents() {
        return Events;
    }

    public void setEvents(Map<String, OnboardingEvent> events) {
        Events = events;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "OnboardingState{" +
                "id='" + id + '\'' +
                ", transactionId=" + transactionId +
                ", customerId='" + customerId + '\'' +
                ", agentId='" + agentId + '\'' +
                ", Events=" + Events +
                '}';
    }
}
