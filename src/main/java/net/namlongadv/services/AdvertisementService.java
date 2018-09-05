package net.namlongadv.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import lombok.extern.slf4j.Slf4j;
import net.namlongadv.common.Constants;
import net.namlongadv.common.Enums;
import net.namlongadv.common.PathContants;
import net.namlongadv.dto.AdvertisementDTO;
import net.namlongadv.models.AdvImage;
import net.namlongadv.models.Advertisement;
import net.namlongadv.models.User;
import net.namlongadv.repositories.AdvImageRepository;
import net.namlongadv.repositories.AdvertisementRepository;
import net.namlongadv.repositories.ProvinceRepository;
import net.namlongadv.repositories.UserRepository;
import net.namlongadv.utils.AuthenticationUtils;
import net.namlongadv.utils.DateUtils;
import net.namlongadv.utils.StringUtils;
import net.namlongadv.utils.UploadFileUtils;
import net.namlongadv.utils.WindowsExplorerComparator;

@Service
@Transactional
@Slf4j
public class AdvertisementService {
	@Autowired
	private AdvertisementRepository advertisementRepository;
	@Autowired
	private ProvinceRepository provinceRepository;
	@Autowired
	private AdvImageRepository advImageRepository;
	@Autowired
	private AdvChangeHistoryService advChangeHistoryService;
	@Autowired
	private UserRepository userRepository;

	@Value("${namlongadv.file.limit}")
	private int fileLimit;
	@Value("${namlongadv.base_url}")
	private String baseUrl;

	@PersistenceContext
	private EntityManager em;

	public Page<Advertisement> findByAddress(@Param("address") String address, @Param("roles") List<String> roles,
			Pageable pageable) {
		return advertisementRepository.findByAddress(address, searchPermission(roles), pageable);
	}

	public Page<Advertisement> findByUpdatedDate(@Param("start") Date start, @Param("end") Date end,
			@Param("roles") List<String> roles, Pageable pageable) {
		return advertisementRepository.findByUpdatedDate(start, end, searchPermission(roles), pageable);
	}

	public Page<Advertisement> findAll(Pageable pageable) {
		return advertisementRepository.findAll(pageable);
	}

	public Page<Advertisement> search(Optional<String> code, Optional<String> address, Optional<String> createdBy,
			Date from, Date to, Optional<String> contactPerson, Optional<String> houseNo, Optional<String> street,
			Optional<String> ward, Optional<String> district, Optional<String> provinceCode, Optional<String> title,
			int page, int size) {
		String sCode = "";
		String sAddress = "";
		String sCreatedBy = "";
		String sContactPerson = "";
		String sHouseNo = null;
		String sStreet = null;
		String sWard = null;
		String sDistrict = null;
		String sProvince = null;
		String sTitle = "";

		if (code.isPresent() && code.get().length() > 0) {
			sCode = code.get().trim();
		}
		if (address.isPresent() && address.get().length() > 0) {
			sAddress = address.get().trim();
		}
		if (createdBy.isPresent() && createdBy.get().length() > 0) {
			sCreatedBy = createdBy.get().trim();
		}
		if (contactPerson.isPresent() && contactPerson.get().length() > 0) {
			sContactPerson = contactPerson.get().trim();
		}
		if (houseNo.isPresent() && houseNo.get().length() > 0) {
			sHouseNo = houseNo.get().trim();
		}
		if (street.isPresent() && street.get().length() > 0) {
			sStreet = street.get().trim();
		}
		if (ward.isPresent() && ward.get().length() > 0) {
			sWard = ward.get().trim();
		}
		if (district.isPresent() && district.get().length() > 0) {
			sDistrict = district.get().trim();
		}
		if (provinceCode.isPresent() && provinceCode.get().length() > 0) {
			sProvince = provinceCode.get().trim();
		}
		if (title.isPresent() && title.get().length() > 0) {
			sTitle = title.get().trim();
		}

		log.debug("Search function ============");
		log.debug("sCode: {}", sCode);
		log.debug("sAddress: {}", sAddress);
		log.debug("sCreatedBy: {}", sCreatedBy);
		log.debug("sContactPerson: {}", sContactPerson);
		log.debug("Time: {} {}", from, to);
		log.debug("sHouseNo {}", sHouseNo);
		log.debug("sStreet {}", sStreet);
		log.debug("sWard {}", sWard);
		log.debug("sDistrict {}", sDistrict);
		log.debug("sProvince {}", sProvince);
		log.debug("============================");

		sCode = sCode == null ? null : StringUtils.convertStringIgnoreUtf8(sCode).toUpperCase();
		sAddress = sAddress == null ? null : StringUtils.convertStringIgnoreUtf8(sAddress).toLowerCase();
		sCreatedBy = sCreatedBy == null ? null : StringUtils.convertStringIgnoreUtf8(sCreatedBy).toLowerCase();
		sContactPerson = sContactPerson == null ? null
				: StringUtils.convertStringIgnoreUtf8(sContactPerson).toLowerCase();
		sHouseNo = sHouseNo == null ? null : StringUtils.convertStringIgnoreUtf8(sHouseNo).toLowerCase();
		sStreet = sStreet == null ? null : StringUtils.convertStringIgnoreUtf8(sStreet).toLowerCase();
		sWard = sWard == null ? null : StringUtils.convertStringIgnoreUtf8(sWard).toLowerCase();
		sDistrict = sDistrict == null ? null : StringUtils.convertStringIgnoreUtf8(sDistrict).toLowerCase();
		sTitle = sTitle == null ? null : StringUtils.convertStringIgnoreUtf8(sTitle).toLowerCase();

		return advertisementRepository.search(sCode, sAddress, sCreatedBy, from, to, sContactPerson, sHouseNo, sStreet,
				sWard, sDistrict, sTitle, sProvince,
				new PageRequest(page, size, new Sort(Sort.Direction.ASC, "addressSearching")));
	}

