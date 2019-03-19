package net.namlongadv.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProvinceDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8483071225591712513L;
	private String code;
	private String name;
}
