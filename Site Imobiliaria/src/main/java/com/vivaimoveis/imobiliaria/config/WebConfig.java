package com.vivaimoveis.imobiliaria.config;

import com.vivaimoveis.imobiliaria.core.converter.StringToUserRoleConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public StringToUserRoleConverter stringToUserRoleConverter() {
        return new StringToUserRoleConverter();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToUserRoleConverter());
    }
}
