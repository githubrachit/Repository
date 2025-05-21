
package com.mli.mpro.proposal.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "willngnssForFinRisk", "invstmntAppealingUrfolio", "feelUncmfrtblWhenInvstmntGoDwn", "dscribeUrSavngshbts", "your_Risk_Score",
	"recommendedFund" })
public class Irp {

    @JsonProperty("willngnssForFinRisk")
    private String willngnssForFinRisk;
    @JsonProperty("invstmntAppealingUrfolio")
    private String invstmntAppealingUrfolio;
    @JsonProperty("feelUncmfrtblWhenInvstmntGoDwn")
    private String feelUncmfrtblWhenInvstmntGoDwn;
    @JsonProperty("dscribeUrSavngshbts")
    private String dscribeUrSavngshbts;
    @JsonProperty("your_Risk_Score")
    private Object your_Risk_Score;
    @JsonProperty("recommendedFund")
    private List<String> recommendedFund = null;
    @JsonProperty("IRPQ1")
    private String IRPQ1;
    @JsonProperty("IRPQ2")
    private String IRPQ2;
    @JsonProperty("IRPQ3")
    private String IRPQ3;
    @JsonProperty("IRPQ4")
    private String IRPQ4;
    @JsonProperty("IRPRiskClassification")
    private String IRPRiskClassification;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Irp() {
    }

    /**
     *
     */

    @JsonProperty("willngnssForFinRisk")
    public String getWillngnssForFinRisk() {
	return willngnssForFinRisk;
    }

    public Irp(String willngnssForFinRisk, String invstmntAppealingUrfolio, String feelUncmfrtblWhenInvstmntGoDwn, String dscribeUrSavngshbts,
	    Object your_Risk_Score, List<String> recommendedFund, String iRPQ1, String iRPQ2, String iRPQ3, String iRPQ4, String iRPRiskClassification) {
	super();
	this.willngnssForFinRisk = willngnssForFinRisk;
	this.invstmntAppealingUrfolio = invstmntAppealingUrfolio;
	this.feelUncmfrtblWhenInvstmntGoDwn = feelUncmfrtblWhenInvstmntGoDwn;
	this.dscribeUrSavngshbts = dscribeUrSavngshbts;
	this.your_Risk_Score = your_Risk_Score;
	this.recommendedFund = recommendedFund;
	IRPQ1 = iRPQ1;
	IRPQ2 = iRPQ2;
	IRPQ3 = iRPQ3;
	IRPQ4 = iRPQ4;
	IRPRiskClassification = iRPRiskClassification;
    }

     

    public Irp(Irp irp) {
	// TODO Auto-generated constructor stub
    }

    @JsonProperty("willngnssForFinRisk")
    public void setWillngnssForFinRisk(String willngnssForFinRisk) {
	this.willngnssForFinRisk = willngnssForFinRisk;
    }

    @JsonProperty("invstmntAppealingUrfolio")
    public String getInvstmntAppealingUrfolio() {
	return invstmntAppealingUrfolio;
    }

    @JsonProperty("invstmntAppealingUrfolio")
    public void setInvstmntAppealingUrfolio(String invstmntAppealingUrfolio) {
	this.invstmntAppealingUrfolio = invstmntAppealingUrfolio;
    }

    @JsonProperty("feelUncmfrtblWhenInvstmntGoDwn")
    public String getFeelUncmfrtblWhenInvstmntGoDwn() {
	return feelUncmfrtblWhenInvstmntGoDwn;
    }

    @JsonProperty("feelUncmfrtblWhenInvstmntGoDwn")
    public void setFeelUncmfrtblWhenInvstmntGoDwn(String feelUncmfrtblWhenInvstmntGoDwn) {
	this.feelUncmfrtblWhenInvstmntGoDwn = feelUncmfrtblWhenInvstmntGoDwn;
    }

    @JsonProperty("dscribeUrSavngshbts")
    public String getDscribeUrSavngshbts() {
	return dscribeUrSavngshbts;
    }

    @JsonProperty("dscribeUrSavngshbts")
    public void setDscribeUrSavngshbts(String dscribeUrSavngshbts) {
	this.dscribeUrSavngshbts = dscribeUrSavngshbts;
    }

    @JsonProperty("your_Risk_Score")
    public Object getYour_Risk_Score() {
	return your_Risk_Score;
    }

    @JsonProperty("your_Risk_Score")
    public void setYour_Risk_Score(Object your_Risk_Score) {
	this.your_Risk_Score = your_Risk_Score;
    }

    @JsonProperty("recommendedFund")
    public List<String> getRecommendedFund() {
	return recommendedFund;
    }

    @JsonProperty("recommendedFund")
    public void setRecommendedFund(List<String> recommendedFund) {
	this.recommendedFund = recommendedFund;
    }

    public String getIRPQ1() {
	return IRPQ1;
    }

    public void setIRPQ1(String iRPQ1) {
	IRPQ1 = iRPQ1;
    }

    public String getIRPQ2() {
	return IRPQ2;
    }

    public void setIRPQ2(String iRPQ2) {
	IRPQ2 = iRPQ2;
    }

    public String getIRPQ3() {
	return IRPQ3;
    }

    public void setIRPQ3(String iRPQ3) {
	IRPQ3 = iRPQ3;
    }

    public String getIRPQ4() {
	return IRPQ4;
    }

    public void setIRPQ4(String iRPQ4) {
	IRPQ4 = iRPQ4;
    }

    public String getIRPRiskClassification() {
	return IRPRiskClassification;
    }

    public void setIRPRiskClassification(String iRPRiskClassification) {
	IRPRiskClassification = iRPRiskClassification;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "Irp [willngnssForFinRisk=" + willngnssForFinRisk + ", invstmntAppealingUrfolio=" + invstmntAppealingUrfolio
		+ ", feelUncmfrtblWhenInvstmntGoDwn=" + feelUncmfrtblWhenInvstmntGoDwn + ", dscribeUrSavngshbts=" + dscribeUrSavngshbts + ", your_Risk_Score="
		+ your_Risk_Score + ", recommendedFund=" + recommendedFund + ", IRPQ1=" + IRPQ1 + ", IRPQ2=" + IRPQ2 + ", IRPQ3=" + IRPQ3 + ", IRPQ4=" + IRPQ4
		+ ", IRPRiskClassification=" + IRPRiskClassification + "]";
    }

}
