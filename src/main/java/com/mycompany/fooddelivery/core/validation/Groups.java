package com.mycompany.fooddelivery.core.validation;

public interface Groups {

	// ---------- Groups Bean validation, only attributes 
	// ---------- with annotation (groups = Groups.KitchenId.class)
	// ---------- will NOT be validated
	public interface KitchenId {}
	
	public interface StateId{}
	
}
