package com.mycompany.fooddelivery.domain.service;

import com.mycompany.fooddelivery.domain.filter.DailySaleFilter;

public interface SalesReportService {

	byte[] issueDailySales(DailySaleFilter filter, String timeOffset);
	
}
