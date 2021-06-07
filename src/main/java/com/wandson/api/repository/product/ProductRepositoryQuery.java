package com.wandson.api.repository.product;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wandson.api.model.Product;

public interface ProductRepositoryQuery {

	Page<Product> filter(String q, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

}
