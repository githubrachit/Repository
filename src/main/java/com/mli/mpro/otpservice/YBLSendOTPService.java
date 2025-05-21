package com.mli.mpro.otpservice;

import java.util.Map;

public interface YBLSendOTPService {

    Object sendOTPToYBLCustomer(Map<String,String> request);
}
