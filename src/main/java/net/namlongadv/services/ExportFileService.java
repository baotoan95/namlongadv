package net.namlongadv.services;

import java.awt.Color;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
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
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.namlongadv.models.Advertisement;
import net.namlongadv.utils.DateUtils;

@Service
@Slf4j
public class ExportFileService {
	public XSSFWorkbook exportAdvsToExcel(List<Advertisement> advs) {
		if (advs != null && !advs.isEmpty()) {
			advs.removeIf(adv -> adv.getId() == null);
		} else {
			return null;
		}

		XSSFWorkbook workbook = null;
		try {
			File template = new ClassPathResource("templates/excel.xlsx").getFile();
			FileInputStream fileInputStream = new FileInputStream(template);
			workbook = new XSSFWorkbook(fileInputStream);
			XSSFSheet sheet = workbook.getSheetAt(0);

			int startData = 5;
			XSSFRow row = null;
			XSSFCell cell = null;
			Advertisement adv = null;

			XSSFCellStyle cellStyle = null;
			

			for (int i = 0; i < advs.size(); i++) {
				adv = advs.get(i);
				if (i % 2 == 0) {
					cellStyle = workbook.createCellStyle();
					cellStyle.setBorderBottom(BorderStyle.THIN);
					cellStyle.setBorderTop(BorderStyle.THIN);
					cellStyle.setBorderLeft(BorderStyle.THIN);
					cellStyle.setBorderRight(BorderStyle.THIN);
					cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
					cellStyle.setFillForegroundColor(new XSSFColor(Color.WHITE));
					cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					Font font = workbook.createFont();
					font.setFontName("Times New Roman");
					font.setFontHeightInPoints((short)13);
					font.setColor(IndexedColors.BLACK.getIndex());
					cellStyle.setFont(font);
				} else {
					cellStyle = workbook.createCellStyle();
					cellStyle.setBorderBottom(BorderStyle.THIN);
					cellStyle.setBorderTop(BorderStyle.THIN);
					cellStyle.setBorderLeft(BorderStyle.THIN);
					cellStyle.setBorderRight(BorderStyle.THIN);
					cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
					cellStyle.setFillForegroundColor(new XSSFColor(new Color(138, 220, 247)));
					cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					Font font = workbook.createFont();
					font.setFontHeightInPoints((short)13);
					font.setFontName("Times New Roman");
					cellStyle.setFont(font);
				}
				
				
				for (int k = 1; k <= 2; k++) {
					row = sheet.createRow(startData);

					
					if (startData % 2 != 0) {
						// Index
						cellStyle.setAlignment(HorizontalAlignment.CENTER);
						cell = row.createCell(0);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(i + 1);
						
						cellStyle.setAlignment(HorizontalAlignment.JUSTIFY);
						// Code
						cell = row.createCell(1);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(adv.getCode());
						// Title
						cell = row.createCell(2);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(adv.getTitle());
						// House no
						cell = row.createCell(3);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(adv.getHouseNo());
						// Street
						cell = row.createCell(4);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(adv.getStreet());
						// Ward
						cell = row.createCell(5);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(adv.getWard());
						// District
						cell = row.createCell(6);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(adv.getDistrict());
						// Province
						cell = row.createCell(7);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(adv.getProvince());
						// Coordinates
						cell = row.createCell(8);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(adv.getMap());
						// Size
						cell = row.createCell(9);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(adv.getSize());
						// Number of lamps
						cell = row.createCell(10);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(adv.getNumOfLamps() != null ? adv.getNumOfLamps() : 0);
						// Describe
						cell = row.createCell(11);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(adv.getDescribe());
						// Note
						cell = row.createCell(12);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(adv.getNote());

						cell = row.createCell(13);
						cell.setCellStyle(cellStyle);
						cell.setCellValue("Thông tin chủ nhà");
						// Owner Phone
						cell = row.createCell(14);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(adv.getOwnerPhone());
						// Owner Email
						cell = row.createCell(15);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(adv.getOwnerEmail());
						// Owner Price
						cell = row.createCell(16);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(adv.getOwnerPrice());
						// Owner start date
						cell = row.createCell(17);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(DateUtils.convertDateToString(adv.getOwnerStartDate()));
						// Owner end date
						cell = row.createCell(18);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(DateUtils.convertDateToString(adv.getOwnerEndDate()));
						// Owner contact
						cell = row.createCell(19);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(adv.getOwnerContactPerson());
						
						cell = row.createCell(20);
						cell.setCellStyle(cellStyle);
					} else {
						for (int j = 0; j <= 12; j++) {
							cell = row.createCell(j);
							cell.setCellStyle(cellStyle);
						}
						// Company start date
						cell = row.createCell(13);
						cell.setCellStyle(cellStyle);
						cell.setCellValue("Thông tin CT quảng cáo");
						// Company start date
						cell = row.createCell(14);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(adv.getAdvCompPhone());
						// Company start date
						cell = row.createCell(15);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(adv.getAdvCompEmail());
						// Company start date
						cell = row.createCell(16);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(adv.getAdvCompPrice());
						// Company start date
						cell = row.createCell(17);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(DateUtils.convertDateToString(adv.getAdvCompStartDate()));
						// Company start date
						cell = row.createCell(18);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(DateUtils.convertDateToString(adv.getAdvCompEndDate()));
						// Company start date
						cell = row.createCell(19);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(adv.getAdvCompContactPerson());
						// Company start date
						cell = row.createCell(20);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(adv.getAdvCompName());
					}
					startData++;
				}
			}
			 mergeCell(sheet, startData - (advs.size() * 2), advs.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return workbook;
	}

	private void mergeCell(XSSFSheet sheet, int startData, int numOfItems) {
		for (int i = 0; i < numOfItems; i++) {
			for (int j = 0; j <= 12; j++) {
				CellRangeAddress cellRangeAddress = new CellRangeAddress(startData, startData + 1, j, j);
				sheet.addMergedRegion(cellRangeAddress);
			}
			startData += 2;
		}
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
