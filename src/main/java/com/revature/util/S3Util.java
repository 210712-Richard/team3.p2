package com.revature.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration 
public class S3Util {
	
	public static final String SPRINT_BUCKET_NAME= "team3.project2.bucket";
	@Bean 
	public S3Client s3Client() {
		return S3Client.builder().region(Region.US_WEST_1).build();
	}
}
