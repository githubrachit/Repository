package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.document.models.AnnexureMedicalQuestions;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.LifeStyleDetails;
import com.mli.mpro.proposal.models.PartyInformation;
import com.mli.mpro.proposal.models.PosvQuestion;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.context.Context;

import java.util.*;
import java.util.stream.Collectors;

import static com.mli.mpro.productRestriction.util.AppConstants.*;

/**
 * @author Dhruv, Mapper class for Annexure medical ques section of Proposal Form document
 */
@Service
public class AnnexureMedicalQuesDetailsMapper {

        @Autowired
        private BaseMapper baseMapper;
        private static final Logger logger = LoggerFactory.getLogger(AnnexureMedicalQuesDetailsMapper.class);


        /**
         * Populate Relevant data for Proposal Document generation in Context
         *
         * @param proposalDetails
         * @return
         * @throws UserHandledException
         */
        public Context setAnnexureData(ProposalDetails proposalDetails) throws UserHandledException {
        Context context = new Context();
        Map<String, Object> dataForDocument = new HashMap<>();
        AnnexureMedicalQuestions annexureQuestions = new AnnexureMedicalQuestions();
        logger.info("Mapping annexure details of proposal form document for transactionId {}", proposalDetails.getTransactionId());
        try {
            setNatureOfDutiesData(proposalDetails,dataForDocument);
            setLifeStyleQuesDetailsData(proposalDetails,dataForDocument);
            List<PosvQuestion> posvQuestionsList =(List<PosvQuestion>) Utility.evaluateConditionalOperation(proposalDetails.getPosvDetails() != null ,
                    proposalDetails.getPosvDetails(),"posvQuestions",
                    new ArrayList<>());
            Map<String, List<PosvQuestion>> posvQAMap = posvQuestionsList.stream().collect(Collectors.groupingBy(PosvQuestion::getParentId));

            /** Setting POSV Annexure Questions response */
            annexureQuestions = setAnnexureQuestions(annexureQuestions, baseMapper, posvQAMap);
            String formType = Utility.getFormType(proposalDetails);
            dataForDocument.put("proposerFormFlag", StringUtils.equalsIgnoreCase(formType, SELF) ||
                    (FORM3.equalsIgnoreCase(formType) && Utility.isSchemeBCase(proposalDetails.getApplicationDetails().getSchemeType())));
            dataForDocument.put("sspFlag", AppConstants.SSP_PRODUCT_ID.equalsIgnoreCase(Utility.getProductId(proposalDetails)));
            dataForDocument.put("annexureQuestions", annexureQuestions);
            context.setVariables(dataForDocument);

        } catch (Exception ex) {
            logger.error("Data addition failed for Proposal Form  Document for transactionId {} : ", proposalDetails.getTransactionId(), ex);
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Data addition failed");
            throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.info("Mapping medical details of proposal form document is completed successfully for transactionId {}", proposalDetails.getTransactionId());
        return context;
    }

    private void setNatureOfDutiesData(ProposalDetails proposalDetails, Map<String, Object> dataForDocument) {
        StringBuilder sspNatureOfDuties = new StringBuilder();
        String partyType = PROPOSER;
        String formType = Utility.getFormType(proposalDetails);
        if(FORM3.equalsIgnoreCase(formType) && !Utility.isSchemeBCase(proposalDetails.getApplicationDetails().getSchemeType())){
            partyType = INSURED;
        }
        PartyInformation partyInformation = Utility.getPartiesInformationByPartyType(proposalDetails, partyType);

        if(getNatureOfRoleForDefence(partyInformation)){
            sspNatureOfDuties.append(partyInformation.getPartyDetails().getIndustryDetails().getIndustryInfo().getNatureOfRole());
        } else if(Objects.nonNull(Utility.getProductInfo(proposalDetails, null))){
            sspNatureOfDuties.append(proposalDetails.getProductDetails().get(0).getProductInfo().getSSESNatureOfDuties());
            if((OTHERS).equalsIgnoreCase(sspNatureOfDuties.toString())){
                sspNatureOfDuties.append(" - ").append(proposalDetails.getProductDetails().get(0).getProductInfo().getSspNatureOfDuties());
            }
        }
        dataForDocument.put("sspNatureOfDuties", sspNatureOfDuties.toString());
    }

    private boolean getNatureOfRoleForDefence(PartyInformation partyInformation) {
        return Objects.nonNull(partyInformation.getPartyDetails()) && Objects.nonNull(partyInformation.getPartyDetails().getIndustryDetails())
                && DEFENCE.equalsIgnoreCase(partyInformation.getPartyDetails().getIndustryDetails().getIndustryType())
                && Objects.nonNull(partyInformation.getPartyDetails().getIndustryDetails().getIndustryInfo())
                && StringUtils.isNotEmpty(partyInformation.getPartyDetails().getIndustryDetails().getIndustryInfo().getNatureOfRole());
    }

    private AnnexureMedicalQuestions setAnnexureQuestions(AnnexureMedicalQuestions annexureQuestions, BaseMapper baseMapper, Map<String, List<PosvQuestion>> posvQAMap) {

        annexureQuestions.setQuesTobaccoAlcoholDrugAns(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H19"));
        annexureQuestions.setQuesSmokingAns(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H19", "H19i"));
        annexureQuestions.setQuesSmokingQtyAns(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H19i", "H19i1"));
        annexureQuestions.setQuesSmokingFreqAns(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H19i", "H19i2"));
        annexureQuestions.setQuesTobaccoGutkaPanMasalaAns(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H19", TOBACCO_GUTKA_PANMASALA));
        annexureQuestions.setQuesTobaccoGutkaPanMasalaQtyAns(baseMapper.getMedicalQuestionAnswer(posvQAMap, TOBACCO_GUTKA_PANMASALA, "H19ii1"));
        annexureQuestions.setQuesTobaccoGutkaPanMasalaFreqAns(baseMapper.getMedicalQuestionAnswer(posvQAMap, TOBACCO_GUTKA_PANMASALA, "H19ii2"));
        annexureQuestions.setQuesBeerWineHardLiquorAns(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H20"));
        annexureQuestions.setQuesBeerAns(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H20", BEER));
        annexureQuestions.setQuesBeerQtyAns(baseMapper.getMedicalQuestionAnswer(posvQAMap, BEER, "H20i1"));
        annexureQuestions.setQuesBeerFreqAns(baseMapper.getMedicalQuestionAnswer(posvQAMap, BEER, "H20i2"));
        annexureQuestions.setQuesWineAns(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H20", WINE));
        annexureQuestions.setQuesWineQtyAns(baseMapper.getMedicalQuestionAnswer(posvQAMap, WINE, "H20ii1"));
        annexureQuestions.setQuesWineFreqAns(baseMapper.getMedicalQuestionAnswer(posvQAMap, WINE, "H20ii2"));
        annexureQuestions.setQuesHardLiquorAns(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H20", HARD_LIQUOR));
        annexureQuestions.setQuesHardLiquorQtyAns(baseMapper.getMedicalQuestionAnswer(posvQAMap, HARD_LIQUOR, "H20iii1"));
        annexureQuestions.setQuesHardLiquorFreqAns(baseMapper.getMedicalQuestionAnswer(posvQAMap, HARD_LIQUOR, "H20iii2"));
        // FUL2- 136867_Heath_Questions_For_Insured
        annexureQuestions.setQuesTobaccoAlcoholDrugAnsInsured(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H42"));
        annexureQuestions.setQuesSmokingAnsInsured(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H42", "H42i"));
        annexureQuestions.setQuesSmokingQtyAnsInsured(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H42i", "H42i1"));
        annexureQuestions.setQuesSmokingFreqAnsInsured(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H42i", "H42i2"));
        annexureQuestions.setQuesTobaccoGutkaPanMasalaAnsInsured(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H42", TOBACCO_GUTKA_PANMASALA_INSURED));
        annexureQuestions.setQuesTobaccoGutkaPanMasalaQtyAnsInsured(baseMapper.getMedicalQuestionAnswer(posvQAMap, TOBACCO_GUTKA_PANMASALA_INSURED, "H42ii1"));
        annexureQuestions.setQuesTobaccoGutkaPanMasalaFreqAnsInsured(baseMapper.getMedicalQuestionAnswer(posvQAMap, TOBACCO_GUTKA_PANMASALA_INSURED, "H42ii2"));
        annexureQuestions.setQuesBeerWineHardLiquorAnsInsured(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H43"));
        annexureQuestions.setQuesBeerAnsInsured(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H43", BEER_INSURED));
        annexureQuestions.setQuesBeerQtyAnsInsured(baseMapper.getMedicalQuestionAnswer(posvQAMap, BEER_INSURED, "H43i1"));
        annexureQuestions.setQuesBeerFreqAnsInsured(baseMapper.getMedicalQuestionAnswer(posvQAMap, BEER_INSURED, "H43i2"));
        annexureQuestions.setQuesWineAnsInsured(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H43", WINE_INSURED));
        annexureQuestions.setQuesWineQtyAnsInsured(baseMapper.getMedicalQuestionAnswer(posvQAMap, WINE_INSURED, "H43ii1"));
        annexureQuestions.setQuesWineFreqAnsInsured(baseMapper.getMedicalQuestionAnswer(posvQAMap, WINE_INSURED, "H43ii2"));
        annexureQuestions.setQuesHardLiquorAnsInsured(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H43", HARD_LIQUOR_INSURED));
        annexureQuestions.setQuesHardLiquorQtyAnsInsured(baseMapper.getMedicalQuestionAnswer(posvQAMap, HARD_LIQUOR_INSURED, "H43iii1"));
        annexureQuestions.setQuesHardLiquorFreqAnsInsured(baseMapper.getMedicalQuestionAnswer(posvQAMap, HARD_LIQUOR_INSURED, "H43iii2"));

        return annexureQuestions;
    }
    private void setLifeStyleQuesDetailsData(ProposalDetails proposalDetails, Map<String, Object> dataForDocument){
        String isProposerWeightChange = AppConstants.NA;
        String proposerWeightChangeReason =AppConstants.NA;
        String isInsuredWeightChange=AppConstants.NA;
        String insuredWeightChangeReason =AppConstants.NA;
        String sumAssuredForSpouseAndChildProposer = AppConstants.NA;
        String sumAssuredForSpouseAndChildInsured = AppConstants.NA;
        List<LifeStyleDetails> lifeStyleDetailsList = proposalDetails.getLifeStyleDetails();
        if(!CollectionUtils.isEmpty(lifeStyleDetailsList) ){
            if(Objects.nonNull(lifeStyleDetailsList.get(0).getHeightAndWeight())){
                isProposerWeightChange = getIsWeightChangedAnswer(lifeStyleDetailsList.get(0).getHeightAndWeight().getIsWeightChanged());
                proposerWeightChangeReason = lifeStyleDetailsList.get(0).getHeightAndWeight().getWeightChangeReason();
            }

            if(Objects.nonNull(lifeStyleDetailsList.get(0).getInsuranceSumAssuredDetails())){
                sumAssuredForSpouseAndChildProposer = lifeStyleDetailsList.get(0).getInsuranceSumAssuredDetails().getSumAssuredForSpouseAndChild();
            }

            if( lifeStyleDetailsList.size() > 1){ // insured condition
                if(Objects.nonNull(lifeStyleDetailsList.get(1).getHeightAndWeight())){
                    isInsuredWeightChange = getIsWeightChangedAnswer(lifeStyleDetailsList.get(1).getHeightAndWeight().getIsWeightChanged());
                    insuredWeightChangeReason = lifeStyleDetailsList.get(1).getHeightAndWeight().getWeightChangeReason();
                }
                if(Objects.nonNull(lifeStyleDetailsList.get(1).getInsuranceSumAssuredDetails())){
                    sumAssuredForSpouseAndChildInsured = lifeStyleDetailsList.get(1).getInsuranceSumAssuredDetails().getSumAssuredForSpouseAndChild();
                }
            }
        }
        dataForDocument.put("isProposerWeightChange", isProposerWeightChange);
        dataForDocument.put("proposerWeightChangeReason", proposerWeightChangeReason);
        dataForDocument.put("isInsuredWeightChange", isInsuredWeightChange);
        dataForDocument.put("insuredWeightChangeReason", insuredWeightChangeReason);
        dataForDocument.put("sumAssuredForSpouseAndChildProposer", sumAssuredForSpouseAndChildProposer);
        dataForDocument.put("sumAssuredForSpouseAndChildInsured", sumAssuredForSpouseAndChildInsured);
    }

    private String getIsWeightChangedAnswer(String answer){

        if (StringUtils.equalsIgnoreCase(answer, "Y")) {
            return "YES";
        } else if (StringUtils.equalsIgnoreCase(answer, "N")) {
            return "NO";
        } else if (StringUtils.equalsIgnoreCase(answer, "NA") || answer == null) {
            return "NA";
        } else {
            return answer;
        }

    }
}
