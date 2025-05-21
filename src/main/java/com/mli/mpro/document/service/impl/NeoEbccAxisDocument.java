package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.*;
import com.mli.mpro.utils.Utility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.context.Context;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component("neoEbccAxisDocument")
public class NeoEbccAxisDocument extends NeoBaseDocument implements DocumentGenerationservice {

    public static final String YES_VALUE = "Yes";
    public static final String MODIFIED_VALUE = "Modified";

    private static final Logger logger = LoggerFactory.getLogger(NeoEbccAxisDocument.class);

    @Override
    public void generateDocument(ProposalDetails proposalDetails) {


        long requestedTime = System.currentTimeMillis();
        int retryCount = 0;
        long transactionId = proposalDetails.getTransactionId();
        DocumentStatusDetails documentStatusDetails = null;
            try {
                Context context = setDataForDocument(proposalDetails);
                String fileName = AppConstants.BANK_JOURNEY_AU.equalsIgnoreCase(proposalDetails.getBankJourney())
                        ? "neo\\au\\EbccAu4.html" : "neo\\axis\\EbccAxis.html";
                generateBaseDoc(proposalDetails,context,transactionId,retryCount,requestedTime, fileName);
                if (encodedString.equalsIgnoreCase(AppConstants.FAILED)) {
                    // update the document generation failure status in db
                    logger.info("Document generation is failed so updating in DB for transactionId {}", transactionId);
                    documentStatusDetails = new DocumentStatusDetails(transactionId,
                            proposalDetails.getApplicationDetails().getPolicyNumber(),
                            proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DOCUMENT_GENERATION_FAILED,
                            0, AppConstants.EBCC_AXIS, proposalDetails.getApplicationDetails().getStage());
                } else {
                    documentStatusDetails = saveGeneratedDocumentToS3(proposalDetails, encodedString, 0);
                }

            } catch (UserHandledException ex) {
                logger.error("Neo ebcc axis document generation failed:", ex);
                documentStatusDetails = documentHelper.populateDocumentStatusObj(true, proposalDetails, proposalDetails.getTransactionId(),
                        AppConstants.DATA_MISSING_FAILURE, AppConstants.EBCC_AXIS);
            } catch (Exception ex) {
                logger.error("Neo ebcc axis Document generation failed:", ex);
                documentStatusDetails = documentHelper.populateDocumentStatusObj(true, proposalDetails, proposalDetails.getTransactionId(),
                        AppConstants.TECHNICAL_FAILURE, AppConstants.EBCC_AXIS);
            }
            documentHelper.updateDocumentStatus(documentStatusDetails);
            long processedTime = (System.currentTimeMillis() - requestedTime);
            logger.info("Neo ebcc axis document is generated for transactionId {} took {} milliseconds ", proposalDetails.getTransactionId(), processedTime);


    }

