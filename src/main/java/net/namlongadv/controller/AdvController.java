package net.namlongadv.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;
import net.namlongadv.common.Constants;
import net.namlongadv.common.Enums;
import net.namlongadv.common.PathContants;
import net.namlongadv.dto.AdvertisementDTO;
import net.namlongadv.dto.AdvertisementWrapperDTO;
import net.namlongadv.models.Advertisement;
import net.namlongadv.repositories.AdvertisementRepository;
import net.namlongadv.repositories.ProvinceRepository;
import net.namlongadv.repositories.UserRepository;
import net.namlongadv.services.AdvChangeHistoryService;
import net.namlongadv.services.AdvertisementService;
import net.namlongadv.utils.AuthenticationUtils;
import net.namlongadv.utils.DateUtils;
import net.namlongadv.utils.StringUtils;

@Controller
@RequestMapping(PathContants.ADVERT)
@Slf4j
public class AdvController {
	@Value("${namlongadv.session.name.page-index}")
	private String pageIndex;
	@Autowired
	private AdvertisementRepository advertisementRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AdvertisementService advertisementService;
	@Autowired
	private ProvinceRepository provinceRepository;
	@Autowired
	private AdvChangeHistoryService changeHistoryService;

	@Value("${namlongadv.file.limit}")
	private int fileLimit;
	@Value("${namlongadv.base_url}")
	private String baseUrl;

