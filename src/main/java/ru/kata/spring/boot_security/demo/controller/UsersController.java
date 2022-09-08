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

	public UsersController(UserServiceImp userServiceImp) {
		this.userServiceImp = userServiceImp;
	}


	@GetMapping("/user")
	public String showUser(@RequestParam(value = "name", required = false) String name, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
		String currentUserName = authentication.getName();

		if (roles.contains("ROLE_ADMIN") || currentUserName.equals(name)) {
			model.addAttribute("userbyid", userServiceImp.getUserByName(name));
			return "user/user";
		} else {
			return "user/notauth";
		}
	}
}