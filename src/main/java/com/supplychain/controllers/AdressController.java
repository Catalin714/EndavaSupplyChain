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

import com.supplychain.domain.Address;
import com.supplychain.domain.repository.services.AddressService;

@Controller
@RequestMapping("/addresses")
public class AdressController {

	@Autowired
	private AddressService addressService;

	@GetMapping
	public String addresses(Model model) {
		List<Address> addresses = addressService.findAll();
		model.addAttribute("addresses", addresses);
		return "addresses";
	}

	@GetMapping("/create")
	public String showFormForAdd(Model model) {
		Address address = new Address();
		model.addAttribute("address", address);
		return "add_address";
	}

	@PostMapping("/create")
	public String saveCustomer(@Valid @ModelAttribute("address") Address address, BindingResult result, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("address", address);
			return "add_user";
		}

		addressService.save(address);

		return "redirect:/addresses";
	}

	@GetMapping("/update/{id}")
	public String showFormForUpdate(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
		Address address = null;
		if (id != null) {

			address = addressService.findById(id).get();
			model.addAttribute("address", address);
		}

		return "add_address";
	}

	@GetMapping("/delete/{id}")
	public String deleteCustomer(@PathVariable("id") Long id) {
		addressService.deleteById(id);
		return "redirect:/addresses";
	}
}
