package com.mycompany.fooddelivery.api.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.mycompany.fooddelivery.api.HateoasLinks;
import com.mycompany.fooddelivery.api.controller.PurchaseOrderController;
import com.mycompany.fooddelivery.api.model.dto.PurchaseOrderSummaryDTO;
import com.mycompany.fooddelivery.domain.model.PurchaseOrder;

@Component
public class PurchaseOrderSummaryDTOConverter extends RepresentationModelAssemblerSupport<PurchaseOrder, PurchaseOrderSummaryDTO> {

	@Autowired
    private ModelMapper modelMapper;
    
	@Autowired
	private HateoasLinks hateoasLinks;
	
	public PurchaseOrderSummaryDTOConverter() {
        super(PurchaseOrderController.class, PurchaseOrderSummaryDTO.class);
    }
	
	@Override
    public PurchaseOrderSummaryDTO toModel(PurchaseOrder purchaseOrder) {
		PurchaseOrderSummaryDTO purchaseOrderSummaryDTO = createModelWithId(purchaseOrder.getId(), purchaseOrder);
        modelMapper.map(purchaseOrder, purchaseOrderSummaryDTO);
        
        purchaseOrderSummaryDTO.add(hateoasLinks.linkToPurchaseOrders());
        
        purchaseOrderSummaryDTO.getRestaurant().add(
        		hateoasLinks.linkToRestaurant(purchaseOrder.getRestaurant().getId()));

        purchaseOrderSummaryDTO.getUser().add(hateoasLinks.linkToUser(purchaseOrder.getUser().getId()));
        
        return purchaseOrderSummaryDTO;
    }
    
    public List<PurchaseOrderSummaryDTO> toCollectionModel(List<PurchaseOrder> purchaseOrders) {
        return purchaseOrders.stream()
                .map(purchaseOrder -> toModel(purchaseOrder))
                .collect(Collectors.toList());
    }
	
}
