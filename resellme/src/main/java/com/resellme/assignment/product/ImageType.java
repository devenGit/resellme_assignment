package com.resellme.assignment.product;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class ImageType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productImageTypeId;
	private String thumbnail;
	private String normal;
	private String highRes;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
	private Product product;
}
