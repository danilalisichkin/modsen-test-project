package com.modsen.authenticationservice.services.impl;

import com.modsen.authenticationservice.services.IJwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService implements IJwtService {

    public SecretKey jwtAccessKey;

    @Value("${jwt.access.expiration}")
    private Integer jwtAccessExpiration;

    @PostConstruct
    public void init() {
        jwtAccessKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    @Override
    public void validateToken(String token) {
        Jwts.parser()
                .setSigningKey(jwtAccessKey)
                .build()
                .parseClaimsJws(token);
    }

    @Override
    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtAccessExpiration * 60 * 1000))
                .signWith(jwtAccessKey)
                .compact();
    }
}