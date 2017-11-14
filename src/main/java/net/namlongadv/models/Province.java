package net.namlongadv.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "province")
@Setter
@Getter
public class Province {
	@Id
	private String provinceid;
	private String name;
	private String type;
}
