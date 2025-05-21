package com.mli.mpro.location.models;

import java.util.List;

import com.mli.mpro.location.services.BranchCodeService;
import com.mli.mpro.proposal.models.PensionPlans;
import com.mli.mpro.utils.Utility;

public class PensionPlansPayload {
	private List<PensionPlans> pensionPlansList;

	public List<PensionPlans> getPensionPlansList() {
		return pensionPlansList;
	}

	public void setPensionPlansList(List<PensionPlans> pensionPlansList) {
		this.pensionPlansList = pensionPlansList;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "PensionPlansPayload{" +
				"pensionPlansList=" + pensionPlansList +
				'}';
	}
}
