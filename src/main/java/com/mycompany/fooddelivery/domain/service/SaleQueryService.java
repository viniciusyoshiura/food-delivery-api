package com.mycompany.fooddelivery.domain.service;

import java.util.List;

import com.mycompany.fooddelivery.api.model.dto.DailySaleDTO;
import com.mycompany.fooddelivery.domain.filter.DailySaleFilter;

public interface SaleQueryService {

	List<DailySaleDTO> queryDailySales(DailySaleFilter filter, String offset);
	
}
