package net.namlongadv.exceptions;

import net.namlongadv.constant.ErrorMessage;

/**
 *
 * @author ToanNgo
 */
public class BusinessException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 260218525950435564L;

	/**
	 * 
	 * @param mgs
	 * @param throwable
	 */
    public BusinessException(String mgs, Throwable throwable) {
        super(mgs, throwable);
    }

    /**
     * 
     * @param throwable
     */
    public BusinessException(Throwable throwable) {
        super(throwable);
    }

    /**
     * 
     * @param mgs
     */
    public BusinessException(String mgs) {
        super(mgs);
    }

    /**
     * 
     * @param err
     */
    public BusinessException(ErrorMessage err) {
        super(err.getCode().toString());
    }

    public BusinessException() {
    }
}
