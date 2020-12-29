package com.mycompany.fooddelivery.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.mycompany.fooddelivery.domain.event.PurchaseOrderCanceledEvent;
import com.mycompany.fooddelivery.domain.model.PurchaseOrder;
import com.mycompany.fooddelivery.domain.service.MailSendingService;
import com.mycompany.fooddelivery.domain.service.MailSendingService.Message;

@Component
public class NotificationPurchaseOrderCanceledListener {

	@Autowired
	private MailSendingService mailSendingService;
	
	// ---------- @TransactionalEventListener trigger the event before transaction
	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void whenConfirmingOrder(PurchaseOrderCanceledEvent purchaseOrderCanceledEvent) {
		
		PurchaseOrder purchaseOrder = purchaseOrderCanceledEvent.getPurchaseOrder();
		
		var message = Message.builder()
				.subject(purchaseOrder.getRestaurant().getName() + " - Order canceled!")
				.body("canceled-purchase-order.html")
				.variable("purchaseOrder", purchaseOrder)
				.recipient(purchaseOrder.getUser().getEmail())
				.build();
		
		mailSendingService.send(message);
		
	}
	
}
