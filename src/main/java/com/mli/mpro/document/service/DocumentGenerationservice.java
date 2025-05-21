package com.mli.mpro.document.service;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.neo.models.attachment.Payload;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.proposal.models.RequestPayload;


public interface DocumentGenerationservice {

    public void generateDocument(ProposalDetails proposalDetails);

    default void createDocumentAndUploadToS3(Payload payload) {
    }

    default String getDocumentBase64(ProposalDetails proposalDetails) throws UserHandledException {
        return AppConstants.FAILED;
    }

    default void generateSellerDocument(RequestPayload requestPayload, String channel) {
    }
}
