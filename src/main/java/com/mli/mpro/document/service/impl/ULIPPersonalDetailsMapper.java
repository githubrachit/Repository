package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.document.models.ProposalNomineeDetails;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.*;
import com.mli.mpro.utils.Utility;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.context.Context;
import com.google.common.base.Strings;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Mapper Class for Personal Details section of Proposal Form Document
 *
 * @author akshom4375
 */
@Service
public class ULIPPersonalDetailsMapper {

    private static final Logger logger = LoggerFactory.getLogger(ULIPPersonalDetailsMapper.class);
    private boolean isNeoOrAggregator = false;
	private static final String CRIMINAL_CHARGES = "criminalCharges";
	private static final String FOREIGN_OFFICE = "foreignOffice";
	private static final String POWER = "power";
    private static final String INDIAN = "Indian";
    @Autowired
    private ULIPNomineeMapper ulipNomineeMapper;

    @Autowired
    private BaseMapper baseMapper;

    public long transactionId = 0;

    /**
     * Fetchin and Setting data from proposal form payload
     *
     * @param proposalDetails
     * @return
     */
    public Context setDataForProposalForm(ProposalDetails proposalDetails) throws UserHandledException {
        logger.info("START {}", "%m");
        // Initialize to empty values
        Map<String, Object> dataVariables = new HashMap<>();
        if (null != proposalDetails.getNomineeDetails()) {
            List<PartyDetails> nomineePartyDetailsList = proposalDetails.getNomineeDetails().getPartyDetails();
            dataVariables = ulipNomineeMapper.setNominees(nomineePartyDetailsList, dataVariables,proposalDetails.getChannelDetails().getChannel());
        }
        String firstName = StringUtils.EMPTY;
        String middleName = StringUtils.EMPTY;
        String lastName = StringUtils.EMPTY;
        String gender = StringUtils.EMPTY;
        String formattedGender = StringUtils.EMPTY;
        String title = StringUtils.EMPTY;
        String nationality = StringUtils.EMPTY;
        String maritialStatus = StringUtils.EMPTY;
        String education = StringUtils.EMPTY;
        String proposerResidentialStatus = StringUtils.EMPTY;
        String formattedDob = StringUtils.EMPTY;
        String houseNo = StringUtils.EMPTY;
        String area = StringUtils.EMPTY;
        String village = StringUtils.EMPTY;
        String landmark = StringUtils.EMPTY;
        String city = StringUtils.EMPTY;
        String pinCode = StringUtils.EMPTY;
        String state = StringUtils.EMPTY;
        String country = StringUtils.EMPTY;
        String annualIncome = StringUtils.EMPTY;
        String occupation = StringUtils.EMPTY;
        String jobTitle = StringUtils.EMPTY;
        String languageOfCommunication = StringUtils.EMPTY;
        String residenceForTax = StringUtils.EMPTY;

        String permLandline = StringUtils.EMPTY;
        String permhouseNo = StringUtils.EMPTY;
        String permarea = StringUtils.EMPTY;
        String permvillage = StringUtils.EMPTY;
        String permlandmark = StringUtils.EMPTY;
        String permcity = StringUtils.EMPTY;
        String permpinCode = StringUtils.EMPTY;
        String permstate = StringUtils.EMPTY;
        String permcountry = StringUtils.EMPTY;

        String isLIPep = StringUtils.EMPTY;
        String isProposerPEP = StringUtils.EMPTY;
        String isPayor = StringUtils.EMPTY;
        String specifyMember = StringUtils.EMPTY;
        String partyInPower = StringUtils.EMPTY;
        String pepForeign = StringUtils.EMPTY;
        String pepOffice = StringUtils.EMPTY;
        String incomeSourcePep = StringUtils.EMPTY;
        String pepIsCrime = StringUtils.EMPTY;
        String convictedDetails = StringUtils.EMPTY;
        String isNomineePep = StringUtils.EMPTY;
        String isFamilyMemberPEP = StringUtils.EMPTY;
        String role = StringUtils.EMPTY;
        String proposerfatherName = StringUtils.EMPTY;
        String fatherMiddleName = StringUtils.EMPTY;
        String fatherFirstName = StringUtils.EMPTY;
        String politicalExperience = StringUtils.EMPTY;
        String affiliation = StringUtils.EMPTY;
        String specifyRoleOther = StringUtils.EMPTY;
        String portfolio = AppConstants.NO;
        String productId = StringUtils.EMPTY;
        String childDob = StringUtils.EMPTY;
        String childName = StringUtils.EMPTY;
        String placeOfPosting = StringUtils.EMPTY;
        String rank = StringUtils.EMPTY;
        String branch = StringUtils.EMPTY;
        String natureOfDuties = StringUtils.EMPTY;
        String hazardousActivities =  StringUtils.EMPTY;
        String hazardousActivitiesDetails =  StringUtils.EMPTY;
        String aadharNumber =  StringUtils.EMPTY;
        String digitalPromotionConsent = AppConstants.YES;
        try {
            //NEORW-173: this will check that incoming request is from NEO or Aggregator
            isNeoOrAggregator = proposalDetails.getChannelDetails().getChannel().equalsIgnoreCase(AppConstants.CHANNEL_NEO)
                    || proposalDetails.getChannelDetails().getChannel().equalsIgnoreCase(AppConstants.CHANNEL_AGGREGATOR)
                    ? true : false;
            String formType = proposalDetails.getApplicationDetails().getFormType();
            EmploymentDetails proposerEmploymentDetails = proposalDetails.getEmploymentDetails();
            List<PartyInformation> partyInformationList = proposalDetails.getPartyInformation();
            BasicDetails proposerBasicInfo = proposalDetails.getPartyInformation().get(0).getBasicDetails();
            BasicDetails insuredBasicInfo=null;

            String schemeType = proposalDetails.getApplicationDetails().getSchemeType();
            Boolean schemeBCase = Utility.schemeBCase(formType,schemeType);
            Date dob = proposerBasicInfo.getDob();
            SimpleDateFormat formatter = new SimpleDateFormat(AppConstants.DATE_FORMAT);

            //NEORW in case of neo, proposer and insurer are the same.
            if(isNeoOrAggregator) {
                insuredBasicInfo = proposalDetails.getPartyInformation().get(0).getBasicDetails();
            }else{
                insuredBasicInfo = proposalDetails.getPartyInformation().get(1).getBasicDetails();
            }
            String channel = proposalDetails.getChannelDetails().getInfluencerChannel();
            transactionId = proposalDetails.getTransactionId();
            /** If Proposer payload contains party information procees with data fetching */
            if (!CollectionUtils.isEmpty(partyInformationList)) {
                logger.info("Fetching ULIP Proposer Basic details {}", "%m");
                BasicDetails proposerBasicDetails = partyInformationList.get(0).getBasicDetails();

                firstName = proposerBasicDetails.getFirstName();
                middleName = proposerBasicDetails.getMiddleName();
                lastName = proposerBasicDetails.getLastName();
                gender = proposerBasicDetails.getGender();
                formattedGender = Utility.getGender(gender);
                title = Utility.getTitle(gender);
                nationality = proposerBasicDetails.getNationalityDetails().getNationality();
                maritialStatus = proposerBasicDetails.getMarriageDetails().getMaritalStatus();
                education = proposerBasicDetails.getEducation();
                if(isNeoOrAggregator){
                 education = settingValueOfEducation(education);
                }
                proposerResidentialStatus = proposerBasicDetails.getResidentialStatus();
                formattedDob = isNeoOrAggregator ? Utility.dateFormatter(Utility.dateFormatter(dob), "dd-MM-yyyy", "dd/MM/yyyy")
                        : Utility.dateFormatter(dob);
                annualIncome =  isNeoOrAggregator ?
                        proposerEmploymentDetails.getPartiesInformation().get(0).getBasicDetails().getAnnualIncome() : proposerBasicDetails.getAnnualIncome();
                occupation = isNeoOrAggregator ?
                        proposerEmploymentDetails.getPartiesInformation().get(0).getBasicDetails().getOccupation() : proposerBasicDetails.getOccupation();
                jobTitle = isNeoOrAggregator ?
                        proposerEmploymentDetails.getPartiesInformation().get(0).getPartyDetails().getJobTitle() : AppConstants.NA;
                languageOfCommunication = proposerBasicDetails.getPreferredLanguageOfCommunication();
                proposerfatherName = proposerBasicDetails.getFatherName();
                fatherFirstName = proposerfatherName;
                residenceForTax = Objects.nonNull(proposerBasicDetails.getResidenceForTax()) ? proposerBasicDetails.getResidenceForTax() : StringUtils.EMPTY;

                if (proposerfatherName.contains(AppConstants.WHITE_SPACE)) {
                    fatherFirstName = proposerfatherName.substring(0, proposerfatherName.indexOf(AppConstants.WHITE_SPACE));
                    fatherMiddleName = proposerfatherName.substring(proposerfatherName.indexOf(AppConstants.WHITE_SPACE), proposerfatherName.length());
                }

                //set defence/CRPF details in case of NEO
                if (isNeoOrAggregator && Objects.nonNull(proposalDetails.getEmploymentDetails())
                        && 	Objects.nonNull(proposalDetails.getEmploymentDetails().getPartiesInformation())
                        && 	!proposalDetails.getEmploymentDetails().getPartiesInformation().isEmpty()
                        && 	Objects.nonNull(proposalDetails.getEmploymentDetails().getPartiesInformation().get(0).getPartyDetails())) {
                    placeOfPosting = proposalDetails.getEmploymentDetails().getPartiesInformation().get(0).getPartyDetails().getPlaceOfPosting();
                    rank =proposalDetails.getEmploymentDetails().getPartiesInformation().get(0).getPartyDetails().getRank();
                    branch = proposalDetails.getEmploymentDetails().getPartiesInformation().get(0).getPartyDetails().getBranchOfArmedForces();
                    natureOfDuties = proposalDetails.getEmploymentDetails().getPartiesInformation().get(0).getPartyDetails().getNatureOfDuties();
                }
                // Proposer communication Address
                List<Address> proposerAddressList = proposerBasicDetails.getAddress();
                if(!(AppConstants.FORM3.equalsIgnoreCase(formType) && !Utility.schemeBCase(schemeType))){

                    if (  !CollectionUtils.isEmpty(proposerAddressList)) {
                        logger.info("Fetching ULIP Proposer Communication Address {}", "%m");
                        AddressDetails commAddress = proposerAddressList.get(0).getAddressDetails();
                        houseNo = commAddress.getHouseNo();
                        area = commAddress.getArea();
                        village = commAddress.getVillage();
                        landmark = commAddress.getLandmark();
                        city = commAddress.getCity();
                        pinCode = commAddress.getPinCode();
                        state = commAddress.getState();
                        country = commAddress.getCountry();
                    }
                    // Proposer permanent address
                    if (!CollectionUtils.isEmpty(proposerAddressList) && proposerAddressList.size() >= 2) {
                        logger.info("Fetching ULIP Proposer Permanent Address {}", "%m");
                        AddressDetails permAddress = proposerAddressList.get(1).getAddressDetails();
                        permhouseNo = permAddress.getHouseNo();
                        permarea = permAddress.getArea();
                        permvillage = permAddress.getVillage();
                        permlandmark = permAddress.getLandmark();
                        permcity = permAddress.getCity();
                        permpinCode = permAddress.getPinCode();
                        permstate = permAddress.getState();
                        permcountry = permAddress.getCountry();
                    }

                }


            }
            // Setting Dependent related personal Data
            dataVariables=setDependentDataForProposalForm(partyInformationList,formType,dataVariables, proposalDetails);

            //Setting Industry Data
            dataVariables = setIndustryDataForProposalForm(proposalDetails, dataVariables);

            //  Communication details
            dataVariables = setCommunicationDataForProposalForm(partyInformationList, dataVariables);

            String isPep = Utility.evaluateConditionalOperation(BooleanUtils.isTrue(proposerEmploymentDetails.isPoliticallyExposed()), AppConstants.YES, AppConstants.NO);

            // Setting politically exposed person details
            if (null != proposerEmploymentDetails.getPepDetails()) {
                isLIPep = Utility.evaluateConditionalOperation(proposerEmploymentDetails.getPepDetails().isLIPEP(), AppConstants.YES, AppConstants.NO);
                isProposerPEP = Utility.evaluateConditionalOperation(proposerEmploymentDetails.getPepDetails().isProposerPEP(), AppConstants.YES, AppConstants.NO);
                isPayor = Utility.evaluateConditionalOperation(proposerEmploymentDetails.getPepDetails().isPayorPep(), AppConstants.YES, AppConstants.NO);
                partyInPower = proposerEmploymentDetails.getPepDetails().getPartyInPower();
                pepForeign = proposerEmploymentDetails.getPepDetails().getPepEverPostedInForeignOffice();
                pepOffice = proposerEmploymentDetails.getPepDetails().getForeignOfficeDetails();
                incomeSourcePep = proposerEmploymentDetails.getPepDetails().getIncomeSources();
                pepIsCrime = proposerEmploymentDetails.getPepDetails().getPepConvicted();
                convictedDetails = proposerEmploymentDetails.getPepDetails().getConvictionDetails();
                isNomineePep = Utility.evaluateConditionalOperation(proposerEmploymentDetails.getPepDetails().isLIorNomineePEP(), AppConstants.YES, AppConstants.NO);
                isFamilyMemberPEP = Utility.evaluateConditionalOperation(proposerEmploymentDetails.getPepDetails().isFamilyMemberPEP(), AppConstants.YES, AppConstants.NO);
                role = proposerEmploymentDetails.getPepDetails().getRoleInPoliticalParty();
                politicalExperience = proposerEmploymentDetails.getPepDetails().getPoliticalExperience();
                affiliation = proposerEmploymentDetails.getPepDetails().getAffiliationsToPoliticalparty();
                portfolio = Utility.evaluateConditionalOperation(!StringUtils.isEmpty(proposerEmploymentDetails.getPepDetails().getPortfolioHandled()), proposerEmploymentDetails.getPepDetails().getPortfolioHandled(), AppConstants.BLANK);
                specifyMember = proposerEmploymentDetails.getPepDetails().getSpecifyFamilyMembers();
                specifyRoleOther = proposerEmploymentDetails.getPepDetails().getRoleOthers();
            }
            if (isNeoOrAggregator) {
                hazardousActivities = proposerEmploymentDetails.getPartiesInformation().get(0)
                        .getPartyDetails().getAreYouInvolvedinhazardousActivities();
                hazardousActivitiesDetails = proposerEmploymentDetails.getPartiesInformation().get(0)
                        .getPartyDetails().getHazardousActivitiesDetails();
            }
            String eiaExist = Utility.evaluateConditionalOperation(
                    Utility.andTwoExpressions(
                            BooleanUtils.isTrue(proposerEmploymentDetails.isEIAExist()),
                            INDIAN.equalsIgnoreCase(nationality)
                    ), AppConstants.YES, AppConstants.NO);
            String wishToHoldEia = "";
            String accountNumber = Utility.nullSafe(proposerEmploymentDetails.getExistingEIANumber());
            String preferredInsuranceRepository = "";
            String existingRepoName = "";
            // set preferredInsuranceRepo and wishToHoidEia in case of NEO
            if (isNeoOrAggregator) {
                preferredInsuranceRepository = Utility.nullSafe(proposerEmploymentDetails.getPreferredInsuranceRepositoryName());
                wishToHoldEia = Utility.nullSafe(proposerEmploymentDetails.getWishToHoldEIAAccount());
                existingRepoName = !org.springframework.util.StringUtils.isEmpty(proposerEmploymentDetails.getExistingEIANumberRepositoryName())
                        ? Utility.getFullRepositoryName(proposerEmploymentDetails.getExistingEIANumberRepositoryName())
                        : AppConstants.BLANK;
            } else {
                preferredInsuranceRepository =Utility.evaluateConditionalOperation(!StringUtils.isEmpty(proposerEmploymentDetails.getPreferredInsuranceRepositoryName()), AppConstants.YES, AppConstants.NO);
            }
            List<PartyDetails> nomineeDetails = null;
            if (isNeoOrAggregator) {
                 nomineeDetails = Optional.ofNullable(proposalDetails.getNomineeDetails())
                    .map(NomineeDetails::getPartyDetails).orElse(null);
            } else {
                nomineeDetails = proposalDetails.getNomineeDetails().getPartyDetails();
            }

            if (!CollectionUtils.isEmpty(nomineeDetails)) {
                childDob = Utility.dateFormatter(proposalDetails.getNomineeDetails().getPartyDetails().get(0).getNomineeChildDob());
                childName = proposalDetails.getNomineeDetails().getPartyDetails().get(0).getNomineeChildName();
            }

            // Nominee details fetch
            dataVariables = setProposalNomineeDetailsForProposalForm(nomineeDetails, dataVariables, channel );

            if (!CollectionUtils.isEmpty(proposalDetails.getProductDetails())
                    && null != proposalDetails.getProductDetails().get(0).getProductInfo()) {
                productId = proposalDetails.getProductDetails().get(0).getProductInfo().getProductId();
            }
            String isChildDetailRequired = Utility.evaluateConditionalOperation(
                    Utility.orTwoExpressions(
                            StringUtils.equalsIgnoreCase(productId, "36"), StringUtils.equalsIgnoreCase(productId, "81"))
                    , AppConstants.YES, AppConstants.NO);
            String proposerCountryOfResidence =(String)Utility.evaluateConditionalOperation( org.springframework.util.StringUtils.isEmpty(proposerBasicInfo.getNationalityDetails().getNriDetails()) , StringUtils.EMPTY
                    , proposerBasicInfo.getNationalityDetails().getNriDetails(),"currentCountryOfResidence");
            String insuredCountryOfResidence =(String)Utility.evaluateConditionalOperation( org.springframework.util.StringUtils.isEmpty(insuredBasicInfo.getNationalityDetails().getNriDetails()) , StringUtils.EMPTY
                    , insuredBasicInfo.getNationalityDetails().getNriDetails(),"currentCountryOfResidence");

            // NR-1001: proposal form template changes
            if (isNeoOrAggregator && !StringUtils.isEmpty(occupation) && !StringUtils.isEmpty(jobTitle)) {
                occupation = Stream.of(occupation, jobTitle)
                        .filter(s -> !org.springframework.util.StringUtils.isEmpty(s)).collect(Collectors.joining(" / "));
            }

            // FUL2-60217 changes
            // settingDataForForm3Cases()
            if (AppConstants.FORM3.equalsIgnoreCase(formType) && !Utility.schemeBCase(schemeType)) {
                BasicDetails companyBasicDetails = proposalDetails.getPartyInformation().stream().filter(Objects::nonNull)
                        .filter(partyInfo -> AppConstants.COMPANY.equalsIgnoreCase(partyInfo.getPartyType()))
                        .filter(Objects::nonNull).map(PartyInformation::getBasicDetails).findFirst()
                        .orElse(new BasicDetails());

                PersonalIdentification authorizedSignatoryPersonalInfo = proposalDetails.getPartyInformation().stream()
                        .filter(Objects::nonNull)
                        .filter(partyInfo -> AppConstants.AUTHORIZED_SIGNATORY.equalsIgnoreCase(partyInfo.getPartyType()))
                        .filter(Objects::nonNull).map(PartyInformation::getPersonalIdentification).filter(Objects::nonNull)
                        .findFirst().orElse(new PersonalIdentification());

                BasicDetails formCInsuredBasicDetails = proposalDetails.getPartyInformation().stream()
                        .filter(Objects::nonNull)
                        .filter(partyInfo -> AppConstants.INSURED.equalsIgnoreCase(partyInfo.getPartyType()))
                        .filter(Objects::nonNull).map(PartyInformation::getBasicDetails).findFirst()
                        .orElse(new BasicDetails());

                title = "M/s";
                firstName = companyBasicDetails.getFirstName();
                dob = companyBasicDetails.getDateOfIncorporation();
                if(!org.springframework.util.StringUtils.isEmpty(dob))
                    formattedDob = formatter.format(dob);
                formattedGender = "";
                nationality = INDIAN;
                annualIncome = companyBasicDetails.getAnnualIncome();
                List<Address> companyAddressList = companyBasicDetails.getAddress();
                if (Objects.nonNull(companyAddressList) && !CollectionUtils.isEmpty(companyAddressList)) {
                    AddressDetails commAddress = companyAddressList.get(1).getAddressDetails();
                    houseNo = commAddress.getHouseNo();
                    area = commAddress.getArea();
                    village = commAddress.getVillage();
                    landmark = commAddress.getLandmark();
                    city = commAddress.getCity();
                    pinCode = commAddress.getPinCode();
                    state = commAddress.getState();
                    country = commAddress.getCountry();
                }
                List<Address> insuredAddressDetails = formCInsuredBasicDetails.getAddress();
                if (Objects.nonNull(insuredAddressDetails) && !CollectionUtils.isEmpty(insuredAddressDetails)) {
                    AddressDetails permAddress = insuredAddressDetails.get(1).getAddressDetails();
                    permhouseNo = permAddress.getHouseNo();
                    permarea = permAddress.getArea();
                    permvillage = permAddress.getVillage();
                    permlandmark = permAddress.getLandmark();
                    permcity = permAddress.getCity();
                    permpinCode = permAddress.getPinCode();
                    permstate = permAddress.getState();
                    permcountry = permAddress.getCountry();
                }
                dataVariables.put("email", authorizedSignatoryPersonalInfo.getEmail());
                List<Phone> phoneList = authorizedSignatoryPersonalInfo.getPhone();
                if (Objects.nonNull(phoneList) && !CollectionUtils.isEmpty(phoneList) && !phoneList.isEmpty()) {
                    dataVariables.put("mobileNumber", phoneList.get(0).getPhoneNumber());
                }
                if (Objects.nonNull(phoneList) && !CollectionUtils.isEmpty(phoneList) && phoneList.size() >= 2) {
                    dataVariables.put("alternate", phoneList.get(1).getPhoneNumber());
                }
                if (Objects.nonNull(phoneList) && !CollectionUtils.isEmpty(phoneList) && phoneList.size() >= 3) {
                    dataVariables.put("std", phoneList.get(2).getStdIsdCode());
                    dataVariables.put("landline", phoneList.get(2).getPhoneNumber());
                }
                eiaExist = (proposerEmploymentDetails.isEIAExist() && nationality.equalsIgnoreCase(INDIAN))
                        ? AppConstants.YES
                        : AppConstants.NO;
            }
            String customerReminderConsent= Utility.customerReminderConsent(proposalDetails);
            if(Objects.nonNull((proposalDetails.getConsentDetails()))){
                if(Objects.nonNull((proposalDetails.getConsentDetails().getDigitalPromotionConsent()))) {
                    if (isNeoOrAggregator) {
                        digitalPromotionConsent = Utility.convertToYesorNoWithDefaultYes(proposalDetails.getConsentDetails().getDigitalPromotionConsent());
                    }
                }
            }


            logger.info("Setting ULIP Personal Details data in data map.");
            // Setting in dataVariables Map
            dataVariables.put("area", area);
            dataVariables.put("formType", formType.toUpperCase());
            dataVariables.put("schemeBCase", schemeBCase);
            //Proposer personal Information
            dataVariables.put("title", title);
            dataVariables.put("digitalPromotionConsent", digitalPromotionConsent);
            dataVariables.put("firstName", firstName);
            dataVariables.put("lastName", lastName);
            dataVariables.put("middleName", middleName);
            dataVariables.put("fatherFirstName", fatherFirstName);
            dataVariables.put("fatherLastName", fatherMiddleName);
            dataVariables.put("gender", formattedGender);
            dataVariables.put("dob", formattedDob);
            dataVariables.put("nationality", nationality);
            dataVariables.put("education", education);
            dataVariables.put("maritialStatus", maritialStatus);
            dataVariables.put("residenceForTax", residenceForTax);
            dataVariables.put("houseNumber", houseNo);
            dataVariables.put("village", village);
            dataVariables.put("landmark", landmark);
            dataVariables.put("city", city);
            dataVariables.put("pinCode", pinCode);
            dataVariables.put("state", state);
            dataVariables.put("country", country);

            //Permanent Communication Address
            dataVariables.put("permHouse", permhouseNo);
            dataVariables.put("permArea", permarea);
            dataVariables.put("permvillage", permvillage);
            dataVariables.put("permlandMark", permlandmark);
            dataVariables.put("permCity", permcity);
            dataVariables.put("permPincode", permpinCode);
            dataVariables.put("permState", permstate);
            dataVariables.put("permCountry", permcountry);
            dataVariables.put("permLandline", permLandline);
            dataVariables.put("languageComm", languageOfCommunication);
            dataVariables.put("isEIA", eiaExist);
            dataVariables.put("pep", BooleanUtils.isTrue(proposerEmploymentDetails.isPoliticallyExposed()));
            dataVariables.put("isLIPep", isLIPep);
            dataVariables.put("isFamilyPep", isFamilyMemberPEP);
            dataVariables.put("isNomineePep", isNomineePep);
            dataVariables.put("isProposerPEP", isProposerPEP);
            dataVariables.put("specifyMember", specifyMember);
            dataVariables.put("isPayor", isPayor);
            dataVariables.put("politicalExperience", politicalExperience);
            dataVariables.put("affiliation", affiliation);
            dataVariables.put("role", role);
            dataVariables.put("portfolio", portfolio);
            dataVariables.put(POWER, partyInPower);
            dataVariables.put(FOREIGN_OFFICE, Utility.evaluateConditionalOperation(StringUtils.isNotBlank(pepForeign), StringUtils.upperCase(pepForeign), AppConstants.BLANK));
            dataVariables.put("specifyRoleOther", Utility.evaluateConditionalOperation(StringUtils.isNotBlank(specifyRoleOther), StringUtils.upperCase(specifyRoleOther), AppConstants.BLANK));
            dataVariables.put("pepOffice", pepOffice);
            dataVariables.put("sourceOfIncome", incomeSourcePep);
            dataVariables.put(CRIMINAL_CHARGES, Utility.evaluateConditionalOperation(StringUtils.isNotBlank(pepIsCrime), StringUtils.upperCase(pepIsCrime), AppConstants.BLANK));
            dataVariables.put("convictionDetails", convictedDetails);

            dataVariables.put("proposerResidentialStatus", proposerResidentialStatus);
            dataVariables.put("insuranceRepository", proposerEmploymentDetails.getPreferredInsuranceRepositoryName());
            dataVariables.put("wishToHoldEia", Utility.convertToYesOrNo(wishToHoldEia));
            if (isNeoOrAggregator) {
                dataVariables.put("existingEIANumber", accountNumber);
                dataVariables.put("preferredInsuranceRepository", preferredInsuranceRepository);
                dataVariables.put("existingInsuranceRepositoryName", existingRepoName);
            } else {
                dataVariables.put("existingEIANumber", Utility.evaluateConditionalOperation(StringUtils.isNotBlank(accountNumber), accountNumber, AppConstants.NO));
                dataVariables.put("preferredInsuranceRepository", Utility.evaluateConditionalOperation(StringUtils.isNotBlank(preferredInsuranceRepository), preferredInsuranceRepository, AppConstants.NO));
            }
            //checking condition for schemeA
            dataVariables.put("isNominee", Utility.evaluateConditionalOperation((formType.equalsIgnoreCase(AppConstants.DEPENDENT) || (AppConstants.FORM3.equalsIgnoreCase(formType) && !Utility.schemeBCase(schemeType))  ), "NA", StringUtils.EMPTY));
            dataVariables.put("jobTitle", jobTitle);
            dataVariables.put("occupation", occupation);
            dataVariables.put("annualIncome", annualIncome);
            dataVariables.put("isPEP", isPep);


            dataVariables.put("childDob", Utility.evaluateConditionalOperation(StringUtils.isEmpty(childDob), AppConstants.NA, childDob));
            dataVariables.put("childName", Utility.evaluateConditionalOperation(StringUtils.isEmpty(childName), AppConstants.NA, childName));
            dataVariables.put("isChildDetailsRequired", isChildDetailRequired);
            // in case of neo convert below fields in YES or NO
            if (isNeoOrAggregator) {
                dataVariables.put("hazardousactivities", Utility.convertToYesOrNo(hazardousActivities));
                dataVariables.put("role", Utility.convertToPartyRole(role));
                dataVariables.put(POWER, Utility.convertToYesOrNo(partyInPower));
                dataVariables.put(FOREIGN_OFFICE, Utility.convertToYesOrNo(pepForeign));
                dataVariables.put(CRIMINAL_CHARGES, Utility.convertToYesOrNo(pepIsCrime));
                dataVariables.put("aadharNumber", aadharNumber);
                dataVariables.put("placeOfPosting", placeOfPosting);
                dataVariables.put("rank", rank);
                dataVariables.put("branch", branch);
                dataVariables.put("natureOfDuties", natureOfDuties);
                dataVariables.put("hazardousActivitiesDetails", hazardousActivitiesDetails);
                dataVariables.put(AppConstants.DECLARATION_VERSION_DATE, Utility.getUlipDeclarationVersionDate());
            } else {
                dataVariables.put("hazardousactivities", hazardousActivities);
                dataVariables.put(POWER, partyInPower);
                dataVariables.put("role", role);
                dataVariables.put(FOREIGN_OFFICE,  StringUtils.isNotBlank(pepForeign) ? StringUtils.upperCase(pepForeign) : AppConstants.BLANK );
                dataVariables.put(CRIMINAL_CHARGES, StringUtils.isNotBlank(pepIsCrime) ? StringUtils.upperCase(pepIsCrime) : AppConstants.BLANK);
            }

            dataVariables.put("residingCountry", proposerCountryOfResidence);
            dataVariables.put("insuredresidingCountry", insuredCountryOfResidence);
            dataVariables.put("nationalityStatus", Utility.evaluateConditionalOperation("indian".equalsIgnoreCase(nationality), AppConstants.NO, AppConstants.YES));
            dataVariables.put("customerReminderConsent",customerReminderConsent);
        } catch (Exception ex) {
	    logger.error("Data addition failed for ULIP Proposal Form Document:", ex);
            List<String> errorMessages = new ArrayList<String>();
            errorMessages.add("Data addition failed");
            throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.info("Mapping person details of proposal form document is completed successfully completed for transactionId {}",
                proposalDetails.getTransactionId());
        Context personalDetailsCxt = new Context();
        personalDetailsCxt.setVariables(dataVariables);

        logger.info("END {}", "%m");
        return personalDetailsCxt;
    }



    private Map<String, Object> setDependentDataForProposalForm(List<PartyInformation> partyInformationList,String formType, Map<String, Object> dataVariables, ProposalDetails proposalDetails) {
        String insuredfirstName = StringUtils.EMPTY;
        String insuredmiddleName = StringUtils.EMPTY;
        String insuredlastName = StringUtils.EMPTY;
        String insuredoccupation = StringUtils.EMPTY;
        String insuredannualIncome = StringUtils.EMPTY;
        Date insureddob = null;
        String insuredformattedDob = StringUtils.EMPTY;
        String insuredgender = "";
        String insuredformattedGender = StringUtils.EMPTY;
        String insuredtitle = StringUtils.EMPTY;
        String insurednationality = StringUtils.EMPTY;
        String insuredResidentialStatus = StringUtils.EMPTY;
        String insuredRelationToProposer = StringUtils.EMPTY;
        String insuredfatherFirstName = StringUtils.EMPTY;
        String insuredfatherLastName = StringUtils.EMPTY;
        String insuredRelation = StringUtils.EMPTY;
        String proposerRelation = "SELF";
        String insuredmaritialStatus = StringUtils.EMPTY;
        String insurededucation = StringUtils.EMPTY;
        String relationShipwithproposer = StringUtils.EMPTY;
        String insuredJobTitle;

        String schemeType = proposalDetails.getApplicationDetails().getSchemeType();

        if (!CollectionUtils.isEmpty(partyInformationList) && partyInformationList.size() >= 2) {
            BasicDetails insuredBasicDetails = partyInformationList.get(1).getBasicDetails();

            // Setting Dependent related personal Data
            if (checkingConditionForForm3Cases(proposalDetails, formType, schemeType)) {
                insuredfirstName = insuredBasicDetails.getFirstName();
                insuredmiddleName = insuredBasicDetails.getMiddleName();
                insuredlastName = insuredBasicDetails.getLastName();
                if(Utility.isChannelNeoOrAggregator(proposalDetails)){
                    insuredoccupation = proposalDetails.getEmploymentDetails().getPartiesInformation().get(1).getBasicDetails().getOccupation();
                    insuredJobTitle =  proposalDetails.getEmploymentDetails().getPartiesInformation().get(1).getPartyDetails().getJobTitle();
                    insuredoccupation = Stream.of(insuredoccupation, insuredJobTitle)
                        .filter(s -> !org.springframework.util.StringUtils.isEmpty(s)).collect(Collectors.joining(" / "));
                    insuredannualIncome = proposalDetails.getEmploymentDetails().getPartiesInformation().get(1).getBasicDetails().getAnnualIncome();
                }else {
                    insuredoccupation = insuredBasicDetails.getOccupation();
                    insuredannualIncome = insuredBasicDetails.getAnnualIncome();
                }
                insureddob = insuredBasicDetails.getDob();
                insuredformattedDob = Utility.dateFormatter(insureddob);
                insuredgender = insuredBasicDetails.getGender();
                insuredformattedGender = Utility.getGender(insuredgender);
                insuredtitle = Utility.getTitle(insuredgender);
                insurednationality = insuredBasicDetails.getNationalityDetails().getNationality();
                insuredResidentialStatus = insuredBasicDetails.getResidentialStatus();
                insuredRelationToProposer = Utility.evaluateConditionalOperation(StringUtils.equalsIgnoreCase(insuredBasicDetails.getRelationshipWithProposer(), "OTHERS")
                        , insuredBasicDetails.getRelationshipWithProposerWhenOther()
                        , insuredBasicDetails.getRelationshipWithProposer());

                String insuredFatherName = insuredBasicDetails.getFatherName();
                if (StringUtils.isNotBlank(insuredFatherName) && insuredFatherName.contains(" ")) {
                    insuredfatherFirstName = insuredFatherName.substring(0, insuredFatherName.indexOf(" "));
                    insuredfatherLastName = insuredFatherName.substring(insuredFatherName.indexOf(" "), insuredFatherName.length());
                } else {
                    insuredfatherFirstName = insuredFatherName;
                }


                insuredRelation = insuredBasicDetails.getRelationshipWithProposer();
                proposerRelation = AppConstants.NA;
                insuredmaritialStatus = Utility.evaluateConditionalOperation(StringUtils.isNotBlank(insuredBasicDetails.getMarriageDetails().getMaritalStatus())
                        , insuredBasicDetails.getMarriageDetails().getMaritalStatus()
                        , AppConstants.NA);
                insurededucation = Utility.evaluateConditionalOperation(StringUtils.isNotBlank(insuredBasicDetails.getEducation()), insuredBasicDetails.getEducation(), AppConstants.NA);
                if(isNeoOrAggregator){
                    insurededucation = settingValueOfEducation(insurededucation);
                }
                relationShipwithproposer = Utility.evaluateConditionalOperation(StringUtils.isNotBlank(insuredBasicDetails.getRelationshipWithProposer())
                        , insuredBasicDetails.getRelationshipWithProposer()
                        , AppConstants.NA);
                Utility.setInsuredContactDetails(proposalDetails, dataVariables);
            }
        }

        //Insured information
        dataVariables.put("insuredfirstName", insuredfirstName);
        dataVariables.put("insuredmiddleName", insuredmiddleName);
        dataVariables.put("insuredlastName", insuredlastName);
        dataVariables.put("insuredoccupation", insuredoccupation);
        dataVariables.put("insuredannualIncome", insuredannualIncome);
        dataVariables.put("insureddob", insuredformattedDob);
        dataVariables.put("insuredgender", insuredformattedGender);
        dataVariables.put("insuredtitle", insuredtitle);
        dataVariables.put("insurednationality", insurednationality);
        dataVariables.put("insuredResidentialStatus", insuredResidentialStatus);
        dataVariables.put("insuredRelationToProposer", insuredRelationToProposer);
        dataVariables.put("insuredfatherFirstName", insuredfatherFirstName);
        dataVariables.put("insuredfatherLastName", insuredfatherLastName);
        dataVariables.put("insuredrelationShipWithProposer", insuredRelation);
        dataVariables.put("relationShipWithProposer", proposerRelation);
        dataVariables.put("insuredmaritialStatus", insuredmaritialStatus);
        dataVariables.put("insurededucation", insurededucation);
        dataVariables.put("relationWithproposer", relationShipwithproposer);

        return dataVariables;
    }

    private boolean checkingConditionForForm3Cases(ProposalDetails proposalDetails, String formType, String schemeType){
         return AppConstants.DEPENDENT.equalsIgnoreCase(formType) ||
                (Utility.isChannelNeoOrAggregator(proposalDetails) && Utility.isApplicationIsForm2(proposalDetails)) ||
                (AppConstants.FORM3.equalsIgnoreCase(formType) && !Utility.schemeBCase(schemeType));
    }




    private Map<String, Object> setCommunicationDataForProposalForm(List<PartyInformation> partyInformationList, Map<String, Object> dataVariables) throws UserHandledException {
        String email = StringUtils.EMPTY;
        String mobileNumber1 = StringUtils.EMPTY;
        String mobileNumber2 = StringUtils.EMPTY;
        String stdCode = StringUtils.EMPTY;
        String landLineNumber = StringUtils.EMPTY;
        String proposerMobileNumber1 = StringUtils.EMPTY;
        String proposerMobileNumber2 = StringUtils.EMPTY;
        String proposerCommStd = StringUtils.EMPTY;
        String proposerCommLandLineNumber = StringUtils.EMPTY;
        String proposerCommunicationAddEmail = StringUtils.EMPTY;
        if (!CollectionUtils.isEmpty(partyInformationList)) {
            List<Phone> communicationAddressPhoneList = partyInformationList.get(0).getPersonalIdentification().getPhone();
            // 19. Communication details - Permanent address
            if (!CollectionUtils.isEmpty(communicationAddressPhoneList)) {
                mobileNumber1 = communicationAddressPhoneList.get(0).getPhoneNumber();
                if ( communicationAddressPhoneList.size() >= 2) {
                    mobileNumber2 = communicationAddressPhoneList.get(1).getPhoneNumber();
                }
                if ( communicationAddressPhoneList.size() >= 3) {
                    stdCode = communicationAddressPhoneList.get(2).getStdIsdCode();
                    landLineNumber = communicationAddressPhoneList.get(2).getPhoneNumber();
                }

                // 18. Communication details - Communication address
                proposerMobileNumber1 = communicationAddressPhoneList.get(0).getPhoneNumber();
                if (isNeoOrAggregator && Objects.nonNull(communicationAddressPhoneList) && communicationAddressPhoneList.size() >= 2) {
                    proposerMobileNumber2 = communicationAddressPhoneList.get(1).getPhoneNumber();
                }
                if (communicationAddressPhoneList.size() >= 5) {
                    proposerMobileNumber2 = communicationAddressPhoneList.get(4).getPhoneNumber();
                }
                if ( communicationAddressPhoneList.size() >= 4) {
                    proposerCommStd = communicationAddressPhoneList.get(3).getStdIsdCode();
                    proposerCommLandLineNumber = communicationAddressPhoneList.get(3).getPhoneNumber();
                }
            }

            proposerCommunicationAddEmail = partyInformationList.get(0).getPersonalIdentification().getEmail();
            email = partyInformationList.get(0).getPersonalIdentification().getEmail();
        }
        dataVariables.put("mobileNumber", mobileNumber1);
        dataVariables.put("alternate", mobileNumber2);
        dataVariables.put("std", stdCode);
        dataVariables.put("landline", landLineNumber);
        dataVariables.put("email", Utility.evaluateConditionalOperation(StringUtils.isNotBlank(email), email, AppConstants.WHITE_SPACE));

        dataVariables.put("proposerMobileNumber1", proposerMobileNumber1);
        dataVariables.put("proposerMobileNumber2", proposerMobileNumber2);
        dataVariables.put("proposerCommStd", proposerCommStd);
        dataVariables.put("proposerCommLandLineNumber", proposerCommLandLineNumber);
        dataVariables.put("proposerCommunicationAddEmail", proposerCommunicationAddEmail);

        return dataVariables;
    }


    private Map<String, Object> setProposalNomineeDetailsForProposalForm(List<PartyDetails> nomineeDetails, Map<String, Object> dataVariables,String channel) throws UserHandledException {

        List<ProposalNomineeDetails> proposalNomineeDetails = new ArrayList<>();
        ProposalNomineeDetails details;

        if (!CollectionUtils.isEmpty(nomineeDetails)) {
            for (int i = 0; i < nomineeDetails.size(); i++) {
                details = new ProposalNomineeDetails();
                PartyDetails nominee = nomineeDetails.get(i);
                details.setDob(Utility.dateFormatter(nominee.getDob()));
                details.setFirstName(nominee.getFirstName());
                details.setLastName(nominee.getLastName());
                details.setMiddleName(nominee.getMiddleName());
                details.setPercentageShare((Integer)Utility.evaluateConditionalOperation(nomineeDetails.size() == 1 , 100 , nominee.getPercentageShare()));
                details.setGender(Utility.getGender(nominee.getGender()));
                details.setTitle(Utility.getTitle(nominee.getGender()));
                if (isNeoOrAggregator) {
                    details.setRelationshipWithProposer(nominee.getRelationshipWithProposer());
                    details.setDob(Utility.dateFormatter(
                            Utility.dateFormatter(nominee.getDob()), "dd-MM-yyyy", "dd/MM/yyyy"));
                    if(Objects.nonNull(nominee.getAppointeeDetails()) && !Strings.isNullOrEmpty(nominee.getAppointeeDetails().getAppointeeGender())){
                        details.setAppointeeGender(Utility.getGender(nominee.getAppointeeDetails().getAppointeeGender()));
                    }
                } else {
                    details.setRelationshipWithProposer((String)dataVariables.get("relationWithproposer"));
                    details.setDob(Utility.dateFormatter(nominee.getDob()));
                }
                if ("Others".equalsIgnoreCase(nominee.getRelationshipWithProposer())) {
                    details.setNomineeRealtionshipOther(AppConstants.YES);
                    details.setSpecifyRelationship(nominee.getRelationshipOthers());
                    details.setReasonForNomination(nominee.getReasonForNomination());
                }
                if (Utility.andTwoExpressions(
                        nominee.getDob() != null, Float.valueOf(Utility.getAge(nominee.getDob())) < 18)) {
                    details.setNomineeMinor(AppConstants.YES);
                    AppointeeDetails appointeeDetails =(AppointeeDetails)Utility.evaluateConditionalOperation(nominee.getAppointeeDetails() != null, nominee.getAppointeeDetails(), new AppointeeDetails());
                    details.setGuardianName(appointeeDetails.getGuardianNameOfNominee());
                    if(AppConstants.INFLUENCER_CHANNEL_38.equalsIgnoreCase(channel) && Objects.nonNull(appointeeDetails.getRelationwithNominee())){
                        details.setGuardianRelation(appointeeDetails.getRelationwithNominee());
                        if (!org.springframework.util.StringUtils.isEmpty(appointeeDetails.getRelationwithNomineeOthers()) && AppConstants.OTHERS.equalsIgnoreCase(appointeeDetails.getRelationwithNominee())){
                            details.setGuardianRelation(appointeeDetails.getRelationwithNomineeOthers());
                        }
                    }else {
                        details.setGuardianRelation(isNeoOrAggregator ? appointeeDetails.getGuardianIsYour() : appointeeDetails.getRelationwithNominee());
                    }
                    if (Utility.andTwoExpressions(
                            !StringUtils.isEmpty(appointeeDetails.getRelationwithNominee())
                            ,"Others".equalsIgnoreCase(appointeeDetails.getRelationwithNominee()))) {
                        details.setGuardianRelationshipOther(AppConstants.YES);
                        details.setGuardianSpecifyRelation(appointeeDetails.getRelationwithNomineeOthers());
                    }
                    if (AppConstants.THANOS_CHANNEL.equalsIgnoreCase(channel)) {
                        logger.info("setting appointeeDetails for thanos case {}  {} ", appointeeDetails.getGuardianName() ,appointeeDetails.getGuardianRelation());
                        details.setGuardianName(appointeeDetails.getGuardianName());
                        details.setGuardianRelation(appointeeDetails.getGuardianRelation());
                    }
                }
                proposalNomineeDetails.add(details);
            }
        }
        dataVariables.put("nomineeDetailsCount", proposalNomineeDetails);
        return dataVariables;
    }



    private Map<String, Object> setIndustryDataForProposalForm(ProposalDetails proposalDetails, Map<String, Object> dataVariables) throws UserHandledException {
        String insuredindustryType = StringUtils.EMPTY;
        String insuredorganisationType = StringUtils.EMPTY;
        String insuredemployerName = StringUtils.EMPTY;
        String industryType = StringUtils.EMPTY;
        String industryTypeOthers =StringUtils.EMPTY;
        String organisationType = StringUtils.EMPTY;
        String employerName = StringUtils.EMPTY;

        String formType = proposalDetails.getApplicationDetails().getFormType();
        List<PartyInformation> proposerEmploymentPartyInformation = proposalDetails.getEmploymentDetails().getPartiesInformation();
        List<PartyInformation> partyInformationList = proposalDetails.getPartyInformation();

        String schemeType = proposalDetails.getApplicationDetails().getSchemeType();

        if (!CollectionUtils.isEmpty(partyInformationList)) {
            if (!CollectionUtils.isEmpty(proposerEmploymentPartyInformation)) {
                setDataForIndustry(dataVariables, proposerEmploymentPartyInformation.get(0).getPartyDetails().getIndustryDetails());
                if (isNeoOrAggregator) {
                    dataVariables.put("navyReflexive1", proposerEmploymentPartyInformation.get(0).getPartyDetails().getRank());
                }
                industryType = proposerEmploymentPartyInformation.get(0).getPartyDetails().getIndustryDetails().getIndustryType();
                //NEORW: Added for Industry Type Others
                industryTypeOthers = Utility.nullSafe(proposerEmploymentPartyInformation.get(0)
                        .getPartyDetails().getIndustryDetails().getIndustryTypeOthers());

                organisationType = proposerEmploymentPartyInformation.get(0).getPartyDetails().getOrganisationType();
                employerName = proposerEmploymentPartyInformation.get(0).getPartyDetails().getNameOfEmployer();

                // FUL2-156443- Populating incorrectly nameOfEmployer value
                List<PartyInformation> partyInfoList = proposalDetails.getPartyInformation();
                if(!org.springframework.util.StringUtils.isEmpty(partyInfoList.get(0).getBSE500Company()) && partyInfoList.get(0).getBSE500Company()!=null && org.springframework.util.StringUtils.isEmpty(employerName)){
                    if(AppConstants.OTHER.equalsIgnoreCase(partyInfoList.get(0).getBSE500Company()) && partyInfoList.get(0).getBasicDetails().getCompanyName() !=null && !org.springframework.util.StringUtils.isEmpty(partyInfoList.get(0).getBasicDetails().getCompanyName())){
                        employerName=partyInfoList.get(0).getBasicDetails().getCompanyName();}
                    else {  employerName= partyInfoList.get(0).getBSE500Company();}
                }
            }

            // Setting Industry Data for Insured
            if (proposerEmploymentPartyInformation.size() >= 2 && (AppConstants.DEPENDENT.equalsIgnoreCase(formType) ||
                    checkNeoAndAggregatorForForm2(proposalDetails) || (AppConstants.FORM3.equalsIgnoreCase(formType) && !Utility.schemeBCase(schemeType)))) {
                    setDataForIndustryInsured(dataVariables, proposerEmploymentPartyInformation.get(1).getPartyDetails().getIndustryDetails());
                    // Industry data for Insured
                    insuredindustryType = proposerEmploymentPartyInformation.get(1).getPartyDetails().getIndustryDetails().getIndustryType();

                    insuredorganisationType = proposerEmploymentPartyInformation.get(1).getPartyDetails().getOrganisationType();
                    insuredemployerName = Utility.evaluateConditionalOperation(
                            StringUtils.isEmpty(proposerEmploymentPartyInformation.get(1).getPartyDetails().getNameOfEmployer()), "NA"
                            , proposerEmploymentPartyInformation.get(1).getPartyDetails().getNameOfEmployer());
            } else {
                logger.info("No Insured employment information available!");
            }
        }

        dataVariables.put("organisationType", Utility.evaluateConditionalOperation(StringUtils.isNotBlank(organisationType), organisationType, AppConstants.WHITE_SPACE));
        dataVariables.put("nameOfEmployer", Utility.evaluateConditionalOperation(StringUtils.isNotBlank(employerName), employerName, AppConstants.WHITE_SPACE));
        dataVariables.put("industryType", Utility.evaluateConditionalOperation(StringUtils.isNotBlank(industryType), StringUtils.upperCase(industryType), AppConstants.BLANK));
        dataVariables.put("industryTypeOthers", industryTypeOthers);
        dataVariables.put("insuredindustryType", Utility.evaluateConditionalOperation(StringUtils.isNotBlank(insuredindustryType), StringUtils.upperCase(insuredindustryType), AppConstants.BLANK));
        dataVariables.put("insuredorganisationType", insuredorganisationType);
        dataVariables.put("insurednameOfEmployer", insuredemployerName);
        return dataVariables;
    }

    private boolean checkNeoAndAggregatorForForm2(ProposalDetails proposalDetails) {
        return isNeoOrAggregator && Utility.isApplicationIsForm2(proposalDetails);
    }

    /**
     * Industry Related section data setting
     *
     * @param dataVariables
     * @param industryDetails
     * @return
     */
    private Map<String, Object> setDataForIndustry(Map<String, Object> dataVariables, IndustryDetails industryDetails) {
        String industryType = industryDetails.getIndustryType();
        industryType = StringUtils.isNotBlank(industryType) ? industryType : AppConstants.BLANK;
        dataVariables.put("industryType",industryType);
        IndustryInfo industryInfo = industryDetails.getIndustryInfo();
        switch (industryType) {

            case AppConstants.DEFENCE:
                dataVariables.put("defenceReflexive1", industryInfo.isPostedOnDefenceLocation() ? "YES" : "NO");
                dataVariables.put("defenceReflexive2", industryInfo.getNatureOfRole());
                dataVariables.put("defence", true);
                break;

            case AppConstants.DIVING:
                dataVariables.put("divingReflexive1", industryInfo.isProfessionalDiver() ? "YES" : "NO");
                dataVariables.put("divingReflexive2", industryInfo.getDiveLocation());
                dataVariables.put("diving", true);
                break;
            case AppConstants.MINING:
                dataVariables.put("miningReflexive1", industryInfo.isWorkingInsideMine() ? "YES" : "NO");
                dataVariables.put("miningReflexive2", industryInfo.isAnyIllnessRelatedToOccupation() ? "YES" : "NO");
                dataVariables.put("mining", true);
                break;
            case AppConstants.AIR_FORCE:
                dataVariables.put("aviationReflexive1", industryInfo.isFlying() ? "YES" : "NO");
                dataVariables.put("isFlying", true);
                dataVariables.put("airforceReflexive2", industryInfo.getTypeOfAirCraft());
                break;
            case AppConstants.OIL:
                dataVariables.put("oilReflexive1", industryInfo.isBasedAtOffshore() ? "YES" : "NO");
                dataVariables.put("oil", true);
                break;
            case AppConstants.NAVY:
                dataVariables.put("navyReflexive1", industryInfo.getNavyAreaWorking());
                dataVariables.put("navy", true);
                break;
            default:
                logger.info("No Industry type found");

        }
        logger.info("Data set for proposer industry, Personal Details");
        return dataVariables;
    }

    /**
     * Insured person industry industry data setting
     *
     * @param dataVariables
     * @param industryDetails
     * @return
     */
    private Map<String, Object> setDataForIndustryInsured(Map<String, Object> dataVariables, IndustryDetails industryDetails) {
        String industryType = industryDetails.getIndustryType();
        industryType = StringUtils.isNotBlank(industryType) ? industryType : AppConstants.BLANK;
        dataVariables.put("insuredindustryType", industryType);
        IndustryInfo industryInfo = industryDetails.getIndustryInfo();
        if (StringUtils.isNotBlank(industryType)) {
            baseMapper.getIndustryType(dataVariables, industryType, industryInfo,false);
            logger.info("Data set for insured industry, Personal Details transactionId : {}", transactionId);
        } else {
            logger.info("Data not available for insured, Personal Details transactionId : {} ", transactionId);
        }

        return dataVariables;
    }

    private String settingValueOfEducation(String education) {
        if (AppConstants.HIGH_SCHOOL.equalsIgnoreCase(education) || "01".equalsIgnoreCase(education)) {
            education = AppConstants.TENTH_PASS;
        } else if ((AppConstants.SENIOR_SCHOOL).equalsIgnoreCase(education) || "08".equalsIgnoreCase(education)) {
            education = AppConstants.TENTH_PLUS_TWO_PASS;
        }
        return education;
    }


}
