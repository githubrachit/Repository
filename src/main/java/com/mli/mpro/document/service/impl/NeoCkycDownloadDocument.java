package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.CkycDownloadResponseDetail;
import com.mli.mpro.proposal.models.DocumentDetails;
import com.mli.mpro.proposal.models.DocumentRequestInfo;
import com.mli.mpro.proposal.models.ProposalDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("neoCkycDownloadDocument")
public class NeoCkycDownloadDocument extends NeoBaseDocument implements DocumentGenerationservice {

    static HashMap<String, String> accountTypeMap = new HashMap<>();

    static {
        accountTypeMap.put("01", "Normal");
        accountTypeMap.put("02", "Small");
        accountTypeMap.put("03", "Simplified");
        accountTypeMap.put("04", "OTPbased EKYC Account");
        accountTypeMap.put("05", "Minor");
        accountTypeMap.put("06", "Existing");
    }

    static HashMap<String, String> genderMap = new HashMap<>();

    static {
        genderMap.put("M", "Male");
        genderMap.put("F", "Female");
        genderMap.put("T", "Transgender");
    }

    static HashMap<String, String> ckycDocSubmittedMap = new HashMap<>();

    static {
        ckycDocSubmittedMap.put("01", "Certified Copies");
        ckycDocSubmittedMap.put("02", "E-KYC data received from UIDAI");
        ckycDocSubmittedMap.put("03", "Data received from offline verification");
        ckycDocSubmittedMap.put("04", "Digital KYC process");
        ckycDocSubmittedMap.put("05", "Equivalent e-document");

    }

    private static final Logger logger = LoggerFactory.getLogger(NeoCkycDownloadDocument.class);

    @Override
    public void generateDocument(ProposalDetails proposalDetails) {


        long requestedTime = System.currentTimeMillis();
        int retryCount = 0;
        long transactionId = proposalDetails.getTransactionId();
        DocumentStatusDetails documentStatusDetails = null;
            try {
                Context context = setDataForDocument(proposalDetails);
                generateBaseDoc(proposalDetails,context,transactionId,retryCount,requestedTime, "neo\\ckycDownload.html");
                if (encodedString.equalsIgnoreCase(AppConstants.FAILED)) {
                    // update the document generation failure status in db
                    logger.info("Document generation is failed so updating in DB for transactionId {}", transactionId);
                    documentStatusDetails = new DocumentStatusDetails(transactionId,
                            proposalDetails.getApplicationDetails().getPolicyNumber(),
                            proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DOCUMENT_GENERATION_FAILED,
                            0, AppConstants.CKYC_DOWNLOAD_DOCUMENT, proposalDetails.getApplicationDetails().getStage());
                } else {
                    documentStatusDetails = saveGeneratedDocumentToS3(proposalDetails, encodedString, 0);
                }

            } catch (UserHandledException ex) {
                logger.error("Neo ckyc download document generation failed:", ex);
                documentStatusDetails = documentHelper.populateDocumentStatusObj(true, proposalDetails, proposalDetails.getTransactionId(),
                        AppConstants.DATA_MISSING_FAILURE, AppConstants.CKYC_DOWNLOAD_DOCUMENT);
            } catch (Exception ex) {
                logger.error("Neo ckyc download Document generation failed:", ex);
                documentStatusDetails = documentHelper.populateDocumentStatusObj(true, proposalDetails, proposalDetails.getTransactionId(),
                        AppConstants.TECHNICAL_FAILURE, AppConstants.CKYC_DOWNLOAD_DOCUMENT);
            }
            documentHelper.updateDocumentStatus(documentStatusDetails);
            long processedTime = (System.currentTimeMillis() - requestedTime);
            logger.info("Neo ckyc download document is generated for transactionId {} took {} milliseconds ", proposalDetails.getTransactionId(), processedTime);


    }

