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

import lombok.extern.slf4j.Slf4j;
import net.namlongadv.models.Advertisement;
import net.namlongadv.repositories.AdvertisementRepository;

@Service
@Slf4j
public class AdvertisementService {
	@Autowired
	private AdvertisementRepository advertisementRepository;

	public Page<Advertisement> findByAddress(@Param("address") String address, @Param("roles") List<String> roles,
			Pageable pageable) {
		roles = searchPermission(roles);
		return advertisementRepository.findByAddress(address, roles, pageable);
	}

	public Page<Advertisement> findByUpdatedDate(@Param("start") Date start, @Param("end") Date end,
			@Param("roles") List<String> roles, Pageable pageable) {
		roles = searchPermission(roles);
		return advertisementRepository.findByUpdatedDate(start, end, roles, pageable);
	}

	public Page<Advertisement> search(@Param("code") String code, @Param("address") String address,
			@Param("username") String username, @Param("fromDate") Date from, @Param("toDate") Date to,
			@Param("contactName") String contactName, @Param("roles") List<String> roles, Pageable pageable) {
		roles = searchPermission(roles);
		return advertisementRepository.search(code, address, username, from, to, contactName, roles, pageable);
	}

	public Page<Advertisement> findByRoles(@Param("roles") List<String> roles, Pageable pageable) {
		roles = searchPermission(roles);
		return advertisementRepository.findByRoles(roles, pageable);
	}

	private List<String> searchPermission(List<String> roles) {
		List<String> result = new ArrayList<>();
		if (roles != null && !roles.isEmpty()) {
			String role = roles.get(0);
			if (role.equals("ROLE_ADMIN") || role.equals("ROLE_BUSINESS") || role.equals("ROLE_ACCOUNTANT")) {

				result.add("ROLE_ADMIN");
				result.add("ROLE_BUSINESS");
				result.add("ROLE_ACCOUNTANT");
				result.add("ROLE_SURVEYOR");
			} else {

				result.add("ROLE_SURVEYOR");
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
				log.debug("Disable permission");
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

}
