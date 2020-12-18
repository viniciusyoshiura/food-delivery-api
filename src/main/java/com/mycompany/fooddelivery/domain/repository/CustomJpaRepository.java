package com.mycompany.fooddelivery.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.mycompany.fooddelivery.domain.model.User;

@NoRepositoryBean
public interface CustomJpaRepository<T, ID> extends JpaRepository<T, ID> {

	Optional<T> searchFirst();
	
	void detach(User user);
	
}
