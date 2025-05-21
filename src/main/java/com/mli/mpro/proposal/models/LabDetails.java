package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "labId", "name", "address", "state" })
public class LabDetails {

    @JsonProperty("labId")
    private String labId;
    @Sensitive(MaskType.NAME)
    @JsonProperty("name")
    private String name;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("address")
    private String address;

    public LabDetails() {
	super();
    }

    @JsonProperty("labId")
    public String getLabId() {
	return labId;
    }

    @JsonProperty("labId")
    public void setLabId(String labId) {
	this.labId = labId;
    }

    @JsonProperty("name")
    public String getName() {
	return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
	this.name = name;
    }

    @JsonProperty("address")
    public String getAddress() {
	return address;
    }

    @JsonProperty("address")
    public void setAddress(String address) {
	this.address = address;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "LabDetails [labId=" + labId + ", name=" + name + ", address=" + address + "]";
    }

}
