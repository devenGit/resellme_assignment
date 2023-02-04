package com.resellme.assignment.catalog.service;

import org.springframework.data.domain.Page;

import com.resellme.assignment.brand.entity.Brand;
import com.resellme.assignment.catalog.dto.CatalogResponseDto;
import com.resellme.assignment.catalog.dto.CatalogSaveDto;
import com.resellme.assignment.catalog.dto.exception.BrandNotFoundException;
import com.resellme.assignment.catalog.entity.Catalog;

public interface CatalogService {
	public CatalogResponseDto saveCatalog(CatalogSaveDto catalogSaveDto) throws BrandNotFoundException;
	public Page<Catalog> getCatalogs(int page, int size, Brand brand);
}
