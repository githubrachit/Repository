package com.mli.mpro.utils;

import com.mli.mpro.onboarding.util.S3Utility;
import com.mli.mpro.productRestriction.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Base64;

public class JKSUtil {
    private static final String keyPreFix = "-----BEGIN PRIVATE KEY-----";
    private static final String keyPostFix = "-----END PRIVATE KEY-----";
    private static final Logger logger = LoggerFactory.getLogger(JKSUtil.class);

    public static KeyStore getKeyStore(String maxCert, String maxPrivateKey, String yblCert, String password, S3Utility s3Utility) throws Exception {
        final String algo = "RSA";
        final String type = "JKS";
        final String alias = "mpro";
        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
        Certificate cert;
        try (ByteArrayInputStream certInputStream = new java.io.ByteArrayInputStream(maxCert.getBytes())) {
            cert = certFactory.generateCertificate(certInputStream);
        }

        maxPrivateKey = maxPrivateKey.replace(keyPreFix, AppConstants.BLANK)
                .replace(keyPostFix, AppConstants.BLANK)
                .replaceAll("\\s", AppConstants.BLANK);
        byte[] privateKeyBytes = Base64.getDecoder().decode(maxPrivateKey);
        PrivateKey privateKey = java.security.KeyFactory.getInstance(algo)
                .generatePrivate(new java.security.spec.PKCS8EncodedKeySpec(privateKeyBytes));

        KeyStore keyStore = KeyStore.getInstance(type);
        keyStore.load(null, null);

        keyStore.setKeyEntry(alias, privateKey, password.toCharArray(), new Certificate[]{cert});
        logger.info("KeyStore created successfully.");

        if(yblCert != null){
            keyStore = importYblCertificate(keyStore, yblCert);
        }
        return keyStore;
    }

    public static KeyStore importYblCertificate(KeyStore keyStore, String yblCert) {
        try{
            final String alias = "ybl-skyway";
            final String type = "X.509";
            CertificateFactory certFactory = CertificateFactory.getInstance(type);
            Certificate additionalCert;
            try (ByteArrayInputStream certInputStream = new ByteArrayInputStream(yblCert.getBytes())) {
                additionalCert = certFactory.generateCertificate(certInputStream);
            }
            keyStore.setCertificateEntry(alias, additionalCert);
            logger.info("YBL Certificate imported successfully.");
        }catch (Exception e){
            logger.error("Error while importing YBL Certificate.", e);
        }
        return keyStore;
    }

}
