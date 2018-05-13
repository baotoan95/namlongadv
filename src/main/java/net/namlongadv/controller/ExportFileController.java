package net.namlongadv.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;
import net.namlongadv.dto.AdvertisementWrapperDTO;
import net.namlongadv.models.Advertisement;
import net.namlongadv.services.ExportFileService;

@Controller
@RequestMapping("export")
@Slf4j
public class ExportFileController {
	@Autowired
	private ExportFileService exportFileService;
	
	@InitBinder
	public void bindingPreparation(WebDataBinder binder) {
		binder.setAutoGrowCollectionLimit(10000);
		
		log.debug("Parse date");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		CustomDateEditor orderDateEditor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, orderDateEditor);
		
	}

	@RequestMapping(value = "excel", method = RequestMethod.POST)
	public void exportExcel(@ModelAttribute AdvertisementWrapperDTO advertWrapperDto, 
			@RequestParam("hideInfo") boolean hideInfo,
			HttpServletResponse response, HttpServletRequest request, HttpSession session) throws UnsupportedEncodingException {
		if (session.getAttribute("pageSize") == null) {
			session.setAttribute("pageSize", 10);
		}
		
		log.debug("Export size: " + advertWrapperDto.getAdvs().size());
		List<Advertisement> advsNeedExport = advertWrapperDto.getAdvs().stream().filter(adv -> adv.getId() != null).collect(Collectors.toList());
		XSSFWorkbook workbook = exportFileService.exportAdvsToExcel(advsNeedExport, hideInfo);

		if (workbook != null) {
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment; filename=advertisements.xlsx");
			try {
				workbook.write(response.getOutputStream());
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}
	}
	
	@RequestMapping(value = "powerpoint", method = RequestMethod.POST)
	public void exportPowerpoint(@ModelAttribute AdvertisementWrapperDTO advertWrapperDto, 
			HttpServletResponse response, HttpSession session) {
		if (session.getAttribute("pageSize") == null) {
			session.setAttribute("pageSize", 10);
		}
		
		log.debug("Export size: " + advertWrapperDto.getAdvs().size());
		List<Advertisement> advsNeedExport = advertWrapperDto.getAdvs().stream().filter(adv -> adv.getId() != null).collect(Collectors.toList());
		XMLSlideShow presentation = exportFileService.exportPowerpoint(advsNeedExport);

		if (presentation != null) {
			response.setContentType("application/vnd.ms-powerpoint");
			response.setHeader("Content-disposition", "attachment; filename=namlongadv-presentation.pptx");
			try {
				presentation.write(response.getOutputStream());
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}
	}
}
