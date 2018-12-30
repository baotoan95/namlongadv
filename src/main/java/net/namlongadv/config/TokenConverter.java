package net.namlongadv.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import net.namlongadv.entities.NLAdvUserDetails;


/**
 * 
 * @author ToanNgo
 *
 */
public class TokenConverter extends JwtAccessTokenConverter {
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		if(authentication.getOAuth2Request().getGrantType().equalsIgnoreCase("password")) {
			NLAdvUserDetails userDetails = (NLAdvUserDetails) authentication.getPrincipal();
			final Map<String, Object> additionalInfo = new HashMap<>();
			additionalInfo.put("user_id", userDetails.getUserId());
			additionalInfo.put("first_name", userDetails.getFirstName());
			additionalInfo.put("last_name", userDetails.getLastName());
			additionalInfo.put("phone_number", userDetails.getPhoneNumber());
			additionalInfo.put("email", userDetails.getEmail());
			((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
		}
		accessToken = super.enhance(accessToken, authentication);
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(new HashMap<>());
		
		return accessToken;
	}
}
