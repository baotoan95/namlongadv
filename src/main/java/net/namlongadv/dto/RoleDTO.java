package net.namlongadv.dto;

import java.io.Serializable;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4072018226251044615L;
	private UUID id;
    private String code;
    private String name;
}
