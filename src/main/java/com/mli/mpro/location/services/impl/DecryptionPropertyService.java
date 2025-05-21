package com.mli.mpro.location.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author ravishankar
 *
 */
@Component
public class DecryptionPropertyService {

    @Value("${mongodb.password}")
    private String decryptedKey;

    public String getDecryptedKey() {
	return decryptedKey;
    }

}
