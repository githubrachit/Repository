package com.mli.mpro.document.mapper;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.proposal.models.BasicDetails;
import com.mli.mpro.proposal.models.NRIDetails;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.*;

@SuppressWarnings("DuplicatedCode")
@Service
public class NeoNriQuestionnaireMapper {

    private static final Logger logger = LoggerFactory.getLogger(NeoNriQuestionnaireMapper.class);
    public static final String PROPOSER = "proposer";

    public Context setDocumentData(ProposalDetails proposalDetails) throws UserHandledException {
        logger.info("START NeoNRI Questionnaire Data population");
        Map<String, Object> dataMap = new HashMap<>();

        String proposalNumber = "";
        String place = "";
        String date = "";

        proposalNumber = Utility.nullSafe(proposalDetails.getApplicationDetails().getPolicyNumber());
        place = "";
        date = Utility.dateFormatter(new Date());

        try {
            if(Utility.isApplicationIsForm2(proposalDetails)){
                Utility.changeProposerAndLifeinsuredForForm2(proposalDetails);
            }
            if (Utility.checkNriParty(proposalDetails,0))
                setProposerData(dataMap, proposalDetails);
            if ((isPartyProposer(proposalDetails) && (Utility.isProductSWPJL(proposalDetails) || Utility.isApplicationIsForm2(proposalDetails) || Utility.isSSPJLProduct(proposalDetails)))
                    && Utility.checkNriParty(proposalDetails,1)) {
                setInsuredData(dataMap, proposalDetails);
            }
            dataMap.put("proposalNumber", proposalNumber);
            dataMap.put("place", place);
            dataMap.put("date", date);

        } catch (Exception ex) {
            logger.error("Data addition failed for NRI Questionnaire Document:", ex);
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Data addition failed");
            throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Context nriDetailsContext = new Context();
        nriDetailsContext.setVariables(dataMap);
        logger.info("END NeoNRI Questionnaire Data population");
        return nriDetailsContext;
    }

    private boolean isPartyProposer(ProposalDetails proposalDetails) {
        if (Utility.isApplicationIsForm2(proposalDetails)) {
            return proposalDetails.getPartyInformation().size() > 1
                 && PROPOSER.equalsIgnoreCase(proposalDetails.getPartyInformation().get(0).getPartyType());
        }
        return proposalDetails.getPartyInformation().size() > 1
                && PROPOSER.equalsIgnoreCase(proposalDetails.getPartyInformation().get(1).getPartyType());
    }

    private void setProposerData(Map<String, Object> dataMap, ProposalDetails proposalDetails) {

        BasicDetails proposerBasicDetails = proposalDetails.getPartyInformation().get(0).getBasicDetails();

        String proposerName = "";
        String insuredName = "";
        String proposerNationality = "";
        String proposerPassportNumber = "";
        String proposerIssueDate = "";
        String proposerPlaceOfIssue = "";
        String proposerTypeOfVisa = "";
        String proposerVisaValidTill = "";
        String proposerCountryOfResidence = "";
        String proposerLatestVisitToIndia = "";
        String proposerFrequentlyVisitedCountry = "";
        String proposerBankDetails = "";
        String proposerSignature = "";
        String proposerCitizenship = "";
        String proposerCityOfBirth = "";

        NRIDetails proposerNriDetails = proposerBasicDetails.getNationalityDetails().getNriDetails();

        String name = String.join(" ",
                Utility.nullSafe(proposerBasicDetails.getFirstName()),
                Utility.nullSafe(proposerBasicDetails.getMiddleName()),
                Utility.nullSafe(proposerBasicDetails.getLastName()));
        if (Utility.isSSPJLProduct(proposalDetails) || Utility.isProductSWPJL(proposalDetails) || Utility.isApplicationIsForm2(proposalDetails)){
            proposerName = name;
        } else {
            insuredName = name;
        }
        proposerNationality = Utility.nullSafe(proposerBasicDetails.getNationalityDetails().getNationality());
        proposerPassportNumber = Utility.nullSafe(proposerNriDetails.getPassportNumber());
        proposerIssueDate = "";
        proposerPlaceOfIssue = Utility.nullSafe(proposerNriDetails.getPassportIssuingCountry());
        proposerTypeOfVisa = Utility.nullSafe(proposerNriDetails.getTypeOfVisa());
        proposerVisaValidTill = Utility.nullSafe(Utility.dateFormatter(proposerNriDetails.getVisaExpiryDate()));
        proposerCountryOfResidence = Utility.nullSafe(proposerNriDetails.getCurrentCountryOfResidence());
        proposerLatestVisitToIndia = Utility.nullSafe(Utility.dateFormatter(proposerNriDetails.getRecentVisitDateToIndia()));
        proposerFrequentlyVisitedCountry = Utility.nullSafe(String.join(",", proposerNriDetails.getCountryVisitedFrequently()));
        proposerCitizenship = Utility.nullSafe(proposerNriDetails.getCitizenship());
        proposerCityOfBirth = Utility.nullSafe(proposerNriDetails.getCityOfBirth());
        proposerSignature = name;

        dataMap.put("proposerName", proposerName);
        dataMap.put("insuredName", insuredName);
        dataMap.put("proposerNationality", proposerNationality);
        dataMap.put("proposerPassportNumber", proposerPassportNumber);
        dataMap.put("proposerIssueDate", proposerIssueDate);
        dataMap.put("proposerPlaceOfIssue", proposerPlaceOfIssue);
        dataMap.put("proposerTypeOfVisa", proposerTypeOfVisa);
        dataMap.put("proposerVisaValidTill", proposerVisaValidTill);
        dataMap.put("proposerCountryOfResidence", proposerCountryOfResidence);
        dataMap.put("proposerLatestVisitToIndia", proposerLatestVisitToIndia);
        dataMap.put("proposerFrequentlyVisitedCountry", proposerFrequentlyVisitedCountry);
        dataMap.put("proposerBankDetails", proposerBankDetails);
        dataMap.put("proposerSignature", proposerSignature);
        dataMap.put("proposerCitizenship", proposerCitizenship);
        dataMap.put("proposerCityOfBirth", proposerCityOfBirth);
    }

    private void setInsuredData(Map<String, Object> dataMap, ProposalDetails proposalDetails) {


        String insuredName = "";
        String insuredNationality = "";
        String insuredPassportNumber = "";
        String insuredIssueDate = "";
        String insuredPlaceOfIssue = "";
        String insuredTypeOfVisa = "";
        String insuredVisaValidTill = "";
        String insuredCountryOfResidence = "";
        String insuredLatestVisitToIndia = "";
        String insuredFrequentlyVisitedCountry = "";
        String insuredBankDetails = "";
        String insuredSignature = "";
        String insuredCitizenship = "";
        String insuredCityOfBirth = "";

        BasicDetails insuredBasicDetails = proposalDetails.getPartyInformation().get(1).getBasicDetails();
        NRIDetails insuredNriDetails = insuredBasicDetails.getNationalityDetails().getNriDetails();
        insuredName = String.join(" ",
                Utility.nullSafe(insuredBasicDetails.getFirstName()),
                Utility.nullSafe(insuredBasicDetails.getMiddleName()),
                Utility.nullSafe(insuredBasicDetails.getLastName()));
        insuredNationality = Utility.nullSafe(insuredBasicDetails.getNationalityDetails().getNationality());
        insuredPassportNumber = Utility.nullSafe(insuredNriDetails.getPassportNumber());
        insuredIssueDate = "";
        insuredPlaceOfIssue = Utility.nullSafe(insuredNriDetails.getPassportIssuingCountry());
        insuredTypeOfVisa = Utility.nullSafe(insuredNriDetails.getTypeOfVisa());
        insuredVisaValidTill = Utility.nullSafe(Utility.dateFormatter(insuredNriDetails.getVisaExpiryDate()));
        insuredCountryOfResidence = Utility.nullSafe(insuredNriDetails.getCurrentCountryOfResidence());
        insuredLatestVisitToIndia = Utility.nullSafe(Utility.dateFormatter(insuredNriDetails.getRecentVisitDateToIndia()));
        insuredFrequentlyVisitedCountry = Utility.nullSafe(String.join(",", insuredNriDetails.getCountryVisitedFrequently()));
        insuredCitizenship = Utility.nullSafe(insuredNriDetails.getCitizenship());
        insuredCityOfBirth = Utility.nullSafe(insuredNriDetails.getCityOfBirth());
        insuredSignature = insuredName;

        dataMap.put("insuredName", insuredName);
        dataMap.put("insuredNationality", insuredNationality);
        dataMap.put("insuredPassportNumber", insuredPassportNumber);
        dataMap.put("insuredIssueDate", insuredIssueDate);
        dataMap.put("insuredPlaceOfIssue", insuredPlaceOfIssue);
        dataMap.put("insuredTypeOfVisa", insuredTypeOfVisa);
        dataMap.put("insuredVisaValidTill", insuredVisaValidTill);
        dataMap.put("insuredCountryOfResidence", insuredCountryOfResidence);
        dataMap.put("insuredLatestVisitToIndia", insuredLatestVisitToIndia);
        dataMap.put("insuredFrequentlyVisitedCountry", insuredFrequentlyVisitedCountry);
        dataMap.put("insuredBankDetails", insuredBankDetails);
        dataMap.put("insuredSignature", insuredSignature);
        dataMap.put("insuredCitizenship", insuredCitizenship);
        dataMap.put("insuredCityOfBirth", insuredCityOfBirth);
    }
}
