package com.mli.mpro.axis.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class AddOnDetails {

    @JsonProperty("addOnTransactionId")
    private long addOnTransactionId;
    @JsonProperty("addOnMongoId")
    private String addOnMongoId;
    @Sensitive(MaskType.POLICY_NUM)
    private String policyNumber;
    @JsonProperty("addOnProductId")
    private String addOnProductId;

    public long getAddOnTransactionId() {
        return addOnTransactionId;
    }
    public void setAddOnTransactionId(long addOnTransactionId) {
	this.addOnTransactionId = addOnTransactionId;
    }

    public String getAddOnMongoId() {
	return addOnMongoId;
    }

    public void setAddOnMongoId(String addOnMongoId) {
	this.addOnMongoId = addOnMongoId;
    }

    public String getPolicyNumber() {
	return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
	this.policyNumber = policyNumber;
    }

    
    public String getAddOnProductId() {
        return addOnProductId;
    }
    public void setAddOnProductId(String addOnProductId) {
        this.addOnProductId = addOnProductId;
    }
    public AddOnDetails() {
    }
    


    public AddOnDetails(long addOnTransactionId, String addOnMongoId, String policyNumber, String addOnProductId) {
	super();
	this.addOnTransactionId = addOnTransactionId;
	this.addOnMongoId = addOnMongoId;
	this.policyNumber = policyNumber;
	this.addOnProductId = addOnProductId;
    }
    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "AddOnDetails [addOnTransactionId=" + addOnTransactionId + ", addOnMongoId=" + addOnMongoId + ", policyNumber=" + policyNumber
		+ ", addOnProductId=" + addOnProductId + "]";
    }
   

}
