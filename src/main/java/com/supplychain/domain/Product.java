package com.supplychain.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(name = "PRODUCTS")
@Data
public class Product {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "TITLE", nullable = false)
	@NotBlank
	private String title;
	@NotBlank
	@Column(name = "IMAGE", nullable = true)
	private String image;

	@Column(name = "PRICE", nullable = false)
	@NotNull
	@Min(value = 0)
	private Double price;

	@Column(name = "QUANTITY", nullable = false)
	@Min(value = 0)
	private Integer quantity;

	@ManyToOne
	@NotNull
	private Customer customer;
}
