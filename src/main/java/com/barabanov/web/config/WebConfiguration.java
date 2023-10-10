package com.barabanov.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Profile("web")
@Configuration
public class WebConfiguration {
    // Настройка веб конфига. Настройка наших сервлетов, фильтров и т.д.
}
