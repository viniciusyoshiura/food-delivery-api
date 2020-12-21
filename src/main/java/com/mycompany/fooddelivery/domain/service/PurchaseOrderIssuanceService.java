package com.mycompany.fooddelivery.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.fooddelivery.domain.exception.BusinessException;
import com.mycompany.fooddelivery.domain.exception.PurchaseOrderNotFoundException;
import com.mycompany.fooddelivery.domain.model.City;
import com.mycompany.fooddelivery.domain.model.PaymentMethod;
import com.mycompany.fooddelivery.domain.model.Product;
import com.mycompany.fooddelivery.domain.model.PurchaseOrder;
import com.mycompany.fooddelivery.domain.model.Restaurant;
import com.mycompany.fooddelivery.domain.model.User;
import com.mycompany.fooddelivery.domain.repository.PurchaseOrderRepository;

@Service
public class PurchaseOrderIssuanceService {
	
	private static final String MSG_PAYMENT_METHOD_NOT_ACCEPTABLE = "Payment method '%s' is not acceptable for this restaurant";
	
	@Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    
	@Autowired
	private RestaurantRegistrationService restaurantRegistrationService;

	@Autowired
	private CityRegistrationService cityRegistrationService;

	@Autowired
	private UserRegistrationService userRegistrationService;

	@Autowired
	private ProductRegistrationService productRegistrationService;

	@Autowired
	private PaymentMethodRegistrationService paymentMethodRegistrationService;
	
    public PurchaseOrder searchOrFail(Long purchaseOrderId) {
        return purchaseOrderRepository.findById(purchaseOrderId)
            .orElseThrow(() -> new PurchaseOrderNotFoundException(purchaseOrderId));
    }   

    @Transactional
    public PurchaseOrder save(PurchaseOrder purchaseOrder) {
        validatePurchaseOrder(purchaseOrder);
        validateItems(purchaseOrder);

        purchaseOrder.setShippingFee(purchaseOrder.getRestaurant().getShippingFee());
        purchaseOrder.calculateTotalValue();

        return purchaseOrderRepository.save(purchaseOrder);
    }

    private void validatePurchaseOrder(PurchaseOrder purchaseOrder) {
        City city = cityRegistrationService.searchOrFail(purchaseOrder.getDeliverAddress().getCity().getId());
        User user = userRegistrationService.searchOrFail(purchaseOrder.getUser().getId());
        Restaurant restaurant = restaurantRegistrationService.searchOrFail(purchaseOrder.getRestaurant().getId());
        PaymentMethod paymentMethod = paymentMethodRegistrationService.searchOrFail(purchaseOrder.getPaymentMethod().getId());

        purchaseOrder.getDeliverAddress().setCity(city);
        purchaseOrder.setUser(user);
        purchaseOrder.setRestaurant(restaurant);
        purchaseOrder.setPaymentMethod(paymentMethod);
        
        if (restaurant.doesNotAcceptPaymentMethod(paymentMethod)) {
            throw new BusinessException(String.format(MSG_PAYMENT_METHOD_NOT_ACCEPTABLE,
            		paymentMethod.getDescription()));
        }
    }

    private void validateItems(PurchaseOrder purchaseOrder) {
    	purchaseOrder.getItems().forEach(item -> 
    	{
            Product product = productRegistrationService.searchOrFail(
            		purchaseOrder.getRestaurant().getId(), item.getProduct().getId());
            
            item.setPurchaseOrder(purchaseOrder);
            item.setProduct(product);
            item.setUnitPrice(product.getPrice());
        });
    }
    
}
