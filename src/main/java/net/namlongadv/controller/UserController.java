package net.namlongadv.controller;

import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import net.namlongadv.dto.PageDTO;
import net.namlongadv.dto.UserDTO;
import net.namlongadv.exceptions.BadRequestException;
import net.namlongadv.services.UserService;

@RestController
@RequestMapping("users")
@Slf4j
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping
	public PageDTO<UserDTO> users(@RequestParam(value = "page", required = false) Optional<Integer> page,
			@RequestParam(value = "size", required = false) Optional<Integer> size) {
		log.debug("Getting users page");
		return userService.getAll(page.orElse(0), size.orElse(Integer.MAX_VALUE));
	}
	
	@GetMapping(value = "/{userId}")
	public UserDTO user(@PathVariable("userId") UUID userId) throws BadRequestException {
		log.debug("Getting " + userId + "'s info");
		return userService.getUserById(userId);
	}
	
	@PostMapping
	public UserDTO createUser(@RequestBody UserDTO userDTO) throws BadRequestException {
		return userService.create(userDTO);
	}
	
	@PutMapping
	public UserDTO updateUser(@RequestBody UserDTO userDTO) throws BadRequestException {
		return userService.save(userDTO);
	}

	@DeleteMapping(value = "/{userId}")
	public ResponseEntity<Boolean> deleteUser(@PathVariable("userId") UUID userId, HttpSession session) {
		return ResponseEntity.ok(userService.delete(userId));
	}
}
