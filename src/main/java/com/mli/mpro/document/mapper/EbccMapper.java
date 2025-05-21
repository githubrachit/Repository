package com.mli.mpro.document.mapper;

import com.mli.mpro.proposal.models.JourneyFieldsModificationStatus;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.context.Context;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.utils.Utility;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.Address;
import com.mli.mpro.proposal.models.AddressDetails;
import com.mli.mpro.proposal.models.BasicDetails;
import com.mli.mpro.proposal.models.PartyInformation;
import com.mli.mpro.proposal.models.ProposalDetails;

@Service
public class EbccMapper {

  private static final Logger logger = LoggerFactory.getLogger(EbccMapper.class);

  /**
   * Mapping data from Form60 to Context DataMap
   *
   * @param proposalDetails
   * @return
   * @throws UserHandledException
   */
  public Context setDataOfEbccDocument(ProposalDetails proposalDetails)
      throws UserHandledException {
    logger.info("START Ebcc Data population");
    Map<String, Object> dataVariables = new HashMap<>();
    String communicationAddress = AppConstants.BLANK;
    String permanentAddress = AppConstants.BLANK;

    String proposalNumber = AppConstants.BLANK;
    String title = AppConstants.BLANK;
    String firstName = AppConstants.BLANK;
    String middleName = AppConstants.BLANK;
    String lastName = AppConstants.BLANK;
    String dob = AppConstants.BLANK;
    String nameStatus = AppConstants.BLANK;
    String dobStatus = AppConstants.BLANK;
    String panStatus = AppConstants.BLANK;
    String commAddStatus = AppConstants.BLANK;
    String panCard = null;
    String customerFullName = AppConstants.BLANK;
    try {
      ZoneId zoneid = ZoneId.of(AppConstants.APP_TIMEZONE);
      LocalDateTime currTime = LocalDateTime.now(zoneid);
      DateTimeFormatter dtf = DateTimeFormatter.ofPattern(
          "MMMM d'" + Utility.getLastDigitSufix(currTime.getDayOfMonth()) + "' yyyy',' hh:mm:ss a");
      final String timeStamp = dtf.format(currTime).toString();
      String formType = proposalDetails.getApplicationDetails().getFormType();
      boolean proposerFormFlag = compareValues(formType, "SELF") || Utility.schemeBCase(formType, proposalDetails.getApplicationDetails().getSchemeType());
      proposalNumber = proposalDetails.getApplicationDetails().getPolicyNumber();

      List<PartyInformation> partyInfoList = proposalDetails.getPartyInformation();

      JourneyFieldsModificationStatus fieldsModificationStatus = proposalDetails
          .getAdditionalFlags()
          .getJourneyFieldsModificationStatus();

      logger.info("Initiating processing proposalDetails for EBCC Document...");
      if (!CollectionUtils.isEmpty(partyInfoList)) {
        BasicDetails basicDetails = partyInfoList.get(0).getBasicDetails();
        List<Address> addressList = basicDetails.getAddress();

        if (!CollectionUtils.isEmpty(addressList)) {
          AddressDetails proposerPermAddressDetails = addressList.get(1).getAddressDetails();
          permanentAddress = getAddress(proposerPermAddressDetails.getHouseNo(),
              proposerPermAddressDetails.getArea(), proposerPermAddressDetails.getLandmark(),
              proposerPermAddressDetails.getPinCode(), proposerPermAddressDetails.getCity(),
              proposerPermAddressDetails.getState(), proposerPermAddressDetails.getCountry());

          AddressDetails proposerCommAddressDetails = addressList.get(0).getAddressDetails();
          communicationAddress = getAddress(proposerCommAddressDetails.getHouseNo(),
              proposerCommAddressDetails.getArea(), proposerCommAddressDetails.getLandmark(),
              proposerCommAddressDetails.getPinCode(), proposerCommAddressDetails.getCity(),
              proposerCommAddressDetails.getState(), proposerCommAddressDetails.getCountry());

        } else {
          logger.info("Address data not found!");
        }

        title = Utility.getTitle(basicDetails.getGender());
        firstName = basicDetails.getFirstName();
        middleName = Utility.nullSafe(basicDetails.getMiddleName());
        lastName = basicDetails.getLastName();
        customerFullName = String.join(AppConstants.WHITE_SPACE, firstName, middleName, lastName);
        dob = Utility.dateFormatter(basicDetails.getDob());
        if (!org.springframework.util.StringUtils
            .isEmpty(proposalDetails.getPartyInformation().get(0).getPersonalIdentification()
                .getPanDetails().getPanNumber())) {
          panCard = proposalDetails.getPartyInformation().get(0).getPersonalIdentification()
              .getPanDetails().getPanNumber();
        } else {
          panCard = "";
        }
        nameStatus = getFieldStatus(fieldsModificationStatus.getNameStatus());
        dobStatus = getFieldStatus(fieldsModificationStatus.getDobStatus());
        panStatus = getFieldStatus(fieldsModificationStatus.getPanStatus());
        commAddStatus = getFieldStatus(fieldsModificationStatus.getCommunicationAddStatus());

      }

      // Putting data in DataMap
      dataVariables.put(AppConstants.PANDOB_PROPOSALNUMBER,
          getNonEmptyValue(StringUtils.isNotBlank(proposalNumber), proposalNumber,
              AppConstants.BLANK));
      dataVariables.put("formType", formType);
      dataVariables.put("proposerFormFlag", proposerFormFlag);
      dataVariables.put("title", title);
      dataVariables.put("firstName", firstName);
      dataVariables.put("middleName", middleName);
      dataVariables.put("lastName", lastName);
      dataVariables.put("dob", dob);
      dataVariables.put("communicationAddress", communicationAddress);
      dataVariables.put("panCard", panCard);
      dataVariables.put("customerFullName", customerFullName);
      dataVariables.put("permanentAddress", permanentAddress);
      dataVariables.put("nameStatus", nameStatus);
      dataVariables.put("dobStatus", dobStatus);
      dataVariables.put("panStatus", panStatus);
      dataVariables.put("commAddStatus", commAddStatus);
      dataVariables.put(AppConstants.PANDOB_TIMESTAMP, timeStamp);

    } catch (Exception ex) {
      logger.error("Data addition failed for EBCC Document:", ex);
      List<String> errorMessages = new ArrayList<>();
      errorMessages.add("Data addition failed");
      throw new UserHandledException(new Response(), errorMessages,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }

    Context ebccDetailsCxt = new Context();
    ebccDetailsCxt.setVariables(dataVariables);
    logger.info("END EBCC Data population");
    return ebccDetailsCxt;
  }

  private String getFieldStatus(String fieldStatus) {
    if (fieldStatus.equalsIgnoreCase(AppConstants.FIELD_MODIFIED)) {
      return fieldStatus;
    } else {
      return AppConstants.BLANK;
    }
  }

  private String getNonEmptyValue(boolean flag, String value, String blankValue) {
    return flag ? value : blankValue;
  }

  private boolean compareValues(String value1, String value2) {
    return StringUtils.equalsIgnoreCase(value1, value2) ? true : false;
  }

  private String getAddress(String add1, String add2, String add3, String pincode, String city,
      String state, String country) {
    if (add1 == null) {
      add1 = "";
    }
    if (add2 == null) {
      add2 = "";
    }
    if (add3 == null) {
      add3 = "";
    }
    return add1 + " ".concat(add2 + " ").concat(add3 + " ").concat(city).concat(",").concat(pincode)
        .concat(",").concat(state).concat(",").concat(country)
        .concat(".");
  }

}

