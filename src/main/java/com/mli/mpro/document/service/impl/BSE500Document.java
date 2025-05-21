package com.mli.mpro.document.service.impl;

import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.ProposalDetails;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

/**
 * @author manish on 09/04/20
 */
@Component("bse500Document")
@EnableAsync
public class BSE500Document extends OvdDocument {

    protected String bse500FinalProcessedHtml = AppConstants.BSE500_DOCUMENT_CONTENT;
    protected String bse500DocumentName = "BSE500";
    protected String bse500S3documentName = AppConstants.BSE500;
    protected String bse500S3documentID = AppConstants.BSE500_DOCUMENT_ID;
    @Override
    public void generateDocument(ProposalDetails proposalDetails) {
        super.finalProcessedHtml = bse500FinalProcessedHtml;
        super.documentName = bse500DocumentName;
        super.S3documentName = bse500S3documentName;
        super.S3documentID = bse500S3documentID;
        generatePdfDocument(proposalDetails);
    }
}
