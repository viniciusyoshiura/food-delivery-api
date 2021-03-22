package com.mycompany.fooddelivery.api.v1.model.dto;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurants")
@Setter
@Getter
public class RestaurantDTO extends RepresentationModel<RestaurantDTO> {
	
//	@JsonView({ RestaurantView.Summary.class, RestaurantView.OnlyName.class })
	private Long id;
	
//	@JsonView({ RestaurantView.Summary.class, RestaurantView.OnlyName.class })
	private String name;
	
//	@JsonView({ RestaurantView.Summary.class})
	private BigDecimal shippingFee;
	
//	@JsonView({ RestaurantView.Summary.class})
	private KitchenDTO kitchen;
	
	private Boolean active;
	private AddressDTO address;
	private Boolean open;
}
