package com.mycompany.fooddelivery.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import com.mycompany.fooddelivery.domain.event.PurchaseOrderCanceledEvent;
import com.mycompany.fooddelivery.domain.event.PurchaseOrderConfirmedEvent;
import com.mycompany.fooddelivery.domain.event.PurchaseOrderDeliveredEvent;
import com.mycompany.fooddelivery.domain.exception.BusinessException;
import com.mycompany.fooddelivery.domain.model.enumerator.StatusPurchaseOrder;

import lombok.Data;
import lombok.EqualsAndHashCode;

// ---------- AbstractAggregateRoot is used to register an event
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
public class PurchaseOrder extends AbstractAggregateRoot<PurchaseOrder>{

	@EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String uuid;
	
	private BigDecimal subtotal;
    private BigDecimal shippingFee;
    private BigDecimal totalValue;
	
    @Embedded
    private Address deliverAddress;
    
    @Enumerated(EnumType.STRING)
    private StatusPurchaseOrder status = StatusPurchaseOrder.CREATED;
    
    @CreationTimestamp
    private OffsetDateTime dateRegister;

    private OffsetDateTime dateConfirmation;
    private OffsetDateTime dateCancellation;
    private OffsetDateTime dateDelivery;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private PaymentMethod paymentMethod;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurant restaurant;
    
    @ManyToOne
    @JoinColumn(name = "user_client_id", nullable = false)
    private User user;
    
    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL)
    private List<ItemPurchaseOrder> items = new ArrayList<>();
    
    public void calculateTotalValue() {
    	
    	getItems().forEach(ItemPurchaseOrder::calculateTotalPrice);
    	
        this.subtotal = getItems().stream()
            .map(item -> item.getTotalPrice())
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        this.totalValue = this.subtotal.add(this.shippingFee);
    }

    public void defineShippingFee() {
        setShippingFee(getRestaurant().getShippingFee());
    }

    public void assignOrderToItems() {
        getItems().forEach(item -> item.setPurchaseOrder(this));
    }
    
    public void confirm() {
		setStatus(StatusPurchaseOrder.CONFIRMED);
		setDateConfirmation(OffsetDateTime.now());
		
		// ---------- Registering the event to be triggered when the PurchaseOrder is confirmed
		registerEvent(new PurchaseOrderConfirmedEvent(this));
	}
	
	public void deliver() {
		setStatus(StatusPurchaseOrder.DELIVERED);
		setDateDelivery(OffsetDateTime.now());
		
		// ---------- Registering the event to be triggered when the PurchaseOrder is delivered
		registerEvent(new PurchaseOrderDeliveredEvent(this));
	}
	
	public void cancel() {
		setStatus(StatusPurchaseOrder.CANCELLED);
		setDateCancellation(OffsetDateTime.now());
		
		// ---------- Registering the event to be triggered when the PurchaseOrder is canceled
		registerEvent(new PurchaseOrderCanceledEvent(this));
	}
	
	private void setStatus(StatusPurchaseOrder newStatusPurchaseOrder) {
		if (getStatus().cannotUpdateTo(newStatusPurchaseOrder)) {
			throw new BusinessException(
					String.format("Order status with uuid %s could not be updated from %s to %s",
							getUuid(), getStatus().getDescription(), 
							newStatusPurchaseOrder.getDescription()));
		}
		
		this.status = newStatusPurchaseOrder;
	}
	
	public boolean canBeConfirmed() {
		return getStatus().canUpdateTo(StatusPurchaseOrder.CONFIRMED);
	}
	
	public boolean canBeDelivered() {
		return getStatus().canUpdateTo(StatusPurchaseOrder.DELIVERED);
	}
	
	public boolean canBeCancelled() {
		return getStatus().canUpdateTo(StatusPurchaseOrder.CANCELLED);
	}
	
	@PrePersist
	private void generateUuid() {
		setUuid(UUID.randomUUID().toString());
	}
}
