package com.mli.mpro.routingposv.models;

import com.mli.mpro.utils.Utility;

public class RoutingInfoData {

    private String productId;
	private String riskyTagURMU;
	private String riskyTagPERS;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getRiskyTagURMU() {
		return riskyTagURMU;
	}
	public void setRiskyTagURMU(String riskyTagURMU) {
		this.riskyTagURMU = riskyTagURMU;
	}
	public String getRiskyTagPERS() {
		return riskyTagPERS;
	}
	public void setRiskyTagPERS(String riskyTagPERS) {
		this.riskyTagPERS = riskyTagPERS;
	}
	@Override
	public String toString() {
		if (Utility.isCalledFromLogs(Thread.currentThread())) {
			return Utility.toString(this);
		}
		return "RequestData [productId=" + productId + ", riskyTagURMU=" + riskyTagURMU + ", riskyTagPERS="
				+ riskyTagPERS + "]";
	}

	

}