	@InitBinder
	public void bindingPreparation(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		CustomDateEditor orderDateEditor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, orderDateEditor);
	}

	/*
	 * Search
	 */
	@GetMapping("/search")
	public String search(HttpSession session, @RequestParam(value = "code", required = false) Optional<String> code,
			@RequestParam(value = "address", required = false) Optional<String> address,
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
			@RequestParam(value = "createdBy", required = false) Optional<String> createdBy,
			@RequestParam(value = "daterange", required = false) Optional<String> daterange,
			@RequestParam(value = "contactPerson", required = false) Optional<String> contactPerson,
			@RequestParam(value = "houseNo", required = false) Optional<String> houseNo,
			@RequestParam(value = "province", required = false) Optional<String> province,
			@RequestParam(value = "ward", required = false) Optional<String> ward,
			@RequestParam(value = "street", required = false) Optional<String> street,
			@RequestParam(value = "district", required = false) Optional<String> district,
			@RequestParam(value = "title", required = false) Optional<String> title, ModelMap model,
			HttpServletRequest request) {
		// Set page size
		setPageSize(size, session);
		session.setAttribute(Constants.SESSION_NAME.CURRENT_PAGE, page);
		model.addAttribute("isSearch", true);

		int pageSize = (int) session.getAttribute(Constants.SESSION_NAME.PAGE_SIZE);

		Page<Advertisement> rs = null;
		try {
			// ============ Set date range
			if (!daterange.isPresent() || daterange.get().trim().length() == 0) {
				log.debug("Filter with default date range");
				daterange = Optional.of("01/01/2017 - " + DateUtils.convertDateToString(new Date()));
			}
			String[] dates = daterange.get().trim().split(" - ");
			Date from = DateUtils.convertStringToDate(dates[0]);
			Date to = DateUtils.increaseDay(DateUtils.convertStringToDate(dates[1]), 1);

			log.debug("From: {}", from);
			log.debug("To: {}", to);
			// ============ End set date range

			// Search
			rs = advertisementService.search(code, address, createdBy, from, to, contactPerson, 
					houseNo, street, ward,
					district, province, title, page.intValue(), pageSize);
			log.debug("Search result: " + rs.getContent().size());
		} catch (ParseException e) {
			return Constants.ADV_PAGE_REDIRECT + pageSize;
		}

		List<Advertisement> pageContent = advertisementService.setPermission(rs.getContent(),
				AuthenticationUtils.getCurrentUserRoles(), AuthenticationUtils.getUserDetails().getUserId());
		List<Advertisement> content = new ArrayList<>();
		content.addAll(pageContent);
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		content = advertisementService.sort(content);

		// Build query string
		model.put("queryString", advertisementService.getSearchQueryAndSetToModel(code, address, createdBy, daterange,
				contactPerson, houseNo, street, ward, district, province, title, model));
		model.addAttribute(Constants.MODEL_NAME.PAGE_CONTENT, content);
		model.put("page", rs);
		model.addAttribute(Constants.MODEL_NAME.PROVINCES,
				StreamSupport.stream(provinceRepository.findAll().spliterator(), false).collect(Collectors.toList()));
		session.setAttribute(Constants.SESSION_NAME.SEARCH_CONTENT, request.getQueryString());
		return PathContants.ADVERT_LIST;
	}

	/*
	 * View
	 */
	@GetMapping("/view")
	public String advs(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = "10") Integer size, HttpSession session,
			ModelMap model) {
		// Set page size
		setPageSize(size, session);
		session.setAttribute(Constants.SESSION_NAME.CURRENT_PAGE, page);
		session.setAttribute(pageIndex, PathContants.ADVERT_LIST);
		session.removeAttribute(Constants.SESSION_NAME.SEARCH_CONTENT);

		model.addAttribute("advertWrapper", new AdvertisementWrapperDTO());
		Page<Advertisement> result = advertisementService
				.findAll(new PageRequest(page, (int) session.getAttribute(Constants.SESSION_NAME.PAGE_SIZE),
						new Sort(Sort.Direction.DESC, "updatedDate")));

		model.addAttribute(Constants.MODEL_NAME.PAGE_CONTENT, advertisementService.setPermission(result.getContent(),
				AuthenticationUtils.getCurrentUserRoles(), AuthenticationUtils.getUserDetails().getUserId()));
		model.addAttribute("page", result);
		model.addAttribute(Constants.MODEL_NAME.PROVINCES,
				StreamSupport.stream(provinceRepository.findAll().spliterator(), false).collect(Collectors.toList()));
		return PathContants.ADVERT_LIST;
	}

	/*
	 * Get add new page
	 */
	@GetMapping
	public String adv(HttpSession session, ModelMap model) {
		session.setAttribute(pageIndex, PathContants.ADVERT);
		AdvertisementDTO advDto = new AdvertisementDTO();
		// Set default values
		advDto.getAdvertisement().setImplTime(20);
		advDto.getAdvertisement().setImplForm("in bạt hiflex 720 DPI");
		advDto.getAdvertisement().setHouseNo("Số ");
		advDto.getAdvertisement().setStreet("Đường ");
		advDto.getAdvertisement().setWidthSize("m");
		advDto.getAdvertisement().setHeightSize("m");

		model.addAttribute(Constants.MODEL_NAME.ADV_DTO, advDto);
		model.addAttribute(Constants.MODEL_NAME.PROVINCES,
				StreamSupport.stream(provinceRepository.findAll().spliterator(), false).collect(Collectors.toList()));
		return PathContants.ADVERT;
	}

	@PostMapping(value = "add", consumes = { "multipart/form-data" })
	public String addAdv(@Valid @ModelAttribute(Constants.MODEL_NAME.ADV_DTO) AdvertisementDTO advertDto,
			HttpSession session, ModelMap model, BindingResult result) {
		session.setAttribute(pageIndex, PathContants.ADVERT);

		Advertisement advert = advertDto.getAdvertisement();
		// Standardize and Fulfill data
		advert.setId(null);
		advert = advertisementService.fulfillStandardizeAdv(advert);

		// Address conflict
		List<Advertisement> addressConflict = advertisementService.checkAddressConflict(advertDto, null);
		if (!addressConflict.isEmpty()) {
			advertDto.setAdvertisement(advertisementService.setPermission(advertDto.getAdvertisement(),
					AuthenticationUtils.getCurrentUserRoles(), AuthenticationUtils.getUserDetails().getUserId()));
			model.addAttribute(Constants.MODEL_NAME.ADV_DTO, advertDto);
			model.addAttribute(Constants.MODEL_NAME.PROVINCES, StreamSupport
					.stream(provinceRepository.findAll().spliterator(), false).collect(Collectors.toList()));

			// Prepare error message (address conflict)
			model.addAttribute(Constants.MODEL_NAME.ERROR_MESSAGE, advertisementService.generateAddressConflictMessage(addressConflict, Enums.ACTION.ADD));
			return PathContants.ADVERT;
		}

		advert.setCreatedDate(new Date());
		advert.setCreatedBy(userRepository.findOne(AuthenticationUtils.getUserDetails().getUserId()));
		advert.setCode(advertisementService.generateCode(advert.getProvinceCode()));
		advert.setAdvImages(advertisementService.uploadAdvImage(advertDto));
		advert.setUpdatedDate(new Date());
		advert.setOwnerContactPersonSearching(StringUtils.convertStringIgnoreUtf8(advert.getOwnerContactPerson()));
		advert.setAdvCompNameSearching(StringUtils.convertStringIgnoreUtf8(advert.getAdvCompName()));

		// Save
		log.info("Saving an advert");
		Advertisement advertisement = advertisementRepository.save(advert);

		if (advertisement == null) {
			advertDto.setAdvertisement(advertisementService.setPermission(advertDto.getAdvertisement(),
					AuthenticationUtils.getCurrentUserRoles(), AuthenticationUtils.getUserDetails().getUserId()));
			model.addAttribute(Constants.MODEL_NAME.ADV_DTO, advertDto);
			model.addAttribute(Constants.MODEL_NAME.PROVINCES, StreamSupport
					.stream(provinceRepository.findAll().spliterator(), false).collect(Collectors.toList()));
			model.addAttribute(Constants.MODEL_NAME.ERROR_MESSAGE,
					"There is an error occur, please contact IT department to support.");
			return PathContants.ADVERT;
		}
		log.info("Save successful and redirect to view page");
		
		// Initialize history
		changeHistoryService.saveHistory(changeHistoryService.convertToAdvChangeHistory(advertisement));

		String queryString = session.getAttribute(Constants.SESSION_NAME.SEARCH_CONTENT) != null
				? session.getAttribute(Constants.SESSION_NAME.SEARCH_CONTENT).toString()
				: null;
		log.debug("In search: {}", queryString);

		if (queryString != null) {
			return Constants.ADV_SEARCH_REDIRECT + session.getAttribute(Constants.SESSION_NAME.SEARCH_CONTENT);
		}
		int page = session.getAttribute(Constants.SESSION_NAME.CURRENT_PAGE) != null
				? (int) session.getAttribute("currentPage")
				: 0;

		return "redirect:/adv/view?page=" + page + "&size=" + session.getAttribute(Constants.SESSION_NAME.PAGE_SIZE);
	}

	/*
	 * Get update page
	 */
	@GetMapping("/{advId}")
	public String adv(@PathVariable("advId") UUID advId, HttpSession session, ModelMap model) {
		session.setAttribute(pageIndex, PathContants.ADVERT_LIST);

		Advertisement advertisement = advertisementService.setPermission(advertisementRepository.findOne(advId),
				AuthenticationUtils.getCurrentUserRoles(), AuthenticationUtils.getUserDetails().getUserId());
		if (advertisement != null) {
			AdvertisementDTO advertDto = new AdvertisementDTO();
			advertDto.setAdvertisement(advertisement);
			model.addAttribute(Constants.MODEL_NAME.ADV_DTO, advertDto);
			model.addAttribute(Constants.MODEL_NAME.PROVINCES, StreamSupport
					.stream(provinceRepository.findAll().spliterator(), false).collect(Collectors.toList()));
			model.addAttribute(Constants.MODEL_NAME.HISTORY, changeHistoryService.findByAdvId(advId));
			return PathContants.ADVERT;
		} else {
			return Constants.ADV_PAGE_REDIRECT + session.getAttribute(Constants.SESSION_NAME.PAGE_SIZE);
		}
	}

	@PostMapping(value = "update", consumes = { "multipart/form-data" })
	public String updateAdv(@Valid @ModelAttribute(Constants.MODEL_NAME.ADV_DTO) AdvertisementDTO advertDto,
			HttpSession session, ModelMap model) {
		session.setAttribute(pageIndex, PathContants.ADVERT);

		return advertisementService.update(advertDto, session, model);
	}

	/*
	 * Delete
	 */
	@RequestMapping(value = "/delete/{advId}", method = RequestMethod.GET)
	public String adv(@PathVariable("advId") UUID advId, HttpSession session) {
		String queryString = session.getAttribute(Constants.SESSION_NAME.SEARCH_CONTENT) != null
				? session.getAttribute(Constants.SESSION_NAME.SEARCH_CONTENT).toString()
				: null;
		log.debug("Delete {}", advId);
		advertisementRepository.delete(advId);
		changeHistoryService.delete(advId);
		if (queryString != null) {
			return Constants.ADV_SEARCH_REDIRECT + session.getAttribute(Constants.SESSION_NAME.SEARCH_CONTENT);
		}
		return Constants.ADV_PAGE_REDIRECT + session.getAttribute(Constants.SESSION_NAME.PAGE_SIZE);
	}

	private void setPageSize(int pageSize, HttpSession session) {
		if (session.getAttribute(Constants.SESSION_NAME.PAGE_SIZE) == null || pageSize <= 0 || pageSize > 10000
				|| pageSize == 10) {
			session.setAttribute(Constants.SESSION_NAME.PAGE_SIZE, 10);
		} else {
			session.setAttribute(Constants.SESSION_NAME.PAGE_SIZE, pageSize);
		}
	}
	
	@GetMapping("initHistory")
	public void initHistory() {
		advertisementService.initHistory();
	}
	
	@GetMapping("updateNameProvince")
	public void updateNameProvince() {
		provinceRepository.findAll().forEach(province -> {
			province.setName(province.getName().replace("Thành phố", "TP"));
			provinceRepository.save(province);
		});
	}
}
