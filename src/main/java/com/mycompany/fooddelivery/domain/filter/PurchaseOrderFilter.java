package com.mycompany.fooddelivery.domain.filter;

import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PurchaseOrderFilter {
	
	@ApiModelProperty(example = "47172b2b-bf75-461d-b4c1-73f69ca30434", value = "Order Uuid")
	private String uuid;
	
	@ApiModelProperty(example = "1", value = "Client ID for search filtering")
	private Long userId;
	
	@ApiModelProperty(example = "1", value = "Restaurant ID for search filtering")
	private Long restaurantId;
	
	@ApiModelProperty(example = "2021-01-01T00:00:00Z",
	        value = "Date time start for search filtering")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime dataRegisterStart;
	
	@ApiModelProperty(example = "2021-01-01T23:59:59Z",
	        value = "Date time end for search filtering")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime dataRegisterEnd;
	
}
