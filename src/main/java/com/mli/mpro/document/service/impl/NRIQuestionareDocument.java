package com.mli.mpro.document.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mli.mpro.proposal.models.*;
import com.mli.mpro.utils.Utility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;

import static org.thymeleaf.util.StringUtils.isEmpty;

@Component("nriQuestionareDocument")
@EnableAsync
public class NRIQuestionareDocument implements DocumentGenerationservice {

    private static final Logger logger = LoggerFactory.getLogger(NRIQuestionareDocument.class);

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private DocumentHelper documentHelper;

    /*
     * This is the main method which executes the process of NRI Questionare
     * document generation by calling necessary methods Here Spring Template
     * Engine is used to bind the data dynamically to the static HTML which is
     * stored in templates folder.
     */
    @Override
    @Async
    public void generateDocument(ProposalDetails proposalDetails) {

	long requestedTime = System.currentTimeMillis();
	String proposerNationality = proposalDetails.getPartyInformation().get(0).getBasicDetails().getNationalityDetails().getNationality();
	String insuredNationality = proposalDetails.getPartyInformation().get(1).getBasicDetails().getNationalityDetails().getNationality();
	String channelName = proposalDetails.getChannelDetails().getChannel();
	long transactionId = proposalDetails.getTransactionId();
	String communicationAddressCountry = null;
	String permanentAddressCountry = null;
	Address	currentAddressObj = null;
	Address permanentAddressObj = null;
	PartyInformation proposerInformation = proposalDetails.getPartyInformation().stream().filter(proposer -> proposer.getPartyType().equalsIgnoreCase(AppConstants.PROPOSER_PARTY_TYPE)).findFirst().orElse(null);
	if(proposerInformation!=null) {
		currentAddressObj = proposerInformation.getBasicDetails().getAddress().stream().filter(addType -> addType.getAddressType().equalsIgnoreCase(AppConstants.CURRENT_ADDRESS)).findFirst().orElse(null);
		permanentAddressObj = proposerInformation.getBasicDetails().getAddress().stream().filter(addType -> addType.getAddressType().equalsIgnoreCase(AppConstants.PERMANENT_ADDRESS)).findFirst().orElse(null);
	}

	communicationAddressCountry = Optional.ofNullable(currentAddressObj).map(Address::getAddressDetails).map(AddressDetails::getCountry).orElse(null);

	permanentAddressCountry = Optional.ofNullable(permanentAddressObj).map(Address::getAddressDetails).map(AddressDetails::getCountry).orElse(null);

	int retryCount = 0;
	String documentUploadStatus = "";
	DocumentStatusDetails documentStatusDetails = null;
	// JIRA_ID - F21-12 (FATCA changes) generating doc to collect info if customer's nationality is Non-Indian or his/her any address is other than India
	if (Utility.orTwoExpressions(	Utility.orTwoExpressions(Utility.andTwoExpressions(!StringUtils.isEmpty(proposerNationality) , !AppConstants.INDIAN_NATIONALITY.equalsIgnoreCase(proposerNationality))
			,  Utility.andTwoExpressions(!StringUtils.isEmpty(insuredNationality) , !AppConstants.INDIAN_NATIONALITY.equalsIgnoreCase(insuredNationality))	)
			,  Utility.orTwoExpressions(Utility.andTwoExpressions(!isEmpty(permanentAddressCountry) , !AppConstants.INDIA_COUNTRY.equalsIgnoreCase(permanentAddressCountry))
			,  Utility.andTwoExpressions(!isEmpty(communicationAddressCountry) , !AppConstants.INDIA_COUNTRY.equalsIgnoreCase(communicationAddressCountry)) )	)) {
	    try {
		Context context = setDataForDocument(proposalDetails);
		String processedHtml = springTemplateEngine.process("NRIQuestionare", context);
		logger.info("Data binding with HTML is done for transactionId {}", transactionId);
		long processedTime = (System.currentTimeMillis() - requestedTime);
		logger.info("Data binding with HTML for transactionId {} took {} miliseconds ", transactionId, processedTime);
		String encodedString = documentHelper.generatePDFDocument(processedHtml, retryCount);
		logger.info("HTML to pdf conversation is done for transactionId {}", transactionId);
		processedTime = (System.currentTimeMillis() - requestedTime);
		logger.info("HTML to pdf conversation for transactionId {} took {} miliseconds ", transactionId, processedTime);
		if (encodedString.equalsIgnoreCase(AppConstants.FAILED)) {
		    // update the document generation failure status in db
		    logger.info("Document generation is failed so updating in DB for transactionId {}", transactionId);
		    documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
			    proposalDetails.getApplicationDetails().getPolicyNumber(), proposalDetails.getSourcingDetails().getAgentId(),

			    AppConstants.DOCUMENT_GENERATION_FAILED, 0, AppConstants.NRI_QUESTIONAIRE, proposalDetails.getApplicationDetails().getStage());
		} else {
		    DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE, encodedString, AppConstants.NRI_QUESTIONAIRE);
		    List<DocumentRequestInfo> documentpayload = new ArrayList<>();
		    documentpayload.add(documentRequestInfo);
		    DocumentDetails documentDetails = new DocumentDetails(channelName, proposalDetails.getTransactionId(), "NRI_Pr", AppConstants.NRI_QUESTIONAIRE,
			    documentpayload);
			documentDetails.setThanosDolphinIntegrationEnabled(proposalDetails.isThanosDolphinIntegrationEnabled());
			documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, retryCount);
		    if (documentUploadStatus.equalsIgnoreCase("FAILED")) {
			// update the document upload failure status in db
			logger.info("Document upload is failed for transactionId {} {}", transactionId, AppConstants.NRI_QUESTIONARE);
			documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
				proposalDetails.getApplicationDetails().getPolicyNumber(), proposalDetails.getSourcingDetails().getAgentId(),
				AppConstants.DOCUMENT_UPLOAD_FAILED, 0, AppConstants.NRI_QUESTIONARE, proposalDetails.getApplicationDetails().getStage());
		    } else {
			logger.info("Document is successfully generated and uploaded to S3 for transactionId {} {}", transactionId, AppConstants.NRI_QUESTIONARE);
			// update the document upload success status in db
			documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
				proposalDetails.getApplicationDetails().getPolicyNumber(), proposalDetails.getSourcingDetails().getAgentId(),
				documentUploadStatus, 0, AppConstants.NRI_QUESTIONARE, proposalDetails.getApplicationDetails().getStage());
		    }
		}
	    } catch (UserHandledException ex) {
		logger.error("NRI Document generation failed:",ex);
		documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(), proposalDetails.getApplicationDetails().getPolicyNumber(),
			proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DATA_MISSING_FAILURE, 0, AppConstants.NRI_QUESTIONARE,
			proposalDetails.getApplicationDetails().getStage());
	    } catch (Exception ex) {
			logger.error("NRI Document generation failed:",ex);
			documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(), proposalDetails.getApplicationDetails().getPolicyNumber(),
			proposalDetails.getSourcingDetails().getAgentId(), AppConstants.TECHNICAL_FAILURE, 0, AppConstants.NRI_QUESTIONARE,
			proposalDetails.getApplicationDetails().getStage());
	    }
	    documentHelper.updateDocumentStatus(documentStatusDetails);
	    long processedTime = (System.currentTimeMillis() - requestedTime);
	    logger.info("NRI Questionare document for transactionId {} took {} miliseconds ", proposalDetails.getTransactionId(), processedTime);
	}

    }

    /*
     * This methods sets the data required for NRI Questionare document based on
     * criterias for given Proposal Details
     */
    private Context setDataForDocument(ProposalDetails proposalDetails) throws UserHandledException {
	Context context = new Context();
	try {

	    Map<String, Object> dataForDocument = new HashMap<>();
	    String formType = proposalDetails.getApplicationDetails().getFormType();
	    BasicDetails basicDetails = proposalDetails.getPartyInformation().get(0).getBasicDetails();
	    BasicDetails insuredBasicDetails = proposalDetails.getPartyInformation().get(1).getBasicDetails();
	    NRIDetails nriDetails = basicDetails.getNationalityDetails().getNriDetails();
	    NRIDetails insuredNridetails = insuredBasicDetails.getNationalityDetails().getNriDetails();
	    List<MultipleFTINDetailsForNRI> FTINDetails = null;
	    List<MultipleFTINDetailsForNRI> InsuredFTINDetails = null;
	    boolean isProposerFTIN = nriDetails.getFtinDetails().getIsFTINExist();
	    boolean isInsuredFTIN = insuredNridetails.getFtinDetails().getIsFTINExist();
	    String proposerVisaValid = Utility.dateFormatter(nriDetails.getVisaExpiryDate());
	    String proposerLatestEntry = Utility.dateFormatter(nriDetails.getRecentVisitDateToIndia());
	    String insuredVisaValid = Utility.dateFormatter(insuredNridetails.getVisaExpiryDate());
	    String insuredLatestEntry = Utility.dateFormatter(insuredNridetails.getRecentVisitDateToIndia());
	    String countriesVisited = "";
	    String insuredCountriesVisited = "";
	    AddressDetails proposerAddressDetails = basicDetails.getAddress().get(0).getAddressDetails();
	    AddressDetails proposerPermAddressDetails = basicDetails.getAddress().get(1).getAddressDetails();
	    for (int i = 0; i < nriDetails.getCountryVisitedFrequently().size(); i++) {
		countriesVisited = countriesVisited.concat(nriDetails.getCountryVisitedFrequently().get(i)).concat(" ");
	    }
	    for (int i = 0; i < insuredNridetails.getCountryVisitedFrequently().size(); i++) {
		insuredCountriesVisited = insuredCountriesVisited.concat(insuredNridetails.getCountryVisitedFrequently().get(i)).concat(" ");
	    }
	    String proposerPan = proposalDetails.getPartyInformation().get(0).getPersonalIdentification().getPanDetails().getPanNumber();
	    if (StringUtils.isEmpty(proposerPan)) {
		proposerPan = "N/A";
	    }
	    String houseNo = proposerAddressDetails.getHouseNo();
	    String area = proposerAddressDetails.getArea();
	    String village = proposerAddressDetails.getVillage();
	    String landmark = proposerAddressDetails.getLandmark();
	    String city = proposerAddressDetails.getCity();
	    String state = proposerAddressDetails.getState();
	    String country = proposerAddressDetails.getCountry();
	    String pinCode = proposerAddressDetails.getPinCode();
	    String commAddress = Stream.of(houseNo, area, village, landmark, city, state, country, pinCode).filter(s -> !StringUtils.isEmpty(s))
		    .collect(Collectors.joining(", "));
	    String permHouseNo = proposerPermAddressDetails.getHouseNo();
	    String permArea = proposerPermAddressDetails.getArea();
	    String permVillage = proposerPermAddressDetails.getVillage();
	    String permLandmark = proposerPermAddressDetails.getLandmark();
	    String permCity = proposerPermAddressDetails.getCity();
	    String permState = proposerPermAddressDetails.getState();
	    String pemrCcountry = proposerPermAddressDetails.getCountry();
	    String permPinCode = proposerPermAddressDetails.getPinCode();
	    String permAddress = Stream.of(permHouseNo, permArea, permVillage, permLandmark, permCity, permState, pemrCcountry, permPinCode)
		    .filter(s -> !StringUtils.isEmpty(s)).collect(Collectors.joining(", "));
	    String proposerPassport = nriDetails.getPassportNumber();
	    String typeOfVisa = nriDetails.getTypeOfVisa();
	    String proposerCountryOfRes = nriDetails.getCurrentCountryOfResidence();
	    String proposerIssuingCountry = nriDetails.getPassportIssuingCountry();
	    String proposerUID = nriDetails.getFtinDetails().getIdentificationNumber();
	    String issuingCountry = nriDetails.getIssuingCountry();
	    String resdientCountry = nriDetails.getCountryOfResidenceAsPerTaxLaw();
	    String identificationType = nriDetails.getFtinDetails().getTypeOfForeignIdentification();
	    String proposerCountryOfBirth = nriDetails.getCountryOfBirth();
	    String insuredCountryOfBirth = "N/A";

	    String insuredPassport = insuredNridetails.getPassportNumber();
	    String insuredtypeOfVisa = insuredNridetails.getTypeOfVisa();
	    String insuredCountryOfRes = insuredNridetails.getCurrentCountryOfResidence();
	    String insuredIssuingCountry = insuredNridetails.getPassportIssuingCountry();
	    String insuredUID = insuredNridetails.getFtinDetails().getIdentificationNumber();
	    String insuredissuingCountry = insuredNridetails.getIssuingCountry();
	    String insuredresident = insuredNridetails.getCountryOfResidenceAsPerTaxLaw();
	    String insuredIdentificationType = insuredNridetails.getFtinDetails().getTypeOfForeignIdentification();
	    String insuredCommAdd = "N/A";
	    String insuredPermadd = "N/A";
	    String insuredPrimary = "N/A";
	    String insuredAlt = "N/A";
	    String insuredNationality = "N/A";
	    if (AppConstants.DEPENDENT.equalsIgnoreCase(formType)) {
		insuredCommAdd = commAddress;
		insuredPermadd = permAddress;
		insuredPrimary = proposalDetails.getPartyInformation().get(0).getPersonalIdentification().getPhone().get(0).getPhoneNumber();
		insuredAlt = proposalDetails.getPartyInformation().get(0).getPersonalIdentification().getPhone().get(1).getPhoneNumber();
		insuredNationality = insuredBasicDetails.getNationalityDetails().getNationality().toUpperCase();
		insuredCountryOfBirth = insuredNridetails.getCountryOfBirth();
	    }
		if(AppConstants.ANNUITY_PRODUCTS.contains(proposalDetails.getProductDetails().get(0).getProductInfo().getProductId())){
			insuredCommAdd = commAddress;
			insuredPermadd = permAddress;
			insuredPrimary = proposalDetails.getPartyInformation().get(0).getPersonalIdentification().getPhone().get(0).getPhoneNumber();
			insuredAlt = proposalDetails.getPartyInformation().get(0).getPersonalIdentification().getPhone().get(1).getPhoneNumber();
			insuredNationality = insuredBasicDetails.getNationalityDetails().getNationality().toUpperCase();
			insuredCountryOfBirth = insuredNridetails.getCountryOfBirth();
		}
	    if (isProposerFTIN) {
		FTINDetails = nriDetails.getFtinDetails().getMultipleFTINDetailsForNRI();
		dataForDocument.put("FTINLength", FTINDetails);
	    } else {
		dataForDocument.put("NONFTIN", true);
	    }
	    if (isInsuredFTIN) {
		InsuredFTINDetails = insuredNridetails.getFtinDetails().getMultipleFTINDetailsForNRI();
		dataForDocument.put("FTINLengthInsured", InsuredFTINDetails);
	    } else {
		dataForDocument.put("InsuredNONFTIN", true);
	    }
	    dataForDocument.put("ProposerPassport",Utility.evaluateConditionalOperation( StringUtils.isEmpty(proposerPassport) , "N/A" , proposerPassport));
	    dataForDocument.put("ProposerTypeOfVisa",Utility.evaluateConditionalOperation( StringUtils.isEmpty(typeOfVisa) , "N/A" , typeOfVisa));
	    dataForDocument.put("ProposerVisaValid",Utility.evaluateConditionalOperation( StringUtils.isEmpty(proposerVisaValid) , "N/A" , proposerVisaValid));
	    dataForDocument.put("ProposerCurrentCountry",Utility.evaluateConditionalOperation( StringUtils.isEmpty(proposerCountryOfRes) , "N/A" , proposerCountryOfRes));
	    dataForDocument.put("ProposerLatestEntry", Utility.evaluateConditionalOperation(StringUtils.isEmpty(proposerLatestEntry) , "N/A" , proposerLatestEntry));
	    dataForDocument.put("CountriesVisited",Utility.evaluateConditionalOperation( StringUtils.isEmpty(countriesVisited) , "N/A" , countriesVisited));
	    dataForDocument.put("ProposerNationality", basicDetails.getNationalityDetails().getNationality().toUpperCase());
	    dataForDocument.put("ProposerCountry", proposerCountryOfBirth);
	    dataForDocument.put("ProposerIssuing",Utility.evaluateConditionalOperation( StringUtils.isEmpty(proposerIssuingCountry) , "N/A" , proposerIssuingCountry));
	    dataForDocument.put("CoummunicationAddress1", commAddress);
	    dataForDocument.put("PermanentAddress1", permAddress);
	    dataForDocument.put("ProposerPrimary", proposalDetails.getPartyInformation().get(0).getPersonalIdentification().getPhone().get(0).getPhoneNumber());
	    dataForDocument.put("ProposerAlternative",
		    proposalDetails.getPartyInformation().get(0).getPersonalIdentification().getPhone().get(1).getPhoneNumber());
	    dataForDocument.put("ProposerPAN", proposerPan);
	    dataForDocument.put("ProposerUID",Utility.evaluateConditionalOperation( StringUtils.isEmpty(proposerUID) , "N/A" , proposerUID));
	    dataForDocument.put("ProposerUIDCountry", Utility.evaluateConditionalOperation(StringUtils.isEmpty(issuingCountry) , "N/A" , issuingCountry));
	    dataForDocument.put("ProposerResidentCountry", Utility.evaluateConditionalOperation(StringUtils.isEmpty(resdientCountry) , "N/A" , resdientCountry));
	    dataForDocument.put("IdentificationType",Utility.evaluateConditionalOperation( StringUtils.isEmpty(identificationType) , "N/A" , identificationType));
	    dataForDocument.put("IdentificationNumber",Utility.evaluateConditionalOperation( StringUtils.isEmpty(proposerUID) , "N/A" , proposerUID));
	    dataForDocument.put("IssuingCountry", Utility.evaluateConditionalOperation(StringUtils.isEmpty(issuingCountry) , "N/A" , issuingCountry));

	    // insuredDetails
	    dataForDocument.put("InsuredPassport",Utility.evaluateConditionalOperation( StringUtils.isEmpty(insuredPassport) , "N/A" , insuredPassport));
	    dataForDocument.put("InsuredTypeOfVisa",Utility.evaluateConditionalOperation( StringUtils.isEmpty(insuredtypeOfVisa) , "N/A" , insuredtypeOfVisa));
	    dataForDocument.put("InsuredVisaValid",Utility.evaluateConditionalOperation( StringUtils.isEmpty(insuredVisaValid) , "N/A" , insuredVisaValid));
	    dataForDocument.put("InsuredCurrentCountry", Utility.evaluateConditionalOperation(StringUtils.isEmpty(insuredCountryOfRes) , "N/A" , insuredCountryOfRes));
	    dataForDocument.put("InsuredLatestEntry",Utility.evaluateConditionalOperation( StringUtils.isEmpty(insuredLatestEntry) , "N/A" , insuredLatestEntry));
	    dataForDocument.put("InsuredCountriesVisited",Utility.evaluateConditionalOperation( StringUtils.isEmpty(insuredCountriesVisited) , "N/A" , insuredCountriesVisited));
	    dataForDocument.put("InsuredNationality", insuredNationality);
	    dataForDocument.put("InsuredCountry", insuredCountryOfBirth);
	    dataForDocument.put("InsuredIssuing", Utility.evaluateConditionalOperation(StringUtils.isEmpty(insuredIssuingCountry) , "N/A" , insuredIssuingCountry));
	    dataForDocument.put("CoummunicationAddress2", insuredCommAdd);
	    dataForDocument.put("PermanentAddress2", insuredPermadd);
	    dataForDocument.put("InsuredPrimary", insuredPrimary);
	    dataForDocument.put("InsuredAlternative", insuredAlt);
	    dataForDocument.put("InsuredPAN", "N/A");
	    dataForDocument.put("InsuredUID",Utility.evaluateConditionalOperation( StringUtils.isEmpty(insuredUID) , "N/A" , insuredUID));
	    dataForDocument.put("InsuredUIDCountry",Utility.evaluateConditionalOperation( StringUtils.isEmpty(insuredissuingCountry) , "N/A" , insuredissuingCountry));
	    dataForDocument.put("InsuredResidentCountry", Utility.evaluateConditionalOperation(StringUtils.isEmpty(insuredresident) , "N/A" , insuredresident));
	    dataForDocument.put("InsuredIdentificationType", Utility.evaluateConditionalOperation(StringUtils.isEmpty(insuredIdentificationType) , "N/A" , insuredIdentificationType));
	    dataForDocument.put("InsuredIdentificationNumber",Utility.evaluateConditionalOperation( StringUtils.isEmpty(insuredUID) , "N/A" , insuredUID));
	    dataForDocument.put("InsuredIssuingCountry", Utility.evaluateConditionalOperation(StringUtils.isEmpty(insuredissuingCountry) , "N/A" , insuredissuingCountry));
			dataForDocument.put(AppConstants.IS_NOT_YBL_PROPOSAL, !Utility.isYBLProposal(proposalDetails));
	    context.setVariables(dataForDocument);
	} catch (Exception ex) {
		logger.error("Data addition failed for NRI Document:",ex);
	    List<String> errorMessages = new ArrayList<String>();
	    errorMessages.add("Data addition failed");
	    throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	return context;
    }
}
