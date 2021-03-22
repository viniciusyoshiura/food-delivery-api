package com.mycompany.fooddelivery.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.fooddelivery.api.v1.HateoasLinks;
import com.mycompany.fooddelivery.api.v1.converter.GroupingeDTOConverter;
import com.mycompany.fooddelivery.api.v1.model.dto.GroupingeDTO;
import com.mycompany.fooddelivery.api.v1.openapi.controller.UserGroupingeControllerOpenApi;
import com.mycompany.fooddelivery.domain.model.User;
import com.mycompany.fooddelivery.domain.service.UserRegistrationService;

@RestController
@RequestMapping(path = "/v1/users/{userId}/groups", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserGroupingeController implements UserGroupingeControllerOpenApi{

	@Autowired
    private UserRegistrationService userRegistrationService;
    
    @Autowired
    private GroupingeDTOConverter groupingeDTOConverter;
    
    @Autowired
    private HateoasLinks hateoasLinks;  
    
    @GetMapping
    public CollectionModel<GroupingeDTO> list(@PathVariable Long userId) {
//        User user = userRegistrationService.searchOrFail(userId);
//        
//        return groupingeDTOConverter.toCollectionModel(user.getGroupinges());
    	
    	User user = userRegistrationService.searchOrFail(userId);
        
        CollectionModel<GroupingeDTO> groupDTOs = groupingeDTOConverter.toCollectionModel(user.getGroupinges())
                .removeLinks()
                .add(hateoasLinks.linkToUserGroupingeAssociation(userId, "associate"));
        
        groupDTOs.getContent().forEach(groupDTO -> {
        	groupDTO.add(hateoasLinks.linkToUserGroupingeDisassociation(
        			userId, groupDTO.getId(), "disassociation"));
        });
        
        return groupDTOs;
    }
    
    @DeleteMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> disassociate(@PathVariable Long userId, @PathVariable Long groupId) {
        userRegistrationService.disassociateGroup(userId, groupId);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associate(@PathVariable Long userId, @PathVariable Long groupId) {
        userRegistrationService.associateGroup(userId, groupId);
        return ResponseEntity.noContent().build();
    } 
	
}
