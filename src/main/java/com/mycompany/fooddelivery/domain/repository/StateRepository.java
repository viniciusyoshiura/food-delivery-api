package com.mycompany.fooddelivery.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mycompany.fooddelivery.domain.model.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long>{

}
