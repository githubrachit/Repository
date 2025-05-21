package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.Address;
import com.mli.mpro.proposal.models.AddressDetails;
import com.mli.mpro.proposal.models.BasicDetails;
import com.mli.mpro.proposal.models.CersaiCkycDataCompareResponse;
import com.mli.mpro.proposal.models.DocumentDetails;
import com.mli.mpro.proposal.models.PartyInformation;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MliCkycDocumentMapper {

  private static final Logger logger = LoggerFactory.getLogger(MliCkycDocumentMapper.class);

  public Context setDataForMliCkycDocument(ProposalDetails proposalDetails) throws UserHandledException {


    logger.info("Starting MliCkycDocument document data population for transactionId {}",
        proposalDetails.getTransactionId());
    Map<String, Object> dataVariables = new HashMap<>();
    try {
      dataVariables.put("policyNumber", proposalDetails.getApplicationDetails().getPolicyNumber());
      dataVariables.put("ckycNumber", proposalDetails.getApplicationDetails().getCkycNumber());
      BasicDetails proposalBasicDetails = proposalDetails.getPartyInformation().get(0).getBasicDetails();
      CersaiCkycDataCompareResponse cersaiCkycDataCompareResponse = proposalDetails.getUnderwritingServiceDetails()
          .getCersaiCkycDataCompareResponse();

      List<PartyInformation> partyInfoList = proposalDetails.getPartyInformation();
      BasicDetails basicDetails = partyInfoList.get(0).getBasicDetails();
      List<Address> addressList = basicDetails.getAddress();

      if(Objects.nonNull(proposalBasicDetails)) {
        dataVariables.put("applicantName", StringUtils.capitalize(proposalBasicDetails.getFirstName() + " " + proposalBasicDetails.getMiddleName() + " "
            + proposalBasicDetails.getLastName()));
        dataVariables.put("fatherName", proposalBasicDetails.getFatherName());
        dataVariables.put("motherName", proposalBasicDetails.getMotherName());
        dataVariables.put("dateOfBirth", Utility.dateFormatter(proposalBasicDetails.getDob()));
        dataVariables.put("gender", proposalBasicDetails.getGender());
        dataVariables.put("maritalStatus", proposalBasicDetails.getMarriageDetails().getMaritalStatus());
        dataVariables.put("occupation", proposalBasicDetails.getOccupation());
        dataVariables.put("residentialStatus", StringUtils.capitalize(proposalBasicDetails.getResidentialStatus()));
      }

      if(Objects.nonNull(cersaiCkycDataCompareResponse)) {
        dataVariables.put("applicantNameModificationStatus", cersaiCkycDataCompareResponse.getApplicantNameModified());
        dataVariables.put("fatherNameModificationStatus", cersaiCkycDataCompareResponse.getFatherNameModified());
        dataVariables.put("motherNameModificationStatus", cersaiCkycDataCompareResponse.getMotherNameModified());
        dataVariables.put("dateOfBirthModificationStatus", cersaiCkycDataCompareResponse.getDateOfBirthModified());
        dataVariables.put("genderModificationStatus", cersaiCkycDataCompareResponse.getGenderModified());
        dataVariables.put("maritalModificationStatus", cersaiCkycDataCompareResponse.getMaritalStatusModified());
        dataVariables.put("citizenshipModificationStatus", cersaiCkycDataCompareResponse.getCitizenshipModified());
        dataVariables.put("occupationModificationStatus", cersaiCkycDataCompareResponse.getOccupationModified());
        dataVariables.put("residentialModificationStatus", cersaiCkycDataCompareResponse.getResidentialStatusModified());
        dataVariables.put("mobileNumberModificationStatus", cersaiCkycDataCompareResponse.getMobileNumberModified());
        dataVariables.put("emailIdModificationStatus", cersaiCkycDataCompareResponse.getEmailIdModified());
        dataVariables.put("permanentAddrDocTypeModificationStatus", cersaiCkycDataCompareResponse.getPermAddressModified());
        dataVariables.put("currentAddrDocTypeModificationStatus", cersaiCkycDataCompareResponse.getCommunicationAddressModified());
        dataVariables.put("TelNumberModificationStatus", cersaiCkycDataCompareResponse.getTelNumberModified());
        dataVariables.put("residenceNumberModificationStatus", cersaiCkycDataCompareResponse.getResidenceNumberModified());
        dataVariables.put("faxNumberModificationStatus", cersaiCkycDataCompareResponse.getFaxNumberModified());
        dataVariables.put("idProofDocTypeModificationStatus", StringUtils.isEmpty(cersaiCkycDataCompareResponse.getDocsPassedInProofOfId())?AppConstants.NO:StringUtils.upperCase(cersaiCkycDataCompareResponse.getDocsPassedInProofOfId()));
        dataVariables.put("photoDocTypeModificationStatus", StringUtils.isEmpty(cersaiCkycDataCompareResponse.getDocsPassedInRecentPhoto())?AppConstants.NO:StringUtils.upperCase(cersaiCkycDataCompareResponse.getDocsPassedInRecentPhoto()));
        dataVariables.put("addrProofDocTypeModificationStatus", StringUtils.isEmpty(cersaiCkycDataCompareResponse.getDocsPassedInProofOfAddress())?AppConstants.NO:StringUtils.upperCase(cersaiCkycDataCompareResponse.getDocsPassedInProofOfAddress()));
        dataVariables.put("pfiDocModificationStatus", StringUtils.upperCase(cersaiCkycDataCompareResponse.getIdModificationStatus()));

      }
      dataVariables.put("citizenship", AppConstants.BLANK);
      dataVariables.put("TelNumber", AppConstants.BLANK);
      dataVariables.put("residenceNumber", AppConstants.BLANK);
      dataVariables.put("faxNumber", AppConstants.BLANK);

      dataVariables.put("mobileNumber", proposalDetails.getPartyInformation().get(0).getPersonalIdentification().getPhone().get(0).getPhoneNumber());
      dataVariables.put("emailId", proposalDetails.getPartyInformation().get(0).getPersonalIdentification().getEmail());

      dataVariables.put("pfiDocType", proposalDetails.getUnderwritingServiceDetails().getUnderwritingStatus().getRequiredDocuments().stream().filter(documentDetails -> "ID_Pr".equalsIgnoreCase(documentDetails.getMproDocumentId())).map(DocumentDetails::getDocumentName).findFirst().orElse(""));
      dataVariables.put("pfiDocModificationStatus", "YES");  // need to check this
      dataVariables.put("pfiIdNumber", AppConstants.AADHAAR.equalsIgnoreCase(proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getProofType())
          ? proposalDetails.getPartyInformation().get(0).getPersonalIdentification().getAadhaarDetails().getAadhaarNumber() : addressList.get(0).getProofNumber());

      dataVariables.put("permanentAddrDocType", proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress().get(1).getProofType());
      AddressDetails permanentAddressDetails = proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress().get(1).getAddressDetails();
      dataVariables.put("permanentAddrLine1", permanentAddressDetails.getHouseNo());
      dataVariables.put("permanentAddrLine2", permanentAddressDetails.getArea());
      dataVariables.put("permanentAddrLine3", org.springframework.util.StringUtils.hasText(permanentAddressDetails.getLandmark()) ? permanentAddressDetails.getLandmark() : "-");
      String permAddrCityTownVillage = permanentAddressDetails.getCity() + " " + (org.springframework.util.StringUtils.hasText(permanentAddressDetails.getVillage()) ? permanentAddressDetails.getVillage() : " ") ;
      dataVariables.put("permanentAddrCityTownVillage", permAddrCityTownVillage);
      dataVariables.put("permanentAddrPincode", permanentAddressDetails.getPinCode());
      dataVariables.put("permanentAddrDistrict", AppConstants.BLANK);
      dataVariables.put("permanentAddrState", permanentAddressDetails.getState());
      dataVariables.put("permanentAddrCountry", permanentAddressDetails.getCountry());

      dataVariables.put("currentAddrDocType",proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getProofType());
      AddressDetails currentAddressDetails = proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails();
      dataVariables.put("currentAddrLine1", currentAddressDetails.getHouseNo());
      dataVariables.put("currentAddrLine2", currentAddressDetails.getArea());
      dataVariables.put("currentAddrLine3", org.springframework.util.StringUtils.hasText(currentAddressDetails.getLandmark()) ? currentAddressDetails.getLandmark() : "-");
      String cityTownVillage = currentAddressDetails.getCity() + " " + (org.springframework.util.StringUtils.hasText(currentAddressDetails.getVillage()) ? currentAddressDetails.getVillage() : " ") ;
      dataVariables.put("currentAddrCityTownVillage", cityTownVillage);
      dataVariables.put("currentAddrPincode",  currentAddressDetails.getPinCode());
      dataVariables.put("currentAddrDistrict", AppConstants.BLANK);
      dataVariables.put("currentAddrState", currentAddressDetails.getState());
      dataVariables.put("currentAddrCountry", currentAddressDetails.getCountry());

      dataVariables.put("idProofDocType", "ID Proof Type");
      dataVariables.put("addrProofDocType", "Address Proof");
      dataVariables.put("photoDocType", "Photo");

      SimpleDateFormat format = new SimpleDateFormat(AppConstants.DD_MM_YYYY_HH_MM_SS_HYPHEN);
      format.setTimeZone(TimeZone.getTimeZone(AppConstants.APP_TIMEZONE));
      String timestamp = format.format(new LocalDateTime().toDate());
      String[] dateTime = timestamp.split(" ");
      dataVariables.put("timeStamp", dateTime[0]+" & "+dateTime[1]);
    } catch (Exception e) {
      logger.info("Data addition failed for MliCkycDocument for transactionId {} : {} ",
          proposalDetails.getTransactionId(), Utility.getExceptionAsString(e));
      List<String> errorMessages = new ArrayList<>();
      errorMessages.add("Data addition failed");
      throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    logger.info("Ending MliCkycDocument data population for transactionId {}", proposalDetails.getTransactionId());
    Context context = new Context();
    context.setVariables(dataVariables);
    return context;
  }
}
