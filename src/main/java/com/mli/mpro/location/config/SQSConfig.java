package com.mli.mpro.location.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
public class SQSConfig {

	private static final Logger log = LoggerFactory.getLogger(SQSConfig.class);

	@Value("${sqs.aws.region}")
	private String awsRegion;

	@Bean
	public AmazonSQS amazonSQS() {
		log.info("Connection to AWS SQS Initiated");
		return AmazonSQSAsyncClientBuilder.standard().withRegion(Regions.fromName(awsRegion)).build();

	}
	
	@Bean
	public SqsClient sqsClient1() {
		log.info("Connection to AWS SqsClient Initiated");
		return SqsClient.builder().region(Region.of(awsRegion)).build();
	}
	

}
