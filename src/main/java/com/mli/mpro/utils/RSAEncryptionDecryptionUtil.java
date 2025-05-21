package com.mli.mpro.utils;

import com.mli.mpro.common.exception.UserHandledException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import javax.crypto.Cipher;
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
@Configuration
public class RSAEncryptionDecryptionUtil {

    private static final Logger logger = LoggerFactory.getLogger(RSAEncryptionDecryptionUtil.class);

    @Value("${rsaPublicKeyPath}")
    private String rsaPublicKeyPath;

    @Value("${rsaPrivateKeyPath}")
    private String rsaPrivateKeyPath;

    // Method to perform RSA encryption
    public String rsaEncrypt(String algorithm, String base64KeyAndIV) throws UserHandledException {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            RSAPublicKey rsaPublicKey = (RSAPublicKey) getPublicKey();
            logger.info("encryption RSA Public Key {}", rsaPublicKey);
            cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
            byte[] encryptedKeyAndIV = cipher.doFinal(base64KeyAndIV.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedKeyAndIV);
        } catch (Exception ex) {
            logger.error("error while rsaEncryption {}", Utility.getExceptionAsString(ex));
            throw new UserHandledException(Collections.singletonList(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Method to get public key from a file path
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
            // Ensure the processed key only contains valid base64 characters
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

    public String rsaDecrypt(String padding, String encryptedKeyAndIVBase64) {
        try {
            Cipher cipher = Cipher.getInstance(padding);
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) getPrivateKey();
            logger.info("KEK length : ",encryptedKeyAndIVBase64.length());
            logger.info("decryption RSA private key : {}  decryptionPath: {} ", rsaPrivateKey, rsaPrivateKeyPath);
            cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
            byte[] decryptedKeyAndIV = cipher.doFinal(Base64.getDecoder().decode(encryptedKeyAndIVBase64));
            String keyAndIV = new String(decryptedKeyAndIV, StandardCharsets.UTF_8);
            logger.info("Key ANd IV : {} ", keyAndIV);
            System.out.println("response-------- "+keyAndIV);
            return keyAndIV;
        } catch (Exception ex) {
            logger.info("Exception Occurred while doing rsa decryption {}  ", Utility.getExceptionAsString(ex));
        }
        return "";
    }
}
