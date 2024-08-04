/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.config;

import com.kaan.deneme.dao.RecaptchaResponse;
import com.kaan.deneme.service.RecaptchaService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author kaan
 */
@Component
public class RecaptchaFilter extends OncePerRequestFilter {

    private RecaptchaService recaptchaService;

    public RecaptchaFilter(RecaptchaService recaptchaService) {
        this.recaptchaService = recaptchaService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getMethod().equals("POST")) {
            String recaptchaToken = request.getParameter("recaptchaToken");
            if (!((recaptchaToken == null)) && !recaptchaToken.isBlank()) {
                RecaptchaResponse captchaResponse = recaptchaService.validate(recaptchaToken);
                if (!captchaResponse.isSuccess()) {
                    throw new BadCredentialsException("Invalid Captcha !");
                }
            }
        }
        doFilter(request, response, filterChain);
    }

}
