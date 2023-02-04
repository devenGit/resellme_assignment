package com.resellme.assignment.product;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductDto {
	private String productCode;
	private Double price;
	private String imageUrl;
}
