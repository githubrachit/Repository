package com.mli.mpro.location.models;

import java.util.List;

import com.mli.mpro.utils.Utility;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document(collection = "allFunds")
public class AllFund {
	@Id
	private String id;
	private String type;
	private String productId;
	private String productName;
	private AllFunds allFunds;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public AllFunds getAllFunds() {
		return allFunds;
	}

	public void setAllFunds(AllFunds allFunds) {
		this.allFunds = allFunds;
	}

	public static class AllFunds {
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
		return "AllFund{" +
				"id='" + id + '\'' +
				", type='" + type + '\'' +
				", productId='" + productId + '\'' +
				", productName='" + productName + '\'' +
				", allFunds=" + allFunds +
				'}';
	}
}
