package net.namlongadv.exceptions;

import net.namlongadv.constant.ErrorMessage;

/**
 *
 * @author ToanNgo
 */
public class AuthorizationException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5965952513581393280L;

	/**
	 * 
	 * @param mgs
	 * @param throwable
	 */
    public AuthorizationException(String mgs, Throwable throwable) {
        super(mgs, throwable);
    }

    /**
     * 
     * @param throwable
     */
    public AuthorizationException(Throwable throwable) {
        super(throwable);
    }

    /**
     * 
     * @param mgs
     */
    public AuthorizationException(String mgs) {
        super(mgs);
    }

    /**
     * 
     * @param err
     */
    public AuthorizationException(ErrorMessage err) {
        super(err.getCode().toString());
    }

    public AuthorizationException() {
    }
}
