package com.mycompany.fooddelivery.domain.infrastructure.service;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import com.mycompany.fooddelivery.core.storage.StorageProperties;
import com.mycompany.fooddelivery.domain.infrastructure.service.exception.StorageException;
import com.mycompany.fooddelivery.domain.service.PhotoStorageService;

public class PhotoStorageServiceLocalImpl implements PhotoStorageService {
	
	@Autowired
	private StorageProperties storageProperties;
	
	@Override
	public void store(NewPhoto newPhoto) {
		try {
			Path filePath = getFilePath(newPhoto.getFileName());
			
			FileCopyUtils.copy(newPhoto.getInputStream(), 
					Files.newOutputStream(filePath));
		} catch (Exception e) {
			throw new StorageException("It was not possible to store the file.", e);
		}
	}
	
	private Path getFilePath(String filName) {
		// ---------- Resolve concatenates folder + fileName
		return storageProperties.getLocal().getFolderPhotos()
			.resolve(Path.of(filName));
	}

	@Override
	public void remove(String fileName) {
		try {
			Path filePath = getFilePath(fileName);
			// ---------- Remove file from local storage
			Files.deleteIfExists(filePath);
		} catch (Exception e) {
			throw new StorageException("It was not possible to remove file.", e);
		}
		
	}
	
	@Override
	public RetrievedPhoto retrieve(String fileName) {
	    try {
	        Path filePath = getFilePath(fileName);

	        RetrievedPhoto retrievedPhoto = RetrievedPhoto.builder()
					.inputStream(Files.newInputStream(filePath))
					.build();
			
			return retrievedPhoto;
	    } catch (Exception e) {
	        throw new StorageException("It was not possible to retrieve file.", e);
	    }
	} 

}
