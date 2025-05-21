package com.mli.mpro.location.models;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class RequestPayload {
    private String continent;
	@Sensitive(MaskType.ADDRESS)
    private String country;
	@Sensitive(MaskType.ADDRESS)
    private String state;
	@Sensitive(MaskType.ADDRESS)
    private String city;
    private String type;
    private Long transactionId;
    private String agentId;
    private String branchCd;
    private ProposalDetails proposalDetails;
	@Sensitive(MaskType.PINCODE)
    private String pincode;
    public RequestPayload() {

    }

    public RequestPayload(String country, String state, String city, String continent, String branchCd) {
	super();
	this.country = country;
	this.state = state;
	this.city = city;
	this.continent = continent;
	this.branchCd = branchCd;
    }

    public String getContinent() {
	return continent;
    }

    public void setContinent(String continent) {
	this.continent = continent;
    }

    public String getCountry() {
	return country;
    }

    public void setCountry(String country) {
	this.country = country;
    }

    public String getState() {
	return state;
    }

    public void setState(String state) {
	this.state = state;
    }

    public String getCity() {
	return city;
    }

    public void setCity(String city) {
	this.city = city;
    }
    

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public ProposalDetails getProposalDetails() {
		return proposalDetails;
	}

	public void setProposalDetails(ProposalDetails proposalDetails) {
		this.proposalDetails = proposalDetails;
	}

	public String getBranchCd() {
		return branchCd;
	}

	public void setBranchCd(String branchCd) {
		this.branchCd = branchCd;
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
		return "RequestPayload [continent=" + continent + ", country=" + country + ", state=" + state + ", city=" + city
				+ ", type=" + type + ", transactionId=" + transactionId + ", agentId=" + agentId + ", " +
				"proposalDetails=" + proposalDetails + "branchCd=" + branchCd + ", pincode=" + pincode + "]";
	}
}