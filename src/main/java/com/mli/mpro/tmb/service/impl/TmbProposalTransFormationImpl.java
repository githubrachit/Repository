package com.mli.mpro.tmb.service.impl;

import com.mli.mpro.axis.models.BancaDetails;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.location.services.PincodeMasterService;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.*;
import com.mli.mpro.tmb.model.AccountDetail;
import com.mli.mpro.tmb.model.SaveProposalRequest;
import com.mli.mpro.tmb.utility.TMBMasterData;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

import static com.mli.mpro.productRestriction.util.AppConstants.FIELD_MODIFIED;
import static com.mli.mpro.productRestriction.util.AppConstants.FIELD_NOT_MODIFIED;


@Service
public class TmbProposalTransFormationImpl {

    private static final Logger log = LoggerFactory.getLogger(TmbProposalTransFormationImpl.class);

    @Autowired
    private PincodeMasterService pincodeMasterService;


    public void addBancaDetails(SaveProposalRequest request, ProposalDetails proposalDetails) {
        com.mli.mpro.axis.models.BancaDetails bancaDetails = new BancaDetails();
        bancaDetails.setCustomerId(Utility.evaluateFiledValue(request.getCustomerId()));
        bancaDetails.setInsurerCustomerID("");
        bancaDetails.setUkyc(Utility.evaluateFiledValue(request.getCustomerDetailsResponse().getKycFlag()));
        bancaDetails.setCustomerClassification((Utility.evaluateFiledValue(request.getCustomerDetailsResponse().getCustomerClassification())));
        proposalDetails.setBancaDetails(bancaDetails);
    }

