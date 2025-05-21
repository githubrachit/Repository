package com.mli.mpro.location.models;

import com.mli.mpro.location.models.RecommendedFunds.Aggressive;
import com.mli.mpro.location.models.RecommendedFunds.Balanced;
import com.mli.mpro.location.models.RecommendedFunds.Conservative;
import com.mli.mpro.location.models.RecommendedFunds.VeryAggressive;
import com.mli.mpro.utils.Utility;

public class RecommendedFund {

	private VeryAggressive veryAggressive;
	private Aggressive aggressive;
	private Conservative conservative;
	private Balanced balanced;

	public VeryAggressive getVeryAggressive() {
		return veryAggressive;
	}

	public void setVeryAggressive(VeryAggressive veryAggressive) {
		this.veryAggressive = veryAggressive;
	}

	public Aggressive getAggressive() {
		return aggressive;
	}

	public void setAggressive(Aggressive aggressive) {
		this.aggressive = aggressive;
	}

	public Conservative getConservative() {
		return conservative;
	}

	public void setConservative(Conservative conservative) {
		this.conservative = conservative;
	}

	public Balanced getBalanced() {
		return balanced;
	}

	public void setBalanced(Balanced balanced) {
		this.balanced = balanced;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "RecommendedFund{" +
				"veryAggressive=" + veryAggressive +
				", aggressive=" + aggressive +
				", conservative=" + conservative +
				", balanced=" + balanced +
				'}';
	}
}
