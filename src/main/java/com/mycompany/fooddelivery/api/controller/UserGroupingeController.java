package com.mycompany.fooddelivery.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.fooddelivery.api.converter.GroupingeDTOConverter;
import com.mycompany.fooddelivery.api.model.dto.GroupingeDTO;
import com.mycompany.fooddelivery.api.openapi.controller.UserGroupingeControllerOpenApi;
import com.mycompany.fooddelivery.domain.model.User;
import com.mycompany.fooddelivery.domain.service.UserRegistrationService;

@RestController
@RequestMapping(path = "/users/{userId}/groups", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserGroupingeController implements UserGroupingeControllerOpenApi{

	@Autowired
    private UserRegistrationService userRegistrationService;
    
    @Autowired
    private GroupingeDTOConverter groupingeDTOConverter;
    
    @GetMapping
    public List<GroupingeDTO> list(@PathVariable Long userId) {
        User user = userRegistrationService.searchOrFail(userId);
        
        return groupingeDTOConverter.toCollectionModel(user.getGroupinges());
    }
    
    @DeleteMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disassociate(@PathVariable Long userId, @PathVariable Long groupId) {
        userRegistrationService.disassociateGroup(userId, groupId);
    }
    
    @PutMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associate(@PathVariable Long userId, @PathVariable Long groupId) {
        userRegistrationService.associateGroup(userId, groupId);
    } 
	
}