    public void addPartyInformation(SaveProposalRequest request, ProposalDetails proposalDetails) {
        PartyInformation partyInformation = new PartyInformation();
        String residentialStatus = "";
        String applicationSource = "";
        String nationality = Utility.evaluateFiledValue(TMBMasterData.getCountry(request.getCustomerDetailsResponse().getNationality()));

        boolean isIndian = AppConstants.INDIA_COUNTRY.equalsIgnoreCase(nationality);
        List<PartyInformation> partyInformationList = new ArrayList<>();
        partyInformation.setPartyType(AppConstants.PROPOSER);

        BasicDetails basicDetails = new BasicDetails();
        basicDetails.setOccupation(Utility.evaluateFiledValue(TMBMasterData.getOccupation(request.getCustomerDetailsResponse().getOccupationType())));
        basicDetails.setEducation(Utility.evaluateFiledValue(TMBMasterData.getEducation(request.getCustomerDetailsResponse().getEducation())));
        basicDetails.setFirstName(Utility.evaluateFiledValue(request.getCustomerDetailsResponse().getCustomerFirstName()));
        basicDetails.setLastName(Utility.evaluateFiledValue(request.getCustomerDetailsResponse().getCustomerLastName()));
        basicDetails.setGender(Utility.evaluateFiledValue(request.getCustomerDetailsResponse().getGender()));
        basicDetails.setDob(Utility.stringToDate(request.getCustomerDetailsResponse().getDob()));
        basicDetails.setAnnualIncome(Utility.evaluateFiledValue(String.valueOf(request.getCustomerDetailsResponse().getAnnualIncome())));
        if (StringUtils.hasText(nationality) && isIndian) {
            residentialStatus = AppConstants.INDIAN_RESIDENTIAL_STATUS;
            applicationSource = AppConstants.INDIA_SMALL;
        }
        basicDetails.setResidentialStatus(residentialStatus);
        basicDetails.setAreBothAddressSame(false);


        MarriageDetails marriageDetails = new MarriageDetails();
        marriageDetails.setMaritalStatus(Utility.evaluateFiledValue(TMBMasterData.getMaritalStatus(request.getCustomerDetailsResponse().getMaritalStatus())));
        basicDetails.setMarriageDetails(marriageDetails);

        NationalityDetails nationalityDetails = new NationalityDetails();
        nationalityDetails.setNationality(residentialStatus);
        NRIDetails nriDetails = new NRIDetails();
        nriDetails.setApplicationSource(applicationSource);
        nationalityDetails.setNriDetails(nriDetails);
        basicDetails.setNationalityDetails(nationalityDetails);


        List<Address> addressList = new ArrayList<>();
        Address currentAddress = new Address();
        currentAddress.setAddressType(AppConstants.ADDRESS_CURRENT);
        AddressDetails addressDetails = new AddressDetails();
        String cAddress = Utility.evaluateFiledValue(request.getCustomerDetailsResponse().getCommunicationAddress());
        setHouseNoAreaVillageLandmarkToAddressObject(addressDetails, cAddress.trim());
        String currentPinCode = Utility.evaluateFiledValue(request.getCustomerDetailsResponse().getCommunicationPincode());
        if (StringUtils.hasText(currentPinCode)) {
            addressDetails.setPinCode(currentPinCode);
            HashMap<String, String> stateCityByPinCode = pincodeMasterService.getStatesAndCitiesByPincode(currentPinCode);
            if (Objects.nonNull(stateCityByPinCode)) {
                String state = stateCityByPinCode.get(AppConstants.PINCODE_STATE);
                String city = stateCityByPinCode.get(AppConstants.PINCODE_CITY);
                addressDetails.setCity(city);
                addressDetails.setState(state);
            }

        }
        addressDetails.setCountry(Utility.evaluateFiledValue(TMBMasterData.getCountry(request.getCustomerDetailsResponse().getCommunicationCountry())));
        currentAddress.setAddressDetails(addressDetails);
        addressList.add(currentAddress);

        Address permanentAddress = new Address();
        permanentAddress.setAddressType(AppConstants.PERMANENT);
        AddressDetails permanentAddressDetails = new AddressDetails();
        String pAddress = Utility.evaluateFiledValue(request.getCustomerDetailsResponse().getPermanentAddress());
        setHouseNoAreaVillageLandmarkToAddressObject(permanentAddressDetails, pAddress.trim());
        permanentAddressDetails.setCity(request.getCustomerDetailsResponse().getPermanentCity());
        permanentAddressDetails.setState(request.getCustomerDetailsResponse().getPermanentState());
        permanentAddressDetails.setPinCode(request.getCustomerDetailsResponse().getPermanentPincode());
        String permanentPinCode = Utility.evaluateFiledValue(request.getCustomerDetailsResponse().getPermanentPincode());
        if (StringUtils.hasText(permanentPinCode)) {
            HashMap<String, String> stateCityByPinCode = pincodeMasterService.getStatesAndCitiesByPincode(permanentPinCode);
            if(Objects.nonNull(stateCityByPinCode)){
                String state = stateCityByPinCode.get(AppConstants.PINCODE_STATE);
                String city = stateCityByPinCode.get(AppConstants.PINCODE_CITY);
                permanentAddressDetails.setCity(city);
                permanentAddressDetails.setState(state);
            }

        }
        permanentAddressDetails.setCountry(Utility.evaluateFiledValue(TMBMasterData.getCountry(request.getCustomerDetailsResponse().getPermanentCountry())));
        permanentAddress.setAddressDetails(permanentAddressDetails);
        addressList.add(permanentAddress);
        basicDetails.setAddress(addressList);
        partyInformation.setBasicDetails(basicDetails);


        PersonalIdentification personalIdentification = new PersonalIdentification();
        List<Phone> phoneList = new ArrayList<>();
        Phone phone = new Phone();
        phone.setPhoneNumber(getPhoneNumberWithOutCountryCode(request.getCustomerDetailsResponse().getMobileNumber()));
        phone.setStdIsdCode("+91");
        phone.setPhoneType(AppConstants.MOBILE_NUMBER);
        phoneList.add(phone);
        personalIdentification.setPhone(phoneList);
        personalIdentification.setEmail(Utility.evaluateFiledValue(request.getCustomerDetailsResponse().getEmailId()));
        PanDetails panDetails = new PanDetails();
        panDetails.setPanNumber(Utility.evaluateFiledValue(request.getCustomerDetailsResponse().getPan()));
        panDetails.setPANExist(false);
        personalIdentification.setPanDetails(panDetails);
        partyInformation.setPersonalIdentification(personalIdentification);
        partyInformationList.add(partyInformation);
        proposalDetails.setPartyInformation(partyInformationList);
    }

