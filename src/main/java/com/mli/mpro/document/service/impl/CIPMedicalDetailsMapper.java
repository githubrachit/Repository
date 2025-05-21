package com.mli.mpro.document.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.mli.mpro.utils.Utility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.document.models.MedicalQuestions;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.LifeStyleDetails;
import com.mli.mpro.proposal.models.PosvQuestion;
import com.mli.mpro.proposal.models.ProposalDetails;

import static org.thymeleaf.util.StringUtils.isEmpty;

@Service
public class CIPMedicalDetailsMapper {
	@Autowired
	private BaseMapper baseMapper;
    private static final Logger logger = LoggerFactory.getLogger(MedicalDetailsMapper.class);
	public static final String PARENT_ID_ONE = "H13fi";
	public static final String PARENT_ID_TWO = "H13Fii";
    public Context setDataForMedicalDetails(ProposalDetails proposalDetails) throws UserHandledException {

	logger.info("Mapping medical details of proposal form document for transactionId {}", proposalDetails.getTransactionId());
	Context context = new Context();
	Map<String, Object> dataForDocument = new HashMap<>();
	MedicalQuestions medicalQuestions;
	try {
	    List<LifeStyleDetails> lifeStyleDetailsList = proposalDetails.getLifeStyleDetails();
		boolean proposerFormFlag = filter(proposalDetails.getApplicationDetails().getFormType(),(String s)-> s.equalsIgnoreCase(AppConstants.SELF));
		String proposerHeight = AppConstants.NA;
		String proposerWeight  = AppConstants.NA;

		String insuredHeight = AppConstants.NA;
		String insuredWeight = AppConstants.NA;
		String proposerAbhaNumber = AppConstants.BLANK;
		String insurerAbhaNumber = AppConstants.BLANK;
		if(lifeStyleDetailsList!=null && lifeStyleDetailsList.size() >1){
			if(!(isEmpty(proposalDetails.getLifeStyleDetails().get(1).getHeightAndWeight().getHeight())
					|| StringUtils.equalsIgnoreCase(proposalDetails.getLifeStyleDetails().get(1).getHeightAndWeight().getHeight(), "0"))){
				insuredHeight =  String.join(AppConstants.WHITE_SPACE, proposalDetails.getLifeStyleDetails().get(1).getHeightAndWeight().getHeight(),
						AppConstants.HEIGHT_UNIT);
			}
			if(!(isEmpty(proposalDetails.getLifeStyleDetails().get(1).getHeightAndWeight().getWeight())
					|| StringUtils.equalsIgnoreCase(proposalDetails.getLifeStyleDetails().get(1).getHeightAndWeight().getWeight(), "0"))){
				insuredHeight = String.join(AppConstants.WHITE_SPACE, proposalDetails.getLifeStyleDetails().get(1).getHeightAndWeight().getWeight(),
						AppConstants.WEIGHT_UNIT);
			}
			if(!(isEmpty(proposalDetails.getLifeStyleDetails().get(0).getHeightAndWeight().getWeight())
					|| StringUtils.equalsIgnoreCase(proposalDetails.getLifeStyleDetails().get(0).getHeightAndWeight().getWeight(), "0"))){
				proposerWeight =  String.join(AppConstants.WHITE_SPACE, proposalDetails.getLifeStyleDetails().get(0).getHeightAndWeight().getWeight(),
						AppConstants.WEIGHT_UNIT);
			}
			if(!(StringUtils.isEmpty(lifeStyleDetailsList.get(0).getHeightAndWeight().getHeight())
					|| StringUtils.equalsIgnoreCase(proposalDetails.getLifeStyleDetails().get(0).getHeightAndWeight().getHeight(), "0"))){
				proposerHeight = String.join(AppConstants.WHITE_SPACE, proposalDetails.getLifeStyleDetails().get(0).getHeightAndWeight().getHeight(),
						AppConstants.HEIGHT_UNIT);
			}
		}
		String diseaseBefor60 = getFamilyDiagonsedDisease60(proposalDetails);

		List<PosvQuestion> posvQuestionsList = getposvQuestionsList(proposalDetails);
	    Map<String, List<PosvQuestion>> posvQAMap = posvQuestionsList.stream().collect(Collectors.groupingBy(PosvQuestion::getParentId));

		// FUL2-195747 PF form changes for ABHA Id
		if (Utility.isJointLifeCase(proposalDetails)) {
			proposerAbhaNumber = Utility.setSecondInsurerJointLifeAbhaNumberInPfForm(proposalDetails);
			insurerAbhaNumber = Utility.setInsurerAbhaNumberInPfForm(proposalDetails);
		} else {
			insurerAbhaNumber = Utility.setInsurerAbhaNumberInPfForm(proposalDetails);
		}

		/** Setting POSV Medical Questions response */
		medicalQuestions = new MedicalQuestions();
		medicalQuestions.setQuestion1Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H13Ai"));
		medicalQuestions.setQuestion2Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H13Ai", "H13Aii"));
		medicalQuestions.setQuestion3Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H13Bi"));
		medicalQuestions.setQuestion3AChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H13Bi", "H13Bii"));
		medicalQuestions.setQuestion3BChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H13C"));
		medicalQuestions.setQuestion3CChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H13C", "H13Ci"));
		medicalQuestions.setQuestion3DChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H13D"));
		medicalQuestions.setQuestion4Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H13D", "H13Di"));
		medicalQuestions.setQuestion4AChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H13E"));
		medicalQuestions.setQuestion4BChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H13E", "H13Ei"));
		medicalQuestions.setQuestion4CChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H13F"));
		medicalQuestions.setQuestion4DChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", PARENT_ID_ONE));

		medicalQuestions.setQuestion5Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, PARENT_ID_ONE, "H13Fia"));
		medicalQuestions.setQuestion5AChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, PARENT_ID_ONE, "H13Fib"));
		medicalQuestions.setQuestion5BChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, PARENT_ID_ONE, "H13Fic"));
		medicalQuestions.setQuestion5CChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, PARENT_ID_ONE, "H13Fid"));

		medicalQuestions.setQuestion6Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", PARENT_ID_TWO));
		medicalQuestions.setQuestion6AChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, PARENT_ID_TWO, "H13Fiia"));
		medicalQuestions.setQuestion6BChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, PARENT_ID_TWO, "H13Fiib"));
		medicalQuestions.setQuestion6CChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, PARENT_ID_TWO, "H13Fiic"));
		medicalQuestions.setQuestion6DChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, PARENT_ID_TWO, "H13Fiid"));
		medicalQuestions.setQuestion6EChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H13G"));
		medicalQuestions.setQuestion7Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H13G", "H13Gi"));
		medicalQuestions.setQuestion9Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H14"));
		medicalQuestions.setQuestion9AChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H14", "H14A"));
		medicalQuestions.setQuestion10Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H15"));
		medicalQuestions.setQuestion11Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H15", "H15A"));

		dataForDocument.put("proposerHeight", proposerHeight);
		dataForDocument.put("proposerWeight", proposerWeight);
		dataForDocument.put("insuredHeight", insuredHeight);
		dataForDocument.put("insuredWeight", insuredWeight);
		dataForDocument.put("proposerFormFlag", proposerFormFlag);
		dataForDocument.put("diseaseBefor60", diseaseBefor60);
		dataForDocument.put("medicalQuestions", medicalQuestions);
		dataForDocument.put("proposerAbhaNumber",proposerAbhaNumber);
		dataForDocument.put("insurerAbhaNumber",insurerAbhaNumber);

		context.setVariables(dataForDocument);
	    logger.info("Mapping medical details of proposal form document is completed successfully for transactionId {}", proposalDetails.getTransactionId());

	} catch (Exception ex) {
	    logger.error("Data addition failed for Proposal Form Document:", ex);
	    List<String> errorMessages = new ArrayList<String>();
	    errorMessages.add("Data addition failed");
	    throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	return context;

    }


	public static <T> boolean filter(T str, Predicate<T> p){
		return p.test(str);
	}

	private List<PosvQuestion> getposvQuestionsList(ProposalDetails proposalDetails) throws Exception {
		return proposalDetails.getPosvDetails() != null ? proposalDetails.getPosvDetails().getPosvQuestions()
				: new ArrayList<>();
	}

	private String getFamilyDiagonsedDisease60(ProposalDetails proposalDetails) throws Exception{
		if (proposalDetails.getApplicationDetails().getFormType().equalsIgnoreCase(AppConstants.DEPENDENT)) {
			return proposalDetails.getLifeStyleDetails().get(1).getFamilyOrCriminalHistory().getFamilyDiagnosedWithDiseasesBefore60();
		}
		return proposalDetails.getLifeStyleDetails().get(0).getFamilyOrCriminalHistory().getFamilyDiagnosedWithDiseasesBefore60();
	}

}
