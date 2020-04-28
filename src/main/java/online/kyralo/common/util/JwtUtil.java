package online.kyralo.common.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

import static online.kyralo.common.constants.SecurityConstants.*;


/**
 * @author 王宸
 */
@Component
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    private static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * 解析token
     * @param token the JWT token to parse
     * @return token claims
     */
    public static Claims parseToken(String token) {

        try {
            return Jwts.parser()
                    .setSigningKey(KEY)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();
        } catch (JwtException e) {
            logger.warn("jwt验证失败" + e.getMessage());
            return null;
        }
    }

    /**
     * 用户获取token
     * @param userId 用户Id
     *
     * */
    public static String generateTokenByUserId(String userId) {
        Claims claims = Jwts.claims()
                .setId(UUID.randomUUID().toString().replace("-",""))
                .setAudience(userId)
                .setSubject("ROLE_USER");

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(KEY)
                .compact();
    }

    public static Claims claimGet(HttpServletRequest request){
        Claims claims = null;
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            claims = JwtUtil.parseToken(token);
        }
        return claims;
    }
}
