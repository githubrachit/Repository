
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "CkycDownloadResponseDetail")
public class CkycDownloadResponseDetail
{

    @JsonProperty("transactionId")
    private String transactionId;
    @JsonProperty("transactionStatus")
    private String transactionStatus;
    @JsonProperty("ckycPersonalDetail")
    private CkycPersonalDetail ckycPersonalDetail;
    @JsonProperty("ckycIdDetails")
    private List<CkycIdDetails> ckycIdDetails;
    @JsonProperty("ckycRelatedPersonDetails")
    private List<CkycRelatedPersonDetails> ckycRelatedPersonDetails;
    @JsonProperty("ckycImageDetails")
    private List<CkycImageDetail> ckycImageDetails = null;

    private final static long serialVersionUID = 3057831719423822324L;

    @JsonProperty("transactionId")
    public String getTransactionId() {
        return transactionId;
    }

    @JsonProperty("transactionId")
    @XmlElement(name = "TransactionId")
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @JsonProperty("transactionStatus")
    public String getTransactionStatus() {
        return transactionStatus;
    }

    @JsonProperty("transactionStatus")
    @XmlElement(name = "TransactionStatus")
    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    @JsonProperty("ckycPersonalDetail")
    public CkycPersonalDetail getCkycPersonalDetail() {
        return ckycPersonalDetail;
    }

    @JsonProperty("ckycPersonalDetail")
    @XmlElement(name = "CKYCPersonalDetail")
    public void setCkycPersonalDetail(CkycPersonalDetail ckycPersonalDetail) {
        this.ckycPersonalDetail = ckycPersonalDetail;
    }

    @JsonProperty("ckycIdDetails")
    public List<CkycIdDetails> getCkycIdDetails() { return ckycIdDetails; }

    @JsonProperty("ckycIdDetails")
    @XmlElement(name = "CKYCIDDetails")
    public void setCkycIdDetails(List<CkycIdDetails> ckycIdDetails) {
        this.ckycIdDetails = ckycIdDetails;
    }

    @JsonProperty("ckycRelatedPersonDetails")
    public List<CkycRelatedPersonDetails> getCkycRelatedPersonDetails() { return ckycRelatedPersonDetails; }

    @JsonProperty("ckycRelatedPersonDetails")
    @XmlElement(name = "CKYCRelatedPersonDetails")
    public void setCkycRelatedPersonDetails(List<CkycRelatedPersonDetails> ckycRelatedPersonDetails) {
        this.ckycRelatedPersonDetails = ckycRelatedPersonDetails;
    }

    @JsonProperty("ckycImageDetails")
    public List<CkycImageDetail> getCkycImageDetails() {
        return ckycImageDetails;
    }

    @JsonProperty("ckycImageDetails")
    @XmlElement(name = "CKYCImageDetails")
    public void setCkycImageDetails(List<CkycImageDetail> ckycImageDetails) {
        this.ckycImageDetails = ckycImageDetails;
    }


    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        final StringBuilder sb = new StringBuilder("CkycDownloadResponseDetail{");
        sb.append("transactionId='").append(transactionId).append('\'');
        sb.append(", transactionStatus='").append(transactionStatus).append('\'');
        sb.append(", ckycPersonalDetail=").append(ckycPersonalDetail);
        sb.append(", ckycIdDetails=").append(ckycIdDetails);
        sb.append(", ckycRelatedPersonDetails=").append(ckycRelatedPersonDetails);
        sb.append(", ckycImageDetails=").append(ckycImageDetails);
        sb.append('}');
        return sb.toString();
    }

}
