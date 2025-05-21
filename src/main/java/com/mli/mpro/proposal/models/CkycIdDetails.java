
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CKYCIDDetails")
public class CkycIdDetails
{

    @JsonProperty("ckycIdentity")
    private CkycIdentity ckycIdentity;

    private final static long serialVersionUID = 8376319086577888916L;

    @JsonProperty("ckycIdentity")
    public CkycIdentity getCkycIdentity() {
        return ckycIdentity;
    }

    @JsonProperty("ckycIdentity")
    @XmlElement(name = "CKYCIdentityDetails")
    public void setCkycIdentity(CkycIdentity ckycIdentity) {
        this.ckycIdentity = ckycIdentity;
    }


    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        final StringBuilder sb = new StringBuilder("CkycIdDetails{");
        sb.append("ckycIdentity=").append(ckycIdentity);
        sb.append('}');
        return sb.toString();
    }

}
