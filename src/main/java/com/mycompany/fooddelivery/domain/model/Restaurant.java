package com.mycompany.fooddelivery.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.mycompany.fooddelivery.core.validation.Groups;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurant {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(nullable = false)
	private String name;
	
	@NotNull 
	@PositiveOrZero
	@Column(name="shipping_fee", nullable = false)
	private BigDecimal shippingFee;
	
	// ---------- By default sufix ToOne is eager loading
	// ---------- @ConvertGroup, convert Default validaton
	// ---------- to Groups.KitchenId (see also Kitchen)
	@Valid
	@ConvertGroup(from = Default.class, to = Groups.KitchenId.class)
	@NotNull
	@ManyToOne //(fetch = FetchType.LAZY)
	@JoinColumn(name =  "kitchen_id", nullable = false)
	private Kitchen kitchen;
	
	// ---------- Since Address is embeddable
	@Embedded
	private Address address;
	
	private Boolean active = Boolean.TRUE;
	
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dateRegister;
	
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dateUpdate;
	
	// ---------- By default sufix ToMany is lazy loading
	@ManyToMany
	@JoinTable(name = "restaurant_payment_method",
		joinColumns = @JoinColumn(name = "restaurant_id"),
		inverseJoinColumns = @JoinColumn(name = "payment_method_id"))
	private List<PaymentMethod> paymentMethods = new ArrayList<>();
	
	@OneToMany(mappedBy = "restaurant")
	private List<Product> products = new ArrayList<>();
	
	public void activate() {
		setActive(true);
	}
	
	public void deactivate() {
		setActive(false);
	}
	
}
