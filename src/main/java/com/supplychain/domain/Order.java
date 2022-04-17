package com.supplychain.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(name = "ORDERS")
@Data
public class Order {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;

	@ManyToOne
	private User manufacturer;

	@OneToMany(mappedBy = "order", fetch = FetchType.EAGER, orphanRemoval = true)
	private List<OrderItem> orderItems = new ArrayList<OrderItem>();

	@ManyToOne
	@NotNull
	private Address address;

	@Column(name = "PRODUCTS_TOTAL")
	private Integer products_total;

	@Column(name = "PRICE_TOTAL")
	private Double price_total;

	private Date createdAt;
	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	@PrePersist
	private void setData() {
		this.setCreatedAt(new Date());
		this.setStatus(OrderStatus.NEW);
	}

}
