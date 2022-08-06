package io.swen90007sm2.app.security.constant;

/**
 * @author 996Worker
 * @description Constants for security domin
 * @create 2022-08-03 23:26
 */
public interface SecurityConstant {

    String JWT_HEADER_NAME = "Authorization";
    String JWT_PAYLOAD_USER_ID = "userId";
    String JWT_PAYLOAD_ROLE = "role";

    String JWT_ISSUED_DATE = "issued-date";

    String JWT_EXPIRATION_DATE = "expired-date";

    String JWT_TOKEN_SUBJECT = "security";

    String JWT_PRIVATE = "R53YQvJnVt3DNnFtqE5n";

    long DEFAULT_TOKEN_EXPIRATION_TIME_MS = 24 * 60 * 60 * 1000;

    String CUSTOMER_ROLE_NAME = "customer_role";
    String ADMIN_ROLE_NAME = "admin_role";
    String HOTELIER_ROLE_NAME = "hotelier_role";
}