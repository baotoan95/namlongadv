package net.namlongadv.services;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.namlongadv.models.Advertisement;

@Service
@Slf4j
public class ExportFileService {
	public XSSFWorkbook exportAdvsToExcel(List<Advertisement> advs) {

		if (advs != null && !advs.isEmpty()) {
			advs.removeIf(adv -> adv.getId() == null);
		} else {
			return null;
		}
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet();
		// Create header
		String[] headerTitle = { "STT", "Vị trí", "Ngày bắt đầu", "Ngày kết thúc", "Đơn giá" };
		XSSFRow row = sheet.createRow(0);
		XSSFCell cell = null;
		for (int i = 0; i < headerTitle.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(headerTitle[i]);
		}

		// Create data
		Advertisement adv = null;
		for (int i = 0; i < advs.size(); i++) {
			adv = advs.get(i);
			row = sheet.createRow(i + 1);
			
			log.info("Export advertisement with id: {}", adv.getId());
			row.createCell(0).setCellValue(i + 1);
			row.createCell(1).setCellValue(adv.getLocation());
			row.createCell(2).setCellValue(adv.getStartDate());
			row.createCell(3).setCellValue(adv.getEndDate());
			row.createCell(4).setCellValue(adv.getPrice());
		}

		return workbook;
	}
}
