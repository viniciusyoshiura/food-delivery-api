package com.mycompany.fooddelivery.core.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.mycompany.fooddelivery.core.storage.StorageProperties.TypeStorage;
import com.mycompany.fooddelivery.domain.infrastructure.service.PhotoStorageServiceLocalImpl;
import com.mycompany.fooddelivery.domain.infrastructure.service.PhotoStorageServiceS3Impl;
import com.mycompany.fooddelivery.domain.service.PhotoStorageService;

@Configuration
public class StorageConfig {

	@Autowired
	private StorageProperties storageProperties;
	
	@Bean
	@ConditionalOnProperty(name = "storage.type", havingValue = "s3")
	public AmazonS3 amazonS3() {
		var credentials = new BasicAWSCredentials(
				storageProperties.getS3().getAccessKey(), 
				storageProperties.getS3().getSecretKey());
		
		return AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(storageProperties.getS3().getRegion())
				.build();
	}
	
	@Bean
	public PhotoStorageService photoStorageService() {
		if (TypeStorage.S3.equals(storageProperties.getType())) {
			return new PhotoStorageServiceS3Impl();
		} else {
			return new PhotoStorageServiceLocalImpl();
		}
	}
	
}
