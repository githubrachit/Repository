package com.mli.mpro.location.models.unifiedPayment.models;
import java.util.List;

public class TransactionHistoryPayload {
    private String startDateTime;
    private String endDateTime;
    private List<UnifiedWebHookRequest> transactionDetailsList;

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public List<UnifiedWebHookRequest> getTransactionDetailsList() {
        return transactionDetailsList;
    }

    public void setTransactionDetailsList(List<UnifiedWebHookRequest> transactionDetailsList) {
        this.transactionDetailsList = transactionDetailsList;
    }

    @Override
    public String toString() {
        return "TransactionHistoryPayload{" +
                "startDateTime='" + startDateTime + '\'' +
                ", endDateTime='" + endDateTime + '\'' +
                ", transactionDetailsList=" + transactionDetailsList +
                '}';
    }
}
