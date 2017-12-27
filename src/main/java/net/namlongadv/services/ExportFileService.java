package net.namlongadv.services;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.List;

import org.apache.poi.xslf.usermodel.SlideLayout;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFSlideLayout;
import org.apache.poi.xslf.usermodel.XSLFSlideMaster;
import org.apache.poi.xslf.usermodel.XSLFTextBox;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.apache.poi.xslf.usermodel.XSLFTextShape;
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
		String[] headerTitle = { "STT", "Vị trí" };
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
			row.createCell(1).setCellValue(adv.getTitle());
		}

		return workbook;
	}
	
	public XMLSlideShow exportPowerpoint(List<Advertisement> advs) {
		// Create a new presentation slide
		XMLSlideShow ppt = new XMLSlideShow();

		// Get layout
		XSLFSlideMaster defaultMaster = ppt.getSlideMasters().get(0);
		XSLFSlideLayout layout = defaultMaster.getLayout(SlideLayout.TITLE_AND_CONTENT);
		// Create new slide
		XSLFSlide slide = ppt.createSlide(layout);
		XSLFTextShape titleShape = slide.getPlaceholder(0);
		titleShape.setText("This is the title");
		XSLFTextShape contentShape = slide.getPlaceholder(1);
		contentShape.setText("And this is the content of this slide");
		
		XSLFTextBox shape = slide.createTextBox();
		XSLFTextParagraph p = shape.addNewTextParagraph();
		XSLFTextRun r = p.addNewTextRun();
		r.setText("Baeldung");
		r.setFontColor(Color.green);
		r.setFontSize(24.);
		shape.setAnchor(new Rectangle(10, 20, 800, 700));
		shape.setTopInset(100);
		shape.setLeftInset(100);
		
		return ppt;
	}
}
