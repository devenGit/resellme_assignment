package com.resellme.assignment.product;

import com.resellme.assignment.catalog.entity.Catalog;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;
	
	@Column(name = "product_code")
	private String productCode;
	
	private Double price;
	
	@OneToOne(mappedBy = "product", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
	private ImageType imageType;
	
	@ManyToOne
	@JoinColumn(name = "catalog_id")
	private Catalog catalog;
	
}