	public String getSearchQueryAndSetToModel(Optional<String> code, Optional<String> address,
			Optional<String> createdBy, Optional<String> daterange, Optional<String> contactPerson,
			Optional<String> houseNo, Optional<String> street, Optional<String> ward, Optional<String> district,
			Optional<String> provinceCode, Optional<String> title, ModelMap model) {
		StringBuilder queryString = new StringBuilder();
		// Code
		if (code.isPresent() && code.get().length() > 0) {
			model.put("code", code.get().trim());
			queryString.append("&code=" + code.get().trim());
		}
		// Address
		if (address.isPresent() && address.get().length() > 0) {
			model.put("address", address.get().trim());
			queryString.append("&address=" + address.get().trim());
		}
		// Create by
		if (createdBy.isPresent() && createdBy.get().length() > 0) {
			model.put("createdBy", createdBy.get().trim());
			queryString.append("&createdBy=" + createdBy.get().trim());
		}
		// Contact person
		if (contactPerson.isPresent() && contactPerson.get().length() > 0) {
			model.put("contactPerson", contactPerson.get().trim());
			queryString.append("&contactPerson=" + contactPerson.get().trim());
		}
		// House No
		if (houseNo.isPresent() && houseNo.get().length() > 0) {
			model.put("houseNo", houseNo.get().trim());
			queryString.append("&houseNo=" + houseNo.get().trim());
		}
		// Street
		if (street.isPresent() && street.get().length() > 0) {
			model.put("street", street.get().trim());
			queryString.append("&street=" + street.get().trim());
		}
		// Ward
		if (ward.isPresent() && ward.get().length() > 0) {
			model.put("ward", ward.get().trim());
			queryString.append("&ward=" + ward.get().trim());
		}
		// District
		if (district.isPresent() && district.get().length() > 0) {
			model.put("district", district.get().trim());
			queryString.append("&district=" + district.get().trim());
		}
		// Province
		if (provinceCode.isPresent() && provinceCode.get().length() > 0) {
			model.put("province", provinceCode.get().trim());
			queryString.append("&province=" + provinceCode.get().trim());
		}
		// Title
		if (title.isPresent() && title.get().length() > 0) {
			model.put("title", title.get().trim());
			queryString.append("&title=" + title.get().trim());
		}
		// Date range
		if (!daterange.isPresent() || (daterange.isPresent() && daterange.get().trim().length() == 0)) {
			log.debug("Filter with default date range");
			daterange = Optional.of("01/01/2017 - " + DateUtils.convertDateToString(new Date()));
		}
		model.put("daterange", daterange.get());
		queryString.append("&daterange=" + daterange.get());

		return queryString.toString();
	}

