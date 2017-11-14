package net.namlongadv.models;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "advertisements")
@Setter
@Getter
public class Advertisement {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private UUID id;
	private String code;
	private String location;
	private String street;
	private String houseNo;
	private String ward;
	private String district;
	private String province;
	private String size;
	private String price;
	private String map;
	private String ownerPhone;
	private String ownerEmail;
	private String ownerPrice;
	private String ownerContactPerson;
	private String advCompPhone;
	private String advCompEmail;
	private String advCompPrice;
	private String advCompContactPerson;
	private String note;
	@OneToMany(mappedBy = "advertisement", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AdvImage> advImages;
}
