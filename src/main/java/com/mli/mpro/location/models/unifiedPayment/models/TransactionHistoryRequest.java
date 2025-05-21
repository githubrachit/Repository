package com.mli.mpro.location.models.unifiedPayment.models;
import com.mli.mpro.utils.Utility;

import java.util.List;

public class TransactionHistoryRequest {
    private String policyNo;
    private List<String> journeyTypes;

    public TransactionHistoryRequest() {
    }

    public TransactionHistoryRequest(String policyNo, List<String> journeyTypes) {
        this.policyNo = policyNo;
        this.journeyTypes = journeyTypes;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public List<String> getJourneyTypes() {
        return journeyTypes;
    }

    public void setJourneyTypes(List<String> journeyTypes) {
        this.journeyTypes = journeyTypes;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "TransactionHistoryRequest{" +
                "policyNo='" + policyNo + '\'' +
                ", journeyTypes=" + journeyTypes +
                '}';
    }
}