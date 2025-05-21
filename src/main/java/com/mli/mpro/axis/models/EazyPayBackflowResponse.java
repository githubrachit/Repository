package com.mli.mpro.axis.models;

import com.mli.mpro.utils.Utility;

import java.util.List;

public class EazyPayBackflowResponse {

    private List<TransactionsEazyPay> transactionsEazyPay;
    
    private String message;
    private String status;

   
    

    public List<TransactionsEazyPay> getTransactionsEazyPay() {
        return transactionsEazyPay;
    }

    public void setTransactionsEazyPay(List<TransactionsEazyPay> transactionsEazyPay) {
        this.transactionsEazyPay = transactionsEazyPay;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
           return Utility.toString(this);
        }
	return "EazyPayBackflowResponse [transactionsEazyPay=" + transactionsEazyPay + ", message=" + message + ", status=" + status + "]";
    }

   
    
    
}
