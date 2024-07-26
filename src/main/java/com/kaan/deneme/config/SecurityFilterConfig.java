/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.config;

import com.kaan.deneme.model.Role;
import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author kaan
 */
@Configuration
@EnableWebSecurity
public class SecurityFilterConfig {

    private JwtAuthenticationProvider jwtAuthenticationProvider;

    private AuthenticationFilter authenticationFilter;

    private JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;

    private CustomLogoutSuccessHandler logoutSuccessHandler;

    private RecaptchaFilter recaptchaFilter;

    @Autowired
    public SecurityFilterConfig(JwtAuthenticationProvider jwtAuthenticationProvider, AuthenticationFilter authenticationFilter, JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler, CustomLogoutSuccessHandler logoutSuccessHandler, RecaptchaFilter recaptchaFilter) {
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
        this.authenticationFilter = authenticationFilter;
        this.jwtAuthenticationSuccessHandler = jwtAuthenticationSuccessHandler;
        this.logoutSuccessHandler = logoutSuccessHandler;
        this.recaptchaFilter = recaptchaFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .sessionManagement((sessionManagementCustomizer) -> sessionManagementCustomizer.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .sessionManagement((sessionManagementCustomizer) -> sessionManagementCustomizer.sessionFixation((sessionFixationCustomizer) -> sessionFixationCustomizer.none()))
                .authenticationProvider(jwtAuthenticationProvider)
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(recaptchaFilter, AuthenticationFilter.class)
                .csrf(csrfCustomizer -> csrfCustomizer.disable())
                .httpBasic(httpBasicCustomizer -> httpBasicCustomizer.disable())
                .authorizeHttpRequests(authorize -> authorize
                .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                .requestMatchers("/").permitAll()
                .requestMatchers("/customer/getv1-panel").hasAnyAuthority(Role.ADMIN.name(), Role.MOD.name())
                .requestMatchers("/customer/getv1").hasAnyAuthority(Role.ADMIN.name(), Role.MOD.name())
                .requestMatchers("/customer/getv2").hasAuthority(Role.CUSTOMER.name())
                .requestMatchers("/customer/deletev1-panel").hasAnyAuthority(Role.ADMIN.name(), Role.MOD.name())
                .requestMatchers("/customer/deletev1").hasAnyAuthority(Role.ADMIN.name(), Role.MOD.name())
                .requestMatchers("/customer/deletev2").hasAuthority(Role.CUSTOMER.name())
                .requestMatchers("/customer/addv1-panel").hasAnyAuthority(Role.ADMIN.name(), Role.MOD.name())
                .requestMatchers("/customer/addv1").hasAnyAuthority(Role.ADMIN.name(), Role.MOD.name())
                .requestMatchers("/customer/register-panel").permitAll()
                .requestMatchers("/customer/addv2").permitAll()
                .requestMatchers("/customer/updatev1-panel").hasAnyAuthority(Role.ADMIN.name(), Role.MOD.name())
                .requestMatchers("/customer/updatev1").hasAnyAuthority(Role.ADMIN.name(), Role.MOD.name())
                .requestMatchers("/customer/updatev2").hasAuthority(Role.CUSTOMER.name())
                .requestMatchers("/customer/updatev2-panel").hasAuthority(Role.CUSTOMER.name())
                .requestMatchers("/customer/purchase", "/customer/add-balance", "/customer/add-balance-panel").hasAuthority(Role.CUSTOMER.name())
                .requestMatchers("/customer/main-panel").hasAuthority(Role.CUSTOMER.name())
                .requestMatchers("/customer/verify","/customer/forgot-password","/customer/pass-reset-panel","/customer/send-reset-mail","/customer/reset-password").anonymous()
                .requestMatchers("/mod/get-all").hasAuthority(Role.ADMIN.name())
                .requestMatchers("/mod/get").hasAuthority(Role.ADMIN.name())
                .requestMatchers("/mod/add").hasAuthority(Role.ADMIN.name())
                .requestMatchers("/mod/update").hasAuthority(Role.ADMIN.name())
                .requestMatchers("/mod/delete").hasAuthority(Role.ADMIN.name())
                .requestMatchers("/mod/main-panel").hasAuthority(Role.MOD.name())
                .requestMatchers("/mod/delete-panel", "/mod/update-panel", "/mod/add-panel").hasAuthority(Role.ADMIN.name())
                .requestMatchers("/fav/get-all-book").permitAll() // Herkes erişebilir
                .requestMatchers("/fav/add", "/fav/delete", "/fav/get-all-customer").hasAuthority(Role.CUSTOMER.name())
                .requestMatchers("/comment/add", "/comment/update", "/comment/delete", "/comment/getv3").hasAuthority(Role.CUSTOMER.name())
                .requestMatchers("/comment/getv1").permitAll()
                .requestMatchers("/comment/getv2").hasAuthority(Role.MOD.name())
                //.requestMatchers("/comment/getv3").permitAll()
                .requestMatchers("/card/delete-panel", "/card/delete", "/card/add", "/card/get-all", "card/get-cards-panel", "/card/add-panel").hasAuthority(Role.CUSTOMER.name())
                .requestMatchers("/book/get-all", "/book/get", "/book/get-image", "/book/filter").permitAll()
                .requestMatchers("/book/delete", "/book/delete-panel", "/book/add", "/book/update", "/book/add-panel", "/book/get-all-auth", "/book/update-panel", "/book/get-book-json", "/book/add-image", "/book/delete-image").hasAnyAuthority(Role.ADMIN.name(), Role.MOD.name())
                .requestMatchers("/basket/**").hasAuthority(Role.CUSTOMER.name())
                .requestMatchers("/admin/**").hasAuthority(Role.ADMIN.name())
                .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                .successHandler(jwtAuthenticationSuccessHandler)
                .permitAll()
                )
                .formLogin((formLoginCustomizer) -> formLoginCustomizer.loginPage("/login"))
                .logout((logout) -> logout.permitAll())
                .logout((logout) -> logout.logoutSuccessHandler(logoutSuccessHandler)); //                .logout((logout) -> logout.logoutSuccessUrl("/"))
        //                .logout((logoutCustomizer) -> logoutCustomizer.deleteCookies("JSESSIONID", "Authorization"))
        //                .logout((logoutCustomizer) -> logoutCustomizer.clearAuthentication(true)) ;

        return httpSecurity.build();
    }

}
