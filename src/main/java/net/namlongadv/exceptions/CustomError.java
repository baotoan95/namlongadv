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
public class CustomError implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5972573265189678299L;
	private String code;
	private CustomErrorParam params;

	public CustomError() {
	}

	/**
	 * 
	 * @param code
	 * @param params
	 */
	public CustomError(String code, CustomErrorParam params) {
		this.code = code;
		this.params = params;
	}

	/**
	 * Get code
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Set code
	 *
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Get params
	 *
	 * @return the params
	 */
	public CustomErrorParam getParams() {
		return params;
	}

	/**
	 * Set params
	 *
	 * @param params
	 */
	public void setParams(CustomErrorParam params) {
		this.params = params;
	}

}
