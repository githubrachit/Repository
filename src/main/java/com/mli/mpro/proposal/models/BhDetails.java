package com.mli.mpro.proposal.models;

import com.mli.mpro.utils.Utility;

import java.time.LocalDateTime;

public class BhDetails {
    private String bhApproval;
    private String bhApprovalTime;
    private String bhOasApprovalLinkStatus;
    private String metOrSpokeDateForBh;
    private String phoneCallOrPersonMeetForBh;
    private String isApplicantExistingCustomerForBh;
    private String reminderEmailSms;
    private String lastReminderDateTime;

    public String getBhApproval() {
        return bhApproval;
    }

    public void setBhApproval(String bhApproval) {
        this.bhApproval = bhApproval;
    }

    public String getBhApprovalTime() {
        return bhApprovalTime;
    }

    public void setBhApprovalTime(String bhApprovalTime) {
        this.bhApprovalTime = bhApprovalTime;
    }

    public String getBhOasApprovalLinkStatus() {
        return bhOasApprovalLinkStatus;
    }

    public void setBhOasApprovalLinkStatus(String bhOasApprovalLinkStatus) {
        this.bhOasApprovalLinkStatus = bhOasApprovalLinkStatus;
    }

    public String getMetOrSpokeDateForBh() {
        return metOrSpokeDateForBh;
    }

    public void setMetOrSpokeDateForBh(String metOrSpokeDateForBh) {
        this.metOrSpokeDateForBh = metOrSpokeDateForBh;
    }

    public String getPhoneCallOrPersonMeetForBh() {
        return phoneCallOrPersonMeetForBh;
    }

    public void setPhoneCallOrPersonMeetForBh(String phoneCallOrPersonMeetForBh) {
        this.phoneCallOrPersonMeetForBh = phoneCallOrPersonMeetForBh;
    }

    public String getIsApplicantExistingCustomerForBh() {
        return isApplicantExistingCustomerForBh;
    }

    public void setIsApplicantExistingCustomerForBh(String isApplicantExistingCustomerForBh) {
        this.isApplicantExistingCustomerForBh = isApplicantExistingCustomerForBh;
    }

    public String getReminderEmailSms() {
        return reminderEmailSms;
    }

    public void setReminderEmailSms(String reminderEmailSms) {
        this.reminderEmailSms = reminderEmailSms;
    }

    public String getLastReminderDateTime() {
        return lastReminderDateTime;
    }

    public void setLastReminderDateTime(String lastReminderDateTime) {
        this.lastReminderDateTime = lastReminderDateTime;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "BhDetails{" +
                "bhApproval='" + bhApproval + '\'' +
                ", bhApprovalTime='" + bhApprovalTime + '\'' +
                ", bhOasApprovalLinkStatus='" + bhOasApprovalLinkStatus + '\'' +
                ", metOrSpokeDateForBh='" + metOrSpokeDateForBh + '\'' +
                ", phoneCallOrPersonMeetForBh='" + phoneCallOrPersonMeetForBh + '\'' +
                ", isApplicantExistingCustomerForBh='" + isApplicantExistingCustomerForBh + '\'' +
                ", reminderEmailSms='" + reminderEmailSms + '\'' +
                ", lastReminderDateTime='" + lastReminderDateTime + '\'' +
                '}';
    }


}
