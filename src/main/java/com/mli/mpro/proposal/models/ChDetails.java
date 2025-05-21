package com.mli.mpro.proposal.models;

import com.mli.mpro.utils.Utility;

public class ChDetails {
    private String chApproval;
    private String chApprovalTime;
    private String chOasApprovalLinkStatus;
    private String isApplicantExistingCustomerForCh;
    private String metOrSpokeDateForCh;

    private String phoneCallOrPersonMeetForCh;

    private String reminderEmailSms;
    private String lastReminderDateTime;

    public String getChApproval() {
        return chApproval;
    }

    public void setChApproval(String chApproval) {
        this.chApproval = chApproval;
    }

    public String getChApprovalTime() {
        return chApprovalTime;
    }

    public void setChApprovalTime(String chApprovalTime) {
        this.chApprovalTime = chApprovalTime;
    }

    public String getChOasApprovalLinkStatus() {
        return chOasApprovalLinkStatus;
    }

    public void setChOasApprovalLinkStatus(String chOasApprovalLinkStatus) {
        this.chOasApprovalLinkStatus = chOasApprovalLinkStatus;
    }

    public String getIsApplicantExistingCustomerForCh() {
        return isApplicantExistingCustomerForCh;
    }

    public void setIsApplicantExistingCustomerForCh(String isApplicantExistingCustomerForCh) {
        this.isApplicantExistingCustomerForCh = isApplicantExistingCustomerForCh;
    }

    public String getMetOrSpokeDateForCh() {
        return metOrSpokeDateForCh;
    }

    public void setMetOrSpokeDateForCh(String metOrSpokeDateForCh) {
        this.metOrSpokeDateForCh = metOrSpokeDateForCh;
    }

    public String getPhoneCallOrPersonMeetForCh() {
        return phoneCallOrPersonMeetForCh;
    }

    public void setPhoneCallOrPersonMeetForCh(String phoneCallOrPersonMeetForCh) {
        this.phoneCallOrPersonMeetForCh = phoneCallOrPersonMeetForCh;
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
        return "ChDetails{" +
                "chApproval='" + chApproval + '\'' +
                ", chApprovalTime='" + chApprovalTime + '\'' +
                ", chOasApprovalLinkStatus='" + chOasApprovalLinkStatus + '\'' +
                ", isApplicantExistingCustomerForCh='" + isApplicantExistingCustomerForCh + '\'' +
                ", metOrSpokeDateForCh='" + metOrSpokeDateForCh + '\'' +
                ", phoneCallOrPersonMeetForCh='" + phoneCallOrPersonMeetForCh + '\'' +
                ", reminderEmailSms='" + reminderEmailSms + '\'' +
                ", lastReminderDateTime='" + lastReminderDateTime + '\'' +
                '}';
    }
}
