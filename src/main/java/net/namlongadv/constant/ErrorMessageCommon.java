package net.namlongadv.constant;

import net.namlongadv.exceptions.ConstructorException;

/**
 *
 * @author ToanNgo
 */
public final class ErrorMessageCommon {

	/**
	 * Common errors message
	 */

    public static final String NUMERIC_LESS_THAN_ZERO = "Data Error: Row {{%s}}: wrong format of value. Valid format: Numeric > 0";
    public static final String INVALID_DATETIME_MILIS_FORMAT = "Data Error: Row {{%s}}: Wrong format of date. Valid format: YYYY/MM/DD HH:mm:ss.000";
    
    /**
     * User management
     */
    public static final String USER_EXISTED = "User is existed in the system";
    public static final String USER_NOT_EXISTED = "User is not existed in the system";
    public static final String USER_REQUIRED_FIELDS = "Please input required fields";

    /**
     * Prevent create instance of this class
     */
    private ErrorMessageCommon() {
        throw new ConstructorException();
    }

}
