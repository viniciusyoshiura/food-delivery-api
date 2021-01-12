package com.mycompany.fooddelivery.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.fooddelivery.api.openapi.controller.PurchaseOrderStatusFlowControllerOpenApi;
import com.mycompany.fooddelivery.domain.service.PurchaseOrderStatusFlowService;

@RestController
@RequestMapping(path = "/purchase-orders/{purchaseOrderUuid}", produces = MediaType.APPLICATION_JSON_VALUE)
public class PurchaseOrderStatusFlowController implements PurchaseOrderStatusFlowControllerOpenApi{

	@Autowired
	private PurchaseOrderStatusFlowService purchaseOrderStatusFlowService;
	
	@PutMapping("/confirmation")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void confirm(@PathVariable String purchaseOrderUuid) {
		purchaseOrderStatusFlowService.confirm(purchaseOrderUuid);
	}
	
	@PutMapping("/cancellation")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancel(@PathVariable String purchaseOrderUuid) {
		purchaseOrderStatusFlowService.cancel(purchaseOrderUuid);
	}

	@PutMapping("/delivery")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deliver(@PathVariable String purchaseOrderUuid) {
		purchaseOrderStatusFlowService.deliver(purchaseOrderUuid);
	}
	
}
