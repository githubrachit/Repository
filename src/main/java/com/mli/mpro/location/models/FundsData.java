package com.mli.mpro.location.models;

import com.mli.mpro.utils.Utility;

import java.util.List;

public class FundsData {
	private String productId;
	private String type;
	private AllFundsModel allFunds;
	private RecommendedFund recommendedFund;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public AllFundsModel getAllFunds() {
		return allFunds;
	}

	public void setAllFunds(AllFundsModel allFunds) {
		this.allFunds = allFunds;
	}

	public RecommendedFund getRecommendedFund() {
		return recommendedFund;
	}

	public void setRecommendedFund(RecommendedFund recommendedFund) {
		this.recommendedFund = recommendedFund;
	}

	public static class AllFundsModel {
		private List<String> funds;
		private List<String> strategies;

		public List<String> getFunds() {
			return funds;
		}

		public void setFunds(List<String> funds) {
			this.funds = funds;
		}

		public List<String> getStrategies() {
			return strategies;
		}

		public void setStrategies(List<String> strategies) {
			this.strategies = strategies;
		}

	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "FundsData{" +
				"productId='" + productId + '\'' +
				", type='" + type + '\'' +
				", allFunds=" + allFunds +
				", recommendedFund=" + recommendedFund +
				'}';
	}
}
