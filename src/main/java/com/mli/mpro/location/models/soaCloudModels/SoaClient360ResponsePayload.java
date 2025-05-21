package com.mli.mpro.location.models.soaCloudModels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mli.mpro.utils.Utility;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SoaClient360ResponsePayload {
    private List<AccountDetail> accountDetail;
    private List<BaseAddDetail> baseAddDetail;
    private List<ContactDetail> contactDetail;
    private List<CreditCardDetail> creditCardDetail;
    private IdentifiableInfo identifiableInfo;
    private PersonalDetail personalDetail;
    private List<UnderwritingClient> underwritingClient;
    private ClntMsres clntMsres;
    private List<ClientPolicyRelationship> clientPolicyRelationship;
    private List<RequirementDetails> reqtDtls;
    private List<PayerDtls> payerDtls;
    private List<InsuredPolicyIds> insuredPolicyIds;
    private UWUserDetails uWUserDetails;
    private FatcaDetails fatcaDetails;
    private InsuredClientIdsRelated insuredClientIdsRelated;

    public List<AccountDetail> getAccountDetail() {
        return accountDetail;
    }

    public void setAccountDetail(List<AccountDetail> accountDetail) {
        this.accountDetail = accountDetail;
    }

    public List<BaseAddDetail> getBaseAddDetail() {
        return baseAddDetail;
    }

    public void setBaseAddDetail(List<BaseAddDetail> baseAddDetail) {
        this.baseAddDetail = baseAddDetail;
    }

    public List<ContactDetail> getContactDetail() {
        return contactDetail;
    }

    public void setContactDetail(List<ContactDetail> contactDetail) {
        this.contactDetail = contactDetail;
    }

    public List<CreditCardDetail> getCreditCardDetail() {
        return creditCardDetail;
    }

    public void setCreditCardDetail(List<CreditCardDetail> creditCardDetail) {
        this.creditCardDetail = creditCardDetail;
    }

    public IdentifiableInfo getIdentifiableInfo() {
        return identifiableInfo;
    }

    public void setIdentifiableInfo(IdentifiableInfo identifiableInfo) {
        this.identifiableInfo = identifiableInfo;
    }

    public PersonalDetail getPersonalDetail() {
        return personalDetail;
    }

    public void setPersonalDetail(PersonalDetail personalDetail) {
        this.personalDetail = personalDetail;
    }

    public List<UnderwritingClient> getUnderwritingClient() {
        return underwritingClient;
    }

    public void setUnderwritingClient(List<UnderwritingClient> underwritingClient) {
        this.underwritingClient = underwritingClient;
    }

    public ClntMsres getClntMsres() {
        return clntMsres;
    }

    public void setClntMsres(ClntMsres clntMsres) {
        this.clntMsres = clntMsres;
    }

    public List<ClientPolicyRelationship> getClientPolicyRelationship() {
        return clientPolicyRelationship;
    }

    public void setClientPolicyRelationship(List<ClientPolicyRelationship> clientPolicyRelationship) {
        this.clientPolicyRelationship = clientPolicyRelationship;
    }

    public List<RequirementDetails> getReqtDtls() {
        return reqtDtls;
    }

    public void setReqtDtls(List<RequirementDetails> reqtDtls) {
        this.reqtDtls = reqtDtls;
    }

    public List<PayerDtls> getPayerDtls() {
        return payerDtls;
    }

    public void setPayerDtls(List<PayerDtls> payerDtls) {
        this.payerDtls = payerDtls;
    }

    public List<InsuredPolicyIds> getInsuredPolicyIds() {
        return insuredPolicyIds;
    }

    public void setInsuredPolicyIds(List<InsuredPolicyIds> insuredPolicyIds) {
        this.insuredPolicyIds = insuredPolicyIds;
    }

    public UWUserDetails getuWUserDetails() {
        return uWUserDetails;
    }

    public void setuWUserDetails(UWUserDetails uWUserDetails) {
        this.uWUserDetails = uWUserDetails;
    }

    public FatcaDetails getFatcaDetails() {
        return fatcaDetails;
    }

    public void setFatcaDetails(FatcaDetails fatcaDetails) {
        this.fatcaDetails = fatcaDetails;
    }

    public InsuredClientIdsRelated getInsuredClientIdsRelated() {
        return insuredClientIdsRelated;
    }

    public void setInsuredClientIdsRelated(InsuredClientIdsRelated insuredClientIdsRelated) {
        this.insuredClientIdsRelated = insuredClientIdsRelated;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "SoaClient360ResponsePayload{" +
                "accountDetail=" + accountDetail +
                ", baseAddDetail=" + baseAddDetail +
                ", contactDetail=" + contactDetail +
                ", creditCardDetail=" + creditCardDetail +
                ", identifiableInfo=" + identifiableInfo +
                ", personalDetail=" + personalDetail +
                ", underwritingClient=" + underwritingClient +
                ", clntMsres=" + clntMsres +
                ", clientPolicyRelationship=" + clientPolicyRelationship +
                ", reqtDtls=" + reqtDtls +
                ", payerDtls=" + payerDtls +
                ", insuredPolicyIds=" + insuredPolicyIds +
                ", uWUserDetails=" + uWUserDetails +
                ", fatcaDetails=" + fatcaDetails +
                ", insuredClientIdsRelated=" + insuredClientIdsRelated +
                '}';
    }
}
