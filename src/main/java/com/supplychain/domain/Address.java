package com.supplychain.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@NotBlank
	@Column(name = "street")
	private String street;
	@NotBlank
	@Column(name = "city")
	private String city;
	@NotBlank
	@Column(name = "phoneNumber")
	private String phoneNumber;
	@NotBlank
	@Column(name = "country")
	private String country;

}
