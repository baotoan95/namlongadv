package net.namlongadv.controller;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.namlongadv.constant.Constants;
import net.namlongadv.constant.PathContants;
import net.namlongadv.dto.AdvertisementDTO;
import net.namlongadv.dto.GenericResponse;
import net.namlongadv.exceptions.BadRequestException;
import net.namlongadv.services.AdvertisementService;

@RestController
@RequestMapping(PathContants.ADVERT)
public class AdvController {
	@Autowired
	private AdvertisementService advertisementService;

	@InitBinder
	public void initBinder(final WebDataBinder webdataBinder) {
		webdataBinder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				setValue(new Date(Long.valueOf(text)));
			}
		});
	}

	@Value("${namlongadv.file.limit}")
	private int fileLimit;

	/*
	 * Search
	 */
	@GetMapping("/search")
	public ResponseEntity<GenericResponse> search(@RequestParam(required = false) Optional<Integer> page,
			@RequestParam(required = false) Optional<Integer> size, @RequestParam(required = true) String filter)
			throws BadRequestException {
		return ResponseEntity
				.ok(new GenericResponse(advertisementService.findAll(filter, page.orElse(1), size.orElse(-1))));
	}

	@PostMapping(consumes = { "multipart/form-data" })
	public ResponseEntity<GenericResponse> saveAdv(
			@ModelAttribute(Constants.MODEL_NAME.ADV_DTO) AdvertisementDTO advertDTO) throws BadRequestException {
		String msg = "advert.update_success";
		List<AdvertisementDTO> conflictedAdvs = advertisementService.save(advertDTO);
		if(conflictedAdvs.isEmpty() && Objects.isNull(advertDTO.getId())) {
			msg = "advert.add_success";			
		} else if(!conflictedAdvs.isEmpty()){
			msg = "advert.address_conflict_confirm";
		}
		return ResponseEntity.ok(new GenericResponse(msg, advertisementService.save(advertDTO)));
	}

	@GetMapping("/{id}")
	public ResponseEntity<GenericResponse> getAdv(@PathVariable("id") UUID id) throws BadRequestException {
		return ResponseEntity.ok(new GenericResponse(advertisementService.findOne(id)));
	}
	/*
	 * Delete
	 */
	@DeleteMapping("/{advId}")
	public ResponseEntity<GenericResponse> adv(@PathVariable("advId") UUID advId) {
		return ResponseEntity.ok(new GenericResponse(advertisementService.delete(advId)));
	}

	@GetMapping("initHistory")
	public void initHistory() {
		advertisementService.initHistory();
	}
}
