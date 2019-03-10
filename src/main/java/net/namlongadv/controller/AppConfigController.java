package net.namlongadv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.namlongadv.common.ConfigKey;
import net.namlongadv.services.AppConfigService;

@RestController
@RequestMapping("appConfig")
public class AppConfigController {
	@Autowired
	private AppConfigService appConfigService;
	
	@GetMapping("{key}")
	public ResponseEntity<String> getConfig(@PathVariable("key") ConfigKey key) {
		return ResponseEntity.ok(appConfigService.getConfig(key));
	}
}
