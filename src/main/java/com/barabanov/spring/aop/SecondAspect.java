package com.barabanov.spring.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Slf4j
@Aspect
@Component
public class SecondAspect
{
    @Around("com.barabanov.spring.aop.FirstAspect.anyFindByIdServiceMethod() && target(service) && args(id)")
    public Object addLoggingAround(ProceedingJoinPoint joinPoint, Object service, Object id) throws Throwable
    {
        log.info("AROUND before - invoked findById method in class {}, with id {}", service, id);
        try
        {
            Object result = joinPoint.proceed();
            log.info("AROUND after returning - invoked findById method in class {}, with result {}", service, result);
            return result;
        }
        catch (Throwable ex)
        {
            log.info("AROUND after returning - invoked findById method in class {}, exception {}: {}", service, ex.getCause(), ex.getMessage());
            throw ex;
        }
        finally
        {
            log.info("AROUND after finally - invoked findById method in class {}", service);
        }
    }
}
