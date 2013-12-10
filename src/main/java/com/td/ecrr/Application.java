package com.td.ecrr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.spring3.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;


@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {
    
    @Bean
    ServletContextTemplateResolver templateResolver() {
        ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        return resolver;
    }
    
    @Bean
    SpringTemplateEngine engine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver());
        return engine;
    }
    
    @Bean
    ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(engine());
        return viewResolver;
    }
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
