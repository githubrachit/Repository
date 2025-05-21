package com.mli.mpro.configuration.models;

import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;

public class DocumentExecutionStrategy {
    
    private DocumentGenerationservice documentGenerationservice;

    public DocumentExecutionStrategy(DocumentGenerationservice documentGenerationservice) {
	super();
	this.documentGenerationservice = documentGenerationservice;
    }

    public DocumentGenerationservice getDocumentGenerationservice() {
        return documentGenerationservice;
    }

    public void setDocumentGenerationservice(DocumentGenerationservice documentGenerationservice) {
        this.documentGenerationservice = documentGenerationservice;
    }
    
    public void initiateDocumentgeneration(ProposalDetails proposalDetails){
	getDocumentGenerationservice().generateDocument(proposalDetails);
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
           return Utility.toString(this);
        }
        return "DocumentExecutionStrategy{" +
                "documentGenerationservice=" + documentGenerationservice +
                '}';
    }
}
