package com.supplychain.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.supplychain.domain.Order;
import com.supplychain.domain.OrderItem;
import com.supplychain.domain.Product;
import com.supplychain.domain.User;
import com.supplychain.domain.UserType;
import com.supplychain.domain.repository.services.AddressService;
import com.supplychain.domain.repository.services.OrderService;
import com.supplychain.domain.repository.services.ProductService;
import com.supplychain.domain.repository.services.RoleService;
import com.supplychain.domain.repository.services.UserService;

@Controller
@RequestMapping("/users")
public class ClientController {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private ProductService productService;
	@Autowired
	private AddressService addressService;

	@Autowired
	private OrderService orderService;
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

		request.getSession().setAttribute("cart", order);
	}

	@GetMapping("/clients")
	public String clients(Model model) {
		List<User> users = userService.findByRolesRoleName(UserType.ROLE_CLIENT.name());
		model.addAttribute("users", users);

		return "clients";
	}

	@GetMapping("/manufacturers")
	public String manufacturers(Model model) {
		List<User> users = userService.findByRolesRoleName(UserType.ROLE_MANUFACTURER.name());
		model.addAttribute("users", users);

		return "manufacturers";
	}

	@GetMapping("/create")
	public String showFormForAdd(Model model) {

		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("types", UserType.values());
		model.addAttribute("roles", roleService.findAll());
		return "add_user";
	}

	@PostMapping("/create")
	public String saveCustomer(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("types", UserType.values());
			model.addAttribute("roles", roleService.findAll());
			model.addAttribute("user", user);
			return "add_user";
		}
		user.setPassword(encoder.encode(user.getPassword()));
		user.setEnabled(true);
		userService.createUser(user);

		return "redirect:/users/clients";
	}

	@GetMapping("/update/{id}")
	public String showFormForUpdate(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
		User user = null;
		if (id != null) {

			user = userService.findById(id).get();
			model.addAttribute("user", user);
			model.addAttribute("roles", roleService.findAll());
			model.addAttribute("types", UserType.values());
		}

		return "add_user";
	}

	@GetMapping("/delete/{id}")
	public String deleteCustomer(@PathVariable("id") Long id) {

		userService.deleteUser(id);

		return "redirect:/users/clients";
	}

	@GetMapping("/cart")
	public String cart(HttpServletRequest request, Model model) {
		Order orderSession = (Order) request.getSession().getAttribute("cart");
		double total = 0;
		if (orderSession != null) {
			for (OrderItem item : orderSession.getOrderItems()) {
				total += item.getQuantity() * item.getProduct().getPrice();
			}

			model.addAttribute("order", orderSession);
		} else {
			model.addAttribute("order", new Order());
		}

		model.addAttribute("addresses", addressService.findAll());
		model.addAttribute("total", total);
		return "cart";
	}

	@PostMapping("/validate")
	public String validateOrder(HttpServletRequest request, Model model, @Valid @ModelAttribute("order") Order myorder,
			BindingResult result, RedirectAttributes redirect) {

		Order orderSession = (Order) request.getSession().getAttribute("cart");
		orderSession.setUser(user);
		if (myorder.getAddress() != null && myorder.getAddress().getId() != null) {
			orderSession.setAddress(myorder.getAddress());
		} else {
			model.addAttribute("addresses", addressService.findAll());
			result.addError(
					new FieldError("myorder", "address", null, false, null, null, "You must add a delivery address"));
			return "cart";
		}
//		orderSession.setAddress(order.getAddress());
		order = orderService.save(orderSession);
		order = null;
		request.getSession().setAttribute("cart", new Order());

		return "redirect:/users/my-orders";
	}

	@GetMapping("/addToCart/{id}")
	public String addToCart(@PathVariable("id") Long id, HttpServletRequest request) {

		Product p = productService.findById(id).get();
		Order orderSession = (Order) request.getSession().getAttribute("cart");

		if (orderSession == null) {
			if (p != null) {
				order = new Order();
				OrderItem item = new OrderItem();
				item.setProduct(p);
				item.setQuantity(1);
				if (order.getOrderItems().size() == 0) {
					order.getOrderItems().add(item);
				} else {
					for (OrderItem itemO : order.getOrderItems()) {
						if (itemO.equals(item)) {
							itemO.setQuantity(itemO.getQuantity() + 1);
							break;
						}
					}
					order.getOrderItems().add(item);
				}
			}
			request.getSession().setAttribute("cart", order);
		} else {
			if (p != null) {
				boolean notexist = false;
				OrderItem item = new OrderItem();
				item.setProduct(p);
				item.setQuantity(1);

				for (OrderItem itemO : order.getOrderItems()) {
					if (itemO.equals(item)) {
						itemO.setQuantity(itemO.getQuantity() + 1);
						notexist = false;
						break;
					} else {
						notexist = true;
					}
				}
				if (notexist) {
					orderSession.getOrderItems().add(item);
				}

			}
			request.getSession().setAttribute("cart", orderSession);
		}

		return "redirect:/";
	}

	@GetMapping("/add/{id}")
	public String addCart(@PathVariable("id") Long id, HttpServletRequest request) {

		Product p = productService.findById(id).get();
		Order orderSession = (Order) request.getSession().getAttribute("cart");

		if (p != null) {
			OrderItem item = new OrderItem();
			item.setProduct(p);
			item.setQuantity(1);

			for (OrderItem itemO : order.getOrderItems()) {
				if (itemO.equals(item)) {
					itemO.setQuantity(itemO.getQuantity() + 1);
					break;
				}
			}

		}
		request.getSession().setAttribute("cart", orderSession);

		return "redirect:/users/cart";
	}

	@GetMapping("/del/{id}")
	public String delCart(@PathVariable("id") Long id, HttpServletRequest request) {

		Product p = productService.findById(id).get();
		Order orderSession = (Order) request.getSession().getAttribute("cart");

		if (p != null) {
			OrderItem item = new OrderItem();
			item.setProduct(p);
			item.setQuantity(1);
			for (OrderItem itemO : order.getOrderItems()) {
				if (itemO.equals(item)) {
					itemO.setQuantity(itemO.getQuantity() - 1);
					if (itemO.getQuantity() == 0) {
						order.getOrderItems().remove(itemO);
					}
					break;
				}
			}

		}
		request.getSession().setAttribute("cart", orderSession);

		return "redirect:/users/cart";
	}

	@GetMapping("/my-orders")
	public String myOrders(HttpServletRequest request, Model model) {

		model.addAttribute("orders", orderService.findByUserEmail(user.getEmail()));
		return "my_orders";
	}

}
