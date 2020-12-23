package com.mycompany.fooddelivery.domain.infrastructure.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;

import com.mycompany.fooddelivery.api.model.dto.DailySaleDTO;
import com.mycompany.fooddelivery.domain.filter.DailySaleFilter;
import com.mycompany.fooddelivery.domain.model.PurchaseOrder;
import com.mycompany.fooddelivery.domain.model.enumerator.StatusPurchaseOrder;
import com.mycompany.fooddelivery.domain.service.SaleQueryService;

@Repository
public class SaleQueryServiceImpl implements SaleQueryService {

	@PersistenceContext
	private EntityManager manager;
	
	/*
	 * select date(p.date_register) as date_register, count(p.id) as total_sales, sum(p.total_value) as total_billed from purchase_order p group by date(p.date_register)
	 */
	
	@Override
	public List<DailySaleDTO> queryDailySales(DailySaleFilter filter, String timeOffset) {
		// ---------- CriteriaBuilder builds aggregation functions among others functionalities
		var builder = manager.getCriteriaBuilder();
		
		// ---------- Creating a criteriaBuilder with a return class (DailySaleDTO)
		var query = builder.createQuery(DailySaleDTO.class);
		
		// ---------- from purchase_order p
		var root = query.from(PurchaseOrder.class);
		
		// ---------- predicates to be used in where
		var predicates = new ArrayList<Predicate>();
		
		// ---------- convert_tz(p.date_register, '+00:00', '-03:00')
		var functionConvertTzDateRegister = builder.function(
				"convert_tz", Date.class, root.get("dateRegister"),
				builder.literal("+00:00"), builder.literal(timeOffset));
		
		// ---------- date(convert_tz(p.date_register, '+00:00', '-03:00'))
		var functionDateDateRegister = builder.function(
				"date", Date.class, functionConvertTzDateRegister);
		
		// ---------- select date(convert_tz(p.date_register, '+00:00', '-03:00')) as date_register, count(p.id) as total_sales, sum(p.total_value)
		// ---------- builder constructs DailySaleDTO (using constructor) from selection
		var selection = builder.construct(DailySaleDTO.class,
				functionDateDateRegister,
				builder.count(root.get("id")),
				builder.sum(root.get("totalValue")));
		
		if (filter.getRestaurantId() != null) {
			// ---------- where restaurant.id = :restauranId
			predicates.add(builder.equal(root.get("restaurant"), filter.getRestaurantId()));
		}
	      
		if (filter.getDateRegisterStart() != null) {
			// ---------- where dateRegister >= :dateRegisterStart
			predicates.add(builder.greaterThanOrEqualTo(root.get("dateRegister"), 
					filter.getDateRegisterStart()));
		}

		if (filter.getDateRegisterEnd() != null) {
			// ---------- where dateRegister <= :dateRegisterEnd
			predicates.add(builder.lessThanOrEqualTo(root.get("dateRegister"), 
					filter.getDateRegisterEnd()));
		}
	    
		// ---------- where status IN('CONFIRMED', 'DELIVERED')
		predicates.add(root.get("status").in(
				StatusPurchaseOrder.CONFIRMED, StatusPurchaseOrder.DELIVERED));
		
		// ---------- Projecting selection
		query.select(selection);
		
		// ---------- Setting predicates (where)
		query.where(predicates.toArray(new Predicate[0]));
		
		// ---------- order by date(p.date_register) asc
		List<Order> orderList = new ArrayList<>();
		orderList.add(builder.asc(functionDateDateRegister));

		query.orderBy(orderList);
		
		// ---------- group by date(p.date_register)
		query.groupBy(functionDateDateRegister);
		
		
		return manager.createQuery(query).getResultList();
	}
	
}
