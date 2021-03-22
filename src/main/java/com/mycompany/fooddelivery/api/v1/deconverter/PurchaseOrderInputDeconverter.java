package com.mycompany.fooddelivery.api.v1.deconverter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.fooddelivery.api.v1.model.input.PurchaseOrderInput;
import com.mycompany.fooddelivery.domain.model.PurchaseOrder;

@Component
public class PurchaseOrderInputDeconverter {

	@Autowired
	private ModelMapper modelMapper;

	public PurchaseOrder toDomainObject(PurchaseOrderInput purchaseOrderInput) {
		return modelMapper.map(purchaseOrderInput, PurchaseOrder.class);
	}

	public void copyToDomainObject(PurchaseOrderInput purchaseOrderInput, PurchaseOrder purchaseOrder) {
		modelMapper.map(purchaseOrderInput, purchaseOrder);
	}

}
