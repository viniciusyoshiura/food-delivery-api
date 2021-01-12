package com.mycompany.fooddelivery.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.fooddelivery.api.converter.GroupingeDTOConverter;
import com.mycompany.fooddelivery.api.deconverter.GroupingeInputDeconverter;
import com.mycompany.fooddelivery.api.model.dto.GroupingeDTO;
import com.mycompany.fooddelivery.api.model.input.GroupingeInput;
import com.mycompany.fooddelivery.api.openapi.controller.GroupingeControllerOpenApi;
import com.mycompany.fooddelivery.domain.model.Groupinge;
import com.mycompany.fooddelivery.domain.repository.GroupingeRepository;
import com.mycompany.fooddelivery.domain.service.GroupingeRegistrationService;

@RestController
@RequestMapping(path = "/groups", produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupingeController implements GroupingeControllerOpenApi {

	@Autowired
    private GroupingeRepository groupingeRepository;
    
    @Autowired
    private GroupingeRegistrationService groupingeRegistrationService;
    
    @Autowired
    private GroupingeDTOConverter groupingeDTOConverter;
    
    @Autowired
    private GroupingeInputDeconverter groupingeInputDeconverter;
    
    @GetMapping
    public List<GroupingeDTO> list() {
        List<Groupinge> allGroups = groupingeRepository.findAll();
        
        return groupingeDTOConverter.toCollectionModel(allGroups);
    }
    
    @GetMapping("/{groupingeId}")
    public GroupingeDTO search(@PathVariable Long groupingeId) {
    	Groupinge groupinge = groupingeRegistrationService.searchOrFail(groupingeId);
        
        return groupingeDTOConverter.toModel(groupinge);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GroupingeDTO insert(@RequestBody @Valid GroupingeInput groupingeInput) {
    	Groupinge groupinge = groupingeInputDeconverter.toDomainObject(groupingeInput);
        
    	groupinge = groupingeRegistrationService.save(groupinge);
        
        return groupingeDTOConverter.toModel(groupinge);
    }
    
    @PutMapping("/{groupingeId}")
    public GroupingeDTO update(@PathVariable Long groupingeId,
            @RequestBody @Valid GroupingeInput groupingeInput) {
    	Groupinge currentGroupinge = groupingeRegistrationService.searchOrFail(groupingeId);
        
        groupingeInputDeconverter.copyToDomainObject(groupingeInput, currentGroupinge);
        
        currentGroupinge = groupingeRegistrationService.save(currentGroupinge);
        
        return groupingeDTOConverter.toModel(currentGroupinge);
    }
    
    @DeleteMapping("/{groupingeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long groupingeId) {
        groupingeRegistrationService.remove(groupingeId);	
    }   
	
}
