//package net.namlongadv.controller;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import net.namlongadv.models.Advertisement;
//import net.namlongadv.repositories.UserRepository;
//import net.namlongadv.services.AdvertisementService;
//import net.namlongadv.utils.StringUtils;
//
//@RestController
//public class RestApiController {
//	@Autowired
//	private AdvertisementService advertisementService;
//	@Autowired
//	private UserRepository userRepository;
//	
//	@RequestMapping(value = "/location", method = RequestMethod.GET)
//	public List<String> addresses(@RequestParam(value = "term", required = true) String address) {
//		// Get user roles info
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//		List<String> roles = new ArrayList<>(); 
//		authorities.stream().forEach(auth -> roles.add(auth.getAuthority()));
//		roles.add("ROLE_ADMIN");
//		
//		Page<Advertisement> rs = advertisementService.findByAddress(StringUtils.convertStringIgnoreUtf8(address), roles, new PageRequest(0, 20));
//		List<String> addresses = new ArrayList<>();
//		
//		rs.getContent().stream().forEach(adv -> {
//			StringBuilder iAddress = new StringBuilder();
//			iAddress.append(adv.getHouseNo().concat(", "));
//			iAddress.append(adv.getStreet().concat(", "));
//			iAddress.append(adv.getWard().concat(", "));
//			iAddress.append(adv.getDistrict().concat(", "));
//			iAddress.append(adv.getProvince());
//			addresses.add(iAddress.toString());
//		});
//		return addresses;
//	}
//	
//	@RequestMapping(value = "/users", method = RequestMethod.GET)
//	public List<String> users() {
//		List<String> users = new ArrayList<>();
//		userRepository.findAll().forEach(u -> {
//			users.add(u.getUsername());
//		});
//		return users;
//	}
//}
