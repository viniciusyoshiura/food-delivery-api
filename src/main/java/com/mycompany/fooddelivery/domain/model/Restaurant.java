package com.mycompany.fooddelivery.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	
	private Boolean open = Boolean.FALSE;
	
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
	private Set<PaymentMethod> paymentMethods = new HashSet<>();
	
	@OneToMany(mappedBy = "restaurant")
	private List<Product> products = new ArrayList<>();
	
	@ManyToMany
	@JoinTable(name = "restaurant_responsible_user",
		joinColumns = @JoinColumn(name = "restaurant_id"),
	    inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> responsibles = new HashSet<>(); 
	
	public void activate() {
		setActive(true);
	}
	
	public void deactivate() {
		setActive(false);
	}
	
	public boolean removePaymentMethod(PaymentMethod paymentMethod) {
		return getPaymentMethods().remove(paymentMethod);
	}
	
	public boolean insertPaymentMethod(PaymentMethod paymentMethod) {
		return getPaymentMethods().add(paymentMethod);
	}
	
	public void open() {
	    setOpen(true);
	}

	public void close() {
	    setOpen(false);
	}
	
	public boolean isOpen() {
	    return this.open;
	}

	public boolean isClosed() {
	    return !isOpen();
	}

	public boolean isDeactive() {
	    return !isActive();
	}

	public boolean isActive() {
	    return this.active;
	}

	public boolean openningAllowed() {
	    return isActive() && isClosed();
	}

	public boolean activationAllowed() {
	    return isDeactive();
	}

	public boolean deactivationAllowed() {
	    return isActive();
	}

	public boolean closureAllowed() {
	    return isOpen();
	}  
	
	public boolean removeResponsible(User user) {
	    return getResponsibles().remove(user);
	}

	public boolean addResponsible(User user) {
	    return getResponsibles().add(user);
	}
	
	public boolean acceptPaymentMethod(PaymentMethod paymentMethod) {
	    return getPaymentMethods().contains(paymentMethod);
	}

	public boolean doesNotAcceptPaymentMethod(PaymentMethod paymentMethod) {
	    return !acceptPaymentMethod(paymentMethod);
	}
}
