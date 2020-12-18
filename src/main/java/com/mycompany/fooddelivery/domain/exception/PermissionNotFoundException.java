package com.mycompany.fooddelivery.domain.exception;

public class PermissionNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public PermissionNotFoundException(String message) {
        super(message);
    }
    
    public PermissionNotFoundException(Long permissionId) {
        this(String.format("Theres is no permission registration with code %d", permissionId));
    }   

}
