package com.mli.mpro.location.clientAllPolicyDetails.model;

import com.mli.mpro.location.models.clientPolicyDetailsResponseModels.OutputResponse;
import com.mli.mpro.utils.Utility;

public class OutputResult {
	
	private OutputResponse result;

	public OutputResponse getResult() {
		return result;
	}

	public void setResult(OutputResponse result) {
		this.result = result;
	}

	@Override
	public String toString() {
		if (Utility.isCalledFromLogs(Thread.currentThread())) {
			return Utility.toString(this);
		}
		return "OutputResult [result=" + result + "]";
	}
}
