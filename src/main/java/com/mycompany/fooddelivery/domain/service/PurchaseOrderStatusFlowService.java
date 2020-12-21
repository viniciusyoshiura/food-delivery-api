package com.mycompany.fooddelivery.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.fooddelivery.domain.model.PurchaseOrder;

@Service
public class PurchaseOrderStatusFlowService {
	
	@Autowired
	private PurchaseOrderIssuanceService purchaseOrderIssuanceService;
	
	@Transactional
	public void confirm(String purchaseOrderUuid) {
		PurchaseOrder purchaseOrder = purchaseOrderIssuanceService.searchOrFail(purchaseOrderUuid);
		purchaseOrder.confirm();
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
