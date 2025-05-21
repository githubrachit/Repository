package com.mli.mpro.proposal.models;

import com.mli.mpro.utils.Utility;

public class ProposalFormRuleDetails {
    private String kickoutMsg;
    private String resultFlag;

    public ProposalFormRuleDetails() {
	super();
    }

    public ProposalFormRuleDetails(String kickoutMsg, String resultFlag) {
	super();
	this.kickoutMsg = kickoutMsg;
	this.resultFlag = resultFlag;
    }

    public String getKickoutMsg() {
	return kickoutMsg;
    }

    public void setKickoutMsg(String kickoutMsg) {
	this.kickoutMsg = kickoutMsg;
    }

    public String getResultFlag() {
	return resultFlag;
    }

    public void setResultFlag(String resultFlag) {
	this.resultFlag = resultFlag;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "ProposalFormDetails [kickoutMsg=" + kickoutMsg + ", resultFlag=" + resultFlag + "]";
    }

}
