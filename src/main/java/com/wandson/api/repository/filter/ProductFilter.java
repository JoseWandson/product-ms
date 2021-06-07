package com.wandson.api.repository.filter;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductFilter {

	private String q;
	private BigDecimal minPrice;
	private BigDecimal maxPrice;

}
