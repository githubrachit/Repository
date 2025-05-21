package com.mli.mpro.yblAccount.models;

import java.util.List;

import com.mli.mpro.utils.Utility;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "YblProductAccount")
public class yblAccountDetails {
	
	private String type;
	private List<String> NRE;
	private List<String> FCNR;
	private List<String> SWIFT;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<String> getNRE() {
		return NRE;
	}
	public void setNRE(List<String> nRE) {
		NRE = nRE;
	}
	public List<String> getFCNR() {
		return FCNR;
	}
	public void setFCNR(List<String> fCNR) {
		FCNR = fCNR;
	}
	public List<String> getSWIFT() {
		return SWIFT;
	}
	public void setSWIFT(List<String> sWIFT) {
		SWIFT = sWIFT;
	}
	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "GstAccountDetails [type=" + type + ", NRE=" + NRE + ", FCNR=" + FCNR + ", SWIFT=" + SWIFT + "]";
	}
	
}