	public List<Advertisement> sort(List<Advertisement> advs) {
		return advs.stream().sorted(new Comparator<Advertisement>() {
			private final WindowsExplorerComparator windowsExplorerComparator = new WindowsExplorerComparator();

			@Override
			public int compare(Advertisement adv1, Advertisement adv2) {
				return windowsExplorerComparator.compare(adv1.getAddressSearching(), adv2.getAddressSearching());
			}
		}).collect(Collectors.toList());
	}

	public Page<Advertisement> findByRoles(@Param("roles") List<String> roles, Pageable pageable) {
		return advertisementRepository.findByRoles(searchPermission(roles), pageable);
	}

	private List<String> searchPermission(List<String> roles) {
		List<String> result = new ArrayList<>();
		if (roles != null && !roles.isEmpty()) {
			String role = roles.get(0);
			if (role.equals(Constants.USER_ROLE.ADMIN) || role.equals(Constants.USER_ROLE.BUSINESS)
					|| role.equals(Constants.USER_ROLE.ACCOUNTANT)) {

				result.add(Constants.USER_ROLE.ADMIN);
				result.add(Constants.USER_ROLE.BUSINESS);
				result.add(Constants.USER_ROLE.ACCOUNTANT);
				result.add(Constants.USER_ROLE.SURVEYOR);
			} else {

				result.add(Constants.USER_ROLE.SURVEYOR);
			}
		}
		return result;
	}

	public List<Advertisement> setPermission(List<Advertisement> advs, List<String> roles, UUID userId) {
		advs.forEach(adv -> {
			adv = setPermission(adv, roles, userId);
		});
		return advs;
	}

	public Advertisement setPermission(Advertisement adv, List<String> roles, UUID userId) {
		if (roles.contains("ROLE_ADMIN")) {
			adv.setBelongCurrentUser(true);
			return adv;
		}

		if (!adv.getCreatedBy().getId().equals(userId)) {
			adv.setAllowDelete(false);
		} else {
			adv.setBelongCurrentUser(true);
		}
		return adv;
	}

	public boolean checkIsMap(String str) {
		str = StringUtils.convertStringIgnoreUtf8(str.substring(0, str.lastIndexOf(".")));
		return str.lastIndexOf("map") == 13;
	}

	public Advertisement fulfillStandardizeAdv(Advertisement advert) {
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
		advert.setTitle(StringUtils.standardize(advert.getTitle().toUpperCase()));

		return addSearchingFields(advert);
	}

	public Advertisement addSearchingFields(Advertisement advert) {
		// Ignore utf8 for searching
		advert.setHouseNoSearching(StringUtils.convertStringIgnoreUtf8(advert.getHouseNo()));
		advert.setStreetSearching(StringUtils.convertStringIgnoreUtf8(advert.getStreet()));
		advert.setWardSearching(StringUtils.convertStringIgnoreUtf8(advert.getWard()));
		advert.setDistrictSearching(StringUtils.convertStringIgnoreUtf8(advert.getDistrict()));
		advert.setProvinceSearching(StringUtils.convertStringIgnoreUtf8(advert.getProvince()));
		advert.setTitleSearching(StringUtils.convertStringIgnoreUtf8(advert.getTitle()));

		String fullAddress = advert.getHouseNo() + ", " + advert.getStreet() + ", " + advert.getWard() + ", "
				+ advert.getDistrict() + ", " + advert.getProvince();
		advert.setAddressSearching(StringUtils.convertStringIgnoreUtf8(fullAddress));

		return advert;
	}

