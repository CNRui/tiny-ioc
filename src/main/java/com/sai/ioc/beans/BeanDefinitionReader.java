package com.sai.ioc.beans;

import com.sai.ioc.beans.factory.BeanDefinitionRegistry;

public interface BeanDefinitionReader {

    BeanDefinitionRegistry getRegistry();

    void loadBeanDefinition() throws Exception;
}