    public PartyInformation getInsuredPartyInformation(SaveProposalRequest request) {
        PartyInformation insuredInfo = new PartyInformation();
        try {
            String residentialStatus = "";
            String applicationSource = "";
            BasicDetails basicDetails = new BasicDetails();
            insuredInfo.setPartyType("Insured");
            String nationality = Utility.evaluateFiledValue(TMBMasterData.getCountry(request.getCustomerDetailsResponse().getNationality()));

            boolean isIndian = AppConstants.INDIA_COUNTRY.equalsIgnoreCase(nationality);
            basicDetails.setOccupation(Utility.evaluateFiledValue(TMBMasterData.getOccupation(request.getCustomerDetailsResponse().getOccupationType())));
            basicDetails.setEducation(Utility.evaluateFiledValue(TMBMasterData.getEducation(request.getCustomerDetailsResponse().getEducation())));
            basicDetails.setFirstName(Utility.evaluateFiledValue(request.getCustomerDetailsResponse().getCustomerFirstName()));
            basicDetails.setLastName(Utility.evaluateFiledValue(request.getCustomerDetailsResponse().getCustomerLastName()));
            basicDetails.setGender(Utility.evaluateFiledValue(request.getCustomerDetailsResponse().getGender()));
            basicDetails.setDob(Utility.stringToDate(request.getCustomerDetailsResponse().getDob()));

            if (StringUtils.hasText(nationality) && isIndian) {
                residentialStatus = AppConstants.INDIAN_RESIDENTIAL_STATUS;
                applicationSource = AppConstants.INDIA_SMALL;
            }
            basicDetails.setResidentialStatus(residentialStatus);
            MarriageDetails marriageDetails = new MarriageDetails();
            marriageDetails.setMaritalStatus(Utility.evaluateFiledValue(TMBMasterData.getMaritalStatus(request.getCustomerDetailsResponse().getMaritalStatus())));
            basicDetails.setMarriageDetails(marriageDetails);

            NationalityDetails nationalityDetails = new NationalityDetails();
            nationalityDetails.setNationality(residentialStatus);
            NRIDetails nriDetails = new NRIDetails();
            nriDetails.setApplicationSource(applicationSource);
            nationalityDetails.setNriDetails(nriDetails);
            basicDetails.setNationalityDetails(nationalityDetails);
            insuredInfo.setBasicDetails(basicDetails);
        }catch (Exception e){
            log.error("Error while getting insured party information for transactionId {} {}", request.getTransactionId(),Utility.getExceptionAsString(e));
        }
        return insuredInfo;
    }

    public void addBank(SaveProposalRequest request, ProposalDetails proposalDetails) {
        Bank bank = new Bank();
        List<BankDetails> bankDetails = new ArrayList<>();
        List<BankDetails> bankDetailsObject = new ArrayList<>();
        PartnerDetails partnerDetails = new PartnerDetails();
        List<AccountDetail> accountDetails = request.getAccountDetailsResponse().getResponse();
        for (AccountDetail accountDetail : accountDetails) {
            String accountType = TMBMasterData.getAccountType(Utility.evaluateFiledValue(accountDetail.getAccountType()));
            BankDetails bankDetails1 = new BankDetails();
            bankDetails1.setAccountHolderName(Utility.evaluateFiledValue(request.getCustomerDetailsResponse().getAccountHolderName()));
            bankDetails1.setBankAccountNumber(Utility.evaluateFiledValue(accountDetail.getAccountnumber()));
            bankDetails1.setTypeOfAccount(accountType);
            bankDetails1.setIfsc(Utility.evaluateFiledValue(accountDetail.getIfsc()));
            bankDetails1.setBankName(Utility.evaluateFiledValue(accountDetail.getBankName()));
            bankDetails1.setBankBranch(Utility.evaluateFiledValue(accountDetail.getBranchName()));
            bankDetails1.setMicr(Utility.evaluateFiledValue(accountDetail.getMicr()));
            bankDetails1.setBankAccOpeningDate(Utility.stringToDate(accountDetail.getBankAccOpeningDate()));
            bankDetails.add(bankDetails1);
        }
        bankDetailsObject.add(bankDetails.get(0));
        bank.setBankDetails(bankDetailsObject);
        proposalDetails.setBank(bank);
        partnerDetails.setAccounts(bankDetails);
        proposalDetails.setPartnerDetails(partnerDetails);
    }

