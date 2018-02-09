package net.namlongadv.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService nLAdvUserDetailsService;
    @Autowired
    private DataSource dataSource;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        .antMatchers("/login/**").permitAll()
        .antMatchers("/resources/**").permitAll()
        .anyRequest().authenticated()
        .and().formLogin().loginPage("/login").loginProcessingUrl("/perform_login")
        .defaultSuccessUrl("/adv/view?page=0&size=10")
        .failureUrl("/login?error")
        .and().logout().deleteCookies("JSESSIONID")
        .logoutUrl("/logout").logoutSuccessUrl("/login")
        .and().csrf()
        .and().rememberMe()
        .rememberMeParameter("remember-me")
        .key("secret-namlongadv-btit95")
        .rememberMeCookieName("namlongadv-btit95")
        .userDetailsService(nLAdvUserDetailsService)
        .tokenRepository(persistentTokenRepository()).tokenValiditySeconds(5 * 86400);
        
        http.sessionManagement().invalidSessionUrl("/login");
    }
    
    @Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
	}
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
    
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(nLAdvUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        
        return authenticationProvider;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }
    
}
