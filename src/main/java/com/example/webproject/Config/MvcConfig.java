package com.example.webproject.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addViewController("/login").setViewName("form/login");

        registry.addViewController("/signup").setViewName("form/signup");

        registry.addViewController("/main").setViewName("form/index");

    }
}