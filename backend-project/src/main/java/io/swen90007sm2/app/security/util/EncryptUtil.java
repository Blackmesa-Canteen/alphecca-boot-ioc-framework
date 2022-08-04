package io.swen90007sm2.app.security.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * tools for encryption or decryption
 *
 * @author xiaotian
 */
public class EncryptUtil {


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
}
