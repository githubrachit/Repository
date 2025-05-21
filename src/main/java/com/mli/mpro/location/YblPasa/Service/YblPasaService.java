package com.mli.mpro.location.YblPasa.Service;

import com.mli.mpro.common.models.InputRequest;
import com.mli.mpro.location.YblPasa.Payload.YblPasaResponse;
import org.springframework.retry.annotation.Retryable;



public interface YblPasaService {
    @Retryable(value = {Exception.class}, maxAttempts = 3)

    public YblPasaResponse fetchYblPasaData(InputRequest pasaInputRequest);

}
