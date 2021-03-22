package com.mycompany.fooddelivery.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.mycompany.fooddelivery.api.v1.model.dto.PurchaseOrderSummaryDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("PurchaseOrdersSummaryDTO")
@Getter
@Setter
public class PurchaseOrdersDTOOpenApi {

	private PurchaseOrdersEmbeddedDTOOpenApi _embedded;
    private Links _links;
    private PageDTOOpenApi page;
    
    @ApiModel("PurchaseOrdersEmbeddedDTOOpenApi")
    @Data
    public class PurchaseOrdersEmbeddedDTOOpenApi {
        
        private List<PurchaseOrderSummaryDTO> purchaseOrders;
        
    }   
	
}
