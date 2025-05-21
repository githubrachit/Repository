package com.mli.mpro.axis.models;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class VoucherOutputResponse {
    private String vocherNumber;
    @Sensitive(MaskType.POLICY_NUM)
    private String policyNo;
    private String message;
    private String transactionId;
    private String status;

    public VoucherOutputResponse() {
    }

    public VoucherOutputResponse(String vocherNumber, String policyNo, String message, String transactionId, String status) {
	super();
	this.vocherNumber = vocherNumber;
	this.policyNo = policyNo;
	this.message = message;
	this.transactionId = transactionId;
	this.status = status;
    }

    public String getVocherNumber() {
	return vocherNumber;
    }

    public void setVocherNumber(String vocherNumber) {
	this.vocherNumber = vocherNumber;
    }

    public String getPolicyNo() {
	return policyNo;
    }

    public void setPolicyNo(String policyNo) {
	this.policyNo = policyNo;
    }

    public String getMessage() {
	return message;
    }

    public void setMessage(String message) {
	this.message = message;
    }

    public String getTransactionId() {
	return transactionId;
    }

    public void setTransactionId(String transactionId) {
	this.transactionId = transactionId;
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
	return "VoucherOutputResponse [vocherNumber=" + vocherNumber + ", policyNo=" + policyNo + ", message=" + message + ", transactionId=" + transactionId
		+ ", status=" + status + "]";
    }

}
