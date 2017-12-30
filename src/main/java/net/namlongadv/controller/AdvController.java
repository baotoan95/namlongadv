package net.namlongadv.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
import net.namlongadv.repositories.UserRepository;
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
	@Value("${namlongadv.file.limit}")
	private int fileLimit;
	@Value("${namlongadv.base_url}")
	private String baseUrl;

	@InitBinder
	public void bindingPreparation(WebDataBinder binder) {
		log.debug("Parse date");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor orderDateEditor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, orderDateEditor);
	}

	@RequestMapping(value = "/search")
	public String search(@RequestParam(value = "code", required = false) Optional<String> code,
			@RequestParam(value = "address", required = false) Optional<String> address,
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
			@RequestParam(value = "createdBy", required = false) Optional<String> createdBy,
			@RequestParam(value = "daterange", required = false) Optional<String> daterange,
			@RequestParam(value = "contactPerson", required = false) Optional<String> contactPerson, ModelMap model) {

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

			String sCode = null;
			String sAddress = "";
			String sCreatedBy = null;
			String sContactPerson = "";
			if (code.isPresent() && code.get().length() > 0) {
				sCode = code.get().toUpperCase();
				model.put("code", code.get());
			}
			if (address.isPresent() && address.get().length() > 0) {
				sAddress = address.get().toUpperCase();
				model.put("address", address.get());
			}
			if (createdBy.isPresent() && createdBy.get().length() > 0) {
				sCreatedBy = createdBy.get().toLowerCase();
				model.put("createdBy", createdBy.get());
			}
			if (contactPerson.isPresent() && contactPerson.get().length() > 0) {
				sContactPerson = contactPerson.get().toUpperCase();
				model.put("contactPerson", contactPerson.get());
			}

			rs = advertisementRepository.search(sCode, sAddress, sCreatedBy, from, to, sContactPerson,
					new PageRequest(page.intValue(), size.intValue()));
		} catch (ParseException e) {
			return "redirect:/adv/view?page=0&size=10";
		}

		model.put("page", rs);
		return "advs";
	}

	@RequestMapping(value = { "/view" }, method = RequestMethod.GET)
	public String advs(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = "10") Integer size, HttpSession session,
			ModelMap model) {
		log.info("Getting advs page");
		session.setAttribute(pageIndex, "advs");

		model.addAttribute("advertWrapper", new AdvertisementWrapperDTO());
		model.addAttribute("page", advertisementRepository
				.findAll(new PageRequest(page, size, new Sort(Sort.Direction.DESC, "updatedDate"))));
		return "advs";
	}

	@RequestMapping(method = RequestMethod.GET)
	public String adv(HttpSession session, ModelMap model) {
		log.debug("Getting adv page");
		session.setAttribute(pageIndex, "adv");
		AdvertisementDTO advDto = new AdvertisementDTO();
		// Generate code
		advDto.getAdvertisement().setCode(StringUtils.randomCode());
		model.addAttribute("advertDto", advDto);
		return "adv";
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, consumes = { "multipart/form-data" })
	public String adv(@ModelAttribute("advertDto") AdvertisementDTO advertDto, HttpSession session, ModelMap model) {
		Authentication authenticate = SecurityContextHolder.getContext().getAuthentication();
		NLAdvUserDetails userDetails = (NLAdvUserDetails) authenticate.getPrincipal();
		
		session.setAttribute(pageIndex, "adv");
		log.debug("Save adv");

		Advertisement advert = advertDto.getAdvertisement();
		Advertisement prevAdvertisement = null;
		try {
			prevAdvertisement = advertisementRepository.findOne(advert.getId());
		} catch (Exception e) {
			// Do nothing
		}

		// Validation code
		if (advert.getCode().trim().length() == 0) {
			advert.setCode(StringUtils.randomCode());
		}
		// Validation address
		String fullAddress = StringUtils.standardize(advert.getHouseNo() + ", " + advert.getStreet() + ", " + advert.getWard() + ", "
				+ advert.getDistrict() + ", " + advert.getProvince());
		String fullExAddress = null;
		if(prevAdvertisement != null) {
			fullExAddress = StringUtils.standardize(prevAdvertisement.getHouseNo() + ", " + prevAdvertisement.getStreet() + ", " + prevAdvertisement.getWard() + ", "
				+ prevAdvertisement.getDistrict() + ", " + prevAdvertisement.getProvince());
		}
		List<Advertisement> advs = advertisementRepository.findByAddress(fullAddress, new PageRequest(0, 1))
				.getContent();
		if (advs.size() > 0 && (fullExAddress != null && !fullExAddress.equalsIgnoreCase(fullAddress))) {
			model.addAttribute("advertDto", advertDto);
			String errorMsg = "Địa chỉ vừa nhập đã được đặt<br/>"
					+ "<a href='"+baseUrl+"/adv/"+advs.get(0).getId()+"'>Bấm vào đây để xem chi tiết</a>";
			model.addAttribute("errorMsg", errorMsg);
			return "adv";
		}

		List<String> pathFiles = new ArrayList<>();
		if (advertDto.getFiles() != null) {
			log.info("Preparing to upload files");
			pathFiles = UploadFileUtils.uploadMultipleFile(advertDto.getFiles(), fileLimit);
			log.info("Upload successful");

		}

		List<AdvImage> advImages = new ArrayList<>();
		for (String pathFile : pathFiles) {
			String name = pathFile.substring(pathFile.lastIndexOf(File.separator), pathFile.length());
			advImages.add(new AdvImage(name, pathFile, "Advertise_board", advert));
		}

		advert.setAdvImages(advImages);
		// For update
		if (prevAdvertisement != null) {
			advert.setUpdatedDate(new Date());
			log.debug("Image is empty: " + advImages.size());
			if (advImages.isEmpty()) {
				advert.setAdvImages(prevAdvertisement.getAdvImages());
			} else {
				// Delete previous files
				List<AdvImage> oldAdvImages = advImageRepository.findByAdvertisement_Id(prevAdvertisement.getId());
				for (AdvImage advImg : oldAdvImages) {
					try {
						FileUtils.forceDelete(new File(advImg.getUrl()));
					} catch (IOException e) {
						// Do nothing
					}
				}
			}
		} else {
			advert.setCreatedDate(new Date());
			advert.setUpdatedDate(new Date());
		}

		advert.setCreatedBy(userRepository.findOne(userDetails.getUserId()));
		// Save
		log.info("Saving an advert");
		Advertisement advertisement = advertisementRepository.save(advert);
		if (advertisement == null) {
			model.addAttribute("adv", advert);
			model.addAttribute("errorMsg", "There is an error occur, please contact IT to support.");
			return "adv";
		}
		log.info("Save successful and redirect to view page");

		return "redirect:/adv/view?page=0&size=10";
	}

	@RequestMapping(value = "/{advId}", method = RequestMethod.GET)
	public String adv(@PathVariable("advId") UUID advId, HttpSession session, ModelMap model) {
		log.debug("Getting {}'s info", advId);
		session.setAttribute(pageIndex, "advs");

		Advertisement advertisement = advertisementRepository.findOne(advId);
		if (advertisement != null) {
			AdvertisementDTO advertDto = new AdvertisementDTO();
			advertDto.setAdvertisement(advertisement);
			model.addAttribute("advertDto", advertDto);
			return "adv";
		} else {
			return "redirect:/adv/view?page=0&size=10";
		}
	}

	@RequestMapping(value = "/delete/{advId}", method = RequestMethod.GET)
	public String adv(@PathVariable("advId") UUID advId) {
		log.debug("Getting {}'s info", advId);

		advertisementRepository.delete(advId);
		return "redirect:/adv/view?page=0&size=10";
	}
}
