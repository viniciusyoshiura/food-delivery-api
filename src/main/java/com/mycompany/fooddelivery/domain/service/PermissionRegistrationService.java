package com.mycompany.fooddelivery.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.fooddelivery.domain.exception.PermissionNotFoundException;
import com.mycompany.fooddelivery.domain.model.Permission;
import com.mycompany.fooddelivery.domain.repository.PermissionRepository;

@Service
public class PermissionRegistrationService {

	@Autowired
    private PermissionRepository permissionRepository;
    
    public Permission searchOrFail(Long permissionId) {
        return permissionRepository.findById(permissionId)
            .orElseThrow(() -> new PermissionNotFoundException(permissionId));
    }
	
}
