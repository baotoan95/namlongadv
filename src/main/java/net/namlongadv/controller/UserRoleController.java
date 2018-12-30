package net.namlongadv.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.namlongadv.dto.UserRoleDTO;
import net.namlongadv.repositories.RoleRepository;

@RestController
@RequestMapping("userRoles")
public class UserRoleController {
	@Autowired
	private RoleRepository roleRepository;
	
	@GetMapping()
	public ResponseEntity<List<UserRoleDTO>> userRoles() {
		List<UserRoleDTO> userRoles = Lists.newArrayList(roleRepository.findAll()).stream().map(userRole -> {
			return UserRoleDTO.builder().id(userRole.getId()).code(userRole.getCode())
					.name(userRole.getName()).build();
		}).collect(Collectors.toList());
		return ResponseEntity.ok(userRoles);
	}
}
