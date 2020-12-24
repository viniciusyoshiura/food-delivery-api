package com.mycompany.fooddelivery.domain.exception;

public class ProductPhotoNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public ProductPhotoNotFoundException(String message) {
        super(message);
    }

    public ProductPhotoNotFoundException(Long restaurantId, Long productId) {
        this(String.format("Theres is not a photo registration in in producct with code %d for the restaurant with code %d",
        		productId, restaurantId));
    }

}
