package com.mycompany.fooddelivery.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mycompany.fooddelivery.domain.model.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long>{

}
