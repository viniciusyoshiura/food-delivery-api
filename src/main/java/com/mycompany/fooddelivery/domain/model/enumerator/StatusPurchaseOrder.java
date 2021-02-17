package com.mycompany.fooddelivery.domain.model.enumerator;

import java.util.Arrays;
import java.util.List;

public enum StatusPurchaseOrder {

	CREATED("Created"),
	CONFIRMED("Confirmed", CREATED),
	DELIVERED("Delivered", CONFIRMED),
	CANCELLED("Cancelled", CREATED);
	
	private String description;
	private List<StatusPurchaseOrder> previousStatus;
	
	StatusPurchaseOrder(String description, StatusPurchaseOrder ...previousStatus ) {
		this.description = description;
		this.previousStatus = Arrays.asList(previousStatus);
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public boolean cannotUpdateTo(StatusPurchaseOrder newStatus) {
		return !newStatus.previousStatus.contains(this);
	}
	
	public boolean canUpdateTo(StatusPurchaseOrder newStatus) {
		return !cannotUpdateTo(newStatus);
	}
}
