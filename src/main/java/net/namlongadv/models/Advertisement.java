package net.namlongadv.models;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

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
	private String provinceCode;
	@Column(columnDefinition="text")
	private String title;
	@Column(columnDefinition="text")
	private String street;
	@Column(columnDefinition="text")
	private String houseNo;
	@Column(columnDefinition="text")
	private String ward;
	@Column(columnDefinition="text")
	private String district;
	@Column(columnDefinition="text")
	private String province;
	private String widthSize;
	private String heightSize;
	private String map;
	@Column(columnDefinition="text")
	private String describe;
	@Column(columnDefinition="text")
	private String views;
	@Column(columnDefinition="text")
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
	@Column(columnDefinition="text")
	private String ownerNote;
	private String advCompPhone;
	private String advCompEmail;
	private String advCompPrice;
	private String advCompContactPerson;
	private String advCompName;
	private Date advCompStartDate;
	private Date advCompEndDate;
	@Column(columnDefinition="text")
	private String advCompNote;
	private String price;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "createdBy")
	private User createdBy;
	private Date createdDate;
	private Date updatedDate;
	@OneToMany(mappedBy = "advertisement", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AdvImage> advImages;
	private Boolean trash = false;
	@Transient
	private boolean allowEdit = true;
	private Date publishedDate;
	private Integer publishedId;
	private String type;
	@Column(columnDefinition="text")
	private String addressSearching;
	@Column(columnDefinition="text")
	private String advCompNameSearching;
	@Column(columnDefinition="text")
	private String ownerContactPersonSearching;
}
