package net.namlongadv.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "district")
@Setter
@Getter
public class District {
	@Id
	private String districtid;
	private String name;
	private String type;
	private String location;
	private String provinceid;
}
