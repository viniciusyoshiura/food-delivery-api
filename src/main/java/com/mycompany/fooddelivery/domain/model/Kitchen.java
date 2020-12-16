package com.mycompany.fooddelivery.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.mycompany.fooddelivery.core.validation.Groups;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonRootName("gastronomy")
@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
//@Table(name = "kitchen_table")
public class Kitchen {
	
	@NotNull(groups = Groups.KitchenId.class)
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//@Column(name="nam_kitchen")
//	@JsonProperty(value = "title")
	@NotBlank
	private String name;
	
	@OneToMany(mappedBy = "kitchen")
	private List<Restaurant> restaurants = new ArrayList<>();
}
