package net.namlongadv.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;
import net.namlongadv.dto.AdvertisementDTO;
import net.namlongadv.dto.AdvertisementWrapperDTO;
import net.namlongadv.models.AdvImage;
import net.namlongadv.models.Advertisement;
import net.namlongadv.models.NLAdvUserDetails;
import net.namlongadv.repositories.AdvImageRepository;
import net.namlongadv.repositories.AdvertisementRepository;
import net.namlongadv.repositories.ProvinceRepository;
import net.namlongadv.repositories.UserRepository;
import net.namlongadv.services.AdvertisementService;
import net.namlongadv.utils.DateUtils;
import net.namlongadv.utils.StringUtils;
import net.namlongadv.utils.UploadFileUtils;

@Controller
@Slf4j
@RequestMapping("adv")
public class AdvController {
	@Value("${namlongadv.session.name.page-index}")
	private String pageIndex;
	@Autowired
	private AdvertisementRepository advertisementRepository;
	@Autowired
	private AdvImageRepository advImageRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AdvertisementService advertisementService;
	@Autowired
	private ProvinceRepository provinceRepository;

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
	@RequestMapping(value = "/search")
	public String search(HttpSession session, @RequestParam(value = "code", required = false) Optional<String> code,
			@RequestParam(value = "address", required = false) Optional<String> address,
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
			@RequestParam(value = "createdBy", required = false) Optional<String> createdBy,
			@RequestParam(value = "daterange", required = false) Optional<String> daterange,
			@RequestParam(value = "contactPerson", required = false) Optional<String> contactPerson,
			@RequestParam(value = "province", required = false) Optional<String> province, ModelMap model) {
		// Get user roles info
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		NLAdvUserDetails userDetails = (NLAdvUserDetails) authentication.getPrincipal();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		List<String> roles = new ArrayList<>();
		authorities.stream().forEach(auth -> roles.add(auth.getAuthority()));

		// Set page size
		setPageSize(size, session);

		model.addAttribute("isSearch", true);

		Page<Advertisement> rs = null;
		try {
			if (!daterange.isPresent() || (daterange.isPresent() && daterange.get().trim().length() == 0)) {
				log.debug("Filter with default date range");
				daterange = Optional.of("01/01/2017 - " + DateUtils.convertDateToString(new Date()));
			}
			model.put("daterange", daterange.get());

			String[] dates = daterange.get().trim().split(" - ");
			Date from = DateUtils.decreaseDay(DateUtils.convertStringToDate(dates[0]), 1);
			Date to = DateUtils.increaseDay(DateUtils.convertStringToDate(dates[1]), 1);

			log.debug("From: {}", from);
			log.debug("To: {}", to);

			String sCode = "";
			String sAddress = "";
			String sCreatedBy = "";
			String sContactPerson = "";
			if (code.isPresent() && code.get().length() > 0) {
				sCode = code.get().trim();
				model.put("code", code.get().trim());
			}
			if (address.isPresent() && address.get().length() > 0) {
				sAddress = address.get().trim();
				model.put("address", address.get().trim());
			}
			if (createdBy.isPresent() && createdBy.get().length() > 0) {
				sCreatedBy = createdBy.get().trim();
				model.put("createdBy", createdBy.get().trim());
			}
			if (contactPerson.isPresent() && contactPerson.get().length() > 0) {
				sContactPerson = contactPerson.get().trim();
				model.put("contactPerson", contactPerson.get().trim());
			}

			log.debug("Search function ============");
			log.debug("sCode: {}", sCode);
			log.debug("sAddress: {}", sAddress);
			log.debug("sCreatedBy: {}", sCreatedBy);
			log.debug("sContactPerson: {}", sContactPerson);
			log.debug("Time: {} {}", from, to);
			log.debug("============================");

			if (province.isPresent() && !province.get().isEmpty()) {
				rs = advertisementService.search(sCode.toUpperCase(), StringUtils.convertStringIgnoreUtf8(sAddress),
						sCreatedBy.toUpperCase(), from, to, StringUtils.convertStringIgnoreUtf8(sContactPerson),
						province.get(), roles, new PageRequest(page.intValue(), (int) session.getAttribute("pageSize"),
								new Sort(Sort.Direction.DESC, "updatedDate")));
				model.put("province", province.get());
			} else {
				rs = advertisementService.search(sCode.toUpperCase(), StringUtils.convertStringIgnoreUtf8(sAddress),
						sCreatedBy.toUpperCase(), from, to, StringUtils.convertStringIgnoreUtf8(sContactPerson), roles,
						new PageRequest(page.intValue(), (int) session.getAttribute("pageSize"),
								new Sort(Sort.Direction.DESC, "updatedDate")));
			}
			log.debug("Search result: " + rs.getContent().size());
		} catch (ParseException e) {
			return "redirect:/adv/view?page=0&size=" + session.getAttribute("pageSize");
		}

		List<Advertisement> pageContent = advertisementService.setPermission(rs.getContent(), roles,
				userDetails.getUserId());
		model.addAttribute("pageContent", pageContent);
		model.put("page", rs);
		model.addAttribute("provinces",
				StreamSupport.stream(provinceRepository.findAll().spliterator(), false).collect(Collectors.toList()));
		return "advs";
	}

