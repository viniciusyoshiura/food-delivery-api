package com.mycompany.fooddelivery.domain.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class ItemPurchaseOrder {

	@EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private Integer quantity;
    private String observation;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Product product;
    
}
