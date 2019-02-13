package net.namlongadv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.namlongadv.dto.GenericResponse;
import net.namlongadv.services.ProvinceService;

@RestController
@RequestMapping("provinces")
public class ProvinceController {
	@Autowired
	private ProvinceService provinceService;
	
	@GetMapping
	public ResponseEntity<GenericResponse> findAll() {
		return ResponseEntity.ok(new GenericResponse(provinceService.findAll()));
	}
}
