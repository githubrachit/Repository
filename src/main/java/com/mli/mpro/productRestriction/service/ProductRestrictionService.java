package com.mli.mpro.productRestriction.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.EncryptionRequest;
import com.mli.mpro.common.models.EncryptionResponse;
import com.mli.mpro.productRestriction.models.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;

public interface ProductRestrictionService {
    List<String> update(String type, List<String> data, String status);

    ValidateProposalDataResponse validateProposalData(ProductRestrictionPayload productRestrictionPayload);

    RequestResponse validateProduct(RequestResponse requestResponse, @RequestHeader MultiValueMap<String, String> headerMap, ErrorResponseParams errorResponseParams) throws JsonProcessingException, UserHandledException;

    List<String> updateRestrictionData(RestrictionData restrictionData);

    EncryptionRequest encryption(EncryptionResponse inputPayload) throws NoSuchPaddingException, IllegalBlockSizeException,
            CertificateException, NoSuchAlgorithmException, BadPaddingException, IOException, InvalidKeyException, UserHandledException;

}
