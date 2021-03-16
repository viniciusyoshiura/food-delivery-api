package com.mycompany.fooddelivery.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.mycompany.fooddelivery.api.model.dto.PaymentMethodDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("PaymentMethodsDTO")
@Data
public class PaymentMethodsDTOOpenApi {

	private PaymentMethodsEmbeddedDTOOpenApi _embedded;
    private Links _links;
    
    @ApiModel("PaymentMethodsEmbeddedDTOOpenApi")
    @Data
    public class PaymentMethodsEmbeddedDTOOpenApi {
        
        private List<PaymentMethodDTO> paymentMethods;
        
    }   
	
}
