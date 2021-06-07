package com.wandson.api.repository.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.wandson.api.model.Product;
import com.wandson.api.model.Product_;

public class ProductRepositoryImpl implements ProductRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Product> filter(String q, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
		var builder = manager.getCriteriaBuilder();
		CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
		Root<Product> root = criteria.from(Product.class);

		Predicate[] predicates = criarRestricoes(q, minPrice, maxPrice, builder, root);
		criteria.where(predicates);

		TypedQuery<Product> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, total(q, minPrice, maxPrice));
	}

	private Predicate[] criarRestricoes(String q, BigDecimal minPrice, BigDecimal maxPrice, CriteriaBuilder builder,
			Root<Product> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (StringUtils.hasText(q)) {
			String qLowerCase = "%" + q.toLowerCase() + "%";
			predicates.add(builder.or(builder.like(builder.lower(root.get(Product_.name)), qLowerCase),
					builder.like(builder.lower(root.get(Product_.description)), qLowerCase)));
		}

		if (Objects.nonNull(minPrice)) {
			predicates.add(builder.greaterThanOrEqualTo(root.get(Product_.price), minPrice));
		}

		if (Objects.nonNull(maxPrice)) {
			predicates.add(builder.lessThanOrEqualTo(root.get(Product_.price), maxPrice));
		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}

	private Long total(String q, BigDecimal minPrice, BigDecimal maxPrice) {
		var builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Product> root = criteria.from(Product.class);

		Predicate[] predicates = criarRestricoes(q, minPrice, maxPrice, builder, root);
		criteria.where(predicates);

		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

}
