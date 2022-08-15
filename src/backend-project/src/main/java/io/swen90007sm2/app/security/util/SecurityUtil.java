package io.swen90007sm2.app.security.util;

import io.swen90007sm2.app.security.bean.AuthToken;
import io.swen90007sm2.app.security.helper.TokenHelper;
import org.mindrot.jbcrypt.BCrypt;

/**
 * tools for encryption or decryption
 *
 * @author xiaotian
 */
public class SecurityUtil {


    /**
     * generate cypher from origin string
     * @param origin string
     * @return string
     */
    public static String encrypt(String origin) {
        return BCrypt.hashpw(origin, BCrypt.gensalt());
    }

    /**
     * check origin with cypher
     * @param origin origin
     * @param cypher cypher string
     * @return true if matched
     */
    public static boolean isOriginMatchCypher(String origin, String cypher) {
        return BCrypt.checkpw(origin, cypher);
    }

    /**
     * parse a token string to authToken obj
     * @param tokenStr token string of jwt
     * @return AuthToken obj
     */
    public static AuthToken parseAuthTokenString(String tokenStr) {
        return TokenHelper.parseAuthTokenString(tokenStr);
    }

    public static void main(String[] args) {
        String originalPwd = "123456";
        System.out.println(encrypt(originalPwd));
    }
}
