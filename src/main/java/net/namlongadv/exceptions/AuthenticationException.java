package net.namlongadv.exceptions;

import net.namlongadv.constant.ErrorMessage;

/**
 * 
 * @author ToanNgo
 *
 */
public class AuthenticationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7696792036013250125L;

	/**
	 * Create AuthenticationException
	 * @param mgs
	 * @param throwable
	 */
    public AuthenticationException(String mgs, Throwable throwable) {
        super(mgs, throwable);
    }

    /**
     * 
     * @param throwable
     */
    public AuthenticationException(Throwable throwable) {
        super(throwable);
    }

    /**
     * 
     * @param mgs
     */
    public AuthenticationException(String mgs) {
        super(mgs);
    }

    /**
     * 
     * @param err
     */
    public AuthenticationException(ErrorMessage err) {
        super(err.getCode().toString());
    }

    /**
     * AuthenticationException
     */
    public AuthenticationException() {
    }
}
