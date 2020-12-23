package com.mycompany.fooddelivery.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mycompany.fooddelivery.domain.model.Product;
import com.mycompany.fooddelivery.domain.model.ProductPhoto;
import com.mycompany.fooddelivery.domain.model.Restaurant;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryQueries {

	@Query("from Product where restaurant.id = :restaurantId and id = :productId")
	Optional<Product> findById(@Param("restaurantId") Long restaurantId, @Param("productId") Long productId);

	List<Product> findByRestaurant(Restaurant restaurant);

	@Query("from Product p where p.active = true and p.restaurant = :restaurant")
	List<Product> findActivesByRestaurant(Restaurant restaurant);

	@Query("select f from ProductPhoto f join f.product p "
			+ "where p.restaurant.id = :restaurantId and f.product.id = :productId")
	Optional<ProductPhoto> findPhotoById(Long restaurantId, Long productId);
}
