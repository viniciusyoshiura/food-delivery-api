package com.mycompany.fooddelivery.domain.exception;

public class StateNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;
	
	public StateNotFoundException(String msg) {
		super(msg);
	}
	
	public StateNotFoundException(Long stateId) {
		this(String.format("There is not a state registration with id %d", stateId));
	}
	
}
