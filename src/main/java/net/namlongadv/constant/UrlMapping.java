package net.namlongadv.constant;

import net.namlongadv.exceptions.ConstructorException;

/**
 * Manage all end-points
 *
 * @author HoangDQ
 */
public final class UrlMapping {

    /**
     * End-points API
     */
    public static final String ASSETS = "assets";
    public static final String ASSET_TYPES = "assettypes";
    public static final String VARIABLES = "variables";
    public static final String BLOB = "blob";
    public static final String DOCUMENTS = "documents";
    public static final String PARTS = "parts";
    public static final String WORK_ORDERS = "workorders";
    public static final String INSTRUCTIONS = "instructions";
    public static final String WORK_TEMPLATES = "worktemplates";
    public static final String TAGS = "tags";
    public static final String COMMENTS = "comments";
    public static final String WORK_LOGS = "worklogs";
    public static final String USERS = "users";
    public static final String TEAMS = "teams";
    public static final String ROLES = "roles";
    public static final String INVOICES = "invoices";
    public static final String PART_REQUESTS = "partrequests";
    public static final String UAA = "uaa";

    /**
     * Prevent create instance of this class
     */
    private UrlMapping() {
        throw new ConstructorException();
    }

}
