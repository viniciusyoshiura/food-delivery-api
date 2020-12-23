package com.mycompany.fooddelivery.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class ProductPhoto {

	@EqualsAndHashCode.Include
	@Id
	// ---------- The primary key is from product, so use @Column
	@Column(name = "product_id")
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	// ---------- MapsId - product is mapped by id of product entity
	@MapsId
	private Product product;
	
	private String fileName;
	private String description;
	private String contentType;
	private Long size;
	
	public Long getRestaurantId() {
		if (getProduct() != null) {
			return getProduct().getRestaurant().getId();
		}
		
		return null;
	}
}
