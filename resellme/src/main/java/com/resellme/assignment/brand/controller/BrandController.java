package com.resellme.assignment.brand.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resellme.assignment.brand.entity.dto.BrandResponseDto;
import com.resellme.assignment.brand.service.BrandService;

@RestController
public class BrandController {
	@Autowired
	private BrandService brandService;

	@GetMapping("/brand")
	public ResponseEntity<List<BrandResponseDto>> getBrands() {
		return new ResponseEntity<List<BrandResponseDto>>(brandService.getBrandsSortedByLastCatalog(), HttpStatus.OK);
	}
}
