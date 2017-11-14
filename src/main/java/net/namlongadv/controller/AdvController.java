package net.namlongadv.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;
import net.namlongadv.dto.AdvertisementDTO;
import net.namlongadv.models.AdvImage;
import net.namlongadv.models.Advertisement;
import net.namlongadv.repositories.AdvImageRepository;
import net.namlongadv.repositories.AdvertisementRepository;
import net.namlongadv.repositories.DistrictRepository;
import net.namlongadv.repositories.ProvinceRepository;
import net.namlongadv.repositories.WardRepository;
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
	private ProvinceRepository provinceRepository;
	@Autowired
	private DistrictRepository districtRepository;
	@Autowired
	private WardRepository wardRepository;

	@RequestMapping(value = { "/view" }, method = RequestMethod.GET)
	public String advs(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = "10") Integer size, HttpSession session,
			ModelMap model) {
		log.info("Getting advs page");
		session.setAttribute(pageIndex, "advs");
		model.addAttribute("page", advertisementRepository.findAll(new PageRequest(page, size)));
		model.addAttribute("provinces", provinceRepository.findAll());
		model.addAttribute("districts", districtRepository.findAll());
		model.addAttribute("wards", wardRepository.findAll());
		return "advs";
	}

	@RequestMapping(method = RequestMethod.GET)
	public String adv(HttpSession session, ModelMap model) {
		log.debug("Getting adv page");
		session.setAttribute(pageIndex, "adv");
		model.addAttribute("advertDto", new AdvertisementDTO());
		model.addAttribute("provinces", provinceRepository.findAll());
		model.addAttribute("districts", districtRepository.findAll());
		model.addAttribute("wards", wardRepository.findAll());
		return "adv";
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, consumes = { "multipart/form-data" })
	public String adv(@ModelAttribute("advertDto") AdvertisementDTO advertDto, HttpSession session, ModelMap model) {
		session.setAttribute(pageIndex, "adv");
		log.debug("Save adv");

		Advertisement advert = advertDto.getAdvertisement();
		Advertisement prevAdvertisement = null;
		try {
			prevAdvertisement = advertisementRepository.findOne(advert.getId());
		} catch (Exception e) {
			// Do nothing
		}

		List<String> pathFiles = new ArrayList<>();
		if (advertDto.getFiles() != null) {
			log.info("Preparing to upload files");
			pathFiles = UploadFileUtils.uploadMultipleFile(advertDto.getFiles());
			log.info("Upload successful");

		}

		List<AdvImage> advImages = new ArrayList<>();
		for (String pathFile : pathFiles) {
			String name = pathFile.substring(pathFile.lastIndexOf(File.separator), pathFile.length());
			advImages.add(new AdvImage(name, pathFile, "Advertise_board", advert));
		}

		advert.setAdvImages(advImages);
		if (prevAdvertisement != null) {
			log.debug("Image is empty: " + advImages.size());
			if(advImages.isEmpty()) {
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
		}

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

		model.addAttribute("provinces", provinceRepository.findAll());
		model.addAttribute("districts", districtRepository.findAll());
		model.addAttribute("wards", wardRepository.findAll());
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
