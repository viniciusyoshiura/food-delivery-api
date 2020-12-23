package com.mycompany.fooddelivery.domain.service;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.fooddelivery.domain.model.ProductPhoto;
import com.mycompany.fooddelivery.domain.repository.ProductRepository;

@Service
public class ProductPhotoService {

	@Autowired
	private ProductRepository productRepository;
	
	@PersistenceContext
	private EntityManager manager;
	
	@Transactional
	public ProductPhoto save(ProductPhoto productPhoto) {
		
		Long restaurantId = productPhoto.getRestaurantId();
		Long productId = productPhoto.getProduct().getId();
		
		Optional<ProductPhoto> existentProductPhoto = productRepository
				.findPhotoById(restaurantId, productId);
		
		if (existentProductPhoto.isPresent()) {
			// ---------- Remove photo before persist
			manager.remove(existentProductPhoto.get());
//			productRepository.delete(existentProductPhoto.get());
		}
		
		return productRepository.save(productPhoto);
	}
	
}
