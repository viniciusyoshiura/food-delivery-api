package com.mycompany.fooddelivery.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.mycompany.fooddelivery.domain.event.PurchaseOrderDeliveredEvent;
import com.mycompany.fooddelivery.domain.model.PurchaseOrder;
import com.mycompany.fooddelivery.domain.service.MailSendingService;
import com.mycompany.fooddelivery.domain.service.MailSendingService.Message;

@Component
public class NotificationPurchaseOrderDeliveredListener {

	@Autowired
	private MailSendingService mailSendingService;
	
	// ---------- @TransactionalEventListener trigger the event before transaction
	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void whenConfirmingOrder(PurchaseOrderDeliveredEvent purchaseOrderDeliveredEvent) {
		
		PurchaseOrder purchaseOrder = purchaseOrderDeliveredEvent.getPurchaseOrder();
		
		var message = Message.builder()
				.subject(purchaseOrder.getRestaurant().getName() + " - Order delivered!")
				.body("delivered-purchase-order.html")
				.variable("purchaseOrder", purchaseOrder)
				.recipient(purchaseOrder.getUser().getEmail())
				.build();
		
		mailSendingService.send(message);
		
	}
	
}
