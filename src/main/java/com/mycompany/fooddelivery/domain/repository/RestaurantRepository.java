package com.mycompany.fooddelivery.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mycompany.fooddelivery.domain.model.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{
	
	// ---------- Use this strategy to load all entities with one select
//	@Query("from Restaurant r join fetch r.kitchen left join fetch r.paymentMethods")
//	List<Restaurant> findAll();
	
	List<Restaurant> findByShippingFeeBetween(BigDecimal initialFee, BigDecimal endFee);
	
	// ---------- See orm.xml	
	//@Query("from Restaurant where name like %:name% and kitchen.id = :id") 
	List<Restaurant> findByNameAndKitchenId(String name, @Param("id") Long kitchenId);
	
	List<Restaurant> findByNameContainingAndKitchenId(String name, Long kitchenId);
	Optional<Restaurant> findFirstRestaurantByNameContaining(String name);
	List<Restaurant> findTop2ByNameContaining(String name);
	
	int countByKitchenId(Long kitchenId);
}
