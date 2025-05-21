package com.mli.mpro.location.models;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.ccms.model.SmsDetails;
import com.mli.mpro.email.models.RequestPayload;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.sms.requestmodels.OutputResponse;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class NotificationSender {
    public RequestPayload soaEmailPayload;
    @Sensitive(MaskType.EMAIL)
    public String emailBody;
    public com.mli.mpro.sms.requestmodels.OutputResponse inputRequest;



    private SmsDetails smsDetails;
    private com.mli.mpro.sms.models.RequestPayload soaSmsPayload;
    ProposalDetails proposalDetails;


    //** Setters
    public NotificationSender setSoaEmailPayload(RequestPayload soaEmailPayload) {
        this.soaEmailPayload = soaEmailPayload;
        return this;
    }

    public NotificationSender setEmailBody(String emailBody) {
        this.emailBody = emailBody;
        return this;
    }

    public NotificationSender setInputRequest(OutputResponse inputRequest) {
        this.inputRequest = inputRequest;
        return this;
    }

    public NotificationSender setSmsDetails(SmsDetails smsDetails) {
        this.smsDetails = smsDetails;
        return this;
    }

    public NotificationSender setSoaSmsPayload(com.mli.mpro.sms.models.RequestPayload soaSmsPayload) {
        this.soaSmsPayload = soaSmsPayload;
        return this;
    }

    public NotificationSender setProposalDetails(ProposalDetails proposalDetails) {
        this.proposalDetails = proposalDetails;
        return this;
    }

    //** Getters
    public RequestPayload getSoaEmailPayload() {
        return soaEmailPayload;
    }

    public String getEmailBody() {
        return emailBody;
    }

    public Object getInputRequest() {
        return inputRequest;
    }

    public SmsDetails getSmsDetails() {
        return smsDetails;
    }

    public com.mli.mpro.sms.models.RequestPayload getSoaSmsPayload() {
        return soaSmsPayload;
    }

    public ProposalDetails getProposalDetails() {
        return proposalDetails;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "NotificationSender{" +
                "soaEmailPayload=" + soaEmailPayload +
                ", emailBody='" + emailBody + '\'' +
                ", inputRequest=" + inputRequest +
                ", smsDetails=" + smsDetails +
                ", soaSmsPayload=" + soaSmsPayload +
                ", proposalDetails=" + proposalDetails +
                '}';
    }
}
