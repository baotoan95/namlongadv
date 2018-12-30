package net.namlongadv.entities;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	@Column(columnDefinition="text")
	private String widthSize;
	@Column(columnDefinition="text")
	private String heightSize;
	@Column(columnDefinition="text")
	private String amount;
	@Column(columnDefinition="text")
	private String map;
	@Column(columnDefinition="text")
	private String describe;
	@Column(columnDefinition="text")
	private String views;
	@Column(columnDefinition="text")
	private String flow;
	private Integer implTime;
	@Column(columnDefinition="text")
	private String implForm;
	@Column(columnDefinition="text")
	private String lightSystem;
	@Column(columnDefinition="text")
	private String ownerPhone;
	@Column(columnDefinition="text")
	private String ownerEmail;
	@Column(columnDefinition="text")
	private String ownerPrice;
	@Column(columnDefinition="text")
	private String ownerContactPerson;
	private Date ownerStartDate;
	private Date ownerEndDate;
	@Column(columnDefinition="text")
	private String ownerNote;
	@Column(columnDefinition="text")
	private String advCompPhone;
	@Column(columnDefinition="text")
	private String advCompEmail;
	@Column(columnDefinition="text")
	private String advCompPrice;
	@Column(columnDefinition="text")
	private String advCompContactPerson;
	@Column(columnDefinition="text")
	private String advCompName;
	private Date advCompStartDate;
	private Date advCompEndDate;
	@Column(columnDefinition="text")
	private String advCompNote;
	@Column(columnDefinition="text")
	private String price;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "createdBy")
	@JsonIgnore
	private User createdBy;
	private Date createdDate;
	private Date updatedDate;
	@OneToMany(mappedBy = "advertisement", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AdvImage> advImages;
	private Boolean trash = false;
	private Date publishedDate;
	private Integer publishedId;
	@Column(columnDefinition="text")
	private String type;
	@Column(columnDefinition="text")
	private String addressSearching;
	@Column(columnDefinition="text")
	private String advCompNameSearching;
	@Column(columnDefinition="text")
	private String ownerContactPersonSearching;
	@Column(columnDefinition="text")
	private String houseNoSearching;
	@Column(columnDefinition="text")
	private String streetSearching;
	@Column(columnDefinition="text")
	private String wardSearching;
	@Column(columnDefinition="text")
	private String districtSearching;
	@Column(columnDefinition="text")
	private String provinceSearching;
	@Column(columnDefinition="text")
	private String titleSearching;
	@Transient
	private boolean allowEdit = true;
	@Transient
	private boolean allowDelete = true;
	@Transient
	private boolean belongCurrentUser = false;
}
