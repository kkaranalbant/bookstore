/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.bookstore.config;

import com.kaan.bookstore.model.Role;
import com.kaan.bookstore.security.JwtAuthFilter;
import com.kaan.bookstore.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author kaan
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthFilter jwtFilter;

    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfiguration(JwtAuthFilter jwtFilter, UserDetailsServiceImpl userDetailsService) {
        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeHttpRequestsCustomizer -> authorizeHttpRequestsCustomizer.requestMatchers("/customer/create").permitAll())
                .authorizeHttpRequests(authorizeHttpRequestsCustomizer -> authorizeHttpRequestsCustomizer.requestMatchers("/customer/update").hasAuthority(Role.CUSTOMER.getAuthority()))
                .authorizeHttpRequests(authorizeHttpRequestsCustomizer -> authorizeHttpRequestsCustomizer.requestMatchers("/customer/updatebyid").hasAuthority(Role.MODERATOR.getAuthority()))
                .authorizeHttpRequests(authorizeHttpRequestsCustomizer -> authorizeHttpRequestsCustomizer.requestMatchers("/customer/buy").hasAuthority(Role.CUSTOMER.getAuthority()))
                .authorizeHttpRequests(authorizeHttpRequestsCustomizer -> authorizeHttpRequestsCustomizer.requestMatchers("/customer/topup").hasAuthority(Role.CUSTOMER.getAuthority()))
                .authorizeHttpRequests(authorizeHttpRequestsCustomizer -> authorizeHttpRequestsCustomizer.requestMatchers("/basket/removebook").hasAuthority(Role.CUSTOMER.getAuthority()))
                .authorizeHttpRequests(authorizeHttpRequestsCustomizer -> authorizeHttpRequestsCustomizer.requestMatchers("/basket/emptybasket").hasAuthority(Role.CUSTOMER.getAuthority()))
                .authorizeHttpRequests(authorizeHttpRequestsCustomizer -> authorizeHttpRequestsCustomizer.requestMatchers("/basket/addtobasket").hasAuthority(Role.CUSTOMER.getAuthority()))
                .authorizeHttpRequests(authorizeHttpRequestsCustomizer -> authorizeHttpRequestsCustomizer.requestMatchers("/book/create").hasAuthority(Role.MODERATOR.getAuthority()))
                .authorizeHttpRequests(authorizeHttpRequestsCustomizer -> authorizeHttpRequestsCustomizer.requestMatchers("/book/update").hasAuthority(Role.MODERATOR.getAuthority()))
                .authorizeHttpRequests(authorizeHttpRequestsCustomizer -> authorizeHttpRequestsCustomizer.requestMatchers("/book/delete").hasAuthority(Role.MODERATOR.getAuthority()))
                .authorizeHttpRequests(authorizeHttpRequestsCustomizer -> authorizeHttpRequestsCustomizer.requestMatchers("/card/create").hasAuthority(Role.CUSTOMER.getAuthority()))
                .authorizeHttpRequests(authorizeHttpRequestsCustomizer -> authorizeHttpRequestsCustomizer.requestMatchers("/card/deletebynumber").hasAuthority(Role.CUSTOMER.getAuthority()))
                .authorizeHttpRequests(authorizeHttpRequestsCustomizer -> authorizeHttpRequestsCustomizer.requestMatchers("/card/deleteall").hasAuthority(Role.CUSTOMER.getAuthority()))
                .authorizeHttpRequests(authorizeHttpRequestsCustomizer -> authorizeHttpRequestsCustomizer.requestMatchers("/card/deletebynumberandid").hasAuthority(Role.MODERATOR.getAuthority()))
                .authorizeHttpRequests(authorizeHttpRequestsCustomizer -> authorizeHttpRequestsCustomizer.requestMatchers("/moderator/create").hasAuthority(Role.ADMIN.getAuthority()))
                .authorizeHttpRequests(authorizeHttpRequestsCustomizer -> authorizeHttpRequestsCustomizer.requestMatchers("/moderator/updatebyid").hasAuthority(Role.ADMIN.getAuthority()))
                .authorizeHttpRequests(authorizeHttpRequestsCustomizer -> authorizeHttpRequestsCustomizer.requestMatchers("/moderator/update").hasAuthority(Role.MODERATOR.getAuthority()))
                .sessionManagement(sessionManagementCustomizer -> sessionManagementCustomizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(getBCryptPasswordEncoder());
        return authenticationProvider ;
    }
    
    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
