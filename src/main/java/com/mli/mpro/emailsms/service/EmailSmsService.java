package com.mli.mpro.emailsms.service;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.email.models.RequestPayload;
import com.mli.mpro.productRestriction.models.ProductRestrictionPayload;
import com.mli.mpro.proposal.models.ProposalDetails;


public interface EmailSmsService {

    void sendEmailAndSmsForPos(ProductRestrictionPayload productRestrictionPayload) throws UserHandledException;
    String sendEmail(RequestPayload payload, String emailBody) throws UserHandledException;
    String sendSMS(com.mli.mpro.sms.models.RequestPayload payload, ProposalDetails proposalDetails);
}
