package com.mycompany.fooddelivery.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface PhotoStorageService {

	void store(NewPhoto newPhoto);
	
	void remove(String fileName);
	
	InputStream retrieve(String fileName);
	
	// --------- Method declaration and implementation
	default String generateFileName(String fileName) {
		return UUID.randomUUID().toString() + "_" + fileName;
	}
	
	// --------- Method declaration and implementation
	default void replace(String oldFileName, NewPhoto newPhoto) {
		this.store(newPhoto);
		
		if (oldFileName != null) {
			this.remove(oldFileName);
		}
	}
	
	@Builder
	@Getter
	class NewPhoto {
		
		private String fileName;
		private InputStream inputStream;
		
	}
	
}
