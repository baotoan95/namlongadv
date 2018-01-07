package net.namlongadv.controller;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;
import net.namlongadv.models.User;
import net.namlongadv.models.UserRole;
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
	@Value("${namlongadv.session.name.page-index}")
	private String pageIndex;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@RequestMapping(method = RequestMethod.GET)
	public String user(ModelMap model, HttpSession session) {
		log.debug("Getting add user page");
		session.setAttribute(pageIndex, "user");

		List<UserRole> userRoles = Lists.newArrayList(roleRepository.findAll());
		model.addAttribute("roles", userRoles);
		User newUser = new User();
		// Set default value
		newUser.setRoles(Arrays.asList(userRoles.get(0)));
		newUser.setAccountNonLocked(true);
		model.addAttribute("user", newUser);
		return "user";
	}

	@RequestMapping(method = { RequestMethod.PUT, RequestMethod.POST }, produces="text/html;charset=UTF-8")
	public String user(@ModelAttribute("user") User user, BindingResult bindingResult, ModelMap model, HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("utf-8");
		System.setProperty("file.encoding","UTF-8");
		model.addAttribute("roles", roleRepository.findAll());
		
		log.debug("Full name: {}", user.getName());

		User prevUser = userRepository.findByUsername(user.getUsername());
		// If id not null == add action
		if (user.getId() == null) {
			log.debug("Check user exist");
			if (prevUser != null) {
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
		log.debug("Encoding new password");
		if (prevUser != null) {
			// For update
			if (user.getPassword() != null && !user.getPassword().isEmpty()) {
				log.debug("Update with new password");
				user.setPassword(passwordEncoder.encode(user.getPassword()));
			} else {
				log.debug("Update with old password");
				user.setPassword(prevUser.getPassword());
			}
		} else {
			// For create
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}

		log.debug("Check locked user");
		user.setAccountNonLocked(user.isAccountNonLocked());

		User userAdded = userRepository.save(user);
		if (userAdded == null) {
			log.error("Fail to add the user");
			model.addAttribute("user", user);
			model.addAttribute("errorMsg", "Fail to add the user");
			return "user";
		}
		log.debug("User saved");
		return "redirect:/user/view?page=0&size=10";
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public String user(@PathVariable("userId") UUID userId, ModelMap model) {
		log.debug("Getting " + userId + "'s info");
		User user = userRepository.findOne(userId);
		if (user == null) {
			return "redirect:/user/view?page=0&size=10";
		}
		log.debug("Full name: {}", user.getName());
		model.addAttribute("roles", roleRepository.findAll());
		model.addAttribute("user", user);
		return "user";
	}

	@RequestMapping(value = "/delete/{userId}", method = RequestMethod.GET)
	public String user(@PathVariable("userId") UUID userId) {
		userRepository.delete(userId);
		return "redirect:/user/view?page=0&size=10";
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String users(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = "10") Integer size, ModelMap model,
			HttpSession session) {
		log.debug("Getting users page");
		session.setAttribute(pageIndex, "users");
		model.addAttribute("page", userRepository.findAll(new PageRequest(page, size)));
		return "users";
	}
}
