package com.mycompany.fooddelivery.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.fooddelivery.domain.exception.ProductNotFoundException;
import com.mycompany.fooddelivery.domain.model.Product;
import com.mycompany.fooddelivery.domain.repository.ProductRepository;

@Service
public class ProductRegistrationService {

	@Autowired
    private ProductRepository productRepository;
    
    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }
    
    public Product searchOrFail(Long restaurantId, Long productId) {
        return productRepository.findById(restaurantId, productId)
            .orElseThrow(() -> new ProductNotFoundException(restaurantId, productId));
    }   
	
}
