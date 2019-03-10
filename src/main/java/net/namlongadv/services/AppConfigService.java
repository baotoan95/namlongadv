package net.namlongadv.services;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.namlongadv.common.ConfigKey;
import net.namlongadv.entities.AppConfig;
import net.namlongadv.repositories.AppConfigRepository;

@Service
public class AppConfigService {
	@Autowired
	private AppConfigRepository appConfigRepository;
	
	public String getConfig(ConfigKey key) {
		AppConfig appConfig = this.appConfigRepository.findOne(key);
		if(Objects.isNull(appConfig)) {
			return null;
		}
		return appConfig.getValue();
	}
}
