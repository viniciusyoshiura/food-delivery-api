package com.mycompany.fooddelivery.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.fooddelivery.api.model.dto.DailySaleDTO;
import com.mycompany.fooddelivery.domain.filter.DailySaleFilter;
import com.mycompany.fooddelivery.domain.service.SaleQueryService;

@RestController
@RequestMapping(path = "/statistics")
public class StatisticsController {

	@Autowired
	private SaleQueryService saleQueryService;

	@GetMapping("/daily-sales")
	public List<DailySaleDTO> queryDailySales(DailySaleFilter filter,
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
		
		// --------- Implementation of queryDailySales uses CriteriaBuilder
		return saleQueryService.queryDailySales(filter, timeOffset);
	}

}
