package com.mycompany.fooddelivery.domain.service;

import java.io.InputStream;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.fooddelivery.domain.exception.ProductPhotoNotFoundException;
import com.mycompany.fooddelivery.domain.model.ProductPhoto;
import com.mycompany.fooddelivery.domain.repository.ProductRepository;
import com.mycompany.fooddelivery.domain.service.PhotoStorageService.NewPhoto;

@Service
public class ProductPhotoService {

	@Autowired
	private ProductRepository productRepository;
	
	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PhotoStorageService photoStorageService;
	
	@Transactional
	public ProductPhoto save(ProductPhoto productPhoto, InputStream fileData) {
		
		Long restaurantId = productPhoto.getRestaurantId();
		Long productId = productPhoto.getProduct().getId();
		
		// ---------- Generating newFileName to avoid repetitions
		String newFileName = photoStorageService.generateFileName(productPhoto.getFileName());
		String existingFileName = null;
		
		Optional<ProductPhoto> existentProductPhoto = productRepository
				.findPhotoById(restaurantId, productId);
		
		if (existentProductPhoto.isPresent()) {
			// ---------- Remove photo from database before persist
			manager.remove(existentProductPhoto.get());
//			productRepository.delete(existentProductPhoto.get());
			// ---------- Remove photo from local storage before persist
			existingFileName = existentProductPhoto.get().getFileName();
		}
		
		productPhoto.setFileName(newFileName);
		
		// --------- Save ProducPhoto before store photo
		productPhoto =  productRepository.save(productPhoto);
		
		// --------- Flushes all jpa pendencies
		productRepository.flush();
		
		NewPhoto newPhoto = NewPhoto.builder()
				.fileName(productPhoto.getFileName())
				.inputStream(fileData)
				.build();
		
		// ---------- Store newPhoto and remove oldPhoto from local storage
		photoStorageService.replace(existingFileName, newPhoto);
		
		return productPhoto;
	}
	
	public ProductPhoto searchOrFail(Long restaurantId, Long productId) {
	    return productRepository.findPhotoById(restaurantId, productId)
	            .orElseThrow(() -> new ProductPhotoNotFoundException(restaurantId, productId));
	}  
	
	@Transactional
	public void remove(Long restaurantId, Long productId) {
		ProductPhoto productPhoto = searchOrFail(restaurantId, productId);
	    
		manager.remove(productPhoto);
		manager.flush();

		photoStorageService.remove(productPhoto.getFileName());
	}
	
}
