package com.barabanov.spring;

import com.barabanov.spring.database.pool.ConnectionPool;
import com.barabanov.spring.database.repository.CompanyRepository;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationRunner
{
    public static void main(String[] args)
    {
        String value = "hell!";
        System.out.println(CharSequence.class.isAssignableFrom(value.getClass()));
        System.out.println(BeanFactoryPostProcessor.class.isAssignableFrom(value.getClass()));

        try (var context = new ClassPathXmlApplicationContext("application.xml"))
        {
        var connectionPool = context.getBean("p2", ConnectionPool.class);
        System.out.println(connectionPool);

        var companyRepository = context.getBean("companyRepository", CompanyRepository.class);
        System.out.println(companyRepository);
        }

    }

}
