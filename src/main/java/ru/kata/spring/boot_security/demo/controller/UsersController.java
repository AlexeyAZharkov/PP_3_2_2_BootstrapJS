package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import java.util.Set;


@Controller

public class UsersController {
	private final UserServiceImp userServiceImp;
//	private final Authentication authentication;

	public UsersController(UserServiceImp userServiceImp) {
		this.userServiceImp = userServiceImp;
	}

//	@GetMapping(value = "")
//	public String printWelcome() {
//		return "admin/admin";
//	}
//
//	@GetMapping(value = "/set3users")
//	public String set3users(ModelMap model) {
//		userServiceImp.addUser(new User("Alex", "Zh", "axx"));
//		userServiceImp.addUser(new User("Alex1", "Zh1", "ax11x"));
//		userServiceImp.addUser(new User("Alex2", "Zh2", "ax22x"));
//		return "redirect:/admin/users";
//	}
//
//	@GetMapping(value = "/users")
//	public String userPage(Model model) {
//		model.addAttribute("allUsers", userServiceImp.listUsers());
//		return "admin/users";
//	}
//
//	@GetMapping("/new")
//	public String newPerson(@ModelAttribute("user") User user) {
//		return "admin/new";
//	}
//
//	@PostMapping("/new")
//	public String create(@ModelAttribute("user") User user) {
//		userServiceImp.addUser(user);
//		return "redirect:/admin/users";
//	}
//
//	@GetMapping("/delete")
//	public String deleteUser(@RequestParam(value = "id", required = false) Long id) {
//		userServiceImp.deleteUser(id);
//		return "redirect:/admin/users";
//	}
//
//	@GetMapping("/edit")
//	public String editUser(@RequestParam(value = "id", required = false) Long id, Model model) {
//		model.addAttribute("user", userServiceImp.getUserById(id));
//		return "admin/edit";
//	}
//
//	@PostMapping("/updateuser/{id}")
//	public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
//		userServiceImp.updateUser(id, user);
//		return "redirect:/admin/users";
//	}

	@GetMapping("/user")
	public String showUser(@RequestParam(value = "name", required = false) String name, Model model) {
//		Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
		String currentUserName = authentication.getName();
		System.out.println(currentUserName + " -- " + name);
		System.out.println(currentUserName == name);

		if (roles.contains("ROLE_ADMIN") || currentUserName.equals(name)) {
			model.addAttribute("userbyid", userServiceImp.getUserByName(name));
			System.out.println(currentUserName == name);
			return "user/user";
		} else {
			return "user/notauth";
		}

	}
//
//	@GetMapping("/user")
//	public String showUser(@RequestParam(value = "id", required = false) Long id, Model model) {
//		model.addAttribute("userbyid", userServiceImp.getUserById(id));
//		return "user";
//	}



}