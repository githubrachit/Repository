package com.mli.mpro.location.models.unifiedPayment.models;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class TransactionHistoryResponse {
    @JsonProperty("resultInfoModel")
    private ResultInfoModel resultInfoModel;
    @JsonProperty("payload")
    private TransactionHistoryPayload transactionHistoryPayload;

    public ResultInfoModel getResultInfoModel() {
        return resultInfoModel;
    }

    public void setResultInfoModel(ResultInfoModel resultInfoModel) {
        this.resultInfoModel = resultInfoModel;
    }

    public TransactionHistoryPayload getTransactionHistoryPayload() {
        return transactionHistoryPayload;
    }

    public void setTransactionHistoryPayload(TransactionHistoryPayload transactionHistoryPayload) {
        this.transactionHistoryPayload = transactionHistoryPayload;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "TransactionHistoryResponse{" +
                "resultInfoModel=" + resultInfoModel +
                ", transactionHistoryPayload=" + transactionHistoryPayload +
                '}';
    }
}
