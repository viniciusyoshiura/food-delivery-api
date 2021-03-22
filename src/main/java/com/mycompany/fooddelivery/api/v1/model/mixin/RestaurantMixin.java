package com.mycompany.fooddelivery.api.v1.model.mixin;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.fooddelivery.domain.model.Address;
import com.mycompany.fooddelivery.domain.model.Kitchen;
import com.mycompany.fooddelivery.domain.model.PaymentMethod;
import com.mycompany.fooddelivery.domain.model.Product;

public abstract class RestaurantMixin {

	// ---------- @JsonIgnoreProperties ignore in serialization and desserialization property by name
	// ---------- allowGetters allows getters in JSON
	@JsonIgnoreProperties(value = "name", allowGetters = true)
	private Kitchen kitchen;
	
	@JsonIgnore
	private Address address;
	
	@JsonIgnore
	private OffsetDateTime dateRegister;
	
	@JsonIgnore
	private OffsetDateTime dateUpdate;
	
	@JsonIgnore
	private List<PaymentMethod> paymentMethods = new ArrayList<>();
	
	@JsonIgnore
	private List<Product> products = new ArrayList<>();
	
}
