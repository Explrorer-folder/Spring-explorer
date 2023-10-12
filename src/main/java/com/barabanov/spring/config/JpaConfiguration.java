package com.barabanov.spring.config;

import com.barabanov.spring.config.condition.JpaCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;


@Conditional(JpaCondition.class)
@Configuration
public class JpaConfiguration
{

    /*@Bean
    @ConfigurationProperties(prefix = "db")
    public DatabaseProperties databaseProperties()
    {
        return new DatabaseProperties();
    }*/

    @PostConstruct
    void init()
    {
        System.out.println("Jpa configuration is enabled");
    }
}
