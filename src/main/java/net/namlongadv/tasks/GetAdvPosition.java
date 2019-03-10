package net.namlongadv.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import net.namlongadv.common.ConfigKey;
import net.namlongadv.convertor.AdvertisementConvertor;
import net.namlongadv.dto.PositionDTO;
import net.namlongadv.entities.AppConfig;
import net.namlongadv.repositories.AdvertisementRepository;
import net.namlongadv.repositories.AppConfigRepository;

@Configuration
@EnableScheduling
@Slf4j
public class GetAdvPosition {
	@Autowired
	private AdvertisementRepository advRepository;
	@Autowired
	private AppConfigRepository appConfigRepository;
	@Autowired
	private ObjectMapper objectMapper;
	
	//0 0 0 * * ? : start at 00:00
	//*/5 * * * * ? : every 5m
	@Scheduled(cron = "0 0 0 * * ?")
	public void getAdvPosition() {
		log.info("Starting update adv positions");
		List<PositionDTO> positions = new ArrayList<PositionDTO>();
		advRepository.findAll().forEach(adv -> {
			PositionDTO positionDTO = AdvertisementConvertor.convertToPositionDTO(adv);
			if(!Objects.isNull(positionDTO)) {
				positions.add(positionDTO);	
			}
		});
		try {
			appConfigRepository.save(new AppConfig(ConfigKey.ADV_POSITION, objectMapper.writeValueAsString(positions)));
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}
		log.debug("Finished update adv positions (" + positions.size() + ")");
	}
}
