package com.mli.mpro.otpservice;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OtpRequestPayload {
    @Sensitive(MaskType.DOB)
    private String dob;
    private String unqTokenNo;
    private String otpCode;
    private String stepId;
    private String methodType;//generate
    private long transactionId;
    private String actionType;

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getUnqTokenNo() {
        return unqTokenNo;
    }

    public void setUnqTokenNo(String unqTokenNo) {
        this.unqTokenNo = unqTokenNo;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public String getStepId() {
        return stepId;
    }

    public void setStepId(String stepId) {
        this.stepId = stepId;
    }

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }
    
    public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	@Override
	public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
		return "OtpRequestPayload [dob=" + dob + ", unqTokenNo=" + unqTokenNo + ", otpCode=" + otpCode + ", stepId="
				+ stepId + ", methodType=" + methodType + ", transactionId=" + transactionId + ", actionType="
				+ actionType + "]";
	}
}
