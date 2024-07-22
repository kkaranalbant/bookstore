/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.config;

import com.kaan.deneme.model.Role;
import com.kaan.deneme.service.IpService;
import com.kaan.deneme.service.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 *
 * @author kaan
 */
@Component
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static Logger logger;

    private JwtService jwtService;
    
    private IpService ipService ;

    static {
        logger = LoggerFactory.getLogger(JwtAuthenticationSuccessHandler.class);
    }

    public JwtAuthenticationSuccessHandler(JwtService jwtService , IpService ipService) {
        this.jwtService = jwtService;
        this.ipService = ipService ;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String token = jwtService.createToken(authentication.getName());
        token = "Bearer " + token;
        String encodedCookieValue = URLEncoder.encode(token, StandardCharsets.UTF_8);
        response.addCookie(new Cookie("Authorization", encodedCookieValue));
        Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
        UserDetails details = (UserDetails) (authentication.getPrincipal());
        String username = details.getUsername() ;
        for (GrantedAuthority role : roles) {
            if (role.getAuthority().equals(Role.CUSTOMER.name())) {
                response.sendRedirect("/customer/main-panel");
                logger.info("Customer with the username "+username+" has logged in.\n"
                        + "Ip : "+ipService.getIpAddress(request));
            } else if (role.getAuthority().equals(Role.MOD.name())) {
                logger.info("Moderator with the username "+username+" has logged in.\n"
                        + "Ip : "+ipService.getIpAddress(request));
                response.sendRedirect("/mod/main-panel");
            } else {
                response.sendRedirect("/admin/panel");
            }
        }
    }

}
