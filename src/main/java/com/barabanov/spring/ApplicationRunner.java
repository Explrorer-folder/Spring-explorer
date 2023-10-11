package com.barabanov.spring;

import com.barabanov.spring.config.ApplicationConfiguration;
import com.barabanov.spring.database.pool.ConnectionPool;
import com.barabanov.spring.service.CompanyService;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationRunner
{
    public static void main(String[] args)
    {
        String value = "hell!";
        System.out.println(CharSequence.class.isAssignableFrom(value.getClass()));
        System.out.println(BeanFactoryPostProcessor.class.isAssignableFrom(value.getClass()));

        try (var context = new AnnotationConfigApplicationContext())
        {
            context.register(ApplicationConfiguration.class);
            context.getEnvironment().setActiveProfiles("web","prod");
            context.refresh();

            var connectionPool = context.getBean("pool1", ConnectionPool.class);
            System.out.println(connectionPool);

            var companyService = context.getBean(CompanyService.class);
            System.out.println(companyService.findById(1));
        }

    }

}
