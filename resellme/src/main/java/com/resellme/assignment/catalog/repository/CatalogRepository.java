package com.resellme.assignment.catalog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.resellme.assignment.brand.entity.Brand;
import com.resellme.assignment.catalog.entity.Catalog;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Long> {
	Page<Catalog> findByBrand(Brand brand, Pageable page);
}
