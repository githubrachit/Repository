package com.mli.mpro.location.services;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.location.models.unifiedPayment.models.Data;
import com.mli.mpro.location.models.unifiedPayment.models.UIPaymentRequestResponse;
import com.mli.mpro.location.models.unifiedPayment.models.UnifiedWebHookRequest;

public interface UnifiedPaymentService {
    UIPaymentRequestResponse unifiedPayment(UIPaymentRequestResponse request) throws UserHandledException;

    Object unifiedPaymentStatusUpdate(UnifiedWebHookRequest request);

    UIPaymentRequestResponse checkUnifiedPaymentStatus(UIPaymentRequestResponse request) throws UserHandledException;
}
