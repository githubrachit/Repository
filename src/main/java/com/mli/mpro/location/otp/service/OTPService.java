package com.mli.mpro.location.otp.service;

import com.mli.mpro.location.otp.models.OtpInputRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface OTPService {
    public ResponseEntity<Object> sendOTP(@RequestBody OtpInputRequest inputRequest);
    public ResponseEntity<Object> verifyOTP(@RequestBody OtpInputRequest inputRequest);
}
