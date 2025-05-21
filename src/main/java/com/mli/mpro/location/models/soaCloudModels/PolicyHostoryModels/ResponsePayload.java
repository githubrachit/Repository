package com.mli.mpro.location.models.soaCloudModels.PolicyHostoryModels;

import java.util.ArrayList;
import java.util.List;

public class ResponsePayload {

    public String lastUpdatedDateTime;
    public String customerName;
    public String insuredName;
    public String nomineeName;
    public String agentName;
    public String payorName;
    public String basePlan;
    public String intrimTag;
    public String source;
    public String createdOn;
    public String goGreen;
    public RollBackDetails rollbackDetails;
    public List<PolicyMovementDetailsSeller> policyMovementDetailsSeller;
    public List<PolicyMovementDetailsCustomer> policyMovementDetailsCustomer;
    public DecisionOutcome decisionOutcome;

    private String policyNumber;
    private List<TpaDetail> tpaDetails;
    public String getLastUpdatedDateTime() {
        return lastUpdatedDateTime;
    }

    public void setLastUpdatedDateTime(String lastUpdatedDateTime) {
        this.lastUpdatedDateTime = lastUpdatedDateTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getInsuredName() {
        return insuredName;
    }

    public void setInsuredName(String insuredName) {
        this.insuredName = insuredName;
    }

    public String getNomineeName() {
        return nomineeName;
    }

    public void setNomineeName(String nomineeName) {
        this.nomineeName = nomineeName;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getPayorName() {
        return payorName;
    }

    public void setPayorName(String payorName) {
        this.payorName = payorName;
    }

    public String getBasePlan() {
        return basePlan;
    }

    public void setBasePlan(String basePlan) {
        this.basePlan = basePlan;
    }

    public String getIntrimTag() {
        return intrimTag;
    }

    public void setIntrimTag(String intrimTag) {
        this.intrimTag = intrimTag;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getGoGreen() {
        return goGreen;
    }

    public void setGoGreen(String goGreen) {
        this.goGreen = goGreen;
    }

    public RollBackDetails getRollbackDetails() {
        return rollbackDetails;
    }

    public void setRollbackDetails(RollBackDetails rollbackDetails) {
        this.rollbackDetails = rollbackDetails;
    }

    public List<PolicyMovementDetailsSeller> getPolicyMovementDetailsSeller() {
        return policyMovementDetailsSeller;
    }

    public void setPolicyMovementDetailsSeller(List<PolicyMovementDetailsSeller> policyMovementDetailsSeller) {
        this.policyMovementDetailsSeller = policyMovementDetailsSeller;
    }

    public List<PolicyMovementDetailsCustomer> getPolicyMovementDetailsCustomer() {
        return policyMovementDetailsCustomer;
    }

    public void setPolicyMovementDetailsCustomer(List<PolicyMovementDetailsCustomer> policyMovementDetailsCustomer) {
        this.policyMovementDetailsCustomer = policyMovementDetailsCustomer;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public List<TpaDetail> getTpaDetails() {
        return tpaDetails;
    }

    public void setTpaDetails(List<TpaDetail> tpaDetails) {
        this.tpaDetails = tpaDetails;
    }

    public DecisionOutcome getDecisionOutcome() {
        return decisionOutcome;
    }

    public void setDecisionOutcome(DecisionOutcome decisionOutcome) {
        this.decisionOutcome = decisionOutcome;
    }

    @Override
    public String toString() {
        return "ResponsePayload{" +
                "lastUpdatedDateTime='" + lastUpdatedDateTime + '\'' +
                ", customerName='" + customerName + '\'' +
                ", insuredName='" + insuredName + '\'' +
                ", nomineeName='" + nomineeName + '\'' +
                ", agentName='" + agentName + '\'' +
                ", payorName='" + payorName + '\'' +
                ", basePlan='" + basePlan + '\'' +
                ", intrimTag='" + intrimTag + '\'' +
                ", source='" + source + '\'' +
                ", createdOn='" + createdOn + '\'' +
                ", goGreen='" + goGreen + '\'' +
                ", rollbackDetails=" + rollbackDetails +
                ", policyMovementDetailsSeller=" + policyMovementDetailsSeller +
                ", policyMovementDetailsCustomer=" + policyMovementDetailsCustomer +
                ", decisionOutcome=" + decisionOutcome +
                ", policyNumber='" + policyNumber + '\'' +
                ", tpaDetails=" + tpaDetails +
                '}';
    }
}
