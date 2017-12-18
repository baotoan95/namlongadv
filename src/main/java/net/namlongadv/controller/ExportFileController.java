package net.namlongadv.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.slf4j.Slf4j;
import net.namlongadv.dto.AdvertisementWrapperDTO;
import net.namlongadv.services.ExportFileService;

@Controller
@RequestMapping("export")
@Slf4j
public class ExportFileController {
	@Autowired
	private ExportFileService exportFileService;

	@RequestMapping(value = "excel", method = RequestMethod.POST)
	public void exportExcel(@ModelAttribute AdvertisementWrapperDTO advertWrapperDto, HttpServletResponse response) {
		log.debug("Export size: " + advertWrapperDto.getAdvs().size());
		XSSFWorkbook workbook = exportFileService.exportAdvsToExcel(advertWrapperDto.getAdvs());

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
}
