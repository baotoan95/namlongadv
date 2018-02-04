package net.namlongadv.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import net.namlongadv.common.Constants;
import net.namlongadv.models.Advertisement;
import net.namlongadv.repositories.AdvertisementRepository;
import net.namlongadv.utils.StringUtils;

@Service
public class AdvertisementService {
	@Autowired
	private AdvertisementRepository advertisementRepository;

	public Page<Advertisement> findByAddress(@Param("address") String address, @Param("roles") List<String> roles,
			Pageable pageable) {
		return advertisementRepository.findByAddress(address, searchPermission(roles), pageable);
	}

	public Page<Advertisement> findByUpdatedDate(@Param("start") Date start, @Param("end") Date end,
			@Param("roles") List<String> roles, Pageable pageable) {
		return advertisementRepository.findByUpdatedDate(start, end, searchPermission(roles), pageable);
	}

	public Page<Advertisement> search(@Param("code") String code, @Param("address") String address,
			@Param("username") String username, @Param("fromDate") Date from, @Param("toDate") Date to,
			@Param("contactName") String contactName, @Param("roles") List<String> roles, Pageable pageable) {
		return advertisementRepository.search(code, address, username, from, to, contactName, searchPermission(roles),
				pageable);

	}

	public Page<Advertisement> search(@Param("code") String code, @Param("address") String address,
			@Param("username") String username, @Param("fromDate") Date from, @Param("toDate") Date to,
			@Param("contactName") String contactName, String province_code, @Param("roles") List<String> roles,
			Pageable pageable) {
		return advertisementRepository.search(code, address, username, from, to, contactName, province_code,
				searchPermission(roles), pageable);
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
			return adv;
		}

		if (roles.contains("ROLE_BUSINESS") || roles.contains("ROLE_ACCOUNTANT")) {
			List<String> createdRoles = new ArrayList<>();
			adv.getCreatedBy().getRoles().forEach(role -> {
				createdRoles.add(role.getCode());
			});
			if (createdRoles.contains("ROLE_ADMIN") || !adv.getCreatedBy().getId().equals(userId)
					&& (createdRoles.contains("ROLE_BUSINESS") || createdRoles.contains("ROLE_ACCOUNTANT"))) {
				adv.setAllowEdit(false);
			}

			return adv;
		}

		if (roles.contains("ROLE_SURVEYOR")) {
			if (!adv.getCreatedBy().getId().equals(userId)) {
				adv.setAllowEdit(false);
			}
			return adv;
		}

		return adv;
	}

	public boolean checkIsMap(String str) {
		str = StringUtils.convertStringIgnoreUtf8(str.substring(0, str.lastIndexOf(".")));
		return str.lastIndexOf("map") == 13;
	}

}
