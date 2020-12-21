package com.mycompany.fooddelivery.domain.exception;

public class PurchaseOrderNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;
    
    public PurchaseOrderNotFoundException(String purchaseOrderUuid) {
        super(String.format("Theres is not a purchase order with uuid %s", purchaseOrderUuid));
    }   

}
