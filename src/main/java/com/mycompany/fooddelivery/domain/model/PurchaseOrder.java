package com.mycompany.fooddelivery.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import com.mycompany.fooddelivery.domain.model.enumerator.StatusPurchaseOrder;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class PurchaseOrder {

	@EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private BigDecimal subtotal;
    private BigDecimal shippingFee;
    private BigDecimal totalValue;
	
    @Embedded
    private Address deliverAddress;
    
    private StatusPurchaseOrder status;
    
    @CreationTimestamp
    private OffsetDateTime dateRegister;

    private OffsetDateTime dateConfirmation;
    private OffsetDateTime dateCancellation;
    private OffsetDateTime dateDelivery;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private PaymentMethod paymentMethod;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurant restaurant;
    
    @ManyToOne
    @JoinColumn(name = "user_client_id", nullable = false)
    private User user;
    
    @OneToMany(mappedBy = "purchaseOrder")
    private List<ItemPurchaseOrder> items = new ArrayList<>();
}
