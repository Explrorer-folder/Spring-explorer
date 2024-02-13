package com.barabanov.spring.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.transaction.annotation.Transactional;


@Aspect
@Slf4j
public class FirstAspect
{

    @Pointcut("com.barabanov.spring.aop.CommonPointcuts.isControllerLayer() && @annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void hasGetMapping()
    {}

    //Первый параметр Model, остальных параметров любое количество.
    @Pointcut("com.barabanov.spring.aop.CommonPointcuts.isControllerLayer() && args(org.springframework.ui.Model,..)")
    public void hasModelParam()
    {}

    @Pointcut("com.barabanov.spring.aop.CommonPointcuts.isControllerLayer() && @args(com.barabanov.spring.validation.UserInfo,..)")
    public void hasUserInfoParamAnnotation()
    {}

    @Pointcut("bean(*Service)")
    public void isServiceLayerBean()
    {}

    @Pointcut("execution(public * com.barabanov.spring.service.*Service.findById(*))")
    public void anyFindByIdServiceMethod()
    {}

    //    @Before("execution(public * com.barabanov.spring.service.*Service.findById(*))")
    @Before(value = "anyFindByIdServiceMethod()" +
            "&& args(id)" +
            "&& target(service)" +
            "&& this(serviceProxy)" +
            "&& @within(transactional)",
            argNames = "joinPoint,id,service,serviceProxy,transactional")
    public void addLogging(JoinPoint joinPoint,
                           Object id,
                           Object service,
                           Object serviceProxy,
                           Transactional transactional)
    {
        log.info("before - invoked findById method in class {}, with id {}", service, id);
    }


    @AfterReturning(value = "anyFindByIdServiceMethod()" +
            "&& target(service)",
            returning = "result")
    public void addLogging(Object result, Object service)
    {
        log.info("after returning - invoked findById method in class {}, with result {}", service, result);
    }


    @AfterThrowing(value = "anyFindByIdServiceMethod()" +
            "&& target(service)",
            throwing = "ex")
    public void addLoggingThrowing(Throwable ex, Object service)
    {
        log.info("after returning - invoked findById method in class {}, exception {}: {}", service, ex.getCause(), ex.getMessage());
    }


    @After("anyFindByIdServiceMethod() && target(service)")
    public void addLogging(Object service)
    {
        log.info("after finally - invoked findById method in class {}", service);
    }


    @Around("anyFindByIdServiceMethod() && target(service) && args(id)")
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
