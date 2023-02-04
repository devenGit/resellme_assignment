package com.resellme.assignment.catalog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.resellme.assignment.brand.entity.Brand;
import com.resellme.assignment.brand.repository.BrandRepository;
import com.resellme.assignment.catalog.dto.CatalogResponseDto;
import com.resellme.assignment.catalog.dto.CatalogSaveDto;
import com.resellme.assignment.catalog.dto.exception.BrandNotFoundException;
import com.resellme.assignment.catalog.entity.Catalog;
import com.resellme.assignment.catalog.service.CatalogService;

@RestController(value = "/catalog")
public class CatalogController {
	
	@Autowired
	private CatalogService catalogService;
	
	@Autowired
	private BrandRepository brandRepository;
	
	@PostMapping("/upload")
	public ResponseEntity<CatalogResponseDto> saveCatalog(@RequestBody CatalogSaveDto catalogDto, @RequestParam MultipartFile file) throws BrandNotFoundException {
		CatalogResponseDto response = catalogService.saveCatalog(catalogDto);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@GetMapping(path = "/{brandId}",params = {"page", "size"})
	public ResponseEntity<List<Catalog>> getCatalogs(@RequestParam int page, @RequestParam int size, @PathVariable Long brandId) {
		Optional<Brand> brand = brandRepository.findById(brandId);
		if(brand.isPresent()) {
			Page<Catalog> result = catalogService.getCatalogs(page, size, brand.get());
			return new ResponseEntity<>(result.getContent(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
