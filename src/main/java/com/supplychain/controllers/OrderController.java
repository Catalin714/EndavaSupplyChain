package com.supplychain.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.supplychain.domain.Order;
import com.supplychain.domain.OrderItem;
import com.supplychain.domain.OrderStatus;
import com.supplychain.domain.User;
import com.supplychain.domain.repository.services.AddressService;
import com.supplychain.domain.repository.services.OrderService;
import com.supplychain.domain.repository.services.UserService;

@Controller
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private UserService userService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private AddressService addressService;

	User user;

	@ModelAttribute
	public void putInModel(Model model, Principal p, HttpServletRequest request) {
		if (p != null) {
			user = (User) request.getSession().getAttribute("connectedUser");
			if (user == null) {
				user = userService.getUser(p.getName());
				request.getSession().setAttribute("connectedUser", user);
			} else {
				request.getSession().setAttribute("connectedUser", user);
			}
		}

	}

	@GetMapping
	public String clients(Model model, Principal p) {
		model.addAttribute("orders", orderService.findAll());
		return "orders";
	}

	@GetMapping("/create")
	public String showFormForAdd(Model model) {

		Order order = new Order();
		model.addAttribute("order", order);
		model.addAttribute("addresses", addressService.findAll());
		return "add_order";
	}

	@PostMapping("/create")
	public String saveCustomer(@Valid @ModelAttribute("order") Order order, BindingResult result, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("order", order);
			model.addAttribute("addresses", addressService.findAll());
			model.addAttribute("order", order);
			return "add_order";
		}
		orderService.save(order);

		return "redirect:/orders";
	}

	@GetMapping("/change-status/{id}")
	public String changeStatus(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {

		Order oreder = orderService.findById(id);
		double total = 0;
		if (oreder != null) {
			for (OrderItem item : oreder.getOrderItems()) {
				total += item.getQuantity() * item.getProduct().getPrice();
			}

			model.addAttribute("order", oreder);
		} else {

		}
		model.addAttribute("statusList", OrderStatus.values());
		model.addAttribute("addresses", addressService.findAll());
		model.addAttribute("total", total);
		return "order_status";
	}

	@PostMapping("/change-status")
	public String changeStatus(HttpServletRequest request, Model model, @Valid @ModelAttribute("order") Order myorder,
			BindingResult result) {

		Order order = orderService.findById(myorder.getId());
		orderService.changeStatus(myorder.getStatus(), order.getId());
		if (myorder.getStatus().equals(OrderStatus.IN_PROGRESS)) {
			orderService.affectManufacturer(user, order.getId());
		}

		return "redirect:/orders";
	}

	@GetMapping("/update/{id}")
	public String showFormForUpdate(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
		Order order = null;
		if (id != null) {

			order = orderService.findById(id);
			model.addAttribute("order", order);
		}

		return "add_order";
	}

	@GetMapping("/cancel/{id}")
	public String cancelOrder(@PathVariable("id") Long id) {

		orderService.deleteById(id);

		return "redirect:/users/my-orders";
	}

	@GetMapping("/delete/{id}")
	public String deleteCustomer(@PathVariable("id") Long id) {

		orderService.deleteById(id);

		return "redirect:/orders";
	}
}
