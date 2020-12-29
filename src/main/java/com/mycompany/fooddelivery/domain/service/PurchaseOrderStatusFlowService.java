package com.mycompany.fooddelivery.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.fooddelivery.domain.model.PurchaseOrder;
import com.mycompany.fooddelivery.domain.repository.PurchaseOrderRepository;

@Service
public class PurchaseOrderStatusFlowService {
	
	@Autowired
	private PurchaseOrderIssuanceService purchaseOrderIssuanceService;
	
	@Autowired
	private PurchaseOrderRepository purchaseOrderRepository;
	
	@Transactional
	public void confirm(String purchaseOrderUuid) {
		PurchaseOrder purchaseOrder = purchaseOrderIssuanceService.searchOrFail(purchaseOrderUuid);
		
		// ---------- Publish event in PurchaseOrder, since it is an aggregate root
		// ---------- See the event registration, see PurchaseOrder.confirm()
		purchaseOrder.confirm();
		
		// ---------- Repository.save trigger all related events, i.e., every time a purchase order is confirmed, an event is triggered
		// ---------- See the event listener: NotificationPurchaseOrderConfirmedListener
		purchaseOrderRepository.save(purchaseOrder);
		
	}
	
	@Transactional
	public void cancel(String purchaseOrderUuid) {
		PurchaseOrder purchaseOrder = purchaseOrderIssuanceService.searchOrFail(purchaseOrderUuid);
		// ---------- Publish event in PurchaseOrder, since it is an aggregate root
		// ---------- See the event registration, see PurchaseOrder.cancel()
		purchaseOrder.cancel();
	    
	    // ---------- Repository.save trigger all related events, i.e., every time a purchase order is canceled, an event is triggered
	    // ---------- See the event listener: NotificationPurchaseOrderConfirmedListener
	    purchaseOrderRepository.save(purchaseOrder);
	}
	
	@Transactional
	public void deliver(String purchaseOrderUuid) {
		PurchaseOrder purchaseOrder = purchaseOrderIssuanceService.searchOrFail(purchaseOrderUuid);
		// ---------- Publish event in PurchaseOrder, since it is an aggregate root
		// ---------- See the event registration, see PurchaseOrder.deliver()
	
		purchaseOrder.deliver();
	    
	    // ---------- Repository.save trigger all related events, i.e., every time a purchase order is canceled, an event is triggered
	    // ---------- See the event listener: NotificationPurchaseOrderConfirmedListener
	    purchaseOrderRepository.save(purchaseOrder);
	}
}
