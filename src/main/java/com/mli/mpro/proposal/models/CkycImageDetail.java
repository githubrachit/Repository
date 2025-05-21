
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CKYCImage")
public class CkycImageDetail
{

    @JsonProperty("ckycImageSequence")
    private String ckycImageSequence;
    @JsonProperty("ckycImageExtension")
    private String ckycImageExtension;
    @JsonProperty("ckycImageType")
    private String ckycImageType;
    @JsonProperty("ckycImageGlobalorLocal")
    private String ckycImageGlobalorLocal;
    @JsonProperty("ckycImageBranch")
    private String ckycImageBranch;
    @JsonProperty("ckycImageData")
    private String ckycImageData;
    @JsonProperty("cKYCImageData")
    private String cKYCImageData;

    private final static long serialVersionUID = 482814912455993132L;

    @JsonProperty("ckycImageSequence")
    public String getCkycImageSequence() {
        return ckycImageSequence;
    }

    @JsonProperty("ckycImageSequence")
    @XmlElement(name = "CKYCImageSequence")
    public void setCkycImageSequence(String ckycImageSequence) {
        this.ckycImageSequence = ckycImageSequence;
    }

    @JsonProperty("ckycImageExtension")
    public String getCkycImageExtension() {
        return ckycImageExtension;
    }

    @JsonProperty("ckycImageExtension")
    @XmlElement(name = "CKYCImageExtension")
    public void setCkycImageExtension(String ckycImageExtension) {
        this.ckycImageExtension = ckycImageExtension;
    }

    @JsonProperty("ckycImageType")
    public String getCkycImageType() {
        return ckycImageType;
    }

    @JsonProperty("ckycImageType")
    @XmlElement(name = "CKYCImageType")
    public void setCkycImageType(String ckycImageType) {
        this.ckycImageType = ckycImageType;
    }

    @JsonProperty("ckycImageGlobalorLocal")
    public String getCkycImageGlobalorLocal() {
        return ckycImageGlobalorLocal;
    }

    @JsonProperty("ckycImageGlobalorLocal")
    @XmlElement(name = "CKYCImageGlobalorLocal")
    public void setCkycImageGlobalorLocal(String ckycImageGlobalorLocal) {
        this.ckycImageGlobalorLocal = ckycImageGlobalorLocal;
    }

    @JsonProperty("ckycImageBranch")
    public String getCkycImageBranch() {
        return ckycImageBranch;
    }

    @JsonProperty("ckycImageBranch")
    @XmlElement(name = "CKYCImageBranch")
    public void setCkycImageBranch(String ckycImageBranch) {
        this.ckycImageBranch = ckycImageBranch;
    }

    @JsonProperty("ckycImageData")
    public String getCkycImageData() {
        return ckycImageData;
    }

    @JsonProperty("ckycImageData")
    @XmlElement(name = "CKYCImageData")
    public void setCkycImageData(String ckycImageData) {
        this.ckycImageData = ckycImageData;
    }

    @JsonProperty("cKYCImageData")
    public String getCKYCImageData() {
        return cKYCImageData;
    }

    @JsonProperty("cKYCImageData")
    public void setCKYCImageData(String cKYCImageData) {
        this.cKYCImageData = cKYCImageData;
    }


    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        final StringBuilder sb = new StringBuilder("CkycImageDetail{");
        sb.append("ckycImageSequence='").append(ckycImageSequence).append('\'');
        sb.append(", ckycImageExtension='").append(ckycImageExtension).append('\'');
        sb.append(", ckycImageType='").append(ckycImageType).append('\'');
        sb.append(", ckycImageGlobalorLocal='").append(ckycImageGlobalorLocal).append('\'');
        sb.append(", ckycImageBranch=").append(ckycImageBranch);
        sb.append(", ckycImageData='").append(ckycImageData).append('\'');
        sb.append(", cKYCImageData='").append(cKYCImageData).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
