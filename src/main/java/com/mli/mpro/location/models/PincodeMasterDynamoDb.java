package com.mli.mpro.location.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@DynamoDBTable(tableName = "PincodeMaster")
public class PincodeMasterDynamoDb {


	@Sensitive(MaskType.ADDRESS)
	@DynamoDBAttribute(attributeName = "city")
	private String city;
	@Sensitive(MaskType.ADDRESS)
	@DynamoDBAttribute(attributeName = "state")
	private String state;
	@Sensitive(MaskType.PINCODE)
	@DynamoDBHashKey(attributeName = "pincode")
	private String pincode;

	public PincodeMasterDynamoDb() {
		super();
	}

	public PincodeMasterDynamoDb(String city, String state, String pincode) {
		super();
		this.city = city;
		this.state = state;
		this.pincode = pincode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	@Override
	public String toString() {
		if (Utility.isCalledFromLogs(Thread.currentThread())) {
			return Utility.toString(this);
		}
		return "PincodeMasterDynamoDb [city=" + city + ", state=" + state + ", pincode=" + pincode + "]";
	}
}
