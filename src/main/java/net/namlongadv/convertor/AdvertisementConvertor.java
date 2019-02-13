package net.namlongadv.convertor;

import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import net.namlongadv.dto.AdvertisementDTO;
import net.namlongadv.entities.Advertisement;
import net.namlongadv.utils.StringUtils;

public class AdvertisementConvertor {
	public static AdvertisementDTO convertToDTO(Advertisement adv) {
		AdvertisementDTO advDTO = new AdvertisementDTO();
		BeanUtils.copyProperties(adv, advDTO);
		advDTO.setPrevImages(adv.getAdvImages().stream().map(advImg -> {
			return ImageConvertor.convertToDTO(advImg);
		}).collect(Collectors.toList()));
		return advDTO;
	}

	public static Advertisement convertToEntity(AdvertisementDTO advDTO) {
		Advertisement adv = new Advertisement();
		BeanUtils.copyProperties(advDTO, adv);
		// Standardize
		adv.setHouseNo(StringUtils.standardize(adv.getHouseNo()));
		adv.setWard(StringUtils.standardize(adv.getWard()));
		adv.setDistrict(StringUtils.standardize(adv.getDistrict()));
		adv.setProvince(StringUtils.standardize(adv.getProvince()));
		adv.setStreet(StringUtils.standardize(adv.getStreet()));
		adv.setTitle(StringUtils.standardize(adv.getTitle().toUpperCase()));
		// Ignore utf8 for searching
		adv.setHouseNoSearching(StringUtils.convertStringIgnoreUtf8(adv.getHouseNo()));
		adv.setStreetSearching(StringUtils.convertStringIgnoreUtf8(adv.getStreet()));
		adv.setWardSearching(StringUtils.convertStringIgnoreUtf8(adv.getWard()));
		adv.setDistrictSearching(StringUtils.convertStringIgnoreUtf8(adv.getDistrict()));
		adv.setProvinceSearching(StringUtils.convertStringIgnoreUtf8(adv.getProvince()));
		adv.setTitleSearching(StringUtils.convertStringIgnoreUtf8(adv.getTitle()));

		String fullAddress = adv.getHouseNo() + ", " + adv.getStreet() + ", " + adv.getWard() + ", "
				+ adv.getDistrict() + ", " + adv.getProvince();
		adv.setAddressSearching(StringUtils.convertStringIgnoreUtf8(fullAddress));
		
		return adv;
	}
}
