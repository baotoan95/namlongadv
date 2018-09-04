package net.namlongadv.models;

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
	private String widthSize;
	private String heightSize;
	private String amount;
	private String map;
	@Column(columnDefinition="text")
	private String describe;
	@Column(columnDefinition="text")
	private String views;
	@Column(columnDefinition="text")
	private String flow;
	private String implTime;
	private String implForm;
	private String lightSystem;
	private String ownerPhone;
	private String ownerEmail;
	private String ownerPrice;
	private String ownerContactPerson;
	private String ownerStartDate;
	private String ownerEndDate;
	@Column(columnDefinition="text")
	private String ownerNote;
	private String advCompPhone;
	private String advCompEmail;
	private String advCompPrice;
	private String advCompContactPerson;
	private String advCompName;
	private String advCompStartDate;
	private String advCompEndDate;
	@Column(columnDefinition="text")
	private String advCompNote;
	private String price;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "updatedBy")
	private User updatedBy;
	private Date updatedDate;
	private String type;
}
