/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.config;

import com.kaan.deneme.model.Role;
import com.kaan.deneme.service.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 *
 * @author kaan
 */
@Component
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private JwtService jwtService;

    public JwtAuthenticationSuccessHandler(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String token = jwtService.createToken(authentication.getName()) ;
        token = "Bearer "+token ;
        String encodedCookieValue = URLEncoder.encode(token, StandardCharsets.UTF_8);
        response.addCookie(new Cookie("Authorization" , encodedCookieValue));
        Collection <? extends GrantedAuthority> roles =  authentication.getAuthorities() ;
        for (GrantedAuthority role : roles) {
            if (role.getAuthority().equals(Role.CUSTOMER.name())) {
                response.sendRedirect("/customer/main-panel");
            }
            else if (role.getAuthority().equals(Role.MOD.name())) {
                response.sendRedirect("/mod/main-panel");
            }
            else {
                response.sendRedirect("/admin/panel");
            }
        }
    }

}
