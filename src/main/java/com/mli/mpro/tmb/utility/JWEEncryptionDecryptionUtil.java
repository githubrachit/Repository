package com.mli.mpro.tmb.utility;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.utils.Utility;
import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Collections;
@Service
public class JWEEncryptionDecryptionUtil {

    private static final Logger logger = LoggerFactory.getLogger(JWEEncryptionDecryptionUtil.class);

    @Value("${rsaPublicKeyPathForTmb}")
    private String rsaPublicKeyPath;

    @Value("${rsaPrivateKeyPathForTmb}")
    private String rsaPrivateKeyPath;

    public String rsaEncrypt(String payload) throws UserHandledException {
        try {
            RSAPublicKey rsaPublicKey = (RSAPublicKey) getPublicKey();
            JsonWebEncryption jwe = new JsonWebEncryption();
            jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.RSA_OAEP_256);
            jwe.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_256_GCM);
            jwe.setKey(rsaPublicKey);
            jwe.setPayload(payload);
            String serializedJwe = jwe.getCompactSerialization();
            return serializedJwe;
        } catch (Exception ex) {
            logger.error("error while rsaEncryption {}", Utility.getExceptionAsString(ex));
            throw new UserHandledException(Collections.singletonList(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public PublicKey getPublicKey() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(rsaPublicKeyPath);
             BufferedInputStream bis = new BufferedInputStream(inputStream)) {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            if (bis.available() > 0) {
                Certificate cert = cf.generateCertificate(bis);
                return cert.getPublicKey();
            }
        } catch (Exception ex) {
            logger.error("Error " +
                    "occurred while getting public key from the resource path {}: {}", rsaPublicKeyPath, Utility.getExceptionAsString(ex));
        }

        return null;
    }

    public PrivateKey getPrivateKey() {
        try {
            logger.info("Inside get private key method");
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(rsaPrivateKeyPath);
            if (inputStream == null) {
                throw new IOException("private key not found on classpath: " + rsaPrivateKeyPath);
            }
            StringBuilder keyBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    keyBuilder.append(line).append("\n");
                }
            }
            String key = keyBuilder.toString();
            String privateKeyPEM = key
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("[\\r\\n]+", "");

            privateKeyPEM = privateKeyPEM.replaceAll("[^A-Za-z0-9+/=]", "");
            if (!privateKeyPEM.matches("[A-Za-z0-9+/=]+")) {
                throw new IllegalArgumentException("Key contains invalid base64 characters");
            }

            byte[] decoded = Base64.getDecoder().decode(privateKeyPEM);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
            KeyFactory kf = KeyFactory.getInstance("RSA");

            return kf.generatePrivate(keySpec);
        } catch (Exception ex) {
            logger.error("Error occurred while getting private key from the resource path {}: {}", rsaPrivateKeyPath, Utility.getExceptionAsString(ex));
        }
        return null;
    }

    public String rsaDecrypt(String padding) {
        try {
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) getPrivateKey();
            JsonWebEncryption jwe = new JsonWebEncryption();
            jwe.setCompactSerialization(padding);
            jwe.setKey(rsaPrivateKey);
            return jwe.getPayload();
        } catch (Exception ex) {
            logger.info("Exception Occurred while doing rsa decryption {}  ", Utility.getExceptionAsString(ex));
        }
        return null;
    }
}
