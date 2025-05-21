/**
 * 
 */
package com.mli.mpro.document.service.impl;

import com.mli.mpro.document.models.NomineeDataModel;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.PartyDetails;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
/**
 * @author akshom4375
 */
@Service
public class ULIPNomineeMapper {

    private static final Logger logger = LoggerFactory.getLogger(ULIPNomineeMapper.class);
    private boolean isNeoOrAggregator = false;

    private ZoneId defaultZoneId = ZoneId.of(AppConstants.APP_TIMEZONE);

    /**
     * Setting data in Context for Nominee section of ULIP Proposal Form
     * 
     * @param proposalDetails
     * @return
     */
    public Context setDataOfNominee(ProposalDetails proposalDetails) {
	logger.info("START setDataOfNominee {}", "%m");
	Map<String, Object> dataVariables = new HashMap<>();
	Context decalarationContext = new Context();
	//NEORW-173: this will check that incoming request is from NEO or Aggregator
	isNeoOrAggregator = proposalDetails.getChannelDetails().getChannel().equalsIgnoreCase(AppConstants.CHANNEL_NEO)
			|| proposalDetails.getChannelDetails().getChannel().equalsIgnoreCase(AppConstants.CHANNEL_AGGREGATOR)
			? true : false;

	if (null != proposalDetails.getNomineeDetails()) {
	    List<PartyDetails> nomineePartyDetailsList = proposalDetails.getNomineeDetails().getPartyDetails();
	    dataVariables = setNominees(nomineePartyDetailsList, dataVariables, proposalDetails.getChannelDetails().getChannel());
	}

	decalarationContext.setVariables(dataVariables);

	logger.info("END setDataOfNominee {}", "%m");
	return decalarationContext;
    }

    /**
     * Set nominees for iterative retrieval
     * 
     * @param nomineePartyDetailsList
     * @param dataVariables
     * @return
     */
	public Map<String, Object> setNominees(List<PartyDetails> nomineePartyDetailsList, Map<String, Object> dataVariables, String channel) {
		logger.info("START setNominees {}", "%m");
		NomineeDataModel nomineeDataModel;
		List<NomineeDataModel> nomineeDataModelsList = new ArrayList<>();
		String childName = StringUtils.EMPTY;
		String childDob = StringUtils.EMPTY;
		try {
			//Child Details
			if (!CollectionUtils.isEmpty(nomineePartyDetailsList)) {
				childName = nomineePartyDetailsList.get(0).getNomineeChildName();
				if (null != nomineePartyDetailsList.get(0).getNomineeChildDob()) {
					childDob = Utility.dateFormatter(nomineePartyDetailsList.get(0).getNomineeChildDob());
				}

				int nomineeLength = nomineePartyDetailsList.size();
				logger.info("Number of Nominees: {}" + nomineeLength);
				for (PartyDetails item : nomineePartyDetailsList) {
					nomineeDataModel = new NomineeDataModel();
					nomineeDataModel.setTitle(Utility.getTitle(item.getGender()));
					nomineeDataModel.setFirstName(item.getFirstName());
					nomineeDataModel.setMiddleName(item.getMiddleName());
					nomineeDataModel.setLastName(item.getLastName());
					nomineeDataModel.setGender(Utility.getGender(item.getGender()));
					nomineeDataModel.setGuardianName(Utility.evaluateConditionalOperation(
							(null != item.getAppointeeDetails().getGuardianNameOfNominee()) , item.getAppointeeDetails().getGuardianNameOfNominee()
									, AppConstants.BLANK));
                    if (isNeoOrAggregator) {
                        nomineeDataModel.setGuardianRelation(item.getAppointeeDetails().getGuardianIsYour());
                        nomineeDataModel.setDob(Utility.dateFormatter(
                                Utility.dateFormatter(item.getDob()), "dd-MM-yyyy", "dd/MM/yyyy"));
                    } else {
                        nomineeDataModel.setGuardianRelation(
                                (null != item.getAppointeeDetails().getRelationwithNominee()) ? item.getAppointeeDetails().getRelationwithNominee()
                                        : AppConstants.BLANK);
                        nomineeDataModel.setDob(Utility.dateFormatter(item.getDob()));
                    }
                    nomineeDataModel.setGuardianRelationshipWhenOther(Utility.evaluateConditionalOperation(
							(null != item.getAppointeeDetails().getRelationwithNomineeOthers()) , item.getAppointeeDetails().getRelationwithNomineeOthers()
									, AppConstants.BLANK));

                    int lifeInsuredAge = -1;
                    Date nomineeDob = item.getDob();
                    if (null != nomineeDob) {
                        Instant nomineeDobInstant = nomineeDob.toInstant();
                        LocalDate nomineeDobLocalDate = nomineeDobInstant.atZone(defaultZoneId).toLocalDate();
                        lifeInsuredAge = Utility.calculateAge(nomineeDobLocalDate, LocalDate.now());
                    }
                    nomineeDataModel.setMinorFlag(lifeInsuredAge != -1 && lifeInsuredAge < 18 ? true : false);
                    nomineeDataModel.setDob(Utility.dateFormatter(item.getDob()));
                    nomineeDataModel.setPercentageShare(Utility.evaluateConditionalOperation((nomineeLength == 1) , "100" , String.valueOf(item.getPercentageShare())));
					nomineeDataModel.setReasonForNomination(Utility.evaluateConditionalOperation(null != item.getReasonForNomination() , item.getReasonForNomination() , AppConstants.BLANK));
					nomineeDataModel.setRelation(Utility.evaluateConditionalOperation(null != item.getRelationshipWithProposer() , item.getRelationshipWithProposer() , AppConstants.BLANK));
					nomineeDataModel.setRelationshipOthers(Utility.evaluateConditionalOperation(null != item.getRelationshipOthers() , item.getRelationshipOthers() , AppConstants.BLANK));
					nomineeDataModel
							.setShowGuradianOther(StringUtils.equalsIgnoreCase(item.getAppointeeDetails().getRelationwithNominee(), "Others"));
					nomineeDataModel.setShowOther(StringUtils.equalsIgnoreCase(item.getRelationshipWithProposer(), "Others") );
					if (AppConstants.THANOS_CHANNEL.equalsIgnoreCase(channel) && (lifeInsuredAge != -1 && lifeInsuredAge < 18)) {
						logger.info("setting appointeeDetails for thanos case {}  {} ", item.getAppointeeDetails().getGuardianName() ,item.getAppointeeDetails().getGuardianRelation());
						nomineeDataModel.setGuardianName(item.getAppointeeDetails().getGuardianName());
						nomineeDataModel.setGuardianRelation(item.getAppointeeDetails().getGuardianRelation());
					}
					nomineeDataModelsList.add(nomineeDataModel);
				}
			} else {
				logger.info("Nominees not present!");
			}
		} catch (Exception ex) {
	    logger.error("Error occurred while setting nominee data in Context:", ex);
		}
		logger.info("END setNominees {}", "%m");
		dataVariables.put("nomineeDetails", nomineeDataModelsList);
		dataVariables.put("childName", Utility.evaluateConditionalOperation(StringUtils.isNotBlank(childName) , childName , AppConstants.NA));
		dataVariables.put("childDob", Utility.evaluateConditionalOperation( StringUtils.isNotBlank(childDob) , childDob , AppConstants.NA));
		return dataVariables;
	}

}
