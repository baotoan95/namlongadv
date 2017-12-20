package net.namlongadv.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.namlongadv.models.Advertisement;
import net.namlongadv.repositories.AdvertisementRepository;
import net.namlongadv.repositories.UserRepository;

@RestController
public class RestApiController {
	@Autowired
	private AdvertisementRepository advertisementRepository;
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(value = "/location", method = RequestMethod.GET)
	public List<String> addresses(@RequestParam(value = "term", required = true) String address) {
		Page<Advertisement> rs = advertisementRepository.findByAddressOrderByStartDate(address, new PageRequest(0, 20));
		List<String> addresses = new ArrayList<>();
		
		rs.getContent().stream().forEach(adv -> {
			StringBuilder iAddress = new StringBuilder();
			iAddress.append(adv.getHouseNo().length() > 0 ? adv.getHouseNo().concat(", ") : "");
			iAddress.append(adv.getStreet().length() > 0 ? adv.getStreet().concat(", ") : "");
			iAddress.append(adv.getWard().length() > 0 ? adv.getWard().concat(", ") : "");
			iAddress.append(adv.getDistrict().length() > 0 ? adv.getDistrict().concat(", ") : "");
			iAddress.append(adv.getProvince().length() > 0 ? adv.getProvince() : "");
			addresses.add(iAddress.toString());
		});
		return addresses;
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public List<String> users() {
		List<String> users = new ArrayList<>();
		userRepository.findAll().forEach(u -> {
			users.add(u.getUsername());
		});
		return users;
	}
}
