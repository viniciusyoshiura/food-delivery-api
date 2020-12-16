package com.mycompany.fooddelivery.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mycompany.fooddelivery.domain.model.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
	
}
