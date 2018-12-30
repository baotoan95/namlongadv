package net.namlongadv.utils;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.namlongadv.dto.UaaTokenDTO;
import net.namlongadv.exceptions.ConstructorException;

public final class UaaTokenUtils {

    private static final Logger LOG = LoggerFactory.getLogger(UaaTokenUtils.class);
    private static String TOKEN_LOCAL_CACHE = "";
    private static Long EXPIRES = 0L;

    private UaaTokenUtils() {
        throw new ConstructorException();
    }
    
    /**
     * Parse Token
     * 
     * @param token
     * @return
     */
    public static UaaTokenDTO parseUaaToken(String token) {
        try {
            // Decode data on other side, by processing encoded data
            String[] split_string = token.split("\\.");
            String base64EncodedBody = split_string[1];

            Base64 base64Url = new Base64(true);
            String tokenBody = new String(base64Url.decode(base64EncodedBody));

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(tokenBody, UaaTokenDTO.class);
        } catch (IOException e) {
            LOG.error(String.format("parseUaaToken [%s] got [%s]", token, e.getMessage()), e);
        }
        return UaaTokenDTO.empty();
    }

    /**
     * Gets the client access token
     *
     * @param restTemplate
     * @param uaaUrl
     * @param uaaClient
     * @param uaaSecret
     * @return
     */
    public static String getClientAccessToken(RestTemplate restTemplate, String uaaUrl, String uaaClient, String uaaSecret) {
        try {
            if (StringUtils.isEmpty(TOKEN_LOCAL_CACHE)) {
                return getNewClientAccessToken(restTemplate, uaaUrl, uaaClient, uaaSecret);
            } else {
                if (isTokenExpired()) {
                    return getNewClientAccessToken(restTemplate, uaaUrl, uaaClient, uaaSecret);
                } else {
                    LOG.info("Getting UAA Token From Cache.");
                    return TOKEN_LOCAL_CACHE;
                }
            }
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            return "";
        }
    }

    /**
     * check the token is expired or not
     *
     * @return
     */
    public static boolean isTokenExpired() {
        boolean isExpired = System.currentTimeMillis() > EXPIRES;
        return isExpired;
    }

    /**
     * @param restTemplate
     * @param uaaUrl
     * @param uaaClient
     * @param uaaSecret
     * @return
     * @throws JSONException
     */
    private static String getNewClientAccessToken(RestTemplate restTemplate, String uaaUrl, String uaaClient, String uaaSecret) throws JSONException {
        LOG.info("Getting New UAA Token.");

        byte[] encodedBytes = Base64.encodeBase64((uaaClient + ":" + uaaSecret).getBytes());
        String clientBase64Token = new String(encodedBytes);
        String fullUaaTokenUrl = uaaUrl + "/oauth/token?grant_type=client_credentials";

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Basic " + clientBase64Token);

        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> result = restTemplate.exchange(fullUaaTokenUrl, HttpMethod.GET, requestEntity, String.class);
        LOG.info("Get UAA Token result: {}", result.getStatusCode());

        JSONObject jsonObject = new JSONObject(result.getBody());

        String token = jsonObject.getString("access_token");
        Long expiredTime = System.currentTimeMillis() + (jsonObject.getLong("expires_in") * 1000);

        TOKEN_LOCAL_CACHE = token;
        EXPIRES = expiredTime;

        return token;
    }
}
