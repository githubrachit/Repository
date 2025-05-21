package com.mli.mpro.proposal.models;

import com.mli.mpro.utils.Utility;

public class BureauResponse {

    private String scoreName;
    private String scoreDate;
    private String score;

    public BureauResponse() {

    }

    public BureauResponse(String scoreName, String scoreDate, String score) {
	super();
	this.scoreName = scoreName;
	this.scoreDate = scoreDate;
	this.score = score;
    }

    public String getScoreName() {
	return scoreName;
    }

    public void setScoreName(String scoreName) {
	this.scoreName = scoreName;
    }

    public String getScoreDate() {
	return scoreDate;
    }

    public void setScoreDate(String scoreDate) {
	this.scoreDate = scoreDate;
    }

    public String getScore() {
	return score;
    }

    public void setScore(String score) {
	this.score = score;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
           return Utility.toString(this);
        }
	return "BureauResponse [scoreName=" + scoreName + ", scoreDate=" + scoreDate + ", score=" + score + "]";
    }

}
