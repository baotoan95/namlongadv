package net.namlongadv.aop;

import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import net.namlongadv.constant.Constants;
import net.namlongadv.dto.UaaTokenDTO;
import net.namlongadv.exceptions.AuthenticationException;
import net.namlongadv.exceptions.BadRequestException;
import net.namlongadv.utils.UaaTokenUtils;

/**
 * 
 * @author ToanNgo
 *
 */

@RequestScope
@Component
public class UserSession {

	@Autowired
	private HttpServletRequest request;

	private UUID userId;
	private String userName;
	private String author;
	private String email;

	@PostConstruct
	void init() throws AuthenticationException, BadRequestException {
		// Check Authorization - UAA
//		this.reset();
		String uaaToken = this.request.getHeader(HttpHeaders.AUTHORIZATION);
		if (null == uaaToken || uaaToken.isEmpty()) {
			throw new BadRequestException(Constants.INVALID_USER_SESSION);
		}
		UaaTokenDTO uaa = UaaTokenUtils.parseUaaToken(uaaToken);
		if (null == uaa || uaa.isEmpty()) {
			throw new AuthenticationException(Constants.INVALID_USER_SESSION);
		}
		this.userId = uaa.getUserId();
		this.author = uaa.getFirstName() + Constants.SPACE + uaa.getLastName();
		this.userName = uaa.getUserName();
		this.email = uaa.getEmail();
	}

//	public void reset() {
//		this.author = Constants.EMPTY;
//		this.userName = Constants.EMPTY;
//		this.email = Constants.EMPTY;
//	}

	public boolean isValid() {
		return userId != null;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the userId
	 */
	public UUID getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

}
