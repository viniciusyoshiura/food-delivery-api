package com.mycompany.fooddelivery.domain.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.mycompany.fooddelivery.domain.event.PurchaseOrderConfirmedEvent;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BonusPurchaseOrderConfirmedListener {

	@EventListener
	public void whenConfirmingOrder(PurchaseOrderConfirmedEvent purchaseOrderConfirmedEvent) {
		
		log.info("Calculating customer points for: {}", purchaseOrderConfirmedEvent.getPurchaseOrder().getUser().getName());
		
	}
	
}
