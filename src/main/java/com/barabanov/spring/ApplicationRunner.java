package com.barabanov.spring;

import com.barabanov.spring.ioc.Container;
import com.barabanov.spring.service.UserService;


public class ApplicationRunner
{
    public static void main(String[] args)
    {
        var container = new Container();

//        var connectionPool = new ConnectionPool();
//        var userRepository = new UserRepository(connectionPool);
//        var companyRepository = new CompanyRepository(connectionPool);
//        var userService = new UserService(userRepository, companyRepository);

        var userService = container.get(UserService.class);
    }
}
