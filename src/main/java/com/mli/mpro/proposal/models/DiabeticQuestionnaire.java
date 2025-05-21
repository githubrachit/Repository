package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

import java.util.StringJoiner;

/**
 * @author manish on 20/01/21
 */
public class DiabeticQuestionnaire {

    @JsonProperty("diabetesDiagnosedDate")
    private String diabetesDiagnosedDate;
    @JsonProperty("areYouOnInsulin")
    private String areYouOnInsulin;
    @JsonProperty("strictDiet")
    private String strictDiet;
    @JsonProperty("strictDietDetail")
    private String strictDietDetail;
    @JsonProperty("optionalFollowUps")
    private String optionalFollowUps;
    @JsonProperty("followUpsOther")
    private String followUpsOther;
    @JsonProperty("lastBloodTestReport")
    private String lastBloodTestReport;
    @JsonProperty("numbnessInFeet")
    private String numbnessInFeet;
    @JsonProperty("numbnessInFeetDetail")
    private String numbnessInFeetDetail;
    public DiabeticQuestionnaire() {}

    public String getDiabetesDiagnosedDate() {
        return diabetesDiagnosedDate;
    }

    public void setDiabetesDiagnosedDate(String diabetesDiagnosedDate) {
        this.diabetesDiagnosedDate = diabetesDiagnosedDate;
    }

    public String getAreYouOnInsulin() {
        return areYouOnInsulin;
    }

    public void setAreYouOnInsulin(String areYouOnInsulin) {
        this.areYouOnInsulin = areYouOnInsulin;
    }

    public String getStrictDiet() {
        return strictDiet;
    }

    public void setStrictDiet(String strictDiet) {
        this.strictDiet = strictDiet;
    }

    public String getStrictDietDetail() {
        return strictDietDetail;
    }

    public void setStrictDietDetail(String strictDietDetail) {
        this.strictDietDetail = strictDietDetail;
    }

    public String getOptionalFollowUps() {
        return optionalFollowUps;
    }

    public void setOptionalFollowUps(String optionalFollowUps) {
        this.optionalFollowUps = optionalFollowUps;
    }

    public String getFollowUpsOther() {
        return followUpsOther;
    }

    public void setFollowUpsOther(String followUpsOther) {
        this.followUpsOther = followUpsOther;
    }

    public String getLastBloodTestReport() {
        return lastBloodTestReport;
    }

    public void setLastBloodTestReport(String lastBloodTestReport) {
        this.lastBloodTestReport = lastBloodTestReport;
    }

    public String getNumbnessInFeet() {
        return numbnessInFeet;
    }

    public void setNumbnessInFeet(String numbnessInFeet) {
        this.numbnessInFeet = numbnessInFeet;
    }

    public String getNumbnessInFeetDetail() {
        return numbnessInFeetDetail;
    }

    public void setNumbnessInFeetDetail(String numbnessInFeetDetail) {
        this.numbnessInFeetDetail = numbnessInFeetDetail;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return new StringJoiner(", ", DiabeticQuestionnaire.class.getSimpleName() + "[", "]")
                .add("diabetesDiagnosedDate='" + diabetesDiagnosedDate + "'")
                .add("areYouOnInsulin='" + areYouOnInsulin + "'")
                .add("strictDiet='" + strictDiet + "'")
                .add("strictDietDetail='" + strictDietDetail + "'")
                .add("optionalFollowUps='" + optionalFollowUps + "'")
                .add("followUpsOther='" + followUpsOther + "'")
                .add("lastBloodTestReport='" + lastBloodTestReport + "'")
                .add("numbnessInFeet='" + numbnessInFeet + "'")
                .add("numbnessInFeetDetail='" + numbnessInFeetDetail + "'")
                .toString();
    }
}
