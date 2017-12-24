package net.namlongadv.tasks;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;
import net.namlongadv.models.Advertisement;
import net.namlongadv.repositories.AdvertisementRepository;
import net.namlongadv.services.MailService;
import net.namlongadv.utils.DateUtils;

@Slf4j
//@Configuration
//@EnableScheduling
public class CheckExpiration {
	@Autowired
	private AdvertisementRepository advertisementRepository;
	@Autowired
	private MailService mailService;
	
	@Scheduled(fixedRate = 10000)
	public void scheduleFixedRateTask() {
		List<Advertisement> advs = advertisementRepository.findByEndDateLessThanEqual(DateUtils.addDays(new Date(), 30));
		if(advs != null && !advs.isEmpty()) {
			try {
				mailService.sendEmail();
			} catch (Exception e) {
				log.error("Can't send a mail: " + e.getMessage());
			}
		}
	    log.debug(new Date().toString());
	}
}
