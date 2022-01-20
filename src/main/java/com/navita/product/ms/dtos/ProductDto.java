package com.navita.product.ms.dtos;

import lombok.Data;

import java.util.UUID;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class ProductDto {
	private UUID id;
	@NotBlank
    private String name;
	@NotBlank
    private String description;
	@Min(value = 0L, message = "O valor deve ser positivo.")
	private Double price;
}
