package com.mycompany.fooddelivery.api.model.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "paymentMethods")
@Setter
@Getter
public class PaymentMethodDTO extends RepresentationModel<PaymentMethodDTO> {
	
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Credit card")
    private String description;
	
}
