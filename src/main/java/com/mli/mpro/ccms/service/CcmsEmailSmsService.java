package com.mli.mpro.ccms.service;


import com.mli.mpro.ccms.model.Data;
import com.mli.mpro.ccms.model.DocInfo;
import com.mli.mpro.common.exception.UserHandledException;

public interface CcmsEmailSmsService {
    String nearRealTimeNotificationGenerationAsync(DocInfo docInfo, Data ccmsSmsPayload) throws UserHandledException;

    String nearRealTimeNotificationGeneration(DocInfo docInfo, Data ccmsSmsPayload) throws UserHandledException;

}
