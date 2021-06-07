package com.wandson.api.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wandson.api.model.Product;
import com.wandson.api.model.Product_;
import com.wandson.api.repository.ProductRepository;
import com.wandson.api.service.exception.ProductNaoEncontradoException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public Product save(Product product) {
		return productRepository.save(product);
	}

	public Product update(Long id, Product product) {
		var savedProduct = seekOrFail(id);

		BeanUtils.copyProperties(product, savedProduct, Product_.id.getName());

		return productRepository.save(savedProduct);
	}

	public Product seekOrFail(Long id) {
		return productRepository.findById(id).orElseThrow(() -> new ProductNaoEncontradoException(id));
	}

	public List<Product> list() {
		return productRepository.findAll();
	}

	public Page<Product> search(String q, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
		return productRepository.filter(q, minPrice, maxPrice, pageable);
	}

	public void remove(Long id) {
		try {
			productRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ProductNaoEncontradoException(id);
		}
	}

}
