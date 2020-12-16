package com.mycompany.fooddelivery.domain.exception;

public class KitchenNotFoundException extends EntityNotFoundException{

	private static final long serialVersionUID = 1L;
	
	public KitchenNotFoundException(String msg) {
        super(msg);
    }
	
	public KitchenNotFoundException(Long kitchenId) {
        this(String.format("There is no kitchen registration with code %d", kitchenId));
    }   
	
}
