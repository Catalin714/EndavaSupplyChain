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

import com.supplychain.domain.Customer;
import com.supplychain.domain.repository.services.CustomerService;

@Controller
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping
	public String customers(Model model) {
		List<Customer> customers = customerService.findAll();
		model.addAttribute("customers", customers);
		return "customers";
	}

	@GetMapping("/create")
	public String showFormForAdd(Model model) {
		Customer customer = new Customer();
		model.addAttribute("customer", customer);
		return "add_customer";
	}

	@PostMapping("/create")
	public String saveCustomer(@Valid @ModelAttribute("customer") Customer customer, BindingResult result,
			Model model) {

		if (result.hasErrors()) {
			model.addAttribute("customer", customer);
			return "add_customer";
		}

		customerService.save(customer);

		return "redirect:/customers";
	}

	@GetMapping("/update/{id}")
	public String showFormForUpdate(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
		Customer customer = null;
		if (id != null) {

			customer = customerService.findById(id).get();
			model.addAttribute("customer", customer);
		}

		return "add_customer";
	}

	@GetMapping("/delete/{id}")
	public String deleteCustomer(@PathVariable("id") Long id) {
		customerService.deleteById(id);
		return "redirect:/customers";
	}
}