	public List<AdvImage> uploadAdvImage(AdvertisementDTO advertDto) {
		List<AdvImage> advImages = new ArrayList<>();
		if (advertDto.getFiles() != null) {
			log.info("Preparing to upload files");
			// Upload
			List<String> pathFiles = new UploadFileUtils().uploadMultipleFile(advertDto.getFiles(), fileLimit, false);
			for (String pathFile : pathFiles) {
				String name = pathFile.substring(pathFile.lastIndexOf(File.separator) + 1, pathFile.length());
				advImages.add(new AdvImage(name, pathFile, "Advertise_board", advertDto.getAdvertisement(), false));
			}
			// Get map file
			if (Objects.nonNull(advertDto.getMap()) && !advertDto.getMap().isEmpty()) {
				List<String> fileNames = new UploadFileUtils().uploadMultipleFile(Arrays.asList(advertDto.getMap()),
						fileLimit, true);
				if (!fileNames.isEmpty()) {
					String name = fileNames.get(0).substring(fileNames.get(0).lastIndexOf(File.separator) + 1,
							fileNames.get(0).length());
					advImages.add(new AdvImage(name, fileNames.get(0), "Advertise_board", advertDto.getAdvertisement(),
							true));
				}
			}
			log.info("Upload successful");
		}

		return advImages;
	}

	public List<Advertisement> checkAddressConflict(AdvertisementDTO advertDto, Advertisement prevAdvertisement) {
		Advertisement advert = advertDto.getAdvertisement();

		String fullAddress = advert.getHouseNo() + ", " + advert.getStreet() + ", " + advert.getWard() + ", "
				+ advert.getDistrict() + ", " + advert.getProvince();
		String fullExAddress = null;
		if (Objects.nonNull(prevAdvertisement)) {
			fullExAddress = prevAdvertisement.getHouseNo() + ", " + prevAdvertisement.getStreet() + ", "
					+ prevAdvertisement.getWard() + ", " + prevAdvertisement.getDistrict() + ", "
					+ prevAdvertisement.getProvince();
		}

		List<Advertisement> advs = advertisementRepository
				.findByHouseNoIgnoreCaseAndStreetIgnoreCaseAndWardIgnoreCaseAndDistrictIgnoreCaseAndProvinceIgnoreCase(
						advert.getHouseNo(), advert.getStreet(), advert.getWard(), advert.getDistrict(),
						advert.getProvince(), new PageRequest(0, 100))
				.getContent();

		if (!advs.isEmpty() && fullAddress.length() > 8 && !advertDto.isIgnoreError()
				&& (Objects.isNull(fullExAddress) || !fullExAddress.equalsIgnoreCase(fullExAddress))) {
			return advs;
		} else {
			return Collections.emptyList();
		}
	}

	public String generateAddressConflictMessage(List<Advertisement> conflictItems, Enums.ACTION action) {
		// Prepare error message (address conflict)
		StringBuilder errorMsg = new StringBuilder("=============<br/>Địa chỉ vừa nhập đã được đặt:<br/>");
		Advertisement adv = null;
		for (int i = 0; i < conflictItems.size(); i++) {
			adv = conflictItems.get(i);
			errorMsg.append(
					(i + 1) + ". <a href='" + baseUrl + "/adv/" + adv.getId() + "'>" + adv.getTitle() + "</a><br/>");
		}

		if (action == Enums.ACTION.ADD) {
			errorMsg.append("Nhấn nút <b>Thêm</b> để tiếp tục lưu.<br/>=============");
		} else {
			errorMsg.append("Nhấn nút <b>Cập Nhật</b> để tiếp tục lưu.<br/>=============");
		}

		return errorMsg.toString();
	}

