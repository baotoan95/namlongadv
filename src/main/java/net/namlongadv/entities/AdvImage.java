package net.namlongadv.entities;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "adv_images")
@Setter
@Getter
@NoArgsConstructor
public class AdvImage {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private UUID id;
	private String name;
	private String url;
	private String type;
	private Boolean map;
	private int weight = 0;
	private boolean selected = true;
	@ManyToOne
	@JoinColumn(name = "advertisement")
	private Advertisement advertisement;
	
	public AdvImage(String name, String url, String type,
			Advertisement advertisement, Boolean map, int weight, boolean selected) {
		super();
		this.name = name;
		this.url = url;
		this.type = type;
		this.advertisement = advertisement;
		this.map = map;
		this.weight = weight;
		this.selected = selected;
	}
	
	public Boolean isMap() {
		return this.map == null ? false : this.map;
	}
	
	public String toString() {
		return name + " - " + map;
	}
}
