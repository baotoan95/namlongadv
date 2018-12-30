package net.namlongadv.exceptions;

import net.namlongadv.constant.ErrorMessage;

/**
 *
 * @author ToanNgo
 */
public class BadRequestException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4560133898504885679L;
	private final CustomError customError;
	private static final String MSG = "Bad Request Exception";
	
	/**
	 * Constructor
	 */
	public BadRequestException() {
		super(MSG);
		this.customError = new CustomError();
	}
	
	/**
	 * 
	 * @param mgs
	 */
	public BadRequestException(String mgs) {
		super(mgs);
		this.customError = new CustomError();
	}
	/**
	 * 
	 * @param mgs
	 */
	public BadRequestException(String mgs, CustomError customError) {
		super(mgs);
		this.customError = customError;
	}

	/**
	 * 
	 * @param err
	 */
	public BadRequestException(ErrorMessage err, CustomError customError) {
		super(err.getCode().toString());
		this.customError = customError;
	}

	/**
	 * 
	 * @param customError
	 */
	public BadRequestException(CustomError customError) {
		super(MSG);
		this.customError = customError;
	}

	/**
	 * @return the customError
	 */
	public CustomError getCustomError() {
		return customError;
	}

}
