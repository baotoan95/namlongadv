package net.namlongadv.tasks;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;
import net.namlongadv.models.Advertisement;
import net.namlongadv.models.Email;
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
			mailService.send(new Email("baotoan.95", "baotoan.95", "ABC", "kkk"));
		}
	    log.debug(new Date().toString());
	}
}
