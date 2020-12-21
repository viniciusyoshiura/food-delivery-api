package com.mycompany.fooddelivery.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.fooddelivery.domain.service.PurchaseOrderStatusFlowService;

@RestController
@RequestMapping(value = "/purchase-orders/{purchaseOrderId}")
public class PurchaseOrderStatusFlowController {

	@Autowired
	private PurchaseOrderStatusFlowService purchaseOrderStatusFlowService;
	
	@PutMapping("/confirmation")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void confirmar(@PathVariable Long purchaseOrderId) {
		purchaseOrderStatusFlowService.confirm(purchaseOrderId);
	}
	
	@PutMapping("/cancellation")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancelar(@PathVariable Long purchaseOrderId) {
		purchaseOrderStatusFlowService.cancel(purchaseOrderId);
	}

	@PutMapping("/delivery")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void entregar(@PathVariable Long purchaseOrderId) {
		purchaseOrderStatusFlowService.deliver(purchaseOrderId);
	}
	
}
