package net.namlongadv.dto;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import net.namlongadv.constant.Constants;
import net.namlongadv.utils.StringUtils;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchForm implements Serializable {
	/**
	 * @author ToanNgo
	 */
	private static final long serialVersionUID = -7215166056393244662L;
	private String code;
	private String address;
	private String createdBy;
	private String contactPerson;
	private String houseNo;
	private String street;
	private String ward;
	private String district;
	private String provinceCode;
	private String title;
	private Date from;
	private Date to;
	private PageRequestDTO pageRequestDTO;
	private List<String> roles;

	public void setCode(String code) {
		this.code = !Objects.isNull(code) ? StringUtils.convertStringIgnoreUtf8(code.trim()).toLowerCase() : Constants.EMPTY;
	}

	public void setAddress(String address) {
		this.address = !Objects.isNull(address) ? StringUtils.convertStringIgnoreUtf8(address.trim()).toLowerCase() : Constants.EMPTY;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = !Objects.isNull(createdBy) ? StringUtils.convertStringIgnoreUtf8(createdBy.trim()).toLowerCase() : Constants.EMPTY;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = !Objects.isNull(contactPerson)
				? StringUtils.convertStringIgnoreUtf8(contactPerson.trim()).toLowerCase()
				: Constants.EMPTY;
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = !Objects.isNull(houseNo) ? StringUtils.convertStringIgnoreUtf8(houseNo.trim()).toLowerCase() : null;
	}

	public void setStreet(String street) {
		this.street = !Objects.isNull(street) ? StringUtils.convertStringIgnoreUtf8(street.trim()).toLowerCase() : null;
	}

	public void setWard(String ward) {
		this.ward = !Objects.isNull(ward) ? StringUtils.convertStringIgnoreUtf8(ward.trim()).toLowerCase() : null;
	}

	public void setDistrict(String district) {
		this.district = !Objects.isNull(district) ? StringUtils.convertStringIgnoreUtf8(district.trim()).toLowerCase() : null;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = !Objects.isNull(provinceCode)
				? StringUtils.convertStringIgnoreUtf8(provinceCode.trim()).toLowerCase()
				: null;
	}

	public void setTitle(String title) {
		this.title = !Objects.isNull(title) ? StringUtils.convertStringIgnoreUtf8(title.trim()).toLowerCase() : Constants.EMPTY;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public void setPageRequestDTO(PageRequestDTO pageRequestDTO) {
		pageRequestDTO.setPage(pageRequestDTO.getPage() != null ? pageRequestDTO.getPage() : 0);
		pageRequestDTO.setSize(pageRequestDTO.getSize() != null ? pageRequestDTO.getSize() : Integer.MAX_VALUE);
		this.pageRequestDTO = pageRequestDTO;
	}

	public PageRequestDTO getPageRequestDTO() {
		setPageRequestDTO(this.pageRequestDTO);
		return pageRequestDTO;
	}

	public void setRoles(List<String> roles) {
		this.roles = !Objects.isNull(roles) ? roles : Collections.emptyList();
	}

	public String getCode() {
		return Objects.isNull(code) ? Constants.EMPTY : StringUtils.convertStringIgnoreUtf8(code.trim()).toLowerCase();
	}

	public String getAddress() {
		return Objects.isNull(address) ? Constants.EMPTY : StringUtils.convertStringIgnoreUtf8(address.trim()).toLowerCase();
	}

	public String getCreatedBy() {
		return Objects.isNull(createdBy) ? Constants.EMPTY : StringUtils.convertStringIgnoreUtf8(createdBy.trim()).toLowerCase();
	}

	public String getContactPerson() {
		return Objects.isNull(contactPerson) ? Constants.EMPTY : StringUtils.convertStringIgnoreUtf8(contactPerson.trim()).toLowerCase();
	}

	public String getHouseNo() {
		return Objects.isNull(houseNo) ? null : StringUtils.convertStringIgnoreUtf8(houseNo.trim()).toLowerCase();
	}

	public String getStreet() {
		return Objects.isNull(street) ? null : StringUtils.convertStringIgnoreUtf8(street.trim()).toLowerCase();
	}

	public String getWard() {
		return Objects.isNull(ward) ? null : StringUtils.convertStringIgnoreUtf8(ward.trim()).toLowerCase();
	}

	public String getDistrict() {
		return Objects.isNull(district) ? null : StringUtils.convertStringIgnoreUtf8(district.trim()).toLowerCase();
	}

	public String getProvinceCode() {
		return Objects.isNull(provinceCode) ? null : StringUtils.convertStringIgnoreUtf8(provinceCode.trim()).toLowerCase();
	}

	public String getTitle() {
		return Objects.isNull(title) ? Constants.EMPTY : StringUtils.convertStringIgnoreUtf8(title.trim()).toLowerCase(); 
	}

	public Date getFrom() {
		return from;
	}

	public Date getTo() {
		return to;
	}

	public List<String> getRoles() {
		return roles;
	}

}
