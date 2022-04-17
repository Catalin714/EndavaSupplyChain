package com.supplychain.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.supplychain.domain.Product;
import com.supplychain.domain.repository.services.CustomerService;
import com.supplychain.domain.repository.services.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {
	@Autowired
	private ProductService productService;

	@Autowired
	private CustomerService customerService;

	@GetMapping
	public String products(Model model) {
		List<Product> products = productService.findAll();
		model.addAttribute("products", products);
		return "products";
	}

	@GetMapping("/create")
	public String showFormForAdd(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		model.addAttribute("customers", customerService.findAll());
		return "add_product";
	}

	@PostMapping("/create")
	public String saveCustomer(@Valid @ModelAttribute("product") Product product, BindingResult result, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("customers", customerService.findAll());
			model.addAttribute("product", product);
			return "add_product";
		}

		productService.save(product);

		return "redirect:/products";
	}

	@GetMapping("/update/{id}")
	public String showFormForUpdate(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
		Product product = null;
		if (id != null) {
			model.addAttribute("customers", customerService.findAll());
			product = productService.findById(id).get();
			model.addAttribute("product", product);
		}

		return "add_product";
	}

	@GetMapping("/delete/{id}")
	public String deleteCustomer(@PathVariable("id") Long id) {
		productService.deleteById(id);
		return "redirect:/products";
	}
}
