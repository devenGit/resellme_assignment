package com.resellme.assignment.catalog.dto;

import java.util.Set;

import com.resellme.assignment.product.ProductDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CatalogSaveDto {
	private String catalogTitle;
	private String catalogDescription;
	private Set<ProductDto> products;
	private Long brandId;
}
