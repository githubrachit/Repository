
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CKYCIdentityDetails")
public class CkycIdentity
{

    @JsonProperty("ckycIdSequence")
    private String ckycIdSequence;
    @JsonProperty("ckycIdType")
    private String ckycIdType;
    @Sensitive(MaskType.CKYC_NUM)
    @JsonProperty("ckycIdNumber")
    private String ckycIdNumber;
    @JsonProperty("ckycIdVerificationStatus")
    private String ckycIdVerificationStatus;

    private final static long serialVersionUID = -5838498655968309704L;

    @JsonProperty("ckycIdSequence")
    public String getCkycIdSequence() {
        return ckycIdSequence;
    }

    @JsonProperty("ckycIdSequence")
    @XmlElement(name = "CKYCIDSequence")
    public void setCkycIdSequence(String ckycIdSequence) {
        this.ckycIdSequence = ckycIdSequence;
    }

    @JsonProperty("ckycIdType")
    public String getCkycIdType() {
        return ckycIdType;
    }

    @JsonProperty("ckycIdType")
    @XmlElement(name = "CKYCIDType")
    public void setCkycIdType(String ckycIdType) {
        this.ckycIdType = ckycIdType;
    }

    @JsonProperty("ckycIdNumber")
    public String getCkycIdNumber() {
        return ckycIdNumber;
    }

    @JsonProperty("ckycIdNumber")
    @XmlElement(name = "CKYCIDNumber")
    public void setCkycIdNumber(String ckycIdNumber) {
        this.ckycIdNumber = ckycIdNumber;
    }

    @JsonProperty("ckycIdVerificationStatus")
    public String getCkycIdVerificationStatus() {
        return ckycIdVerificationStatus;
    }

    @JsonProperty("ckycIdVerificationStatus")
    @XmlElement(name = "CKYCIDVerificationStatus")
    public void setCkycIdVerificationStatus(String ckycIdVerificationStatus) {
        this.ckycIdVerificationStatus = ckycIdVerificationStatus;
    }


    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        final StringBuilder sb = new StringBuilder("CkycIdentity{");
        sb.append("ckycIdSequence='").append(ckycIdSequence).append('\'');
        sb.append(", ckycIdType='").append(ckycIdType).append('\'');
        sb.append(", ckycIdNumber='").append(ckycIdNumber).append('\'');
        sb.append(", ckycIdVerificationStatus='").append(ckycIdVerificationStatus).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
