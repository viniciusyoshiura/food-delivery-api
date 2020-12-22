package com.mycompany.fooddelivery.domain.infrastructure.specs;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.mycompany.fooddelivery.domain.model.PurchaseOrder;
import com.mycompany.fooddelivery.domain.repository.filter.PurchaseOrderFilter;

public class PurchaseOrderSpecs {

	public static Specification<PurchaseOrder> usingFilter(PurchaseOrderFilter filter) {
		
		// ---------- root is the entity (PurchaseOrder)
		// ---------- builder is used to build predicates
		return (root, query, builder) -> {
			
			// ---------- Fetch solves the problem of N+1 queries of nested entities
			root.fetch("restaurant").fetch("kitchen");
			root.fetch("user");
			
			var predicates = new ArrayList<Predicate>();
			
			if (filter.getUserId() != null) {
				predicates.add(builder.equal(root.get("user"), filter.getUserId()));
			}
			
			if (filter.getRestaurantId() != null) {
				predicates.add(builder.equal(root.get("restaurant"), filter.getRestaurantId()));
			}
			
			if (filter.getDataRegisterStart() != null) {
				predicates.add(builder.greaterThanOrEqualTo(root.get("dateRegister"), 
						filter.getDataRegisterStart()));
			}
			
			if (filter.getDataRegisterEnd() != null) {
				predicates.add(builder.lessThanOrEqualTo(root.get("dateRegister"), 
						filter.getDataRegisterEnd()));
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
	
}
