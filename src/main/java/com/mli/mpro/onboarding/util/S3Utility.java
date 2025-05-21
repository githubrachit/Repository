package com.mli.mpro.onboarding.util;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.image.TiffImageData;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.mli.mpro.productRestriction.util.AppConstants;

import com.mli.mpro.proposal.models.DocumentDetails;
import com.mli.mpro.proposal.models.Response;

import org.apache.commons.codec.binary.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static com.google.common.base.Strings.isNullOrEmpty;


@Component
public final class S3Utility {

    private Logger logger = LoggerFactory.getLogger(S3Utility.class);
    @Value("${s3.bucket.name}")
    private  String s3bucketName;
    @Value("${s3.bucket.name.quote}")
    private String s3bucketNameQuote;

    private  AmazonS3 s3Client;

    @PostConstruct
    public void init() {
        
        s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.AP_SOUTH_1)
                .build();
    }


    public S3Object fetchDocumentFromBucket(DocumentDetails document){
        String path = createPath(document);
        logger.info("path for document {}", path);
        S3Object obj = null;
        if(s3Client.doesObjectExist(s3bucketName,path)) {
            obj = s3Client.getObject(s3bucketName,path);
        }
        return obj;
    }

    public String createPath(DocumentDetails details) {
        String channel = details.getChannelName();
        long transactionId = details.getTransactionId();
        String documentName = details.getDocumentName();
        String documentType = details.getDocumentType();
        String path=AppConstants.BLANK;
        path = "NB/B2B/mPRO/" + channel + "/"
                    + transactionId + "/" + documentName + "." + documentType;

        return path;
    }

    public String getDataS3(String quoteId) {
        String response = null;
        try {
            logger.info("fetching illustration response for QuoteID - {}", quoteId);
            String path = createPath(quoteId, AppConstants.RESPONSE);
            response = s3Client.getObjectAsString(s3bucketNameQuote, path);

        } catch (AmazonServiceException e) {
            logger.error("QuoteID - {} .Error Code {} Error {} while fetching illustration response",
                    quoteId, e.getErrorCode(), e);
        }
        return response;
    }

    public String createPath(String quoteId, String type) {
        String path;
        if (isNullOrEmpty(type)) {
            path = AppConstants.PATH + quoteId + AppConstants.PATH_DELIMITER;
        } else {
            path = AppConstants.PATH + quoteId + AppConstants.PATH_DELIMITER + type;
        }
        return path;
    }


}





