package com.mli.mpro.document.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.DocumentDetails;
import com.mli.mpro.proposal.models.DocumentRequestInfo;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;

@Component("gstWaiverDocument")
@EnableAsync
public class GSTWaiverDocument implements DocumentGenerationservice {

	@Autowired
	private DocumentHelper documentHelper;

	private static final Logger logger = LoggerFactory.getLogger(CkycDocument.class);

	@Override
	@Async
	public void generateDocument(ProposalDetails proposalDetails) {
		String gstWaiver = proposalDetails.getAdditionalFlags().getGstWaiverRequired();
		if (!StringUtils.isEmpty(gstWaiver)) {
			logger.info("calling gstWaiver Document PDF Generation trancationId {}",
					proposalDetails.getTransactionId());
			String gstWaiverRequired = proposalDetails.getAdditionalFlags().getGstWaiverRequired();
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			PdfDocument pdfDocument = new PdfDocument(new PdfWriter(byteArrayOutputStream));
			Document document = new Document(pdfDocument, PageSize.A4);
			try {
				DocumentStatusDetails documentStatusDetails = null;

				logger.info("Gst Waiver document initiated");
				Paragraph preface = new Paragraph();
				// Header for pdf
				String text = "GST Waiver Document";
				document.showTextAligned(text, 240, 809, TextAlignment.CENTER, 0);

				preface.add("\nGST Waiver Status -                 " + gstWaiverRequired);
				document.add(preface);
				document.close();
				String encodedString = com.amazonaws.util.Base64.encodeAsString(byteArrayOutputStream.toByteArray());
				if (encodedString.equalsIgnoreCase(AppConstants.FAILED)) {
					// update the document generation failure status in db
					logger.info("Document generation is failed so updating in DB for transactionId {}",
							proposalDetails.getTransactionId());
					documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
							proposalDetails.getApplicationDetails().getPolicyNumber(),
							proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DOCUMENT_GENERATION_FAILED,
							0, AppConstants.GST_WAIVER_STATUS, proposalDetails.getApplicationDetails().getStage());
					documentHelper.updateDocumentStatus(documentStatusDetails);
				} else {
					generateGstWaiver(encodedString, proposalDetails);
				}
			} catch (Exception e) {
				logger.info("exception occured while generating the gst waiver document {}",
						Utility.getExceptionAsString(e));
			}
		}
	}

	private void generateGstWaiver(String encodedString, ProposalDetails proposalDetails) {
		logger.info("Started to generate gst waiver document for transactionId {}", proposalDetails.getTransactionId());
		DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE, encodedString,
				AppConstants.GST_WAIVER_STATUS);
		List<DocumentRequestInfo> documentpayload = new ArrayList<>();
		String documentUploadStatus = "";
		int retryCount = 0;
		DocumentStatusDetails documentStatusDetails=null;
		documentpayload.add(documentRequestInfo);
		DocumentDetails documentDetails = new DocumentDetails(proposalDetails.getChannelDetails().getChannel(),
				proposalDetails.getTransactionId(), AppConstants.GST_WAIVER_STATUS_ID, AppConstants.GST_WAIVER_STATUS,
				documentpayload);
		if (AppConstants.PROPOSAL_E2E_TRANSFORMATION.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getSourceChannel())) {
			documentDetails.setSourceChannel(proposalDetails.getAdditionalFlags().getSourceChannel());
		}
		documentDetails.setThanosDolphinIntegrationEnabled(proposalDetails.isThanosDolphinIntegrationEnabled());
		documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, retryCount);

		if (documentUploadStatus.equalsIgnoreCase("FAILED")) {
			documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
					proposalDetails.getApplicationDetails().getPolicyNumber(),
					proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DOCUMENT_UPLOAD_FAILED, 0,
					AppConstants.GST_WAIVER_STATUS, proposalDetails.getApplicationDetails().getStage());
		} else {
			logger.info("GST Document is successfully generated and uploaded to S3 for transactionId {} {}",
					proposalDetails.getTransactionId(), AppConstants.GST_WAIVER_STATUS);
			// update the document upload success status in db
			documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
					proposalDetails.getApplicationDetails().getPolicyNumber(),
					proposalDetails.getSourcingDetails().getAgentId(), documentUploadStatus, 0,
					AppConstants.GST_WAIVER_STATUS, proposalDetails.getApplicationDetails().getStage());
		}

		documentHelper.updateDocumentStatus(documentStatusDetails);
	}
}
