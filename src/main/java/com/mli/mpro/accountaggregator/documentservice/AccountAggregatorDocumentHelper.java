package com.mli.mpro.accountaggregator.documentservice;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.mli.mpro.productRestriction.util.AppConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class AccountAggregatorDocumentHelper {

	private static final Logger logger = LoggerFactory.getLogger(AccountAggregatorDocumentHelper.class);

	public String generateDocument(String processedHtml, int retryCount) {
		String encodedString = AppConstants.FAILED;
		if (!StringUtils.isEmpty(processedHtml)) {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			try {
				PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
				DefaultFontProvider defaultFontProvider = new DefaultFontProvider(false, true, false);
				ConverterProperties converterProperties = new ConverterProperties();
				converterProperties.setFontProvider(defaultFontProvider);
				HtmlConverter.convertToPdf(processedHtml, pdfWriter, converterProperties);
				encodedString = com.amazonaws.util.Base64.encodeAsString(byteArrayOutputStream.toByteArray());
			} catch (Exception e) {
				logger.error("Failed to generate encoded string :",e);
				if (retryCount < 2) {
					retryCount++;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException ex) {
						logger.error("InterruptedException:",ex);
					}
					encodedString = generateDocument(processedHtml, retryCount);
				}
			}
		}
		logger.info("Generated document base64 string is::{}", encodedString);
		return encodedString;
	}
}
