package net.namlongadv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT })
	public String user(@ModelAttribute User user, ModelMap model) {
		log.debug("Adding a user");
		log.debug("Encode password");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User userAdded = userRepository.save(user);
		if (userAdded == null) {
			log.error("Fail to add the user");
			model.addAttribute("user", user);
			model.addAttribute("errorMsg", "Fail to add the user");
			return "user";
		}
		log.debug("User added");
		model.addAttribute("users", userRepository.findAll());
		return "users";
	}

	@RequestMapping(value = "/{username}", method = RequestMethod.GET)
	public String user(@PathVariable("username") String username, ModelMap model) {
		log.debug("Getting " + username + "'s info");
		User user = userRepository.findOne(username);
		if (user == null) {
			return "redirect:/user/view/".concat(String.valueOf(numOfPage)).concat("/1");
		}
		model.addAttribute("roles", roleRepository.findAll());
		model.addAttribute("user", user);
		return "user";
	}
	
	@RequestMapping(value = "/delete/{username}", method = RequestMethod.GET)
	public String user(@PathVariable("username") String username) {
		userRepository.delete(username);
		return "redirect:/user/view/".concat(String.valueOf(numOfPage)).concat("/1");
	}

	@RequestMapping(value = "/view/{numOfPage}/{page}")
	public String users(@PathVariable("numOfPage") int numOfPage, @PathVariable("page") int page, ModelMap model) {
		model.addAttribute("users", userRepository.findAll());
		return "users";
	}
}
