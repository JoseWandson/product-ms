package com.wandson.api.resource;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wandson.api.dto.ProductDTO;
import com.wandson.api.event.ResourceCreatedEvent;
import com.wandson.api.model.Product;
import com.wandson.api.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductResource {

	@Autowired
	private ProductService productService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ApplicationEventPublisher publisher;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Product create(@Valid @RequestBody ProductDTO productDTO, HttpServletResponse response) {
		var savedProduct = productService.save(modelMapper.map(productDTO, Product.class));

		publisher.publishEvent(new ResourceCreatedEvent(this, response, savedProduct.getId()));

		return savedProduct;
	}

	@PutMapping("/{id}")
	public Product update(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
		var product = modelMapper.map(productDTO, Product.class);
		return productService.update(id, product);
	}

	@GetMapping("/{id}")
	public Product findById(@PathVariable Long id) {
		return productService.seekOrFail(id);
	}

	@GetMapping
	public List<Product> list() {
		return productService.list();
	}

	@GetMapping("/search")
	public Page<Product> search(String q, @RequestParam(value = "min_price", required = false) BigDecimal minPrice,
			@RequestParam(value = "max_price", required = false) BigDecimal maxPrice, Pageable pageable) {
		return productService.search(q, minPrice, maxPrice, pageable);
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long id) {
		productService.remove(id);
	}

}
