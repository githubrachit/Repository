
package com.mli.mpro.proposal.models;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "receipt" })
public class PaymentDetails {

    @JsonProperty("receipt")
    private List<Receipt> receipt = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PaymentDetails() {
    }

    /**
     * 
     * @param receipt
     */
    public PaymentDetails(List<Receipt> receipt) {
	super();
	this.receipt = receipt;
    }

   

    public PaymentDetails(PaymentDetails paymentDetails) {
	List<Receipt> receiptList = paymentDetails.receipt;
	receiptList = (receiptList != null && receiptList.size() != 0) ? receiptList.stream().collect(Collectors.toList())
		: receiptList;
	this.receipt = receiptList;
    }

    @JsonProperty("receipt")
    public List<Receipt> getReceipt() {
	return receipt;
    }

    @JsonProperty("receipt")
    public void setReceipt(List<Receipt> receipt) {
	this.receipt = receipt;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "PaymentDetails [receipt=" + receipt + "]";
    }

}
