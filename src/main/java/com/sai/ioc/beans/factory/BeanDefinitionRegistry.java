package com.sai.ioc.beans.factory;

import com.sai.ioc.beans.BeanDefinition;

public interface BeanDefinitionRegistry {


    void registerBeanDefinition(String name, BeanDefinition beanDefinition);

    BeanDefinition getBeanDefinition(String name) throws Exception;

    String[] getBeanDefinitionNames();

}