    public void addCkycDetails(SaveProposalRequest request, ProposalDetails proposalDetails) {
        CkycDetails ckycDetails = new CkycDetails();
        ckycDetails.setMothersFirstName(AppConstants.EMPTY_STRING);
        ckycDetails.setMothersLastName(AppConstants.EMPTY_STRING);
        String ckycNumber = request.getCustomerDetailsResponse().getCkycNo();
        ckycDetails.setDoYouHaveCKYCNumber((null == ckycNumber || AppConstants.EMPTY_STRING.equals(ckycNumber)) ? AppConstants.CAMEL_NO : AppConstants.CAMEL_YES);
        ckycDetails.setAddressProofType(AppConstants.EMPTY_STRING);
        ckycDetails.setPleaseSpecify(AppConstants.EMPTY_STRING);
        ckycDetails.setAddressProofExpiryDate(AppConstants.EMPTY_STRING);
        ckycDetails.setIdProofType(AppConstants.EMPTY_STRING);
        ckycDetails.setIdProofnumber(AppConstants.EMPTY_STRING);
        ckycDetails.setIdProofExpiryDate(AppConstants.EMPTY_STRING);
        ckycDetails.setNriCityOfBirth(AppConstants.EMPTY_STRING);
        proposalDetails.setCkycDetails(ckycDetails);
        ckycDetails.setCkycNumber(ckycNumber);
        proposalDetails.setCkycDetails(ckycDetails);
    }

    private String getPhoneNumberWithOutCountryCode(String mobileNumber) {
        String phoneNumber = mobileNumber;
        if (phoneNumber != null && phoneNumber.length() > 10) {
            phoneNumber = phoneNumber.substring(phoneNumber.length() - 10);
        }
        return phoneNumber;

    }

    public void addNomineeDetails(ProposalDetails proposalDetails) {
        List<PartyDetails> partyDetailsList = new ArrayList<>();
        PartyDetails partyDetails = new PartyDetails();
        partyDetailsList.add(partyDetails);
        proposalDetails.setNomineeDetails(new NomineeDetails(partyDetailsList));
    }

    public void addSourcingDetails(SaveProposalRequest request,ProposalDetails proposalDetails) {
        SourcingDetails sourcingDetails = new SourcingDetails();
        sourcingDetails.setAgentId(request.getAgentId());
        proposalDetails.setSourcingDetails(sourcingDetails);
    }


    public void addChannelDetails(ProposalDetails proposalDetails) {
        ChannelDetails channelDetails = new ChannelDetails();
        channelDetails.setChannel(AppConstants.CHANNEL_TMB);
        proposalDetails.setChannelDetails(channelDetails);
    }

    public void addProductDetails(ProposalDetails proposalDetails) {
        List<ProductDetails> productDetailsList = new ArrayList<>();
        ProductDetails productDetails = new ProductDetails();
        ProductInfo productInfo = new ProductInfo();
        productDetails.setProductInfo(productInfo);
        productDetailsList.add(productDetails);
        proposalDetails.setProductDetails(productDetailsList);
    }

    public void addPsmDetails(ProposalDetails proposalDetails) {
        PSMDetails psmDetails = new PSMDetails();
        proposalDetails.setPsmDetails(psmDetails);
    }

    public void addApplicationDetails(ProposalDetails proposalDetails) {

        ApplicationDetails applicationDetails = new ApplicationDetails();
        applicationDetails.setApplicationStatus(AppConstants.INITIATED_MSG);
        applicationDetails.setStage(AppConstants.ONE);
        applicationDetails.setFormType(AppConstants.SELF);
        applicationDetails.setCreatedTime(new Date());
        applicationDetails.setUpdatedTime(new Date());
        proposalDetails.setApplicationDetails(applicationDetails);
    }


