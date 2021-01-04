package com.mycompany.fooddelivery.domain.repository;

import java.time.OffsetDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mycompany.fooddelivery.domain.model.PaymentMethod;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long>{

	@Query("select max(dateUpdate) from PaymentMethod")
	OffsetDateTime getLastDateUpdate();
	
	@Query("select dateUpdate from dateUpdate where id = :paymentMethodId")
	OffsetDateTime getDateUpdateById(Long paymentMethodId); 
	
}
