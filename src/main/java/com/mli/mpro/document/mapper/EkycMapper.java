package com.mli.mpro.document.mapper;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.proposal.models.*;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.*;

@Service
public class EkycMapper {

    private static final Logger logger = LoggerFactory.getLogger(EkycMapper.class);

    public Context setDataOfEkycDocument(ProposalDetails proposalDetails) throws UserHandledException {
        logger.info("START Ekyc Data population");
        Map<String, Object> dataMap = new HashMap<>();

        String proposalNumber = "";
        String name = "";
        String dob = "";
        String gender = "";
        String careOf = "";
        String house = "";
        String street = "";
        String landmark = "";
        String location = "";
        String pincode = "";
        String village = "";
        String subDist = "";
        String dist = "";
        String state = "";
        String country = "";
        String email = "";
        String mobile = "";
        String photograph = "";

        try {

            if (Objects.nonNull(proposalDetails.getApplicationDetails())) {
                proposalNumber = proposalDetails.getApplicationDetails().getPolicyNumber();
            }

            if (Objects.nonNull(proposalDetails.getEkycResponse())) {
                EkycResponse ekycResponse = proposalDetails.getEkycResponse();

                name = Utility.nullSafe(ekycResponse.getName());
                dob = Utility.nullSafe(ekycResponse.getDOB());
                gender = Utility.nullSafe(ekycResponse.getGender());
                careOf = Utility.nullSafe(ekycResponse.getCareOf());
                house = Utility.nullSafe(ekycResponse.getHouse());
                street = Utility.nullSafe(ekycResponse.getStreet());
                landmark = Utility.nullSafe(ekycResponse.getLandmark());
                location = Utility.nullSafe(ekycResponse.getLocation());
                pincode = Utility.nullSafe(ekycResponse.getPinCode());
                village = Utility.nullSafe(ekycResponse.getVillOrCity());
                subDist = Utility.nullSafe(ekycResponse.getSubDist());
                dist = Utility.nullSafe(ekycResponse.getDist());
                state =Utility.nullSafe(ekycResponse.getState());
                email = Utility.nullSafe(ekycResponse.getEmail());
                mobile = Utility.nullSafe(ekycResponse.getPhone());
                photograph = Utility.nullSafe(ekycResponse.getPhotograph());
            }
            BasicDetails basicDetails = null;
            if (Objects.nonNull(proposalDetails.getPartyInformation())) {
                basicDetails = proposalDetails.getPartyInformation()
                        .stream()
                        .findFirst()
                        .map(PartyInformation::getBasicDetails)
                        .orElse(null);
            }
            if (Objects.nonNull(basicDetails) && Objects.nonNull(basicDetails.getAddress()) &&
                    !basicDetails.getAddress().isEmpty() &&
                    Objects.nonNull(basicDetails.getAddress().get(0).getAddressDetails())) {
                country = basicDetails.getAddress().get(0).getAddressDetails().getCountry();
            }

            dataMap.put("proposalNumber", proposalNumber);
            dataMap.put("name", name);
            dataMap.put("dob", dob);
            dataMap.put("gender", gender);
            dataMap.put("careOf", careOf);
            dataMap.put("house", house);
            dataMap.put("street", street);
            dataMap.put("landmark", landmark);
            dataMap.put("location", location);
            dataMap.put("pincode", pincode);
            dataMap.put("village", village);
            dataMap.put("subDist", subDist);
            dataMap.put("dist", dist);
            dataMap.put("state", state);
            dataMap.put("country", country);
            dataMap.put("email", email);
            dataMap.put("mobile", mobile);
            dataMap.put("photograph", photograph);

        } catch (Exception ex) {
            logger.error("Data addition failed for EKYC Document:", ex);
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Data addition failed");
            throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Context ekycDetailsContext = new Context();
        ekycDetailsContext.setVariables(dataMap);
        logger.info("END EKYC Data population");
        return ekycDetailsContext;
    }
}
