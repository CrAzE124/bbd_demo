package com.example.consumer.config;

import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.logging.Logger;

@Configuration
public class LoggerConfiguration {
    @Bean
    @Scope("prototype")
    Logger logger(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getDeclaredType().getName());
    }
}
