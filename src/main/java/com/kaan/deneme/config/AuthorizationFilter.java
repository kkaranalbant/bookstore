/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.config;

import com.kaan.deneme.model.Role;
import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 *
 * @author kaan
 */
@Configuration
@EnableWebSecurity
public class AuthorizationFilter {

    private AuthenticationProvider authenticationProvider;

    private AuthenticationFilter authenticationFilter;
    
    

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                //.authenticationProvider(authenticationProvider)
                //.addFilter(authenticationFilter)
                //.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(csrfCustomizer -> csrfCustomizer.disable())
                .httpBasic(httpBasicCustomizer -> httpBasicCustomizer.disable())
                .authorizeHttpRequests(authorize -> authorize
                .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                .requestMatchers("/").permitAll()
                .requestMatchers("/customer/getv1").hasAnyAuthority(Role.ADMIN.name(), Role.MOD.name())
                .requestMatchers("/customer/getv2").hasAuthority(Role.CUSTOMER.name())
                .requestMatchers("/customer/deletev1").hasAnyAuthority(Role.ADMIN.name(), Role.MOD.name())
                .requestMatchers("/customer/deletev2").hasAuthority(Role.CUSTOMER.name())
                .requestMatchers("/customer/addv1").hasAnyAuthority(Role.ADMIN.name(), Role.MOD.name())
                .requestMatchers("/customer/addv2").hasAuthority(Role.CUSTOMER.name())
                .requestMatchers("/customer/updatev1").hasAnyAuthority(Role.ADMIN.name(), Role.MOD.name())
                .requestMatchers("/customer/updatev2").hasAuthority(Role.CUSTOMER.name())
                .requestMatchers("/customer/purchase").hasAuthority(Role.CUSTOMER.name())
                .requestMatchers("/mod/get-all").hasAuthority(Role.ADMIN.name())
                .requestMatchers("/mod/get").hasAuthority(Role.ADMIN.name())
                .requestMatchers("/mod/add").hasAuthority(Role.ADMIN.name())
                .requestMatchers("/mod/update").hasAuthority(Role.ADMIN.name())
                .requestMatchers("/mod/delete").hasAuthority(Role.ADMIN.name())
                .requestMatchers("/fav/get-all-book").permitAll() // Herkes erişebilir
                .requestMatchers("/fav/add", "/fav/delete", "/fav/get-all-customer").hasAuthority(Role.CUSTOMER.name())
                .requestMatchers("/comment/add", "/comment/update", "/comment/delete", "/comment/getv3").hasAuthority(Role.CUSTOMER.name())
                .requestMatchers("/comment/getv1").permitAll()
                .requestMatchers("/comment/getv3").hasAnyAuthority(Role.ADMIN.name(), Role.MOD.name())
                .requestMatchers("/card/delete", "/card/add", "/card/get-all").hasAuthority(Role.CUSTOMER.name())
                .requestMatchers("/book/get-all", "/book/get").permitAll()
                .requestMatchers("/book/delete", "/book/add", "/book/update").hasAnyAuthority(Role.ADMIN.name(), Role.MOD.name())
                .requestMatchers("/basket/**").hasAuthority(Role.CUSTOMER.name())
                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin((form) -> form
                .loginPage("/login")
                .permitAll()
                )
                .logout((logout) -> logout.permitAll());

        return httpSecurity.build();
    }
}
