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
	
	@Scheduled(fixedRate = 518400000)
	public void scheduleFixedRateTask() {
		Date date = DateUtils.addDays(new Date(), 30);
		List<Advertisement> advs = advertisementRepository.findByOwnerEndDateLessThanEqualOrAdvCompEndDateLessThanEqual(date, date);
		if(advs != null && !advs.isEmpty()) {
			try {
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append("<b>FYI</b><br/><br/>There are some advertisement contracts almost expired:<br/>");
				int index = 1;
				for(Advertisement adv: advs) {
					stringBuilder.append(index);
					stringBuilder.append(". <a href='"+baseUrl+"/adv/"+adv.getId()+"'>");
					stringBuilder.append(adv.getTitle());
					stringBuilder.append("</a><br/>");
					index++;
				}
				stringBuilder.append("<br/>Please take a moment to look over these<br/><br/>");
				stringBuilder.append("Thank you and best regards!<br/>");
				stringBuilder.append("<b>NamLong-Management App</b><br/><br/><i>(This is an automated email, please do not reply to this email)</i>");
				mailService.sendEmail(new String[] {"baotoan.95@gmail.com", "linh.do@namlongadvertising.com"}, stringBuilder.toString(), "Expiration alert - NamLongManagement App");
//				linh.do@namlongadvertising.com
			} catch (Exception e) {
				log.error("Can't send a mail: " + e.getMessage());
			}
		}
	    log.debug(new Date().toString());
	}
}
