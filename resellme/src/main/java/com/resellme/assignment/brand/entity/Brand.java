package com.resellme.assignment.brand.entity;

import java.util.Date;
import java.util.List;

import com.resellme.assignment.catalog.entity.Catalog;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Brand {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long brandId;
	
	@Column(name = "name")
	private String brandName;

	@Column(name = "created_date")
	private Date createDate;
	
	@OneToMany(mappedBy = "brand")
	private List<Catalog> catalogs;
}
