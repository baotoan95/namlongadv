package net.namlongadv.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.namlongadv.models.AdvChangeHistory;
import net.namlongadv.services.AdvChangeHistoryService;

@RestController
@RequestMapping("advHistories")
public class AdvHistoryController {
	@Autowired
	private AdvChangeHistoryService changeHistoryService;
	
	@GetMapping
	public ResponseEntity<List<AdvChangeHistory>> getByAdvId(@RequestParam("advId") UUID advId) {
		return ResponseEntity.ok(changeHistoryService.advChangesById(advId));
	}
	
	@GetMapping("updateCreatedBy")
	public ResponseEntity<Void> updateCreatedBy() {
		changeHistoryService.updateCreatedBy();
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("deleteById")
	public ResponseEntity<Void> delete(@RequestParam("id") UUID id) {
		changeHistoryService.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
