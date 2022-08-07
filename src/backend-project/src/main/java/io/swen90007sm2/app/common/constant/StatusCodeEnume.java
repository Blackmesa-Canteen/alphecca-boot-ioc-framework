package io.swen90007sm2.app.common.constant;

/**
 * @author xiaotian
 */
public enum StatusCodeEnume {

    /* General code*/
    SUCCESS(200, "Ok"),
    SERVER_INTERNAL_EXCEPTION(500, "Server Internal Error, please contact admin."),
    GENERAL_INTERNAL_EXCEPTION(400, "Bad request."),
    RESOURCE_NOT_FOUND_EXCEPTION(404, "Resource not found"),
    REQUEST_FORBIDDEN_EXCEPTION(403, "Request Forbidden."),

    /* auth */
    USER_EXIST_EXCEPTION(15001,"Duplicated user."),
    LOGIN_PASSWORD_EXCEPTION(15002,"Wrong passwords or username."),
    NOT_LOGIN_EXCEPTION(15003, "Please login."),

    CUSTOMER_AUTH_FAILED_EXCEPTION(15010, "Customer Access Only."),
    HOTELIER_AUTH_FAILED_EXCEPTION(15011, "Hotelier Access Only."),
    ADMIN_AUTH_FAILED_EXCEPTION(15012, "Admin Access Only."),

    TOKEN_PARSE_EXCEPTION(15020, "can not parse token"),
    TOKEN_INVALID_EXCEPTION(15022, "Token invalid."),
            ;

    private final Integer code;
    private final String message;

    StatusCodeEnume(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}