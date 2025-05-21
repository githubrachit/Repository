package com.mli.mpro.emailsms.notification;

import com.mli.mpro.ccms.model.Data;
import com.mli.mpro.ccms.model.DocInfo;
import com.mli.mpro.ccms.model.SmsDetails;
import com.mli.mpro.ccms.service.CcmsEmailSmsService;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.location.models.NotificationSender;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.sms.models.RequestPayload;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CCMSNotificationSenderServiceImpl extends NotificationSenderService {


    private DocInfo docInfo;
    private Data ccmsData;
    private com.mli.mpro.sms.requestmodels.OutputResponse inputRequest;
    private ProposalDetails proposalDetails;


    public CcmsEmailSmsService getCcmsEmailSmsService(){
        return ApplicationContextProvider.getApplicationContext().getBean(CcmsEmailSmsService.class);
    }

    @Override
    public String sendEmail(Object inputRequest) throws UserHandledException {
        return null;
    }

    @Override
    public String aSyncSendEmail(Object inputRequest) throws UserHandledException {
        return null;
    }

    @Override
    public String sendSms() throws UserHandledException {
        return getCcmsEmailSmsService().nearRealTimeNotificationGeneration(docInfo,ccmsData);
    }

    @Override
    public String aSyncSendSms() throws UserHandledException {
        return getCcmsEmailSmsService().nearRealTimeNotificationGenerationAsync(docInfo,ccmsData);
    }

    public void initNotificationSender(NotificationSender notificationSender){
        this.inputRequest = notificationSender.inputRequest;
        this.proposalDetails = notificationSender.getProposalDetails();
        com.mli.mpro.sms.models.RequestPayload smsRequestPayload = notificationSender.getSoaSmsPayload();
        this.ccmsData = new Data(smsRequestPayload);
        SmsDetails smsDetails = notificationSender.getSmsDetails();
        docInfo = new DocInfo();
        docInfo.setDocId(getDocId(smsDetails, smsRequestPayload));
        docInfo.setProductType(proposalDetails.getProductDetails().get(0).getProductType());
        docInfo.setDocVersion(AppConstants.DOC_VERSION);
        docInfo.setDate(getCurrentDate());

    }

    private String getDocId(SmsDetails smsDetails, RequestPayload smsRequestPayload) {
        if(AppConstants.PROPOSAL_REJECTION.equalsIgnoreCase(smsDetails.getDocumentType()) && StringUtils.isNotEmpty(smsRequestPayload.getCustomerName())){
            return smsDetails.getDocId().get(1);
        }
        return smsDetails.getDocId().get(0);
    }

    public String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat(AppConstants.DD_MMM_YYYY_DATE_FORMAT);
        return dateFormat.format(new Date());
    }
}
