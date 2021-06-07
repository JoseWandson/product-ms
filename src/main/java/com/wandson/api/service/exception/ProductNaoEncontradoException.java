package com.wandson.api.service.exception;

public class ProductNaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ProductNaoEncontradoException(Long productId) {
		this(String.format("There is no product registration with id %d", productId));
	}

	public ProductNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

}
