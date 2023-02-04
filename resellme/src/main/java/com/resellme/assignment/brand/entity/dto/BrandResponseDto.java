package com.resellme.assignment.brand.entity.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandResponseDto {
	private Long brandId;
	private String brandName;
	private String lastCatalogTitle;
	private Date lastCatalogTime;
}
