package net.namlongadv.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.namlongadv.entities.User;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3808418990645968646L;
	
	private UUID id;
	private String code;
	private String provinceCode;
	private String title;
	private String street;
	private String houseNo;
	private String ward;
	private String district;
	private String province;
	private String widthSize;
	private String heightSize;
	private String amount;
	private String describe;
	private String views;
	private String flow;
	private Integer implTime;
	private String implForm;
	private String lightSystem;
	private String ownerPhone;
	private String ownerEmail;
	private String ownerPrice;
	private String ownerContactPerson;
	private Date ownerStartDate;
	private Date ownerEndDate;
	private String ownerNote;
	private String advCompPhone;
	private String advCompEmail;
	private String advCompPrice;
	private String advCompContactPerson;
	private String advCompName;
	private Date advCompStartDate;
	private Date advCompEndDate;
	private String advCompNote;
	private String price;
	@JsonIgnore
	private User createdBy;
	private Date createdDate;
	private Date updatedDate;
	private boolean trash;
	private Date publishedDate;
	private Integer publishedId;
	private String type;
	private List<MultipartFile> images = new ArrayList<>();
	private List<UUID> prevImages = new ArrayList<>(); 
	private MultipartFile map;
	private boolean ignoreError = false;
}
