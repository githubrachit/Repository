package com.mli.mpro.proposal.models;

import com.mli.mpro.utils.Utility;

import java.util.List;

public class FinancialGridDetails {

    private String underwritingResult;

    private List<FinancialDocumentDetails> financialDocumentsRequired;

    public FinancialGridDetails() {
	super();
    }

    public String getUnderwritingResult() {
	return underwritingResult;
    }

    public void setUnderwritingResult(String underwritingResult) {
	this.underwritingResult = underwritingResult;
    }

    public List<FinancialDocumentDetails> getFinancialDocumentsRequired() {
	return financialDocumentsRequired;
    }

    public void setFinancialDocumentsRequired(List<FinancialDocumentDetails> financialDocumentsRequired) {
	this.financialDocumentsRequired = financialDocumentsRequired;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
           return Utility.toString(this);
        }
	return "FinancialGridDetails [underwritingResult=" + underwritingResult + ", financialDocumentsRequired=" + financialDocumentsRequired + "]";
    }

}
