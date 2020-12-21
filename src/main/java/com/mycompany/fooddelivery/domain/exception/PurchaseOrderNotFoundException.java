package com.mycompany.fooddelivery.domain.exception;

public class PurchaseOrderNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public PurchaseOrderNotFoundException(String message) {
        super(message);
    }
    
    public PurchaseOrderNotFoundException(Long purchaseOrderId) {
        this(String.format("Theres is not a purchase order with id %d", purchaseOrderId));
    }   

}
