package com.resellme.assignment.product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
	Product findByProductCode(String productCode);
}
