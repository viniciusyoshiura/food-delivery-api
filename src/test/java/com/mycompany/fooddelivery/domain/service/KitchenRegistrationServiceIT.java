package com.mycompany.fooddelivery.domain.service;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mycompany.fooddelivery.domain.exception.EntityInUseException;
import com.mycompany.fooddelivery.domain.exception.KitchenNotFoundException;
import com.mycompany.fooddelivery.domain.model.Kitchen;

// ---------- Sufix -IT to be executed with plugin 'maven-failsafe-plugin'
@RunWith(SpringRunner.class)
@SpringBootTest
public class KitchenRegistrationServiceIT {

	@Autowired
	private KitchenRegistrationService kitchenRegistrationService;

	@Test
	public void kitchenRegistrationSuccess() {
		// given
		Kitchen kitchen = new Kitchen();
		kitchen.setName("Chinesa");
		
		// then
		kitchen = kitchenRegistrationService.save(kitchen);
		
		// assert
		assertThat(kitchen).isNotNull();
		assertThat(kitchen.getId()).isNotNull();
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void kitchenRegistrationWithoutName() {
		Kitchen kitchen = new Kitchen();
		kitchen.setName(null);
		
		kitchen = kitchenRegistrationService.save(kitchen);
	}
	
	@Test(expected = EntityInUseException.class)
    public void removeKitchenInUse() {
		kitchenRegistrationService.remove(1L);
    }
	
	@Test(expected = KitchenNotFoundException.class)
    public void removeNonexistentKitchen() {
		kitchenRegistrationService.remove(100L);
    }       
    
	
}
