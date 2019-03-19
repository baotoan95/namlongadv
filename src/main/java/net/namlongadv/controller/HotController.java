package net.namlongadv.controller;

import java.io.File;

import org.ehcache.CachePersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.namlongadv.repositories.AdvImageRepository;
import net.namlongadv.repositories.HotRepository;

@RestController
@RequestMapping("/hotfix")
public class HotController {
	@Autowired
	private HotRepository hotRepository;
	@Autowired
	private AdvImageRepository advImageRepository;
	@Autowired
	private CacheManager cacheManager;

	@GetMapping("modifyDataType")
	public void modifyDataType() {
		try {
			hotRepository.modifyDataTypeAdv();
		} catch (Exception e) {
		}
		try {
			hotRepository.modifyDataTypeAdvHistory();			
		} catch (Exception e) {
		}
	}
	
	@GetMapping("reportImageMissing")
	public String reportImgNotExist() {
		StringBuilder report = new StringBuilder(); 
		report.append("<table border='1'>");
		report.append("<tr>"
						+ "<td>AdvId</td>"
						+ "<td>ImageId</td>"
						+ "<td>ImageName</td>"
					+ "</tr>");
		advImageRepository.findAll().spliterator().forEachRemaining(image -> {
			if(!new File(image.getUrl()).exists()) {
				report.append("<tr>"
								+ "<td>" + image.getAdvertisement().getId() + "</td>"
								+ "<td>" + image.getId() + "</td>"
								+ "<td>" + image.getName() + "</td>"
							+ "</tr>");
			}
		});
		report.append("</table>");
		return report.toString();
	}
	
	@GetMapping("deleteImageMissing")
	public String deleteImgNotExist() {
		advImageRepository.findAll().spliterator().forEachRemaining(image -> {
			if(!new File(image.getUrl()).exists()) {
				advImageRepository.deleteById(image.getId());
			}
		});
		return "Done";
	}
	
	@GetMapping("clearCache")
	public ResponseEntity<Void> clearCache() throws CachePersistenceException {
		cacheManager.getCacheNames().parallelStream().forEach(name -> cacheManager.getCache(name).clear());
		return ResponseEntity.ok().build();
	}
}
