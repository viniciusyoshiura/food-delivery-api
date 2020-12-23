package com.mycompany.fooddelivery.domain.infrastructure.repositories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.mycompany.fooddelivery.domain.model.ProductPhoto;
import com.mycompany.fooddelivery.domain.repository.ProductRepositoryQueries;

@Repository
public class ProductRepositoryQueriesImpl implements ProductRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;

	@Transactional
	@Override
	public ProductPhoto save(ProductPhoto productPhoto) {
		return manager.merge(productPhoto);
	}
	
//	@Transactional
//	@Override
//	public void delete(ProductPhoto productPhoto) {
//		manager.remove(productPhoto);
//	}
	
}
