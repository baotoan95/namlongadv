package net.namlongadv.entities;

import java.util.Collection;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class NLAdvUserDetails extends User {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1439099307330350284L;
	private UUID userId;
	private String name;
	private String email;

	public NLAdvUserDetails(UUID userId, String username, String password, String name,
			String email, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.userId = userId;
		this.name = name;
		this.email = email;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
