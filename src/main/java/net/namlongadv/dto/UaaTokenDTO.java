package net.namlongadv.dto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import net.namlongadv.constant.Constants;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UaaTokenDTO implements Serializable {

	private static final long serialVersionUID = -8244200086456194927L;

	@JsonProperty("sub")
	private String sub;

	@JsonProperty("user_id")
	private UUID userId;

	@JsonProperty("user_name")
	private String userName;

	@JsonProperty("email")
	private String email;

	@JsonProperty("scope")
	private List<String> scope;

	@JsonProperty("client_id")
	private String clientId;

	@JsonProperty("grant_type")
	private String grantType;

	@JsonProperty("iat")
	private String iat;

	@JsonProperty("exp")
	private String exp;

	@JsonProperty("aud")
	private List<String> aud;
	
	@JsonProperty("first_name")
	private String firstName;
	
	@JsonProperty("last_name")
	private String lastName;
	
	@JsonProperty("phone_number")
	private String phoneNumber;

	public UaaTokenDTO() {

	}

	public UaaTokenDTO(String userName) {
		this.userName = userName;
	}

	public static UaaTokenDTO empty() {
		return new UaaTokenDTO(Constants.EMPTY);
	}

	public boolean isEmpty() {
		return StringUtils.isEmpty(userName);
	}

	/**
	 * @return the sub
	 */
	public String getSub() {
		return sub;
	}

	/**
	 * @param sub the sub to set
	 */
	public void setSub(String sub) {
		this.sub = sub;
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

	/**
	 * @return the scope
	 */
	public List<String> getScope() {
		return scope;
	}

	/**
	 * @param scope the scope to set
	 */
	public void setScope(List<String> scope) {
		this.scope = scope;
	}

	/**
	 * @return the clientId
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/**
	 * @return the grantType
	 */
	public String getGrantType() {
		return grantType;
	}

	/**
	 * @param grantType the grantType to set
	 */
	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

	/**
	 * @return the iat
	 */
	public String getIat() {
		return iat;
	}

	/**
	 * @param iat the iat to set
	 */
	public void setIat(String iat) {
		this.iat = iat;
	}

	/**
	 * @return the exp
	 */
	public String getExp() {
		return exp;
	}

	/**
	 * @param exp the exp to set
	 */
	public void setExp(String exp) {
		this.exp = exp;
	}

	/**
	 * @return the aud
	 */
	public List<String> getAud() {
		return aud;
	}

	/**
	 * @param aud the aud to set
	 */
	public void setAud(List<String> aud) {
		this.aud = aud;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
