package com.mycompany.fooddelivery.api.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.fooddelivery.api.model.dto.PurchaseOrderSummaryDTO;
import com.mycompany.fooddelivery.domain.model.PurchaseOrder;

@Component
public class PurchaseOrderSummaryDTOConverter {

	@Autowired
    private ModelMapper modelMapper;
    
    public PurchaseOrderSummaryDTO toModel(PurchaseOrder purchaseOrder) {
        return modelMapper.map(purchaseOrder, PurchaseOrderSummaryDTO.class);
    }
    
    public List<PurchaseOrderSummaryDTO> toCollectionModel(List<PurchaseOrder> purchaseOrders) {
        return purchaseOrders.stream()
                .map(purchaseOrder -> toModel(purchaseOrder))
                .collect(Collectors.toList());
    }
	
}
