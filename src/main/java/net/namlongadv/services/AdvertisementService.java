package net.namlongadv.services;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import net.namlongadv.common.Enums;
import net.namlongadv.common.SearchCriteria;
import net.namlongadv.constant.Constants;
import net.namlongadv.convertor.AdvertisementConvertor;
import net.namlongadv.dto.AdvertisementDTO;
import net.namlongadv.dto.PageDTO;
import net.namlongadv.entities.AdvImage;
import net.namlongadv.entities.Advertisement;
import net.namlongadv.exceptions.BadRequestException;
import net.namlongadv.repositories.AdvertisementRepository;
import net.namlongadv.repositories.UserRepository;
import net.namlongadv.specs.AdvertSpecificationBuilder;
import net.namlongadv.utils.AuthenticationUtils;
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
	private AdvChangeHistoryService advChangeHistoryService;
	@Autowired
	private UserRepository userRepository;

	@Value("${namlongadv.file.limit}")
	private int fileLimit;
	@Value("${namlongadv.base_url}")
	private String baseUrl;
	@Autowired
	private ObjectMapper objectMapper;

	@PersistenceContext
	private EntityManager em;

	public PageDTO<AdvertisementDTO> findAll(String filter, int page, int size) throws BadRequestException {
		page = page - 1;
		size = size <= -1 ? Integer.MAX_VALUE : size;
		filter = StringUtils.isEmptyOrNull(filter) ? "[]" : filter;
		try {
			List<SearchCriteria> params = objectMapper.readValue(filter, new TypeReference<List<SearchCriteria>>() {});
			Specification<Advertisement> specification = new AdvertSpecificationBuilder(params).build();
			Page<Advertisement> advertPage = advertisementRepository.findAll(specification, new PageRequest(page, size, new Sort(Sort.Direction.ASC, "addressSearching")));
			System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
			List<AdvertisementDTO> advDTOs = sort(advertPage.getContent()).stream().map(adv -> {
				return AdvertisementConvertor.convertToDTO(adv);
			}).collect(Collectors.toList());
			// TODO: view permission
			return new PageDTO<>(advertPage.getTotalElements(), advDTOs);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BadRequestException("Can't parse search criteries");
		}
	}
	
	public AdvertisementDTO findOne(UUID advId) throws BadRequestException {
		Advertisement adv = advertisementRepository.findOne(advId);
		if(adv != null) {
			return AdvertisementConvertor.convertToDTO(adv);
		}
		throw new BadRequestException("Advertisement is not existed in the system");
	}
	
	public AdvertisementDTO addNew(AdvertisementDTO advDTO) throws BadRequestException {
		// Address conflict
		List<Advertisement> addressConflict = checkAddressConflict(advDTO, null);
		if (!addressConflict.isEmpty() && !advDTO.isIgnoreError()) {
			// TODO: list address conflict
			throw new BadRequestException("Address conflicted");
		}
		Advertisement advert = AdvertisementConvertor.convertToEntity(advDTO);
		advert.setCreatedDate(new Date());
		advert.setCreatedBy(userRepository.findOne(AuthenticationUtils.getUserDetails().getUserId()));
		advert.setCode(generateCode(advert.getProvinceCode()));
		advert.setAdvImages(uploadAdvImage(advDTO));
		advert.setUpdatedDate(new Date());
		advert.setOwnerContactPersonSearching(StringUtils.convertStringIgnoreUtf8(advert.getOwnerContactPerson()));
		advert.setAdvCompNameSearching(StringUtils.convertStringIgnoreUtf8(advert.getAdvCompName()));
		
		return AdvertisementConvertor.convertToDTO(advertisementRepository.save(advert));
	}
	
	public boolean delete(UUID userId) {
		try {
			advertisementRepository.delete(userId);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public Page<Advertisement> findByAddress(@Param("address") String address, @Param("roles") List<String> roles,
			Pageable pageable) {
		return advertisementRepository.findByAddress(address, searchPermission(roles), pageable);
	}

	public Page<Advertisement> findByUpdatedDate(@Param("start") Date start, @Param("end") Date end,
			@Param("roles") List<String> roles, Pageable pageable) {
		return advertisementRepository.findByUpdatedDate(start, end, searchPermission(roles), pageable);
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

	public List<AdvImage> uploadAdvImage(AdvertisementDTO advertDto) {
		List<AdvImage> advImages = new ArrayList<>();
		if (advertDto.getImages() != null) {
			log.info("Preparing to upload files");
			// Upload
			List<String> pathFiles = new UploadFileUtils().uploadMultipleFile(advertDto.getImages(), fileLimit, false);
			for (String pathFile : pathFiles) {
				String name = pathFile.substring(pathFile.lastIndexOf(File.separator) + 1, pathFile.length());
				advImages.add(new AdvImage(name, pathFile, "Advertise_board", Advertisement.builder().id(advertDto.getId()).build(), false));
			}
			// Get map file
			if (Objects.nonNull(advertDto.getMap()) && !advertDto.getMap().isEmpty()) {
				List<String> fileNames = new UploadFileUtils().uploadMultipleFile(Arrays.asList(advertDto.getMap()),
						fileLimit, true);
				if (!fileNames.isEmpty()) {
					String name = fileNames.get(0).substring(fileNames.get(0).lastIndexOf(File.separator) + 1,
							fileNames.get(0).length());
					advImages.add(new AdvImage(name, fileNames.get(0), "Advertise_board", Advertisement.builder().id(advertDto.getId()).build(),
							true));
				}
			}
			log.info("Upload successful");
		}

		return advImages;
	}

	public List<Advertisement> checkAddressConflict(AdvertisementDTO advertDto, Advertisement prevAdvertisement) {
		String fullAddress = advertDto.getHouseNo() + ", " + advertDto.getStreet() + ", " + advertDto.getWard() + ", "
				+ advertDto.getDistrict() + ", " + advertDto.getProvince();
		String fullExAddress = null;
		if (Objects.nonNull(prevAdvertisement)) {
			fullExAddress = prevAdvertisement.getHouseNo() + ", " + prevAdvertisement.getStreet() + ", "
					+ prevAdvertisement.getWard() + ", " + prevAdvertisement.getDistrict() + ", "
					+ prevAdvertisement.getProvince();
		}

		List<Advertisement> advs = advertisementRepository
				.findByHouseNoIgnoreCaseAndStreetIgnoreCaseAndWardIgnoreCaseAndDistrictIgnoreCaseAndProvinceIgnoreCase(
						advertDto.getHouseNo(), advertDto.getStreet(), advertDto.getWard(), advertDto.getDistrict(),
						advertDto.getProvince(), new PageRequest(0, 100))
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

//	public String update(AdvertisementDTO advertDto, HttpSession session, ModelMap model) {
//		// Update if it publish to billboardquangcao.com
//		if (advertDto.getAdvertisement().getPublishedId() != null
//				&& advertDto.getAdvertisement().getPublishedId() > 0) {
//			advertDto.getAdvertisement().setPublishedDate(new Date());
//		} else {
//			advertDto.getAdvertisement().setPublishedDate(null);
//		}
//
//		// Check existed
//		Advertisement advert = advertDto.getAdvertisement();
//		final Advertisement prevAdvertisement = advertisementRepository.findOne(advert.getId());
//		try {
//			log.debug("Updating adv with id: {}", advertDto.getAdvertisement().getId());
//			if (Objects.nonNull(prevAdvertisement)) {
//				// Standardize and Fulfill data
//				advert = fulfillStandardizeAdv(advert);
//				List<Advertisement> addressConflict = checkAddressConflict(advertDto, prevAdvertisement);
//
//				if (!addressConflict.isEmpty()) {
//					advertDto.getAdvertisement().setAdvImages(prevAdvertisement.getAdvImages());
//					advertDto.setAdvertisement(advertDto.getAdvertisement());
//					advertDto.setAdvertisement(
//							setPermission(advertDto.getAdvertisement(), AuthenticationUtils.getCurrentUserRoles(),
//									AuthenticationUtils.getUserDetails().getUserId()));
//					model.addAttribute(Constants.MODEL_NAME.ADV_DTO, advertDto);
//					model.addAttribute(Constants.MODEL_NAME.PROVINCES, StreamSupport
//							.stream(provinceRepository.findAll().spliterator(), false).collect(Collectors.toList()));
//
//					// Prepare error message (address conflict)
//					model.addAttribute(Constants.MODEL_NAME.ERROR_MESSAGE,
//							generateAddressConflictMessage(addressConflict, Enums.ACTION.UPDATE));
//					return PathContants.ADVERT;
//				}
//
//				// Normal cases
//				List<AdvImage> advImages = uploadAdvImage(advertDto);
//				// Delete previous files
//				List<AdvImage> oldAdvImages = advImageRepository.findByAdvertisement_Id(prevAdvertisement.getId());
//				UUID prevAdvId = prevAdvertisement.getId();
//				oldAdvImages.forEach(image -> {
//					int duplicated = advImageRepository.countByIdAndAdvertisement_IdNot(image.getId(), prevAdvId);
//					log.debug("Image duplicated: {}", duplicated);
//					if (!Arrays.asList(advertDto.getPrevImages()).contains(image.getId()) && duplicated == 0) {
//						try {
//							FileUtils.forceDelete(new File(image.getUrl()));
//							advImageRepository.delete(image.getId());
//						} catch (IOException e) {
//							// Do nothing
//						}
//					} else {
//						advImages.add(image);
//					}
//				});
//
//				advert.setCreatedBy(prevAdvertisement.getCreatedBy());
//				// Update code if it change
//				if (!prevAdvertisement.getProvinceCode().equals(advert.getProvinceCode())) {
//					advert.setCode(generateCode(advert.getProvinceCode()));
//				}
//
//				advert.setAdvImages(advImages);
//				advert.setUpdatedDate(new Date());
//				advert.setOwnerContactPersonSearching(
//						StringUtils.convertStringIgnoreUtf8(advert.getOwnerContactPerson()));
//				advert.setAdvCompNameSearching(StringUtils.convertStringIgnoreUtf8(advert.getAdvCompName()));
//
//				// Saving change history
//				User updatedBy = userRepository.findByUsername(AuthenticationUtils.getUserDetails().getUsername());
//				advChangeHistoryService.saveHistory(
//						advChangeHistoryService.createIfDifferent(prevAdvertisement, advert, updatedBy, false));
//
//				log.info("Saving an advert");
//				Advertisement advertisement = advertisementRepository.save(advert);
//
//				if (advertisement == null) {
//					model.addAttribute(PathContants.ADVERT, advert);
//					model.addAttribute(Constants.MODEL_NAME.PROVINCES, StreamSupport
//							.stream(provinceRepository.findAll().spliterator(), false).collect(Collectors.toList()));
//					model.addAttribute(Constants.MODEL_NAME.ERROR_MESSAGE,
//							"There is an error occur, please contact IT department to support.");
//					return PathContants.ADVERT;
//				}
//				log.info("Save successful and redirect to view page");
//
//				String queryString = session.getAttribute(Constants.SESSION_NAME.SEARCH_CONTENT) != null
//						? session.getAttribute(Constants.SESSION_NAME.SEARCH_CONTENT).toString()
//						: null;
//				log.debug("In search: {}", queryString);
//				if (queryString != null) {
//					return Constants.ADV_SEARCH_REDIRECT + session.getAttribute(Constants.SESSION_NAME.SEARCH_CONTENT);
//				}
//				int page = session.getAttribute(Constants.SESSION_NAME.CURRENT_PAGE) != null
//						? (int) session.getAttribute("currentPage")
//						: 0;
//
//				return "redirect:/adv/view?page=" + page + "&size="
//						+ session.getAttribute(Constants.SESSION_NAME.PAGE_SIZE);
//			}
//		} catch (Exception e) {
//			log.error("{}", e);
//		}
//		return Constants.ADV_PAGE_REDIRECT + session.getAttribute(Constants.SESSION_NAME.PAGE_SIZE);
//	}

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
