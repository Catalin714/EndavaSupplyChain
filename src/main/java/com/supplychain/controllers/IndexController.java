package com.supplychain.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.supplychain.domain.Order;
import com.supplychain.domain.Product;
import com.supplychain.domain.User;
import com.supplychain.domain.repository.services.CustomerService;
import com.supplychain.domain.repository.services.ProductService;
import com.supplychain.domain.repository.services.UserService;

@Controller
@RequestMapping("/")
public class IndexController {

	@Autowired
	private UserService userService;
	private Order order;
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
		order = (Order) request.getSession().getAttribute("cart");
		if (order == null) {
			request.getSession().setAttribute("cart", new Order());
		}

	}

	@Autowired
	private ProductService productService;

	@Autowired
	private CustomerService customerService;

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/login";
	}

	@GetMapping
	public String index(HttpServletRequest request, Model model) {

		List<Product> products = productService.findAll();
		model.addAttribute("products", products);
		return "index";
	}

}
