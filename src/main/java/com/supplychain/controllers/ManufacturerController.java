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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.supplychain.domain.User;
import com.supplychain.domain.repository.services.UserService;

@Controller
public class ManufacturerController {
	@Autowired
	private UserService userService;

	@GetMapping("/manufacturers")
	public String listCustomers(@RequestParam(value = "id", required = false) Long id, Model theModel) {
		List<User> users = userService.findAll();
		theModel.addAttribute("users", users);

		bindAttribute(id, theModel);
		return "manufacturers";
	}

	private void bindAttribute(@RequestParam(value = "id", required = false) Long id, Model theModel) {
		User user = new User();

		if (id != null) {
			user = userService.findById(id).get();
		}

		theModel.addAttribute("user", user);
	}

	@GetMapping("/create")
	public String showFormForAdd(Model theModel) {

		User user = new User();
		theModel.addAttribute("user", user);
		return "add_manufacturer";
	}

	@PostMapping("/create")
	public String saveCustomer(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {

		if (result.hasErrors()) {
			List<User> users = userService.findAll();
			model.addAttribute("users", users);
			model.addAttribute("user", user);
			return "add_user";
		}
		userService.createUser(user);

		return "redirect:/manufacturers";
	}

	@GetMapping("/update/{id}")
	public String showFormForUpdate(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
		User user = null;
		if (id != null) {
			user = userService.findById(id).get();
			model.addAttribute("user", user);
		}

		return "add_manufacturer";
	}

	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("id") Long id) {

		// delete the customer
		userService.deleteUser(id);

		return "redirect:/manufacturers";
	}
}
