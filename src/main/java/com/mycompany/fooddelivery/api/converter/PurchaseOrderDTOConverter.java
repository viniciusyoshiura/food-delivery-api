package com.mycompany.fooddelivery.api.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.mycompany.fooddelivery.api.HateoasLinks;
import com.mycompany.fooddelivery.api.controller.PurchaseOrderController;
import com.mycompany.fooddelivery.api.model.dto.PurchaseOrderDTO;
import com.mycompany.fooddelivery.domain.model.PurchaseOrder;

@Component
public class PurchaseOrderDTOConverter extends RepresentationModelAssemblerSupport<PurchaseOrder, PurchaseOrderDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private HateoasLinks hateoasLinks;
	
	public PurchaseOrderDTOConverter() {
		super(PurchaseOrderController.class, PurchaseOrderDTO.class);
	}

	@Override
	public PurchaseOrderDTO toModel(PurchaseOrder purchaseOrder) {
		PurchaseOrderDTO purchaseOrderDTO = createModelWithId(purchaseOrder.getId(), purchaseOrder);
		modelMapper.map(purchaseOrder, purchaseOrderDTO);
		
		// ---------- Adding links to PurchaseOrder
		purchaseOrderDTO.add(hateoasLinks.linkToPurchaseOrders("purchase-orders"));
		
		// ---------- Adding links to PurchaseOrderStatus
		if(purchaseOrder.canBeConfirmed()) {
			purchaseOrderDTO.add(hateoasLinks.linkToPurchaseOrderConfirmation(purchaseOrder.getUuid(), "confirm"));
		}
		if(purchaseOrder.canBeDelivered()) {
			purchaseOrderDTO.add(hateoasLinks.linkToPurchaseOrderDelivery(purchaseOrder.getUuid(), "deliver"));
		}
		if(purchaseOrder.canBeCancelled()) {
			purchaseOrderDTO.add(hateoasLinks.linkToPurchaseOrderCancellation(purchaseOrder.getUuid(), "cancel"));
		}
		purchaseOrderDTO.getRestaurant().add(
				hateoasLinks.linkToRestaurant(purchaseOrder.getRestaurant().getId()));
	    
		purchaseOrderDTO.getUser().add(
				hateoasLinks.linkToUser(purchaseOrder.getUser().getId()));
	    
		purchaseOrderDTO.getPaymentMethod().add(
				hateoasLinks.linkToPaymentMethod(purchaseOrder.getPaymentMethod().getId()));
	    
		purchaseOrderDTO.getDeliverAddress().getCity().add(
				hateoasLinks.linkToCity(purchaseOrder.getDeliverAddress().getCity().getId()));
	    
		purchaseOrderDTO.getItems().forEach(item -> {
	        item.add(hateoasLinks.linkToProduct(
	        		purchaseOrderDTO.getRestaurant().getId(), item.getProductId(), "product"));
	    });
		

		return purchaseOrderDTO;
	}

//    public PurchaseOrderDTO toModel(PurchaseOrder purchaseOrder) {
//        return modelMapper.map(purchaseOrder, PurchaseOrderDTO.class);
//    }

	public List<PurchaseOrderDTO> toCollectionModel(List<PurchaseOrder> purchaseOrders) {
		return purchaseOrders.stream().map(purchaseOrder -> toModel(purchaseOrder)).collect(Collectors.toList());
	}

}
