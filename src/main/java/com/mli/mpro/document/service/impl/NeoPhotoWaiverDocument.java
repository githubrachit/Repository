package com.mli.mpro.document.service.impl;

import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author manish on 05/02/21
 */
@Component("neoPhotoWaiverDocument")
@EnableAsync
public class NeoPhotoWaiverDocument extends OvdDocument {

    protected String photoWaiverConstant = "\"Please refer policy number %s for proposer’s photo.\" Here %s is previous policy number basis which photo is waived off.";
    protected String photoWaiver = "Photo Waiver";

    @Override
    public void generateDocument(ProposalDetails proposalDetails) {
        super.finalProcessedHtml = setContentForPhotoWaiverHTML(proposalDetails);
        super.documentName = photoWaiver;
        super.S3documentName = photoWaiver;
        super.S3documentID = photoWaiver;
        generatePdfDocument(proposalDetails);
    }

    private String setContentForPhotoWaiverHTML(ProposalDetails proposalDetails) {
        String existingPolicyNumber;
        if (Objects.nonNull(proposalDetails.getPartyInformation())
                && !proposalDetails.getPartyInformation().isEmpty()
                && Objects.nonNull(proposalDetails.getPartyInformation().get(0).getBasicDetails())) {
            existingPolicyNumber = Utility.nullSafe(proposalDetails.getPartyInformation().get(0).getBasicDetails().getExistingCustomerPolicyNo());
        } else {
            existingPolicyNumber = "";
        }
        return String.format(photoWaiverConstant, existingPolicyNumber, existingPolicyNumber);
    }
}
