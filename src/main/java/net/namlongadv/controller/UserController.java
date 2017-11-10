package net.namlongadv.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.slf4j.Slf4j;
import net.namlongadv.models.User;
import net.namlongadv.repositories.RoleRepository;
import net.namlongadv.repositories.UserRepository;

@Controller
@RequestMapping("user")
@Slf4j
public class UserController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Value("${namlongadv.pagination.num-of-page}")
	private int numOfPage;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@RequestMapping(method = RequestMethod.GET)
	public String user(ModelMap model) {
		log.debug("Getting add user page");
		model.addAttribute("roles", roleRepository.findAll());
		model.addAttribute("user", new User());
		return "user";
	}

	@RequestMapping(method = { RequestMethod.PUT, RequestMethod.POST })
	public String user(@ModelAttribute("user") @Validated User user, BindingResult bindingResult, ModelMap model) {
		model.addAttribute("roles", roleRepository.findAll());
		
		// If id not null == add action
		if (user.getId() == null) {
			log.debug("Check user exist");
			if (userRepository.findByUsername(user.getUsername()) != null) {
				log.debug("User existed " + user);
				user.setUsername(null);
				model.addAttribute("user", user);
				model.addAttribute("errorMsg", "Tên tài khoản đã tồn tại");
				return "user";
			}
			log.debug("User is not existed. Then add new this one.");
		}

		log.debug("Validation form");
		if (user.getName().trim().length() == 0) {
			bindingResult.rejectValue("name", "error.name", "Vui lòng nhập tên");
		}
		if (bindingResult.hasErrors()) {
			log.debug("Form has errors " + bindingResult.getAllErrors());
			return "user";
		}

		log.debug("Preparing to save a user");
		log.debug("Encode password");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		log.debug("Check locked user");
		user.setAccountNonLocked(!user.isAccountNonLocked());

		User userAdded = userRepository.save(user);
		if (userAdded == null) {
			log.error("Fail to add the user");
			model.addAttribute("user", user);
			model.addAttribute("errorMsg", "Fail to add the user");
			return "user";
		}
		log.debug("User saved");
		return "redirect:/user/view/".concat(String.valueOf(numOfPage)).concat("/1");
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public String user(@PathVariable("userId") UUID userId, ModelMap model) {
		log.debug("Getting " + userId + "'s info");
		User user = userRepository.findOne(userId);
		if (user == null) {
			return "redirect:/user/view/".concat(String.valueOf(numOfPage)).concat("/1");
		}
		model.addAttribute("roles", roleRepository.findAll());
		model.addAttribute("user", user);
		return "user";
	}

	@RequestMapping(value = "/delete/{userId}", method = RequestMethod.GET)
	public String user(@PathVariable("userId") UUID userId) {
		userRepository.delete(userId);
		return "redirect:/user/view/".concat(String.valueOf(numOfPage)).concat("/1");
	}

	@RequestMapping(value = "/view/{numOfPage}/{page}")
	public String users(@PathVariable("numOfPage") int numOfPage, @PathVariable("page") int page, ModelMap model) {
		model.addAttribute("users", userRepository.findAll());
		return "users";
	}
}
