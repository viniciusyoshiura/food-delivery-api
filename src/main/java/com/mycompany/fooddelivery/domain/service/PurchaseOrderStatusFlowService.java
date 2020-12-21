package com.mycompany.fooddelivery.domain.service;

import java.time.OffsetDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.fooddelivery.domain.exception.BusinessException;
import com.mycompany.fooddelivery.domain.model.PurchaseOrder;
import com.mycompany.fooddelivery.domain.model.enumerator.StatusPurchaseOrder;

@Service
public class PurchaseOrderStatusFlowService {
	
	private static final String MSG_STATUS_UPDATED_ERROR = "Order status with code %d could not be updated from %s to %s";
	
	@Autowired
	private PurchaseOrderIssuanceService purchaseOrderIssuanceService;
	
	@Transactional
	public void confirm(Long purchaseOrderId) {
		PurchaseOrder purchaseOrder = purchaseOrderIssuanceService.searchOrFail(purchaseOrderId);
		
		if (!purchaseOrder.getStatus().equals(StatusPurchaseOrder.CREATED)) {
			throw new BusinessException(
					String.format(MSG_STATUS_UPDATED_ERROR,
							purchaseOrder.getId(), purchaseOrder.getStatus().getDescription(), 
							StatusPurchaseOrder.CONFIRMED.getDescription()));
		}
		
		purchaseOrder.setStatus(StatusPurchaseOrder.CONFIRMED);
		purchaseOrder.setDateConfirmation(OffsetDateTime.now());
	}
	
	@Transactional
	public void cancel(Long purchaseOrderId) {
		PurchaseOrder purchaseOrder = purchaseOrderIssuanceService.searchOrFail(purchaseOrderId);
	    
	    if (!purchaseOrder.getStatus().equals(StatusPurchaseOrder.CREATED)) {
	        throw new BusinessException(
	                String.format(MSG_STATUS_UPDATED_ERROR,
	                		purchaseOrder.getId(), purchaseOrder.getStatus().getDescription(), 
	                		StatusPurchaseOrder.CANCELED.getDescription()));
	    }
	    
	    purchaseOrder.setStatus(StatusPurchaseOrder.CANCELED);
	    purchaseOrder.setDateCancellation(OffsetDateTime.now());
	}
	
	@Transactional
	public void deliver(Long purchaseOrderId) {
		PurchaseOrder purchaseOrder = purchaseOrderIssuanceService.searchOrFail(purchaseOrderId);
	    
	    if (!purchaseOrder.getStatus().equals(StatusPurchaseOrder.CONFIRMED)) {
	        throw new BusinessException(
	                String.format(MSG_STATUS_UPDATED_ERROR,
	                		purchaseOrder.getId(), purchaseOrder.getStatus().getDescription(), 
	                        StatusPurchaseOrder.DELIVERED.getDescription()));
	    }
	    
	    purchaseOrder.setStatus(StatusPurchaseOrder.DELIVERED);
	    purchaseOrder.setDateDelivery(OffsetDateTime.now());
	}
}
