package net.namlongadv.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "adv_change_history")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AdvChangeHistory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private UUID id;
	private UUID advertId;
	private String code;
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
	@Column(columnDefinition="text")
	private String implTime;
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
	@Column(columnDefinition="text")
	private String ownerStartDate;
	@Column(columnDefinition="text")
	private String ownerEndDate;
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
	@Column(columnDefinition="text")
	private String advCompStartDate;
	@Column(columnDefinition="text")
	private String advCompEndDate;
	@Column(columnDefinition="text")
	private String advCompNote;
	@Column(columnDefinition="text")
	private String price;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "updatedBy")
	private User updatedBy;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "createdBy")
	private User createdBy;
	private Date updatedDate;
	@Column(columnDefinition="text")
	private String type;
	@Transient
	private int numOfChanges;
}
