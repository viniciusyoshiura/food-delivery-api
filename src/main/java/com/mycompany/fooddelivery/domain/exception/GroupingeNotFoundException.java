package com.mycompany.fooddelivery.domain.exception;

public class GroupingeNotFoundException extends EntityNotFoundException{

	private static final long serialVersionUID = 1L;

    public GroupingeNotFoundException(String message) {
        super(message);
    }
    
    public GroupingeNotFoundException(Long groupId) {
        this(String.format("There is no group registration with code %d", groupId));
    } 
	
}
