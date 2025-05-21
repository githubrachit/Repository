package com.mli.mpro.location.models;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class PincodeMasterResponseModel {

	@Sensitive(MaskType.PINCODE)
	private String pincode;
	@Sensitive(MaskType.ADDRESS)
	private String city;
	private String id;
	@Sensitive(MaskType.ADDRESS)
	private String state;

	public PincodeMasterResponseModel() {
		super();
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		if (Utility.isCalledFromLogs(Thread.currentThread())) {
			return Utility.toString(this);
		}
		return "PincodeMasterResponseDto [pincode=" + pincode + ", city=" + city + ", id=" + id + ", state=" + state
				+ "]";
	}

}
