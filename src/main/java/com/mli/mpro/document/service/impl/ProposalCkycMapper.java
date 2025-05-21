package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.Utility;
import com.mli.mpro.proposal.models.AddressDetails;
import com.mli.mpro.proposal.models.BasicDetails;
import com.mli.mpro.proposal.models.MultipleFTINDetailsForNRI;
import com.mli.mpro.proposal.models.ProposalDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.Context;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Mapper class for CKYC details section of Proposal Form Document
 *
 * @author manish on 23/03/20
 */
@Service
public class ProposalCkycMapper {

    private static final Logger logger = LoggerFactory.getLogger(ProposalCkycMapper.class);

    public Context setDataForCkycDetails(ProposalDetails proposalDetails) throws UserHandledException {
        Map<String, Object> dataVariables = new HashMap<>();
        Context context = new Context();
        try {
            if (Objects.nonNull(proposalDetails)) {
                logger.info("Starting CKYC data population for transactionId {}", proposalDetails.getTransactionId());

                // set data for basic details
                setDataForBasicDetails(dataVariables, proposalDetails);

                // set data for occupation
                setDataForOccupation(dataVariables, proposalDetails);

                // set data for address details
                setDataForAddressDetails(dataVariables, proposalDetails);

                // set data for NRI details
                setDataForNriDetails(dataVariables, proposalDetails);

                if (Objects.nonNull(proposalDetails.getCkycDetails())) {
                    dataVariables.put(AppConstants.APPLICATION_TYPE, !StringUtils.isEmpty(proposalDetails.getCkycDetails().getCkycNumber())
                            ? AppConstants.CKYC_EXISTING : AppConstants.CKYC_NEW);
                    dataVariables.put(AppConstants.KYC_NUMBER, Utility.nullSafe(proposalDetails.getCkycDetails().getCkycNumber()));
                    dataVariables.put(AppConstants.ID_PROOF_NAME, Utility.nullSafe(proposalDetails.getCkycDetails().getIdProofType()));
                    dataVariables.put(AppConstants.ID_PROOF_NUMBER, Utility.nullSafe(proposalDetails.getCkycDetails().getIdProofnumber()));
                    dataVariables.put(AppConstants.ID_PROOF_EXPIRY, Utility.nullSafe(proposalDetails.getCkycDetails().getIdProofExpiryDate()));
                    dataVariables.put(AppConstants.CITY_OF_BIRTH, Utility.nullSafe(proposalDetails.getCkycDetails().getNriCityOfBirth()));
                    dataVariables.put(AppConstants.ADDRESS_PROOF_NAME, Utility.nullSafe(proposalDetails.getCkycDetails().getAddressProofType()));
                    dataVariables.put(AppConstants.ADDRESS_PROOF_EXPIRY, Utility.nullSafe(proposalDetails.getCkycDetails().getAddressProofExpiryDate()));
                }
                convertNullValuesToBlank(dataVariables);
                context.setVariables(dataVariables);
            }
        } catch (Exception ex) {
            logger.error("Data population failed for CKYC:", ex);
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Data addition failed");
            throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.info("End of CKYC data population");
        return context;
    }

    private void setDataForNriDetails(Map<String, Object> dataVariables, ProposalDetails proposalDetails) {

        String overseasAddress = "";
        String email = "";
        String mobileNumber = "";
        String countryCode = "";
        String countryCodeOfBirth = "";
        List<MultipleFTINDetailsForNRI> multipleFTINDetailsForNRIS = null;
        String countryOfResidenceAsPerTaxLaw = "";
        String countryOfResidence = "";
        String ftinOrPan = "";

        if (Objects.nonNull(proposalDetails.getPartyInformation()) &&
                !proposalDetails.getPartyInformation().isEmpty() &&
                Objects.nonNull(proposalDetails.getPartyInformation().get(0).getBasicDetails())) {
            BasicDetails proposerBasicDetails = proposalDetails.getPartyInformation().get(0).getBasicDetails();

            if (Objects.nonNull(proposerBasicDetails.getNationalityDetails()) &&
                    Objects.nonNull(proposerBasicDetails.getNationalityDetails().getNriDetails())) {
                multipleFTINDetailsForNRIS = Objects.nonNull(proposerBasicDetails.getNationalityDetails().getNriDetails().getFtinDetails())
                        ? proposerBasicDetails.getNationalityDetails().getNriDetails().getFtinDetails().getMultipleFTINDetailsForNRI()
                        : Collections.emptyList();
                countryOfResidenceAsPerTaxLaw = Utility.nullSafe(proposerBasicDetails.getNationalityDetails().getNriDetails().getCountryOfResidenceAsPerTaxLaw());
                countryOfResidence = Utility.nullSafe(proposerBasicDetails.getNationalityDetails().getNriDetails().getCurrentCountryOfResidence());
                countryCode = Utility.nullSafe(proposerBasicDetails.getNationalityDetails().getNriDetails().getCurrentCountryOfResidence()); //TODO: Validate
                countryCodeOfBirth = Utility.nullSafe(proposerBasicDetails.getNationalityDetails().getNriDetails().getCountryOfBirth());
                overseasAddress = Utility.nullSafe(proposerBasicDetails.getNationalityDetails().getNriDetails().getOverseasFullAddress());
                ftinOrPan = Utility.nullSafe(proposerBasicDetails.getNationalityDetails().getNriDetails().getFtinOrPan());

                if (Objects.nonNull(proposerBasicDetails.getPersonalIdentification())) {
                    email = proposerBasicDetails.getPersonalIdentification().getEmail();
                    if (Objects.nonNull(proposerBasicDetails.getPersonalIdentification().getPhone()) &&
                            !proposerBasicDetails.getPersonalIdentification().getPhone().isEmpty()) {
                        mobileNumber = Utility.nullSafe(proposerBasicDetails.getPersonalIdentification().getPhone().get(0).getPhoneNumber());
                    }
                }
            }

            dataVariables.put(AppConstants.OVERSEAS_ADDRESS, overseasAddress);
            dataVariables.put(AppConstants.MOBILE_NUMBER, mobileNumber);
            dataVariables.put(AppConstants.EMAIL, email);
            dataVariables.put(AppConstants.TAX_IDENTIFICATION_NUMBERS, Objects.nonNull(multipleFTINDetailsForNRIS) ? multipleFTINDetailsForNRIS
                    : Collections.emptyList());
            dataVariables.put(AppConstants.COUNTRY_CODE, countryCode);
            dataVariables.put(AppConstants.COUNTRY_CODE_OF_BIRTH, countryCodeOfBirth);
            dataVariables.put(AppConstants.FTIN_EXIST, !CollectionUtils.isEmpty(multipleFTINDetailsForNRIS));
            dataVariables.put(AppConstants.COUNTRY_OF_RESIDENCE_AS_PER_TAX_LAW, countryOfResidenceAsPerTaxLaw);
            dataVariables.put(AppConstants.COUNTRY_OF_RESIDENCE, countryOfResidence);
            dataVariables.put(AppConstants.FTIN_OR_PAN, ftinOrPan);
        }
    }

    private void setDataForAddressDetails(Map<String, Object> dataVariables, ProposalDetails proposalDetails) {

        String addressProofType = "";
        String houseNumber = "";
        String area = "";
        String landmark = "";
        String city = "";
        String state = "";
        String pinCode = "";
        String country = "";
        String residentalStatus = "";

        if(Objects.nonNull(proposalDetails.getPartyInformation())
                && !proposalDetails.getPartyInformation().isEmpty()
                && Objects.nonNull(proposalDetails.getPartyInformation().get(0).getBasicDetails())
                && Objects.nonNull(proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress())
                && !proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress().isEmpty()
                && Objects.nonNull(proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails())) {

            AddressDetails addressDetails = proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails();

            addressProofType = Objects.nonNull(proposalDetails.getCkycDetails()) ? Utility.nullSafe(proposalDetails.getCkycDetails().getAddressProofType()) : AppConstants.BLANK;
            houseNumber = Utility.nullSafe(addressDetails.getHouseNo());
            area = Utility.nullSafe(addressDetails.getArea());
            landmark = Utility.nullSafe(addressDetails.getLandmark());
            city = Utility.nullSafe(addressDetails.getCity());
            pinCode = Utility.nullSafe(addressDetails.getPinCode());
            state = Utility.nullSafe(addressDetails.getState());
            country = Utility.nullSafe(addressDetails.getCountry());
            residentalStatus = Utility.nullSafe(addressDetails.getCountry());

        }

        dataVariables.put(AppConstants.RESIDENTAL_STATUS, residentalStatus);
        dataVariables.put(AppConstants.ADDRESS_PROOF_TYPE, addressProofType);
        dataVariables.put(AppConstants.HOUSE_NUMBER, houseNumber);
        dataVariables.put(AppConstants.AREA, area);
        dataVariables.put(AppConstants.LANDMARK, landmark);
        dataVariables.put(AppConstants.CKYC_CITY, city);
        dataVariables.put(AppConstants.STATE, state);
        dataVariables.put(AppConstants.CKYC_PIN_CODE, pinCode);
        dataVariables.put(AppConstants.CKYC_COUNTRY, country);
        dataVariables.put(AppConstants.PLACE, city);

    }

    private void setDataForOccupation(Map<String, Object> dataVariables, ProposalDetails proposalDetails) {
        String occupation = "";
        if (Objects.nonNull(proposalDetails.getEmploymentDetails())
                && Objects.nonNull(proposalDetails.getEmploymentDetails().getPartiesInformation())
                && !proposalDetails.getEmploymentDetails().getPartiesInformation().isEmpty()
                && Objects.nonNull(proposalDetails.getEmploymentDetails().getPartiesInformation().get(0).getBasicDetails())) {
            occupation = Utility.nullSafe(proposalDetails.getEmploymentDetails().getPartiesInformation().get(0).getBasicDetails().getOccupation());
        }
        dataVariables.put(AppConstants.CKYC_OCCUPATION, occupation);
    }

    private void setDataForBasicDetails(Map<String, Object> dataVariables, ProposalDetails proposalDetails) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));

        String title = "";
        String firstName = "";
        String lastName = "";
        String middleName = "";
        String proposerName = "";
        String maidenName = "";
        String fatherName = "";
        String motherName = "";
        String gender = "";
        String dob = "";
        String nationality = "";
        String maritalStatus = "";
        String currentDate = "";

        if (Objects.nonNull(proposalDetails.getPartyInformation()) &&
                !proposalDetails.getPartyInformation().isEmpty() &&
                Objects.nonNull(proposalDetails.getPartyInformation().get(0).getBasicDetails())) {
            BasicDetails proposerBasicDetails = proposalDetails.getPartyInformation().get(0).getBasicDetails();

            title = getTitle(Utility.nullSafe(proposerBasicDetails.getGender()));
            firstName = Utility.nullSafe(proposerBasicDetails.getFirstName());
            lastName = Utility.nullSafe(proposerBasicDetails.getLastName());
            proposerName = Stream.of(
                    getTitle(proposerBasicDetails.getGender()),
                    Utility.nullSafe(proposerBasicDetails.getFirstName()),
                    Utility.nullSafe(proposerBasicDetails.getMiddleName()),
                    Utility.nullSafe(proposerBasicDetails.getLastName()))
                    .filter(s -> !StringUtils.isEmpty(s)).collect(Collectors.joining(" "));
            middleName = Utility.nullSafe(proposerBasicDetails.getMiddleName());
            maidenName = AppConstants.BLANK;
            fatherName = Utility.nullSafe(proposerBasicDetails.getFatherName());
            if (Objects.nonNull(proposalDetails.getCkycDetails())) {
                motherName = Stream.of(
                        getTitle("F"),
                        Utility.nullSafe(proposalDetails.getCkycDetails().getMothersFirstName()),
                        Utility.nullSafe(proposalDetails.getCkycDetails().getMothersLastName()))
                        .filter(s -> !StringUtils.isEmpty(s)).collect(Collectors.joining(" "));
            }
            gender = getGender(Utility.nullSafe(proposerBasicDetails.getGender()));
            dob = Utility.dateFormatter(formatter.format(proposerBasicDetails.getDob()), "dd-MM-yyyy", "dd/MM/yyyy");
            nationality = Objects.nonNull(proposerBasicDetails.getNationalityDetails()) ? Utility.nullSafe(proposerBasicDetails.getNationalityDetails().getNationality())
                    : AppConstants.BLANK;
            maritalStatus = Objects.nonNull(proposerBasicDetails.getMarriageDetails()) ? Utility.nullSafe(proposerBasicDetails.getMarriageDetails().getMaritalStatus())
                    : AppConstants.BLANK;
        }

        if (Objects.nonNull(proposalDetails.getApplicationDetails())
                && Objects.nonNull(proposalDetails.getPaymentDetails())
                && Objects.nonNull(proposalDetails.getPaymentDetails().getReceipt())
                && !proposalDetails.getPaymentDetails().getReceipt().isEmpty()) {
            currentDate = Utility.getDateOnTheBasisOfRateChange(proposalDetails, false);
        }

        dataVariables.put(AppConstants.TITLE, title);
        dataVariables.put(AppConstants.FIRST_NAME, firstName);
        dataVariables.put(AppConstants.MIDDLE_NAME, middleName);
        dataVariables.put(AppConstants.LAST_NAME, lastName);
        dataVariables.put(AppConstants.PROPOSER_NAME, proposerName);
        dataVariables.put(AppConstants.MAIDEN_NAME, maidenName);
        dataVariables.put(AppConstants.FATHER_NAME, fatherName);
        dataVariables.put(AppConstants.MOTHER_NAME, motherName);
        dataVariables.put(AppConstants.GENDER, gender);
        dataVariables.put(AppConstants.CKYC_DOB, dob);
        dataVariables.put(AppConstants.NATIONALITY, nationality);
        dataVariables.put(AppConstants.MARITIAL_STATUS, maritalStatus);
        dataVariables.put(AppConstants.NAME_OF_APPLICANT, proposerName);
        dataVariables.put(AppConstants.CURRENT_DATE, currentDate);
    }

    private String getTitle(String gender) {
        String title = "Mr";
        if (gender.equals("F")) {
            title = "Ms";

        } else if (gender.equalsIgnoreCase("Others")) {
            title = "Mx";
        }
        return title;
    }

    private String getGender(String gender) {
        String formattedgender = "Male";
        if (gender.equals("F")) {
            formattedgender = "Female";

        } else if (gender.equalsIgnoreCase("Others")) {
            formattedgender = "Other";
        }
        return formattedgender;
    }

    private void convertNullValuesToBlank(Map<String, Object> dataMap) {
        Set<String> keys = dataMap.keySet();
        for (String key : keys) {
            if (Objects.isNull(dataMap.get(key))) {
                dataMap.put(key, "");
            }
        }
    }
}
