/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.config;

import com.kaan.deneme.service.JwtService;
import com.kaan.deneme.service.UserCredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private JwtService jwtService;

    private UserCredentialsService userCredentialsService;

    @Autowired
    public JwtAuthenticationProvider(JwtService jwtService, UserCredentialsService userCredentialsService) {
        this.jwtService = jwtService;
        this.userCredentialsService = userCredentialsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String jwt = (String) authentication.getPrincipal();
        if (jwt == null || !jwt.startsWith("Bearer ")) {
            throw new BadCredentialsException("Invalid JWT token");
        }

        String username = jwtService.getUsername(jwt);
        if (username == null) {
            throw new BadCredentialsException("Invalid JWT token");
        }

        UserDetails userDetails = userCredentialsService.loadUserByUsername(username);
        if (jwtService.tokenControl(username, jwt)) {
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        } else {
            throw new BadCredentialsException("Invalid JWT token");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
