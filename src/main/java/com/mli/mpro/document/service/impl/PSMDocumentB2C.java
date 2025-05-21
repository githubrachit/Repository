package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.mli.mpro.common.models.DocumentResponsePayload;
import com.mli.mpro.document.mapper.PSMDocumentMapper;
import com.mli.mpro.document.service.DocumentGenerationByType;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.ProposalDetails;

@Component("psmDocumentService")
public class PSMDocumentB2C implements DocumentGenerationByType {

    private static final Logger logger = LoggerFactory.getLogger(PSMDocumentB2C.class);

    @Autowired
    private PSMDocumentMapper psmDocumentMapper;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private DocumentHelper documentHelper;

    @Override
    public DocumentResponsePayload generateDocumentByType(ProposalDetails proposalDetails) {

	DocumentResponsePayload documentResponsePayload = new DocumentResponsePayload();
	try {
	    logger.info("Initiating data mapping for PSM document generation {}", proposalDetails.getApplicationDetails().getPolicyNumber());
	    Context context = psmDocumentMapper.setDataForDocument(proposalDetails);
	    logger.info("Initiating Data to HTML mapping for PSM document generation {}", proposalDetails.getApplicationDetails().getPolicyNumber());
	    String processedHtml = springTemplateEngine.process("PSMB2C", context);
	    String encodedString = documentHelper.generatePDFDocument(processedHtml, 0);
	    if (encodedString.equalsIgnoreCase(AppConstants.FAILED)) {
		documentResponsePayload.setDocumentGenerationStatus(AppConstants.FAILED);
	    } else {
		documentResponsePayload.setDocumentGenerationStatus(AppConstants.SUCCESS);
		documentResponsePayload.setDocumentInBase64(encodedString);
	    }
	} catch (Exception ex) {
	    documentResponsePayload.setDocumentGenerationStatus(AppConstants.FAILED);
		logger.error("Error occurred while generating document by type for PSM document generation :", ex);
	}
	return documentResponsePayload;
    }

	@Override
	public DocumentResponsePayload generateDocumentVersionOne(ProposalDetails proposalDetails) {
		DocumentResponsePayload documentResponsePayload = new DocumentResponsePayload();
		try {
			Context context = psmDocumentMapper.setDataPsmAndIrpDoc(proposalDetails);
			String processHtml = springTemplateEngine.process("neo\\irpPsmVersion1\\IrpPsm_neo", context);
			String encodedString = documentHelper.generatePDFDocument(processHtml, 0);
			if (encodedString.equalsIgnoreCase(AppConstants.FAILED)) {
				documentResponsePayload.setDocumentGenerationStatus(AppConstants.FAILED);
			} else {
				documentResponsePayload.setDocumentGenerationStatus(AppConstants.SUCCESS);
				documentResponsePayload.setDocumentInBase64(encodedString);
			}
			return documentResponsePayload;
		} catch (Exception ex) {
			documentResponsePayload.setDocumentGenerationStatus(AppConstants.FAILED);
			logger.error("Error occurred while generating document by type for PSM document generation :", ex);
		}
		return documentResponsePayload;
	}
}
