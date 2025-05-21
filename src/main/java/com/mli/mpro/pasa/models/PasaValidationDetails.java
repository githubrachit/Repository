package com.mli.mpro.pasa.models;

import java.util.List;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "pasaValidationDetails")
public class PasaValidationDetails {

	@JsonProperty("fromAge")
	private double fromAge;
	@JsonProperty("toAge")
	private double toAge;
	@Sensitive(MaskType.AMOUNT)
	@JsonProperty("sumAssured")
	private double sumAssured;
	@JsonProperty("customerClassification")
	private String customerClassification;
	@JsonProperty("channel")
	private String channel;
	@JsonProperty("pasaProducts")
    private List<PasaProducts> pasaProducts;
	public double getFromAge() {
		return fromAge;
	}
	public void setFromAge(double fromAge) {
		this.fromAge = fromAge;
	}
	public double getToAge() {
		return toAge;
	}
	public void setToAge(double toAge) {
		this.toAge = toAge;
	}
	public double getSumAssured() {
		return sumAssured;
	}
	public void setSumAssured(double sumAssured) {
		this.sumAssured = sumAssured;
	}
	public String getCustomerClassification() {
		return customerClassification;
	}
	public void setCustomerClassification(String customerClassification) {
		this.customerClassification = customerClassification;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	public List<PasaProducts> getPasaProducts() {
		return pasaProducts;
	}
	public void setPasaProducts(List<PasaProducts> pasaProducts) {
		this.pasaProducts = pasaProducts;
	}
	@Override
	public String toString() {
		if (Utility.isCalledFromLogs(Thread.currentThread())) {
			return Utility.toString(this);
		}
		return "PasaValidationDetails [fromAge=" + fromAge + ", toAge=" + toAge + ", sumAssured=" + sumAssured
				+ ", customerClassification=" + customerClassification + ", channel=" + channel + ", pasaProducts="
				+ pasaProducts + "]";
	}
	
	
}
