package com.elior.s3app.utils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class AwsFactory {

	private static final String REGION = "region";

	public static AmazonS3 createS3ClientbyCredentials(String accessKey, String secretKey, String endPoint) {
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		AmazonS3 s3client = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, REGION))
				.withForceGlobalBucketAccessEnabled(true).withPathStyleAccessEnabled(true).build();
		return s3client;
	}
}
