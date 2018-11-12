package com.algaworks.despacheweb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.algaworks.despacheweb.service.ClienteService;

@Configuration
@ComponentScan(basePackageClasses = {ClienteService.class})
public class ServiceConfig {

}
