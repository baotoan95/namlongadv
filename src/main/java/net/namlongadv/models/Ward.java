package net.namlongadv.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ward")
@Setter
@Getter
public class Ward {
	@Id
	private String wardid;
	private String name;
	private String type;
	private String location;
	private String districtid;
}
