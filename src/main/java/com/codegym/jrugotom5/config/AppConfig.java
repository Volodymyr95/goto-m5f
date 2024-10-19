package com.codegym.jrugotom5.config;

import com.codegym.jrugotom5.converter.AdvertToDtoConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(advertToDtoConverter());
        return modelMapper;
    }

    @Bean
    public AdvertToDtoConverter advertToDtoConverter() {
        return new AdvertToDtoConverter();
    }
}
