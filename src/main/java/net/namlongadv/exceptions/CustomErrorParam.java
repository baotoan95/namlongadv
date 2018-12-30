package net.namlongadv.exceptions;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author ToanNgo
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomErrorParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8322068489914916976L;
	private String field;
	private String parent;

	private Integer minLength;
	private Integer maxLength;
	
	public CustomErrorParam() {
		super();
	}

	/**
	 * 
	 * @param field
	 */
	public CustomErrorParam(String field) {
		super();
		this.field = field;
	}

	/**
	 * 
	 * @param field
	 * @param parent
	 */
	public CustomErrorParam(String field, String parent) {
		super();
		this.field = field;
		this.parent = parent;
	}

	/**
	 * 
	 * @param field
	 * @param minLength
	 * @param maxLength
	 */
	public CustomErrorParam(String field, Integer minLength, Integer maxLength) {
		this.field = field;
		this.minLength = minLength;
		this.maxLength = maxLength;
	}

	/**
	 * Get field
	 *
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * Set field
	 *
	 * @param field
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * Get parent
	 *
	 * @return the parent
	 */
	public String getParent() {
		return parent;
	}

	/**
	 * Set parent
	 *
	 * @param parent
	 */
	public void setParent(String parent) {
		this.parent = parent;
	}

	/**
	 * Get minLength
	 *
	 * @return the minLength
	 */
	public Integer getMinLength() {
		return minLength;
	}

	/**
	 * Set minLength
	 *
	 * @param minLength
	 */
	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}

	/**
	 * Get maxLength
	 *
	 * @return the maxLength
	 */
	public Integer getMaxLength() {
		return maxLength;
	}

	/**
	 * Set maxLength
	 *
	 * @param maxLength
	 */
	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

}
