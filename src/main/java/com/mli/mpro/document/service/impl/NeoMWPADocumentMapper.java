package com.mli.mpro.document.service.impl;

import com.mli.mpro.proposal.models.AddressDetails;
import com.mli.mpro.proposal.models.BasicDetails;
import com.mli.mpro.proposal.models.PartyDetails;
import com.mli.mpro.proposal.models.ProposalDetails;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.context.Context;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class NeoMWPADocumentMapper {

    private static final Logger logger = LoggerFactory.getLogger(NeoMWPADocumentMapper.class);

    public Context setMWPADetails(ProposalDetails proposalDetails) {
        logger.info("Recieved request for MWPA document mapper {}", proposalDetails);
        Context context = new Context();
        List<Map<String, Object>> beneficiariesList= new ArrayList<>();
        Map<String, Object> dataVariables= new HashMap<>();
        String policyNumber = StringUtils.EMPTY;
        String otpDateTimeStamp = StringUtils.EMPTY;
        String firstNameofProposer = StringUtils.EMPTY;
        String middleNameofProposer = StringUtils.EMPTY;
        String lastNameofProposer = StringUtils.EMPTY;
        String houseno = StringUtils.EMPTY;
        String road = StringUtils.EMPTY;
        String landMark = StringUtils.EMPTY;
        String city = StringUtils.EMPTY;
        String state = StringUtils.EMPTY;
        String pinCode = StringUtils.EMPTY;

        if (Objects.nonNull(proposalDetails.getApplicationDetails())){
            policyNumber = proposalDetails.getApplicationDetails().getPolicyNumber();
            otpDateTimeStamp = proposalDetails.getApplicationDetails().getOtpDateTimeStamp();
        }
        if (!CollectionUtils.isEmpty(proposalDetails.getPartyInformation()) && Objects.nonNull(proposalDetails.getPartyInformation().get(0).getBasicDetails())){
            BasicDetails basicDetails = proposalDetails.getPartyInformation().get(0).getBasicDetails();
            firstNameofProposer = basicDetails.getFirstName();
            middleNameofProposer = basicDetails.getMiddleName();
            lastNameofProposer = basicDetails.getLastName();
            if (!CollectionUtils.isEmpty(basicDetails.getAddress()) && Objects.nonNull(basicDetails.getAddress().get(0).getAddressDetails())){
                AddressDetails addressDetails = basicDetails.getAddress().get(0).getAddressDetails();
                houseno = addressDetails.getHouseNo();
                road = addressDetails.getArea();
                landMark = addressDetails.getLandmark();
                city = addressDetails.getCity();
                state = addressDetails.getState();
                pinCode = addressDetails.getPinCode();
            }
        }

        if (Objects.nonNull(proposalDetails.getNomineeDetails()) && !CollectionUtils.isEmpty(proposalDetails.getNomineeDetails().getPartyDetails())) {
            for (PartyDetails partyDetails : proposalDetails.getNomineeDetails().getPartyDetails()) {
                Map<String, Object> beneficiaryData= new HashMap<>();
                OffsetDateTime dateTime = partyDetails.getDob().toInstant().atOffset(ZoneId.of("Asia/Kolkata").getRules().getOffset(partyDetails.getDob().toInstant()));
                String dateOfBirth = dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                beneficiaryData.put("title", !StringUtils.isEmpty(partyDetails.getSalutation()) ? partyDetails.getSalutation() : "");
                beneficiaryData.put("firstName", !StringUtils.isEmpty(partyDetails.getFirstName()) ? partyDetails.getFirstName(): "");
                beneficiaryData.put("middleName", !StringUtils.isEmpty(partyDetails.getMiddleName()) ? partyDetails.getMiddleName(): "");
                beneficiaryData.put("lastName", !StringUtils.isEmpty(partyDetails.getLastName()) ? partyDetails.getLastName(): "");
                beneficiaryData.put("relationship", !StringUtils.isEmpty(partyDetails.getRelationshipWithProposer()) ? partyDetails.getRelationshipWithProposer() : "");
                beneficiaryData.put("relationshipOthers", !StringUtils.isEmpty(partyDetails.getRelationshipOthers()) ? partyDetails.getRelationshipOthers() : "");
                beneficiaryData.put("dobofBeneficiary", !StringUtils.isEmpty(dateOfBirth) ? dateOfBirth : "");
                beneficiaryData.put("percentageShare", partyDetails.getPercentageShare() > 0 ? partyDetails.getPercentageShare() : "");
                beneficiaryData.put("nationality", "");
                beneficiaryData.put("bankAccountNumber","");
                beneficiaryData.put("accountHolderName","");
                beneficiaryData.put("ifscCode","");
                beneficiaryData.put("micrCode","");
                beneficiaryData.put("bankName","");
                beneficiaryData.put("bankBranch","");
                beneficiariesList.add(beneficiaryData);
            }
        }

        dataVariables.put("policyNumber", policyNumber);
        dataVariables.put("otpDateTimeStamp", otpDateTimeStamp);
        dataVariables.put("firstNameofProposer", firstNameofProposer);
        dataVariables.put("middleNameofProposer", middleNameofProposer);
        dataVariables.put("lastNameofProposer", lastNameofProposer);
        dataVariables.put("houseno", houseno);
        dataVariables.put("road", road);
        dataVariables.put("landMark", landMark);
        dataVariables.put("city", city);
        dataVariables.put("state", state);
        dataVariables.put("pinCode", pinCode);
        dataVariables.put("beneficiariesList", beneficiariesList);
        context.setVariables(dataVariables);
        return context;
    }

}
