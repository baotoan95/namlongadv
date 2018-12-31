package net.namlongadv.dto;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6437282434269438553L;
	private UUID id;
    private String username;
    private String password;
    private String newPassword;
    private String name;
    private String email;
    private String department;
    private String phone;
    private UUID role;
    private boolean accountNonLocked;
}
