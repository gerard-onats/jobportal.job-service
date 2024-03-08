package com.jobportal.jobservice.utils;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${app.jwt.secretKey}")
    private String SIGNING_KEY;

    public boolean validateAccessToken(final String jwt) {
        try {
            Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(jwt);
            return true;
        }
        catch (SignatureException ex) {
            logger.error("Signature validation failed.");
        }
        catch(UnsupportedJwtException ex) {
            logger.error("JWT is not supported: {}", ex.getMessage());
        }
        catch(MalformedJwtException ex) {
            logger.error("JWT is invalid.");
        }
        catch (ExpiredJwtException ex) {
            logger.error("JWT is expired.");
        }
        catch (IllegalArgumentException ex) {
            logger.error("JWT cannot be parsed properly: {}", ex.getMessage());
        }
        return false;
    }

    public Claims getClaims(final String token) {
        return Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
