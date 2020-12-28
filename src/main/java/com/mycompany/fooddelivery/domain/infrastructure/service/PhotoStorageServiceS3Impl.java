package com.mycompany.fooddelivery.domain.infrastructure.service;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.mycompany.fooddelivery.core.storage.StorageProperties;
import com.mycompany.fooddelivery.domain.infrastructure.service.exception.StorageException;
import com.mycompany.fooddelivery.domain.service.PhotoStorageService;

public class PhotoStorageServiceS3Impl implements PhotoStorageService {
	
	@Autowired
	private AmazonS3 amazonS3;
	
	@Autowired
	private StorageProperties storageProperties;
	
	@Override
	public RetrievedPhoto retrieve(String fileName) {
		String filePath = getFilePath(fileName);
		
		URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), filePath);
		
		return RetrievedPhoto.builder()
				.url(url.toString()).build();
	}

	@Override
	public void store(NewPhoto newPhoto) {
		
		try {
			String filePath = getFilePath(newPhoto.getFileName());
			
			var objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType(newPhoto.getContentType());
			
			// ---------- .withCannedAcl - public permission to read files
			var putObjectRequest = new PutObjectRequest(
					storageProperties.getS3().getBucket(),
					filePath,
					newPhoto.getInputStream(),
					objectMetadata)
				.withCannedAcl(CannedAccessControlList.PublicRead);
			
			amazonS3.putObject(putObjectRequest);
		} catch (Exception e) {
			throw new StorageException("It was not possible to send file to Amazon S3.", e);
		}
		
	}
	
	@Override
	public void remove(String fileName) {
		
		try {
			String caminhoArquivo = getFilePath(fileName);

	        var deleteObjectRequest = new DeleteObjectRequest(
	                storageProperties.getS3().getBucket(), caminhoArquivo);

	        amazonS3.deleteObject(deleteObjectRequest);
	    } catch (Exception e) {
	        throw new StorageException("It was not possible to remove file in Amazon S3.", e);
	    }
		
	}
	
	private String getFilePath(String fileName) {
		return String.format("%s/%s", storageProperties.getS3().getFolderPhotos(), fileName);
	}
	
}
