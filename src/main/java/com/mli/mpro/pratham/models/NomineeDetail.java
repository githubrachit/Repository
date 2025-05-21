
package com.mli.mpro.pratham.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "nomineeTitle", "nomineeFirstname", "nomineeMiddlename", "nomineeLastname", "nomineeDob",
		"nomineeGender", "relationshipWithNominee", "nomineePercentageShare" })
public class NomineeDetail {

	@JsonProperty("nomineeTitle")
	private String nomineeTitle;
	@Sensitive(MaskType.FIRST_NAME)
	@JsonProperty("nomineeFirstname")
	private String nomineeFirstname;
	@Sensitive(MaskType.MIDDLE_NAME)
	@JsonProperty("nomineeMiddlename")
	private String nomineeMiddlename;
	@Sensitive(MaskType.LAST_NAME)
	@JsonProperty("nomineeLastname")
	private String nomineeLastname;
	@Sensitive(MaskType.DOB)
	@JsonProperty("nomineeDob")
	private String nomineeDob;
	@Sensitive(MaskType.GENDER)
	@JsonProperty("nomineeGender")
	private String nomineeGender;
	@JsonProperty("relationshipWithNominee")
	private String relationshipWithNominee;
	@JsonProperty("nomineePercentageShare")
	private String nomineePercentageShare;

	@JsonProperty("nomineeTitle")
	public String getNomineeTitle() {
		return nomineeTitle;
	}

	@JsonProperty("nomineeTitle")
	public void setNomineeTitle(String nomineeTitle) {
		this.nomineeTitle = nomineeTitle;
	}

	@JsonProperty("nomineeFirstname")
	public String getNomineeFirstname() {
		return nomineeFirstname;
	}

	@JsonProperty("nomineeFirstname")
	public void setNomineeFirstname(String nomineeFirstname) {
		this.nomineeFirstname = nomineeFirstname;
	}

	@JsonProperty("nomineeMiddlename")
	public String getNomineeMiddlename() {
		return nomineeMiddlename;
	}

	@JsonProperty("nomineeMiddlename")
	public void setNomineeMiddlename(String nomineeMiddlename) {
		this.nomineeMiddlename = nomineeMiddlename;
	}

	@JsonProperty("nomineeLastname")
	public String getNomineeLastname() {
		return nomineeLastname;
	}

	@JsonProperty("nomineeLastname")
	public void setNomineeLastname(String nomineeLastname) {
		this.nomineeLastname = nomineeLastname;
	}

	@JsonProperty("nomineeDob")
	public String getNomineeDob() {
		return nomineeDob;
	}

	@JsonProperty("nomineeDob")
	public void setNomineeDob(String nomineeDob) {
		this.nomineeDob = nomineeDob;
	}

	@JsonProperty("nomineeGender")
	public String getNomineeGender() {
		return nomineeGender;
	}

	@JsonProperty("nomineeGender")
	public void setNomineeGender(String nomineeGender) {
		this.nomineeGender = nomineeGender;
	}

	@JsonProperty("relationshipWithNominee")
	public String getRelationshipWithNominee() {
		return relationshipWithNominee;
	}

	@JsonProperty("relationshipWithNominee")
	public void setRelationshipWithNominee(String relationshipWithNominee) {
		this.relationshipWithNominee = relationshipWithNominee;
	}

	@JsonProperty("nomineePercentageShare")
	public String getNomineePercentageShare() {
		return nomineePercentageShare;
	}

	@JsonProperty("nomineePercentageShare")
	public void setNomineePercentageShare(String nomineePercentageShare) {
		this.nomineePercentageShare = nomineePercentageShare;
	}

	@Override
	public String toString() {
		if (Utility.isCalledFromLogs(Thread.currentThread())) {
			return Utility.toString(this);
		}
		return "NomineeDetail [nomineeTitle=" + nomineeTitle + ", nomineeFirstname=" + nomineeFirstname
				+ ", nomineeMiddlename=" + nomineeMiddlename + ", nomineeLastname=" + nomineeLastname + ", nomineeDob="
				+ nomineeDob + ", nomineeGender=" + nomineeGender + ", relationshipWithNominee="
				+ relationshipWithNominee + ", nomineePercentageShare=" + nomineePercentageShare + "]";
	}

}
