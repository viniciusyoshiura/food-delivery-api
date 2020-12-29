package com.mycompany.fooddelivery.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.mycompany.fooddelivery.domain.event.PurchaseOrderConfirmedEvent;
import com.mycompany.fooddelivery.domain.model.PurchaseOrder;
import com.mycompany.fooddelivery.domain.service.MailSendingService;
import com.mycompany.fooddelivery.domain.service.MailSendingService.Message;

@Component
public class NotificationPurchaseOrderConfirmedListener {

	@Autowired
	private MailSendingService mailSendingService;
	
	// ---------- @TransactionalEventListener trigger the event before transaction
//	@EventListener
	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void whenConfirmingOrder(PurchaseOrderConfirmedEvent purchaseOrderConfirmedEvent) {
		
		PurchaseOrder purchaseOrder = purchaseOrderConfirmedEvent.getPurchaseOrder();
		
		var message = Message.builder()
				.subject(purchaseOrder.getRestaurant().getName() + " - Order confirmed!")
				.body("confirmed-purchase-order.html")
				.variable("purchaseOrder", purchaseOrder)
				.recipient(purchaseOrder.getUser().getEmail())
				.build();
		
		mailSendingService.send(message);
		
	}
	
}
