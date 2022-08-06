package io.swen90007sm2.app.security.helper;

import io.jsonwebtoken.*;
import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.exception.RequestException;
import io.swen90007sm2.app.cache.ICacheStorage;
import io.swen90007sm2.app.common.constant.StatusCodeEnume;
import io.swen90007sm2.app.common.util.TimeUtil;
import io.swen90007sm2.app.security.bean.AuthToken;
import io.swen90007sm2.app.security.constant.AuthRole;
import io.swen90007sm2.app.security.constant.SecurityConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author 996Worker
 * @description helper for JWT token
 * @create 2022-08-03 23:47
 */
public class TokenHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenHelper.class);


    /**
     * generate auth-use JWT token string
     * @param userId userId
     * @param role AuthRole, 3 types
     * @return string token
     */
    public static String genAuthToken(String userId, AuthRole role) {
        JwtBuilder builder = Jwts.builder();
        HashMap<String, Object> payloadMap = new HashMap<>();

        payloadMap.put(SecurityConstant.JWT_PAYLOAD_USER_ID, userId);
        payloadMap.put(SecurityConstant.JWT_PAYLOAD_ROLE, role.getRoleName());

        // expiration
        Date nowDate = TimeUtil.now();
        payloadMap.put(SecurityConstant.JWT_ISSUED_DATE, nowDate);
        payloadMap.put(SecurityConstant.JWT_EXPIRATION_DATE,
                TimeUtil.add(
                nowDate,
                SecurityConstant.DEFAULT_TOKEN_EXPIRATION_TIME_MS,
                TimeUnit.MILLISECONDS
            )
        );

        return builder
                .setSubject(SecurityConstant.JWT_TOKEN_SUBJECT)
                .setClaims(payloadMap)
                .signWith(SignatureAlgorithm.HS512, SecurityConstant.JWT_PRIVATE)
                .compact();
    }

    /**
     * parse JWT token parse into AuthToken bean
     * @param tokenStr token str
     * @return AuthToken bean
     */
    public static AuthToken parseAuthTokenString(String tokenStr) {
        if (tokenStr == null) {
            // parsed no token str from header, not login at all
            throw new RequestException(StatusCodeEnume.NOT_LOGIN_EXCEPTION.getMessage(), StatusCodeEnume.NOT_LOGIN_EXCEPTION.getCode());
        }
        JwtParser jwtParser = Jwts.parser();

        // private key to decrypt and check the payload
        jwtParser.setSigningKey(SecurityConstant.JWT_PRIVATE);

        try {
            Jws<Claims> claimsJws = jwtParser.parseClaimsJws(tokenStr);
            // get info from jwt payload
            Claims claimsBody = claimsJws.getBody();
            String username = claimsBody.get(SecurityConstant.JWT_PAYLOAD_USER_ID, String.class);
            String roleName = claimsBody.get(SecurityConstant.JWT_PAYLOAD_ROLE, String.class);
            Date issuedDate = claimsBody.get(SecurityConstant.JWT_ISSUED_DATE, Date.class);
            Date expirationDate = claimsBody.get(SecurityConstant.JWT_EXPIRATION_DATE, Date.class);

            AuthToken authToken = new AuthToken();
            authToken.setToken(tokenStr);
            authToken.setRoleName(roleName);
            authToken.setUserId(username);
            authToken.setIssuedDate(issuedDate);
            authToken.setExpirationDate(expirationDate);

            return authToken;

        } catch (MalformedJwtException | SignatureException e) {
            throw new RequestException(
                    StatusCodeEnume.TOKEN_INVALID_EXCEPTION.getMessage(),
                    StatusCodeEnume.TOKEN_INVALID_EXCEPTION.getCode()
            );
        } catch (Exception e) {
            LOGGER.error("invalid token!");
            throw new RequestException(
                    StatusCodeEnume.TOKEN_PARSE_EXCEPTION.getMessage(),
                    StatusCodeEnume.TOKEN_PARSE_EXCEPTION.getCode()
            );
        }
    }

    // test
    public static void main(String[] args) {
        String token = genAuthToken("tiantian", AuthRole.CUSTOMER_ROLE);
        System.out.println(token);

        AuthToken authToken = parseAuthTokenString(token);

        System.out.println(authToken);
    }
}