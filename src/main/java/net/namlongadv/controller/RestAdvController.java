//package net.namlongadv.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import net.namlongadv.models.Advertisement;
//import net.namlongadv.services.HibernateSearchService;
//
//@RestController
//public class RestAdvController {
//	@Autowired
//	private HibernateSearchService searchService;
//	
//	@RequestMapping(value = "/query")
//	public List<Advertisement> advs(@RequestParam("key") String key) {
//		List<Advertisement> advs = searchService.fuzzySearch(key);
//		return advs;
//	}
//}