    protected DocumentStatusDetails saveGeneratedDocumentToS3(ProposalDetails proposalDetails, String pdfDocumentOrDocumentStatus, int retryCount) {
        DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(
                AppConstants.DOCUMENT_TYPE,
                pdfDocumentOrDocumentStatus,
                AppConstants.EBCC_AXIS);
        List<DocumentRequestInfo> documentPayload = new ArrayList<>();
        documentPayload.add(documentRequestInfo);
        DocumentDetails documentDetails = new DocumentDetails(proposalDetails.getChannelDetails().getChannel(), proposalDetails.getTransactionId(),
                "EBCC_AXIS_PDF", AppConstants.EBCC_AXIS, documentPayload);

        String documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, retryCount);
        if (AppConstants.FAILED.equalsIgnoreCase(documentUploadStatus)) {
            // update the document upload failure status in DB
            logger.info("Document upload is failed for transactionId {} {}", proposalDetails.getTransactionId(), AppConstants.EBCC_AXIS);
            return documentHelper.populateDocumentStatusObj(true, proposalDetails, proposalDetails.getTransactionId(),
                    AppConstants.DOCUMENT_UPLOAD_FAILED, AppConstants.EBCC_AXIS);
        } else {
            // update the document upload success status in DB
            logger.info("Document is successfully uploaded to S3 for transactionId {} {}", proposalDetails.getTransactionId(),
                    AppConstants.EBCC_AXIS);
            return documentHelper.populateDocumentStatusObj(true, proposalDetails, proposalDetails.getTransactionId(),
                    documentUploadStatus, AppConstants.EBCC_AXIS);
        }
    }

    private Context setDataForDocument(ProposalDetails proposalDetails) throws UserHandledException {

        Context context = new Context();
        logger.info("Data Mapping is initiated for transactionId {}", proposalDetails.getTransactionId());
        try {
            Map<String, Object> dataForDocument = new HashMap<>();
            BasicDetails basicDetails = proposalDetails.getPartyInformation().get(0).getBasicDetails();;
            AddressDetails addressDetails = basicDetails.getAddress().get(0).getAddressDetails();
            String ebccAddModify = "";
            String ebccDobModify = "";
            String ebccGenderModify = "";
            String ebccNameModify = "";
            String ebccPanModify = "";
            String customerId = "";
            String kycNo = "No";
            String gender = basicDetails.getGender();
            String tag = "MR";
            if (gender.equals("F")) {
                tag = "MS";
            } else if (gender.equalsIgnoreCase("Others")) {
                tag = "MX";
            }

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy HH:mm a");
            SimpleDateFormat dateformatter = new SimpleDateFormat("dd/MM/yyyy");

            formatter.setTimeZone(TimeZone.getTimeZone(AppConstants.APP_TIMEZONE));
            dateformatter.setTimeZone(TimeZone.getTimeZone(AppConstants.APP_TIMEZONE));

            String panCard = StringUtils.EMPTY;


            String firstName = basicDetails.getFirstName();
            String middleName = basicDetails.getMiddleName();
            String lastName = basicDetails.getLastName();
            String customerName = Stream.of(firstName, middleName, lastName).filter(s -> !StringUtils.isEmpty(s))
                    .collect(Collectors.joining(" "));
            Date dateOfBirth = basicDetails.getDob();
            String dob = dateformatter.format(dateOfBirth);
            String todaysDate = formatter.format(new Date());
            if (!StringUtils.isEmpty(proposalDetails.getPartyInformation().get(0).getPersonalIdentification()
                    .getPanDetails().getPanNumber())) {
                panCard = proposalDetails.getPartyInformation().get(0).getPersonalIdentification().getPanDetails()
                        .getPanNumber().toUpperCase();
            }

            String communicationAddress = getAddress(addressDetails.getHouseNo(), addressDetails.getArea(),
                    addressDetails.getLandmark(), addressDetails.getPinCode(), addressDetails.getCity(),
                    addressDetails.getState(), addressDetails.getCountry());
            if (proposalDetails.getAxisBancaDetails() != null) {
                BancaDetails axisBancaDetails = proposalDetails.getAxisBancaDetails();
                if (axisBancaDetails.getEbccFlag().equalsIgnoreCase(YES_VALUE)) {
                    ebccAddModify = YES_VALUE.equalsIgnoreCase(axisBancaDetails.getEbccAddModify()) ? MODIFIED_VALUE : "";
                    ebccDobModify = YES_VALUE.equalsIgnoreCase(axisBancaDetails.getEbccDobModify())? MODIFIED_VALUE : "";
                    ebccGenderModify = YES_VALUE.equalsIgnoreCase(axisBancaDetails.getEbccGenderModify()) ? MODIFIED_VALUE : "";
                    ebccNameModify = YES_VALUE.equalsIgnoreCase(axisBancaDetails.getEbccNameModify()) ? MODIFIED_VALUE : "";
                    ebccPanModify = YES_VALUE.equalsIgnoreCase(axisBancaDetails.getEbccPanModify())? MODIFIED_VALUE : "";
                }
                customerId = axisBancaDetails.getAxisLeadId();
                kycNo = axisBancaDetails.getEbccFlag();
            }
            dataForDocument.put("kyc",kycNo );
            dataForDocument.put("customerId", customerId);
            dataForDocument.put("customerName", customerName);
            dataForDocument.put("title", "Sir/Madam");
            dataForDocument.put("todaysDate", todaysDate);
            dataForDocument.put("tagName", tag);
            dataForDocument.put("customerDob", dob);
            dataForDocument.put("customergender", gender);
            dataForDocument.put("customerPan", panCard);
            dataForDocument.put("gender", gender);
            dataForDocument.put("commuAddress", communicationAddress);
            dataForDocument.put("ebccAddModify", ebccAddModify);
            dataForDocument.put("ebccDobModify", ebccDobModify);
            dataForDocument.put("ebccPanModify", ebccPanModify);
            dataForDocument.put("ebccGenderModify", ebccGenderModify);
            dataForDocument.put("ebccNameModify", ebccNameModify);
            dataForDocument.put(AppConstants.IS_NOT_YBL_PROPOSAL, !Utility.isYBLProposal(proposalDetails));
            setPermanentAddressForAuBank(dataForDocument, basicDetails, proposalDetails);
            context.setVariables(dataForDocument);
        } catch (Exception ex) {
            logger.error("Data addition failed for Issuer Confirmation Document:", ex);
            List<String> errorMessages = new ArrayList<String>();
            errorMessages.add("Data addition failed");
            throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return context;
    }

    private void setPermanentAddressForAuBank(Map<String, Object> dataForDocument, BasicDetails basicDetails, ProposalDetails proposalDetails) {
        try {
            if (!AppConstants.BANK_JOURNEY_AU.equalsIgnoreCase(proposalDetails.getBankJourney()))
                return;
            Address addressPermanent = getPermanentAddress(basicDetails, proposalDetails.getTransactionId());
            if (Objects.nonNull(addressPermanent) && AppConstants.NEO_NO.equalsIgnoreCase(addressPermanent.getIsPRASameAsCRA())) {
                AddressDetails addressDetailsPermanent = addressPermanent.getAddressDetails();
                String permanentAddress = getAddress(addressDetailsPermanent.getHouseNo(), addressDetailsPermanent.getArea(),
                        addressDetailsPermanent.getLandmark(), addressDetailsPermanent.getPinCode(), addressDetailsPermanent.getCity(),
                        addressDetailsPermanent.getState(), addressDetailsPermanent.getCountry());
                dataForDocument.put("permaAddress", permanentAddress);
                dataForDocument.put("permanentAddressPresent", AppConstants.YES);
                if (Objects.nonNull(proposalDetails.getAxisBancaDetails())) {
                    String customerId = StringUtils.isNotEmpty(proposalDetails.getAxisBancaDetails().getCustomerID())
                            ? proposalDetails.getAxisBancaDetails().getCustomerID() : "";
                    dataForDocument.put("customerId", customerId);
                } else {
                    logger.info("axisBancaDetails object is null for transactionId: {}", proposalDetails.getTransactionId());
                }
            }
        } catch (Exception e) {
            logger.info("Exception while generation au ebcc doc for transactionId: {} , Exception: {}",
                    proposalDetails.getTransactionId(), Utility.getExceptionAsString(e));
        }
    }

    private Address getPermanentAddress(BasicDetails basicDetails, long transactionId) {
        Address address = null;
        try {
            if (!CollectionUtils.isEmpty(basicDetails.getAddress()) && basicDetails.getAddress().size() > 1) {
                address = basicDetails.getAddress().get(1);
            } else {
                logger.info("Permanent address is null for transactionId {}", transactionId);
            }
        } catch (Exception e) {
            logger.error("Exception while reading address for transactionId {} , Exception: {}", transactionId, Utility.getExceptionAsString(e));
        }
        return address;
    }

    private String getAddress(String add1, String add2, String add3, String pincode, String city, String state,
                              String country) {
        return String.join(" ", add1, add2, add3, city, state, country, pincode);
    }
}
