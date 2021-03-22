package com.mycompany.fooddelivery.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.fooddelivery.api.v1.openapi.controller.PurchaseOrderStatusFlowControllerOpenApi;
import com.mycompany.fooddelivery.domain.service.PurchaseOrderStatusFlowService;

@RestController
@RequestMapping(path = "/v1/purchase-orders/{purchaseOrderUuid}", produces = MediaType.APPLICATION_JSON_VALUE)
public class PurchaseOrderStatusFlowController implements PurchaseOrderStatusFlowControllerOpenApi{

	@Autowired
	private PurchaseOrderStatusFlowService purchaseOrderStatusFlowService;
	
	@PutMapping("/confirmation")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> confirm(@PathVariable String purchaseOrderUuid) {
		purchaseOrderStatusFlowService.confirm(purchaseOrderUuid);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/cancellation")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> cancel(@PathVariable String purchaseOrderUuid) {
		purchaseOrderStatusFlowService.cancel(purchaseOrderUuid);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/delivery")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> deliver(@PathVariable String purchaseOrderUuid) {
		purchaseOrderStatusFlowService.deliver(purchaseOrderUuid);
		return ResponseEntity.noContent().build();
	}
	
}
