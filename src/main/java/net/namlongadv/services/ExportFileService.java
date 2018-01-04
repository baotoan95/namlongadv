package net.namlongadv.services;

import java.awt.Color;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.sl.usermodel.ShapeType;
import org.apache.poi.sl.usermodel.TextParagraph.TextAlign;
import org.apache.poi.sl.usermodel.TextShape.TextAutofit;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFAutoShape;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextBox;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.namlongadv.models.AdvImage;
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
					font.setFontHeightInPoints((short) 13);
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
					font.setFontHeightInPoints((short) 13);
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
		XMLSlideShow ppt = null;
		try {
			File template = new ClassPathResource("templates/quotation.pptx").getFile();
			// Create a new presentation slide
			ppt = new XMLSlideShow(new FileInputStream(template));

			XSLFSlide slide = null;
			Advertisement adv = null;

			String text = "";
			for (int i = 0; i < advs.size(); i++) {
				adv = advs.get(i);
				slide = ppt.createSlide();
				
				createProvinceHolder(slide, adv.getProvince());
				
				XSLFTextBox textBox = slide.createTextBox();
				textBox.setAnchor(new Rectangle(0, 100, 300, 500));
				text = "Vị trí: " + adv.getHouseNo() + ", " + adv.getStreet() + ", " + adv.getWard() + ", " + adv.getDistrict() + ", " + adv.getProvince();
				createListImage(text, textBox);
				
				text = "Tầm nhìn: " + adv.getViews();
				createListImage(text, textBox);
				
				text = "Kích thước: " + adv.getSize();
				createListImage(text, textBox);
				
				text = "Đơn giá: " + adv.getPrice() + " (USD/năm)";
				createListImage(text, textBox);
				
				text = "Mật độ: " + adv.getFlow() + " (người/ngày)";
				createListImage(text, textBox);
				
				text = "Thời gian thực hiện: " + adv.getImplTime() + " ngày";
				createListImage(text, textBox);
				
				text = "Hình thức thực hiện: " + adv.getImplForm();
				createListImage(text, textBox);
				
				text = "Hệ thống chiếu sáng: " + adv.getLightSystem();
				createListImage(text, textBox);
				
				XSLFTextParagraph l5 = textBox.addNewTextParagraph();
				l5.setIndentLevel(0);
				XSLFTextRun text5 = l5.addNewTextRun();
				text5.setText("Đơn giá trên bao gồm: in ấn, công treo, xin phép và bảo hành 12 tháng. Chưa bao gồm VAT.");
				text5.setBold(true);
				text5.setFontSize(16d);
				
				XSLFTextBox title = slide.createTextBox();
				title.setAnchor(new Rectangle(300, 80, 400, 30));
				title.setTextAutofit(TextAutofit.NORMAL);
				XSLFTextParagraph pTitle = title.addNewTextParagraph();
				pTitle.setTextAlign(TextAlign.CENTER);
				XSLFTextRun textTitle = pTitle.addNewTextRun();
				textTitle.setText(adv.getTitle());
				textTitle.setBold(true);
				textTitle.setFontColor(Color.blue);
				textTitle.setFontSize(20d);
				
				List<AdvImage> images = adv.getAdvImages();
				images = images.stream().filter(image -> image.getId() != null).collect(Collectors.toList());
				if(images != null && !images.isEmpty()) {
					String imagePath = images.get(0).getUrl();
					insertImage(imagePath, new Rectangle(305, 140, 400, 380), ppt, slide);
					images.remove(0);

					for(AdvImage advImage: images) {
						slide = ppt.createSlide();
						createProvinceHolder(slide, adv.getProvince());
						
						XSLFTextBox slideTitle = slide.createTextBox();
						slideTitle.setAnchor(new Rectangle(0, 60, 700, 30));
						slideTitle.setTextAutofit(TextAutofit.NORMAL);
						XSLFTextParagraph pTitle2 = slideTitle.addNewTextParagraph();
						pTitle2.setTextAlign(TextAlign.CENTER);
						XSLFTextRun textTitle2 = pTitle2.addNewTextRun();
						textTitle2.setText(adv.getTitle());
						textTitle2.setBold(true);
						textTitle2.setFontColor(Color.blue);
						textTitle2.setFontSize(20d);
						
						insertImage(advImage.getUrl(), new Rectangle(110, 140, 500, 380), ppt, slide);
					}
				}
				
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}

		return ppt;
	}
	
	private void createListImage(String text, XSLFTextBox textBox) {
		XSLFTextParagraph l1 = textBox.addNewTextParagraph();
		l1.setIndentLevel(0);
		l1.setBullet(true);
		l1.setIndent(-20d);
		l1.setLeftMargin(30d);
		XSLFTextRun text1 = l1.addNewTextRun();
		text1.setText(text);
		text1.setBold(true);
		text1.setFontSize(16d);
	}
	
	private void insertImage(String imgUrl, Rectangle anchor, XMLSlideShow ppt, XSLFSlide slide) {
		byte[] pictureData;
		try {
			pictureData = IOUtils.toByteArray(new FileInputStream(imgUrl));
			XSLFPictureData pd = ppt.addPicture(pictureData, PictureData.PictureType.PNG);
			XSLFPictureShape picture = slide.createPicture(pd);
			picture.setAnchor(anchor);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	private void createProvinceHolder(XSLFSlide slide, String province) {
		XSLFAutoShape cardRect = ((XSLFSlide) slide).createAutoShape();
		cardRect.setShapeType(ShapeType.RECT);
		cardRect.setFillColor(Color.ORANGE);
		cardRect.setAnchor(new Rectangle(0, 0, 250, 50));
		cardRect.setVerticalAlignment(org.apache.poi.sl.usermodel.VerticalAlignment.MIDDLE);
		XSLFTextParagraph textParagraph = cardRect.addNewTextParagraph();
		XSLFTextRun textRun = textParagraph.addNewTextRun();
		textRun.setText(province);
		textRun.setFontColor(Color.RED);
		textRun.setFontSize(25d);
		textRun.setBold(true);
	}
}