    public void addAdditonalFlag(ProposalDetails proposalDetails) {
        AdditionalFlags additionalFlags = new AdditionalFlags();
        additionalFlags.setCurrentActiveScreen(1);
        additionalFlags.setRequestSource("MPRO");
        additionalFlags.setSourceChannel(AppConstants.REQUEST_SOURCE_TMB);
        additionalFlags.setIsSellerDeclarationApplicable(AppConstants.YES_LOWERCASE);
        additionalFlags.setJourneyType(AppConstants.REQUEST_SOURCE_TMB);
        settingJourneyFieldsModificationStatus(proposalDetails, additionalFlags);
        if (Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.FormC_ALL))) {
            additionalFlags.setFormCFeatureEnabled(AppConstants.Y);
        } else {
            additionalFlags.setFormCFeatureEnabled(AppConstants.N);
        }
        additionalFlags.setInsurerVerified(false);
        proposalDetails.setAdditionalFlags(additionalFlags);
    }

    private void settingJourneyFieldsModificationStatus(ProposalDetails proposalDetails, AdditionalFlags additionalFlags) {
        try {
            JourneyFieldsModificationStatus journeyFieldsModificationStatus = new JourneyFieldsModificationStatus();
            PartyInformation partyInformation = proposalDetails.getPartyInformation().get(0);

            journeyFieldsModificationStatus.setNameStatus(getFieldStatus(
                    partyInformation.getBasicDetails().getFirstName(),
                    partyInformation.getBasicDetails().getLastName())
            );


            journeyFieldsModificationStatus.setDobStatus(getFieldStatus(partyInformation.getBasicDetails().getDob()));


            journeyFieldsModificationStatus.setPanStatus(getFieldStatus(partyInformation.getPersonalIdentification().getPanDetails().getPanNumber()));


            journeyFieldsModificationStatus.setCommunicationAddStatus(getAddressFieldStatus(proposalDetails, 0));


            journeyFieldsModificationStatus.setPermanentAddStatus(getAddressFieldStatus(proposalDetails, 1));

            journeyFieldsModificationStatus.setNeftBankDetailsStatus(getBankFieldStatus(proposalDetails));
            additionalFlags.setJourneyFieldsModificationStatus(journeyFieldsModificationStatus);
        } catch (Exception e) {
            log.error("Error while setting journey fields modification status: {}", Utility.getExceptionAsString(e));
        }
    }

    private String getBankFieldStatus(ProposalDetails proposalDetails) {
        if(proposalDetails.getBank() != null && proposalDetails.getBank().getBankDetails() != null && !proposalDetails.getBank().getBankDetails().isEmpty()){
            BankDetails bankDetails = proposalDetails.getBank().getBankDetails().get(0);
            return getFieldStatus(
                    bankDetails.getAccountHolderName(),
                    bankDetails.getBankAccountNumber(),
                    bankDetails.getIfsc(),
                    bankDetails.getTypeOfAccount(),
                    bankDetails.getBankBranch(),
                    bankDetails.getMicr(),
                    bankDetails.getBankAccOpeningDate().toString()
            );
        }
        return FIELD_MODIFIED;
    }

    private String getFieldStatus(String... fields) {
        for (String field : fields) {
            if (!StringUtils.hasText(field)) {
                return FIELD_MODIFIED;
            }
        }
        return FIELD_NOT_MODIFIED;
    }

    private String getFieldStatus(Date field) {
        return field != null ? FIELD_NOT_MODIFIED : FIELD_MODIFIED;
    }

    private String getAddressFieldStatus(ProposalDetails proposalDetails, int index) {
        if (proposalDetails.getPartyInformation() != null && !proposalDetails.getPartyInformation().isEmpty()) {
            Address address = proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress().get(index);
            if (address != null && address.getAddressDetails() != null) {
                AddressDetails addressDetails = address.getAddressDetails();
                return getFieldStatus(
                        addressDetails.getHouseNo(),
                        addressDetails.getArea(),
                        addressDetails.getCity(),
                        addressDetails.getState(),
                        addressDetails.getPinCode(),
                        addressDetails.getCountry()
                );
            }
        }
        return FIELD_MODIFIED;
    }

    public void addEmploymentDetails(ProposalDetails proposalDetails) {
        EmploymentDetails employmentDetails = new EmploymentDetails();
        employmentDetails.setEIAExist(false);
        employmentDetails.setEIANumberExist(false);
        proposalDetails.setEmploymentDetails(employmentDetails);
    }

    public void addPaymentDetails(ProposalDetails proposalDetails) {
        Receipt receipt = new Receipt();
        proposalDetails.setPaymentDetails(new PaymentDetails(Collections.singletonList(receipt)));
    }

    private void setHouseNoAreaVillageLandmarkToAddressObject(AddressDetails addressDetailsObject, String addressStr) {

        Map<Integer, String> linkedHashMap;
        if (addressStr.length() <= 60) {
            if (addressStr.length() < 30) {
                addressDetailsObject.setHouseNo(addressStr);
                addressDetailsObject.setArea(AppConstants.BLANK);
            } else {
                linkedHashMap = callStringPartiton(addressStr, 30);
                addressDetailsObject.setHouseNo(linkedHashMap.get(0));
                addressDetailsObject.setArea(linkedHashMap.get(1));
            }
        } else if (addressStr.length() >= 60 && addressStr.length() <= 120) {
            linkedHashMap = callStringPartiton(addressStr, 60);
            addressDetailsObject.setHouseNo(linkedHashMap.get(0));
            addressDetailsObject.setArea(linkedHashMap.get(1));
        } else if (addressStr.length() >= 120 && addressStr.length() <= 180) {
            linkedHashMap = callStringPartiton(addressStr, 60);
            addressDetailsObject.setHouseNo(linkedHashMap.get(0));

            linkedHashMap = callStringPartiton(linkedHashMap.get(1), 60);
            addressDetailsObject.setArea(linkedHashMap.get(0));
            addressDetailsObject.setVillage(linkedHashMap.get(1));
        } else if (addressStr.length() > 180) {
            linkedHashMap = callStringPartiton(addressStr, 60);
            addressDetailsObject.setHouseNo(linkedHashMap.get(0));

            linkedHashMap = callStringPartiton(linkedHashMap.get(1), 60);
            addressDetailsObject.setArea(linkedHashMap.get(0));

            linkedHashMap = callStringPartiton(linkedHashMap.get(1), 60);
            addressDetailsObject.setVillage(linkedHashMap.get(0));
            addressDetailsObject.setLandmark(linkedHashMap.get(1));
        }
    }

    private Map<Integer, String> callStringPartiton(String address, int partitions) {
        Map<Integer, String> linkedHashMap = new LinkedHashMap<>();
        int length = address.length();
        String firstHalf = address.substring(0, partitions);
        String secondHalf = address.substring(partitions, length);
        linkedHashMap.put(0, firstHalf);
        linkedHashMap.put(1, secondHalf);
        return linkedHashMap;
    }

    public void addMandatoryFields(ProposalDetails proposalDetails) {
        try {
            NonEditableNonMandatoryFields nonEditableNonMandatoryFields = new NonEditableNonMandatoryFields();
            List<String> nonMandatoryFields = new ArrayList<>();

            if (proposalDetails == null || proposalDetails.getApplicationDetails() == null) {
                log.info("Proposal details or application details are null.");
                return;
            }

            if (AppConstants.FORM_TYPE_SELF.equalsIgnoreCase(proposalDetails.getApplicationDetails().getFormType())) {
                populateFieldsForSelfForm(proposalDetails, nonMandatoryFields);
            } else {
                nonMandatoryFields = proposalDetails.getNonEditableNonMandatoryFields().getTmb();
                populateFieldsForNonSelfForm(proposalDetails, nonMandatoryFields);
            }
            nonEditableNonMandatoryFields.setTmb(nonMandatoryFields);
            proposalDetails.setNonEditableNonMandatoryFields(nonEditableNonMandatoryFields);
        } catch (Exception e) {
            log.error("Error while adding mandatory fields to proposal details: {}", Utility.getExceptionAsString(e));
        }
    }

    private void populateFieldsForSelfForm(ProposalDetails proposalDetails, List<String> nonMandatoryFields) {
        BasicDetails proposalInformation = proposalDetails.getPartyInformation().get(0).getBasicDetails();
        PersonalIdentification proposalPersonalIdentification = proposalDetails.getPartyInformation().get(0).getPersonalIdentification();
        BankDetails proposalBankDetails = proposalDetails.getBank().getBankDetails().get(0);
        AddressDetails currentAddress = proposalInformation.getAddress().get(0).getAddressDetails();
        AddressDetails permanentAddress = proposalInformation.getAddress().get(1).getAddressDetails();
        addIfHasText(proposalInformation.getFirstName(), "firstName", nonMandatoryFields);
        addIfHasText(proposalInformation.getLastName(), "lastName", nonMandatoryFields);
        addIfHasText(proposalInformation.getDob().toString(), "dateOfBirth", nonMandatoryFields);
        addIfHasText(proposalInformation.getAnnualIncome(), "income", nonMandatoryFields);
        addIfHasText(proposalPersonalIdentification.getPanDetails().getPanNumber(), "panNumber", nonMandatoryFields);
        addIfHasText(proposalInformation.getGender(), "gender", nonMandatoryFields);
        addIfHasText(proposalInformation.getResidentialStatus(), "residentialStatus", nonMandatoryFields);
        addIfHasText(proposalInformation.getEducation(), "education", nonMandatoryFields);
        addIfHasText(proposalInformation.getOccupation(), "occupation", nonMandatoryFields);
        addIfHasText(proposalInformation.getNationalityDetails().getNationality(), "nationality", nonMandatoryFields);
        addIfHasText(proposalPersonalIdentification.getEmail(), "email", nonMandatoryFields);
        addIfHasText(proposalPersonalIdentification.getPhone().get(0).getPhoneNumber(), "mobileNumber", nonMandatoryFields);
        addIfHasText(proposalBankDetails.getAccountHolderName(), "bankAccountHolderName", nonMandatoryFields);
        addIfHasText(proposalBankDetails.getBankAccountNumber(), "bankAccountNo", nonMandatoryFields);
        addIfHasText(proposalBankDetails.getIfsc(), "bankAccountIFSC", nonMandatoryFields);
        addIfHasText(proposalBankDetails.getTypeOfAccount(), "typeofAccount", nonMandatoryFields);
        addIfHasText(proposalBankDetails.getBankBranch(), "bankBranch", nonMandatoryFields);
        addIfHasText(proposalBankDetails.getMicr(), "bankAccountMICR", nonMandatoryFields);
        addIfHasText(proposalBankDetails.getBankAccOpeningDate().toString(), "bankAccOpeningDate", nonMandatoryFields);
        addIfHasText(currentAddress.getHouseNo(), "communicationHouseNo", nonMandatoryFields);
        addIfHasText(currentAddress.getArea(), "communicationRoadNo", nonMandatoryFields);
        addIfHasText(currentAddress.getCity(), "communicationCity", nonMandatoryFields);
        addIfHasText(currentAddress.getState(), "communicationState", nonMandatoryFields);
        addIfHasText(currentAddress.getPinCode(), "communicationPinCode", nonMandatoryFields);
        addIfHasText(currentAddress.getCountry(), "communicationCountry", nonMandatoryFields);
        addIfHasText(permanentAddress.getHouseNo(), "permanentHouseNo", nonMandatoryFields);
        addIfHasText(permanentAddress.getArea(), "permanentRoadNo", nonMandatoryFields);
        addIfHasText(permanentAddress.getCity(), "permanentCity", nonMandatoryFields);
        addIfHasText(permanentAddress.getState(), "permanentState", nonMandatoryFields);
        addIfHasText(permanentAddress.getPinCode(), "permanentPinCode", nonMandatoryFields);
        addIfHasText(permanentAddress.getCountry(), "permanentCountry", nonMandatoryFields);


    }

    private void populateFieldsForNonSelfForm(ProposalDetails proposalDetails, List<String> nonMandatoryFields) {
        BasicDetails insuredInformation = proposalDetails.getPartyInformation().get(1).getBasicDetails();

        addIfHasText(insuredInformation.getFirstName(), "insurerName", nonMandatoryFields);
        addIfHasText(insuredInformation.getLastName(), "insurerLastName", nonMandatoryFields);
        addIfHasText(insuredInformation.getGender(), "insurerGender", nonMandatoryFields);
        addIfHasText(insuredInformation.getEducation(), "insurerEducation", nonMandatoryFields);
        addIfHasText(insuredInformation.getOccupation(), "insurerOccupation", nonMandatoryFields);
        addIfHasText(insuredInformation.getDob().toString(), "insurerDateOfBirth", nonMandatoryFields);
    }

    private void addIfHasText(String fieldValue, String fieldName, List<String> fieldsList) {
        if (StringUtils.hasText(fieldValue)) {
            fieldsList.add(fieldName);
        }
    }

}
