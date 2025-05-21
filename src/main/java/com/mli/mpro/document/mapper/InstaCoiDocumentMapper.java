package com.mli.mpro.document.mapper;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InstaCoiDocumentMapper {
    private static final Logger logger = LoggerFactory.getLogger(InstaCoiDocumentMapper.class);
    public Context setDocumentData(ProposalDetails proposalDetails) throws UserHandledException{
        logger.info("START NeoInstaCoi Document DataMaping");
        Map<String, Object> dataMap = new HashMap<>();

        String proposalNumber = "";
        String planeName = "";
        String clearCaseFlag = "";
        try{
            proposalNumber = Utility.nullSafe(proposalDetails.getApplicationDetails().getPolicyNumber());
            planeName = Utility.nullSafe(proposalDetails.getProductDetails().get(0).getProductInfo().getProductName());
            if("Y".equalsIgnoreCase(proposalDetails.getCcFlag())){
                clearCaseFlag = "Yes";
            }else{
                clearCaseFlag="No";
            }
            dataMap.put("proposalNumber", proposalNumber);
            dataMap.put("planName", planeName);
            dataMap.put("instaCoiFlag", clearCaseFlag);
        }catch (Exception ex) {
            logger.error("Data addition failed for instaCoi Document:", ex);
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Data addition failed insta coi Document");
            throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Context instaCoiDocumentContext = new Context();
        instaCoiDocumentContext.setVariables(dataMap);
        logger.info("END InstaCoi Document Data Mapper");
        return instaCoiDocumentContext;
    }
}
