package com.mycompany.fooddelivery.api.model.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class DailySaleDTO {

	private Date date;
	private Long totalSales;
	private BigDecimal totalBilled;
	
}
