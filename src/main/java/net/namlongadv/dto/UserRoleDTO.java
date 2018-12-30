package net.namlongadv.dto;

import java.io.Serializable;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserRoleDTO implements Serializable {
	private static final long serialVersionUID = 7452320344417819956L;
	private UUID id;
	private String code;
	private String name;
}
