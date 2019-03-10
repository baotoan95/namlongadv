package net.namlongadv.repositories;

import org.springframework.data.repository.CrudRepository;

import net.namlongadv.common.ConfigKey;
import net.namlongadv.entities.AppConfig;

public interface AppConfigRepository extends CrudRepository<AppConfig, ConfigKey> {

}
