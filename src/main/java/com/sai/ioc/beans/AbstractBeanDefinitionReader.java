package com.sai.ioc.beans;

import com.sai.ioc.beans.factory.AutowireCapableBeanFactory;
import com.sai.ioc.beans.factory.BeanDefinitionRegistry;
import com.sai.ioc.beans.io.ResourceLoader;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {


//    private Map<String, BeanDefinition> registry;

    private final BeanDefinitionRegistry registry;

    private ResourceLoader resourceLoader;

    public AbstractBeanDefinitionReader(ResourceLoader resourceLoader, BeanDefinitionRegistry beanDefinitionRegistry) {
//        this.registry = new HashMap<String, BeanDefinition>();
        this.registry = beanDefinitionRegistry;
        this.resourceLoader = resourceLoader;
    }

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    //    public Map<String, BeanDefinition> getRegistry() {
//        return registry;
//    }

    @Override
    public final BeanDefinitionRegistry getRegistry() {
        return this.registry;
    }

    protected ResourceLoader getResourceLoader() {
        return resourceLoader;
    }
}
