/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.bookstore.service;

import com.kaan.bookstore.model.BaseUserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 *
 * @author kaan
 */
@Service
public class JwtService {

    private UserDetailsServiceImpl userDetailsService;

    @Value("${jwt.key}")
    private String secret;

    public JwtService(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 2))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        Date expirationDate = extractExpiration(token);
        String subject = extractUsername(token);
        return userDetails.getUsername().equals(subject) && expirationDate.before(new Date());
    }

    private SecretKey getSignKey() {
        byte[] keyByte = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyByte);
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Date extractExpiration(String token) {
        return getClaims(token).getExpiration();
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public UserDetails getUserByToken(String tokenWithPrefix) {
        String token = deletePrefixOfToken(tokenWithPrefix);
        String username = extractUsername(token);
        return userDetailsService.loadUserByUsername(username);
    }

    public String deletePrefixOfToken(String tokenWithPrefix) {
        if (tokenWithPrefix == null) return null ;
        if (tokenWithPrefix.startsWith("Bearer")) {
            return tokenWithPrefix.substring(7);
        }
        return tokenWithPrefix;
    }

}
