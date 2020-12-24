package com.mycompany.fooddelivery.domain.infrastructure.service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.mycompany.fooddelivery.domain.infrastructure.service.exception.StorageException;
import com.mycompany.fooddelivery.domain.service.PhotoStorageService;

@Service
public class PhotoStorageServiceLocalImpl implements PhotoStorageService {

	@Value("${storage.local.folder-photos}")
	private Path photoFolder;
	
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
		return photoFolder.resolve(Path.of(filName));
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
	public InputStream retrieve(String fileName) {
	    try {
	        Path filePath = getFilePath(fileName);

	        return Files.newInputStream(filePath);
	    } catch (Exception e) {
	        throw new StorageException("It was not possible to retrieve file.", e);
	    }
	} 

}
