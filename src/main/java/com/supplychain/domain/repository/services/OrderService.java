package com.supplychain.domain.repository.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.supplychain.domain.Order;
import com.supplychain.domain.OrderItem;
import com.supplychain.domain.OrderStatus;
import com.supplychain.domain.Product;
import com.supplychain.domain.User;
import com.supplychain.repositories.OrderItemRepository;
import com.supplychain.repositories.OrderRepository;
import com.supplychain.repositories.ProductRepository;

@Service
public class OrderService {
	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;
	private final OrderItemRepository orderItemRepository;

	public OrderService(OrderRepository orderRepository, ProductRepository productRepository,
			OrderItemRepository orderProductRepository) {
		this.orderRepository = orderRepository;
		this.productRepository = productRepository;
		this.orderItemRepository = orderProductRepository;
	}

	public List<Order> findByUserEmail(String email) {
		return orderRepository.findByUserEmail(email);
	}

	@Transactional
	public List<Order> findAll() {
		return orderRepository.findAll();
	}

	public void changeStatus(OrderStatus status, Long id) {
		orderRepository.changeStatus(status, id);
	}

	public void affectManufacturer(User user, Long id) {
		orderRepository.affectManufacturer(user, id);
	}

	public void deleteById(Long id) {
		orderRepository.deleteById(id);
	}

	public Order findById(Long id) {
		return orderRepository.findById(id).orElse(null);
	}

	@Transactional
	public List<Order> getOrders(String email) {
		return orderRepository.findByUserEmail(email);
	}

	@Transactional
	public Order save(Order order) {
		Order o = orderRepository.save(order);
		for (OrderItem item : order.getOrderItems()) {
			item.setOrder(o);
			orderItemRepository.save(item);
		}
		return o;
	}

	@Transactional
	public ResponseEntity<String> deleteOrder(Long orderID) {
		try {
			Order order = orderRepository.findById(orderID).get();

			List<OrderItem> orderProducts = new ArrayList<OrderItem>();
			orderProducts = order.getOrderItems();

			// go through all products of the order
			for (int i = 0; i < orderProducts.size(); i++) {
				OrderItem orderProduct = orderProducts.get(i);
				Product product = orderProduct.getProduct();
				Product oldProduct = productRepository.findById(product.getId()).get();
				// restore the old product inventory
				oldProduct.setQuantity(product.getQuantity() + orderProduct.getQuantity());
				productRepository.save(oldProduct);
			}

			orderRepository.deleteById(orderID);
			return ResponseEntity.status(HttpStatus.CREATED).body("Order has been deleted!(" + HttpStatus.OK + ")");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This order cannot be removed!", e);
		}
	}

	@Transactional
	public ResponseEntity<String> editOrder(Order order) {
		try {
			Order newOrder = orderRepository.findById(order.getId()).get();
			newOrder.setAddress(order.getAddress());
			orderRepository.save(newOrder);
			return ResponseEntity.status(HttpStatus.CREATED).body("Order has been edited!(" + HttpStatus.OK + ")");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This order cannot be edited!", e);
		}
	}

}
