package com.mli.mpro.location.soa.service;

import com.mli.mpro.location.productRecommendation.models.InputRequest;
import com.mli.mpro.location.productRecommendation.models.RequestData;
import com.mli.mpro.location.soa.exception.SoaCustomException;
import org.springframework.http.ResponseEntity;

public interface ProductRecommendationService {
    ResponseEntity<Object> getRecommendedProductsList(InputRequest inputRequest) throws SoaCustomException;
}
