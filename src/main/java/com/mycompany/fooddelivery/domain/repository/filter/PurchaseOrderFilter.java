package com.mycompany.fooddelivery.domain.repository.filter;

import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PurchaseOrderFilter {
	
	private String uuid;
	private Long userId;
	private Long restaurantId;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime dataRegisterStart;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime dataRegisterEnd;
	
}
