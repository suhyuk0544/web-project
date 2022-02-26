package com.example.webproject;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    // 요청 - 뷰 연결
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/main").setViewName("form/index");
        registry.addViewController("/login").setViewName("form/login");
//        registry.addViewController("/").setViewName("form/login");
    }
}