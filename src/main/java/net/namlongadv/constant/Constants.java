package net.namlongadv.constant;

import net.namlongadv.exceptions.ConstructorException;

/**
 * 
 * @author ToanNgo
 *
 */
public final class Constants {

	/**
	 * Application information
	 */
	public static final String COMPANY = "NamLongAdv";
	public static final String APP_VERSION = "0.0.1";
	public static final String APP_LICENSE = "";
	public static final String APP_LICENSE_URL = "";
	
	public static final String ASSET_SERVICE_API= "Asset Management API";
	public static final String ASSET_SERVICE_DESC = "Managing assets, documents, parts, assset types, variables.";
	
	public static final String FILE_SERVICE_API = "File Management API";
	public static final String FILE_SERVICE_API_DESC = "Managing upload and store files.";
	
	public static final String WORK_SERVICE_API = "Work Management API";
	public static final String WORK_SERVICE_API_DESC = "Managing Work Orders, Work Templates, Instructions, Invoices, Tags and comments.";
	
	public static final String USER_SERVICE_API = "User Management API";
	public static final String USER_SERVICE_API_DESC = "Managing user profiles, roles and teams.";
	
	public static final String UAA_SERVICE_API = "UAA Service API";
	public static final String UAA_SERVICE_API_DESC = "User Authentication Authorization.";
	
    /**
     * Common key words
     */
    public static final String EMPTY = "";
    public static final String EMPTY_JSON_ARRAY = "[]";
    public static final String SPACE = " ";
    public static final String DOT = ".";
    public static final String COMMA = ",";
    public static final String HYPHEN = "-";
    public static final String UNDERSCORE = "_";
    public static final String SLASH = "/";
    public static final String SEMICOLON = ";";
    public static final String COLON = ":";
    public static final String FILTER = "filter";
    public static final String MODEL = "model";
    public static final String PAGE = "page";
    public static final String SIZE = "size";
    public static final String PAGE_NUM = "1";
    public static final String PAGE_SIZE = "10";
    public static final String BAD_REQUEST_DUPLICATED = "data-duplicate";
    public static final String BAD_REQUEST_DATA_NOT_FOUND = "data-not-found";
    public static final String BAD_REQUEST_DATA_IN_USE = "data-in-used";
    public static final String BAD_REQUEST_DATA_DELETED = "data-deleted";
    public static final String BAD_REQUEST_DATA_LIST_EMPTY = "data-list-empty";
    public static final String BAD_REQUEST_DATA_MISSED_FIELD = "data-missed-field";
    public static final String BAD_REQUEST_DATA_LENGTH_INVALID = "data-length-invalid";
    public static final String BAD_REQUEST_DATA_TYPE_INVALID = "data-type-invalid";
    public static final String BAD_REQUEST_DATA_INVALID = "data-invalid";
    public static final String BAD_REQUEST_ITEM_NOT_EXIST = "item-not-exist";

    /**
     * Spring profile
     */
    public static final String PROFILE_LOCAL = "local";
    public static final String PROFILE_DEV = "dev";
    public static final String PROFILE_STG = "stg";
    public static final String PROFILE_PROD = "prod";
    public static final String PROFILE_TEST = "test";

    /**
     * Unlimited page size
     */
    public static final int MAX_PAGE_SIZE = -1;
    
    /**
     * User Session
     */
    public static final Long DEFAULT_USER_ID = 1L;
    public static final String DEFAULT_USER_NAME = "DEFAULT_USER";
    public static final String INVALID_USER_SESSION = "Invalid User Session";
    
    /**
     * Authentication
     */
    public static final String PW_INCORRECT = "Password incorrect";
    public static final String DEFAULT_PW = "admin";
    
    /**
     * Mail issues
     */
    public static final String CANT_SEND_MAIL = "Can't send the mail";

    /**
     *  Prevent create instance of this class
     */
    private Constants() {
        throw new ConstructorException();
    }
}
