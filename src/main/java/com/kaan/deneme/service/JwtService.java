package com.kaan.deneme.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    
    private UserCredentialsService userCredentialsService ;

    @Value("${jwt.secret}")
    private String secretValue;

    @Value("${jwt.expiration}")
    private long jwtExpirationInMs;
    
    @Autowired
    public JwtService (UserCredentialsService userCredentialsService) {
        this.userCredentialsService = userCredentialsService ;
    }

    public String getUsername(String jwt) {
        return extractToken(jwt, Claims::getSubject);
    }
    
    public Long getId (String jwt) {
    return extractToken(jwt, claims -> claims.get("Id", Long.class));
    }

    private <T> T extractToken(String jwt, Function<Claims, T> claimsFunction) {
        Claims claims = Jwts.parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
        return claimsFunction.apply(claims);
    }

    private Key getKey() {
        byte[] secret = Decoders.BASE64.decode(secretValue);
        return Keys.hmacShaKeyFor(secret);
    }

    public boolean tokenControl(String username, String jwt) {
        return ((getUsername(jwt).equals(username)) && extractToken(jwt, Claims::getExpiration).after(new Date()));
    }

    public String createToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        Long id = userCredentialsService.getIdByUsername(username);
        claims.put("Id", id);
        return doGenerateToken(claims, username);
    }
    
    public String getJwt (HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        return header.substring(7);
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
