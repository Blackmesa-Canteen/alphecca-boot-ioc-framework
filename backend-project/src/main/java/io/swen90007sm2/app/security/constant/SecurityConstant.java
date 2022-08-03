package io.swen90007sm2.app.security.constant;

/**
 * @author 996Worker
 * @description Constants for security domin
 * @create 2022-08-03 23:26
 */
public class SecurityConstant {

    public static String JWT_HEADER_NAME = "Authorization";
    public static String JWT_PAYLOAD_USERNAME = "username";
    public static String JWT_PAYLOAD_ROLE = "role";

    public static String JWT_TOKEN_SUBJECT = "security";

    public static String JWT_PRIVATE = "R53YQvJnVt3DNnFtqE5n";

    public static long DEFAULT_TOKEN_EXPIRATION_TIME_MS = 24 * 60 * 60 * 1000;

    public static String CUSTOMER_ROLE = "customer_role";
    public static String ADMIN_ROLE = "admin_role";
    public static String HOTELIER_ROLE = "hotelier_role";
}