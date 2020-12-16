package com.mycompany.fooddelivery.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mycompany.fooddelivery.domain.model.Kitchen;

@Repository
public interface KitchenRepository extends JpaRepository<Kitchen, Long> {

	List<Kitchen>findByName(String name);
	List<Kitchen>findByNameContaining(String name);
	
	boolean existsByName(String name);
}
