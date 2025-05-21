package com.mli.mpro.axis.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class TransactionsEazyPay {

    @JsonProperty("channel")
    private String channel;

    private String crmId;
    @Sensitive(MaskType.POLICY_NUM)
    private String policyNo;

    private String status;
    private String transactionId;
    
    

    public TransactionsEazyPay() {
        //comment on default constructor by resolving sonar critical issue
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getCrmId() {
        return crmId;
    }

    public void setCrmId(String crmId) {
        this.crmId = crmId;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "TransactionsEazyPay [channel=" + channel + ", crmId=" + crmId + ", policyNo=" + policyNo + ", status=" + status + ", transactionId="
		+ transactionId + "]";
    }

   

}
