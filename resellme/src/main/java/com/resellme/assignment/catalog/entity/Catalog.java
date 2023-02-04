package com.resellme.assignment.catalog.entity;

import java.util.Date;
import java.util.Set;

import com.resellme.assignment.brand.entity.Brand;
import com.resellme.assignment.product.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Catalog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long catalogId;
	
	@Column(name = "title")
	private String catalogTitle;
	
	@Column(name = "description")
	private String catalogDescription;
	
	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;
	
	@OneToMany(mappedBy = "catalog")
	private Set<Product> products;
	
	@Column(name = "created_date")
	private Date createDate;
}
