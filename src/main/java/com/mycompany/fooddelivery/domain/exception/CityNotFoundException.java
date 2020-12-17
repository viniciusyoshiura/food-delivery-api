package com.mycompany.fooddelivery.domain.exception;

public class CityNotFoundException extends EntityNotFoundException{

	private static final long serialVersionUID = 1L;
	
	public CityNotFoundException(String msg) {
        super(msg);
    }
	
	public CityNotFoundException(Long cityId) {
        this(String.format("There is not a city registration with code %d", cityId));
    } 
	
}
