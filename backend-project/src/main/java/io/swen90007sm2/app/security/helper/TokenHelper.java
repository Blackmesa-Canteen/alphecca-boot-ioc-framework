package io.swen90007sm2.app.security.helper;

import io.jsonwebtoken.*;
import io.swen90007sm2.alpheccaboot.exception.RequestException;
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
     * @param username username
     * @param role AuthRole, 3 types
     * @return string token
     */
    public static String genAuthToken(String username, AuthRole role) {
        JwtBuilder builder = Jwts.builder();
        HashMap<String, Object> payloadMap = new HashMap<>();

        payloadMap.put(SecurityConstant.JWT_PAYLOAD_USERNAME, username);
        payloadMap.put(SecurityConstant.JWT_PAYLOAD_ROLE, role.getRoleName());

        return builder
                .setSubject(SecurityConstant.JWT_TOKEN_SUBJECT)
                .setIssuedAt(TimeUtil.now())
                .setClaims(payloadMap)
                .setExpiration(
                        TimeUtil.add(
                            TimeUtil.now(),
                            SecurityConstant.DEFAULT_TOKEN_EXPIRATION_TIME_MS,
                            TimeUnit.MILLISECONDS
                        )
                ).signWith(SignatureAlgorithm.ES256, SecurityConstant.JWT_PRIVATE)
                .compact();
    }

    /**
     * parse JWT token parse into AuthToken bean
     * @param tokenStr token str
     * @return AuthToken bean
     */
    public static AuthToken parseAuthTokenString(String tokenStr) {
        JwtParser jwtParser = Jwts.parser();

        // private key to decrypt and check the payload
        jwtParser.setSigningKey(SecurityConstant.JWT_PRIVATE);

        try {
            Jws<Claims> claimsJws = jwtParser.parseClaimsJws(tokenStr);
            // get info from jwt payload
            Claims claimsBody = claimsJws.getBody();
            String username = claimsBody.get(SecurityConstant.JWT_PAYLOAD_USERNAME, String.class);
            String roleName = claimsBody.get(SecurityConstant.JWT_PAYLOAD_ROLE, String.class);
            Date issuedDate = claimsBody.getIssuedAt();
            Date expirationDate = claimsBody.getExpiration();

            AuthToken authToken = new AuthToken();
            authToken.setRole(roleName);
            authToken.setUserName(username);
            authToken.setIssuedDate(issuedDate);
            authToken.setExpirationDate(expirationDate);

            return authToken;

        } catch (ExpiredJwtException e) {
            throw new RequestException("Token expired, login again", StatusCodeEnume.TOKEN_EXPIRED_EXCEPTION.getCode());
        } catch (MalformedJwtException | SignatureException e) {
            throw new RequestException("Invalid token.", StatusCodeEnume.TOKEN_INVALID_EXCEPTION.getCode());
        } catch (Exception e) {
            LOGGER.error("invalid token!");
            throw new RequestException(
                    "parse incoming auth token string error!",
                    StatusCodeEnume.TOKEN_PARSE_EXCEPTION.getCode()
            );
        }
    }
}