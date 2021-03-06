package net.namlongadv.tasks;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;
import net.namlongadv.models.Advertisement;
import net.namlongadv.repositories.AdvertisementRepository;
import net.namlongadv.services.MailService;
import net.namlongadv.utils.DateUtils;

@Slf4j
@Configuration
@EnableScheduling
public class CheckExpiration {
	@Autowired
	private AdvertisementRepository advertisementRepository;
	@Autowired
	private MailService mailService;
	@Value("${namlongadv.base_url}")
	private String baseUrl;
	
	@Scheduled(cron = "0 0 5 * * ?")
	public void scheduleFixedRateTask() {
		Date date = DateUtils.addDays(new Date(), 30);
		List<Advertisement> advs = advertisementRepository.findByOwnerEndDateLessThanEqualOrAdvCompEndDateLessThanEqualOrderByAdvCompEndDateDescOwnerEndDateDesc(date, date);
		if(advs != null && !advs.isEmpty()) {
			try {
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append("<b>FYI</b><br/><br/>There are some advertisement contracts almost expired:<br/><br/>");
				stringBuilder.append("<table id=\"advs\" style=\"font-family: \"Trebuchet MS\", Arial, Helvetica, sans-serif; border-collapse: collapse; width: 100%;\">" + 
						"		<tr style=\"\">" + 
						"			<th style=\"border: 1px solid #ddd; padding: 8px; padding-top: 12px; padding-bottom: 12px; text-align: left; background-color: #4CAF50; color: white; text-align: center;\">STT</th>" + 
						"			<th style=\"border: 1px solid #ddd; padding: 8px; padding-top: 12px; padding-bottom: 12px; text-align: left; background-color: #4CAF50; color: white; text-align: center;\">Tiêu Đề</th>" + 
						"			<th style=\"border: 1px solid #ddd; padding: 8px; padding-top: 12px; padding-bottom: 12px; text-align: left; background-color: #4CAF50; color: white; text-align: center;\"></th>" + 
						"			<th style=\"border: 1px solid #ddd; padding: 8px; padding-top: 12px; padding-bottom: 12px; text-align: left; background-color: #4CAF50; color: white; text-align: center;\">Người Liên Hệ</th>" + 
						"			<th style=\"border: 1px solid #ddd; padding: 8px; padding-top: 12px; padding-bottom: 12px; text-align: left; background-color: #4CAF50; color: white; text-align: center;\">Tên Công Ty</th>" + 
						"			<th style=\"border: 1px solid #ddd; padding: 8px; padding-top: 12px; padding-bottom: 12px; text-align: left; background-color: #4CAF50; color: white; text-align: center;\">Ngày Kết Thúc</th>" + 
						"			<th style=\"border: 1px solid #ddd; padding: 8px; padding-top: 12px; padding-bottom: 12px; text-align: left; background-color: #4CAF50; color: white; text-align: center;\">Ghi Chú</th>" + 
						"		</tr>");
				int index = 1;
				String background = "#e0f9e4";
				for(Advertisement adv: advs) {
					if(index % 2 != 0) {
						background = "none";
					} else {
						background = "#fafffa";
					}
					stringBuilder.append(
									"<tr style=\"background-color: "+background+"\">" + 
							"			<td rowspan=\"2\" style=\"border: 1px solid #ddd; padding: 8px;\">"+ index +"</td>" + 
							"			<td rowspan=\"2\" style=\"border: 1px solid #ddd; padding: 8px;\">"+ adv.getTitle() +" (<a href='"+ baseUrl +"/adv/"+ adv.getId() +"'>view</a>)</td>" + 
							"			<td style=\"border: 1px solid #ddd; padding: 8px;\">Thông tin chủ nhà</td>" + 
							"			<td style=\"border: 1px solid #ddd; padding: 8px;\">"+ adv.getOwnerContactPerson() +"</td>" + 
							"			<td style=\"border: 1px solid #ddd; padding: 8px;\"></td>" + 
							"			<td style=\"border: 1px solid #ddd; padding: 8px;\">"+ (adv.getOwnerEndDate() != null ? DateUtils.convertDateToString(adv.getOwnerEndDate(), "dd/MM/yyyy") : "") +"</td>" + 
							"			<td style=\"border: 1px solid #ddd; padding: 8px;\">"+ adv.getOwnerNote() +"</td>" + 
							"		</tr>" + 
							"		<tr style=\"background-color: "+background+"\">" + 
							"			<td style=\"border: 1px solid #ddd; padding: 8px;\">Thông tin công ty</td>" + 
							"			<td style=\"border: 1px solid #ddd; padding: 8px;\">"+ adv.getAdvCompContactPerson() +"</td>" + 
							"			<td style=\"border: 1px solid #ddd; padding: 8px;\">" + adv.getAdvCompName() + "</td>" + 
							"			<td style=\"border: 1px solid #ddd; padding: 8px;\">"+ (adv.getAdvCompEndDate() != null ? DateUtils.convertDateToString(adv.getAdvCompEndDate(), "dd/MM/yyyy") : "") +"</td>" + 
							"			<td style=\"border: 1px solid #ddd; padding: 8px;\">"+ adv.getAdvCompNote() +"</td>" + 
							"		</tr>");
					index++;
				}
				stringBuilder.append("</table>");
				stringBuilder.append("<br/>Please take a moment to look over these.<br/><br/>");
				stringBuilder.append("Thank you and best regards!<br/>");
				stringBuilder.append("<b>NamLong-Management App</b><br/><br/><i>(This is an automated email, please do not reply to this email)</i>");
				mailService.sendEmail(new String[] {"linh.do@namlongadvertising.com", "duongtran@namlongadvertising.com", "namlong@namlongadvertising.com"}, stringBuilder.toString(), "Expiration alert - NamLongManagement App");
//				mailService.sendEmail(new String[] {"baotoan.95@gmail.com"}, stringBuilder.toString(), "Expiration alert - NamLongManagement App");
			} catch (Exception e) {
				log.error("Can't send a mail: " + e.getMessage());
			}
		}
	    log.debug(new Date().toString());
	}
}
