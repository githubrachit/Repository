package com.mli.mpro.location.services;

import com.mli.mpro.common.models.YblReplacementSaleRequestPayload;
import org.springframework.retry.annotation.Retryable;

public interface YblReplacementSaleService {
    @Retryable(value = {Exception.class}, maxAttempts = 3)
    void callYblReplacementSale(YblReplacementSaleRequestPayload yblReplacementSaleRequestPayload) throws Exception;
}
