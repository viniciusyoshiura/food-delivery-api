package com.mycompany.fooddelivery.domain.exception;

public class ProductNotFoundException extends EntityNotFoundException{

	private static final long serialVersionUID = 1L;

    public ProductNotFoundException(String message) {
        super(message);
    }
    
    public ProductNotFoundException(Long restaurantId, Long productId) {
        this(String.format("Theres is not a product registration with code %d for the restaurant with code %d", 
        		productId, restaurantId));
    }

}
