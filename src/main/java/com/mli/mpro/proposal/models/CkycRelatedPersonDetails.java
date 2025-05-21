
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CKYCRelatedPersonDetails")
public class CkycRelatedPersonDetails
{

    @JsonProperty("ckycRelatedPerson")
    private CkycRelatedPerson ckycRelatedPerson;

    private final static long serialVersionUID = 9181936337349243727L;

    @JsonProperty("ckycRelatedPerson")
    public CkycRelatedPerson getCkycRelatedPerson() {
        return ckycRelatedPerson;
    }

    @JsonProperty("ckycRelatedPerson")
    @XmlElement(name = "CKYCRelatedPerson")
    public void setCkycRelatedPerson(CkycRelatedPerson ckycRelatedPerson) {
        this.ckycRelatedPerson = ckycRelatedPerson;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        final StringBuilder sb = new StringBuilder("CkycRelatedPersonDetails{");
        sb.append("ckycRelatedPerson=").append(ckycRelatedPerson);
        sb.append('}');
        return sb.toString();
    }

}
