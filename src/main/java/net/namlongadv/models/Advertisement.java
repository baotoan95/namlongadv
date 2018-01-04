package net.namlongadv.models;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Table(name = "advertisements")
@Setter
@Getter
@Entity
@ToString
public class Advertisement {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private UUID id;
	private String code;
	private String title;
	private String street;
	private String houseNo;
	private String ward;
	private String district;
	private String province;
	private String size;
	private String map;
	private String describe;
	private String views;
	private Integer flow;
	private Integer implTime;
	private String implForm;
	private String lightSystem;
	private String ownerPhone;
	private String ownerEmail;
	private String ownerPrice;
	private String ownerContactPerson;
	private Date ownerStartDate;
	private Date ownerEndDate;
	private String advCompPhone;
	private String advCompEmail;
	private String advCompPrice;
	private String advCompContactPerson;
	private String advCompName;
	private Date advCompStartDate;
	private Date advCompEndDate;
	private String note;
	private String price;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "createdBy")
	private User createdBy;
	private Date createdDate;
	private Date updatedDate;
	private Integer numOfLamps;
	@OneToMany(mappedBy = "advertisement", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AdvImage> advImages;
}
