package com.mycompany.fooddelivery.domain.event;

import com.mycompany.fooddelivery.domain.model.PurchaseOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PurchaseOrderDeliveredEvent {

	private PurchaseOrder purchaseOrder;
	
}
