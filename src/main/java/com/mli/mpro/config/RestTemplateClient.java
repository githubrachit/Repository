package com.mli.mpro.config;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.certificatemanager.AWSCertificateManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterResult;
import com.amazonaws.services.simplesystemsmanagement.model.Parameter;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.location.services.ACMService;
import com.mli.mpro.location.services.ASMService;
import com.mli.mpro.onboarding.util.S3Utility;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.JKSUtil;
import com.mli.mpro.utils.Utility;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.security.KeyStore;

@Configuration
@DependsOn("propertyConfigLoader")
public class RestTemplateClient {

    private static final Logger logger = LoggerFactory.getLogger(RestTemplateClient.class);

    @Value("${nis.ssl.key-store}")
    private String yblnisKeytore;
    @Value("${nis.ssl.latest-key-store-active}")
    private String latestKeyStoreActive;
    @Value("${nis.ssl.key-store-password}")
    private String yblnisKeystorePassword;

    @Value("${spring.profiles.active}")
    private String environment;
    private final String certificateBackUpPath = "static/certificatesBackup/";

    private String mproCertArn;
    private String mproSecretName;
    private String yblCertSSM;
    private boolean mproCertReadFromAcm;

    private ACMService acmService;
    private ASMService asmService;
    private S3Utility s3Utility;


    // ACM FUL2-239232
    private final String MPRO_CERT_ARN = "MPRO_CERT_ARN";
    private final String MPRO_SECRET_NAME = "MPRO_SECRET_NAME";
    private final String MPRO_YBL_MAIN_CERTIFICATE_SSM = "MPRO_YBL_MAIN_CERTIFICATE_SSM";
    private final String MPRO_CERT_READ_FROM_ACM = "MPRO_CERT_READ_FROM_ACM";
    private final String COMMON_SSM_LOCATION = "/mpro/env/common/";


    @Autowired
    RestTemplateClient(ACMService acmService, ASMService asmService, S3Utility s3Utility) {
        this.acmService = acmService;
        this.asmService = asmService;
        this.s3Utility = s3Utility;

    }

    RestTemplateClient() {
    }


    @Bean
    public RestTemplate restTemplateClientHttpRequestFactory() {
        KeyStore keyStore = null;
        SSLConnectionSocketFactory socketFactory = null;
        int timeout = 3000;

        setYblKeyStoreFileName();
        mproCertReadFromAcm = Boolean.valueOf(Utility.getParameterValues(getKeyNameForEnv(environment, MPRO_CERT_READ_FROM_ACM)).getValue());
        logger.info("inside restTemplateClientHttpRequestFactory isFeatureEnable : {}", mproCertReadFromAcm);
        if(mproCertReadFromAcm){
            try{
                keyStore = getKeyStoreViaAWS();
                logger.info("inside restTemplateClientHttpRequestFactory Successfully generate keyStore Via ACM & ASM Service");
            } catch (Exception ex){
                logger.error("error occurred restTemplateClientHttpRequestFactory While setting keyStore from ACM & ASM with ex {}", Utility.getExceptionAsString(ex));
            }
        }

        try (InputStream fileInputStream = getClass().getClassLoader().getResourceAsStream(yblnisKeytore)) {
            if(keyStore == null){
                logger.info("inside restTemplateClientHttpRequestFactory generating keyStore form code base jks file.");
                keyStore = KeyStore.getInstance(AppConstants.JKS);
                keyStore.load(fileInputStream,
                        yblnisKeystorePassword.toCharArray());
            }
        } catch (Exception e) {
            logger.info("error occurred restTemplateClientHttpRequestFactory with key store path : {} with ex {}", yblnisKeytore, Utility.getExceptionAsString(e));
        }

        try{
            socketFactory = new SSLConnectionSocketFactory(
                    new SSLContextBuilder()
                            .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                            .loadKeyMaterial(keyStore, yblnisKeystorePassword.toCharArray())
                            .build(),
                    NoopHostnameVerifier.INSTANCE);
        } catch (Exception ex){
            logger.error("Setting socketFactory error occurred restTemplateClientHttpRequestFactory While setting socketFactory with ex {}", Utility.getExceptionAsString(ex));
        }

        HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(
                socketFactory).build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
                httpClient);
        // set timeout of 3s for yblReplacementSaleApi
        requestFactory.setConnectTimeout(timeout);
        requestFactory.setReadTimeout(timeout);
        logger.info("inside restTemplateClientHttpRequestFactory JKS binding successfully done.. ");
        return new RestTemplate(requestFactory);
    }

    private void setYblKeyStoreFileName() {
        if(environment.equalsIgnoreCase(AppConstants.ENV_DEV) ||
                environment.equalsIgnoreCase(AppConstants.ENV_UAT) ||
                environment.equalsIgnoreCase(AppConstants.ENV_PREPROD)){
            yblnisKeytore = yblnisKeytore.replace(AppConstants.JKS_EXTENTION,
                    AppConstants.RESEND_LINK_DELIMITER + environment + AppConstants.JKS_EXTENTION);
        }
        // if latestKeyStoreActive is N then use certificates from backup folder
        if(AppConstants.ENV_PROD.equalsIgnoreCase(environment) && AppConstants.N.equalsIgnoreCase(latestKeyStoreActive)){
            logger.info("inside restTemplateClientHttpRequestFactory with flag : {}", latestKeyStoreActive);
            yblnisKeytore = certificateBackUpPath + yblnisKeytore;
        }
        logger.info("inside restTemplateClientHttpRequestFactory with key store path : {}", yblnisKeytore);
    }

    private KeyStore getKeyStoreViaAWS() throws Exception {
        logger.info("Initiate ACM & ASM service to get the key store");
        String yblCert = null;
        AWSCertificateManager acmClient = acmService.createClient();
        AWSSecretsManager asmClient = asmService.createClient();

        mproCertArn = Utility.getParameterValues(getKeyNameForEnv(environment, MPRO_CERT_ARN)).getValue();
        String maxCert = acmService.getCertificate(acmClient, mproCertArn).getCertificate();
        logger.info("MAX Cert ARN {} || Max Cert {}", mproCertArn, maxCert.replaceAll("-","").substring(maxCert.length()-80));

        mproSecretName = Utility.getParameterValues(getKeyNameForEnv(environment, MPRO_SECRET_NAME)).getValue();
        String maxPrivateKey = asmService.getCertificate(asmClient, mproSecretName).getSecretString();
        logger.info("MAX secret ARN {} || Max Secret {}", mproSecretName, maxPrivateKey.replaceAll("-","").substring(maxPrivateKey.length()-80));

        if(AppConstants.ENV_PROD.equalsIgnoreCase(environment)){
            yblCertSSM = Utility.getParameterValues(getKeyNameForEnv(environment, MPRO_YBL_MAIN_CERTIFICATE_SSM)).getValue();
            yblCert = yblCertSSM;
            logger.info("YBL Cert {}", yblCert.replaceAll("-","").substring(yblCert.length()-80));
        }
        logger.info("Initiate ACM & ASM service to get the key store successfully while all certificate are fetched");
        return JKSUtil.getKeyStore(maxCert, maxPrivateKey, yblCert, yblnisKeystorePassword,s3Utility);
    }

    private String getKeyNameForEnv(String env, String ssmKeyName){
        StringBuilder keyName = new StringBuilder();
        keyName.append(COMMON_SSM_LOCATION.replace("env",env))
                .append(ssmKeyName);
        logger.info("inside getKeyNameForEnv keyName : {}", keyName);
        return keyName.toString();
    }

}
