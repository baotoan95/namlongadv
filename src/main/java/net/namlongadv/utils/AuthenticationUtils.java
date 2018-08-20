package net.namlongadv.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import net.namlongadv.models.NLAdvUserDetails;

public class AuthenticationUtils {
	private AuthenticationUtils() {
		// Prevent create instance of this object
	}
	
	public static NLAdvUserDetails getUserDetails() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (NLAdvUserDetails) authentication.getPrincipal();
	}
	
	public static List<String> getCurrentUserRoles() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		List<String> roles = new ArrayList<>();
		authorities.stream().forEach(auth -> roles.add(auth.getAuthority()));
		return roles;
	}
}
