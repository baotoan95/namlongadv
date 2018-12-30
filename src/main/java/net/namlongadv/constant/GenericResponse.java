package net.namlongadv.constant;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Common Rest response
 *
 * @author HoangDQ
 * @see JsonIgnoreProperties
 * @see JsonInclude
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponse {
	/**
	 * Message of response
	 */
    private String message;
    
    /**
     * Response data
     */
    private Object data;

    /**
     * Create response with message and content
     * 
     * @param message
     * @param data
     */
    public GenericResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    /**
     * Create default response with empty content and default message
     */
    public GenericResponse() {
        this("acknowledge", Constants.EMPTY);
    }

    /**
     * Create response with data and default message
     * 
     * @param data
     */
    public GenericResponse(Object data) {
        this("acknowledge", data);
    }

    /**
     * Create response with message and empty content
     * 
     * @param message
     */
    public GenericResponse(String message) {
        this(message, Constants.EMPTY);
    }

    /**
     * Create response with message is reason phrase of httpStatus
     * and empty content
     * 
     * @param httpStatus
     * @see HttpStatus
     */
    public GenericResponse(HttpStatus httpStatus) {
        this(httpStatus.getReasonPhrase(), Constants.EMPTY);
    }

    /**
     * Get response message
     * 
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set response message
     * 
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Get response data
     * 
     * @return
     */
    public Object getData() {
        return data;
    }

    /**
     * Set response data
     * 
     * @param data
     */
    public void setData(Object data) {
        this.data = data;
    }
}
