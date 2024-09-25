package com.modsen.authenticationservice.services.impl;

import com.modsen.authenticationservice.services.IJwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService implements IJwtService {

    @Value("${jwt.access.secret}")
    public static String jwtAccessSecret;

    @Value("${jwt.access.expiration}")
    private static Integer jwtAccessExpiration;

    @Override
    public void validateToken(String token) {
        try {
                Jwts.parser()
                        .setSigningKey(jwtAccessSecret)
                        .build()
                        .parseClaimsJws(token);
        } catch (ExpiredJwtException e) {

        } catch (UnsupportedJwtException e) {

        } catch (MalformedJwtException e) {

        } catch (SignatureException e) {

        } catch (Exception e) {

        }
    }

    @Override
    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtAccessExpiration * 60 * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtAccessSecret)
                .compact();
    }
}
