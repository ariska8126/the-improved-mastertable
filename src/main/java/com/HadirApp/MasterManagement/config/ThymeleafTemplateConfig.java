///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.HadirApp.MasterManagement.config;
//
//import java.nio.charset.StandardCharsets;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.thymeleaf.spring5.SpringTemplateEngine;
//import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
//import org.thymeleaf.templatemode.TemplateMode;
//
///**
// *
// * @author creative
// */
//@Configuration
//public class ThymeleafTemplateConfig {
//    @Bean
//    public SpringTemplateEngine springTemplateEngine() {
//        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//        templateEngine.addTemplateResolver(htmlTemplateResolver());
//        
//        return templateEngine;
//    }
//    
//    @Bean
//    public SpringResourceTemplateResolver htmlTemplateResolver(){
//        SpringResourceTemplateResolver emailTemplateResolver = new SpringResourceTemplateResolver();
//        emailTemplateResolver.setPrefix("/templates/");
//        emailTemplateResolver.setSuffix(".html");
//        emailTemplateResolver.setTemplateMode(TemplateMode.HTML);
//        emailTemplateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
//        return emailTemplateResolver;
//    }
//}
