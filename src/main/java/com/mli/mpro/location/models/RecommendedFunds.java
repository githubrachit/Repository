package com.mli.mpro.location.models;

import java.util.List;

import com.mli.mpro.utils.Utility;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document(collection = "recommendedFunds")
public class RecommendedFunds {

	@Id
	private String id;
	private String type;
	private String productId;
	private String productName;
	private VeryAggressive veryAggressive;
	private Aggressive aggressive;
	private Conservative conservative;
	private Balanced balanced;

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

	public static class VeryAggressive {
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

	public static class Aggressive {
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

	public static class Conservative {
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

	public static class Balanced {
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
		return "RecommendedFunds{" +
				"id='" + id + '\'' +
				", type='" + type + '\'' +
				", productId='" + productId + '\'' +
				", productName='" + productName + '\'' +
				", veryAggressive=" + veryAggressive +
				", aggressive=" + aggressive +
				", conservative=" + conservative +
				", balanced=" + balanced +
				'}';
	}
}
