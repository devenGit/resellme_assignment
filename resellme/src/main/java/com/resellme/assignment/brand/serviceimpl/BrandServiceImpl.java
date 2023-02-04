package com.resellme.assignment.brand.serviceimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resellme.assignment.brand.entity.Brand;
import com.resellme.assignment.brand.entity.dto.BrandResponseDto;
import com.resellme.assignment.brand.repository.BrandRepository;
import com.resellme.assignment.brand.service.BrandService;

@Service
public class BrandServiceImpl implements BrandService {
	
	@Autowired
	private BrandRepository brandRepository;
	
	@Override
	public List<BrandResponseDto> getBrandsSortedByLastCatalog() {
		List<Brand> brands = brandRepository.findAll();
		List<BrandResponseDto> response = new ArrayList<>();
		for(Brand brand : brands) {
			BrandResponseDto dto = new BrandResponseDto();
			dto.setBrandId(brand.getBrandId());
			dto.setBrandName(brand.getBrandName());
			dto.setLastCatalogTime(brand.getCatalogs().get(0).getCreateDate());
			dto.setLastCatalogTitle(brand.getCatalogs().get(0).getCatalogTitle());
			response.add(dto);
		}
		Collections.sort(response, Comparator.comparing(BrandResponseDto::getLastCatalogTime));
		return response;
	}

}
