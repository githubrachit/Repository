package com.mli.mpro.document.mapper;

import static java.util.Objects.isNull;

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
import com.mli.mpro.document.models.NomineeDataModel;
import com.mli.mpro.utils.Utility;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.BasicDetails;
import com.mli.mpro.proposal.models.PartyDetails;
import com.mli.mpro.proposal.models.PartyInformation;
import com.mli.mpro.proposal.models.ProposalDetails;

@Service
public class MwpaMapper {
    private static final Logger logger = LoggerFactory.getLogger(MwpaMapper.class);

    /**
     * Mapping data from MWPA Document to Context DataMap
     * 
     * @param proposalDetails
     * @return
     * @throws UserHandledException
     */
    public Context setDataOfMwpaDocument(ProposalDetails proposalDetails) throws UserHandledException {
	logger.info("START MWPA Data population");
	Map<String, Object> dataVariables = new HashMap<>();
	try {
	    String title = "";
	    String proposalNumber = proposalDetails.getApplicationDetails().getPolicyNumber();
	    String firstName = "";
	    String middleName = "";
	    String lastName = "";
	    String dob = "";

	    List<PartyInformation> partyInfoList = proposalDetails.getPartyInformation();
	    if (null != partyInfoList && !CollectionUtils.isEmpty(partyInfoList) && partyInfoList.size() >= 1) {
		BasicDetails basicDetails = partyInfoList.get(0).getBasicDetails();
		title = Utility.getTitle(basicDetails.getGender());
		firstName = basicDetails.getFirstName();
		middleName = basicDetails.getMiddleName();
		lastName = basicDetails.getLastName();
		dob = Utility.dateFormatter(basicDetails.getDob());

	    }

	    Map<String, Object> mwpaMap = new HashMap<>();
	    mwpaMap.put("proposalNumber", proposalNumber);
	    mwpaMap.put("title", title);
	    mwpaMap.put("firstName", firstName);
	    mwpaMap.put("middleName", middleName);
	    mwpaMap.put("lastName", lastName);
	    mwpaMap.put("dob", dob);
			mwpaMap.put(AppConstants.IS_NOT_YBL_PROPOSAL, !Utility.isYBLProposal(proposalDetails));
	    List<PartyDetails> nomineePartyDetailsList = proposalDetails.getNomineeDetails().getPartyDetails();
	    mwpaMap.put("nomineeDetails", setNominees(nomineePartyDetailsList, dataVariables));
	    dataVariables.put("mwpaMap", mwpaMap);

	} catch (Exception ex) {
	    logger.error("Data addition failed for Mwpa Document:", ex);
	    List<String> errorMessages = new ArrayList<>();
	    errorMessages.add("Data addition failed");
	    throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	Context mwpaDetailsCxt = new Context();
	mwpaDetailsCxt.setVariables(dataVariables);
	logger.info("END Mwpa Data population");
	return mwpaDetailsCxt;
    }

    /**
     * Set nominees for iterative retrieval
     * 
     * @param nomineePartyDetailsList
     * @param dataVariables
     * @return
     */
    private List<NomineeDataModel> setNominees(List<PartyDetails> nomineePartyDetailsList, Map<String, Object> dataVariables) {
	logger.info("mwpa setNominees {}", "%m");
	NomineeDataModel nomineeDataModel;
	List<NomineeDataModel> nomineeDataModelsList = new ArrayList<>();
	try {
	    if (null != nomineePartyDetailsList && !CollectionUtils.isEmpty(nomineePartyDetailsList)) {
		int nomineeLength = nomineePartyDetailsList.size();
		logger.info("Number of Nominees: {} ", nomineeLength);
		for (PartyDetails item : nomineePartyDetailsList) {
		    nomineeDataModel = new NomineeDataModel();
		    nomineeDataModel.setTitle(Utility.getTitle(item.getGender()));
		    nomineeDataModel.setFirstName(item.getFirstName());
		    nomineeDataModel.setMiddleName(item.getMiddleName());
		    nomineeDataModel.setLastName(item.getLastName());
		    nomineeDataModel
			    .setRelation(StringUtils.isNotEmpty(item.getRelationshipWithProposer()) ? item.getRelationshipWithProposer() : AppConstants.BLANK);
		    nomineeDataModel.setDob(Utility.dateFormatter(item.getDob()));
		    nomineeDataModel.setNomineeShare(String.valueOf(item.getPercentageShare()));

		    if (!isNull(item.getNomineeBankDetails()) && !CollectionUtils.isEmpty(item.getNomineeBankDetails().getBankDetails()) && item.getNomineeBankDetails().getBankDetails().size() >= 1) {
			nomineeDataModel.setTypeOfAccount(item.getNomineeBankDetails().getBankDetails().get(0).getTypeOfAccount());
			nomineeDataModel.setBankBranch(item.getNomineeBankDetails().getBankDetails().get(0).getBankBranch());
			nomineeDataModel.setBankName(item.getNomineeBankDetails().getBankDetails().get(0).getBankName());
			nomineeDataModel.setBankAccountNumber(item.getNomineeBankDetails().getBankDetails().get(0).getBankAccountNumber());
			nomineeDataModel.setMicr(item.getNomineeBankDetails().getBankDetails().get(0).getMicr());
			nomineeDataModel.setIfsc(item.getNomineeBankDetails().getBankDetails().get(0).getIfsc());
			nomineeDataModel.setAccountHolderName(item.getNomineeBankDetails().getBankDetails().get(0).getAccountHolderName());

		    }

		    nomineeDataModelsList.add(nomineeDataModel);
		}
	    } else {
		logger.info("Nominees not present!");
	    }
	} catch (Exception ex) {
	    logger.error("Error occurred while setting nominee data in Context:", ex);
	}
	logger.info("END mwpa setNominees, nomineeDataModelsList size -- {}", nomineeDataModelsList.size());
	return nomineeDataModelsList;
    }
}
