package com.mli.mpro.document.mapper;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.context.Context;

import java.util.*;

import static com.mli.mpro.utils.Utility.isPartyProposer;

@Service
public class NeoMedicalScheduleDocumentMapper {
    private static final Logger logger = LoggerFactory.getLogger(NeoMedicalScheduleDocumentMapper.class);

    public Context setDocumentData(ProposalDetails proposalDetails) throws UserHandledException {
        long transactionId = proposalDetails.getTransactionId();
        logger.info("Medical Schedule Document Data Mapping for transactionId {}", transactionId);
        Map<String, Object> dataMap = new HashMap<>();
        Context medicalScheduleDocumentContext = new Context();
        String policyNumber;
        String equoteNumber;
        String timeStamp;
        String centreType = AppConstants.BLANK;
        String nationality = AppConstants.BLANK;
        Date prefferedDate = null;
        String prefferedTime = AppConstants.BLANK;
        String medicalCentreSelected = AppConstants.BLANK;
        String reasonForChange = AppConstants.BLANK;
        try {
            policyNumber = Utility.isNotNullOrEmpty(proposalDetails.getApplicationDetails().getPolicyNumber()) ?
                    proposalDetails.getApplicationDetails().getPolicyNumber() : AppConstants.BLANK;
            equoteNumber = Utility.isNotNullOrEmpty(proposalDetails.getEquoteNumber()) ? proposalDetails.getEquoteNumber() : AppConstants.BLANK;
            timeStamp = Utility.isNotNullOrEmpty(proposalDetails.getApplicationDetails().getOtpDateTimeStamp()) ?
                    proposalDetails.getApplicationDetails().getOtpDateTimeStamp() : AppConstants.BLANK;

            if ((isPartyProposer(proposalDetails) && (Utility.isProductSWPJL(proposalDetails) || Utility.isApplicationIsForm2(proposalDetails)
                    || Utility.isSSPJLProduct(proposalDetails)))) {
                nationality = getNationality(proposalDetails, nationality, 1);
            } else {
                nationality = getNationality(proposalDetails, nationality, 0);
            }

            if (Objects.nonNull(proposalDetails.getUnderwritingServiceDetails()) && Objects.nonNull(proposalDetails.getUnderwritingServiceDetails().getMedicalShedulingDetails())) {

                centreType = Utility.isNotNullOrEmpty(proposalDetails.getUnderwritingServiceDetails().getMedicalShedulingDetails().getVisitType()) ?
                        proposalDetails.getUnderwritingServiceDetails().getMedicalShedulingDetails().getVisitType() : AppConstants.BLANK;

                prefferedDate = Objects.nonNull(proposalDetails.getUnderwritingServiceDetails().getMedicalShedulingDetails().getPreferredDate()) ?
                                proposalDetails.getUnderwritingServiceDetails().getMedicalShedulingDetails().getPreferredDate() :
                                new Date();
                prefferedTime = Utility.isNotNullOrEmpty(proposalDetails.getUnderwritingServiceDetails().getMedicalShedulingDetails().getPreferredTime()) ?
                                proposalDetails.getUnderwritingServiceDetails().getMedicalShedulingDetails().getPreferredTime() : AppConstants.BLANK;
                reasonForChange = Utility.isNotNullOrEmpty(proposalDetails.getUnderwritingServiceDetails().getMedicalShedulingDetails().getPinCodeChangeReason()) ?
                                  proposalDetails.getUnderwritingServiceDetails().getMedicalShedulingDetails().getPinCodeChangeReason() : AppConstants.BLANK;
                medicalCentreSelected = getMedicalCentreName(proposalDetails);

                settingMedicalGeoLocationDetails(proposalDetails, dataMap);

            }

            dataMap.put("policyNumber", policyNumber);
            dataMap.put("equoteNumber", equoteNumber);
            dataMap.put("timeStamp", timeStamp);
            dataMap.put("centerType", centreType);
            dataMap.put("nationality", nationality);
            dataMap.put("prefferedDate", prefferedDate);
            dataMap.put("prefferedTime", prefferedTime);
            dataMap.put("medicalCentreSelected", medicalCentreSelected);
            dataMap.put("reasonForChange", reasonForChange);


        } catch (Exception e) {
            logger.error("Data addition failed in Medical Schedule Document for transactionId {}", transactionId, e);
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Data addition failed for Medical Schedule Document");
            throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        medicalScheduleDocumentContext.setVariables(dataMap);
        logger.info("End of Medical Schedule Document Mapper for transactionId {}", transactionId);
        return medicalScheduleDocumentContext;

    }

    private void settingMedicalGeoLocationDetails(ProposalDetails proposalDetails, Map<String, Object> dataMap) {
        String newPincode = AppConstants.BLANK;
        String city = AppConstants.BLANK;
        String state = AppConstants.BLANK;
        String address = AppConstants.BLANK;
        if (Objects.nonNull(proposalDetails.getUnderwritingServiceDetails().getMedicalShedulingDetails().getMedicalGeoLocationDetails())) {
            city = Utility.isNotNullOrEmpty(proposalDetails.getUnderwritingServiceDetails().getMedicalShedulingDetails().getMedicalGeoLocationDetails().getCity())
                    ? proposalDetails.getUnderwritingServiceDetails().getMedicalShedulingDetails().getMedicalGeoLocationDetails().getCity() : AppConstants.BLANK;

            state = Utility.isNotNullOrEmpty(proposalDetails.getUnderwritingServiceDetails().getMedicalShedulingDetails().getMedicalGeoLocationDetails().getState())
                    ? proposalDetails.getUnderwritingServiceDetails().getMedicalShedulingDetails().getMedicalGeoLocationDetails().getState() : AppConstants.BLANK;

            newPincode = Utility.isNotNullOrEmpty(proposalDetails.getUnderwritingServiceDetails().getMedicalShedulingDetails().getMedicalGeoLocationDetails().getPinCode())
                    ? proposalDetails.getUnderwritingServiceDetails().getMedicalShedulingDetails().getMedicalGeoLocationDetails().getPinCode() : AppConstants.BLANK;
            address = settingAddress(proposalDetails);
        }
        dataMap.put("newPincode", newPincode);
        dataMap.put("city", city);
        dataMap.put("state", state);
        dataMap.put("address", address);

    }

    private String getMedicalCentreName(ProposalDetails proposalDetails) {
        String medicalCentreSelected = AppConstants.BLANK;
        if (Objects.nonNull(proposalDetails.getUnderwritingServiceDetails().getMedicalShedulingDetails().getLabDetails())) {
            medicalCentreSelected = Utility.isNotNullOrEmpty(proposalDetails.getUnderwritingServiceDetails().getMedicalShedulingDetails().getLabDetails().getName()) ?
                                    proposalDetails.getUnderwritingServiceDetails().getMedicalShedulingDetails().getLabDetails().getName() : AppConstants.BLANK;
        }
        
        return medicalCentreSelected;
    }

    private String settingAddress(ProposalDetails proposalDetails) {
        String address;
        address = Utility.nullSafe(proposalDetails.getUnderwritingServiceDetails().getMedicalShedulingDetails().getMedicalGeoLocationDetails().getHouse()) + " " +
                Utility.nullSafe(proposalDetails.getUnderwritingServiceDetails().getMedicalShedulingDetails().getMedicalGeoLocationDetails().getArea());
        return address;
    }

    private String getNationality(ProposalDetails proposalDetails, String nationality, int i) {
        if (!CollectionUtils.isEmpty(proposalDetails.getPartyInformation()) &&
                Objects.nonNull(proposalDetails.getPartyInformation().get(i)) &&
                Objects.nonNull(proposalDetails.getPartyInformation().get(i).getBasicDetails()) &&
                Objects.nonNull(proposalDetails.getPartyInformation().get(i).getBasicDetails().getNationalityDetails()) &&
                Objects.nonNull(proposalDetails.getPartyInformation().get(i).getBasicDetails().getNationalityDetails().getNationality())) {
            nationality = proposalDetails.getPartyInformation().get(i).getBasicDetails().getNationalityDetails().getNationality();
        }
        return nationality;
    }
}