	public String update(AdvertisementDTO advertDto, HttpSession session, ModelMap model) {
		// Update if it publish to billboardquangcao.com
		if (advertDto.getAdvertisement().getPublishedId() != null
				&& advertDto.getAdvertisement().getPublishedId() > 0) {
			advertDto.getAdvertisement().setPublishedDate(new Date());
		} else {
			advertDto.getAdvertisement().setPublishedDate(null);
		}

		// Check existed
		Advertisement advert = advertDto.getAdvertisement();
		final Advertisement prevAdvertisement = advertisementRepository.findOne(advert.getId());
		try {
			log.debug("Updating adv with id: {}", advertDto.getAdvertisement().getId());
			if (Objects.nonNull(prevAdvertisement)) {
				// Standardize and Fulfill data
				advert = fulfillStandardizeAdv(advert);
				List<Advertisement> addressConflict = checkAddressConflict(advertDto, prevAdvertisement);

				if (!addressConflict.isEmpty()) {
					advertDto.getAdvertisement().setAdvImages(prevAdvertisement.getAdvImages());
					advertDto.setAdvertisement(advertDto.getAdvertisement());
					advertDto.setAdvertisement(
							setPermission(advertDto.getAdvertisement(), AuthenticationUtils.getCurrentUserRoles(),
									AuthenticationUtils.getUserDetails().getUserId()));
					model.addAttribute(Constants.MODEL_NAME.ADV_DTO, advertDto);
					model.addAttribute(Constants.MODEL_NAME.PROVINCES, StreamSupport
							.stream(provinceRepository.findAll().spliterator(), false).collect(Collectors.toList()));

					// Prepare error message (address conflict)
					model.addAttribute(Constants.MODEL_NAME.ERROR_MESSAGE,
							generateAddressConflictMessage(addressConflict, Enums.ACTION.UPDATE));
					return PathContants.ADVERT;
				}

				// Normal cases
				List<AdvImage> advImages = uploadAdvImage(advertDto);
				// Delete previous files
				List<AdvImage> oldAdvImages = advImageRepository.findByAdvertisement_Id(prevAdvertisement.getId());
				UUID prevAdvId = prevAdvertisement.getId();
				oldAdvImages.forEach(image -> {
					int duplicated = advImageRepository.countByIdAndAdvertisement_IdNot(image.getId(), prevAdvId);
					log.debug("Image duplicated: {}", duplicated);
					if (!Arrays.asList(advertDto.getPrevImages()).contains(image.getId()) && duplicated == 0) {
						try {
							FileUtils.forceDelete(new File(image.getUrl()));
							advImageRepository.delete(image.getId());
						} catch (IOException e) {
							// Do nothing
						}
					} else {
						advImages.add(image);
					}
				});

				advert.setCreatedBy(prevAdvertisement.getCreatedBy());
				// Update code if it change
				if (!prevAdvertisement.getProvinceCode().equals(advert.getProvinceCode())) {
					advert.setCode(generateCode(advert.getProvinceCode()));
				}

				advert.setAdvImages(advImages);
				advert.setUpdatedDate(new Date());
				advert.setOwnerContactPersonSearching(
						StringUtils.convertStringIgnoreUtf8(advert.getOwnerContactPerson()));
				advert.setAdvCompNameSearching(StringUtils.convertStringIgnoreUtf8(advert.getAdvCompName()));

				// Saving change history
				User updatedBy = userRepository.findByUsername(AuthenticationUtils.getUserDetails().getUsername());
				advChangeHistoryService.saveHistory(
						advChangeHistoryService.createIfDefferent(prevAdvertisement, advert, updatedBy, false));

				log.info("Saving an advert");
				Advertisement advertisement = advertisementRepository.save(advert);

				if (advertisement == null) {
					model.addAttribute(PathContants.ADVERT, advert);
					model.addAttribute(Constants.MODEL_NAME.PROVINCES, StreamSupport
							.stream(provinceRepository.findAll().spliterator(), false).collect(Collectors.toList()));
					model.addAttribute(Constants.MODEL_NAME.ERROR_MESSAGE,
							"There is an error occur, please contact IT department to support.");
					return PathContants.ADVERT;
				}
				log.info("Save successful and redirect to view page");

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

				return "redirect:/adv/view?page=" + page + "&size="
						+ session.getAttribute(Constants.SESSION_NAME.PAGE_SIZE);
			}
		} catch (Exception e) {
			log.error("{}", e);
		}
		return Constants.ADV_PAGE_REDIRECT + session.getAttribute(Constants.SESSION_NAME.PAGE_SIZE);
	}

	public String generateCode(String provinceCode) {
		String code = "";
		while (true) {
			code = provinceCode + "-" + StringUtils.randomCode();
			if (advertisementRepository.findByCode(code) == null) {
				return code;
			}
		}
	}

	public void initHistory() {
		advertisementRepository.findAll().forEach(adv -> {
			advChangeHistoryService.saveHistory(advChangeHistoryService.convertToAdvChangeHistory(adv));
		});
	}
}
