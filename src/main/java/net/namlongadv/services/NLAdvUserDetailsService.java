package net.namlongadv.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.namlongadv.entities.NLAdvUserDetails;
import net.namlongadv.entities.User;
import net.namlongadv.repositories.UserRepository;

@Service
@Slf4j
public class NLAdvUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Check username: {}", username);
        User user = userRepository.findByUsername(username);
        if(user == null) {
            log.debug("User {} not found", username);
            throw new UsernameNotFoundException(username);
        }
        log.debug("User {} is authenticated", username);
        return new NLAdvUserDetails(user.getId(), user.getUsername(), user.getPassword(), getAuthority(user));
    }
    
    private List<SimpleGrantedAuthority> getAuthority(User user) {
		List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
		user.getRoles().forEach(role -> {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
		});
		return grantedAuthorities;
	}
    
}
