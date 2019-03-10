package net.namlongadv.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.namlongadv.common.ConfigKey;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_config")
public class AppConfig {
	@Id
	@Enumerated(EnumType.STRING)
	private ConfigKey key;
	@Column(columnDefinition="text")
	private String value;
}
