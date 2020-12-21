package com.mycompany.fooddelivery.api.model.input;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PurchaseOrderInput {

	@Valid
    @NotNull
    private RestaurantIdInput restaurant;
    
    @Valid
    @NotNull
    private AddressInput deliverAddress;
    
    @Valid
    @NotNull
    private PaymentMethodIdInput paymentMethod;
    
    @Valid
    @Size(min = 1)
    @NotNull
    private List<ItemPurchaseOrderInput> items;
	
}
