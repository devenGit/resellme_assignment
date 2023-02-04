package com.resellme.assignment.brand.service;

import java.util.List;

import com.resellme.assignment.brand.entity.dto.BrandResponseDto;

public interface BrandService {
	public List<BrandResponseDto> getBrandsSortedByLastCatalog();
}
