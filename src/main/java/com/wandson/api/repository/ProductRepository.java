package com.wandson.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wandson.api.model.Product;
import com.wandson.api.repository.product.ProductRepositoryQuery;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryQuery {

}
