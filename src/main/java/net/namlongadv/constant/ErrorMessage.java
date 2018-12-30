package net.namlongadv.constant;

import java.util.Arrays;

/**
 * 
 * @author HoangDQ
 *
 */
public enum ErrorMessage {

    /**
     * Endpoint success case
     */
    SUCCESS(0, "acknowledge");

    private final Integer code;
    private final String message;

    /**
     * Create ErrorMessage with httpCode and message
     * 
     * @param code
     * @param message
     */
    ErrorMessage(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Get ErrorMessage by code
     *
     * @param val
     * @return
     */
    public static ErrorMessage find(String val) {
        try {
            return get(Integer.parseInt(val.trim()));
        } catch (NumberFormatException e) {
            return SUCCESS;
        }
    }

    /**
     * Get ErrorMessage by value
     *
     * @param mgs
     * @return
     */
    public static ErrorMessage get(String mgs) {
        return Arrays.stream(ErrorMessage.values())
                .filter(z -> z.getMessage().equalsIgnoreCase(mgs))
                .findFirst().orElse(SUCCESS);
    }

    /**
     * Get ErrorMessage by code
     *
     * @param code
     * @return ErrorMessage
     */
    public static ErrorMessage get(int code) {
        return Arrays.stream(ErrorMessage.values())
                .filter(z -> Integer.compare(code, z.getCode()) == 0)
                .findFirst().orElse(SUCCESS);
    }

    /**
     * Get code
     * @return Integer
     */
    public Integer getCode() {
        return code;
    }

    /**
     * Get message
     * @return String
     */
    public String getMessage() {
        return message;
    }

}
