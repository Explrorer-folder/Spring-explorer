package com.barabanov.spring.bfpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;


public class LogBeanFactoryPostProcessor implements BeanFactoryPostProcessor, Ordered
{
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException
    {
        // тут можно самим изменять bean Definitions т.к. есть доступ к BeanFactory.
        System.out.println();
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
