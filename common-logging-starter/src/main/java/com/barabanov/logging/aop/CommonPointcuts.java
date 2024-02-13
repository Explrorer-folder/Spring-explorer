package com.barabanov.logging.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
public class CommonPointcuts
{
    @Pointcut(value = "@within(org.springframework.stereotype.Controller)")
    public void isControllerLayer()
    {}


    @Pointcut("within(com.barabanov.*.service.*)")
    public void isServiceLayer()
    {}

    @Pointcut("this(org.springframework.stereotype.Repository)")
    public void isRepositoryLayer()
    {}
}
