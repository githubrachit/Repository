package com.mli.mpro.location.models.soaCloudModels.master360ResponseModels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.io.Serializable;
import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "nomineeName",
    "nomineeCliId",
    "nomineeDOB",
    "nomineeSharingPercentage",
    "nomineeRelationship"
})
public class Nominee implements Serializable
{
    @Sensitive(MaskType.NAME)
    @JsonProperty("nomineeName")
    private String nomineeName;
    @JsonProperty("nomineeCliId")
    private String nomineeCliId;
    @Sensitive(MaskType.DOB)
    @JsonProperty("nomineeDOB")
    private String nomineeDOB;
    @JsonProperty("nomineeSharingPercentage")
    private String nomineeSharingPercentage;
    @JsonProperty("nomineeRelationship")
    private String nomineeRelationship;

    @JsonProperty("nomineeName")
    public String getNomineeName() {
        return nomineeName;
    }

    @JsonProperty("nomineeName")
    public void setNomineeName(String nomineeName) {
        this.nomineeName = nomineeName;
    }

    public Nominee withNomineeName(String nomineeName) {
        this.nomineeName = nomineeName;
        return this;
    }

    @JsonProperty("nomineeCliId")
    public String getNomineeCliId() {
        return nomineeCliId;
    }

    @JsonProperty("nomineeCliId")
    public void setNomineeCliId(String nomineeCliId) {
        this.nomineeCliId = nomineeCliId;
    }

    public Nominee withNomineeCliId(String nomineeCliId) {
        this.nomineeCliId = nomineeCliId;
        return this;
    }

    @JsonProperty("nomineeDOB")
    public String getNomineeDOB() {
        return nomineeDOB;
    }

    @JsonProperty("nomineeDOB")
    public void setNomineeDOB(String nomineeDOB) {
        this.nomineeDOB = nomineeDOB;
    }

    public Nominee withNomineeDOB(String nomineeDOB) {
        this.nomineeDOB = nomineeDOB;
        return this;
    }

    @JsonProperty("nomineeSharingPercentage")
    public String getNomineeSharingPercentage() {
        return nomineeSharingPercentage;
    }

    @JsonProperty("nomineeSharingPercentage")
    public void setNomineeSharingPercentage(String nomineeSharingPercentage) {
        this.nomineeSharingPercentage = nomineeSharingPercentage;
    }

    public Nominee withNomineeSharingPercentage(String nomineeSharingPercentage) {
        this.nomineeSharingPercentage = nomineeSharingPercentage;
        return this;
    }

    @JsonProperty("nomineeRelationship")
    public String getNomineeRelationship() {
        return nomineeRelationship;
    }

    @JsonProperty("nomineeRelationship")
    public void setNomineeRelationship(String nomineeRelationship) {
        this.nomineeRelationship = nomineeRelationship;
    }

    public Nominee withNomineeRelationship(String nomineeRelationship) {
        this.nomineeRelationship = nomineeRelationship;
        return this;
    }

	@Override
	public String toString() {

        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }

		return "Nominee [nomineeName=" + nomineeName + ", nomineeCliId=" + nomineeCliId + ", nomineeDOB=" + nomineeDOB
				+ ", nomineeSharingPercentage=" + nomineeSharingPercentage + ", nomineeRelationship="
				+ nomineeRelationship + "]";
	}

  
}
