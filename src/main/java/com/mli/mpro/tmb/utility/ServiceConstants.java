package com.mli.mpro.tmb.utility;
public class ServiceConstants {
    private ServiceConstants() {
    }

    public static final String OTP_SENT_MESSAGE = "OTP sent successfully";
    public static final String INSURANCE_LINK_SENT = "OTP consent link sent to customer successfully";
    public static final String OTP_NOT_SENT_MESSAGE = "OTP not sent successfully";
    public static final String OTP_VERIFIED_MESSAGE = "OTP verified successfully";
    public static final String OTP_NOT_VERIFIED_MESSAGE = "OTP not verified. Please enter correct OTP";
    public static final String INVALID_REQUEST = "Invalid request";
    public static final String OTP_ALREADY_SUBMITTED = "OTP already submitted";
    public static final String OTP_PAGE_EXPIRED = "OTP page expired";
    public static final String OTP_FLOODING_MESSAGE = "You have reached the maximum limit. Please try again later";
    public static final String TRANSACTION_INVALID = "TransactionId Invalid";
    public static final String LINK_INVALID = "Cannot send another link";
    public static final String GENERATE_LINK = "GenerateCustomerLink";
    public static final String SEND_OTP = "SendOtp";
    public static final String VERIFY_OTP = "VerifyOtp";
    public static final String CUSTOMER_DATA_FETCH = "FetchCustomerDetails";
    public static final String ACCOUNT_DATA_FETCH = "FetchAccountDetails";
    public static final String RENEWAL_PUSH = "RenewalPush";
    public static final String EKYC_FAILURE = "CustomerId is not registered with ckyc";
}

