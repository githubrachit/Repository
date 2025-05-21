package com.mli.mpro.proposal.models;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.Utility;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pensionPlans")
public class PensionPlans {
	
	@Id
    private String id;

	private String planId;

	private String planName;

	private String planNamePlnDoc;

	public PensionPlans() {
	}

	public PensionPlans(String planId, String planName, String planNamePlnDoc) {
		super();
		this.planId = planId;
		this.planName = planName;
		this.planNamePlnDoc = planNamePlnDoc;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getPlanNamePlnDoc() {
		return planNamePlnDoc;
	}

	public void setPlanNamePlnDoc(String planNamePlnDoc) {
		this.planNamePlnDoc = planNamePlnDoc;
	}

	@Override
	public String toString() {
		if (Utility.isCalledFromLogs(Thread.currentThread())) {
			return Utility.toString(this);
		}
		return "PensionPlans [planId=" + planId + ", planName=" + planName + ", planNamePlnDoc=" + planNamePlnDoc + "]";
	}

}
