package com.resellme.assignment.brand.entity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class BrandCataLogDto {
	private Long catalogId;
	
	private String catalogTitle;
	
	private String catalogDescription;
	
	private Long brandId;
}
