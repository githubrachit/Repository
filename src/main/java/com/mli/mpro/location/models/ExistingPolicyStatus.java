package com.mli.mpro.location.models;

import java.util.List;

import com.mli.mpro.utils.Utility;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "existingPolicyStatus")
public class ExistingPolicyStatus {

	@Id
	private String id;
	private String productId;
	private List<String> baseCoverStatusCode;
	private String productName;
	private List<String> planCodes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public List<String> getBaseCoverStatusCode() {
		return baseCoverStatusCode;
	}

	public void setBaseCoverStatusCode(List<String> baseCoverStatusCode) {
		this.baseCoverStatusCode = baseCoverStatusCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public List<String> getPlanCodes() {
		return planCodes;
	}

	public void setPlanCodes(List<String> planCodes) {
		this.planCodes = planCodes;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "ExistingPolicyStatus [id=" + id + ", productId=" + productId + ", baseCoverStatusCode="
				+ baseCoverStatusCode + ", productName=" + productName + ", planCodes=" + planCodes + "]";
	}

}
