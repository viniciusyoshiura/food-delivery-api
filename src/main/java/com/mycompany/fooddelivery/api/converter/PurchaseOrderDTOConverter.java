package com.mycompany.fooddelivery.api.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.fooddelivery.api.model.dto.PurchaseOrderDTO;
import com.mycompany.fooddelivery.domain.model.PurchaseOrder;

@Component
public class PurchaseOrderDTOConverter {

	@Autowired
    private ModelMapper modelMapper;
    
    public PurchaseOrderDTO toModel(PurchaseOrder purchaseOrder) {
        return modelMapper.map(purchaseOrder, PurchaseOrderDTO.class);
    }
    
    public List<PurchaseOrderDTO> toCollectionModel(List<PurchaseOrder> purchaseOrders) {
        return purchaseOrders.stream()
                .map(purchaseOrder -> toModel(purchaseOrder))
                .collect(Collectors.toList());
    }
	
}
