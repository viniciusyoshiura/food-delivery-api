package com.mycompany.fooddelivery.domain.exception;

public class RestaurantNotFoundException extends EntityNotFoundException{

	private static final long serialVersionUID = 1L;
	
	public RestaurantNotFoundException (String msg) {
        super(msg);
    }
	
	public RestaurantNotFoundException(Long restaurantId) {
        this(String.format("There is not a restaurant with code %d", restaurantId));
    }   
	
}
