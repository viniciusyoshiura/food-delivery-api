package com.mycompany.fooddelivery.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.fooddelivery.api.converter.PurchaseOrderDTOConverter;
import com.mycompany.fooddelivery.api.converter.PurchaseOrderSummaryDTOConverter;
import com.mycompany.fooddelivery.api.deconverter.PurchaseOrderInputDeconverter;
import com.mycompany.fooddelivery.api.model.dto.PurchaseOrderDTO;
import com.mycompany.fooddelivery.api.model.dto.PurchaseOrderSummaryDTO;
import com.mycompany.fooddelivery.api.model.input.PurchaseOrderInput;
import com.mycompany.fooddelivery.domain.exception.BusinessException;
import com.mycompany.fooddelivery.domain.exception.EntityNotFoundException;
import com.mycompany.fooddelivery.domain.model.PurchaseOrder;
import com.mycompany.fooddelivery.domain.model.User;
import com.mycompany.fooddelivery.domain.repository.PurchaseOrderRepository;
import com.mycompany.fooddelivery.domain.service.PurchaseOrderIssuanceService;

@RestController
@RequestMapping(value = "/purchase-orders")
public class PurchaseOrderController {

	@Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    
    @Autowired
    private PurchaseOrderIssuanceService purchaseOrderIssuanceService;
    
    @Autowired
    private PurchaseOrderDTOConverter purchaseOrderDTOConverter;
    
    @Autowired
    private PurchaseOrderSummaryDTOConverter purchaseOrderSummaryDTOConverter;
    
    @Autowired
    private PurchaseOrderInputDeconverter purchaseOrderInputDeconverter;
    
    @GetMapping
    public List<PurchaseOrderSummaryDTO> list() {
        List<PurchaseOrder> allPurchaseOrders = purchaseOrderRepository.findAll();
        
        return purchaseOrderSummaryDTOConverter.toCollectionModel(allPurchaseOrders);
    }
    
    @GetMapping("/{purchaseOrderUuid}")
    public PurchaseOrderDTO search(@PathVariable String purchaseOrderUuid) {
    	PurchaseOrder purchaseOrder = purchaseOrderIssuanceService.searchOrFail(purchaseOrderUuid);
        
        return purchaseOrderDTOConverter.toModel(purchaseOrder);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PurchaseOrderDTO insert(@Valid @RequestBody PurchaseOrderInput purchaseOrderInput) {
        try {
            PurchaseOrder newPurchaseOrder = purchaseOrderInputDeconverter.toDomainObject(purchaseOrderInput);

            // TODO get user authenticated
            newPurchaseOrder.setUser(new User());
            newPurchaseOrder.getUser().setId(1L);

            newPurchaseOrder = purchaseOrderIssuanceService.save(newPurchaseOrder);

            return purchaseOrderDTOConverter.toModel(newPurchaseOrder);
        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }
	
}
