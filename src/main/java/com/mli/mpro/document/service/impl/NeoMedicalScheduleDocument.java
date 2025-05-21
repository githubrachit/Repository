package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.document.mapper.NeoMedicalScheduleDocumentMapper;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.DocumentDetails;
import com.mli.mpro.proposal.models.DocumentRequestInfo;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.mli.mpro.utils.Utility.isPartyProposer;


@Component("neoMedicalScheduleDocument")
public class NeoMedicalScheduleDocument implements DocumentGenerationservice {

    private static final Logger logger = LoggerFactory.getLogger(NeoMedicalScheduleDocument.class);

    @Autowired
    private SpringTemplateEngine springTemplateEngine;
    @Autowired
    private DocumentHelper documentHelper;
    @Autowired
    private NeoMedicalScheduleDocumentMapper neoMedicalScheduleDocumentMapper;

    @Override
    public void generateDocument(ProposalDetails proposalDetails) {
        long transactionId = proposalDetails.getTransactionId();
        long requestedTime = System.currentTimeMillis();
        long processedTime = (System.currentTimeMillis() - requestedTime);
        String finalProcessedTemplate = "";
        logger.info("Starting Medical Schedule Document Creation for transactionId {}", transactionId);
        DocumentStatusDetails documentStatusDetails;
        String documentUploadStatus;

        try {

            if(Utility.isApplicationIsForm2(proposalDetails)){
                Utility.changeProposerAndLifeinsuredForForm2(proposalDetails);
            }

            Context neoMedicalScheduleContext = neoMedicalScheduleDocumentMapper.setDocumentData(proposalDetails);

            finalProcessedTemplate = getFinalProcessedTemplate(proposalDetails, finalProcessedTemplate, neoMedicalScheduleContext);
            String encodedString = documentHelper.generatePDFDocument(finalProcessedTemplate, 0);
            if (encodedString.equalsIgnoreCase(AppConstants.FAILED)) {
                logger.info("Document generation is failed so updating in DB for transactionId {}", transactionId);
                documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
                        proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DOCUMENT_GENERATION_FAILED,
                        0, AppConstants.MEDICAL_SCHEDULE_DOCUMENT, proposalDetails.getApplicationDetails().getStage());
            } else {
                DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE, encodedString, AppConstants.MEDICAL_SCHEDULE_DOCUMENT);
                List<DocumentRequestInfo> documentRequestInfoList = new ArrayList<>();
                documentRequestInfoList.add(documentRequestInfo);
                DocumentDetails documentDetails = new DocumentDetails(proposalDetails.getChannelDetails().getChannel(),
                        transactionId, AppConstants.MEDICAL_SCHEDULE_DOCUMENT_ID, AppConstants.MEDICAL_SCHEDULE_DOCUMENT,
                        documentRequestInfoList);
                documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, 0);
                if (documentUploadStatus.equalsIgnoreCase(AppConstants.FAILED)) {
                    documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
                            proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DOCUMENT_GENERATION_FAILED,
                            0, AppConstants.MEDICAL_SCHEDULE_DOCUMENT, proposalDetails.getApplicationDetails().getStage());
                } else {
                    logger.info("Medical Schedule Document is successfully generated and uploaded to S3 for transactionId {}", transactionId);
                    documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
                            proposalDetails.getSourcingDetails().getAgentId(), documentUploadStatus, 0,
                            AppConstants.MEDICAL_SCHEDULE_DOCUMENT, proposalDetails.getApplicationDetails().getStage());
                }
            }

        } catch (UserHandledException e) {
            logger.error("Error occured while generating Medical Schedule Document for transactionId {}", transactionId, e);
            documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
                    proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DATA_MISSING_FAILURE,
                    0, AppConstants.MEDICAL_SCHEDULE_DOCUMENT, proposalDetails.getApplicationDetails().getStage());
        } catch (Exception e) {
            logger.error("Medical Schedule Document generation failed for equoteNumber {} and transactionId {}", proposalDetails.getEquoteNumber(), transactionId, e);
            documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
                    proposalDetails.getSourcingDetails().getAgentId(), AppConstants.TECHNICAL_FAILURE, 0, "NRI Medical Schedule Document"
                    , proposalDetails.getApplicationDetails().getStage());
        }
        documentHelper.updateDocumentStatus(documentStatusDetails);
        logger.info("Medical Schedule Document for transactionId {} took {} milliseconds", transactionId, processedTime);
    }

    private String getFinalProcessedTemplate(ProposalDetails proposalDetails, String finalProcessedTemplate, Context neoMedicalScheduleContext) {
        String visitType;
        if (checkingIfMedicalSchedulingNotNull(proposalDetails)
                && !Utility.isNullOrEmptyString(proposalDetails.getUnderwritingServiceDetails().getMedicalShedulingDetails().getVisitType())) {

                visitType = proposalDetails.getUnderwritingServiceDetails().getMedicalShedulingDetails().getVisitType();

            if ((isPartyProposer(proposalDetails) && (Utility.isProductSWPJL(proposalDetails)
                    || Utility.isApplicationIsForm2(proposalDetails) || Utility.isSSPJLProduct(proposalDetails))) && Utility.checkNriParty(proposalDetails,1)) {
                finalProcessedTemplate = nriMedicalScheduleTemplates(finalProcessedTemplate, neoMedicalScheduleContext, visitType);
            } else if (Utility.checkNriParty(proposalDetails, 0)) {
                    finalProcessedTemplate = nriMedicalScheduleTemplates(finalProcessedTemplate, neoMedicalScheduleContext, visitType);
            } else if (checkingConditionForGeneratingDomesticDoc(proposalDetails)) {
                    finalProcessedTemplate = domesticMedicalScheduleTemplates(finalProcessedTemplate, neoMedicalScheduleContext, visitType);
                }
        }
        return finalProcessedTemplate;
    }

    private boolean checkingConditionForGeneratingDomesticDoc(ProposalDetails proposalDetails) {
        if ((isPartyProposer(proposalDetails) && (Utility.isProductSWPJL(proposalDetails)
                || Utility.isApplicationIsForm2(proposalDetails) || Utility.isSSPJLProduct(proposalDetails)))) {
            return Utility.checkDomesticParty(proposalDetails, 1)
                    && Objects.nonNull(proposalDetails.getUnderwritingServiceDetails().getMedicalShedulingDetails().getIsPinCodeChanged())
                    && AppConstants.IS_PINCODE_CHANGED.equalsIgnoreCase(proposalDetails.getUnderwritingServiceDetails().getMedicalShedulingDetails().getIsPinCodeChanged());

        } else {
            return Utility.checkDomesticParty(proposalDetails, 0)
                    && Objects.nonNull(proposalDetails.getUnderwritingServiceDetails().getMedicalShedulingDetails().getIsPinCodeChanged())
                    && AppConstants.IS_PINCODE_CHANGED.equalsIgnoreCase(proposalDetails.getUnderwritingServiceDetails().getMedicalShedulingDetails().getIsPinCodeChanged());

        }
    }

    private boolean checkingIfMedicalSchedulingNotNull(ProposalDetails proposalDetails) {
        return Objects.nonNull(proposalDetails.getUnderwritingServiceDetails()) && Objects.nonNull(proposalDetails.getUnderwritingServiceDetails().getMedicalShedulingDetails());
    }

    private String domesticMedicalScheduleTemplates(String finalProcessedTemplate, Context neoMedicalScheduleContext, String visitType) {
        if (AppConstants.CENTER_VISIT.equalsIgnoreCase(visitType)) {
           finalProcessedTemplate = springTemplateEngine.process("domestic\\domesticCenterVisit", neoMedicalScheduleContext);
        } else if (AppConstants.HOME_VISIT.equalsIgnoreCase(visitType)) {
            finalProcessedTemplate = springTemplateEngine.process("domestic\\domesticHomeVisit", neoMedicalScheduleContext);
        }
        return finalProcessedTemplate;
    }

    private String nriMedicalScheduleTemplates(String finalProcessedTemplate, Context neoMedicalScheduleContext, String visitType) {
        if (AppConstants.CENTER_VISIT.equalsIgnoreCase(visitType)) {
            finalProcessedTemplate = springTemplateEngine.process("nri\\nriCenterVisit", neoMedicalScheduleContext);
        } else if (AppConstants.HOME_VISIT.equalsIgnoreCase(visitType)) {
            finalProcessedTemplate = springTemplateEngine.process("nri\\nriHomeVisit", neoMedicalScheduleContext);
        }
        return finalProcessedTemplate;
    }
}
