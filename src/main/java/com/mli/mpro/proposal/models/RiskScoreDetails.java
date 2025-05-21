package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class RiskScoreDetails {

    @Sensitive(MaskType.POLICY_NUM)
    @JsonProperty("policyNumber")
    private String policyNumber;
    @JsonProperty("entryDate")
    private String entryDate;
    @JsonProperty("channel")
    private String channel;
    @JsonProperty("statusCode")
    private String statusCode;
    @JsonProperty("message")
    private String message;
    @JsonProperty("score")
    private String score;
   //neo fields
    @JsonProperty("Status_URMU")
    private String statusURMU;
    @JsonProperty("Status_PERS")
    private String statusPERS;
    @JsonProperty("Scoring_Channel_URMU")
    private String scoringChannelURMU;
    @JsonProperty("Scoring_Channel_PERS")
    private String scoringChannelPERS;
    @JsonProperty("Error_Code_URMU")
    private String errorCodeURMU;
    @JsonProperty("Error_Code_PERS")
    private String errorCodePERS;
    @JsonProperty("Message_URMU")
    private String messageURMU;
    @JsonProperty("Message_PERS")
    private String messagePERS;
    @JsonProperty("Risky_Flag_URMU")
    private String riskyFlagURMU;
    @JsonProperty("Risky_Flag_PERS")
    private String riskyFlagPERS;
    @JsonProperty("Risky_Tag_URMU")
    private String riskyTagURMU;
    @JsonProperty("Risky_Tag_PERS")
    private String riskyTagPERS;
    @JsonProperty("Normalised_Score_URMU")
    private String normalisedScoreURMU;
    @JsonProperty("Normalised_Score_PERS")
    private String normalisedScorePERS;

    public RiskScoreDetails() {
        super();
    }


    public RiskScoreDetails(String policyNumber, String entryDate, String channel, String statusCode, String message,
                            String score) {
        super();
        this.policyNumber = policyNumber;
        this.entryDate = entryDate;
        this.channel = channel;
        this.statusCode = statusCode;
        this.message = message;
        this.score = score;
    }


    public String getPolicyNumber() {
        return policyNumber;
    }


    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }


    public String getEntryDate() {
        return entryDate;
    }


    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }


    public String getChannel() {
        return channel;
    }


    public void setChannel(String channel) {
        this.channel = channel;
    }


    public String getStatusCode() {
        return statusCode;
    }


    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }


    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }


    public String getScore() {
        return score;
    }


    public void setScore(String score) {
        this.score = score;
    }

    public String getStatusURMU() {
        return statusURMU;
    }

    public void setStatusURMU(String statusURMU) {
        this.statusURMU = statusURMU;
    }

    public String getStatusPERS() {
        return statusPERS;
    }

    public void setStatusPERS(String statusPERS) {
        this.statusPERS = statusPERS;
    }

    public String getScoringChannelURMU() {
        return scoringChannelURMU;
    }

    public void setScoringChannelURMU(String scoringChannelURMU) {
        this.scoringChannelURMU = scoringChannelURMU;
    }

    public String getScoringChannelPERS() {
        return scoringChannelPERS;
    }

    public void setScoringChannelPERS(String scoringChannelPERS) {
        this.scoringChannelPERS = scoringChannelPERS;
    }

    public String getErrorCodeURMU() {
        return errorCodeURMU;
    }

    public void setErrorCodeURMU(String errorCodeURMU) {
        this.errorCodeURMU = errorCodeURMU;
    }

    public String getErrorCodePERS() {
        return errorCodePERS;
    }

    public void setErrorCodePERS(String errorCodePERS) {
        this.errorCodePERS = errorCodePERS;
    }

    public String getMessageURMU() {
        return messageURMU;
    }

    public void setMessageURMU(String messageURMU) {
        this.messageURMU = messageURMU;
    }

    public String getMessagePERS() {
        return messagePERS;
    }

    public void setMessagePERS(String messagePERS) {
        this.messagePERS = messagePERS;
    }

    public String getRiskyFlagURMU() {
        return riskyFlagURMU;
    }

    public void setRiskyFlagURMU(String riskyFlagURMU) {
        this.riskyFlagURMU = riskyFlagURMU;
    }

    public String getRiskyFlagPERS() {
        return riskyFlagPERS;
    }

    public void setRiskyFlagPERS(String riskyFlagPERS) {
        this.riskyFlagPERS = riskyFlagPERS;
    }

    public String getRiskyTagURMU() {
        return riskyTagURMU;
    }

    public void setRiskyTagURMU(String riskyTagURMU) {
        this.riskyTagURMU = riskyTagURMU;
    }

    public String getRiskyTagPERS() {
        return riskyTagPERS;
    }

    public void setRiskyTagPERS(String riskyTagPERS) {
        this.riskyTagPERS = riskyTagPERS;
    }

    public String getNormalisedScoreURMU() {
        return normalisedScoreURMU;
    }

    public void setNormalisedScoreURMU(String normalisedScoreURMU) {
        this.normalisedScoreURMU = normalisedScoreURMU;
    }

    public String getNormalisedScorePERS() {
        return normalisedScorePERS;
    }

    public void setNormalisedScorePERS(String normalisedScorePERS) {
        this.normalisedScorePERS = normalisedScorePERS;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "RiskScoreDetails [policyNumber=" + policyNumber + ", entryDate=" + entryDate + ", channel=" + channel
                + ", statusCode=" + statusCode + ", message=" + message + ", score=" + score + "]";
    }


}
