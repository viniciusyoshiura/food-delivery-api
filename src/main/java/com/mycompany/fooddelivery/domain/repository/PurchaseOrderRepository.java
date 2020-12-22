package com.mycompany.fooddelivery.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mycompany.fooddelivery.domain.model.PurchaseOrder;

// ---------- JpaSpecificationExecutor is used to create filter specs
@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long>, JpaSpecificationExecutor<PurchaseOrder> {

	@Query("from PurchaseOrder p join fetch p.user join fetch p.restaurant r join fetch r.kitchen")
	List<PurchaseOrder> findAll();
	
	Optional<PurchaseOrder> findByUuid(String uuid);
	
}
