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
import net.namlongadv.dto.GenericResponse;
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
	public ResponseEntity<GenericResponse> users(
			@RequestParam(value = "page", required = false) Optional<Integer> page,
			@RequestParam(value = "size", required = false) Optional<Integer> size,
			@RequestParam(required = false) String filter) {
		log.debug("Getting users page");
		return ResponseEntity.ok(new GenericResponse(userService.getAll(filter, page.orElse(1), size.orElse(-1))));
	}
	
	@GetMapping(value = "/{userId}")
	public ResponseEntity<GenericResponse> user(@PathVariable("userId") UUID userId) throws BadRequestException {
		log.debug("Getting " + userId + "'s info");
		return ResponseEntity.ok(new GenericResponse(userService.getUserById(userId)));
	}
	
	@PostMapping
	public ResponseEntity<GenericResponse> createUser(@RequestBody UserDTO userDTO) throws BadRequestException {
		return ResponseEntity.ok(new GenericResponse(userService.create(userDTO)));
	}
	
	@PutMapping
	public ResponseEntity<GenericResponse> updateUser(@RequestBody UserDTO userDTO) throws BadRequestException {
		return ResponseEntity.ok(new GenericResponse(userService.save(userDTO)));
	}

	@DeleteMapping(value = "/{userId}")
	public ResponseEntity<GenericResponse> deleteUser(@PathVariable("userId") UUID userId, HttpSession session) {
		return ResponseEntity.ok(new GenericResponse(userService.delete(userId)));
	}
}
