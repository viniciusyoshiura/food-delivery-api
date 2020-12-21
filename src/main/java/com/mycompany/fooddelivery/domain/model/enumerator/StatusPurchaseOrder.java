package com.mycompany.fooddelivery.domain.model.enumerator;

public enum StatusPurchaseOrder {

	CREATED("Created"),
	CONFIRMED("Confirmed"),
	DELIVERED("Delivered"),
	CANCELED("Canceled");
	
	private String description;
	
	StatusPurchaseOrder(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return this.description;
	}
	
}
