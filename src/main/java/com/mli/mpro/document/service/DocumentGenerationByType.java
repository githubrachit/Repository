package com.mli.mpro.document.service;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.DocumentResponsePayload;
import com.mli.mpro.proposal.models.ProposalDetails;

public interface DocumentGenerationByType {
    
    DocumentResponsePayload generateDocumentByType(ProposalDetails proposalDetails);
    DocumentResponsePayload generateDocumentVersionOne(ProposalDetails proposalDetails);
}