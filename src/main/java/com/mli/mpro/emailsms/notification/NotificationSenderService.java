package com.mli.mpro.emailsms.notification;

import com.mli.mpro.ccms.model.SmsDetails;
import com.mli.mpro.ccms.repository.SmsRepository;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.email.models.EmailDetails;
import com.mli.mpro.location.models.NotificationSender;
import com.mli.mpro.productRestriction.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;


@Component
public abstract class NotificationSenderService {

    public NotificationSenderService getInstance(boolean ccmsFlow){
        if(ccmsFlow){
            return new CCMSNotificationSenderServiceImpl();
        }
        return new SoaNotificationSenderServiceImpl();
    }

    public abstract String sendEmail(Object inputRequest) throws UserHandledException;
    public abstract String aSyncSendEmail(Object inputRequest) throws UserHandledException;

    public abstract String sendSms() throws UserHandledException;
    public abstract String aSyncSendSms() throws UserHandledException;
    public abstract void initNotificationSender(NotificationSender notificationSender);

    public boolean isCCMSFlow(SmsDetails smsDetails) {
        if(smsDetails == null) return false;
        return !CollectionUtils.isEmpty(smsDetails.getDocId()) && FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.CCMS_SMS_FEATURE_FLAG); //return true if CCMS flow
    }

}
