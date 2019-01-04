package net.namlongadv.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

	@Value("${namlongadv.file.limit}")
	private int fileLimit;

	/*
	 * Search
	 */
	@GetMapping("/search")
	public ResponseEntity<GenericResponse> search(
			@RequestParam(required = false) Optional<Integer> page,
			@RequestParam(required = false) Optional<Integer> size,
			@RequestParam(required = true) String filter
		) throws BadRequestException {
		return ResponseEntity.ok(new GenericResponse(advertisementService.findAll(filter, page.orElse(1), size.orElse(-1))));
	}

	@PostMapping(consumes = { "multipart/form-data" })
	public ResponseEntity<GenericResponse> addAdv(@ModelAttribute(Constants.MODEL_NAME.ADV_DTO) AdvertisementDTO advertDTO) throws BadRequestException {
		return ResponseEntity.ok(new GenericResponse(advertisementService.addNew(advertDTO)));
	}

//	@PostMapping(value = "update", consumes = { "multipart/form-data" })
//	public String updateAdv(@Valid @ModelAttribute(Constants.MODEL_NAME.ADV_DTO) AdvertisementDTO advertDto,
//			HttpSession session, ModelMap model) {
//		setPageSize(session.getAttribute(Constants.SESSION_NAME.PAGE_SIZE) != null ? Integer.parseInt(session.getAttribute(Constants.SESSION_NAME.PAGE_SIZE).toString()) : null, session);
//		session.setAttribute(pageIndex, PathContants.ADVERT);
//		return advertisementService.update(advertDto, session, model);
//	}

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
