package com.mli.mpro.document.service.impl;

import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.IndustryInfo;
import com.mli.mpro.proposal.models.PosvQuestion;
import com.mli.mpro.utils.Utility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
/**
 * Class with mapper methods
 * @author nshom4522
 */
@Service
public class BaseMapper {
    private static final Logger logger = LoggerFactory.getLogger(BaseMapper.class);
    /**
     * Find answer of POSV question
     *
     * @param posvQAMap
     * @param questionId
     * @return
     */
    public String getMedicalQuestionAnswer(Map<String, List<PosvQuestion>> posvQAMap, String parentId, String questionId) {
        logger.debug("getMedicalQuestionAnswer questionId: {}", questionId);
        List<PosvQuestion> posvQAList = posvQAMap.get(parentId);
        if (null != posvQAList) {
            List<PosvQuestion> filteredPosvQA = posvQAList.stream().filter(x -> x.getQuestionId().equalsIgnoreCase(questionId)).collect(Collectors.toList());

            if (null != filteredPosvQA && filteredPosvQA.size() > 0) {
                String answer = filteredPosvQA.get(0).getAnswer();

                if (StringUtils.equalsIgnoreCase(answer, "Y")) {
                    return "YES";
                } else if (StringUtils.equalsIgnoreCase(answer, "N")) {
                    return "NO";
                } else if (StringUtils.equalsIgnoreCase(answer, "NA") || answer == null || StringUtils.equalsIgnoreCase(answer, "")) {
                    return "NA";
                } else {
                    return answer;
                }
            }
        }
        return "NA";
    }
    /**
     * Data getting
     * @return
     */

    public String getPFGeneratedDate() {
        String date = "";
        String month = "";
        String year = "";
        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("d");
        DateFormat dateFormat = new SimpleDateFormat();
        dateFormat.setTimeZone(TimeZone.getTimeZone(AppConstants.APP_TIMEZONE));
        date = formatter.format(currentDate);
        String ordinalValue = Utility.ordinal(Integer.valueOf(date));
        formatter = new SimpleDateFormat("MMM");
        month = formatter.format(currentDate);
        formatter = new SimpleDateFormat("yyyy");
        year = formatter.format(currentDate);

        String pfDate = ordinalValue.concat(" ").concat(month).concat(" ").concat(year);
        return pfDate;
    }

    public String getPfGeneratedTime() {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        dateFormat.setTimeZone(TimeZone.getTimeZone(AppConstants.APP_TIMEZONE));
        String preferredTime = dateFormat.format(new Date());
        return preferredTime;
    }

    public void getIndustryType(Map<String, Object> dataVariables, String industryType, IndustryInfo industryInfo,boolean isProposerIndustryTypeDefence) {
        switch (industryType) {

            case AppConstants.DEFENCE:
                if(!isProposerIndustryTypeDefence){
                    dataVariables.put("insureddefenceReflexive1", industryInfo.isPostedOnDefenceLocation() ? "YES" : "NO");
                    dataVariables.put("insureddefenceReflexive2", industryInfo.getNatureOfRole());
                    dataVariables.put("insureddefence", true);
                }
                if(isProposerIndustryTypeDefence){
                    dataVariables.put("defenceReflexive1", industryInfo.isPostedOnDefenceLocation() ? "YES" : "NO");
                    dataVariables.put("defenceReflexive2", industryInfo.getNatureOfRole());
                    dataVariables.put("defence", true);
                }
                break;

            case AppConstants.DIVING:
                dataVariables.put("insureddivingReflexive1", industryInfo.isProfessionalDiver() ? "YES" : "NO");
                dataVariables.put("insureddivingReflexive2", industryInfo.getDiveLocation());
                dataVariables.put("insureddiving", true);
                break;
            case AppConstants.MINING:
                dataVariables.put("insuredminingReflexive1", industryInfo.isWorkingInsideMine() ? "YES" : "NO");
                dataVariables.put("insuredminingReflexive2", industryInfo.isAnyIllnessRelatedToOccupation() ? "YES" : "NO");
                dataVariables.put("insuredmining", true);
                break;
            case AppConstants.AIR_FORCE:
                dataVariables.put("insuredairforceReflexive1", industryInfo.isFlying() ? "YES" : "NO");
                dataVariables.put("insuredairforceReflexive2", industryInfo.getTypeOfAirCraft());
                dataVariables.put("insuredairforce", true);
                break;
            case AppConstants.OIL:
                dataVariables.put("insuredoilReflexive1", industryInfo.isBasedAtOffshore() ? "YES" : "NO");
                dataVariables.put("insuredoil", true);
                break;
            case AppConstants.NAVY:
                dataVariables.put("insurednavyReflexive1", industryInfo.getNavyAreaWorking());
                dataVariables.put("insurednavy", true);
                break;
            default:
                logger.info("No Industry type found");

        }
    }

}
