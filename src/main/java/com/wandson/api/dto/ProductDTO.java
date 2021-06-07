package com.wandson.api.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {

	@NotBlank
	private String name;

	@NotBlank
	private String description;

	@Positive
	private BigDecimal price;

}
