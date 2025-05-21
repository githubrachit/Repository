package com.mli.mpro.document.service.impl;

import com.mli.mpro.configuration.repository.ConfigurationRepository;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.*;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PPHINomineeDetailsMapper {
    private static final Logger logger = LoggerFactory.getLogger(PPHINomineeDetailsMapper.class);
    private boolean defaultVisibleFlag = false;

    private static final String NA = "NA";

    private final String nomineeStr = "isNominee";
    private final String appointeeStr = "isAppointee";
    private final String present = "Present";

    private final String nominee1Present = "isNominee1Present";
    private final String nominee2Present = "isNominee2Present";
    private final String nominee3Present = "isNominee3Present";

    private final String appointee1Present = "isAppointee1Present";
    private final String appointee2Present = "isAppointee2Present";
    private final String appointee3Present = "isAppointee3Present";
    private final String materialListViewName = "MaterialListView";

    private final String houseNumber = "houseNumber";
    private final String area = "area";
    private final String landmark = "landmark";
    private final String village = "village";
    private final String city = "city";
    private final String pinCode = "pinCode";
    private final String state = "state";
    private final String country = "country";
    private final String mobileNumber = "mobileNumber";
    private final String email = "email";

    private final String bankAccNo = "bankAccNo";
    private final String bankAccHolderName = "bankAccHolderName";
    private final String bankMicrCode = "bankMicrCode";
    private final String bankIfscCode = "bankIfscCode";
    private final String bankNameBranch = "bankNameBranch";
    private final String savingAccType = "savingAccType";
    private final String currentAccType = "currentAccType";
    private final String otherAccType = "otherAccType";
    private final String otherTypeText = "otherTypeText";
    private final String otherTypeTextPresent = "otherTypeTextPresent";
    private final String bankOpenDate = "bankOpenDate";

    private final String nominee = "N";
    private final String currentNominee = "CN";
    private final String permanentNominee = "PN";
    private final String appointee = "A";
    private final String currentAppointee = "CA";
    private final String permanentAppointee = "PA";

    private static final String YES = "YES";
    private static final String NO = "NO";

    private final String blank = "";
    private final String nomineeDetailsView = "NomineeDetailsView";

    AddressDetails addressDetails = null;
    BankDetails bankDetails = null;

    @Autowired
    ConfigurationRepository configurationRepository;

    public Context setDataForPPHINomineeAndAppointee(ProposalDetails proposalDetails) {
        Context context = new Context();
        logger.info("Setting PPHI Nominee Details for transactionId: {}", proposalDetails.getTransactionId());
        NomineeDetails nomineeDetails = proposalDetails.getNomineeDetails();
        boolean isMWPACase = checkMWPACase(proposalDetails);
        Map<String,Object> dataVariables = new HashMap<>();
        setDefaultVisibleFlagValue(dataVariables,defaultVisibleFlag,proposalDetails);

        if(nomineeDetails!=null && nomineeDetails.getPartyDetails() != null && nomineeDetails.getPartyDetails().size() > 0){
            AtomicInteger index = new AtomicInteger(0);
            nomineeDetails.getPartyDetails().stream().forEach(partyDetails -> {
                if(Utility.isAnyObjectNull(partyDetails)){
                    logger.info("Nominee PartyDetails is null for transactionId: {}", proposalDetails.getTransactionId());
                    return;
                }
                int currentIndex = index.incrementAndGet();
                logger.info("Start Setting Nominee Details for transactionId: {} and index: {}", proposalDetails.getTransactionId(), currentIndex);

                //check appointee is present or not
                Date dob = partyDetails.getDob();
                Float age = Float.valueOf(Utility.getAge(dob));
                logger.info("Age is {} for transactionId {} with index {}", age, proposalDetails.getTransactionId(), currentIndex);
                htmlDataMapping(proposalDetails, partyDetails, dob, age, currentIndex, dataVariables, isMWPACase);
                logger.info("Completed Setting Nominee Details for transactionId {} and index {} and dataVariable {}", proposalDetails.getTransactionId(), currentIndex, dataVariables);
            });
        }
        context.setVariables(dataVariables);
        return context;
    }

    private void htmlDataMapping(ProposalDetails proposalDetails, PartyDetails partyDetails, Date dob, Float age, int currentIndex, Map<String, Object> dataVariables, boolean isMWPACase) {
        if(dob !=null && age < 18){
            appointeeDetailsCapture(proposalDetails, partyDetails, currentIndex, dataVariables);
            if(isMWPACase){
                nomineeDetailsCapture(proposalDetails, partyDetails, currentIndex, dataVariables);
            }
        } else if(dob !=null && age >= 18) {
            nomineeDetailsCapture(proposalDetails, partyDetails, currentIndex, dataVariables);
        } else {
            logger.info("Age is 0 or dob null for transactionId {}", proposalDetails.getTransactionId());
            setDataVisibleOrNot(dataVariables, nomineeDetailsView, blank, blank,false);
        }
    }

    private boolean checkMWPACase(ProposalDetails proposalDetails){
        if(!proposalDetails.getProductDetails().isEmpty() && proposalDetails.getProductDetails().get(0)!=null){
            return AppConstants.OBJECTIVETYPE_MWPA.equalsIgnoreCase(proposalDetails.getProductDetails().get(0).getObjectiveOfInsurance());
        }else {
            return false;
        }
    }

    private void nomineeDetailsCapture(ProposalDetails proposalDetails, PartyDetails partyDetails, int currentIndex, Map<String, Object> dataVariables) {
        logger.info("Age is greater than 18 for transactionId {} with index {}", proposalDetails.getTransactionId(), currentIndex);
        setDataVisibleOrNot(dataVariables, nomineeDetailsView, blank, blank,true);
        setDataVisibleOrNot(dataVariables,nomineeStr,String.valueOf(currentIndex),present,true);
        // Nominee Details for current address
        setNomineeCurrentDetails(partyDetails, dataVariables, currentIndex);
        // Nominee Details for permanent address
        setNomineePermanentDetails(partyDetails, dataVariables, currentIndex);
        // Nominee Bank Details
        setNomineeBankDetailsFirst(partyDetails, dataVariables, currentIndex);
    }

    private void appointeeDetailsCapture(ProposalDetails proposalDetails, PartyDetails partyDetails, int currentIndex, Map<String, Object> dataVariables) {
        logger.info("Age is less than 18 for transactionId {} with index {}", proposalDetails.getTransactionId(), currentIndex);
        setDataVisibleOrNot(dataVariables, nomineeDetailsView, blank, blank,true);
        setDataVisibleOrNot(dataVariables,appointeeStr,String.valueOf(currentIndex),present,true);
        // Appointee Details for current address
        setAppointeeCurrentDetails(partyDetails, dataVariables, currentIndex);
        // Appointee Details for permanent address
        setAppointeePermanentDetails(partyDetails, dataVariables, currentIndex);
        // Appointee Bank Details
        setAppointeeBankDetails(partyDetails, dataVariables, currentIndex);
    }


    private void setDefaultVisibleFlagValue(Map<String, Object> dataVariables, boolean flag, ProposalDetails proposalDetails) {
        dataVariables.put(nominee1Present,flag);
        dataVariables.put(nominee2Present,flag);
        dataVariables.put(nominee3Present,flag);
        dataVariables.put(appointee1Present,flag);
        dataVariables.put(appointee2Present,flag);
        dataVariables.put(appointee3Present,flag);
        dataVariables.put(materialListViewName,materialListView() && wipCasePPHI(proposalDetails));
    }

    private boolean materialListView(){
        if(FeatureFlagUtil.isFeatureFlagEnabled("enableNomineeAppointeePiDetailsFeatureFlag")
            || FeatureFlagUtil.isFeatureFlagEnabled("enableNomineeAppointeeAddressFeatureFlag")
                ||FeatureFlagUtil.isFeatureFlagEnabled("enableNomineeAppointeeBankDetails")){
                return true;
        }
        return false;
    }
    private boolean wipCasePPHI(ProposalDetails proposalDetails){
        return compareWipCaseDate(proposalDetails);
    }
    private boolean compareWipCaseDate(ProposalDetails proposalDetails){
        try {
            logger.info("Comparing WIP Case Date for transactionId: {}", proposalDetails.getTransactionId());
            String dateStr = configurationRepository.findByKey("PPHI-PF-WIP-CASE-DATE").getValue();
            logger.info("WIP Case Date from Configuration: {}", dateStr);
            String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            simpleDateFormat.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
            Date wipCaseDate = simpleDateFormat.parse(dateStr);
            Date policyDate = proposalDetails.getApplicationDetails().getCreatedTime();
            return policyDate.after(wipCaseDate);
        } catch (Exception e) {
            logger.error("Error occurred while comparing WIP Case Date for transactionId: {}", proposalDetails.getTransactionId(),e);
            return false;
        }
    }

    private void setDataVisibleOrNot(Map<String, Object> dataVariables,String preFix, String currentIndex, String postFix, boolean flag) {
        String key = concatString(preFix,currentIndex,postFix);
        if(dataVariables.get(key)!=null){
            dataVariables.remove(key);
        }
        dataVariables.put(key,flag);
    }

    private void setAppointeeBankDetails(PartyDetails partyDetails, Map<String, Object> dataVariables, int currentIndex) {
        logger.info("Setting Appointee Details setAppointeeBankDetails for currentIndex: {}", currentIndex);
        dataVariables.put(concatString(bankAccNo,appointee,String.valueOf(currentIndex)),getBankDetailsNomineeAndAppointee(partyDetails,appointee,bankAccNo));
        dataVariables.put(concatString(bankAccHolderName,appointee,String.valueOf(currentIndex)),getBankDetailsNomineeAndAppointee(partyDetails,appointee,bankAccHolderName));
        dataVariables.put(concatString(bankMicrCode,appointee,String.valueOf(currentIndex)),getBankDetailsNomineeAndAppointee(partyDetails,appointee,bankMicrCode));
        dataVariables.put(concatString(bankIfscCode,appointee,String.valueOf(currentIndex)),getBankDetailsNomineeAndAppointee(partyDetails,appointee,bankIfscCode));
        dataVariables.put(concatString(bankNameBranch,appointee,String.valueOf(currentIndex)),getBankDetailsNomineeAndAppointee(partyDetails,appointee,bankNameBranch));
        dataVariables.put(concatString(savingAccType,appointee,String.valueOf(currentIndex)),getBankDetailsNomineeAndAppointee(partyDetails,appointee,savingAccType));
        dataVariables.put(concatString(currentAccType,appointee,String.valueOf(currentIndex)),getBankDetailsNomineeAndAppointee(partyDetails,appointee,currentAccType));
        dataVariables.put(concatString(otherTypeTextPresent,appointee,String.valueOf(currentIndex)),getBankDetailsNomineeAndAppointee(partyDetails,appointee,otherAccType).equalsIgnoreCase(YES));
        dataVariables.put(concatString(otherAccType,appointee,String.valueOf(currentIndex)),getBankDetailsNomineeAndAppointee(partyDetails,appointee,otherAccType));
        dataVariables.put(concatString(otherTypeText,appointee,String.valueOf(currentIndex)),getBankDetailsNomineeAndAppointee(partyDetails,appointee,otherTypeText));
        dataVariables.put(concatString(bankOpenDate,appointee,String.valueOf(currentIndex)),getBankDetailsNomineeAndAppointee(partyDetails,appointee,bankOpenDate));
    }

    private void setAppointeePermanentDetails(PartyDetails partyDetails, Map<String, Object> dataVariables, int currentIndex) {
        logger.info("Setting Appointee Details setAppointeePermanentDetails for currentIndex: {}", currentIndex);
        dataVariables.put(concatString(houseNumber,permanentAppointee,String.valueOf(currentIndex)),getAddressDetailsNomineeAndAppointee(partyDetails,permanentAppointee,houseNumber));
        dataVariables.put(concatString(area,permanentAppointee,String.valueOf(currentIndex)),getAddressDetailsNomineeAndAppointee(partyDetails,permanentAppointee,area));
        dataVariables.put(concatString(landmark,permanentAppointee,String.valueOf(currentIndex)),getAddressDetailsNomineeAndAppointee(partyDetails,permanentAppointee,landmark));
        dataVariables.put(concatString(village,permanentAppointee,String.valueOf(currentIndex)),getAddressDetailsNomineeAndAppointee(partyDetails,permanentAppointee,village));
        dataVariables.put(concatString(city,permanentAppointee,String.valueOf(currentIndex)),getAddressDetailsNomineeAndAppointee(partyDetails,permanentAppointee,city));
        dataVariables.put(concatString(pinCode,permanentAppointee,String.valueOf(currentIndex)),getAddressDetailsNomineeAndAppointee(partyDetails,permanentAppointee,pinCode));
        dataVariables.put(concatString(state,permanentAppointee,String.valueOf(currentIndex)),getAddressDetailsNomineeAndAppointee(partyDetails,permanentAppointee,state));
        dataVariables.put(concatString(country,permanentAppointee,String.valueOf(currentIndex)),getAddressDetailsNomineeAndAppointee(partyDetails,permanentAppointee,country));
    }

    private void setAppointeeCurrentDetails(PartyDetails partyDetails, Map<String, Object> dataVariables, int currentIndex) {
        logger.info("Setting Appointee Details setAppointeeCurrentDetails for currentIndex: {}", currentIndex);
        dataVariables.put(concatString(houseNumber,currentAppointee,String.valueOf(currentIndex)),getAddressDetailsNomineeAndAppointee(partyDetails,currentAppointee,houseNumber));
        dataVariables.put(concatString(area,currentAppointee,String.valueOf(currentIndex)),getAddressDetailsNomineeAndAppointee(partyDetails,currentAppointee,area));
        dataVariables.put(concatString(landmark,currentAppointee,String.valueOf(currentIndex)),getAddressDetailsNomineeAndAppointee(partyDetails,currentAppointee,landmark));
        dataVariables.put(concatString(village,currentAppointee,String.valueOf(currentIndex)),getAddressDetailsNomineeAndAppointee(partyDetails,currentAppointee,village));
        dataVariables.put(concatString(city,currentAppointee,String.valueOf(currentIndex)),getAddressDetailsNomineeAndAppointee(partyDetails,currentAppointee,city));
        dataVariables.put(concatString(pinCode,currentAppointee,String.valueOf(currentIndex)),getAddressDetailsNomineeAndAppointee(partyDetails,currentAppointee,pinCode));
        dataVariables.put(concatString(state,currentAppointee,String.valueOf(currentIndex)),getAddressDetailsNomineeAndAppointee(partyDetails,currentAppointee,state));
        dataVariables.put(concatString(country,currentAppointee,String.valueOf(currentIndex)),getAddressDetailsNomineeAndAppointee(partyDetails,currentAppointee,country));
        dataVariables.put(concatString(mobileNumber,appointee,String.valueOf(currentIndex)),getEmailMobileNomineeAndAppointee(partyDetails,appointee,mobileNumber));
        dataVariables.put(concatString(email,appointee,String.valueOf(currentIndex)),getEmailMobileNomineeAndAppointee(partyDetails,appointee,email));
    }

    private void setNomineeBankDetailsFirst(PartyDetails partyDetails, Map<String, Object> dataVariables, int currentIndex) {
        logger.info("Setting Nominee Details setNomineeBankDetailsFirst for currentIndex: {}", currentIndex);
        dataVariables.put(concatString(bankAccNo,nominee,String.valueOf(currentIndex)),getBankDetailsNomineeAndAppointee(partyDetails,nominee,bankAccNo));
        dataVariables.put(concatString(bankAccHolderName,nominee,String.valueOf(currentIndex)),getBankDetailsNomineeAndAppointee(partyDetails,nominee,bankAccHolderName));
        dataVariables.put(concatString(bankMicrCode,nominee,String.valueOf(currentIndex)),getBankDetailsNomineeAndAppointee(partyDetails,nominee,bankMicrCode));
        dataVariables.put(concatString(bankIfscCode,nominee,String.valueOf(currentIndex)),getBankDetailsNomineeAndAppointee(partyDetails,nominee,bankIfscCode));
        dataVariables.put(concatString(bankNameBranch,nominee,String.valueOf(currentIndex)),getBankDetailsNomineeAndAppointee(partyDetails,nominee,bankNameBranch));
        dataVariables.put(concatString(savingAccType,nominee,String.valueOf(currentIndex)),getBankDetailsNomineeAndAppointee(partyDetails,nominee,savingAccType));
        dataVariables.put(concatString(currentAccType,nominee,String.valueOf(currentIndex)),getBankDetailsNomineeAndAppointee(partyDetails,nominee,currentAccType));
        dataVariables.put(concatString(otherTypeTextPresent,nominee,String.valueOf(currentIndex)),getBankDetailsNomineeAndAppointee(partyDetails,nominee,otherAccType).equalsIgnoreCase(YES));
        dataVariables.put(concatString(otherAccType,nominee,String.valueOf(currentIndex)),getBankDetailsNomineeAndAppointee(partyDetails,nominee,otherAccType));
        dataVariables.put(concatString(otherTypeText,nominee,String.valueOf(currentIndex)),getBankDetailsNomineeAndAppointee(partyDetails,nominee,otherTypeText));
        dataVariables.put(concatString(bankOpenDate,nominee,String.valueOf(currentIndex)),getBankDetailsNomineeAndAppointee(partyDetails,nominee,bankOpenDate));
    }

    private void setNomineePermanentDetails(PartyDetails partyDetails, Map<String, Object> dataVariables, int currentIndex) {
        logger.info("Setting Nominee Details setNomineePermanentDetails for currentIndex: {}", currentIndex);
        dataVariables.put(concatString(houseNumber,permanentNominee,String.valueOf(currentIndex)),getAddressDetailsNomineeAndAppointee(partyDetails,permanentNominee,houseNumber));
        dataVariables.put(concatString(area,permanentNominee,String.valueOf(currentIndex)),getAddressDetailsNomineeAndAppointee(partyDetails,permanentNominee,area));
        dataVariables.put(concatString(landmark,permanentNominee,String.valueOf(currentIndex)),getAddressDetailsNomineeAndAppointee(partyDetails,permanentNominee,landmark));
        dataVariables.put(concatString(village,permanentNominee,String.valueOf(currentIndex)),getAddressDetailsNomineeAndAppointee(partyDetails,permanentNominee,village));
        dataVariables.put(concatString(city,permanentNominee,String.valueOf(currentIndex)),getAddressDetailsNomineeAndAppointee(partyDetails,permanentNominee,city));
        dataVariables.put(concatString(pinCode,permanentNominee,String.valueOf(currentIndex)),getAddressDetailsNomineeAndAppointee(partyDetails,permanentNominee,pinCode));
        dataVariables.put(concatString(state,permanentNominee,String.valueOf(currentIndex)),getAddressDetailsNomineeAndAppointee(partyDetails,permanentNominee,state));
        dataVariables.put(concatString(country,permanentNominee,String.valueOf(currentIndex)),getAddressDetailsNomineeAndAppointee(partyDetails,permanentNominee,country));
    }

    private void setNomineeCurrentDetails(PartyDetails partyDetails, Map<String, Object> dataVariables, int currentIndex) {
        logger.info("Setting Nominee Details setNomineeCurrentDetails for currentIndex: {}", currentIndex);
        dataVariables.put(concatString(houseNumber,currentNominee,String.valueOf(currentIndex)),getAddressDetailsNomineeAndAppointee(partyDetails,currentNominee,houseNumber));
        dataVariables.put(concatString(area,currentNominee,String.valueOf(currentIndex)),getAddressDetailsNomineeAndAppointee(partyDetails,currentNominee,area));
        dataVariables.put(concatString(landmark,currentNominee,String.valueOf(currentIndex)),getAddressDetailsNomineeAndAppointee(partyDetails,currentNominee,landmark));
        dataVariables.put(concatString(village,currentNominee,String.valueOf(currentIndex)),getAddressDetailsNomineeAndAppointee(partyDetails,currentNominee,village));
        dataVariables.put(concatString(city,currentNominee,String.valueOf(currentIndex)),getAddressDetailsNomineeAndAppointee(partyDetails,currentNominee,city));
        dataVariables.put(concatString(pinCode,currentNominee,String.valueOf(currentIndex)),getAddressDetailsNomineeAndAppointee(partyDetails,currentNominee,pinCode));
        dataVariables.put(concatString(state,currentNominee,String.valueOf(currentIndex)),getAddressDetailsNomineeAndAppointee(partyDetails,currentNominee,state));
        dataVariables.put(concatString(country,currentNominee,String.valueOf(currentIndex)),getAddressDetailsNomineeAndAppointee(partyDetails,currentNominee,country));
        dataVariables.put(concatString(mobileNumber,nominee,String.valueOf(currentIndex)),getEmailMobileNomineeAndAppointee(partyDetails,nominee,mobileNumber));
        dataVariables.put(concatString(email,nominee,String.valueOf(currentIndex)),getEmailMobileNomineeAndAppointee(partyDetails,nominee,email));
    }

    private String getAddressDetailsNomineeAndAppointee(PartyDetails partyDetails, String type, String tagType) {
        logger.info("Getting Address Details for type: {} and tagType: {}", type, tagType);
        addressDetails = getAddressDetails(partyDetails,type);
        if(addressDetails == null){
            return NA;
        }
        switch (tagType){
            case houseNumber:
                return checkIsEmpty(addressDetails.getHouseNo());
            case area:
                return checkIsEmpty(addressDetails.getArea());
            case landmark:
                return checkIsEmpty(addressDetails.getLandmark());
            case village:
                return checkIsEmpty(addressDetails.getVillage());
            case city:
                return checkIsEmpty(addressDetails.getCity());
            case pinCode:
                return checkIsEmpty(addressDetails.getPinCode());
            case state:
                return checkIsEmpty(addressDetails.getState());
            case country:
                return checkIsEmpty(addressDetails.getCountry());
            default:
                return NA;
        }
    }

    private AddressDetails getAddressDetails(PartyDetails partyDetails, String type) {
        logger.info("Getting Address Details for type: {}", type);
        try {
            switch (type){
                case currentNominee:
                    if (partyDetails.getNomineeAddress() == null) {
                        return null;
                    }
                    return addressDetailsEmptyCheck(addressListEmptyCheck(partyDetails.getNomineeAddress(),0));
                case permanentNominee:
                    if (partyDetails.getNomineeAddress() == null) {
                        return null;
                    }
                    return addressDetailsEmptyCheck(addressListEmptyCheck(partyDetails.getNomineeAddress(),1));
                case currentAppointee:
                    if (partyDetails.getAppointeeDetails() == null || partyDetails.getAppointeeDetails().getAppointeeAddress() == null) {
                        return null;
                    }
                    return addressDetailsEmptyCheck(addressListEmptyCheck(partyDetails.getAppointeeDetails().getAppointeeAddress(),0));
                case permanentAppointee:
                    if (partyDetails.getAppointeeDetails() == null || partyDetails.getAppointeeDetails().getAppointeeAddress() == null) {
                        return null;
                    }
                    return addressDetailsEmptyCheck(addressListEmptyCheck(partyDetails.getAppointeeDetails().getAppointeeAddress(),1));
                default:
                    return null;
            }
        } catch (Exception e) {
            String StrException = Utility.getExceptionAsString(e);
            logger.error("Error occurred while getting Address Details for type: {},{}", type, StrException);
        }
        return null;
    }

    private Address addressListEmptyCheck(List<Address> addresses, int index){
        return (addresses == null || addresses.isEmpty()) ? null : addresses.get(index);
    }
    private AddressDetails addressDetailsEmptyCheck(Address address){
        return (address == null || address.getAddressDetails() == null) ? null : address.getAddressDetails();
    }

    private String getBankDetailsNomineeAndAppointee(PartyDetails partyDetails, String type, String tagType) {
        bankDetails = getRelatedBankDetails(partyDetails, type);
        if(bankDetails == null){
            return NA;
        }
        switch (tagType){
            case bankAccNo:
                return checkIsEmpty(bankDetails.getBankAccountNumber());
            case bankAccHolderName:
                return checkIsEmpty(bankDetails.getAccountHolderName());
            case bankMicrCode:
                return checkIsEmpty(bankDetails.getMicr());
            case bankIfscCode:
                return checkIsEmpty(bankDetails.getIfsc());
            case bankNameBranch:
                return checkIsEmpty(concatNonEmptyStrings(bankDetails.getBankName(),AppConstants.SPACE,bankDetails.getBankBranch()));
            case savingAccType:
                return accountTypeMatch(List.of("SAVINGS ACCOUNT")) ? YES : NO;
            case currentAccType:
                return accountTypeMatch(List.of("CURRENT ACCOUNT")) ? YES : NO;
            case otherAccType:
                return accountTypeMatch(List.of("OTHER","CASH CREDIT","NRE","NRO","FCNR")) ? YES : NO;
            case otherTypeText:
                return checkIsEmpty(bankDetails.getTypeOfAccount());
            case bankOpenDate:
                String bankSinceDate = String.valueOf(Utility.dateFormatter(bankDetails.getBankAccOpeningDate()));
                return checkIsEmpty(StringUtils.hasLength(bankSinceDate) ? bankSinceDate : NA);
            default:
                return NA;
        }
    }

    private String concatNonEmptyStrings(String... strings) {
        StringBuilder sb = new StringBuilder();
        for (String str : strings) {
            if (StringUtils.hasLength(str)) {
                if (sb.length() > 0 || !str.equals(AppConstants.SPACE)) {
                    sb.append(str);
                }
            }
        }
        return sb.toString();
    }

    private boolean accountTypeMatch(List<String> type) {
        if (bankDetails.getTypeOfAccount()==null){
            return false;
        }
        return type.contains(bankDetails.getTypeOfAccount().toUpperCase());
    }

    private BankDetails getRelatedBankDetails(PartyDetails partyDetails, String type) {
        switch (type){
            case nominee:
                return getBankDetailsForNominee(partyDetails);
            case appointee:
                return getBankDetailsForAppointee(partyDetails);
            default:
                return null;
        }
    }

    private static BankDetails getBankDetailsForNominee(PartyDetails partyDetails) {
        if (partyDetails.getNomineeBankDetails() == null
                || partyDetails.getNomineeBankDetails().getBankDetails() == null
                ||partyDetails.getNomineeBankDetails().getBankDetails().isEmpty()) {
            return null;
        }
        return partyDetails.getNomineeBankDetails().getBankDetails().get(0);
    }
    private static BankDetails getBankDetailsForAppointee(PartyDetails partyDetails) {
        if (partyDetails.getAppointeeDetails() == null
                || partyDetails.getAppointeeDetails().getAppointeeBankDetails()==null
                || partyDetails.getAppointeeDetails().getAppointeeBankDetails().getBankDetails() == null
                ||partyDetails.getAppointeeDetails().getAppointeeBankDetails().getBankDetails().isEmpty()) {
            return null;
        }
        return partyDetails.getAppointeeDetails().getAppointeeBankDetails().getBankDetails().get(0);
    }

    private String getEmailMobileNomineeAndAppointee(PartyDetails partyDetails, String type, String tagType) {
        switch (type){
            case nominee:
                return getRequiredPINomineeDetails(partyDetails, tagType);
            case appointee:
                return getRequiredPIAppointeeDetails(partyDetails, tagType);
            default:
                return NA;
        }
    }

    private String getRequiredPIAppointeeDetails(PartyDetails partyDetails, String tagType) {
        if(mobileNumber.equalsIgnoreCase(tagType)){
            if(partyDetails.getAppointeeDetails() == null
                    || partyDetails.getAppointeeDetails().getAppointeePhoneDetails() == null
                    || partyDetails.getAppointeeDetails().getAppointeePhoneDetails().isEmpty()){
                return NA;
            }
            return checkIsEmpty(partyDetails.getAppointeeDetails().getAppointeePhoneDetails().get(0).getPhoneNumber());
        } else {
            if(partyDetails.getAppointeeDetails() == null){
                return NA;
            }
            return checkIsEmpty(partyDetails.getAppointeeDetails().getAppointeeEmail());
        }
    }

    private String getRequiredPINomineeDetails(PartyDetails partyDetails, String tagType) {
        if(mobileNumber.equalsIgnoreCase(tagType)){
            if(partyDetails.getNomineePhoneDetails() == null || partyDetails.getNomineePhoneDetails() == null || partyDetails.getNomineePhoneDetails().isEmpty()){
                return NA;
            }
            return checkIsEmpty(partyDetails.getNomineePhoneDetails().get(0).getPhoneNumber());
        } else {
            return checkIsEmpty(partyDetails.getNomineeEmail());
        }
    }

    private String checkIsEmpty(String str){
        return StringUtils.hasLength(str) ? str : NA;
    }

    private String concatString(String... strings) {
        StringBuilder sb = new StringBuilder();
        for (String str : strings) {
            sb.append(str);
        }
        return sb.toString();
    }
}
