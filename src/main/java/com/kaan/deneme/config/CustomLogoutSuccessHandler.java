/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.config;

import com.kaan.deneme.service.IpService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

/**
 *
 * @author kaan
 */
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private static Logger logger;

    private IpService ipService;

    static {
        logger = LoggerFactory.getLogger(CustomLogoutSuccessHandler.class);
    }

    public CustomLogoutSuccessHandler(IpService ipService) {
        this.ipService = ipService;
    }

    @Override

    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        if (authentication != null) {

            String username = authentication.getName();

            logger.info("Person with username " + username + " has logged out.\n"
                    + "Ip : " + ipService.getIpAddress(request));

        }

        response.setStatus(HttpServletResponse.SC_OK);

        response.sendRedirect("/");

        Cookie cookie = new Cookie("JSESSIONID", null);

        cookie.setMaxAge(0);

        cookie.setPath("/");

        response.addCookie(cookie);

        cookie = new Cookie("Authorization", null);

        cookie.setMaxAge(0);

        cookie.setPath("/");

        response.addCookie(cookie);

        SecurityContextHolder.clearContext();

    }

}