    protected DocumentStatusDetails saveGeneratedDocumentToS3(ProposalDetails proposalDetails, String pdfDocumentOrDocumentStatus, int retryCount) {
        DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(
                AppConstants.DOCUMENT_TYPE,
                pdfDocumentOrDocumentStatus,
                AppConstants.CKYC_DOWNLOAD_DOCUMENT);
        List<DocumentRequestInfo> documentPayload = new ArrayList<>();
        documentPayload.add(documentRequestInfo);
        DocumentDetails documentDetails = new DocumentDetails(proposalDetails.getChannelDetails().getChannel(), proposalDetails.getTransactionId(),
                "CKYC_DOWNLOAD_PDF", AppConstants.CKYC_DOWNLOAD_DOCUMENT, documentPayload);

        String documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, retryCount);
        if (AppConstants.FAILED.equalsIgnoreCase(documentUploadStatus)) {
            // update the document upload failure status in DB
            logger.info("Document upload is failed for transactionId {} {}", proposalDetails.getTransactionId(), AppConstants.CKYC_DOWNLOAD_DOCUMENT);
            return documentHelper.populateDocumentStatusObj(true, proposalDetails, proposalDetails.getTransactionId(),
                    AppConstants.DOCUMENT_UPLOAD_FAILED, AppConstants.CKYC_DOWNLOAD_DOCUMENT);
        } else {
            // update the document upload success status in DB
            logger.info("Document is successfully uploaded to S3 for transactionId {} {}", proposalDetails.getTransactionId(),
                    AppConstants.CKYC_DOWNLOAD_DOCUMENT);
            return documentHelper.populateDocumentStatusObj(true, proposalDetails, proposalDetails.getTransactionId(),
                    documentUploadStatus, AppConstants.CKYC_DOWNLOAD_DOCUMENT);
        }
    }

    private Context setDataForDocument(ProposalDetails proposalDetails) throws UserHandledException {

        Context context = new Context();
        logger.info("Data Mapping is initiated for transactionId {}", proposalDetails.getTransactionId());
        try {
            Map<String, Object> dataForDocument = new HashMap<>();
            CkycDownloadResponseDetail ckycDownloadResponseDetail = proposalDetails.getCkycDataResponse().getDownloadFromCkycResponseDetails().getCkycDownloadResponseDetail();
            String ckycNumber="";
            String accountType="";
            String ckycFullName="";
            String fatherFullName="";
            String motherFullName="";
            String ckycMaidenFullName="";
            String ckycDOB="";
            String ckycGender="";
            String pan="";
            String ckycOffTelNumber="";
            String ckycEmailAdd="";
            String ckycMobileNumber="";
            String ckycRemarks="";
            String ckycKYCVerificationName="";
            String ckycKYCVerificationBranch="";
            String ckycResTelNumber="";
            String ckycKYCVerificationEmpcode="";
            String ckycKYCVerificationDate="";
            String ckycKYCVerificationDesg="";
            String ckycPlaceofDeclaration="";
            String ckycDateofDeclaration="";
            String ckycTypeofDocSubmitted="";
            String ckycPerAdd1="";
            String ckycPerAdd2="";
            String ckycPerAdd3="";
            String ckycPerAddCity="";
            String ckycPerAddDistrict="";
            String ckycPerAddState="";
            String ckycPerAddPin ="";
            String ckycPerAddPOA ="";
            String ckycCorAdd1 = "";
            String ckycCorAdd2 = "";
            String ckycCorAdd3 = "";
            String ckycCorAddCity = "";
            String ckycCorAddDistrict ="";
            String ckycCorAddState = "";
            String ckycCorAddPin = "";
            String ckycCorAddPOA = "";
            if(ckycDownloadResponseDetail !=null && ckycDownloadResponseDetail.getCkycPersonalDetail() !=null) {
                 ckycNumber = ckycDownloadResponseDetail.getCkycPersonalDetail().getCkycNumber();
                 accountType = accountTypeMap.get(ckycDownloadResponseDetail.getCkycPersonalDetail().getCkycAccType());
                 ckycFullName = ckycDownloadResponseDetail.getCkycPersonalDetail().getCkycFullName();
                 fatherFullName = ckycDownloadResponseDetail.getCkycPersonalDetail().getCkycFatherFullName();
                 motherFullName = ckycDownloadResponseDetail.getCkycPersonalDetail().getCkycMotherFullName();
                 ckycMaidenFullName = ckycDownloadResponseDetail.getCkycPersonalDetail().getCkycMaidenFullName();
                 ckycDOB = ckycDownloadResponseDetail.getCkycPersonalDetail().getCkycDOB();
                 ckycGender = genderMap.get(ckycDownloadResponseDetail.getCkycPersonalDetail().getCkycGender());
                 pan = ckycDownloadResponseDetail.getCkycPersonalDetail().getCkycPAN();
                 ckycOffTelNumber = ckycDownloadResponseDetail.getCkycPersonalDetail().getCkycOffTelNumber();
                 ckycEmailAdd = ckycDownloadResponseDetail.getCkycPersonalDetail().getCkycEmailAdd();
                 ckycMobileNumber = ckycDownloadResponseDetail.getCkycPersonalDetail().getCkycMobileNumber();
                 ckycResTelNumber = ckycDownloadResponseDetail.getCkycPersonalDetail().getCkycResTelNumber();
                ckycRemarks = ckycDownloadResponseDetail.getCkycPersonalDetail().getCkycRemarks();
                 ckycKYCVerificationName = ckycDownloadResponseDetail.getCkycPersonalDetail().getCkycKYCVerificationName();
                 ckycKYCVerificationBranch = ckycDownloadResponseDetail.getCkycPersonalDetail().getCkycKYCVerificationBranch();
                 ckycKYCVerificationEmpcode = ckycDownloadResponseDetail.getCkycPersonalDetail().getCkycKYCVerificationEmpcode();
                 ckycKYCVerificationDate = ckycDownloadResponseDetail.getCkycPersonalDetail().getCkycKYCVerificationDate();
                 ckycKYCVerificationDesg = ckycDownloadResponseDetail.getCkycPersonalDetail().getCkycKYCVerificationDesg();
                 ckycPlaceofDeclaration = ckycDownloadResponseDetail.getCkycPersonalDetail().getCkycPlaceofDeclaration();
                 ckycDateofDeclaration = ckycDownloadResponseDetail.getCkycPersonalDetail().getCkycDateofDeclaration();
                 ckycTypeofDocSubmitted = ckycDocSubmittedMap.get(ckycDownloadResponseDetail.getCkycPersonalDetail().getCkycTypeofDocSubmitted());
                 ckycPerAdd1 = ckycDownloadResponseDetail.getCkycPersonalDetail().getCkycPerAdd1();
                 ckycPerAdd2 = ckycDownloadResponseDetail.getCkycPersonalDetail().getCkycPerAdd2();
                 ckycPerAdd3 = ckycDownloadResponseDetail.getCkycPersonalDetail().getCkycPerAdd3();
                 ckycPerAddCity = ckycDownloadResponseDetail.getCkycPersonalDetail().getCkycPerAddCity();
                 ckycPerAddDistrict = ckycDownloadResponseDetail.getCkycPersonalDetail().getCkycPerAddDistrict();
                 ckycPerAddState = ckycDownloadResponseDetail.getCkycPersonalDetail().getCkycPerAddState();
                 ckycPerAddPin = ckycDownloadResponseDetail.getCkycPersonalDetail().getCkycPerAddPin();
                 ckycPerAddPOA = ckycDownloadResponseDetail.getCkycPersonalDetail().getCkycPerAddPOA();
                 ckycCorAdd1 = ckycDownloadResponseDetail.getCkycPersonalDetail().getCkycCorAdd1();
                 ckycCorAdd2 = ckycDownloadResponseDetail.getCkycPersonalDetail().getCkycCorAdd2();
                 ckycCorAdd3 = ckycDownloadResponseDetail.getCkycPersonalDetail().getCkycCorAdd3();
                 ckycCorAddCity = ckycDownloadResponseDetail.getCkycPersonalDetail().getCkycCorAddCity();
                 ckycCorAddDistrict = ckycDownloadResponseDetail.getCkycPersonalDetail().getCkycCorAddDistrict();
                 ckycCorAddState = ckycDownloadResponseDetail.getCkycPersonalDetail().getCkycCorAddState();
                 ckycCorAddPin = ckycDownloadResponseDetail.getCkycPersonalDetail().getCkycCorAddPin();
                 ckycCorAddPOA = ckycDownloadResponseDetail.getCkycPersonalDetail().getCkycCorAddPOA();
            }

            dataForDocument.put("ckycNumber",ckycNumber );
            dataForDocument.put("accountType", accountType);
            dataForDocument.put("ckycFullName", ckycFullName);
            dataForDocument.put("fatherFullName", fatherFullName);
            dataForDocument.put("motherFullName", motherFullName);
            dataForDocument.put("ckycMaidenFullName", ckycMaidenFullName);
            dataForDocument.put("ckycDOB", ckycDOB);
            dataForDocument.put("ckycGender", ckycGender);
            dataForDocument.put("pan", pan);
            dataForDocument.put("ckycOffTelNumber", ckycOffTelNumber);
            dataForDocument.put("ckycEmailAdd", ckycEmailAdd);
            dataForDocument.put("ckycMobileNumber", ckycMobileNumber);
            dataForDocument.put("ckycRemarks", ckycRemarks);
            dataForDocument.put("ckycKYCVerificationName", ckycKYCVerificationName);
            dataForDocument.put("ckycKYCVerificationBranch", ckycKYCVerificationBranch);
            dataForDocument.put("ckycResTelNumber",ckycResTelNumber);
            dataForDocument.put("ckycKYCVerificationEmpcode",ckycKYCVerificationEmpcode);
            dataForDocument.put("ckycKYCVerificationDate",ckycKYCVerificationDate);
            dataForDocument.put("ckycKYCVerificationDesg",ckycKYCVerificationDesg);
            dataForDocument.put("ckycPlaceofDeclaration",ckycPlaceofDeclaration);
            dataForDocument.put("ckycDateofDeclaration",ckycDateofDeclaration);
            dataForDocument.put("ckycTypeofDocSubmitted",ckycTypeofDocSubmitted);
            dataForDocument.put("ckycPerAdd1",ckycPerAdd1);
            dataForDocument.put("ckycPerAdd2",ckycPerAdd2);
            dataForDocument.put("ckycPerAdd3",ckycPerAdd3);
            dataForDocument.put("ckycPerAddCity",ckycPerAddCity);
            dataForDocument.put("ckycPerAddDistrict",ckycPerAddDistrict);
            dataForDocument.put("ckycPerAddState",ckycPerAddState);
            dataForDocument.put("ckycPerAddPin",ckycPerAddPin);
            dataForDocument.put("ckycPerAddPOA",ckycPerAddPOA);
            dataForDocument.put("ckycCorAdd1",ckycCorAdd1);
            dataForDocument.put("ckycCorAdd2",ckycCorAdd2);
            dataForDocument.put("ckycCorAdd3",ckycCorAdd3);
            dataForDocument.put("ckycCorAddCity",ckycCorAddCity);
            dataForDocument.put("ckycCorAddDistrict",ckycCorAddDistrict);
            dataForDocument.put("ckycCorAddState",ckycCorAddState);
            dataForDocument.put("ckycCorAddPin",ckycCorAddPin);
            dataForDocument.put("ckycCorAddPOA",ckycCorAddPOA);
            context.setVariables(dataForDocument);
        } catch (Exception ex) {
            logger.error("Data addition failed for Issuer Confirmation Document:", ex);
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Data addition failed");
            throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return context;
    }

}