	/*
	 * View
	 */
	@RequestMapping(value = { "/view" }, method = RequestMethod.GET)
	public String advs(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = "10") Integer size, HttpSession session,
			ModelMap model) {
		// Get user roles info
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		NLAdvUserDetails userDetails = (NLAdvUserDetails) authentication.getPrincipal();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		List<String> roles = new ArrayList<>();
		authorities.stream().forEach(auth -> roles.add(auth.getAuthority()));

		// Set page size
		setPageSize(size, session);

		session.setAttribute(pageIndex, "advs");

		model.addAttribute("advertWrapper", new AdvertisementWrapperDTO());
		Page<Advertisement> result = advertisementService.findByRoles(roles, new PageRequest(page,
				(int) session.getAttribute("pageSize"), new Sort(Sort.Direction.DESC, "updatedDate")));
		List<Advertisement> pageContent = advertisementService.setPermission(result.getContent(), roles,
				userDetails.getUserId());
		model.addAttribute("pageContent", pageContent);
		model.addAttribute("page", result);
		model.addAttribute("provinces",
				StreamSupport.stream(provinceRepository.findAll().spliterator(), false).collect(Collectors.toList()));
		return "advs";
	}

	/*
	 * Get add new page
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String adv(HttpSession session, ModelMap model) {
		session.setAttribute(pageIndex, "adv");
		AdvertisementDTO advDto = new AdvertisementDTO();
		// Generate code
		String code = null;
		while (true) {
			code = StringUtils.randomCode();
			if (advertisementRepository.checkCode(code) == null) {
				advDto.getAdvertisement().setCode(code);
				break;
			}
		}
		// Set default values
		advDto.getAdvertisement().setImplTime(20);
		advDto.getAdvertisement().setImplForm("in bạt hiflex 720 DPI");

		model.addAttribute("advertDto", advDto);
		model.addAttribute("provinces",
				StreamSupport.stream(provinceRepository.findAll().spliterator(), false).collect(Collectors.toList()));
		return "adv";
	}

	/*
	 * Add/Update
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, consumes = { "multipart/form-data" })
	public String adv(@Valid @ModelAttribute("advertDto") AdvertisementDTO advertDto, HttpSession session,
			ModelMap model, BindingResult result) {
		if (result.hasErrors()) {
			log.debug("=====Errors:");
			result.getFieldErrors().forEach(error -> {
				log.debug(error.getField());
			});
			log.debug("===========");
		}
		log.debug("=================Advertisement====================");
		log.debug(advertDto.getAdvertisement().toString());
		// Get user roles info
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		NLAdvUserDetails userDetails = (NLAdvUserDetails) authentication.getPrincipal();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		List<String> roles = new ArrayList<>();
		authorities.stream().forEach(auth -> roles.add(auth.getAuthority()));

		session.setAttribute(pageIndex, "adv");

		// Update if it publish to billboardquangcao.com
		log.debug("PublishedId: {}", advertDto.getAdvertisement().getPublishedId());
		if (advertDto.getAdvertisement().getPublishedId() != null
				&& advertDto.getAdvertisement().getPublishedId() > 0) {
			advertDto.getAdvertisement().setPublishedDate(new Date());
		} else {
			advertDto.getAdvertisement().setPublishedDate(null);
		}

		// Check existed
		Advertisement advert = advertDto.getAdvertisement();
		Advertisement prevAdvertisement = null;
		try {
			prevAdvertisement = advertisementRepository.findOne(advert.getId());
		} catch (Exception e) {
			// Do nothing
		}

		// Validation code if not auto generate
		if (advert.getCode().trim().length() == 0) {
			advert.setCode(StringUtils.randomCode());
		}

		// Set province
		if (advert.getProvinceCode() != null && advert.getProvinceCode().length() > 0) {
			advert.setProvince(provinceRepository.findOne(advert.getProvinceCode()).getName());
		} else {
			advert.setProvince("");
		}

		// Standardize
		advert.setHouseNo(StringUtils.standardize(advert.getHouseNo()));
		advert.setWard(StringUtils.standardize(advert.getWard()));
		advert.setDistrict(StringUtils.standardize(advert.getDistrict()));
		advert.setProvince(StringUtils.standardize(advert.getProvince()));
		advert.setStreet(StringUtils.standardize(advert.getStreet()));

		// Validation address
		String fullAddress = advert.getHouseNo() + ", " + advert.getStreet() + ", " + advert.getWard() + ", "
				+ advert.getDistrict() + ", " + advert.getProvince();
		advert.setAddressSearching(StringUtils.convertStringIgnoreUtf8(fullAddress));
		String fullExAddress = null;
		if (prevAdvertisement != null) {
			fullExAddress = prevAdvertisement.getHouseNo() + ", " + prevAdvertisement.getStreet() + ", "
					+ prevAdvertisement.getWard() + ", " + prevAdvertisement.getDistrict() + ", "
					+ prevAdvertisement.getProvince();
		}
		List<Advertisement> advs = advertisementRepository
				.findByHouseNoIgnoreCaseAndStreetIgnoreCaseAndWardIgnoreCaseAndDistrictIgnoreCaseAndProvinceIgnoreCase(
						advert.getHouseNo(), advert.getStreet(), advert.getWard(), advert.getDistrict(),
						advert.getProvince(), new PageRequest(0, 1))
				.getContent();

		log.debug("Preparing to save adv ==============");
		log.debug("Search address result: {}", advs.size());
		log.debug("Full address: {}", fullAddress);
		log.debug("Full ex address: {}", fullExAddress);
		log.debug("====================================");

		// Address conflict
		if (!advs.isEmpty() && fullAddress.length() > 8
				&& (fullExAddress == null || (fullExAddress != null && !fullExAddress.equalsIgnoreCase(fullAddress)))) {
			model.addAttribute("advertDto", advertDto);
			model.addAttribute("provinces", StreamSupport.stream(provinceRepository.findAll().spliterator(), false)
					.collect(Collectors.toList()));
			String errorMsg = "Địa chỉ vừa nhập đã được đặt<br/>" + "<a href='" + baseUrl + "/adv/"
					+ advs.get(0).getId() + "'>Bấm vào đây để xem chi tiết</a>";
			model.addAttribute("errorMsg", errorMsg);
			return "adv";
		}

		// Upload files
		List<String> pathFiles = new ArrayList<>();
		if (advertDto.getFiles() != null) {
			log.info("Preparing to upload files");
			pathFiles = new UploadFileUtils().uploadMultipleFile(advertDto.getFiles(), fileLimit);
			log.info("Upload successful");
		}

		// Add images to advertisement
		List<AdvImage> advImages = new ArrayList<>();
		for (String pathFile : pathFiles) {
			String name = pathFile.substring(pathFile.lastIndexOf(File.separator) + 1, pathFile.length());
			advImages.add(
					new AdvImage(name, pathFile, "Advertise_board", advert, advertisementService.checkIsMap(name)));
		}

		// For update
		if (prevAdvertisement != null) {
			// Delete previous files
			List<AdvImage> oldAdvImages = advImageRepository.findByAdvertisement_Id(prevAdvertisement.getId());
			oldAdvImages.forEach(image -> {
				if (!Arrays.asList(advertDto.getPrevImages()).contains(image.getId())) {
					try {
						FileUtils.forceDelete(new File(image.getUrl()));
					} catch (IOException e) {
						// Do nothing
					}
				} else {
					advImages.add(image);
				}
			});
			advert.setCreatedBy(prevAdvertisement.getCreatedBy());
		} else {
			advert.setCreatedDate(new Date());
			advert.setCreatedBy(userRepository.findOne(userDetails.getUserId()));
		}

		advert.setAdvImages(advImages);
		advert.setUpdatedDate(new Date());
		advert.setOwnerContactPersonSearching(StringUtils.convertStringIgnoreUtf8(advert.getOwnerContactPerson()));
		advert.setAdvCompNameSearching(StringUtils.convertStringIgnoreUtf8(advert.getAdvCompName()));

		// Save
		log.info("Saving an advert");
		Advertisement advertisement = advertisementRepository.save(advert);

		if (advertisement == null) {
			model.addAttribute("adv", advert);
			model.addAttribute("provinces", StreamSupport.stream(provinceRepository.findAll().spliterator(), false)
					.collect(Collectors.toList()));
			model.addAttribute("errorMsg", "There is an error occur, please contact IT department to support.");
			return "adv";
		}
		log.info("Save successful and redirect to view page");

		return "redirect:/adv/view?page=0&size=" + session.getAttribute("pageSize");
	}

	/*
	 * Get update page
	 */
	@RequestMapping(value = "/{advId}", method = RequestMethod.GET)
	public String adv(@PathVariable("advId") UUID advId, HttpSession session, ModelMap model) {
		// Get user roles info
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		NLAdvUserDetails userDetails = (NLAdvUserDetails) authentication.getPrincipal();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		List<String> roles = new ArrayList<>();
		authorities.stream().forEach(auth -> roles.add(auth.getAuthority()));

		log.debug("Getting {}'s info", advId);
		session.setAttribute(pageIndex, "advs");

		Advertisement advertisement = advertisementService.setPermission(advertisementRepository.findOne(advId), roles,
				userDetails.getUserId());
		if (advertisement != null) {
			AdvertisementDTO advertDto = new AdvertisementDTO();
			advertDto.setAdvertisement(advertisement);
			model.addAttribute("advertDto", advertDto);
			model.addAttribute("provinces", StreamSupport.stream(provinceRepository.findAll().spliterator(), false)
					.collect(Collectors.toList()));
			return "adv";
		} else {
			return "redirect:/adv/view?page=0&size=" + session.getAttribute("pageSize");
		}
	}

	/*
	 * Delete
	 */
	@RequestMapping(value = "/delete/{advId}", method = RequestMethod.GET)
	public String adv(@PathVariable("advId") UUID advId, HttpSession session) {
		log.debug("Delete {}", advId);
		advertisementRepository.delete(advId);
		return "redirect:/adv/view?page=0&size=" + session.getAttribute("pageSize");
	}

	private void setPageSize(int pageSize, HttpSession session) {
		if (session.getAttribute("pageSize") == null || pageSize <= 0 || pageSize > 10000 || pageSize == 10) {
			session.setAttribute("pageSize", 10);
		} else {
			session.setAttribute("pageSize", pageSize);
		}
	}
}
