package com.mli.mpro.document.service;

import com.mli.mpro.common.models.DocumentResponsePayload;
import com.mli.mpro.proposal.models.InputRequest;
import com.mli.mpro.proposal.models.ProposalDetails;

public interface DocumentImplementationService {

    DocumentResponsePayload decideDocumentToGenerate(ProposalDetails proposalDetails, String documentType);
    DocumentResponsePayload documentGenerateForNewVersion(ProposalDetails proposalDetails);
    String documentGenerateValidation(InputRequest proposalDetails);
}
