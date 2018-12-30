package net.namlongadv.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@EnableResourceServer
@RestController
@RequestMapping("users")
@Slf4j
public class UAAController {
	@Autowired
	private ConsumerTokenServices tokenService;
	
	@GetMapping("/info")
	public Principal principal(Principal user) {
		log.info("Getting user info...", user.getName());
		return user;
	}
	
	@GetMapping("/logout")
	public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authorization) {
		if(authorization != null) {
			String token = authorization.replaceFirst("bearer", "").trim();
			tokenService.revokeToken(token);
		}
		return ResponseEntity.ok().build();
	}
}
