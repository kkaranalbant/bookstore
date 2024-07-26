/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

/**
 *
 * @author kaan
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index.html");
        registry.addViewController("/book/get").setViewName("book-info");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/book/add").setViewName("add-book");
        registry.addViewController("/book/get-all-auth").setViewName("book-view-all");
        registry.addViewController("/book/update-panel").setViewName("update-book");
        registry.addViewController("/book/delete-panel").setViewName("delete-book");
        registry.addViewController("/customer/addv1-panel").setViewName("add-customer");
        registry.addViewController("/customer/getv1-panel").setViewName("get-customer");
        registry.addViewController("/customer/updatev1-panel").setViewName("update-customer");
        registry.addViewController("/customer/deletev1-panel").setViewName("delete-customer");
        registry.addViewController("/customer/getv2-panel").setViewName("get-self-customer");
        registry.addViewController("/customer/updatev2-panel").setViewName("update-self-customer");
        registry.addViewController("/customer/register-panel").setViewName("add-self-customer");
        registry.addViewController("/card/delete-panel").setViewName("remove-card");
        registry.addViewController("/mod/add-panel").setViewName("add-mod");
        registry.addViewController("/mod/update-panel").setViewName("update-mod");
        registry.addViewController("/mod/delete-panel").setViewName("delete-mod");
        registry.addViewController("/card/get-cards-panel").setViewName("view-card");
        registry.addViewController("/card/add-panel").setViewName("add-card");
        registry.addViewController("/fav/get-all-customer").setViewName("customer-fav");
        registry.addViewController("/basket/get").setViewName("customer-basket");
        registry.addViewController("/comment/getv3").setViewName("customer-comment");
        registry.addViewController("/customer/purchase-panel").setViewName("customer-purchase");
        registry.addViewController("/admin/panel").setViewName("admin-main-panel");
        registry.addViewController("/customer/main-panel").setViewName("customer-main-panel");
        registry.addViewController("/mod/main-panel").setViewName("mod-main-panel");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/customer/verify").setViewName("verification");
        registry.addViewController("/customer/send-reset-mail").setViewName("forgot-password-panel");
        registry.addViewController("/customer/pass-reset-panel").setViewName("reset-password-panel");
    }

    @Bean
    public ThymeleafViewResolver thymeleafViewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(thymeleafTemplateEngine());
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }

    @Bean
    public SpringTemplateEngine thymeleafTemplateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setEnableSpringELCompiler(true);
        templateEngine.setTemplateResolver(thymeleafTemplateResolver());
        return templateEngine;
    }

    @Bean
    public SpringResourceTemplateResolver thymeleafTemplateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("classpath:/templates/"); // Templates dizini
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }
}
