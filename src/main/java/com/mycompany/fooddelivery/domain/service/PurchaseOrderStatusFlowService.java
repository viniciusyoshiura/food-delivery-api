package com.mycompany.fooddelivery.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.fooddelivery.domain.model.PurchaseOrder;
import com.mycompany.fooddelivery.domain.service.MailSendingService.Message;

@Service
public class PurchaseOrderStatusFlowService {
	
	@Autowired
	private PurchaseOrderIssuanceService purchaseOrderIssuanceService;
	
	@Autowired
	private MailSendingService mailSendingService;
	
	@Transactional
	public void confirm(String purchaseOrderUuid) {
		PurchaseOrder purchaseOrder = purchaseOrderIssuanceService.searchOrFail(purchaseOrderUuid);
		purchaseOrder.confirm();
		
		var message = Message.builder()
				.subject(purchaseOrder.getRestaurant().getName() + " - Order confirmed!")
				.body("confirmed-purchase-order.html")
				.variable("purchaseOrder", purchaseOrder)
				.recipient(purchaseOrder.getUser().getEmail())
				.build();
		
		mailSendingService.send(message);
		
	}
	
	@Transactional
	public void cancel(String purchaseOrderUuid) {
		PurchaseOrder purchaseOrder = purchaseOrderIssuanceService.searchOrFail(purchaseOrderUuid);
	    purchaseOrder.cancel();
	}
	
	@Transactional
	public void deliver(String purchaseOrderUuid) {
		PurchaseOrder purchaseOrder = purchaseOrderIssuanceService.searchOrFail(purchaseOrderUuid);
	    purchaseOrder.deliver();
	}
}
